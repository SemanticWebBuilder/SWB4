/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Language;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Facebook;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialWebPage;
import org.semanticwb.social.Twitter;

/**
 *
 * @author jorge.jimenez
 */
public class SocialWebPageToSocialNets extends GenericResource {

    private static Logger log = SWBUtils.getLogger(SocialWebPageToSocialNets.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/post/socialWebPageToSocialNets.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        PrintWriter out = response.getWriter();
        if (request.getParameter("statusMsg") != null) {
            out.println("<script type=\"text/javascript\">");
            out.println("   showStatus('" + request.getParameter("statusMsg") + "');");
            if (request.getParameter("reloadTab") != null) {
                out.println(" reloadTab('" + request.getParameter("reloadTab") + "');");//so
            }
            out.println("</script>");

        }

        if (dis != null) {
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception ex) {
                log.error(ex);
                ex.printStackTrace(System.out);
            }
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("preview")) {
            doPreview(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    private void doPreview(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws IOException {
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/post/Preview.jsp");
        request.setAttribute("suri", request.getParameter("suri"));
        request.setAttribute("suriSite", request.getParameter("suriSite"));
        request.setAttribute("paramRequest", paramRequest);


        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error  " + ex.getMessage());
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {

        if (response.getAction().equals("uploadPhoto")) {
            String idSWB = URLDecoder.decode(request.getParameter("swb"));
            SocialWebPage swb = (SocialWebPage) SemanticObject.createSemanticObject(idSWB).createGenericInstance();
            SocialNetwork socialNetwork = null;
            Iterator i = swb.listSocialNetses();

            String suri = request.getParameter("suri");
            Language l = (Language) SemanticObject.createSemanticObject(suri).createGenericInstance();

            WebPage ws = (WebPage) swb;
            StringBuilder address = new StringBuilder(128);
            address.append("http://").append(request.getServerName());
            if (request.getServerPort() != 80) {
                address.append(":").append(request.getServerPort());
            }
            address.append(SWBPortal.getWebWorkPath()).append("/").append("models").append("/").append(swb.getWebSite().getId()).append("/").append("WebPage").append("/").append(swb.getId()).append("/").append(swb.getSocialwpPhoto());

            while (i.hasNext()) {

                socialNetwork = (SocialNetwork) i.next();
                SemanticObject semanticObject = SemanticObject.createSemanticObject(socialNetwork.getURI());

                if (socialNetwork instanceof Facebook) {
                    Facebook facebook = (Facebook) semanticObject.createGenericInstance();

                    facebook.postPhotoSocialNets(facebook, swb, l, address);
                } else if (socialNetwork instanceof Twitter) {
                    Twitter twitter = (Twitter) semanticObject.createGenericInstance();
                    twitter.postPhotoSocialNets(twitter, swb, l, address);

                }
            }
            response.setMode(SWBResourceURL.Mode_VIEW);
            response.setRenderParameter("statusMsg", "Publicado");

        }
    }
}
