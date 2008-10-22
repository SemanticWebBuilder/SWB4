/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.*;
import java.util.*;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.portal.api.*;
import org.w3c.dom.*;

/**
 *
 * @author juan.fernandez
 */
public class SWBARule extends GenericResource {

    private Logger log = SWBUtils.getLogger(SWBARule.class);
    StringBuffer sbTree = null;
    HashMap comboAtt = null;
    Vector vecOrderAtt = null;
    Document localDoc = null;
    int elemNum = 0;
    String xmlAttr = null;

    /** Creates a new instance of Rules */
    public SWBARule() {
    }
//
//    /** User view, creates the rules
//     * @param request input parameters
//     * @param response an answer to the request
//     * @param paramRequest a list of objects (WebPage, user, action, ...)
//     * @throws AFException an AF Exception
//     * @throws IOException an IO Exception
//     */
//    @Override
//    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        Portlet base = getResourceBase();
//        WebPage topic = paramRequest.getTopic();
//        User user = paramRequest.getUser();
//        StringBuffer ret = new StringBuffer("");
//        PrintWriter out = response.getWriter();
//        WebPage tp = paramRequest.getTopic();
//        String tmparam = request.getParameter("tm");
//        if (tmparam == null) {
//            tmparam = paramRequest.getTopic().getWebSiteId();
//        }
//        if (request.getParameter("tm") != null && request.getParameter("tp") != null) {
//            tp = SWBContext.getWebSite(request.getParameter("tm")).getWebPage(request.getParameter("tp"));
//            tmparam = request.getParameter("tm");
//        }
//        WebSite tm = tp.getWebSite();
//
//        if (request.getParameter("id") != null || request.getParameter("act") != null) {
//            String rA = request.getParameter("act");
//            if (rA != null && rA.equals("removemsg")) {
//                out.println("<script>wbTree_remove();</script>");
//                //out.println("<font face=\"Verdana\" size=\"1\">");
//                out.println(paramRequest.getLocaleString("msgStatusRuleRemoved"));
//                //out.println("</font>");
//                rA = "";
//            }
//            if (rA != null && rA.equals("notremovemsg")) {
//                //out.println("<script>wbTree_remove();</script>");
//                //out.println("<font face=\"Verdana\" size=\"1\">");
//                out.println(paramRequest.getLocaleString("msgStatusRuleNotRemoved"));
//                //out.println("</font>");
//                rA = "";
//            }
//            if (rA == null) {
//                rA = "details";
//            }
//            if (rA.equals("add") || rA.equals("edit") || rA.equals("details") || rA.equals("history") || rA.equals("editcondition") || rA.equals("agregar") || rA.equals("insertcondition")) {
//
//                doEdit(request, response, paramRequest);
//            }
//            if (rA.equals("remove") || rA.equals("removeit") || rA.equals("removes") || rA.equals("update") || rA.equals("updatecondition") || rA.equals("addcondition") || rA.equals("deletecondition") || rA.equals("editadd")) {
//
//                String tmreq = request.getParameter("tm");
//                String idreq = request.getParameter("id");
//                String tpreq = request.getParameter("tp");
//                if (rA.equals("remove")) {
//                    try {
//
//                        Rule rul = SWBContext.getWebSite(tmreq).getRule(idreq);
//                        Iterator iobjs = rul.listRelatedObjects();
//
//                        if (iobjs.hasNext()) {
//                            SWBResourceURL urlWin = paramRequest.getRenderUrl();
//                            urlWin.setCallMethod(urlWin.Call_DIRECT);
//                            urlWin.setParameter("act", "removes");
//                            urlWin.setParameter("id", idreq);
//                            urlWin.setParameter("tm", tmreq);
//                            urlWin.setParameter("tp", tpreq);
//                            out.println("<script language=javascript>");
//                            out.println("window.open('" + urlWin.toString() + "','relatedElements','height=400,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=400');");
//                            out.println("</script>");
//                        } else {
//                            SWBResourceURL url = paramRequest.getActionUrl();
//                            url.setParameter("act", "removeit");
//                            if (request.getParameter("id") != null) {
//                                url.setParameter("id", idreq);
//                            }
//                            url.setParameter("tm", tmreq);
//                            url.setParameter("tp", tpreq);
//                            out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + url.toString() + "\">");
//                        }
//                    } catch (Exception e) {
//                        log.error("Error while display asociated elements with the rule with id:" + idreq + " in WebSite:" + tmreq, e);
//                    }
//                } else if (rA.equals("removes")) {
//                    try {
//
//                        Rule recRule = SWBContext.getWebSite(tmreq).getRule(idreq);
//                        Iterator iobjs = recRule.listRelatedObjects();
//                        if (iobjs.hasNext()) { // si existen elementos asociados a la regla se muestran
//                            //Rule recRule = SWBContext.getWebSite(tmreq).getRule(idreq);
//                            SWBResourceURL url = paramRequest.getActionUrl();
//                            url.setCallMethod(url.Call_CONTENT);
//                            out.println("<HTML>");
//                            out.println("<META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html\" >");
//                            out.println("<META HTTP-EQUIV=\"CACHE-CONTROL\" CONTENT=\"NO-CACHE\" >");
//                            out.println("<META HTTP-EQUIV=\"PRAGMA\" CONTENT=\"NO-CACHE\" >");
//                            out.println("<HEAD>");
//                            out.println("<TITLE>INFOTEC WebBuilder</TITLE>");
//
//                            out.println("<LINK REL=\"SHORTCUT ICON\" HREF=\"/wbadmin/images/wb.ico\" >");
//                            out.println("<LINK href=\"/work/sites/WBAdmin/templates/3/1/images/wb3.css\" rel=\"stylesheet\" type=\"text/css\" >");
//                            out.println("<body>");
//                            out.println("<table border=0 cellpadding=5 cellspacing=0 width=\"100%\" align=center class=box>");
//                            out.println("<tr>");
//                            out.println("<td colspan=2 class=tabla>");
//                            out.println(paramRequest.getLocaleString("msgAsocElementsList") + " - <b>" + recRule.getTitle() + "</b>");
//                            out.println("</td>");
//                            out.println("</tr>");
//
//                            boolean entroTopic = false;
//                            boolean entroTemplate = false;
//                            boolean entroRecurso = false;
//                            boolean entroRegla = false;
//                            String rowColor = "";
//                            boolean cambiaColor = true;
//                            while (iobjs.hasNext()) { //esta siendo usado por objetos
//
//                                Object obj = (Object) iobjs.next();
//                                if (obj instanceof WebPage) { //el objeto en el que esta siendo usado es un WebPage
//                                    WebPage recOcc = (WebPage) obj;
//                                    if (!entroTopic) {
//                                        out.println("<tr>");
//                                        out.println("<td colspan=2 class=tabla><HR size=2 noshade>");
//                                        out.println(paramRequest.getLocaleString("msgAsocTopics"));
//                                        out.println("</td>");
//                                        out.println("</tr>");
//                                        entroTopic = true;
//                                        cambiaColor = true;
//                                    }
//                                    rowColor = "#EFEDEC";
//                                    if (!cambiaColor) {
//                                        rowColor = "#FFFFFF";
//                                    }
//                                    cambiaColor = !(cambiaColor);
//                                    out.println("<tr bgcolor=\"" + rowColor + "\">");
//                                    out.println("<td colspan=2 class=valores>");
//                                    //WebPage tpAsoc = SWBContext.getWebSite(recOcc.getWebSiteId()).getWebPage(recOcc.getIdtp());
//                                    out.println(recOcc.getTitle(user.getLanguage()));
//                                    out.println("</td>");
//                                    out.println("</tr>");
//
//                                } else if (obj instanceof Template) { //el objeto en el que esta siendo usado es un Template
//                                    Template recTpl = (Template) obj;
//                                    if (!entroTemplate) {
//                                        out.println("<tr>");
//                                        out.println("<td colspan=2 class=tabla><HR size=2 noshade>");
//                                        out.println(paramRequest.getLocaleString("msgAsocTemplates"));
//                                        out.println("</td>");
//                                        out.println("</tr>");
//                                        entroTemplate = true;
//                                        cambiaColor = true;
//                                    }
//                                    rowColor = "#EFEDEC";
//                                    if (!cambiaColor) {
//                                        rowColor = "#FFFFFF";
//                                    }
//                                    cambiaColor = !(cambiaColor);
//                                    out.println("<tr bgcolor=\"" + rowColor + "\">");
//                                    out.println("<td colspan=2 class=valores>");
//                                    out.println(recTpl.getTitle());
//                                    out.println("</td>");
//                                    out.println("</tr>");
//                                } else if (obj instanceof Portlet) { //el objeto en el que esta siendo usado es un Portlet
//                                    Portlet recRes = (Portlet) obj;
//                                    if (!entroRecurso) {
//                                        out.println("<tr>");
//                                        out.println("<td colspan=2 class=tabla><HR size=2 noshade>");
//                                        out.println(paramRequest.getLocaleString("msgAsocResources"));
//                                        out.println("</td>");
//                                        out.println("</tr>");
//                                        entroRecurso = true;
//                                        cambiaColor = true;
//                                    }
//                                    rowColor = "#EFEDEC";
//                                    if (!cambiaColor) {
//                                        rowColor = "#FFFFFF";
//                                    }
//                                    cambiaColor = !(cambiaColor);
//                                    out.println("<tr bgcolor=\"" + rowColor + "\">");
//                                    out.println("<td colspan=2 class=valores>");
//                                    out.println(recRes.getTitle());
//                                    out.println("</td>");
//                                    out.println("</tr>");
//                                } else if (obj instanceof Rule) { //el objeto en el que esta siendo usado es un Portlet
//                                    Rule rRule = (Rule) obj;
//                                    if (!entroRegla) {
//                                        out.println("<tr>");
//                                        out.println("<td colspan=2 class=tabla><HR size=2 noshade>");
//                                        out.println(paramRequest.getLocaleString("msgAsocRules"));
//                                        out.println("</td>");
//                                        out.println("</tr>");
//                                        entroRegla = true;
//                                        cambiaColor = true;
//                                    }
//                                    rowColor = "#EFEDEC";
//                                    if (!cambiaColor) {
//                                        rowColor = "#FFFFFF";
//                                    }
//                                    cambiaColor = !(cambiaColor);
//                                    out.println("<tr bgcolor=\"" + rowColor + "\">");
//                                    out.println("<td colspan=2 class=valores>");
//                                    out.println(rRule.getTitle());
//                                    out.println("</td>");
//                                    out.println("</tr>");
//                                }
//                            }
//                            out.println("<tr>");
//                            out.println("<td colspan=2 align=right><HR size=2 noshade>");
//
//                            url.setParameter("id", idreq);
//                            url.setParameter("tm", tmreq);
//                            url.setParameter("tp", tpreq);
//                            url.setParameter("act", "removeit");
//
//                            out.println("<A href=\"\" class=boton style=\"text-decoration:none;\" onclick=\"javascript:window.close();\">&nbsp;&nbsp;" + paramRequest.getLocaleString("msgLinkCancel") + "&nbsp;&nbsp;</A>&nbsp;<A target=\"status\" href=\"" + url.toString() + "\"class=boton style=\"text-decoration:none;\" name=btn_submit  onclick=\"javascript:if(confirm('" + paramRequest.getLocaleString("msgConfirmShureRemoveRule") + "?')) { window.close(); return (true);} else { window.close(); return (false);} \" >&nbsp;&nbsp;" + paramRequest.getLocaleString("msgBTNEliminateRule") + "&nbsp;&nbsp;</A>");
//
//                            out.println("</td>");
//                            out.println("</tr>");
//                            out.println("<td colspan=2 class=datos>");
//                            out.println("* " + paramRequest.getLocaleString("msgNote") + ": " + paramRequest.getLocaleString("msgNoteMsg"));
//                            out.println("</td>");
//                            out.println("</tr>");
//                            out.println("</table></body></html>");
//                        } else {
//
//                            SWBResourceURL url = paramRequest.getActionUrl();
//                            url.setParameter("act", "removeit");
//                            if (request.getParameter("id") != null) {
//                                url.setParameter("id", idreq);
//                            }
//                            url.setParameter("tm", tmreq);
//                            url.setParameter("tp", tpreq);
//                            out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + url.toString() + "\">");
//
//                        }
//
//
//                    } catch (Exception e) {
//                        log.error("Error while display asociated elements with the rule with id:" + idreq + " in WebSite:" + tmreq);
//                    }
//
//                } else {
//                    SWBResourceURL url = paramRequest.getActionUrl();
//                    url.setParameter("act", rA);
//                    if (request.getParameter("id") != null) {
//                        url.setParameter("id", request.getParameter("id"));
//                    }
//                    url.setParameter("tm", request.getParameter("tm"));
//                    url.setParameter("tp", request.getParameter("tp"));
//                    out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + url.toString() + "\">");
//                }
//            }
//        } else {
//
//            out.println("<p class=\"box\"><table border=\"0\" width=100% cellpadding=5 cellspacing=0>");
//            String accion = request.getParameter("act");
//            if (accion == null) {
//                accion = "";
//            // presentación de reglas existentes
//            //System.out.println("parametro"+request.getParameter("tm"));
//            }
//            Iterator<Rule> enuRules = SWBContext.getWebSite(tmparam).listRules();//getRule(idreq).getRules(tmparam);
//            int numRules = 0;
//            out.println("<tr><td class=\"tabla\">" + paramRequest.getLocaleString("thID") + "</td><td class=\"tabla\">" + paramRequest.getLocaleString("thTitle") + "</td><td class=\"tabla\">" + paramRequest.getLocaleString("thDescription") + "</td><td class=\"tabla\">" + paramRequest.getLocaleString("thCreated") + "</td><td class=\"tabla\"><image src=\"" + SWBPlatform.getContextPath() + "/admin/images/trash_vacio.gif\" border=0 width=\"25\" heigth=\"25\" title=\"" + paramRequest.getLocaleString("titleEliminate") + "\"></a></td></tr>");
//            String rowColor = "";
//            boolean cambiaColor = true;
//            while (enuRules.hasNext()) {
//                Rule rRule = enuRules.next();
//                SWBResourceURL urlEdit = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
//                urlEdit.setParameter("act", "edit");
//                urlEdit.setParameter("id", rRule.getId());
//                if (tmparam != null) {
//                    urlEdit.setParameter("tm", tmparam);
//                }
//                SWBResourceURL urlDel = paramRequest.getActionUrl();
//                urlDel.setParameter("act", "remove");
//                urlDel.setParameter("id", rRule.getId());
//                if (tmparam != null) {
//                    urlDel.setParameter("tm", tmparam);
//                }
//                rowColor = "#EFEDEC";
//                if (!cambiaColor) {
//                    rowColor = "#FFFFFF";
//                }
//                cambiaColor = !(cambiaColor);
//                out.println("<tr bgcolor=\"" + rowColor + "\"><td align=\"center\" class=\"valores\">");
//                out.println("<a href=\"" + urlEdit.toString() + "\" class=\"link\"><image src=\"" + SWBUtils.getApplicationPath() + "/admin/images/rules.gif\" border=\"0\"></a>&nbsp;" + rRule.getId() + "</td>");
//                out.println("<td align=\"left\" class=\"valores\">" + rRule.getTitle() + "</td><td class=\"valores\">" + rRule.getDescription() + "</td><td>" + rRule.getCreated().toString() + "</td>");
//                out.println("<td align=center class=\"valores\"><a href=\"" + urlDel.toString() + "\" class=\"link\"><image src=\"" + SWBUtils.getApplicationPath() + "/admin/images/b_eliminar2.gif\" border=0  title=\"" + paramRequest.getLocaleString("titleEliminate") + "\" onclick=\"javascript:if(confirm('" + paramRequest.getLocaleString("confirmShureDeleteRule") + "?'))return true; else return false;\"></a></td></tr>\n\n");
//                numRules++;
//            }
//            if (numRules == 0) {
//                out.println("\n<tr><td colspan=2 align=center>" + paramRequest.getLocaleString("msgNoRulesDefined") + "</td></tr>");
//            }
//            out.println("<tr><td colspan=2 align=left class=\"valores\">");
//            SWBResourceURL url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
//            url.setParameter("act", "edit");
//            out.println("<a href=\"" + url.toString() + "\" class=\"link\"><image src=\"" + SWBUtils.getApplicationPath() + "/admin/images/ico_agregar.gif\" border=\"0\" title=\"" + paramRequest.getLocaleString("titleAddRule") + "\" ></a>");
//            out.println("</td></tr>");
//            out.println("</table></p>");
//        }
//
//    }
//
//    private String getApplet(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        StringBuffer ret = new StringBuffer();
//        WebPage tp = paramRequest.getTopic();
//        String tmparam = request.getParameter("tm");
//        if (tmparam == null) {
//            tmparam = paramRequest.getTopic().getWebSiteId();
//        }
//        if (request.getParameter("tm") != null && request.getParameter("tp") != null) {
//            tp = SWBContext.getWebSite(request.getParameter("tm")).getWebPage(request.getParameter("tp"));
//            tmparam = request.getParameter("tm");
//        }
//        ret.append("\n<APPLET id=\"rulesApplet\" name=\"rulesApplet\" code=\"applets.rules.RuleApplet.class\" codebase=\"" + SWBPlatform.getContextPath() + "\"  ARCHIVE=\"wbadmin/lib/Modeler.jar, wbadmin/lib/WBCommons.jar, wbadmin/lib/Rules.jar\" width=\"100%\" height=\"400\">");  //ARCHIVE=\"wbadmin/lib/GenericTree.jar, wbadmin/lib/WBCommons.jar\"
//        SWBResourceURL urlapp = paramRequest.getRenderUrl();
//        urlapp.setMode("gateway");
//        urlapp.setCallMethod(urlapp.Call_DIRECT);
//        ret.append("\n<PARAM NAME =\"cgipath\" VALUE=\"" + urlapp + "\">");
//        ret.append("\n<PARAM NAME =\"tm\" VALUE=\"" + tmparam + "\">");
//        if (null != request.getParameter("id")) {
//            ret.append("\n<PARAM NAME =\"id\" VALUE=\"" + request.getParameter("id") + "\">");
//        } else {
//            ret.append("\n<PARAM NAME =\"id\" VALUE=\"0\">");
//        }
//        if (null != request.getParameter("act")) {
//            ret.append("\n<PARAM NAME =\"act\" VALUE=\"" + request.getParameter("act") + "\">");
//        }
//        ret.append("\n<PARAM NAME =\"locale\" VALUE=\"" + paramRequest.getUser().getLanguage() + "\">");
//        //out.println("<PARAM NAME =\"jsess\" VALUE=\""+request.getSession().getId()+"\">");
//        ret.append("\n</APPLET>");
//        return ret.toString();
//
//    }
//
//    /** Add, update or removes rules
//     * @param request input parameters
//     * @param response an answer to the request
//     * @throws AFException an AF Exception
//     * @throws IOException an IO Exception
//     */
//    @Override
//    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
//
//        String accion = request.getParameter("act");
//        String id = request.getParameter("id");
//        User user = response.getUser();
//        WebPage WebPage = response.getTopic();
//        String tmparam = request.getParameter("tm");
//        try {
//            if (id != null) {
//                if (accion.equals("removeit")) {
//                    try {
//                        SWBContext.getWebSite(tmparam).removeRule(id);
//                        response.setCallMethod(response.Call_CONTENT);
//                        response.setMode(response.Mode_VIEW);
//
//                        response.setRenderParameter("act", "removemsg");
//                        response.setRenderParameter("status", "true");
//                    } catch (Exception e) {
//                        response.setRenderParameter("act", "notremovemsg");
//                        response.setRenderParameter("status", "true");
//
//                    }
//                    response.setMode(response.Mode_VIEW);
//                }
//                if (accion.equals("update")) {
//                    String tmsid = "global";
//                    if (request.getParameter("tmsid") != null) {
//                        tmsid = request.getParameter("tmsid");
//                    }
//                    WebSite ptm = SWBContext.getWebSite(tmsid);
//                    //tmnid=ptm.getDbdata().getNId();
//                    Rule rRule = ptm.getRule(id);
//                    //RuleSrv rsr = new RuleSrv();
//                    //rsr.updateRule(tmsid, Integer.parseInt(id), request.getParameter("title"), request.getParameter("description"), rRule.getXml(),  user.getId());
//                    rRule.setTitle(request.getParameter("title"));
//                    rRule.setDescription(request.getParameter("description"));
//                    rRule.setModifiedBy(user);
//
//                    response.setRenderParameter("act", "edit");
//                    response.setRenderParameter("id", id);
//                    response.setRenderParameter("tree", "reload");
//                    response.setRenderParameter("tmsid", tmsid);
//                    response.setRenderParameter("lastTM", request.getParameter("lastTM"));
//                    response.setRenderParameter("tm", request.getParameter("tmsid"));
//                    if (request.getParameter("tp") != null) {
//                        response.setRenderParameter("tp", request.getParameter("tp"));
//                    }
//                    if (request.getParameter("title") != null) {
//                        response.setRenderParameter("title", request.getParameter("title"));
//                    }
//                    response.setMode(response.Mode_EDIT);
//                }
//
//                if (accion.trim().equalsIgnoreCase("editadd")) {
//                    try {
//                        Document newRuleDoc = SWBUtils.XML.xmlToDom("<?xml version=\"1.0\" encoding=\"UTF-8\"?><rule/>");
//                        String tmsid = "global";
//                        if (request.getParameter("tmsid") != null) {
//                            tmsid = request.getParameter("tmsid");
//                        //int tmnid = 0;
//                        }
//                        if (!tmsid.equals("global")) {
//                            WebSite ptm = SWBContext.getWebSite(tmsid);
//                        //tmnid=ptm.getDbdata().getNId();
//                        }
//                        WebSite ptm = SWBContext.getWebSite(tmsid);
//                        //RuleSrv rsr = new RuleSrv();
//                        Rule newRule = ptm.createRule(); //rsr.createRule( tmsid, request.getParameter("title"), request.getParameter("description"), AFUtils.getInstance().DomtoXml(newRuleDoc), user.getId());
//                        newRule.setTitle(request.getParameter("title"));
//                        newRule.setDescription(request.getParameter("description"));
//                        newRule.setXml(SWBUtils.XML.domToXml(newRuleDoc));
//                        newRule.setCreator(user);
//
//
//                        String idnew = newRule.getId();
//                        response.setRenderParameter("act", "details");
//                        response.setRenderParameter("id", idnew);
//                        response.setRenderParameter("tree", "reload");
//                        response.setRenderParameter("tmsid", tmsid);
//                        response.setRenderParameter("tm", tmparam);
//                        if (request.getParameter("tm") != null) {
//                            response.setRenderParameter("tm", request.getParameter("tm"));
//                        }
//                        if (request.getParameter("tp") != null) {
//                            response.setRenderParameter("tp", request.getParameter("tp"));
//                        }
//                        if (request.getParameter("title") != null) {
//                            response.setRenderParameter("title", request.getParameter("title"));
//                        }
//                        response.setMode(response.Mode_EDIT);
//                    } catch (Exception ei) {
//                        log.error(response.getLocaleString("msgErrorAddNewRule") + ". WBARules.processAction", ei);
//                    }
//                }
//            }
//        } catch (Exception ee) {
//            log.error(ee);
//        }
//    }
//
//    /**
//     *
//     * @param request
//     * @param response
//     * @param paramRequest
//     * @throws AFException
//     * @throws IOException
//     */
//    @Override
//    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        if (paramRequest.getMode().equals("gateway")) {
//            doGateway(request, response, paramRequest);
//        } else {
//            super.processRequest(request, response, paramRequest);
//        }
//    }
//
//    /** Edit view, Rule information edition
//     * @param request input parameters
//     * @param response an answer to the request
//     * @param paramRequest a list of objects (WebPage, user, action, ...)
//     * @throws AFException an AF Exception
//     * @throws IOException an IO Exception
//     */
//    @Override
//    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//
//        String tmsid = request.getParameter("tm");
//        String rrid = request.getParameter("id");
//
//        if (tmsid == null) {
//            tmsid = paramRequest.getTopic().getWebSiteId();
//        }
//        comboAtt = null;
//        vecOrderAtt = null;
//        loadComboAttr(tmsid, rrid, paramRequest);
//        //WebPage WebPage = paramRequest.getTopic();
//        StringBuffer ret = new StringBuffer("");
//        WebPage tp = paramRequest.getTopic();
//        if (request.getParameter("tm") != null && request.getParameter("tp") != null) {
//            tp = SWBContext.getWebSite(request.getParameter("tm")).getWebPage(request.getParameter("tp"));
//        }
//        //WebSite tm = tp.getWebSite();
//        String tree = request.getParameter("tree");
//        if (tree != null && tree.equals("reload")) {
//            if (request.getParameter("lastTM") != null) {
//                ret.append("\n<script>wbTree_executeAction('gotopath." + request.getParameter("lastTM") + ".rules');wbTree_reload();</script>");
//            }
//            ret.append("\n<script>wbTree_executeAction('gotopath." + request.getParameter("tmsid") + ".rules');wbTree_reload();wbTree_executeAction('gotopath." + request.getParameter("tmsid") + ".rules." + request.getParameter("id") + "');</script>");
//        }
////        ret.append("\n<script language=\"JavaScript1.2\">");
////        ret.append("\n");
////        ret.append("\nvar error=0;");
////        ret.append("\n");
////        ret.append("\nfunction revisaOpcion(valor,forma){");
////        ret.append("\n var myindex = forma.sentence.selectedIndex;");
////        ret.append("\n  error=0;");
////        ret.append("\n  if(parseInt(myindex)==-1){");
////        ret.append("\n      alert('"+paramRequest.getLocaleString("alertSelectOptionFromList")+"');");
////        ret.append("\n");
////        ret.append("\n  }");
////        ret.append("\n  else{");
////        ret.append("\n          if(valor=='edit') { ");
////        ret.append("\n              forma.act.value='editcondition'; ");
////        ret.append("\n              forma.action =\""+paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW)+"\";");
////        ret.append("\n          }");
////        ret.append("\n          else{");
////        ret.append("\n             if(valor=='insert') { ");
////        ret.append("\n                 forma.act.value='insertcondition'; ");
////        ret.append("\n                 forma.action =\""+paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW)+"\";");
////        ret.append("\n             }");
////        ret.append("\n             else forma.act.value='deletecondition';");
////        ret.append("\n          }");
////        ret.append("\n     forma.submit();");
////        ret.append("\n  }");
////        ret.append("\n}");
////        ret.append("\n");
////        ret.append("\nfunction revisarStr(indice,forma)");
////        ret.append("\n{");
////        ret.append("\n var valor = forma.sentence.options[indice].value;");
////        ret.append("\n ");
////        ret.append("\n fin = valor.length;");
////        ret.append("\n if(valor.indexOf('&')>0) fin=valor.indexOf('&');");
////        ret.append("\n condicion = valor.substring(valor.lastIndexOf('|')+1,fin);");
////        //ret.append("\n //alert(valor);");
////        ret.append("\n if(condicion=='not')");
////        ret.append("\n {");
////        ret.append("\n  var avalues = valor.split(\"|\",3);");
////        ret.append("\n  var valorBuscar = avalues[0]+\"|\"+(parseInt(avalues[1])+1)+\"|\"+(parseInt(avalues[2])+1);");
////        ret.append("\n  bandera=0;");
////        ret.append("\n  for(var i = 0; i<forma.sentence.length; i++){");
////        //ret.append("\n   // buscando si existe elemento dentro del not");
////        ret.append("\n   var tempo = forma.sentence.options[i].value;");
////        ret.append("\n   var atempo = tempo.split(\"|\",3);");
////        ret.append("\n   var valorEncontrado = atempo[0]+\"|\"+atempo[1]+\"|\"+atempo[2];");
////        ret.append("\n   if(valorBuscar==valorEncontrado)");
////        ret.append("\n   {");
////        ret.append("\n    alert(\""+paramRequest.getLocaleString("alertAnOptionInNot")+"\");");
////        ret.append("\n    i=forma.sentence.length;");
////        ret.append("\n    bandera=1;");
////        ret.append("\n   }");
////        ret.append("\n  }");
////        ret.append("\n  if(bandera==0)");
////        ret.append("\n  { ");
////        ret.append("\n   alert('"+paramRequest.getLocaleString("alertDefineSentenceInNot")+"');");
////        ret.append("\n   error = 1;");
////        ret.append("\n  }");
////        ret.append("\n }");
////        ret.append("\n else");
////        ret.append("\n {");
////        ret.append("\n  if(condicion==\"and\"|condicion==\"or\")");
////        ret.append("\n  {");
////        ret.append("\n   var avalues = valor.split(\"|\",3);");
////        ret.append("\n   var valorBuscar1 = avalues[0]+\"|\"+(parseInt(avalues[1])+1)+\"|\"+(parseInt(avalues[2])+1);");
////        ret.append("\n   var valorBuscar2 = avalues[0]+\"|\"+(parseInt(avalues[1])+2)+\"|\"+(parseInt(avalues[2])+2);");
////        ret.append("\n   bandera=0;");
////        ret.append("\n   findNum=0;");
////        ret.append("\n   for(var i = 0; i<forma.sentence.length; i++){");
////        //ret.append("\n    // buscando si existe elemento dentro del not");
////        ret.append("\n    var tempo = forma.sentence.options[i].value;");
////        ret.append("\n    var atempo = tempo.split(\"|\",3);");
////        ret.append("\n    var valorEncontrado = atempo[0]+\"|\"+atempo[1]+\"|\"+atempo[2];");
////        ret.append("\n    if(valorBuscar1==valorEncontrado|valorBuscar2==valorEncontrado)");
////        ret.append("\n    { ");
////        ret.append("\n     findNum++;");
////        ret.append("\n     alert(\""+paramRequest.getLocaleString("alertFoundOptionIN")+" \"+condicion+\" "+paramRequest.getLocaleString("alertSentence")+" No.\"+findNum);");
////        ret.append("\n    }");
////        ret.append("\n   }");
////        ret.append("\n   if(findNum<2)");
////        ret.append("\n   { ");
////        ret.append("\n    alert('"+paramRequest.getLocaleString("alertMostDefinedSentenceINAND_OR")+"');");
////        ret.append("\n    error = 1;");
////        ret.append("\n   }");
////        ret.append("\n ");
////        ret.append("\n ");
////        ret.append("\n  }");
////        ret.append("\n }");
////        ret.append("\n ");
////        ret.append("\n if(error==1) indice = forma.sentence.length;");
////        ret.append("\n indice++;");
////        ret.append("\n if(indice<forma.sentence.length) return revisarStr(indice,forma);");
////        ret.append("\n  ");
////        ret.append("\n}");
////        ret.append("\n");
////        ret.append("\n function revisaCon(forma){    ");
////        ret.append("\n  if(forma.sentence.length>0)    ");
////        ret.append("\n  {      ");
////        ret.append("\n    var myindex = forma.sentence.selectedIndex;    ");
////        ret.append("\n    if(parseInt(myindex)>=0)    ");
////        ret.append("\n    {    ");
////        ret.append("\n       var valor = forma.sentence.options[myindex].value;    ");
////        ret.append("\n       fin = valor.length;    ");
////        ret.append("\n       if(valor.indexOf('&')>0) fin=valor.indexOf('&');    ");
////        ret.append("\n       condicion = valor.substring(valor.lastIndexOf('|')+1,fin);    ");
////        ret.append("\n       if(condicion=='and'|condicion=='or'|condicion=='not')    ");
////        ret.append("\n       {    ");
////        ret.append("\n          var existe = 0;     ");
////        ret.append("\n          if(condicion=='not')    ");
////        ret.append("\n          {    ");
////        ret.append("\n             var avalues = valor.split(\"|\",3);    ");
////        ret.append("\n             var valorBuscar = parseInt(avalues[1]);    ");
////        ret.append("\n         ");
////        ret.append("\n             for(var i = 0; i<forma.sentence.length; i++)    ");
////        ret.append("\n             {    ");
////        ret.append("\n                var tempo = forma.sentence.options[i].value;    ");
////        ret.append("\n                var atempo = tempo.split(\"|\",3);    ");
////        ret.append("\n                var valorEncontrado = parseInt(atempo[2]);    ");
////        ret.append("\n                if(valorBuscar==valorEncontrado)    ");
////        ret.append("\n                     {    ");
////        ret.append("\n                         alert(\""+paramRequest.getLocaleString("alertNotOnlyOneConditionAndNotSentence")+"\");    ");
////        ret.append("\n                         i=forma.sentence.length;    ");
////        ret.append("\n                         existe=1;    ");
////        ret.append("\n                     }    ");
////        ret.append("\n             }      ");
////        ret.append("\n          }    ");
////        ret.append("\n          if(existe==0)    ");
////        ret.append("\n          {    ");
////        ret.append("\n              forma.action=\""+paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT)+"\";");
////        ret.append("\n              forma.act.value=\"agregar\";    ");
////        ret.append("\n              forma.submit();    ");
////        ret.append("\n          }    ");
////        ret.append("\n       }    ");
////        ret.append("\n       else    ");
////        ret.append("\n         {    ");
////        ret.append("\n          alert('"+paramRequest.getLocaleString("alertNotAddSentenceToASentence")+" '+ condicion);    ");
////        ret.append("\n         }      ");
////        ret.append("\n      }    ");
////        ret.append("\n      else    ");
////        ret.append("\n      {    ");
////        ret.append("\n           alert('"+paramRequest.getLocaleString("alertAddSentenceMostSelectAndOrNot")+"');    ");
////        ret.append("\n      }    ");
////        ret.append("\n   }    ");
////        ret.append("\n   else    ");
////        ret.append("\n   {     ");
////        ret.append("\n     forma.action=\""+paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT)+"\";");
////        ret.append("\n     forma.act.value='agregar';    ");
////        ret.append("\n     forma.submit();     ");
////        ret.append("\n   }    ");
////        ret.append("\n }    ");
////        ret.append("\n ");
////        ret.append("\n</script>");
//        String accion = request.getParameter("act");
//        if (accion == null) {
//            accion = "edit";
//        }
//        String id = "0";
//        SWBResourceURL url = paramRequest.getActionUrl();
//        if (tmsid != null) {
//            url.setParameter("tm", tmsid);
//        }
//        if (request.getParameter("id") != null) {
//            id = request.getParameter("id");
//        }
//        try {
//            if (!id.equals("0")) {
//                Rule rRule = SWBContext.getWebSite(tmsid).getRule(id);
//                SWBResourceURL urlEdit = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
//                urlEdit.setParameter("act", "edit");
//                urlEdit.setParameter("id", id);
//                SWBResourceURL urlDetail = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
//                urlDetail.setParameter("act", "details");
//                urlDetail.setParameter("id", id);
//                SWBResourceURL urlHistory = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
//                urlHistory.setParameter("act", "history");
//                urlHistory.setParameter("id", id);
//                ret.append("\n<p align=center class=\"box\">");
//                ret.append("\n<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
//                ret.append("\n<TR>");
//                ret.append("\n<TD width=\"150\" align=\"right\" class=\"datos\"> " + paramRequest.getLocaleString("msgIdentifier") + "</TD>");
//                ret.append("\n<TD class=\"valores\">");
//                ret.append(id);
//                ret.append("\n</TD>");
//                ret.append("\n</TR>");
//                ret.append("\n</TABLE></P>");
//                if (request.getParameter("act") == null) {
//                    ret.append("\n<p align=center class=\"box\">");
//                    ret.append("\n<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
//                    ret.append("\n<tr><TD width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgTitle") + "</td><td  class=\"valores\">" + rRule.getTitle() + "</td></tr>");
//                    ret.append("\n<tr><TD width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgDescription") + "</td><td  class=\"valores\">" + rRule.getDescription() + "</td></tr>");
//                    //TODO: dateFormat
//                    ret.append("\n<tr><TD width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgCreationDate") + "</td><td   class=\"valores\">" + rRule.getCreated().toString() + "</td></tr>");
//                    ret.append("\n<tr><td colspan=2 class=\"valores\" align=center><input type=hidden name=act value=\"update\">");
//                    ret.append("\n</td></tr></table></p>");
//                    Document docxml = SWBUtils.XML.xmlToDom(rRule.getXml());
//                    sbTree = new StringBuffer();
//                    if (docxml != null) {
//                        Element rule = (Element) docxml.getFirstChild();
//                        elemNum = 0;
//                        rRule = null;
//                        ret.append("\n<p class=\"box\">");
//                        ret.append("\n<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
//                        ret.append("\n<tr><td class=\"datos\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + paramRequest.getLocaleString("msgCriteriaDefinition") + "</td></tr>");
//                        ret.append("\n<tr><td class=\"datos\">");
//                        ret.append(getApplet(request, response, paramRequest));
//                        ret.append("\n</td></tr>");
//                        ret.append("\n</table></p>");
//                    }
//                    sbTree = null;
//                    docxml = null;
//                } else if (accion.equals("edit") | accion.equals("details")) {
//                    ret.append("\n<form name=\"addruleform\" action=\"" + url.toString() + "\" method=\"post\" class=\"box\">");
//                    ret.append("\n<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
//                    ret.append("\n<tr><TD width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgTitle") + "</td><td ><input type=text size=67 name=\"title\" class=\"campos\" value=\"" + rRule.getTitle() + "\"></td></tr>");
//                    ret.append("\n<tr><TD width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgDescription") + "</td><td ><textarea name=\"description\" class=\"campos\" cols=50 rows=6>" + rRule.getDescription() + "</textarea></td></tr>");
//                    //TODO: dateformat
//                    ret.append("\n<tr><TD width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgCreationDate") + "</td><td  class=\"valores\">" + rRule.getCreated().toString() + "</td></tr>");
//                    ret.append("\n<tr><TD width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgSite") + "</td><td  class=\"valores\">");
//                    Iterator<WebSite> ittm = SWBContext.listWebSites();
//                    String strSelect = "";
//                    String lastTM = "";
//                    tmsid = "";
//                    tmsid = rRule.getWebSite().getId();
//
//                    ret.append(SWBContext.getWebSite(tmsid).getTitle());
//                    while (ittm.hasNext()) {
//                        WebSite thistm = (WebSite) ittm.next();
//                        if (tmsid.equals(thistm.getId())) {
//                            lastTM = thistm.getId();
//                        }
//                    }
//                    ret.append("\n<input type=hidden name=tmsid value=\"" + tmsid + "\"><input type=hidden name=lastTM value=\"" + lastTM + "\"></td></tr>");
//                    ret.append("\n<tr><td colspan=2 class=\"valores\" align=right><input type=hidden name=act value=\"update\"><input type=hidden name=id value=\"" + rRule.getId() + "\">");
//                    ret.append("\n<HR size=\"1\" noshade><input type=button class=\"boton\" value=\"" + paramRequest.getLocaleString("btnUpdate") + "\" onclick=\"addruleform.submit();\" title=\"" + paramRequest.getLocaleString("titleUpdateTitleDescription") + "\"></td></tr></table></form>");
//                    Document docxml = SWBUtils.XML.xmlToDom(rRule.getXml());
//                    sbTree = new StringBuffer();
//                    if (docxml != null) {
//                        Element rule = (Element) docxml.getFirstChild();
//                        elemNum = 0;
//                        rRule = null;
//                        ret.append("\n<p class=\"box\">");
//                        ret.append("\n<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
//                        ret.append("\n<tr><td class=\"datos\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + paramRequest.getLocaleString("msgCriteriaDefinition") + "</td></tr>");
//                        ret.append("\n<tr><td class=\"datos\">");
//                        ret.append(getApplet(request, response, paramRequest));
//                        ret.append("\n</td></tr>");
//                        ret.append("\n</td></tr></table></p>");
//                    }
//                    sbTree = null;
//                    docxml = null;
//                }
//            } else {
//                if (accion.equals("edit") || accion.equals("add")) {
//                    ret.append("\n<form name=\"addruleform\" action=\"" + url.toString() + "\" method=\"post\" class=\"box\">");
//                    ret.append("\n<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
//                    ret.append("\n<tr><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgTitle") + ":</td><td><input type=text size=67 name=\"title\" class=\"campos\" value=\"\"></td></tr>");
//                    ret.append("\n<tr><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgDescription") + ":</td><td><textarea name=\"description\" class=\"campos\" cols=50 rows=6></textarea></td></tr>");
//                    ret.append("\n<tr><td colspan=2 align=right class=\"valores\"><input type=hidden name=act value=\"editadd\"><input type=hidden name=id value=\"0\">");
//                    ret.append("\n<input type=hidden name=tmsid value=\"" + request.getParameter("tm") + "\"><HR size=\"1\" noshade><input type=\"submit\" class=\"boton\" name=\"btn_next\" value=\"" + paramRequest.getLocaleString("btnAdd") + "\" ></td></tr></table></form>");
//                }
//            }
//
//        } catch (Exception e) {
//            log.error(paramRequest.getLocaleString("msgErrorEditRule") + ", WBARules.doEdit", e);
//        }
//        response.getWriter().print(ret.toString());
//    }
//
//    /** load in a HashMap all user attributes and default attribute to creates rules
//     * @param tmid identificador de mapa de tópicos
//     * @param ruleid identificador de la regla
//     * @param paramRequest lista de objetos (WebPage, user, action, ...)
//     */
//    private void loadComboAttr(String tmid, String ruleid, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException {
//        comboAtt = new HashMap();
//        vecOrderAtt = new Vector(1, 1);
//        // Agreando valores iniciales al HashMap como son isloged, isregistered, language, device
//        HashMap hmAttr = new HashMap();
//        HashMap hmOper = new HashMap();
//        HashMap hmValues = new HashMap();
//        hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgUserRegistered"));  ///////////////////////////
//        hmAttr.put("Tipo", "select");
//        hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
//        hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
//        hmAttr.put("Operador", hmOper);
//        hmValues.put("true", paramRequest.getLocaleString("msgYes"));
//        hmValues.put("false", paramRequest.getLocaleString("msgNo"));
//        hmAttr.put("Valor", hmValues);
//        comboAtt.put("isregistered", hmAttr);
//
//        hmAttr = new HashMap();
//        hmOper = new HashMap();
//        hmValues = new HashMap();
//        hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgUserSigned"));   ///////////////////////////
//        hmAttr.put("Tipo", "select");
//        hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
//        hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
//        hmAttr.put("Operador", hmOper);
//        hmValues.put("true", paramRequest.getLocaleString("msgYes"));
//        hmValues.put("false", paramRequest.getLocaleString("msgNo"));
//        hmAttr.put("Valor", hmValues);
//        comboAtt.put("isloged", hmAttr);
//
//        {
//            hmAttr = new HashMap();
//            hmOper = new HashMap();
//            hmValues = new HashMap();
//            hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgDevice"));   ///////////////////////////
//            hmAttr.put("Tipo", "select");
//            Iterator<Device> eleDev = SWBContext.getGlobalWebSite().listDevices();
//            while (eleDev.hasNext()) {
//                Device rDev = eleDev.next();
//                hmValues.put(rDev.getId(), rDev.getTitle());
//            }
//            hmAttr.put("Valor", hmValues);
//            hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
//            hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
//            hmAttr.put("Operador", hmOper);
//            comboAtt.put("device", hmAttr);
//        }
//        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        int numero = 0;
//        try {
//            String repId = SWBContext.getWebSite(tmid).getUserRepository().getId();
//            UserRepository urep = SWBContext.getWebSite(tmid).getUserRepository();
//            //TODO: UserRepository AttrsList()
//            //int numatt = DBUser.getInstance(repId).getUserAttrsList().size();
//            int numatt = 0;
//
//            hmAttr = new HashMap();
//            hmOper = new HashMap();
//            hmValues = new HashMap();
//            hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgUserType"));   ///////////////////////////
//            hmAttr.put("Tipo", "select");
//            hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
//            hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
//            hmAttr.put("Operador", hmOper);
//            HashMap hmUsrTypeList = DBUser.getInstance(repId).getUserAttrsList();
//            Iterator iteruserType = hmUsrTypeList.values().iterator();
//            while (iteruserType.hasNext()) {
//                WBUserAttribute usrTypeAtt = (WBUserAttribute) iteruserType.next();
//                if (usrTypeAtt.getType() == WBUserAttribute.USER_TYPE) {
//                    hmValues.put(usrTypeAtt.getName(), usrTypeAtt.getValueLocalized(usrTypeAtt.getName(), paramRequest.getUser().getLanguage()));
//                }
//            }
//
//            hmAttr.put("Valor", hmValues);
//            if (!tmid.equals(SWBContext.getGlobalWebSite().getId())) {
//                comboAtt.put("USERTYPE", hmAttr);
//            }
//            vecOrderAtt.add(numero++, "isregistered");
//            vecOrderAtt.add(numero++, "isloged");
//
//            vecOrderAtt.add(numero++, "device");
//            if (!tmid.equals(SWBContext.getGlobalWebSite().getId())) {
//                vecOrderAtt.add(numero++, "USERTYPE");
//            }
//            if (numatt > 0) {
//                HashMap hmAttUsrList = DBUser.getInstance(repId).getUserAttrsList();
//                Iterator iteratt = hmAttUsrList.values().iterator();
//                while (iteratt.hasNext()) {
//                    WBUserAttribute usrAtt = (WBUserAttribute) iteratt.next();
//                    hmAttr = new HashMap();
//                    hmOper = new HashMap();
//                    if (usrAtt.getType() != WBUserAttribute.USER_TYPE) {
//                        String tipoControl = "select";
//                        if (usrAtt.getKind() == WBUserAttribute.INTEGER_ATTRIBUTE || usrAtt.getKind() == WBUserAttribute.TEXT_ATTRIBUTE) {
//                            tipoControl = "TEXT";
//                        }
//                        if (!comboAtt.containsKey(usrAtt.getName()) && !usrAtt.getName().equals("PASSWORD")) {
//                            //buscando valores si el control es de tipo select
//                            if (usrAtt.getKind() == WBUserAttribute.LIST_ATTRIBUTE) {
//                                hmValues = new HashMap();
//                                if (usrAtt.getName().equals("LANGUAGE")) {
//                                    // obtener los lenguajes de la base de datos
//
////                                    Hashtable htlang = DBCatalogs.getInstance().getLanguages(tmid);
////                                    Enumeration enulang = htlang.elements();
//                                    Iterator<Language> enulang = SWBContext.getWebSite(tmid).listLanguages();
//
//                                    while (enulang.hasNext()) {
//                                        Language rLang = enulang.next();
//                                        String valor = rLang.getId();
//                                        //TODO: getTopicLang
//                                        Language tpLang = SWBContext.getWebSite(tmid).getLanguage(rLang.getId());
//                                        hmValues.put(valor, rLang.getTitle(paramRequest.getUser().getLanguage()));
//                                    }
//
//                                } else {
//                                    Iterator itt = usrAtt.getList().iterator();
//                                    while (itt.hasNext()) {
//                                        String valor = (String) itt.next();
//                                        hmValues.put(valor, usrAtt.getValueLocalized(valor, paramRequest.getUser().getLanguage()));
//                                    }
//                                }
//
//                                hmAttr.put("Valor", hmValues);
//                            } else if (usrAtt.getKind() == WBUserAttribute.USER_TYPE_ATTRIBUTE) {
//                                Iterator itt = usrAtt.getAttrs().iterator();
//                                hmValues = new HashMap();
//                                int numAttr = 0;
//                                while (itt.hasNext()) {
//                                    WBUserAttribute usrAttValues = (WBUserAttribute) itt.next();
//                                    hmValues.put(usrAttValues.getName(), usrAttValues.getValueLocalized(usrAttValues.getName(), paramRequest.getUser().getLanguage()));
//                                    numAttr++;
//                                }
//                                if (numAttr > 0) {
//                                    hmAttr.put("Valor", hmValues);
//                                } else {
//                                    tipoControl = "TEXT";
//                                }
//                            }
//                            //  SI NO EXISTE EL LOCALE PONE EL NOMBRE DEL ATRIBUTO
//                            String msgDisplay = usrAtt.getCaptionLocalized(paramRequest.getUser().getLanguage());
//                            if (msgDisplay != null && msgDisplay.trim().length() == 0) {
//                                msgDisplay = usrAtt.getName();
//                            }
//                            hmAttr.put("Etiqueta", msgDisplay); ///////////////////////////
//                            hmAttr.put("Tipo", tipoControl);
//                            String Attr = null;
//                            Attr = usrAtt.getDescription();
//                            if (!Attr.equals(null)) {
//                                hmAttr.put("MENSAJE", Attr);
//                            }
//                            if (usrAtt.getKind() == WBUserAttribute.INTEGER_ATTRIBUTE) {
//                                hmOper.put("&gt;", paramRequest.getLocaleString("msgGreaterThan"));
//                                hmOper.put("&lt;", paramRequest.getLocaleString("msgLessThan"));
//                            }
//                            hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
//                            hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
//                            hmAttr.put("Operador", hmOper);
//                            if (usrAtt.getType() == WBUserAttribute.BASIC_TYPE && tmid.equals(TopicMgr.getInstance().getGlobalTopicMap().getId())) {
//                                comboAtt.put(usrAtt.getName(), hmAttr);
//                                vecOrderAtt.add(numero++, usrAtt.getName());
//
//                            } else {
//                                if (!tmid.equals(SWBContext.getGlobalWebSite().getId())) {
//                                    comboAtt.put(usrAtt.getTitle(), hmAttr);
//                                    vecOrderAtt.add(numero++, usrAtt.getTitle());
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error(paramRequest.getLocaleString("msgErrorLoadingUserAttributeList") + ". WBARules.loadComboAttr", e);
//        }
//
//        // Se agrega la parte de roles
//
//        hmAttr = new HashMap();
//        hmOper = new HashMap();
//        hmValues = new HashMap();
//        hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgRoles"));   ///////////////////////////
//        hmAttr.put("Tipo", "select");
//        hmOper.put("=", paramRequest.getLocaleString("msgHave"));
//        hmOper.put("!=", paramRequest.getLocaleString("msgNotHave"));
//        hmAttr.put("Operador", hmOper);
//        //Enumeration enumRoles = DBRole.getInstance().getRoles(TopicMgr.getInstance().getTopicMap(tmid).getDbdata().getRepository());
//        Iterator<Role> enumRoles = SWBContext.getWebSite(tmid).getUserRepository().listRoles();
//        while (enumRoles.hasNext()) {
//            Role role = enumRoles.next();
//            hmValues.put(role.getId(), role.getTitle());
//        }
//        
//        hmAttr.put("Valor", hmValues);
//        //TODO: RuleMgr ???
//        comboAtt.put(RuleMgr.TAG_INT_ROLE, hmAttr);
//        vecOrderAtt.add(numero++, RuleMgr.TAG_INT_ROLE);
//
//        // Se agrega la parte de reglas
//        if (ruleid == null) {
//            ruleid = "";
//        }
//        int numreglas = 0;
//        Iterator<Rule> numrules = SWBContext.getWebSite(tmid).listRules();
//        while (numrules.hasNext()) {
//            Rule rule = numrules.next();
//            if (!ruleid.equals(rule.getId())) {
//                numreglas++;
//            }
//        }
//        if (numreglas > 0) {
//            hmAttr = new HashMap();
//            hmOper = new HashMap();
//            hmValues = new HashMap();
//            hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgRules"));   ///////////////////////////
//            hmAttr.put("Tipo", "select");
//            hmOper.put("=", paramRequest.getLocaleString("msgCumpla"));
//            hmOper.put("!=", paramRequest.getLocaleString("msgNoCumpla"));
//            hmAttr.put("Operador", hmOper);
//            Iterator<Rule> enumRules = SWBContext.getWebSite(tmid).listRules();
//            while (enumRules.hasNext()) {
//                Rule rule = enumRules.next();
//                if (!ruleid.equals(rule.getId())) {
//                    hmValues.put(rule.getId(), rule.getTitle());
//                }
//            }
//            hmAttr.put("Valor", hmValues);
//            comboAtt.put(RuleMgr.TAG_INT_RULE, hmAttr);
//            vecOrderAtt.add(numero++, RuleMgr.TAG_INT_RULE);
//        }
//    }
//
//    /** Gets the document with the user attributes
//     * @return a document with the user attributes
//     */
//    private Document getXMLComboAttr() throws SWBResourceException, java.io.IOException {
//        Document dom = SWBUtils.XML.getNewDocument();
//        Element attributes = dom.createElement("attributes");
//        dom.appendChild(attributes);
//
//        for (int i = 0; i < vecOrderAtt.size(); i++) {
//            String valor = (String) vecOrderAtt.get(i);
//            HashMap hmAttr = (HashMap) comboAtt.get(valor);
//            String label = (String) hmAttr.get("Etiqueta");
//            String seleccionado = "";
//            // armando combo de operadores
//
//            Element attribute = dom.createElement("attribute");
//            attribute.setAttribute("type", (String) hmAttr.get("Tipo"));
//            attribute.setAttribute("name", valor);
//            attribute.setAttribute("title", label);
//
//            HashMap hmOper = (HashMap) hmAttr.get("Operador");
//            //Set keysOper = hmOper.keySet().iterator();
//            Iterator itOper = hmOper.keySet().iterator();
//
//            Element operators = dom.createElement("operators");
//
//            while (itOper.hasNext()) {
//                String thisValue = (String) itOper.next();
//                String thisLabel = (String) hmOper.get(thisValue);
//                Element operator = dom.createElement("operator");
//                operator.setAttribute("value", thisValue);
//                operator.setAttribute("title", thisLabel);
//                operators.appendChild(operator);
//            }
//
//            attribute.appendChild(operators);
//
//            hmOper = null;
//
//            Element attValues = dom.createElement("catalog");
//            attValues.setAttribute("name", "attValues");
//
//            // armando combo para armar valores posibles del elemento
//            if (!hmAttr.get("Tipo").equals("TEXT")) {
//                HashMap valoresCombo = (HashMap) hmAttr.get("Valor");
//                //Set keyValSet = valoresCombo.keySet();
//                Iterator itValCombo = valoresCombo.keySet().iterator();
//                while (itValCombo.hasNext()) {
//                    String nomValCombo = (String) itValCombo.next();
//                    String labelValCombo = (String) valoresCombo.get(nomValCombo);
//                    Element attValue = dom.createElement("option");
//                    attValue.setAttribute("title", labelValCombo);
//                    attValue.setAttribute("value", nomValCombo);
//                    attValues.appendChild(attValue);
//                }
//                attribute.appendChild(attValues);
//            } else {
//                //armar text para pedir/mostrar valor
//                Element attValue = dom.createElement("option");
//                attValue.setAttribute("title", "");
//                attValue.setAttribute("value", "TEXT");
//                attValues.appendChild(attValue);
//                attribute.appendChild(attValues);
//            }
//            attributes.appendChild(attribute);
//        }
//        return dom;
//    }
//
//    private Document getError(int id) {
//        Document dom = null;
//        try {
//            dom = SWBUtils.XML.getNewDocument();
//            Element res = dom.createElement("res");
//            dom.appendChild(res);
//            Element err = dom.createElement("err");
//            res.appendChild(err);
//            addElement("id", "" + id, err);
//            if (id == 0) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_loginfail") + "...", err);
//            } else if (id == 1) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_nouser") + "...", err);
//            } else if (id == 2) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noservice") + "...", err);
//            } else if (id == 3) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_serviceprocessfail") + "...", err);
//            } else if (id == 4) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_parametersprocessfail") + "...", err);
//            } else if (id == 5) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopicmap") + "...", err);
//            } else if (id == 6) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopic") + "...", err);
//            } else if (id == 7) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_usernopermiss") + "...", err);
//            } else if (id == 8) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicAlreadyexist") + "...", err);
//            } else if (id == 9) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_byImplement") + "...", err);
//            } else if (id == 10) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicMapAlreadyExist") + "...", err);
//            } else if (id == 11) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_FileNotFound") + "...", err);
//            } else if (id == 12) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noversions") + "...", err);
//            } else if (id == 13) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_xmlinconsistencyversion") + "...", err);
//            } else if (id == 14) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noResourcesinMemory") + "...", err);
//            } else if (id == 15) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noTemplatesinMemory") + "...", err);
//            } else if (id == 16) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_TemplatenotRemovedfromFileSystem") + "...", err);
//            } else if (id == 17) {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_adminUsernotCreated") + "...", err);
//            } else {
//                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_errornotfound") + "...", err);
//            }
//        } catch (Exception e) {
//            log.error(SWBUtils.TEXT.getLocaleString("locale_Gateway", "error_Gateway_getService_documentError") + "...", e);
//        }
//        return dom;
//    }
//
//    /**
//     *
//     * @param request
//     * @param response
//     * @param paramRequest
//     * @throws AFException
//     * @throws IOException
//     */
//    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        PrintWriter out = response.getWriter();
//        ServletInputStream in = request.getInputStream();
//        Document dom = SWBUtils.XML.xmlToDom(in);
//
//        if (!dom.getFirstChild().getNodeName().equals("req")) {
//            response.sendError(404, request.getRequestURI());
//            return;
//        }
//
//        String cmd = null;
//        if (dom.getElementsByTagName("cmd").getLength() > 0) {
//            cmd = dom.getElementsByTagName("cmd").item(0).getFirstChild().getNodeValue();
//        }
//        if (cmd == null) {
//            response.sendError(404, request.getRequestURI());
//            return;
//        }
//        String ret;
//        Document res = getService(cmd, dom, paramRequest.getUser(), request, response, paramRequest);
//        if (res == null) {
//            ret = SWBUtils.XML.domToXml(getError(3));
//        } else {
//            ret = SWBUtils.XML.domToXml(res, true);
//        }
//        out.print(new String(ret.getBytes()));
//    }
//
//    private Element addElement(String name, String value, Element parent) {
//        Document doc = parent.getOwnerDocument();
//        Element ele = doc.createElement(name);
//        if (value != null) {
//            ele.appendChild(doc.createTextNode(value));
//        }
//        parent.appendChild(ele);
//        return ele;
//    }
//
//    private Document getService(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
//        String tmpcmd = cmd, tm = null, id = null;
//        if (null != cmd && cmd.indexOf('.') != -1) {
//            tmpcmd = cmd.substring(0, cmd.indexOf('.'));
//            tm = cmd.substring(cmd.indexOf('.') + 1, cmd.lastIndexOf('.'));
//            id = cmd.substring(cmd.lastIndexOf('.') + 1);
//        }
//        if (tmpcmd.equals("getXMLAttr")) {
//            try {
//                return getXMLComboAttr();
//            } catch (Exception e) {
//                log.error("Error while trying to get XML user attributes. ", e);
//            }
//        } else if (tmpcmd.equals("getXMLRule")) {
//            Rule rRule = null;
//            rRule = SWBContext.getWebSite(tm).getRule(id);
//            return SWBUtils.XML.xmlToDom(rRule.getXml());
//        } else if (tmpcmd.equals("updateRule")) {
//            Document dom = null;
//            Rule rRule = SWBContext.getWebSite(tm).getRule(id);
//            String strXMLRule = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
//            String strTmp = SWBUtils.XML.domToXml(src);
//            if (strTmp.indexOf("<rule>") != -1) {
//                strXMLRule += strTmp.substring(strTmp.indexOf("<rule>"), strTmp.lastIndexOf("</rule>") + 7);
//            } else {
//                strXMLRule += "<rule/>";
//            }
//            try {
//                rRule.setXml(strXMLRule);
//                rRule.setModifiedBy(user);
//                //rRule.update(paramRequest.getUser().getId(),paramRequest.getLocaleString("msgLogRuleCriteriaModificationID")+": "+id);
//                rRule = null;
//                dom = SWBUtils.XML.xmlToDom(strXMLRule);
//            } catch (Exception e) {
//                log.error("Error while trying to update rule.", e);
//            }
//            return dom;
//        }
//        return getError(2);
//    }
}
