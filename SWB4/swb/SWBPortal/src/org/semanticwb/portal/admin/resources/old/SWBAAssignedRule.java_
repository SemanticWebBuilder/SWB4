/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.Rule;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author juan.fernandez
 */
public class SWBAAssignedRule extends GenericResource{

     /** Creates a new instance of SWBAAssignedRule */
    public SWBAAssignedRule()
    {
    }

    /**
     * User view of WBAAssignedRules resource
     * @param request parameters
     * @param response answer to the request
     * @param paramRequest a list of objects (user, WebPage, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String tmid = request.getParameter("tm");
        String id = request.getParameter("id");
        if(request.getParameter("id")!=null)
        {
            id = request.getParameter("id");
            Rule hmtps = SWBContext.getWebSite(tmid).getRule(id);
            Iterator itt = hmtps.listRelatedObjects();
            
            out.println("<p align=center class=\"box\">");
            out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<tr ><td class=\"tabla\">"+paramRequest.getLocaleString("msgSection")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("msgSite")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("msgIdentifier")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("msgCreationDate")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("msgLastModification")+"</td><td class=\"tabla\" align=center>"+paramRequest.getLocaleString("msgStatus")+"<br>"+paramRequest.getLocaleString("msgSection")+" / "+ paramRequest.getLocaleString("msgRule")+"</td></tr>");
            String rowColor="";
            boolean cambiaColor = true;
            while(itt.hasNext())
            {
                Object obj = itt.next();
                if(obj instanceof WebPage)
                {
                    WebPage tp = (WebPage)obj;
                    boolean tp_active = tp.getRuleRef().isActive();

                    WebSite tm=tp.getWebSite();
                    String strActive = "<font color=\"green\">"+paramRequest.getLocaleString("msgOnLine")+"</font>";
                    if(!tp.isActive()) strActive = "<font color=\"red\">"+paramRequest.getLocaleString("msgOffLine")+"</font>";
                    String strActiveTpl = "<font color=\"green\">"+paramRequest.getLocaleString("msgOnLine")+"</font>";
                    if(!tp_active) strActiveTpl = "<font color=\"red\">"+paramRequest.getLocaleString("msgOffLine")+"</font>";
                    rowColor="#EFEDEC";
                    if(!cambiaColor) rowColor="#FFFFFF";
                    cambiaColor = !(cambiaColor);
                    out.println("<tr bgcolor=\""+rowColor+"\"><td  class=\"valores\">"+tp.getTitle(paramRequest.getUser().getLanguage())+"</td><td class=\"valores\">"+tm.getTitle()+"</td><td class=\"valores\">"+tp.getId()+"</td><td class=\"valores\">"+tp.getCreated()+"</td><td class=\"valores\">"+tp.getUpdated()+"</td><td class=\"valores\" align=center>"+strActive+" / "+strActiveTpl+"</td></tr>");
                }
            }
            out.println("</TABLE></P>");
        }
    }
    
}
