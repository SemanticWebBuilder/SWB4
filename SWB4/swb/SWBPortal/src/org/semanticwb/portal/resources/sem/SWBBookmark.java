package org.semanticwb.portal.resources.sem;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.*;
import org.semanticwb.model.WebSite;
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
        } else if (mode.equals("ADMIN")) {

        } else if (mode.equals("DELALL")) {
            doDelAll(request, response, paramRequest);
        } else if (mode.equals("ADDNEW")) {
            doAddNew(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }

    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String mode = response.getMode();
        WebSite model = response.getTopic().getWebSite();

        if (mode.equals("ADDNEW")) {
            System.out.println(">>>>>adding...");
            System.out.println(">>>>>title: " + request.getParameter("title"));
            System.out.println(">>>>>url: " + request.getParameter("urllink"));
            System.out.print(">>>>>tags: " + request.getParameter("tags"));

            BookmarkEntry e = BookmarkEntry.createBookmarkEntry(model);
            e.setTitle(request.getParameter("title"));
            e.setUrl(request.getParameter("urllink"));
            e.setDescription(request.getParameter("description"));
            e.setTags(request.getParameter("tags"));
            e.setRank(Double.parseDouble(request.getParameter("score")));
            addEntry(e);
            response.setMode(response.Mode_VIEW);
        } else if (mode.equals("DELALL")) {
            removeAllEntry();
            response.setMode(response.Mode_VIEW);
        } else {
            super.processAction(request, response);
        }
    }

    

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        Iterator<BookmarkEntry> entries = listEntrys();
        int regNumber = 0;
        SWBResourceURL url = paramRequest.getRenderUrl();

        out.println("<div class=\"soria\" id=\"mainmenu\">");
        url.setMode("ADDNEW");
        out.println("<a href=\"" + url + "\">" + paramRequest.getLocaleString("add") + "</a> ");
        url.setMode("ADMIN");
        out.println("<a href=\"" + url + "\">" + paramRequest.getLocaleString("manage") + "</a> ");
        url.setMode("DELALL");
        out.println("<a href=\"" + url + "\">" + paramRequest.getLocaleString("delall") + "</a>");
        out.println("</div>");

        out.println("<div class=\"soria\" id =\"rview\">");
        out.println("<table>");
        while(entries.hasNext()) {
            BookmarkEntry en = entries.next();
            out.println("<tr id = \""+ regNumber++ + "\"><td>");
            out.print("<a href=\"" +  en.getUrl() + "\">" + en.getTitle() + "</a>");
            out.println(" - " + en.getUrl() + " - " + en.getCreated().toString() + "<BR>");
            out.print("[");
            out.print(en.getTags());
            out.print(" - ");
            out.print(en.getDescription() + "] ");
            out.println(en.getRank());
            url.setParameter("id", String.valueOf(regNumber));
            url.setMode(url.Mode_EDIT);
            out.print("<a href=\"" + url +"\">" + paramRequest.getLocaleString("edit") + "</a>");
            url.setMode("DELETE");
            out.print("<a href=\"" + url +"\">" + paramRequest.getLocaleString("delete") + "</a>");
            out.println("</tr></td>");
        }
        out.println("</table>");
        out.println("</div>");
    }

    public void doAddNew(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getActionUrl();
        //url.setAction("AADDNEW");
        //TODO: Cambiar por un FormManager
        out.print("<form id=\""+ getResourceBase().getId() + "/bookmark\" dojoType=\"dijit.form.Form\" class=\"swbform\" ");
        out.println("action=\"" + url + "\" method=\"POST\">");
        out.println("<label for=\"title\">"+ paramRequest.getLocaleString("title") +"</label>");
        out.println("<input type=\"text\" name=\"title\" id=\"title\" /><br>");
        out.println("<label for=\"urllink\">"+ paramRequest.getLocaleString("url") +"</label>");
        out.println("<input type=\"text\" name=\"urllink\" id=\"urllink\" value=\"http://\" /><br>");
        out.println("<label for=\"description\">"+ paramRequest.getLocaleString("desc") +"</label>");
        out.println("<textarea name=\"description\" id=\"description\"></textarea><br>");
        out.println("<label for=\"tags\">"+ paramRequest.getLocaleString("tags") +"</label>");
        out.println("<input type=\"text\" name=\"tags\" id=\"tags\" /><br>");
        out.println("<label for=\"score\">"+ paramRequest.getLocaleString("score") +"</label>");
        out.println("<input type=\"text\" name=\"score\" id=\"score\" /><br>");
        out.println("<button dojoType='dijit.form.Button' type=\"submit\">" + paramRequest.getLocaleString("add") + "</button>\n");
    }

    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

    }

    public void doDelAll(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getActionUrl();

        out.println("<PRE>Desea eliminar todos los registros?</PRE>");
        out.println("<a href=\"" + url.toString() + "\">" + paramRequest.getLocaleString("yes") + "</a>");
        url = paramRequest.getRenderUrl();
        url.setMode(url.Mode_VIEW);
        out.println("<a href=\"" + url.toString() + "\">" + paramRequest.getLocaleString("no") + "</a>");
    }
}
