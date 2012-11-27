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
package org.semanticwb.triplestore.mongo;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/**
 *
 * @author jei
 */
public class SWBTSMongoModelMaker
{
    private static Logger log=SWBUtils.getLogger(SWBTSMongoModelMaker.class);
    
    private HashMap<String,Integer> map;

    public SWBTSMongoModelMaker()
    {
        map=new HashMap();

        
        DB db = SWBTSMongo.getMongo().getDB(SWBPlatform.getEnv("swb/mongodbname","swb"));
        if(!db.isAuthenticated() && SWBPlatform.getEnv("swb/mongodbuser")!=null && SWBPlatform.getEnv("swb/mongodbpasswd")!=null)
        {
            db.authenticate(SWBPlatform.getEnv("swb/mongodbuser"), SWBPlatform.getEnv("swb/mongodbpasswd").toCharArray());
        }
        

        Set<String> colls = db.getCollectionNames();
        
        if(colls.contains("swb_graph"))
        {
            DBCollection coll = db.getCollection("swb_graph");              
            DBCursor cur=coll.find();
            
            while(cur.hasNext())
            {
                DBObject obj=cur.next();
                
                int id=(Integer)obj.get("id");
                String name=(String)obj.get("name");
                map.put(name, id);
            }            
            
            cur.close();
            
        }else
        {
            DBCollection coll = db.getCollection("swb_graph");
            coll.createIndex(new BasicDBObject("id", -1));
            
            coll = db.getCollection("swb_prefix");
            BasicDBObject index=new BasicDBObject("prefix", 1);
            index.put("graphid", 1);            
            coll.createIndex(index);
        }
        
        
    }

    public Iterator<String> listModelNames()
    {
        return map.keySet().iterator();
    }

    public Model getModel(String name)
    {
        Integer id=map.get(name);
        if(id!=null)
        {
            return new ModelCom(new SWBTSMongoGraph(id,name));
        }else
        {
            return null;
        }
    }

    public Model createModel(String name)
    {
        Model model=getModel(name);
        if(model==null)
        {
            try
            {
                int id=0;

                DB db = SWBTSMongo.getMongo().getDB(SWBPlatform.getEnv("swb/mongodbname","swb"));
                if(!db.isAuthenticated() && SWBPlatform.getEnv("swb/mongodbuser")!=null && SWBPlatform.getEnv("swb/mongodbpasswd")!=null)
                {
                    db.authenticate(SWBPlatform.getEnv("swb/mongodbuser"), SWBPlatform.getEnv("swb/mongodbpasswd").toCharArray());
                }                
                
                DBCollection coll = db.getCollection("swb_graph");
                DBCursor cur=coll.find().sort(new BasicDBObject("id", -1)).limit(1);
                if(cur.hasNext())
                {
                    DBObject obj=cur.next();
                    id=(Integer)obj.get("id")+1;
                }
                
                coll = db.getCollection("swb_graph_ts"+id);    
                
                BasicDBObject index=new BasicDBObject("subj", -1);
                coll.createIndex(index);
                
                index=new BasicDBObject("prop", 1);
                coll.createIndex(index);
                
                index=new BasicDBObject("obj", -1);
                coll.createIndex(index);
                
                index=new BasicDBObject("subj", -1);
                index.put("prop", 1);
                coll.createIndex(index);
                
                index=new BasicDBObject("prop", 1);
                index.put("obj", -1);
                coll.createIndex(index);
                
                index=new BasicDBObject("subj", -1);
                index.put("prop", 1);
                index.put("obj", -1);
                coll.createIndex(index);                                
                
                
                coll = db.getCollection("swb_graph");                
                BasicDBObject doc = new BasicDBObject();
                doc.put("id",id);
                doc.put("name",name);
                coll.insert(doc);

                model=new ModelCom(new SWBTSMongoGraph(id,name));
                map.put(name, id);

            }catch(Exception e2)
            {
                log.error(e2);
            }
        }
        return model;
    }

    public void removeModel(String name)
    {
        Model model=getModel(name);
        if(model!=null)
        {
            int id=((SWBTSMongoGraph)model.getGraph()).getId();
            try
            {
                DB db = SWBTSMongo.getMongo().getDB(SWBPlatform.getEnv("swb/mongodbname","swb"));
                if(!db.isAuthenticated() && SWBPlatform.getEnv("swb/mongodbuser")!=null && SWBPlatform.getEnv("swb/mongodbpasswd")!=null)
                {
                    db.authenticate(SWBPlatform.getEnv("swb/mongodbuser"), SWBPlatform.getEnv("swb/mongodbpasswd").toCharArray());
                }
                
                DBCollection coll = db.getCollection("swb_graph_ts"+id);
                coll.drop();
                
                coll = db.getCollection("swb_graph");                
                BasicDBObject doc = new BasicDBObject();
                doc.put("id",id);
                coll.findAndRemove(doc);                

                coll = db.getCollection("swb_prefix");                
                doc = new BasicDBObject();
                doc.put("graphid",id);
                coll.findAndRemove(doc);                

                map.remove(name);

            }catch(Exception e2)
            {
                log.error(e2);
            }
        }
    }

    public HashMap<String,Integer> getMap()
    {
        return map;
    }

}
