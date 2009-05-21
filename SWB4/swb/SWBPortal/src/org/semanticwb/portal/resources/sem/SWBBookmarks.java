package org.semanticwb.portal.resources.sem;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.Iterator;
import javax.servlet.http.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
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
    public static final int DISPLAY_LIST = 4;
    public static final int DISPLAY_TREE = 5;
    public static final int DISPLAY_CLOUD = 6;
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
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        WebSite model = response.getTopic().getWebSite();
        String untaggedName = createId("untagged");
        String generalName = createId("general");
        String action = response.getAction();

        if (action.equals("ADDNEW")) {
            BookmarkEntry entry = BookmarkEntry.createBookmarkEntry(model);
            String tgs = stripHtmlTags(request.getParameter("tags").trim());
            entry.setTitle(stripHtmlTags(request.getParameter("title")));
            entry.setBookmarkURL(stripHtmlTags(request.getParameter("urllink")));
            entry.setDescription(stripHtmlTags(request.getParameter("description")));
            entry.setTags(tgs);

            String tags[] = tgs.split(",");

            //If no tags, add entry to untagged group
            if (tgs.equals("")) {
                BookmarkGroup group = getGroupByName(untaggedName);
                
                //If no untagged group, create one
                if (group == null) {
                    group = BookmarkGroup.createBookmarkGroup(model);
                    group.setTitle(untaggedName);
                    addGroup(group);
                }

                //Add entry
                if (group != null) {
                    group.addEntry(entry);
                }
            } else {
                for (int i = 0; i < tags.length; i++) {
                    BookmarkGroup group = getGroupByName(tags[i].trim());

                    //If group does not exist, create group
                    if (group == null) {
                        group = BookmarkGroup.createBookmarkGroup(model);
                        group.setTitle(tags[i].trim());
                        group.addEntry(entry);
                        addGroup(group);
                    } else {
                        group.addEntry(entry);
                    }
                }
            }

            //Add entry to general group
            BookmarkGroup group = getGroupByName(generalName);
            if (group != null) {
                group.addEntry(entry);
            }

            response.setCallMethod(response.Call_CONTENT);
            if(request.getParameter("mode") != null) {
                if (request.getParameter("mode").equals("manage")) {
                    response.setMode("MANAGE");
                } else if (request.getParameter("mode").equals("view")) {
                    response.setMode(response.Mode_VIEW);
                }
            }
        } else if (action.equals("DELALL")) {
            Iterator<BookmarkGroup> groups = listGroups();
            while (groups.hasNext()) {
                BookmarkGroup group = groups.next();
                Iterator<BookmarkEntry> entries = group.listEntrys();
                while (entries.hasNext()) {
                    BookmarkEntry entry = entries.next();
                    group.removeEntry(entry);
                }
                if (group.getEntryCount() == 0
                        && (!group.getTitle().equals(generalName))) {
                    removeGroup(group);
                }
            }
            response.setCallMethod(response.Call_CONTENT);
            response.setMode("MANAGE");
        } else if (action.equals("SORT")) {
            setSortType(Integer.parseInt(request.getParameter("oType")));
            response.setMode("MANAGE");
        } else if (action.equals ("DELETE")) {
            Iterator<BookmarkGroup> groups = listGroups();
            while (groups.hasNext()) {
                BookmarkGroup group = groups.next();
                BookmarkEntry entry = group.getEntryById(request.getParameter("id"));

                if (entry != null) {
                    group.removeEntry(entry);
                    if (group.getEntryCount() == 0
                            && (!group.getTitle().equals(generalName))) {
                        removeGroup(group);
                    }
                }
            }
            
            response.setCallMethod(response.Call_CONTENT);
            if(request.getParameter("level") != null) {
                if (request.getParameter("level").equals("manage")) {
                    response.setMode("MANAGE");
                } else if (request.getParameter("level").equals("view")) {
                    response.setMode(response.Mode_VIEW);
                }
            }
            response.setCallMethod(response.Call_CONTENT);
            response.setMode("MANAGE");
        } else if (action.equals("EDIT")) {
            BookmarkGroup group = getGroupByName(generalName);
            String title = stripHtmlTags(request.getParameter("title"));
            String tgs = stripHtmlTags(request.getParameter("tags").trim());
            String url = stripHtmlTags(request.getParameter("urllink"));
            String description = stripHtmlTags(request.getParameter("description"));

            if (group != null) {
                BookmarkEntry entry = group.getEntryById(request.getParameter("id"));
                if (entry != null) {
                    entry.setTitle(title);
                    entry.setBookmarkURL(url);
                    entry.setDescription(description);
                    updateTags(entry.getSemanticObject().getId(), entry.getTags(), tgs, response.getTopic().getWebSite());
                    entry.setTags(tgs);
                }
            }
            response.setCallMethod(response.Call_CONTENT);
            response.setMode("MANAGE");
        } else {
            super.processAction(request, response);
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();

        if (mode.equals(paramRequest.Mode_VIEW)) {
            doView(request, response, paramRequest);
        } else if (mode.equals("ADDNEW")) {
            doAddNew(request, response, paramRequest);
        } else if (mode.equals("RLIST")) {
            doRenderList(request, response, paramRequest);
        } else if (mode.equals("MANAGE")) {
            doManage(request, response, paramRequest);
        } else if (mode.equals("RCONTENT")) {
            doRenderContent(request, response, paramRequest);
        } else if (mode.equals("EDIT")) {
            doEdit(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doAddNew(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        String mode = request.getParameter("mode");
        String url = request.getParameter("url");
        PrintWriter out = response.getWriter();
        StringBuffer sbf = new StringBuffer();
        String lang = "es";

        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        if(paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }

        String title = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_title.getDisplayName(lang), false);
        String link = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_bookmarkURL.getDisplayName(lang), false);
        String desc = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_description.getDisplayName(lang), false);
        String tags = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_tags.getDisplayName(lang), false);

        aUrl.setAction("ADDNEW");
        aUrl.setParameter("mode", mode);

        //TODO: Cambiar por un FormManager
        sbf.append("<div class=\"swbform\">\n" +
                   "  <form dojoType=\"dijit.form.Form\" " +
                      "id=\"" + createId("bookmark") +" \" " +
                      "action=\"" + aUrl + "\" method=\"post\" " +
                      "onsubmit=\"submitForm('"+ createId("bookmark") + "');" +
                      " return false;\">\n" +
                   "    <fieldset>\n" +
                   "      <table>\n" +
                   "        <tr>\n" +
                   "          <td width=\"200px\" align=\"right\">\n" +
                   "            <label for=\"title\">" + title + ": </label>\n" +
                   "          </td>\n" +
                   "          <td>\n" +
                   "            <input dojoType=\"dijit.form.ValidationTextBox\" " +
                                "required=\"true\" id=\"" + createId("bkm-title") + "\" " +
                                "promptMessage=\"" + paramRequest.getLocaleString("promptField") +
                                " " + title + "\" " +
                                "name=\"title\"></input>\n" +
                   "          </td>\n" +
                   "        </tr>\n" +
                   "        <tr>\n" +
                   "          <td width=\"200px\" align=\"right\">\n" +
                   "            <label for=\"urllink\">" + link + ": </label>\n" +
                   "          </td>\n" +
                   "          <td width=\"200px\" align=\"right\">\n" +
                   "            <input dojoType=\"dijit.form.ValidationTextBox\" " +
                                "id=\"" + createId("bkm-url") + "\" name=\"urllink\" " +
                                "value=\""+ ((url==null)?"http://":url) +"\" "+
                                ((url!=null)?" readonly=\"readonly\" ":"") +"></input>\n" +
                   "          </td>\n" +
                   "        </tr>\n" +
                   "        <tr>\n" +
                   "          <td width=\"200px\" align=\"right\">\n" +
                   "            <label for=\"description\">" + desc + ": </label>\n" +
                   "          </td>\n" +
                   "          <td>\n" +
                   "            <input dojoType=\"dijit.form.ValidationTextBox\" " +
                                "required=\"true\" id=\"" + createId("bkm-desc") + "\" " +
                                "name=\"description\" " +
                                "promptMessage=\"" + paramRequest.getLocaleString("promptField") +
                                " " + desc + "\"></input>\n" +
                   "          </td>\n" +
                   "        </tr>\n" +
                   "        <tr>\n" +
                   "          <td width=\"200px\" align=\"right\">\n" +
                   "            <label for=\"tags\">" + tags + ": </label>\n" +
                   "          </td>\n" +
                   "          <td>\n" +
                   "            <input dojoType=\"dijit.form.ValidationTextBox\" " +
                                "name=\"tags\" promptMessage=\"" +
                                paramRequest.getLocaleString("promptField") + " " +
                                tags + "\"></input>\n" +
                   "          </td>\n" +
                   "        </tr>\n" +
                   "        <tr>\n" +
                   "          <td align=\"right\" colspan=\"2\">\n" +
                   "            <button onclick=\"doApply();\" " +
                                "dojoType=\"dijit.form.Button\" id=\"" + createId("bkm-send") + "\">" +
                                 paramRequest.getLocaleString("add") + "</button>\n" +
                   "          </td>\n" +
                   "        </tr>\n" +
                   "      </table>\n" +
                   "    </fieldset>\n" +
                   "  </form>" +
                   "</div>");
        out.print(sbf.toString());
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        BookmarkGroup generalGp = getGroupByName(createId("general"));
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        StringBuffer sbf = new StringBuffer();
        String lang = "es";

        response.setContentType("text/html;");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        if(generalGp == null) return;

        //Get user language
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }

        //Sanitize input
        String title = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_title.getDisplayName(lang), false);
        String link = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_bookmarkURL.getDisplayName(lang), false);
        String desc = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_description.getDisplayName(lang), false);
        String tags = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_tags.getDisplayName(lang), false);

        BookmarkEntry entry = generalGp.getEntryById(id);
        aUrl.setAction("EDIT");
        aUrl.setParameter("id", id);
                sbf.append("  <div class=\"swbform\">\n" +
                   "    <form dojoType=\"dijit.form.Form\" id=\"" + createId("bookmark") + "\" " +
                        "action=\"" + aUrl + "\" method=\"post\" " +
                        "onsubmit=\"submitForm('"+ createId("bookmark") +"'); " +
                        "return false;\">\n" +
                   "      <fieldset>\n" +
                   "        <table>\n" +
                   "          <tr>\n" +
                   "            <td width=\"200px\" align=\"right\">\n" +
                   "              <label for=\"title\">" + title + ": </label>\n" +
                   "            </td>\n" +
                   "            <td>\n" +
                   "              <input dojoType=\"dijit.form.ValidationTextBox\" " +
                                  "required=\"true\" id=\"" + createId("bkm-title") + "\" " +
                                  "promptMessage=\"" + paramRequest.getLocaleString("promptField") +
                                  " " + title + "\" " +
                                  "name=\"title\" value=\"" + entry.getTitle() + "\"></input>\n" +
                   "            </td>\n" +
                   "          </tr>\n" +
                   "          <tr>\n" +
                   "            <td width=\"200px\" align=\"right\">\n" +
                   "              <label for=\"urllink\">" + link + ": </label>\n" +
                   "            </td>\n" +
                   "            <td width=\"200px\" align=\"right\">\n" +
                   "              <input dojoType=\"dijit.form.ValidationTextBox\" " +
                                  "id=\"" + createId("bkm-url") + "\" name=\"urllink\" " +
                                  "value=\""+ entry.getBookmarkURL() +"\" readonly=\"readonly\"></input>\n" +
                   "            </td>\n" +
                   "          </tr>\n" +
                   "          <tr>\n" +
                   "            <td width=\"200px\" align=\"right\">\n" +
                   "              <label for=\"description\">" + desc + ": </label>\n" +
                   "            </td>\n" +
                   "            <td>\n" +
                   "              <input dojoType=\"dijit.form.ValidationTextBox\" " +
                                  "required=\"true\" id=\"" + createId("bkm-desc") + "\" " +
                                  "name=\"description\" " +
                                  "promptMessage=\"" + paramRequest.getLocaleString("promptField") +
                                  " " + desc + "\" " +
                                  "value=\"" + entry.getDescription() + "\"></input>\n" +
                   "            </td>\n" +
                   "          </tr>\n" +
                   "          <tr>\n" +
                   "            <td width=\"200px\" align=\"right\">\n" +
                   "              <label for=\"tags\">" + tags + ": </label>\n" +
                   "            </td>\n" +
                   "            <td>\n" +
                   "              <input dojoType=\"dijit.form.ValidationTextBox\" " +
                                  "name=\"tags\" promptMessage=\"" +
                                  paramRequest.getLocaleString("promptField") + " " +
                                  tags + "\" " +
                                  "value=\"" + entry.getTags() + "\"></input>\n" +
                   "            </td>\n" +
                   "          </tr>\n" +
                   "          <tr>\n" +
                   "            <td align=\"right\" colspan=\"2\">\n" +
                   "              <button onclick=\"doApply();\" " +
                                  "dojoType=\"dijit.form.Button\" id=\"" + createId("bkm-send") + "\">" +
                                   paramRequest.getLocaleString("ok") + "</button>\n" +
                   "            </td>\n" +
                   "          </tr>\n" +
                   "        </table>\n" +
                   "      </fieldset>\n" +
                   "    </form>" +
                   "  </div>");
        out.print(sbf.toString());
    }

    public void doManage(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        String generalName = createId("general");
        PrintWriter out = response.getWriter();
        StringBuffer sbf = new StringBuffer();
        int sType = getSortType();

        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        //Add necesary scripting
        sbf.append("<script type=\"text/javascript\">\n" +
                   "  dojo.require(\"dijit.form.Form\");\n" +
                   "  dojo.require(\"dijit.form.Button\");\n" +
                   "  dojo.require(\"dijit.form.ValidationTextBox\");\n" +
                   "  dojo.require(\"dijit.form.FilteringSelect\");\n" +
                   "  dojo.require(\"dijit.Dialog\");\n" +
                   "  dojo.require(\"dojox.layout.ContentPane\");\n" +
                   "  dojo.require(\"dojo.parser\");\n\n" +
                   "  var eCount;\n" +
                   "  function isEmpty(objid) {\n" +
                   "    var obj = dojo.byId(objid);\n" +
                   "    if (obj == null || obj.value == '' \n" +
                   "        || obj.value.charAt(0) == ' ') {\n" +
                   "      return true;\n" +
                   "    }else {\n" +
                   "      return false;\n" +
                   "    }\n" +
                   "  }\n" +
                   "  function doApply() {\n" +
                   "    eCount=0;\n" +
                   "    if (isEmpty('" + createId("bkm-title") + "')) eCount++;\n" +
                   "    if (isEmpty('" + createId("bkm-url") + "')) eCount++;\n" +
                   "    if (isEmpty('" + createId("bkm-desc") + "')) eCount++;\n" +
                   "    if (eCount > 0) {\n" +
                   "      alert('"+ paramRequest.getLocaleString("msgFieldError") +"');\n" +
                   "    } else {\n" +
                   "      dojo.byId('" + createId("bkm-send") + "').form.submit();\n" +
                   "    }\n" +
                   "  }\n" +
                   "</script>\n");

        sbf.append("<div class=\"swb-bkm-container\">\n" +
                   "  <div class=\"swb-bkm-header\">\n" +
                   "    <h1>" + paramRequest.getLocaleString("manage") + "</h1>\n" +
                   "    <div class=\"swb-bkm-navmenu\">\n");

        //Set aUrl action to ORDER
        aUrl.setAction("SORT");
        aUrl.setParameter("oType", String.valueOf(SORT_BYNAME));
        sbf.append((sType==SORT_BYNAME)?"      <b>":"      <a href=\""+ aUrl +"\">");
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
        
        BookmarkGroup generalGp = getGroupByName(generalName);
        //Set url mode to VIEW
        rUrl.setMode(rUrl.Mode_VIEW);
        sbf.append("<a href=\""+ rUrl +"\">" + paramRequest.getLocaleString("exit") + "</a>\n" +
                   "    </div>\n" +
                   "  </div>\n" +
                   "  <div class=\"swb-bkm-wrapper\">\n" +
                   "    <div class=\"swb-bkm-content\" id=\"" +
                          createId("swb-bkm-content") + "\">\n" +
                          renderContent(generalGp.getSemanticObject().getId(), paramRequest) +
                          //listEntriesByGroup(generalGp.getSemanticObject().getId(), "manage", sType, paramRequest) +
                   "    </div>\n" +
                   "  </div>\n" +
                   renderMenu(paramRequest) +
                   "</div>\n" +
                   "<div class=\"swb-bkm-footer\">\n" +
                   "  <p><br></p>\n" +
                   "</div>\n");
        out.print(sbf.toString());
    }

    public void doRenderList(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.print(listEntriesByGroup(request.getParameter("gid"), request.getParameter("mode"), getSortType(), paramRequest));
    }

    public void doRenderContent(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.print(renderContent(request.getParameter("gid"), paramRequest));
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        WebSite model = paramRequest.getTopic().getWebSite();
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        String untaggedName = createId("untagged");
        String generalName = createId("general");
        PrintWriter out = response.getWriter();
        StringBuffer sbf = new StringBuffer();
        String lang = "es";
        User user = paramRequest.getUser();
        
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        //Initialize sort type
        if (getSortType() == SORT_NOSORTED) setSortType(SORT_BYDATE);

        //Initialize general bookmarks group
        BookmarkGroup generalGp = getGroupByName(generalName);
        if (generalGp == null) {
            generalGp = BookmarkGroup.createBookmarkGroup(model);
            generalGp.setTitle(generalName);
            addGroup(generalGp);
        }

        //Get user language
        if (user != null) {
            lang = user.getLanguage();
        }

        //Add necesary scripting
        sbf.append("<script type=\"text/javascript\">\n" +
                   "  dojo.require(\"dijit.form.Form\");\n" +
                   "  dojo.require(\"dijit.form.Button\");\n" +
                   "  dojo.require(\"dijit.form.ValidationTextBox\");\n" +
                   "  dojo.require(\"dijit.form.FilteringSelect\");\n" +
                   "  dojo.require(\"dijit.Dialog\");\n" +
                   "  dojo.require(\"dojox.layout.ContentPane\");\n" +
                   "  dojo.require(\"dojo.parser\");\n\n" +
                   "  var eCount;\n" +
                   "  function isEmpty(objid) {\n" +
                   "    var obj = dojo.byId(objid);\n" +
                   "    if (obj == null || obj.value == '' \n" +
                   "        || obj.value.charAt(0) == ' ') {\n" +
                   "      return true;\n" +
                   "    }else {\n" +
                   "      return false;\n" +
                   "    }\n" +
                   "  }\n" +
                   "  function doApply() {\n" +
                   "    eCount=0;\n" +
                   "    if (isEmpty('" + createId("bkm-title") + "')) eCount++;\n" +
                   "    if (isEmpty('" + createId("bkm-url") + "')) eCount++;\n" +
                   "    if (isEmpty('" + createId("bkm-desc") + "')) eCount++;\n" +
                   "    if (eCount > 0) {\n" +
                   "      alert('"+ paramRequest.getLocaleString("msgFieldError") +"');\n" +
                   "    } else {\n" +
                   "      dojo.byId('" + createId("bkm-send") + "').form.submit();\n" +
                   "    }\n" +
                   "  }\n" +
                   "  function refreshContent(url) {\n" +
                   "    var val = dijit.byId('" + createId("swb-bkm-select") + "').value;\n" +
                   "    dojo.byId('" + createId("bookmarksList") + "').innerHtml='';\n" +
                   "    getHtml(url + '&gid=' + val, '" + createId("bookmarksList") + "');\n" +
                   "  }\n" +
                   "</script>\n");
        
        //Set url call method to Call_DIRECT to make an AJAX call
        rUrl.setCallMethod(rUrl.Call_DIRECT).setMode("ADDNEW");
        rUrl.setParameter("mode", "view");
        rUrl.setParameter("url", paramRequest.getTopic().getRealUrl());
        
        sbf.append("  <div class=\"swb-bkm-box\">\n" +
                   "    <div class=\"titleBar\">\n" +
                   "      <span class=\"title\">" +
                            SWBBookmarks.sclass.getDisplayName(lang) +
                          "</span>\n" +
                   "    </div>\n" +
                   "    <div class=\"content\">\n" +
                   "      <div class=\"contentPane\">\n");
                   if (user.isSigned()) {

                   sbf.append("        <a href=\"#\" onclick=\"showDialog('" + rUrl +
                            "', '" + paramRequest.getLocaleString("add") + 
                            " " + BookmarkEntry.sclass.getDisplayName(lang) +
                            "'); return false;\">" +
                            paramRequest.getLocaleString("mark") + "</a> \n");

        //Set url mode to MANAGE mode to manage bookmarks
        rUrl = paramRequest.getRenderUrl().setMode("MANAGE");
        rUrl.setParameter("mode", "manage");

        sbf.append("        <a href=\"" + rUrl + "\">" +
                            paramRequest.getLocaleString("manage") + "</a>" +
                   "        <div class=\"bookmarksFrame\">\n" +
                   "          <form>\n" +
                   "            <label for=\"" + createId("bkm-selector") + "\">\n" +
                   "              " + BookmarkGroup.sclass.getDisplayName(lang) +
                                  ": " + 
                               "</label>\n");
        
        //Set url call method to Call_DIRECT to make an AJAX call
        rUrl = paramRequest.getRenderUrl();
        rUrl.setCallMethod(rUrl.Call_DIRECT).setMode("RLIST");
        rUrl.setParameter("mode", "view");
        sbf.append("            <select id=\"" + createId("swb-bkm-select") + "\" " +
                                "dojoType=\"dijit.form.FilteringSelect\" " +
                                "autoComplete=\"false\" " +
                                "onChange=\"refreshContent('" + rUrl + "');\">\n");

        //List group names in select element
        Iterator<BookmarkGroup> groups = listGroups();
        while (groups.hasNext()) {
            BookmarkGroup group = groups.next();

            if (group.getTitle().equals(generalName)) {
                sbf.append("              <option value=\"" +
                        group.getSemanticObject().getId() + "\">" +
                        paramRequest.getLocaleString("showAll") + "</option>\n");
            } else if (group.getEntryCount() > 0) {
                sbf.append("              <option value=\"" +
                        group.getSemanticObject().getId() + "\">");
                if (group.getTitle().equals(untaggedName)) {
                    sbf.append(paramRequest.getLocaleString("notags") +
                            "</option>\n");
                } else {
                    sbf.append(group.getTitle() + "</option>\n");
                }
            }
        }
        sbf.append("            </select>\n" +
                   "          </form>\n" +
                   "          <div id=\"" + createId("bookmarksList") + "\">\n" +
                   listEntriesByGroup(generalGp.getSemanticObject().getId(), "view", getSortType(), paramRequest) +
                   "          </div>\n" +
                   "        </div>\n");

                   } else {
                        sbf.append(paramRequest.getLocaleString("msgNotLogged"));
                   }
                   sbf.append("      </div>\n" +
                   "    </div>\n" +
                   "  </div>\n");
        out.print(sbf.toString());
    }

    /**
     * Creates a unique ID for HTML elements using the resource-base ID.
     * @param postfix local ID for an HTML element.
     * @return unique global ID for an HTML element.
     */
    private String createId(String postfix) {
        return getResourceBase().getId() + "/" + postfix;
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
    public String listEntriesByGroup(String groupId, String mode, int sortType, SWBParamRequest paramRequest) throws SWBResourceException {
        ArrayList<BookmarkEntry> sEntries = new ArrayList<BookmarkEntry>();
        Format formatter = new SimpleDateFormat("dd MMM");
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        BookmarkGroup group = getGroupById(groupId);
        StringBuffer sbf = new StringBuffer();
        String lang = "es";

        if (group == null) return " ";

        //Get user language
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }

        //Add entries to a temp Array
        Iterator<BookmarkEntry> entries = group.listEntrys();
        while (entries.hasNext()) {
            BookmarkEntry entry = entries.next();
            sEntries.add(entry);
        }

        //Sort entries according to the sortType
        if (sortType == SORT_BYDATE || sortType == SORT_BYTAGS) {
            Collections.sort(sEntries, DATE_ORDER_ASC);
        } else if (sortType == SORT_BYNAME) {
            Collections.sort(sEntries, NAME_ORDER_DESC);
        }

        sbf.append("<div class=\"rview\">\n" +
                   "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"10\" " +
                      "width=\"100%\">\n" +
                   "    <tbody>\n");

        //Print bookmarks list
        entries = sEntries.iterator();
        while (entries.hasNext()) {
            BookmarkEntry entry = entries.next();
            String eid = entry.getSemanticObject().getId();
            sbf.append("    <tr class=\"entry\">\n" +
                       "      <td>\n" +
                       "        <div id=\"" + createId("r_entry_" + eid) + "\">\n" +
                       "          <a title=\"" + entry.getBookmarkURL() + "\" " +
                                  "href=\"" + entry.getBookmarkURL() + "\">" +
                                  entry.getTitle() + "</a>\n");

            //Add extra information if in MANAGE mode
            if (mode.equals("manage")) {
                sbf.append("&nbsp;-&nbsp;" +  entry.getBookmarkURL() +
                        formatter.format(entry.getCreated()) + "&nbsp;");

                //Set url call method to Call_DIRECT to make an AJAX call
                rUrl.setCallMethod(rUrl.Call_DIRECT).setMode(rUrl.Mode_EDIT);
                rUrl.setParameter("id", eid);

                //Add EDIT link
                sbf.append("<a title=\"" + paramRequest.getLocaleString("edit") +
                           "\" href=\"#\" onclick=\"showDialog('" + rUrl +
                           "', '" + paramRequest.getLocaleString("edit") + " " +
                            BookmarkEntry.sclass.getDisplayName(lang) + "');\">" +
                            "<img src=\"" + SWBPlatform.getContextPath() +
                            "/swbadmin/icons/editar_1.gif\" border=\"0\" " +
                            "alt=\"" + paramRequest.getLocaleString("edit") + "\">" +
                            "</a>&nbsp;");

                //Set aUrl action to DELETE and attach entry id
                aUrl.setAction("DELETE");
                aUrl.setParameter("id", eid);
                
                //Add DELETE link
                sbf.append("<a title=\"" + paramRequest.getLocaleString("delete") + 
                           "\" href=\"#\" " +
                           "onclick=\"if (confirm('" +
                           paramRequest.getLocaleString("msgRemove") + "'))" +
                           "{location='" + aUrl + "'} else {return false;}\">" +
                           "<img src=\"" + SWBPlatform.getContextPath() +
                           "/swbadmin/images/delete.gif\" border=\"0\" " +
                           "alt=\"" + paramRequest.getLocaleString("delete") + 
                           "\"></a><br>");

                //Add tags
                sbf.append("[");
                String []tags = entry.getTags().split(",");
                for (int i = 0; i < tags.length; i++) {
                    group = getGroupByName(tags[i].trim());
                    if (group != null) {
                        //Set url mode to RCONTENT in order to update content in a div
                        rUrl.setMode("RCONTENT");
                        rUrl.setParameter("gid", group.getSemanticObject().getId());
                        sbf.append("<a href=\"#\" " +
                           "onclick=\"dojo.query('.swb-bkm-menuOpt').removeClass('swb-bkm-boldElement');" +
                           "dojo.addClass(dojo.byId('" + createId(group.getSemanticObject().getId()) +
                           "'), 'swb-bkm-boldElement');getHtml('" + rUrl +
                           "', '" + createId("swb-bkm-content") + "');\">" +
                           group.getTitle() + "</a>");
                        if (i < tags.length - 1) {
                            sbf.append(", ");
                        }
                    }
                }
                sbf.append("&nbsp;-&nbsp;" + entry.getDescription() + "]\n");
            }
            sbf.append("        </div>\n" +
                       "      </td>\n" +
                       "    </tr>\n");
        }
        sbf.append("    </tbody>\n" +
                   "  </table>\n" +
                   "</div>\n");
        return sbf.toString();
    }

    public String renderContent(String gid, SWBParamRequest paramRequest) throws IOException, SWBResourceException {
        String untaggedName = createId("untagged");
        String generalName = createId("general");
        BookmarkGroup group = getGroupById(gid);
        StringBuffer sbf = new StringBuffer();
        int sType = getSortType();

        if (group == null) return " ";

        if (sType == SORT_BYTAGS && group.getTitle().equals(generalName)) {
            Iterator<BookmarkGroup> groups = listGroups();
            while (groups.hasNext()) {
                group = groups.next();
                if (!group.getTitle().equals(generalName)) {
                    if (group.getTitle().equals(untaggedName)) {
                        sbf.append("<h1>" + paramRequest.getLocaleString("notags") + "</h1>\n");
                    } else {
                        sbf.append("<h1>" + group.getTitle() + "</h1>\n");
                    }
                    sbf.append(listEntriesByGroup(group.getSemanticObject().getId(), "manage", SORT_BYDATE, paramRequest));
                }
            }
        } else if (sType == SORT_BYTAGS) {
            if (!group.getTitle().equals(untaggedName)) {
                sbf.append("<h1>" + group.getTitle() + "</h1>\n");
            } else {
                sbf.append("<h1>" + paramRequest.getLocaleString("notags") + "</h1>\n");
            }
            sbf.append(listEntriesByGroup(gid, "manage", SORT_BYDATE, paramRequest));
        } else {
            sbf.append(listEntriesByGroup(gid, "manage", sType, paramRequest));
        }
        return sbf.toString();
    }


    /**
     * Creates navigation menu for Bookmarks administration.
     * @param paramRequest
     * @return HTML String for the navigation menu.
     * @throws org.semanticwb.portal.api.SWBResourceException
     */
    public String renderMenu(SWBParamRequest paramRequest) throws SWBResourceException {
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        String untaggedName = createId("untagged");
        String generalName = createId("general");
        StringBuffer sbf = new StringBuffer();
        String lang = "es";

        //Get user language
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }

        //Set url call method to Call_DIRECT to make an AJAX call
        rUrl.setCallMethod(rUrl.Call_DIRECT).setMode("RCONTENT");

        sbf.append("<div class=\"swb-bkm-navbarmain\">\n" +
                   "  <div class=\"swb-bkm-navigation\">\n");

        //List group links
        Iterator<BookmarkGroup> groups = listGroups();
        while (groups.hasNext()) {
            BookmarkGroup group = groups.next();
            String gid = group.getSemanticObject().getId();

            if (!group.getTitle().equals(generalName)) {
                rUrl.setParameter("gid", gid);
                if (!group.getTitle().equals(untaggedName)) {
                    if (group.getEntryCount() > 0) {
                        sbf.append("    <a class=\"swb-bkm-menuOpt\" id=\"" +
                        createId(gid) + "\" href=\"#\" " +
                        "onclick=\"dojo.query('.swb-bkm-menuOpt').removeClass('swb-bkm-boldElement');" +
                        "dojo.addClass(dojo.byId('" + createId(gid) + "'), 'swb-bkm-boldElement');" +
                        "getHtml('" + rUrl + "', '" + createId("swb-bkm-content") + "');\">" +
                        group.getTitle() + "(" + group.getEntryCount() + ")</a><br>\n");
                    }
                }
                else if (group.getEntryCount() > 0) {
                    sbf.append("    <a class=\"swb-bkm-menuOpt\" id=\"" +
                    createId(gid) + "\" href=\"#\" " +
                    "onclick=\"dojo.query('.swb-bkm-menuOpt').removeClass('swb-bkm-boldElement');" +
                    "dojo.addClass(dojo.byId('" + createId(gid) + "'), 'swb-bkm-boldElement');" +
                    "getHtml('"+ rUrl +"', '" + createId("swb-bkm-content") + "');\">" +
                    paramRequest.getLocaleString("notags") + "(" +
                    group.getEntryCount() + ")" + "</a><br>\n");
                }
            }
        }
        BookmarkGroup generalGp = getGroupByName(generalName);
        rUrl.setParameter("gid", generalGp.getSemanticObject().getId());
        sbf.append("    <a href=\"#\" class=\"swb-bkm-menuOpt\" " +
                        "id=\"" + createId("bkm-showAll") + "\" " +
                        "onclick=\"dojo.query('.swb-bkm-menuOpt').removeClass('swb-bkm-boldElement');" +
                        "dojo.addClass(dojo.byId('" + createId("bkm-showAll") + "'),'swb-bkm-boldElement');" +
                        "getHtml('" + rUrl + "', '" + createId("swb-bkm-content") + "');\">" +
                        paramRequest.getLocaleString("showAll") + "</a><br>\n" +
                   "  </div>\n" +
                   "  <div class=\"swb-bkm-extra\">\n");

        //Set url call method to Call_DIRECT to make an AJAX call
        rUrl = paramRequest.getRenderUrl();
        rUrl.setCallMethod(rUrl.Call_DIRECT).setMode("ADDNEW");
        rUrl.setParameter("mode", "manage");

        //Add NEW Bookmark link
        sbf.append("    <a href=\"#\" onclick=\"showDialog('" + rUrl + "', '" +
                paramRequest.getLocaleString("add") + " " +
                BookmarkEntry.sclass.getDisplayName(lang) + "');return false;\">" +
                paramRequest.getLocaleString("add") + " " +
                BookmarkEntry.sclass.getDisplayName(lang) + "</a><br>\n");

        //Add DELETE ALL link
        if (generalGp.getEntryCount() > 0) {
            aUrl.setAction("DELALL");
            sbf.append("    <a href=\"#\" onclick=\"if(confirm('" +
                paramRequest.getLocaleString("msgRemoveAll") +
                "')){location='" + aUrl + "'} else {return false;}\">" +
                paramRequest.getLocaleString("delall") + "</a>\n");
        }

        sbf.append("  </div>\n" +
                   "</div>\n" +
                   "  <script type=\"text/javascript\">\n" +
                   "    dojo.addClass(dojo.byId('" + createId("bkm-showAll") + "'), 'swb-bkm-boldElement');\n" +
                   "  </script>\n");
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

    public void updateTags(String eId, String oldTags, String newTags, WebSite model) {
        BookmarkGroup generalGp = getGroupByName(createId("general"));
        BookmarkGroup untaggedGp = getGroupByName(createId("untagged"));
        BookmarkEntry entry = generalGp.getEntryById(eId);

        if (oldTags.equals(newTags)) return;

        String [] oTags = oldTags.split(",");
        String [] nTags = newTags.split(",");

        //Remove BookmarkEntry from groups
        if (oldTags.equals("")) {
            untaggedGp.removeEntry(entry);
        } else {
            for (int i = 0; i < oTags.length; i++) {
                String groupName = oTags[i].trim();

                BookmarkGroup group = getGroupByName(groupName);
                if (group != null) {
                    entry = group.getEntryById(eId);
                    if (entry != null) {
                        group.removeEntry(entry);
                        if (group.getEntryCount() == 0) {
                            removeGroup(group);
                        }
                    }
                }
            }
        }

        entry = generalGp.getEntryById(eId);
        //Add BookmarkEntry to new groups
        if (newTags.equals("")) {
            untaggedGp.addEntry(entry);
        } else {
            for (int i = 0; i < nTags.length; i++) {
                String groupName = nTags[i].trim();

                BookmarkGroup group = getGroupByName(groupName);
                if (group != null) {
                    group.addEntry(entry);
                } else {
                    group = BookmarkGroup.createBookmarkGroup(model);
                    group.setTitle(nTags[i].trim());
                    group.addEntry(entry);
                    addGroup(group);
                }
            }
        }
    }
}