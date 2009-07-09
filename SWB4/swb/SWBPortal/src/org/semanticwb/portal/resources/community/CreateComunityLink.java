/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources.community;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author victor.lorenzana
 */
public class CreateComunityLink extends GenericAdmResource{


    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Resource base=paramRequest.getResourceBase();
        PrintWriter out=response.getWriter();
        WebPage currentWebPage=paramRequest.getWebPage();
        if (currentWebPage.getParent() != null && currentWebPage.getParent().getParent() != null && currentWebPage.getParent().getParent().getParent() != null && currentWebPage.getParent().getParent().getParent().getId().equals(CreateCommunity.TEMAS_TOPIC_ID))
        {
            String params="?modeswbcreatecommunity=true" +
            "&swbtp="+ paramRequest.getWebPage().getId();
            if(base.getAttribute("selecttype")!=null) {
                params+="&selecttype=" + base.getAttribute("selecttype");
            }
            WebPage comunityContainer=WebPage.getWebPage(CommunityConfiguration.COMMUNITY_CONTAINER_ID, paramRequest.getWebPage().getWebSite());
            out.print("<a href=\""+comunityContainer.getUrl() + params +"\" class=\"categoria\"><span class=\"listadocompleto\">Crear una comunidad</span></a>");
        }
    }
}
