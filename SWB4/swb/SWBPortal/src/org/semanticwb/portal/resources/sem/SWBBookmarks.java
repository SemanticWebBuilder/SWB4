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

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        String urlString = paramRequest.getTopic().getRealUrl();
        System.out.println(">>>>>>>>>>>" + paramRequest.getTopic().getWebPageURL());
        StringBuffer sbf = new StringBuffer();

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        rUrl.setMode("ADDNEW");
        sbf.append("<script type=\"text/javascript\">\n");
        sbf.append("dojo.require(\"dijit.Dialog\");\n" +
                    "var dojoDlg;\n" +
                    "dojo.addOnLoad(function () {\n" +
                        "dojoDlg = new dijit.Dialog({\n" +
                        "title: \"dialog\"\n" +
                        "})\n" +
                    "});\n");
        sbf.append("function showDialog() {\n" +
                        "dojo.xhrGet({\n" +
                            "url: '" + rUrl +"',\n" +
                            "load:function(response){\n" +
                                "dojoDlg.attr(\"content\", response);\n" +
                                "dojoDlg.show();\n" +
                            "},\n" +
                            "error: function(response) {\nreturn response;\n},\nhandleAs: \"text\"\n" +
                        "});\n" +
                    "}\n");
        sbf.append("</script>");

        sbf.append("<a href=\"#\" onclick=\"showDialog();\">Dialog</a> ");
        rUrl.setParameter("url", urlString);
        sbf.append("<a href=\""+ rUrl +"\">"+ paramRequest.getLocaleString("mark") +"</a> ");
        rUrl.setMode("MANAGE");
        rUrl.setParameter("level", "admin");
        sbf.append("<a href=\""+ rUrl +"\">" + paramRequest.getLocaleString("manage") + "</a>");
        out.println(sbf.toString());
    }

    public void doManage(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        WebSite model = paramRequest.getTopic().getWebSite();
        String untaggedName = getResourceBase().getId() + "/untagged";
        String generalName = getResourceBase().getId() + "/general";
        BookmarkGroup generalGp = getGroupByName(generalName);
        BookmarkGroup untagged = getGroupByName(untaggedName);
        Iterator<BookmarkGroup> groups = listGroups();
        int sType = getSortType();
        StringBuffer sbf = new StringBuffer();

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
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

        sbf.append("<div id=\"container\">");
        sbf.append("<div id=\"header\"><h1>"+ paramRequest.getLocaleString("manage") +"</h1><div id=\"navmenu\">");
        aUrl.setMode("ORDER");
        aUrl.setParameter("oType", String.valueOf(SORT_BYNAME));
        sbf.append((sType==SORT_BYNAME)?"<b>":"<a href=\""+ aUrl +"\">");
        sbf.append(paramRequest.getLocaleString("byname"));
        sbf.append((sType==SORT_BYNAME)?"</b> |":"</a> |");
        aUrl.setParameter("oType", String.valueOf(SORT_BYDATE));
        sbf.append((sType==SORT_BYDATE)?"<b>":"<a href=\""+ aUrl +"\">");
        sbf.append(paramRequest.getLocaleString("bydate"));
        sbf.append((sType==SORT_BYDATE)?"</b> |":"</a> |");
        aUrl.setParameter("oType", String.valueOf(SORT_BYTAGS));
        sbf.append((sType==SORT_BYTAGS)?"<b>":"<a href=\""+ aUrl +"\">");
        sbf.append(paramRequest.getLocaleString("bytag"));
        sbf.append((sType==SORT_BYTAGS)?"</b> |":"</a> |");
        rUrl.setMode("EXIT");
        sbf.append("<a href=\""+ rUrl +"\">" + paramRequest.getLocaleString("exit") + "</a>");
        sbf.append("</div></div>");
        sbf.append("<div id=\"wrapper\">");
        sbf.append("<div id=\"content\">");

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
        rUrl.setMode("BYTAG");
        while (groups.hasNext()) {
            BookmarkGroup group = groups.next();
            if (!group.getTitle().equals(generalName)) {
                rUrl.setParameter("gid", group.getSemanticObject().getId());
                if (!group.getTitle().equals(untaggedName)) {
                    if (group.getEntryCount() > 0) {
                        //System.out.println(">>>>>>Desplegando elementos del grupo " + group.getTitle());
                        sbf.append("<b><a href=\"" + rUrl + "\">" + group.getTitle() + "(" + group.getEntryCount() + ")" + "</a></b><br>");
                    }
                } else if (group.getEntryCount() > 0) {
                    //System.out.println(">>>>>>Desplegando elementos del grupo " + group.getTitle());
                    sbf.append("<b><a href=\"" + rUrl + "\">" + paramRequest.getLocaleString("notags") + "(" + group.getEntryCount() + ")" + "</a></b><br>");
                }
            }
        }
        sbf.append("</div>");
        sbf.append("<div id=\"extra\">");
        rUrl.setParameter("level","admin");
        rUrl.setMode("ADDNEW");
        sbf.append("<a href=\"" + rUrl + "\">" + paramRequest.getLocaleString("add") + "</a><br>");
        if (generalGp.getEntryCount() > 0) {
            aUrl.setMode("DELALL");
            sbf.append("<a href=\"#\" onclick=\"if(confirm('"+ paramRequest.getLocaleString("msgRemoveAll") +"')){location='"+ aUrl +"'} else {return false;}\">" + paramRequest.getLocaleString("delall") + "</a>");
        }
        sbf.append("</div>");
        out.println(sbf.toString());
    }

    public void doByTag(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String gid = request.getParameter("gid");
        BookmarkGroup group = getGroupById(gid);
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        StringBuffer sbf = new StringBuffer();

        if (group == null) {
            return;
        }

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        sbf.append("<div id=\"header\"><h1>" + group.getTitle() + "</h1></div>");
        out.println("<div id=\"wrapper\">");
        out.println("<div id=\"content\">");
        out.println(listEntriesByGroup(getSortType(), gid, paramRequest));
        out.println("</div></div><div id=\"footer\">");
        rUrl.setMode("MANAGE");
        out.println("<br><a href=\"" + rUrl + "\">" + paramRequest.getLocaleString("back") + "</a>");
        out.println("</div>");
    }

    public void doAddNew(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        String lang = paramRequest.getUser().getLanguage();
        String title = BookmarkEntry.swb_title.getDisplayName(lang);
        String link = BookmarkEntry.swb_res_bkm_bookmarkURL.getDisplayName(lang);
        String desc = BookmarkEntry.swb_description.getDisplayName(lang);
        String tags = BookmarkEntry.swb_res_bkm_tags.getDisplayName(lang);
        StringBuffer sbf = new StringBuffer();

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        sbf.append("<script type=\"text/javascript\">");
        sbf.append("dojo.require(\"dijit.form.Form\");");
        sbf.append("dojo.require(\"dijit.form.Button\");");
        sbf.append("</script>");

        aUrl.setMode("ADDNEW");
        //TODO: Cambiar por un FormManager
        sbf.append("<div class=\"swbform\">");
        sbf.append("<form id=\"" + getResourceBase().getId() + "/bookmark\" dojoType=\"dijit.form.Form\" ");
        sbf.append("action=\"" + aUrl + "\" method=\"post\">");
        sbf.append("<fieldset><b>"+ paramRequest.getLocaleString("add") + " " + BookmarkEntry.sclass.getDisplayName(lang) +"</b></fieldset>");
        sbf.append("<fieldset>");
        sbf.append("<label for=\"title\">" + title + "</label>");
        sbf.append("<input type=\"text\" name=\"title\" id=\"title\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura " + title + "\"></input><br>");
        sbf.append("<label for=\"urllink\">" + link + "</label>");
        sbf.append("<input type=\"text\" name=\"urllink\" id=\"urllink\" value=\""+ ((request.getParameter("url")==null)?"http://":request.getParameter("url")) +"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura " + link + "\"></input><br>");
        sbf.append("<label for=\"description\">" + desc + "</label>");
        sbf.append("<input type=\"text\" name=\"description\" id=\"description\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura " + desc + "\"></input><br>");
        sbf.append("<label for=\"tags\">" + tags + "</label>");
        sbf.append("<textarea name=\"tags\" id=\"tags\" dojoType=\"dijit.form.TextArea\" ></textarea><br>");
        sbf.append("</fieldset>");
        sbf.append("<fieldset>");
        sbf.append("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("add") + "</button>");
        sbf.append("</fieldset>");
        sbf.append("</form>");
        sbf.append("</div>");
        out.println(sbf.toString());
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        String lang = paramRequest.getUser().getLanguage();
        String title = BookmarkEntry.swb_title.getDisplayName(lang);
        String link = BookmarkEntry.swb_res_bkm_bookmarkURL.getDisplayName(lang);
        String desc = BookmarkEntry.swb_description.getDisplayName(lang);
        String tags = BookmarkEntry.swb_res_bkm_tags.getDisplayName(lang);
        BookmarkGroup generalGp = getGroupByName(getResourceBase().getId() + "/general");
        StringBuffer sbf = new StringBuffer();

        if(generalGp == null) {
            return;
        }

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        BookmarkEntry entry = generalGp.getEntryById(request.getParameter("id"));
        aUrl.setMode(aUrl.Mode_EDIT);
        //TODO: Cambiar por un FormManager
        sbf.append("<form id=\"" + getResourceBase().getId() + "/bookmark\" dojoType=\"dijit.form.Form\" class=\"swbform\" ");
        sbf.append("action=\"" + aUrl + "\" method=\"post\">");
        sbf.append("<fieldset><b>"+ paramRequest.getLocaleString("edit") + " " + BookmarkEntry.sclass.getDisplayName(lang) +"</b></fieldset>");
        sbf.append("<fieldset>");
        sbf.append("<label for=\"title\">" + title + "</label>");
        sbf.append("<input type=\"text\" name=\"title\" id=\"title\" value=\""+ entry.getTitle() +"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura " + title + "\"></input><br>");
        sbf.append("<label for=\"urllink\">" + link + "</label>");
        sbf.append("<input type=\"text\" name=\"urllink\" id=\"urllink\" value=\""+ entry.getBookmarkURL() +"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura " + link + "\"></input><br>");
        sbf.append("<label for=\"description\">" + desc + "</label>");
        sbf.append("<input type=\"text\" name=\"description\" id=\"description\" value=\""+ entry.getDescription() +"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura " + desc + "\"></input><br>");
        sbf.append("<label for=\"tags\">" + tags + "</label>");
        sbf.append("<textarea name=\"tags\" id=\"tags\" dojoType=\"dijit.form.TextArea\" >"+ entry.getTags() +"</textarea><br>");
        sbf.append("</fieldset>");
        sbf.append("<fieldset>");
        sbf.append("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("ok") + "</button>");
        sbf.append("</fieldset>");
        sbf.append("</form>");
        out.println(sbf.toString());
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
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        SWBResourceURL aUrl = paramRequest.getActionUrl();

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
            rUrl.setMode(rUrl.Mode_EDIT);
            rUrl.setParameter("id", eId);
            sbf.append("<a title=\""+ paramRequest.getLocaleString("edit") +"\" href=\"" + rUrl + "\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\""+paramRequest.getLocaleString("edit")+"\"></a>&nbsp;");
            aUrl.setMode("DELETE");
            aUrl.setParameter("id", eId);
            sbf.append("<a title=\""+ paramRequest.getLocaleString("delete") +"\" href=\"#\" onclick=\"if(confirm('"+ paramRequest.getLocaleString("msgRemove") +"')){location='"+ aUrl +"'} else {return false;}\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\""+paramRequest.getLocaleString("delete")+"\"></a>");
            sbf.append("</td></tr>\n");
            sbf.append("<tr class=\"entry\" id=\"r_info_"+ eId +"\">\n<td>");
            String tags[] = entry.getTags().split(",");
            sbf.append("<div>[");
            for (int i = 0; i < tags.length; i++) {
                group = getGroupByName(tags[i].trim());
                if (group != null) {
                    rUrl.setMode("BYTAG");
                    rUrl.setParameter("gid", group.getSemanticObject().getId());
                    sbf.append("<a href=\"" + rUrl + "\">" + group.getTitle() + "</a>");
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