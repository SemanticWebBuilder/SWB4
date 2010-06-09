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
 

package org.semanticwb.portal.resources;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.Templates;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.User;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBParamRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class ChangePassword.
 */
public class ChangePassword extends GenericAdmResource {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(Language.class);
    
    /** The template. */
    Templates template;
    
    /** The path. */
    String path = SWBPlatform.getContextPath()+"swbadmin/xsl/ChangePassword/";

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#setResourceBase(org.semanticwb.model.Resource)
     */
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);

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
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        String alert = new String();
        User user = paramRequest.getUser();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            //Templates template = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(getResourceBase().getWorkPath() + "/" + getResourceBase().getAttribute("template")));
            Document document = SWBUtils.XML.getNewDocument();
            Element euser = document.createElement("USER");
            document.appendChild(euser);
            Element userattr = document.createElement("userattr");

            if(request.getSession(true).getAttribute("_msg")!= null) {
                alert = "<script type=\"text/javascript\" language=\"JavaScript\">\n" +
                        "   alert('" + request.getSession(true).getAttribute("_msg") + "');\n" +
                        "</script>\n";
                request.getSession(true).setAttribute("_msg", null);
                request.removeAttribute("_msg");
            }
            /*if( user.getPassword()!=null && !user.getPassword().trim().equals("")) {*/
                userattr.setAttribute("msgCurrentPassword", paramRequest.getLocaleString("msgCurrentPassword"));            
                userattr.setAttribute("msgNewPassword", paramRequest.getLocaleString("msgNewPassword"));
                userattr.setAttribute("msgConfirmPassword", paramRequest.getLocaleString("msgConfirmPassword"));
                userattr.setAttribute("name", user.getName());
                userattr.setAttribute("lastName", user.getLastName());
                userattr.setAttribute("firstName", user.getFirstName());
                userattr.setAttribute("middleName", user.getSecondLastName());
                userattr.setAttribute("email", user.getEmail());
                userattr.setAttribute("login", user.getLogin());
                
                org.semanticwb.portal.api.SWBResourceURL url=paramRequest.getActionUrl();
                euser.appendChild(userattr);
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(outputStream);
                Transformer transformer = template.newTransformer();
                url.setAction("change");
                transformer.setParameter("actionUrl", url.toString());
                transformer.transform(domSource, streamResult);
                response.getWriter().println(alert + outputStream.toString());
            /*}*/
        } catch (Exception e) {
            throw new SWBResourceException(e.toString());
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, org.semanticwb.portal.api.SWBActionResponse response) throws SWBResourceException, IOException
    {
        String msg = null;
        String curPassword = request.getParameter("curPassword");
        String newPassword = request.getParameter("newPassword");
        String rePassword = request.getParameter("rePassword");
        User user = response.getUser();

        try{
            String alg = user.getPassword().substring(1,user.getPassword().indexOf("}"));
            if( user.getPassword()!=null && !user.getPassword().trim().equals("") && !SWBUtils.CryptoWrapper.comparablePassword(curPassword, alg).equals(user.getPassword()) ) {
                msg = response.getLocaleString("msgErrCurrentPassword");
            }else if( newPassword!=null && rePassword!=null && newPassword.trim().equals(rePassword.trim()) && !newPassword.trim().equals("") ) {
                user.setPassword(newPassword.trim());
                msg = response.getLocaleString("msgOkUpdate");
            } else {
                msg = response.getLocaleString("msgErrNewPassword");
            }
        }catch(NoSuchAlgorithmException nse) {
            msg = response.getLocaleString("msgErrUpdate");
        }
        request.getSession(true).setAttribute("_msg", msg);
    }
}

    