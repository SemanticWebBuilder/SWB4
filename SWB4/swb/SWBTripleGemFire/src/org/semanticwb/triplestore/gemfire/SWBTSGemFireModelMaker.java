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
package org.semanticwb.triplestore.gemfire;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.query.IndexType;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/**
 *
 * @author jei
 */
public class SWBTSGemFireModelMaker
{
    private static Logger log=SWBUtils.getLogger(SWBTSGemFireModelMaker.class);
    
    private HashMap<String,Integer> map;

    public SWBTSGemFireModelMaker()
    {
        map=new HashMap();

        
        //Region db = SWBTSGemFire.getCache().getRegion(SWBPlatform.getEnv("swb/gemfire_region_name","swb"));
      /*  if(db==null)
        {
            db=SWBTSGemFire.getCache().createRegionFactory(com.gemstone.gemfire.cache.RegionShortcut.REPLICATE_PERSISTENT).create(SWBPlatform.getEnv("swb/gemfire_region_name","swb"));            
        }
*/
        Region<String,Integer> graphs=SWBTSGemFire.getCache().getRegion("swb_graph");
/*        if(graphs==null)
        {
            graphs=db.createSubregion("swb_graph", db.getAttributes());
            Region<String,String> prefix=db.createSubregion("swb_prefix", db.getAttributes());
        }
  */      
        Region<String,String> prefix=SWBTSGemFire.getCache().getRegion("swb_prefix");
/*        if(graphs==null)
        {
            prefix=db.createSubregion("swb_prefix", db.getAttributes());
        }
*/        
        Iterator<Map.Entry<String,Integer>> cur=graphs.entrySet().iterator();

        while(cur.hasNext())
        {
            Map.Entry<String,Integer> obj=cur.next();

            int id=(Integer)obj.getValue();
            String name=(String)obj.getKey();
            map.put(name, id);
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
            return new ModelCom(new SWBTSGemFireGraph(id,name));
        }else
        {
            return null;
        }
    }

    public Model createModel(String name)
    {
        Model model=getModel(name); //System.out.println("CreateModel: "+model);
        if(model==null)
        {
            try
            {
                int id=0;
                
                //Region db = SWBTSGemFire.getCache().getRegion(SWBPlatform.getEnv("swb/gemfire_region_name","swb"));
                Region<String,Integer> graphs=SWBTSGemFire.getCache().getRegion("swb_graph");
               
                
                Iterator<Integer> it=graphs.values().iterator();
                while (it.hasNext())
                {
                    Integer integer = it.next();
                    if(id<integer)id=integer; //System.out.println("********** "+integer+" "+id+"      "+name);
                }
                
                id++;
                /*
                Region<String,SWBTSGemFireTriple> graph=db.createSubregion("swb_graph_ts"+id, db.getAttributes());

                SWBTSGemFire.getCache().getQueryService().createIndex(SWBPlatform.getEnv("swb/gemfire_region_name","swb")+"_ind_subj_"+id, IndexType.FUNCTIONAL, "subj", "/"+SWBPlatform.getEnv("swb/gemfire_region_name","swb")+"/swb_graph_ts"+id);
                SWBTSGemFire.getCache().getQueryService().createIndex(SWBPlatform.getEnv("swb/gemfire_region_name","swb")+"_ind_prop_"+id, IndexType.FUNCTIONAL, "prop", "/"+SWBPlatform.getEnv("swb/gemfire_region_name","swb")+"/swb_graph_ts"+id);
                SWBTSGemFire.getCache().getQueryService().createIndex(SWBPlatform.getEnv("swb/gemfire_region_name","swb")+"_ind_obj_"+id, IndexType.FUNCTIONAL, "obj", "/"+SWBPlatform.getEnv("swb/gemfire_region_name","swb")+"/swb_graph_ts"+id);
                */
                //graphs.put("swb_graph_ts"+id, id);
                graphs.put(name, id);
                
                model=new ModelCom(new SWBTSGemFireGraph(id,name));
                map.put(name, id);
                //System.out.println("name:"+name+", id:"+id+"   ==="+graphs.values().size());


            }catch(Exception e2)
            {
                log.error(e2);
            }
        } //System.out.println("Model:"+model);
        return model;
    }

    public void removeModel(String name)
    {
        Model model=getModel(name);
        if(model!=null)
        {
            int id=((SWBTSGemFireGraph)model.getGraph()).getId();
            try
            {
                //Region db = SWBTSGemFire.getCache().getRegion(SWBPlatform.getEnv("swb/gemfire_region_name","swb"));
               /* 
                Region graph = db.getSubregion("swb_graph_ts"+id);
                graph.destroyRegion();
                */
                Region graphs = SWBTSGemFire.getCache().getRegion("swb_graph");                
                graphs.remove("name");

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
