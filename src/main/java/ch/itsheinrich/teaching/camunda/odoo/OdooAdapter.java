/*
 * Copyright (C) 2017 Peter Heinrich
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package ch.itsheinrich.teaching.camunda.odoo;

import ch.itsheinrich.teaching.camunda.model.Customer;
import ch.itsheinrich.teaching.camunda.model.OrderLine;
import ch.itsheinrich.teaching.camunda.model.Product;
import ch.itsheinrich.teaching.camunda.model.SaleOrder;
import ch.qos.logback.core.CoreConstants;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 *
 * @author Peter Heinrich
 */
public class OdooAdapter {

    private final OdooConnection config;
    private final XmlRpcClient objectClient;
    private final XmlRpcClient reportClient;

    public OdooAdapter(OdooConnection config) {
        this.config = config;
        objectClient = new XmlRpcClient() {
            {
                setConfig(new XmlRpcClientConfigImpl() {
                    {
                        setServerURL(config.getObjectUrl());
                    }
                });
            }
        };
        reportClient = new XmlRpcClient() {
            {
                setConfig(new XmlRpcClientConfigImpl() {
                    {
                        setServerURL(config.getReportUrl());
                    }
                });
            }
        };
    }

    public void printFields(String model) throws XmlRpcException {
        Object res = (Map<String, Map<String, Object>>) objectClient.execute("execute_kw", asList(
                config.getDb(),
                config.getUserId(),
                config.getPassword(),
                model, "fields_get",
                emptyList(),
                new HashMap() {
            {
                put("attributes", asList("string", "type"));
            }
        }
        ));
        System.out.println("ch.itsheinrich.teaching.camunda.odoo.OdooConnector.printFields() " + res);
    }

    public List<Customer> readFiveCustomers() throws XmlRpcException {

        List res = asList((Object[]) objectClient.execute(
                "execute_kw", asList(
                        config.getDb(),
                        config.getUserId(),
                        config.getPassword(),
                        "res.partner",
                        "search_read",
                        asList(
                                asList(
                                        asList("is_company", "=", true),
                                        asList("customer", "=", true)
                                )
                        ),
                        new HashMap() {
                    {
                        put("fields", asList(
                                "name",
                                "comment"
                        ));
                        put("offset", 0);
                        put("limit", 5);
                    }
                })));
        List<Customer> results = new LinkedList<>();

        res.forEach((odooResultObject) -> {
            results.add(new Customer((HashMap) odooResultObject));
        });
        return results;
    }

    public Customer loadCustomers(int id) throws XmlRpcException {

        List res = asList((Object[]) objectClient.execute(
                "execute_kw", asList(
                        config.getDb(),
                        config.getUserId(),
                        config.getPassword(),
                        "res.partner",
                        "search_read",
                        asList(
                                asList(
                                        asList("id", "=", id)
                                )
                        ),
                        new HashMap() {
                    {
                        put("fields", asList(
                                "name",
                                "street",
                                "city"
                        ));
                        put("offset", 0);
                        put("limit", 1);
                    }
                })));

        if (res.size() == 0) {
            return null;
        }
        Customer c = new Customer((HashMap) res.get(0));
        c.setCity((String) ((HashMap) res.get(0)).get("city"));
        c.setStreet((String) ((HashMap) res.get(0)).get("street"));
        return c;
    }

    public List<Product> readFiveProducts() throws XmlRpcException {

        List odooResultList = asList((Object[]) objectClient.execute(
                "execute_kw", asList(
                        config.getDb(),
                        config.getUserId(),
                        config.getPassword(),
                        "product.product",
                        "search_read",
                        asList(
                                asList(
                                        asList("price", ">", 0.0)
                                )
                        ),
                        new HashMap() {
                    {
                        put("fields", asList(
                                "name",
                                "product_tmpl_id"
                        ));
                        put("offset", 0);
                        put("limit", 5);
                    }
                })));
        List<Product> results = new LinkedList<>();
        for (Object odooResultObject : odooResultList) {
            Product p = new Product((HashMap) odooResultObject);
            p.setPrice(
                    readListPrice(
                            (Integer) ((Object[]) ((HashMap) odooResultObject).get("product_tmpl_id"))[0]
                    )
            );
            results.add(p);
        }
        return results;
    }

    public double readListPrice(int productTemplateId) throws XmlRpcException {

        List odooResultList = asList((Object[]) objectClient.execute(
                "execute_kw", asList(
                        config.getDb(),
                        config.getUserId(),
                        config.getPassword(),
                        "product.template",
                        "search_read",
                        asList(
                                asList(
                                        asList("id", "=", productTemplateId)
                                )
                        ),
                        new HashMap() {
                    {
                        put("fields", asList(
                                "list_price"
                        ));
                        put("offset", 0);
                        put("limit", 1);
                    }
                })));
        if (odooResultList.size() == 0) {
            return 0.0;
        }
        return (Double) ((HashMap) (odooResultList.get(0))).get("list_price");
    }

    public List<SaleOrder> getFiveQuotations() throws XmlRpcException {
        List odooResultList = asList((Object[]) objectClient.execute(
                "execute_kw", asList(
                        config.getDb(),
                        config.getUserId(),
                        config.getPassword(),
                        "sale.order",
                        "search_read",
                        asList(
                                asList()
                        ),
                        new HashMap() {
                    {
                        put("fields", asList(
                                "name",
                                "order_line",
                                "date_order",
                                "currency_id",
                                "partner_id",
                                "partner_invoice_id",
                                "partner_shipping_id",
                                "pricelist_id"
                        ));
                        put("offset", 0);
                        put("limit", 5);
                    }
                })));
        List<SaleOrder> results = new LinkedList<>();

        for (Object odooResultObject : odooResultList) {

            SaleOrder saleOrder = new SaleOrder((HashMap) odooResultObject);
            odooResultList = asList((Object[]) objectClient.execute(
                    "execute_kw", asList(
                            config.getDb(),
                            config.getUserId(),
                            config.getPassword(),
                            "sale.order.line",
                            "search_read",
                            asList(
                                    asList()
                            ),
                            new HashMap() {
                        {
                            put("fields", asList(
                                    "customer_lead",
                                    "name",
                                    "oder_id",
                                    "product_id",
                                    "product_uom",
                                    "price_unit",
                                    "product_uom_qty"
                            ));

                        }
                    })));

            for (Object orderLineRes : odooResultList) {
                saleOrder.getOrderLine().add(new OrderLine((HashMap) orderLineRes));
            }
            results.add(saleOrder);
        }
        return results;
    }

    public SaleOrder getQuotation(int id) throws XmlRpcException {
        List odooResultList = asList((Object[]) objectClient.execute(
                "execute_kw", asList(
                        config.getDb(),
                        config.getUserId(),
                        config.getPassword(),
                        "sale.order",
                        "search_read",
                        asList(
                                asList(
                                        asList("id", "=", id)
                                )
                        ),
                        new HashMap() {
                    {
                        put("fields", asList(
                                "name",
                                "order_line",
                                "date_order",
                                "currency_id",
                                "partner_id",
                                "partner_invoice_id",
                                "partner_shipping_id",
                                "pricelist_id"
                        ));
                        put("offset", 0);
                        put("limit", 1);
                    }
                })));

        Object odooResultObject = odooResultList.get(0);

        SaleOrder saleOrder = new SaleOrder((HashMap) odooResultObject);
        odooResultList = asList((Object[]) objectClient.execute(
                "execute_kw", asList(
                        config.getDb(),
                        config.getUserId(),
                        config.getPassword(),
                        "sale.order.line",
                        "search_read",
                        asList(
                                asList()
                        ),
                        new HashMap() {
                    {
                        put("fields", asList(
                                "customer_lead",
                                "name",
                                "oder_id",
                                "product_id",
                                "product_uom",
                                "price_unit",
                                "product_uom_qty"
                        ));

                    }
                })));

        for (Object orderLineRes : odooResultList) {
            saleOrder.getOrderLine().add(new OrderLine((HashMap) orderLineRes));
        }

        return saleOrder;
    }

    public byte[] printSaleOrder(int orderId) throws XmlRpcException {
        // sale.report_saleorder
        final Map<String, Object> result = (Map<String, Object>) reportClient.execute(
                "render_report", asList(
                        config.getDb(),
                        config.getUserId(),
                        config.getPassword(),
                        "sale.report_saleorder",
                        asList(orderId)));
        final byte[] report_data = DatatypeConverter.parseBase64Binary(
                (String) result.get("result"));
        return report_data;
    }

    /**
     * ************** EXPERIMENTAL ***********************
     */
    public int createSaleOrder(Integer partnerId, List<OrderLine> orderList) throws XmlRpcException {

        List values = asList(new HashMap() {
            {
                put("name", "Camunda Order " + OdooUtils.dateToString(new Date()));
                put("date_order", OdooUtils.dateToString(new Date()));
                put("currency_id", "6");
                put("partner_id", partnerId.toString());
                put("partner_invoice_id", partnerId.toString());
                put("partner_shipping_id", partnerId.toString());
                put("pricelist_id", "1");
            }
        });

        int id = (Integer) objectClient.execute("execute_kw", asList(
                config.getDb(),
                config.getUserId(),
                config.getPassword(),
                "sale.order", "create",
                values
        ));

        List<HashMap> orderLines = new ArrayList<>();
        for (OrderLine line : orderList) {
            createOrderLine(line, id);
        }

        System.out.println("ch.itsheinrich.teaching.camunda.odoo.OdooAdapter.createSaleOrder() EXECUTED! RESULT=" + id);
        return id;
    }

    public void createOrderLine(OrderLine line, int orderId) throws XmlRpcException {

        HashMap lineMap = line.toHashMap();
        lineMap.put("order_id", orderId);
        List values = asList(lineMap);

        int id = (Integer) objectClient.execute("execute_kw", asList(
                config.getDb(),
                config.getUserId(),
                config.getPassword(),
                "sale.order.line", "create",
                values
        ));

    }
}
