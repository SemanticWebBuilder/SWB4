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
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
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

    /** User view, creates the rules
     * @param request input parameters
     * @param response an answer to the request
     * @param paramRequest a list of objects (WebPage, user, action, ...)
     * @throws SWBResourceException an SWB Resource Exception
     * @throws IOException an IO Exception
     */

    private String getApplet(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuffer ret = new StringBuffer();
        String suri = request.getParameter("suri");
        log.debug("getApplet: " + suri);
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        Rule rRule = (Rule) ont.getGenericObject(suri);
        WebPage tp = paramRequest.getTopic();
        String tmparam = request.getParameter("tm");
        if (tmparam == null) {
            tmparam = paramRequest.getTopic().getWebSiteId();
        }
        if (request.getParameter("tm") != null && request.getParameter("tp") != null) {
            tp = SWBContext.getWebSite(request.getParameter("tm")).getWebPage(request.getParameter("tp"));
            tmparam = request.getParameter("tm");
        }
        ret.append("\n<APPLET id=\"rulesApplet\" name=\"rulesApplet\" code=\"applets.rules.RuleApplet.class\" codebase=\"" + SWBPlatform.getContextPath() + "\"  ARCHIVE=\"swbadmin/lib/Modeler.jar, swbadmin/lib/WBCommons.jar, swbadmin/lib/Rules.jar\" width=\"100%\" height=\"400\">");  //ARCHIVE=\"wbadmin/lib/GenericTree.jar, wbadmin/lib/WBCommons.jar\"
        SWBResourceURL urlapp = paramRequest.getRenderUrl();
        urlapp.setMode("gateway");
        urlapp.setCallMethod(urlapp.Call_DIRECT);
        urlapp.setParameter("id", suri);
        urlapp.setParameter("suri", suri);
        ret.append("\n<PARAM NAME =\"cgipath\" VALUE=\"" + urlapp + "\">");
        ret.append("\n<PARAM NAME =\"tm\" VALUE=\"" + rRule.getWebSite().getId() + "\">");
        if (null != request.getParameter("suri")) {
            ret.append("\n<PARAM NAME =\"id\" VALUE=\"" + rRule.getId() + "\">");
            ret.append("\n<PARAM NAME =\"suri\" VALUE=\"" + rRule.getId() + "\">");
        } else {
            ret.append("\n<PARAM NAME =\"id\" VALUE=\"0\">");
        }
        ret.append("\n<PARAM NAME =\"act\" VALUE=\"edit\">");
        ret.append("\n<PARAM NAME =\"locale\" VALUE=\"" + paramRequest.getUser().getLanguage() + "\">");
        ret.append("\n</APPLET>");
        return ret.toString();
    }

    /** Add, update or removes rules
     * @param request input parameters
     * @param response an answer to the request
     * @throws SWBResourceException an SWB Resource Exception
     * @throws IOException an IO Exception
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {

        String accion = request.getParameter("act");
        String id = request.getParameter("id");
        User user = response.getUser();
        WebPage WebPage = response.getTopic();
        String tmparam = request.getParameter("tm");
        try {
            if (id != null) {
                if (accion.equals("removeit")) {
                    try {
                        SWBContext.getWebSite(tmparam).removeRule(id);
                        response.setCallMethod(response.Call_CONTENT);
                        response.setMode(response.Mode_VIEW);

                        response.setRenderParameter("act", "removemsg");
                        response.setRenderParameter("status", "true");
                    } catch (Exception e) {
                        response.setRenderParameter("act", "notremovemsg");
                        response.setRenderParameter("status", "true");

                    }
                    response.setMode(response.Mode_VIEW);
                }
                if (accion.equals("update")) {
                    String tmsid = "global";
                    if (request.getParameter("tmsid") != null) {
                        tmsid = request.getParameter("tmsid");
                    }
                    WebSite ptm = SWBContext.getWebSite(tmsid);
                    Rule rRule = ptm.getRule(id);
                    rRule.setTitle(request.getParameter("title"));
                    rRule.setDescription(request.getParameter("description"));
                    rRule.setModifiedBy(user);

                    response.setRenderParameter("act", "edit");
                    response.setRenderParameter("id", id);
                    response.setRenderParameter("tree", "reload");
                    response.setRenderParameter("tmsid", tmsid);
                    response.setRenderParameter("lastTM", request.getParameter("lastTM"));
                    response.setRenderParameter("tm", request.getParameter("tmsid"));
                    if (request.getParameter("tp") != null) {
                        response.setRenderParameter("tp", request.getParameter("tp"));
                    }
                    if (request.getParameter("title") != null) {
                        response.setRenderParameter("title", request.getParameter("title"));
                    }
                    response.setMode(response.Mode_EDIT);
                }

                if (accion.trim().equalsIgnoreCase("editadd")) {
                    try {
                        Document newRuleDoc = SWBUtils.XML.xmlToDom("<?xml version=\"1.0\" encoding=\"UTF-8\"?><rule/>");
                        String tmsid = "global";
                        if (request.getParameter("tmsid") != null) {
                            tmsid = request.getParameter("tmsid");
                        }
                        if (!tmsid.equals("global")) {
                            WebSite ptm = SWBContext.getWebSite(tmsid);
                        }
                        WebSite ptm = SWBContext.getWebSite(tmsid);
                        Rule newRule = ptm.createRule(); 
                        newRule.setTitle(request.getParameter("title"));
                        newRule.setDescription(request.getParameter("description"));
                        newRule.setXml(SWBUtils.XML.domToXml(newRuleDoc));
                        newRule.setCreator(user);

                        String idnew = newRule.getId();
                        response.setRenderParameter("act", "details");
                        response.setRenderParameter("id", idnew);
                        response.setRenderParameter("tree", "reload");
                        response.setRenderParameter("tmsid", tmsid);
                        response.setRenderParameter("tm", tmparam);
                        if (request.getParameter("tm") != null) {
                            response.setRenderParameter("tm", request.getParameter("tm"));
                        }
                        if (request.getParameter("tp") != null) {
                            response.setRenderParameter("tp", request.getParameter("tp"));
                        }
                        if (request.getParameter("title") != null) {
                            response.setRenderParameter("title", request.getParameter("title"));
                        }
                        response.setMode(response.Mode_EDIT);
                    } catch (Exception ei) {
                        log.error(response.getLocaleString("msgErrorAddNewRule") + ". WBARules.processAction", ei);
                    }
                }
            }
        } catch (Exception ee) {
            log.error(ee);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("gateway")) {
            doGateway(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /** Edit view, Rule information edition
     * @param request input parameters
     * @param response an answer to the request
     * @param paramRequest a list of objects (WebPage, user, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        String rrid = request.getParameter("suri");
        Rule rRule = (Rule) ont.getGenericObject(rrid);
        String tmsid = rRule.getWebSite().getId();
        if (tmsid == null) {
            tmsid = paramRequest.getTopic().getWebSiteId();
        }
        comboAtt = null;
        vecOrderAtt = null;
        loadComboAttr(tmsid, rRule.getId(), paramRequest);
        StringBuffer ret = new StringBuffer("");
        String accion = request.getParameter("act");
        if (accion == null) {
            accion = "edit";
        }
        String id = "0";
        SWBResourceURL url = paramRequest.getActionUrl();

        if (request.getParameter("suri") != null) {
            id = request.getParameter("suri");
        }

        try {
            if (!id.equals("0")) {
                SWBResourceURL urlEdit = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
                urlEdit.setParameter("act", "edit");
                urlEdit.setParameter("id", id);
                SWBResourceURL urlDetail = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
                urlDetail.setParameter("act", "details");
                urlDetail.setParameter("id", id);
                SWBResourceURL urlHistory = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
                urlHistory.setParameter("act", "history");
                urlHistory.setParameter("id", id);
                ret.append("\n<div class=\"swbform\">");
                ret.append("\n<fieldset>");
                ret.append("\n<TABLE width=\"98%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\">");
                ret.append("\n<TR>");
                ret.append("\n<TD width=\"150\" align=\"right\"> " + paramRequest.getLocaleString("msgIdentifier") + "&nbsp;&nbsp;&nbsp;</TD>");
                ret.append("\n<TD>");
                ret.append(rRule.getId());
                ret.append("\n</TD>");
                ret.append("\n</TR>");
                ret.append("\n</TABLE></fieldset>");

                if (accion.equals("edit") | accion.equals("details")) {
                    String xml = null;
                    xml = rRule.getXml();
                    if (null == xml) {
                        xml = "<rule/>";
                        rRule.setXml(xml);
                    }
                    Document docxml = SWBUtils.XML.xmlToDom(xml);

                    sbTree = new StringBuffer();
                    if (docxml != null) {
                        Element rule = (Element) docxml.getFirstChild();
                        elemNum = 0;
                        rRule = null;
                        ret.append("\n<fieldset>");
                        ret.append("\n<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" >");
                        //ret.append("\n<tr><td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + paramRequest.getLocaleString("msgCriteriaDefinition") + "</td></tr>");
                        ret.append("\n<tr><td >");
                        ret.append(getApplet(request, response, paramRequest));
                        ret.append("\n</td></tr>");
                        ret.append("\n</table>");
                        ret.append("\n</fieldset>");
                    }
                    sbTree = null;
                    docxml = null;
                }
                ret.append("\n</div>");
            }
        } catch (Exception e) {
            log.error(paramRequest.getLocaleString("msgErrorEditRule") + ", WBARules.doEdit", e);
        }
        response.getWriter().print(ret.toString());
    }

    /** load in a HashMap all user attributes and default attribute to creates rules
     * @param tmid identificador de mapa de tópicos
     * @param ruleid identificador de la regla
     * @param paramRequest lista de objetos (WebPage, user, action, ...)
     */
    private void loadComboAttr(String tmid, String ruleid, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException {

        log.debug("loadComboAttr ruleid: " + ruleid + ", tmid: " + tmid);

        WebSite ws = SWBContext.getWebSite(tmid);
        User user = paramRequest.getUser();
        UserRepository usrRepo = ws.getUserRepository();
        comboAtt = new HashMap();
        vecOrderAtt = new Vector(1, 1);
        // Agreando valores iniciales al HashMap como son isloged, isregistered, language, device
        HashMap hmAttr = new HashMap();
        HashMap hmOper = new HashMap();
        HashMap hmValues = new HashMap();
        hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgUserRegistered"));  ///////////////////////////
        hmAttr.put("Tipo", "select");
        hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
        hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
        hmAttr.put("Operador", hmOper);
        hmValues.put("true", paramRequest.getLocaleString("msgYes"));
        hmValues.put("false", paramRequest.getLocaleString("msgNo"));
        hmAttr.put("Valor", hmValues);
        comboAtt.put("isregistered", hmAttr);

        hmAttr = new HashMap();
        hmOper = new HashMap();
        hmValues = new HashMap();
        hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgUserSigned"));   ///////////////////////////
        hmAttr.put("Tipo", "select");
        hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
        hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
        hmAttr.put("Operador", hmOper);
        hmValues.put("true", paramRequest.getLocaleString("msgYes"));
        hmValues.put("false", paramRequest.getLocaleString("msgNo"));
        hmAttr.put("Valor", hmValues);
        comboAtt.put("isloged", hmAttr);

        // DISPOSITIVOS POR SITIO
        hmAttr = new HashMap();
        hmOper = new HashMap();
        hmValues = new HashMap();
        hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgDevice"));   ///////////////////////////
        hmAttr.put("Tipo", "select");
        Iterator<Device> eleDev = SWBContext.getWebSite(tmid).listDevices();
        while (eleDev.hasNext()) {
            Device rDev = eleDev.next();
            hmValues.put(rDev.getId(), rDev.getTitle());
        }
        hmAttr.put("Valor", hmValues);
        hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
        hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
        hmAttr.put("Operador", hmOper);
        comboAtt.put("device", hmAttr);

        int numero = 0;
        vecOrderAtt.add(numero++, "isregistered");
        vecOrderAtt.add(numero++, "isloged");
        vecOrderAtt.add(numero++, "device");

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            hmAttr = new HashMap();
            hmOper = new HashMap();
            hmValues = new HashMap();
            hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgUserGroups"));   ///////////////////////////
            hmAttr.put("Tipo", "select");
            hmOper.put("=", paramRequest.getLocaleString("msgHave"));
            hmOper.put("!=", paramRequest.getLocaleString("msgNotHave"));
            hmAttr.put("Operador", hmOper);
            Iterator<UserGroup> enumUsrG = usrRepo.listUserGroups();
            while (enumUsrG.hasNext()) {
                UserGroup usrGroup = enumUsrG.next();
                hmValues.put(usrGroup.getId(), usrGroup.getTitle());
            }
            hmAttr.put("Valor", hmValues);
            //TODO: RuleMgr ???ROLE
            comboAtt.put("UserGroup", hmAttr); //RuleMgr.TAG_INT_ROLE
            vecOrderAtt.add(numero++, "UserGroup"); //RuleMgr.TAG_INT_ROLE

            // Se agrega la parte de roles
            hmAttr = new HashMap();
            hmOper = new HashMap();
            hmValues = new HashMap();
            hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgRoles"));   ///////////////////////////
            hmAttr.put("Tipo", "select");
            hmOper.put("=", paramRequest.getLocaleString("msgHave"));
            hmOper.put("!=", paramRequest.getLocaleString("msgNotHave"));
            hmAttr.put("Operador", hmOper);
            //Enumeration enumRoles = DBRole.getInstance().getRoles(TopicMgr.getInstance().getTopicMap(tmid).getDbdata().getRepository());
            Iterator<Role> enumRoles = SWBContext.getWebSite(tmid).getUserRepository().listRoles();
            while (enumRoles.hasNext()) {
                Role role = enumRoles.next();
                hmValues.put(role.getId(), role.getTitle());
            }
            hmAttr.put("Valor", hmValues);
            //TODO: RuleMgr ???ROLE
            comboAtt.put("ROLE", hmAttr); //RuleMgr.TAG_INT_ROLE
            vecOrderAtt.add(numero++, "ROLE"); //RuleMgr.TAG_INT_ROLE

            // Se agrega la parte de reglas
            if (ruleid == null) {
                ruleid = "";
            }

            int numreglas = 0;
            Iterator<Rule> numrules = SWBContext.getWebSite(tmid).listRules();
            while (numrules.hasNext()) {
                Rule rule = numrules.next();
                if (!ruleid.equals(rule.getId())) {
                    numreglas++;
                }
            }
            log.debug("numReglas:" + numreglas);
            if (numreglas > 0) {
                hmAttr = new HashMap();
                hmOper = new HashMap();
                hmValues = new HashMap();
                hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgRules"));   ///////////////////////////
                hmAttr.put("Tipo", "select");
                hmOper.put("=", paramRequest.getLocaleString("msgCumpla"));
                hmOper.put("!=", paramRequest.getLocaleString("msgNoCumpla"));
                hmAttr.put("Operador", hmOper);
                Iterator<Rule> enumRules = SWBContext.getWebSite(tmid).listRules();
                while (enumRules.hasNext()) {
                    Rule rule = enumRules.next();
                    if (!ruleid.equals(rule.getId())) {
                        hmValues.put(rule.getId(), rule.getTitle());
                    }
                }
                hmAttr.put("Valor", hmValues);
                comboAtt.put("RULE", hmAttr); //RuleMgr.TAG_INT_RULE
                vecOrderAtt.add(numero++, "RULE"); //RuleMgr.TAG_INT_RULE
            }

            //Tipo de usuario
            Iterator<String> usrTypes = usrRepo.getUserTypes();
            log.debug("usrTypes:" + usrTypes.hasNext());
            while (usrTypes.hasNext()) {

                String uType = usrTypes.next();
                log.debug("UType: " + uType);
                hmAttr = new HashMap();
                hmOper = new HashMap();
                hmValues = new HashMap();
                hmAttr.put("Etiqueta", uType);   ///////////////////////////
                hmAttr.put("Tipo", "select");
                hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
                hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
                hmAttr.put("Operador", hmOper);
                Iterator<SemanticProperty> iteruserType = usrRepo.listAttributesofUserType(uType);
                while (iteruserType.hasNext()) {
                    SemanticProperty usrTypeAtt = iteruserType.next();
                    hmValues.put(usrTypeAtt.getName(), usrTypeAtt.getDisplayName(user.getLanguage()));
                }
                hmAttr.put("Valor", hmValues);
                if (!tmid.equals(SWBContext.getGlobalWebSite().getId())) {
                    comboAtt.put(uType, hmAttr);
                }
                vecOrderAtt.add(numero++, uType); //RuleMgr.TAG_INT_ROLE
                log.debug("tipo de usuario: " + uType);
            }

            Iterator<SemanticProperty> iteratt = usrRepo.listBasicAttributes();
            int attnum = 0;
            while (iteratt.hasNext()) {

                String tipoControl = "TEXT";
                SemanticProperty usrAtt = iteratt.next();
                attnum++;
                log.debug("ListAttributes:" + usrAtt.getName() + ", " + attnum + ", objProp: " + usrAtt.isObjectProperty());
                hmAttr = new HashMap();
                hmOper = new HashMap();
                hmValues = new HashMap();

                hmAttr.put("Etiqueta", usrAtt.getDisplayName(user.getLanguage()));
                if (usrAtt.getDisplayProperty() != null) {
                    log.debug("DisplayProperty");
                    DisplayProperty dobj = new DisplayProperty(usrAtt.getDisplayProperty());

                    if(!dobj.isHidden())
                    {
                    String selectValues = dobj.getSelectValues(user.getLanguage());
                    ///////////////////////////
                    if (selectValues != null) {
                        tipoControl = "select";
                        hmAttr.put("Tipo", tipoControl);
                        hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
                        hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
                        hmAttr.put("Operador", hmOper);
                        StringTokenizer st = new StringTokenizer(selectValues, "|");
                        while (st.hasMoreTokens()) {
                            String tok = st.nextToken();
                            int ind = tok.indexOf(':');
                            String idt = tok;
                            String val = tok;
                            if (ind > 0) {
                                idt = tok.substring(0, ind);
                                val = tok.substring(ind + 1);
                            }
                            hmValues.put(idt, val);
                        }
                        hmAttr.put("Valor", hmValues);
                        comboAtt.put(usrAtt.getName(), hmAttr);
                        vecOrderAtt.add(numero++, usrAtt.getName());
                    } else {
                        if (usrAtt.isDataTypeProperty()) {
                            log.debug("DP: DataTypeProperty");
                            if (usrAtt.isInt()||usrAtt.isFloat()||usrAtt.isLong()) {
                                tipoControl = "TEXT";
                                hmAttr.put("Tipo", tipoControl);
                                hmOper.put("&gt;", paramRequest.getLocaleString("msgGreaterThan"));
                                hmOper.put("&lt;", paramRequest.getLocaleString("msgLessThan"));
                                hmOper.put("=", paramRequest.getLocaleString("msgIs"));
                                hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
                                hmAttr.put("Operador", hmOper);
                                comboAtt.put(usrAtt.getName(), hmAttr);
                                vecOrderAtt.add(numero++, usrAtt.getName());
                            } else if (usrAtt.isBoolean()) {
                                tipoControl = "select";
                                hmAttr.put("Tipo", tipoControl);
                                hmOper.put("=", paramRequest.getLocaleString("msgIs"));
                                hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
                                hmAttr.put("Operador", hmOper);
                                hmValues.put("true", paramRequest.getLocaleString("msgTrue"));
                                hmValues.put("false", paramRequest.getLocaleString("msgFalse"));
                                hmAttr.put("Valor", hmValues);
                                comboAtt.put(usrAtt.getName(), hmAttr);
                                vecOrderAtt.add(numero++, usrAtt.getName());
                            } else if (usrAtt.isString()) {
                                tipoControl = "TEXT";
                                hmAttr.put("Tipo", tipoControl);
                                hmOper.put("=", paramRequest.getLocaleString("msgIs"));
                                hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
                                hmAttr.put("Operador", hmOper);
                                comboAtt.put(usrAtt.getName(), hmAttr);
                                vecOrderAtt.add(numero++, usrAtt.getName());
                            }

                            // PARA ObjectType

//                            else {
//                                tipoControl = "TEXT";
//                                hmAttr.put("Tipo", tipoControl);
//                                hmOper.put("=", paramRequest.getLocaleString("msgIs"));
//                                hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
//                                hmAttr.put("Operador", hmOper);
//                                comboAtt.put(usrAtt.getName(), hmAttr);
//                                vecOrderAtt.add(numero++, usrAtt.getName());
//                            }
                        }
//                        else if (usrAtt.isObjectProperty()) {
//                            log.debug("DP: ObjectProperty");
//                            tipoControl = "select";
//                            if (usrAtt == User.swb_hasRole) {
//                                Iterator<Role> itRol = usrRepo.listRoles();
//                                while (itRol.hasNext()) {
//                                    Role rol = itRol.next();
//                                    hmValues.put(rol.getId(), rol.getDisplayTitle(user.getLanguage()));
//                                }
//                                hmAttr.put("Valor", hmValues);
//                            }
//                        }
                    }
                    log.debug("DP:Tipo --- " + tipoControl);
                  }
                }
            }

            // Atributos Extendidos
            iteratt = usrRepo.listExtendedAttributes();
            attnum = 0;
            while (iteratt.hasNext()) {
                String tipoControl = "TEXT";
                SemanticProperty usrAtt = iteratt.next();
                attnum++;
                log.debug("ListAttributes:" + usrAtt.getName() + ", " + attnum + ", objProp: " + usrAtt.isObjectProperty());
                hmAttr = new HashMap();
                hmOper = new HashMap();
                hmValues = new HashMap();
                hmAttr.put("Etiqueta", "EX: "+usrAtt.getDisplayName(user.getLanguage()));
                if (usrAtt.getDisplayProperty() != null ) {
                    log.debug("DisplayProperty");
                    DisplayProperty dobj = new DisplayProperty(usrAtt.getDisplayProperty());
                    if(!dobj.isHidden())
                    {
                        String selectValues = dobj.getSelectValues(user.getLanguage());
                        ///////////////////////////
                        if (selectValues != null) {
                            tipoControl = "select";
                            hmAttr.put("Tipo", tipoControl);
                            hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
                            hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
                            hmAttr.put("Operador", hmOper);
                            StringTokenizer st = new StringTokenizer(selectValues, "|");
                            while (st.hasMoreTokens()) {
                                String tok = st.nextToken();
                                int ind = tok.indexOf(':');
                                String idt = tok;
                                String val = tok;
                                if (ind > 0) {
                                    idt = tok.substring(0, ind);
                                    val = tok.substring(ind + 1);
                                }
                                hmValues.put(idt, val);
                            }
                            hmAttr.put("Valor", hmValues);
                            comboAtt.put(usrAtt.getName(), hmAttr);
                            vecOrderAtt.add(numero++, usrAtt.getName());
                        } else {
                            if (usrAtt.isDataTypeProperty()) {
                                log.debug("DP: DataTypeProperty");
                                if (usrAtt.isInt()||usrAtt.isFloat()||usrAtt.isLong()) {
                                    tipoControl = "TEXT";
                                    hmAttr.put("Tipo", tipoControl);
                                    hmOper.put("&gt;", paramRequest.getLocaleString("msgGreaterThan"));
                                    hmOper.put("&lt;", paramRequest.getLocaleString("msgLessThan"));
                                    hmOper.put("=", paramRequest.getLocaleString("msgIs"));
                                    hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
                                    hmAttr.put("Operador", hmOper);
                                    comboAtt.put(usrAtt.getName(), hmAttr);
                                    vecOrderAtt.add(numero++, usrAtt.getName());
                                } else if (usrAtt.isBoolean()) {
                                    tipoControl = "select";
                                    hmAttr.put("Tipo", tipoControl);
                                    hmOper.put("=", paramRequest.getLocaleString("msgIs"));
                                    hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
                                    hmAttr.put("Operador", hmOper);
                                    hmValues.put("true", paramRequest.getLocaleString("msgTrue"));
                                    hmValues.put("false", paramRequest.getLocaleString("msgFalse"));
                                    hmAttr.put("Valor", hmValues);
                                    comboAtt.put(usrAtt.getName(), hmAttr);
                                    vecOrderAtt.add(numero++, usrAtt.getName());
                                } else if (usrAtt.isString()) {
                                    tipoControl = "TEXT";
                                    hmAttr.put("Tipo", tipoControl);
                                    hmOper.put("=", paramRequest.getLocaleString("msgIs"));
                                    hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
                                    hmAttr.put("Operador", hmOper);
                                    comboAtt.put(usrAtt.getName(), hmAttr);
                                    vecOrderAtt.add(numero++, usrAtt.getName());
                                }

                                // PARA ObjectType

    //                            else {
    //                                tipoControl = "TEXT";
    //                                hmAttr.put("Tipo", tipoControl);
    //                                hmOper.put("=", paramRequest.getLocaleString("msgIs"));
    //                                hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
    //                                hmAttr.put("Operador", hmOper);
    //                                comboAtt.put(usrAtt.getName(), hmAttr);
    //                                vecOrderAtt.add(numero++, usrAtt.getName());
    //                            }
                            }
    //                        else if (usrAtt.isObjectProperty()) {
    //                            log.debug("DP: ObjectProperty");
    //                            tipoControl = "select";
    //                            if (usrAtt == User.swb_hasRole) {
    //                                Iterator<Role> itRol = usrRepo.listRoles();
    //                                while (itRol.hasNext()) {
    //                                    Role rol = itRol.next();
    //                                    hmValues.put(rol.getId(), rol.getDisplayTitle(user.getLanguage()));
    //                                }
    //                                hmAttr.put("Valor", hmValues);
    //                            }
    //                        }
                        }
                        log.debug("DP:Tipo --- " + tipoControl);
                    }
                }
            }
        //////////////////////////////////////////////////////////////////////

        } catch (Exception e) {
            log.error(paramRequest.getLocaleString("msgErrorLoadingUserAttributeList") + ". SWBARules.loadComboAttr", e);
        }

        // HABILITAR PARA DEBUG

//        Iterator<String> itkeys = comboAtt.keySet().iterator();
//        while(itkeys.hasNext())
//        {
//            log.debug("---- "+itkeys.next());
//        }

    }

    /** Gets the document with the user attributes
     * @return a document with the user attributes
     */
    private Document getXMLComboAttr() throws SWBResourceException, java.io.IOException {
        Document dom = null;
        try {
            dom = SWBUtils.XML.getNewDocument();
            Element attributes = dom.createElement("attributes");
            dom.appendChild(attributes);

            for (int i = 0; i < vecOrderAtt.size(); i++) {
                String valor = (String) vecOrderAtt.get(i);
                HashMap hmAttr = (HashMap) comboAtt.get(valor);
                String label = (String) hmAttr.get("Etiqueta");

                // armando combo de operadores
                Element attribute = dom.createElement("attribute");
                attribute.setAttribute("type", (String) hmAttr.get("Tipo"));
                attribute.setAttribute("name", valor);
                attribute.setAttribute("title", label);

                HashMap hmOper = (HashMap) hmAttr.get("Operador");
                Iterator itOper = hmOper.keySet().iterator();
                Element operators = dom.createElement("operators");
                while (itOper.hasNext()) {
                    String thisValue = (String) itOper.next();
                    String thisLabel = (String) hmOper.get(thisValue);
                    Element operator = dom.createElement("operator");
                    operator.setAttribute("value", thisValue);
                    operator.setAttribute("title", thisLabel);
                    operators.appendChild(operator);
                }
                attribute.appendChild(operators);
                hmOper = null;
                Element attValues = dom.createElement("catalog");
                attValues.setAttribute("name", "attValues");

                // armando combo para armar valores posibles del elemento
                if (!hmAttr.get("Tipo").equals("TEXT")) {
                    HashMap valoresCombo = (HashMap) hmAttr.get("Valor");
                    Iterator itValCombo = valoresCombo.keySet().iterator();
                    while (itValCombo.hasNext()) {
                        String nomValCombo = (String) itValCombo.next();
                        String labelValCombo = (String) valoresCombo.get(nomValCombo);
                        Element attValue = dom.createElement("option");
                        attValue.setAttribute("title", labelValCombo);
                        attValue.setAttribute("value", nomValCombo);
                        attValues.appendChild(attValue);
                    }
                    attribute.appendChild(attValues);
                } else {
                    //armar text para pedir/mostrar valor
                    Element attValue = dom.createElement("option");
                    attValue.setAttribute("title", "");
                    attValue.setAttribute("value", "TEXT");
                    attValues.appendChild(attValue);
                    attribute.appendChild(attValues);
                }
                attributes.appendChild(attribute);
            }
        } catch (Exception e) {
        }
        log.debug("XML: "+SWBUtils.XML.domToXml(dom));
        return dom;
    }

    private Document getError(int id) {
        Document dom = null;
        try {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);
            Element err = dom.createElement("err");
            res.appendChild(err);
            addElement("id", "" + id, err);
            if (id == 0) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_loginfail") + "...", err);
            } else if (id == 1) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_nouser") + "...", err);
            } else if (id == 2) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noservice") + "...", err);
            } else if (id == 3) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_serviceprocessfail") + "...", err);
            } else if (id == 4) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_parametersprocessfail") + "...", err);
            } else if (id == 5) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopicmap") + "...", err);
            } else if (id == 6) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopic") + "...", err);
            } else if (id == 7) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_usernopermiss") + "...", err);
            } else if (id == 8) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicAlreadyexist") + "...", err);
            } else if (id == 9) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_byImplement") + "...", err);
            } else if (id == 10) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicMapAlreadyExist") + "...", err);
            } else if (id == 11) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_FileNotFound") + "...", err);
            } else if (id == 12) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noversions") + "...", err);
            } else if (id == 13) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_xmlinconsistencyversion") + "...", err);
            } else if (id == 14) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noResourcesinMemory") + "...", err);
            } else if (id == 15) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noTemplatesinMemory") + "...", err);
            } else if (id == 16) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_TemplatenotRemovedfromFileSystem") + "...", err);
            } else if (id == 17) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_adminUsernotCreated") + "...", err);
            } else {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_errornotfound") + "...", err);
            }
        } catch (Exception e) {
            log.error(SWBUtils.TEXT.getLocaleString("locale_Gateway", "error_Gateway_getService_documentError") + "...", e);
        }
        return dom;
    }

    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        //log.debug("doGateway: URI"+request.getParameter("suri")+", id:"+request.getParameter("id"));
        ServletInputStream in = request.getInputStream();
        Document dom = SWBUtils.XML.xmlToDom(in);

        if (!dom.getFirstChild().getNodeName().equals("req")) {
            response.sendError(404, request.getRequestURI());
            return;
        }

        String cmd = null;
        if (dom.getElementsByTagName("cmd").getLength() > 0) {
            cmd = dom.getElementsByTagName("cmd").item(0).getFirstChild().getNodeValue();
        }
        if (cmd == null) {
            response.sendError(404, request.getRequestURI());
            return;
        }
        String ret;
        Document res = getService(cmd, dom, paramRequest.getUser(), request, response, paramRequest);
        if (res == null) {
            ret = SWBUtils.XML.domToXml(getError(3));
        } else {
            ret = SWBUtils.XML.domToXml(res, true);
        }
        out.print(new String(ret.getBytes()));
    }

    private Element addElement(String name, String value, Element parent) {
        Document doc = parent.getOwnerDocument();
        Element ele = doc.createElement(name);
        if (value != null) {
            ele.appendChild(doc.createTextNode(value));
        }
        parent.appendChild(ele);
        return ele;
    }

    private Document getService(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        String tmpcmd = cmd, tm = null, id = null;
        if (null != cmd && cmd.indexOf('.') != -1) {
            tmpcmd = cmd.substring(0, cmd.indexOf('.'));
            tm = cmd.substring(cmd.indexOf('.') + 1, cmd.lastIndexOf('.'));
            id = cmd.substring(cmd.lastIndexOf('.') + 1);
        }

        log.debug("getService: " + request.getParameter("suri"));
        if (tmpcmd.equals("getXMLAttr")) {
            try {
                return getXMLComboAttr();
            } catch (Exception e) {
                log.error("Error while trying to get XML user attributes. ", e);
            }
        } else if (tmpcmd.equals("getXMLRule")) {
            Rule rRule = null;
            rRule = (Rule) ont.getGenericObject(request.getParameter("suri"));
            return SWBUtils.XML.xmlToDom(rRule.getXml());
        } else if (tmpcmd.equals("updateRule")) {
            Document dom = null;
            Rule rRule = (Rule) ont.getGenericObject(request.getParameter("suri"));
            String strXMLRule = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
            String strTmp = SWBUtils.XML.domToXml(src);
            if (strTmp.indexOf("<rule>") != -1) {
                strXMLRule += strTmp.substring(strTmp.indexOf("<rule>"), strTmp.lastIndexOf("</rule>") + 7);
            } else {
                strXMLRule += "<rule/>";
            }
            try {
                rRule.setXml(strXMLRule);
                rRule = null;
                dom = SWBUtils.XML.xmlToDom(strXMLRule);
            } catch (Exception e) {
                log.error("Error while trying to update rule.", e);
            }
            return dom;
        }
        return getError(2);
    }
}
