/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */
package com.infotec.wb.resources;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.Templates;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBParamRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * ChangePassword.java
 * 
 * @author Sergio T�llez
 * @version 1.0
 * 
 * Recurso de Cambio de contrase�a.
 */
public class ChangePassword extends GenericAdmResource 
{

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        String alert = new String();
        User user = paramRequest.getUser();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Templates templates = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(getResourceBase().getWorkPath() + "/" + getResourceBase().getAttribute("template")));
            Document document = SWBUtils.XML.getNewDocument();
            Element euser = document.createElement("USER");
            document.appendChild(euser);
            Element userattr = document.createElement("userattr");
            /*if (!user.isRegistered() || !user.isLoged()) { //TODO:Se va ha defirnir en esta ver. 4 como saber si el usuario esta registrado y logeado
            userattr.setAttribute("msgErrLogin", paramsRequest.getLocaleString("msgErrLogin"));
            } else {
             **/
            if (request.getParameter("_msg") != null) {
                alert = "<script language=\"JavaScript\">\n" +
                        "   alert('" + request.getParameter("_msg") + "');\n" +
                        "</script>\n";
            }
            if (user.getUsrPassword() != null && !user.getUsrPassword().trim().equals("")) {
                userattr.setAttribute("msgCurrentPassword", paramRequest.getLocaleString("msgCurrentPassword"));
            }
            userattr.setAttribute("msgNewPassword", paramRequest.getLocaleString("msgNewPassword"));
            userattr.setAttribute("msgConfirmPassword", paramRequest.getLocaleString("msgConfirmPassword"));
            userattr.setAttribute("name", user.getName());
            userattr.setAttribute("lastName", user.getUsrLastName());
            userattr.setAttribute("firstName", user.getUsrFirstName());
            userattr.setAttribute("middleName", user.getUsrSecondLastName());
            userattr.setAttribute("email", user.getUsrEmail());
            userattr.setAttribute("login", user.getUsrLogin());
            //}
            
            org.semanticwb.portal.api.SWBResourceURL url=paramRequest.getActionUrl();
            euser.appendChild(userattr);
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(outputStream);
            Transformer transformer = templates.newTransformer();
            url.setAction("change");
            transformer.setParameter("actionUrl", url.toString());
            transformer.transform(domSource, streamResult);
            response.getWriter().println(alert + outputStream.toString());
        } catch (Exception e) {
            throw new SWBResourceException(e.toString());
        }
    }

    @Override
    public void processAction(HttpServletRequest request, org.semanticwb.portal.api.SWBActionResponse response) throws SWBResourceException, IOException 
    {
        String msg = response.getLocaleString("msgErrUpdate");
        String curPassword = null != request.getParameter("curPassword") && !"".equals(request.getParameter("curPassword").trim()) ? request.getParameter("curPassword").trim() : "";
        String newPassword = null != request.getParameter("newPassword") && !"".equals(request.getParameter("newPassword").trim()) ? request.getParameter("newPassword").trim() : null;
        String rePassword = null != request.getParameter("rePassword") && !"".equals(request.getParameter("rePassword").trim()) ? request.getParameter("rePassword").trim() : null;

        User user = response.getUser();
        if ((user.getUsrPassword() != null && !user.getUsrPassword().trim().equals("")) && !user.getUsrPassword().equals(curPassword)) {
            msg = response.getLocaleString("msgErrCurrentPassword");
        } else if (newPassword != null && rePassword != null && newPassword.equals(rePassword)) {
            try {
                /*  TODO: VER 4.0
                UserSrv srv = new UserSrv();
                if (srv.changePassword(user.getRepository(), user.getId(), newPassword, response.getUser().getId())) {
                    msg = response.getLocaleString("msgOkUpdate");
                }
                 **/
            } catch (Exception e) {
            }
        } else {
            msg = response.getLocaleString("msgErrNewPassword");
        }
        response.setRenderParameter("_msg", msg);
    }
}

    