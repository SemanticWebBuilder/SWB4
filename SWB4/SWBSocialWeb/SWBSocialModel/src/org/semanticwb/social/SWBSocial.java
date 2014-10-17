/**  
* SWB Social es una plataforma que descentraliza la publicación, seguimiento y monitoreo hacia las principales redes sociales. 
* SWB Social escucha y entiende opiniones acerca de una organización, sus productos, sus servicios e inclusive de su competencia, 
* detectando en la información sentimientos, influencia, geolocalización e idioma, entre mucha más información relevante que puede ser 
* útil para la toma de decisiones. 
* 
* SWB Social, es una herramienta basada en la plataforma SemanticWebBuilder. SWB Social, como SemanticWebBuilder, es una creación original 
* del Fondo de Información y Documentación para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SWB Social a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarla en las mismas condiciones con que INFOTEC la ha diseñado y puesto a su disposición; 
* aprender de élla; distribuirla a terceros; acceder a su código fuente y modificarla, y combinarla o enlazarla con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. y SWB Social 1.0
* 
* INFOTEC no otorga garantía sobre SWB Social, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder o SWB Social, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
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
            QueryExecution qe=null;
            try{
                qe=wsite.getSemanticModel().sparQLQuery(query);
            }catch(Exception ignore){return "0";}
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
