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

import ch.itsheinrich.teaching.camunda.model.SaleOrder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter Heinrich
 */
public class OdooUtils {

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static Date stringToDate(String dateString) {
        try {
            return df.parse(dateString);
        } catch (ParseException ex) {
            Logger.getLogger(SaleOrder.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static String dateToString(Date date) {
        return df.format(date);
    }
}
