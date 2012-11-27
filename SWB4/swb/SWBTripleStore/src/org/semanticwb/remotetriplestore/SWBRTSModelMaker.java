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
package org.semanticwb.remotetriplestore;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.protocol.Command;

/**
 *
 * @author serch
 */
public class SWBRTSModelMaker {

    private static Logger log=SWBUtils.getLogger(SWBRTSModelMaker.class);
    HashMap<String,Model> models=new HashMap();
    

    public SWBRTSModelMaker()
    {
        try {
            /*
             * swb/tripleremoteserver=192.168.6.139
             * swb/tripleremoteport=6666
             */
            int port = Integer.parseInt(SWBPlatform.getEnv("swb/tripleremoteport", "6666"));
            String address = SWBPlatform.getEnv("swb/tripleremoteserver",null);
            SWBRTSUtil.initPool(InetAddress.getByName(address), port);
        } catch (Exception e) {
            log.error(e);
        }
    }

    public synchronized Iterator<String> listModelNames()
    {
        Iterator<String> ret = null;
        try {
            
            String params[]={Command.LIST_MODEL_NAMES};
            SWBRTSUtil util = new SWBRTSUtil(params);
            ret=util.call().iterator();
        } catch (Exception e)
        {
            log.error(e);
        }
        return ret;
    }

    public Model getModel(String name)
    {
        Model ret=models.get(name);
        if(ret==null)
        {
            try {
                String params[]={Command.GET_MODEL,name};
                SWBRTSUtil util = new SWBRTSUtil(params);
                List<String> l=util.call();

                if (l.size()>0)
                {
                    ret = new ModelCom(new SWBRTSGraph(name));
                }

            } catch (Exception e)
            {
                log.error(e);
            }
            models.put(name, ret);
        }
        return ret;
    }

    public Model createModel(String name)
    {
        Model model=getModel(name);
        if(model==null)
        {
            try {

                String params[]={Command.CREATE_MODEL,name};
                SWBRTSUtil util = new SWBRTSUtil(params);
                List<String> l=util.call();
                if (l.size()>0)
                {
                    model = new ModelCom(new SWBRTSGraph(name));
                }
            } catch (Exception e)
            {
                log.error(e);
            }
        }
        models.put(name, model);
        return model;
    }

    public synchronized void removeModel(String name)
    {
        try {
            String params[]={Command.REMOVE_MODEL,name};
            SWBRTSUtil util = new SWBRTSUtil(params);
            List<String> l=util.call();
        } catch (Exception e)
        {
            log.error(e);
        }
        models.remove(name);
    }

   
}
