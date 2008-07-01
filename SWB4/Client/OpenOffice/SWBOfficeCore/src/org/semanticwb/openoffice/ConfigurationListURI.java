/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author victor.lorenzana
 */
public final class ConfigurationListURI
{

    public static final String COMMENT = "List of URI";
    public static final String CONFIGURATION = "org.semanticwb.openoffice.configuration.ConfigurationListURL";
    /**
     * List of UserConfigurations
     */
    private HashMap<URI, UserConguration> userConfigurations = new HashMap<URI, UserConguration>();
    private String path;    
    public ConfigurationListURI()
    {
        path = System.getProperty(CONFIGURATION, "urilist.xml");
        load();
    }

    /**
     * Returns all the URI.
     * @return A Iterable list of URI
     */
    public URI[] getAddresses()
    {
        return this.userConfigurations.keySet().toArray(new URI[this.userConfigurations.size()]);
    }

    /**
     * 
     * @param uri
     * @return
     */
    public String getLogin(URI uri)
    {
        String login = "";
        if (this.userConfigurations.containsKey(uri))
        {
            login = this.userConfigurations.get(uri).getLogin();
        }
        return login;
    }

    /**
     * 
     * @param uri
     * @param login
     */
    public void addUserConfiguration(URI uri, String login)
    {
        this.userConfigurations.put(uri, new UserConguration(uri, login));
        this.save();
    }

    /**
     * 
     */
    private void save()
    {
        File fileconfig = new File(path);
        if (this.userConfigurations.size() > 0)
        {
            try
            {
                FileOutputStream os = new FileOutputStream(fileconfig);
                Properties properties = new Properties();
                for (URI key : this.userConfigurations.keySet())
                {
                    String login = this.userConfigurations.get(key).getLogin();
                    properties.setProperty(key.toString(), login);
                }
                properties.storeToXML(os, COMMENT, "UTF-8");
                os.close();
            }
            catch (FileNotFoundException fnfe)
            {
                ErrorLog.log(fnfe);
            }
            catch (IOException ioe)
            {
                ErrorLog.log(ioe);
            }
        }
    }

    private void load()
    {

        File fileconfig = new File(path);
        if (fileconfig.exists())
        {
            try
            {
                FileInputStream in = new FileInputStream(fileconfig);
                Properties properties = new Properties();
                properties.loadFromXML(in);
                Set<String> keys = properties.stringPropertyNames();
                for (String key : keys)
                {
                    try
                    {
                        String login = properties.getProperty(key);
                        URI uri = new URI(key);
                        this.userConfigurations.put(uri, new UserConguration(uri, login));
                    }
                    catch (URISyntaxException ex)
                    {
                        ErrorLog.log(ex);
                    }
                }
                in.close();
            }
            catch (FileNotFoundException fnfe)
            {
                ErrorLog.log(fnfe);
            }
            catch (IOException ioe)
            {
                ErrorLog.log(ioe);
            }
        }
    }
}
