package org.semanticwb.social.components.resources;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialSite;
import org.semanticwb.social.components.tree.ElementTreeNode;
import org.semanticwb.social.utils.SWBSocialResourceUtils;

/**
 *
 * @author carlos.ramos
 */
public class SocialWebResource extends GenericSocialResource
{
    private static Logger log = SWBUtils.getLogger(SocialWebResource.class);
    
    public static final String ATTR_THIS = "this";
    public static final String ATTR_PARAMREQUEST = "paramRequest";
    public static final String ATTR_AXN = "action";
    public static final String ATTR_OBJURI = "objUri";
    public static final String ATTR_BRAND = "wsite";
    public static final String ATTR_TREEITEM = "treeItem";
    
    public static final String OAUTH_MODE = "oauth";
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(OAUTH_MODE.equals(paramRequest.getMode())) {
            doAuthenticate(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
System.out.println("\n\n********************");
System.out.println("********************");
System.out.println("********************   doView");
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        if(user.isSigned())
        {
            String msg = request.getParameter("msg");
            if(msg!=null) {
                out.println("<div class=\"\"><p class=\"\">"+msg+"</p></div>");
            }
            
            RequestDispatcher dis = null;
            
            final String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/admin/jsp/components/" + this.getClass().getSimpleName() + "/";
            final String objUri = request.getParameter("objUri");
            if(objUri==null)
            {
                dis = request.getRequestDispatcher(basePath+"/new.jsp");
            }else
            {
                dis = request.getRequestDispatcher(basePath+"/edit.jsp");
            }
            
            try
            {
                request.setAttribute(ATTR_THIS, this);
                request.setAttribute(ATTR_PARAMREQUEST, paramRequest);
                dis.include(request, response);
            }catch (Exception e) {
                log.error(e);
            }
        }else {
            out.println("<p>Usuario no autorizado. Consulte a su administrador</p>");
        }
    }
    
    public void doAuthenticate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
System.out.println("********************   doAuthenticate.");
        
        final String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/admin/jsp/components/" + this.getClass().getSimpleName() + "/";
        String objUri = (String)request.getAttribute("objUri");
        final SocialSite wsite = (SocialSite)request.getAttribute("wsite");
        
        HttpSession session = request.getSession(true);
        if(session.getAttribute("sw")==null)
        {
            String sclassURI = request.getParameter("socialweb");
            String title = request.getParameter("title");
            String desc = request.getParameter("desc");
            String appId = request.getParameter("appId");
            String sk = request.getParameter("sk");
            
            SemanticClass sclass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(sclassURI);
            long id = wsite.getSemanticObject().getModel().getCounter(sclass);
            SocialNetwork socialNetwork = (SocialNetwork)wsite.getSemanticObject().getModel().createGenericObject(wsite.getSemanticObject().getModel().getObjectUri(Long.toString(id), sclass), sclass);
            objUri = socialNetwork.getURI();
            socialNetwork.setActive(false);
            socialNetwork.setTitle(title);
            socialNetwork.setDescription(desc);
            socialNetwork.setAppKey(appId);            
            socialNetwork.setSecretKey(sk);
            
             ElementTreeNode treeItem = (ElementTreeNode)request.getAttribute("treeItem");
             System.out.println("treeItem:"+treeItem);
             SWBSocialResourceUtils.Resources.insertTreeNode(request, paramRequest, treeItem, socialNetwork); 
             SWBSocialResourceUtils.Resources.setStatusMessage(request, paramRequest,"Cuenta creada");
            
            session.setAttribute("sw", socialNetwork);
            socialNetwork.authenticate(request, response, paramRequest);
        }
        else
        {
            SocialNetwork socialNetwork = (SocialNetwork)session.getAttribute("sw");
            session.removeAttribute("sw");
            objUri = socialNetwork.getURI();
            if(!socialNetwork.isSn_authenticated()) {
                socialNetwork.authenticate(request, response, paramRequest);
            }
        }

        RequestDispatcher dis = null;
        dis = request.getRequestDispatcher(basePath+"/new.jsp");
        try
        {
            request.setAttribute(ATTR_THIS, this);
            request.setAttribute(ATTR_PARAMREQUEST, paramRequest);
            request.setAttribute("objUri", objUri);
            dis.include(request, response);
        }catch (Exception e) {
            log.error(e);
            e.printStackTrace(System.out);
        }
    }
    
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
System.out.println("********************   doEdit.");
        
        final String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/admin/jsp/components/" + this.getClass().getSimpleName() + "/";
System.out.println(" recuperando socialNetwork....");
        String objUri = (String)request.getAttribute("objUri");
        SocialNetwork socialNetwork;
        try {
            socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
System.out.println(" 1.socialNetwork="+socialNetwork.getId());
            String title = request.getParameter("title");
            String desc = request.getParameter("desc");
            String appId = request.getParameter("appId");
            String sk = request.getParameter("sk");
            if(title!=null && !title.isEmpty()) {
                socialNetwork.setTitle(title);
            }
            if(desc!=null && !desc.isEmpty()) {
                socialNetwork.setDescription(desc);
            }
            if(!socialNetwork.isSn_authenticated() && appId!=null && sk!=null && !appId.isEmpty() && !sk.isEmpty())
            {
                socialNetwork.setAppKey(appId);
                socialNetwork.setSecretKey(sk);
                HttpSession session = request.getSession(true);
                session.setAttribute("objUri", objUri);
                socialNetwork.authenticate(request, response, paramRequest);
            }
        }catch(Exception e) {
            HttpSession session = request.getSession(true);
            objUri = (String)session.getAttribute("objUri");
            try {
                socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
System.out.println(" 2.socialNetwork="+socialNetwork.getId());
                if(!socialNetwork.isSn_authenticated()) {
                    socialNetwork.authenticate(request, response, paramRequest);
                }
            }catch(Exception ex) {
                socialNetwork = null;
            }
        }
        
        RequestDispatcher dis = null;
        dis = request.getRequestDispatcher(basePath+"/edit.jsp");
        try
        {
            request.setAttribute(ATTR_THIS, this);
            request.setAttribute(ATTR_PARAMREQUEST, paramRequest);
            request.setAttribute("objUri", objUri);
            dis.include(request, response);
        }catch (Exception e) {
            log.error(e);
            e.printStackTrace(System.out);
        }
    }
    
    /*
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
System.out.println("processAction....");
        final String wsiteId = request.getParameter(ATTR_BRAND);
        final SocialSite model = SocialSite.ClassMgr.getSocialSite(wsiteId);
        final String action = response.getAction();
        if(SWBResourceURL.Action_ADD.equals(action))
        {
            String sclassURI = request.getParameter("socialweb");
            String title = request.getParameter("title");
            String desc = request.getParameter("desc");
            String appId = request.getParameter("appId");
            String sk = request.getParameter("sk");
            
            SemanticClass sclass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(sclassURI);
            long id = model.getSemanticObject().getModel().getCounter(sclass);
            SocialNetwork socialNetwork = (SocialNetwork)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(Long.toString(id), sclass), sclass);
            socialNetwork.setTitle(title);
            socialNetwork.setDescription(desc);
            socialNetwork.setAppKey(appId);            
            socialNetwork.setSecretKey(sk);
            final String url = ((Oauthable)socialNetwork).doRequestPermissions();
            response.setRenderParameter(ATTR_OBJURI, socialNetwork.getURI());
            response.setRenderParameter(ATTR_BRAND, wsiteId);
            response.setRenderParameter(ATTR_TREEITEM, request.getParameter(ATTR_TREEITEM));
        }
    }
    */
}