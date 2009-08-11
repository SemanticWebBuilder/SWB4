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
        new Configuration();
        path = System.getProperty(CONFIGURATION, "list.xml");
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
    public void removeAddress(URI uri)
    {
        if (this.userConfigurations.containsKey(uri))
        {
            this.userConfigurations.remove(uri);
            this.save();
        }
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
                        ex.printStackTrace();
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
