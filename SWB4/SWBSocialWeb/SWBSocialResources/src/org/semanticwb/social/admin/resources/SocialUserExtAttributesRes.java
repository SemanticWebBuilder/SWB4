/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class SocialUserExtAttributesRes extends GenericResource{
    
    private static Logger log = SWBUtils.getLogger(SocialUserExtAttributesRes.class);
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        PrintWriter out=response.getWriter();
        String suri=request.getParameter("suri");
        
        out.println("<script type=\"javascript\">");
        if (request.getParameter("statusMsg") != null) {
            out.println("   showStatus('" + request.getParameter("statusMsg") + "');");
        }
        if (request.getParameter("reloadTap") != null) {
            out.println(" reloadTab('" + suri + "'); ");
        }
        out.println("</script>");

        
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/user/socialUserExtAttributes.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        if (dis != null) {
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception ex) {
                log.error(ex);
                ex.printStackTrace(System.out);
            }
        }
    }
    
      @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
          
        setUserExtendedAttributes(request, response);

        response.setRenderParameter("statusMsg", response.getLocaleString("saveUserExtAttr"));
        response.setRenderParameter("suri", request.getParameter("suri"));
        response.setRenderParameter("reloadTap", request.getParameter("suri"));
        response.setMode(response.Mode_VIEW);
        //response.setCallMethod(response.Call_CONTENT);
    }
      
      
   private void setUserExtendedAttributes(HttpServletRequest request, SWBActionResponse response) {
        try {
            //System.out.println("setUserExtendedAttributes-1:"+request.getParameter("suri"));
            if(request.getParameter("suri")!=null)
            {
                //WebSite wsite=SWBContext.getAdminWebSite();
                
                
                SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("suri"));
                User user=(User)semObj.createGenericInstance();

                Iterator<SemanticProperty> list = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialUserExtAttributes").listProperties();
                /*
                SocialUserExtAttributes socialExtAtt = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), wsite);
                if (socialExtAtt == null) {
                    socialExtAtt = SocialUserExtAttributes.ClassMgr.createSocialUserExtAttributes(user.getId(), wsite);  
                }**/
             
                
                //System.out.println("setUserExtendedAttributes-2:"+list.hasNext()+",usr:"+user);
                while (list.hasNext()) {
                    SemanticProperty sp = list.next();
                    if (null == request.getParameter(sp.getName())) {
                        user.removeExtendedAttribute(sp);
                        //user.setExtendedAttribute(sp, false);
                        //user.setExtendedAttribute(sp, Boolean.valueOf(false));
                        //if(sp.getName().equals(SocialUserExtAttributes.social_userCanReTopicMsg.getName())) socialExtAtt.setUserCanReTopicMsg(false);
                        //if(sp.getName().equals(SocialUserExtAttributes.social_userCanReValueMsg.getName())) socialExtAtt.setUserCanReValueMsg(false);
                        //if(sp.getName().equals(SocialUserExtAttributes.social_userCanRemoveMsg.getName())) socialExtAtt.setUserCanRemoveMsg(false);
                        //if(sp.getName().equals(SocialUserExtAttributes.social_userCanRespondMsg.getName())) socialExtAtt.setUserCanRespondMsg(false);
                        //System.out.println("setUserExtendedAttributes-3REMGeorge24:"+sp+",user prop:"+user.getExtendedAttribute(sp));
                    } else {
                        //System.out.println("setUserExtendedAttributes-4:"+sp);
                        String [] values = request.getParameterValues(sp.getName());
                        if (values.length > 1) {
                            String value = "";
                            for (int i = 0; i < values.length; i++) {
                                value += values[i];
                                if (i < values.length -1) {
                                    value += "|";
                                }
                            }
                            user.setExtendedAttribute(sp, SWBUtils.XML.replaceXMLChars(value));
                            user.getSemanticObject().setProperty(sp, SWBUtils.XML.replaceXMLChars(value));
                        } else if (sp.isString()) {
                            user.setExtendedAttribute(sp, SWBUtils.XML.replaceXMLChars(request.getParameter(sp.getName())));
                            user.getSemanticObject().setProperty(sp, SWBUtils.XML.replaceXMLChars(request.getParameter(sp.getName())));
                        } else if (sp.isBoolean()) {
                            user.setExtendedAttribute(sp, Boolean.valueOf(true));
                            //if(sp.getName().equals(SocialUserExtAttributes.social_userCanReTopicMsg.getName())) socialExtAtt.setUserCanReTopicMsg(true);
                            //if(sp.getName().equals(SocialUserExtAttributes.social_userCanReValueMsg.getName())) socialExtAtt.setUserCanReValueMsg(true);
                            //if(sp.getName().equals(SocialUserExtAttributes.social_userCanRemoveMsg.getName())) socialExtAtt.setUserCanRemoveMsg(true);
                            //if(sp.getName().equals(SocialUserExtAttributes.social_userCanRespondMsg.getName())) socialExtAtt.setUserCanRespondMsg(true);
                            //System.out.println("sp:"+sp+", a True");
                        } else if (sp.isInt()) {
                            try {
                                Integer val = Integer.valueOf(request.getParameter(sp.getName()));
                                user.setExtendedAttribute(sp, val);
                            } catch (Exception ne) {
                            }
                        } else if (sp.isDouble()) {
                            try {
                                Double val = Double.valueOf(request.getParameter(sp.getName()));
                                user.setExtendedAttribute(sp, val);
                            } catch (Exception ne) {
                            }
                        } else if (sp.isDate()) {
                            try {
                                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                                Date val = sf.parse(request.getParameter(sp.getName()));
                                user.setExtendedAttribute(sp, val);
                            } catch (Exception ne) {
                                ne.printStackTrace();
                            }
                        }

                    }
                }
            }
        } catch (SWBException nex) {
            log.error(nex);
        }
    }   
      
      
      
      
    /*  
    private void setUserExtendedAttributes(HttpServletRequest request, SWBActionResponse response) {
        //User user=response.getUser();
        //Tomando en cuenta que todos los usuarios que se modificaran en sus propiedades extendidas, sean usuarios del repositorio de Admin.
        System.out.println("setUserExtendedAttributes-JJ0");
        if(request.getParameter("suri")!=null)
        {
            WebSite wsite=SWBContext.getAdminWebSite();

            SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("suri"));
            User user=(User)semObj.createGenericInstance();

            SocialUserExtAttributes socialextatt = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), wsite);
            System.out.println("user.getId()--GEORGE:"+user.getId()+",socialextatt-1:"+socialextatt);
            if (socialextatt == null) {
                socialextatt = SocialUserExtAttributes.ClassMgr.createSocialUserExtAttributes(user.getId(), wsite);
                System.out.println("user.getId():"+user.getId()+",socialextatt-2:"+socialextatt);
            }
        */    
            /*
            Enumeration enParams=request.getParameterNames();
            while(enParams.hasMoreElements())
            {
                String paramName=(String)enParams.nextElement();
                System.out.println("param:"+paramName+",value:"+request.getParameter(paramName));
            }*/
/*
            if (request.getParameter(SocialUserExtAttributes.social_userCanRemoveMsg.getName()) != null) {
                System.out.println(SocialUserExtAttributes.social_userCanRemoveMsg.getName()+"---True");
                socialextatt.setUserCanRemoveMsg(true);
            }else{
                System.out.println(SocialUserExtAttributes.social_userCanRemoveMsg.getName()+"---False");
                socialextatt.setUserCanRemoveMsg(false);
               // socialextatt.removeProperty(SocialUserExtAttributes.social_userCanRemoveMsg.getName());
            }

            if (request.getParameter(SocialUserExtAttributes.social_userCanRespondMsg.getName()) != null) {
                System.out.println(SocialUserExtAttributes.social_userCanRespondMsg.getName()+"---True");
                socialextatt.setUserCanRespondMsg(true);
            }else{
                System.out.println(SocialUserExtAttributes.social_userCanRespondMsg.getName()+"---False");
                socialextatt.setUserCanRespondMsg(false);
                //socialextatt.removeProperty(SocialUserExtAttributes.social_userCanRespondMsg.getName());
            }

            if (request.getParameter(SocialUserExtAttributes.social_userCanReValueMsg.getName()) != null) {
                System.out.println(SocialUserExtAttributes.social_userCanReValueMsg.getName()+"---True");
                socialextatt.setUserCanReValueMsg(true);
            }else{
                System.out.println(SocialUserExtAttributes.social_userCanReValueMsg.getName()+"---False");
                socialextatt.setUserCanReValueMsg(false);
                //socialextatt.removeProperty(SocialUserExtAttributes.social_userCanReValueMsg.getName());
            }

            if (request.getParameter(SocialUserExtAttributes.social_userCanReTopicMsg.getName()) != null) {
                System.out.println(SocialUserExtAttributes.social_userCanReTopicMsg.getName()+"---True");
                socialextatt.setUserCanReTopicMsg(true);
            }else{
                System.out.println(SocialUserExtAttributes.social_userCanReTopicMsg.getName()+"---False");
                socialextatt.setUserCanReTopicMsg(false);
                //socialextatt.removeProperty(SocialUserExtAttributes.social_userCanReTopicMsg.getName());
            }
        }
    }
    * **/
    
}
