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

import com.hp.hpl.jena.graph.impl.TransactionHandlerBase;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.protocol.Command;

/**
 *
 * @author jei
 */
public class SWBRTSTransactionHandler extends TransactionHandlerBase
{
    public static Logger log=SWBUtils.getLogger(SWBRTSTransactionHandler.class);
    private SWBRTSGraph graph;
    
    public static long base=System.currentTimeMillis();

    public SWBRTSTransactionHandler(SWBRTSGraph graph)
    {
        this.graph=graph;
    }

    public boolean transactionsSupported()
    {
        return true;
    }

    public void begin()
    {
        //System.out.println("begin:"+Thread.currentThread().getId());
        try {
            String params[]={Command.TRANS_BEGIN, graph.getName(), ""+(Thread.currentThread().getId()+base)};
            SWBRTSUtil util = new SWBRTSUtil(params);
            List<String> l=util.call();
        } catch (Exception e)
        {
            log.error(e);
        }        
    }

    public void abort()
    {
        //System.out.println("abort:"+Thread.currentThread().getId());
        
        try {
            String params[]={Command.TRANS_ABORT, graph.getName(), ""+(Thread.currentThread().getId()+base)};
            SWBRTSUtil util = new SWBRTSUtil(params);
            List<String> l=util.call();
        } catch (Exception e)
        {
            log.error(e);
        }
    }

    public void commit()
    {
        //System.out.println("commit:"+Thread.currentThread().getId());        
        try {
            String params[]={Command.TRANS_COMMINT, graph.getName(), ""+(Thread.currentThread().getId()+base)};
            SWBRTSUtil util = new SWBRTSUtil(params);
            List<String> l=util.call();
        } catch (Exception e)
        {
            log.error(e);
        }
    }

}
