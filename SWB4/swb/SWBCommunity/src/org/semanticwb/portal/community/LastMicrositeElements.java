/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.community;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author victor.lorenzana
 */
public class LastMicrositeElements extends GenericAdmResource
{

    private static Logger log = SWBUtils.getLogger(LastMicrositeElements.class);
    private static final String NL = "\r\n";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        int limit = 3;
        String slimit = this.getResourceBase().getAttribute("limit", "3");
        try
        {
            limit = Integer.parseInt(slimit);
        }
        catch (NumberFormatException nfe)
        {
            log.error(nfe);

        }
        ArrayList<MicroSiteElement> elements = new ArrayList<MicroSiteElement>();
        StringBuilder prefixStatement = new StringBuilder();
        prefixStatement.append(" PREFIX swb: <http://www.semanticwebbuilder.org/swb4/ontology>" + NL);
        prefixStatement.append(" PREFIX swbcomm: <http://www.semanticwebbuilder.org/swb4/community#>" + NL);
        prefixStatement.append(" PREFIX rdf: <" + SemanticVocabulary.RDF_URI + "> " + NL);
        prefixStatement.append(" PREFIX rdfs: <" + SemanticVocabulary.RDFS_URI + "> " + NL);
        prefixStatement.append("SELECT ?x ?date WHERE {?x swb:created ?date . ?x rdf:type swbcomm:MicroSiteElement} ORDER BY ?date LIMIT "+limit);
        QueryExecution qe = paramRequest.getWebPage().getSemanticObject().getModel().sparQLQuery(prefixStatement.toString());
        ResultSet rs = qe.execSelect();
        while (rs.hasNext())
        {
            QuerySolution rb = rs.nextSolution();
            List resultVars = rs.getResultVars();
            for (Object name : resultVars)
            {
                if (rb.get(name.toString()).isResource())
                {
                    Resource res = rb.getResource(name.toString());
                    SemanticObject obj = SemanticObject.createSemanticObject(res);
                    MicroSiteElement microSite = new MicroSiteElement(obj);
                    elements.add(microSite);
                }
            }
        }
        String path = "/swbadmin/jsp/microsite/LastMicrositeElements/LastMicrositeElementsView.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("elements", elements);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }



    }
}
