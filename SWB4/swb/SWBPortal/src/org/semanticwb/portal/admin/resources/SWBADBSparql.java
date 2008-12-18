/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.util.Iterator;
import org.semanticwb.SWBPlatform;



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

        out.println("<div class=\"swbform\">");
        out.println("<form action=\""+paramRequest.getRenderUrl()+"\" method=\"post\">");
        out.println("<fieldset>");
        out.println("<table border=\"0\" cellspacing=\"2\" cellpadding=\"0\" >");
        out.println("<tr><td >");
        out.println("SPARQL Example:");
        out.println("</td></tr>");
        out.println("<tr><td ><PRE >");
        out.println("PREFIX  swb:  &lt;http://www.semanticwebbuilder.org/swb4/ontology#&gt;");
        out.println("PREFIX  rdfs: &lt;http://www.w3.org/2000/01/rdf-schema#&gt;");
        out.println("PREFIX  rdf:  &lt;http://www.w3.org/1999/02/22-rdf-syntax-ns#&gt;");
        out.println("<br>");
        out.println("SELECT ?title ?desc WHERE {?x swb:title ?title. ?x rdf:type swb:WebPage. ?x swb:description ?desc}");
        out.println("</PRE></td></tr>");
        out.println("<tr><td class=\"tabla\">");
        out.println("SPARQL:");
        out.println("</td></tr>");
        out.println("<tr><td>");
        out.print("<textarea name=\"query\" rows=10 cols=80>");
        out.print(_query);
        out.println("</textarea>");
        out.println("</td></tr>");
        out.println("</table>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<input type=\"submit\" name=\"submit\" value=\""+paramRequest.getLocaleString("send")+"\">");
        out.println("</fieldset>");

        long time=System.currentTimeMillis();
        try
        {
            if(_query.length()>0)
            {
                out.println("<fieldset>");
                out.println("<table border=0>");

                Model model=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();

                String queryString = _query;

                Query query = QueryFactory.create(queryString) ;
                query.serialize(); //new IndentedWriter(response.getOutputStream(),true)) ;
                // Create a single execution of this query, apply to a model
                // which is wrapped up as a Dataset
                QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
                // Or QueryExecutionFactory.create(queryString, model) ;
                try {
                    // Assumption: it's a SELECT query.
                    ResultSet rs = qexec.execSelect() ;
                    int col = rs.getResultVars().size();
                    //log.debug("cols:"+col);
                    out.println("<thead>");
                    out.println("<tr>");
                    
                    Iterator<String> itcols=rs.getResultVars().iterator();
                    while(itcols.hasNext())
                    {
                        out.println("<th>");
                        out.println(itcols.next());
                        out.println("</th>");
                    }
                    out.println("</tr>");
                    out.println("</thead>");
                    out.println("<tbody>");
                    int ch=0;
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


                    }
                    out.println("</tbody>");
                    
                }
                finally
                {
                    // QueryExecution objects should be closed to free any system resources 
                    
                    qexec.close() ;
                }

                out.println("</table>");
                out.println("</fieldset>");
                out.println("<fieldset>");
                out.println("<p aling=\"center\">Execution Time:"+(System.currentTimeMillis()-time)+"</p>");
                out.println("</fieldset>");
            }
        }catch(Exception e)
        {
            out.println("<fieldset>");
            out.println("Error: <BR>");
            out.println("<textarea name=\"query\" rows=20 cols=80>");
            e.printStackTrace(out);
            out.println("</textarea>");
            out.println("</fieldset>");
        }
        out.println("</form>");
        out.println("</div>");
    }

    
    
}
