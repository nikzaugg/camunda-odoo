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

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Peter Heinrich
 */
public final class Customer implements Serializable {

    private final int odooId;
    private final String name;
    private String street;
    private String city;

    public Customer(int odooId, String name) {
        this.odooId = odooId;
        this.name = name;
    }

    public Customer(HashMap odooResultObject) {
        this.name = (String) odooResultObject.get("name");
        this.odooId = (Integer) odooResultObject.get("id");
    }

    public int getOdooId() {
        return odooId;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }    

    @Override
    public String toString() {
        return "Customer{" + "odooId=" + odooId + ", name=" + name + ", street=" + street + ", city=" + city + '}';
    }
        

}
