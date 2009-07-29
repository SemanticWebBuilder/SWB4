/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author victor.lorenzana
 */
public class SWBAAbout extends GenericAdmResource{


    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, java.io.IOException
    {
        PrintWriter out=response.getWriter();
        out.println("<BR><BR><BR><BR><center>");
        out.println("<table width=\"500\" height=\"300\" border=\"0\" background=\""+SWBPlatform.getContextPath()+"/swbadmin/images/splash.jpg\" >");
        out.println("  <tr>");
        out.println("    <td align=\"center\" valign=\"top\" height=\"170\" >&nbsp;</td>");
        out.println("  </tr>");
        out.println("  <tr>");
        out.println("    <td align=\"center\" valign=\"top\" >");
        out.println("	<font color=\"#000000\" size=\"4\" face=\"Arial, Helvetica, sans-serif\"><B>Version: "+SWBPlatform.getVersion()+"</B></font>");
        out.println("     </td>");
        out.println("  </tr>");
        out.println("</table></center>");

    }
}
