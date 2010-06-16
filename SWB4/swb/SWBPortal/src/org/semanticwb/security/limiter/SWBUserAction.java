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

/**
 *
 * @author serch
 */
public class SWBUserAction implements UserAction {

    public boolean isEnabledForFileUpload(HttpServletRequest hsr)
    {
        WebSite site = SWBContext.getAdminWebSite();
        User user=SWBPortal.getUserMgr().getUser(hsr, site);
        SWBContext.setSessionUser(user);
        return user.haveAccess(site.getHomePage());

    }

    public boolean isEnabledForFileBrowsing(HttpServletRequest hsr)
    {
        WebSite site = SWBContext.getAdminWebSite();
        User user=SWBPortal.getUserMgr().getUser(hsr, site);
        SWBContext.setSessionUser(user);
        return user.haveAccess(site.getHomePage());
    }

}
