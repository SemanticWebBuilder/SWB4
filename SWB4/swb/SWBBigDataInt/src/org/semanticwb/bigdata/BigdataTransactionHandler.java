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
package org.semanticwb.bigdata;

import com.bigdata.rdf.sail.BigdataSail;
import com.hp.hpl.jena.graph.impl.TransactionHandlerBase;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import org.openrdf.sail.SailConnection;
import org.openrdf.sail.SailException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.RTransactionHandler;

/**
 *
 * @author jei
 */
public class BigdataTransactionHandler extends TransactionHandlerBase implements RTransactionHandler
{
    public static Logger log=SWBUtils.getLogger(BigdataTransactionHandler.class);
    private BigdataSail sail;
    private ConcurrentHashMap<Long,BigdataSail.BigdataSailConnection> conmap;

    public BigdataTransactionHandler(BigdataSail sail)
    {
        conmap=new ConcurrentHashMap();
        this.sail=sail;
    }


    public boolean transactionsSupported()
    {
        return true;
    }
    
    public void begin()
    {
        begin(null);
    }

    public void begin(Long id)
    {
        //System.out.println("begin:"+id);
        BigdataSail.BigdataSailConnection con=null;
        if(id==null)
        {
            id=Thread.currentThread().getId();
        }
        con=conmap.get(id);
        if(con!=null)
        {
            try
            {
                con.close();
            } catch (SailException ex)
            {
                log.error("Error closing connection", ex);
            }
            log.event("BigData Connection was not closed...");
        }
        try
        {
            con = sail.getConnection();
            conmap.put(id, con);
        } catch (Exception ex)
        {
            throw new RuntimeException("Error initializing transaction", ex);
        }
    }
    
    public void abort()
    {
        abort(null);
    }

    public void abort(Long id)
    {
        //System.out.println("abort:"+id);
        SailConnection con=null;
        if(id==null)
        {
            id=Thread.currentThread().getId();
        }
        con=conmap.get(id);        
        
        if(con!=null)
        {
            try
            {
                con.rollback();
            } catch (SailException ex)
            {
                throw new RuntimeException("Error rollingback transaction", ex);
            }finally
            {
                conmap.remove(id);
                try
                {
                    con.close();
                } catch (SailException ex)
                {
                    throw new RuntimeException("Error closing transaction", ex);
                }
            }
        }
    }
    
    public void commit()
    {
        commit(null);
    }

    public void commit(Long id)
    {
        //System.out.println("commit:"+id);
        SailConnection con=null;
        if(id==null)
        {
            id=Thread.currentThread().getId();
        }
        con=conmap.get(id);
        {
            try
            {
                con.commit();
            } catch (Exception ex)
            {
                throw new RuntimeException("Error commiting transaction", ex);
            }finally
            {
                conmap.remove(id);
                try
                {
                    con.close();
                } catch (Exception ex)
                {
                    throw new RuntimeException("Error closing transaction", ex);
                }
            }
        }
    }

    /**
     * Return the SailConnection associated to the current transaction and thread
     * @return SailConnection
     */
    public SailConnection getConnection()
    {
        return getConnection(Thread.currentThread().getId());
    }
    
    /**
     * Return the SailConnection associated to the current transaction and thread
     * @return SailConnection
     */
    public SailConnection getConnection(Long id)
    {
        if(id==null)
        {
            id=Thread.currentThread().getId();
        }
        //System.out.println("getConnection("+id+")");
        return conmap.get(id);
    }

}
