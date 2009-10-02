/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package desktopgeneratorcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author victor.lorenzana
 */
public class Configuration
{
    private HashMap<String, String> userConfigurations = new HashMap<String, String>();
    
    private String path;

    public Configuration(File location)
    {
        path=location.getAbsolutePath();
        load();
    }
    public boolean exists(String key)
    {
        return userConfigurations.keySet().contains(key);
    }
    public void add(String key, String value)
    {
        this.userConfigurations.put(key, value);
        this.save();
    }

    /**
     * Returns all the URI.
     * @return A Iterable list of URI
     */
    public String[] getKeys()
    {
        return this.userConfigurations.keySet().toArray(new String[this.userConfigurations.size()]);
    }

    public String get(String key)
    {
        return this.userConfigurations.get(key);
    }

    private void save()
    {
        File fileconfig = new File(path);
        if (this.userConfigurations.size() > 0)
        {
            try
            {
                FileOutputStream os = new FileOutputStream(fileconfig);
                Properties properties = new Properties();
                for (String key : this.userConfigurations.keySet())
                {
                    String value = this.userConfigurations.get(key);
                    properties.setProperty(key, value);
                }
                properties.storeToXML(os, "", "UTF-8");
                os.close();
            }
            catch (FileNotFoundException fnfe)
            {
                //ErrorLog.log(fnfe);
            }
            catch (IOException ioe)
            {
                //ErrorLog.log(ioe);
            }
        }
    }
    public String getPath()
    {
        return path;
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
                    String value = properties.getProperty(key);
                    this.userConfigurations.put(key.toString(), value);
                }
                in.close();
            }
            catch (FileNotFoundException fnfe)
            {
                //ErrorLog.log(fnfe);
            }
            catch (IOException ioe)
            {
                //ErrorLog.log(ioe);
            }
        }
    }
}

