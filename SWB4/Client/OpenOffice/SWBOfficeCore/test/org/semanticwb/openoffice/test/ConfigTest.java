/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
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
