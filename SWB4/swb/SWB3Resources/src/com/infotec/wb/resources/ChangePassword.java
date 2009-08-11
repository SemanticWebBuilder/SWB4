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
            if (user.getPassword() != null && !user.getPassword().trim().equals("")) {
                userattr.setAttribute("msgCurrentPassword", paramRequest.getLocaleString("msgCurrentPassword"));
            }
            userattr.setAttribute("msgNewPassword", paramRequest.getLocaleString("msgNewPassword"));
            userattr.setAttribute("msgConfirmPassword", paramRequest.getLocaleString("msgConfirmPassword"));
            userattr.setAttribute("name", user.getName());
            userattr.setAttribute("lastName", user.getLastName());
            userattr.setAttribute("firstName", user.getFirstName());
            userattr.setAttribute("middleName", user.getSecondLastName());
            userattr.setAttribute("email", user.getEmail());
            userattr.setAttribute("login", user.getLogin());
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
        if ((user.getPassword() != null && !user.getPassword().trim().equals("")) && !user.getPassword().equals(curPassword)) {
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

    
