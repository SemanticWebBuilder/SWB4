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

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.impl.GraphBase;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBRuntimeException;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.RGraph;
import org.semanticwb.triplestore.ext.GraphExt;
import org.semanticwb.triplestore.mongo.SWBTSMongoPrefixMapping;
import org.semanticwb.triplestore.mongo.SWBTSMongoUtil;

/**
 *
 * @author jei
 */
public class SWBTSMongoGraphExt extends GraphBase implements RGraph, GraphExt
{
    private static Logger log = SWBUtils.getLogger(SWBTSMongoGraphExt.class);

    private String name;
    private int id;

    private PrefixMapping pmap;
    //private BigdataTransactionHandler trans;


    public SWBTSMongoGraphExt(int id, String name)
    {
        this.id=id;
        this.name=name;
        pmap=new SWBTSMongoPrefixMapping(id);
    }

    @Override
    protected ExtendedIterator<Triple> graphBaseFind(TripleMatch tm)
    {
        return new SWBTSMongoIteratorExt(this, tm);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void performAdd(Triple t)
    {
        performAdd(t,null);
    }    

    @Override
    public void performAdd(Triple t, Long id)
    {
        try
        {
            String subj= SWBTSMongoUtil.node2String(t.getSubject());
            String hsubj=SWBTSMongoUtil.getHashText(subj);
            String prop=SWBTSMongoUtil.node2String(t.getPredicate());
            String hprop=SWBTSMongoUtil.getHashText(prop);
            String obj=SWBTSMongoUtil.node2String(t.getObject());
            String hobj=SWBTSMongoUtil.getHashText(obj);
            
            //if(subj==null || prop==null || obj==null)return;

            String sext="";
            if(hsubj!=null)
            {
                sext+="|subj|"+subj.length()+"|"+subj;
                subj="lgs|"+hsubj;
            }
            if(hprop!=null)
            {
                sext+="|prop|"+prop.length()+"|"+prop;
                prop="lgp|"+hprop;
            }
            if(hobj!=null)
            {
                sext+="|obj|"+obj.length()+"|"+obj;
                obj="lgo|"+hobj;
            }
            
            //System.out.println("performAdd:"+subj+" "+prop+" "+obj);
            //new Exception().printStackTrace();
            
            String ord=SWBTSMongoUtil.node2SortString(t.getObject());
            String stype=SWBTSMongoUtil.getSTypeFromSUBJ(subj);            
            
            DB db = SWBTSMongoExt.getMongo().getDB(SWBPlatform.getEnv("swb/mongodbname","swb"));
            
            if(!db.isAuthenticated() && SWBPlatform.getEnv("swb/mongodbuser")!=null && SWBPlatform.getEnv("swb/mongodbpasswd")!=null)
            {
                db.authenticate(SWBPlatform.getEnv("swb/mongodbuser"), SWBPlatform.getEnv("swb/mongodbpasswd").toCharArray());
            }
            
            DBCollection coll = db.getCollection("swb_graph_ts"+getId());
            
            BasicDBObject doc = new BasicDBObject();
            doc.put("subj", subj);
            doc.put("prop", prop);
            doc.put("obj", obj);
            
            doc.put("ord", ord);
            doc.put("stype", stype);      
            doc.put("timems", System.currentTimeMillis());
            
            if(sext.length()>0)
            {
                doc.put("ext", sext);
            }
            coll.insert(doc);

        } catch (Exception e2)
        {
            log.error(e2);
        }
    }

    @Override
    public void performDelete(Triple t)
    {
        performDelete(t,null);
    }    

    @Override
    public void performDelete(Triple t, Long id)
    {
        try
        {
            String subj=SWBTSMongoUtil.node2HashString(t.getMatchSubject(),"lgs");
            String prop=SWBTSMongoUtil.node2HashString(t.getMatchPredicate(),"lgp");
            String obj=SWBTSMongoUtil.node2HashString(t.getMatchObject(),"lgo");

            //System.out.println("performDelete:"+subj+" "+prop+" "+obj);
            
            DB db = SWBTSMongoExt.getMongo().getDB(SWBPlatform.getEnv("swb/mongodbname","swb"));
            if(!db.isAuthenticated() && SWBPlatform.getEnv("swb/mongodbuser")!=null && SWBPlatform.getEnv("swb/mongodbpasswd")!=null)
            {
                db.authenticate(SWBPlatform.getEnv("swb/mongodbuser"), SWBPlatform.getEnv("swb/mongodbpasswd").toCharArray());
            }            
            
            DBCollection coll = db.getCollection("swb_graph_ts"+getId());
            
            BasicDBObject doc = new BasicDBObject();
            
            if(subj!=null)doc.put("subj", subj);
            if(prop!=null)doc.put("prop", prop);
            if(obj!=null)doc.put("obj", obj);
            
            coll.findAndRemove(doc);
        } catch (Exception e2)
        {
            log.error(e2);
        }
    }

    public String getName()
    {
        return name;
    }

    public int getId()
    {
        return id;
    }

    @Override
    public void close()
    {
        //Thread.currentThread().dumpStack();
        super.close();
    }

    @Override
    public PrefixMapping getPrefixMapping()
    {
        return pmap;
    }

    public long count(TripleMatch tm, String stype)
    {
        //System.out.println("SWBTSMongoIterator:"+counter+" tm:"+tm+" "+graph.getName());
        long count=0;
        
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
            
            DBCollection coll = db.getCollection("swb_graph_ts"+getId());
            
            BasicDBObject doc = new BasicDBObject();
            
            if(subj!=null)doc.put("subj", subj);
            if(prop!=null)doc.put("prop", prop);
            if(obj!=null)doc.put("obj", obj);
            if(stype!=null)doc.put("stype", stype);
            
            count=coll.count(doc);
            
        }catch(MongoException e)
        {
            log.error(e);
        }        

        return count;
    }

    public ExtendedIterator<Triple> find(TripleMatch tm, String stype, Long limit, Long offset, String sortby)
    {
        return new SWBTSMongoIteratorExt(this, tm, stype, limit, offset, sortby);
    }

    @Override
    public String encodeSubject(Node n)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String encodeProperty(Node n)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String encodeObject(Node n)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node decodeSubject(String sub, String ext)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node decodeProperty(String sub, String ext)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node decodeObject(String sub, String ext)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
