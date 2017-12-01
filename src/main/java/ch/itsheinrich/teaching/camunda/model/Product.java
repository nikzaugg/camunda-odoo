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
    private double price;

    public Product(int odooId, String name, double price) {
        this.odooId = odooId;
        this.name = name;
        this.price = price;
    }

    public Product(HashMap odooResultObject) {
        this.name = (String) odooResultObject.get("name");
        this.odooId = (Integer) odooResultObject.get("id");
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

    public void setPrice(double price) {
        this.price = price;
    }
   

    @Override
    public String toString() {
        return "Product{" + "odooId=" + odooId + ", name=" + name + ", price=" + price + '}';
    }

}
