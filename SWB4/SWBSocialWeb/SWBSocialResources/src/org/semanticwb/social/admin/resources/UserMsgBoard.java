/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.UserMessage;
import org.semanticwb.social.util.SWBSocialUtil;

/**
 *
 * @author jorge.jimenez
 */
public class UserMsgBoard extends GenericResource{
    
    private static Logger log = SWBUtils.getLogger(UserMsgBoard.class);
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        if(request.getParameter("statusMsg")!=null)
        {
            System.out.println("statusMsg:"+request.getParameter("statusMsg"));
            out.println("<script type=\"javascript\">");
            out.println("   showStatus('" + request.getParameter("statusMsg") + "');");
            out.println("</script>");
        }
        
        
        String jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/homePreview/userMsgBoard.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
     }
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action=response.getAction();
        if(action!=null)
        {
            WebSite wsite=SWBSocialUtil.getConfigWebSite();
            if(action.equals("newUserMsg"))
            {
                try
                {
                    /*
                    Enumeration<String> enParamNames=request.getParameterNames();
                    while(enParamNames.hasMoreElements())
                    {
                        String strName=enParamNames.nextElement();
                        System.out.println("ParamName:"+strName+",value:"+request.getParameter(strName));
                        if(strName.equals("hasUsers"))
                        {
                            String[] values=request.getParameterValues(strName);
                            for(int i=0;i<values.length;i++) 
                            {
                                System.out.println("Value["+i+"]:"+values[i]);
                            }
                        }
                    }*/
                    
                    if(request.getParameter("fromUser")!=null && request.getParameter("usrMsg")!=null && 
                            request.getParameter("usrMsg").trim().length()>0 && request.getParameter("hasUsers")!=null)
                    {
                        UserMessage userMsg=UserMessage.ClassMgr.createUserMessage(wsite);
                        userMsg.setUsrMsg(request.getParameter("usrMsg"));
                        
                        SemanticObject semObjFromUser=SemanticObject.getSemanticObject(request.getParameter("fromUser"));
                        User fromUser=(User)semObjFromUser.getGenericInstance();
                        userMsg.setFromUser(fromUser);
                        
                        
                        String[] values=request.getParameterValues("hasUsers");
                        for(int i=0;i<values.length;i++) 
                        {
                            SemanticObject semObjToUser=SemanticObject.getSemanticObject(values[i]);
                            User toUser=(User)semObjToUser.getGenericInstance();
                            userMsg.addUsers(toUser);
                        }
                        
                        if(request.getParameter("userPriority")!=null && request.getParameter("userPriority").equals("on"))
                        {
                            userMsg.setUserPriority(true);
                        }
                    }
                }catch(Exception e)
                {
                    System.out.println(e.getMessage());
                    log.error(e);
                }
            }else if(action.equals("remMsg")) {
                if(request.getParameter("msgUri")!=null)
                {
                    System.out.println("msgUri:"+request.getParameter("msgUri"));
                    SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("msgUri"));
                    if(semObj!=null)
                    {
                        UserMessage userMsg=(UserMessage)semObj.createGenericInstance(); 
                        System.out.println("UsrMsg Msg:"+userMsg.getUsrMsg());
                        semObj.remove();
                        System.out.println("semObj:"+semObj+",Ha sido eliminado...");
                    }
                }
                response.setRenderParameter("statusMsg", "Mensaje Eliminado");
                response.setMode(SWBActionResponse.Mode_VIEW);
            }else if(action.equals("editUserMsg"))
            {
                if(request.getParameter("msgUri")!=null && request.getParameter("fromUser")!=null && request.getParameter("usrMsg")!=null && 
                   request.getParameter("usrMsg").trim().length()>0 && request.getParameter("hasUsers")!=null)
                {
                    SemanticObject semObj=SemanticObject.getSemanticObject(request.getParameter("msgUri"));
                    UserMessage userMsg=(UserMessage)semObj.getGenericInstance();
                    userMsg.setUsrMsg(request.getParameter("usrMsg"));
                    userMsg.removeAllUsers();
                    String[] values=request.getParameterValues("hasUsers");
                    for(int i=0;i<values.length;i++) 
                    {
                        SemanticObject semObjToUser=SemanticObject.getSemanticObject(values[i]);
                        User toUser=(User)semObjToUser.getGenericInstance();
                        userMsg.addUsers(toUser);
                    }

                    if(request.getParameter("userPriority")!=null && request.getParameter("userPriority").equals("on"))
                    {
                        userMsg.setUserPriority(true);
                    }else{
                         userMsg.setUserPriority(false);
                    }
                }
            }
        }
    }
}
