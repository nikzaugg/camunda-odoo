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

import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.HashMap;
import static org.apache.coyote.http11.Constants.a;

/**
 *
 * @author Peter Heinrich
 */
public class OrderLine {

    private  int odooId;
    private  double leadTime;
    private  String name;
    private  int productId;
    private  int unitOfMeasureId;
    private  double unitPrice;
    private  double quantity;

    public OrderLine() {
    
    }

    
    public OrderLine(double leadTime, String name, int productId, int unitOfMeasureId, double unitPrice, double quantity) {
        this.leadTime = leadTime;
        this.name = name;
        this.productId = productId;
        this.unitOfMeasureId = unitOfMeasureId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public OrderLine(HashMap odooResultObject) {
        this.odooId = (Integer) odooResultObject.get("id");
        this.leadTime = (Double) odooResultObject.get("customer_lead");
        this.name = (String) odooResultObject.get("name");        
        this.productId = (Integer) ((Object[]) odooResultObject.get("product_id"))[0];
        this.unitOfMeasureId = (Integer) ((Object[]) odooResultObject.get("product_uom"))[0];
        this.unitPrice = (Double) odooResultObject.get("price_unit");
        this.quantity = (Double) odooResultObject.get("product_uom_qty");
    }

    public int getOdooId() {
        return odooId;
    }

    public void setOdooId(int odooId) {
        this.odooId = odooId;
    }

    public double getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(double leadTime) {
        this.leadTime = leadTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUnitOfMeasureId() {
        return unitOfMeasureId;
    }

    public void setUnitOfMeasureId(int unitOfMeasureId) {
        this.unitOfMeasureId = unitOfMeasureId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    

    @Override
    public String toString() {
        return "\n\tOrderLine{" + "odooId=" + odooId + ", leadTime=" + leadTime + ", name=" + name + ", productId=" + productId + ", unitOfMeasureId=" + unitOfMeasureId + ", unitPrice=" + unitPrice + ", quantity=" + quantity + '}';
    }

    public HashMap toHashMap() {
        HashMap<String,Object> odooResultObject = new HashMap<>();        
       // odooResultObject.put("id", this.odooId);
        odooResultObject.put("customer_lead",this.leadTime);
        odooResultObject.put("name",this.name);        
        odooResultObject.put("price_unit", this.unitPrice);
        odooResultObject.put("product_uom_qty", this.quantity);
       // odooResultObject.put("product_id", asList("4",Integer.toString(this.productId)));
        odooResultObject.put("product_id", this.productId);
       // odooResultObject.put("product_uom", asList("4",Integer.toString(this.unitOfMeasureId)));                        
        odooResultObject.put("product_uom", this.unitOfMeasureId);                        
        return odooResultObject;
        
    }

}
