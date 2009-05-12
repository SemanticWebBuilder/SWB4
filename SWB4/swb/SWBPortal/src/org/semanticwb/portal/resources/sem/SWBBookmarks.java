package org.semanticwb.portal.resources.sem;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;

/**
 *
 * @author Hasdai Pacheco {haxdai(at)gmail.com}
 * A Bookmarks manager. The bookmarks are sorted in groups.
 */
public class SWBBookmarks extends org.semanticwb.portal.resources.sem.base.SWBBookmarksBase {

    /**Undefined sorting constant.*/
    public static final int SORT_NOSORTED = 0;
    /**Name-based sorting constant.*/
    public static final int SORT_BYNAME = 1;
    /**Tags-based sorting constant*/
    public static final int SORT_BYTAGS = 2;
    /**Date-based sorting constant*/
    public static final int SORT_BYDATE = 3;
    /**BookmarkEntry date-based comparator.*/
    public static final Comparator DATE_ORDER_ASC = new Comparator() {

        public int compare(Object arg0, Object arg1) {
            BookmarkEntry e1 = (BookmarkEntry) arg0;
            BookmarkEntry e2 = (BookmarkEntry) arg1;
            return e2.getCreated().compareTo(e1.getCreated());
        }
    };
    /**BookmarkEntry name-based comparator.*/
    public static Comparator NAME_ORDER_DESC = new Comparator() {
        public int compare(Object arg0, Object arg1) {
            BookmarkEntry e1 = (BookmarkEntry) arg0;
            BookmarkEntry e2 = (BookmarkEntry) arg1;
            return -e2.getTitle().compareTo(e1.getTitle());
        }
    };

    /**
     * Default constructor.
     */
    public SWBBookmarks() {
    }

    /**
     * Default constructor.
     */
    public SWBBookmarks(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
         
        if (mode.equals(paramRequest.Mode_EDIT)) {
            doEdit(request, response, paramRequest);
        } else if (mode.equals("ADDNEW")) {
            doAddNew(request, response, paramRequest);
        } else if (mode.equals("BYTAG")) {
            doByTag(request, response, paramRequest);
        } else if (mode.equals("MANAGE")){
            doManage(request, response, paramRequest);
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

            //System.out.println("<<<<<<" + stripHtmlTags(request.getParameter("title")));
            BookmarkEntry entry = BookmarkEntry.createBookmarkEntry(model);
            entry.setTitle(stripHtmlTags(request.getParameter("title")));
            entry.setBookmarkURL(stripHtmlTags(request.getParameter("urllink")));
            entry.setDescription(stripHtmlTags(request.getParameter("description")));
            entry.setTags(stripHtmlTags(tgs));

            if (stripHtmlTags(tgs).trim().equals("")) {
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
            if(request.getParameter("level") != null &&
                    request.getParameter("level").equals("admin")) {
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
                    if (group.getEntryCount() == 0 
                            && (!group.getTitle().equals(generalName)
                            && !group.getTitle().equals(untaggedName))) {
                        //System.out.println(">>>>>>>>>>>>>>>Grupo " + target.getTitle() + " eliminado");
                        removeGroup(group);
                    }
                }
            }
            response.setMode("MANAGE");
        } else if (mode.equals("DELALL")) {
            while (groups.hasNext()) {
                group = groups.next();
                Iterator<BookmarkEntry> entries = group.listEntrys();
                while (entries.hasNext()) {
                    BookmarkEntry entry = entries.next();
                    group.removeEntry(entry);
                }
                if (group.getEntryCount() == 0 
                        && (!group.getTitle().equals(generalName)
                        && !group.getTitle().equals(untaggedName))) {
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
        StringBuffer sbf = new StringBuffer();
        String lang = paramRequest.getUser().getLanguage();

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        sbf.append("<script type=\"text/javascript\">\n" +
                   "  function showForm(div, url, title) {\n" +
                   "    dojo.xhrGet({\n" +
                   "      url: url,\n" +
                   "      load: function (response) {\n" +
                   "        var dojoDlg = new dijit.Dialog();\n" +
                   "        dojoDlg.attr('content', response);\n" +
                   "        dojoDlg.attr('title', title);\n" +
                   "        dojoDlg.show();\n" +
                   "      },\n" +
                   "      error: function (response) {\n" +
                   "        return response;\n" +
                   "      },\n" +
                   "      handleAs: \"text\"\n" +
                   "    });\n" +
                   "  }\n" +
                   "</script>\n");

        rUrl.setMode("ADDNEW");
        rUrl.setParameter("url", paramRequest.getTopic().getRealUrl());
        sbf.append(" <a href=\"#\" onclick=\"showForm('swbDialog', '" + rUrl +
                   "', '" + paramRequest.getLocaleString("add") + " " +
                   BookmarkEntry.sclass.getDisplayName(lang) +
                   "'); return false;\">" +
                   paramRequest.getLocaleString("mark") + "</a>\n");
        
        rUrl = paramRequest.getRenderUrl();
        rUrl.setMode("MANAGE");
        sbf.append("<a href=\""+ rUrl +"\">" +
                   paramRequest.getLocaleString("manage") + "</a>");
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

        sbf.append("<script type=\"text/javascript\">\n" +
                   "  dojo.addOnLoad(function () {\n" +
                   "    if (dojoDlg != null) {\n" +
                   "      dojoDlg.destroyRecursive();\n" +
                   "    } else {\n" +
                   "      var dojoDlg = new dijit.Dialog();\n" +
                   "    }\n" +
                   "  });\n" +
                   "  function showForm(div, url, title) {\n" +
                   "    dojo.xhrGet({\n" +
                   "      url: url,\n" +
                   "      load: function (response) {\n" +
                   "        dojoDlg = new dijit.Dialog();\n" +
                   "        dojoDlg.attr('content', response);\n" +
                   "        dojoDlg.attr('title', title);\n" +
                   "        dojoDlg.show();\n" +
                   "      },\n" +
                   "      error: function (response) {\n" +
                   "        return response;\n" +
                   "      },\n" +
                   "      handleAs: \"text\"\n" +
                   "    });\n" +
                   "  }\n" +
                   "</script>\n");

        sbf.append("  <div class=\"swb-bkm-container\">\n");
        sbf.append("    <div class=\"swb-bkm-header\">\n" +
                   "      <h1>"+ paramRequest.getLocaleString("manage") + "</h1>\n" +
                   "      <div class=\"swb-bkm-navmenu\">\n");
        aUrl.setMode("ORDER");
        aUrl.setParameter("oType", String.valueOf(SORT_BYNAME));
        sbf.append((sType==SORT_BYNAME)?"        <b>":"        <a href=\""+ aUrl +"\">");
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
        rUrl.setMode(rUrl.Mode_VIEW);
        sbf.append("<a href=\""+ rUrl +"\">" + paramRequest.getLocaleString("exit") + "</a>\n");
        sbf.append("      </div>\n" +
                   "    </div>");
        sbf.append("    <div class=\"swb-bkm-wrapper\">\n");
        sbf.append("      <div class=\"swb-bkm-content\">\n");
        if (getSortType() != SORT_BYTAGS) {
            sbf.append(listEntriesByGroup(getSortType(), generalGp.getId(), paramRequest));
        } else {
            groups = listGroups();
            while (groups.hasNext()) {
                BookmarkGroup group = groups.next();
                if (!group.getTitle().equals(generalName)) {
                    if(group.getTitle().equals(untaggedName) && group.getEntryCount() > 0) {
                        sbf.append("        <h1>" + paramRequest.getLocaleString("notags") + "</h1>\n");
                    } else if (!group.getTitle().equals(untaggedName)){
                        sbf.append("        <h1>" + group.getTitle() + "</h1>\n");
                    }
                    //System.out.println("listando elementos de " + group.getTitle() + ":" + group.getSemanticObject().getId() + "->" + group.getEntryCount());
                    sbf.append(listEntriesByGroup(SORT_BYDATE, group.getSemanticObject().getId(), paramRequest));
                }
            }
        }
        sbf.append("      </div>\n" +
                   "    </div>\n");
        sbf.append(createMenu(paramRequest));
        sbf.append("  </div>\n");
        sbf.append("  <div class=\"swb-bkm-footer\">\n");
        sbf.append("    <p><br></p>\n");
        sbf.append("  </div>\n");
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
        
        sbf.append("  <div class=\"swb-bkm-header\">\n" +
                   "    <h1>" + group.getTitle() + "</h1>\n" +
                   "  </div>\n");
        sbf.append("  <div class=\"swb-bkm-wrapper\">\n" +
                   "    <div class=\"swb-bkm-content\">\n" +
                          listEntriesByGroup(getSortType(), gid, paramRequest) +
                   "    </div>\n" +
                   "  </div>\n" +
                   "  <div class=\"swb-bkm-footer\">\n");
        rUrl.setMode("MANAGE");
        sbf.append("    <a href=\"" + rUrl + "\">" +
                        paramRequest.getLocaleString("back") + "</a>\n");
        sbf.append("  </div>\n");
        out.println(sbf.toString());
    }

    public void doAddNew(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        String lang = paramRequest.getUser().getLanguage();
        String title = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_title.getDisplayName(lang), false);
        String link = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_bookmarkURL.getDisplayName(lang), false);
        String desc = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_description.getDisplayName(lang), false);
        String tags = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_tags.getDisplayName(lang), false);
        String url = request.getParameter("url");
        StringBuffer sbf = new StringBuffer();

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        aUrl.setMode("ADDNEW");
        if (url == null) {
            aUrl.setParameter("level", "admin");
        }
        //TODO: Cambiar por un FormManager
        sbf.append("  <div class=\"swbform\">\n" +
                   "    <form id=\"" + getResourceBase().getId() + "/bookmark\" " +
                        "action=\"" + aUrl + "\" method=\"post\" " +
                        "onsubmit=\"submitForm('"+ getResourceBase().getId() +
                        "/bookmark" +"'); return false;\">\n" +
                   "      <fieldset>\n" +
                   "        <table>\n" +
                   "          <tr>\n" +
                   "            <td width=\"200px\" align=\"right\">\n" +
                   "              <label for=\"title\">" + title + ": </label>\n" +
                   "            </td>\n" +
                   "            <td>\n" +
                   "              <input type=\"text\" name=\"title\" class=\"swb-bkm-textBox\" />\n" +
                   "            </td>\n" +
                   "          </tr>\n" +
                   "          <tr>\n" +
                   "            <td width=\"200px\" align=\"right\">\n" +
                   "              <label for=\"urllink\">" + link + ": </label>\n" +
                   "            </td>\n" +
                   "            <td width=\"200px\" align=\"right\">\n" +
                   "              <input type=\"text\" name=\"urllink\" value=\""+ ((url==null)?"http://":url) +"\" "+ ((url!=null)?"readonly=\"readonly\" ":"") +" class=\"swb-bkm-textBox\"></input>\n" +
                   "            </td>\n" +
                   "          </tr>\n" +
                   "          <tr>\n" +
                   "            <td width=\"200px\" align=\"right\">\n" +
                   "              <label for=\"description\">" + desc + ": </label>\n" +
                   "            </td>\n" +
                   "            <td>\n" +
                   "              <input type=\"text\" name=\"description\" class=\"swb-bkm-textBox\"></input>\n" +
                   "            </td>\n" +
                   "          </tr>\n" +
                   "          <tr>\n" +
                   "            <td width=\"200px\" align=\"right\">\n" +
                   "              <label for=\"tags\">" + tags + ": </label>\n" +
                   "            </td>\n" +
                   "            <td>\n" +
                   "              <textarea name=\"tags\" class=\"swb-bkm-textArea\"></textarea>\n" +
                   "            </td>\n" +
                   "          </tr>\n" +
                   "        </table>\n" +
                   "      </fieldset>\n" +
                   "      <fieldset>\n" +
                   "        <span align=\"center\">\n" +
                   "          <button class=\"swb-bkm-frmButton\" type=\"submit\" onclick=\"dojoDlg.destroyRecursive();\">" + paramRequest.getLocaleString("add") + "</button>\n" +
                   "        </span>\n" +
                   "      </fieldset>\n" +
                   "    </form>" +
                   "  </div>");
        out.println(sbf.toString());
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        String id = request.getParameter("id");
        String lang = paramRequest.getUser().getLanguage();
        String title = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_title.getDisplayName(lang), false);
        String link = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_bookmarkURL.getDisplayName(lang), false);
        String desc = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_description.getDisplayName(lang), false);
        String tags = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_tags.getDisplayName(lang), false);
        BookmarkGroup generalGp = getGroupByName(getResourceBase().getId() + "/general");
        StringBuffer sbf = new StringBuffer();

        if(generalGp == null) {
            return;
        }

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        BookmarkEntry entry = generalGp.getEntryById(id);
        aUrl.setMode(aUrl.Mode_EDIT);
        rUrl.setMode("REPLACE");
        rUrl.setParameter("id", id);
        aUrl.setParameter("id", id);
        aUrl.setParameter("replace", "true");

        sbf.append("<script type=\"text/javascript\">\n");
        sbf.append("function showForm(div, url) {\n" +
                        "dojo.xhrGet({\n" +
                            "url: url,\n" +
                            "load:function(response){\n" +
                                "dojoDlg = dojo.byId(div);" +
                                "dojoDlg.innerHTML=\"\";\n" +
                                "dojoDlg.innerHTML=response;\n" +
                            "},\n" +
                            "error: function(response) {\nreturn response;\n},\nhandleAs: \"text\"\n" +
                        "});\n" +
                    "}\n");
        sbf.append("</script>");

        sbf.append("<div class=\"swb-bkm-form\">\n" +
                   "  <form id=\"" + id + "/bookmark\" " +
                      "action=\"" + aUrl + "\" method=\"post\" " +
                      "onSubmit=\"submitForm('"+ getResourceBase().getId() +
                      "/bookmark" +"'); return false;\">\n" +
                   "      <input class=\"swb-bkm-textBox\" name=\"id\" " +
                          "type=\"hidden\" value=\""+ id +"\"></input>\n" +
                   "      <table>\n" +
                   "        <tr>\n" +
                   "          <td align=\"right\">\n" +
                   "            <label for=\"title\">" + title + ": </label>\n" +
                   "          </td>\n" +
                   "          <td>\n" +
                   "            <input class=\"swb-bkm-textBox\" type=\"text\" " +
                                "name=\"title\" value=\""+ entry.getTitle() +
                                "\"></input>\n" +
                   "          </td>\n" +
                   "        </tr>\n" +
                   "        <tr>\n" +
                   "          <td align=\"right\">\n" +
                   "            <label for=\"urllink\">" + link + ": </label></td>\n" +
                   "          <td>\n" +
                   "            <input class=\"swb-bkm-textBox\" type=\"text\" " +
                                "disabled=\"disabled\" name=\"urllink\" " +
                                "value=\""+ entry.getBookmarkURL() +
                                "\" ></input>\n" +
                   "          </td>\n" +
                   "        </tr>\n" +
                   "        <tr>\n" +
                   "          <td align=\"right\">\n" +
                   "            <label for=\"description\">" + desc + ": </label>\n" +
                   "          </td>\n" +
                   "          <td>\n" +
                   "            <input class=\"swb-bkm-textBox\" type=\"text\" " +
                                "name=\"description\" value=\""+
                                entry.getDescription() +"\"></input>\n" +
                   "          </td>\n" +
                   "        </tr>\n" +
                   "        <tr>\n" +
                   "          <td align=\"right\">\n" +
                   "            <label for=\"tags\">" + tags + ": </label>\n" +
                   "          </td>\n" +
                   "          <td>\n" +
                   "            <textarea class=\"swb-bkm-textArea\" " +
                                "name=\"tags\">"+ entry.getTags() +
                                "</textarea>\n" +
                   "          </td>\n" +
                   "        </tr>\n" +
                   "        <tr>\n" +
                   "          <td colspan=2 align=\"right\">\n" +
                   "            <button class=\"swb-bkm-frmButton\" type=\"submit\">" + paramRequest.getLocaleString("ok") + "</button>\n" +
                   "            <button class=\"swb-bkm-frmButton\" onclick=\"showForm('r_entry_" + id + "','"+ rUrl +"'); return false;\">" + paramRequest.getLocaleString("cancel") + "</button>\n" +
                   "          </td>\n" +
                   "        </tr>\n" +
                   "      </tbody>\n" +
                   "    </table>\n" +
                   "  </form>\n" +
                   "</div>\n");
        out.println(sbf.toString());
    }


    /**
     * Gets a BookmarkGroup given it's name.
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
     * Gets a BookmarkGroup given it's ID.
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

    /**
     * List Bookmarks of a BookmarkGroup.
     * @param sortType Constant for sort type.
     * @param groupId ID of the BookmarkGroup to display.
     * @param paramRequest
     * @return HTML String with the list of Bookmarks in BookmarkGroup.
     * @throws org.semanticwb.portal.api.SWBResourceException
     */
    public String listEntriesByGroup(int sortType, String groupId, SWBParamRequest paramRequest) throws SWBResourceException {
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        StringBuffer sbf = new StringBuffer();
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

        sbf.append("  <div class =\"rview\">\n");
        sbf.append("    <table border=\"0\" cellpadding=\"0\" cellspacing=\"10\" width=\"100%\">\n");
        sbf.append("      <tbody>\n");
        entries = sEntries.iterator();
        while (entries.hasNext()) {
            BookmarkEntry entry = entries.next();
            String eId = entry.getSemanticObject().getId();
            sbf.append("        <tr class = \"entry\">\n");
            sbf.append("          <td>\n" +
                       "            <div id=\"r_entry_"+ eId + "\">\n" +
                       "              <a title=\""+ entry.getBookmarkURL() +
                                    "\" href=\"" + entry.getBookmarkURL() +
                                    "\">" + entry.getTitle() +
                                 "</a>" +
                                 "&nbsp;-&nbsp;" + entry.getBookmarkURL() +
                                 "&nbsp;-&nbsp;" + formatter.format(entry.getCreated()) +
                                 "&nbsp;");
            rUrl.setMode(rUrl.Mode_EDIT);
            rUrl.setParameter("id", eId);
            sbf.append("<a title=\""+ paramRequest.getLocaleString("edit") +
                                    "\" href=\"" + rUrl + "\"><img src=\"" +
                                     SWBPlatform.getContextPath() +
                                     "/swbadmin/icons/editar_1.gif\" border=\"0\" " +
                                     "alt=\""+paramRequest.getLocaleString("edit")+
                                     "\"></a>&nbsp;");
            aUrl.setMode("DELETE");
            aUrl.setParameter("id", eId);
            sbf.append("<a title=\""+ paramRequest.getLocaleString("delete") +
                                    "\" href=\"#\" onclick=\"if(confirm('"+
                                    paramRequest.getLocaleString("msgRemove") +
                                    "')){location='"+ aUrl +"'} else {return false;}" +
                                    "\"><img src=\"" + SWBPlatform.getContextPath() +
                                    "/swbadmin/images/delete.gif\" border=\"0\" " +
                                    "alt=\""+paramRequest.getLocaleString("delete")+
                                    "\"></a><br>");
            String tags[] = entry.getTags().split(",");
            sbf.append("[");
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
            sbf.append(entry.getDescription());
            sbf.append("]\n" +
                       "            </div>\n" +
                       "          </td>\n" +
                       "        </tr>\n");
        }
        sbf.append("      </tbody>\n");
        sbf.append("    </table>\n");
        sbf.append("  </div>\n");
        return sbf.toString();
    }

    /**
     * Creates navigation menu for Bookmarks administration.
     * @param paramRequest
     * @return HTML String for the navigation menu.
     * @throws org.semanticwb.portal.api.SWBResourceException
     */
    public String createMenu(SWBParamRequest paramRequest) throws SWBResourceException {
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        Iterator<BookmarkGroup> groups = listGroups();
        String generalName = getResourceBase().getId() + "/general";
        String untaggedName = getResourceBase().getId() + "/untagged";
        String lang = paramRequest.getUser().getLanguage();
        BookmarkGroup generalGp = getGroupByName(generalName);
        StringBuffer sbf = new StringBuffer();  

        sbf.append("  <div class=\"swb-bkm-navbarmain\">\n");
        sbf.append("    <div class=\"swb-bkm-navigation\">\n");
        rUrl.setMode("BYTAG");
        while (groups.hasNext()) {
            BookmarkGroup group = groups.next();
            if (!group.getTitle().equals(generalName)) {
                rUrl.setParameter("gid", group.getSemanticObject().getId());
                if (!group.getTitle().equals(untaggedName)) {
                    if (group.getEntryCount() > 0) {
                        sbf.append("      <b><a href=\"" + rUrl + "\">" + group.getTitle() + "(" + group.getEntryCount() + ")" + "</a></b><br>\n");
                    }
                } else if (group.getEntryCount() > 0) {
                    sbf.append("      <b><a href=\"" + rUrl + "\">" + paramRequest.getLocaleString("notags") + "(" + group.getEntryCount() + ")" + "</a></b><br>\n");
                }
            }
        }
        sbf.append("    </div>\n" +
                   "    <div class=\"swb-bkm-extra\">\n");
        rUrl.setParameter("level","admin");
        rUrl.setMode("ADDNEW");
        sbf.append("      <a href=\"#\" onclick=\"showForm('swbDialog', '" + rUrl +
                   "', '" + paramRequest.getLocaleString("add") + " " +
                   BookmarkEntry.sclass.getDisplayName(lang) +
                   "'); return false;\">" + paramRequest.getLocaleString("add") + 
                   " " + BookmarkEntry.sclass.getDisplayName(lang)  + "</a><br>\n");
        if (generalGp.getEntryCount() > 0) {
            aUrl.setMode("DELALL");
            sbf.append("<a href=\"#\" onclick=\"if(confirm('"+
                    paramRequest.getLocaleString("msgRemoveAll") +
                    "')){location='"+ aUrl +"'} else {return false;}\">" +
                    paramRequest.getLocaleString("delall") + "</a>\n");
        }
        sbf.append("    </div>");
        sbf.append("  </div>");
        return sbf.toString();
    }

    /**
     * Strips all HTML tags from the input string.
     * @param input
     * @return
     */
    public String stripHtmlTags(String input) {
        return input.replaceAll("<(.|\n)+?>", "");
    }
}