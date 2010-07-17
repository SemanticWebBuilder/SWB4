/**  
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
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources;

import java.io.*;
import java.util.*;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.portal.api.*;
import org.semanticwb.process.model.ProcessRule;
import org.semanticwb.process.model.ProcessSite;
import org.w3c.dom.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBARuleProcess.
 * 
 * @author juan.fernandez
 */
public class SWBARuleProcess extends GenericResource {

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBARuleProcess.class);
    
    /** The sb tree. */
    StringBuffer sbTree = null;
    
    /** The combo att. */
    HashMap comboAtt = null;
    
    /** The vec order att. */
    Vector vecOrderAtt = null;
    
    /** The local doc. */
    Document localDoc = null;
    
    /** The elem num. */
    int elemNum = 0;
    
    /** The xml attr. */
    String xmlAttr = null;

    /**
     * Creates a new instance of Rules.
     */
    public SWBARuleProcess() {
    }

    /**
     * User view, creates the rules.
     * 
     * @param request input parameters
     * @param response an answer to the request
     * @param paramRequest a list of objects (WebPage, user, action, ...)
     * @return the applet
     * @throws SWBResourceException an SWB Resource Exception
     * @throws IOException an IO Exception
     */

    private String getApplet(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuilder ret = new StringBuilder();
        String suri = request.getParameter("suri");
        log.debug("getApplet: " + suri);
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        ProcessRule rRule = (ProcessRule) ont.getGenericObject(suri);
        WebPage tp = paramRequest.getWebPage();

        ret.append("\n<div class=\"applet\">");
        ret.append("\n<applet id=\"rulesApplet\" name=\"rulesApplet\" code=\"applets.rules.RuleApplet.class\" codebase=\"" + SWBPlatform.getContextPath() + "/\"  ARCHIVE=\"swbadmin/lib/SWBAplModeler.jar, swbadmin/lib/SWBAplCommons.jar, swbadmin/lib/SWBAplRules.jar\" width=\"100%\" height=\"500\">");  //ARCHIVE=\"wbadmin/lib/SWBAplGenericTree.jar, wbadmin/lib/SWBAplCommons.jar\"
        SWBResourceURL urlapp = paramRequest.getRenderUrl();
        urlapp.setMode("gateway");
        urlapp.setCallMethod(urlapp.Call_DIRECT);
        urlapp.setParameter("id", suri);
        urlapp.setParameter("suri", suri);
        ret.append("\n<param name=\"jsess\" value=\""+request.getSession().getId()+"\">");
        ret.append("\n<param name =\"cgipath\" value=\"" + urlapp + "\">");
        ret.append("\n<param name =\"tm\" value=\"" + rRule.getProcessSite().getId() + "\">");
        if (null != request.getParameter("suri")) {
            ret.append("\n<param name =\"id\" value=\"" + rRule.getId() + "\">");
            ret.append("\n<param name =\"suri\" value=\"" + rRule.getId() + "\">");
        } else {
            ret.append("\n<param name =\"id\" value=\"0\">");
        }
        ret.append("\n<param name =\"act\" value=\"edit\">");
        ret.append("\n<param name =\"locale\" value=\"" + paramRequest.getUser().getLanguage() + "\">");
        ret.append("\n</applet>");
        ret.append("\n</div>");
        return ret.toString();
    }

    /**
     * Add, update or removes rules.
     * 
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
        WebPage WebPage = response.getWebPage();
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
                    ProcessSite ptm = ProcessSite.ClassMgr.getProcessSite(tmsid);
                    ProcessRule rRule = ptm.getProcessRule(id);
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
                        ProcessSite ptm = ProcessSite.ClassMgr.getProcessSite(tmsid);
                        ProcessRule newRule = ptm.createProcessRule();
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
     * Process request.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("gateway")) {
            doGateway(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * Edit view, Rule information edition.
     * 
     * @param request input parameters
     * @param response an answer to the request
     * @param paramRequest a list of objects (WebPage, user, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        String rrid = request.getParameter("suri");
        ProcessRule rRule = (ProcessRule) ont.getGenericObject(rrid);
        String tmsid = rRule.getProcessSite().getId();
        if (tmsid == null) {
            tmsid = paramRequest.getWebPage().getWebSiteId();
        }

        log.debug("tm:"+tmsid);

        comboAtt = null;
        vecOrderAtt = null;
        loadComboAttr(tmsid, rRule.getURI(), paramRequest, request);
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

                if (accion.equals("edit") || accion.equals("details")) {
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
                        //ret.append("\n<fieldset>");
                        ret.append("\n<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" >");
                        //ret.append("\n<tr><td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + paramRequest.getLocaleString("msgCriteriaDefinition") + "</td></tr>");
                        ret.append("\n<tr><td >");
                        ret.append(getApplet(request, response, paramRequest));
                        ret.append("\n</td></tr>");
                        ret.append("\n</table>");
                        //ret.append("\n</fieldset>");
                    }
                    sbTree = null;
                    docxml = null;
                }
                ret.append("\n</div>");
            }
        } catch (Exception e) {
            log.error(paramRequest.getLocaleString("msgErrorEditRule") + ", SWBARuleProcess.doEdit", e);
        }
        response.getWriter().print(ret.toString());
    }

    /**
     * load in a HashMap all user attributes and default attribute to creates rules.
     * 
     * @param tmid identificador de mapa de tópicos
     * @param ruleid identificador de la regla
     * @param paramRequest lista de objetos (WebPage, user, action, ...)
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void loadComboAttr(String tmid, String ruleid, SWBParamRequest paramRequest, HttpServletRequest request) throws SWBResourceException, java.io.IOException
    {
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        String rrid = request.getParameter("suri");
        ProcessRule rRule = (ProcessRule) ont.getGenericObject(rrid);
        String tmsid = rRule.getProcessSite().getId();
        if (tmsid == null) {
            tmsid = paramRequest.getWebPage().getWebSiteId();
        }

        log.debug("loadComboAttr ruleid: " + ruleid + ", tmid: " + tmsid);

        ProcessSite ws = ProcessSite.ClassMgr.getProcessSite(tmsid);
        User user = paramRequest.getUser();
        UserRepository usrRepo = ws.getUserRepository();
        comboAtt = new HashMap();
        vecOrderAtt = new Vector(1, 1);

        HashMap hmAttr = null;
        HashMap hmOper = null;
        HashMap hmValues = null;
        int numero = 0;

        log.debug("Propiedades clases....");
        log.debug("ProcessSite:"+ws.getDisplayTitle(user.getLanguage()));
        SemanticClass pcls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(SemanticVocabulary.PROCESS_CLASS);
        log.debug("SemClass:"+pcls);
        Iterator<SemanticObject> it = pcls.listInstances();

        String tipoControl ="";

        while (it.hasNext()) {
            SemanticObject semanticObject = it.next();
            SemanticClass cls=semanticObject.transformToSemanticClass();
            System.out.println("trans2semClass:"+cls);
            Iterator<SemanticProperty> itsp = cls.listProperties();
            while (itsp.hasNext()) {
                SemanticProperty semProp = itsp.next();
                if(null!=semProp)log.debug("SemProp:"+semProp.getName());
                // Agreando valores al HashMap 
                hmAttr = new HashMap();
                hmOper = new HashMap();
                hmValues = new HashMap();
                hmAttr.put("Etiqueta", cls.getName()+"."+semProp.getName());  ///////////////////////////

                if (semProp.isDataTypeProperty()) {
                    log.debug("DP: DataTypeProperty");
                    if (semProp.isInt()||semProp.isFloat()||semProp.isLong()) {
                        tipoControl = "TEXT";
                        hmAttr.put("Tipo", tipoControl);
                        hmOper.put("&gt;", paramRequest.getLocaleString("msgGreaterThan"));
                        hmOper.put("&lt;", paramRequest.getLocaleString("msgLessThan"));
                        hmOper.put("=", paramRequest.getLocaleString("msgIs"));
                        hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
                        hmAttr.put("Operador", hmOper);

                    } else if (semProp.isBoolean()) {
                        tipoControl = "select";
                        hmAttr.put("Tipo", tipoControl);
                        hmOper.put("=", paramRequest.getLocaleString("msgIs"));
                        hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
                        hmAttr.put("Operador", hmOper);
                        hmValues.put("true", paramRequest.getLocaleString("msgTrue"));
                        hmValues.put("false", paramRequest.getLocaleString("msgFalse"));
                        hmAttr.put("Valor", hmValues);

                    } else if (semProp.isString()) {
                        tipoControl = "TEXT";
                        hmAttr.put("Tipo", tipoControl);
                        hmOper.put("=", paramRequest.getLocaleString("msgIs"));
                        hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
                        hmAttr.put("Operador", hmOper);

                    } else  {
                        tipoControl = "TEXT";
                        hmAttr.put("Tipo", tipoControl);
                        hmOper.put("=", paramRequest.getLocaleString("msgIs"));
                        hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
                        hmAttr.put("Operador", hmOper);

                    }

                }
                // Object property
                else if(semProp.isObjectProperty())
                {
                    hmAttr.put("Tipo", "select");
                    hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
                    hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
                    hmOper.put("[", paramRequest.getLocaleString("msgContains"));
                    hmOper.put("![", paramRequest.getLocaleString("msgNotContains"));
                    hmAttr.put("Operador", hmOper);
                    Iterator<SemanticObject> itsemobj = semProp.getRangeClass().listInstances();
                    while (itsemobj.hasNext()) {
                        SemanticObject semanticObject1 = itsemobj.next();
                        hmValues.put(semanticObject1.getId(), semanticObject1.getDisplayName(user.getLanguage()));
                    }
                    hmAttr.put("Valor", hmValues);
                }

                comboAtt.put(semProp.getPrefix()+"_"+semProp.getName(), hmAttr);
                vecOrderAtt.add(numero++, semProp.getPrefix()+"_"+semProp.getName());
            }
        }

        /*
        // Agreando valores iniciales al HashMap como son isloged, isregistered, language, device
        
        hmAttr = new HashMap();
        hmOper = new HashMap();
        hmValues = new HashMap();
        hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgUserRegistered"));  ///////////////////////////
        hmAttr.put("Tipo", "select");
        hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
        hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
        hmAttr.put("Operador", hmOper);
        hmValues.put("true", paramRequest.getLocaleString("msgYes"));
        hmValues.put("false", paramRequest.getLocaleString("msgNo"));
        hmAttr.put("Valor", hmValues);
        comboAtt.put(SWBRuleMgr.TAG_INT_ISREGISTERED, hmAttr); //RuleMgr.TAG_INT_ISREGISTERED

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
        comboAtt.put(SWBRuleMgr.TAG_INT_ISSIGNED, hmAttr); //RuleMgr.TAG_INT_ISLOGED

        // DISPOSITIVOS POR SITIO
        hmAttr = new HashMap();
        hmOper = new HashMap();
        hmValues = new HashMap();
        hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgDevice"));   ///////////////////////////
        hmAttr.put("Tipo", "select");
        Iterator<Device> eleDev = SWBContext.getWebSite(tmid).listDevices();
        while (eleDev.hasNext()) {
            Device rDev = eleDev.next();
            hmValues.put(rDev.getURI(), rDev.getDisplayTitle(user.getLanguage()));
        }
        hmAttr.put("Valor", hmValues);
        hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
        hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
        hmAttr.put("Operador", hmOper);
        comboAtt.put(SWBRuleMgr.TAG_INT_DEVICE, hmAttr); //RuleMgr.TAG_INT_DEVICE

        vecOrderAtt.add(numero++, SWBRuleMgr.TAG_INT_ISREGISTERED); //RuleMgr.TAG_INT_ISREGISTERED
        vecOrderAtt.add(numero++, SWBRuleMgr.TAG_INT_ISSIGNED); //RuleMgr.TAG_INT_ISLOGED
        vecOrderAtt.add(numero++, SWBRuleMgr.TAG_INT_DEVICE); //RuleMgr.TAG_INT_DEVICE

        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Grupo de usuarios
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
                hmValues.put(usrGroup.getURI(), usrGroup.getDisplayTitle(user.getLanguage()));
            }
            hmAttr.put("Valor", hmValues);
            //TODO: RuleMgr ???ROLE
            comboAtt.put(SWBRuleMgr.TAG_INT_USERGROUP, hmAttr); //RuleMgr.TAG_INT_USERGROUP
            vecOrderAtt.add(numero++, SWBRuleMgr.TAG_INT_USERGROUP); //RuleMgr.TAG_INT_USERGROUP

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
                hmValues.put(role.getURI(), role.getDisplayTitle(user.getLanguage()));
            }
            hmAttr.put("Valor", hmValues);
            //TODO: RuleMgr ???ROLE
            comboAtt.put(SWBRuleMgr.TAG_INT_ROLE, hmAttr); //SWBRuleMgr.TAG_INT_ROLE
            vecOrderAtt.add(numero++, SWBRuleMgr.TAG_INT_ROLE); //RuleMgr.TAG_INT_ROLE

            // Se agrega la parte de reglas
            if (ruleid == null) {
                ruleid = "";
            }

            int numreglas = 0;
            Iterator<Rule> numrules = SWBContext.getWebSite(tmid).listRules();
            while (numrules.hasNext()) {
                Rule rule = numrules.next();
                if (!ruleid.equals(rule.getURI())) {
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
                    if (!ruleid.equals(rule.getURI())) {
                        hmValues.put(rule.getURI(), rule.getDisplayTitle(user.getLanguage()));
                    }
                }
                hmAttr.put("Valor", hmValues);
                comboAtt.put(SWBRuleMgr.TAG_INT_RULE, hmAttr); //RuleMgr.TAG_INT_RULE
                vecOrderAtt.add(numero++, SWBRuleMgr.TAG_INT_RULE); //RuleMgr.TAG_INT_RULE
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
                vecOrderAtt.add(numero++, uType); 
                log.debug("tipo de usuario: " + uType);
            }

            Iterator<SemanticProperty> iteratt = usrRepo.listAttributes(); //listBasicAttributes
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

                        if(usrAtt.getName().equals("usrLanguage"))
                        {
                            Iterator<Language> itlang= ws.listLanguages();
                            while (itlang.hasNext()) {
                                Language lang = itlang.next();
                                if (null!=lang) {
                                    String strLang = lang.getDisplayTitle(user.getLanguage());
                                    String strVal = lang.getId();
                                    hmValues.put(strVal,strLang);
                                }
                            }
                        }
                        else
                        {
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
                        }
                        hmAttr.put("Valor", hmValues);
                        comboAtt.put(usrAtt.getName(), hmAttr);
                        vecOrderAtt.add(numero++, usrAtt.getName());
                    } else if (!usrAtt.equals(User.swb_usrPassword)){
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
//
//            // Atributos Extendidos
//            iteratt = usrRepo.listExtendedAttributes();
//            attnum = 0;
//            while (iteratt.hasNext()) {
//                String tipoControl = "TEXT";
//                SemanticProperty usrAtt = iteratt.next();
//                attnum++;
//                log.debug("ListAttributes:" + usrAtt.getName() + ", " + attnum + ", objProp: " + usrAtt.isObjectProperty());
//                hmAttr = new HashMap();
//                hmOper = new HashMap();
//                hmValues = new HashMap();
//                hmAttr.put("Etiqueta", "EX: "+usrAtt.getDisplayName(user.getLanguage()));
//                if (usrAtt.getDisplayProperty() != null ) {
//                    log.debug("DisplayProperty");
//                    DisplayProperty dobj = new DisplayProperty(usrAtt.getDisplayProperty());
//                    if(!dobj.isHidden())
//                    {
//                        String selectValues = dobj.getSelectValues(user.getLanguage());
//                        ///////////////////////////
//                        if (selectValues != null) {
//                            tipoControl = "select";
//                            hmAttr.put("Tipo", tipoControl);
//                            hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
//                            hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
//                            hmAttr.put("Operador", hmOper);
//                            StringTokenizer st = new StringTokenizer(selectValues, "|");
//                            while (st.hasMoreTokens()) {
//                                String tok = st.nextToken();
//                                int ind = tok.indexOf(':');
//                                String idt = tok;
//                                String val = tok;
//                                if (ind > 0) {
//                                    idt = tok.substring(0, ind);
//                                    val = tok.substring(ind + 1);
//                                }
//                                hmValues.put(idt, val);
//                            }
//                            hmAttr.put("Valor", hmValues);
//                            comboAtt.put(usrAtt.getName(), hmAttr);
//                            vecOrderAtt.add(numero++, usrAtt.getName());
//                        } else {
//                            if (usrAtt.isDataTypeProperty()) {
//                                log.debug("DP: DataTypeProperty");
//                                if (usrAtt.isInt()||usrAtt.isFloat()||usrAtt.isLong()) {
//                                    tipoControl = "TEXT";
//                                    hmAttr.put("Tipo", tipoControl);
//                                    hmOper.put("&gt;", paramRequest.getLocaleString("msgGreaterThan"));
//                                    hmOper.put("&lt;", paramRequest.getLocaleString("msgLessThan"));
//                                    hmOper.put("=", paramRequest.getLocaleString("msgIs"));
//                                    hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
//                                    hmAttr.put("Operador", hmOper);
//                                    comboAtt.put(usrAtt.getName(), hmAttr);
//                                    vecOrderAtt.add(numero++, usrAtt.getName());
//                                } else if (usrAtt.isBoolean()) {
//                                    tipoControl = "select";
//                                    hmAttr.put("Tipo", tipoControl);
//                                    hmOper.put("=", paramRequest.getLocaleString("msgIs"));
//                                    hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
//                                    hmAttr.put("Operador", hmOper);
//                                    hmValues.put("true", paramRequest.getLocaleString("msgTrue"));
//                                    hmValues.put("false", paramRequest.getLocaleString("msgFalse"));
//                                    hmAttr.put("Valor", hmValues);
//                                    comboAtt.put(usrAtt.getName(), hmAttr);
//                                    vecOrderAtt.add(numero++, usrAtt.getName());
//                                } else if (usrAtt.isString()) {
//                                    tipoControl = "TEXT";
//                                    hmAttr.put("Tipo", tipoControl);
//                                    hmOper.put("=", paramRequest.getLocaleString("msgIs"));
//                                    hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
//                                    hmAttr.put("Operador", hmOper);
//                                    comboAtt.put(usrAtt.getName(), hmAttr);
//                                    vecOrderAtt.add(numero++, usrAtt.getName());
//                                }
//
//                                // PARA ObjectType
//
//    //                            else {
//    //                                tipoControl = "TEXT";
//    //                                hmAttr.put("Tipo", tipoControl);
//    //                                hmOper.put("=", paramRequest.getLocaleString("msgIs"));
//    //                                hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
//    //                                hmAttr.put("Operador", hmOper);
//    //                                comboAtt.put(usrAtt.getName(), hmAttr);
//    //                                vecOrderAtt.add(numero++, usrAtt.getName());
//    //                            }
//                            }
//    //                        else if (usrAtt.isObjectProperty()) {
//    //                            log.debug("DP: ObjectProperty");
//    //                            tipoControl = "select";
//    //                            if (usrAtt == User.swb_hasRole) {
//    //                                Iterator<Role> itRol = usrRepo.listRoles();
//    //                                while (itRol.hasNext()) {
//    //                                    Role rol = itRol.next();
//    //                                    hmValues.put(rol.getId(), rol.getDisplayTitle(user.getLanguage()));
//    //                                }
//    //                                hmAttr.put("Valor", hmValues);
//    //                            }
//    //                        }
//                        }
//                        log.debug("DP:Tipo --- " + tipoControl);
//                    }
//                }
//            }
//        //////////////////////////////////////////////////////////////////////

        } catch (Exception e) {
            log.error(paramRequest.getLocaleString("msgErrorLoadingUserAttributeList") + ". SWBARules.loadComboAttr", e);
        }
*/
        // HABILITAR PARA DEBUG

        Iterator<String> itkeys = comboAtt.keySet().iterator();
        while(itkeys.hasNext())
        {
            log.debug("---- "+itkeys.next());
        }

    }

    /**
     * Gets the document with the user attributes.
     * 
     * @return a document with the user attributes
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
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

    /**
     * Gets the error.
     * 
     * @param id the id
     * @return the error
     */
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
     * Do gateway.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        //log.debug("doGateway: URI"+request.getParameter("suri")+", id:"+request.getParameter("id"));
        ServletInputStream in = request.getInputStream();
        String sin=SWBUtils.IO.readInputStream(in);
        log.debug("doGateway: "+sin);
        Document dom = SWBUtils.XML.xmlToDom(sin);

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

    /**
     * Adds the element.
     * 
     * @param name the name
     * @param value the value
     * @param parent the parent
     * @return the element
     */
    private Element addElement(String name, String value, Element parent) {
        Document doc = parent.getOwnerDocument();
        Element ele = doc.createElement(name);
        if (value != null) {
            ele.appendChild(doc.createTextNode(value));
        }
        parent.appendChild(ele);
        return ele;
    }

    /**
     * Gets the service.
     * 
     * @param cmd the cmd
     * @param src the src
     * @param user the user
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @return the service
     */
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
            ProcessRule rRule = null;
            rRule = (ProcessRule) ont.getGenericObject(request.getParameter("suri"));
            return SWBUtils.XML.xmlToDom(rRule.getXml());
        } else if (tmpcmd.equals("updateRule")) {
            Document dom = null;
            ProcessRule rRule = (ProcessRule) ont.getGenericObject(request.getParameter("suri"));
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
