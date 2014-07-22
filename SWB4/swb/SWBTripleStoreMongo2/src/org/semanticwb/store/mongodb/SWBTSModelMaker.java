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
package org.semanticwb.store.mongodb;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.rdf.GraphCached;
import org.semanticwb.store.jenaimp.SWBTSGraph;
import org.semanticwb.store.jenaimp.SWBTSGraphCache;

/**
 *
 * @author jei
 */
public class SWBTSModelMaker extends org.semanticwb.store.jenaimp.SWBTSModelMaker
{
    private static Logger log=SWBUtils.getLogger(SWBTSModelMaker.class);
    
    private HashMap<String,String> params;

    public SWBTSModelMaker(String clsname)
    {
        super(clsname);
        params=new HashMap();
        params.put("swb/tripleremoteserver", SWBPlatform.getEnv("swb/tripleremoteserver", "localhost"));
        params.put("swb/tripleremoteport", SWBPlatform.getEnv("swb/tripleremoteport", "27017"));
        params.put("swb/mongodbname", SWBPlatform.getEnv("swb/mongodbname", "swb"));  
        if(SWBPlatform.getEnv("swb/mongodbuser")!=null)params.put("swb/mongodbuser", SWBPlatform.getEnv("swb/mongodbuser"));  
        if(SWBPlatform.getEnv("swb/mongodbpasswd")!=null)params.put("swb/mongodbpasswd", SWBPlatform.getEnv("swb/mongodbpasswd"));                  
    }

    @Override
    public List<String> listModelNames()
    {
        ArrayList arr=new ArrayList();
        try
        {
            MongoClient mongo = new MongoClient( params.get("swb/tripleremoteserver"), Integer.parseInt(params.get("swb/tripleremoteport")));
            DB db = mongo.getDB(params.get("swb/mongodbname"));
            Iterator<String> it=db.getCollectionNames().iterator();
            while (it.hasNext())
            {
                String name = it.next();
                if(name.startsWith("swb_graph_ts_"))
                {
                    arr.add(name.substring(13));
                }
            }
            mongo.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return arr;
    }

    @Override
    public Model createModel(String name)
    {
        Model model=map.get(name);
        if(model==null || model.isClosed())
        {
            try
            {
                Class cls=Class.forName(clsname);
                Constructor c=cls.getConstructor(String.class, Map.class);                
                
                org.semanticwb.store.Graph g=(org.semanticwb.store.Graph)c.newInstance(name,params);
                
                int size=Integer.parseInt(SWBPlatform.getEnv("swb/tripleStoreResourceCache","0"));

                if(size>0)
                {
                    model=new ModelCom(new SWBTSGraphCache(new SWBTSGraph(g),size));
                }else if(size==0)
                {
                    model=new ModelCom(new SWBTSGraph(g));
                }else if(size<0)
                {
                    model=new ModelCom(new GraphCached(new SWBTSGraph(g)));
                }
                
                map.put(name, model);
            }catch(Exception e2)
            {
                log.error(e2);
            }
        }
        
        return model;
    }

    @Override
    public void removeModel(String name)
    {
        Model m=map.get(name);
        if(m!=null)
        {
            try
            {
                m.close();                
                map.remove(name);
 
                MongoClient mongo = new MongoClient( params.get("swb/tripleremoteserver"), Integer.parseInt(params.get("swb/tripleremoteport")));
                DB db = mongo.getDB(params.get("swb/mongodbname"));
                db.dropDatabase();
                mongo.close();                
            }catch(Exception e2)
            {
                log.error(e2);
            }
        }
    }
   


}
