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

import java.util.HashMap;

/**
 *
 * @author Peter Heinrich
 */
public class Product {
    private final int odooId;
    private final String name;
    private String default_code;
    private double price;
    private double list_price;
    private double sap_quantity;

    public Product(int odooId, String name, String default_code, double price) {
        this.odooId = odooId;
        this.name = name;
        this.default_code = default_code;
        this.price = price;
    }

    public Product(HashMap odooResultObject) {
        this.name = (String) odooResultObject.get("name");
        this.odooId = (Integer) odooResultObject.get("id");
        this.default_code = (String) odooResultObject.get("default_code");
        this.sap_quantity = (double) odooResultObject.get("sap_quantity");
        this.list_price = (double) odooResultObject.get("list_price");
    }

    public int getOdooId() {
        return odooId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    public String getDefault_code() {return this.default_code;}
    public double getList_price() {return this.list_price;}
    public double getSap_quantity() {return this.sap_quantity;}

    public void setPrice(double price) {
        this.price = price;
    }
    /*  public List toHashMap() {
        List article = asList("product.product",new HashMap() {
            {
                put("default_code", this.default_code);
                put("name", "Kap test api");
                /*put("categ_id", new HashMap() {
                    {put("name", asList(
                            "categ_test_api"
                    ));}
                    //put("name", "categ_test_api")
                });
                put("type", "product");
                put("list_price", 250);
                put("sap_quantity", 10);

            }
        });
        return article;

    }
*/
    @Override
    public String toString() {
        return "Product{" + "odooId=" + odooId + ", name=" + name + ", price=" + price + '}';
    }

}
