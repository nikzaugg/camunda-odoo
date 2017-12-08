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
package ch.itsheinrich.teaching.camunda.REST;

import ch.itsheinrich.teaching.camunda.model.Customer;
import ch.itsheinrich.teaching.camunda.model.SaleOrder;
import ch.itsheinrich.teaching.camunda.odoo.AdapterFactory;
import ch.itsheinrich.teaching.camunda.odoo.OdooAdapter;
import ch.itsheinrich.teaching.camunda.odoo.OdooConnection;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Lauch Dieter
 */
@RestController
public class CustomersController {

    @RequestMapping(method = RequestMethod.GET, value = "/customers", produces = APPLICATION_JSON_VALUE)
    public List<Customer> getCustomerNames() {
        try {
            return AdapterFactory.getOdooAdapter().readFiveCustomers();
        }
        catch(Exception e) {
            return new ArrayList<>();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/quotations", produces = APPLICATION_JSON_VALUE)
    public List<SaleOrder> getQuotationsNames() {
        try {
            return AdapterFactory.getOdooAdapter().getFiveQuotations();
        }
        catch(Exception e) {
            return new ArrayList<>();
        }
    }
}
