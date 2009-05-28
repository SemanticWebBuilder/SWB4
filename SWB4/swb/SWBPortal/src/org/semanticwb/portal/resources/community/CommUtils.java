/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources.community;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author Ana
 */
public class CommUtils extends GenericResource{

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("<h4>Mis Utiler&iacute;as</h4>");
        out.println("  <ul>");
        GenericIterator <WebPage> gitChilds=paramRequest.getTopic().listChilds();
        while(gitChilds.hasNext()){
            WebPage child=gitChilds.next();
            out.println("    <li><a href=\"\">"+child.getTitle()+"</a></li>");
        }
        out.println("  </ul>");
    }
}
