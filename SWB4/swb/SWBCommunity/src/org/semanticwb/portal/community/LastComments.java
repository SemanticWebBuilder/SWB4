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
package org.semanticwb.portal.community;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Resource;
import java.io.IOException;
import java.util.ArrayList;
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
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class LastComments extends GenericAdmResource
{

    private static Logger log = SWBUtils.getLogger(LastNews.class);
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
        ArrayList<Comment> comments = new ArrayList<Comment>();
        ArrayList<SemanticObject> owners = new ArrayList<SemanticObject>();
        StringBuilder prefixStatement = new StringBuilder();
        prefixStatement.append(" PREFIX swb: <http://www.semanticwebbuilder.org/swb4/ontology#>" + NL);
        prefixStatement.append(" PREFIX swbcomm: <http://www.semanticwebbuilder.org/swb4/community#>" + NL);
        prefixStatement.append(" PREFIX rdf: <" + SemanticVocabulary.RDF_URI + "> " + NL);
        prefixStatement.append(" PREFIX rdfs: <" + SemanticVocabulary.RDFS_URI + "> " + NL);
        prefixStatement.append("SELECT ?element ?comment WHERE {" + NL +
                " ?comment a swbcomm:Comment. " + NL +
                " ?element swbcomm:hasComment ?comment." + NL +
                " ?comment swb:created ?date" + NL +
                "} ORDER BY DESC(?date) LIMIT " + limit + NL);

        QueryExecution qe = paramRequest.getWebPage().getSemanticObject().getModel().sparQLQuery(prefixStatement.toString());
        ResultSet rs = qe.execSelect();
        while (rs.hasNext())
        {
            QuerySolution rb = rs.nextSolution();
            if (rb.get("comment") != null && rb.get("comment").isResource())
            {
                Resource res = rb.getResource("comment");
                SemanticObject obj = SemanticObject.createSemanticObject(res.getURI());
                Comment element = (Comment) obj.createGenericInstance();
                comments.add(element);

                res = rb.getResource("element");
                obj = SemanticObject.createSemanticObject(res.getURI());
                owners.add(obj);
            }
        }
        String path = "/swbadmin/jsp/microsite/LastComments/LastComments.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("comments", comments);
            request.setAttribute("owners", owners);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }
}
