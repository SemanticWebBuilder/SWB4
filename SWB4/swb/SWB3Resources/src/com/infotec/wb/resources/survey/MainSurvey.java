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

package com.infotec.wb.resources.survey;

import com.infotec.wb.resources.survey.db.RecAnswer;
import com.infotec.wb.resources.survey.db.RecCategory;
import com.infotec.wb.resources.survey.db.RecControlCatalog;
import com.infotec.wb.resources.survey.db.RecFreqAnswer;
import com.infotec.wb.resources.survey.db.RecGroupAnswer;
import com.infotec.wb.resources.survey.db.RecQuestion;
import com.infotec.wb.resources.survey.db.RecResponseUser;
import com.infotec.wb.resources.survey.db.RecSubject;
import com.infotec.wb.resources.survey.db.RecSurvey;
import com.infotec.wb.resources.survey.db.RecValidateCode;
//import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Cookie;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.Templates;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamSource;
//import javax.xml.transform.stream.StreamResult;
import java.util.*;
import java.sql.*;

//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
//import java.io.StringWriter;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBMail;
import org.semanticwb.model.Dns;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.util.db.GenericDB;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

/** Esta clase despliega el recurso de sistema Formulario para WebBuilder, al momento de
 * instalarse genera tablas propias en la base de datos. Este recurso viene de la
 * versi�n 2 de WB.
 *
 * This class displays the Survey system resource for WebBuilder, creates its own
 * tables in database when is installed.
 *
 * Created by
 * User: Juan Antonio Fernández Arias
 * 
 */
public class MainSurvey extends GenericResource
{
    private static Logger log = SWBUtils.getLogger(MainSurvey.class);

    private Resource base=null;
    static Properties recproperties=null;
    
    /*Variables del archivo properties*/
    static String pr_subject = null;
    static String pr_body1 = null;
    static String pr_body2 = null;
    static String pr_body3 = null;
    static String pr_bodyend = null;
    static String pr_txtend1 = null;
    static String pr_txtins = null;
    static String pr_txtend3 = null;
    static String pr_txtaviso = null;
    static String pr_txtnoacc1 = null;
    static String pr_txtnoacc2 = null;
    static String pr_txtuserun = null;
    static String pr_admsubject = null;
    static String pr_admbody1 = null;
    static String pr_admbody2 = null;
    static String pr_admbody3 = null;
    static String pr_admbody4 = null;
    static String pr_admbody5 = null;
    static String pr_insrequerido = null;
    static String pr_insdesarrollo = null;
    static String pr_jsrequerido = null;
    static String pr_insliga = null;
    
    /*Documento xml*/
    Templates tpl;
    Templates plt;
    String workpath;
    String webpath;
    Transformer trans;
//    DocumentBuilderFactory dbuildf=null;
//    DocumentBuilder dbuild=null;
    String path = SWBPlatform.getContextPath() + "/swbadmin/xsl/MainSurvey/";
    
    String strRscType="MainSurvey";
    
    public String transformXML(Document dom)
    {

        java.io.ByteArrayOutputStream sw = new java.io.ByteArrayOutputStream();
        javax.xml.transform.Templates tpl=null;
        String xml=null;
        try
        {
            
            // xsl proprcionado por el usuario administrador
            if(null!=base.getAttribute("template"))
            {
                tpl=SWBUtils.XML.loadTemplateXSLT(SWBUtils.IO.getStreamFromString(SWBUtils.IO.getFileFromPath(SWBPortal.getWorkPath()+base.getWorkPath() + "/" + base.getAttribute("template").trim())));
                if(tpl==null){
                    System.out.println("XML:"+SWBUtils.XML.domToXml(dom));
                }
            }
            
            else
            {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/MainSurvey/MainSurvey.xsl"));
            }
            
        }
        catch(Exception e)
        {
            log.error("Error while trying to load XSLT style template MainSurvey.transformXML()",e);
        }
        
        if(tpl!=null)
        {
            try
            {
                javax.xml.transform.Transformer trans = tpl.newTransformer();
                
                // PASO DE PARAMETROS PARA EL XSLT
                
                //                        trans.setParameter("tableName",rmdt.getName().trim());
                //                        trans.setParameter("path",WBUtils.getInstance().getWebWorkPath()+base.getResourceWorkPath()+"/");
                
                // CODIFICACION DE LA TRANSFORMACION
                xml = SWBUtils.XML.domToXml(dom);
                trans.setOutputProperty("encoding", "ISO-8859-1");
                trans.transform(new javax.xml.transform.dom.DOMSource(SWBUtils.XML.xmlToDom(xml)), new javax.xml.transform.stream.StreamResult(sw));
            }
            catch(Exception ex)
            {
                log.error("Error while trying to tranform MainSurvey data.",ex);
            }
        }
        else return null;
        return sw.toString();
        
    }
    
    @Override
    public void setResourceBase(Resource base)
    {
        try {
            super.setResourceBase(base);
        } catch (Exception e) {
            log.error("Error while setting resource base: " + base.getId(), e);
        }
        this.base = base;
        String appRuth = null;
        String webpath = null;
        webpath = SWBPlatform.getContextPath();
        appRuth = SWBUtils.getApplicationPath();
        if(recproperties==null)
        {
            recproperties=new Properties();
            try
            {
                InputStream fptr = null;
                try
                {
                    fptr=SWBPortal.getAdminFileStream(SWBPlatform.getContextPath()+"/swbadmin/resources/MainSurvey/resource.properties");
                }
                catch(Exception e)
                {
                    log.error("Error while trying to load resource file, class - Mainsurvey, method - setResourceBase",e);
                }
                if(fptr!=null)
                {
                    recproperties.load(fptr);
                    pr_subject=recproperties.getProperty("msgtitulo");
                    pr_body1=recproperties.getProperty("msgtexto1");
                    pr_body2=recproperties.getProperty("msgtexto2");
                    pr_body3=recproperties.getProperty("msgtexto3");
                    pr_bodyend=recproperties.getProperty("msgsaludo");
                    pr_txtend1=recproperties.getProperty("txtfin1");
                    pr_txtins=recproperties.getProperty("txtins");
                    pr_txtend3=recproperties.getProperty("txtfin3");
                    pr_txtaviso=recproperties.getProperty("txtaviso");
                    pr_txtnoacc1=recproperties.getProperty("txtnoacceso1");
                    pr_txtnoacc2=recproperties.getProperty("txtnoacceso2");
                    pr_txtuserun=recproperties.getProperty("txtuserunknow");
                    pr_admsubject=recproperties.getProperty("msgadmtitulo");
                    pr_admbody1=recproperties.getProperty("msgadmtexto1");
                    pr_admbody2=recproperties.getProperty("msgadmtexto2");
                    pr_admbody3=recproperties.getProperty("msgadmtexto3");
                    pr_admbody4=recproperties.getProperty("msgadmtexto4");
                    pr_admbody5=recproperties.getProperty("msgadmtexto5");
                    pr_insrequerido=recproperties.getProperty("insrequerido");
                    pr_insdesarrollo=recproperties.getProperty("insdesarrollo");
                    pr_jsrequerido=recproperties.getProperty("jsrequerido");
                    pr_insliga=recproperties.getProperty("insliga");
                }
                else
                {
                    pr_subject=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgPruebaCorreoEncuesta");
                    pr_body1=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgEsteEmailPruebaUsuarioContestoEncuesta");
                    pr_body2=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgNosComunicaremosConUsted");
                    pr_body3=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgFecha");
                    pr_bodyend = SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgSaludos");
                    pr_txtend1=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgFormularioFinalizado");
                    pr_txtins=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgInstrucciones");
                    pr_txtend3=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgSaludos2");
                    pr_txtaviso=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgRecibiraCorreoElectronicoNosotros");
                    pr_txtnoacc1=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgLoSentimosYaContestadoFormulario");
                    pr_txtnoacc2=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgConsulteAdministrador");
                    pr_txtuserun=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgAnonimo");
                    pr_admsubject=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgHaContestadoFormulario");
                    pr_admbody1=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgElSiguienteUsuarioContestoFormulario");
                    pr_admbody2=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgEsteMensajeInformativo");
                    pr_admbody3=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgUbicadoEn");
                    pr_admbody4=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgUsuarioContesto");
                    pr_admbody5=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgFecha");
                    pr_insrequerido=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgPreguntasRequeridas");
                    pr_insdesarrollo=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgFormularioEnDesarrollo");
                    pr_jsrequerido=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgEsRequeridaPregunta");
                    pr_insliga=SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_MainSurvey_setResourceBase_msgIrFormulario");
                }
            }catch(Exception e)
            {
                log.error(e.getMessage(),e);
            }
        }
        
        try
        {
            super.setResourceBase(base);
            webpath = (String)SWBPlatform.getContextPath();
            workpath = (String)SWBPortal.getWebWorkPath() + base.getWorkPath();
        }
        catch(Exception e)
        {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(),e);
        }
        
        if(!"".equals(base.getAttribute("template","").trim()))
        {

            try
            {
                tpl=SWBUtils.XML.loadTemplateXSLT(SWBUtils.IO.getStreamFromString(SWBUtils.IO.getFileFromPath(SWBPortal.getWorkPath()+base.getWorkPath() + "/" + base.getAttribute("template").trim())));
                base.setAttribute("path", workpath + "/");
                trans = tpl.newTransformer();
            }
            catch(Exception e)
            {
                log.error("Error while loading resource template: "+base.getId(), e);
            }
        }
        
        // If there is not an xsl template then takes default template on this path - /wbadmin/xsl/MainSurvey/
        if(tpl == null)
        {
            try
            {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/MainSurvey/MainSurvey.xsl"));
                trans = tpl.newTransformer();
            }
            catch(Exception e)
            {
                log.error("Error while loading default resource template: "+base.getId(), e);
            }
        }
        if("".equals(base.getAttribute("path", "").trim())) base.setAttribute("path", webpath + "swbadmin/xsl/MainSurvey/");
        
//        /*Inicializa el documento xml*/
//        try
//        {
//            dbuildf=DocumentBuilderFactory.newInstance();
//            dbuild=dbuildf.newDocumentBuilder();
//        }
//        catch(Exception e)
//        {
//            log.error( "Error while trying to initialize XML object, class - Mainsurvey, method - setResourceBase",e);
//        }
    }
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        String idtm = base.getWebSiteId();
        PrintWriter out = null;
        StringBuffer ret = null;
        StringBuffer sourcetitle = null;
        ArrayList arr_questions = null;
        RecSurvey objSur = null;
        RecQuestion recQuestion = null;
        Utils util = null;
        HashMap hm_carga = null;
        HashMap hm_remaining = null;
        Hashtable ht_questions = null;
        WebPage topic = null;
        User user = null;
        
        String s_webpath = null;
        String aux_in = null;
        String aux_out = null;
        String s_responseid = null;
        String s_address = null;
        String displayquestions = null;
        String s_urlresponse = null;
        String s_marked_questions = null;
        String s_init = null;
        String s_isfinished = null;
        String s_remaining = null;
        String s_questionid = null;
        String s_move = null;
        String s_question = null;
        String s_queryrandom = null;
        String s_section = null;
        String s_val = null;
        String s_que = null;
        String s_sec = null;
        //String s_showpending = null;
        
        int status = 0;
        int showques = 0;
        int i_responsed = 0;
        int i_minute = 0;
        int i_calificacion = 0;
        int i_len = 0;
        int i_total = 0;
        int i_question = 0;
        
        long l_answerid = 0;
        long l_responseid = 0;
        long l_surveyid = 0;
        
        boolean b_access = false;
        
        /* XML */
        Document domxml=null;
        Element survey = null;
        Element resource = null;
        Element wellcome = null;
        Element finished = null;
        Element remaining = null;
        /* XML */
        
        try
        {
            /*Init objects*/
            out = response.getWriter();
            ret = new StringBuffer();
            topic = paramsRequest.getWebPage();
            user = paramsRequest.getUser();
            String usrid =user.getId()==null?"0":user.getId();
            //if(user!=null&&!user.isValid()) System.out.println("usr id:"+user.getId());
            util = new Utils();
            s_address = paramsRequest.getWebPage().getUrl();
            
            objSur = new RecSurvey();
            objSur.setIdtm(idtm);
            objSur.setResID(base.getId());
            objSur.load();
            l_surveyid = objSur.getSurveyID();
            i_minute = objSur.getTimeAnswer();
            //s_showpending = base.getAttribute("show_pending","0");
            s_responseid = (String)request.getSession().getAttribute("sr_responseid"+base.getId());
            
            /* XML */
            domxml = SWBUtils.XML.getNewDocument();
            resource = domxml.createElement("resource");
            domxml.appendChild(resource);
            survey = domxml.createElement("survey");
            resource.appendChild(survey);
            
            survey.setAttribute("address",s_address);
            survey.setAttribute("minScore",Integer.toString((int)java.lang.Math.round(objSur.getMinScore())));
            survey.setAttribute("timeAnswer",Integer.toString(objSur.getTimeAnswer()));
            survey.setAttribute("maxAnswer",Integer.toString(objSur.getMaxAnswer()));
            survey.setAttribute("type",Integer.toString(objSur.getTypeID()));
            survey.setAttribute("lastChance",base.getAttribute("lastchance","0"));
            survey.setAttribute("path",base.getAttribute("path",""));
            survey.setAttribute("title",base.getTitle());
            survey.setAttribute("description",base.getDescription());
            survey.setAttribute("instructions",base.getAttribute("instructions",""));
            
            
            /* XML */
            
            if(paramsRequest.getArguments().size()>0)
            {
                //**********************  Displays a link that refers to the survey  ***********************
                s_webpath = SWBPlatform.getContextPath()+SWBPlatform.getEnv("swb/distributor")+"/";
                //ret.append("<a href=\"" +s_webpath + base.getAttribute("url") +"\">" + pr_insliga + "</a>");
                
                /* xml */
                survey.setAttribute("page","link");
                survey.setAttribute("url",s_webpath + base.getAttribute("url"));
                /* xml */
                ret.append(transformXML(domxml));
            }
            else
            {
                status = Integer.parseInt(base.getAttribute("status","0"));
                if(status == 0)
                {
                    //************************   Access denied because the survey is on development status  ************
                    /* xml */
                    survey.setAttribute("page","develop");
                    /* xml */
                    ret.append(transformXML(domxml));
                }
                else
                {
                    // Means the survey is on line
                    sourcetitle = new StringBuffer();
                    showques = Integer.parseInt(base.getAttribute("showquestion","0"));
                    s_init = request.getParameter("wb_surveyinit");
                    s_remaining = request.getParameter("wb_remaining_question");
                    
                    // Displays the survey and check if exists a responseid
                    if(s_responseid == null&&user!=null&&user.isValid())
                    {
                        if(null!=user && !usrid.equals("0")) //!user.getId().substring(0,1).equals("0") //user.getId().equals("0")
                        {
                            // Gets a pending survey from this user
                            l_responseid = util.getPendingSurvey(usrid,base,objSur);
                            if(l_responseid != 0)
                            {
                                // If has pending survey restores session and continues with survey's flow
                                request.getSession().setAttribute("sr_responseid"+base.getId(),Long.toString(l_responseid));
                                s_responseid = (String)request.getSession().getAttribute("sr_responseid"+base.getId());
                            }
                        }
                    }
                    
                    if((s_init == null) && (s_responseid == null))
                    {
                        // Displays first page with a wellcome message
                        //****************** FIRST PAGE ***************
                        /* xml */
                        survey.setAttribute("page","wellcome");
                        wellcome = domxml.createElement("wellcome");
                        survey.appendChild(wellcome);
                        wellcome.setAttribute("surveyInit","");
                        /* xml */
                        ret.append(transformXML(domxml));
                    }
                    else 
                    {
                        hm_carga = util.cargaHM(l_surveyid,idtm);
                        // Evaluates if user is on session
                        if(s_responseid == null)
                        {
                            i_responsed = util.getReponseNumber(objSur, usrid,base);
                            
                            survey.setAttribute("availableTime", getAvailableTime(i_responsed));
                            
                            //Evaluates if user is allowed to do the survey
                            
                            if(objSur.getMaxAnswer() != 0 & objSur.getMaxAnswer() <= i_responsed)
                            {
                                //****************  SURVEY DENIED  ***********
                                survey.setAttribute("page","denied");
                                ret.append(transformXML(domxml));
                            }
                            else
                            {
                                //************** START SURVEY *************
                                // If user is beggining the exam, then all questions are created just once
                                if(s_init.equals("1"))
                                {
                                    // If does not exist questions, then generates questions randomly
                                    arr_questions = util.getRandomQuestions(showques, l_surveyid,idtm);
                                    l_responseid = util.startSurvey(paramsRequest,l_surveyid,arr_questions,idtm);
                                    request.getSession().setAttribute("sr_responseid"+base.getId(),Long.toString(l_responseid));
                                    s_responseid = (String)request.getSession().getAttribute("sr_responseid"+base.getId());
                                    s_queryrandom = util.getRandomQuery(l_responseid,idtm);
                                }
                                
                                if(s_question == null) s_question = "0";
                                i_question = Integer.parseInt(s_question) + 1;
                                
                                if(s_remaining == null) s_remaining = "0";
                                
                                // If the same user has two or more navigator windows
                                if(s_responseid == null)
                                {
                                    ret.append("<meta http-equiv=\"refresh\" content=\"0;url=\"" + s_address +"\">");
                                }
                                else
                                {
                                    //Displays first survey page
                                    /********************************* FIRST PAGE QUESTIONS **********************************/
                                    /* xml */
                                    survey.setAttribute("page","question");
                                    survey.setAttribute("required",pr_insrequerido);
                                    survey.setAttribute("move","");
                                    survey.setAttribute("markedQuestions","0");
                                    survey.setAttribute("surveyInit","0");
                                    survey.setAttribute("surveyQuestion",Integer.toString(i_question));
                                    survey.setAttribute("totalQuestion",Integer.toString(util.getTotalQuestions(Long.parseLong(s_responseid),idtm)));
                                    survey.setAttribute("availableTime", getAvailableTime(Long.parseLong(s_responseid)));
                                    /* xml */
                                    
                                    displayquestions = DisplayQuestions(request, response, s_address, domxml, survey, hm_carga, i_question);
                                    ret.append(displayquestions);
                                    ret.append(sourcetitle.toString());
                                }
                            }
                        }
                        // User is on session and continues with the survey
                        else
                        {
                            
                            survey.setAttribute("availableTime", getAvailableTime(Long.parseLong(s_responseid)));
                            
                            // Evaluates if user has decided to finish the exam
                            s_isfinished = request.getParameter("wb_isfinished");
                            if(s_isfinished == null) s_isfinished = "0";
                            s_question = request.getParameter("wb_survey_question");
                            if(s_question == null) s_question = "0";
                            
                            b_access = util.hasAccess(Long.parseLong(s_responseid),i_minute,s_isfinished,idtm);
                            if(b_access)
                            {
                                i_total = util.getTotalQuestions(Long.parseLong(s_responseid),idtm);
                                s_move = request.getParameter("wb_move");
                                
                                if(s_move == null) s_move = "1";
                                if(s_remaining == null) s_remaining = "0";
                                
                                if(s_move.equals("1"))
                                {
                                    // Gets current answer
                                    l_answerid = util.getCurrentAnswer(Long.parseLong(s_responseid),Integer.parseInt(s_question),idtm);
                                    // Evaluates if has previous question, "0" means that session was recovery and has not parameters
                                    if(l_answerid != 0)
                                    {
                                        // Saves previous question
                                        saveEvaluation(request, hm_carga, l_responseid);
                                    }
                                    i_question = Integer.parseInt(s_question) + 1;
                                }
                                else
                                {
                                    i_question = Integer.parseInt(s_question) - 1;
                                    if(i_question < 1)
                                    {
                                        i_question = 1;
                                    }
                                }
                                s_marked_questions = request.getParameter("wb_marked_questions");
                                if(s_marked_questions == null) s_marked_questions="0";
                                //Displays page with links to remaining questions
                                /********************************* REMAINING QUESTIONS **********************************/
                                if(s_marked_questions.equals("1"))
                                {
                                    /* xml */
                                    survey.setAttribute("page","remaining");
                                    remaining = domxml.createElement("remaining");
                                    survey.appendChild(remaining);
                                    remaining.setAttribute("surveyQuestion",Integer.toString(i_total + 1));
                                    remaining.setAttribute("move","");
                                    remaining.setAttribute("markedQuestions","0");
                                    remaining.setAttribute("isFinished","0");
                                    remaining.setAttribute("surveyInit","0");
                                    remaining.setAttribute("remainingQuestion","");
                                    
                                    /* xml */
                                    
                                    s_queryrandom = util.getRandomQuery(Long.parseLong(s_responseid),idtm);
                                    hm_remaining = util.getRemainingAnswer(Long.parseLong(s_responseid),idtm);
                                    ht_questions = util.getAllQuestions(objSur.getSurveyID(), s_queryrandom, hm_carga, Long.parseLong(s_responseid),idtm);
                                    
                                    if(hm_remaining.size() > 0)
                                    {
                                        remaining.setAttribute("hasRemaining","1");
                                        for(int i=0; i < hm_remaining.size();i++)
                                        {
                                            s_remaining =  (String)hm_remaining.get(Integer.toString(i));
                                            i_len = s_remaining.indexOf("|");
                                            s_questionid = s_remaining.substring(i_len + 1);
                                            s_remaining = s_remaining.substring(0,i_len);
                                            for(int j=0; j < ht_questions.size();j++)
                                            {
                                                s_val = (String)ht_questions.get(Integer.toString(j+1));
                                                StringTokenizer strToken = new StringTokenizer(s_val,"|");
                                                while(strToken.hasMoreTokens())
                                                {
                                                    strToken.nextToken();
                                                    s_que = strToken.nextToken();
                                                    s_sec = strToken.nextToken();
                                                }
                                                if(s_questionid.equals(s_que))
                                                {
                                                    s_section = s_sec;
                                                    break;
                                                }
                                            }
                                            recQuestion = new RecQuestion();
                                            recQuestion.setIdtm(idtm);
                                            recQuestion.setQuestionID(Integer.parseInt(s_questionid));
                                            recQuestion.load();
                                            
                                            Element remainQuestion = null;
                                            remainQuestion = domxml.createElement("remainQuestion");
                                            remaining.appendChild(remainQuestion);
                                            remainQuestion.setAttribute("opt",s_remaining);
                                            remainQuestion.setAttribute("id",Integer.toString((Integer.parseInt(s_remaining) + 1)));
                                            remainQuestion.setAttribute("sec",s_section);
                                            remainQuestion.appendChild(domxml.createTextNode(recQuestion.getQuestion()));
                                            recQuestion = null;
                                        }
                                    }
                                    else
                                    {
                                        remaining.setAttribute("hasRemaining","0");
                                    }
                                    remaining.setAttribute("surveySection",s_section);
                                    ret.append(transformXML(domxml));
                                }
                                // Keeps showing questions
                                else
                                {
                                    /* xml */
                                    survey.setAttribute("page","question");
                                    survey.setAttribute("required",pr_insrequerido);
                                    survey.setAttribute("move","");
                                    survey.setAttribute("markedQuestions","0");
                                    survey.setAttribute("surveyInit","0");
                                    survey.setAttribute("surveyQuestion",Integer.toString(i_question));
                                    survey.setAttribute("totalQuestion",Integer.toString(util.getTotalQuestions(Long.parseLong(s_responseid),idtm)));
                                    /* xml */
                                    
                                    displayquestions = DisplayQuestions(request, response, s_address, domxml, survey, hm_carga, i_question);
                                    ret.append(displayquestions);
                                    ret.append(sourcetitle.toString());
                                }
                            }
                            else
                            {
                                // User has decided to end the survey
                                /**************  FINISH SURVEY **********************/
                                
                                // Gets current answer
                                l_answerid = util.getCurrentAnswer(Long.parseLong(s_responseid),Integer.parseInt(s_question),idtm);
                                
                                // Evaluates if has previous question, "0" means that session was recovery and has not parameters
                                if(l_answerid != 0)
                                {
                                    // Saves previous question
                                    saveEvaluation(request, hm_carga, l_responseid);
                                }
                                
                                /*Asigna calificacion y cierra la encuesta*/
                                i_calificacion = setEvaluation(response, request, user, topic,paramsRequest);
                                
                                survey.setAttribute("page","finished");
                                finished = domxml.createElement("finished");
                                survey.appendChild(finished);
                                
                                /*Evalua si hay una direccion de redireccionamiento*/
                                s_urlresponse = base.getAttribute("url_response","0");
                                if(s_urlresponse.equals("")) s_urlresponse = "0";
                                survey.setAttribute("redirect",s_urlresponse);
                                
                                finished.setAttribute("id",s_responseid);
                                finished.setAttribute("score",Integer.toString(i_calificacion));
                                finished.setAttribute("message",base.getAttribute("agradecimiento",""));
                                finished.setAttribute("window",paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("PrintResult").toString());
                                if(base.getAttribute("printresult") == null)
                                {
                                    finished.setAttribute("print","0");
                                }
                                else
                                {
                                    finished.setAttribute("print","1");
                                }
                                if(i_calificacion >= objSur.getMinScore())
                                {
                                    finished.setAttribute("aprove","1");
                                }
                                else
                                {
                                    finished.setAttribute("aprove","0");
                                }
                                
                                
                                
                                ret.append(transformXML(domxml));
                                /*Elimina la variable de sesion*/
                                request.getSession().removeAttribute("sr_responseid"+base.getId());
                            }
                        }
                    }
                    hm_carga = null;
                }
            }
        }
        catch(Exception e)
        {
            log.error("Error while creating a survey, class - MainSurvey, method - DoView",e);
        }
        
        
        //System.out.println("responseid:"+i_responsed);
        
        aux_in = ret.toString();
        aux_out = aux_in.replaceAll("&lt;br&gt;","<br>");
        //System.out.println(AFUtils.getInstance().DomtoXml(domxml));
        out.println(aux_out);
    }
    
    public boolean isContinue(RecSurvey objSur, User user, HttpServletRequest request)
    {
        String idtm=base.getWebSiteId();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int onceans = 0;
        int responseid = 0;
        int anonim = 0;
        long surveyid = 0;
        boolean res = true;
        String email = null;
        String cookname = null;
        onceans = objSur.getMaxAnswer();
        if(onceans == 1)
        {
            anonim = Integer.parseInt(base.getAttribute("anonimo","0"));
            if(anonim == 0)
            {
                try
                {
                    surveyid = objSur.getSurveyID();
                    email = user.getEmail();
                    conn = SWBUtils.DB.getDefaultConnection();
                    st = conn.prepareStatement("select responseid from sr_responseuser where finished=1 and surveyid=? and wbuser=? and idtm=?");
                    st.setLong(1,surveyid);
                    st.setString(2,email);
                    st.setString(3,idtm);
                    rs = st.executeQuery();
                    if(rs.next())
                    {
                        responseid = rs.getInt("responseid");
                        res = false;
                    }
                    if(rs != null) rs.close();
                    if(st != null) st.close();
                    if(conn != null) conn.close();
                }
                catch (SQLException e)
                {
                    log.error(e.getMessage(),e);
                }
                finally
                {
                    rs = null;
                    st = null;
                    conn = null;
                }
                if(res)
                {
                    surveyid = objSur.getSurveyID();
                    cookname = "WBSurvey" + surveyid;
                    Cookie[] cookies = request.getCookies();
                    if (cookies!=null)
                    {
                        for(int i=0;i<cookies.length;i++)
                        {
                            if(cookies[i].getName().equalsIgnoreCase(cookname))
                            {
                                responseid = Integer.parseInt(cookies[i].getValue());
                                res = false;
                                break;
                            }
                        }
                    }
                    
                }
            }
            else
            {
                surveyid = objSur.getSurveyID();
                cookname = "WBSurvey" + surveyid;
                Cookie[] cookies = request.getCookies();
                if (cookies!=null)
                {
                    for(int i=0;i<cookies.length;i++)
                    {
                        if(cookies[i].getName().equalsIgnoreCase(cookname))
                        {
                            responseid = Integer.parseInt(cookies[i].getValue());
                            res = false;
                            break;
                        }
                    }
                }
            }
        }
        return res;
    }
    
    public String DisplayEnd(RecSurvey objSur, User user, String paddress, int pshowtype, Document domxml, Element xmlout)
    {
        Element xmlregard=null;
        Element xmldenied=null;
        
        xmlregard = domxml.createElement("regard");
        xmldenied = domxml.createElement("denied");
        
        if(pshowtype == 0)
        {
            Element xmlSdescription = domxml.createElement("description");
            Element xmlSmessage = domxml.createElement("message");
            Element xmlSmessageend = domxml.createElement("messageend");
            xmlSdescription.appendChild(domxml.createTextNode(base.getAttribute("agradecimiento","")));
            xmlregard.appendChild(xmlSdescription);
            if(user.isSigned() && base.getAttribute("anonimo","0").equals("0") && base.getAttribute("sendemail","0").equals("1"))
            {
                xmlSmessage.appendChild(domxml.createTextNode(pr_txtaviso));
                xmlregard.appendChild(xmlSmessage);
            }
            xmlSmessageend.appendChild(domxml.createTextNode(pr_txtend3));
            xmlregard.appendChild(xmlSmessageend);
            xmlSdescription = null;
            xmlSmessage = null;
            xmlSmessageend = null;
            xmlout.appendChild(xmlregard);
        }
        else
        {
            Element xmlSnoacc1 = domxml.createElement("text1");
            Element xmlSnoacc2 = domxml.createElement("text2");
            xmlSnoacc1.appendChild(domxml.createTextNode(pr_txtnoacc1));
            xmlSnoacc2.appendChild(domxml.createTextNode(pr_txtnoacc2));
            xmldenied.appendChild(xmlSnoacc1);
            xmldenied.appendChild(xmlSnoacc2);
            xmlSnoacc1 = null;
            xmlSnoacc2 = null;
            xmlout.appendChild(xmldenied);
        }
        return transformXML(domxml);
    }
    
    
    public String DisplayQuestions(HttpServletRequest request, HttpServletResponse response, String addr, Document domxml, Element survey, HashMap hmRelated, int p_questionid)
    {
        String idtm=base.getWebSiteId();
        StringBuffer sourcetitle = new StringBuffer();
        Utils util = null;
        Hashtable ht_sections = new Hashtable();
        Hashtable ht_questions = new Hashtable();
        ArrayList arrChild = null;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        RecSurvey objSur = null;
        RecSubject objSec = null;
        RecQuestion objQue = null;
        String s_sectionid = null;
        String s_back = null;
        String s_questionid = null;
        String isec= null;
        String strval = null;
        String mysec = null;
        String strquest = null;
        String responseid = null;
        String ast = null;
        String s_question = null;
        String description = null;
        String s_queryrandom = null;
        String s_remaining = null;
        String s_move = null;
        String s_showpending = null;
        
        long questionid = 0;
        long freqid = 0;
        long surveyid = 0;
        int controlid = 0;
        int required = 0;
        int validate = 0;
        int textid = 0;
        int display = 0;
        int prenum = 0;
        int ilen = 0;
        int i_pipe1 = 0;
        int i_pipe2 = 0;
        int i_showquestion = 0;
        int i_surveytype = 0;
        int i_sectionp = 0;
        int i_sectiont = 0;
        int i_question = 0;
        int i_total = 0;
        int i = 0;
        int i_section = 0;
        
        /*Preguntas hijas*/
        int childcontrolid = 0;
        int childrequired = 0;
        int childtextid = 0;
        int childvalidate = 0;
        long childfreqid = 0;
        String childquestionid = null;
        String childquestion = null;
        /*Preguntas hijas*/
        
        responseid = (String)request.getSession().getAttribute("sr_responseid"+base.getId());
        s_remaining = request.getParameter("wb_remaining_question");
        objSur = new RecSurvey();
        objSur.setIdtm(idtm);
        objSur.setResID(base.getId());
        objSur.load();
        util = new Utils();
        
        
        surveyid = objSur.getSurveyID();
        display = Integer.parseInt(base.getAttribute("showdisplay","0"));
        i_showquestion = Integer.parseInt(base.getAttribute("showquestion","0"));
        s_showpending = base.getAttribute("show_pending","0");
        s_queryrandom = util.getRandomQuery(Long.parseLong(responseid),idtm);
        ht_sections = objSur.getSubjects(s_queryrandom);
        ht_questions = util.getAllQuestions(surveyid,s_queryrandom,hmRelated,Long.parseLong(responseid),idtm);
        i_total = ht_questions.size();
        
        /*XML*/
        Element xmlquestion=null;
        Element xmlfreqanswerid=null;
        Element xmlfreqansweridSON=null;
        
        xmlquestion = domxml.createElement("question");
        xmlfreqanswerid = domxml.createElement("freqanswerid");
        xmlfreqansweridSON = domxml.createElement("freqansweridson");
        
        survey.setAttribute("surveyid",Long.toString(surveyid));
        survey.setAttribute("ins_text",pr_txtins);
        
        Element xmlSinstruction = domxml.createElement("instruction");
        xmlSinstruction.appendChild(domxml.createTextNode(base.getAttribute("instructions","")));
        survey.appendChild(xmlSinstruction);
        xmlSinstruction=null;
        Element xmlSrequired = domxml.createElement("required");
        xmlSrequired.appendChild(domxml.createTextNode(pr_insrequerido));
        survey.appendChild(xmlSrequired);
        xmlSrequired=null;
        
        Element xmlJSMCode = domxml.createElement("jsmaincode");
        Element xmlJSDCode = domxml.createElement("jsdatecode");
        
        Element xmlButtons = domxml.createElement("buttons");
        Element xmlHidden = domxml.createElement("hidden");
        /*XML*/
        
        switch(display)
        {
            case 1:  /********   Muestra la encuesta pregunta por pregunta   *********/
                strval =  (String)ht_questions.get(Integer.toString(p_questionid));
                if(strval!=null)
                {
                    i_pipe1 = strval.indexOf("|");
                    i_pipe2 = strval.lastIndexOf("|");
                    ilen = strval.length();
                    isec = strval.substring(0,i_pipe1);
                    strquest = strval.substring((i_pipe1+1),i_pipe2);
                    s_sectionid = strval.substring((i_pipe2+1),ilen);
                    
                    objSec = new RecSubject();
                    objSec.setIdtm(idtm);
                    objSec.setSubjectid(Long.parseLong(isec));
                    objSec.load();
                    
                    sourcetitle.append(getValidationCode());
                    sourcetitle.append("\n\nfunction DoSave(){");
                    sourcetitle.append("\n  if (!ValidateForm()){");
                    sourcetitle.append("\n      return;");
                    sourcetitle.append("\n  }");
                    sourcetitle.append("\n  else{");
                    sourcetitle.append("\n      window.document.frmSurvey.submit();");
                    sourcetitle.append("\n  }");
                    sourcetitle.append("\n}");
                    sourcetitle.append("\n\nfunction ValidateForm(){");
                    sourcetitle.append("\n  var isVal = true;");
                    try
                    {
                        /* XML */
                        survey.setAttribute("action",addr);
                        Element xmlque1 = null;
                        Element grupotmp1 =  domxml.createElement("group");
                        Element xmlGtitle1 = domxml.createElement("title");
                        xmlGtitle1.appendChild(domxml.createTextNode(SWBUtils.TEXT.encode(objSec.getTitle(),"ISO-8859-1")));
                        Element xmlGdescription1 = domxml.createElement("description");
                        xmlGdescription1.appendChild(domxml.createTextNode(SWBUtils.TEXT.encode(objSec.getDescription(),"ISO-8859-1")));
                        grupotmp1.setAttribute("id",s_sectionid);
                        grupotmp1.appendChild(xmlGtitle1);
                        grupotmp1.appendChild(xmlGdescription1);
                        xmlGtitle1=null;
                        xmlGdescription1=null;
                        /* XML */
                        
                        conn = SWBUtils.DB.getDefaultConnection();
                        rs=getQuestions(conn,st,rs,surveyid,Integer.parseInt(isec),Integer.parseInt(strquest),s_queryrandom);
                        while(rs.next())
                        {
                            prenum ++;
                            questionid = rs.getLong("questionid");
                            s_question = SWBUtils.IO.readInputStream(rs.getAsciiStream("question"));//rs.getString("question");
                            description = rs.getString("instruction");
                            freqid = rs.getLong("freqanswerid");
                            controlid = rs.getInt("controlid");
                            required = rs.getInt("required");
                            validate = rs.getInt("validated");
                            textid = rs.getInt("codeid");
                            
                            /* XML */
                            xmlque1=null;
                            Element xmlQtext1= domxml.createElement("text");
                            xmlQtext1.appendChild(domxml.createTextNode(s_question));
                            xmlquestion.setAttribute("numsec",Integer.toString(p_questionid));
                            xmlquestion.setAttribute("numque",Long.toString(questionid));
                            Element xmldes1;
                            xmldes1 = domxml.createElement("description");
                            xmldes1.appendChild(domxml.createTextNode(""));
                            /* XML */
                            
                            if(description != null)
                            {
                                xmldes1.getFirstChild().setNodeValue(description);
                            }
                            ast = "";
                            if(required == 1)
                            {
                                ast="*";
                                xmlquestion.setAttribute("required","1");
                            }
                            else
                            {
                                xmlquestion.setAttribute("required","0");
                            }
                            xmlque1 = (Element)xmlquestion.cloneNode(true);
                            
                            if(controlid == 3 | controlid == 4 | controlid == 5 | controlid == 6)
                            {
                                sourcetitle.append("\n  DoQuest" + questionid +"();");
                            }
                            if(required == 1)
                            {
                                sourcetitle.append(setValidationCode(controlid,questionid,p_questionid));
                            }
                            
                            xmlfreqanswerid = getFreqAnswersXML(freqid,questionid,controlid,required,responseid,validate,textid,domxml,0);
                            xmlque1.appendChild(xmlQtext1);
                            xmlque1.appendChild(xmldes1);
                            xmlque1.appendChild(xmlfreqanswerid);
                            
                            xmlque1.setAttribute("surveyQuestion",Integer.toString(p_questionid));
                            // Evaluates showing pending questions
                            if(s_showpending.equals("1"))
                            {
                                xmlque1.setAttribute("showpending","1");
                                if(util.getMarkAnswer(Long.parseLong(responseid),p_questionid,idtm) == 1)
                                {
                                    xmlque1.setAttribute("pending","1");
                                }
                                else
                                {
                                    xmlque1.setAttribute("pending","0");
                                }
                            }
                            else
                            {
                                xmlque1.setAttribute("showpending","0");
                            }
                            
                            /*preguntas relacionadas*/
                            arrChild = new ArrayList();
                            arrChild = getChilds(Long.toString(questionid),hmRelated,arrChild);
                            for(int j=0;j<arrChild.size();j++)
                            {
                                childquestionid = (String)arrChild.get(j);
                                objQue = new RecQuestion();
                                objQue.setIdtm(idtm);
                                objQue.setQuestionID(Integer.parseInt(childquestionid));
                                objQue.load();
                                childquestion = objQue.getQuestion();
                                childfreqid = objQue.getFreqAnswerID();
                                childcontrolid = Integer.parseInt(Long.toString(objQue.getControlID()));
                                childrequired = Integer.parseInt(Long.toString(objQue.getRequired()));
                                childtextid = Integer.parseInt(Long.toString(objQue.getCodeID()));
                                childvalidate = Integer.parseInt(Long.toString(objQue.getValidate()));
                                
                                /* XML */
                                Element xmlQSon1 = domxml.createElement("questionson");
                                Element xmlQtexts1= domxml.createElement("text");
                                xmlQtexts1.appendChild(domxml.createTextNode(childquestion));
                                xmlQSon1.setAttribute("numque",childquestionid);
                                Element xmldesQS1;
                                xmldesQS1 = domxml.createElement("description");
                                xmldesQS1.appendChild(domxml.createTextNode(""));
                                /* XML */
                                
                                if(objQue.getInstruction() != null)
                                {
                                    xmldesQS1.getFirstChild().setNodeValue(objQue.getInstruction());
                                }
                                ast = "";
                                if(childrequired == 1)
                                {
                                    ast="*";
                                    xmlQSon1.setAttribute("required","1");
                                }
                                else
                                {
                                    xmlQSon1.setAttribute("required","0");
                                }
                                
                                if(childcontrolid == 3 | childcontrolid == 4 | childcontrolid == 5 | childcontrolid == 6)
                                {
                                    sourcetitle.append("\n  DoQuest" + childquestionid +"();");
                                }
                                if(childrequired == 1)
                                {
                                    sourcetitle.append(setValidationCode(childcontrolid,Long.parseLong(childquestionid),prenum));
                                }
                                xmlfreqansweridSON.setAttribute("responseid",responseid);
                                xmlfreqansweridSON = getFreqAnswersXML(childfreqid,Long.parseLong(childquestionid),childcontrolid,childrequired,responseid,childvalidate,childtextid,domxml,1);
                                
                                xmlQSon1.appendChild(xmlQtexts1);
                                xmlQSon1.appendChild(xmldesQS1);
                                xmlQSon1.appendChild(xmlfreqansweridSON);
                                xmlque1.appendChild(xmlQSon1);
                                xmlQtexts1=null;
                                xmldesQS1=null;
                                xmlQSon1=null;
                            }
                            /*preguntas relacionadas*/
                            grupotmp1.appendChild(xmlque1);
                            xmlQtext1=null;
                            xmlque1=null;
                        }
                        s_questionid = Integer.toString(prenum);
                        survey.appendChild(grupotmp1);
                        grupotmp1=null;
                        
                        if(rs != null) rs.close();
                        if(st != null) st.close();
                        if(conn != null) conn.close();
                    }
                    catch(Exception e)
                    {
                        log.error(SWBUtils.TEXT.getLocaleString("locale_wb2_resources","errormsg_MainSurvey_DisplayQuestions_logErrorMainSurveyDisplayQuestionsCrearPregunta") + surveyid,e);
                    }
                    finally
                    {
                        rs = null;
                        st = null;
                        conn = null;
                    }
                    
                    /* XML */
                    xmlButtons = domxml.createElement("buttons");
                    
                    /* Hidden values*/
                    xmlHidden = domxml.createElement("hidden");
                    survey.appendChild(xmlHidden);
                    xmlHidden.setAttribute("backVisible","0");
                    xmlHidden.setAttribute("surveyQuestion",Integer.toString(p_questionid));
                    xmlHidden.setAttribute("surveyType","1");
                    xmlHidden.setAttribute("surveySection",s_sectionid);
                    
                    xmlHidden.setAttribute("surveySection", s_sectionid);
                    xmlHidden.setAttribute("secuential",s_questionid);
                    /* XML */
                    
                    if(p_questionid > 1)
                    {
                        xmlButtons.setAttribute("isBack","1");
                        xmlHidden.setAttribute("backVisible","1");
                    }
                    
                    if(p_questionid != i_total)
                    {
                        xmlButtons.setAttribute("isNext","1");
                    }
                    else
                    {
                        xmlButtons.setAttribute("isFinished","1");
                        xmlButtons.setAttribute("showPending",s_showpending);
                    }
                    
                    if(s_remaining != null)
                    {
                        xmlButtons.setAttribute("isRemaining","1");
                    }
                    else
                    {
                        xmlButtons.setAttribute("isRemaining","0");
                    }
                    
                    sourcetitle.append("\n  return isVal;");
                    sourcetitle.append("\n}");
                    
                    xmlJSDCode.setAttribute("src",SWBPlatform.getContextPath()+"swbadmin/js/calendar.js");
                    xmlJSMCode.appendChild(domxml.createTextNode(sourcetitle.toString()));
                    
                    /* XML */
                    Element xmlSact = domxml.createElement("act");
                    xmlSact.setAttribute("visible","1");
                    xmlSact.appendChild(domxml.createTextNode("add"));
                    survey.appendChild(xmlSact);
                    Element xmlSsection = domxml.createElement("section");
                    xmlSsection.setAttribute("visible","1");
                    xmlSsection.appendChild(domxml.createTextNode(s_sectionid));
                    survey.appendChild(xmlSsection);
                    Element xmlSsecuential = domxml.createElement("secuential");
                    xmlSsecuential.setAttribute("visible","1");
                    xmlSsecuential.appendChild(domxml.createTextNode(s_questionid));
                    
                    survey.appendChild(xmlSsecuential);
                    survey.appendChild(xmlButtons);
                    survey.appendChild(xmlJSDCode);
                    survey.appendChild(xmlJSMCode);
                    
                    xmlSact=null;
                    xmlSsection=null;
                    xmlSsecuential=null;
                    xmlJSDCode=null;
                    xmlJSMCode=null;
                    /* XML */
                    
                }
                break;
            case 2:   /*********   Muestra la encuesta grupo por grupo  ************/
                s_sectionid = request.getParameter("wb_section");
                s_questionid = request.getParameter("wb_secuential");
                s_move = request.getParameter("wb_move");
                if(s_move == null) s_move = "1";
                i_section = 0;
                
                if(s_sectionid == null)
                {
                    s_sectionid="0";
                    s_questionid = "0";
                }
                else
                {
                    if(s_move.equals("1"))
                    {
                        i_section = Integer.parseInt(s_sectionid) + 1;
                    }
                    else
                    {
                        s_back = "1";
                        i_section = Integer.parseInt(s_sectionid) - 1;
                        if(i_section < 1)
                        {
                            i_section = 0;
                        }
                    }
                }
                s_sectionid = Integer.toString(i_section);
                isec = (String)ht_sections.get(s_sectionid);
                
                xmlButtons = domxml.createElement("buttons");
                /* Hidden values*/
                xmlHidden = domxml.createElement("hidden");
                survey.appendChild(xmlHidden);
                xmlHidden.setAttribute("backVisible","0");
                xmlHidden.setAttribute("surveyQuestion",Integer.toString(p_questionid));
                xmlHidden.setAttribute("surveyType","2");
                
                xmlHidden.setAttribute("surveySection",s_sectionid);
                
                if(s_back != null)
                {
                    i_sectionp = (Integer.parseInt(s_sectionid) + 1);
                    for(i=0; i < i_total; i++)
                    {



                        strval =  (String)ht_questions.get(Integer.toString(i+1));
                        i_pipe2 = strval.lastIndexOf("|");
                        ilen = strval.length();
                        mysec = strval.substring((i_pipe2+1),ilen);
                        i_sectiont = Integer.parseInt(mysec);
                        if(i_sectionp == i_sectiont)
                        {
                            break;
                        }
                    }
                    s_questionid = Integer.toString(i);
                    xmlHidden.setAttribute("backVisible","1");
                }
                
                objSec = new RecSubject();
                objSec.setIdtm(idtm);
                objSec.setSubjectid(Long.parseLong(isec));
                objSec.load();
                sourcetitle.append(getValidationCode());
                sourcetitle.append("\n\nfunction ValidateForm(){");
                sourcetitle.append("\n  var isVal = true;");
                survey.setAttribute("action",addr);
                try
                {
                    /* XML */
                    Element xmlque2=null;
                    Element grupotmp2 = domxml.createElement("group");
                    Element xmlGtitle2 = domxml.createElement("title");
                    xmlGtitle2.appendChild(domxml.createTextNode(SWBUtils.TEXT.encode(objSec.getTitle(),"ISO-8859-1")));
                    Element xmlGdescription2 = domxml.createElement("description");
                    xmlGdescription2.appendChild(domxml.createTextNode(SWBUtils.TEXT.encode(objSec.getDescription(),"ISO-8859-1")));
                    grupotmp2.setAttribute("id",Integer.toString((Integer.parseInt(s_sectionid) + 1)));
                    grupotmp2.appendChild(xmlGtitle2);
                    grupotmp2.appendChild(xmlGdescription2);
                    xmlGtitle2=null;
                    xmlGdescription2=null;
                    survey.setAttribute("numsec",Integer.toString(prenum));
                    /* XML */
                    
                    conn = SWBUtils.DB.getDefaultConnection();
                    rs=getQuestions(conn,st,rs,surveyid,Integer.parseInt(isec),0,s_queryrandom);
                    prenum = Integer.parseInt(s_questionid);
                    while(rs.next())
                    {
                        if(!hmRelated.containsValue(Long.toString(rs.getLong("questionid"))))
                        {
                            prenum ++;
                            questionid = rs.getLong("questionid");
                            s_question = SWBUtils.IO.readInputStream(rs.getAsciiStream("question")); //rs.getString("question");
                            description = rs.getString("instruction");
                            freqid = rs.getLong("freqanswerid");
                            controlid = rs.getInt("controlid");
                            required = rs.getInt("required");
                            validate = rs.getInt("validated");
                            textid = rs.getInt("codeid");
                            ast = "";
                            /* XML */
                            xmlque2=null;
                            Element xmlQtext2= domxml.createElement("text");
                            xmlQtext2.appendChild(domxml.createTextNode(s_question));
                            xmlquestion.setAttribute("numsec",Integer.toString(prenum));
                            xmlquestion.setAttribute("numque",Long.toString(questionid));
                            Element xmldes2;
                            xmldes2 = domxml.createElement("description");
                            xmldes2.appendChild(domxml.createTextNode(""));
                            /* XML */
                            
                            if(description != null)
                            {
                                xmldes2.getFirstChild().setNodeValue(description);
                            }
                            if(required == 1)
                            {
                                ast="*";
                                xmlquestion.setAttribute("required","1");
                            }
                            else
                            {
                                xmlquestion.setAttribute("required","0");
                            }
                            xmlque2 = (Element)xmlquestion.cloneNode(true);
                            if(controlid == 3 | controlid == 4 | controlid == 5 | controlid == 6)
                            {
                                sourcetitle.append("\n  DoQuest" + questionid +"();");
                            }
                            if(required == 1)
                            {
                                sourcetitle.append(setValidationCode(controlid,questionid,prenum));
                            }
                            
                            /* XML */
                            xmlfreqanswerid = getFreqAnswersXML(freqid,questionid,controlid,required,responseid,validate,textid,domxml,0);
                            xmlque2.appendChild(xmlQtext2);
                            xmlque2.appendChild(xmldes2);
                            xmlque2.appendChild(xmlfreqanswerid);
                            /* XML */
                            
                            xmlque2.setAttribute("surveyQuestion",Integer.toString(prenum));
                            // Evaluates showing pending questions
                            if(s_showpending.equals("1"))
                            {
                                xmlque2.setAttribute("showpending","1");
                                if(util.getMarkAnswer(Long.parseLong(responseid),prenum,idtm) == 1)
                                {
                                    xmlque2.setAttribute("pending","1");
                                }
                                else
                                {
                                    xmlque2.setAttribute("pending","0");
                                }
                            }
                            else
                            {
                                xmlque2.setAttribute("showpending","0");
                            }
                            
                            /*preguntas relacionadas*/
                            arrChild = new ArrayList();
                            arrChild = getChilds(Long.toString(questionid),hmRelated,arrChild);
                            
                            for(int j=0;j<arrChild.size();j++)
                            {
                                childquestionid = (String)arrChild.get(j);
                                objQue = new RecQuestion();
                                objQue.setIdtm(idtm);
                                objQue.setQuestionID(Integer.parseInt(childquestionid));
                                objQue.load();
                                childquestion = objQue.getQuestion();
                                childfreqid = objQue.getFreqAnswerID();
                                childcontrolid = Integer.parseInt(Long.toString(objQue.getControlID()));
                                childrequired = Integer.parseInt(Long.toString(objQue.getRequired()));
                                childtextid = Integer.parseInt(Long.toString(objQue.getCodeID()));
                                childvalidate = Integer.parseInt(Long.toString(objQue.getValidate()));
                                
                                /* XML */
                                Element xmlQSon2 = domxml.createElement("questionson");
                                Element xmlQtexts2= domxml.createElement("text");
                                xmlQtexts2.appendChild(domxml.createTextNode(childquestion));
                                xmlQSon2.setAttribute("numque",childquestionid);
                                Element xmldesQS2;
                                xmldesQS2 = domxml.createElement("description");
                                xmldesQS2.appendChild(domxml.createTextNode(""));
                                /* XML */
                                
                                if(objQue.getInstruction() != null)
                                {
                                    xmldesQS2.getFirstChild().setNodeValue(objQue.getInstruction());
                                }
                                ast = "";
                                if(childrequired == 1)
                                {
                                    ast="*";
                                    xmlQSon2.setAttribute("required","1");
                                }
                                else
                                {
                                    xmlQSon2.setAttribute("required","0");
                                }
                                
                                if(childcontrolid == 3 | childcontrolid == 4 | childcontrolid == 5 | childcontrolid == 6)
                                {
                                    sourcetitle.append("\n  DoQuest" + childquestionid +"();");
                                }
                                if(childrequired == 1)
                                {
                                    sourcetitle.append(setValidationCode(childcontrolid,Long.parseLong(childquestionid),prenum));
                                }
                                
                                /* XML */
                                xmlfreqansweridSON = getFreqAnswersXML(childfreqid,Long.parseLong(childquestionid),childcontrolid,childrequired,responseid,childvalidate,childtextid,domxml,1);
                                xmlQSon2.appendChild(xmlQtexts2);
                                xmlQSon2.appendChild(xmldesQS2);
                                xmlQSon2.appendChild(xmlfreqansweridSON);
                                xmlque2.appendChild(xmlQSon2);
                                xmlQtexts2=null;
                                xmldesQS2=null;
                                xmlQSon2=null;
                                /* XML */
                            }
                            
                            grupotmp2.appendChild(xmlque2);
                            xmlQtext2=null;
                            xmlque2=null;
                        }
                    }
                    xmlHidden.setAttribute("surveySecuential",Integer.toString(prenum));
                    survey.appendChild(grupotmp2);
                    grupotmp2=null;
                    
                    if(rs != null) rs.close();
                    if(st != null) st.close();
                    if(conn != null) conn.close();
                }
                catch(Exception e)
                {
                    log.error(SWBUtils.TEXT.getLocaleString("locale_wb2_resources","errormsg_MainSurvey_DisplayQuestions_msgErrorMainSurveyDisplayQuestionsCrearPreguntaEncuesta") +" "+ surveyid,e);
                }
                finally
                {
                    rs = null;
                    st = null;
                    conn = null;
                }
                
                if(p_questionid > 1)
                {
                    xmlButtons.setAttribute("isBack","1");
                }
                if(prenum != i_total)
                {
                    xmlButtons.setAttribute("isNext","1");
                }
                else
                {
                    xmlButtons.setAttribute("isFinished","1");
                    xmlButtons.setAttribute("showPending",s_showpending);
                }
                
                if(s_remaining != null)
                {
                    xmlButtons.setAttribute("isRemaining","1");
                }
                else
                {
                    xmlButtons.setAttribute("isRemaining","0");
                }
                
                sourcetitle.append("\n  return isVal;");
                sourcetitle.append("\n}");
                
                xmlHidden.setAttribute("surveySection", s_sectionid);
                xmlHidden.setAttribute("secuential",s_questionid);
                
                xmlJSDCode.setAttribute("src",SWBPlatform.getContextPath()+"swbadmin/js/calendar.js");
                xmlJSMCode.appendChild(domxml.createTextNode(sourcetitle.toString()));
                
                /* XML */
                Element xmlSact = domxml.createElement("act");
                xmlSact.setAttribute("visible","1");
                xmlSact.appendChild(domxml.createTextNode("add"));
                survey.appendChild(xmlSact);
                Element xmlSsection = domxml.createElement("section");
                xmlSsection.setAttribute("visible","1");
                xmlSsection.appendChild(domxml.createTextNode(s_sectionid));
                survey.appendChild(xmlSsection);
                Element xmlSsecuential = domxml.createElement("secuential");
                xmlSsecuential.setAttribute("visible","1");
                xmlSsecuential.appendChild(domxml.createTextNode(s_questionid));
                survey.appendChild(xmlSsecuential);
                survey.appendChild(xmlButtons);
                survey.appendChild(xmlJSDCode);
                survey.appendChild(xmlJSMCode);
                
                xmlSact=null;
                xmlSsection=null;
                xmlSsecuential=null;
                xmlJSDCode=null;
                xmlJSMCode=null;
                xmlButtons = null;
                xmlHidden = null;
                /* XML */
                
                break;
            case 3:   /********    Muestra toda la encuesta en una p�gina    ************/
                ilen= ht_sections.size();
                int secnum = 0;
                sourcetitle.append(getValidationCode());
                sourcetitle.append("\n\nfunction ValidateForm(){");
                sourcetitle.append("\n  var isVal = true;");
                
                /* XML */
                survey.setAttribute("action",addr);
                survey.setAttribute("numsec",Integer.toString(prenum));
                xmlButtons = domxml.createElement("buttons");
                
                /* Hidden values*/
                xmlHidden = domxml.createElement("hidden");
                survey.appendChild(xmlHidden);
                xmlHidden.setAttribute("backVisible","0");
                xmlHidden.setAttribute("surveyQuestion",Integer.toString(p_questionid));
                xmlHidden.setAttribute("surveyType","3");
                xmlHidden.setAttribute("surveySection",s_sectionid);
                
                xmlHidden.setAttribute("surveySection", s_sectionid);
                xmlHidden.setAttribute("secuential",s_questionid);
                /* XML */
                
                for(i=0; i <ilen;i++)
                {
                    isec= (String)ht_sections.get(Integer.toString(i));
                    secnum = i+1;
                    objSec = new RecSubject();
                    objSec.setIdtm(idtm);
                    objSec.setSubjectid(Long.parseLong(isec));
                    objSec.load();
                    
                    /* XML */
                    Element xmlque=null;
                    Element grupotmp =  domxml.createElement("group");
                    Element xmlGtitle = domxml.createElement("title");
                    xmlGtitle.appendChild(domxml.createTextNode(objSec.getTitle()));
                    Element xmlGdescription = domxml.createElement("description");
                    xmlGdescription.appendChild(domxml.createTextNode(objSec.getDescription()));
                    grupotmp.setAttribute("id",Integer.toString(secnum));
                    grupotmp.appendChild(xmlGtitle);
                    grupotmp.appendChild(xmlGdescription);
                    xmlGtitle=null;
                    xmlGdescription=null;
                    /* XML */
                    
                    try
                    {
                        conn = SWBUtils.DB.getDefaultConnection();
                        rs=getQuestions(conn,st,rs,surveyid,Integer.parseInt(isec),0,s_queryrandom);
                        while(rs.next())
                        {
                            if(!hmRelated.containsValue(Long.toString(rs.getLong("questionid"))))
                            {
                                prenum ++;
                                questionid = rs.getLong("questionid");
                                s_question = SWBUtils.IO.readInputStream(rs.getAsciiStream("question")); //rs.getString("question");
                                description = rs.getString("instruction");
                                freqid = rs.getLong("freqanswerid");
                                controlid = rs.getInt("controlid");
                                required = rs.getInt("required");
                                validate = rs.getInt("validated");
                                textid = rs.getInt("codeid");
                                ast = "";
                                
                                /* XML */
                                Element xmlQtext= domxml.createElement("text");
                                xmlQtext.appendChild(domxml.createTextNode(s_question));
                                xmlquestion.setAttribute("numsec",Integer.toString(prenum));
                                xmlquestion.setAttribute("numque",Long.toString(questionid));
                                Element xmldes;
                                xmldes = domxml.createElement("description");
                                xmldes.appendChild(domxml.createTextNode(""));
                                /* XML */
                                
                                if(required == 1)
                                {
                                    ast="*";
                                    xmlquestion.setAttribute("required","1");
                                }
                                else
                                {
                                    xmlquestion.setAttribute("required","0");
                                }
                                xmlque = (Element)xmlquestion.cloneNode(true);
                                if(description != null)
                                {
                                    xmldes.getFirstChild().setNodeValue(description);
                                }
                                if(controlid == 3 | controlid == 4 | controlid == 5 | controlid == 6)
                                {
                                    sourcetitle.append("\n  DoQuest" + questionid +"();");
                                }
                                if(required == 1)
                                {
                                    sourcetitle.append(setValidationCode(controlid,questionid,prenum));
                                }
                                
                                /* XML */
                                xmlfreqanswerid = getFreqAnswersXML(freqid,questionid,controlid,required,responseid,validate,textid,domxml,0);
                                xmlque.appendChild(xmlQtext);
                                xmlque.appendChild(xmldes);
                                xmlque.appendChild(xmlfreqanswerid);
                                /* XML */
                                
                                xmlque.setAttribute("surveyQuestion",Integer.toString(p_questionid));
                                // Evaluates showing pending questions
                                if(s_showpending.equals("1"))
                                {
                                    xmlque.setAttribute("showpending","1");
                                    if(util.getMarkAnswer(Long.parseLong(responseid),prenum,idtm) == 1)
                                    {
                                        xmlque.setAttribute("pending","1");
                                    }
                                    else
                                    {
                                        xmlque.setAttribute("pending","0");
                                    }
                                }
                                else
                                {
                                    xmlque.setAttribute("showpending","0");
                                }
                                
                                /*preguntas relacionadas*/
                                arrChild = new ArrayList();
                                arrChild = getChilds(Long.toString(questionid),hmRelated,arrChild);
                                for(int j=0;j<arrChild.size();j++)
                                {
                                    childquestionid = (String)arrChild.get(j);
                                    objQue = new RecQuestion();
                                    objQue.setIdtm(idtm);
                                    objQue.setQuestionID(Integer.parseInt(childquestionid));
                                    objQue.load();
                                    
                                    childquestion = objQue.getQuestion();
                                    childfreqid = objQue.getFreqAnswerID();
                                    childcontrolid = Integer.parseInt(Long.toString(objQue.getControlID()));
                                    childrequired = Integer.parseInt(Long.toString(objQue.getRequired()));
                                    childtextid = Integer.parseInt(Long.toString(objQue.getCodeID()));
                                    childvalidate = Integer.parseInt(Long.toString(objQue.getValidate()));
                                    
                                    Element xmlQSon = domxml.createElement("questionson");
                                    Element xmlQtexts= domxml.createElement("text");
                                    xmlQtexts.appendChild(domxml.createTextNode(childquestion));
                                    xmlQSon.setAttribute("numque",childquestionid);
                                    Element xmldesQS;
                                    xmldesQS = domxml.createElement("description");
                                    xmldesQS.appendChild(domxml.createTextNode(""));
                                    
                                    if(objQue.getInstruction() != null)
                                    {
                                        xmldesQS.getFirstChild().setNodeValue(objQue.getInstruction());
                                    }
                                    ast = "";
                                    if(childrequired == 1)
                                    {
                                        ast="*";
                                        xmlQSon.setAttribute("required","1");
                                    }
                                    else
                                    {
                                        xmlQSon.setAttribute("required","0");
                                    }
                                    
                                    if(childcontrolid == 3 | childcontrolid == 4 | childcontrolid == 3 | childcontrolid == 6)
                                    {
                                        sourcetitle.append("\n  DoQuest" + childquestionid +"();");
                                    }
                                    if(childrequired == 1)
                                    {
                                        sourcetitle.append(setValidationCode(childcontrolid,Long.parseLong(childquestionid),prenum));
                                    }
                                    xmlfreqansweridSON = getFreqAnswersXML(childfreqid,Long.parseLong(childquestionid),childcontrolid,childrequired,responseid,childvalidate,childtextid,domxml,1);
                                    xmlQSon.appendChild(xmlQtexts);
                                    xmlQSon.appendChild(xmldesQS);
                                    xmlQSon.appendChild(xmlfreqansweridSON);
                                    xmlque.appendChild(xmlQSon);
                                    xmlQtexts=null;
                                    xmldesQS=null;
                                    xmlQSon=null;
                                }
                                grupotmp.appendChild(xmlque);
                                xmlQtext=null;
                                xmlque=null;
                                xmldes=null;
                            }
                        }
                        if(rs != null) rs.close();
                        if(st != null) st.close();
                        if(conn != null) conn.close();
                    }
                    catch(Exception e)
                    {
                        log.error(SWBUtils.TEXT.getLocaleString("locale_wb2_resources","errormsg_MainSurvey_DisplayQuestions_logErrorMainSurveyDisplayQuestionsCrearPreguntaEncuesta")+" " + surveyid,e);
                    }
                    finally
                    {
                        rs = null;
                        st = null;
                        conn = null;
                    }
                    survey.appendChild(grupotmp);
                    grupotmp=null;
                }
                sourcetitle.append("\n  return isVal;");
                sourcetitle.append("\n}");
                
                /* XML */
                xmlJSDCode.setAttribute("src",SWBPlatform.getContextPath()+"swbadmin/js/calendar.js");
                xmlJSMCode.appendChild(domxml.createTextNode(sourcetitle.toString()));
                
                
                xmlButtons.setAttribute("isFinished","1");
                xmlButtons.setAttribute("showPending",s_showpending);
                if(s_remaining != null)
                {
                    xmlButtons.setAttribute("isRemaining","1");
                }
                else
                {
                    xmlButtons.setAttribute("isRemaining","0");
                }
                
                survey.appendChild(xmlButtons);
                survey.appendChild(xmlJSDCode);
                survey.appendChild(xmlJSMCode);
                
                xmlJSMCode=null;
                xmlJSDCode=null;
                /* XML */
                
                break;
        }
        return transformXML(domxml);
    }
    
    
    public String setValidationCode(int pcontrolid, long pquestionid, int pprenum)
    {
        StringBuffer sourcerequired = new StringBuffer();
        String errmsg = "\n   alert(\""+ pr_jsrequerido + " " + pprenum +"!\");";
        if(pcontrolid == 1 | pcontrolid == 2 | pcontrolid == 7)
        {
            sourcerequired.append("\n  if(!TextRequired(window.document.frmSurvey.quest" + pquestionid +")){");
            sourcerequired.append("\n   isVal = false;");
            sourcerequired.append(errmsg);
            sourcerequired.append("\n   return isVal;");
            sourcerequired.append("\n   }");
        }
        if(pcontrolid == 3)
        {
            sourcerequired.append("\n  if(!CheckBoxRequired(window.document.frmSurvey.Squest" + pquestionid +")){");
            sourcerequired.append("\n   isVal = false;");
            sourcerequired.append(errmsg);
            sourcerequired.append("\n   return isVal;");
            sourcerequired.append("\n   }");
        }
        if(pcontrolid == 4)
        {
            sourcerequired.append("\n  if(!RadioRequired(window.document.frmSurvey.Squest" + pquestionid +")){");
            sourcerequired.append("\n   isVal = false;");
            sourcerequired.append(errmsg);
            sourcerequired.append("\n   return isVal;");
            sourcerequired.append("\n   }");
        }
        if(pcontrolid == 5)
        {
            sourcerequired.append("\n  if(!ComboMultipleRequired(window.document.frmSurvey.Squest" + pquestionid +")){");
            sourcerequired.append("\n   isVal = false;");
            sourcerequired.append(errmsg);
            sourcerequired.append("\n   return isVal;");
            sourcerequired.append("\n   }");
        }
        if(pcontrolid == 6)
        {
            sourcerequired.append("\n  if(!ComboRequired(window.document.frmSurvey.Squest" + pquestionid +")){");
            sourcerequired.append("\n   isVal = false;");
            sourcerequired.append(errmsg);
            sourcerequired.append("\n   return isVal;");
            sourcerequired.append("\n   }");
        }
        return sourcerequired.toString();
    }
    
    public String saveEvaluation(HttpServletRequest request, HashMap hm, long p_responseid) throws SWBResourceException
    {
        String idtm=base.getWebSiteId();
        Utils util = null;
        RecSurvey objSur = null;
        RecAnswer objAns = null;
        RecResponseUser objRes = null;
        DocumentBuilderFactory dbf=null;
        DocumentBuilder db=null;
        
        String resul=null;
        String s_responseid = null;
        String s_queryrandom = null;
        String s_mark = null;
        String s_questid = null;
        String s_value = null;
        
        int i_answered = 0;
        int i_mark = 0;
        int answerid = 0;
        int i_secaux = 0;
        int i_showdisplay = 0;
        long iresponse = 0;
        long sres = 0;
        long l_parent = 0;
        try
        {
            s_responseid = (String)request.getSession().getAttribute("sr_responseid"+base.getId());
            util = new Utils();
            objSur = new RecSurvey();
            objSur.setIdtm(idtm);
            objSur.setResID(base.getId());
            objSur.load();
            
            objRes = new RecResponseUser();
            objRes.setIdtm(idtm);
            objRes.setResponseID(Long.parseLong(s_responseid));
            objRes.load();
            iresponse = objRes.getResponseID();
            s_queryrandom = util.getRandomQuery(iresponse,idtm);
            Enumeration enume = request.getParameterNames();
            while(enume.hasMoreElements())
            {
                String key = (String)enume.nextElement();
                int keylen = key.length();
                if (keylen >=5)
                {
                    s_value = request.getParameter(key);
                    String realkey = key.substring(0,5);
                    if(realkey.equalsIgnoreCase("quest"))
                    {
                        s_questid = key.substring(5,keylen);
                        s_mark = request.getParameter("wb_mark"+s_questid);
                        try
                        {
                            dbf=DocumentBuilderFactory.newInstance();
                            db=dbf.newDocumentBuilder();
                            org.w3c.dom.Document  dom = db.newDocument();
                            Element resource = dom.createElement("resource");
                            Element opcion = dom.createElement("answer");
                            opcion.appendChild(dom.createTextNode(""));
                            dom.appendChild(resource);
                            String strOption = s_value;
                            i_answered = 0;
                            StringTokenizer strToken=new StringTokenizer(strOption,"|");
                            while(strToken.hasMoreTokens())
                            {
                                strOption=strToken.nextToken();
                                opcion.getFirstChild().setNodeValue(strOption);
                                Element newOp = (Element)opcion.cloneNode(true);
                                resource.appendChild(newOp);
                                i_answered = 1;
                            }
                            String strxml= null;
                            strxml = new String(SWBUtils.XML.domToXml(dom));
                            sres = objRes.getResponseID();
                            
                            l_parent = util.getParentQuestion(s_questid,objSur.getSurveyID(),idtm);
                            if(l_parent == 0)
                            {
                                if(s_mark == null)
                                {
                                    i_mark = 0;
                                }
                                else
                                {
                                    i_mark = 1;
                                }
                                if(i_answered == 0)
                                {
                                    i_mark = 1;
                                }
                            }
                            else
                            {
                                i_mark = 0;
                            }
                            answerid = util.getAnswerId(s_questid,Long.toString(sres),idtm);
                            if(answerid != 0)
                            {
                                objAns=new RecAnswer();
                                objAns.setIdtm(idtm);
                                objAns.setAnswerid(answerid);
                                objAns.load();
                                objAns.setStringxml(strxml);
                                objAns.setMark(i_mark);
                                objAns.update();
                                objAns = null;
                            }
                            else
                            {
                                objAns=new RecAnswer();
                                objAns.setIdtm(idtm);
                                objAns.setQuestionid(Long.parseLong(s_questid));
                                objAns.setResponseid((long)iresponse);
                                objAns.setStringxml(strxml);
                                objAns.create();
                            }
                        }
                        catch(Exception e)
                        {
                            log.error( "Error while trying to create an XML document, class - MainSurvey, method - saveEvaluation",e);
                        }
                    }
                }
            }
            
            i_showdisplay = Integer.parseInt(base.getAttribute("showdisplay","0"));
            switch(i_showdisplay)
            {
                case 1:
                    Hashtable htques = util.getAllQuestions(objSur.getSurveyID(),s_queryrandom,hm,Long.parseLong(s_responseid),idtm);
                    int htqueslen = htques.size();
                    String questionid = null;
                    questionid = request.getParameter("wb_survey_question");
                    int quesaux = Integer.parseInt(questionid);
                    if(htqueslen > quesaux)
                    {
                        resul="continue";
                    }
                    else
                    {
                        resul="finish";
                    }
                    break;
                case 2:
                    Hashtable htsec = objSur.getSubjects(s_queryrandom);
                    int htseclen = htsec.size();
                    String sectionid = null;
                    sectionid = request.getParameter("wb_section");
                    if(sectionid == null) sectionid = "0";
                    i_secaux = Integer.parseInt(sectionid) + 1;
                    if(htseclen > i_secaux)
                    {
                        sectionid = Integer.toString(i_secaux);
                        resul = "continue";
                    }
                    else
                    {
                        resul="finish";
                    }
                    break;
                case 3:
                    resul="finish";
                    break;
            }
            util = null;
        }
        catch(Exception e)
        {
            log.error("Error while saving a survey answer, class - MainSurvey, method - saveEvaluation",e);
        }
        return resul;
    }
    
    
    public int setEvaluation(HttpServletResponse response, HttpServletRequest request, User user, WebPage topic, SWBParamRequest paramsRequest) throws SWBResourceException
    {
        String idtm=base.getWebSiteId();
        String struseremail=null;
        String strresponseid = null;
        RecSurvey objSur = null;
        HashMap hmres = null;
        RecResponseUser objRes = null;
        RecQuestion objQue = null;
        RecAnswer objAns = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        int currnum = 0;
        int corrnum = 0;
        int wronnum = 0;
        int scornum = 0;
        int modenum = 0;
        int anonnum = 0;
        int id = 0;
        float calinum = 0;
        long controlid = 0;
        long iresponse = 0;
        long surveytype = 0;
        long surveyid = 0;
        int onceanswer = 0;
        
        try
        {
            objSur = new RecSurvey();
            objSur.setIdtm(idtm);
            objSur.setResID(base.getId());
            objSur.load();
            
            objRes = new RecResponseUser();
            objRes.setIdtm(idtm);
            modenum = Integer.parseInt(base.getAttribute("moderate","0"));
            anonnum = Integer.parseInt(base.getAttribute("anonimo","0"));
            calinum = objSur.getMinScore();
            surveyid = objSur.getSurveyID();
            surveytype = objSur.getTypeID();
            strresponseid = (String)request.getSession().getAttribute("sr_responseid"+base.getId());
            if(strresponseid == null)
            {
                struseremail = pr_txtuserun;
                if(anonnum == 0)
                {
                    if (user!=null && user.isSigned()) struseremail = user.getId();
                }
                objRes.setSurveyId(objSur.getSurveyID());
                objRes.setUser(struseremail);
                objRes.setStatistic(1);
                objRes.setComments("");
                objRes.create();
                iresponse = objRes.getResponseID();
                request.getSession().setAttribute("sr_responseid"+base.getId(),Long.toString(iresponse));
            }
            else
            {
                objRes.setResponseID(Long.parseLong(strresponseid));
                objRes.load();
                iresponse =(int)objRes.getResponseID();
            }
            
            con = SWBUtils.DB.getDefaultConnection();
            st = con.prepareStatement("select answerid, questionid, stringxml from sr_answer where responseid = ? and idtm=? order by answerid");
            st.setLong(1,iresponse);
            st.setString(2,idtm);
            rs = st.executeQuery();
            while(rs.next())
            {
                currnum++;
                if(surveytype == 2)
                {  // Califica el formulario
                    String answerid = rs.getString("answerid");
                    String questionid = rs.getString("questionid");
                    String strxmlans = SWBUtils.IO.readInputStream(rs.getAsciiStream("stringxml"));
                    objQue = new RecQuestion();
                    objQue.setIdtm(idtm);
                    objQue.setQuestionID(Integer.parseInt(questionid));
                    objQue.load();
                    controlid = objQue.getControlID();
                    // Evalua si el control no es de tipo texto de respuesta abierta
                    if (controlid > 2 & controlid < 7)
                    {
                        String strxmlque = objQue.getStringXML();
                        hmres = getTestResult(strxmlans,questionid,strxmlque,modenum,calinum);
                        objAns = new RecAnswer();
                        objAns.setIdtm(idtm);
                        objAns.setAnswerid(Long.parseLong(answerid));
                        objAns.load();
                        objAns.setScore(Integer.parseInt((String)hmres.get("score")));
                        objAns.update();
                        scornum = scornum + Integer.parseInt((String)hmres.get("score"));
                        corrnum = corrnum + Integer.parseInt((String)hmres.get("correct"));
                        wronnum = wronnum + Integer.parseInt((String)hmres.get("wrong"));
                    }
                }
            }
            if(rs != null) rs.close();
            if(st != null) st.close();
            if(con != null)con.close();
            if(currnum > 0)
            {
                float tes = scornum / currnum;
                scornum = java.lang.Math.round(tes);
            }
            
            objRes.setScore(scornum);
            objRes.setCorrectAnswer(corrnum);
            objRes.setWrongAnswer(wronnum);
            objRes.setCurrentQuestion(currnum);
            objRes.setFinished(1);
            objRes.update();
            /*Escribe la cookie*/
            onceanswer = objSur.getMaxAnswer();
            if(anonnum == 1 && onceanswer == 1)
            {
                Cookie add = null;
                add = new Cookie("WBSurvey" + surveyid,Long.toString(surveyid));
                add.setMaxAge(60*60*365);
                add.setPath("/");
                response.addCookie(add);
            }
            if(!user.isSigned())
            {  // No esta logeado, no es anonimo y solo se contesta una vez
                if(anonnum == 0 && onceanswer == 1)
                {
                    Cookie add = null;
                    add = new Cookie("WBSurvey" + surveyid,Long.toString(surveyid));
                    add.setMaxAge(60*60*365);
                    add.setPath("/");
                    response.addCookie(add);
                }
            }
            
            /*Envia el correo electronico al usuario*/
            sendEmail(user,objSur,0,topic,request,strresponseid,paramsRequest);
            /*Envia el correo electronico al administrador*/
            sendEmail(user,objSur,1,topic,request,strresponseid,paramsRequest);
        }
        catch(Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_wb2_resources","errormsg_MainSurvey_setEvaluation_logErrorMainsurveysetEvaluation") + e.getMessage(),e);
        }
        finally
        {
            rs = null;
            st = null;
            con = null;
        }
        return scornum;
    }
    
    
    public void sendEmail(User user, RecSurvey objSur, int admin, WebPage topic, HttpServletRequest request, String strresponseid, SWBParamRequest paramsRequest)
    {
        StringBuffer sbbody = new StringBuffer();
        WebSite tm = null;
        WebPage thome = null;
        String struseremail = null;
        String strsubject = null;
        String admemail = null;
        String strtime = null;
        String strtimeaux = null;
        String dns=null;
        Timestamp ttime = null;
        int anonnum = 0;
        int sendmail = 0;
        
        ttime = new Timestamp(new java.util.Date().getTime());
        strtimeaux = ttime.toString();
        strtime = strtimeaux.substring(0,16);
        anonnum = Integer.parseInt(base.getAttribute("anonimo","0")); //objSur.getAnonimo();
        try
        {
            if(admin == 0)
            {
                sendmail =  Integer.parseInt(base.getAttribute("sendemail","0")); //objSur.getSendEmail();
                if(user.isSigned())
                {
                    if (anonnum == 0 && sendmail == 1)
                    {
                        struseremail = user.getEmail();
                        tm =topic.getWebSite();
                        thome=tm.getHomePage();
                        
                        Iterator<Dns> ehtdns1 =topic.getWebSite().listDnses();
                        while(ehtdns1.hasNext())
                        {
                            Dns objRecDns=ehtdns1.next();
                            if(objRecDns.getWebSite().getId().equalsIgnoreCase(tm.getId()) && objRecDns.getWebPage().getId().equalsIgnoreCase(topic.getId()))
                            {
                                dns=objRecDns.getDns();
                                break;
                            }
                            if(objRecDns.getWebSite().getId().equalsIgnoreCase(tm.getId()) && objRecDns.getWebPage().getId().equalsIgnoreCase(thome.getId()))
                            {
                                dns=objRecDns.getDns();
                            }
                        }
                        if(dns==null)
                        {
                            ehtdns1 =SWBContext.getGlobalWebSite().listDnses();
                            while(ehtdns1.hasNext())
                            {
                                Dns objRecDns=ehtdns1.next();
                                if(objRecDns.getWebSite().getId().equalsIgnoreCase(tm.getId()) && objRecDns.getWebPage().getId().equalsIgnoreCase(topic.getId()))
                                {
                                    dns=objRecDns.getDns();
                                    break;
                                }
                                if(objRecDns.getWebSite().getId().equalsIgnoreCase(tm.getId()) && objRecDns.getWebPage().getId().equalsIgnoreCase(thome.getId()))
                                {
                                    dns=objRecDns.getDns();
                                }
                            }
                        }
                        if(dns == null) dns ="";
                        strsubject = pr_subject + " " + dns;
                        sbbody.append("\n<font face=verdana size=1>" + pr_body1);
                        sbbody.append("\n\n<br><br>" + pr_body2 + " <b><i>" + base.getTitle()+"</i></b>");
                        sbbody.append("\n<br>" + pr_body3 + " <b><i>" + strtime +"</i></b>");
                        sbbody.append("\n\n\n<br><br><br>" + pr_bodyend + "</font>");
                        SWBMail mail = new SWBMail();
                        mail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
                        mail.setSubject(strsubject);
                        mail.setContentType("text/html");
                        mail.setData(sbbody.toString());
                        mail.addAddress(struseremail);
                        SWBUtils.EMAIL.sendBGEmail(mail);
                        //SWBUtils.EMAIL.sendEmail(SWBPlatform.getEnv("af/adminEmail"),struseremail,null,null, strsubject, "text/html",0,sbbody.toString());
                    }
                }
            }
            else
            {
                
                admemail =  base.getAttribute("emailadmin","");
                if(!admemail.equalsIgnoreCase(""))
                {
                    struseremail = admemail;
                    strsubject = pr_admsubject;
                    sbbody.append("\n<font face=verdana size=1>" + pr_admbody1 + " <b>"+topic.getWebSite().getDisplayTitle(user.getLanguage())+"</b>");
                    sbbody.append("\n\n<br><br>" + pr_admbody2 + " <b><i>" + base.getTitle()+"</i></b>");
                    sbbody.append("\n<br>" + pr_admbody3 + " <b><i><a href=\""+ request.getRequestURL().toString()  +"\">" + topic.getDisplayName()+"</a></i></b>");
                    if(anonnum == 0)
                    {
                        if(user.isSigned())
                        {
                            sbbody.append("\n<br>" + pr_admbody4 + " <b><i>" + user.getFirstName() + " " + user.getLastName()+ "</i></b>, <a href=\"mailto:"+user.getEmail()+"\">"+user.getEmail()+"</a>");
                        }
                        else
                        {
                            sbbody.append("\n<br>" + pr_admbody4 + " " + pr_txtuserun );
                        }
                    }
                    else
                    {
                        sbbody.append("\n<br>" + pr_admbody4 + " " + pr_txtuserun);
                    }
                    
                    sbbody.append("\n<br>" + pr_admbody5 + " <b><i>" + strtime + "</i></b></font>");
                    Evaluation eval = new Evaluation();
                    eval.setResourceBase(base);
                    sbbody.append("\n<hr>" + eval.getEvaluationForEmail(strresponseid,topic.getUrl()+"/re_revisar",request,base.getId(),paramsRequest) + "<hr>");
                    SWBMail mail = new SWBMail();
                    mail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
                    mail.setSubject(strsubject);
                    mail.setContentType("text/html");
                    mail.setData(sbbody.toString());
                    mail.addAddress(struseremail);
                    SWBUtils.EMAIL.sendBGEmail(mail);
                    //SWBUtils.EMAIL.sendBGEmail(SWBPlatform.getEnv("af/adminEmail"),struseremail,null,null, strsubject, "text/html",0,sbbody.toString());
                }
            }
        }
        catch(Exception e)
        {
            log.error("Error while trying to send email to:" + struseremail + " class - MainSurvey, method - sendEmail",e);
        }
    }
    
    
    public HashMap getTestResult(String pxmlans,String pquestionid, String pxmlque, int pmoderate, float pcalificacion)
    {
        HashMap hmresult = new HashMap();
        HashMap hmque = new HashMap();
        org.w3c.dom.Document docxml;
        Element resource;
        int corrans = 0;
        int score = 0;
        int wronans = 0;
        int iresult = 0;
        try
        {
            // Obtiene los valores de la pregunta
            if (pxmlque != null)
            {
                docxml = SWBUtils.XML.xmlToDom(pxmlque);
                if (docxml!=null)
                {
                    resource = (Element) docxml.getFirstChild();
                    org.w3c.dom.NodeList nodes = resource.getChildNodes();
                    for (int i = 0; i < nodes.getLength(); i++)
                    {
                        org.w3c.dom.Node node = nodes.item(i);
                        String qvalue = node.getAttributes().getNamedItem("value").getNodeValue();
                        if (Integer.parseInt(qvalue) != 0)
                        {
                            String qrefer = node.getAttributes().getNamedItem("refer").getNodeValue();
                            hmque.put(qrefer,qvalue);
                        }
                    }
                }
                // Obtiene los valores de la respuesta
                if (pxmlans != null)
                {
                    docxml = SWBUtils.XML.xmlToDom(pxmlans);
                    if (docxml != null)
                    {
                        resource = (Element) docxml.getFirstChild();
                        org.w3c.dom.NodeList nodes = resource.getChildNodes();
                        int itot = 0;
                        int ilen = hmque.size();
                        for (int i = 0; i < nodes.getLength(); i++)
                        {
                            org.w3c.dom.Node node = nodes.item(i);
                            String val = node.getFirstChild().getNodeValue();
                            //Selecciona el tipo de evaluaci�n a realizar
                            if (pmoderate == 1)
                            {  // Es moderado el sistema al calificar
                                if (hmque.containsKey(val))
                                {
                                    String strval = (String)hmque.get(val);
                                    score = score + Integer.parseInt(strval);
                                    iresult = Integer.parseInt(strval);
                                }
                                else
                                {
                                    i = nodes.getLength();
                                    iresult = 0;
                                }
                            }
                            else
                            {  // Es estricto el sistema al calificar
                                if (hmque.containsKey(val))
                                {
                                    itot = itot + 1;
                                    if(itot == ilen)
                                    {
                                        iresult = 100;
                                        score = 100;
                                    }
                                }
                                else
                                {
                                    i = nodes.getLength();
                                    iresult = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            log.error( "Error, class - Mainsurvey, method - getTestResult",e);
        }
        if (iresult < pcalificacion)
        {
            corrans = 0;
            wronans = 1;
        }
        else
        {
            corrans = 1;
            wronans = 0;
        }
        hmresult.put("correct", Integer.toString(corrans));
        hmresult.put("wrong", Integer.toString(wronans));
        hmresult.put("score", Integer.toString(score));
        return hmresult;
    }
    
    
    
    public Element getFreqAnswersXML(long pfreqid, long pquestionid, int pcontrolid, int prequired, String presponseid, int pvalidate, int ptextid, Document domxml, int ison)
    {
        //StringBuffer ans = new StringBuffer();
        String idtm = base.getWebSiteId();
        StringBuffer source = new StringBuffer();
        Utils util = new Utils();
        org.w3c.dom.Document docxml;
        Element resource;
        RecFreqAnswer objFreq;
        HashMap hmans = new HashMap();
        Hashtable htsortq = new Hashtable();
        Hashtable htfreqa = new Hashtable();
        String strxml = null;
        String strvalue = null;
        String val = null;
        String att = null;
        String sel = null;
        int inum = 0;
        
        /* XML */
        Element xmlfreqanswer=null;
        if(ison == 0)
        {
            xmlfreqanswer = domxml.createElement("freqanswerid");
        }else
        {
            xmlfreqanswer = domxml.createElement("freqansweridson");
        }
        xmlfreqanswer.setAttribute("responseid",presponseid);
        xmlfreqanswer.setAttribute("controlid", Integer.toString(pcontrolid));
        xmlfreqanswer.setAttribute("faid", Long.toString(pfreqid));
        xmlfreqanswer.setAttribute("numque", Long.toString(pquestionid));
        
        switch(pcontrolid)
        {
            case 1:
                strvalue = getUserTextAnswer(Long.toString(pquestionid), presponseid);
                xmlfreqanswer.setAttribute("value",strvalue);
                xmlfreqanswer.setAttribute("validate","0");
                if(!getJSValidation(pvalidate,ptextid).equals(""))
                {
                    xmlfreqanswer.setAttribute("validate","1");
                }
                xmlfreqanswer.setAttribute("jsvalid",getJSValidation(pvalidate,ptextid));
                break;
            case 2:
                strvalue = getUserTextAnswer(Long.toString(pquestionid), presponseid);
                //ans.append("\n<tr><td>&nbsp;</td><td colspan=2 align=left><textarea rows=5 cols=50 id=\"quest" + pquestionid + "\" name=\"quest" + pquestionid + "\" onKeyPress=\"javascript: CheckChar()\"" + getJSValidation(pvalidate,ptextid) + ">" + strvalue + "</textarea></td></tr>");
                xmlfreqanswer.setAttribute("value",strvalue);
                xmlfreqanswer.setAttribute("validate","0");
                if(!getJSValidation(pvalidate,ptextid).equals(""))
                {
                    xmlfreqanswer.setAttribute("validate","1");
                }
                xmlfreqanswer.setAttribute("jsvalid",getJSValidation(pvalidate,ptextid));
                break;
            case 3:
                objFreq=new RecFreqAnswer();
                objFreq.setIdtm(idtm);
                objFreq.setFreqanswerid(pfreqid);
                objFreq.load();
                strxml = objFreq.getStringxml();
                if(strxml != null)
                {
                    try
                    {
                        source.append("\nfunction DoQuest" + pquestionid +"(){");
                        source.append("\n  var varquest" + pquestionid + "= \"\";");
                        docxml = SWBUtils.XML.xmlToDom(strxml);
                        if (docxml!=null)
                        {
                            hmans = util.getUserOptionAnswer(Long.toString(pquestionid), presponseid,idtm);
                            resource = (Element) docxml.getFirstChild();
                            org.w3c.dom.NodeList nodes = resource.getChildNodes();
                            // Regresa un hashtable con las opciones ordenadas de acuerdo a la tabla srquestion
                            htsortq = util.sortHashTable(Long.toString(pquestionid),idtm);
                            // Llena un hashtable con los valores de las opciones
                            for (int i = 0; i < nodes.getLength(); i++)
                            {
                                org.w3c.dom.Node node = nodes.item(i);
                                val = node.getFirstChild().getNodeValue();
                                att = node.getAttributes().getNamedItem("id").getNodeValue();
                                htfreqa.put(att,val);
                            }
                            // Hace el barrido del hashtable ordenado para escribir las opciones
                            for(int k=0; k < htsortq.size(); k++)
                            {
                                inum++;
                                att = (String) htsortq.get(Integer.toString(inum));
                                val = (String) htfreqa.get(att);
                                //ans.append("\n<tr><td>&nbsp;</td><td colspan=2 align=left><input type=\"checkbox\" id=\"Squest" + pquestionid  + "\" name=\"Squest" + pquestionid  +  "\" value=\"1\"");
                                Element opcionXML =  domxml.createElement("option");
                                opcionXML.appendChild(domxml.createTextNode(val));
                                opcionXML.setAttribute("numque", Long.toString(pquestionid));
                                opcionXML.setAttribute("numopt", att);
                                opcionXML.setAttribute("secopt", Integer.toString(inum));
                                sel = "0";
                                if(!hmans.isEmpty())
                                {
                                    for(int j=0; j < hmans.size(); j++)
                                    {
                                        strvalue = (String) hmans.get(Integer.toString(j));
                                        if(strvalue.equalsIgnoreCase(att))
                                        {
                                            //ans.append(" checked");
                                            sel = "1";
                                        }
                                    }
                                }
                                opcionXML.setAttribute("selected", sel);
                                //ans.append(">&nbsp;<font size=2 color=\"#666699\">"+inum+")&nbsp;" + val + "</font></td></tr>");
                                source.append("\n   if(window.document.frmSurvey.Squest" + pquestionid  + "[" + (inum -1) + "].checked == true){");
                                source.append("\n       varquest"  + pquestionid + "=  varquest" + pquestionid + " + \"" + att +"|\";");
                                source.append("\n   }");
                                
                                xmlfreqanswer.appendChild(opcionXML.cloneNode(true));
                                opcionXML=null;
                            }
                        }
                        source.append("\n       window.document.frmSurvey.quest" + pquestionid + ".value = varquest" + pquestionid +";");
                        source.append("\n}");
                        //ans.append(source.toString());
                        Element xmlFAJS = domxml.createElement("jscode");
                        xmlFAJS.appendChild(domxml.createTextNode(source.toString()));
                        xmlfreqanswer.appendChild(xmlFAJS.cloneNode(true));
                        xmlFAJS=null;
                    }
                    catch(Exception e)
                    {
                        log.error( "Error while trying to create answer set for check box, class - Mainsurvey, method - getFreqAnswersXML",e);
                    }
                }
                break;
            case 4:
                
                objFreq=new RecFreqAnswer();
                objFreq.setIdtm(idtm);
                objFreq.setFreqanswerid(pfreqid);
                objFreq.load();
                strxml = objFreq.getStringxml();
                if(strxml != null)
                {
                    try
                    {
                        source.append("\nfunction DoQuest" + pquestionid +"(){");
                        source.append("\n   var varquest" + pquestionid + "= \"\"");
                        source.append("\n   for (Contador=0;Contador<=(window.document.frmSurvey.Squest" + pquestionid  + ".length-1);Contador++){");
                        source.append("\n       if (window.document.frmSurvey.Squest" + pquestionid  + "[Contador].checked){");
                        source.append("\n           varquest" + pquestionid + "=Contador + 1;");
                        source.append("\n           break;");
                        source.append("\n       }");
                        source.append("\n   }");
                        source.append("\n       window.document.frmSurvey.quest" + pquestionid + ".value = varquest" + pquestionid +";");
                        //source.append("\n alert('valor de varquest' + varquest" + pquestionid + ");");
                        source.append("\n}");
                        docxml = SWBUtils.XML.xmlToDom(strxml);
                        if (docxml!=null)
                        {
                            hmans = util.getUserOptionAnswer(Long.toString(pquestionid), presponseid,idtm);
                            resource = (Element) docxml.getFirstChild();
                            org.w3c.dom.NodeList nodes = resource.getChildNodes();
                            // Regresa un hashtable con las opciones ordenadas de acuerdo a la tabla srquestion
                            htsortq = util.sortHashTable(Long.toString(pquestionid),idtm);
                            // Llena un hashtable con los valores de las opciones
                            for (int i = 0; i < nodes.getLength(); i++)
                            {
                                org.w3c.dom.Node node = nodes.item(i);
                                val = node.getFirstChild().getNodeValue();
                                att = node.getAttributes().getNamedItem("id").getNodeValue();
                                htfreqa.put(att,val);
                            }
                            // Hace el barrido del hashtable ordenado para escribir las opciones
                            for(int k=0; k < htsortq.size(); k++)
                            {
                                inum++;
                                att = (String) htsortq.get(Integer.toString(inum));
                                val = (String) htfreqa.get(att);
                                //ans.append("\n<tr><td>&nbsp;</td><td colspan=2 align=left><input type=\"radio\" id=\"quest" + pquestionid + "\" name=\"quest" + pquestionid + "\" value=\"" + att + "\"");
                                Element opcionXML =  domxml.createElement("option");
                                opcionXML.appendChild(domxml.createTextNode(val));
                                
                                opcionXML.setAttribute("numque", Long.toString(pquestionid));
                                opcionXML.setAttribute("numopt", att);
                                opcionXML.setAttribute("secopt", Integer.toString(inum));
                                opcionXML.setAttribute("selected", "0");
                                if(!hmans.isEmpty())
                                {
                                    for(int j=0; j < hmans.size(); j++)
                                    {
                                        strvalue = (String) hmans.get(Integer.toString(j));
                                        if(strvalue.equalsIgnoreCase(att))
                                        {
                                            //ans.append(" checked");
                                            opcionXML.setAttribute("selected", "1");
                                        }
                                        else
                                        {
                                            opcionXML.setAttribute("selected", "0");
                                        }
                                    }
                                }
                                //ans.append(">&nbsp;<font size=2 color=\"#666699\">"+inum+")&nbsp;" + val + "</font></td></tr>");
                                xmlfreqanswer.appendChild(opcionXML.cloneNode(true));
                                opcionXML=null;
                            }
                        }
                        Element xmlFAJS = domxml.createElement("jscode");
                        xmlFAJS.appendChild(domxml.createTextNode(source.toString()));
                        xmlfreqanswer.appendChild(xmlFAJS.cloneNode(true));
                        xmlFAJS=null;
                    }
                    catch(Exception e)
                    {
                        log.error( "Error while trying to create answer set for radio button, class - Mainsurvey, method - getFreqAnswersXML",e);
                    }
                }
                break;
            case 5:
                objFreq=new RecFreqAnswer();
                objFreq.setIdtm(idtm);
                objFreq.setFreqanswerid(pfreqid);
                objFreq.load();
                strxml = objFreq.getStringxml();
                if(strxml != null)
                {
                    try
                    {
                        docxml = SWBUtils.XML.xmlToDom(strxml);
                        if (docxml!=null)
                        {
                            hmans = util.getUserOptionAnswer(Long.toString(pquestionid), presponseid,idtm);
                            resource = (Element) docxml.getFirstChild();
                            org.w3c.dom.NodeList nodes = resource.getChildNodes();
                            // Regresa un hashtable con las opciones ordenadas de acuerdo a la tabla srquestion
                            htsortq = util.sortHashTable(Long.toString(pquestionid),idtm);
                            // Llena un hashtable con los valores de las opciones
                            for (int i = 0; i < nodes.getLength(); i++)
                            {
                                org.w3c.dom.Node node = nodes.item(i);
                                val = node.getFirstChild().getNodeValue();
                                att = node.getAttributes().getNamedItem("id").getNodeValue();
                                htfreqa.put(att,val);
                            }
                            //ans.append("\n<tr><td>&nbsp;</td><td colspan=2 align=left><select name=\"Squest" + pquestionid + "\" size=" + nodes.getLength() + " multiple>");
                            // Hace el barrido del hashtable ordenado para escribir las opciones
                            for(int k=0; k < htsortq.size(); k++)
                            {
                                inum++;
                                att = (String) htsortq.get(Integer.toString(inum));
                                val = (String) htfreqa.get(att);
                                //ans.append("\n<option value=\"" + att + "\"");
                                Element opcionXML =  domxml.createElement("option");
                                opcionXML.appendChild(domxml.createTextNode(val));
                                opcionXML.setAttribute("selected", "0");
                                opcionXML.setAttribute("numque", Long.toString(pquestionid));
                                opcionXML.setAttribute("numopt", att);
                                
                                if(!hmans.isEmpty())
                                {
                                    for(int j=0; j < hmans.size(); j++)
                                    {
                                        strvalue = (String) hmans.get(Integer.toString(j));
                                        if(strvalue.equalsIgnoreCase(att))
                                        {
                                            //ans.append(" selected");
                                            opcionXML.setAttribute("selected", "1");
                                        }
                                    }
                                }
                                //ans.append(">" + val + "</option>");
                                xmlfreqanswer.appendChild(opcionXML.cloneNode(true));
                                opcionXML=null;
                            }
                            //ans.append("\n</select></td></tr>");
                        }
                        //source.append("\n<script language=\"JavaScript\">");
                        source.append("\nfunction DoQuest" + pquestionid +"(){");
                        source.append("\n  var varquest" + pquestionid + "= \"\";");
                        source.append("\n   for(var i=0;i<window.document.frmSurvey.Squest" + pquestionid  + ".length;i++)");
                        source.append("\n   {");
                        source.append("\n      if(window.document.frmSurvey.Squest" + pquestionid  + "[i].selected){");
                        source.append("\n          varquest"  + pquestionid + "=  varquest" + pquestionid + " + window.document.frmSurvey.Squest" + pquestionid  + "[i].value + '|';");
                        source.append("\n      }");
                        source.append("\n   }");
                        source.append("\n   window.document.frmSurvey.quest" + pquestionid + ".value = varquest" + pquestionid +";");
                        source.append("\n}");
                        //source.append("\n</script>");
                        //ans.append(source.toString());
                        Element xmlFAJS = domxml.createElement("jscode");
                        xmlFAJS.appendChild(domxml.createTextNode(source.toString()));
                        xmlfreqanswer.appendChild(xmlFAJS.cloneNode(true));
                        xmlFAJS=null;
                    }
                    catch(Exception e)
                    {
                        log.error( "Error while trying to create answer set for select, class - Mainsurvey, method - getFreqAnswersXML",e);
                    }
                }
                break;
            case 6:
                objFreq=new RecFreqAnswer();
                objFreq.setIdtm(idtm);
                objFreq.setFreqanswerid(pfreqid);
                objFreq.load();
                strxml = objFreq.getStringxml();
                if(strxml != null)
                {
                    try
                    {
                        docxml = SWBUtils.XML.xmlToDom(strxml);
                        if (docxml!=null)
                        {
                            hmans = util.getUserOptionAnswer(Long.toString(pquestionid), presponseid,idtm);
                            resource = (Element) docxml.getFirstChild();
                            org.w3c.dom.NodeList nodes = resource.getChildNodes();
                            // Regresa un hashtable con las opciones ordenadas de acuerdo a la tabla srquestion
                            htsortq = util.sortHashTable(Long.toString(pquestionid),idtm);
                            // Llena un hashtable con los valores de las opciones
                            for (int i = 0; i < nodes.getLength(); i++)
                            {
                                org.w3c.dom.Node node = nodes.item(i);
                                val = node.getFirstChild().getNodeValue();
                                att = node.getAttributes().getNamedItem("id").getNodeValue();
                                htfreqa.put(att,val);
                            }
                            //ans.append("\n<tr><td>&nbsp;</td><td colspan=2 align=left><select name=\"quest" + pquestionid + "\">");
                            //ans.append("\n<option value=\"0\">-- Seleccione --</option>");
                            // Hace el barrido del hashtable ordenado para escribir las opciones
                            for(int k=0; k < htsortq.size(); k++)
                            {
                                inum++;
                                att = (String) htsortq.get(Integer.toString(inum));
                                val = (String) htfreqa.get(att);
                                //ans.append("\n<option value=\"" + att + "\"");
                                Element opcionXML =  domxml.createElement("option");
                                opcionXML.appendChild(domxml.createTextNode(val));
                                opcionXML.setAttribute("selected", "0");
                                opcionXML.setAttribute("numque", Long.toString(pquestionid));
                                opcionXML.setAttribute("numopt", att);
                                
                                if(!hmans.isEmpty())
                                {
                                    for(int j=0; j < hmans.size(); j++)
                                    {
                                        strvalue = (String) hmans.get(Integer.toString(j));
                                        if(strvalue.equalsIgnoreCase(att))
                                        {
                                            //ans.append(" selected");
                                            opcionXML.setAttribute("selected", "1");
                                        }
                                    }
                                }
                                //ans.append(">" + val + "</option>");
                                xmlfreqanswer.appendChild(opcionXML.cloneNode(true));
                                opcionXML=null;
                            }
                            //ans.append("\n</select></td></tr>");
                        }
                        source.append("\nfunction DoQuest" + pquestionid +"(){");
                        source.append("\n  var varquest" + pquestionid + "= \"\";");
                        source.append("\n   for(var i=0;i<window.document.frmSurvey.Squest" + pquestionid  + ".length;i++)");
                        source.append("\n   {");
                        source.append("\n       if(window.document.frmSurvey.Squest" + pquestionid  + "[i].selected){");
                        source.append("\n           if(window.document.frmSurvey.Squest" + pquestionid  + "[i].value > 0){");
                        source.append("\n               varquest"  + pquestionid + "= window.document.frmSurvey.Squest" + pquestionid  + "[i].value;");
                        source.append("\n           }");
                        source.append("\n       }");
                        source.append("\n   }");
                        source.append("\n   window.document.frmSurvey.quest" + pquestionid + ".value = varquest" + pquestionid +";");
                        //source.append("\n alert('valor de varquest = ' + varquest" + pquestionid + ");");
                        source.append("\n}");
                        Element xmlFAJS = domxml.createElement("jscode");
                        xmlFAJS.appendChild(domxml.createTextNode(source.toString()));
                        xmlfreqanswer.appendChild(xmlFAJS.cloneNode(true));
                        xmlFAJS=null;
                    }
                    catch(Exception e)
                    {
                        log.error( "Error while trying to create answer set for multiple select, class - Mainsurvey, method - getFreqAnswersXML",e);
                    }
                }
                break;
            case 7:
                strvalue = getUserTextAnswer(Long.toString(pquestionid), presponseid);
                //ans.append("\n<tr><td>&nbsp;</td><td colspan=2 align=left><input type=\"text\" name=\"quest" + pquestionid + "\" size=\"50\" value=\"" + strvalue + "\" onKeyPress=\"javascript: CheckChar()\"" + getJSValidation(pvalidate,ptextid) + "></td></tr>");
                //ans.append("\n<tr><td>&nbsp;</td><td colspan=2 align=left><input type=text name=\"quest" + pquestionid + "\" size=\"15\" value=\"" + strvalue + "\" readonly><a href=\"javascript:show_calendar('frmSurvey.quest" + pquestionid + "');\" onmouseover=\"window.status='Selecciona fecha';return true;\" onmouseout=\"window.status='';return true;\"><img src=\""+SWBPlatform.getContextPath()+"wbadmin/images/show-calendar.gif\" width=24 height=22 border=0></a>");
                xmlfreqanswer.setAttribute("value",strvalue);
                xmlfreqanswer.setAttribute("validate","0");
                xmlfreqanswer.setAttribute("img",SWBPlatform.getContextPath()+"wbadmin/images/show-calendar.gif");
                break;
        }
        return xmlfreqanswer;
    }
    
    
    public String getUserTextAnswer(String pquestionid, String presponseid)
    {
        String idtm=base.getWebSiteId();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String answer = "";
        String xmlans = null;
        String val = null;
        org.w3c.dom.Document docxml;
        Element resource;
        if(presponseid.equalsIgnoreCase("0"))
        {
            answer = "";
        }
        else
        {
            try
            {
                conn = SWBUtils.DB.getDefaultConnection();
                st = conn.prepareStatement("select stringxml from sr_answer where questionid=? and responseid =? and idtm=?");
                st.setLong(1,Long.parseLong(pquestionid));
                st.setLong(2,Long.parseLong(presponseid));
                st.setString(3,idtm);
                rs = st.executeQuery();
                if(rs.next())
                {
                    xmlans = rs.getString("stringxml");//SWBUtils.IO.readInputStream(rs.getAsciiStream("stringxml"));
                }
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(conn != null) conn.close();
                
                if(xmlans == null)
                {
                    answer = "";
                }
                else
                {
                    docxml = SWBUtils.XML.xmlToDom(xmlans);
                    if (docxml != null)
                    {
                        resource = (Element) docxml.getFirstChild();
                        org.w3c.dom.NodeList nodes = resource.getChildNodes();
                        val = "";
                        for (int i = 0; i < nodes.getLength(); i++)
                        {
                            org.w3c.dom.Node node = nodes.item(i);
                            if(node.getChildNodes().getLength() == 1)
                            {
                                val = node.getFirstChild().getNodeValue();
                            }
                            answer=val;
                        }
                    }
                }
            }
            catch(Exception e)
            {
                log.error( "Error while trying to load records from sr_answer, class - MainSurvey, method - getUserTextAnswer",e);
            }
            finally
            {
                rs = null;
                st = null;
                conn = null;
            }
        }
        return answer;
    }
    
    
    public ArrayList getChilds(String parent, HashMap hm, ArrayList arr)
    {
        if(hm.containsKey(parent))
        {
            arr.add(hm.get(parent));
            getChilds((String)hm.get(parent),hm,arr);
        }
        return arr;
    }
    
    
    public ResultSet getQuestions(Connection conn, PreparedStatement st, ResultSet rs, long psurveyid, int psectionid, long pquestionid, String pquery)
    {
        String idtm=base.getWebSiteId();
        StringBuffer strsql = new StringBuffer();
        String strson = null;
        Utils util = new Utils();
        strson = util.getChildQuestion(psurveyid,idtm);
        try
        {
            if(pquestionid != 0)
            {
                strsql.append("select sr_orderquestion.ordernum, sr_orderquestion.questionid, sr_question.question, sr_question.instruction, sr_question.freqanswerid, sr_question.controlid, sr_question.required, sr_question.validated, sr_question.codeid from sr_orderquestion, sr_question where sr_orderquestion.questionid =  sr_question.questionid ");
                if(!pquery.equalsIgnoreCase("all"))
                {
                    strsql.append(pquery);
                }
                else
                {
                    if(strson != null)
                    {
                        strsql.append(strson);
                    }
                    //las hijas
                }
                strsql.append(" and surveyid = ? and subjectid = ? and sr_question.questionid = ? and sr_question.idtm=? order by sr_orderquestion.ordernum, sr_orderquestion.questionid");
                
                st = conn.prepareStatement(strsql.toString());
                st.setLong(1,psurveyid);
                st.setInt(2,psectionid);
                st.setLong(3,pquestionid);
                st.setString(4,idtm);
            }
            else
            {
                strsql.append("select sr_orderquestion.ordernum, sr_orderquestion.questionid, sr_question.question, sr_question.instruction, sr_question.freqanswerid, sr_question.controlid, sr_question.required, sr_question.validated, sr_question.codeid from sr_orderquestion, sr_question where sr_orderquestion.questionid =  sr_question.questionid ");
                if(!pquery.equalsIgnoreCase("all"))
                {
                    strsql.append(pquery);
                }
                else
                {
                    if(strson != null)
                    {
                        strsql.append(strson);
                    }
                    //las hijas
                }
                strsql.append(" and surveyid = ? and subjectid = ? and sr_question.idtm=? order by sr_orderquestion.ordernum, sr_orderquestion.questionid");
                st = conn.prepareStatement(strsql.toString());
                st.setLong(1,psurveyid);
                st.setInt(2,psectionid);
                st.setString(3, idtm);
            }
            rs=st.executeQuery();
        }
        catch(Exception e)
        {
            log.error("Error while trying to load records from sr_orderquestion, sr_question, class - Mainsurvey, method - getQuestions",e);
        }
        return rs;
    }
    
    public String getJSValidation(int pvalidate, int ptextid)
    {
        String idtm=base.getWebSiteId();
        RecValidateCode objCat = null;
        String strfun = "";
        String strval = null;
        String strini = null;
        String strfin = null;
        int iini = 0;
        int ifin = 0;
        if(pvalidate == 1)
        {
            objCat = new RecValidateCode();
            objCat.setIdtm(idtm);
            objCat.setCodeid(ptextid);
            objCat.load();
            strval = objCat.getValidationcode();
            if(strval != null)
            {
                ifin = strval.indexOf("(");
                if(ifin != 0)
                {
                    strini = strval.substring(0,ifin);
                    iini = strini.lastIndexOf(" ");
                    strfin = strini.substring(iini + 1,ifin);
                    strfun = "javascript: " + strfin + "(this)";
                }
            }
        }
        return strfun;
    }
    
    
    public String getValidationCode()
    {
        String idtm=base.getWebSiteId();
        StringBuffer sourcetitle = new StringBuffer();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection();
            st = con.prepareStatement("select validationcode from sr_validatecode where idtm=?");
            st.setString(1,idtm);
            rs = st.executeQuery();
            while(rs.next())
            {
                sourcetitle.append("\n\n" + SWBUtils.IO.readInputStream(rs.getAsciiStream("validationcode")));
            }
            if(rs != null) rs.close();
            if(st != null) st.close();
            if(con != null) con.close();
        }
        catch(Exception e)
        {
            log.error("Error while trying to load records from sr_validatecode, class - Mainsurvey, method - getValidationCode",e);
        }
        finally
        {
            rs = null;
            st = null;
            con = null;
        }
        sourcetitle.append("\n\nfunction CheckChar(){");
        sourcetitle.append("\n  var key = window.event.keyCode;");
        sourcetitle.append("\n  if (key == 124){");
        sourcetitle.append("\n      window.event.returnValue = null;");
        sourcetitle.append("\n  }");
        sourcetitle.append("\n  else{");
        sourcetitle.append("\n      return;");
        sourcetitle.append("\n  }");
        sourcetitle.append("\n}");
        sourcetitle.append("\n\nfunction Open_Ins(url_ins,strargs){");
        sourcetitle.append("\n  window.open(url_ins,'WinIns',strargs);");
        sourcetitle.append("\n}");
        sourcetitle.append("\n\nfunction RadioRequired(fieldname){");
        sourcetitle.append("\n  var resul=false;");
        sourcetitle.append("\n  for (Contador=0;Contador<=(fieldname.length-1);Contador++)");
        sourcetitle.append("\n  {");
        sourcetitle.append("\n      if (fieldname[Contador].checked){");
        sourcetitle.append("\n          resul=fieldname[Contador].checked;");
        sourcetitle.append("\n          break;");
        sourcetitle.append("\n      }");
        sourcetitle.append("\n  }");
        sourcetitle.append("\n  return resul;");
        sourcetitle.append("\n}");
        sourcetitle.append("\n\nfunction CheckBoxRequired(fieldname){");
        sourcetitle.append("\n  var resul=false;");
        sourcetitle.append("\n  for (Contador=0;Contador<=(fieldname.length-1);Contador++)");
        sourcetitle.append("\n  {");
        sourcetitle.append("\n      if (fieldname[Contador].checked){");
        sourcetitle.append("\n          resul=fieldname[Contador].checked;");
        sourcetitle.append("\n          break;");
        sourcetitle.append("\n      }");
        sourcetitle.append("\n  }");
        sourcetitle.append("\n  return resul;");
        sourcetitle.append("\n}");
        sourcetitle.append("\n\nfunction ComboRequired(fieldname){");
        sourcetitle.append("\n  if (fieldname.selectedIndex == 0 ){");
        sourcetitle.append("\n      fieldname.focus();");
        sourcetitle.append("\n      return false;");
        sourcetitle.append("\n  }");
        sourcetitle.append("\n  return true;");
        sourcetitle.append("\n}");
        sourcetitle.append("\n\nfunction ComboMultipleRequired(fieldname){");
        sourcetitle.append("\n  var resul = false;");
        sourcetitle.append("\n  for(var i=0;i<fieldname.length;i++)");
        sourcetitle.append("\n  {");
        sourcetitle.append("\n      if(fieldname[i].selected){");
        sourcetitle.append("\n          resul = true;");
        sourcetitle.append("\n      }");
        sourcetitle.append("\n  }");
        sourcetitle.append("\n  return resul;");
        sourcetitle.append("\n}");
        sourcetitle.append("\n\nfunction TextRequired(fieldname){");
        sourcetitle.append("\n  trim(fieldname);");
        sourcetitle.append("\n  if (fieldname.value.length <= 0){");
        sourcetitle.append("\n      fieldname.focus();");
        sourcetitle.append("\n      fieldname.select()");
        sourcetitle.append("\n      return false;");
        sourcetitle.append("\n  }");
        sourcetitle.append("\n  else{");
        sourcetitle.append("\n      return true;");
        sourcetitle.append("\n  }");
        sourcetitle.append("\n}");
        sourcetitle.append("\n\nfunction trim(fieldname){");
        sourcetitle.append("\n  var retVal = \"\";");
        sourcetitle.append("\n  var start = 0;");
        sourcetitle.append("\n  while ((start < fieldname.value.length) && (fieldname.value.charAt(start) == ' ')){");
        sourcetitle.append("\n      ++start;");
        sourcetitle.append("\n  }");
        sourcetitle.append("\n  var end = fieldname.value.length;");
        sourcetitle.append("\n  while ((end > 0) && (fieldname.value.charAt(end - 1) == ' ')){");
        sourcetitle.append("\n      --end;");
        sourcetitle.append("\n  }");
        sourcetitle.append("\n  retVal = fieldname.value.substring(start, end);");
        sourcetitle.append("\n  if (end == 0){");
        sourcetitle.append("\n      fieldname.value=\"\";");
        sourcetitle.append("\n  }");
        sourcetitle.append("\n  else{");
        sourcetitle.append("\n      fieldname.value=retVal;");
        sourcetitle.append("\n  }");
        sourcetitle.append("\n}");
        return sourcetitle.toString();
    }
    
    
    /**
     *
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        String idtm=base.getWebSiteId();
        PrintWriter out = response.getWriter();
        String accion = paramsRequest.getAction();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        //String webpath=(String)SWBPlatform.getContextPath();
        User user = paramsRequest.getUser();
        //SWBResourceURL url = paramsRequest.getRenderUrl();
        
        StringBuffer ret = new StringBuffer();
        if(request.getParameter("files")!=null&&request.getParameter("files").equals("1"))
        {
            if(request.getParameter("applet")!=null) ret.append(request.getParameter("applet"));
        }
        else
        {
            
            
            boolean flag = true;
            if (request.getParameter("content_type") != null)
            {
                if (request.getParameter("content_type").equals("excel"))flag=false;
            }
            
            if(null!=request.getParameter("regreso"))
                ret.append("<html><body>");
            
            if(flag&&!accion.equals("re_reporte"))
            {
                ret.append("\n<div class=\"swbform\" >");
            }
            
            
            String idres = base.getId();
            RecSurvey oRS = new RecSurvey();
            oRS.setIdtm(idtm);
            oRS.setResID(idres);
            oRS.load();
            
            if(oRS.getSurveyID()==0&&accion.equals("edit"))accion="add";
            
            if (request.getParameter("regreso")!=null)
            {
                request.getSession().setAttribute("regreso",request.getParameter("regreso"));
            }
            
            if (request.getParameter("retq")!=null)
            {
                request.getSession().setAttribute("retq",request.getParameter("retq"));
            }
            
            if(accion.equals("selectquestion_en") && request.getSession().getAttribute("retq")!=null)
            {
                request.getSession().setAttribute("retq",null);
            }
            
            
            
            if(request.getSession().getAttribute("regreso")==null && request.getSession().getAttribute("retq")==null&&flag&&!accion.equals("re_reporte")&&!accion.equals("re_email")&&!accion.equals("re_enviaemail")&&!accion.equals("re_borrarformulario"))
            {
                ret.append("\n<fieldset>");
                ret.append("\n<legend>Datos</legend>");
                ret.append("<table cellspacing=0 cellpadding=5 width=100%>");
                ret.append("<tr><td colspan=2 >"+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgFormulario")+"</td></tr>");
                ret.append("<tr><td colspan=2 >");
                
                if(!accion.equals("edit")&&!accion.equals("add")&&!accion.equals("encuestas") && !accion.equals("reportes") && !accion.equals("update_en") && !accion.equals("updategroup_en") && !accion.equals("add_en") && !accion.equals("agregar_en") && !accion.equals("select_en") && !accion.equals("delete_en") && !accion.equals("deleteselect_en")  && !accion.equals("edit") && !accion.equals("selectquestion_en")&&!accion.equals("orderupdate_en")&&!accion.equals("orderaddXML_en")&&!accion.equals("add_s")&&!accion.equals("_add_p")&&!accion.equals("re_formulario")&&!accion.equals("_re_general")&&!accion.equals("re_consulta")&&!accion.equals("re_reporte")&&!accion.equals("reGeneral")&&!accion.equals("re_revisar")&&!accion.equals("re_email")&&!accion.equals("re_enviaemail")&&!accion.equals("re_borrarformulario"))
                {
                    SWBResourceURL urlga = paramsRequest.getRenderUrl();
                    urlga.setMode(SWBResourceURL.Mode_ADMIN);
                    urlga.setAction("gruposa");
                    
                    SWBResourceURL urlgq = paramsRequest.getRenderUrl();
                    urlgq.setMode(SWBResourceURL.Mode_ADMIN);
                    urlgq.setAction("gruposq");
                    
                    SWBResourceURL urlq = paramsRequest.getRenderUrl();
                    urlq.setMode(SWBResourceURL.Mode_ADMIN);
                    urlq.setAction("select_p"); //select_p  preguntas
                    
                    SWBResourceURL urlr = paramsRequest.getRenderUrl();
                    urlr.setMode(SWBResourceURL.Mode_ADMIN);
                    urlr.setAction("respuestas");
                    
                    SWBResourceURL urlt = paramsRequest.getRenderUrl();
                    urlt.setMode(SWBResourceURL.Mode_ADMIN);
                    //urlt.setAction("tipos");
                    urlt.setAction("select_ct");
                    
                    SWBResourceURL urls = paramsRequest.getRenderUrl();
                    urls.setMode(SWBResourceURL.Mode_ADMIN);
                    urls.setAction("select_s"); //secciones
                    
                    SWBResourceURL urlg = paramsRequest.getRenderUrl();
                    urlg.setMode(SWBResourceURL.Mode_ADMIN);
                    urlg.setAction("re_general");
                    
                    SWBResourceURL urlup = paramsRequest.getRenderUrl();
                    urlup.setMode(SWBResourceURL.Mode_ADMIN);
                    urlup.setAction("update_en");
                    
                    if(accion.equals("gruposa")||accion.equals("update_ga")||accion.equals("edit_ga")||accion.equals("agregar_ga")||accion.equals("modificar_ga")||accion.equals("eliminar_ga")) ret.append(""+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgCategoriasRespuestas")+"&nbsp|&nbsp;");
                    else ret.append("<a  href=\""+urlga+"\">"+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgCategoriasRespuestas")+"</a>&nbsp|&nbsp;");
                    if(accion.equals("gruposq")||accion.equals("update_gq")||accion.equals("edit_gq")||accion.equals("agregar_gq")||accion.equals("modificar_gq")||accion.equals("eliminar_gq")) ret.append(""+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgCategoriasPreguntas")+"&nbsp|&nbsp;");
                    else ret.append("<a  href=\""+urlgq+"\">"+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgCategoriasPreguntas")+"</a>&nbsp|&nbsp;");
                    if(accion.equals("preguntas")||accion.equals("update_p")||accion.equals("add_p")||accion.equals("agregar_p")||accion.equals("delete_p")||accion.equals("deleteselect_p")||accion.equals("updatequestion_p")||accion.equals("select_p")) ret.append(""+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgPreguntas")+"&nbsp|&nbsp;");
                    else ret.append("<a  href=\""+urlq+"\">"+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgPreguntas")+"</a>&nbsp|&nbsp;");
                    if(accion.equals("respuestas")||accion.equals("update_fa")||accion.equals("edit_fa")||accion.equals("agregar_fa")||accion.equals("modificar_fa")||accion.equals("eliminar_fa")||accion.equals("result_fa")) ret.append(""+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgRespuestas")+"&nbsp|&nbsp;");
                    else ret.append("<a  href=\""+urlr+"\">"+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgRespuestas")+"</a>&nbsp|&nbsp;");
                    if(accion.equals("tipos")||accion.equals("update_ct")||accion.equals("add_ct")||accion.equals("agregar_ct")||accion.equals("delete_ct")||accion.equals("deleteselect_ct")||accion.equals("updatecatalogtype_ct")||accion.equals("select_ct"))  ret.append(""+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgTiposValidacion")+"&nbsp|&nbsp;");
                    else ret.append("<a  href=\""+urlt+"\">"+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgTiposValidacion")+"</a>&nbsp|&nbsp;");
                    if(accion.equals("secciones")||accion.equals("update_s")||accion.equals("add_s")||accion.equals("agregar_s")||accion.equals("delete_s")||accion.equals("deleteselect_s")||accion.equals("updatesection_s")||accion.equals("select_s")) ret.append(""+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgGrupos")+"&nbsp|&nbsp;");
                    else ret.append("<a  href=\""+urls+"\">"+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgGrupos")+"</a>&nbsp|&nbsp;");
                    if(accion.startsWith("re_")) ret.append(""+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgReportesGenerales")+"");
                    else ret.append("<a  href=\""+urlg+"\">"+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgReportesGenerales")+"</a>");
                    ret.append("&nbsp|&nbsp;<a  href=\""+urlup+"\">"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgRegresar")+"</a>");
                }
                ret.append("</td></tr>");
                ret.append("</table>");
                ret.append("\n</fieldset>");
            }
            
            if(accion.equals("edit")||accion.equals("add")||accion.equals("encuestas")||accion.equals("update_en")||accion.equals("updategroup_en")||accion.equals("add_en")||accion.equals("agregar_en")||accion.equals("select_en")||accion.equals("delete_en")||accion.equals("deleteselect_en")||accion.equals("selectquestion_en")||accion.equals("orderupdate_en")||accion.equals("orderaddXML_en"))
            {
                Survey oS = new Survey();
                oS.setResourceBase(base);
                String tempAction = new String(accion.toString());
                
                if(accion.equals("add") )
                {
                    tempAction = "add_en";
                }
                
                if(accion.equals("edit") )
                {
                    tempAction = "update_en";
                }
                ret.append(oS.doAdmin(request, response, paramsRequest, tempAction));
            }
            
            if(accion.equals("gruposa")||accion.equals("update_ga")||accion.equals("edit_ga")||accion.equals("agregar_ga")||accion.equals("modificar_ga")||accion.equals("eliminar_ga"))
            {
                GroupAnswer oGA = new GroupAnswer();
                oGA.setResourceBase(base);
                ret.append(oGA.doAdmin(request, response, paramsRequest));
            }
            
            if(accion.equals("gruposq")||accion.equals("update_gq")||accion.equals("edit_gq")||accion.equals("agregar_gq")||accion.equals("modificar_gq")||accion.equals("eliminar_gq"))
            {
                Category oGQ = new Category();
                oGQ.setResourceBase(base);
                ret.append(oGQ.doAdmin(request,response,paramsRequest));
            }
            
            //            if(accion.equals("controles")||accion.equals("update_cc")||accion.equals("edit_cc")||accion.equals("agregar_cc")||accion.equals("modificar_cc")||accion.equals("eliminar_cc"))
            //            {
            //                ControlCatalog oCC = new ControlCatalog();
            //                oCC.setResourceBase(base);
            //                ret.append(oCC.doAdmin(request, response, paramsRequest));
            //            }
            
            if(accion.equals("preguntas")||accion.equals("update_p")||accion.equals("add_p")||accion.equals("agregar_p")||accion.equals("delete_p")||accion.equals("deleteselect_p")||accion.equals("updatequestion_p")||accion.equals("select_p"))
            {
                Question oQ = new Question();
                oQ.setResourceBase(base);
                ret.append(oQ.doAdmin(request,response,paramsRequest));
            }
            
            if(accion.equals("respuestas")||accion.equals("update_fa")||accion.equals("edit_fa")||accion.equals("agregar_fa")||accion.equals("modificar_fa")||accion.equals("eliminar_fa")||accion.equals("result_fa"))
            {
                FreqAnswer oFA = new FreqAnswer();
                oFA.setResourceBase(base);
                ret.append(oFA.doAdmin(request,response,paramsRequest));
            }
            
            if(accion.equals("tipos")||accion.equals("update_ct")||accion.equals("add_ct")||accion.equals("agregar_ct")||accion.equals("delete_ct")||accion.equals("deleteselect_ct")||accion.equals("updatecatalogtype_ct")||accion.equals("select_ct"))
            {
                CatalogType oCT = new CatalogType();
                oCT.setResourceBase(base);
                ret.append(oCT.doAdmin(request, response, paramsRequest));
            }
            
            if(accion.equals("secciones")||accion.equals("update_s")||accion.equals("add_s")||accion.equals("agregar_s")||accion.equals("delete_s")||accion.equals("deleteselect_s")||accion.equals("updatesection_s")||accion.equals("select_s"))
            {
                Subject oS = new Subject();
                oS.setResourceBase(base);
                ret.append(oS.doAdmin(request, response, paramsRequest));
            }
            
            if(accion.startsWith("re_"))
            {
                Reports oR = new Reports();
                oR.setResourceBase(base);
                ret.append(oR.doAdmin(request, response, paramsRequest));
            }
            
            if(accion.equals("reGeneral"))
            {
                GeneralReport oRG = new GeneralReport();
                oRG.setResourceBase(base);
                ret.append(oRG.doAdmin(request, response, paramsRequest));
            }
            
            // cuando un contenido se borra, se eliminan los registros de la DB referentes al base.getId() correspondiente
            if(accion.equals("remove"))
            {
                try
                {
                    conn = SWBUtils.DB.getDefaultConnection();
                    pst = conn.prepareStatement("select * from sr_survey where res_id = ? and idtm=?");
                    pst.setString(1,base.getId());
                    pst.setString(2,idtm);
                    rs = pst.executeQuery();
                    if(rs.next())
                    {
                        // borrar registro del formulario de la DB
                        // obteniendo de srresponseuser los responseid relacionados con surveyid
                        long surveyid = rs.getLong("surveyid");
                        PreparedStatement rsResponse = conn.prepareStatement("select responseid from sr_responseuser where surveyid = ? and idtm=?");
                        rsResponse.setLong(1,surveyid);
                        rsResponse.setString(2,idtm);
                        ResultSet rs1 = rsResponse.executeQuery();
                        StringBuffer strResponse = new StringBuffer();
                        while(rs1.next())
                        {
                            long responseID =rs.getLong("responseid");
                            strResponse.append(responseID+",");
                        }
                        if(rs1 != null)
                        {
                            rs1.close();
                            rs1 = null;
                        }
                        if(rsResponse != null) rsResponse.close();
                        if(strResponse.length() > 0)
                        {
                            PreparedStatement psta = conn.prepareStatement("delete from sr_answer where responseid IN ("+strResponse.deleteCharAt(strResponse.length()-1).toString()+") and idtm=?");
                            psta.setString(1,idtm);
                            psta.executeUpdate();
                            if(psta != null)
                            {
                                psta.close();
                                psta = null;
                            }
                        }
                        PreparedStatement pstr = conn.prepareStatement("delete from sr_responseuser where surveyid = ? and idtm=?");
                        pstr.setLong(1,surveyid);
                        pstr.setString(2,idtm);
                        pstr.executeUpdate();
                        if(pstr != null)
                        {
                            pstr.close();
                            pstr = null;
                        }
                        PreparedStatement psto = conn.prepareStatement("delete from sr_orderquestion where surveyid = ? and idtm=?");
                        psto.setLong(1,surveyid);
                        psto.setString(2,idtm);
                        psto.executeUpdate();
                        if(psto != null)
                        {
                            psto.close();
                            psto = null;
                        }
                        PreparedStatement psts = conn.prepareStatement("delete from sr_survey where surveyid = ? and idtm=?");
                        psts.setLong(1,surveyid);
                        psts.setString(2,idtm);
                        psts.executeUpdate();
                        if(psts != null)
                        {
                            psts.close();
                            psts = null;
                        }
                        PreparedStatement pstrel = conn.prepareStatement("delete from sr_relatedquestion where surveyid = ? and idtm=?");
                        pstrel.setLong(1,surveyid);
                        pstrel.setString(2,idtm);
                        pstrel.executeUpdate();
                        if(pstrel != null)
                        {
                            pstrel.close();
                            pstrel = null;
                        }
                    }
                    if(rs != null) rs.close();
                    if(pst != null) pst.close();
                    if(conn != null)conn.close();
                    log.error(paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgEliminoContenidoId")+": "+base.getId());
                    // seccion para eliminar el template del formulario
                    String RutaBorrar = "";
                    RutaBorrar = SWBPortal.getWorkPath()+base.getWorkPath();
                    if(!SWBUtils.IO.removeDirectory(RutaBorrar))
                    {log.error(paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_logNoPudoBorrarTemplateFormulario"));}
                    
                }
                catch(Exception e)
                {
                    log.error(paramsRequest.getLocaleString("errormsg_MainSurvey_getAdmHtml_logErrorBorrarFormularioExistenteDBsrsurveyMainSurveygetAdmHtml.remove"),e);
                }
                finally
                {
                    rs = null;
                    pst = null;
                    conn = null;
                }
            }
            
            if(request.getParameter("content_type")!=null)
            {
                if(request.getParameter("content_type").equals("excel"))
                {
                    try
                    {
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition","attachment; filename=\"report"+System.currentTimeMillis()+".xls\";");
                        out.println(ret.toString());
                        out.close( );
                    } catch(Exception e)
                    { log.error(paramsRequest.getLocaleString("errormsg_MainSurvey_getAdmHtml_msgErrorGenerarContenidoExcelMainSurveygetAdmHtml.content_type"),e);}
                }
                else response.setContentType("text/html");
            }
            
            if(flag&&!accion.equals("re_reporte"))
            {
                ret.append("\n</div>");
            }
            
            if(null!=request.getParameter("regreso"))
                ret.append("</body></html>");
        }
        out.println(ret.toString());
    }
    
    /**
     *
     * @param recobj
     * @throws SWBResourceException
     */
    @Override
    public void install(ResourceType recobj) throws SWBResourceException
    {
        // Comprobando si ya existen tablas en la DB
        String idtm = recobj.getWebSite().getId();
        boolean existe = false;
//        try
//        {
////            Iterator<ResourceType> itrest=recobj.getWebSite().listResourceTypes();
////            while (itrest.hasNext()) {
////                ResourceType resourceType = itrest.next();
////                if(recobj!=resourceType&&resourceType.getResourceClassName().equals("com.infotec.wb.resources.survey.MainSurvey"))
////                {
////                    existe=true;
////                }
////            }
//            Connection conn = SWBUtils.DB.getDefaultConnection();
//            PreparedStatement pst1  = conn.prepareStatement("select * from sr_answer"); //select * from wbresourcetype where idtm<>? and objclass=?
////            pst1.setString(1, idtm);
////            pst1.setString(2, "com.infotec.wb.resources.survey.MainSurvey");
//            ResultSet rs1 = pst1.executeQuery();
//            if(rs1.next())
//            {
//                existe = true;
//            }
//            rs1.close();
//            pst1.close();
//            conn.close();
//        }
//        catch(Exception e)
//        { existe=false;}
//
//        if(!existe)
//        {
//            try
//            {
//                GenericDB db = new GenericDB();
//                String xml = SWBUtils.IO.getFileFromPath(SWBUtils.getApplicationPath() + "/WEB-INF/xml/survey.xml");
//                db.executeSQLScript(xml, SWBUtils.DB.getDatabaseName(), SWBUtils.DB.getDefaultPoolName());
//            }
//            catch(Exception e)
//            {
//                log.error("Error while trying to create resource tables, class - MainSurvey, method - install",e);
//            }
//        }

        Connection conn = null;
        PreparedStatement pst1  = null;
        ResultSet rs1 = null;


        try
        {
            conn = SWBUtils.DB.getDefaultConnection();
            pst1  = conn.prepareStatement("select * from sr_survey");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                existe = true;
            }
            rs1.close();
            pst1.close();
            conn.close();
        }
        catch(Exception e)
        { existe=false;}
        finally
        {
            rs1=null;
            pst1 = null;
            conn = null;
        }

        if(!existe)
        {
            Connection con = null;
            Statement st = null;
            try
            {
                String dbname=SWBUtils.DB.getDatabaseName();
                //System.out.println("dbname: "+dbname);
                if(dbname.toLowerCase().lastIndexOf("informix")>-1) dbname="informix";
                if(dbname.toLowerCase().lastIndexOf("mysql")>-1) dbname="mysql";
                if(dbname.toLowerCase().lastIndexOf("microsoft sql server")>-1) dbname="sqlserver";
                if(dbname.toLowerCase().lastIndexOf("adaptive server enterprise")>-1) dbname="sybase";
                if(dbname.toLowerCase().lastIndexOf("postgresql")>-1) dbname="postgres";
                if(dbname.toLowerCase().lastIndexOf("oracle")>-1) dbname="oracle";
                if(dbname.toLowerCase().lastIndexOf("hsql")>-1) dbname="hsql";
                InputStream  is_filesql = this.getClass().getResourceAsStream("survey_script_"+dbname+".sql");
                String file =SWBUtils.IO.readInputStream((is_filesql));
                con=SWBUtils.DB.getDefaultConnection();
                st=con.createStatement();
                int x=0;
                if(file!=null)
                {
                    StringTokenizer sto=new StringTokenizer(file,";");
                    while(sto.hasMoreTokens())
                    {
                        String query=sto.nextToken();
                        x=st.executeUpdate(query);
                    }
                }
                if(st != null) st.close();
                if(con != null) con.close();
            }
            catch(Exception e)
            {
                log.error("Error while trying to create resource tables, class - MainSurvey, method - install",e);
                try
                {
                    GenericDB db = new GenericDB();
                    String xml = SWBUtils.IO.getFileFromPath(SWBUtils.getApplicationPath() + "/WEB-INF/xml/survey.xml");
                    db.executeSQLScript(xml, SWBUtils.DB.getDatabaseName(), SWBUtils.DB.getDefaultPoolName());
                }
                catch(Exception e2)
                {
                    log.error("Error while trying to create resource tables, class - MainSurvey, method - install",e2);
                }
            }
            finally
            {
                st = null;
                con = null;
            }

            
        }


        // cargando el cat�logo por sitio
        Timestamp ahora = new Timestamp(System.currentTimeMillis());
        try
        {
            
            RecValidateCode rval = null;
            rval = new RecValidateCode();  //INSERT INTO sr_validatecode VALUES("1", "Email", "Direcci�n de correo v�lida", "function checkMail(field){}",now())$
            rval.setIdtm(idtm);
            rval.setTitle( "Email");
            rval.setDescription("Dirección de correo válida");
            rval.setValidationcode("function checkMail(field){}");
            rval.setLastupdate(ahora);
            rval.create();
            
            rval=null;
            
            rval = new RecValidateCode();  //INSERT INTO sr_validatecode VALUES("2", "Numero", "N�mero v�lido", "function validatenum(field){}",now())$
            rval.setIdtm(idtm);
            rval.setTitle( "Número");
            rval.setDescription("Numero válido");
            rval.setValidationcode("function validatenum(field){}");
            rval.setLastupdate(ahora);
            rval.create();
            
            rval=null;
            
            rval = new RecValidateCode();  //INSERT INTO sr_validatecode VALUES("3", "Alfab�tico", "Caracteres v�lidos", "function validastring(field){}",now())$
            rval.setIdtm(idtm);
            rval.setTitle( "Alfabético");
            rval.setDescription("Caracteres válidos");
            rval.setValidationcode("function validastring(field){}");
            rval.setLastupdate(ahora);
            rval.create();
            
            rval=null;
            
            rval = new RecValidateCode();  //INSERT INTO sr_validatecode VALUES("4", "Alfanum�rico", "Caracteres y numeros v�lidos", "function validastringnum(field){}",now())$
            rval.setIdtm(idtm);
            rval.setTitle( "Alfanumérico");
            rval.setDescription("Caracteres y números válidos");
            rval.setValidationcode("function validastringnum(field){}");
            rval.setLastupdate(ahora);
            rval.create();
            
            rval=null;
        }
        catch(Exception e)
        {
            log.error("Error while trying to create ValidateCode objects, class - MainSurvey, method - install",e);
        }
        try
        {
            RecControlCatalog rcon =null;
            rcon = new RecControlCatalog();  //INSERT INTO sr_controlcatalog VALUES("1", "Textbox", "Respuesta abierta (text box)", now())$
            rcon.setIdtm(idtm);
            rcon.setTitle( "Textbox");
            rcon.setDescription("Respuesta abierta (text box)");
            rcon.setLastupdate(ahora);
            rcon.create();
            
            rcon=null;
            
            rcon = new RecControlCatalog();  //INSERT INTO sr_controlcatalog VALUES("2", "Textarea", "Respuesta abierta (text area)", now())$
            rcon.setIdtm(idtm);
            rcon.setTitle( "Textarea");
            rcon.setDescription("Respuesta abierta (text area)");
            rcon.setLastupdate(ahora);
            rcon.create();
            
            rcon=null;
            
            rcon = new RecControlCatalog();  //INSERT INTO sr_controlcatalog VALUES("3", "Checkbox", "Varias opciones (check box)", now())$
            rcon.setIdtm(idtm);
            rcon.setTitle( "Checkbox");
            rcon.setDescription("Varias opciones (check box)");
            rcon.setLastupdate(ahora);
            rcon.create();
            
            rcon=null;
            
            rcon = new RecControlCatalog();  //INSERT INTO sr_controlcatalog VALUES("4", "Radio Button", "Una sola opci�n (radio button)", now())$
            rcon.setIdtm(idtm);
            rcon.setTitle( "Radio Button");
            rcon.setDescription("Una sola opción (radio button)");
            rcon.setLastupdate(ahora);
            rcon.create();
            
            rcon=null;
            
            rcon = new RecControlCatalog();  //INSERT INTO sr_controlcatalog VALUES("5", "Select Multiple", "Varias opciones (select multiple)", now())$
            rcon.setIdtm(idtm);
            rcon.setTitle( "Select Multiple");
            rcon.setDescription("Varias opciones (select multiple)");
            rcon.setLastupdate(ahora);
            rcon.create();
            
            rcon=null;
            
            rcon = new RecControlCatalog();  //INSERT INTO sr_controlcatalog VALUES("6", "Select Simple", "Una sola opci�n (select)", now())$
            rcon.setIdtm(idtm);
            rcon.setTitle( "Select Simple");
            rcon.setDescription("Una sola opción (select)");
            rcon.setLastupdate(ahora);
            rcon.create();
            
            rcon=null;
            
            rcon = new RecControlCatalog();  //INSERT INTO sr_controlcatalog VALUES("7", "Date", "Selecciona una fecha (fecha: javascipt)", now())$
            rcon.setIdtm(idtm);
            rcon.setTitle( "Date");
            rcon.setDescription("Selecciona una fecha (fecha: javascipt)");
            rcon.setLastupdate(ahora);
            rcon.create();
            
            rcon=null;
            
            rcon = new RecControlCatalog();  //INSERT INTO sr_controlcatalog VALUES("8", "File", "Selecciona un archivo (file)", now())$
            rcon.setIdtm(idtm);
            rcon.setTitle( "File");
            rcon.setDescription("Selecciona un archivo (file)");
            rcon.setLastupdate(ahora);
            rcon.create();
            
            rcon=null;
        }
        catch(Exception e)
        {
            log.error("Error while trying to create ControlCatalog objects, class - MainSurvey, method - install",e);
        }
        
        try
        {
            RecSubject rsub = null;
            
            rsub = new RecSubject();  //INSERT INTO sr_subject VALUES("1", "Grupo Generales", "Seccion para las preguntas generales de los formularios", now(), now())$
            rsub.setIdtm(idtm);
            rsub.setTitle( "Grupo Generales");
            rsub.setDescription("Sección para las preguntas generales de los formularios");
            rsub.create(); 
            
            rsub=null;
        }
        catch(Exception e)
        {
            log.error("Error while trying to create Subject objects, class - MainSurvey, method - install",e);
        }
        try
        {
            RecCategory rcat = null;
            
            rcat = new RecCategory();  //INSERT INTO sr_category VALUES("1","General","Preguntas de orden general sin un proposito especifico",now())$
            rcat.setIdtm(idtm);
            rcat.setTitle( "General");
            rcat.setDescription("Preguntas de orden general sin un propósito específico");
            rcat.setLastupdate(ahora);
            rcat.create();
            
            rcat=null;
            
            rcat = new RecCategory();  //INSERT INTO sr_category VALUES("2","Informaci�n Personal","Preguntas de informaci�n personal del encuestado",now())$
            rcat.setIdtm(idtm);
            rcat.setTitle( "Información Personal");
            rcat.setDescription("Preguntas de información personal del encuestado");
            rcat.setLastupdate(ahora);
            rcat.create();
            
            rcat=null;
        }
        catch(Exception e)
        {
            log.error("Error while trying to create Category objects, class - MainSurvey, method - install",e);
        }
        try
        {
            RecGroupAnswer rga = null;
            
            rga = new RecGroupAnswer();  //INSERT INTO sr_groupanswer VALUES("1","Abiertas","Respuestas Abiertas",now())$
            rga.setIdtm(idtm);
            rga.setTitle( "Abiertas");
            rga.setDescription("Respuestas Abiertas");
            rga.setLastupdate(ahora);
            rga.create();
            
            rga=null;
            
            rga = new RecGroupAnswer();  //INSERT INTO sr_groupanswer VALUES("2","Respuestas Multiples","Sets con mas de una opcion de respuesta posible o correcta",now())$
            rga.setIdtm(idtm);
            rga.setTitle( "Respuestas Múltiples");
            rga.setDescription("Sets con más de una opción de respuesta posible o correcta");
            rga.setLastupdate(ahora);
            rga.create();
            
            rga=null;
            
            rga = new RecGroupAnswer();  //INSERT INTO sr_groupanswer VALUES("3","Respuestas Unicas","Sets con solo una opci�n de respuesta posible o correcta",now())$
            rga.setIdtm(idtm);
            rga.setTitle( "Respuestas únicas");
            rga.setDescription("Sets con solo una opción de respuesta posible o correcta");
            rga.setLastupdate(ahora);
            rga.create();
            
            rga=null;
        }
        catch(Exception e)
        {
            log.error("Error while trying to create Group Answer objects, class - MainSurvey, method - install",e);
        }
        try
        {
            RecFreqAnswer rfa = null;
            
            rfa = new RecFreqAnswer();  //INSERT INTO sr_freqanswer VALUES("1","Respuesta Abierta","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\">Respuesta de texto abierta</option></resource>","1","1",now(),now())$
            rfa.setIdtm(idtm);
            rfa.setTitle( "Respuesta Abierta");
            rfa.setStringxml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\">Respuesta de texto abierta</option></resource>");
            rfa.setGroupaid(1);
            rfa.setIsreuse(1);
            rfa.setCreated(ahora);
            rfa.setLastupdate(ahora);
            rfa.create();
            
            rfa=null;
            
            rfa = new RecFreqAnswer();  //INSERT INTO sr_freqanswer VALUES("2","si - no","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\">Si</option><option id=\"2\">No</option></resource>","3","1",now(),now())$
            rfa.setIdtm(idtm);
            rfa.setTitle( "si - no");
            rfa.setStringxml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\">Si</option><option id=\"2\">No</option></resource>");
            rfa.setGroupaid(3);
            rfa.setIsreuse(1);
            rfa.setCreated(ahora);
            rfa.setLastupdate(ahora);
            rfa.create();
            
            rfa=null;
            
            rfa = new RecFreqAnswer();  //INSERT INTO sr_freqanswer VALUES("3","D�as Semana","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\">Lunes</option><option id=\"2\">Martes</option><option id=\"3\">Miercoles</option><option id=\"4\">Jueves</option><option id=\"5\">Viernes</option><option id=\"6\">Sabado</option><option id=\"7\">Domingo</option></resource>","2","1",now(),now())$
            rfa.setIdtm(idtm);
            rfa.setTitle( "Días Semana");
            rfa.setStringxml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\">Lunes</option><option id=\"2\">Martes</option><option id=\"3\">Miercoles</option><option id=\"4\">Jueves</option><option id=\"5\">Viernes</option><option id=\"6\">Sabado</option><option id=\"7\">Domingo</option></resource>");
            rfa.setGroupaid(2);
            rfa.setIsreuse(1);
            rfa.setCreated(ahora);
            rfa.setLastupdate(ahora);
            rfa.create();
            
            rfa=null;
            
            rfa = new RecFreqAnswer();  //INSERT INTO sr_freqanswer VALUES("4","Meses","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\">Enero</option><option id=\"2\">Febrero</option><option id=\"3\">Marzo</option><option id=\"4\">Abril</option><option id=\"5\">Mayo</option><option id=\"6\">Junio</option><option id=\"7\">Julio</option><option id=\"8\">Agosto</option><option id=\"9\">Septiembre</option><option id=\"10\">Octubre</option><option id=\"11\">Noviembre</option><option id=\"12\">Diciembre</option></resource>","2","1",now(),now())$
            rfa.setIdtm(idtm);
            rfa.setTitle( "Meses");
            rfa.setStringxml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\">Enero</option><option id=\"2\">Febrero</option><option id=\"3\">Marzo</option><option id=\"4\">Abril</option><option id=\"5\">Mayo</option><option id=\"6\">Junio</option><option id=\"7\">Julio</option><option id=\"8\">Agosto</option><option id=\"9\">Septiembre</option><option id=\"10\">Octubre</option><option id=\"11\">Noviembre</option><option id=\"12\">Diciembre</option></resource>");
            rfa.setGroupaid(2);
            rfa.setIsreuse(1);
            rfa.setCreated(ahora);
            rfa.setLastupdate(ahora);
            rfa.create();
            
            rfa=null;
            
            rfa = new RecFreqAnswer();  //INSERT INTO sr_freqanswer VALUES("5","Verdadero - Falso","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\">Verdadero</option><option id=\"2\">Falso</option></resource>","3","1",now(),now())$
            rfa.setIdtm(idtm);
            rfa.setTitle( "Verdadero - Falso");
            rfa.setStringxml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\">Verdadero</option><option id=\"2\">Falso</option></resource>");
            rfa.setGroupaid(3);
            rfa.setIsreuse(1);
            rfa.setCreated(ahora);
            rfa.setLastupdate(ahora);
            rfa.create();
            
            rfa=null;
            
            rfa = new RecFreqAnswer();  //INSERT INTO sr_freqanswer VALUES("6","Acuerdo - Desacuerdo","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\">Totalmente de Acuerdo</option><option id=\"2\">De acuerdo</option><option id=\"3\">En desacuerdo</option><option id=\"4\">Totalmente en desacuerdo</option></resource>","3","1",now(),now())$
            rfa.setIdtm(idtm);
            rfa.setTitle( "Acuerdo - Desacuerdo");
            rfa.setStringxml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\">Totalmente de Acuerdo</option><option id=\"2\">De acuerdo</option><option id=\"3\">En desacuerdo</option><option id=\"4\">Totalmente en desacuerdo</option></resource>");
            rfa.setGroupaid(3);
            rfa.setIsreuse(1);
            rfa.setCreated(ahora);
            rfa.setLastupdate(ahora);
            rfa.create();
            
            rfa=null;
            
            rfa = new RecFreqAnswer();  //INSERT INTO sr_freqanswer VALUES("7","Excelente - Pesimo","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\">Excelente</option><option id=\"2\">Muy bien</option><option id=\"3\">Bien</option><option id=\"4\">Regular</option><option id=\"5\">Malo</option><option id=\"6\">Pesimo</option></resource>","3","1",now(),now())$
            rfa.setIdtm(idtm);
            rfa.setTitle( "Excelente - Pésimo");
            rfa.setStringxml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\">Excelente</option><option id=\"2\">Muy bien</option><option id=\"3\">Bien</option><option id=\"4\">Regular</option><option id=\"5\">Malo</option><option id=\"6\">Pesimo</option></resource>");
            rfa.setGroupaid(3);
            rfa.setIsreuse(1);
            rfa.setCreated(ahora);
            rfa.setLastupdate(ahora);
            rfa.create();
            
            rfa=null;
        }
        catch(Exception e)
        {
            log.error("Error while trying to create FreqAnswer objects, class - MainSurvey, method - install",e);
        }
        try
        {
            RecQuestion rque = null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("1","�Cu�l es tu nombre?","","3","1","0","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\">Nombre Completo</option></resource>","2","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("¿Cuál es tu nombre?");
            rque.setInstruction("");
            rque.setCodeID(3);
            rque.setValidate(1);
            rque.setRequired(0);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\">Nombre Completo</option></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("2","Calle","","4","0","0","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","2","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("Calle");
            rque.setInstruction("");
            rque.setCodeID(4);
            rque.setValidate(0);
            rque.setRequired(0);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("3","N�mero exterior","","2","0","0","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","2","1",now(),now());$
            rque.setIdtm(idtm);
            rque.setQuestion("Número exterior");
            rque.setInstruction("");
            rque.setCodeID(2);
            rque.setValidate(0);
            rque.setRequired(0);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("4","N�mero interior (si aplica)","","2","0","0","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","2","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("Número interior (si aplica)");
            rque.setInstruction("");
            rque.setCodeID(2);
            rque.setValidate(0);
            rque.setRequired(0);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("5","Colonia","","3","0","0","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","2","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("Colonia");
            rque.setInstruction("");
            rque.setCodeID(3);
            rque.setValidate(0);
            rque.setRequired(0);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("6","Ciudad","","3","0","0","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","2","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("Ciudad");
            rque.setInstruction("");
            rque.setCodeID(3);
            rque.setValidate(0);
            rque.setRequired(0);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("7","C.P.","","2","1","1","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","2","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("C.P.");
            rque.setInstruction("");
            rque.setCodeID(2);
            rque.setValidate(1);
            rque.setRequired(1);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("8","Tel�fono","","2","1","0","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","2","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("Teléfono");
            rque.setInstruction("");
            rque.setCodeID(2);
            rque.setValidate(1);
            rque.setRequired(0);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("9","Fax","","2","1","0","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","2","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("Fax");
            rque.setInstruction("");
            rque.setCodeID(2);
            rque.setValidate(1);
            rque.setRequired(0);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("10","E-mail (para poder contactarte)","","1","1","1","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","2","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("E-mail (para poder contactarte)");
            rque.setInstruction("");
            rque.setCodeID(1);
            rque.setValidate(1);
            rque.setRequired(1);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("11","Delegaci�n o Municipio","","3","0","1","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","2","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("Delegación o Municipio");
            rque.setInstruction("");
            rque.setCodeID(3);
            rque.setValidate(0);
            rque.setRequired(1);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("12","Nombre(s)","","3","1","1","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","2","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("Nombre(s)");
            rque.setInstruction("");
            rque.setCodeID(3);
            rque.setValidate(1);
            rque.setRequired(1);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("13","Apellido Paterno","","3","1","1","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","2","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("Apellido Paterno");
            rque.setInstruction("");
            rque.setCodeID(3);
            rque.setValidate(1);
            rque.setRequired(1);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("14","Apellido Materno","","3","1","1","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","2","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("Apellido Materno");
            rque.setInstruction("");
            rque.setCodeID(3);
            rque.setValidate(1);
            rque.setRequired(1);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("15","Entidad federativa","","3","1","1","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","1","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("Entidad federativa");
            rque.setInstruction("");
            rque.setCodeID(3);
            rque.setValidate(1);
            rque.setRequired(1);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(1);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("16","Localidad","","3","1","1","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","1","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("Localidad");
            rque.setInstruction("");
            rque.setCodeID(3);
            rque.setValidate(1);
            rque.setRequired(1);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(1);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("17","Lugar de Nacimiento","","3","1","1","0","1","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","2","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("Lugar de Nacimiento");
            rque.setInstruction("");
            rque.setCodeID(3);
            rque.setValidate(1);
            rque.setRequired(1);
            rque.setValidOptions(0);
            rque.setControlID(1);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
            
            rque = new RecQuestion();  //INSERT INTO sr_question VALUES("18","Fecha de nacimiento","","4","0","1","0","7","1","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>","2","1",now(),now())$
            rque.setIdtm(idtm);
            rque.setQuestion("Fecha de nacimiento");
            rque.setInstruction("");
            rque.setCodeID(4);
            rque.setValidate(0);
            rque.setRequired(1);
            rque.setValidOptions(0);
            rque.setControlID(7);
            rque.setFreqAnswerID(1);
            rque.setStringXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<resource><option id=\"1\" refer=\"1\" value=\"100\"/></resource>");
            rque.setCategoryID(2);
            rque.setIsReUse(1);
//            rque.setIsActive(1);
//            rque.setIsData(0);
            rque.create();
            
            rque=null;
        }
        catch(Exception e)
        {
            log.error("Error while trying to create Category objects, class - MainSurvey, method - install",e);
        }
        
        
    }
    
    
    /**
     *
     * @param recobj
     * @throws SWBResourceException
     */
    @Override
    public void uninstall(ResourceType recobj) throws SWBResourceException
    {
        // Comprobando si ya existen tablas en la DB
        String idtm=recobj.getWebSite().getId();
        boolean existe = false;
        try
        {
            Connection conn = SWBUtils.DB.getDefaultConnection();
            PreparedStatement pst1  = conn.prepareStatement("select * from wbresourcetype where idtm<>? and objclass=? ");
            pst1.setString(1, idtm);
            pst1.setString(2, "com.infotec.wb.resources.survey.MainSurvey");
            ResultSet rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                existe = true;
            }
            rs1.close();
            pst1.close();
            conn.close();
        }
        catch(Exception e)
        { existe=false;}
        
        if(!existe)
        {
            Connection con = null;
            Statement st = null;
            String tablename = null;
            String strsql = null;
            HashMap hmtables = new HashMap();
            try
            {
                hmtables.put("0","sr_answer");
                hmtables.put("1","sr_validatecode");
                hmtables.put("2","sr_controlcatalog");
                hmtables.put("3","sr_freqanswer");
                hmtables.put("4","sr_groupanswer");
                hmtables.put("5","sr_category");
                hmtables.put("6","sr_orderquestion");
                hmtables.put("7","sr_question");
                hmtables.put("8","sr_responseuser");
                hmtables.put("9","sr_subject");
                hmtables.put("10","sr_survey");
                hmtables.put("11","sr_relatedquestion");
                con = SWBUtils.DB.getDefaultConnection();
                if(con!=null)
                {
                    st = con.createStatement();
                    for(int i=0; i < hmtables.size(); i++)
                    {
                        tablename = (String) hmtables.get(Integer.toString(i));
                        strsql = "drop table " + tablename;
                        st.executeUpdate(strsql);
                    }
                    if(st != null) st.close();
                    if(con != null) con.close();
                }
            }
            catch(Exception e)
            {
                log.error("Error while trying to drop resource tables, class - MainSurvey, method - uninstall",e);
            }
            finally
            {
                st = null;
                con = null;
            }
        }
        else
        {
            // falta incluir en las tablas de este recurso el id del topicmap para poder eliminar las encuestas correspondientes.
            // saber los id de los formulario pertenecientes a este tipo de recurso de este sitio
            
            // Hacer un ciclo con estos id para eliminar toda la informaci�n de estos formularios mediante el siguiente c�digo:
            Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rs=null;
            try
            {
                conn = SWBUtils.DB.getDefaultConnection();
                // borrar registro del formulario de la DB
                // obteniendo de srresponseuser los responseid relacionados con surveyid
                PreparedStatement psta = conn.prepareStatement("delete from sr_answer where idtm=?");
                psta.setString(1,idtm);
                psta.executeUpdate();
                if(psta != null)
                {
                    psta.close();
                    psta = null;
                }
                PreparedStatement pstr = conn.prepareStatement("delete from sr_responseuser where idtm = ?");
                pstr.setString(1,idtm);
                pstr.executeUpdate();
                if(pstr != null)
                {
                    pstr.close();
                    pstr = null;
                }
                PreparedStatement psto = conn.prepareStatement("delete from sr_orderquestion where idtm = ?");
                psto.setString(1,idtm);
                psto.executeUpdate();
                if(psto != null)
                {
                    psto.close();
                    psto = null;
                }
                PreparedStatement psts = conn.prepareStatement("delete from sr_survey where idtm = ?");
                psts.setString(1,idtm);
                psts.executeUpdate();
                if(psts != null)
                {
                    psts.close();
                    psts = null;
                }
                PreparedStatement pstrel = conn.prepareStatement("delete from sr_relatedquestion where idtm = ?");
                pstrel.setString(1,idtm);
                pstrel.executeUpdate();
                if(pstrel != null)
                {
                    pstrel.close();
                    pstrel = null;
                }
                
                
                // seccion para eliminar el template del formulario
                String RutaBorrar = "";
                RutaBorrar = SWBPortal.getWorkPath()+base.getWorkPath();
                if(!SWBUtils.IO.removeDirectory(RutaBorrar))
                {log.error("Error while trying to remove resource work directory. Formulario resource.");}
                
            }
            catch(Exception e)
            {
                log.error("Error while trying to uninstall Formulario resource.",e);
            }
            finally
            {
                rs = null;
                pst = null;
                conn = null;
            }
            
            
            
            
        }
    }
    
    /**
     * Gets the available time to answer the exam
     * @param responseid The identifier of the response of the session user
     * @return A string with the available time to answer the exam
     */
    public String getAvailableTime(long responseid)
    {
        String idtm = base.getWebSiteId();
        StringBuffer ret = new StringBuffer();
        int min = 0;
        if(responseid>0)
        {
            RecResponseUser rrusr = new RecResponseUser();
            rrusr.setIdtm(idtm);
            rrusr.setResponseID(responseid);
            rrusr.load();
            
            Resource res = getResourceBase();
            
            RecSurvey rex = new RecSurvey();
            rex.setIdtm(idtm);
            rex.setResID(res.getId());
            rex.load();
            
            Timestamp limite = new Timestamp(rrusr.getCreated().getTime()+rex.getTimeAnswer()*60000);
            Timestamp min_actual = new java.sql.Timestamp(System.currentTimeMillis());
            
            ret.append("\n<script language=\"JavaScript\">");
            ret.append("\nCountActive = true;");
            //DisplayFormat = "%%D%% d�as, %%H%% Horas, %%M%% Minutos, %%S%% Segundos.";
            ret.append("\nDisplayFormat = \"%%H%%:%%M%%:%%S%%\";");
            //ret.append("\nFinishMessage = \"Se termino el tiempo para contestar el examen.\";");
            ret.append("\nFinishMessage = \""+ res.getAttribute("text_time_finish","Se termin� el tiempo para contestar.")+ "\";");
            
            ret.append("\nfunction calcage(secs, num1, num2) {");
            ret.append("\n  s = ((Math.floor(secs/num1))%num2).toString();");
            ret.append("\n  if (s.length < 2)");
            ret.append("\n    s = \"0\" + s;");
            ret.append("\n  return \"<b>\" + s + \"</b>\";");
            ret.append("\n}");
            
            ret.append("\nfunction CountBack(secs) {");
            ret.append("\n  if (secs < 0) {");
            ret.append("\n    document.getElementById(\"cntdwn\").innerHTML = FinishMessage;");
            ret.append("\n    return;");
            ret.append("\n  }");
            ret.append("\n  DisplayStr = DisplayFormat.replace(/%%D%%/g, calcage(secs,86400,100000));");
            ret.append("\n  DisplayStr = DisplayStr.replace(/%%H%%/g, calcage(secs,3600,24));");
            ret.append("\n  DisplayStr = DisplayStr.replace(/%%M%%/g, calcage(secs,60,60));");
            ret.append("\n  DisplayStr = DisplayStr.replace(/%%S%%/g, calcage(secs,1,60));");
            ret.append("\n");
            ret.append("\n  document.getElementById(\"cntdwn\").innerHTML = DisplayStr;");
            ret.append("\n  if (CountActive)");
            ret.append("\n    setTimeout(\"CountBack(\" + (secs-1) + \")\", 990);");
            ret.append("\n}");
            
            ret.append("\nfunction showTimer() {");
            ret.append("\n document.write(\"<span id='cntdwn' style='background-color:white; color:#0033CC; font-family:arial,verdana; font-size:14' ></span>\");");
            ret.append("\n}");
            ret.append("\nshowTimer();");
            ret.append("\nvar dthen = new Date();");
            ret.append("\n dthen.setTime("+limite.getTime()+");");
            ret.append("\nvar dnow = new Date();");
            ret.append("\n dnow.setTime("+min_actual.getTime()+");");
            ret.append("\nddiff = new Date(dthen-dnow);");
            ret.append("\ngsecs = Math.floor(ddiff.valueOf()/1000);");
            ret.append("\nCountBack(gsecs);");
            ret.append("\n</script>");
        }
        return ret.toString();
    }
}
