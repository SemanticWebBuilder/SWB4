/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.rdf.sparql.SWBQueryExecution;
/**
 *
 * @author jorge.jimenez
 */
public class SWBSocial {
    
    
   public static String executeQuery(String query, WebSite wsite)
   {
        if(query!=null)
        {
            //QueryExecution qe=new SWBQueryExecution(wsite.getSemanticModel().getRDFModel(), query);
            QueryExecution qe=wsite.getSemanticModel().sparQLQuery(query);
            ResultSet rs=qe.execSelect();
            while(rs.hasNext())
            {
                QuerySolution qs=rs.next();
                Iterator<String> it=rs.getResultVars().iterator();
                while(it.hasNext())
                {
                    String name=it.next();
                    //System.out.println("name en executeQueryG:"+name);
                    if(name.equalsIgnoreCase("c1"))
                    {
                        RDFNode node=qs.get(name);
                        //System.out.println("node en executeQuery:"+node);
                        //System.out.println("node en executeQuery-1:"+node.asLiteral());
                        String val="";
                        if(node.isLiteral())val=node.asLiteral().getLexicalForm();
                        //System.out.println("val en executeQuery:"+val);
                        return val;
                    }
                }
            }
        }
        return "0";
   }
   
   public static ArrayList executeQueryArray(String query, WebSite wsite)
   {
        ArrayList aResult=new ArrayList();
        //QueryExecution qe=new SWBQueryExecution(wsite.getSemanticModel().getRDFModel(), query);
        QueryExecution qe=wsite.getSemanticModel().sparQLQuery(query);
        ResultSet rs=qe.execSelect();
        while(rs!=null && rs.hasNext())
        {
            QuerySolution qs=rs.next();
            Iterator<String> it=rs.getResultVars().iterator();
            while(it.hasNext())
            {
                String name=it.next();
                if(name.equalsIgnoreCase("postUri"))
                {
                    //System.out.println("sQuery a Ejecutar..name:"+name);
                    RDFNode node=qs.get(name);
                    String val="";
                    if(node.isResource()){
                        val=node.asResource().getURI();
                        //System.out.println("ValGeorgeResource:"+val);
                        SemanticObject semObj=SemanticObject.createSemanticObject(val, wsite.getSemanticModel()); 
                        //System.out.println("semObj:"+semObj);
                        PostIn postIn=(PostIn)semObj.createGenericInstance();
                        //System.out.println("semObj/PostIn:"+postIn);
                        aResult.add(postIn);
                    }
                }
            }
        }
        return aResult;
   }
   
   public static ArrayList executeQueryArrayPostOuts(String query, WebSite wsite)
   {
        ArrayList aResult=new ArrayList();
        QueryExecution qe=wsite.getSemanticModel().sparQLQuery(query);
        ResultSet rs=qe.execSelect();
        while(rs!=null && rs.hasNext())
        {
            QuerySolution qs=rs.next();
            Iterator<String> it=rs.getResultVars().iterator();
            while(it.hasNext())
            {
                String name=it.next();
                if(name.equalsIgnoreCase("postUri"))
                {
                    RDFNode node=qs.get(name);
                    String val="";
                    if(node.isResource()){
                        val=node.asResource().getURI();
                        SemanticObject semObj=SemanticObject.createSemanticObject(val, wsite.getSemanticModel()); 
                        PostOut postOut=(PostOut)semObj.createGenericInstance();
                        aResult.add(postOut);
                    }
                }
            }
        }
        return aResult;
   }
    
}
