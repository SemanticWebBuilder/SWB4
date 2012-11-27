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

import com.hp.hpl.jena.graph.TransactionHandler;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.impl.GraphBase;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.protocol.Command;
import org.semanticwb.triplestore.SWBTSUtil;

/**
 *
 * @author serch
 */
class SWBRTSGraph extends GraphBase{

    private static Logger log = SWBUtils.getLogger(SWBRTSGraph.class);

    private String name;

    private PrefixMapping pmap;
    //private BigdataTransactionHandler trans;
    private SWBRTSTransactionHandler trans;

    public SWBRTSGraph(String name)
    {
        this.name=name;
        pmap=new SWBRTSPrefixMapping(this);
        trans=new SWBRTSTransactionHandler(this);
        //System.out.println("name:"+name+" "+getTransactionHandler().transactionsSupported());        
    }

    @Override
    protected ExtendedIterator<Triple> graphBaseFind(TripleMatch tm)
    {
//        if(tm.getMatchSubject()!=null && (tm.getMatchSubject().toString().equals(" ") ||
//            tm.getMatchSubject().toString().equals("generico")))
//        {
//            System.out.println("graphBaseFind:"+tm);
//            new Exception().printStackTrace();
//        }
        return new SWBRTSIterator(this, tm);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void performAdd(Triple t)
    {
        try
        {
            String subj=SWBTSUtil.node2String(t.getSubject());
            String prop=SWBTSUtil.node2String(t.getPredicate());
            String obj=SWBTSUtil.node2String(t.getObject());

            try {
                String params[]={Command.GRAPH_ADD,getName(),subj,prop,obj,""+(Thread.currentThread().getId()+SWBRTSTransactionHandler.base)};
                SWBRTSUtil util = new SWBRTSUtil(params);
                util.call();
            } catch (Exception e)
            {
                log.error(e);
            }
        } catch (Exception e2)
        {
            log.error(e2);
        }
    }

    @Override
    public void performDelete(Triple t)
    {
        String subj=SWBTSUtil.node2String(t.getSubject());
        String prop=SWBTSUtil.node2String(t.getPredicate());
        String obj=SWBTSUtil.node2String(t.getObject());

        try {
            String[] params = {Command.GRAPH_REMOVE,getName(),subj,prop,obj,""+(Thread.currentThread().getId()+SWBRTSTransactionHandler.base)};
            SWBRTSUtil util = new SWBRTSUtil(params);
            util.call();
        } catch (Exception e)
        {
            log.error(e);
        }
    }

    public String getName()
    {
        return name;
    }

    @Override
    public void close()
    {
        //System.out.println("Close");
        //new Exception().printStackTrace();
        //Thread.currentThread().dumpStack();
        //super.close();
    }

    @Override
    public PrefixMapping getPrefixMapping()
    {
        return pmap;
    }

    @Override
    public TransactionHandler getTransactionHandler() 
    {
        return trans;
    }
    
    
    
    

}
