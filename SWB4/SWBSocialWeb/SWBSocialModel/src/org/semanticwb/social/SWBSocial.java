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
import java.util.LinkedHashMap;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
//import org.semanticwb.rdf.sparql.SWBQueryExecution;
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
            //System.out.println("SWBSocial/executeQuery:"+query);
            QueryExecution qe=wsite.getSemanticModel().sparQLQuery(query);
            //System.out.println("SWBSocial-1/executeQuery:"+qe);
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
                        //System.out.println("node:"+node);
                        try{
                            if(node.isLiteral())val=node.asLiteral().getLexicalForm();
                        }catch(Exception e){val="0";}
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
                        if(semObj.createGenericInstance() instanceof PostIn){
                            PostIn postIn=(PostIn)semObj.createGenericInstance();
                             aResult.add(postIn);
                             //System.out.println("semObj/PostIn:"+postIn);
                        }else if(semObj.createGenericInstance() instanceof PostOut){
                            PostOut postOut = (PostOut)semObj.createGenericInstance();
                             aResult.add(postOut);
                             //System.out.println("semObj/postOut:"+postOut);
                        }         
                       
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
   
   public static ArrayList executeQueryArraySemObj(String query, WebSite wsite)
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
                if(name.equalsIgnoreCase("semObj"))
                {
                    RDFNode node=qs.get(name);
                    String val="";
                    if(node.isResource()){
                        val=node.asResource().getURI();
                        SemanticObject semObj=SemanticObject.createSemanticObject(val, wsite.getSemanticModel()); 
                        aResult.add(semObj);
                    }
                }
            }
        }
        return aResult;
   }
   /**
    * Specific implementation to return a pair of values, the Semantic Object
    * and the count value for this SemObj
    * @param query
    * @param wsite
    * @return The user and the number of posts per user.
    */
   public static LinkedHashMap executeQueryArraySemObjAndCount(String query, WebSite wsite)
   {
        LinkedHashMap aResult = new LinkedHashMap();
        QueryExecution qe=wsite.getSemanticModel().sparQLQuery(query);
        ResultSet rs=qe.execSelect();
        while(rs!=null && rs.hasNext())
        {
            QuerySolution qs=rs.next();
            Iterator<String> it=rs.getResultVars().iterator();
            SemanticObject semObjTmp = null;
            String tmpValue = null;
            while(it.hasNext())
            {
                String name=it.next();
                if(name.equalsIgnoreCase("semObj"))
                {
                    RDFNode node=qs.get(name);
                    String val="";
                    if( node != null && node.isResource()){
                        val=node.asResource().getURI();
                        SemanticObject semObj=SemanticObject.createSemanticObject(val, wsite.getSemanticModel()); 
                        semObjTmp = semObj;
                        //aResult.add(semObj);
                    }
                }else{                    
                    RDFNode node=qs.get(name);
                    if(node != null && node.asLiteral().getInt() > 0){
                        tmpValue = String.valueOf(node.asLiteral().getInt());
                    }
                }
            }
            if(semObjTmp != null && tmpValue != null){
                aResult.put(semObjTmp, tmpValue);
            }
        }
        return aResult;
   }
    
}
