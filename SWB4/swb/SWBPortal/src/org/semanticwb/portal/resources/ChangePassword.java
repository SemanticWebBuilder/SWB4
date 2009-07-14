
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

public class ChangePassword extends GenericAdmResource {
    private static Logger log = SWBUtils.getLogger(Language.class);
    Templates template;
    String path = SWBPlatform.getContextPath()+"swbadmin/xsl/ChangePassword/";

    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);

        if(!"".equals(base.getAttribute("template","").trim()))
        {
            try
            {
                template = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
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

    @Override
    public void processAction(HttpServletRequest request, org.semanticwb.portal.api.SWBActionResponse response) throws SWBResourceException, IOException
    {
        String msg = null;
        String curPassword = request.getParameter("curPassword");
        String newPassword = request.getParameter("newPassword");
        String rePassword = request.getParameter("rePassword");
        User user = response.getUser();

        try{
            if( user.getPassword()!=null && !user.getPassword().trim().equals("") && !SWBUtils.CryptoWrapper.comparablePassword(curPassword).equals(user.getPassword()) ) {
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

    