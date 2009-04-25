package org.semanticwb.portal.resources.sem;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
    public static final Comparator DATE_ORDER = new Comparator() {

        public int compare(Object arg0, Object arg1) {
            BookmarkEntry e1 = (BookmarkEntry) arg0;
            BookmarkEntry e2 = (BookmarkEntry) arg1;
            return e2.getCreated().compareTo(e1.getCreated());
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
            url.setMode(url.Mode_VIEW);
        } else if (mode.equals("ADDNEW")) {
            doAddNew(request, response, paramRequest);
            url.setMode(url.Mode_VIEW);
        } else if (mode.equals("BYTAG")) {
            doByTag(request, response, paramRequest);
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
            response.setMode(response.Mode_VIEW);
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
            response.setMode(response.Mode_VIEW);
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
            response.setMode(response.Mode_VIEW);
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
            response.setMode(response.Mode_VIEW);
        } else {
            super.processAction(request, response);
        }
    }
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getRenderUrl();
        WebSite model = paramRequest.getTopic().getWebSite();
        String untaggedName = getResourceBase().getId() + "/untagged";
        String generalName = getResourceBase().getId() + "/general";
        BookmarkGroup generalGp = getGroupByName(generalName);
        BookmarkGroup untagged = getGroupByName(untaggedName);
        Iterator<BookmarkGroup> groups = listGroups();
        
        /*
        while (groups.hasNext()) {
            BookmarkGroup grp = groups.next();
            System.out.println("[" + grp.getTitle() + ", " + grp.getEntryCount() + "]");
        }*/

        if (getSortType() == SORT_NOSORTED) {
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

        out.println("<div class=\"soria\" id=\"mainmenu\">");
        url.setMode("BYTAG");
        while (groups.hasNext()) {
            BookmarkGroup group = groups.next();
            if (!group.getTitle().equals(generalName)) {
                url.setParameter("gid", group.getSemanticObject().getId());
                if (!group.getTitle().equals(untaggedName)) {
                    if (group.getEntryCount() > 0) {
                        //System.out.println(">>>>>>Desplegando elementos del grupo " + group.getTitle());
                        out.println("<a href=\"" + url + "\">" + group.getTitle() + "(" + group.getEntryCount() + ")" + "</a><br>");
                    }
                } else if (group.getEntryCount() > 0) {
                    System.out.println(">>>>>>Desplegando elementos del grupo " + group.getTitle());
                    //out.println("<a href=\"" + url + "\">" + paramRequest.getLocaleString("notags") + "(" + group.getEntryCount() + ")" + "</a><br>");
                }
            }
        }
        url = paramRequest.getRenderUrl();
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
        out.println(listEntriesByGroup(SORT_BYDATE, generalGp.getId(), paramRequest));
    }

    public void doByTag(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String gid = request.getParameter("gid");
        BookmarkGroup group = getGroupById(gid);
        SWBResourceURL url = paramRequest.getRenderUrl();

        if (group != null) {
            out.println("<h1>" + group.getTitle() + "</h1><br>");
        }
        //TODO: agregar sortType a cada grupo
        //TODO: agregar rdf:labels a hasGroup y sortType
        out.println(listEntriesByGroup(getSortType(), gid, paramRequest));

        url.setMode(url.Mode_VIEW);
        out.println("<br><a href=\"" + url + "\">" + paramRequest.getLocaleString("back") + "</a>");
    }

    public void doAddNew(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getActionUrl();
        String lang = paramRequest.getUser().getLanguage();
        String title = BookmarkEntry.swb_title.getDisplayName(lang);
        String link = BookmarkEntry.swb_res_bkm_bookmarkURL.getDisplayName(lang);
        String desc = BookmarkEntry.swb_description.getDisplayName(lang);
        String tags = BookmarkEntry.swb_res_bkm_tags.getDisplayName(lang);

        //TODO: Cambiar por un FormManager
        out.print("<form id=\"" + getResourceBase().getId() + "/bookmark\" dojoType=\"dijit.form.Form\" class=\"swbform\" ");
        out.println("action=\"" + url + "\" method=\"post\">");
        out.println("<fieldset><b>"+ paramRequest.getLocaleString("add") + " " + BookmarkEntry.sclass.getDisplayName(lang) +"</b></fieldset>");
        out.println("<fieldset>");
        out.println("<label for=\"title\">" + title + "</label>");
        out.println("<input type=\"text\" name=\"title\" id=\"title\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura " + title + "\"></input><br>");
        out.println("<label for=\"urllink\">" + link + "</label>");
        out.println("<input type=\"text\" name=\"urllink\" id=\"urllink\" value=\"http://\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura " + link + "\"></input><br>");
        out.println("<label for=\"description\">" + desc + "</label>");
        out.println("<input type=\"text\" name=\"description\" id=\"description\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura " + desc + "\"></input><br>");
        out.println("<label for=\"tags\">" + tags + "</label>");
        out.println("<textarea name=\"tags\" id=\"tags\" dojoType=\"dijit.form.TextArea\" ></textarea><br>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("add") + "</button>");
        url = paramRequest.getRenderUrl();
        url.setMode(url.Mode_VIEW);
        out.println("<button dojoType=\"dijit.form.Button\" onClick=\"location='" + url + "'\">" + paramRequest.getLocaleString("cancel") + "</button>");
        out.println("</fieldset>");
        out.println("</form>");
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
        url.setMode(url.Mode_VIEW);
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

        if (group == null) {
            return "";
        }

        switch (sortType) {
            case SORT_BYDATE: {
                Iterator<BookmarkEntry> entries = group.listEntrys();
                while (entries.hasNext()) {
                    BookmarkEntry entry = entries.next();
                    sEntries.add(entry);
                }
                break;
            }
        }

        sbf.append("<div class=\"soria\" id =\"rview\">");
        sbf.append("<table>");

        Iterator<BookmarkEntry> entries = sEntries.iterator();
        while (entries.hasNext()) {
            BookmarkEntry entry = entries.next();
            String eId = entry.getSemanticObject().getId();

            sbf.append("<tr class = \"entry\">");
            sbf.append("<td class=\"title\"><a href=\"" + entry.getBookmarkURL() + "\">" + entry.getTitle() + "</a></td>");
            sbf.append("<td>-</td><td class=\"url\">" + entry.getBookmarkURL() +
                    "</td><td>-</td><td class=\"date\">" + entry.getCreated().toString() + "</td>");
            sbf.append("<td>[</td><td class=\"tags\">" + entry.getTags().replace(",", "").trim() + "</td>");
            sbf.append("<td>-</td>");
            sbf.append("<td class=\"desc\">" + entry.getDescription() + "</td><td>]</td>");
            sbf.append("<td class=\"rank\">");
            sbf.append("</td>");
            url.setMode(url.Mode_EDIT);
            url.setParameter("id", eId);
            sbf.append("<td class=\"aedit\"><a href=\"" + url + "\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\""+paramRequest.getLocaleString("edit")+"\"></a></td>");
            url = paramRequest.getActionUrl();
            url.setMode("DELETE");
            url.setParameter("id", eId);
            sbf.append("<td class=\"adel\"><a href=\"#\" onclick=\"if(confirm('"+ paramRequest.getLocaleString("msgRemove") +"')){location='"+ url +"'} else {return false;}\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\""+paramRequest.getLocaleString("delete")+"\"></a></td>");
            sbf.append("</tr>");
        }
        sbf.append("</table>");
        sbf.append("</div>");
        return sbf.toString();
    }
}