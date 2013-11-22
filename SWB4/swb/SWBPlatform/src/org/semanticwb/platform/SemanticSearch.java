/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.platform;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.vocabulary.RDF;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.RGraph;

/**
 *
 * @author javier.solis.g
 */
public class SemanticSearch
{
    private static Logger log= SWBUtils.getLogger(SemanticSearch.class);    
    
    public static Iterator<SemanticObject> listFullInstances(SemanticModel model, SemanticClass cls)
    {        
        if(SWBPlatform.isSWBTripleStoreExt())
        {
            RGraph graph=(RGraph)model.getRDFModel().getGraph();
            int id=graph.getId();
            
            try
            {
                Connection con= SWBUtils.DB.getDefaultConnection();
                String query="select t1.subj, t1.prop, t1.obj, t1.ord, t1. stype, t1.timems, t1.ext \n" +
        "        from swb_graph_ts"+id+" as t1 \n" +
        "        LEFT JOIN swb_graph_ts"+id+" as t2 ON (t1.subj=t2.subj) \n" +
        "        WHERE t2.prop=? and t2.obj=? \n" +
        "        order by t2.timems";
                PreparedStatement ps=con.prepareStatement(query);
                ps.setString(1, graph.encodeProperty(RDF.type.asNode()));
                ps.setString(2, graph.encodeSubject(cls.getOntClass().asNode()));
                ResultSet rs=ps.executeQuery();                                
                return new SemanticFullIterator(model, rs, con);
            }catch(Exception e)
            {
                new IOException(e);
            }
        }
        return null;
    }
    
    public static Iterator<SemanticObject> search(SemanticModel model, List<Triple> triples)
    {
        return search(model, triples, null, null, null, null, false);
    }
    
    public static SemanticQueryIterator<SemanticObject> search(SemanticModel model, List<Triple> triples, String stype, Integer limit, Integer offset, Property orderby, boolean desc)
    {        
        if(SWBPlatform.isSWBTripleStoreExt())
        {
            RGraph graph=(RGraph)model.getRDFModel().getGraph();
            int id=graph.getId();
            
            try
            {
                int joins=triples.size()-1;
                //if(joins<0)joins=0;
                if(orderby!=null)joins++;
                
                Connection con= SWBUtils.DB.getDefaultConnection();
                StringBuffer query=new StringBuffer();
                StringBuffer where=new StringBuffer();
                query.append("select t1.subj, t1.ext from swb_graph_ts"+id+" as t1");
                for(int x=1;x<=joins;x++)
                {
                    query.append(" LEFT JOIN swb_graph_ts"+id+" as t"+(x+1)+" on (t"+x+".subj = t"+(x+1)+".subj)");                    
                }
                
                if(stype!=null)where.append(" t1.stype='"+stype+"'");

                ArrayList<String> params=new ArrayList();
                int cp=1;
                Iterator<Triple> it=triples.iterator();
                while (it.hasNext())
                {
                    Triple t = it.next();
                    String subj=graph.encodeSubject(t.getMatchSubject());
                    String prop=graph.encodeProperty(t.getMatchPredicate());
                    String obj=graph.encodeObject(t.getMatchObject());
                    
                    if(subj!=null)
                    {
                        if(where.length()>0)where.append(" and");                
                        where.append(" t"+cp+".subj=?");
                        params.add(subj);
                    }                    
                    if(prop!=null)
                    {
                        if(where.length()>0)where.append(" and");                
                        where.append(" t"+cp+".prop=?");
                        params.add(prop);
                    }                    
                    if(obj!=null)
                    {
                        if(where.length()>0)where.append(" and");                
                        where.append(" t"+cp+".obj=?");
                        params.add(obj);
                    }                    
                    cp++;
                }
                if(orderby!=null)
                {
                    if(where.length()>0)where.append(" and");                                
                    where.append(" t"+cp+".prop='uri|"+orderby.getURI()+"' order by t"+cp+".ord");
                    if(desc)where.append(" desc");                
                }
                
                if(where.length()>0)
                {
                    query.append(" where");
                    query.append(where);
                }
                
                if(offset !=null && limit!=null)
                {
                    query.append(" limit "+offset+", "+limit);
                }else if(limit!=null)
                {
                    query.append(" limit "+limit);
                }                
                
                System.out.println(query.toString());
                PreparedStatement ps=con.prepareStatement(query.toString());
                Iterator<String> it2=params.iterator();
                int pc=1;
                while (it2.hasNext())
                {
                    String string = it2.next();
                    System.out.println("param:"+pc+":"+string);
                    ps.setString(pc, string);
                    pc++;
                }
                ResultSet rs=ps.executeQuery();                                
                return new SemanticQueryIterator(model, rs, con);
            }catch(Exception e)
            {
                log.error(e);
            }
        }
        return null;
    }    
}




