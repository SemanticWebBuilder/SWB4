/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources.community;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author Ana
 */
public class CommUtils extends GenericResource{

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        SWBResourceURL url=paramRequest.getActionUrl();
        PrintWriter out=response.getWriter();
        out.println("<h4>Mis Utiler&iacute;as</h4>");
        out.println("  <ul>");
        GenericIterator <WebPage> gitChilds=paramRequest.getTopic().listChilds();
        while(gitChilds.hasNext()){
            WebPage child=gitChilds.next();
            url.setParameter("topic2Remove", child.getId());
            out.println("<li><a onclick=\"return confirm('Esta seguro de eliminar esta utilería?');\" href=\""+url.toString()+"\"><img src=\""+SWBPlatform.getContextPath()+"/swbadmin/icons/TRASH.png\" border=\"0\" align=\"absmiddle\" alt=\"Eliminar Utileria\" /></a>&nbsp;&nbsp;<a href=\""+child.getUrl()+"\">"+child.getTitle()+"</a></li>");
        }
        out.println("  </ul>");
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        WebSite site=response.getTopic().getWebSite();
        if(request.getParameter("topic2Remove")!=null){
            site.removeWebPage(request.getParameter("topic2Remove"));
        }
    }
}
