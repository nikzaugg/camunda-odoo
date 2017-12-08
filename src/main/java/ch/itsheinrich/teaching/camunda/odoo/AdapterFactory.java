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

import ch.itsheinrich.teaching.camunda.REST.CustomersController;

import java.io.InputStream;

/**
 *
 * @author Peter Heinrich
 */
public class AdapterFactory {

    private static OdooAdapter adapter = null;

    public static OdooAdapter getOdooAdapter() {
        if (adapter == null) {
            try {
                // OdooConnection odooConnection = OdooConnection.getConfigAndLoginToPublicOdooServer();
                InputStream odooConfig = CustomersController.class.getClassLoader().getResourceAsStream("OdooConfiguration.properties");
                OdooConnection odooConnection = new OdooConnection(odooConfig);
                odooConnection.login();
                adapter = new OdooAdapter(odooConnection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adapter;
    }
}
