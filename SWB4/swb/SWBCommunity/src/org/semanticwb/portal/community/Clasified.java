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
package org.semanticwb.portal.community;

import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

public class Clasified extends org.semanticwb.portal.community.base.ClasifiedBase 
{
    private static Logger log=SWBUtils.getLogger(Clasified.class);

    private static Timer timer=null;

    static
    {
        log.debug("Starting Classifies Depuring...");
        timer=new Timer();
        TimerTask task=new TimerTask()
        {
            @Override
            public void run()
            {
                Iterator<MicrositeContainer> mit=MicrositeContainer.ClassMgr.listMicrositeContainers();
                while(mit.hasNext())
                {
                    MicrositeContainer site=mit.next();
                    //System.out.println("Sitio:"+site);
                    Iterator<Clasified> it=Clasified.ClassMgr.listClasifieds(site);
                    while(it.hasNext())
                    {
                        Clasified obj=it.next();
                        //System.out.println(obj+" exp:"+obj.getExpirationDate());
                        if(obj.getExpirationDate().getTime()<System.currentTimeMillis())
                        {
                            obj.remove();
                        }
                    }
                }
            }
        };
        timer.schedule(task, 1000L*60*60, 1000L*60*60*24);
        //timer.schedule(task, 1000L*60, 1000L*60);
    }


    public Clasified(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public Date getExpirationDate()
    {
        return new Date(getCreated().getTime()+1000L*60*60*24*31);
    }

}
