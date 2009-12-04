/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SFBase64;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author serch
 */
public class PasswordManager extends GenericResource
{

    static int SIZE = 16;
    static String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    static SecureRandom generator = new SecureRandom();


    {
        generator.setSeed(System.currentTimeMillis());
    }
    static Logger log = SWBUtils.getLogger(PasswordManager.class);
    private static final String FRM_CHGPWD = "frmchgpwd";
    private static final String FRM_NEWPWD = "frmnewpwd";
    private static final String FRM_LOGREQ = "frmlogreq";
    private static final String FRM_MAILMS = "frmmailms";
    private static final String TXT_chgpwd = "<fieldset><form action=\"{actionurlUPG}\" method=\"post\">\n<label>Cambio password:</label><input type=\"password\" id=\"swb_newPassword\" name=\"swb_newPassword\" /><br />\n<label>Confirmar password:</label><input type=\"password\" id=\"swb_newPassword2\" name=\"swb_newPassword2\" /><br />\n<input type=\"submit\" value=\"Enviar\" /><input type=\"hidden\" name=\"cadcontrol\" value=\"{?cadcontrol}\" /></form></fieldset>\n";
    private static final String TXT_newpwd = "<fieldset><form action=\"{actionurlADD}\" method=\"post\">\n<label>Nuevo password:</label><input type=\"password\" id=\"swb_newPassword\" name=\"swb_newPassword\" /><br />\n<label>Confirmar password:</label><input type=\"password\" id=\"swb_newPassword2\" name=\"swb_newPassword2\" /><br />\n<input type=\"submit\" value=\"Enviar\" /><input type=\"hidden\" name=\"cadcontrol\" value=\"{?cadcontrol}\" /></form></fieldset>\n";
    private static final String TXT_logreq = "<fieldset><form action=\"{actionurlSML}\" method=\"post\">\n<label>Login:</label><input type=\"text\" id=\"swb_login\" name=\"swb_login\" /><br />\n<input type=\"submit\" value=\"Enviar\" /><input type=\"hidden\" name=\"cadcontrol\" value=\"{?cadcontrol}\" /></form></fieldset>\n";
    private static final String TXT_mailms = "Ha solicitado recuperar su contraseña, para continuar acuda a {?url}";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if (null != request.getAttribute("message"))
        {
            String message = (String) request.getAttribute("message");
            response.getWriter().println(message);
        } else if (paramRequest.getUser().isSigned())
        {
            showPasswordChange(request, response, paramRequest);
        } else if (gotValidToken(request))
        {
            showNewPassword(request, response, paramRequest);
        } else
        {
            showLoginRequest(request, response, paramRequest);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        String cadcontrol = (String) request.getAttribute("cadcontrol");
        if (cadcontrol.equals(request.getParameter("cadcontrol")))
        {
            if (response.getAction().equals("UPG"))
            {
                String pwd1 = request.getParameter("swb_newPassword");
                String pwd2 = request.getParameter("swb_newPassword2");
                if (pwd2.equals(pwd1))
                {
                    response.getUser().setPassword(pwd2);
                    response.setRenderParameter("message", "password actualizado");
                } else
                {
                    response.setRenderParameter("message", "passwords no corresponden");
                }
            }
            if (response.getAction().equals("ADD"))
            {
                if (gotValidToken(request))
                {
                    String pwd1 = request.getParameter("swb_newPassword");
                    String pwd2 = request.getParameter("swb_newPassword2");
                    if (pwd2.equals(pwd1))
                    {
                        response.getUser().setPassword(pwd2);
                        response.setRenderParameter("message", "password actualizado");
                    } else
                    {
                        response.setRenderParameter("message", "passwords no corresponden");
                    }
                }
            }
            if (response.getAction().equals("UPG"))
            {
                String frmmailms = getResourceBase().getAttribute(FRM_MAILMS);
                if (null == frmmailms)
                {
                    frmmailms = TXT_mailms;
                }
                String email = response.getUser().getUserRepository().getUserByLogin(request.getParameter("login")).getEmail();
                SWBUtils.EMAIL.sendBGEmail(email, "Recuperar password", replaceTags(frmmailms, request, null, generateToken()));
                //Send Mail
            }
        } else
        {
            response.setRenderParameter("message", "session inv&acute;lida!");
        }

    }

    void showPasswordChange(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        String frmcontent = getResourceBase().getAttribute(FRM_CHGPWD);
        if (null == frmcontent)
        {
            frmcontent = TXT_chgpwd;
        }
        String cadcontrol = getCadControl();
        request.setAttribute("cadcontrol", cadcontrol);
        out.println(replaceTags(frmcontent, request, paramRequest, cadcontrol));

    }

    void showLoginRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        String frmcontent = getResourceBase().getAttribute(FRM_LOGREQ);
        if (null == frmcontent)
        {
            frmcontent = TXT_logreq;
        }
        String cadcontrol = getCadControl();
        request.setAttribute("cadcontrol", cadcontrol);
        out.println(replaceTags(frmcontent, request, paramRequest, cadcontrol));
    }

    void showNewPassword(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        String frmcontent = getResourceBase().getAttribute(FRM_NEWPWD);
        if (null == frmcontent)
        {
            frmcontent = TXT_newpwd;
        }
        String cadcontrol = getCadControl();
        request.setAttribute("cadcontrol", cadcontrol);
        out.println(replaceTags(frmcontent, request, paramRequest, cadcontrol));
    }

    boolean gotValidToken(HttpServletRequest request)
    {
        boolean ret = false;
        return ret;
    }

    String replaceTags(String str, HttpServletRequest request, SWBParamRequest paramRequest, String url)
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
        str = SWBUtils.TEXT.replaceAll(str, "{webpath}", SWBPortal.getContextPath());
        str = SWBUtils.TEXT.replaceAll(str, "{distpath}", SWBPortal.getDistributorPath());
        str = SWBUtils.TEXT.replaceAll(str, "{webworkpath}", SWBPortal.getWebWorkPath());
        str = SWBUtils.TEXT.replaceAll(str, "{workpath}", SWBPortal.getWorkPath());
        str = SWBUtils.TEXT.replaceAll(str, "{websiteid}", paramRequest.getWebPage().getWebSiteId());
        str = SWBUtils.TEXT.replaceAll(str, "{actionurl}", paramRequest.getActionUrl().toString());
        str = SWBUtils.TEXT.replaceAll(str, "{actionurlUPG}", paramRequest.getActionUrl().setAction("UPG").toString());
        str = SWBUtils.TEXT.replaceAll(str, "{actionurlADD}", paramRequest.getActionUrl().setAction("ADD").toString());
        str = SWBUtils.TEXT.replaceAll(str, "{actionurlSML}", paramRequest.getActionUrl().setAction("SML").toString());
        str = SWBUtils.TEXT.replaceAll(str, "{?url}", generateToken());
        //System.out.println(str);
        return str;
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        String frmchgpwd = getResourceBase().getAttribute(FRM_CHGPWD);
        String frmnewpwd = getResourceBase().getAttribute(FRM_NEWPWD);
        String frmlogreq = getResourceBase().getAttribute(FRM_LOGREQ);
        String frmmailms = getResourceBase().getAttribute(FRM_MAILMS);
        if (null == frmchgpwd)
        {
            frmchgpwd = TXT_chgpwd;
        }
        if (null == frmnewpwd)
        {
            frmnewpwd = TXT_newpwd;
        }
        if (null == frmlogreq)
        {
            frmlogreq = TXT_logreq;
        }
        if (null == frmmailms)
        {
            frmmailms = TXT_mailms;
        }

        String act = request.getParameter("act");
        if (act != null)
        {
            frmchgpwd = request.getParameter(FRM_CHGPWD);
            getResourceBase().setAttribute(FRM_CHGPWD, frmchgpwd);
            frmnewpwd = request.getParameter(FRM_NEWPWD);
            getResourceBase().setAttribute(FRM_NEWPWD, frmnewpwd);
            frmlogreq = request.getParameter(FRM_LOGREQ);
            getResourceBase().setAttribute(FRM_LOGREQ, frmlogreq);
            frmmailms = request.getParameter(FRM_MAILMS);
            getResourceBase().setAttribute(FRM_MAILMS, frmmailms);
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
        out.println("Forma de Solicitud password:");
        out.println("<br/>");
        out.print("<textarea name=\"" + FRM_CHGPWD + "\" rows=10 cols=80>");
        out.print(frmchgpwd);
        out.println("</textarea>");
        out.println("<br/>");
        out.println("Forma de Solicitud nuevo password:");
        out.println("<br/>");
        out.print("<textarea name=\"" + FRM_NEWPWD + "\" rows=10 cols=80>");
        out.print(frmnewpwd);
        out.println("</textarea>");
        out.println("<br/>");
        out.println("Forma de Solicitud login:");
        out.println("<br/>");
        out.print("<textarea name=\"" + FRM_LOGREQ + "\" rows=10 cols=80>");
        out.print(frmlogreq);
        out.println("</textarea>");
        out.println("<br/>");
        out.println("Texto Correo:");
        out.println("<br/>");
        out.print("<textarea name=\"" + FRM_MAILMS + "\" rows=10 cols=80>");
        out.print(frmmailms);
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

        out.println("       &nbsp;&nbsp;{webpath}<BR>");
        out.println("       &nbsp;&nbsp;{distpath}<BR>");
        out.println("       &nbsp;&nbsp;{webworkpath}<BR>");
        out.println("       &nbsp;&nbsp;{websiteid}<BR>");
        out.println("       &nbsp;&nbsp;{workpath}<BR>");
        out.println("       &nbsp;&nbsp;{actionurl}<BR>");
        out.println("       &nbsp;&nbsp;{actionurlUPG}<BR>");
        out.println("       &nbsp;&nbsp;{actionurlADD}<BR>");
        out.println("       &nbsp;&nbsp;{actionurlSML}<BR>");

        out.println("       <BR>&nbsp;&nbsp;<b>Note:</b> XXXXX=Text<BR><BR>");
        out.println("	</font>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"submit/btnSend\" >Enviar</button>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");

    }

    String getCadControl()
    {
        StringBuilder sb = new StringBuilder(SIZE);
        for (int i = 0; i < SIZE; i++)
        {
            sb.append(letras.charAt(generator.nextInt(letras.length())));
        }
        return sb.toString();
    }

    String generateToken()
    {
        return "TOKEN";
    }
}
