/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author serch
 */
public class Login extends GenericAdmResource
{
    String path = SWBPlatform.getContextPath() + "swbadmin/xsl/Login/";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        String url = SWBPlatform.getContextPath() + "/login/" + paramsRequest.getTopic().getWebSiteId() + "/" + paramsRequest.getTopic().getId();
        String query = request.getQueryString();
        PrintWriter out = response.getWriter();
        if (!paramsRequest.getUser().isSigned())
        {
            out.println("<form action=\""+url+"?"+query+"\" method=\"post\">");
            out.println("<fieldset><label>Usuario:</label><input type=\"text\" id=\"wb_username\" name=\"wb_username\"><br />");
            out.println("<label>Contrase&ntilde;a:</label><input type=\"password\" id=\"wb_password\" name=\"wb_password\"><br />");
            out.println("<input type=\"submit\" value=\"Enviar\"></fieldset></form>");
        } else
        out.println("<a href=\""+url+"?wb_logout=true\" >Louout</a>");

    }
    
}
