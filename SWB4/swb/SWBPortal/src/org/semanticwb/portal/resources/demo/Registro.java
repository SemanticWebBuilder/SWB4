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
            System.out.println("Topic: "+response.getTopic());
            System.out.println("website: "+response.getTopic().getWebSite());
            System.out.println("Repository: "+response.getTopic().getWebSite().getUserRepository());
            UserRepository ur = response.getTopic().getWebSite().getUserRepository();
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
            String url = response.getTopic().getRealUrl();
            Subject subject = SWBPortal.getUserMgr().getSubject(request, response.getTopic().getWebSiteId());
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
            response.sendRedirect(response.getTopic().getWebSite().getHomePage().getUrl());



        }
    }

}
