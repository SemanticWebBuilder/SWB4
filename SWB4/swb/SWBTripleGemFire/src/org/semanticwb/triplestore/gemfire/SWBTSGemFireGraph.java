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
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.internal.ResultsBag;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.impl.GraphBase;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.RGraph;

/**
 *
 * @author jei
 */
public class SWBTSGemFireGraph extends GraphBase implements RGraph
{
    private static Logger log = SWBUtils.getLogger(SWBTSGemFireGraph.class);

    private String name;
    private int id;

    private PrefixMapping pmap;
    //private BigdataTransactionHandler trans;


    public SWBTSGemFireGraph(int id, String name)
    {
        this.id=id;
        this.name=name;
        pmap=new SWBTSGemFirePrefixMapping(this);
    }

    @Override
    protected ExtendedIterator<Triple> graphBaseFind(TripleMatch tm)
    {
        return new SWBTSGemFireIterator(this, tm);
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
            String subj=SWBTSGemFireUtil.node2String(t.getSubject());
            String hsubj=SWBTSGemFireUtil.getHashText(subj);
            String prop=SWBTSGemFireUtil.node2String(t.getPredicate());
            String hprop=SWBTSGemFireUtil.getHashText(prop);
            String obj=SWBTSGemFireUtil.node2String(t.getObject());
            String hobj=SWBTSGemFireUtil.getHashText(obj);
            
            
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
            
            //Region db = SWBTSGemFire.getCache().getRegion(SWBPlatform.getEnv("swb/gemfire_region_name","swb"));
            Region<String,SWBTSGemFireTriple> graph= SWBTSGemFire.getCache().getRegion("swb_graph_ts"); //+getId());
           /* if(graph==null)
            {
                graph=db.createSubregion("swb_graph_ts"+getId(), db.getAttributes());
            }*/
            
            SWBTSGemFireTriple tp=new SWBTSGemFireTriple(subj, prop, obj, sext, ""+this.id);
            graph.put(tp.getId(), tp);
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
            String subj=SWBTSGemFireUtil.node2HashString(t.getMatchSubject(),"lgs");
            String prop=SWBTSGemFireUtil.node2HashString(t.getMatchPredicate(),"lgp");
            String obj=SWBTSGemFireUtil.node2HashString(t.getMatchObject(),"lgo");

            //System.out.println("performDelete:"+subj+" "+prop+" "+obj);
            
           // Region db = SWBTSGemFire.getCache().getRegion(SWBPlatform.getEnv("swb/gemfire_region_name","swb"));
            Region<String,SWBTSGemFireTriple> graph= SWBTSGemFire.getCache().getRegion("swb_graph_ts");//+getId());
           /* if(graph==null)
            {
                graph=db.createSubregion("swb_graph_ts"+getId(), db.getAttributes());
            }
           */ 
            Iterator<SWBTSGemFireTriple> it=find(graph, subj, prop, obj).iterator();
            while (it.hasNext())
            {
                SWBTSGemFireTriple tp = it.next();
                graph.destroy(tp.getId());
            }
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
    
    
    public ResultsBag find(Region store, String t_subj, String t_prop, String t_obj)
    {
        //System.out.println("Buscando: s:"+t_subj+" p:"+t_prop+" o:"+t_obj);
        ArrayList<SWBTSGemFireTriple> ret=new ArrayList<SWBTSGemFireTriple>();
        ArrayList<String> arr=new ArrayList<String>();
        try
        {
            int x=0;
            String subj="";
            if(t_subj!=null)
            {
                arr.add(t_subj);
                x++;
                subj="subj=$"+x;
            }
            String prop="";
            if(t_prop!=null)
            {
                arr.add(t_prop);
                x++;
                prop="prop=$"+x;
            }
            String obj="";
            if(t_obj!=null)
            {
                arr.add(t_obj);
                x++;
                obj="obj=$"+x;
            }
            String query=subj;
            query+=(subj.length()>0 && prop.length()>0)?" and ":"";
            query+=prop;
            query+=(query.length()>0 && obj.length()>0)?" and ":"";
            query+=obj;
            x++;
            if(query.length()>0)
            {
                query="SELECT * FROM /"/*+store.getParentRegion().getName()+"/"*/+store.getName()+" WHERE "+query+" and model=$"+x;
            }else
            {
                query="SELECT * FROM /"/*+store.getParentRegion().getName()+"/"*/+store.getName()+" WHERE model=$"+x;
            }
            arr.add(""+this.id);
            QueryService queryService = SWBTSGemFire.getCache().getQueryService();
            Query q = queryService.newQuery(query);
            
           // System.out.println("Query:"+query+" ("+arr+")");
            ResultsBag r=(ResultsBag)q.execute(arr.toArray());
          //  System.out.println("Query: size "+r.size());
          //  System.out.println("Query:"+query+" ("+arr+")"+" "+r.size());
            
//            SWBTSGemFireTriple[] r=(SWBTSGemFireTriple[])q.execute(arr.toArray());      
//            for(int i=0;i<r.length;i++)
//            {
//                ret.add(r[i]);
//            }

            return r;
            
            //return store.query(query);      

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    

}
