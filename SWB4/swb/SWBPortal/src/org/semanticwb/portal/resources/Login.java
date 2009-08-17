/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.base.util.SFBase64;

/**
 *
 * @author serch
 */
public class Login extends GenericAdmResource
{

    static Logger log = SWBUtils.getLogger(Login.class);
    private static final String FRM_LOGIN = "frmlogin";
    private static final String FRM_LOGOUT = "frmlogout";
    private static final String TXT_login = "<fieldset><form action=\"{?loginurl}\" method=\"post\">\n<label>Usuario:</label><input type=\"text\" id=\"wb_username\" name=\"wb_username\" /><br />\n<label>Contrase&ntilde;a:</label><input type=\"password\" id=\"wb_password\" name=\"wb_password\" /><br />\n<input type=\"submit\" value=\"Enviar\" /></form></fieldset>\n";
    private static final String TXT_logout = "<a href=\"{?logouturl}?wb_logout=true\" >Logout</a>";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        String frmlogin = getResourceBase().getAttribute(FRM_LOGIN);
        String frmlogout = getResourceBase().getAttribute(FRM_LOGOUT);
        if (null == frmlogin)
        {
            frmlogin = TXT_login;
        }
        if (null == frmlogout)
        {
            frmlogout = TXT_logout;
        }

        String url = SWBPlatform.getContextPath() + "/login/" + paramsRequest.getWebPage().getWebSiteId() + "/" + paramsRequest.getWebPage().getId();
        if (!paramsRequest.getUser().isSigned())
        {
            out.println(replaceTags(getResourceBase().getAttribute(FRM_LOGIN), request, paramsRequest, url));
//                out.println("<fieldset><form action=\"" + url + "\" method=\"post\">");
//                out.println("<label>Usuario:</label><input type=\"text\" id=\"wb_username\" name=\"wb_username\" /><br />");
//                out.println("<label>Contrase&ntilde;a:</label><input type=\"password\" id=\"wb_password\" name=\"wb_password\" /><br />");
//                out.println("<input type=\"submit\" value=\"Enviar\" /></form></fieldset>");
        } else
        {

            out.println(replaceTags(getResourceBase().getAttribute(FRM_LOGOUT), request, paramsRequest, url + "?wb_logout=true"));
        //out.println("<a href=\"" + url + "?wb_logout=true\" >Logout</a>");
        }

    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        String frmlogin = getResourceBase().getAttribute(FRM_LOGIN);
        String frmlogout = getResourceBase().getAttribute(FRM_LOGOUT);
        if (null == frmlogin)
        {
            frmlogin = TXT_login;
        }
        if (null == frmlogout)
        {
            frmlogout = TXT_logout;
        }
        String act = request.getParameter("act");
        if (act != null)
        {
            frmlogin = request.getParameter(FRM_LOGIN);
            getResourceBase().setAttribute(FRM_LOGIN, frmlogin);
            frmlogout = request.getParameter(FRM_LOGOUT);
            getResourceBase().setAttribute(FRM_LOGOUT, frmlogout);
            try
            {
                getResourceBase().updateAttributesToDB();
            } catch (Exception e)
            {
                log.error(e);
            }
        }

        out.println("<script type=\"text/javascript\">");
        out.println("  dojo.require(\"dijit.form.Form\");");
        out.println("  dojo.require(\"dijit.form.Button\");");
        out.println("</script>");

        out.println("<div class=\"swbform\">");
        out.println("<form dojoType=\"dijit.form.Form\" id=\"" + getResourceBase().getId() + "/sparql\" action=\"" + paramsRequest.getRenderUrl() + "\" method=\"post\" >");
        out.println("<input type=\"hidden\" name=\"act\" value=\"upd\">");

        out.println("<fieldset>");
        out.println("<legend>Configuraci&oacute;n Despliegue</legend>");
        out.println("<br/>");
        out.println("Forma de autenticaci&oacute;n:");
        out.println("<br/>");
        out.print("<textarea name=\"" + FRM_LOGIN + "\" rows=10 cols=80>");
        out.print(frmlogin);
        out.println("</textarea>");
        out.println("<br/>");
        out.println("Pie:");
        out.println("<br/>");
        out.print("<textarea name=\"" + FRM_LOGOUT + "\" rows=10 cols=80>");
        out.print(frmlogout);
        out.println("</textarea>");
        out.println("<br/>");
        out.println("<font style=\"color: #428AD4; font-family: Verdana; font-size: 10px;\">");
        out.println("		<b>Tags:</b><br/>");
        out.println("       &nbsp;&nbsp;{?XXXXXX} Para las formas.<BR>");
        out.println("       &nbsp;&nbsp;{?loginurl}<BR>");
        out.println("       &nbsp;&nbsp;{?logouturl}<BR>");
        out.println("       &nbsp;&nbsp;{user.login}<BR>");
        out.println("       &nbsp;&nbsp;{user.email}<BR>");
        out.println("       &nbsp;&nbsp;{user.language}<BR>");
        out.println("       &nbsp;&nbsp;{getEnv(\"XXXXX\")}<BR>");
        out.println("       &nbsp;&nbsp;{request.getParameter(\"XXXXX\")}<BR>");
        out.println("       &nbsp;&nbsp;{session.getAttribute(\"XXXXX\")}<BR>");
        out.println("       &nbsp;&nbsp;{encodeB64(\"XXXXX\")}<BR>");
        out.println("       <BR>&nbsp;&nbsp;<b>Note:</b> XXXXX=Text<BR><BR>");
        out.println("	</font>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"submit/btnSend\" >Enviar</button>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");

    }

    private String replaceTags(String str, HttpServletRequest request, SWBParamRequest paramRequest, String url)
    {
        //System.out.print("\nstr:"+str+"-->");
        if (str == null || str.trim().length() == 0)
        {
            return null;
        }
        str = str.trim();
        //TODO: codificar cualquier atributo o texto
        Iterator it = SWBUtils.TEXT.findInterStr(str, "{encodeB64(\"", "\")}");
        while (it.hasNext())
        {
            String s = (String) it.next();
            str = SWBUtils.TEXT.replaceAll(str, "{encodeB64(\"" + s + "\")}", SFBase64.encodeString(replaceTags(s, request, paramRequest, url)));
        }

        it = SWBUtils.TEXT.findInterStr(str, "{request.getParameter(\"", "\")}");
        while (it.hasNext())
        {
            String s = (String) it.next();
            str = SWBUtils.TEXT.replaceAll(str, "{request.getParameter(\"" + s + "\")}", request.getParameter(replaceTags(s, request, paramRequest, url)));
        }

        it = SWBUtils.TEXT.findInterStr(str, "{session.getAttribute(\"", "\")}");
        while (it.hasNext())
        {
            String s = (String) it.next();
            str = SWBUtils.TEXT.replaceAll(str, "{session.getAttribute(\"" + s + "\")}", (String) request.getSession().getAttribute(replaceTags(s, request, paramRequest, url)));
        }

        it = SWBUtils.TEXT.findInterStr(str, "{getEnv(\"", "\")}");
        while (it.hasNext())
        {
            String s = (String) it.next();
            str = SWBUtils.TEXT.replaceAll(str, "{getEnv(\"" + s + "\")}", SWBPlatform.getEnv(replaceTags(s, request, paramRequest, url)));
        }

        it = SWBUtils.TEXT.findInterStr(str, "{?", "}");
        while (it.hasNext())
        {
            String s = (String) it.next();
            str = SWBUtils.TEXT.replaceAll(str, "{?" + s + "}", url);
        }

        str = SWBUtils.TEXT.replaceAll(str, "{rows.number}", request.getAttribute("rowsnum") != null ? (String) request.getAttribute("rowsnum") : "N/A");
        str = SWBUtils.TEXT.replaceAll(str, "{exec.time}", (String) request.getAttribute("extime"));
        str = SWBUtils.TEXT.replaceAll(str, "{user.login}", paramRequest.getUser().getLogin());
        str = SWBUtils.TEXT.replaceAll(str, "{user.email}", paramRequest.getUser().getEmail());
        str = SWBUtils.TEXT.replaceAll(str, "{user.language}", paramRequest.getUser().getLanguage());
        //System.out.println(str);
        return str;
    }
}
