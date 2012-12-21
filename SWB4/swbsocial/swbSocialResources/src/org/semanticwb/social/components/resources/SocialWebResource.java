package org.semanticwb.social.components.resources;

import javax.servlet.http.HttpSession;
import org.semanticwb.portal.api.SWBResourceURL;
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
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

import org.semanticwb.social.Oauthable;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialSite;

/**
 *
 * @author carlos.ramos
 */
public class SocialWebResource extends GenericAdmResource
{
    private static Logger log = SWBUtils.getLogger(SocialWebResource.class);
    
    public static final String ATTR_THIS = "this";
    public static final String ATTR_PARAMREQUEST = "paramRequest";
    public static final String ATTR_AXN = "action";
    public static final String ATTR_OBJURI = "objUri";
    public static final String ATTR_WSITEID = "wsite";
    public static final String ATTR_TREEITEM = "treeItem";
    
    public static final String OAUTH_MODE = "oauth";
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if(OAUTH_MODE.equals(mode))
        {
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
//        if(user.isSigned())
//        {
            String msg = request.getParameter("msg");
            if(msg!=null) {
                out.println("<div class=\"\"><p class=\"\">"+msg+"</p></div>");
            }
            
            final String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/admin/jsp/components/" + this.getClass().getSimpleName() + "/";
System.out.println("basePath="+basePath);
            final String action=request.getParameter(ATTR_AXN);
            final String objUri = request.getParameter(ATTR_OBJURI);
            final String wsite = request.getParameter(ATTR_WSITEID);
            final String treeItem = request.getParameter(ATTR_TREEITEM);
            
            RequestDispatcher dis = null;
            dis = request.getRequestDispatcher(basePath+"/new.jsp");
            try
            {
                request.setAttribute(ATTR_THIS, this);
                request.setAttribute(ATTR_PARAMREQUEST, paramRequest);
                request.setAttribute(ATTR_AXN, action);
                request.setAttribute(ATTR_OBJURI, objUri);
                request.setAttribute(ATTR_WSITEID, wsite);
                request.setAttribute(ATTR_TREEITEM, treeItem);
                dis.include(request, response);
            }catch (Exception e) {
                log.error(e);
            }
//        }else {
//            out.println("<p>Usuario no autorizado. Consulte a su administrador</p>");
//        }
    }
    
    public void doAuthenticate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
System.out.println("********************   doAuthenticate.");
        
final String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/admin/jsp/components/" + this.getClass().getSimpleName() + "/";
final String action = request.getParameter(ATTR_AXN)==null?paramRequest.getAction():"";

        String objUri = request.getParameter(ATTR_OBJURI);
        final String wsiteId = request.getParameter(ATTR_WSITEID);
        final SocialSite wsite = SocialSite.ClassMgr.getSocialSite(wsiteId);
        final String treeItem = request.getParameter(ATTR_TREEITEM);
        
System.out.println("objUri es nulo?"+" "+(objUri==null));        
        
        HttpSession session = request.getSession(true);
        if(session.getAttribute("sw")==null)
        {
System.out.println("------------------------ 1");
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
            //request.setAttribute("objUri", objUri);
            socialNetwork.authenticate(request, response, paramRequest);
            session.setAttribute("sw", socialNetwork);
        }
        else
        {
System.out.println("------------------------ 2");
            SocialNetwork socialNetwork = (SocialNetwork)session.getAttribute("sw");
            objUri = socialNetwork.getURI();
            socialNetwork.authenticate(request, response, paramRequest);
            session.removeAttribute("sw");
System.out.println("request.getAttribute('msg')="+request.getAttribute("msg"));            
        }

System.out.println("including jsp...............");
RequestDispatcher dis = null;
dis = request.getRequestDispatcher(basePath+"/new.jsp");
try
{
    request.setAttribute(ATTR_THIS, this);
    request.setAttribute(ATTR_PARAMREQUEST, paramRequest);
    request.setAttribute(ATTR_AXN, action);
    request.setAttribute(ATTR_OBJURI, objUri);
    request.setAttribute(ATTR_WSITEID, wsiteId);
    request.setAttribute(ATTR_TREEITEM, treeItem);
    dis.include(request, response);
}catch (Exception e) {
    log.error(e);
    e.printStackTrace(System.out);
}
    }
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
System.out.println("processAction....");
        final String wsiteId = request.getParameter(ATTR_WSITEID);
        final SocialSite model = SocialSite.ClassMgr.getSocialSite(wsiteId);
        final String action = response.getAction();
System.out.println("action="+action);
        if(SWBResourceURL.Action_ADD.equals(action))
        {
System.out.println("PHASE 1");
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
            response.setRenderParameter(ATTR_WSITEID, wsiteId);
            response.setRenderParameter(ATTR_TREEITEM, request.getParameter(ATTR_TREEITEM));
//            response.setRenderParameter(URL_REQ_PERMISSIONS, url);
            //response.setCallMethod(SWBResourceURL.Call_DIRECT);
        }
        
        
        
        
        
        /*if(SWBResourceURL.Action_ADD.equals(action))
        {
System.out.println("wsite="+request.getAttribute("wsite"));
//            final String socialweb = request.getParameter("socialweb");
//            SemanticClass sclass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(socialweb);
//            long id = model.getSemanticObject().getModel().getCounter(sclass);
//            //GenericObject gobj = model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(Long.toString(id), sclass), sclass);     
//            SocialNetwork socialNetwork = (SocialNetwork)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(Long.toString(id), sclass), sclass);     
//            //gobj.setProperty(SocialNetwork.swb_title.getURI(), title.getValue());
//            socialNetwork.setTitle(title.getValue());
        }*/
    }
}