/*
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
 */
package org.semanticwb.portal.resources;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.User;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceURL;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class ChangePassword.
 */
public class ChangePassword extends GenericAdmResource {
    /** The log. */
    private static Logger log = SWBUtils.getLogger(ChangePassword.class);

    private static Login login;
    
    /** The template. */
    Templates template;
    
    /** The path. */
    String path = SWBPlatform.getContextPath()+"swbadmin/xsl/ChangePassword/";

    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);

        WebSite site = base.getWebSite();
        Iterator<Resource> rs = site.getResourceType("Login").listResources();
        Resource res;
        do {
            res = rs.next();
        }while(rs.hasNext() && res == null);
        if(res!=null && res.isActive()) {
            login = new Login();
            login.setResourceBase(res);
        }

        if(!"".equals(base.getAttribute("template","").trim()))
        {
            try
            {
                template = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
                path = SWBPlatform.getContextPath()+"swbadmin/xsl/ChangePassword/";
            }catch(Exception e) {
                log.error("Error while loading resource template: "+base.getId(), e);
            }
        }
        if(template==null)
        {
            try {
                template = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/ChangePassword/ChangePassword.xslt"));
            }catch(Exception e) {
                log.error("Error while loading default resource template: "+base.getId(), e);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        User user = paramRequest.getUser();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            Templates template = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(getResourceBase().getWorkPath() + "/" + getResourceBase().getAttribute("template")));
            PrintWriter out = response.getWriter();
            if(request.getParameter("msg")!=null) {
                out.println("<script type=\"text/javascript\">");
                out.println("<!--");
                out.println("   alert('"+ paramRequest.getLocaleString(request.getParameter("msg"))+"');");
                out.println("-->");
                out.println("</script>");
            }
            if(user.isSigned()) {
                Document document = SWBUtils.XML.getNewDocument();
                Element euser = document.createElement("USER");
                document.appendChild(euser);
                Element userattr = document.createElement("userattr");

                userattr.setAttribute("msgCurrentPassword", paramRequest.getLocaleString("msgCurrentPassword"));
                userattr.setAttribute("msgNewPassword", paramRequest.getLocaleString("msgNewPassword"));
                userattr.setAttribute("msgConfirmPassword", paramRequest.getLocaleString("msgConfirmPassword"));
                userattr.setAttribute("name", user.getName());
                userattr.setAttribute("lastName", user.getLastName());
                userattr.setAttribute("firstName", user.getFirstName());
                userattr.setAttribute("middleName", user.getSecondLastName());
                userattr.setAttribute("email", user.getEmail());
                userattr.setAttribute("login", user.getLogin());
                try {
                    SWBResourceURL url = paramRequest.getActionUrl().setAction(paramRequest.Action_EDIT);
                    euser.appendChild(userattr);
                    DOMSource domSource = new DOMSource(document);
                    StreamResult streamResult = new StreamResult(outputStream);
                    Transformer transformer = template.newTransformer();
                    transformer.setParameter("actionUrl", url.toString());
                    transformer.transform(domSource, streamResult);
                    out.println(outputStream.toString());
                }catch(TransformerConfigurationException tce) {
                    log.error(tce);
                }catch(TransformerException te) {
                    log.error(te);
                }
            }else {
                if(login!=null && user.haveAccess(login.getResourceBase())) {
                    login.render(request, response, paramRequest);
                }
            }
//        } catch (Exception e) {
//            throw new SWBResourceException(e.toString());
//        }
    }
    
    @Override
    public void processAction(HttpServletRequest request, org.semanticwb.portal.api.SWBActionResponse response) throws SWBResourceException, IOException {
        final String action = response.getAction();
        User user = response.getUser();

        if(response.Action_EDIT.equals(action)) {
            String curPassword = request.getParameter("curPassword")==null?null:request.getParameter("curPassword").trim();
            String newPassword = request.getParameter("newPassword")==null?null:request.getParameter("newPassword").trim();
            String rePassword = request.getParameter("rePassword")==null?null:request.getParameter("rePassword").trim();    
            if(user.isSigned()) {
                try {
                    String alg = user.getPassword().substring(1,user.getPassword().indexOf("}"));
                    if( !user.getPassword().equals("") && !SWBUtils.CryptoWrapper.comparablePassword(curPassword, alg).equals(user.getPassword()) ) {
                        response.setRenderParameter("msg", "msgErrCurrentPassword");
                    }else if( newPassword!=null && !newPassword.equals("") && rePassword!=null && newPassword.equals(rePassword) ) {
                        user.setPassword(newPassword);
                        response.setRenderParameter("msg", "msgOkUpdate");
                    }else {
                        response.setRenderParameter("msg", "msgErrNewPassword");
                    }
                }catch(java.security.NoSuchAlgorithmException nse) {
                    response.setRenderParameter("msg", "msgErrUpdate");
                }
            }
        }else if(login!=null && user.haveAccess(login.getResourceBase())) {
            login.processAction(request, response);
        }
    }
}

    