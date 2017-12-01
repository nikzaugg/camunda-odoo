/*
 * Copyright (C) 2017 Pivotal Software, Inc.
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
package ch.itsheinrich.teaching.camunda.service;

import ch.itsheinrich.teaching.camunda.model.Customer;
import ch.itsheinrich.teaching.camunda.model.Product;
import ch.itsheinrich.teaching.camunda.odoo.AdapterFactory;
import java.util.List;
import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.stereotype.Service;

/**
 *
 * @author Peter Heinrich
 */
@Service("LoadProductList")
public class LoadProductListDelegate implements JavaDelegate {

    private static final Logger logger = Logger.getLogger(LoadCustomerAddressesDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer odooId = (Integer) execution.getVariable("customer_id");

        // Load customer dataset from Odoo based on the ID selected in client_id
        try {
            List<Product> productList = AdapterFactory.getOdooAdapter().readFiveProducts();

            ObjectValue productListJSON = Variables.objectValue(productList)
                    .serializationDataFormat(Variables.SerializationDataFormats.JSON)
                    .create();

            execution.setVariable("productList", productListJSON);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
