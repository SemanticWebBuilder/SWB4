package org.semanticwb.portal.resources.sem;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.*;
import org.semanticwb.portal.api.*;

public class SWBBookmark extends org.semanticwb.portal.resources.sem.base.SWBBookmarkBase 
{

    public SWBBookmark()
    {
    }

    public SWBBookmark(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        SWBResourceURL url = paramRequest.getRenderUrl();
        String mode = url.getMode();
        
        if (mode.equals(url.Mode_EDIT)) {
            doEdit(request, response, paramRequest);
            url.setMode(url.Mode_VIEW);
        } else if (mode.equals("DELETE")) {
            doDelete(request, response, paramRequest);
            url.setMode(url.Mode_VIEW);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        Iterator<BookmarkEntry> entries = listEntrys();
        int regNumber = 0;
        SWBResourceURL url = paramRequest.getRenderUrl();

        out.println("<table>");
        while(entries.hasNext()) {
            BookmarkEntry en = entries.next();
            out.println("<tr id = \""+ regNumber++ + "\"><td>");
            out.print("<a href=\"" +  en.getUrl() + ">" + en.getTitle(paramRequest.getUser().getLanguage()) + "</a>");
            out.println(" - " + en.getUrl() + " - " + en.getCreated().toString() + "<BR>");
            out.print("[");
            Iterator<String> ts = en.listTags();
            while (ts.hasNext()) {
                out.print(ts.next() + " ");
            }
            out.print(" - ");
            out.print(en.getDescription(paramRequest.getUser().getLanguage()) + "] ");
            out.println(en.getScore());
            url.setParameter("id", String.valueOf(regNumber));
            url.setMode(url.Mode_EDIT);
            out.print("<a href=\"" + url +">Edit</a>");
            url.setMode("DELETE");
            out.print("<a href=\"" + url +">Eliminar</a>");
            out.println("</tr></td>");
        }
        out.println("</table>");
    }
}