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

package org.semanticwb.portal.resources.demo;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.Subject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author serch
 */
public class Registro extends GenericResource{

    static private Logger log = SWBUtils.getLogger(Registro.class);

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        User user = paramRequest.getUser();
        if (!user.isSigned()) {
            doView(request, response, paramRequest);
        } else
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("url", paramRequest.getActionUrl().setAction(SWBResourceURL.Action_EDIT));
            String err = "";
            if (request.getParameter("_errMsg")!=null) err = request.getParameter("_errMsg");
            request.setAttribute("errMessage", err);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/swbadmin/jsp/demo/registroUpdate.jsp");
            try
            {
                dispatcher.forward(request, response);
            //dispatcher.include(request, response);
            } catch (ServletException ex)
            {
                log.error("Can't include a jsp file /swbadmin/jsp/demo/registroUpdate.jsp" , ex);
            }
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        User user = paramRequest.getUser();
        if (user.isSigned()) {
            doEdit(request, response, paramRequest);
        } else
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("url", paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD));
            String err = "";
            if (request.getParameter("_errMsg")!=null) err = request.getParameter("_errMsg");
            request.setAttribute("errMessage", err);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/swbadmin/jsp/demo/registroCreate.jsp");
            try
            {
                dispatcher.forward(request, response);
            //dispatcher.include(request, response);
            } catch (ServletException ex)
            {
                log.error("Can't include a jsp file /swbadmin/jsp/demo/registroCreate.jsp" , ex);
            }
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        if (response.getAction().equals(SWBResourceURL.Action_ADD))
        {
            if (response.getUser().isSigned()) {
                response.setRenderParameter("errMessage", " ya se ha firmado, para crear un nuevo usuario se requiere que cierre su sessi&oacute;n");
                return;
            }
            String login = request.getParameter("usrLogin");
            if (null==login || "".equals(login))
            {
                response.setRenderParameter("errMessage", " Debe suministrar la clave de usuario");
                return;
            }
            String pwd = request.getParameter("usrPwd");
            String pwd2 = request.getParameter("usrPwd2");
            if (!pwd.equals(pwd2))
            {
                response.setRenderParameter("errMessage", " las contrase&ntilde;as no corresponden");
                return;
            }
            System.out.println("Topic: "+response.getWebPage());
            System.out.println("website: "+response.getWebPage().getWebSite());
            System.out.println("Repository: "+response.getWebPage().getWebSite().getUserRepository());
            UserRepository ur = response.getWebPage().getWebSite().getUserRepository();
            System.out.println("Rep: "+ur);
            User u=ur.getUserByLogin(login);
            if (null!=u)
            {
                response.setRenderParameter("errMessage", " la clave de usuario "+login+" ya est&aacute; en uso");
                return;
            }
            u = ur.createUser();
            u.setLogin(login);
            u.setPassword(pwd);
            u.setLanguage("es");
            u.setActive(true);
            try
            {
                u.checkCredential(pwd.toCharArray());
            } catch (NoSuchAlgorithmException ex)
            {
                log.error("There are no crypto algorithms!!!! ");
            }
            String url = response.getWebPage().getRealUrl();
            Subject subject = SWBPortal.getUserMgr().getSubject(request, response.getWebPage().getWebSiteId());
            Set<Principal> principals  = subject.getPrincipals();
            principals.clear();
            principals.add(u);
            response.setMode(SWBResourceURL.Mode_EDIT);

        }
        else if (response.getAction().equals(SWBResourceURL.Action_EDIT))
        {
            User user = response.getUser();
            String lName = null;
            String mail = null;
            String rolId = null;
            Iterator<Role> lista = user.getUserRepository().listRoles();
                while (lista.hasNext()){
                    org.semanticwb.model.Role actRole = lista.next();
                    user.removeRole(actRole);
                }
            if (null!=request.getParameter("usrFName")){
                user.setFirstName(SWBUtils.XML.replaceXMLChars(request.getParameter("usrFName")));
            }
            if (null!=request.getParameter("usrLName")){
                user.setLastName(SWBUtils.XML.replaceXMLChars(request.getParameter("usrLName")));
            }
            if (null!=request.getParameter("usrMail")){
                user.setEmail(SWBUtils.XML.replaceXMLChars(request.getParameter("usrMail")));
            }
            if (null!=request.getParameter("usrRol")){
                rolId = SWBUtils.XML.replaceXMLChars(request.getParameter("usrRol"));
                Role role = user.getUserRepository().getRole(rolId);
                if (null!=role){user.addRole(role);}
            }
            response.sendRedirect(response.getWebPage().getWebSite().getHomePage().getUrl());



        }
    }

}
