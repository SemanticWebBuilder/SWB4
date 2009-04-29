package org.semanticwb.portal.resources.sem;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.servlet.http.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;

/**
 *
 * @author Hasdai Pacheco {haxdai(at)gmail.com}
 */
public class SWBBookmarks extends org.semanticwb.portal.resources.sem.base.SWBBookmarksBase {

    public static final int SORT_NOSORTED = 0;
    public static final int SORT_BYNAME = 1;
    public static final int SORT_BYTAGS = 2;
    public static final int SORT_BYDATE = 3;
    public static final int SORT_BYRANK = 4;
    public static final Comparator DATE_ORDER_ASC = new Comparator() {

        public int compare(Object arg0, Object arg1) {
            BookmarkEntry e1 = (BookmarkEntry) arg0;
            BookmarkEntry e2 = (BookmarkEntry) arg1;
            return e2.getCreated().compareTo(e1.getCreated());
        }
    };

    public static Comparator NAME_ORDER_DESC = new Comparator() {
        public int compare(Object arg0, Object arg1) {
            BookmarkEntry e1 = (BookmarkEntry) arg0;
            BookmarkEntry e2 = (BookmarkEntry) arg1;
            return -e2.getTitle().compareTo(e1.getTitle());
        }
    };

    public SWBBookmarks() {
    }

    public SWBBookmarks(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        SWBResourceURL url = paramRequest.getRenderUrl();
        String mode = url.getMode();

        if (mode.equals(url.Mode_EDIT)) {
            doEdit(request, response, paramRequest);
        } else if (mode.equals("ADDNEW")) {
            doAddNew(request, response, paramRequest);
        } else if (mode.equals("BYTAG")) {
            doByTag(request, response, paramRequest);
        } else if (mode.equals("NOTHING")) {
            doNothing(request, response, paramRequest);
        } else if (mode.equals("MANAGE")){
            doManage(request, response, paramRequest);
        } else if (mode.equals("EXIT")) {
            doView(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        WebSite model = response.getTopic().getWebSite();
        String mode = response.getMode();
        String tgs = request.getParameter("tags");
        String untaggedName = getResourceBase().getId() + "/untagged";
        String generalName = getResourceBase().getId() + "/general";
        Iterator<BookmarkGroup> groups = listGroups();
        BookmarkGroup group = null;

        if (mode.equals("ADDNEW")) {
            String tags[] = tgs.split(",");
            BookmarkEntry entry = BookmarkEntry.createBookmarkEntry(model);

            entry.setTitle(request.getParameter("title"));
            entry.setBookmarkURL(request.getParameter("urllink"));
            entry.setDescription(request.getParameter("description"));
            entry.setTags(tgs);

            if (tgs.trim().equals("")) {
                group = getGroupByName(untaggedName);
                if (group != null) {
                    group.addEntry(entry);
                }
            } else {
                for (int i = 0; i < tags.length; i++) {
                    group = getGroupByName(tags[i].trim());
                    if (group == null) {
                        group = BookmarkGroup.createBookmarkGroup(model);
                        group.setTitle(tags[i].trim());
                        group.addEntry(entry);
                        addGroup(group);
                    //System.out.println(">>>>>>>>>>>>>>>Agregado " + entry.getTitle() + " a grupo " + group.getTitle());
                    } else {
                        group.addEntry(entry);
                    //System.out.println(">>>>>>>>>>>>>>>Agregado " + entry.getTitle() + " a grupo " + group.getTitle());
                    }
                }
            }
            group = getGroupByName(generalName);
            if (group != null) {
                group.addEntry(entry);
            //System.out.println(">>>>>>>>>>>>>>>Agregado " + entry.getTitle() + " a grupo " + target.getTitle());
            }
            if(request.getParameter("level") != null && request.getParameter("level").equals("admin")) {
                response.setMode("MANAGE");
            } else {
                response.setMode(response.Mode_VIEW);
            }
        } else if (mode.equals("DELETE")) {
            while (groups.hasNext()) {
                group = groups.next();
                BookmarkEntry entry = group.getEntryById(request.getParameter("id"));

                if (entry != null) {
                    //System.out.println(">>>>>>>>>>>>>>>Eliminando " + entry.getTitle() + " de grupo " + target.getTitle());
                    group.removeEntry(entry);
                    if (group.getEntryCount() == 0 && (!group.getTitle().equals(generalName) && !group.getTitle().equals(untaggedName))) {
                        //System.out.println(">>>>>>>>>>>>>>>Grupo " + target.getTitle() + " eliminado");
                        removeGroup(group);
                    }
                }
            }
            response.setMode("MANAGE");
        } else if (mode.equals("DELALL")) {
            //removeAllEntry();
            while (groups.hasNext()) {
                group = groups.next();
                Iterator<BookmarkEntry> entries = group.listEntrys();
                while (entries.hasNext()) {
                    BookmarkEntry entry = entries.next();
                    group.removeEntry(entry);
                }
                if (group.getEntryCount() == 0 && (!group.getTitle().equals(generalName) && !group.getTitle().equals(untaggedName))) {
                    removeGroup(group);
                }
            }
            response.setMode("MANAGE");
        } else if (mode.equals(response.Mode_EDIT)) {
            group = getGroupByName(generalName);
            if (group != null) {
                BookmarkEntry entry = group.getEntryById(request.getParameter("id"));
                if (entry != null) {
                    entry.setTitle(request.getParameter("title"));
                    entry.setBookmarkURL(request.getParameter("urllink"));
                    entry.setDescription(request.getParameter("description"));
                    entry.setTags(request.getParameter("tags"));
                }
            }
            response.setMode("MANAGE");
        } else if (mode.equals("ORDER")) {
            setSortType(Integer.parseInt(request.getParameter("oType")));
            response.setMode("MANAGE");
        } else {
            super.processAction(request, response);
        }
    }

    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getRenderUrl();
        String urlString = paramRequest.getTopic().getRealUrl();

        url.setMode("ADDNEW");
        url.setParameter("url", urlString);
        out.println("<a href=\""+ url +"\">"+ paramRequest.getLocaleString("mark") +"</a> ");
        url.setMode("MANAGE");
        url.setParameter("level", "admin");
        out.println("<a href=\""+ url +"\">" + paramRequest.getLocaleString("manage") + "</a>");
    }

    public void doManage(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getActionUrl();
        WebSite model = paramRequest.getTopic().getWebSite();
        String untaggedName = getResourceBase().getId() + "/untagged";
        String generalName = getResourceBase().getId() + "/general";
        BookmarkGroup generalGp = getGroupByName(generalName);
        BookmarkGroup untagged = getGroupByName(untaggedName);
        Iterator<BookmarkGroup> groups = listGroups();
        int sType = getSortType();
        
        /*
        while (groups.hasNext()) {
            BookmarkGroup grp = groups.next();
            System.out.println("[" + grp.getTitle() + ", " + grp.getEntryCount() + "]");
        }*/

        if (sType == SORT_NOSORTED) {
            setSortType(SORT_BYDATE);
        }

        if (generalGp == null) {
            generalGp = BookmarkGroup.createBookmarkGroup(model);
            generalGp.setTitle(generalName);
            generalGp.setDescription("Group with all entries in addition order");
            addGroup(generalGp);
            //System.out.println(">>>>>>>>Grupo general creado");
        }
        if (untagged == null) {
            untagged = BookmarkGroup.createBookmarkGroup(model);
            untagged.setTitle(untaggedName);
            untagged.setDescription("Group with untagged entries");
            addGroup(untagged);
            //System.out.println(">>>>>>>>Grupo untagged creado");
        }

        out.println("<div id=\"container\">");
        out.print("<div id=\"header\"><h1>"+ paramRequest.getLocaleString("manage") +"</h1><div id=\"navmenu\">");
        url.setMode("ORDER");
        url.setParameter("oType", String.valueOf(SORT_BYNAME));
        out.print((sType==SORT_BYNAME)?"<b>":"<a href=\""+ url +"\">");
        out.print(paramRequest.getLocaleString("byname"));
        out.print((sType==SORT_BYNAME)?"</b> |":"</a> |");
        url.setParameter("oType", String.valueOf(SORT_BYDATE));
        out.print((sType==SORT_BYDATE)?"<b>":"<a href=\""+ url +"\">");
        out.print(paramRequest.getLocaleString("bydate"));
        out.print((sType==SORT_BYDATE)?"</b> |":"</a> |");
        url.setParameter("oType", String.valueOf(SORT_BYTAGS));
        out.print((sType==SORT_BYTAGS)?"<b>":"<a href=\""+ url +"\">");
        out.print(paramRequest.getLocaleString("bytag"));
        out.print((sType==SORT_BYTAGS)?"</b> |":"</a> |");
        url = paramRequest.getRenderUrl();
        url.setMode("EXIT");
        out.print("<a href=\""+ url +"\">" + paramRequest.getLocaleString("exit") + "</a>");
        out.println("</div></div>");
        out.println("<div id=\"wrapper\">");
        out.println("<div id=\"content\">");
        if (getSortType() != SORT_BYTAGS) {
            out.println(listEntriesByGroup(getSortType(), generalGp.getId(), paramRequest));
        } else {
            groups = listGroups();
            while (groups.hasNext()) {
                BookmarkGroup group = groups.next();
                if (!group.getTitle().equals(generalName)) {
                    if(group.getTitle().equals(untaggedName) && group.getEntryCount() > 0) {
                        out.println("<h1>" + paramRequest.getLocaleString("notags") + "</h1>");
                    } else if (!group.getTitle().equals(untaggedName)){
                        out.println("<h1>" + group.getTitle() + "</h1>");
                    }
                    //System.out.println("listando elementos de " + group.getTitle() + ":" + group.getSemanticObject().getId() + "->" + group.getEntryCount());
                    out.println(listEntriesByGroup(SORT_BYDATE, group.getSemanticObject().getId(), paramRequest));
                }
            }
        }
        out.println("</div></div>");
        out.println("<div id=\"navigation\">");
        url.setMode("BYTAG");
        while (groups.hasNext()) {
            BookmarkGroup group = groups.next();
            if (!group.getTitle().equals(generalName)) {
                url.setParameter("gid", group.getSemanticObject().getId());
                if (!group.getTitle().equals(untaggedName)) {
                    if (group.getEntryCount() > 0) {
                        //System.out.println(">>>>>>Desplegando elementos del grupo " + group.getTitle());
                        out.println("<b><a href=\"" + url + "\">" + group.getTitle() + "(" + group.getEntryCount() + ")" + "</a></b><br>");
                    }
                } else if (group.getEntryCount() > 0) {
                    //System.out.println(">>>>>>Desplegando elementos del grupo " + group.getTitle());
                    out.println("<b><a href=\"" + url + "\">" + paramRequest.getLocaleString("notags") + "(" + group.getEntryCount() + ")" + "</a></b><br>");
                }
            }
        }
        out.println("</div>");
        out.println("<div id=\"extra\">");
        url = paramRequest.getRenderUrl();
        url.setParameter("level","admin");
        url.setMode("ADDNEW");
        out.println("<a href=\"" + url + "\">" + paramRequest.getLocaleString("add") + "</a><br>");
        url.setMode("ADMIN");
        out.println("<a href=\"" + url + "\">" + paramRequest.getLocaleString("manage") + "</a><br>");
        if (generalGp.getEntryCount() > 0) {
            url = paramRequest.getActionUrl();
            url.setMode("DELALL");
            out.println("<a href=\"#\" onclick=\"if(confirm('"+ paramRequest.getLocaleString("msgRemoveAll") +"')){location='"+ url +"'} else {return false;}\">" + paramRequest.getLocaleString("delall") + "</a>");
        }
        out.println("</div>");
    }

    public void doByTag(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String gid = request.getParameter("gid");
        BookmarkGroup group = getGroupById(gid);
        SWBResourceURL url = paramRequest.getRenderUrl();

        if (group != null) {
            out.println("<div id=\"header\"><h1>" + group.getTitle() + "</h1></div>");
        }
        out.println("<div id=\"wrapper\">");
        out.println("<div id=\"content\">");
        out.println(listEntriesByGroup(getSortType(), gid, paramRequest));
        out.println("</div></div><div id=\"footer\">");
        url.setMode("MANAGE");
        out.println("<br><a href=\"" + url + "\">" + paramRequest.getLocaleString("back") + "</a>");
        out.println("</div>");
    }

    public void doAddNew(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getActionUrl();
        String lang = paramRequest.getUser().getLanguage();
        String title = BookmarkEntry.swb_title.getDisplayName(lang);
        String link = BookmarkEntry.swb_res_bkm_bookmarkURL.getDisplayName(lang);
        String desc = BookmarkEntry.swb_description.getDisplayName(lang);
        String tags = BookmarkEntry.swb_res_bkm_tags.getDisplayName(lang);

        if(request.getParameter("level") != null && request.getParameter("level").equals("admin")) {
            url.setMode("MANAGE");
        } else {
            url.setMode(url.Mode_VIEW);
        }
        //TODO: Cambiar por un FormManager
        out.println("<div class=\"swbform\">");
        out.print("<form id=\"" + getResourceBase().getId() + "/bookmark\" dojoType=\"dijit.form.Form\" class=\"swbform\" ");
        out.println("action=\"" + url + "\" method=\"post\">");
        out.println("<fieldset><b>"+ paramRequest.getLocaleString("add") + " " + BookmarkEntry.sclass.getDisplayName(lang) +"</b></fieldset>");
        out.println("<fieldset>");
        out.println("<label for=\"title\">" + title + "</label>");
        out.println("<input type=\"text\" name=\"title\" id=\"title\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura " + title + "\"></input><br>");
        out.println("<label for=\"urllink\">" + link + "</label>");
        out.println("<input type=\"text\" name=\"urllink\" id=\"urllink\" value=\""+ ((request.getParameter("url")==null)?"http://":request.getParameter("url")) +"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura " + link + "\"></input><br>");
        out.println("<label for=\"description\">" + desc + "</label>");
        out.println("<input type=\"text\" name=\"description\" id=\"description\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura " + desc + "\"></input><br>");
        out.println("<label for=\"tags\">" + tags + "</label>");
        out.println("<textarea name=\"tags\" id=\"tags\" dojoType=\"dijit.form.TextArea\" ></textarea><br>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("add") + "</button>");
        url = paramRequest.getRenderUrl();
        if(request.getParameter("level") != null && request.getParameter("level").equals("admin")) {
            url.setMode("MANAGE");
        } else {
            url.setMode(url.Mode_VIEW);
        }
        out.println("<button dojoType=\"dijit.form.Button\" onClick=\"location='" + url + "'\">" + paramRequest.getLocaleString("cancel") + "</button>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getActionUrl();
        String lang = paramRequest.getUser().getLanguage();
        String title = BookmarkEntry.swb_title.getDisplayName(lang);
        String link = BookmarkEntry.swb_res_bkm_bookmarkURL.getDisplayName(lang);
        String desc = BookmarkEntry.swb_description.getDisplayName(lang);
        String tags = BookmarkEntry.swb_res_bkm_tags.getDisplayName(lang);
        BookmarkGroup generalGp = getGroupByName(getResourceBase().getId() + "/general");

        if(generalGp == null) {
            return;
        }

        BookmarkEntry entry = generalGp.getEntryById(request.getParameter("id"));
        url.setMode(url.Mode_EDIT);
        //TODO: Cambiar por un FormManager
        out.print("<form id=\"" + getResourceBase().getId() + "/bookmark\" dojoType=\"dijit.form.Form\" class=\"swbform\" ");
        out.println("action=\"" + url + "\" method=\"post\">");
        out.println("<fieldset><b>"+ paramRequest.getLocaleString("edit") + " " + BookmarkEntry.sclass.getDisplayName(lang) +"</b></fieldset>");
        out.println("<fieldset>");
        out.println("<label for=\"title\">" + title + "</label>");
        out.println("<input type=\"text\" name=\"title\" id=\"title\" value=\""+ entry.getTitle() +"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura " + title + "\"></input><br>");
        out.println("<label for=\"urllink\">" + link + "</label>");
        out.println("<input type=\"text\" name=\"urllink\" id=\"urllink\" value=\""+ entry.getBookmarkURL() +"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura " + link + "\"></input><br>");
        out.println("<label for=\"description\">" + desc + "</label>");
        out.println("<input type=\"text\" name=\"description\" id=\"description\" value=\""+ entry.getDescription() +"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura " + desc + "\"></input><br>");
        out.println("<label for=\"tags\">" + tags + "</label>");
        out.println("<textarea name=\"tags\" id=\"tags\" dojoType=\"dijit.form.TextArea\" >"+ entry.getTags() +"</textarea><br>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("ok") + "</button>");
        url = paramRequest.getRenderUrl();
        url.setMode("MANAGE");
        url.setParameter("id", request.getParameter("id"));
        out.println("<button dojoType=\"dijit.form.Button\" onClick=\"location='" + url + "'\">" + paramRequest.getLocaleString("cancel") + "</button>");
        out.println("</fieldset>");
        out.println("</form>");
    }


    /**
     * Gets a BookmarkGroup with the given name.
     * @param name NAME of the BookmarkGroup to search for.
     * @return BookmarkGroup with NAME equals to name or null if BookmarkGroup
     * does not exist.
     */
    public BookmarkGroup getGroupByName(String name) {
        Iterator<BookmarkGroup> groups = listGroups();
        while (groups.hasNext()) {
            BookmarkGroup group = groups.next();

            if (group.getTitle().equals(name)) {
                return group;
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
        Iterator<BookmarkGroup> groups = listGroups();
        while (groups.hasNext()) {
            BookmarkGroup group = groups.next();

            if (group.getSemanticObject().getId().equals(id)) {
                return group;
            }
        }
        return null;
    }

    public String listEntriesByGroup(int sortType, String groupId, SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuffer sbf = new StringBuffer();
        SWBResourceURL url = paramRequest.getRenderUrl();
        ArrayList<BookmarkEntry> sEntries = new ArrayList<BookmarkEntry>();
        BookmarkGroup group = getGroupById(groupId);
        Format formatter = new SimpleDateFormat("dd MMM");

        if (group == null) {
            return "";
        }

        Iterator<BookmarkEntry> entries = group.listEntrys();
        while (entries.hasNext()) {
            BookmarkEntry entry = entries.next();
            sEntries.add(entry);
        }

        switch (sortType) {
            case SORT_BYDATE: {
                Collections.sort(sEntries, DATE_ORDER_ASC);
                break;
            }
            case SORT_BYNAME: {
                Collections.sort(sEntries, NAME_ORDER_DESC);
                break;
            }
        }

        sbf.append("<div id =\"rview\">\n");
        sbf.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n");
        sbf.append("<tbody>");
        entries = sEntries.iterator();
        while (entries.hasNext()) {
            BookmarkEntry entry = entries.next();
            String eId = entry.getSemanticObject().getId();
            //System.out.println("--" + entry.getTitle());
            sbf.append("<tr class = \"entry\" id=\"r_entry_"+ eId +"\">\n");
            sbf.append("<td><a title=\""+ entry.getBookmarkURL() +"\" href=\"" + entry.getBookmarkURL() + "\">" + entry.getTitle() + "</a>");
            sbf.append("&nbsp;-&nbsp;" + entry.getBookmarkURL() + "&nbsp;-&nbsp;" + formatter.format(entry.getCreated()) + "&nbsp;");
            url.setMode(url.Mode_EDIT);
            url.setParameter("id", eId);
            sbf.append("<a title=\""+ paramRequest.getLocaleString("edit") +"\" href=\"" + url + "\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\""+paramRequest.getLocaleString("edit")+"\"></a>&nbsp;");
            url = paramRequest.getActionUrl();
            url.setMode("DELETE");
            url.setParameter("id", eId);
            sbf.append("<a title=\""+ paramRequest.getLocaleString("delete") +"\" href=\"#\" onclick=\"if(confirm('"+ paramRequest.getLocaleString("msgRemove") +"')){location='"+ url +"'} else {return false;}\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\""+paramRequest.getLocaleString("delete")+"\"></a>");
            sbf.append("</td></tr>\n");
            sbf.append("<tr class=\"entry\" id=\"r_info_"+ eId +"\">\n<td>");
            String tags[] = entry.getTags().split(",");
            sbf.append("<div>[");
            url = paramRequest.getRenderUrl();
            for (int i = 0; i < tags.length; i++) {
                group = getGroupByName(tags[i].trim());
                if (group != null) {
                    url.setMode("BYTAG");
                    url.setParameter("gid", group.getSemanticObject().getId());
                    sbf.append("<a href=\"" + url + "\">" + group.getTitle() + "</a>");
                    if (i < tags.length - 1) {
                        sbf.append(", ");
                    }
                }
            }
            sbf.append(" - ");
            sbf.append(entry.getDescription() + "]</div>\n</td></tr>\n");
            sbf.append("<tr><td><br></td></tr>");
        }
        sbf.append("</tbody>");
        sbf.append("</table>\n");
        sbf.append("</div>\n");
        return sbf.toString();
    }

    public void doNothing(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("hello");
    }
}