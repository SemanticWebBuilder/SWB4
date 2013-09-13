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
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author francisco.jimenez
 */
public class YoutubeWall extends GenericResource{

    private static Logger log = SWBUtils.getLogger(YoutubeWall.class);
    
    /*variables used to define the id of '<div>' for the fields of information, favorite and reweet.
     Each link is in a different '<div>' and it's updated individually*/
    public static String INFORMATION = "/inf";
    public static String LIKE = "/like";
    public static String UNDOLIKE = "/unlike";
    public static String TOPIC ="/topic";
    
    /*Additionally every div has a suffix to identify if the status is inside the tab*/ 
    public static String HOME_TAB = "/myvideos";;
    public static String DISCOVER_TAB ="/discover";
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = (String) request.getParameter("suri");
        String contentTabId = (String) request.getParameter("contentTabId");
        System.out.println("suriReceived in Timeline:" + objUri);
        if(contentTabId == null){//The resource is loaded for the first time and it needs to display the tabs
            String jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/youtubeTabs.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            }catch (Exception e) {
                System.out.println("Error loading the Youtube Tabs " + e);
            }
            return;
        }
        
        String jspResponse = "";
        //Each one of the tabs is loaded once
        if(contentTabId != null && contentTabId.equals(HOME_TAB)){
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/myVideos.jsp";
        }else if(contentTabId != null && contentTabId.equals(DISCOVER_TAB)){
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/youtubeDiscover.jsp";
        }
        
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error("Error in doView() for requestDispatcher" , e);
        }
    }
    
}
