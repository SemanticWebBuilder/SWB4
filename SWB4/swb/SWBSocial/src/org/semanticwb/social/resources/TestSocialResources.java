/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.resources;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class TestSocialResources extends GenericResource{

     private static Logger log = SWBUtils.getLogger(TestSocialResources.class);
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path = SWBPortal.getWebWorkPath() +"/models/"+paramRequest.getWebPage().getWebSiteId()+"/jsp/TestSocialResource.jsp" ;

        RequestDispatcher rd = request.getRequestDispatcher(path);
        try{
            request.setAttribute("paramRequest",paramRequest);
            rd.include(request, response);
        }catch(Exception e)
        {
            log.error(e);
        }
    }

}
