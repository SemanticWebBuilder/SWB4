/*
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
 */
package org.semanticwb;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import org.semanticwb.base.SWBAppObject;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBStartup.
 * 
 * @author serch
 */
public class SWBStartup {

/** The objs. */
private ArrayList objs = null;

/** The log. */
private static Logger log=SWBUtils.getLogger(SWBStartup.class);

    /**
     * Creates a new instance of WBStartup.
     * 
     * @param objs the objs
     */
    public SWBStartup(ArrayList objs)
    {
        this.objs = objs;
    }

    /**
     * Load.
     * 
     * @param name the name
     */
    public void load(String name)
    {
        Properties startup = new Properties();
        InputStream in = getClass().getResourceAsStream("/" + name);
        if (in == null) return;
        try
        {
            startup.load(in);
        } catch (Exception e)
        {
            log.error("startup.properties file not found",e);
        }

        Enumeration en = startup.keys();
        while (en.hasMoreElements())
        {
            String objname = (String) en.nextElement();
            log.event("Initializing " + objname + "...");
            String clsname = startup.getProperty(objname);
            try
            {
                Class cls = Class.forName(clsname);
                try
                {
                    Method getInstance = cls.getMethod("getInstance", (Class[])null);
                    SWBAppObject obj = (SWBAppObject) getInstance.invoke(null, (Object[])null);
                    if (obj != null)
                    {
                        objs.add(obj);
                    }

                }catch(Exception e)
                {
                    SWBAppObject obj = (SWBAppObject) cls.newInstance();
                    if (obj != null)
                    {
                        objs.add(obj);
                        obj.init();
                    }
                    else
                        log.error("Initialization failed " + objname);
                }


            } catch (Exception e)
            {
                log.error("Startup load initialization error "+ objname ,e);
            }

        }
    }
}
