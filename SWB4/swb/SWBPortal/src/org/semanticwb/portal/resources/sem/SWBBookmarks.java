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
        } else if (mode.equals("RCONTENT")) {
            doRenderContent(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        WebSite model = response.getTopic().getWebSite();
        String mode = response.getMode();
        String untaggedName = getResourceBase().getId() + "/untagged";
        String generalName = getResourceBase().getId() + "/general";
        Iterator<BookmarkGroup> groups = listGroups();
        BookmarkGroup group = null;

        if (mode.equals("ADDNEW")) {
            String title = stripHtmlTags(request.getParameter("title"));
            String tgs = stripHtmlTags(request.getParameter("tags").trim());
            String url = stripHtmlTags(request.getParameter("urllink"));
            String description = stripHtmlTags(request.getParameter("description"));
            String tags[] = tgs.split(",");

            BookmarkEntry entry = BookmarkEntry.createBookmarkEntry(model);
                
            entry.setTitle(title);
            entry.setBookmarkURL(url);
            entry.setDescription(description);
            entry.setTags(tgs);

            if (tgs.equals("")) {
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
                    } else {
                        group.addEntry(entry);
                    }
                }
            }
            group = getGroupByName(generalName);
            if (group != null) {
                group.addEntry(entry);
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
                    group.removeEntry(entry);
                    if (group.getEntryCount() == 0 
                            && (!group.getTitle().equals(generalName)
                            && !group.getTitle().equals(untaggedName))) {
                        removeGroup(group);
                    }
                }
            }
            response.setCallMethod(response.Call_CONTENT);
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
            response.setCallMethod(response.Call_CONTENT);
            response.setMode("MANAGE");
        } else if (mode.equals(response.Mode_EDIT)) {
            group = getGroupByName(generalName);
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
        String lang = "es";

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }

        sbf.append("<script type=\"text/javascript\">\n" +
                   "  dojo.require(\"dijit.form.Form\");\n" +
                   "  dojo.require(\"dijit.form.Button\");\n" +
                   "  dojo.require(\"dijit.form.ValidationTextBox\");\n" +
                   "  dojo.require(\"dijit.Dialog\");\n" +
                   "  dojo.require(\"dojox.layout.ContentPane\");\n" +
                   "  dojo.require(\"dojo.parser\");\n\n" +
                   "  var eCount;\n" +
                 /*  "  function showDialog(url, title) {\n" +
                   "    dojo.xhrGet({\n" +
                   "      url: url,\n" +
                   "      load: function(response) {\n" +
                   "        dijit.byId('swbDialogImp').attr('content',response);\n" +
                   "        dijit.byId('swbDialog').show();\n" +
                   "        if (title) {\n" +
                   "          dijit.byId('swbDialog').titleNode.innerHTML=title;\n" +
                   "        }\n" +
                   "      }," +
                   "      error: function(response) {\n" +
                   "        return response;\n" +
                   "      }," +
                   "      handleAs: \"text\"\n" +
                   "    });\n" +
                   "  }\n\n" +*/
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
                   "    if (isEmpty('bkm-title')) eCount++;\n" +
                   "    if (isEmpty('bkm-url')) eCount++;\n" +
                   "    if (isEmpty('bkm-desc')) eCount++;\n" +
                   "    if (eCount > 0) {\n" +
                   "      alert('"+ paramRequest.getLocaleString("msgFieldError") +"');\n" +
                   "    } else {\n" +
                   "      dojo.byId('bkm-send').form.submit();\n" +
                   "    }\n" +
                   "  }\n" +
                   "</script>\n");

        rUrl.setCallMethod(rUrl.Call_DIRECT);
        rUrl.setMode("ADDNEW");
        rUrl.setParameter("url", paramRequest.getTopic().getRealUrl());
        sbf.append(" <a href=\"#\" onclick=\"showDialog('" + rUrl + "', '" +
                paramRequest.getLocaleString("add") + " " + BookmarkEntry.sclass.getDisplayName(lang) + "'); return false;\">" +
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
                   "  dojo.require(\"dijit.form.Form\");\n" +
                   "  dojo.require(\"dijit.form.Button\");\n" +
                   "  dojo.require(\"dijit.form.ValidationTextBox\");\n" +
                   "  dojo.require(\"dijit.Dialog\");\n" +
                   "  dojo.require(\"dojox.layout.ContentPane\");\n" +
                   "  dojo.require(\"dojo.parser\");\n\n" +
                   "  var eCount;\n" +
                   /*"  function showDialog(url, title) {\n" +
                   "    dojo.xhrGet({\n" +
                   "      url: url,\n" +
                   "      load: function(response) {\n" +
                   "        dijit.byId('swbDialogImp').attr('content',response);\n" +
                   "        dijit.byId('swbDialog').show();\n" +
                   "        if (title) {\n" +
                   "          dijit.byId('swbDialog').titleNode.innerHTML=title;\n" +
                   "        }\n" +
                   "      }," +
                   "      error: function(response) {\n" +
                   "        return response;\n" +
                   "      }," +
                   "      handleAs: \"text\"\n" +
                   "    });\n" +
                   "  }\n\n" +*/
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
                   "    if (isEmpty('bkm-title')) eCount++;\n" +
                   "    if (isEmpty('bkm-url')) eCount++;\n" +
                   "    if (isEmpty('bkm-desc')) eCount++;\n" +
                   "    if (eCount > 0) {\n" +
                   "      alert('"+ paramRequest.getLocaleString("msgFieldError") +"');\n" +
                   "    } else {\n" +
                   "      dojo.byId('bkm-send').form.submit();\n" +
                   "    }\n" +
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
        sbf.append("      <div class=\"swb-bkm-content\" id=\"swb-bkm-content\">\n");
        sbf.append(renderContent(getSortType(), generalGp.getId(), paramRequest));
        sbf.append("      </div>\n" +
                   "    </div>\n");
        sbf.append(renderMenu(paramRequest));
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

        if (group == null) {
            return;
        }

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        out.println(listEntriesByGroup(getSortType(), gid, paramRequest));
    }

    public void doAddNew(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        String lang = "es";       
        String url = request.getParameter("url");
        StringBuffer sbf = new StringBuffer();

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        if(paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }

        String title = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_title.getDisplayName(lang), false);
        String link = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_bookmarkURL.getDisplayName(lang), false);
        String desc = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_description.getDisplayName(lang), false);
        String tags = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_tags.getDisplayName(lang), false);

        aUrl.setMode("ADDNEW");
        if (url == null) {
            aUrl.setParameter("level", "admin");
        }
        aUrl.setCallMethod(aUrl.Call_CONTENT);
        //TODO: Cambiar por un FormManager
        sbf.append("  <div class=\"swbform\">\n" +
                   "    <form dojoType=\"dijit.form.Form\" id=\"" + getResourceBase().getId() + "/bookmark\" " +
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
                   "              <input dojoType=\"dijit.form.ValidationTextBox\" " +
                                  "required=\"true\" id=\"bkm-title\" " +
                                  "promptMessage=\"" + paramRequest.getLocaleString("promptField") +
                                  " " + title + "\" " +
                                  "name=\"title\"></input>\n" +
                   "            </td>\n" +
                   "          </tr>\n" +
                   "          <tr>\n" +
                   "            <td width=\"200px\" align=\"right\">\n" +
                   "              <label for=\"urllink\">" + link + ": </label>\n" +
                   "            </td>\n" +
                   "            <td width=\"200px\" align=\"right\">\n" +
                   "              <input dojoType=\"dijit.form.ValidationTextBox\" " +
                                  "id=\"bkm-url\" name=\"urllink\" " +
                                  "value=\""+ ((url==null)?"http://":url) +"\" "+
                                  ((url!=null)?" readonly=\"readonly\" ":"") +"></input>\n" +
                   "            </td>\n" +
                   "          </tr>\n" +
                   "          <tr>\n" +
                   "            <td width=\"200px\" align=\"right\">\n" +
                   "              <label for=\"description\">" + desc + ": </label>\n" +
                   "            </td>\n" +
                   "            <td>\n" +
                   "              <input dojoType=\"dijit.form.ValidationTextBox\" " +
                                  "required=\"true\" id=\"bkm-desc\" " +
                                  "name=\"description\" " +
                                  "promptMessage=\"" + paramRequest.getLocaleString("promptField") +
                                  " " + desc + "\"></input>\n" +
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
                                  tags + "\"></input>\n" +
                   "            </td>\n" +
                   "          </tr>\n" +
                   "          <tr>\n" +
                   "            <td align=\"right\" colspan=\"2\">\n" +
                   "              <button onclick=\"doApply();\" " +
                                  "dojoType=\"dijit.form.Button\" id=\"bkm-send\">" +
                                   paramRequest.getLocaleString("add") + "</button>\n" +
                   "            </td>\n" +
                   "          </tr>\n" +
                   "        </table>\n" +
                   "      </fieldset>\n" +
                   "    </form>" +
                   "  </div>");
        out.println(sbf.toString());
    }

    public void doRenderContent(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        BookmarkGroup generalGp = getGroupByName(getResourceBase().getId() + "/general");

        out.println(renderContent(getSortType(), generalGp.getId(), paramRequest));
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        String id = request.getParameter("id");
        String lang = "es";        
        BookmarkGroup generalGp = getGroupByName(getResourceBase().getId() + "/general");
        StringBuffer sbf = new StringBuffer();

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        if(generalGp == null) {
            return;
        }

        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        
        String title = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_title.getDisplayName(lang), false);
        String link = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_bookmarkURL.getDisplayName(lang), false);
        String desc = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_description.getDisplayName(lang), false);
        String tags = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_tags.getDisplayName(lang), false);

        BookmarkEntry entry = generalGp.getEntryById(id);
        aUrl.setMode(aUrl.Mode_EDIT);
        rUrl.setMode("REPLACE");
        rUrl.setParameter("id", id);
        aUrl.setParameter("id", id);
        aUrl.setParameter("replace", "true");
        
        sbf.append("  <div class=\"swbform\">\n" +
                   "    <form dojoType=\"dijit.form.Form\" id=\"" + getResourceBase().getId() + "/bookmark\" " +
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
                   "              <input dojoType=\"dijit.form.ValidationTextBox\" " +
                                  "required=\"true\" id=\"bkm-title\" " +
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
                                  "id=\"bkm-url\" name=\"urllink\" " +
                                  "value=\""+ entry.getBookmarkURL() +"\" readonly=\"readonly\"></input>\n" +
                   "            </td>\n" +
                   "          </tr>\n" +
                   "          <tr>\n" +
                   "            <td width=\"200px\" align=\"right\">\n" +
                   "              <label for=\"description\">" + desc + ": </label>\n" +
                   "            </td>\n" +
                   "            <td>\n" +
                   "              <input dojoType=\"dijit.form.ValidationTextBox\" " +
                                  "required=\"true\" id=\"bkm-desc\" " +
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
                                  "dojoType=\"dijit.form.Button\" id=\"bkm-send\">" +
                                   paramRequest.getLocaleString("ok") + "</button>\n" +
                   "            </td>\n" +
                   "          </tr>\n" +
                   "        </table>\n" +
                   "      </fieldset>\n" +
                   "    </form>" +
                   "  </div>");
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
        String lang = "es";

        if (group == null) {
            return "";
        }

        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
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

        sbf.append("  <div class =\"rview\">\n" +
                   "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"10\" width=\"100%\">\n" +
                   "      <tbody>\n");
        
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
            rUrl.setCallMethod(rUrl.Call_DIRECT);
            rUrl.setMode(rUrl.Mode_EDIT);
            rUrl.setParameter("id", eId);
            sbf.append("<a title=\""+ paramRequest.getLocaleString("edit") +
                                    "\" href=\"#\" onclick=\"showDialog('" + rUrl +
                                    "', '" + paramRequest.getLocaleString("edit") + 
                                    " " + BookmarkEntry.sclass.getDisplayName(lang) +
                                    "');\"><img src=\"" +
                                     SWBPlatform.getContextPath() +
                                     "/swbadmin/icons/editar_1.gif\" border=\"0\" " +
                                     "alt=\""+paramRequest.getLocaleString("edit") +
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
                    rUrl.setCallMethod(rUrl.Call_DIRECT);
                    rUrl.setMode("BYTAG");
                    rUrl.setParameter("gid", group.getSemanticObject().getId());
                    sbf.append("<a href=\"#\" " +
                               "onclick=\"dojo.query('.swb-bkm-menuOpt').removeClass('swb-bkm-boldElement');" +
                               "dojo.addClass(dojo.byId('" + group.getSemanticObject().getId() + 
                               "'), 'swb-bkm-boldElement');getHtml('" + rUrl +
                               "', 'swb-bkm-content');\">" + group.getTitle() + "</a>");
                    if (i < tags.length - 1) {
                        sbf.append(", ");
                    }
                }
            }
            rUrl.setCallMethod(rUrl.Call_CONTENT);
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
    public String renderMenu(SWBParamRequest paramRequest) throws SWBResourceException {
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        Iterator<BookmarkGroup> groups = listGroups();
        String generalName = getResourceBase().getId() + "/general";
        String untaggedName = getResourceBase().getId() + "/untagged";
        String lang = "es";
        BookmarkGroup generalGp = getGroupByName(generalName);
        StringBuffer sbf = new StringBuffer();

        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }

        rUrl.setCallMethod(rUrl.Call_DIRECT);
        sbf.append("  <div class=\"swb-bkm-navbarmain\">\n" +
                   "    <div class=\"swb-bkm-navigation\">\n");
        rUrl.setMode("BYTAG");
        while (groups.hasNext()) {
            BookmarkGroup group = groups.next();
            String gid = group.getSemanticObject().getId();
            if (!group.getTitle().equals(generalName)) {
                rUrl.setParameter("gid", gid);
                if (!group.getTitle().equals(untaggedName)) {
                    if (group.getEntryCount() > 0) {
                        //sbf.append("      <b><a href=\"" + rUrl + "\">" + group.getTitle() + "(" + group.getEntryCount() + ")" + "</a></b><br>\n");
                        sbf.append("      <a class=\"swb-bkm-menuOpt\" id=\"" + gid + "\" href=\"#\" onclick=\"dojo.query('.swb-bkm-menuOpt').removeClass('swb-bkm-boldElement');dojo.addClass(dojo.byId('" + gid + "'), 'swb-bkm-boldElement'); getHtml('" + rUrl + "', 'swb-bkm-content');\">" + group.getTitle() + "(" + group.getEntryCount() + ")" + "</a><br>\n");
                    }
                } else if (group.getEntryCount() > 0) {
                    //sbf.append("      <b><a href=\"" + rUrl + "\">" + paramRequest.getLocaleString("notags") + "(" + group.getEntryCount() + ")" + "</a></b><br>\n");
                    sbf.append("      <a class=\"swb-bkm-menuOpt\" id=\"" + gid + "\" href=\"#\" onclick=\"dojo.query('.swb-bkm-menuOpt').removeClass('swb-bkm-boldElement');dojo.addClass(dojo.byId('" + gid + "'), 'swb-bkm-boldElement'); getHtml('"+ rUrl +"', 'swb-bkm-content');\">" + paramRequest.getLocaleString("notags") + "(" + group.getEntryCount() + ")" + "</a><br>\n");
                }
            }
        }
        rUrl.setMode("RCONTENT");
        //sbf.append("    </div>\n" +
        //           "    <div class=\"swb-bkm-extra\">\n" +
        sbf.append("      <a href=\"#\" class=\"swb-bkm-menuOpt\" " +
                             "id=\"bkm-showAll\" " +
                             "onclick=\"dojo.query('.swb-bkm-menuOpt').removeClass('swb-bkm-boldElement');" +
                             "dojo.addClass(dojo.byId('bkm-showAll'), 'swb-bkm-boldElement');" +
                             " getHtml('" + rUrl + "', 'swb-bkm-content');\">" +
                             paramRequest.getLocaleString("showAll") + "</a>" +
                   "      <br>\n");

        rUrl.setMode("ADDNEW");
        rUrl.setParameter("level","admin");
        sbf.append("      <a href=\"#\" onclick=\"showDialog('" + rUrl + "', '" +
                    paramRequest.getLocaleString("add") + " " +
                    BookmarkEntry.sclass.getDisplayName(lang) + "'); return false;\">" +
                   paramRequest.getLocaleString("add") + " " +
                   BookmarkEntry.sclass.getDisplayName(lang) + "</a><br>\n");
        if (generalGp.getEntryCount() > 0) {
            aUrl.setMode("DELALL");
            //rUrl.setCallMethod(rUrl.Call_CONTENT);
            sbf.append("      <a href=\"#\" onclick=\"if(confirm('"+
                    paramRequest.getLocaleString("msgRemoveAll") +
                    "')){location='"+ aUrl +"'} else {return false;}\">" +
                    paramRequest.getLocaleString("delall") + "</a>\n");
        }
        sbf.append("    </div>\n");
        sbf.append("  </div>\n");
        sbf.append("  <script type=\"text/javascript\">\n" +
                   "    dojo.addClass(dojo.byId('bkm-showAll'), 'swb-bkm-boldElement');" +
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

    /**
     * Displays the list of tags ordered as deined by sortType.
     * @param sortType Sort type for the group to display.
     * @param groupId ID ide
     * @param paramRequest
     * @return
     * @throws org.semanticwb.portal.api.SWBResourceException
     */
    public String renderContent(int sortType, String groupId, SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuffer sbf = new StringBuffer();
        String generalName = getResourceBase().getId() + "/general";
        String untaggedName = getResourceBase().getId() + "/untagged";
        Iterator<BookmarkGroup> groups = null;


        if (sortType != SORT_BYTAGS) {
            sbf.append(listEntriesByGroup(sortType, groupId, paramRequest));
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
        return sbf.toString();
    }

    public void updateTags(String eId, String oldTags, String newTags, WebSite model) {
        BookmarkGroup generalGp = getGroupByName(getResourceBase().getId() + "/general");
        BookmarkGroup untaggedGp = getGroupByName(getResourceBase().getId() + "/untagged");
        BookmarkEntry entry = generalGp.getEntryById(eId);

        if (oldTags.equals(newTags)) {
            return;
        }

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