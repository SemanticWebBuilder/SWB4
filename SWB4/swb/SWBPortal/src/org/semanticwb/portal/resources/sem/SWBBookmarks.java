package org.semanticwb.portal.resources.sem;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.*;
import org.semanticwb.portal.api.*;

public class SWBBookmarks extends org.semanticwb.portal.resources.sem.base.SWBBookmarksBase 
{

    public SWBBookmarks()
    {
    }

    public SWBBookmarks(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getRenderUrl();
        Iterator<BookmarkGroup> groups = listGroups();

        out.println("<PRE>"+ getSortType() +"</PRE>");
        out.println("<div class=\"soria\" id=\"mainmenu\">");
        url.setMode("ADDNEW");
        out.println("<a href=\"" + url + "\">" + paramRequest.getLocaleString("add") + "</a> ");
        url.setMode("ADMIN");
        out.println("<a href=\"" + url + "\">" + paramRequest.getLocaleString("manage") + "</a> ");
        url.setMode("DELALL");
        out.println("<a href=\"" + url + "\">" + paramRequest.getLocaleString("delall") + "</a>");
        out.println("</div>");

        /*out.println("<div class=\"soria\" id =\"rview\">");
        out.println("<table>");
        while (groups.hasNext()) {
            BookmarkGroup grp = groups.next();
            
        }*/
    }    
}
