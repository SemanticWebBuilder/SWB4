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
package org.semanticwb.triplestore.mongo.ext;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.Filter;
import com.hp.hpl.jena.util.iterator.Map1;
import com.hp.hpl.jena.util.iterator.Map1Iterator;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.triplestore.mongo.SWBTSMongoUtil;

/**
 *
 * @author jei
 */
public class SWBTSMongoIteratorExt implements ExtendedIterator<Triple>
{

    private static Logger log=SWBUtils.getLogger(SWBTSMongoIteratorExt.class);

    private SWBTSMongoGraphExt graph=null;
    private TripleMatch tm=null;

    private DBCursor cur;

    private Triple actual=null;
    private Triple next=null;

    private boolean closed=false;

    private static int counter=0;
    
    public SWBTSMongoIteratorExt(SWBTSMongoGraphExt graph, TripleMatch tm)
    {
        this(graph, tm, null, 0, 0, null);
    }

    public SWBTSMongoIteratorExt(SWBTSMongoGraphExt graph, TripleMatch tm, String stype, long limit, long offset, String sortby)
    {
        counter++;
        //System.out.println("SWBTSMongoIterator:"+counter+" tm:"+tm+" "+graph.getName());
        
        this.graph=graph;
        this.tm=tm;

        String subj= SWBTSMongoUtil.node2HashString(tm.getMatchSubject(),"lgs");
        String prop=SWBTSMongoUtil.node2HashString(tm.getMatchPredicate(),"lgp");
        String obj=SWBTSMongoUtil.node2HashString(tm.getMatchObject(),"lgo");
        
        //System.out.println("subj:"+subj+" prop:"+prop+" obj:"+obj);

        try
        {
            DB db = SWBTSMongoExt.getMongo().getDB(SWBPlatform.getEnv("swb/mongodbname","swb"));
            if(!db.isAuthenticated() && SWBPlatform.getEnv("swb/mongodbuser")!=null && SWBPlatform.getEnv("swb/mongodbpasswd")!=null)
            {
                db.authenticate(SWBPlatform.getEnv("swb/mongodbuser"), SWBPlatform.getEnv("swb/mongodbpasswd").toCharArray());
            }
            
            DBCollection coll = db.getCollection("swb_graph_ts"+graph.getId());
            
            BasicDBObject doc = new BasicDBObject();
            
            if(subj!=null)doc.put("subj", subj);
            if(prop!=null)doc.put("prop", prop);
            if(obj!=null)doc.put("obj", obj);
            if(stype!=null)doc.put("stype",stype);
            
            cur=coll.find(doc);
            if(limit>0)cur=cur.limit((int)limit);
            if(offset>0)cur=cur.skip((int)offset);
            
            if(sortby!=null)
            {
                BasicDBObject sort = new BasicDBObject();
                int sp=sortby.indexOf(" ");
                if(sp>-1)
                {
                    doc.put(sortby.substring(0,sp), Integer.parseInt(sortby.substring(sp+1)));
                }else
                {
                    doc.put(sortby, -1);
                }
                cur=cur.sort(sort);
                //sortby.replace("obj", "sort");
                //if(sortby.equals("obj"))sortby="sort";
            }              
            
            if(cur.hasNext())
            {
                DBObject dbobj=cur.next();
                
                String ext=(String)dbobj.get("ext");
                next = new Triple(SWBTSMongoUtil.string2Node((String)dbobj.get("subj"),ext), SWBTSMongoUtil.string2Node((String)dbobj.get("prop"),ext), SWBTSMongoUtil.string2Node((String)dbobj.get("obj"),ext));
            }else
            {
                close();
            }
        }catch(MongoException e)
        {
            log.error(e);
        }

        //Thread.currentThread().dumpStack();
    }

    @Override
    public Triple removeNext()
    {
        Triple tp=next();
        remove();
        return tp;
    }

    @Override
    public <X extends Triple> ExtendedIterator<Triple> andThen(Iterator<X> other)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ExtendedIterator<Triple> filterKeep(Filter<Triple> f)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ExtendedIterator<Triple> filterDrop(Filter<Triple> f)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <U> ExtendedIterator<U> mapWith(Map1<Triple, U> map1)
    {
        return new Map1Iterator<Triple, U>( map1, this ); 
    }

    @Override
    public List<Triple> toList()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<Triple> toSet()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close()
    {
        if(!closed)
        {
            closed=true;
            try
            {
                counter--;
                if(cur!=null)cur.close();
            } catch (Exception ex)
            {
                log.error(ex);
            }
        }
    }

    public boolean hasNext()
    {
        return next!=null;
    }

    public Triple next()
    {
        actual=next;
        next=null;
        try
        {
            if(cur.hasNext())
            {
                DBObject dbobj=cur.next();
                String ext=(String)dbobj.get("ext");
                next = new Triple(SWBTSMongoUtil.string2Node((String)dbobj.get("subj"),ext), SWBTSMongoUtil.string2Node((String)dbobj.get("prop"),ext), SWBTSMongoUtil.string2Node((String)dbobj.get("obj"),ext));
            }else
            {
                close();
            }

        }catch(MongoException e)
        {
            log.error(e);
        }
        return actual;
    }

    public void remove()
    {
        graph.performDelete(actual);
    }

    @Override
    protected void finalize() throws Throwable
    {
        if(!closed)
        {
            log.warn("Iterator is not closed, "+tm);
            close();
        }
    }

}