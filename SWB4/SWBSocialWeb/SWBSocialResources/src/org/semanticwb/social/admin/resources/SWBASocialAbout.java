/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.util.SWBSocialUtil;

/**
 *
 * @author jorge.jimenez
 */
public class SWBASocialAbout extends GenericResource{


    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, java.io.IOException
    {
        PrintWriter out=response.getWriter();
        /*
        out.println("<BR><BR><BR><BR><center>");
        out.println("<table width=\"500\" height=\"300\" border=\"0\" background=\""+SWBPlatform.getContextPath()+"/swbadmin/images/splash.jpg\" >");
        out.println("  <tr height=\"220\">");
        out.println("    <td height=\"220\">&nbsp;</td>");
        out.println("  </tr>");
        out.println("  <tr height=\"80\">");
        out.println("    <td height=\"80\" align=\"center\" valign=\"top\" >");
        out.println("	   <font color=\"#000000\" size=\"3\" face=\"Arial, Helvetica, sans-serif\"><B>Version: "+SWBPlatform.getVersion()+"</B></font>");
        out.println("    </td>");
        out.println("  </tr>");
        out.println("  <tr height=\"80\">");
        out.println("    <td height=\"80\" align=\"center\" valign=\"top\" >");
        out.println("	   <font color=\"#000000\" size=\"3\" face=\"Arial, Helvetica, sans-serif\"><B>Version: "+SWBSocialUtil.getEnv("versionNo", "1.0") +"</B></font>");
        out.println("    </td>");
        out.println("  </tr>");
        out.println("</table></center>");
        * */
        out.println("<div id=\"version\">");
        out.println("<p class=\"versionmark\">SWB Social</p>");
        out.println("<p class=\"versionsocial\">Versión "+SWBSocialUtil.getEnv("versionNo", "1.0")+"</p>");
        out.println("<p class=\"versionmark\">SemanticWebBuilder</p>");
        out.println("<p class=\"versionswb\">Versión "+SWBPlatform.getVersion()+"/p>");
        out.println("<p class=\"versioncopy\">SemanticWebBuilder y SWB Social son marcas registradas</p>");
        out.println("</div>");


    }
}