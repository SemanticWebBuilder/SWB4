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
 * DBBean.java
 *
 * Created on 17 de octubre de 2001, 15:03
 */
package com.infotec.appfw.bean;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;

/** bean: lee variables declaradas en el archivo de propiedades global.properties en el directorio classes
 */
public class GlobalProperties implements HttpSessionBindingListener
{
    private Properties globalProp = null;

    public GlobalProperties()
    {
        InputStream in = getClass().getResourceAsStream("/global.properties");
        globalProp = new Properties();
        try
        {
            globalProp.load(in);
        } catch (Exception e)
        {
            System.err.println("Can't read the properties file. " +
                               "Make sure global.properties is in the CLASSPATH");
            return;
        }
    }

    public void refresh()
    {
        InputStream in = getClass().getResourceAsStream("/global.properties");
        globalProp = new Properties();
        try
        {
            globalProp.load(in);
        } catch (Exception e)
        {
            System.out.println("Can't read the properties file. " +
                               "Make sure global.properties is in the CLASSPATH");
            return;
        }
        //globalProp.notify();
    }

    public String getProperty(String prop)
    {
        return globalProp.getProperty(prop);
    }

    public void valueUnbound(HttpSessionBindingEvent e)
    {
        System.out.println();
    }

    public void valueBound(HttpSessionBindingEvent e)
    {
    }
}

