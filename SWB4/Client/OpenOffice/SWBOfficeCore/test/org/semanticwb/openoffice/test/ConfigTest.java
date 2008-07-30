/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.openoffice.Configuration;
import org.semanticwb.openoffice.ConfigurationListURI;
import org.semanticwb.openoffice.ErrorLog;
import static org.junit.Assert.*;

/**
 *
 * @author victor.lorenzana
 */
public class ConfigTest
{

    File fileConfigList;
    File fileConfig;

    public ConfigTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        System.setProperty(ConfigurationListURI.CONFIGURATION, "c:\\temp\\list.xml");
        System.setProperty(Configuration.CONFIGURATION_PROPERTY_NAME, "c:\\temp\\config.xml");
        System.setProperty(ErrorLog.CONFIGURATION, "c:\\temp");
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
        fileConfigList = new File(System.getProperty(ConfigurationListURI.CONFIGURATION));
        fileConfig = new File(System.getProperty(Configuration.CONFIGURATION_PROPERTY_NAME));
    }

    /**
     * 
     */
    @After
    public void tearDown()
    {
    }

    /**
     * Test the configurationListURIContructor.
     */
    @Test
    public final void configurationListURIContructorTest()
    {
        ConfigurationListURI config = new ConfigurationListURI();
    }

    /**
     * Test the configurationContructor.
     */
    @Test
    public final void configurationContructorTest()
    {
        Configuration config = new Configuration();
    }

    /**
     * Test the configurationContructor.
     */
    @Test
    public final void addTest()
    {
        Configuration config = new Configuration();
        String key = "Language";
        String expected = "es";
        config.add(key, expected);
        String actual = config.get(key);
        Assert.assertEquals(expected, actual);
    }

    /**
     * Test the configurationContructor.
     */
    @Test
    public final void getKeysTest()
    {
        fileConfig.delete();
        Configuration config = new Configuration();
        config.add("Language", "es");
        config.add(ConfigurationListURI.CONFIGURATION, "1");
        config.add(Configuration.CONFIGURATION_PROPERTY_NAME, "2");
        Assert.assertEquals(config.getKeys().length, 3);
    }

    @Test
    public final void addUserConfigurationURITest()
    {
        try
        {
            ConfigurationListURI config = new ConfigurationListURI();
            URI uri = new URI("http://www.infotec.com.mx/WBADMIN");
            String expected = "login1";
            config.addUserConfiguration(uri, expected);
            String actual = config.getLogin(uri);
            Assert.assertEquals(actual, expected);
        }
        catch (URISyntaxException use)
        {
            
        }
    }

    @Test
    public final void getLoginDeletedConfigurationURITest()
    {
        fileConfigList.delete();
        try
        {
            ConfigurationListURI config = new ConfigurationListURI();
            URI uri = new URI("http://www.infotec.com.mx/WBADMIN");
            String actual = config.getLogin(uri);
            Assert.assertEquals(actual, "");
        }
        catch (URISyntaxException use)
        {
            
        }
    }

    @Test
    public final void getURIListTest()
    {
        try
        {
            fileConfigList.delete();
            ConfigurationListURI config = new ConfigurationListURI();            
            config.addUserConfiguration(new URI("http://www.infotec.com.mx/WBADMIN1"), "1");
            config.addUserConfiguration(new URI("http://www.infotec.com.mx/WBADMIN2"), "2");
            config.addUserConfiguration(new URI("http://www.infotec.com.mx/WBADMIN3"), "3");
            config.addUserConfiguration(new URI("http://www.infotec.com.mx/WBADMIN4"), "4");
            URI[] listUri = config.getAddresses();
            Assert.assertEquals(listUri.length, 4);
        }
        catch (URISyntaxException use)
        {
            Assert.fail(use.getMessage());
        }
    }

    @Test
    public final void logMessage()
    {
        ErrorLog.log("hola");
        ErrorLog.log("hola 2");
        ErrorLog.log("hola 3");
    }
}
