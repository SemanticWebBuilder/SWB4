/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.security.limiter;

import javax.servlet.http.HttpServletRequest;
import net.fckeditor.requestcycle.UserAction;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBUserAction.
 * 
 * @author serch
 */
public class SWBUserAction implements UserAction
{
    
    /**
     * Checks if is enabled for file upload.
     * 
     * @param hsr the hsr
     * @return true, if is enabled for file upload
     */
    public boolean isEnabledForFileUpload(HttpServletRequest hsr)
    {
        WebSite site = SWBContext.getAdminWebSite();
        User user=SWBPortal.getUserMgr().getUser(hsr, site);
        SWBContext.setSessionUser(user);
        return user.haveAccess(site.getHomePage());

    }

    /**
     * Checks if is enabled for file browsing.
     * 
     * @param hsr the hsr
     * @return true, if is enabled for file browsing
     */
    public boolean isEnabledForFileBrowsing(HttpServletRequest hsr)
    {
        WebSite site = SWBContext.getAdminWebSite();
        User user=SWBPortal.getUserMgr().getUser(hsr, site);
        SWBContext.setSessionUser(user);
        return user.haveAccess(site.getHomePage());
    }

}
