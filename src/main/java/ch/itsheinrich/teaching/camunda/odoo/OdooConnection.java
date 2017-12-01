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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 *
 * @author Peter Heinrich
 */
public class OdooConnection {

    public static final Logger LOGGER = Logger.getLogger(OdooConnection.class.getName());

    private final String db;
    private final String username;
    private final String password;
    private int userId;

    private final URL commonUrl;
    private final URL objectUrl;
    private final URL reportUrl;

    public OdooConnection(URL commonUrl, URL objectUrl, URL reportUrl, String db, String username, String password) {
        this.commonUrl = commonUrl;
        this.objectUrl = objectUrl;
        this.reportUrl = reportUrl;
        this.db = db;
        this.username = username;
        this.password = password;

    }

    public OdooConnection(InputStream configProperty) throws IOException {
        Properties prop = new Properties();
        prop.load(configProperty);
        this.commonUrl = new URL(prop.getProperty("commonUrl"));
        this.objectUrl = new URL(prop.getProperty("objectUrl"));
        this.reportUrl = new URL(prop.getProperty("reportUrl"));
        this.db = prop.getProperty("db");
        this.username = prop.getProperty("username");
        this.password = prop.getProperty("password");
    }

    public boolean login() throws XmlRpcException {
        XmlRpcClient client = new XmlRpcClient();
        XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
        common_config.setServerURL(commonUrl);

        Object ret = client.execute(
                common_config, "authenticate", asList(
                        this.db,
                        this.username,
                        this.password,
                        emptyMap()
                )
        );

        if (!(ret instanceof Integer)) {
            return false;
        }

        LOGGER.info("Login successful with the following parameters (password omitted): \n" + this);

        this.userId = (int) ret;
        return true;
    }

    public static OdooConnection getConfigAndLoginToPublicOdooServer() throws XmlRpcException {

        final XmlRpcClient client = new XmlRpcClient();

        final XmlRpcClientConfigImpl start_config = new XmlRpcClientConfigImpl();

        // The public odoo server is kind enough to provide us with all 
        // login credentials required - host, database, username, password
        try {
            start_config.setServerURL(new URL("https://demo.odoo.com/start"));
        } catch (MalformedURLException e) {
            // It can only throw a MalformedURLException here which we can 
            // safely ignore, as the URL is statically provided and of valid
            // format
        }
        LOGGER.info("Connecting " + start_config.getServerURL() + " for retrieving conmfiguration");
        final Map<String, String> info = (Map<String, String>) client.execute(
                start_config, "start", emptyList());

        String host = info.get("host");

        URL commonUrl = null;
        try {
            commonUrl = new URL(String.format("%s/xmlrpc/2/common", host));
        } catch (MalformedURLException ex) {
            // It can only throw a MalformedURLException here which we can 
            // safely ignore, as the URL is statically provided and of valid
            // format
        }

        URL objectUrl = null;
        try {
            objectUrl = new URL(String.format("%s/xmlrpc/2/object", host));
        } catch (MalformedURLException ex) {
            // It can only throw a MalformedURLException here which we can 
            // safely ignore, as the URL is statically provided and of valid
            // format
        }

        URL reportUrl = null;
        try {
            reportUrl = new URL(String.format("%s/xmlrpc/2/report", host));
        } catch (MalformedURLException ex) {
            // It can only throw a MalformedURLException here which we can 
            // safely ignore, as the URL is statically provided and of valid
            // format
        }

        OdooConnection conf = new OdooConnection(
                commonUrl,
                objectUrl,
                reportUrl,
                info.get("database"),
                info.get("user"),
                info.get("password")
        );
        return conf;
    }

    public URL getCommonUrl() {
        return commonUrl;
    }

    public URL getObjectUrl() {
        return objectUrl;
    }

    public URL getReportUrl() {
        return reportUrl;
    }

    public String getDb() {
        return db;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "OdooConnectorConfig{\n"
                + "\tdb=" + db + "\n"
                + "\tusername=" + username + "\n"
                + "\tuserId=" + userId + "\n"
                + "\tcommonUrl=" + commonUrl + "\n"
                + "\tobjectUrl=" + objectUrl + "\n"
                + '}';
    }

}
