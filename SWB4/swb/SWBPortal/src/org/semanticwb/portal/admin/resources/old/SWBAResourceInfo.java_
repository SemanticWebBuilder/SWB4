/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author juan.fernandez
 */
public class SWBAResourceInfo extends GenericResource {

    /** Creates a new instance of SWBAPortletInfo */
    public SWBAResourceInfo() {
    }

    /** User view of WBAResourceInfo resource
     * @param request input parameters
     * @param response an answer to the request
     * @param paramRequest a list of objects (topic, user, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String typeMap = request.getParameter("typemap");
        if(id!=null && typeMap!=null){
            ResourceType rResType = SWBContext.getWebSite(typeMap).getResourceType(id);
            out.println("<p class=\"box\">");
            out.println("<table width=100% cellpadding=10 cellspacing=0>");
            out.println("<tr>");
            out.println("<td class=\"datos\" align=\"right\" width=150>");
            out.println(paramRequest.getLocaleString("msgIdentifier"));
            out.println("</td>");
            out.println("<td class=\"valores\">");
            out.println(rResType.getId());
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</p>");
            
            out.println("<p class=\"box\">");
            out.println("<table width=100% cellpadding=10 cellspacing=0>");
            out.println("<tr>");
            out.println("<td class=\"datos\" align=\"right\" width=150>");
            out.println(paramRequest.getLocaleString("msgName"));
            out.println("</td>");
            out.println("<td class=\"valores\">");
            out.println(rResType.getTitle());
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td class=\"datos\" align=\"right\" width=150>");
            out.println(paramRequest.getLocaleString("msgDisplayName"));
            out.println("</td>");
            out.println("<td class=\"valores\">");
            out.println(rResType.getTitle());
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td class=\"datos\" align=\"right\" width=150>");
            out.println(paramRequest.getLocaleString("msgDescription"));
            out.println("</td>");
            out.println("<td class=\"valores\">");
            out.println(rResType.getDescription());
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td class=\"datos\" align=\"right\" width=150>");
            out.println(paramRequest.getLocaleString("msgBundle"));
            out.println("</td>");
            out.println("<td class=\"valores\">");
            out.println(rResType.getResourceBundle());
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td class=\"datos\" align=\"right\" width=150>");
            out.println(paramRequest.getLocaleString("msgCache"));
            out.println("</td>");
            out.println("<td class=\"valores\">");
            String strChecked="No";
            if(rResType.getResourceCache()>0) strChecked = "checked";
            out.println("<input type=checkbox name=cached disabled "+strChecked+"> "+rResType.getResourceCache());
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td class=\"datos\" align=\"right\" width=150>");
            out.println(paramRequest.getLocaleString("msgLastUpdate"));
            out.println("</td>");
            out.println("<td class=\"valores\">");
            //TODO: dateFormat
            //out.println(WBUtils.dateFormat(rResType.getLastupdate()));
            out.println(rResType.getUpdated().toString());
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td class=\"datos\" align=\"right\" width=150>");
            out.println(paramRequest.getLocaleString("msgObjectClass"));
            out.println("</td>");
            out.println("<td class=\"valores\">");
            out.println(rResType.getResourceClassName());
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td class=\"datos\" align=\"right\" width=150>");
            out.println(paramRequest.getLocaleString("msgTopicMap"));
            out.println("</td>");
            out.println("<td class=\"valores\">");
            out.println(rResType.getWebSite().getId());
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td class=\"datos\" align=\"right\" width=150>");
            out.println(paramRequest.getLocaleString("msgType"));
            out.println("</td>");
            out.println("<td class=\"valores\">");
            String strType = "";
            if(rResType.getResourceMode()==1) strType=paramRequest.getLocaleString("msgTypeContent");
            if(rResType.getResourceMode()==2) strType=paramRequest.getLocaleString("msgTypeStrategy");
            if(rResType.getResourceMode()==3) strType=paramRequest.getLocaleString("msgTypeSystem");
            if(rResType.getResourceMode()==4) strType=paramRequest.getLocaleString("msgTypeInner");
            out.println(strType);
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</p>");
        }
        else{
            out.println("<p class=\"box\">");
            out.println("<font class=\"datos\">"+paramRequest.getLocaleString("msgInfoMissed")+"</font>");
            out.println("</p>");  
        }
    }
    
}
