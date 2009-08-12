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

import com.infotec.wb.resources.survey.db.RecOrderQuestion;
import com.infotec.wb.resources.survey.db.RecQuestion;
import com.infotec.wb.resources.survey.db.RecRelatedQuestion;
import com.infotec.wb.resources.survey.db.RecSurvey;
import javax.servlet.http.*;
import javax.xml.parsers.*;
import java.util.*;
import java.sql.*;
import java.io.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.util.FileUpload;
import org.w3c.dom.*;

/**
 * Created by
 * User: Juan Antonio Fernández Arias
 * 
 */

public class Survey
{
    private static Logger log = SWBUtils.getLogger(Survey.class);

    Resource base=null;
    DocumentBuilderFactory dbf=null;
    DocumentBuilder db=null;
    Document doc;
    HashMap hm = new HashMap();
    HashSet hs = null;

    public Survey()
    {}

    public void setResourceBase(Resource base)
    {
        this.base = base;
        try
        {
            dbf=DocumentBuilderFactory.newInstance();
            db=dbf.newDocumentBuilder();
        }
        catch(Exception e)
        {
            log.error( SWBUtils.TEXT.getLocaleString("locale_wb2_resources","errormsg_Survey_setResourceBase_logErrorDomSurveysetResourceBase"),e);
        }
    }

    public String doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest, String accion) throws SWBResourceException, IOException
    {

        String idtm=base.getWebSiteId();
        String Header = new String("");
        StringBuffer ret = new StringBuffer();
        //String webpath=(String)WBUtils.getInstance().getWebPath();

        User user = paramsRequest.getUser();
        boolean parseoTemplate=false;
        String fileparsed="";
        if(request.getSession().getAttribute("retq")!=null)request.getSession().setAttribute("retq",null);

        if (accion.equalsIgnoreCase("updategroup_en"))
        {
            // secci�n de actualizaci�n del formulario

            try
            {
                FileUpload fUpload=new FileUpload();
                fUpload.getFiles(request,response);
                if (fUpload.getValue("id")!=null)
                {
                    String tempS = new String("");
                    int tempI = 0;
                    RecSurvey objG = new RecSurvey();
                    objG.setIdtm(idtm);
                    objG.setResID(base.getId());
                    objG.load();
                    tempS="";
                    if(!fUpload.getValue("agradecimiento").trim().equals("")) tempS=fUpload.getValue("agradecimiento");
                    base.setAttribute("agradecimiento",tempS);
                    tempS="";
                    if(!fUpload.getValue("instrucciones").trim().equals("")) tempS=fUpload.getValue("instrucciones");
                    base.setAttribute("instructions",tempS);
                    tempS = "0";
                    if(!fUpload.getValue("url_response").equals(null)) tempS = fUpload.getValue("url_response");
                    base.setAttribute("url_response",tempS);
                    objG.setTypeID(Integer.parseInt(fUpload.getValue("tipo")));
                    tempS="0";
                    if(fUpload.getValue("calificacion")!=null) tempS = fUpload.getValue("calificacion");
                    objG.setMinScore(Float.parseFloat(tempS));
                    tempI=0;
                    if(fUpload.getValue("onceanswered")!=null ) tempI = Integer.parseInt(fUpload.getValue("onceanswered"));
                    objG.setMaxAnswer(tempI);
                    tempS = "0";
                    if(fUpload.getValue("lastchance")!=null) tempS = fUpload.getValue("lastchance");
                    //else tempS = fUpload.getValue("tmpLastChance");
                    base.setAttribute("lastchance",tempS);
                    tempI=0;
                    if(fUpload.getValue("timeanswer")!=null ) tempI = Integer.parseInt(fUpload.getValue("timeanswer"));
                    objG.setTimeAnswer(tempI);
                    tempI=0;
                    if(fUpload.getValue("sendemail")!=null ) tempI = Integer.parseInt(fUpload.getValue("sendemail"));
                    base.setAttribute("sendemail",Integer.toString(tempI));
                    tempI = 0;
                    if(fUpload.getValue("show_pending")!=null ) tempI = Integer.parseInt(fUpload.getValue("show_pending"));
                    base.setAttribute("show_pending",Integer.toString(tempI));
                    tempS = "0";
                    if(!fUpload.getValue("emailadmin").equals(null)) tempS = fUpload.getValue("emailadmin");
                    base.setAttribute("emailadmin",tempS);
                    tempI = 3;
                    if(fUpload.getValue("showdisplay")!=null) tempI = Integer.parseInt(fUpload.getValue("showdisplay"));
                    base.setAttribute("showdisplay",Integer.toString(tempI));
                    tempI = 0;
                    if(fUpload.getValue("moderate")!=null) tempI = Integer.parseInt(fUpload.getValue("moderate"));
                    base.setAttribute("moderate",Integer.toString(tempI));
                    tempI = 0;
                    if(!fUpload.getValue("showquestion").equals(null))
                        if(!fUpload.getValue("showquestion").equals(""))
                            tempI = Integer.parseInt(fUpload.getValue("showquestion"));
                    base.setAttribute("showquestion",Integer.toString(tempI));
                    tempS = "";
                    if (fUpload.getValue("estatus")!=null) tempS=fUpload.getValue("estatus");
                    base.setAttribute("status",tempS);
                    tempI = 0;
                    if(fUpload.getValue("anonimo")!=null) tempI = Integer.parseInt(fUpload.getValue("anonimo"));
                    base.setAttribute("anonimo",Integer.toString(tempI));

                    if(fUpload.getValue("ptemplate").length()>1)
                    {
                        tempS="0";
                        if(fUpload.getFileName("ftemplate")!=null)
                        {

                            // seccion para eliminar el template del formulario

                            String Otype=base.getResourceType().getId();
                            //RecResourceType recobj = DBResourceType.getInstance().getResourceType(paramsRequest.getTopic().getMap().getId(),Otype);
                            //String RutaBorrar = WBUtils.getInstance().getWorkPath()+"/resources/"+recobj.getName()+"/"+base.getId();
                            String RutaBorrar = base.getWorkPath();

                            if(!SWBUtils.IO.removeDirectory(RutaBorrar))
                            {log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_logNoPudoBorrarTemplateFormulario"));}

                            tempS=fUpload.getFileName("ftemplate");
                            tempS = tempS.substring(tempS.lastIndexOf("\\")+1,tempS.length());

                            String RutaGuardaTemplate = SWBPlatform.getWorkPath()+base.getWorkPath();
                            File f=new File(RutaGuardaTemplate);
                            if(!f.exists())
                                f.mkdirs();
                            fUpload.saveFile("ftemplate",f.getPath()+"/");
                            WBAdmResourceUtils wresutil = new WBAdmResourceUtils();
                            fileparsed = wresutil.uploadFileParsed(base, fUpload, "ftemplate", request.getSession().getId());
                            base.setAttribute("template",tempS);
                            parseoTemplate=true;
                        }
                    }
                    tempS =paramsRequest.getWebPage().getUrl();
                    base.setAttribute("url", tempS);
                    base.updateAttributesToDB();
                    if (!objG.update()) log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_logNoPudoActualizarEncuestaId") + request.getParameter("id"));
                    log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAdministradorId")+": "+paramsRequest.getUser().getId()+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgActualizFormularioId")+": "+objG.getSurveyID());
                    objG=null;
                }
            }
            catch(Exception e)
            {log.error(paramsRequest.getLocaleString("errormsg_Survey_getAdmHtml_logErrorActualizarTemplateFormulario"),e);}
            accion = "update_en";
        }

        if (accion.equalsIgnoreCase("agregar_en"))
        {
            // secci�n de agregar Formulario
            RecSurvey objS=new RecSurvey();
            objS.setIdtm(idtm);
            try
            {
                FileUpload fUpload=new FileUpload();
                fUpload.getFiles(request,response);
                String tempS=new String("");
                int tempI = 0;

                tempS="";
                if(fUpload.getValue("agradecimiento")!=null) tempS=fUpload.getValue("agradecimiento");
                base.setAttribute("agradecimiento",tempS);
                tempS="";
                if(fUpload.getValue("instrucciones")!=null) tempS=fUpload.getValue("instrucciones");
                base.setAttribute("instructions",tempS);
                tempS = "0";
                if(fUpload.getValue("url_response")!=null) tempS = fUpload.getValue("url_response");
                base.setAttribute("url_response",tempS);
                objS.setTypeID(Integer.parseInt(fUpload.getValue("tipo")));
                tempS="0";
                if(fUpload.getValue("calificacion")!=null) tempS = fUpload.getValue("calificacion");
                objS.setMinScore(Float.parseFloat(tempS));
                tempI=0;
                if(fUpload.getValue("onceanswered")!=null ) tempI = Integer.parseInt(fUpload.getValue("onceanswered"));
                objS.setMaxAnswer(tempI);
                tempS = "0";
                if(fUpload.getValue("lastchance")!=null) tempS = fUpload.getValue("lastchance");
                //else tempS = fUpload.getValue("tmpLastChance");
                //tmpLastChance
                base.setAttribute("lastchance",tempS);
                tempI=0;
                if(fUpload.getValue("timeanswer")!=null ) tempI = Integer.parseInt(fUpload.getValue("timeanswer"));
                objS.setTimeAnswer(tempI);
                tempI=0;
                if(fUpload.getValue("show_pending")!=null ) tempI = Integer.parseInt(fUpload.getValue("show_pending"));
                base.setAttribute("show_pending",Integer.toString(tempI));
                tempS = "0";
                if(fUpload.getValue("sendemail")!=null ) tempI = Integer.parseInt(fUpload.getValue("sendemail"));
                base.setAttribute("sendemail",Integer.toString(tempI));
                tempS = "0";
                if(fUpload.getValue("emailadmin")!=null) tempS = fUpload.getValue("emailadmin");
                base.setAttribute("emailadmin",tempS);
                tempI = 3;
                if(fUpload.getValue("showdisplay")!=null) tempI = Integer.parseInt(fUpload.getValue("showdisplay"));
                base.setAttribute("showdisplay",Integer.toString(tempI));
                tempI = 0;
                if(fUpload.getValue("moderate")!=null) tempI = Integer.parseInt(fUpload.getValue("moderate"));
                base.setAttribute("moderate",Integer.toString(tempI));
                tempI = 0;
                if(fUpload.getValue("showquestion")!=null)
                    if(!fUpload.getValue("showquestion").equals(""))
                        tempI = Integer.parseInt(fUpload.getValue("showquestion"));
                base.setAttribute("showquestion",Integer.toString(tempI));
                tempS = "";
                if (fUpload.getValue("estatus")!=null) tempS=fUpload.getValue("estatus");
                base.setAttribute("status",tempS);
                tempI = 0;
                if(fUpload.getValue("anonimo")!=null) tempI = Integer.parseInt(fUpload.getValue("anonimo"));
                base.setAttribute("anonimo",Integer.toString(tempI));

                tempS = "";
                if(fUpload.getValue("topic")!=null&&fUpload.getValue("tm")!=null)
                {
                    tempS =paramsRequest.getWebPage().getUrl();
                    base.setAttribute("url",tempS);
                }

                tempS = "";
                objS.setResID(base.getId());
                tempS="";
                if(fUpload.getFileName("ftemplate")!=null)
                {
                    tempS=fUpload.getFileName("ftemplate");
                    tempS = tempS.substring(tempS.lastIndexOf("\\")+1,tempS.length());
                    String Otype=base.getResourceType().getId();
                    //RecResourceType recobj = DBResourceType.getInstance().getResourceType(paramsRequest.getTopic().getMap().getId(),Otype);
                    String RutaGuardaTemplate = SWBPlatform.getWorkPath()+base.getWorkPath(); //WBUtils.getInstance().getWorkPath()+"/sites/"+paramsRequest.getTopic().getMap().getId()+"/resources/"+recobj.getName()+"/";
                    File f=new File(RutaGuardaTemplate);
                    if(!f.exists())
                        f.mkdirs();

                    fUpload.saveFile("ftemplate",f.getPath()+"/");
                    WBAdmResourceUtils wresutil = new WBAdmResourceUtils();
                    fileparsed = wresutil.uploadFileParsed(base, fUpload, "ftemplate", request.getSession().getId());
                    parseoTemplate=true;
                    base.setAttribute("template",tempS);
                }
                base.updateAttributesToDB();
                if(!objS.create()) log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_logNoPudoAgregarFormulario"));
                //   Se manda a la selecci�n de preguntas que forman la encuesta, en la siguiente parte
                // se podra tanto seleccionar preguntas como agregar nuevas preguntas.
                // Se pasa a sesion la encuesta capturada.
                log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAdministradorId")+paramsRequest.getUser().getId()+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAgregoFormularioId")+": "+objS.getSurveyID());
                objS=null;

                accion = "update_en"; //"selectquestion_en";
            }
            catch(Exception e)
            {log.error(paramsRequest.getLocaleString("errormsg_Survey_getAdmHtml_logErrorGuardarTemplateFormulario"),e);}
        }

        if (accion.equalsIgnoreCase("orderupdate_en"))
        {
            if(request.getParameter("s_enviar").equals("Actualizar"))
            {
                RecSurvey oRS = new RecSurvey();
                oRS.setIdtm(idtm);
                oRS.setResID(base.getId());
                oRS.load();
                RecOrderQuestion oOQ = new RecOrderQuestion(); //Long.toString(oRS.getSurveyID()),request.getParameter("questionid"));
                oOQ.setIdtm(idtm);
                oOQ.setSurveyid(oRS.getSurveyID());
                oOQ.setQuestionid(Long.parseLong(request.getParameter("questionid")));
                oOQ.load();
                oOQ.setOrdernum(Integer.parseInt(request.getParameter("indice")));
                oOQ.setSubjectid(Long.parseLong(request.getParameter("seccion")));

                int tempoINT = 0;
                if(request.getParameter("isactive")!=null) tempoINT = 1;
                oOQ.setIsActive(tempoINT);
                tempoINT = 0;
                if(request.getParameter("isdata")!=null) tempoINT = 1;
                oOQ.setIsData(tempoINT);
                if (!oOQ.update()) log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_logNoPudoActualizarRegistroDBSrorderquestion"));
                log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgActualizoSeccionIndiceFormulario")+": "+oRS.getSurveyID());
                oOQ=null;

                //
                //              AGREGADO PARA LA PARTE DE RELACIONAR PREGUNTAS
                //
                Connection conexion =null;
                PreparedStatement ppst1 =null;
                PreparedStatement ppst2 =null;
                try
                {
                    conexion = SWBUtils.DB.getDefaultConnection();

                    if(request.getParameter("parent").equals("0")&&!request.getParameter("hparent").equals("null"))
                    {
                        // quitando la relaci�n entre el parent y la pregunta

                        ppst1 = conexion.prepareStatement("" +
                        "Delete from sr_relatedquestion " +
                        "where " +
                        "parentquestion = ? and " +
                        "sonquestion = ? and " +
                        "surveyid = ? and idtm=?");
                        ppst1.setLong(1,Long.parseLong(request.getParameter("hparent")));
                        ppst1.setLong(2,Long.parseLong(request.getParameter("questionid")));
                        ppst1.setLong(3,oRS.getSurveyID());
                        ppst1.setString(4,idtm);
                        ppst1.executeUpdate();
                        if(ppst1!=null)ppst1 .close();
                        log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgEliminoRelacionPreguntasIds")+": "+request.getParameter("hparent")+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgY")+" "+request.getParameter("questionid")+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgDelFormularioId")+": "+oRS.getSurveyID());
                    }
                    else
                    {
                        if(!request.getParameter("parent").equals("0")&&request.getParameter("hparent").equals("null"))
                        {
                            RecRelatedQuestion oRRQ = new RecRelatedQuestion();
                            oRRQ.setIdtm(idtm);
                            oRRQ.setParent(Long.parseLong(request.getParameter("parent")));
                            oRRQ.setSon(Long.parseLong(request.getParameter("questionid")));
                            oRRQ.setSurveyId(oRS.getSurveyID());
                            if(!oRRQ.create()) log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_logErrorCrearRelacionPreguntas"));
                            log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgGeneroRelacionPreguntasIds")+":"+request.getParameter("parent")+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgY")+" "+request.getParameter("questionid")+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgDelFormularioId")+": "+oRS.getSurveyID());
                            oRRQ = null;
                        }
                        else
                        {
                            if(!request.getParameter("parent").equals("0")&&!request.getParameter("hparent").equals("null"))
                            {
                                if(!request.getParameter("parent").equals(request.getParameter("hparent")))
                                {
                                    ppst2 = conexion.prepareStatement("" +
                                    "Delete from sr_relatedquestion " +
                                    "where " +
                                    "parentquestion = ? and " +
                                    "sonquestion = ? and " +
                                    "surveyid = ? and idtm=?");
                                    ppst2.setLong(1,Long.parseLong(request.getParameter("hparent")));
                                    ppst2.setLong(2,Long.parseLong(request.getParameter("questionid")));
                                    ppst2.setLong(3,oRS.getSurveyID());
                                    ppst2.setString(4,idtm);
                                    ppst2.executeUpdate();
                                    if(ppst2!=null)ppst2.close();
                                    log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgEliminoRelacionPreguntasIds")+":"+request.getParameter("hparent")+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgY")+" "+request.getParameter("questionid")+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgDelFormularioId")+": "+oRS.getSurveyID());
                                    RecRelatedQuestion oRRQ = new RecRelatedQuestion();
                                    oRRQ.setIdtm(idtm);
                                    oRRQ.setParent(Long.parseLong(request.getParameter("parent")));
                                    oRRQ.setSon(Long.parseLong(request.getParameter("questionid")));
                                    oRRQ.setSurveyId(oRS.getSurveyID());
                                    if(!oRRQ.create()) log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_logErrorCrearRelacionPreguntas"));
                                    log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgGeneroRelacionPreguntasIds")+":"+request.getParameter("parent")+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgY")+" "+request.getParameter("questionid")+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgDelFormularioId")+": "+oRS.getSurveyID());
                                    oRRQ = null;

                                }
                            }

                        }

                    }

                    if(conexion!=null)conexion.close();
                }
                catch(Exception e )
                {log.error(paramsRequest.getLocaleString("errormsg_Survey_getAdmHtml_logErrorRevisarPreguntaRelacionada"),e);}
                finally
                {
                    ppst2=null;
                    ppst1=null;
                    conexion=null;
                }
            }
            else
            {
                if(request.getParameter("s_enviar").equals("Quitar"))
                {
                    Connection conn =null;
                    PreparedStatement pst =null;
                    PreparedStatement pstq =null;
                    ResultSet rs =null;
                    ResultSet rsS =null;
                    PreparedStatement pstda =null;
                    PreparedStatement pstdq =null;
                    PreparedStatement psts =null;
                    try
                    {
                        RecSurvey oRS = new RecSurvey();
                        oRS.setIdtm(idtm);
                        oRS.setResID(base.getId());
                        oRS.load();
                        conn = SWBUtils.DB.getDefaultConnection();
                        pst = conn.prepareStatement("DELETE FROM sr_orderquestion WHERE surveyid = ? AND questionid = ? and idtm=?");
                        pst.setLong(1,oRS.getSurveyID());
                        pst.setLong(2,(long)Integer.parseInt(request.getParameter("questionid")));
                        pst.setString(3, idtm);
                        pst.executeUpdate();
                        if(pst!=null)pst.close();
                        log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgEliminoRelacionPreguntaId")+": "+request.getParameter("questionid")+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgConFormularioId")+": "+oRS.getSurveyID());
                        pstq = conn.prepareStatement("select * from sr_question where questionid = ? and idtm=? and isreuse = 0");
                        pstq.setLong(1,(long)Integer.parseInt(request.getParameter("questionid")));
                        pstq.setString(2, idtm);
                        rs = pstq.executeQuery();
                        if(rs.next())
                        {
                            psts = conn.prepareStatement("select * from sr_orderquestion where questionid = ? and surveyid <> ? and idtm=? ");
                            psts.setLong(1,(long)Integer.parseInt(request.getParameter("questionid")));
                            psts.setLong(2,oRS.getSurveyID());
                            psts.setString(3,idtm);
                            rsS = psts.executeQuery();
                            if(rsS.next())
                            {
                                log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_logNoPuedeBorrarPreguntaHacenReferenciaOtrosFormularios"));
                            }
                            else
                            {
                                // se borra pregunta no reutilizable
                                pstda = conn.prepareStatement("Delete from sr_answer where questionid = ? and idtm=?");
                                pstda.setInt(1,Integer.parseInt(request.getParameter("questionid")));
                                pstda.setString(2, idtm);
                                pstda.executeUpdate();
                                log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgEliminoRespuestasPreguntaId")+": "+request.getParameter("questionid")+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgDelFormularioId")+": "+oRS.getSurveyID());
                                pstdq = conn.prepareStatement("Delete from sr_question where questionid = ? and idtm=?");
                                pstdq.setInt(1,Integer.parseInt(request.getParameter("questionid")));
                                pstdq.setString(2,idtm);
                                pstdq.executeUpdate();
                                if(pstda!=null)pstda.close();
                                if(pstdq!=null)pstdq.close();
                                log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgEliminoPreguntaNoReutilizableId")+":"+request.getParameter("questionid"));
                            }
                            if(rsS!=null)rsS.close();
                            if(psts!=null)psts.close();
                        }
                        if(rs!=null)rs.close();
                        if(pstq!=null)pstq.close();
                        if(conn!=null)conn.close();
                        oRS = null;
                    }
                    catch(Exception e)
                    { log.error(paramsRequest.getLocaleString("errormsg_Survey_getAdmHtml_logNoPudoQuitarPreguntaSeccion")); }
                    finally
                    {
                        pstda=null;
                        pstdq=null;
                        rsS=null;
                        psts=null;
                        rs=null;
                        pstq=null;
                        conn=null;
                    }
                }
            }
            accion = "selectquestion_en";
        }



        boolean entrarSelQuestion=true;
        if (accion.equalsIgnoreCase("orderaddXML_en"))
        {
            RecSurvey oRS = new RecSurvey();
            oRS.setIdtm(idtm);
            oRS.setResID(base.getId());
            oRS.load();
            //System.out.println("Es la encuesta null:"+(oRS==null?"true":"false"));
            RecOrderQuestion oROQ = new RecOrderQuestion();
            oROQ.setIdtm(idtm);
            oROQ.setOrdernum(Integer.parseInt(request.getParameter("indice")));
            oROQ.setSurveyid(oRS.getSurveyID());
            oROQ.setQuestionid(Long.parseLong(request.getParameter("questionid")));
            oROQ.setSubjectid(Integer.parseInt(request.getParameter("seccion")));
            if(!oROQ.create()) log.error(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_logErrorAgregarPreguntaFormularioDBSrorderquestion"));
            log.info(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAgregoPreguntaId")+": "+request.getParameter("questionid")+" "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAlFormularioId")+": "+oRS.getSurveyID());
            accion = "selectquestion_en";
            entrarSelQuestion=false;
        }
        if(parseoTemplate)
        {
            SWBResourceURL url = paramsRequest.getRenderUrl();
            url.setParameter("applet",fileparsed);
            url.setParameter("files","1");
            ret.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + url + "\">");
            //ret.append(fileparsed);
        }
        else
        {
            ret.append("\n    <script type=\"javascript\">     ");
            ret.append("\n            ");
            ret.append("\n    function jsValida(pForm){      ");
            ret.append("\n            ");
            ret.append("\n       // verificacion del tipo de ARCHIVO del template     ");
            ret.append("\n       valido=0;     ");
            ret.append("\n       re = / /gi;     ");
            ret.append("\n       tmpTemplate = pForm.ftemplate.value.replace(re,\"\");     ");
            ret.append("\n            ");
            ret.append("\n       if(tmpTemplate.length>0){     ");
            ret.append("\n              var archivo = pForm.ftemplate.value.toUpperCase();     ");
            ret.append("\n              var ext = archivo.split('.');     ");
            ret.append("\n              if(ext[ext.length-1]=='XSL' || ext[ext.length-1]=='XSLT'){     ");
            ret.append("\n                   // ARCHIVO VALIDO     ");
            ret.append("\n                  pForm.ptemplate.value=ext[ext.length-2]+'.'+ext[ext.length-1];     ");
            ret.append("\n              }     ");
            ret.append("\n              else{   valido++;  ");
            ret.append("\n                   alert('"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgFormatoInvalidoTEMPLATE")+"\\n\\n"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgSeleccionaArchivoXSL")+"');     ");
            ret.append("\n                   pForm.ftemplate.value=tmpTemplate;     ");
            ret.append("\n                   pForm.forma.ftemplate.focus();     ");
            ret.append("\n                   pForm.forma.ftemplate.select();     ");
            ret.append("\n                   return (false);     ");
            ret.append("\n              }     ");
            ret.append("\n         }     ");
            /*ret.append("\n         else{      ");
            ret.append("\n                  if(pForm.current_template.value==''){   valido++;  ");
            ret.append("\n                   alert('"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAlertTemplateObligatorio")+"\\n\\n"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgSeleccionaArchivoXSLCorrespondiente")+"');   ");
            ret.append("\n                   pForm.ftemplate.value=tmpTemplate;     ");
            ret.append("\n                   pForm.ftemplate.focus();     ");
            ret.append("\n                   pForm.ftemplate.select();     ");
            ret.append("\n                   return (false); }    ");
            ret.append("\n         }  ");*/
            ret.append("\n              "); // validacion del URL absoluto
            ret.append("\n         tmpURL = pForm.url_response.value.replace(re,\"\");     ");
            ret.append("\n              ");
            ret.append("\n         if(tmpURL.length>0){     ");
            ret.append("\n            tmpString = pForm.url_response.value;       ");
            ret.append("\n            if(tmpString.length<7){    valido++;    ");
            ret.append("\n                 alert('"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPaginaRedireccionamientoIncompleta")+"\\n"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPaginaRedireccionamientoEmpezar")+" \\'http://\\'');");
            ret.append("\n                  return(false); ");
            ret.append("\n            } else {  ");
            ret.append("\n                  if(tmpString.toLowerCase().substring(0,7)!='http://') { valido++; ");
            ret.append("\n                     alert('"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPaginaRedireccionamientoInvalida")+"\\n"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPaginaRedireccionamientoEmpezar")+" \\'http://\\'');");
            ret.append("\n                     return(false); } ");
            ret.append("\n            }   ");
            ret.append("\n         }     ");
            ret.append("\n              ");
            ret.append("\n         tmp = pForm.emailadmin.value.replace(re,\"\");     ");
            ret.append("\n              ");
            ret.append("\n         if(tmp.length>0){     ");
            ret.append("\n              if(!validateemail(pForm.emailadmin)){     valido++; ");
            ret.append("\n                   return(false);     ");
            ret.append("\n              }     ");
            ret.append("\n         }     ");
            ret.append("\n              ");

            // validaci�n de n�mero de veces a contestar

            ret.append("\n       if(pForm.onceanswered.value==null  || pForm.onceanswered.value=='' || pForm.onceanswered.value==' '){   valido++;   ");
            ret.append("\n           alert('"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgProporcionarNumeroVecesaContestar")+"');     ");
            ret.append("\n           pForm.onceanswered.focus();     ");
            ret.append("\n           pForm.onceanswered.select();     ");
            ret.append("\n           return(false);     ");
            ret.append("\n       }     ");
            ret.append("\n            ");
            ret.append("\n       if(isNaN(pForm.onceanswered.value)){       valido++;   ");
            ret.append("\n              alert('"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgSoloNumeros")+"');     ");
            ret.append("\n              pForm.onceanswered.focus();     ");
            ret.append("\n              pForm.onceanswered.select();     ");
            ret.append("\n              return(false);     ");
            ret.append("\n       }     ");
            ret.append("\n       else{     ");
            ret.append("\n          if(pForm.onceanswered.value<'0'){   valido++;   ");
            ret.append("\n              alert('"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNumerosmayoresdecero")+"');     ");
            ret.append("\n              pForm.onceanswered.focus();     ");
            ret.append("\n              pForm.onceanswered.select();     ");
            ret.append("\n              return(false);}     ");
            ret.append("\n       }     ");

            // validaci�n de tiempo para contestar

            ret.append("\n       if(pForm.timeanswer.value==null  || pForm.timeanswer.value=='' || pForm.timeanswer.value==' '){   valido++;   ");
            ret.append("\n           alert('"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgProporcionaTiempoContestar")+"');     ");
            ret.append("\n           pForm.timeanswer.focus();     ");
            ret.append("\n           pForm.timeanswer.select();     ");
            ret.append("\n           return(false);     ");
            ret.append("\n       }     ");
            ret.append("\n            ");
            ret.append("\n       if(isNaN(pForm.timeanswer.value)){     valido++;     ");
            ret.append("\n              alert('"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgSoloNumeros")+"');     ");
            ret.append("\n              pForm.timeanswer.focus();     ");
            ret.append("\n              pForm.timeanswer.select();     ");
            ret.append("\n              return(false);     ");
            ret.append("\n       }     ");
            ret.append("\n       else{     ");
            ret.append("\n          if(pForm.timeanswer.value<'0'){    valido++;  ");
            ret.append("\n              alert('"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNumerosmayoresdecero")+"');     ");
            ret.append("\n              pForm.timeanswer.focus();     ");
            ret.append("\n              pForm.timeanswer.select();     ");
            ret.append("\n              return(false);}     ");
            ret.append("\n       }     ");

            ret.append("\n       if(pForm.showquestion.value==null  || pForm.showquestion.value==' '){   valido++;   ");
            ret.append("\n           alert('"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgProporcionarNumeroPreguntasEncuesta")+"');     ");
            ret.append("\n           pForm.showquestion.focus();     ");
            ret.append("\n           return(false);     ");
            ret.append("\n       }     ");
            ret.append("\n            ");
            ret.append("\n       if(isNaN(pForm.showquestion.value)){      valido++;    ");
            ret.append("\n              alert('"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNumeroPreguntasNumerico")+"');     ");
            ret.append("\n              return(false);     ");
            ret.append("\n       }     ");
            ret.append("\n       else{     ");
            ret.append("\n          if(pForm.showquestion.value=='0'){   valido++;   ");
            ret.append("\n              alert('"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgCERONoPermitidoCambialo")+"');     ");
            ret.append("\n              return(false);}     ");
            ret.append("\n       }     ");

//            ret.append("\n          if(pForm.lastchance.checked==false){     ");
//            ret.append("\n              pForm.lastchance.value='0';     ");
//            ret.append("\n              pForm.lastchance.checked=true;     ");
//            ret.append("\n              }     ");
//            ret.append("\n           busca = /\\r/gi;");
//            ret.append("\n           buscaret = /\\n/gi;");
//            ret.append("\n           cambia = \"<br>\";");
//            ret.append("\n           tempo = pForm.descripcion.value.replace(busca,cambia);");
//            ret.append("\n           pForm.descripcion.value = tempo.replace(buscaret,\"\");");
//
//            ret.append("\n           tempo = pForm.instrucciones.value.replace(busca,cambia);");
//            ret.append("\n           pForm.instrucciones.value = tempo.replace(buscaret,\"\");");
//
//            ret.append("\n           tempo = pForm.agradecimiento.value.replace(busca,cambia);");
//            ret.append("\n           pForm.agradecimiento.value = tempo.replace(buscaret,\"\");");
            ret.append("\n           ");
//            ret.append("\n      replaceChars(pForm.descripcion);");
            ret.append("\n      replaceChars(pForm.instrucciones);");
            ret.append("\n      replaceChars(pForm.agradecimiento);");

            ret.append("\n              //  DATOS VALIDOS SE ENVIA LA FORMA     ");
            ret.append("\n       return (true);    ");
            ret.append("\n           ");
            ret.append("\n    }     ");
            ret.append("\n         ");

            //re = / /gi;     ");
            //ret.append("\n       tmpTemplate = pForm.ftemplate.value.replace(re,\"\");

            ret.append("\n    function replaceChars(pIn){");
            ret.append("\n      out = \"\\r\"; // replace this");
            ret.append("\n      add = \"<br>\"; // with this");
            ret.append("\n      temp = \"\" + pIn.value; // temporary holder");
            ret.append("\n      while (temp.indexOf(out)>-1)");
            ret.append("\n      {");
            ret.append("\n          pos= temp.indexOf(out);");
            ret.append("\n          temp = \"\" + (temp.substring(0, pos) + add + temp.substring((pos + out.length), temp.length));");
            ret.append("\n      }");
            ret.append("\n      out = \"\\n\"; // replace this");
            ret.append("\n      add = \"\"; // with this");
            ret.append("\n      temp = \"\" + temp; // temporary holder");
            ret.append("\n      while (temp.indexOf(out)>-1)");
            ret.append("\n      {");
            ret.append("\n          pos= temp.indexOf(out);");
            ret.append("\n          temp = \"\" + (temp.substring(0, pos) + add + temp.substring((pos + out.length), temp.length));");
            ret.append("\n      }");
            ret.append("\n      pIn.value = temp;");
            ret.append("\n    }");

            ret.append("\n    function deshabilita(pForma){     ");
            ret.append("\n         if(pForma.anonimo.checked==true){     ");
            ret.append("\n              alert('"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgFormularioANONIMO")+" \\n\\n+ "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgSoloAplicaTipoDesplegado")+" \\\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgTodaEncuesta")+"\\\"\\n\\n+ "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNoAplicaEnvioEmailUsuario")+"');     ");
            ret.append("\n              pForma.showdisplay.value=3;     ");
            ret.append("\n              pForma.showdisplay.disabled=true;     ");
            ret.append("\n              pForma.sendemail.checked=false;     ");
            ret.append("\n              pForma.sendemail.disabled=true;     ");
            ret.append("\n         }     ");
            ret.append("\n         else{     ");
            ret.append("\n              pForma.showdisplay.disabled=false;     ");
            ret.append("\n              pForma.sendemail.disabled=false;     ");
            ret.append("\n         ");
            ret.append("\n         }     ");
            ret.append("\n    }       ");
            ret.append("\n         ");

            ret.append("\n         ");

            ret.append("\n         ");
            ret.append("\n    function validateemail(field) {     ");
            ret.append("\n         if (field.value.length > 0){     ");
            ret.append("\n              if (field.value.indexOf ('@',0) == -1 || field.value.indexOf ('.',0) == -1 || field.value.length < 5){     ");
            ret.append("\n                   alert('                 "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgEmailNoValido")+" '      +     ");
            ret.append("\n                             '     \\n\\n "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgIngreseDireccionCorreoCorrectamente")+" '      +     ");
            ret.append("\n                             '     \\n\\n "+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgFormatoEsElSiguiente")+" '     )     ");
            ret.append("\n                   field.focus();     ");
            ret.append("\n                   field.select();     ");
            ret.append("\n                   return(false);     ");
            ret.append("\n              }     ");
            ret.append("\n              else{     ");
            ret.append("\n                   if (field.value.indexOf ('@',0) == 0 || field.value.indexOf ('.',0) == 0){     ");
            ret.append("\n                        alert('\\n"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgEmailNoValido")+"\\n\\n"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgIngreseDireccionCorreoCorrectamente")+"\\n\\n"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgFormatoEsElSiguiente")+" ')     ");
            ret.append("\n                        field.focus();     ");
            ret.append("\n                        field.select();     ");
            ret.append("\n                        return(false);     ");
            ret.append("\n                   }     ");
            ret.append("\n                   else{     ");
            ret.append("\n                        var valid = '�ABCD�EFGH�IJKLMN��OPQRST�UVWXYZ�abcd�efgh�ijklmn��opqrst�uvwxyz0123456789.-_@,'");
            ret.append("\n                        var ok = 'yes' ;     ");
            ret.append("\n                        var temp;     ");
            ret.append("\n                        for (var i=0; i < field.value.length; i++) {     ");
            ret.append("\n                             temp = ''+ field.value.substring(i, i+1);     ");
            ret.append("\n                             if (valid.indexOf(temp) == '-1'     ) {     ");
            ret.append("\n                                  ok = 'no';     ");
            ret.append("\n                                  }     ");
            ret.append("\n                        }     ");
            ret.append("\n                        if (ok == 'no') {     ");
            ret.append("\n                             alert('"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgCaracterInvalido")+"');     ");
            ret.append("\n                             field.focus();     ");
            ret.append("\n                             field.select();     ");
            ret.append("\n                             return(false);     ");
            ret.append("\n                        }     ");
            ret.append("\n                        else{     ");
            ret.append("\n                             return(true);     ");
            ret.append("\n                        }     ");
            ret.append("\n                   }     ");
            ret.append("\n              }     ");
            ret.append("\n         }     ");
            ret.append("\n         else{      ");
            ret.append("\n              return(true);     ");
            ret.append("\n         }     ");
            ret.append("\n    }      ");
            ret.append("\n                   ");
            ret.append("\n     function califica(MiForma) {             ");
            ret.append("\n                   ");
            ret.append("\n        if(MiForma.tipo.value=='2'){              ");
            ret.append("\n                   MiForma.calificacion.disabled = false; ");
            ret.append("\n                   MiForma.moderate[0].disabled = false; ");
            ret.append("\n                   MiForma.moderate[1].disabled = false; ");
            ret.append("\n        }           ");
            ret.append("\n         else{          ");
            ret.append("\n                   MiForma.calificacion.disabled = true; ");
            ret.append("\n                   MiForma.moderate[0].disabled = true; ");
            ret.append("\n                   MiForma.moderate[1].disabled = true; ");
            ret.append("\n         }          ");
            ret.append("\n    }               ");
            ret.append("\n                   ");
            ret.append("\n    function trim(field) {     ");
            ret.append("\n         var retVal = '';     ");
            ret.append("\n         var start = 0;     ");
            ret.append("\n         while ((start < field.value.length) && (field.value.charAt(start) == ' ')) {     ");
            ret.append("\n              ++start;     ");
            ret.append("\n         }     ");
            ret.append("\n         var end = field.value.length;     ");
            ret.append("\n         while ((end > 0) && (field.value.charAt(end - 1) == ' ')) {     ");
            ret.append("\n              --end;     ");
            ret.append("\n         }     ");
            ret.append("\n         retVal = field.value.substring(start, end);     ");
            ret.append("\n         if (end == 0)     ");
            ret.append("\n              field.value='';     ");
            ret.append("\n         else     ");
            ret.append("\n              field.value=retVal;          ");
            ret.append("\n    }              ");
            ret.append("\n    function doBlockTime(pForma){");
            ret.append("\n      if(pForma.unlimited_time.checked==true){");
            ret.append("\n          pForma.timeanswer.value = '0';");
            ret.append("\n          pForma.timeanswer.disabled = true;");
            ret.append("\n      }");
            ret.append("\n      else{");
            ret.append("\n          pForma.timeanswer.value = '';");
            ret.append("\n          pForma.timeanswer.disabled = false;");
            ret.append("\n      }");
            ret.append("\n    }");
            ret.append("\n    function doBlockAnswer(pForma){");
            ret.append("\n      if(pForma.unlimited_answer.checked==true){");
            ret.append("\n          pForma.onceanswered.value = '0';");
            ret.append("\n          pForma.onceanswered.disabled = true;");
            ret.append("\n      }");
            ret.append("\n      else{");
            ret.append("\n          pForma.onceanswered.value = '';");
            ret.append("\n          pForma.onceanswered.disabled = false;");
            ret.append("\n      }");
            ret.append("\n    }");
            ret.append("\n    </script>     ");

            if (accion.equals("update_en"))
            {
                // secci�n de actualizaci�n
                RecSurvey objG = new RecSurvey();
                objG.setIdtm(idtm);
                objG.setResID(base.getId());
                objG.load();
                if (objG!=null)
                {
                    String temp =new String("");
                    SWBResourceURL url = paramsRequest.getRenderUrl();
                    url.setMode(url.Mode_ADMIN);
                    url.setAction("updategroup_en");
                    ret.append("\n<div class=\"swbform\">");
                    ret.append("\n<form name=\"forma\"  enctype=\"multipart/form-data\" action=\""+url+"\" method=\"POST\" >");
                    ret.append("\n<fieldset>");
                    ret.append("<input type=hidden name=\"id\" value=\""+objG.getSurveyID()+"\">");
                    ret.append("<table border=0 cellspacing=0 cellpadding=5 width=\"100%\">");
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgTitulo")+":</td><td >"+base.getTitle()+"</td></tr>");
                    //String descripcion = "";
                    String tempS="";
                    if(base.getDescription()!=null)tempS=base.getDescription();
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgDescripcion")+":</td><td >"+tempS+"</td></tr>");
                    tempS=base.getAttribute("agradecimiento","");
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgMensajeAgradecimiento")+":</td><td ><textarea id=agradecimiento name=agradecimiento rows=5 cols=45 style=\"FONT-WEIGHT:normal; FONT-SIZE:10pt; COLOR:#000000; FONT-FAMILY:verdana,arial,helvetica,sans-serif; BACKGROUND-COLOR:#ffffff\" maxlength=200>"+tempS.replaceAll("<br>","\r")+"</textarea></td></tr>");
                    tempS=base.getAttribute("instructions","");
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgInstrucciones")+":</td><td ><textarea id=instrucciones name=instrucciones rows=5 cols=45 style=\"FONT-WEIGHT:normal; FONT-SIZE:10pt; COLOR:#000000; FONT-FAMILY:verdana,arial,helvetica,sans-serif; BACKGROUND-COLOR:#ffffff\" maxlength=200>"+tempS.replaceAll("<br>","\r")+"</textarea></td></tr>");
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgTipo")+":</td><td >"+getTypeTitle(objG.getTypeID(),paramsRequest) +"<input type=\"hidden\" name=\"tipo\" value=\""+objG.getTypeID()+"\"></td></tr>");
                    tempS="";

                    String tempVer = "";
                    if(objG.getTypeID()==1)
                    {
                        tempS=" disabled ";
                        tempVer = "disabled = \"true\" ";
                    }
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgCalificacionMinimaAprobatoria")+":</td><td ><input type=\"text\" name=\"calificacion\" value=\""+objG.getMinScore()+"\" "+tempS+" size=\"3\" maxlength=5></td></tr>");
                    //tempS=Integer.toString(objG.getMaxAnswer());
                    tempS="";
                    tempVer="";
                    if(objG.getMaxAnswer() == 0){
                        tempS =" checked";
                        tempVer = " disabled";
                    }
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgContestarSoloUnaVez")+":</td><td ><input type=\"text\" name=\"onceanswered\" value=\""+objG.getMaxAnswer()+"\" size=3 maxlength=3 "+tempVer+">");
                    ret.append("&nbsp;<input type=\"checkbox\" id=\"unlimited_answer\" name=\"unlimited_answer\" value=\"1\" onclick=\"javascript: doBlockAnswer(forma);\" "+tempS+">&nbsp;" + paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNumeroIlimitado") + "</td>");
                    ret.append("</td></tr>");


                    tempS="";
                    if(base.getAttribute("lastchance","0").equals("1")) tempS=" checked";
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgLastChance")+":</td><td ><input type=\"checkbox\" name=\"lastchance\" value=\"1\" "+tempS+"><input type=hidden name=tmpLastChance value=0></td></tr>");
                    tempS="";
                    tempVer="";
                    if(objG.getTimeAnswer() == 0){
                        tempS=" checked";
                        tempVer = " disabled";
                    }
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgTimeAnswer")+":</td><td ><input type=\"text\" name=\"timeanswer\" value=\""+objG.getTimeAnswer()+"\" size=\"3\" maxlength=3 "+tempVer+">");
                    ret.append("&nbsp;<input type=\"checkbox\" id=\"unlimited_time\" name=\"unlimited_time\" value=\"1\" onclick=\"javascript: doBlockTime(forma);\" "+tempS+">&nbsp;" + paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgTiempoIlimitado") + "</td>");
                    ret.append("</td></tr>");

                    tempS=base.getAttribute("template","");
                    ret.append("\n<tr><td class=\"valores\" width=\"200\" align=\"right\">&nbsp;</td>");
                    if (base.getAttribute("path").indexOf(base.getWorkPath()) !=-1){
                        ret.append("<td class=\"valores\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgActual")+" <a href=\"" + base.getAttribute("path") + tempS +"\">"+tempS+"</a></tr>");
                    }
                    else{
                        ret.append("<td class=\"valores\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPlantillaDefecto")+" <a href=\""+ base.getAttribute("path") + "MainSurvey.xsl\">MainSurvey.xsl</a></tr>");
                    }
                    ret.append("\n<tr><td class=\"valores\" width=\"200\" align=\"right\">" +paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgTemplate")+":</td>");
                    ret.append("<td><input type=\"file\" name=\"ftemplate\" size=\"37\"><input type=\"hidden\" name=\"ptemplate\" value=0><input type=\"hidden\" name=\"current_template\" value=\""+tempS+"\"></td></tr>");

                    tempS=base.getAttribute("url_response","");
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPaginaRedireccionamiento")+" :</td><td ><input type=\"text\" name=\"url_response\" value=\""+tempS+"\" size=60 maxlength=200></td></tr>");
                    tempS = "";
                    if(base.getAttribute("show_pending","").equals("1")) tempS=" checked";
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgMostrarPendientes")+" :</td><td ><input type=\"checkbox\" name=\"show_pending\" value=\"1\" "+ tempS+"></td></tr>");
                    // si sendemail == 1, poner checked, si es cero no poner checked
                    String tempSEmail = "";
                    if (base.getAttribute("sendemail").equals("1")) tempSEmail= "checked";
                    temp="";
                    String tmpDis ="";
                    int tmpTDes=Integer.parseInt(base.getAttribute("showdisplay","0"));
                    if(base.getAttribute("anonimo","0").equals("1"))
                    {
                        temp = " checked";
                        tmpDis = "disabled";
                        tmpTDes=3;
                        tempSEmail="";
                    }
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAnonimo")+":</td><td ><input type=\"checkbox\" name=\"anonimo\" value=\"1\" "+temp+" onclick=\"deshabilita(forma);\"></td></tr>");
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgEnvioCorreoElectronicoUsuario")+":</td><td ><input type=\"checkbox\" name=\"sendemail\" value=\"1\" "+tempSEmail+" "+tmpDis+"></td></tr>");
                    tempS=base.getAttribute("emailadmin","");
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgCorreoElectronicoAdministrador")+":</td><td ><input type=\"text\" name=\"emailadmin\" value=\""+tempS+"\" size=60 maxlength=60></td></tr>");
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgTipoDesplegado")+":</td><td>"+getDisplayList(tmpTDes,tmpDis,paramsRequest)+"</td></tr>");
                    temp="";
                    String tempE="";
                    if(base.getAttribute("moderate").equals("1")) temp="checked";
                    else tempE = " checked ";
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgModoEvaluacion")+":</td><td ><font size=\"1\"><b><input type=\"radio\" name=\"moderate\" value=\"1\" "+temp+" "+tempVer+">&nbsp;"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgModerado")+"&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"radio\" name=\"moderate\" value=\"0\" "+tempE+" "+tempVer+">&nbsp;"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgEstricto")+"</b></font></td></tr>");
                    tempS=base.getAttribute("showquestion","");
                    if("0".equals(tempS)) tempS="";
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNumeroPreguntas")+":</td><td ><input type=\"text\" name=\"showquestion\" value=\""+tempS+"\"  size=3 maxlength=3></font></td></tr>");
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgEstatus")+":</td><td>"+getStatusList(Integer.parseInt(base.getAttribute("status","0")), paramsRequest)+"</font></td></tr>");

                    SWBResourceURL urlf = paramsRequest.getRenderUrl();
                    urlf.setMode(urlf.Mode_ADMIN);
                    //urlf.setWindowState(urlf.WinState_NORMAL);
                    urlf.setAction("re_formulario");
                    urlf.setParameter("surveyid",Long.toString(objG.getSurveyID()));

                    SWBResourceURL urlc = paramsRequest.getRenderUrl();
                    urlc.setMode(urlc.Mode_ADMIN);
                    //urlc.setWindowState(urlc.WinState_NORMAL);
                    urlc.setAction("catalogos");

                    SWBResourceURL urlsq = paramsRequest.getRenderUrl();
                    urlsq.setMode(urlsq.Mode_ADMIN);
                    urlsq.setAction("selectquestion_en");
                    //urlsq.setWindowState(urlsq.WinState_NORMAL);
                    urlsq.setParameter("id",Long.toString(objG.getSurveyID()));

                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgDetalle")+":</td><td><a href=\""+urlsq+"\"><img src=\""+SWBPlatform.getContextPath()+"/swbadmin/icons/editar_1.gif\" border=0 alt=\""+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgDetalle")+"\"></a></td></tr>");
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgReportes")+":</td><td><a href=\""+urlf+"\"><img src=\""+SWBPlatform.getContextPath()+"/swbadmin/icons/editar_1.gif\" border=0 alt=\""+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgReportes")+"\"></a></td></tr>");
                    ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgCatalogos")+":</td><td><a href=\""+urlc+"\"><img src=\""+SWBPlatform.getContextPath()+"/swbadmin/icons/editar_1.gif\" border=0 alt=\""+paramsRequest.getLocaleString("usrmsg_MainSurvey_getAdmHtml_msgCatalogos")+"\"></a></td></tr>");
                    ret.append("</table>");
                    ret.append("\n</fieldset>");
                    ret.append("\n<fieldset>");
                    ret.append("\n<input type=\"button\" name=\"enviar\" value=\""+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_btnActualizar")+"\" onclick=\"javascript: if(jsValida(forma)){this.form.submit();}\"/>");
                    ret.append("\n</fieldset>");
                    ret.append("</form>");
                    ret.append("\n</div>");
                    objG=null;
                }
            }



            if (accion.equalsIgnoreCase("add_en")){
                SWBResourceURL url = paramsRequest.getRenderUrl();
                url.setMode(SWBResourceURL.Mode_ADMIN);
                url.setAction("agregar_en");

                // secci�n de agregar Formulario
                RecSurvey oRS = new RecSurvey();
                oRS.setIdtm(idtm);
                ret.append("\n<div class=\"swbform\">");
                ret.append("\n<form id=\"forma\" name=\"forma\" enctype=\"multipart/form-data\" action=\""+url+"\" method=\"POST\" >");
                ret.append("\n<fieldset>");
                ret.append("<table border=0 cellspacing=0 cellpadding=2 width=\"100%\">");
                //ret.append("<tr><td colspan=2><font size=2 color=\"#666699\"><b>Agregar Formulario</b></font></td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgTitulo")+":</td><td >"+base.getTitle()+"</td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgDescripcion")+":</td><td >"+base.getDescription()+"</td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgMensajeAgradecimiento")+":</td><td ><textarea id=agradecimiento name=agradecimiento rows=5 cols=45 style=\"FONT-WEIGHT:normal; FONT-SIZE:10pt; COLOR:#000000; FONT-FAMILY:verdana,arial,helvetica,sans-serif; BACKGROUND-COLOR:#ffffff\" maxlength=200></textarea></td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgInstrucciones")+":</td><td ><textarea id=instrucciones name=instrucciones rows=5 cols=45 style=\"FONT-WEIGHT:normal; FONT-SIZE:10pt; COLOR:#000000; FONT-FAMILY:verdana,arial,helvetica,sans-serif; BACKGROUND-COLOR:#ffffff\" maxlength=2000></textarea></td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgTipo")+":</td><td >"+oRS.getTypeList(0, " onchange=\"califica(forma)\" ")+"</td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgCalificacionMinimaAprobatoria")+":</td><td ><input type=\"text\" name=\"calificacion\" value=\"0\" size=\"3\" maxlength=5></td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgContestarSoloUnaVez")+":</td>");
                ret.append("<td ><input type=\"text\" name=\"onceanswered\" value=\"\" size=\"3\" maxlength=3>&nbsp;<input type=\"checkbox\" id=\"unlimited_answer\" name=\"unlimited_answer\" value=\"1\" onclick=\"javascript: doBlockAnswer(forma);\">&nbsp;"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNumeroIlimitado")+"</td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgLastChance")+":</td><td ><input type=\"checkbox\" name=\"lastchance\" value=\"1\" ><input type=hidden name=tmpLastChance value=0></td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgTimeAnswer")+":</td>");
                ret.append("<td ><input type=\"text\" name=\"timeanswer\" value=\"\" size=\"3\" maxlength=3>&nbsp;<input type=\"checkbox\" id=\"unlimited_time\" name=\"unlimited_time\" value=\"1\" onclick=\"javascript: doBlockTime(forma);\">&nbsp;"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgTiempoIlimitado")+"</td>");
                ret.append("</tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">&nbsp;</td><td >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPlantillaDefecto")+" <a href=\""+ base.getAttribute("path") + "MainSurvey.xsl\">MainSurvey.xsl</a></td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgTemplate")+":</td><td ><input type=\"hidden\" name=\"ptemplate\" value=0><input type=\"hidden\" name=\"current_template\" value=\"\"><input type=\"file\" name=\"ftemplate\"  size=\"45\" ></td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPaginaRedireccionamiento")+":</td><td ><input type=\"text\" name=\"url_response\" value=\"\" size=\"60\" maxlength=200></td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgMostrarPendientes")+":</td><td ><input type=\"checkbox\" name=\"show_pending\" value=\"1\"></td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAnonimo")+":</td><td ><input type=\"checkbox\" name=\"anonimo\" value=\"1\"  onclick=\"deshabilita(forma);\"></td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgEnvioCorreoElectronicoUsuario")+":</td><td ><input type=\"checkbox\" name=\"sendemail\" value=\"1\"></td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgCorreoElectronicoAdministrador")+":</td><td ><input type=\"text\" name=\"emailadmin\" value=\"\" size=\"60\" maxlength=60></td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgTipoDesplegado")+":</td><td >"+getDisplayList(0,"",paramsRequest)+"</td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgModoEvaluacion")+":</td><td ><font size=\"1\" color=\"#666699\"><b><input type=\"radio\" name=\"moderate\" value=\"1\" checked>&nbsp;"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgModerado")+"&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"radio\" name=\"moderate\" value=\"0\" >&nbsp;"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgEstricto")+"</b></font></td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNumeroPreguntas")+":</td><td ><input type=\"text\" name=\"showquestion\" value=\"\" size=\"3\" maxlength=3></td></tr>");
                ret.append("\n<tr><td  width=\"200\" align=\"right\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgEstatus")+":</td><td >"+getStatusList(0,paramsRequest)+"</td></tr>");

                ret.append("</table>");
                ret.append("\n</fieldset>");
                ret.append("\n<fieldset>");
                ret.append("\n<input type=\"button\" name=\"enviar\" value=\""+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgAgregar")+"\" onclick=\"javascript: if(jsValida(forma)){ window.document.forma.submit();}\">");
                ret.append("<input type=\"hidden\" name=\"topic\" value=\""+request.getParameter("topic") +"\"><input type=\"hidden\" name=\"tm\" value=\""+request.getParameter("tm") +"\">");
                ret.append("\n</fieldset>");
                ret.append("</form>");
                ret.append("\n</div>");
                oRS=null;
            }


            if (accion.equalsIgnoreCase("selectquestion_en"))
            {
                //ret.append("<font size=2><b>Preguntas Relacionadas</b></font><hr>");
                if(request.getParameter("idp")!=null )
                {
                    ret.append("\n    <script type=\"javascript\">    ");
                    ret.append("\n        ");
                    ret.append("\n    function valida(forma){    ");
                    ret.append("\n     var error=0;    ");
                    ret.append("\n     var opciones = (forma.length-8)/3;    ");
                    ret.append("\n     var capturados = new Array();    ");
                    ret.append("\n     var x=0;    ");
                    ret.append("\n     var porcentaje=0;    ");
                    ret.append("\n     var xml = new String(\"\");    ");
                    ret.append("\n         ");
                    ret.append("\n     var tempo;    ");
                    ret.append("\n     tempo = forma.indice.value;    ");
                    ret.append("\n     trim(forma.indice);    ");
                    ret.append("\n     if(forma.indice.value==\"\"){    ");
                    ret.append("\n         alert(\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgCapturarNumeroPregunta")+"\");    ");
                    ret.append("\n         forma.indice.value=tempo;    ");
                    ret.append("\n         forma.indice.focus();    ");
                    ret.append("\n         forma.indice.select();    ");
                    ret.append("\n         return(false);    ");
                    ret.append("\n     }");
                    ret.append("\n     else{");
                    ret.append("\n          if(isNaN(forma.indice.value)){");
                    ret.append("\n               alert(\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNumeroPreguntaValorNumerico")+"\");");
                    ret.append("\n               forma.indice.value=tempo;    ");
                    ret.append("\n               forma.indice.focus();    ");
                    ret.append("\n               forma.indice.select();    ");
                    ret.append("\n               return(false);    ");
                    ret.append("\n          }");
                    ret.append("\n     } ");
                    ret.append("\n     //validacion opciones    ");
                    ret.append("\n     for (cnt = 5; cnt < forma.length-3; cnt++){    ");
                    ret.append("\n       tempo=forma.elements[cnt].value;");
                    ret.append("\n       trim(forma.elements[cnt]);");
                    ret.append("\n      if(forma.elements[cnt].value==\"\"){    ");
                    ret.append("\n       alert(\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgCapturarTodaInformacion")+"\");    ");
                    ret.append("\n        forma.elements[cnt].value=tempo;");
                    ret.append("\n        forma.elements[cnt].focus();    ");
                    ret.append("\n        forma.elements[cnt].select();    ");
                    ret.append("\n       error = 1    ");
                    ret.append("\n       break;    ");
                    ret.append("\n      }    ");
                    ret.append("\n      else{    ");
                    ret.append("\n       if(isNaN(forma.elements[cnt].value)){    ");
                    ret.append("\n        alert(\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgSoloNumeros")+"\");    ");
                    ret.append("\n        forma.elements[cnt].focus();    ");
                    ret.append("\n        forma.elements[cnt].select();    ");
                    ret.append("\n        error=1;    ");
                    ret.append("\n        break;    ");
                    ret.append("\n       }    ");
                    ret.append("\n       else{    ");
                    ret.append("\n        if(forma.elements[cnt].name==\"valor\"&&!isNaN(forma.elements[cnt].value)){    ");
                    ret.append("\n        porcentaje=parseFloat(porcentaje)+parseFloat(forma.elements[cnt].value);    ");
                    ret.append("\n        }    ");
                    ret.append("\n        else{    ");
                    ret.append("\n         if(forma.elements[cnt].name==\"orden\"&&!isNaN(forma.elements[cnt].value)){    ");
                    ret.append("\n          if(forma.elements[cnt].value==0){    ");
                    ret.append("\n             alert(\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgOrdenCERONoPermitido")+"\");    ");
                    ret.append("\n             forma.elements[cnt].focus();    ");
                    ret.append("\n             forma.elements[cnt].select();    ");
                    ret.append("\n             error=1;    ");
                    ret.append("\n             break;    ");
                    ret.append("\n           }    ");
                    ret.append("\n          if(forma.elements[cnt].value>opciones){    ");
                    ret.append("\n           alert(\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgORDENPregunta")+"\\n"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNumeroMaximoDebeSer")+": \"+opciones+\".\\n"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPorFavorCambialo")+"\");    ");
                    ret.append("\n           forma.elements[cnt].focus();    ");
                    ret.append("\n           forma.elements[cnt].select();    ");
                    ret.append("\n           error=1;    ");
                    ret.append("\n           break;    ");
                    ret.append("\n          }    ");
                    ret.append("\n          else{    ");
                    ret.append("\n               ");
                    ret.append("\n           for (i=0; i<capturados.length;i++){    ");
                    ret.append("\n            if(forma.elements[cnt].value==capturados[i]){    ");
                    ret.append("\n             alert(\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNoPuedeRepetirOrden")+"\\n"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgCambialoPorFavor")+"\");    ");
                    ret.append("\n             forma.elements[cnt].focus();    ");
                    ret.append("\n             forma.elements[cnt].select();    ");
                    ret.append("\n             error=1;    ");
                    ret.append("\n             break;    ");
                    ret.append("\n            }    ");
                    ret.append("\n           }    ");
                    ret.append("\n           x++;    ");
                    ret.append("\n           capturados[x]=forma.elements[cnt].value;    ");
                    ret.append("\n          }    ");
                    ret.append("\n             ");
                    ret.append("\n         }    ");
                    ret.append("\n        }    ");
                    ret.append("\n       }    ");
                    ret.append("\n       // aqui se genera el string    ");
                    ret.append("\n       if(forma.elements[cnt].name==\"refer\"||forma.elements[cnt].name==\"orden\"||forma.elements[cnt].name==\"valor\"){    ");
                    ret.append("\n        if(forma.elements[cnt].name==\"orden\"){     ");
                    ret.append("\n         separador=\"|\";    ");
                    ret.append("\n        }    ");
                    ret.append("\n        else{    ");
                    ret.append("\n         separador=\"-\";    ");
                    ret.append("\n        }    ");
                    ret.append("\n        xml += forma.elements[cnt].value + separador;        ");
                    ret.append("\n       }    ");
                    ret.append("\n           ");
                    ret.append("\n      }    ");
                    ret.append("\n     }    ");
                    ret.append("\n     if(error==1){    ");
                    ret.append("\n      return(false);    ");
                    ret.append("\n     }    ");

                    //boolean booOpEval = true;
                    //String strOpEval = "; Display:none";
//                    RecSurvey oRSsesion = new RecSurvey();
//                    oRSsesion.setIdtm(idtm);
//                    oRSsesion.setResID(base.getId());
//                    oRSsesion.load();
//                    if(oRSsesion.getTypeID()!=1)
//                    {
//                        //ret.append("\n     if(porcentaje!=100){    ");
//                        //ret.append("\n      alert(\"La suma del valor debe ser 100%\\nLa suma actual es: \"+porcentaje+\"%\");    ");
//                        //ret.append("\n      return(false);    ");
//                        //ret.append("\n     }    ");
//                        //strOpEval = "";
//                        //booOpEval = false;
//                    }

                    ret.append("\n     forma.xml.value=xml;    ");
                    //ret.append("\n     alert('String xml ... '+forma.xml.value);    ");

                    ret.append("\n    return(true);    ");
                    ret.append("\n    }    ");
                    ret.append("\n        ");
                    ret.append("\n    function enviar(miforma){    ");
                    ret.append("\n         miforma.submit();    ");
                    ret.append("\n        ");
                    ret.append("\n    }    ");
                    ret.append("\n        ");
                    ret.append("\n        function trim(field) {    ");
                    ret.append("\n          var retVal = \"\";    ");
                    ret.append("\n          var start = 0;    ");
                    ret.append("\n          while ((start < field.value.length) && (field.value.charAt(start) == ' ')) {    ");
                    ret.append("\n            ++start;    ");
                    ret.append("\n          }    ");
                    ret.append("\n          var end = field.value.length;    ");
                    ret.append("\n          while ((end > 0) && (field.value.charAt(end - 1) == ' ')) {    ");
                    ret.append("\n            --end;    ");
                    ret.append("\n          }    ");
                    ret.append("\n          retVal = field.value.substring(start, end);    ");
                    ret.append("\n          if (end == 0)    ");
                    ret.append("\n           field.value=\"\";    ");
                    ret.append("\n          else    ");
                    ret.append("\n           field.value=retVal;     ");
                    ret.append("\n         }    ");
                    ret.append("\n             ");
                    //ret.append("\n         function ventana(accion,sizze){    ");
                    //ret.append("\n             ");
                    //ret.append("\n              window.open(\""+url+"/\"+accion+\"&jsession="+request.getSession().getId()+"\",\"tinyWindow\",sizze);    ");
                    //ret.append("\n         }    ");
                    ret.append("\n        ");
                    ret.append("\n         function regreso(param){    ");
                    ret.append("\n             ");
                    ret.append("\n            nparam = new String(param); ");
                    ret.append("\n            var inicial =nparam.split('='); ");
                    ret.append("\n             ");
                    ret.append("\n            if(inicial[0]=='seccion') miforma.hseccion.value=inicial[1];  ");
                    SWBResourceURL url3 = paramsRequest.getRenderUrl();
                    url3.setMode(SWBResourceURL.Mode_ADMIN);
                    url3.setAction("selectquestion_en");
                    ret.append("\n              miforma.action=\""+url3+"\";    ");
                    ret.append("\n              miforma.submit();    ");
                    ret.append("\n             ");
                    ret.append("\n         }    ");
                    ret.append("\n    </script>    ");
                }

                ret.append("\n<script type=\"javascript\">");
                ret.append("\n    function validaIndice(forma){");
                ret.append("\n     var tempo = forma.indice.value;");
                ret.append("\n     trim(forma.indice);");
                ret.append("\n     if(forma.indice.value!=\"\"){");
                ret.append("\n      if (isNaN(forma.indice.value)){");
                ret.append("\n       alert(\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgIndiceValorNumerico")+"\");");
                ret.append("\n       forma.indice.value=tempo;");
                ret.append("\n       forma.indice.focus();");
                ret.append("\n       forma.indice.select();");
                ret.append("\n       return(false);");
                ret.append("\n      } ");
                ret.append("\n     }");
                ret.append("\n    else{");
                ret.append("\n      alert(\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNecesarioIndicePregunta")+"\");");
                ret.append("\n      forma.indice.value=tempo;");
                ret.append("\n      forma.indice.focus();");
                ret.append("\n      forma.indice.select();");
                ret.append("\n      return(false);");
                ret.append("\n     }");
                ret.append("\n     return(true);");
                ret.append("\n    }");
                ret.append("\n    ");

                ret.append("\n         function ventana(accion,sizze){    ");
                ret.append("\n             ");
                ret.append("\n              window.open(accion+\"&jsession="+request.getSession().getId()+" \",\"tinyWindow\",sizze);    ");
                ret.append("\n         }    ");
                ret.append("\n        ");
                SWBResourceURL urlb = paramsRequest.getRenderUrl();
                urlb.setMode(SWBResourceURL.Mode_ADMIN);
                urlb.setAction("selectquestion_en");
                ret.append("\n         function regresa(){    ");
                ret.append("\n             ");
                ret.append("\n              //alert(\"Se agregar� nueva secci�n\\ntitulo:\"+titulo+\"\\ndescripcion: \"+descripcion);    ");
                ret.append("\n              formaRegreso.action=\""+urlb+"\";    ");
                ret.append("\n              formaRegreso.submit();    ");
                ret.append("\n             ");
                ret.append("\n         }    ");

                ret.append("\n         function enviaValor(forma,valor){    ");
                ret.append("\n             ");
                ret.append("\n              forma.s_enviar.value=valor;    ");
                ret.append("\n              forma.submit();    ");
                ret.append("\n             ");
                ret.append("\n         }    ");
                //
                ret.append("\n</script>");

                ret.append("\n<div class=\"swbform\">");
                
                ret.append("\n<form name=\"formaRegreso\" action=\"selectquestion_en\" method=\"GET\"></form>");

                RecSurvey oRS = new RecSurvey();
                oRS.setIdtm(idtm);
                oRS.setResID(base.getId());
                oRS.load();

                ret.append("\n<table border=0 width=100% cellpadding=2 cellspacing=0><tr><td>");

                ret.append("\n<fieldset>");
                ret.append("\n<legend>"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgInformacionGeneral")+"</legend>");
                ret.append("\n<table border=0  cellpadding=2 cellspacing=0 width=\"100%\">");
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgTitulo")+":</td><td >"+base.getTitle()+"</td></tr>");
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgTipo")+":</td><td >"+getTypeTitle(oRS.getTypeID(),paramsRequest) +"</td></tr>");
                ret.append("\n</table>");
                ret.append("\n</fieldset>");
                ret.append("\n<fieldset>");
                ret.append("\n<legend>"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPreguntasAsociadas")+"</legend>");
                ret.append("\n<table border=0 cellpadding=2 cellspacing=0 width=\"100%\">");
                // Encabezado de preguntas relacionadas con la encuesta
                ret.append("\n<thead>");
                ret.append("\n<tr>");
                ret.append("\n<th width=75>"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAction")+"");
                ret.append("\n</th>");
                ret.append("\n<th width=30>"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgID")+"");
                ret.append("\n</th>");
                ret.append("\n<th >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPregunta")+"");
                ret.append("\n</th>");
                ret.append("\n<th >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgRelacionadoCon")+"");
                ret.append("\n</th>");
                ret.append("\n<th >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgGrupo")+"");
                ret.append("\n</th>");
                ret.append("\n<th >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgIndice")+"");
                ret.append("\n</th>");
                ret.append("\n<th >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgData")+"");
                ret.append("\n</th>");
                ret.append("\n<th >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgActive")+"");
                ret.append("\n</th>");
                ret.append("\n</tr>");
                ret.append("\n</thead>");
                ret.append("\n</tbody>");
                ret.append("\n<tr><td colspan=8>");
                // Empieza enlistado de preguntas existentes
                String strSQL = new String("select sr_question.questionid, " +
                " sr_question.question, " +
                " sr_orderquestion.subjectid, " +
                " sr_orderquestion.ordernum, sr_orderquestion.isactive, sr_orderquestion.isdata " +
                " from sr_survey, sr_question, sr_orderquestion "+
                " where " +
                " sr_survey.surveyid = sr_orderquestion.surveyid and " +
                " sr_question.questionid = sr_orderquestion.questionid and " +
                " sr_survey.idtm = sr_orderquestion.idtm and " +
                " sr_question.idtm = sr_orderquestion.idtm and " +
                " sr_survey.surveyid = ? and sr_survey.idtm=? "+
                " ORDER BY  sr_orderquestion.subjectid, sr_orderquestion.ordernum  ");
                Connection conn = null;
                PreparedStatement pst = null;
                ResultSet rs =null;
                try
                {
                    CargaHM(oRS.getSurveyID());
                    conn = SWBUtils.DB.getDefaultConnection();
                    pst = conn.prepareStatement(strSQL);
                    pst.setLong(1,oRS.getSurveyID());
                    pst.setString(2, idtm);
                    rs = pst.executeQuery();
                    SWBResourceURL url4 = paramsRequest.getRenderUrl();
                    url4.setMode(url4.Mode_ADMIN);
                    url4.setAction("orderupdate_en");
                    String rowColor="";
                    boolean cambiaColor = true;
                    while(rs.next())
                    {
                        long tmpqid = rs.getLong("questionid");
                        String tmpQuestion = SWBUtils.IO.readInputStream(rs.getAsciiStream("question")); //rs.getString("question");
                        long tmpsid = rs.getLong("subjectid");
                        int tmpordernum = rs.getInt("ordernum");
                        int tmpactive = rs.getInt("isactive");
                        int tmpdata = rs.getInt("isdata");
                        ret.append("\n<form name=\"forma"+tmpqid+"\" action=\""+url4+"\" method=\"GET\" onSubmit=\"return validaIndice(forma"+tmpqid+");\">");
                        rowColor="bgcolor=\"#EFEDEC\"";
                        if(!cambiaColor) rowColor="";
                        cambiaColor = !(cambiaColor);
                        ret.append("\n<tr "+rowColor+">");
                        SWBResourceURL urlupt = paramsRequest.getRenderUrl();
                        urlupt.setMode(SWBResourceURL.Mode_ADMIN);
                        urlupt.setAction("update_p");
                        urlupt.setParameter("id",Long.toString(tmpqid));
                        urlupt.setParameter("retq","1");
                        urlupt.setCallMethod(SWBResourceURL.Call_DIRECT);
                        ret.append("\n<td align=center width=75><a href=\"#\" onclick=\"javascript:enviaValor(forma"+tmpqid+",'Quitar');\"><img src=\""+SWBPlatform.getContextPath()+"/swbadmin/images/delete.gif\" border=0 alt=\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgQuitar")+"\"></a><a href=\"javascript:ventana('"+urlupt+"','width=550, height=550, scrollbars, resizable');\"><img src=\""+SWBPlatform.getContextPath() +"/swbadmin/icons/editar_1.gif\" border=0 alt=\"Detail question\nid: "+tmpqid+"\"></a><a href=\"#\" onclick=\"javascript:enviaValor(forma"+tmpqid+",'Actualizar');\" name=\"s_enviar\" value=\"Actualizar\" ><img src=\"/swbadmin/icons/activa.gif\" border=0 alt=\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgActualizar")+"\"></a>");
                        ret.append("\n<input type=\"hidden\" name=\"questionid\" value=\""+tmpqid+"\"><input type=\"hidden\" name=\"s_enviar\" value=\"\"></font></td>");
                        ret.append("\n<td  width=30>");
                        ret.append(tmpqid);
                        ret.append("\n</td>");
                        ret.append("\n<td >");
                        if(tmpQuestion.length()>60) ret.append(tmpQuestion.substring(0,57)+" ... ");
                        else ret.append(tmpQuestion);
                        ret.append("\n</td>");
                        ret.append("\n<td >");
                        hs = new HashSet();
                        getChilds(Long.toString(tmpqid));
                        getParents(Long.toString(tmpqid));
                        String strTemp ="";
                        if(hs.size()>0)
                        {                               // COPIANDO IDS PREGUNTAS PARIENTES
                            Iterator iter = hs.iterator();
                            while(iter.hasNext()) strTemp += (String)iter.next() + ",";
                        }
                        if(hm.size()>0)
                        {                               // COPIANDO IDS PREGUNTAS PADRES
                            Set keys = hm.keySet();
                            Iterator iter2 = keys.iterator();
                            while(iter2.hasNext()) strTemp += (String)iter2.next() + ",";
                        }
                        ret.append("\n<select name=\"parent\">");
                        String strNinguno = " selected ";
                        String strParent = " selected ";
                        if(getFather(Long.toString(tmpqid))!=null) strNinguno = "";
                        else strParent = "";
                        ret.append("\n<option value=\"0\" "+strNinguno+">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNinguno")+"</option>");
                        if(getFather(Long.toString(tmpqid))!=null)
                        {
                            ret.append("\n<option value=\""+getFather(Long.toString(tmpqid))+"\" "+strParent+">"+getFather(Long.toString(tmpqid))+"</option>");
                        }
                        Connection conexxion =null;
                        PreparedStatement pstr =null;
                        ResultSet rsr =null;
                        try
                        {
                            if(strTemp.length()>0) strTemp = strTemp.substring(0,strTemp.lastIndexOf(","));
                            else strTemp="0";
                            conexxion = SWBUtils.DB.getDefaultConnection();
                            pstr = conexxion.prepareStatement("" +
                            "Select questionid " +
                            "from sr_orderquestion " +
                            "where " +
                            "surveyid = ? and idtm=? and " +

                            "questionid not in (" +
                            strTemp +  "," +tmpqid +
                            ")");
                            pstr.setLong(1,oRS.getSurveyID());
                            pstr.setString(2,idtm);
                            rsr = pstr.executeQuery();
                            while(rsr.next()){
                                long qid = rsr.getLong("questionid");
                                ret.append("\n<option value=\""+qid+"\">"+qid+"</option>");
                            }
                            if(rsr!=null)rsr.close();
                            if(pstr!=null)pstr.close();
                            if(conexxion!=null)conexxion.close();
                        }
                        catch(Exception e)
                        {log.error(paramsRequest.getLocaleString("errormsg_Survey_getAdmHtml_logErrorConsultarPreguntasRelacionadasFormulario"),e);}
                        finally
                        {
                            rsr=null;
                            pstr=null;
                            conexxion=null;
                        }
                        ret.append("\n</select><input type=\"hidden\" name=\"hparent\" value=\""+getFather(Long.toString(tmpqid))+"\">");
                        ret.append("\n</td>");
                        ret.append("\n<td >"+oRS.getSubjectList(tmpsid,"  ",idtm));
                        ret.append("\n</td>");
                        ret.append("\n<td ><input type=\"text\" name=\"indice\" value=\""+tmpordernum+"\" size=\"3\" maxlength=3>");
                        ret.append("\n</td>");

                        String check="";
                        if(tmpdata==1) check=" checked";
                        ret.append("\n<td  width=30><input type=checkbox name=isdata value=1 "+check+" style=\"background:"+rowColor+";\">");
                        ret.append("\n</td>");
                        check="";
                        if(tmpactive==1) check=" checked";
                        ret.append("\n<td  width=30><input type=checkbox name=isactive value=1 "+check+" style=\"background:"+rowColor+";\">");
                        ret.append("\n</td>");
                        ret.append("\n</tr>");
                        ret.append("\n</form>");

                        hs=null;
                    }
                    if(rs!=null)rs.close();
                    if(pst!=null)pst.close();
                    if(conn!=null)conn.close();
                }
                catch(Exception e)
                { log.error(paramsRequest.getLocaleString("errormsg_Survey_getAdmHtml_logErrorConsultarDBPreguntasRelacionadasFormulario"),e);}
                finally
                {
                    rs=null;
                    pst=null;
                    conn=null;
                }

                ret.append("\n</tbody>");
                ret.append("\n</table>");
                ret.append("\n</fieldset>");
                // Termina enlistado de preguntas existentes
                RecQuestion oRQT = new RecQuestion();
                oRQT.setIdtm(idtm); 
                int pgqid = 0;
                if(request.getParameter("groupqid")!=null)
                {
                    pgqid = Integer.parseInt(request.getParameter("groupqid"));
                    request.getSession().setAttribute("groupqid",request.getParameter("groupqid"));
                }
                else
                {
                    if (request.getSession().getAttribute("groupqid")!=null) pgqid = Integer.parseInt(request.getSession().getAttribute("groupqid").toString());
                }
                ret.append("\n<fieldset>");
                ret.append("\n<legend>"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPreguntasDisponibles")+"</legend>");

                SWBResourceURL urls = paramsRequest.getRenderUrl();
                urls.setMode(urls.Mode_ADMIN);
                urls.setAction("selectquestion_en");

                ret.append("\n<table border=0 cellpadding=2 cellspacing=0 width=\"100%\">");
                ret.append("\n<tr><td colspan=8 align=center ><form action=\""+urls+"\">"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgCategoriasPreguntas")+": "+oRQT.getGroupQuestionList(pgqid)+"<input type=\"submit\" name=\"enviar_gqid\" value=\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgFiltrar")+"\" class=boton></form></td></tr>");
                Connection con=null;
                PreparedStatement st=null;
                ResultSet rsX=null;
                try
                {
                    StringBuffer strGroups = new StringBuffer();
                    String strQuery = new String("");
                    //String strQuery1 = new String("select questionid from sr_orderquestion where surveyid="+oRS.getSurveyID());
                    Connection con1=null;
                    PreparedStatement sqlst=null;
                    ResultSet rsLocal=null;
                    try
                    {
                        con1=SWBUtils.DB.getDefaultConnection("Recurso Formulario.Survey.doAdmin()");
                        sqlst=con1.prepareStatement("select questionid from sr_orderquestion where surveyid=? and idtm=?");
                        sqlst.setLong(1, oRS.getSurveyID());
                        sqlst.setString(2,idtm);
                        rsLocal=sqlst.executeQuery();
                        while(rsLocal.next())
                        {
                            long tipoID =rsLocal.getLong("questionid");
                            strGroups.append(tipoID+",");
                        }
                        if(rsLocal!=null)rsLocal.close();
                        if(sqlst!=null)sqlst.close();
                        if(con1!=null)con1.close();
                    }
                    catch(Exception e)
                    {
                        log.error(paramsRequest.getLocaleString("errormsg_Survey_getAdmHtml_logErrorConsultarSrorderquestion"),e);
                    }
                    finally
                    {
                        rsLocal=null;
                        sqlst=null;
                        con1=null;
                    }
                    if(strGroups.length()==0)
                    {
                        strQuery = "select questionid, question from sr_question where idtm=? and isreuse=1 ";
                    }
                    else
                    {
                        strQuery = "select questionid, question from sr_question where idtm=? and isreuse=1 and questionid not in ("+strGroups.deleteCharAt(strGroups.length()-1).toString()+")";
                    }
                    if(pgqid!=0)strQuery += " and categoryid="+pgqid;
                    con=SWBUtils.DB.getDefaultConnection();
                    st=con.prepareStatement(strQuery);
                    st.setString(1,idtm);
                    rsX=st.executeQuery();
                    long tmpQID=0;
                    String tmpSelected ="";
                    if(request.getParameter("idp")!=null) tmpQID=Long.parseLong(request.getParameter("idp"));
                    ret.append("\n<tr><td colspan=8>");
                    ret.append("\n<form name=\"forma\" action=\""+urls+"\">");
                    ret.append("\n<select name=\"idp\" >");
                    while(rsX.next())
                    {
                        String texto ="";
                        long tmpQuestionID = rsX.getLong("questionid");
                        String tmpQuestion = SWBUtils.IO.readInputStream(rsX.getAsciiStream("question")); //rsX.getString("question");
                        if(tmpQuestion.length()>60) texto=tmpQuestion.substring(0,57)+" ... ";
                        else texto=tmpQuestion;
                        long qID =tmpQuestionID;
                        tmpSelected=" ";
                        if(tmpQID==qID)  tmpSelected = " selected ";
                        ret.append("\n<option value=\""+qID+"\" "+tmpSelected+">"+texto+"</option>");
                    }
                    ret.append("\n</select><input type=\"submit\" name=\"btn_selQuestion\" value=\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_btnSeleccionar")+"\" class=boton>");
                    if(rsX!=null)rsX.close();
                    if(st!=null)st.close();
                    if(con!=null)con.close();
                    SWBResourceURL urladd = paramsRequest.getRenderUrl();
                    urladd.setMode(urladd.Mode_ADMIN);
                    urladd.setAction("add_p");
                    urladd.setParameter("retq","1");
                    urladd.setCallMethod(urladd.Call_DIRECT);
                    ret.append("&nbsp;<input type=\"button\" name=\"btn_newq\" onclick=\"ventana('"+urladd+"','width=550, height=550, scrollbars, resizable');\" value=\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNuevaPregunta")+"\" class=boton>");
                    ret.append("</form></td></tr>");
                }
                catch(Exception e)
                { log.error(paramsRequest.getLocaleString("errormsg_Survey_getAdmHtml_logErrorConsultarDBEncuestas"),e); }
                finally
                {
                    rsX=null;
                    st=null;
                    con=null;
                }

                if (request.getParameter("idp")!=null&&entrarSelQuestion)
                {
                    RecQuestion oQ=new RecQuestion();
                    oQ.setIdtm(idtm);
                    oQ.setQuestionID(Integer.parseInt(request.getParameter("idp")));
                    oQ.load();
                    ret.append("\n<tr><td colspan=8 align=center>&nbsp;</td></tr>");
                    ret.append("\n<tr><td colspan=8 >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPreguntaSeleccionada")+"</td></tr>");
                    ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgIdentificador")+":</td><td colspan=7 >"+oQ.getQuestionID()+"</td></tr>");
                    ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPregunta")+":</td><td colspan=7 >"+oQ.getQuestion()+"</td></tr>");
                    try
                    {
                        String strxml=new String(oQ.getFreqAnswer().getStringxml()) ;
                        String oQuestionXML = oQ.getStringXML();

                        org.w3c.dom.Document  docxml = SWBUtils.XML.getNewDocument();
                        org.w3c.dom.Document  docxmlQ = SWBUtils.XML.getNewDocument();
                        docxmlQ = SWBUtils.XML.xmlToDom(oQuestionXML);
                        Element resource,res;
                        res = (Element) docxmlQ.getFirstChild();
                        org.w3c.dom.NodeList nodesQ = res.getChildNodes();
                        if (strxml != null)
                        {
                            docxml = SWBUtils.XML.xmlToDom(strxml);
                            if (docxml!=null)
                            {
                                resource = (Element) docxml.getFirstChild();
                                org.w3c.dom.NodeList nodes = resource.getChildNodes();
                                ret.append("\n<tr><td colspan=8>");
                                SWBResourceURL urlxml = paramsRequest.getRenderUrl();
                                urlxml.setMode(urlxml.Mode_ADMIN);
                                urlxml.setAction("orderaddXML_en");
                                ret.append("\n<form name=\"miforma\" action=\""+urlxml+"\" method=\"GET\" onsubmit=\"return valida(miforma);\">");
                                ret.append("\n<input type=\"hidden\" name=\"idp\" value=\""+request.getParameter("idp")+"\">");
                                ret.append("<input type=\"hidden\" name=\"hseccion\" value=\"0\">");
                                ret.append("\n<table border=0 cellspacing=1 cellpadding=0 width=100%>");
                                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNumeroPreguntaIndice")+":</td><td colspan=2 ><input class=campos type=text size=\"3\" name=\"indice\" id=\"indice\" maxlength=3></td></tr>");

                                SWBResourceURL urlas = paramsRequest.getRenderUrl();
                                urlas.setMode(urlas.Mode_ADMIN);
                                urlas.setCallMethod(urlas.Call_DIRECT);
                                urlas.setAction("add_s");

                                int tempSeccion = 0;
                                if(request.getParameter("hseccion")!=null) tempSeccion = Integer.parseInt(request.getParameter("hseccion"));
                                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgGrupo")+":</td><td colspan=2 >"+oRS.getSubjectList(tempSeccion,"  ",idtm)+"<input   type=\"button\" name=\"btnnewsection\" value=\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNuevoGrupo")+"\" onclick=\"ventana('"+urlas+"?regreso=1','width=580, height=490, resizable');\" class=boton></td></tr>");

                                ret.append("\n<tr><td  width=200 align=right>");
                                ret.append(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgRespuesta")+":</font>");
                                ret.append("</td><td colspan=2 >"+oQ.getFreqAnswer().getTitle()+"</td></tr>");
                                ret.append("\n<tr><td  width=200 align=center>"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgOpcion")+"</td>");
                                String strVacio="&nbsp;";
                                String strVer="; Display:none";
                                if(oRS.getTypeID()==1)
                                {
                                    ret.append("<td >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgNoAplicaValor")+"</td>");
                                    strVacio="&nbsp;";
                                    strVer="; Display:none";
                                }
                                else
                                {
                                    ret.append("<td >");
                                    if(oQ.getFreqAnswerID()!=1) ret.append(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgValor")+" %");
                                    ret.append("&nbsp;</td>");
                                    strVacio="";
                                    strVer="";
                                }

                                ret.append("<td >");
                                if(oQ.getFreqAnswerID()!=1) ret.append(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgOrden"));
                                ret.append("&nbsp;</td></tr>");
                                String tempS="";
                                if(oQ.getFreqAnswerID()==1) tempS = "; visibility:hidden ";
                                for (int i = 0; i < nodes.getLength(); i++)
                                {
                                    org.w3c.dom.Node node = nodes.item(i);
                                    org.w3c.dom.Node nodeQ = nodesQ.item(i);
                                    String val = node.getFirstChild().getNodeValue();
                                    String att = node.getAttributes().getNamedItem("id").getNodeValue();
                                    String idQ = nodeQ.getAttributes().getNamedItem("id").getNodeValue();
                                    String valueQ = nodeQ.getAttributes().getNamedItem("value").getNodeValue();
                                    String referQ = nodeQ.getAttributes().getNamedItem("refer").getNodeValue();
                                    ret.append("\n" +
                                    "<tr>" +
                                    "<td >" +
                                    "<input type=hidden name=refer value=\""+referQ+"\">"+att+")&nbsp;" + val + "</td>" +
                                    "<td ><input type=text name=valor  value=\""+valueQ+"\" style=\"font-family:verdana; font-size:9; width:100; height:18"+strVer+tempS+"\" readonly>"+strVacio+"</td>" +
                                    "<td ><input type=text name=orden value=\""+idQ+"\" style=\"font-family:verdana; font-size:9; width:100; height:18"+tempS+"\" readonly></td>" +
                                    "</tr>");
                                }
                                ret.append("<input type=\"hidden\" name=\"xml\"><input type=\"hidden\" name=\"questionid\" value=\""+oQ.getQuestionID()+"\">");
                                ret.append("<tr><td colspan=8 ><input type=\"submit\" name=\"sb_enviar\" value=\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgEnviar")+"\" class=boton></td></tr></table></form></td></tr>");
                            }
                        }
                    }
                    catch(Exception e)
                    {log.error(paramsRequest.getLocaleString("errormsg_Survey_getAdmHtml_logErrorCargarDomSetRespuestas")+":",e);}
                }
                SWBResourceURL urlsur = paramsRequest.getRenderUrl();
                urlsur.setMode(urlsur.Mode_ADMIN);
                urlsur.setAction("update_en");
                ret.append("\n<tr><td colspan=8 align=right><hr noshade size=1><input type=button value=\"Regresar\" onclick=\"javascript:window.location='"+urlsur+"'\"></td></tr>");
                ret.append("</table></td></tr></table>");
                ret.append("\n</fieldset>");

            }


            if (accion.equalsIgnoreCase("select_en"))
            {
                // secci�n de selecci�n de Formulario a editar
                int tipo=0;
                if (request.getParameter("tipo")!=null) tipo = Integer.parseInt(request.getParameter("tipo"));
                if (request.getParameter("filtro")!=null)
                {
                    if(request.getParameter("filtro").equals("Todos"))
                    {
                        tipo=0;
                    }
                }
                ret.append("\n<fieldset>");
                ret.append("\n<table border=0 cellspacing=0 width=\"100%\"><tr><td colspan=2 >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgFiltrado")+"</td></tr>");
                ret.append("\n<tr><td >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPorTipo")+"</td><td >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgPorGrupo")+"</td></tr>");
                RecSurvey ObRS = new RecSurvey();
                ObRS.setIdtm(idtm);
                SWBResourceURL urls1 = paramsRequest.getRenderUrl();
                urls1.setMode(urls1.Mode_ADMIN);
                urls1.setAction("select_en");
                ret.append("\n<tr><td colspan=2 ><form name=\"forma\" action=\""+urls1+"\" method=\"GET\" >&nbsp;&nbsp;<input name=filtro type=submit value=\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_btnFiltrar")+"\" class=boton>&nbsp;<input name=filtro type=submit value=\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_btnTodos")+"\" class=boton></td></tr>");
                ret.append("\n<tr><td colspan=2 >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgSeleccionaFormularioEditarLista")+"</td></tr>");
                if(tipo==0 )
                {
                    Connection con=null;
                    PreparedStatement st=null;
                    ResultSet rs=null;
                    try
                    {
                        con=SWBUtils.DB.getDefaultConnection("Recurso Formulario.Survey.doAdmin()");
                        st=con.prepareStatement("select surveyid,title from sr_survey where idtm=?");
                        st.setString(1,idtm);
                        rs=st.executeQuery();
                        while(rs.next())
                        {
                            String titulo =rs.getString("title");
                            long tipoID =rs.getLong("surveyid");
                            SWBResourceURL urlup = paramsRequest.getRenderUrl();
                            urlup.setMode(urlup.Mode_ADMIN);
                            urlup.setAction("update_en");
                            urlup.setParameter("id",Long.toString(tipoID));
                            ret.append("<tr><td ><a href=\""+urlup+"\">"+tipoID+"</a></td><td >"+titulo+"</td></tr>");
                        }
                        if(rs!=null)rs.close();
                        if(st!=null)st.close();
                        if(con!=null)con.close();
                    }
                    catch(Exception e)
                    { log.error(paramsRequest.getLocaleString("errormsg_Survey_getAdmHtml_logErrorConsultarDBSurvey"),e); }
                    finally
                    {
                        rs=null;
                        st=null;
                        con=null;
                    }
                }
                else
                {
                    String strQuery = new String();
                    strQuery = "select * from sr_survey where typeid = ? and idtm=? ";
                    Connection con=null;
                    PreparedStatement st=null;
                    ResultSet rs=null;
                    try
                    {
                        con=SWBUtils.DB.getDefaultConnection("Recurso Formulario.Survey.doAdmin()");
                        st=con.prepareStatement(strQuery);
                        st.setInt(1,tipo);
                        st.setString(2,idtm);
                        rs=st.executeQuery();
                        while(rs.next())
                        {
                            String titulo =rs.getString("title");
                            long tipoID =rs.getLong("surveyid");
                            SWBResourceURL urlup = paramsRequest.getRenderUrl();
                            urlup.setMode(urlup.Mode_ADMIN);
                            urlup.setAction("update_en");
                            urlup.setParameter("id",Long.toString(tipoID));
                            ret.append("<tr><td ><a href=\""+urlup+"\">"+tipoID+"</a></td><td >"+titulo+"</td></tr>");
                        }
                        if(rs!=null)rs.close();
                        if(st!=null)st.close();
                        if(con!=null)con.close();
                    }
                    catch(Exception e)
                    {
                        log.error(paramsRequest.getLocaleString("errormsg_Survey_getAdmHtml_logErrorConsultarDBSRSurvey"),e);
                    }
                    finally
                    {
                        rs=null;
                        st=null;
                        con=null;
                    }
                }
                ret.append("</table>");
                ret.append("\n</fieldset>");
                ret.append("\n</div>");
            }
        }
        return Header + ret.toString();
    }


    public void getChilds(String parent)
    {
        if(hm.containsKey(parent))
        {
            hs.add(hm.get(parent));
            getChilds((String)hm.get(parent));
        }
    }

    public void getParents(String parent)
    {
        if(hm.containsValue(parent))
        {
            Set col = hm.keySet();
            Iterator iter = col.iterator();
            String strKey = "";
            while(iter.hasNext())
            {
                strKey = (String)iter.next();
                if(hm.get(strKey).equals(parent)) break;
            }
            hs.add(strKey);
            getParents(strKey);
        }
    }

    public String getFather(String parent)
    {
        String strKey = null;
        if(hm.containsValue(parent))
        {
            Set col = hm.keySet();
            Iterator iter = col.iterator();
            while(iter.hasNext())
            {
                strKey = (String)iter.next();
                if(hm.get(strKey).equals(parent)) break;
            }
        }
        return strKey;
    }

    public void CargaHM(long surveyid)
    {
        String idtm=base.getWebSiteId();
        Connection conn =null;
        PreparedStatement pst =null;
        ResultSet rs =null;
        try
        {
            conn = SWBUtils.DB.getDefaultConnection("Recurso Formulario.Survey.CargaHM()");
            pst = conn.prepareStatement("" +
            "select parentquestion, sonquestion " +
            "from sr_relatedquestion " +
            "where surveyid = ? and idtm=?");
            pst.setLong(1,surveyid);
            pst.setString(2,idtm);
            rs = pst.executeQuery();
            while(rs.next())
            {
                hm.put(rs.getString("parentquestion"),rs.getString("sonquestion"));
            }
            if(rs!=null)rs.close();
            if(pst!=null)pst.close();
            if(conn!=null)conn.close();
        }
        catch(Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_wb2_resources","errormsg_Survey_getAdmHtml_logErrorConsultarDBSrrelatedquestion"),e);
        }
        finally
        {
            rs=null;
            pst=null;
            conn=null;
        }
    }

    public String getTypeTitle(int typeid, SWBParamRequest paramsRequest)
    {
        String title="";
        try
        {
            title=paramsRequest.getLocaleString("usrmsg_opinion");
            if(typeid==2) title=paramsRequest.getLocaleString("usrmsg_evaluation");
        }
        catch(Exception e)
        {
            log.error("Error while trying to get the survey type name.",e);
        }
        return title;
    }

    public String getDisplayList(int pshowdisplay, String evento, SWBParamRequest paramsRequest )
    {

        StringBuffer ret1=new StringBuffer();
        try
        {
            if (Integer.toString(pshowdisplay).equals(null)) pshowdisplay=0;
            if(evento.equals(null))evento="";
            ret1.append("<select name=\"showdisplay\" "+evento+" class=campos>");
            String selectA ="";
            String selectB ="";
            String selectC ="";
            if (pshowdisplay==1) selectA ="selected";
            if (pshowdisplay==2) selectB ="selected";
            if (pshowdisplay==3) selectC ="selected";
            ret1.append("<option value=\"1\" "+selectA+">"+paramsRequest.getLocaleString("usrmsg_RecSurvey_getAdmHtml_msgPregunta")+"</option>\n");   // Por pregunta
            ret1.append("<option value=\"2\" "+selectB+">"+paramsRequest.getLocaleString("usrmsg_RecSurvey_getAdmHtml_msgSeccion")+"</option>\n");   // Por Secci�n
            ret1.append("<option value=\"3\" "+selectC+">"+paramsRequest.getLocaleString("usrmsg_RecSurvey_getAdmHtml_msgEncuesta")+"</option>\n");   // Toda la Encuesta
            ret1.append("</select>");
        } catch(Exception e)
        {log.error("Error while trying to get Display List");}
        return ret1.toString();
    }

    public String getStatusList(int pstatus, SWBParamRequest paramsRequest)
    {
        StringBuffer ret1=new StringBuffer();
        try
        {
            if (Integer.toString(pstatus).equals(null)) pstatus=-1;
            ret1.append("<select name=\"estatus\">");
            String selectA ="";
            String selectB ="";
            if (pstatus==0) selectA ="selected";
            if (pstatus==1) selectB ="selected";
            ret1.append("<option value=\"0\" "+selectA+">"+paramsRequest.getLocaleString("usrmsg_RecSurvey_getAdmHtml_msgDesarrollo")+"</option>\n");
            ret1.append("<option value=\"1\" "+selectB+">"+paramsRequest.getLocaleString("usrmsg_RecSurvey_getAdmHtml_msgActiva")+"</option>\n");
            ret1.append("</select>");
        } catch(Exception e)
        {log.error("Error while trying to get Status List");}
        return ret1.toString();
    }

    public String getWB3AdminStyle()
    {
        StringBuffer ret = new StringBuffer("");
//        ret.append("\n<style type=\"text/css\">");
//        ret.append("\n.datos {");
//        ret.append("\n	color: #333333;");
//        ret.append("\n	text-decoration: none;");
//        ret.append("\n	font-family: Verdana;");
//        ret.append("\n	font-size: 12px;");
//        ret.append("\n}");
//        ret.append("\n.valores {");
//        ret.append("\n	color: #000000;");
//        ret.append("\n	text-decoration: none;");
//        ret.append("\n	font-family: Verdana;");
//        ret.append("\n	font-size: 12px;");
//        ret.append("\n}");
//        ret.append("\n.link {");
//        ret.append("\n	color: #0000FF;");
//        ret.append("\n	text-decoration: none;");
//        ret.append("\nfont-family: Verdana;");
//        ret.append("\nfont-size: 12px;");
//        ret.append("\n}");
//        ret.append("\n.tabla {");
//        ret.append("\n	color: #428AD4;");
//        ret.append("\n	text-decoration: none;");
//        ret.append("\n	font-family: Verdana;");
//        ret.append("\n	font-size: 11px;");
//        ret.append("\n}");
//        ret.append("\n.boton{");
//        ret.append("\n	color:#FFFFFF;");
//        ret.append("\n	background-color:#2A5216;");
//        ret.append("\n	border-color:#77AE4D;");
//        ret.append("\n	FONT-FAMILY: verdana,arial,helvetica,sans-serif;");
//        ret.append("\n	FONT-SIZE: 10pt;");
//        ret.append("\n}");
//        ret.append("\n.box {");
//        ret.append("\n  width: 95%;");
//        ret.append("\n  background: #FFFFFF;");
//        ret.append("\n  color : #000000;");
//        ret.append("\n  font-family : Verdana;");
//        ret.append("\n  font-size: 12px;");
//        ret.append("\n  padding : 5px;");
//        ret.append("\n  margin: 10px;");
//        ret.append("\n  border-color : #cccccc;");
//        ret.append("\n  border-style : solid;");
//        ret.append("\n  border-width : 1px;");
//        ret.append("\n</style>");
        return ret.toString();
    }

}
