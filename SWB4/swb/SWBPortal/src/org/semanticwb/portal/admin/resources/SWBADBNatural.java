/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.nlp.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.*;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;

/**
 *
 * @author hasdai
 */
public class SWBADBNatural extends GenericResource {

    /** Creates a new instance of SWBADBNatural */
    public SWBADBNatural()
    {
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String _query = request.getParameter("queryText");
        String lang = paramRequest.getUser().getLanguage();

        if (_query == null) {
            _query="";
        } else {
            _query=_query.trim();
        }

        out.println("<script src=\""+SWBPlatform.getContextPath()+"/swbadmin/js/acTextArea.js\"></script>");
        out.println("<script type=\"text/javascript\">" +
                "dojo.require(\"dijit.form.Textarea\");" +
                "dojo.require(\"dijit.form.Form\");" +
                "dojo.require(\"dijit.form.Button\");" +
                "</script>");
        out.println("<script type=\"text/javascript\">" +
                "dojo.addOnLoad(function () {" +
                    "dojo.connect(dojo.byId('queryText'), 'onkeydown', 'queryOnKeyDown');" +
                    "dojo.connect(dojo.byId('queryText'), 'onkeydown', 'queryOnKeyUp');" +
                    "dojo.connect(dojo.byId('queryText'), 'onblur', function () {" +
                        "clearSuggestions();" +
                    "});" +
                "});" + "var lang=\"" + lang + "\"" +
            "</script>");

        out.println("<div class=\"swbform\">");
        out.println("<form dojoType=\"dijit.form.Form\" id=\""+getResourceBase().getId()+"/natsparql\" action=\""+paramRequest.getRenderUrl()+"\" method=\"post\" onsubmit=\"submitForm('"+getResourceBase().getId()+"/natsparql'); return false;\">");
        out.println("<fieldset>");
        out.println("<table border=\"0\" cellspacing=\"2\" cellpadding=\"0\" >");
        out.println("<tr><td >");
        out.println("Natural Language Query Example:");
        out.println("</td></tr>");
        out.println("<tr><td ><PRE >");
        out.println("10 User con Active=true, Primer Apellido ordenar (Primer Apellido)");
        out.println("*Type a word and use CTRL + SPACE to show suggestions, ESC to hide suggestions.");
        out.println("</PRE></td></tr>");
        out.println("<tr><td class=\"tabla\">");
        out.println("Natural Language Query:");
        out.println("</td></tr>");
        out.println("<tr><td>");
        out.print("<textarea id=\"queryText\" name=\"queryText\" rows=5 cols=70>");
        out.print(_query);
        out.println("</textarea><div id=\"results\"></div>");
        out.println("</td></tr>");
        out.println("</table>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"submit/btnSend\" >"+paramRequest.getLocaleString("send")+"</button>");
        out.println("</fieldset>");

        long time=System.currentTimeMillis();
        try
        {
            if (_query.length() > 0)
            {
                out.println("<fieldset>");
                out.println("<table border=0>");

                Model model = SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
                List prefixes = new ArrayList();
                List namespaces = new ArrayList();

                //Create a new word dictionary instance
                Lexicon dict = new Lexicon();
                Iterator<SemanticClass> its = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();

                //Traverse the ontology model to fill the dictionary
                while(its.hasNext()) {
                    SemanticClass sc = its.next();

                    if(!prefixes.contains(sc.getPrefix()))
                        prefixes.add(sc.getPrefix());
                    if(!namespaces.contains(sc.getOntClass().getNameSpace()))
                        namespaces.add(sc.getOntClass().getNameSpace());

                    dict.addWord(new Word(sc.getDisplayName(lang),
                            new WordTag("OBJ", sc.getPrefix() + ":" + sc.getName())));

                    Iterator<SemanticProperty> ip = sc.listProperties();

                    while(ip.hasNext()) {
                        SemanticProperty prop = ip.next();

                        if(!prefixes.contains(prop.getPrefix()))
                            prefixes.add(prop.getPrefix());
                        if(!namespaces.contains(prop.getRDFProperty().getNameSpace()))
                            namespaces.add(prop.getRDFProperty().getNameSpace());

                        dict.addWord(new Word(prop.getDisplayName(lang),
                                new WordTag("PRO", prop.getPropId())));
                    }
                }

                //Get the SPARQL query prefixes
                String prex = "";
                prex += "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n";
                prex += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
                for(int i = 0; i < prefixes.size(); i++) {
                    prex = prex + "PREFIX " + prefixes.get(i) + ": " + "<" + namespaces.get(i) + ">\n";
                }

                //Translate the Natural Language Sentence to SPARQL
                Translator tr = new Translator(dict);
                String queryString = prex + tr.translateSentence(_query);

                //out.println("<textarea cols=80>" + queryString + "</textarea>");
                Query query = QueryFactory.create(queryString);
                query.serialize(); //new IndentedWriter(response.getOutputStream(),true)) ;
                // Create a single execution of this query, apply to a model
                // which is wrapped up as a Dataset
                QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
                // Or QueryExecutionFactory.create(queryString, model) ;
                try {
                    // Assumption: it's a SELECT query.
                    ResultSet rs = qexec.execSelect() ;
                    int col = rs.getResultVars().size();
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
            out.println("<textarea name=\"queryText\" rows=20 cols=80>");
            e.printStackTrace(out);
            out.println("</textarea>");
            out.println("</fieldset>");
        }
        out.println("</form>");
        out.println("</div>");
    }
}