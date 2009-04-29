/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources;

import com.hp.hpl.jena.graph.Node;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeSet;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticModel;



/**
 *
 * @author juan.fernandez
 */
public class SWBSparQL extends GenericResource {

    private static Logger log=SWBUtils.getLogger(SWBSparQL.class);
    static public final String NL = System.getProperty("line.separator") ;

    public static final String PRM_QUERY="query";
    public static final String PRM_ONTTYPE="onttype";
    public static final String PRM_MODELS="models";

    public static final String OT_RDF="rdf";
    public static final String OT_ONTOLOGY="ontology";
    public static final String OT_ONTRDFINF="ontrdfinf";

    private SemanticModel model=null;
    
    /** Creates a new instance of WBADBQuery */
    public SWBSparQL()
    {
    }

    public void createSemanticModel()
    {
        model=null;
        String onttype=getResourceBase().getAttribute(PRM_ONTTYPE);
        String smodels=getResourceBase().getAttribute(PRM_MODELS);
        ArrayList<String> amodels=new ArrayList();
        if(smodels!=null)
        {
            StringTokenizer st=new StringTokenizer(smodels,"|");
            while(st.hasMoreTokens())
            {
                String tk=st.nextToken();
                amodels.add(tk);
            }
        }
        if(amodels.size()==1)
        {
            if(onttype.equals(OT_RDF))
            {
                model=SWBPlatform.getSemanticMgr().getModel(amodels.get(0));
            }
        }else if(amodels.size()>1)
        {

        }


        
    }

    public SemanticModel getSemanticModel()
    {
        return model;
    }

    @Override
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        super.setResourceBase(base);
        createSemanticModel();
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        String query=getResourceBase().getAttribute(PRM_QUERY);
        String onttype=getResourceBase().getAttribute(PRM_ONTTYPE);
        String smodels=getResourceBase().getAttribute(PRM_MODELS);
        ArrayList<String> amodels=new ArrayList();
        if(smodels!=null)
        {
            StringTokenizer st=new StringTokenizer(smodels,"|");
            while(st.hasMoreTokens())
            {
                String tk=st.nextToken();
                amodels.add(tk);
            }
        }

        String act=request.getParameter("act");
        if(act!=null)
        {
            query=request.getParameter(PRM_QUERY);
            getResourceBase().setAttribute(PRM_QUERY, query);
            onttype=request.getParameter(PRM_ONTTYPE);
            getResourceBase().setAttribute(PRM_ONTTYPE, onttype);
            String models[]=request.getParameterValues(PRM_MODELS);
            smodels="";
            amodels=new ArrayList();
            for(int x=0;x<models.length;x++)
            {
                smodels+=models[x];
                amodels.add(models[x]);
                if((x+1)<models.length)smodels+="|";
            }
            getResourceBase().setAttribute(PRM_MODELS, smodels);
            try
            {
                System.out.println("oy:"+onttype+" sm:"+smodels+" q:"+query);
                getResourceBase().updateAttributesToDB();
            }catch(Exception e){log.error(e);}
            createSemanticModel();
        }

        if(onttype==null)onttype="";
        if(query==null)query="";

        out.println("<div class=\"swbform\">");
        out.println("<form dojoType=\"dijit.form.Form\" id=\""+getResourceBase().getId()+"/sparql\" action=\""+paramRequest.getRenderUrl()+"\" method=\"post\" >");
        out.println("<input type=\"hidden\" name=\"act\" value=\"upd\">");

        out.println("<fieldset>");
        //out.println("<table border=\"0\" cellspacing=\"2\" cellpadding=\"0\" >");
        //out.println("<tr><td >");
        out.println("SPARQL Example:");
        //out.println("</td></tr>");
        //out.println("<tr><td >");
        out.println("<PRE >");
        out.println("PREFIX  swb:  &lt;http://www.semanticwebbuilder.org/swb4/ontology#&gt;");
        out.println("PREFIX  rdfs: &lt;http://www.w3.org/2000/01/rdf-schema#&gt;");
        out.println("PREFIX  rdf:  &lt;http://www.w3.org/1999/02/22-rdf-syntax-ns#&gt;");
        out.println("<br>");
        out.println("SELECT ?title ?desc WHERE {?x swb:title ?title. ?x rdf:type swb:WebPage. ?x swb:description ?desc}");
        out.println("</PRE>");
        //out.println("</td></tr>");
        //out.println("<tr><td class=\"tabla\">");
        out.println("Ontology Type:");
        out.println("<br/>");
        out.println("<select name='"+PRM_ONTTYPE+"'>");
        out.println("<option value='rdf' "+(onttype.equals("rdf")?"selected":"")+">RDF (No Inference)</option>");
        out.println("<option value='ontology' "+(onttype.equals("ontology")?"selected":"")+">Ontology</option>");
        out.println("<option value='ontrdfinf' "+(onttype.equals("ontrdfinf")?"selected":"")+">Ontology RDFS Inf.</option>");
        out.println("</select>");
        out.println("<br/>");
        out.println("Models:");
        out.println("<br/>");
        out.println("<select multiple name='"+PRM_MODELS+"'>");
        TreeSet set=new TreeSet();
        Iterator<Entry<String, SemanticModel>> it=SWBPlatform.getSemanticMgr().getModels().iterator();
        while(it.hasNext())
        {
            Entry<String,SemanticModel> ent=it.next();
            set.add(ent.getKey());
        }
        set.add("SWBSchema");
        Iterator <String>it2=set.iterator();
        while(it2.hasNext())
        {
            String key=it2.next();
            String sel="";
            if(amodels.contains(key))sel="selected";
            out.println("<option value='"+key+"' "+sel+">"+key+"</option>");
        }
        //out.println("<option value='remote'>Remote</option>");
        out.println("</select>");
        out.println("<br/>");

        //out.println("Remote URL (SparQL EndPoint):");
        //out.println("<br/>");
        //out.println("<input type=>");


        out.println("SPARQL:");
        out.println("<br/>");
        //out.println("</td></tr>");
        //out.println("<tr><td>");
        out.print("<textarea name=\""+PRM_QUERY+"\" rows=10 cols=80>");
        out.print(query);
        out.println("</textarea>");
        out.println("<br/>");
        //out.println("</td></tr>");
        //out.println("</table>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"submit/btnSend\" >"+paramRequest.getLocaleString("send")+"</button>");
        //out.println("<input type=\"submit\" name=\"submit\" value=\""+paramRequest.getLocaleString("send")+"\">");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");
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
        String queryString=getResourceBase().getAttribute("query");
        long time=System.currentTimeMillis();
        try
        {
            if(queryString!=null && queryString.length()>0)
            {
                out.println("<fieldset>");
                out.println("<table border=0>");

                QueryExecution qexec = getSemanticModel().sparQLQuery(queryString);
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
                            String val=x.toString();
                            if(x.isLiteral())
                            {
                                Node n=x.asNode();
                                val=n.getLiteralLexicalForm();
                                String l=n.getLiteralLanguage();
                                if(l!=null && l.length()>0)val+="{@"+l+"}";
                            }
                            out.println("<td >");
                            out.println(x!=null?val:" - ");
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
    }

    
    
}
