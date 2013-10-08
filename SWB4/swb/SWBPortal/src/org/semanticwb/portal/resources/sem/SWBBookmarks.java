/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.resources.sem;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * A Bookmarks manager resource. The bookmarks are sorted in groups.
 * <p>
 * Recurso de administración de favoritos. Los favoritos se almacenan en grupos.
 * 
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class SWBBookmarks extends org.semanticwb.portal.resources.sem.base.SWBBookmarksBase {

    /**Undefined sorting constant. Constante para ordenamiento indefinido.*/
    public static final int SORT_NOSORTED = 0;
    /**Name-based sorting constant. Constante para ordenamiento por nombre.*/
    public static final int SORT_BYNAME = 1;
    /**Tags-based sorting constant. Constante para ordenamiento por tags.*/
    public static final int SORT_BYTAGS = 2;
    /**Date-based sorting constant. Constante para ordenamiento por fecha.*/
    public static final int SORT_BYDATE = 3;
    
    /** The Constant DISPLAY_LIST. */
    public static final int DISPLAY_LIST = 4;
    
    /** The Constant DISPLAY_TREE. */
    public static final int DISPLAY_TREE = 5;
    
    /** The Constant DISPLAY_CLOUD. */
    public static final int DISPLAY_CLOUD = 6;
    /**BookmarkEntry date-based comparator. Comparador de entradas basado en fecha.*/
    public static final Comparator DATE_ORDER_ASC = new Comparator() {

        public int compare(Object arg0, Object arg1) {
            BookmarkEntry e1 = (BookmarkEntry) arg0;
            BookmarkEntry e2 = (BookmarkEntry) arg1;
            return e2.getCreated().compareTo(e1.getCreated());
        }
    };
    /**BookmarkEntry name-based comparator. Comparador de entradas basado en nombre.*/
    public static Comparator NAME_ORDER_DESC = new Comparator() {

        public int compare(Object arg0, Object arg1) {
            BookmarkEntry e1 = (BookmarkEntry) arg0;
            BookmarkEntry e2 = (BookmarkEntry) arg1;
            return -e2.getTitle().compareTo(e1.getTitle());
        }
    };

    /** The log. */
    public static Logger log = SWBUtils.getLogger(SWBBookmarks.class);

    /**
     * Empty constructor.
     * <p>
     * Constructor vacío.
     */
    public SWBBookmarks() {
    }

    /**
     * Default constructor.
     * <p>
     * Constructor por defecto.
     * 
     * @param base the base
     */
    public SWBBookmarks(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericSemResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        User user = response.getUser();
        WebSite model = response.getWebPage().getWebSite();        
        String tgs = request.getParameter("tags");        

        if(tgs == null) {
            tgs = "";
        } else {
            tgs=tgs.trim();
        }

        if (action.equals("ADDNEW")) {
            if (entryExists(user, response.getWebPage().getTitle(), response.getWebPage().getUrl())) {
                return;
            }
            
            //Create new bookmark entry and fill data
            BookmarkEntry entry = BookmarkEntry.createBookmarkEntry(model);
            tgs = stripHtmlTags(tgs);

            entry.setTitle(response.getWebPage().getTitle());
            entry.setBookmarkURL(response.getWebPage().getUrl());
            //entry.setTitle(stripHtmlTags(request.getParameter("title")));
            //entry.setBookmarkURL(stripHtmlTags(request.getParameter("urllink")));
            //entry.setDescription(stripHtmlTags(request.getParameter("description")));
            //entry.setTags(tgs);

            //Separate tags string
            String tags[] = tgs.split(",");

            //If no tags, add entry to untagged group
            if (tgs.equals("")) {
                BookmarkGroup group = getUserBookmarkGroupByName(user, "untagged");

                //If no untagged group, create one
                if (group == null) {
                    group = createUserBookmarkGroup(model, user, "untagged");
                    addGroup(group);
                }

                //Add entry to untagged group
                if (group != null) {
                    group.addEntry(entry);
                }
            } else {
                //For each tag
                for (int i = 0; i < tags.length; i++) {
                    BookmarkGroup group = getUserBookmarkGroupByName(user, tags[i].trim());

                    //If group named as tag does not exist, create it
                    if (group == null) {
                        group = createUserBookmarkGroup(model, user, tags[i].trim());
                        addGroup(group);
                    }
                    //Add entry to group
                    group.addEntry(entry);
                }
            }

            //Add entry to general group
            BookmarkGroup group = getUserBookmarkGroupByName(user, "general");
            if (group == null) {
                group = createUserBookmarkGroup(model, user, "general");
                addGroup(group);
            }
            group.addEntry(entry);
            //response.setCallMethod(response.Call_CONTENT);
            //response.setMode(response.Mode_VIEW);
        } else if (action.equals("DELALL")) {
            if (user.isSigned()) {
                //Get user's bookmark groups
                ArrayList<BookmarkGroup> groups = getUserBookmarkGroups(user);

                for (BookmarkGroup group : groups) {
                    Iterator<BookmarkEntry> entries = group.listEntrys();

                    //Remove all entries from group
                    while (entries.hasNext()) {
                        BookmarkEntry entry = entries.next();
                        group.removeEntry(entry);
                    }

                    //If empty, remove group
                    if (group.getEntryCount() == 0) {
                        removeGroup(group);
                    }
                }
            }
            response.setCallMethod(response.Call_CONTENT);
            response.setMode(response.Mode_VIEW);
        } else if (action.equals("SORT")) {
            if (user.isSigned()) {
                setSortType(Integer.parseInt(request.getParameter("oType")));
            }
            response.setCallMethod(response.Call_CONTENT);
            response.setMode(response.Mode_VIEW);
        } else if (action.equals("DELETE")) {
            if (user.isSigned()) {
                //Get user's bookmark groups
                ArrayList<BookmarkGroup> groups = getUserBookmarkGroups(user);

                for (BookmarkGroup group : groups) {
                    //Get entry in group
                    BookmarkEntry entry = group.getEntryById(request.getParameter("id"));

                    //If entry exists, remove it from group
                    if (entry != null) {
                        group.removeEntry(entry);
                        //If group is empty, remove it
                        if (group.getEntryCount() == 0) {
                            removeGroup(group);
                        }
                    }
                }
            }
            //response.setCallMethod(response.Call_CONTENT);
            //response.setMode(response.Mode_VIEW);
        } else if (action.equals("EDIT")) {
            //Get bookmarkgroup data from request
            String title = stripHtmlTags(request.getParameter("title"));
            tgs = stripHtmlTags(request.getParameter("tags").trim());
            String url = stripHtmlTags(request.getParameter("urllink"));
            String description = stripHtmlTags(request.getParameter("description"));

            //Get general group
            BookmarkGroup group = getUserBookmarkGroupByName(user, "general");

            if (group != null) {
                BookmarkEntry entry = group.getEntryById(request.getParameter("id"));
                //Update entry data
                if (entry != null) {
                    entry.setTitle(title);
                    entry.setBookmarkURL(url);
                    entry.setDescription(description);
                    updateTags(user, entry.getSemanticObject().getId(), entry.getTags(), tgs, response.getWebPage().getWebSite());
                    entry.setTags(tgs);
                }
            }
            response.setCallMethod(response.Call_CONTENT);
            response.setMode(response.Mode_VIEW);
        } else {
            super.processAction(request, response);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();

        if (mode.equals(paramRequest.Mode_VIEW)) {
            doView(request, response, paramRequest);
        } else if (mode.equals("ADDNEW")) {
            doAddNew(request, response, paramRequest);
        } else if (mode.equals("RLIST")) {
            doRenderList(request, response, paramRequest);
        } else if (mode.equals("RCONTENT")) {
            doRenderContent(request, response, paramRequest);
        } else if (mode.equals("EDIT")) {
            doEdit(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * Do render list.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doRenderList(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.print(renderEntriesByUserGroup(request.getParameter("gid"), getSortType(), paramRequest));
    }

    /**
     * Do add new.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doAddNew(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        SWBResourceURL aUrl = paramRequest.getActionUrl().setAction("ADDNEW");
        String url = request.getParameter("url");
        PrintWriter out = response.getWriter();
        StringBuffer sbf = new StringBuffer();
        String lang = "es";

        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        if (!paramRequest.getUser().isSigned()) {
            out.print(paramRequest.getLocaleString("msgNotLogged"));
            return;
        }

        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }

        String title = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_title.getDisplayName(lang), false);
        String link = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_bookmarkURL.getDisplayName(lang), false);
        String desc = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_description.getDisplayName(lang), false);
        String tags = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_tags.getDisplayName(lang), false);

        //TODO: Cambiar por un FormManager
        sbf.append("<div class=\"swbform\">\n" +
                "  <form dojoType=\"dijit.form.Form\" " +
                "id=\"" + createId("bookmark") + " \" " +
                "action=\"" + aUrl + "\" method=\"post\" " +
                "onsubmit=\"submitForm('" + createId("bookmark") + "');" +
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
                "value=\"" + ((url == null) ? "http://" : url) + "\" " +
                ((url != null) ? " readonly=\"readonly\" " : "") + "></input>\n" +
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
                paramRequest.getLocaleString("lblAdd") + "</button>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </fieldset>\n" +
                "  </form>" +
                "</div>");
        out.print(sbf.toString());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doEdit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        BookmarkGroup generalGp = getUserBookmarkGroupByName(paramRequest.getUser(), "general");
        SWBResourceURL aUrl = paramRequest.getActionUrl().setAction("EDIT");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        StringBuffer sbf = new StringBuffer();
        String lang = "es";

        response.setContentType("text/html;");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        //If user is not logged, abort
        if (!paramRequest.getUser().isSigned()) {
            out.print(paramRequest.getLocaleString("msgNotLogged"));
            return;
        }

        //If no entries in general group, abort
        if (generalGp == null) {
            return;
        }

        //Get user language
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }

        //Sanitize input
        String title = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_title.getDisplayName(lang), false);
        String link = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_bookmarkURL.getDisplayName(lang), false);
        String desc = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_description.getDisplayName(lang), false);
        String tags = SWBUtils.TEXT.replaceSpecialCharacters(BookmarkEntry.swb_res_bkm_tags.getDisplayName(lang), false);

        //Get bookmark entry from general group
        BookmarkEntry entry = generalGp.getEntryById(id);
        aUrl.setParameter("id", id);

        //Render form to edit entry
        sbf.append("  <div class=\"swbform\">\n" +
                "    <form dojoType=\"dijit.form.Form\" id=\"" + createId("bookmark") + "\" " +
                "action=\"" + aUrl + "\" method=\"post\" " +
                "onsubmit=\"submitForm('" + createId("bookmark") + "'); " +
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
                "value=\"" + entry.getBookmarkURL() + "\" readonly=\"readonly\"></input>\n" +
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
                paramRequest.getLocaleString("lblOk") + "</button>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "      </fieldset>\n" +
                "    </form>" +
                "  </div>");
        out.print(sbf.toString());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path ="/swbadmin/jsp/bookmarks/bookmarksView.jsp";
        User user = paramRequest.getUser();
        String showList = paramRequest.getArgument("showList", "false");

        if (user.getURI() == null)
            return;

        String url = "";
        Resourceable rsa = paramRequest.getResourceBase().getResourceable();
        if (rsa != null && rsa instanceof WebPage) {
                url = ((WebPage) rsa).getUrl();
        }
        
        /*if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) {
            doShowGadget(request, response, paramRequest);
        } else {
            doShowAdmin(request, response, paramRequest);
        }*/

        Iterator<BookmarkEntry> entries = listAllEntriesByUser(user);
        try {
            if (entries != null && entries.hasNext())
                request.setAttribute("entries", entries);
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("l", Boolean.valueOf(showList));
            //request.setAttribute("admurl", url);
            
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Creates a unique ID for HTML elements using the resource base ID.
     * <p>
     * Crea un ID único para elementos HTML usando el ID del recurso.
     *
     * @param postfix   local ID for an HTML element. ID local para el elemento
     *                  HTML.
     *
     * @return          unique global ID for an HTML element. ID único para el
     *                  elemento HTML.
     */
    private String createId(String postfix) {
        return getResourceBase().getId() + "/" + postfix;
    }

    /**
     * Do show admin.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doShowAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        PrintWriter out = response.getWriter();
        StringBuffer sbf = new StringBuffer();
        int sType = getSortType();

        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        //If user is not logged, abort
        if (!paramRequest.getUser().isSigned()) {
            out.print(paramRequest.getLocaleString("msgNotLogged"));
            return;
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
                "      alert('" + paramRequest.getLocaleString("msgFieldError") + "');\n" +
                "    } else {\n" +
                "      dojo.byId('" + createId("bkm-send") + "').form.submit();\n" +
                "    }\n" +
                "  }\n" +
                "</script>\n");

        sbf.append("<div dojoType=\"dijit.Dialog\" class=\"soria\" style=\"display:none;\" id=\"swbDialog\" " +
                "title=\"Agregar\" onFocus=\"hideApplet(true);\" onBlur=\"if(!this.open)hideApplet(false);\" >\n" +
                "  <div dojoType=\"dojox.layout.ContentPane\" class=\"soria\" id=\"swbDialogImp\" executeScripts=\"true\">\n" +
                "    Cargando...\n" +
                "  </div>\n" +
                "</div>\n");

        sbf.append("<div class=\"swb-bkm-container\">\n" +
                "  <div class=\"swb-bkm-header\">\n" +
                "    <div class=\"swb-bkm-navmenu\">\n");

        //Set aUrl action to ORDER
        aUrl.setAction("SORT");
        aUrl.setParameter("oType", String.valueOf(SORT_BYNAME));
        sbf.append(paramRequest.getLocaleString("lblBy") + ":&nbsp;[");
        sbf.append((sType == SORT_BYNAME) ? "      <b>" : "      <a href=\"" + aUrl + "\">");
        sbf.append(paramRequest.getLocaleString("lblByname"));
        sbf.append((sType == SORT_BYNAME) ? "</b> |" : "</a> |");
        aUrl.setParameter("oType", String.valueOf(SORT_BYDATE));
        sbf.append((sType == SORT_BYDATE) ? "<b>" : "<a href=\"" + aUrl + "\">");
        sbf.append(paramRequest.getLocaleString("lblBydate"));
        sbf.append((sType == SORT_BYDATE) ? "</b> |" : "</a> |");
        aUrl.setParameter("oType", String.valueOf(SORT_BYTAGS));
        sbf.append((sType == SORT_BYTAGS) ? "<b>" : "<a href=\"" + aUrl + "\">");
        sbf.append(paramRequest.getLocaleString("lblBytag"));
        sbf.append((sType == SORT_BYTAGS) ? "</b> |" : "</a>]");

        BookmarkGroup generalGp = getUserBookmarkGroupByName(paramRequest.getUser(), "general");
        //Set url mode to VIEW
        rUrl.setMode(rUrl.Mode_VIEW);
        sbf.append("    </div>\n" +
                "  </div>\n" +
                "  <div class=\"swb-bkm-wrapper\">\n" +
                "    <div class=\"swb-bkm-content\" id=\"" +
                createId("swb-bkm-content") + "\">\n" +
                (generalGp == null ? "" : renderEntriesByUserGroup(generalGp.getSemanticObject().getId(), paramRequest)) +
                "    </div>\n" +
                "  </div>\n" +
                renderMenu(paramRequest) +
                "</div>\n");
        out.print(sbf.toString());
    }

    /**
     * Do show gadget.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doShowGadget(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        User user = paramRequest.getUser();
        StringBuffer sbf = new StringBuffer();
        String lang = "es";
        
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        //Get user language
        if (user != null) {
            lang = user.getLanguage();
        }

        //Initialize sort type
        if (getSortType() == SORT_NOSORTED) {
            setSortType(SORT_BYDATE);
        }

        if (!user.isSigned()) {
            return;
        }

        String url = "";
        Resourceable rsa = getResourceBase().getResourceable();
        if (rsa != null && rsa instanceof WebPage) {
                url = ((WebPage) rsa).getUrl();
        }
        //Add necesary scripting
        sbf.append("<script type=\"text/javascript\" charset=\"utf-8\" src=\"" + SWBPlatform.getContextPath() + "/swbadmin/js/swb.js\"></script>\n");
        sbf.append("<script type=\"text/javascript\" charset=\"utf-8\" src=\"" + SWBPlatform.getContextPath() + "/swbadmin/js/swb_admin.js\"></script>\n");

        sbf.append("    <script type=\"text/javascript\">\n" +
                "      dojo.require(\"dijit.form.Form\");\n" +
                "      dojo.require(\"dijit.form.Button\");\n" +
                "      dojo.require(\"dijit.form.ValidationTextBox\");\n" +
                "      dojo.require(\"dijit.form.FilteringSelect\");\n" +
                "      dojo.require(\"dijit.Dialog\");\n" +
                "      dojo.require(\"dojox.layout.ContentPane\");\n" +
                "      dojo.require(\"dojo.parser\");\n\n" +
                "      var eCount;\n\n" +
                "      function isEmpty(objid) {\n" +
                "        var obj = dojo.byId(objid);\n" +
                "        if (obj == null || obj.value == '' \n" +
                "            || obj.value.charAt(0) == ' ') {\n" +
                "          return true;\n" +
                "        }else {\n" +
                "          return false;\n" +
                "        }\n" +
                "      }\n\n" +
                "      function doApply() {\n" +
                "        eCount=0;\n" +
                "        if (isEmpty('" + createId("bkm-title") + "')) eCount++;\n" +
                "        if (isEmpty('" + createId("bkm-url") + "')) eCount++;\n" +
                "        if (isEmpty('" + createId("bkm-desc") + "')) eCount++;\n" +
                "        if (eCount > 0) {\n" +
                "          alert('" + paramRequest.getLocaleString("msgFieldError") + "');\n" +
                "        } else {\n" +
                "          dojo.byId('" + createId("bkm-send") + "').form.submit();\n" +
                "        }\n" +
                "      }\n\n" +
                "      function refreshContent(url) {\n" +
                "        var val = dojo.byId('" + createId("swb-bkm-select") + "').value;\n" +
                "        dojo.byId('" + createId("bookmarksList") + "').innerHtml='';\n" +
                "        getHtml(url + '?gid=' + val, '" + createId("bookmarksList") + "');\n" +
                "      }\n\n" +
                "      function openWindow(loc, args) {\n" +
                "        window.location=loc;" +
                "      }\n" +
                "    </script>\n");

        //Set url call method to Call_DIRECT to make an AJAX call
        rUrl.setCallMethod(rUrl.Call_DIRECT).setMode("ADDNEW");
        rUrl.setParameter("url", paramRequest.getWebPage().getRealUrl());

        sbf.append("<div dojoType=\"dijit.Dialog\" class=\"soria\" style=\"display:none;\" id=\"swbDialog\" " +
                "title=\"Agregar\" onFocus=\"hideApplet(true);\" onBlur=\"if(!this.open)hideApplet(false);\" >\n" +
                "  <div dojoType=\"dojox.layout.ContentPane\" class=\"soria\" id=\"swbDialogImp\" executeScripts=\"true\">\n" +
                "    Cargando...\n" +
                "  </div>\n" +
                "</div>\n");

        //If user is signed, show options and bookmark list
        if (user.isSigned()) {
            sbf.append("<div class=\"bookmarks\">");
            ///-------Inicia bloque de controles
            sbf.append("  <p>\n" +
                    "    <a href=\"#\" onclick=\"showDialog('" + rUrl + "', '" + paramRequest.getLocaleString("lblAdd") +
                    " " + BookmarkEntry.sclass.getDisplayName(lang) +
                    "'); return false;\">" +
                    paramRequest.getLocaleString("lblMark") + "</a> \n" +
                    "  <a href=\""+ url +"\">" +
                    paramRequest.getLocaleString("lblManage") + "</a>\n" +
                    "  </p>\n");
            ////-------Termina bloque de controles

            //Get general bookmarks group
            BookmarkGroup generalGp = getUserBookmarkGroupByName(user, "general");

            //If user has groups, show display options and bookmarks list
            if (generalGp != null) {
                //Set url call method to Call_DIRECT to make an AJAX call
                rUrl = paramRequest.getRenderUrl();
                rUrl.setCallMethod(rUrl.Call_DIRECT).setMode("RLIST");

                ////-------Inicia formulario con select
                sbf.append("<p>\n" +
                        "  <form>\n" +
                        "    <select id=\"" + createId("swb-bkm-select") + "\" " +
                        "onChange=\"refreshContent('" + rUrl + "');\">\n");

                //Display group names in select element
                ArrayList<BookmarkGroup> groups = getUserBookmarkGroups(user);
                for (BookmarkGroup group : groups) {
                    sbf.append("<option ");
                    if (group.getTitle().equals("general")) {
                        sbf.append("selected value=\"" + group.getSemanticObject().getId() + "\">" +
                                paramRequest.getLocaleString("lblShowAll") + "</option>\n");
                    } else if (group.getEntryCount() > 0) {
                        sbf.append("value=\"" + group.getSemanticObject().getId() + "\">");
                        if (group.getTitle().equals("untagged")) {
                            sbf.append(paramRequest.getLocaleString("lblNotags") +
                                    "</option>\n");
                        } else {
                            sbf.append(group.getTitle() + "</option>\n");
                        }
                    }
                }
                sbf.append("    </select>\n" +
                        "  </form>\n" +
                        "</p>\n");
                ////-------Termina formulario con select
                ////-------Inicia lista de marcadores
                sbf.append("  <div id=\"" + createId("bookmarksList") + "\">\n" +
                        renderEntriesByUserGroup(generalGp.getSemanticObject().getId(), getSortType(), paramRequest) +
                        "  </div>\n");

                ////-------Termina lista de marcadores*/
            }
            sbf.append("</div>");
        } else {
            sbf.append(paramRequest.getLocaleString("msgNotLogged"));
        }
        out.print(sbf.toString());
    }

    /**
     * Gets an iterator to the user's Bookmarks.
     * <p>
     * Devuelve un iterador a los favoritos del usuario.
     * 
     * @param user owner of the bookmarks. Propietario de los favoritos.
     * @return the iterator
     * @throws SWBResourceException the sWB resource exception
     */
    public Iterator<BookmarkEntry> listAllEntriesByUser(User user) throws SWBResourceException {
        BookmarkGroup group = getUserBookmarkGroupByName(user, "general");

        if (group != null)
            return group.listEntrys();
        else
            return null;
    }

    /**
     * Method to render the list of entries of the given group.
     * <p>
     * Método para construir una lista de favoritos pertenecientes a un grupo dado.
     * 
     * @param groupId   ID of the group to list entries from. ID del grupo del
     * que se listarán las entradas.
     * @param sortType  Type of sorting (date, name, tags). Tipo de ordenamiento
     * de la lista (por fecha, por tags, por nombre).
     * @param paramRequest the param request
     * @return          HTML code for the list of entries. Código HTML de la
     * lista de favoritos.
     * @throws SWBResourceException the sWB resource exception
     */
    public String renderEntriesByUserGroup(String groupId, int sortType, SWBParamRequest paramRequest) throws SWBResourceException {
        ArrayList<BookmarkEntry> sEntries = new ArrayList<BookmarkEntry>();
        Format formatter = new SimpleDateFormat("dd MMM");
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        User user = paramRequest.getUser();
        BookmarkGroup group = getUserBookmarkGroupById(user, groupId);
        StringBuffer sbf = new StringBuffer();
        String lang = "es";

        //If user is not logged, abort
        if (!user.isSigned()) {
            return paramRequest.getLocaleString("msgNotLogged");
        }

        if (group == null || group.getEntryCount() == 0) {
            return " ";
        }

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

        sbf.append("  <ul>\n");
        //Print bookmarks list
        entries = sEntries.iterator();
        for (BookmarkEntry entry : sEntries) {
            sbf.append("    <li>\n" +
                    "      <a href=\"" + entry.getBookmarkURL() + "\">" + entry.getTitle() + "</a>\n" +
                    "    </li>\n");
        }
        sbf.append("  </ul>\n");
        return sbf.toString();
    }

    /**
     * Method to render the list of entries of the given group.
     * <p>
     * Método para construir una lista de favoritos pertenecientes a un grupo dado.
     * 
     * @param groupId   ID of the group to list entries from. ID del grupo del
     * que se listarán las entradas.
     * @param showInfo  wether to show bookmark's extended info. Indica si se
     * debe mostrar toda la información de cada entrada.
     * @param sortType  type of sorting (date, name, tags). Tipo de ordenamiento
     * de la lista (por fecha, por tags, por nombre).
     * @param paramRequest the param request
     * @return          HTML code for the list of entries. Código HTML de la
     * lista de favoritos.
     * @throws SWBResourceException the sWB resource exception
     */
    public String renderEntriesByUserGroup(String groupId, boolean showInfo, int sortType, SWBParamRequest paramRequest) throws SWBResourceException {
        ArrayList<BookmarkEntry> sEntries = new ArrayList<BookmarkEntry>();
        Format formatter = new SimpleDateFormat("dd MMM");
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        User user = paramRequest.getUser();
        BookmarkGroup group = getUserBookmarkGroupById(user, groupId);
        StringBuffer sbf = new StringBuffer();
        String lang = "es";

        //If user is not logged, abort
        if (!user.isSigned()) {
            return paramRequest.getLocaleString("msgNotLogged");
        }

        if (group == null || group.getEntryCount() == 0) {
            return " ";
        }

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

        sbf.append("                <table border=\"0\" cellpadding=\"0\" " +
                "cellspacing=\"10\" width=\"100%\">\n" +
                "                  <tbody>\n");

        //Print bookmarks list
        entries = sEntries.iterator();
        for (BookmarkEntry entry : sEntries) {
            String eid = entry.getSemanticObject().getId();
            sbf.append("                    <tr class=\"entry\">\n" +
                    "                      <td>\n" +
                    "                        <div id=\"" + createId("r_entry_" + eid) + "\">\n" +
                    "                          <a title=\"" + entry.getBookmarkURL() + "\" " +
                    "href=\"" + entry.getBookmarkURL() + "\">" + entry.getTitle() + "</a>\n");

            //Add extra information if in MANAGE mode
            if (showInfo) {
                sbf.append("&nbsp;-&nbsp;" + entry.getBookmarkURL() + "&nbsp;" +
                        formatter.format(entry.getCreated()) + "&nbsp;");

                //Set url call method to Call_DIRECT to make an AJAX call
                rUrl.setCallMethod(rUrl.Call_DIRECT).setMode("EDIT");
                rUrl.setParameter("id", eid);

                //Add EDIT link
                sbf.append("<a title=\"" + paramRequest.getLocaleString("lblEdit") +
                        "\" href=\"#\" onclick=\"showDialog('" + rUrl +
                        "', '" + paramRequest.getLocaleString("lblEdit") + " " +
                        BookmarkEntry.sclass.getDisplayName(lang) + "');\">" +
                        "<img src=\"" + SWBPlatform.getContextPath() +
                        "/swbadmin/icons/editar_1.gif\" border=\"0\" " +
                        "alt=\"" + paramRequest.getLocaleString("lblEdit") + "\">" +
                        "</a>&nbsp;");

                //Set aUrl action to DELETE and attach entry id
                aUrl.setAction("DELETE");
                aUrl.setParameter("id", eid);

                //Add DELETE link
                sbf.append("<a title=\"" + paramRequest.getLocaleString("lblDelete") +
                        "\" href=\"#\" " + "onclick=\"if (confirm('" +
                        paramRequest.getLocaleString("msgRemove") + "'))" +
                        "{location='" + aUrl + "'} else {return false;}\">" +
                        "<img src=\"" + SWBPlatform.getContextPath() +
                        "/swbadmin/images/delete.gif\" border=\"0\" " +
                        "alt=\"" + paramRequest.getLocaleString("lblDelete") +
                        "\"></a><br>");

                //Add tags and descriptions
                sbf.append("[");
                String[] tags = entry.getTags().split(",");
                for (int i = 0; i < tags.length; i++) {
                    group = getUserBookmarkGroupByName(user, tags[i].trim());
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
                "  </table>\n");
        return sbf.toString();
    }

    /**
     * Creates a bookmarks group for the given user. 
     * <p>
     * Crea un grupo de favoritos para el usuario especificado.
     *
     * @param model         model to create group. Modelo para crear el grupo.
     * @param user          the owner of the group. El propietario del grupo.
     * @param groupTitle    title for the new bookmarks group. Título del nuevo
     *                      grupo de favoritos.
     * 
     * @return              {@link BookmarksGroup}. Grupo de favoritos.
     */
    public BookmarkGroup createUserBookmarkGroup(WebSite model, User user, String groupTitle) {
        BookmarkGroup res = null;
        if (user.isSigned()) {
            res = BookmarkGroup.createBookmarkGroup(model);
            res.setTitle(groupTitle);
            res.setCreator(user);
        }
        return res;
    }

    /**
     * Gets a bookmarks group with the specified owner and title.
     * <p>
     * Obtiene un grupo de favoritos con el título y propietario especificados.
     *
     * @param user  owner of the bookmarks group. Propietario del grupo de
     *              favoritos.
     * @param title title of the bookmarks group. Título del grupo de favoritos.
     *
     * @return      {@link BookmarkGroup} or {@code null} if user does not have a
     *              Bookmarks Group with the specified title. Grupo de marcadores
     *              o {@code null} si el usuario no posee un grupo con el título
     *              especificado.
     */
    public BookmarkGroup getUserBookmarkGroupByName(User user, String title) {
        if (user.isSigned()) {
            ArrayList<BookmarkGroup> groups = getUserBookmarkGroups(user);

            for (BookmarkGroup group : groups) {
                if (group.getTitle().equals(title)) {
                    return group;
                }
            }
        }
        return null;
    }

    /**
     * Gets a bookmarks group with the specified owner and ID.
     * <p>
     * Obtiene un grupo de favoritos con el identificador y propietario
     * especificados.
     * 
     * @param user  owner of the bookmarks group. Propietario del grupo de
     *              favoritos.
     * @param gId   ID of the desired Bookmarks Group. ID del grupo de favoritos
     *              deseado.
     * @return      {@link BookmarksGroup} or {@code null} if the user does not
     *              have a group with the given ID. Grupo de marcadores o
     *              {@code null} si el usuario no posee un grupo con el ID
     *              especificado.
     */
    public BookmarkGroup getUserBookmarkGroupById(User user, String gId) {
        if (user.isSigned()) {
            ArrayList<BookmarkGroup> groups = getUserBookmarkGroups(user);
            for (BookmarkGroup group : groups) {
                if (group.getSemanticObject().getId().equals(gId)) {
                    return group;
                }
            }
        }
        return null;
    }

    /**
     * Gets a list of the user's Bookmarks Groups. 
     * <p>
     * Obtiene una lista con los grupos de favoritos de un usuario.
     *
     * @param user  user to get Bookmarks groups from. Usuario del cual se
     *              obtendrá la lista de grupos de favoritos.
     *
     * @return      list of {@link BookmarkGroup} objects. Lista de grupos de
     *              marcadores.
     */
    public ArrayList<BookmarkGroup> getUserBookmarkGroups(User user) {
        ArrayList<BookmarkGroup> groups = new ArrayList<BookmarkGroup>();

        if (user.isSigned()) {
            Iterator<BookmarkGroup> git = listGroups();
            while (git.hasNext()) {
                BookmarkGroup gp = git.next();
                if (gp!=null && gp.getCreator()!=null && user!=null && gp.getCreator().equals(user)) {
                    groups.add(gp);
                }
            }
        }
        return groups;
    }

    /**
     * Creates navigation menu for Bookmarks administration.
     * <p>
     * Crea un menú de navegación para la administración de favoritos.
     * 
     * @param paramRequest the param request
     * @return  HTML String for the navigation menu. Código HTML del menú de
     * navegación.
     * @throws SWBResourceException the sWB resource exception
     */
    public String renderMenu(SWBParamRequest paramRequest) throws SWBResourceException {
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        StringBuffer sbf = new StringBuffer();
        String lang = "es";
        User user = paramRequest.getUser();

        //If user is not logged, abort
        if (!user.isSigned()) {
            return paramRequest.getLocaleString("msgNotLogged");
        }

        //Get user language
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }

        //Set url call method to Call_DIRECT to make an AJAX call
        rUrl.setCallMethod(rUrl.Call_DIRECT).setMode("RCONTENT");

        sbf.append("<div class=\"swb-bkm-navbarmain\">\n" +
                "  <div class=\"swb-bkm-navigation\">\n");

        //Render group links
        ArrayList<BookmarkGroup> groups = getUserBookmarkGroups(paramRequest.getUser());
        for (BookmarkGroup group : groups) {
            String gid = group.getSemanticObject().getId();

            if (!group.getTitle().equals("general")) {
                rUrl.setParameter("gid", gid);
                if (!group.getTitle().equals("")) {
                    if (group.getEntryCount() > 0) {
                        sbf.append("    <a class=\"swb-bkm-menuOpt\" id=\"" +
                                createId(gid) + "\" href=\"#\" " +
                                "onclick=\"dojo.query('.swb-bkm-menuOpt').removeClass('swb-bkm-boldElement');" +
                                "dojo.addClass(dojo.byId('" + createId(gid) + "'), 'swb-bkm-boldElement');" +
                                "getHtml('" + rUrl + "', '" + createId("swb-bkm-content") + "');\">" +
                                (group.getTitle().equals("untagged") ? paramRequest.getLocaleString("lblNotags") : group.getTitle()) +
                                "(" + group.getEntryCount() + ")</a><br>\n");
                    }
                } else if (group.getEntryCount() > 0) {
                    sbf.append("    <a class=\"swb-bkm-menuOpt\" id=\"" +
                            createId(gid) + "\" href=\"#\" " +
                            "onclick=\"dojo.query('.swb-bkm-menuOpt').removeClass('swb-bkm-boldElement');" +
                            "dojo.addClass(dojo.byId('" + createId(gid) + "'), 'swb-bkm-boldElement');" +
                            "getHtml('" + rUrl + "', '" + createId("swb-bkm-content") + "');\">" +
                            paramRequest.getLocaleString("lblNotags") + "(" +
                            group.getEntryCount() + ")" + "</a><br>\n");
                }
            }
        }

        //Render 'SHOW ALL' link
        BookmarkGroup generalGp = getUserBookmarkGroupByName(paramRequest.getUser(), "general");
        if (generalGp != null) {
            rUrl.setParameter("gid", generalGp.getSemanticObject().getId());
            sbf.append("    <a href=\"#\" class=\"swb-bkm-menuOpt\" " +
                    "id=\"" + createId("bkm-showAll") + "\" " +
                    "onclick=\"dojo.query('.swb-bkm-menuOpt').removeClass('swb-bkm-boldElement');" +
                    "dojo.addClass(dojo.byId('" + createId("bkm-showAll") + "'),'swb-bkm-boldElement');" +
                    "getHtml('" + rUrl + "', '" + createId("swb-bkm-content") + "');\">" +
                    paramRequest.getLocaleString("lblShowAll") + "</a><br>\n");
        }
        sbf.append("  </div>\n" +
                "  <div class=\"swb-bkm-extra\">\n");

        //Set url call method to Call_DIRECT to make an AJAX call
        rUrl = paramRequest.getRenderUrl();
        rUrl.setCallMethod(rUrl.Call_DIRECT).setMode("ADDNEW");

        //Add 'NEW BOOKMARK' link
        sbf.append("    <a href=\"#\" onclick=\"showDialog('" + rUrl + "', '" +
                paramRequest.getLocaleString("lblAdd") + " " +
                BookmarkEntry.sclass.getDisplayName(lang) + "');return false;\">" +
                paramRequest.getLocaleString("lblAdd") + " " +
                BookmarkEntry.sclass.getDisplayName(lang) + "</a><br>\n");

        //Add 'DELETE ALL' link
        if (generalGp != null) {
            if (generalGp.getEntryCount() > 0) {
                aUrl.setAction("DELALL");
                sbf.append("    <a href=\"#\" onclick=\"if(confirm('" +
                        paramRequest.getLocaleString("msgRemoveAll") +
                        "')){location='" + aUrl + "'} else {return false;}\">" +
                        paramRequest.getLocaleString("lblDelall") + "</a>\n");
            }
        }

        sbf.append("  </div>\n" +
                "</div>\n" +
                "  <script type=\"text/javascript\">\n" +
                "    dojo.addClass(dojo.byId('" + createId("bkm-showAll") + "'), 'swb-bkm-boldElement');\n" +
                "  </script>\n");
        return sbf.toString();
    }

    /**
     * Do render content.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doRenderContent(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.print(renderEntriesByUserGroup(request.getParameter("gid"), paramRequest));
    }

    
    /**
     * Render entries by user group.
     * 
     * @param gid the gid
     * @param paramRequest the param request
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    public String renderEntriesByUserGroup(String gid, SWBParamRequest paramRequest) throws IOException, SWBResourceException {
        BookmarkGroup group = getUserBookmarkGroupById(paramRequest.getUser(), gid);
        StringBuffer sbf = new StringBuffer();
        int sType = getSortType();

        if (group == null) {
            return " ";
        }

        if (!paramRequest.getUser().isSigned()) {
            return paramRequest.getLocaleString("msgNotLogged");
        }

        //If content will be showed ordered by tag
        if (sType == SORT_BYTAGS && group.getTitle().equals("general")) {
            //Get user's bookmark groups
            ArrayList<BookmarkGroup> groups = getUserBookmarkGroups(paramRequest.getUser());
            for (BookmarkGroup group2 : groups) {
                if (!group2.getTitle().equals("general")) {
                    if (group2.getTitle().equals("untagged")) {
                        sbf.append("<h1>" + paramRequest.getLocaleString("lblNotags") + "</h1>\n");
                    } else {
                        sbf.append("<h1>" + group2.getTitle() + "</h1>\n");
                    }
                    sbf.append(renderEntriesByUserGroup(group2.getSemanticObject().getId(), true, SORT_BYDATE, paramRequest));
                }
            }
        } else if (sType == SORT_BYTAGS) {
            if (!group.getTitle().equals("untagged")) {
                sbf.append("<h1>" + group.getTitle() + "</h1>\n");
            } else {
                sbf.append("<h1>" + paramRequest.getLocaleString("lblNotags") + "</h1>\n");
            }
            sbf.append(renderEntriesByUserGroup(gid, true, SORT_BYDATE, paramRequest));
        } else {
            sbf.append(renderEntriesByUserGroup(gid, true, sType, paramRequest));
        }
        return sbf.toString();
    }

    /**
     * Strips all HTML tags from the input string. 
     * <p>
     * Elimina los tags de HTML de la cadena proporcionada.
     *
     * @param input string to strip tags from. Cadena de la cual se eliminarán
     *              las etiquetas.
     *
     * @return      string without HTML tags. Cadena sin etiquetas HTML.
     */
    public String stripHtmlTags(String input) {
        return input.replaceAll("<(.|\n)+?>", "");
    }

    /**
     * Updates the tags of an user bookmark.
     * <p>
     * Actualiza los tags de un favorito.
     * 
     * @param user      owner of the bookmark. Propietario del favorito.
     * @param eId       bookmark ID. Identificador del favorito.
     * @param oldTags   old Tags. Etiquetas antiguas.
     * @param newTags   new Tags. Etiquetas nuevas.
     * @param model     model to use (generally a WebSite). Modelo a usar
     *                  (generalmente un WebSite).
     */
    public void updateTags(User user, String eId, String oldTags, String newTags, WebSite model) {
        BookmarkGroup generalGp = getUserBookmarkGroupByName(user, "general");
        BookmarkGroup untaggedGp = getUserBookmarkGroupByName(user, "untagged");
        BookmarkEntry entry = generalGp.getEntryById(eId);

        //If entry does not exist or there is no changes, return
        if (entry == null || oldTags.equals(newTags) || !user.isSigned()) {
            return;
        }

        String[] oTags = oldTags.split(",");
        String[] nTags = newTags.split(",");

        //Remove BookmarkEntry from groups
        if (oldTags.equals("")) {
            if (untaggedGp != null) {
                untaggedGp.removeEntry(entry);
            }
        } else {
            for (int i = 0; i < oTags.length; i++) {
                String groupName = oTags[i].trim();

                BookmarkGroup group = getUserBookmarkGroupByName(user, groupName);
                if (group != null) {
                    group.removeEntry(entry);
                    if (group.getEntryCount() == 0) {
                        removeGroup(group);
                    }
                }
            }
        }

        entry = generalGp.getEntryById(eId);
        //Add BookmarkEntry to new groups
        if (newTags.equals("")) {
            if (untaggedGp == null) {
                untaggedGp = createUserBookmarkGroup(model, user, "untagged");
                addGroup(untaggedGp);
            }
            untaggedGp.addEntry(entry);
        } else {
            for (int i = 0; i < nTags.length; i++) {
                String groupName = nTags[i].trim();

                BookmarkGroup group = getUserBookmarkGroupByName(user, groupName);
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

    /**
     * Checks wheter a bookmark entry exists.
     * <p>
     * Verifica si existe una entrada de favoritos.
     * 
     * @param user  owner of the bookmark. Propietario del favorito.
     * @param title title of the bookmark. Título del favorito.
     * @param url   URL of the bookmark. URL del favorito.
     * @return      {@code true} if a bookmark with the given URL exists for
     * the user. {@code true} si el usuario posee un favorito con
     * la URL especificada.
     * @throws SWBResourceException the sWB resource exception
     */
    public boolean entryExists(User user, String title, String url) throws SWBResourceException {
        Iterator<BookmarkEntry> entries = listAllEntriesByUser(user);

        if (entries != null) {
            while (entries.hasNext()) {
                BookmarkEntry entry = entries.next();
                if (entry.getTitle().equals(title) && entry.getBookmarkURL().equals(url)) {
                    return true;
                }
            }
        }
        return false;
    }
}