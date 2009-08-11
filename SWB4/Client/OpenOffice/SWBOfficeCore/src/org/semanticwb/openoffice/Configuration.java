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
package org.semanticwb.openoffice;

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
public final class Configuration
{

    public static final String PROXY_PORT = "ProxyPort";
    public static final String PROXY_SERVER = "ProxyServer";
    
    private static final String COMMENT = "List of configurations of Semantic Webbuilder 4.0";
    public static final String CONFIGURATION_PROPERTY_NAME = "org.semanticwb.openoffice.configuration.Configuration";
    private HashMap<String, String> userConfigurations = new HashMap<String, String>();
    private String path;

    public Configuration()
    {        
        path = System.getProperty(CONFIGURATION_PROPERTY_NAME, "wb4config.xml");
        File fileconfig = new File(path);   
        String errorPath=fileconfig.getParentFile().getPath();
        System.setProperty(ErrorLog.CONFIGURATION,errorPath);
        String listxml=fileconfig.getParentFile().getPath()+File.separatorChar+"list.xml";
        System.setProperty(ConfigurationListURI.CONFIGURATION,listxml);
        load();
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
                ErrorLog.log(fnfe);
            }
            catch (IOException ioe)
            {
                ErrorLog.log(ioe);
            }
        }
    }
}
