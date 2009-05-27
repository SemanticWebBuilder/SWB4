/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources.community;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author victor.lorenzana
 */
public class CreateComunityLink extends GenericResource{


    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();

        WebPage comunityContainer=WebPage.getWebPage(CommunityConfiguration.COMMUNITY_CONTAINER_ID, paramRequest.getTopic().getWebSite());
        out.print("<a href=\""+comunityContainer.getUrl()+"?selecttype=t&swbtp="+ paramRequest.getTopic().getId() +"\" class=\"categoria\"><span class=\"listadocompleto\">Crear una comunidad</span></a>");
    }
}
