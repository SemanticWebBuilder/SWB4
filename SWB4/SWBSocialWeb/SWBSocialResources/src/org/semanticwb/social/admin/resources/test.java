/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.DisplayObject;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Facebook;

/**
 *
 * @author jorge.jimenez
 */
public class test extends GenericResource{
    
    private static Logger log = SWBUtils.getLogger(test.class);
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        /*
        if (request.getParameter("doView") == null) {
            doEdit(request, response, paramRequest);
            return;
        }
        final String myPath = SWBPlatform.getContextPath() + "/mapa.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        if (dis != null) {
            try {
                request.setAttribute("paramRequest", paramRequest);
                request.setAttribute("suri", request.getParameter("suri"));
                dis.include(request, response);
            } catch (Exception ex) {
                log.error(ex);
            }
        }
*/
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        //out.println("<iframe width=\"100%\" height=\"100%\" src=\"" + paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW).setParameter("doView", "1").setParameter("suri", request.getParameter("suri")) + "\"></iframe> ");
    }
    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        WebSite wsite=paramRequest.getWebPage().getWebSite();
        /*
        WebPage homePage= wsite.getHomePage();
        
        WebPage newWebPage=wsite.createWebPage("x1_"+new Date().toString());
        newWebPage.setParent(homePage);
        
        System.out.println("homePage a reload:"+homePage);
        
        out.println("<script type=\"text/javascript\">");
        out.println("   parent.updateTreeNodeByURI('"+homePage.getURI()+"') ");
        out.println("   parent.reloadTreeNodeByURI('"+homePage.getURI()+"') ");        
        out.println("</script>");
        * */
        //WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        Facebook face=Facebook.ClassMgr.createFacebook(wsite);
        face.addFanPage(face);
        face.setTitle("x1_"+new Date().toString());
        face.setIsFanPage(true);
        //System.out.println("IconClassJ:"+((DisplayObject)face.sclass.getDisplayObject().createGenericInstance()).getIconClass()+", de cta:"+face);
        //String iconClassName=((DisplayObject)face.sclass.getDisplayObject().createGenericInstance()).getIconClass();
        //((DisplayObject)face.sclass.getDisplayObject().createGenericInstance()).setIconClass(iconClassName+"_X");
        
    }
}
