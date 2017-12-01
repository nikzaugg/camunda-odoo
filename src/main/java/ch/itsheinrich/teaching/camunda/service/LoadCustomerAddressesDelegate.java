/*
 * Copyright (C) 2017 Peter Heinrich <heip@zhaw.ch>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.itsheinrich.teaching.camunda.service;

import ch.itsheinrich.teaching.camunda.model.Customer;
import ch.itsheinrich.teaching.camunda.odoo.AdapterFactory;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.stereotype.Service;

@Service("LoadCustomerAddresses")
public class LoadCustomerAddressesDelegate implements JavaDelegate {

    private static final Logger logger = Logger.getLogger(LoadCustomerAddressesDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer odooId = (Integer) execution.getVariable("customer_id");

        // Load customer dataset from Odoo based on the ID selected in client_id
        try {
            Customer c = AdapterFactory.getOdooAdapter().loadCustomers(odooId);

           
            ObjectValue customerJSON = Variables.objectValue(c)
                    .serializationDataFormat(Variables.SerializationDataFormats.JSON)
                    .create();

            execution.setVariable("customer", customerJSON);
                       
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
