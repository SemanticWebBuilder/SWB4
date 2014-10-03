/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.infotecadmin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Institution;
import org.semanticwb.social.LicenseType;
import org.semanticwb.social.SocialSite;

/**
 *
 * @author jorge.jimenez
 */
public class SocialSiteAdm extends GenericResource {
    
    private static Logger log = SWBUtils.getLogger(SocialSiteAdm.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        out.println("<script type=\"javascript\">");
        if (request.getParameter("statusMsg") != null) {
            out.println("   showStatus('" + request.getParameter("statusMsg") + "');");
        }
        out.println("</script>");
        
        
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + SWBContext.getAdminWebSite().getId() + "/jsp/admin/socialSiteAdm.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        if (dis != null) {
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception ex) {
                log.error(ex);
            }
        }
    }
    
     @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        try {
            String action=response.getAction();
            String lang=response.getUser().getLanguage();
            if(request.getParameter("socialSite")!=null && request.getParameter("licenseType")!=null && request.getParameter("institution")!=null && action.equals(SWBResourceURL.Action_EDIT))
            {
                System.out.println("License en Clase:"+request.getParameter("licenseType"));
                WebSite wsite=response.getWebPage().getWebSite(); 
                SocialSite socialSite=SocialSite.ClassMgr.getSocialSite(request.getParameter("socialSite"));
                
                LicenseType licenseType=LicenseType.ClassMgr.getLicenseType(request.getParameter("licenseType"), wsite);
                socialSite.setLicenseType(licenseType);
                
                Institution institution=Institution.ClassMgr.getInstitution(request.getParameter("institution"), wsite);
                socialSite.setInstitution(institution);
                
                response.setRenderParameter("statusMsg", "Marca "+socialSite.getDisplayTitle(lang)+" Actualizada");                
            }
        }catch(Exception e){log.error(e);}        
     }
        
    
}
