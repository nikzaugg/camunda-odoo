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
package ch.itsheinrich.teaching.camunda.model;

import ch.itsheinrich.teaching.camunda.odoo.OdooUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Peter Heinrich
 */
public class SaleOrder {

    private final int odooId;
    private final String name;
    private List<OrderLine> orderLine = new ArrayList<>();
    private final Date orderDate;
    private final Integer currencyId;
    private final Integer partnerId;
    private final Integer partnerInvoiceId;
    private final Integer partnerShippingId;
    private final Integer pricelistId;

    public SaleOrder(int odooId, String name, Date orderDate, Integer currencyId, Integer partnerId, Integer partnerInvoiceId, Integer partnerShippingId, Integer pricelistId) {
        this.odooId = odooId;
        this.name = name;
        this.orderDate = orderDate;
        this.currencyId = currencyId;
        this.partnerId = partnerId;
        this.partnerInvoiceId = partnerInvoiceId;
        this.partnerShippingId = partnerShippingId;
        this.pricelistId = pricelistId;
    }

    public SaleOrder(HashMap odooResultObject) {
        this.name = (String) odooResultObject.get("name");
        this.odooId = (Integer) odooResultObject.get("id");
        this.orderDate = OdooUtils.stringToDate((String) odooResultObject.get("date_order"));
        this.currencyId = (Integer) ((Object[]) odooResultObject.get("currency_id"))[0];
        this.partnerId = (Integer) ((Object[]) odooResultObject.get("partner_id"))[0];
        this.partnerInvoiceId = (Integer) ((Object[]) odooResultObject.get("partner_invoice_id"))[0];
        this.partnerShippingId = (Integer) ((Object[]) odooResultObject.get("partner_shipping_id"))[0];
        this.pricelistId = (Integer) ((Object[]) odooResultObject.get("pricelist_id"))[0];

    }

    public int getOdooId() {
        return odooId;
    }

    public String getName() {
        return name;
    }

    public List<OrderLine> getOrderLine() {
        return orderLine;
    }

    @Override
    public String toString() {
        return "\nSaleOrder{" + "odooId=" + odooId + ", name=" + name + ", orderLine=" + orderLine + ", orderDate=" + orderDate + ", currencyId=" + currencyId + ", partnerId=" + partnerId + ", partnerInvoiceId=" + partnerInvoiceId + ", partnerShippingId=" + partnerShippingId + ", pricelistId=" + pricelistId + '}';
    }

    

}
