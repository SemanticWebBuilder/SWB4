/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.infotec.wb.resources;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class Logout extends GenericResource
{
    
    /** TODO: VER 4.0
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        StringBuffer ret = new StringBuffer("");
        String acc = null != request.getParameter("acc") ? request.getParameter("acc").trim() : "";
        
        if ("logout".equals(acc))
        {
            request.getSession().removeAttribute(WBUserMgr.UserAtt+"-"+paramsRequest.getTopic().getMap().getDbdata().getRepository());
            String sCookNameLog = (String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/CookNameLog");
            WBUserMgr.getInstance().removeUser(paramsRequest.getUser().getSesid(),paramsRequest.getTopic().getMap().getDbdata().getRepository(),request, response);
            paramsRequest.getUser().logout();
            paramsRequest.getUser().setRecUser(DBUser.getInstance(paramsRequest.getTopic().getMap().getDbdata().getRepository()).getNewRecUser());
            ret.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0;URL=" + paramsRequest.getTopic().getUrl() + "\">");
        }
        else if (paramsRequest.getArguments().size() > 0 && paramsRequest.getUser().isLoged())
        {
            ret.append("<a href=\"" + getResourceBase().getUrl(paramsRequest.getTopic()) + "?acc=logout\">" + com.infotec.appfw.util.AFUtils.getLocaleString("locale_wb2_resources", "usrmsg_Logout_getHtml_logout") + "</a>");
        } 
        response.getWriter().print(ret.toString());
    }
     * */
}
