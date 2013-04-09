/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Rule;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author jorge.jimenez
 */
public class SWBSocialRule extends GenericResource {

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBSocialRule.class);
    
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
    public SWBSocialRule() {
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
        StringBuffer ret = new StringBuffer();
        String suri = request.getParameter("suri");
        log.debug("getApplet: " + suri);
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SocialRule rRule = (SocialRule) ont.getGenericObject(suri);  
        WebPage tp = paramRequest.getWebPage();
        String tmparam = request.getParameter("tm");
        if (tmparam == null) {
            tmparam = paramRequest.getWebPage().getWebSiteId();
        }
        if (request.getParameter("tm") != null && request.getParameter("tp") != null) {
            tp = SWBContext.getWebSite(request.getParameter("tm")).getWebPage(request.getParameter("tp"));
            tmparam = request.getParameter("tm");
        }
        ret.append("\n<div class=\"applet\">");
        ret.append("\n<applet id=\"rulesApplet\" name=\"rulesApplet\" code=\"applets.rules.RuleApplet.class\" codebase=\"" + SWBPlatform.getContextPath() + "/\"  ARCHIVE=\"swbadmin/lib/SWBAplModeler.jar, swbadmin/lib/SWBAplCommons.jar, swbadmin/lib/SWBAplRules.jar\" width=\"100%\" height=\"500\">");  //ARCHIVE=\"wbadmin/lib/SWBAplGenericTree.jar, wbadmin/lib/SWBAplCommons.jar\"
        SWBResourceURL urlapp = paramRequest.getRenderUrl();
        urlapp.setMode("gateway");
        urlapp.setCallMethod(urlapp.Call_DIRECT);
        urlapp.setParameter("id", suri);
        urlapp.setParameter("suri", suri);
        ret.append("\n<param name=\"jsess\" value=\""+request.getSession().getId()+"\">");
        ret.append("\n<param name =\"cgipath\" value=\"" + urlapp + "\">");
        ret.append("\n<param name =\"tm\" value=\"" + rRule.getSemanticObject().getModel().getName() + "\">");  //TODO_J:Ver si funciona
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
        SocialSite socialSite=SocialSite.ClassMgr.getSocialSite(tmparam);
        System.out.println("processAction/socialSite..J:"+socialSite.getId());
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
                    System.out.println("processAction/tmsid..J:"+tmsid);
                    //SocialSite ptm = SocialSite.ClassMgr.getSocialSite(tmsid);
                    SocialRule rRule = SocialRule.ClassMgr.getSocialRule(id, socialSite);
                    System.out.println("processAction/rRule..J:"+rRule);
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
        SocialRule rRule = (SocialRule) ont.getGenericObject(rrid);
        //String tmsid = rRule.getWebSite().getId();
        String tmsid=rRule.getSemanticObject().getModel().getName();
        if (tmsid == null) {
            tmsid = paramRequest.getWebPage().getWebSiteId();
        }

        log.debug("tm:"+tmsid);

        comboAtt = null;
        vecOrderAtt = null;
        loadComboAttr(tmsid, rRule.getURI(), paramRequest);
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
//                ret.append("\n<fieldset>");
//                ret.append("\n<TABLE width=\"98%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\">");
//                ret.append("\n<TR>");
//                ret.append("\n<TD width=\"150\" align=\"right\"> " + paramRequest.getLocaleString("msgIdentifier") + "&nbsp;&nbsp;&nbsp;</TD>");
//                ret.append("\n<TD>");
//                ret.append(rRule.getId());
//                ret.append("\n</TD>");
//                ret.append("\n</TR>");
//                ret.append("\n</TABLE></fieldset>");

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
            log.error(paramRequest.getLocaleString("msgErrorEditRule") + ", WBARules.doEdit", e);
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
    private void loadComboAttr(String tmid, String ruleid, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException 
    {

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
        hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgSentiment"));  ///////////////////////////
        hmAttr.put("Tipo", "select");
        hmOper.put("=", paramRequest.getLocaleString("msgSameAs"));
        hmOper.put("!=", paramRequest.getLocaleString("msgNotEqual"));
        hmAttr.put("Operador", hmOper);
        hmValues.put("1", paramRequest.getLocaleString("msgPositive"));
        hmValues.put("2", paramRequest.getLocaleString("msgNegative"));
        hmValues.put("0", paramRequest.getLocaleString("msgNeutral"));
        hmAttr.put("Valor", hmValues);
        comboAtt.put(PostIn.social_postSentimentalType.getName(), hmAttr); //RuleMgr.TAG_INT_ISREGISTERED

        //Klout
        hmAttr = new HashMap();
        hmOper = new HashMap();
        hmValues = new HashMap();
        hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgUserKlout"));   ///////////////////////////
        hmAttr.put("Tipo", "TEXT");
        hmOper.put(">", paramRequest.getLocaleString("msgGreater"));
        hmAttr.put("Operador", hmOper);
        comboAtt.put(Stream.social_stream_KloutValue.getName(), hmAttr); //falta tag de IP
        
        int numero = 0;
        vecOrderAtt.add(numero++, PostIn.social_postSentimentalType.getName()); //RuleMgr.TAG_INT_ISREGISTERED
        vecOrderAtt.add(numero++, Stream.social_stream_KloutValue.getName()); //falta tag de IP
        
 /*       

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

        int numero = 0;
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
            Iterator<UserGroup> enumUsrG = usrRepo.listUserGroups();
            while (enumUsrG.hasNext()) {
                UserGroup usrGroup = enumUsrG.next();
                hmValues.put(usrGroup.getURI(), usrGroup.getDisplayTitle(user.getLanguage()));
            }
            hmAttr.put("Valor", hmValues);
            hmOper.put("=", paramRequest.getLocaleString("msgHave"));
            hmOper.put("!=", paramRequest.getLocaleString("msgNotHave"));
            hmAttr.put("Operador", hmOper);

            comboAtt.put(SWBRuleMgr.TAG_INT_USERGROUP, hmAttr); 
            vecOrderAtt.add(numero++, SWBRuleMgr.TAG_INT_USERGROUP); 

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

            // Se agrega la parte de paises

            List<Country> countries = SWBUtils.Collections.copyIterator(SWBContext.getWebSite(tmid).listCountries());
            log.debug("numPais:" + countries.size());
            if (!countries.isEmpty()) {
                hmAttr = new HashMap();
                hmOper = new HashMap();
                hmValues = new HashMap();
                hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgCountries"));   ///////////////////////////
                hmAttr.put("Tipo", "select");
                hmOper.put("=", paramRequest.getLocaleString("msgIs"));
                hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
                hmAttr.put("Operador", hmOper);
                Iterator<Country> enumCountry = countries.iterator();
                while (enumCountry.hasNext()) {
                    Country country = enumCountry.next();
                    hmValues.put(country.getId(), country.getDisplayTitle(user.getLanguage()));
                }
                hmAttr.put("Valor", hmValues);
                comboAtt.put(User.swb_usrCountry.getName(), hmAttr); //falta tag del pais
                vecOrderAtt.add(numero++, User.swb_usrCountry.getName()); //falta tag del pais
            }

            //Se agrega la parte de IP del usuario
            hmAttr = new HashMap();
            hmOper = new HashMap();
            hmValues = new HashMap();
            hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgIPUser"));
            hmAttr.put("Tipo", "TEXT");
            hmOper.put("=", paramRequest.getLocaleString("msgIs"));
            hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
            hmAttr.put("Operador", hmOper);
            comboAtt.put(SWBRuleMgr.TAG_INT_USERIP, hmAttr); //falta tag de IP
            vecOrderAtt.add(numero++, SWBRuleMgr.TAG_INT_USERIP); //falta tag de IP

            //Se agrega la parte de WebPage visitada por el usuario
            hmAttr = new HashMap();
            hmOper = new HashMap();
            hmValues = new HashMap();
            hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgWebPageUser"));
            hmAttr.put("Tipo", "TEXT");
            hmOper.put("=", paramRequest.getLocaleString("msgContains"));
            hmOper.put("!=", paramRequest.getLocaleString("msgNotContains"));
            hmAttr.put("Operador", hmOper);
            comboAtt.put(SWBRuleMgr.TAG_WEBPAGEVISITED_ATT, hmAttr);
            vecOrderAtt.add(numero++, SWBRuleMgr.TAG_WEBPAGEVISITED_ATT);


            //Se agrega la parte de Historial de WebPages visitada por el usuario
            hmAttr = new HashMap();
            hmOper = new HashMap();
            hmValues = new HashMap();
            hmAttr.put("Etiqueta", paramRequest.getLocaleString("msgHistoryWebPageUser"));
            hmAttr.put("Tipo", "TEXT");
            hmOper.put("=", paramRequest.getLocaleString("msgContains"));
            hmOper.put("!=", paramRequest.getLocaleString("msgNotContains"));
            for(int i=1; i<26; i++)
            {
                //System.out.println("i "+i);
                hmOper.put("-"+i, "-"+i);
            }
            hmAttr.put("Operador", hmOper);
            comboAtt.put(SWBRuleMgr.TAG_WEBPAGEHISTORY_ATT, hmAttr); //
            vecOrderAtt.add(numero++, SWBRuleMgr.TAG_WEBPAGEHISTORY_ATT); //



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
**/
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
        //                }
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
//                    }
//                    log.debug("DP:Tipo --- " + tipoControl);
//                  }
//                }
//            }
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

//        } catch (Exception e) {
//            log.error(paramRequest.getLocaleString("msgErrorLoadingUserAttributeList") + ". SWBARules.loadComboAttr", e);
//        }

        // HABILITAR PARA DEBUG

//        Iterator<String> itkeys = comboAtt.keySet().iterator();
//        while(itkeys.hasNext())
//        {
//            log.debug("---- "+itkeys.next());
//        }

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

                    //System.out.println("Operadores cargados: "+thisLabel+", "+thisValue);

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
        //System.out.println("sin:"+sin);
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
            SocialRule rRule = null;
            rRule = (SocialRule) ont.getGenericObject(request.getParameter("suri"));
            return SWBUtils.XML.xmlToDom(rRule.getXml());
        } else if (tmpcmd.equals("updateRule")) {
            Document dom = null;
            SocialRule rRule = (SocialRule) ont.getGenericObject(request.getParameter("suri"));
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
