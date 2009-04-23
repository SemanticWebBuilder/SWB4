package org.semanticwb.portal.resources.sem;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import javax.servlet.http.*;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;

public class SWBBookmarks extends org.semanticwb.portal.resources.sem.base.SWBBookmarksBase 
{
    public static final int SORT_NOSORTED = 0;
    public static final int SORT_BYNAME = 1;
    public static final int SORT_BYTAGS = 2;
    public static final int SORT_BYDATE = 3;
    public static final int SORT_BYRANK = 4;
    public static final Comparator DATE_ORDER = new Comparator() {
        public int compare(Object arg0, Object arg1) {
            BookmarkEntry e1 = (BookmarkEntry) arg0;
            BookmarkEntry e2 = (BookmarkEntry) arg1;
            return e2.getCreated().compareTo(e1.getCreated());
        }
    };

    public SWBBookmarks()
    {
        //initProps();
    }

    public SWBBookmarks(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
        //initProps();
    }


    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        SWBResourceURL url = paramRequest.getRenderUrl();
        String mode = url.getMode();

        /*if (mode.equals(url.Mode_EDIT)) {
            doEdit(request, response, paramRequest);
            url.setMode(url.Mode_VIEW);
        } else if (mode.equals("ADMIN")) {

        } else */if (mode.equals("DELALL")) {
            doDelAll(request, response, paramRequest);
            url.setMode(url.Mode_VIEW);
        } else if (mode.equals("ADDNEW")) {
            doAddNew(request, response, paramRequest);
            url.setMode(url.Mode_VIEW);
        } else if (mode.equals("BYTAG")) {
            doByTag(request, response, paramRequest);
        } else if (mode.equals("DELETE")) {
            doDelete(request, response, paramRequest);
            url.setMode(url.Mode_VIEW);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        WebSite model = response.getTopic().getWebSite();
        String mode = response.getMode();
        String tgs = request.getParameter("tags");
        BookmarkGroup target = null;

        if (mode.equals("ADDNEW")) {
            String tags[] = tgs.split(",");
            BookmarkEntry temp = BookmarkEntry.createBookmarkEntry(model);

            temp.setTitle(request.getParameter("title"));
            temp.setBookmarkURL(request.getParameter("urllink"));
            temp.setDescription(request.getParameter("description"));
            temp.setTags(tgs);
            temp.setRank(Double.parseDouble(request.getParameter("score")));

            if (tgs.trim().equals("")) {
                target = getGroupByName(getResourceBase().getId() + "/untagged");
                if (target != null) {
                    target.addEntry(temp);
                }
                response.setMode(response.Mode_VIEW);
            } else {

                for (int i = 0; i < tags.length; i++) {
                    target = getGroupByName(tags[i].trim());
                    if (target == null) {
                        BookmarkGroup tempg = BookmarkGroup.createBookmarkGroup(model);
                        tempg.setTitle(tags[i].trim());
                        tempg.addEntry(temp);
                        addGroup(tempg);
                        System.out.println(">>>>>>>>>>>>>>>Agregado " + temp.getTitle() + " a grupo " + tempg.getTitle());
                    } else {
                        System.out.println(">>>>>>>>>>>>>>>Agregado " + temp.getTitle() + " a grupo " + target.getTitle());
                        target.addEntry(temp);
                    }
                }
            }
            target = getGroupByName(getResourceBase().getId() + "/general");
            if (target != null) {
                target.addEntry(temp);
                System.out.println(">>>>>>>>>>>>>>>Agregado " + temp.getTitle() + " a grupo " + target.getTitle());
            }
            response.setMode(response.Mode_VIEW);
        } else if (mode.equals("DELETE")) {
            Iterator<BookmarkGroup> git = listGroups();
            while(git.hasNext()) {
                BookmarkGroup grp = git.next();
                System.out.println("<<<<--------->>>buscando " + request.getParameter("id") + " en grupo " + grp.getTitle());
                BookmarkEntry temp = grp.getEntryById(request.getParameter("id"));

                if (temp!=null) {
                    System.out.println(">>>>>>>>>>>>>>>Eliminando " + temp.getTitle() + " de grupo " + grp.getTitle());
                    grp.removeEntry(temp);
                    if(grp.getEntryCount() == 0  && !grp.getTitle().equals(getResourceBase().getId() + "/general")) {
                        System.out.println(">>>>>>>>>>>>>>>Grupo " + grp.getTitle() + " eliminado");
                        removeGroup(grp);
                    }
                }
            }
            response.setMode(response.Mode_VIEW);
        }else if (mode.equals("DELALL")) {
            //removeAllEntry();
            Iterator<BookmarkGroup> groups = listGroups();

            while (groups.hasNext()) {
                BookmarkGroup grp = groups.next();
                Iterator<BookmarkEntry> entries = grp.listEntrys();
                while (entries.hasNext()) {
                    BookmarkEntry en = entries.next();
                    grp.removeEntry(en);
                }
                if (grp.getEntryCount() == 0 && !grp.getTitle().equals(getResourceBase().getId() + "/general")) {
                    removeGroup(grp);
                }
            }
            response.setMode(response.Mode_VIEW);
        } /*else if (mode.equals(response.Mode_EDIT)) {
            BookmarkEntry temp = getEntriById(request.getParameter("id"));

            if (temp!=null) {
                temp.setTitle(request.getParameter("title"));
                temp.setUrl(request.getParameter("urllink"));
                temp.setDescription(request.getParameter("description"));
                temp.setTags(request.getParameter("tags"));
                temp.setRank(Double.parseDouble(request.getParameter("score")));
            }
            response.setMode(response.Mode_VIEW);
        } */else {
            super.processAction(request, response);
        }
    }

    public void doDelAll(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getActionUrl();

        //TODO: cambiar por un dialogo
        out.println("<PRE>Desea eliminar todos los registros?</PRE>");
        out.println("<a href=\"" + url.toString() + "\">" + paramRequest.getLocaleString("yes") + "</a>");
        url = paramRequest.getRenderUrl();
        url.setMode(url.Mode_VIEW);
        out.println("<a href=\"" + url.toString() + "\">" + paramRequest.getLocaleString("no") + "</a>");
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getActionUrl();
        //TODO: cambiar por un diálogo
        out.println("<PRE>Desea eliminar el registro?</PRE>");
        url.setParameter("id", request.getParameter("id"));
        out.println("<a href=\"" + url.toString() + "\">" + paramRequest.getLocaleString("yes") + "</a>");
        url = paramRequest.getRenderUrl();
        url.setMode(url.Mode_VIEW);
        out.println("<a href=\"" + url.toString() + "\">" + paramRequest.getLocaleString("no") + "</a>");
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getRenderUrl();
        WebSite model = paramRequest.getTopic().getWebSite();
        BookmarkGroup generalGp = getGroupByName(getResourceBase().getId() + "/general");
        BookmarkGroup untagged = getGroupByName(getResourceBase().getId() + "/untagged");

        Iterator<BookmarkGroup> git = listGroups();
        while (git.hasNext()) {
            BookmarkGroup grp = git.next();
            System.out.println("["+grp.getTitle()+", "+ grp.getEntryCount() +"]");
        }

        if(getSortType() == SORT_NOSORTED) {
            setSortType(SORT_BYDATE);
        }

        if (generalGp == null) {
            generalGp = BookmarkGroup.createBookmarkGroup(model);
            generalGp.setTitle(getResourceBase().getId() + "/general");
            generalGp.setDescription("Group with all entries in addition order");
            addGroup(generalGp);
            System.out.println(">>>>>>>>Grupo general creado");
        }
        if (untagged == null) {
            untagged = BookmarkGroup.createBookmarkGroup(model);
            untagged.setTitle(getResourceBase().getId() + "/untagged");
            untagged.setDescription("Group with untagged entries");
            addGroup(untagged);
            System.out.println(">>>>>>>>Grupo untagged creado");
        }

        out.println("<div class=\"soria\" id=\"mainmenu\">");
        url.setMode("BYTAG");
        git = listGroups();
        while (git.hasNext()) {
            BookmarkGroup temp = git.next();
            if (!temp.getTitle().equals(getResourceBase().getId() + "/general")) {
                url.setParameter("gid", temp.getSemanticObject().getId());
                if (!temp.getTitle().equals(getResourceBase().getId() + "/untagged")) {
                    if (temp.getEntryCount() > 0) {
                        System.out.println(">>>>>>Desplegando elementos del grupo " + temp.getTitle());
                        out.println("<a href=\"" + url + "\">" + temp.getTitle() + "(" + temp.getEntryCount() + ")" + "</a><br>");
                    }
                } else if (temp.getEntryCount() > 0) {
                    System.out.println(">>>>>>Desplegando elementos del grupo " + temp.getTitle());
                    out.println("<a href=\"" + url + "\">" + paramRequest.getLocaleString("notags") + "(" + temp.getEntryCount() + ")" + "</a><br>");
                }
            }
        }
        url.setMode("ADDNEW");
        out.println("<a href=\"" + url + "\">" + paramRequest.getLocaleString("add") + "</a><br>");
        url.setMode("ADMIN");
        out.println("<a href=\"" + url + "\">" + paramRequest.getLocaleString("manage") + "</a><br>");
        url.setMode("DELALL");
        out.println("<a href=\"" + url + "\">" + paramRequest.getLocaleString("delall") + "</a><br>");
        out.println("</div>");
        out.println(createResultsView(getSortType(), paramRequest));
    }

    public void doByTag(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String gid = request.getParameter("gid");
        BookmarkGroup grp = getGroupById(gid);
        StringBuffer sbf = new StringBuffer();
        SWBResourceURL url = paramRequest.getRenderUrl();

        sbf.append("<div class=\"soria\" id =\"rview\">");
        sbf.append("<table>");

        if (grp != null) {
            out.println("<h1>" + grp.getTitle() + "</h1><br>");

            Iterator<BookmarkEntry> eit = grp.listEntrys();
            while (eit.hasNext()) {
                BookmarkEntry en = eit.next();
                String eId = en.getSemanticObject().getId();

                sbf.append("<tr class = \"entry\">");
                sbf.append("<td class=\"title\"><a href=\"" + en.getBookmarkURL() + "\">" + en.getTitle() + "</a></td>");
                sbf.append("<td>-</td><td class=\"url\">" + en.getBookmarkURL() +
                        "</td><td>-</td><td class=\"date\">" + en.getCreated().toString() + "</td>");
                sbf.append("<td>[</td><td class=\"tags\">" + en.getTags().replace(",", "").trim() + "</td>");
                sbf.append("<td>-</td>");
                sbf.append("<td class=\"desc\">" + en.getDescription() + "</td><td>]</td>");
                sbf.append("<td class=\"rank\">");
                for (double i = 0; i < en.getRank(); i++) {
                    sbf.append("*");
                }
                sbf.append("</td>");
                url.setMode(url.Mode_EDIT);
                url.setParameter("id", eId);
                sbf.append("<td class=\"aedit\"><a href=\"" + url + "\">" + paramRequest.getLocaleString("edit") + "</a></td><td>-</td>");
                url.setMode("DELETE");
                url.setParameter("id", eId);
                sbf.append("<td class=\"adel\"><a href=\"" + url + "\">" + paramRequest.getLocaleString("delete") + "</a></td>");
                sbf.append("</tr>");
            }
            sbf.append("</table>");
            sbf.append("</div>");
        }
        url.setMode(url.Mode_VIEW);
        sbf.append("<a href=\"" + url + "\">" + paramRequest.getLocaleString("back") + "</a><br>");
        out.println(sbf.toString());
    }

    public void doAddNew(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getActionUrl();

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
        out.println("<select name=\"score\" id=\"score\" /><br>");
        out.println("<option>1</option><br>");
        out.println("<option>2</option><br>");
        out.println("<option>3</option><br>");
        out.println("<option>4</option><br>");
        out.println("<option>5</option><br>");
        out.println("</select><br>");
        out.println("<button dojoType='dijit.form.Button' type=\"submit\">" + paramRequest.getLocaleString("add") + "</button>\n");
        url = paramRequest.getRenderUrl();
        url.setMode(url.Mode_VIEW);
        out.println("<button dojoType='dijit.form.Button' onClick=\"location='" + url + "'\">" + paramRequest.getLocaleString("cancel") + "</button>\n");
    }

    /**
     * Gets a BookmarkGroup with the given name.
     * @param name NAME of the BookmarkGroup to search for.
     * @return BookmarkGroup with NAME equals to name or null if BookmarkGroup
     * does not exist.
     */
    public BookmarkGroup getGroupByName(String name) {
        Iterator<BookmarkGroup> git = listGroups();
        while (git.hasNext()) {
            BookmarkGroup gp = git.next();

            if (gp.getTitle().equals(name)) {
                return gp;
            }
        }
        return null;
    }

    /**
     * Gets a BookmarkGroup with the given ID.
     * @param id ID of the BookmarkGroup to search for.
     * @return BookmarkGroup with ID equals to id or null if BookmarkGroup
     * does not exist.
     */
    public BookmarkGroup getGroupById(String id) {
        Iterator<BookmarkGroup> git = listGroups();
        while (git.hasNext()) {
            BookmarkGroup gp = git.next();

            if (gp.getSemanticObject().getId().equals(id)) {
                return gp;
            }
        }
        return null;
    }

    public String createResultsView(int sortType, SWBParamRequest paramRequest) throws SWBResourceException {
        SWBResourceURL url = paramRequest.getRenderUrl();
        WebSite model = paramRequest.getTopic().getWebSite();
        ArrayList<BookmarkEntry> ents = new ArrayList<BookmarkEntry>();
        Iterator<BookmarkGroup> groups = listGroups();
        BookmarkGroup generalGp = getGroupByName(getResourceBase().getId() + "/general");
        BookmarkGroup untagged = getGroupByName(getResourceBase().getId() + "/untagged");

        switch(sortType) {
            case SORT_BYDATE: {
                if (generalGp != null) {
                    Iterator<BookmarkEntry> entries = generalGp.listEntrys();
                    while (entries.hasNext()) {
                        BookmarkEntry entry = entries.next();
                        ents.add(entry);
                    }
                }
                break;
            }
        }
        
        StringBuffer sbf = new StringBuffer();
        sbf.append("<div class=\"soria\" id =\"rview\">");
        sbf.append("<table>");

        Iterator<BookmarkEntry> eit = ents.iterator();
        while(eit.hasNext()) {
            BookmarkEntry en = eit.next();
            String eId = en.getSemanticObject().getId();

            sbf.append("<tr class = \"entry\">");
            sbf.append("<td class=\"title\"><a href=\"" +  en.getBookmarkURL() + "\">" + en.getTitle() + "</a></td>");
            sbf.append("<td>-</td><td class=\"url\">" + en.getBookmarkURL() +
                    "</td><td>-</td><td class=\"date\">" + en.getCreated().toString() + "</td>");
            sbf.append("<td>[</td><td class=\"tags\">" + en.getTags().replace(",", "").trim() + "</td>");
            sbf.append("<td>-</td>");
            sbf.append("<td class=\"desc\">" + en.getDescription() + "</td><td>]</td>");
            sbf.append("<td class=\"rank\">");
            for (double i = 0; i< en.getRank(); i++) {
                sbf.append("*");
            }
            sbf.append("</td>");
            url.setMode(url.Mode_EDIT);
            url.setParameter("id", eId);
            sbf.append("<td class=\"aedit\"><a href=\"" + url +"\">" + paramRequest.getLocaleString("edit") + "</a></td><td>-</td>");
            url.setMode("DELETE");
            url.setParameter("id", eId);
            sbf.append("<td class=\"adel\"><a href=\"" + url +"\">" + paramRequest.getLocaleString("delete") + "</a></td>");
            sbf.append("</tr>");
        }
        sbf.append("</table>");
        sbf.append("</div>");
        return sbf.toString();
    }
}