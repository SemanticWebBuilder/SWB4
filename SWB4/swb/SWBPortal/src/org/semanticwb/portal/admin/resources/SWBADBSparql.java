/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.Statement;
//import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
//import org.semanticwb.base.db.DBConnectionPool;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import com.hp.hpl.jena.query.*;
//import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
//import com.hp.hpl.jena.sparql.util.IndentedWriter;
import java.util.Iterator;
//import org.junit.*;
import org.semanticwb.SWBPlatform;
//import org.semanticwb.platform.SemanticVocabulary;

/**
 *
 * @author juan.fernandez
 */
public class SWBADBSparql extends GenericResource {

    private Logger log=SWBUtils.getLogger(SWBADBSparql.class);
    static public final String NL = System.getProperty("line.separator") ; 
    
    /** Creates a new instance of WBADBQuery */
    public SWBADBSparql()
    {
    }

    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        String _query=request.getParameter("query");
        if(_query==null)_query="";
        else _query=_query.trim();
//        String dbcon=request.getParameter("dbcon");
        
        out.println("<form action=\""+paramRequest.getRenderUrl()+"\" method=\"post\">");
//        out.println("<p class=\"box\">");
//        out.println("<table border=\"0\" cellspacing=\"2\" cellpadding=\"0\" width=\"100%\">");
//        out.println("<tr><td class=\"tabla\" width=\"200\">");
//        out.println(paramRequest.getLocaleString("connPool"));
//        out.println("</td><td class=\"tabla\">");
//        out.println("<select name=\"dbcon\">");
//        Enumeration<DBConnectionPool> en=SWBUtils.DB.getPools();
//        while(en.hasMoreElements())
//        {
//            DBConnectionPool pool = en.nextElement();
//            String name=pool.getName();
//            out.print("<option value=\""+name+"\"");
//            if(name.equals(dbcon))out.print(" selected");
//            out.println(">"+name+"</option>");
//        }
//        out.println("</select>");
//        out.println("</td></tr>");
//        out.println("</table>");
//        out.println("</p>");
        out.println("<p class=\"box\">");
        out.println("<table border=\"0\" cellspacing=\"2\" height=\"100%\" cellpadding=\"0\" width=\"100%\">");
        out.println("<tr><td class=\"tabla\">");
        out.println("SPARQL Example:");
        out.println("</td></tr>");
        out.println("<tr><td class=\"tabla\">");
        out.println("PREFIX  swb:  <http://www.semanticwebbuilder.org/swb4/ontology#><br>");
        out.println("PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#><br>");
        out.println("PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#><br>");
        out.println("<br>");
        out.println("SELECT ?title ?class WHERE {?x swb:title ?title. ?x rdf:type swb:WebPage} <br>");
        out.println("</td></tr>");
        out.println("<tr><td class=\"tabla\">");
        out.println("SPARQL:");
        out.println("</td></tr>");
        out.println("<tr><td>");
        out.print("<textarea name=\"query\" rows=10 cols=80>");
        out.print(_query);
        out.println("</textarea>");
        out.println("</td></tr>");
        out.println("<tr><td>");
        out.println("<HR size=1 noshade>");
        out.println("</td></tr>");
        out.println("<tr><td align=\"right\">");
        out.println("<input type=\"submit\" name=\"submit\" class=\"boton\" value=\""+paramRequest.getLocaleString("send")+"\">");
        out.println("</td></tr>");
        out.println("</table>");
        out.println("</p>");

        long time=System.currentTimeMillis();
        try
        {
            if(_query.length()>0)
            {
                out.println("<p>");
                out.println("<table border=0>");
                
                
        
                Model model=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
        
                // First part or the query string 
//                String prolog = "PREFIX swb: <"+SemanticVocabulary.URI+">" ;
//                prolog+= "PREFIX rdf: <"+SemanticVocabulary.RDF_URI+">" ;
//                prolog+= "PREFIX rdfs: <"+SemanticVocabulary.RDFS_URI+">" ;

                // Query string.
//                String queryString = prolog + NL +
//                    "SELECT ?title ?class WHERE {?x swb:title ?title. ?x rdf:type swb:WebPage}" ; 
                String queryString = _query;

                Query query = QueryFactory.create(queryString) ;
                // Print with line numbers
//                query.serialize(new IndentedWriter(System.out,true)) ;
//                System.out.println() ;

                // Create a single execution of this query, apply to a model
                // which is wrapped up as a Dataset

                QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
                // Or QueryExecutionFactory.create(queryString, model) ;

                try {
                    // Assumption: it's a SELECT query.
                    ResultSet rs = qexec.execSelect() ;
                    int col = rs.getResultVars().size();
                    out.println("<tr>");
                    for (int x = 1; x <= col; x++) 
                    {
                        out.println("<td>");
                        out.println(rs.getResultVars().get(x));
                        out.println("</td>");
                    }
                    out.println("</tr>");
//                    Iterator<String> it=rs.getResultVars().iterator();
//                    while(it.hasNext())
//                    {
//                        System.out.print(it.next()+"\t");
//                    }
//                    System.out.println();

                    
                    int ch=0;
//                    while (rs1.next()) {
//                        if(ch==0)
//                        {
//                            ch=1;
//                            out.println("<tr bgcolor=\"#EFEDEC\">");
//                        }
//                        else
//                        {
//                            ch=0;
//                            out.println("<tr>");
//                        }
//                        for (int x = 1; x <= col; x++) {
//                            String aux = rs1.getString(x);
//                            if (aux == null) aux = "";
//                            out.println("<td class=\"valores\">");
//                            out.println(aux);
//                            out.println("</td>");
//                        }
//                        out.println("</tr>");
//                    }
                    
                    // The order of results is undefined. 
                    for ( ; rs.hasNext() ; )
                    {
                        QuerySolution rb = rs.nextSolution() ;

                        if(ch==0)
                        {
                            ch=1;
                            out.println("<tr bgcolor=\"#EFEDEC\">");
                        }
                        else
                        {
                            ch=0;
                            out.println("<tr>");
                        }
//                        for (int x = 1; x <= col; x++) {
//                            String aux = rb.getString(x);
//                            if (aux == null) aux = "";
//                            out.println("<td class=\"valores\">");
//                            out.println(aux);
//                            out.println("</td>");
//                        }
                        Iterator<String> it=rs.getResultVars().iterator();
                        while(it.hasNext())
                        {
                            String name=it.next();
                            RDFNode x = rb.get(name) ;
                            out.println("<td >");
                            out.println(x!=null?x:" - ");
                            out.println("</td>");
                        }
                        out.println("</tr>");

                        // Get title - variable names do not include the '?' (or '$')
        //                RDFNode x = rb.get("x") ;
        //                RDFNode title = rb.get("title") ;
        //                
        //                System.out.println("x:"+x+" title:"+title) ;
        //                // Check the type of the result value
        //                if ( x.isLiteral() )
        //                {
        //                    Literal titleStr = (Literal)x  ;
        //                    System.out.println("    "+titleStr) ;
        //                }
        //                else
        //                    System.out.println("Strange - not a literal: "+x) ;

                    }
                    
                }
                finally
                {
                    // QueryExecution objects should be closed to free any system resources 
                    
                    qexec.close() ;
                }

                out.println("</table>");
                out.println("</p>");
                out.println("<p aling=\"center\">Execution Time:"+(System.currentTimeMillis()-time)+"</p>");
            }
        }catch(Exception e)
        {
            out.println("<p class=\"box\">");
            out.println("Error: <BR>");
            out.println("<textarea name=\"query\" rows=20 cols=80>");
            e.printStackTrace(out);
            out.println("</textarea>");
            out.println("</p>");
        }
        out.println("</form>");
        
    }

    
    
}
