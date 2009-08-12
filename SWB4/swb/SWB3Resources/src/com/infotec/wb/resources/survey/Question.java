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

import com.infotec.wb.resources.survey.db.RecFreqAnswer;
import com.infotec.wb.resources.survey.db.RecOrderQuestion;
import com.infotec.wb.resources.survey.db.RecQuestion;
import com.infotec.wb.resources.survey.db.RecSurvey;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.SimpleDateFormat;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.Element;

/**
 * Created by
 * User: Juan Antonio Fernández Arias - INFOTEC
 * 
 */
public class Question  {
    private static Logger log = SWBUtils.getLogger(Question.class);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss");
    Resource base=null;

    public Question(){}

    public void setResourceBase(Resource base) {
        this.base = base;
    }

    public String doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        String idtm=base.getWebSiteId();
        StringBuffer ret = new StringBuffer();
        long idtemp=0;

        if(request.getParameter("id")!=null) idtemp=Long.parseLong(request.getParameter("id"));
        
        RecSurvey oRSsesion = new RecSurvey();
        oRSsesion.setIdtm(idtm);
        oRSsesion.setResID(base.getId());
        oRSsesion.load();
        String accion = paramsRequest.getAction();
        User user = paramsRequest.getUser();
        SWBResourceURL urlap = paramsRequest.getRenderUrl();
        urlap.setAction("add_p");
        SWBResourceURL urlagp = paramsRequest.getRenderUrl();
        urlagp.setAction("agregar_p");
        SWBResourceURL urlsp = paramsRequest.getRenderUrl();
        urlsp.setAction("select_p");
        SWBResourceURL urldsp = paramsRequest.getRenderUrl();
        urldsp.setAction("deleteselect_p");
        SWBResourceURL urlmfa = paramsRequest.getRenderUrl();
        urlmfa.setCallMethod(SWBResourceURL.Call_DIRECT);
        urlmfa.setAction("modificar_fa");
        SWBResourceURL urlact = paramsRequest.getRenderUrl();
        urlact.setCallMethod(SWBResourceURL.Call_DIRECT);
        urlact.setAction("add_ct");
        SWBResourceURL urlmgq = paramsRequest.getRenderUrl();
        urlmgq.setCallMethod(SWBResourceURL.Call_DIRECT);
        urlmgq.setAction("modificar_gq");
        SWBResourceURL urlas = paramsRequest.getRenderUrl();
        urlas.setCallMethod(SWBResourceURL.Call_DIRECT);
        urlas.setAction("add_s");
        SWBResourceURL urlup = paramsRequest.getRenderUrl();
        urlup.setAction("update_p");
        
        //System.out.println("Regreso Question: "+request.getSession().getAttribute("regreso"));
        
        SWBResourceURL url = paramsRequest.getRenderUrl();
        response.setContentType("text/html");
        ret.append("\n<link href=\"/swbadmin/css/swb.css\" rel=\"stylesheet\" type=\"text/css\" />");
        
        
         if (accion.equalsIgnoreCase("updatequestion_p")) {
            // secci�n de actualizaci�n de la pregunta
            if (request.getParameter("id")!=null){
                String idQ =request.getParameter("id");
                RecSurvey oRS = new RecSurvey();
                oRS.setIdtm(idtm);
                oRS.setResID(base.getId());
                oRS.load();
                RecQuestion nRQ = new RecQuestion();
                nRQ.setIdtm(idtm);
                int tempI = 0;
                nRQ.setInstruction(request.getParameter("description"));
                nRQ.setQuestion(request.getParameter("question"));
                nRQ.setCodeID(Long.parseLong(request.getParameter("textid")));
                tempI=0;
                if(request.getParameter("validate")!=null) tempI = 1;
                nRQ.setValidate((long)tempI);
                tempI=0;
                if(request.getParameter("required")!=null) tempI = 1;
                nRQ.setRequired((long)tempI);
                int tmpControlid = Integer.parseInt(request.getParameter("controlid"));
                nRQ.setControlID(tmpControlid);
                tempI=0;
                if(request.getParameter("validoptions")!=null) tempI=Integer.parseInt(request.getParameter("validoptions"));
                nRQ.setValidOptions(tempI);
                tempI=0;
                if(request.getParameter("reuse")!=null) tempI = 1;
                nRQ.setIsReUse(tempI);
                tempI=1;
                if(request.getParameter("freqanswerid")!=null) tempI=Integer.parseInt(request.getParameter("freqanswerid"));
                nRQ.setFreqAnswerID(tempI);
                String panswer = null;
                if(request.getParameter("answer")!=null) panswer = request.getParameter("answer");
                String strXML=new String(nRQ.paramXMLtoStringXML(request.getParameter("xml"),panswer));
                nRQ.setStringXML(strXML);
                nRQ.setCategoryID(Integer.parseInt(request.getParameter("groupqid")));

                // comparando la pregunta actual con los datos recibidos para ver si se duplica o no.

                // oRQo -- Pregunta con la info original de la pregunta
                RecQuestion oRQo = new RecQuestion();
                oRQo.setIdtm(idtm);
                oRQo.setQuestionID(Integer.parseInt(idQ));
                oRQo.load();
                boolean igual=true;
                if(oRQo.getControlID()!=nRQ.getControlID()) igual=false;
                if(!oRQo.getInstruction().equalsIgnoreCase(nRQ.getInstruction()))igual=false;
                if(!oRQo.getQuestion().equalsIgnoreCase(nRQ.getQuestion()))igual=false;
                if(oRQo.getCodeID()!=nRQ.getCodeID())igual=false;
                if(oRQo.getValidate()!=nRQ.getValidate())igual=false;
                if(oRQo.getRequired()!=nRQ.getRequired())igual=false;
                if(oRQo.getValidOptions()!=nRQ.getValidOptions())igual=false;
                if(oRQo.getFreqAnswerID()!=nRQ.getFreqAnswerID())igual=false;
                if(oRQo.getCategoryID()!=nRQ.getCategoryID())igual=false;
                if(oRQo.getIsReUse()!=nRQ.getIsReUse()) igual=false;
                if(!oRQo.getStringXML().equalsIgnoreCase(nRQ.getStringXML()))igual=false;
                Connection conn =null;
                PreparedStatement pstS = null;
                ResultSet rsOQ =null;
                PreparedStatement pstd = null;
                if(!igual){
                    //System.out.println("Se modifico la pregunta asociada");
                    // la informaci�n de la pregunta fue cambiada se revisa si s�lo est� asociada a este Formulario o a varias
                    //Revisar que s�lo esta asociada a este formulario
                    try{
                        conn = SWBUtils.DB.getDefaultConnection();
                        pstS = conn.prepareStatement("select surveyid, questionid from sr_orderquestion where questionid = ? and surveyid <> ? and idtm=?");
                        pstS.setLong(1,Long.parseLong(idQ));
                        pstS.setLong(2,oRS.getSurveyID());
                        pstS.setString(3,idtm);
                        rsOQ = pstS.executeQuery();
                        
                        if (rsOQ.next()){
                            //duplicar pregunta
                            //System.out.println("la Pregunta se duplica, crea un RecOrderQuestion nuevo ");
                            if(!nRQ.create()) log.error(paramsRequest.getLocaleString("errormsg_Question_getAdmHtml_msgErrorDuplicarPregunta"));

                            RecOrderQuestion oROQ = new RecOrderQuestion();
                            oROQ.setIdtm(idtm);
                            oROQ.setQuestionid(nRQ.getQuestionID());
                            oROQ.setSurveyid(oRS.getSurveyID());
                            oROQ.setOrdernum(Integer.parseInt(request.getParameter("indice")));
                            oROQ.setSubjectid(Integer.parseInt(request.getParameter("seccion")));

                            if(!oROQ.create()) log.error(paramsRequest.getLocaleString("errormsg_Question_getAdmHtml_logNoPudoAgregarPreguntaDuplicadaFormulario"));
                            log.error(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgDuplicoPreguntaId")+": "+oROQ.getQuestionid());
                            
                            idtemp=nRQ.getQuestionID();
                            
                            oROQ=null;

                            pstd = conn.prepareStatement("delete from sr_orderquestion where surveyid = ? and questionid = ? and idtm=?");
                            pstd.setLong(1,oRS.getSurveyID());
                            pstd.setLong(2,Long.parseLong(idQ));
                            pstd.setString(3,idtm);
                            pstd.executeUpdate();
                            if(pstd!=null)pstd.close();
                            log.error(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgEliminaRelacionPreguntaId")+": "+idQ+" "+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgDuplicadaEncuestaId")+": "+oRS.getSurveyID());
                        }
                        else{
                            //actualizar pregunta
                            //System.out.println("la Pregunta se actualiza unicamente");
                            oRQo.setInstruction(nRQ.getInstruction());
                            oRQo.setQuestion(nRQ.getQuestion());
                            oRQo.setCodeID(nRQ.getCodeID());
                            oRQo.setValidate(nRQ.getValidate());
                            oRQo.setRequired(nRQ.getRequired());
                            oRQo.setValidOptions(nRQ.getValidOptions());
                            oRQo.setControlID(nRQ.getControlID());
                            oRQo.setFreqAnswerID(nRQ.getFreqAnswerID());
                            oRQo.setStringXML(nRQ.getStringXML());
                            oRQo.setCategoryID(nRQ.getCategoryID());
                            oRQo.setIsReUse(nRQ.getIsReUse());

                            if (!oRQo.update()) log.error(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_logNoPudoActualizarPreguntaId")+": " + idQ );

                            idtemp=oRQo.getQuestionID();
                            
                            log.error(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgActualizaPreguntaId")+": "+idQ+" "+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgRelacionadaEncuestaId")+": "+oRS.getSurveyID());
                            RecOrderQuestion oROQ = new RecOrderQuestion();
                            oROQ.setIdtm(idtm);
                            oROQ.setSurveyid(oRS.getSurveyID());
                            oROQ.setQuestionid(Long.parseLong(idQ));
                            oROQ.load();
                            
                            
                            oROQ.setOrdernum(Integer.parseInt(request.getParameter("indice")));
                            oROQ.setSubjectid(Integer.parseInt(request.getParameter("seccion")));

                            if(!oROQ.update()) log.error(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_logNoPudoActualizarIndiceSeccionPregunta"));
                            log.error(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgActualizaRelacionPreguntaId")+": "+idQ+" "+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgEncuestaId")+": "+oRS.getSurveyID());
                            oROQ=null;
                        }
                        if(rsOQ!=null)rsOQ.close();
                        if(pstS!=null)pstS.close();
                        if(conn!=null)conn.close();
                    }
                    catch(Exception e){log.error(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_logErrorConsultarDBSRSURVEY"),e);}
                    finally{
                        rsOQ=null;
                        pstS=null;
                        pstd=null;
                        conn=null;
                    }
                }
                else{
                    // S�lo se actualiza el indice y la secci�n dela pregunta
                    RecOrderQuestion oROQ = new RecOrderQuestion();
                    oROQ.setIdtm(idtm);
                     oROQ.setSurveyid(oRS.getSurveyID());
                    oROQ.setQuestionid(Long.parseLong(idQ));
                    oROQ.load();
                    oROQ.setOrdernum(Integer.parseInt(request.getParameter("indice")));
                    oROQ.setSubjectid(Integer.parseInt(request.getParameter("seccion")));

                    if(!oROQ.update()) log.error(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_logNoPudoActualizarIndiceGrupoPregunta"));
                    log.error(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgActualizaRelacionPreguntaId")+": "+idQ+" "+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgEncuestaId")+": "+oRS.getSurveyID());
                    oROQ=null;
                }


                oRS=null;
                nRQ=null;
                oRQo=null;
                if(request.getSession().getAttribute("retq")!=null){
                         ret.append("\n<script type=\"text/javascript\">");
                         ret.append("\n    window.opener.regresa();");
                         ret.append("\n    window.close();");
                         ret.append("</script>");
                         request.getSession().setAttribute("retq",null);
                }
             }
            accion="select_p";
        }
        
        
        if (accion.equalsIgnoreCase("agregar_p")) {
            // secci�n de agregar preguntas
            RecQuestion objG=new RecQuestion();
            objG.setIdtm(idtm);
            objG.setInstruction(request.getParameter("description"));
            objG.setQuestion(request.getParameter("question"));
            objG.setCodeID((long)Integer.parseInt(request.getParameter("textid")));
            int tempI;
            tempI=0;
            if(request.getParameter("validate")!=null) tempI = Integer.parseInt(request.getParameter("validate"));
            objG.setValidate((long)tempI);
            tempI=0;
            if(request.getParameter("required")!=null) tempI = Integer.parseInt(request.getParameter("required"));
            objG.setRequired((long)tempI);
             long tempL = 0;
             if(request.getParameter("validoptions")!=null ) tempL = Long.parseLong(request.getParameter("validoptions"));
             objG.setValidOptions(tempL);
             objG.setControlID((long)Integer.parseInt(request.getParameter("controlid")));
             tempL=1;
             if(request.getParameter("freqanswerid")!=null ) tempL = Long.parseLong(request.getParameter("freqanswerid"));
             objG.setFreqAnswerID(tempL);
             String strXML = new String(objG.paramXMLtoStringXML(request.getParameter("xml"),request.getParameter("answer")));
            objG.setStringXML(strXML);
            objG.setCategoryID(Integer.parseInt(request.getParameter("groupqid")) );
            try{
            if(objG.create())ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAgregoPreguntaCatalogo"));
            else ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNoPudoAgregarPregunta"));
            
            idtemp=objG.getQuestionID();
            
            }
            catch(Exception e)
            {
                log.error(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNoPudoAgregarPregunta"),e);
            }
             RecOrderQuestion oOQ = new RecOrderQuestion();
             oOQ.setIdtm(idtm);
             oOQ.setOrdernum(Integer.parseInt(request.getParameter("indice")));
             oOQ.setSubjectid(Integer.parseInt(request.getParameter("seccion")));
             oOQ.setQuestionid(objG.getQuestionID());
             RecSurvey oRS = new RecSurvey();
             oRS.setIdtm(idtm);
             oRS.setResID(base.getId());
             oRS.load();
             oOQ.setSurveyid(oRS.getSurveyID());
             oOQ.setIsActive(1);
             oOQ.setIsData(0);
             if(!oOQ.create()) log.error(paramsRequest.getLocaleString("errormsg_Question_getAdmHtml_logErrorAgregarReferenciaPreguntaFormulario"));
             log.error(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAgregaRelacionPreguntaId")+": "+objG.getQuestionID()+" "+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgYSeccionId")+": "+request.getParameter("seccion")+" "+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgConFormularioId")+": "+oRS.getSurveyID());
             oOQ=null;
             objG = null;
              if(request.getSession().getAttribute("retq")!=null){
                         ret.append("\n<script type=\"text/javascript\">");
                         ret.append("\n    window.opener.regresa();");
                         ret.append("\n    window.close();");
                         ret.append("</script>");
                         request.getSession().setAttribute("retq",null);
                    }
             
             accion="select_p";
             
        }
        
        if (accion.equalsIgnoreCase("update_p")) {
            // secci�n de actualizaci�n
            if (request.getParameter("id")!=null){
                int tmpOpciones = 27;
                int tmpFinal = 4;
                if(request.getSession().getAttribute("regreso")!=null)request.getSession().setAttribute("regreso",null);
                if(request.getSession().getAttribute("retq")!=null){
                    tmpOpciones = 27;
                    tmpFinal=4;
                }
                ret.append("\n    <script type=\"text/javascript\">    ");
                ret.append("\n        ");
                ret.append("\n    function valida(forma){    ");
                ret.append("\n     var error=0;    ");
                ret.append("\n     var opciones = (forma.length-"+tmpOpciones+")/3;    ");
                ret.append("\n     var capturados = new Array();    ");
                ret.append("\n     var x=0;    ");
                ret.append("\n     var porcentaje=0;    ");
                ret.append("\n     var xml = new String(\"\");    ");
                ret.append("\n         ");
                ret.append("\n     var tempo;    ");
                ret.append("\n     tempo = forma.indice.value;    ");
                ret.append("\n     trim(forma.indice);    ");
                ret.append("\n     if(forma.indice.value==\"\"){    ");
                ret.append("\n         alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAlertCapturarNumeroPregunta")+"\");    ");
                ret.append("\n         forma.indice.value=tempo;    ");
                ret.append("\n         forma.indice.focus();    ");
                ret.append("\n         forma.indice.select();    ");
                ret.append("\n         return(false);    ");
                ret.append("\n     }");
                ret.append("\n     else{");
                ret.append("\n          if(isNaN(forma.indice.value)){");
                ret.append("\n               alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAlertNumeroPreguntaValorNumerico")+"\");");
                ret.append("\n               forma.indice.value=tempo;    ");
                ret.append("\n               forma.indice.focus();    ");
                ret.append("\n               forma.indice.select();    ");
                ret.append("\n               return(false);    ");
                ret.append("\n          }");
                ret.append("\n     } ");
                ret.append("\n     tempo = forma.question.value;    ");
                ret.append("\n     trim(forma.question);    ");
                ret.append("\n     if(forma.question.value==\"\"){    ");
                ret.append("\n         alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAlertCapturarTextoPregunta")+"\");    ");
                ret.append("\n         forma.question.value=tempo;    ");
                ret.append("\n         forma.question.focus();    ");
                ret.append("\n         forma.question.select();    ");
                ret.append("\n         return(false);    ");
                ret.append("\n     }    ");
                ret.append("\n     forma.question.value=tempo;    ");
                ret.append("\n     if(forma.groupqid.value==0){    ");
                ret.append("\n         alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAlertSeleccionaCategoriaPreguntas")+"\");    ");
                ret.append("\n         return(false);    ");
                ret.append("\n     }    ");
                ret.append("\n     tempo = forma.validoptions.value;    ");
                ret.append("\n     trim(forma.validoptions);    ");
                ret.append("\n     if(forma.validoptions.value==\"\"){    ");
                ret.append("\n         alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAlertCapturarNumeroOpcionesValidas")+"\");    ");
                ret.append("\n         forma.validoptions.value=tempo;    ");
                ret.append("\n         forma.validoptions.focus();    ");
                ret.append("\n         forma.validoptions.select();     ");
                ret.append("\n         return(false);    ");
                ret.append("\n     }    ");
                ret.append("\n     else{    ");
                ret.append("\n         if(isNaN(forma.validoptions.value)){    ");
                ret.append("\n              alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAlertOpcionesValidasNumerico")+"\");    ");
                ret.append("\n              forma.validoptions.value=tempo;    ");
                ret.append("\n              forma.validoptions.focus();    ");
                ret.append("\n              forma.validoptions.select();     ");
                ret.append("\n              return(false);    ");
                ret.append("\n         }    ");
                ret.append("\n         else{    ");
                ret.append("\n              if(forma.validoptions.value>opciones){    ");
                ret.append("\n                   alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNumeroOpcionesValidasMenorIgualOpcionesSetSeleccionado")+" \");    ");
                ret.append("\n                   forma.validoptions.value=tempo;    ");
                ret.append("\n                   forma.validoptions.focus();    ");
                ret.append("\n                   forma.validoptions.select();     ");
                ret.append("\n                   return(false);    ");
                ret.append("\n              }    ");
                ret.append("\n         }    ");
                ret.append("\n     }    ");
                ret.append("\n     forma.validoptions.value=tempo;    ");

                ret.append("\n     //validacion opciones    ");
                ret.append("\n     for (cnt = 23; cnt < forma.length-"+tmpFinal+"; cnt++){    ");
                ret.append("\n       tempo=forma.elements[cnt].value;");
                ret.append("\n       trim(forma.elements[cnt]);");
                ret.append("\n      if(forma.elements[cnt].value==\"\"){    ");
                ret.append("\n       alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAlertCapturarTodaInformacionRequerida")+"\");    ");
                ret.append("\n        forma.elements[cnt].value=tempo;");
                ret.append("\n        forma.elements[cnt].focus();    ");
                ret.append("\n        forma.elements[cnt].select();    ");
                ret.append("\n       error = 1    ");
                ret.append("\n       break;    ");
                ret.append("\n      }    ");
                ret.append("\n      else{    ");
                ret.append("\n       if(isNaN(forma.elements[cnt].value)){    ");
                ret.append("\n        alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAlertSoloNumeros")+"\");    ");
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
                ret.append("\n             alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAlertOrdenCERONoPermitido")+"\");    ");
                ret.append("\n             forma.elements[cnt].focus();    ");
                ret.append("\n             forma.elements[cnt].select();    ");
                ret.append("\n             error=1;    ");
                ret.append("\n             break;    ");
                ret.append("\n           }    ");
                ret.append("\n          if(forma.elements[cnt].value>opciones){    ");
                ret.append("\n           alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAlertORDENPreguntaNumeroMaximo")+": \"+opciones+\".\\n"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAlertPorFavorCambialo")+"\");    ");
                ret.append("\n           forma.elements[cnt].focus();    ");
                ret.append("\n           forma.elements[cnt].select();    ");
                ret.append("\n           error=1;    ");
                ret.append("\n           break;    ");
                ret.append("\n          }    ");
                ret.append("\n          else{    ");
                ret.append("\n               ");
                ret.append("\n           for (i=0; i<capturados.length;i++){    ");
                ret.append("\n            if(forma.elements[cnt].value==capturados[i]){    ");
                ret.append("\n             alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAlertNoPuedeRepetirOrdenCambialo")+"\");    ");
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

                boolean booOpEval = true;
                String strOpEval = "; Display:none";
                if(oRSsesion.getTypeID()!=1){
                    ret.append("\n     if(porcentaje!=100){    ");
                    ret.append("\n      alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAlertSumaValor100")+": \"+porcentaje+\"%\");    ");
                    ret.append("\n      return(false);    ");
                    ret.append("\n     }    ");
                    strOpEval = "";
                    booOpEval = false;
                 }

                ret.append("\n     forma.xml.value=xml;    ");

                ret.append("\n   replaceChars(forma.description);");
                ret.append("\n   replaceChars(forma.answer);");

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
                ret.append("\n         function ventana(accion,sizze){    ");
                ret.append("\n             ");
                ret.append("\n              window.open(accion+\"&jsession="+request.getSession().getId()+" \",\"\",sizze);    ");
                ret.append("\n         }    ");
                ret.append("\n        ");
                ret.append("\n         function recarga(forma){    ");
                ret.append("\n             ");
                
                ret.append("\n              forma.action=\""+urlup+"\";    ");
                ret.append("\n              forma.submit();    ");
                ret.append("\n         }    ");
                ret.append("\n             ");
                ret.append("\n             ");
                ret.append("\n         function regreso(param){    ");
                ret.append("\n             ");
                ret.append("\n            nparam = new String(param); ");
                ret.append("\n            inicial = nparam.split('='); ");
                ret.append("\n            if(inicial[0]=='groupqid') forma.hgroupqid.value=inicial[1]; ");
                ret.append("\n            if(inicial[0]=='textid') forma.htextid.value=inicial[1];        ");
                ret.append("\n            if(inicial[0]=='freqanswerid') forma.hfreqanswerid.value=inicial[1];  ");
                ret.append("\n            if(inicial[0]=='seccion') forma.hseccion.value=inicial[1];  ");
                ret.append("\n              forma.action='"+urlup+"';");
                ret.append("\n              forma.submit();    ");
                ret.append("\n             ");
                ret.append("\n         }    ");
                ret.append("\n         function cambia(){    ");
                ret.append("\n             ");
                ret.append("\n             ");
                ret.append("\n             if(forma.validate.checked==true){ ");
                ret.append("\n                 forma.hvalidate.value=1; ");
                ret.append("\n             }    ");
                ret.append("\n             else {    ");
                ret.append("\n                 forma.hvalidate.value=0; ");
                ret.append("\n             }    ");
                ret.append("\n             ");
                ret.append("\n             ");
                ret.append("\n         }    ");
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
                ret.append("\n             ");
                ret.append("\n    </script>    ");

                String idP =request.getParameter("id");
                RecQuestion oRQ = new RecQuestion();
                oRQ.setIdtm(idtm);
                oRQ.setQuestionID(Integer.parseInt(idP));
                oRQ.load();
                String paramS = new String("");

                RecSurvey oRS = new RecSurvey();
                oRS.setIdtm(idtm);
                oRS.setResID(base.getId());
                oRS.load();
                RecOrderQuestion oROQ = new RecOrderQuestion();
                oROQ.setIdtm(idtm);
                oROQ.setSurveyid(oRS.getSurveyID());
                oROQ.setQuestionid(Integer.parseInt(idP));
                oROQ.load();

                long paramI =0;
                
                SWBResourceURL urluqp = paramsRequest.getRenderUrl();
                urluqp.setMode(urluqp.Mode_ADMIN);
                urluqp.setAction("updatequestion_p");
                ret.append("\n<div class=\"swbform\">");
                ret.append("\n<fieldset>");
                ret.append("\n<form name=\"forma\" action=\""+urluqp+"\" method=\"GET\" onsubmit=\"return valida(forma);\">");
                ret.append("\n<table border=0 cellspacing=1 width=\"100%\">");
                ret.append("\n<tr><td colspan=2 >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgEdicionPregunta")+"");
                ret.append("<input type=\"hidden\" name=\"htextid\" value=\"-1\">");
                ret.append("\n<input type=\"hidden\" name=\"hgroupqid\" value=\"-1\">");
                ret.append("\n<input type=\"hidden\" name=\"hfreqanswerid\" value=\"-1\">");
                ret.append("\n<input type=\"hidden\" name=\"hseccion\" value=\"-1\">");
                ret.append("\n</td></tr>");
                paramS = "";
                if (request.getParameter("indice")!=null){
                    paramS=request.getParameter("indice");
                }
                else{
                    paramS=Integer.toString(oROQ.getOrdernum());
                }
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNumeroPregunta")+":</td>");
                ret.append("\n<td ><input class=campos type=text size=3 name=\"indice\"  value=\""+paramS+"\" maxlength=4></td></tr>");

                if (request.getParameter("description")!=null){
                    paramS=request.getParameter("description");
                }
                else{
                    if(oRQ.getInstruction()!=null)
                    paramS=oRQ.getInstruction();
                }
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgDescripcion")+":</td>");
                ret.append("\n<td ><textarea name=\"description\" cols=\"40\" rows=\"4\" maxlength=255>"+paramS.replaceAll("<br>","\r")+"</textarea></font></td></tr>");
                if (request.getParameter("question")!=null){
                    paramS=request.getParameter("question");
                }
                else{
                    paramS=oRQ.getQuestion();
                }
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgPregunta")+":</td>");
                ret.append("\n<td ><input class=campos type=\"text\" name=\"question\" value=\""+paramS+"\" size=\"60\" maxlength=4000></td></tr>");

                paramS = "";
                String tempTVal = " style=\"visibility:hidden\" ";

                if (request.getParameter("validate")!=null  ){
                    paramS="checked";
                    tempTVal = "";
                }
                else{
                    if(oRQ.getValidate()==1 && request.getParameter("hvalidate")==null) {
                            paramS=" checked";
                            tempTVal = "";
                    }
                    else{
                       if(oRQ.getValidate()==0 && request.getParameter("hvalidate")==null) {
                            paramS="";
                            tempTVal = " style=\"visibility:hidden\" ";
                        }
                        else{
                            if(request.getParameter("hvalidate")!=null && request.getParameter("hvalidate").equals("0")){
                                paramS="";
                                tempTVal = " style=\"visibility:hidden\" ";
                            }
                       }
                    }
                }
                ret.append("\n<tr><td  width=200 align=right><input type=\"hidden\" name=\"hvalidate\" value=\"-1\">"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgValidacion")+":</td>");
                ret.append("\n<td ><input type=\"checkbox\" name=\"validate\" value=\"1\" "+paramS+" onclick=\"javascript:cambia();recarga(forma);\">");
                paramI = 0;
                if (request.getParameter("textid")!=null){
                    if (!request.getParameter("htextid").equals("-1")) paramI=Integer.parseInt(request.getParameter("htextid"));
                    else paramI=Integer.parseInt(request.getParameter("textid"));
                }
                else{
                    if(request.getParameter("htextid")==null) paramI=(int)oRQ.getCodeID();
                }
                
                ret.append("\n"+oRQ.getCatalogTypeList((int)paramI,tempTVal)+"<button dojoType=\"dijit.form.Button\" type=\"button\" name=\"btn_textid\" "+tempTVal+" onclick=\"javascript:ventana('"+urlact+"?regreso=1','width=550, height=500');\">"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_btnNuevaValidacion")+"</button></td></tr>");
                paramI = 0;
                if (request.getParameter("groupqid")!=null){
                    if (Integer.parseInt(request.getParameter("hgroupqid"))!=-1) paramI=Integer.parseInt(request.getParameter("hgroupqid"));
                    else paramI=Integer.parseInt(request.getParameter("groupqid"));
                }
                else{
                    paramI=oRQ.getCategoryID();
                }
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgCategoriaPregunta")+":</td>");
                ret.append("\n<td >"+oRQ.getGroupQuestionList((int)paramI) +"<button dojoType=\"dijit.form.Button\" type=\"button\" name=\"btn_groupq\" onclick=\"javascript:ventana('"+urlmgq+"?regreso=1','width=400, height=290');\">"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_btnNuevaCategoria")+"</button></td></tr>");
                paramS = "";
                if (request.getParameter("required")!=null){
                    paramS="checked";
                }
                else{
                    if(oRQ.getRequired()==1) paramS="checked";
                }
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgRequerida")+":</td>");
                ret.append("\n<td ><input type=\"checkbox\" name=\"required\" value=\"1\" "+paramS+"></td></tr>");
                paramS= "";

                long tempControl = 1;
                paramI = 0;
                if (request.getParameter("controlid")!=null) {
                    paramI=Integer.parseInt(request.getParameter("controlid"));
                    tempControl = paramI;
                }
                else{
                    paramI=(int)oRQ.getControlID();
                    tempControl = paramI;
                }

                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgTipoControl")+":</td>");
                ret.append("\n<td valign=top >"+oRQ.getControlCatalogList((int)paramI," onchange=\"recarga(forma);\"")+"</td></tr>");
                long tmpFAnswer=0;
                                if(request.getParameter("freqanswerid")!=null){
                                    if(Integer.parseInt(request.getParameter("hfreqanswerid"))!=-1) tmpFAnswer = Long.parseLong(request.getParameter("hfreqanswerid"));
                                    else tmpFAnswer = Long.parseLong(request.getParameter("freqanswerid"));
                                }
                                else{
                                    tmpFAnswer=oRQ.getFreqAnswerID();
                                }
                               paramS = "";
                                String bandera=new String("disabled");
                                if(tempControl==1||tempControl==2){
                                        bandera = "";
                                        if(request.getParameter("answer")!=null){
                                            paramS = request.getParameter("answer");
                                        }
                                        else{
                                            try{
                                                String strxml=new String(oRQ.getStringXML()) ;
                                                org.w3c.dom.Document  docxmlQ = SWBUtils.XML.getNewDocument();
                                                docxmlQ = SWBUtils.XML.xmlToDom(oRQ.getStringXML());
                                                Element resource,res;
                                                res = (Element) docxmlQ.getFirstChild();
                                                org.w3c.dom.NodeList nodesQ = res.getChildNodes();
                                                if (strxml != null){
                                                    docxmlQ = SWBUtils.XML.xmlToDom(strxml);
                                                    if (docxmlQ!=null){
                                                        resource = (Element) docxmlQ.getFirstChild();
                                                        org.w3c.dom.NodeList nodes = resource.getChildNodes();
                                                        for (int i = 0; i < nodes.getLength(); i++) {
                                                            org.w3c.dom.Node nodeQ = nodesQ.item(i);
                                                            if(tempControl==1 || tempControl==2) {
                                                                if(nodeQ.getChildNodes().getLength()==1)
                                                                paramS = nodeQ.getFirstChild().getNodeValue();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            catch(Exception e){log.error(paramsRequest.getLocaleString("errormsg_Question_getAdmHtml_logErrorCargarDomPregunta"),e);}
                                        }
                                }
                                ret.append("<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgRespuestaCorrecta")+":</td>");
                                ret.append("\n<td ><textarea title=\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgCapturaRespuestaCorrectaPregunta")+" \"  rows=3 cols=40 name=\"answer\"  "+bandera+" maxlength=500>"+paramS.replaceAll("<br>","\r")+"</textarea></td></tr>");

                if (request.getParameter("validoptions")!=null){
                    paramS=request.getParameter("validoptions");
                }
                else{
                    paramS=Long.toString(oRQ.getValidOptions());
                }
                String tempOpciones=paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNumeroOpcionesValidas")+":";
                String tempSet = paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgSetRespuestas")+":";
                String tempStrOp = "";
                if(tempControl==1 || tempControl==2 || tempControl==7) {

                    tmpFAnswer = 1;
                    paramS="0";
                    tempStrOp = " disabled "; // style=\" visibility:hidden \"";
                }
                String strTemp=" style=\"visibility:hidden\" ";
                if(tmpFAnswer!=1 && tmpFAnswer!=0 ) strTemp = "  ";
                ret.append("\n<tr><td  width=200 align=right>"+tempSet+"</td>");
                ret.append("\n<td >"+oRQ.getFreqAnswerList((int)tmpFAnswer,"onchange=\"recarga(forma); \""+tempStrOp+"  ")+"<input type=\"button\" value=\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNuevoSet")+"\" name=\"btn_set\" onclick=\"ventana('"+urlmfa+"?regreso=1','width=550, height=520');\" "+tempStrOp+" class=boton>&nbsp;<input type=\"button\" value=\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_btnEditarSet")+"\" name=\"btn_eset\" onclick=\"ventana('"+urlmfa+"?regreso=1&questionid="+idP+"&id="+tmpFAnswer+"','width=550, height=500');\" "+strTemp+" class=boton></td></tr>");
                ret.append("\n<tr><td  width=200 align=right>"+tempOpciones+"</td>");
                ret.append("\n<td ><input type=\"text\" name=\"validoptions\" value=\""+paramS+"\" title=\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNumerico")+"\" "+tempStrOp+" maxlength=3></td></tr>");
                paramS = "";
                if (request.getParameter("reuse")!=null){
                    paramS="checked";
                }
                else{
                    if(oRQ.getIsReUse()==1) paramS = "checked";
                }
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgReutilizarPregunta")+":</td>");
                ret.append("\n<td ><input id=\"reuse\" type=\"checkbox\"  name=\"reuse\" value=\"1\" "+paramS+"></td></tr>");
                paramI = 0;
                if (request.getParameter("seccion")!=null){
                    if (Integer.parseInt(request.getParameter("hseccion"))!=-1) paramI=Integer.parseInt(request.getParameter("hseccion"));
                    else paramI=Integer.parseInt(request.getParameter("seccion"));
                }
                else{
                    paramI=oROQ.getSubjectid();
                }
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgGrupo")+":</td>");
                ret.append("\n<td >"+oRS.getSubjectList(paramI,"  ",idtm));
                ret.append("<input  type=\"button\" name=\"btnnewsection\" value=\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNuevoGrupo")+"\" onclick=\"ventana('"+urlas+"?regreso=1','width=580, height=490');\" class=boton></td></tr>");
                ret.append("\n<tr><td colspan=2>");

                ret.append("<table border=0 cellspacing=1 cellpadding=0 width=100%>");
                strTemp=" style=\"visibility:hidden\" ";
                if(tmpFAnswer!=1 && tmpFAnswer!=0 ) strTemp = "  ";
                ret.append("\n<tr><td colspan=3>");
                if(!booOpEval) {
                ret.append("<tr><td align=center >");
                ret.append(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgOpcion"));
                ret.append("&nbsp;</td>");
                ret.append("<td align=center >");
                if(tmpFAnswer!=1) ret.append(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgValor")+" %");
                ret.append("</td>");
                if(tmpFAnswer!=1) ret.append("<td align=center >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgOrden")+"</td></tr>");
                }
                else{
                    ret.append("<tr><td align=center >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgOpcion")+"</td>");
                    ret.append("<td align=center >");
                    ret.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                    ret.append("</td>");
                    ret.append("<td align=center >");
                    if(tmpFAnswer!=1) ret.append(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgOrden") );
                    ret.append("&nbsp;</td></tr>");
                }



                // se agrega la parte de las opciones
                RecFreqAnswer oFA = new RecFreqAnswer();
                oFA.setIdtm(idtm);
                oFA.setFreqanswerid(tmpFAnswer);
                oFA.load();
                long faid = oRQ.getFreqAnswerID();
                try{
                    //String strxml=new String(oRQ.getFreqAnswer().getStringXml()) ;
                    String strxml=new String(oFA.getStringxml()) ;
//                    DocumentBuilderFactory dbf;
//                    DocumentBuilder db;
//                    dbf=DocumentBuilderFactory.newInstance();
//                    db=dbf.newDocumentBuilder();
                    org.w3c.dom.Document  docxml = SWBUtils.XML.getNewDocument(); //db.newDocument();
                    org.w3c.dom.Document  docxmlQ = SWBUtils.XML.getNewDocument(); //db.newDocument();
                    docxmlQ = SWBUtils.XML.xmlToDom(oRQ.getStringXML());
                    Element resource,res;
                    res = (Element) docxmlQ.getFirstChild();
                    org.w3c.dom.NodeList nodesQ = res.getChildNodes();
                    if (strxml != null){
                        docxml = SWBUtils.XML.xmlToDom(strxml);
                        if (docxml!=null){
                            resource = (Element) docxml.getFirstChild();
                            org.w3c.dom.NodeList nodes = resource.getChildNodes();
                            for (int i = 0; i < nodes.getLength(); i++) {
                                org.w3c.dom.Node node = nodes.item(i);
                                org.w3c.dom.Node nodeQ = nodesQ.item(i);

                                String val = node.getFirstChild().getNodeValue();
                                String att = node.getAttributes().getNamedItem("id").getNodeValue();

                                String idQ=att;
                                String valueQ = "0";
                                String referQ= att;

                                if(tmpFAnswer==faid ) {
                                    idQ = nodeQ.getAttributes().getNamedItem("id").getNodeValue();
                                    valueQ = nodeQ.getAttributes().getNamedItem("value").getNodeValue();
                                    referQ = nodeQ.getAttributes().getNamedItem("refer").getNodeValue();
                                }
                                String strVer =" style=\"font-family:verdana; font-size:9; width:100; height:18\" ";

                                if(tmpFAnswer==1){
                                    strVer =" style=\"font-family:verdana; font-size:9; width:100; height:18; visibility:hidden\" ";
                                        if (strOpEval.equals("")){
                                            strOpEval = "; visibility:hidden";
                                        }
                                }
                                ret.append("\n<tr><td ><input type=hidden name=refer value=\""+referQ+"\">"+att+")&nbsp;" + val +"</td><td>" );
                                ret.append("<input type=\"text\" name=\"valor\" value=\""+valueQ+"\" style=\"font-family:verdana; font-size:9; width:100; height:18"+strOpEval+"\" maxlength=3>&nbsp;</td><td>");
                                ret.append("<input type=\"text\" name=\"orden\" value=\""+idQ+"\" "+strVer+" size=3 maxlength=3>");
                               ret.append("\n</td></tr>");
                            }
                           ret.append("<input type=\"hidden\" name=\"xml\"><input type=\"hidden\" name=\"id\" value=\""+oRQ.getQuestionID()+"\">");
                           ret.append("</table>");
                        }
                    }
                }
                catch(Exception e){log.error(paramsRequest.getLocaleString("errormsg_Question_getAdmHtml_logErrorCargarDomSetRespuestas"),e);}
                ret.append("\n</td></tr>");
                ret.append("\n<tr><td colspan=2 align=right><hr noshade size=1><input type=\"submit\" name=\"Actualizar\" value=\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_btnEnviar")+"\" class=boton>");
                if(request.getSession().getAttribute("retq")!=null){
                   ret.append("&nbsp;<input type=\"button\" value=\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_btnCancelar")+"\" onclick=\"window.close();\" class=boton>"); //window.opener.regresa();
                }
                else
                {
                    SWBResourceURL urlBack = paramsRequest.getRenderUrl();
                    urlBack.setAction("select_p");
                    ret.append("&nbsp;<input type=\"button\" name=\"btn_cancelar\" value=\""+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgRegresar")+"\" onclick=\"javascript:window.location='"+urlBack+"'\">");
                }
                ret.append("</td></tr>");
                ret.append("</table></form>");
                ret.append("\n</fieldset>");
                ret.append("\n</div>");
                
             }
        }

       

        if (accion.equalsIgnoreCase("add_p")) {
            int tmpOpciones = 25; //24;
            int tmpFinal = 3;
            if(request.getSession().getAttribute("regreso")!=null)request.getSession().setAttribute("regreso",null);
            if(request.getSession().getAttribute("retq")!=null){
                tmpOpciones = 25;
                tmpFinal=3;
            }
            RecQuestion objG = new RecQuestion();
            objG.setIdtm(idtm);
            // secci�n de agregar Preguntas
            ret.append("\n    <script type=\"text/javascript\">    ");
            ret.append("\n        ");
            ret.append("\n    function valida(forma){    ");
            ret.append("\n     var error=0;    ");
            ret.append("\n     var opciones = (forma.length-"+tmpOpciones+")/3;    ");
            ret.append("\n     var capturados = new Array();    ");
            ret.append("\n     var x=0;    ");
            ret.append("\n     var porcentaje=0;    ");
            ret.append("\n     var xml = new String(\"\");    ");
            ret.append("\n         ");
            ret.append("\n     var tempo;    ");
            ret.append("\n     tempo = forma.indice.value;    ");
            ret.append("\n     trim(forma.indice);    ");
            ret.append("\n     if(forma.indice.value==\"\"){    ");
            ret.append("\n         alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAlertTienesCapturarNumeroPregunta")+"\");    ");
            ret.append("\n         forma.indice.value=tempo;    ");
            ret.append("\n         forma.indice.focus();    ");
            ret.append("\n         forma.indice.select();    ");
            ret.append("\n         return(false);    ");
            ret.append("\n     }");
            ret.append("\n     else{");
            ret.append("\n          if(isNaN(forma.indice.value)){");
            ret.append("\n               alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNumeroPreguntaValorNumerico")+"\");");
            ret.append("\n               forma.indice.value=tempo;    ");
            ret.append("\n               forma.indice.focus();    ");
            ret.append("\n               forma.indice.select();    ");
            ret.append("\n               return(false);    ");
            ret.append("\n          }");
            ret.append("\n     } ");
            ret.append("\n     tempo = forma.question.value;    ");
            ret.append("\n     trim(forma.question);    ");
            ret.append("\n     if(forma.question.value==\"\"){    ");
            ret.append("\n         alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgCapturarTextoPregunta")+"\");    ");
            ret.append("\n         forma.question.value=tempo;    ");
            ret.append("\n         forma.question.focus();    ");
            ret.append("\n         forma.question.select();    ");
            ret.append("\n         return(false);    ");
            ret.append("\n     }    ");
            ret.append("\n     forma.question.value=tempo;    ");
            ret.append("\n     if(forma.groupqid.value==0){    ");
            ret.append("\n         alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgSeleccionaCategoriaPreguntasValida")+"\");    ");
            ret.append("\n         return(false);    ");
            ret.append("\n     }    ");
            ret.append("\n     tempo = forma.validoptions.value;    ");
            ret.append("\n     trim(forma.validoptions);    ");
            ret.append("\n     if(forma.validoptions.value==\"\"){    ");
            ret.append("\n         alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgCapturarNumeroOpcionesValidas")+"\");    ");
            ret.append("\n         forma.validoptions.value=tempo;    ");
            ret.append("\n         forma.validoptions.focus();    ");
            ret.append("\n         forma.validoptions.select();     ");
            ret.append("\n         return(false);    ");
            ret.append("\n     }    ");
            ret.append("\n     else{    ");
            ret.append("\n         if(isNaN(forma.validoptions.value)){    ");
            ret.append("\n              alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgOpcionesValidasNumerico")+"\");    ");
            ret.append("\n              forma.validoptions.value=tempo;    ");
            ret.append("\n              forma.validoptions.focus();    ");
            ret.append("\n              forma.validoptions.select();     ");
            ret.append("\n              return(false);    ");
            ret.append("\n         }    ");
            ret.append("\n         else{    ");
            ret.append("\n              if(forma.validoptions.value>opciones){    ");
            ret.append("\n                   alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNumeroOpcionesValidasMenorIgualNumeroOpcionesSetSeleccionado")+"\");    ");
            ret.append("\n                   forma.validoptions.value=tempo;    ");
            ret.append("\n                   forma.validoptions.focus();    ");
            ret.append("\n                   forma.validoptions.select();     ");
            ret.append("\n                   return(false);    ");
            ret.append("\n              }    ");
            ret.append("\n         }    ");
            ret.append("\n     }    ");
            ret.append("\n     forma.validoptions.value=tempo;    ");
            ret.append("\n     //validacion opciones    ");
            ret.append("\n     for (cnt = 22; cnt < forma.length-"+tmpFinal+"; cnt++){    ");
            ret.append("\n       tempo=forma.elements[cnt].value;");
            ret.append("\n       trim(forma.elements[cnt]);");
            ret.append("\n      if(forma.elements[cnt].value==\"\"){    ");
            ret.append("\n       alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgCapturarTodaInformacion")+"\");    ");
            ret.append("\n        forma.elements[cnt].value=tempo;");
            ret.append("\n        forma.elements[cnt].focus();    ");
            ret.append("\n        forma.elements[cnt].select();    ");
            ret.append("\n       error = 1;    ");
            ret.append("\n       break;    ");
            ret.append("\n      }    ");
            ret.append("\n      else{    ");
            ret.append("\n       if(isNaN(forma.elements[cnt].value)){    ");
            ret.append("\n        alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgSoloNumerosPorFavor")+"\");    ");
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
            ret.append("\n             alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgOrdenCERONoPermitido")+"\");    ");
            ret.append("\n             forma.elements[cnt].focus();    ");
            ret.append("\n             forma.elements[cnt].select();    ");
            ret.append("\n             error=1;    ");
            ret.append("\n             break;    ");
            ret.append("\n           }    ");
            ret.append("\n          if(forma.elements[cnt].value>opciones){    ");
            ret.append("\n           alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgEnORDENPreguntaNumeroMaximo")+": \"+opciones+\".\\n"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgPorFavorCambialo")+"\");    ");
            ret.append("\n           forma.elements[cnt].focus();    ");
            ret.append("\n           forma.elements[cnt].select();    ");
            ret.append("\n           error=1;    ");
            ret.append("\n           break;    ");
            ret.append("\n          }    ");
            ret.append("\n          else{    ");
            ret.append("\n               ");
            ret.append("\n           for (i=0; i<capturados.length;i++){    ");
            ret.append("\n            if(forma.elements[cnt].value==capturados[i]){    ");
            ret.append("\n             alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNoPuedeRepetirOrdenCambialo")+"\");    ");
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

            boolean booOpEval = true;
            String strOpEval = "; Display:none";
            if(oRSsesion.getTypeID()!=1){
                ret.append("\n     if(porcentaje!=100){    ");
                ret.append("\n      alert(\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgSumaDebeSer100")+"\\n"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgSumaActual")+": \"+porcentaje+\"%\");    ");
                ret.append("\n      return(false);    ");
                ret.append("\n     }    ");
                strOpEval = "";
                booOpEval = false;
             }

            ret.append("\n     forma.xml.value=xml;    ");

            ret.append("\n   replaceChars(forma.description);");
            ret.append("\n   replaceChars(forma.answer);");

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
            ret.append("\n         function ventana(accion,sizze){    ");
            ret.append("\n             ");
            ret.append("\n              window.open(accion+\"&jsession="+request.getSession().getId()+"\",\"\",sizze);    ");
            ret.append("\n         }    ");
            ret.append("\n        ");
            ret.append("\n         function recarga(forma){    ");
            ret.append("\n             ");
            ret.append("\n              forma.action=\""+urlap+"\";    "); //add_p
            ret.append("\n              forma.submit();    ");
            ret.append("\n         }    ");
            ret.append("\n             ");
            ret.append("\n             ");
            ret.append("\n         function regreso(param){    ");
            ret.append("\n             ");
            ret.append("\n            nparam = new String(param); ");
            ret.append("\n            var inicial =nparam.split('='); ");
            ret.append("\n             ");
            ret.append("\n            if(inicial[0]=='groupqid') forma.hgroupqid.value=inicial[1]; ");
            ret.append("\n            if(inicial[0]=='textid') forma.htextid.value=inicial[1];        ");
            ret.append("\n            if(inicial[0]=='freqanswerid') forma.hfreqanswerid.value=inicial[1];  ");
            ret.append("\n            if(inicial[0]=='seccion') forma.hseccion.value=inicial[1];  ");
            ret.append("\n              forma.action=\""+urlap+"\";    "); //add_p
            ret.append("\n              forma.submit();    ");
            ret.append("\n             ");
            ret.append("\n         }    ");
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
            ret.append("\n    </script>    ");


            String paramS = new String("");
            int paramI =0;
            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<form name=\"forma\" action=\""+urlagp+"\" method=\"GET\" onsubmit=\"return valida(forma);\" >"); //agregar_p
            ret.append("<input type=\"hidden\" name=\"htextid\" value=\"-1\">");
            ret.append("\n<input type=\"hidden\" name=\"hgroupqid\" value=\"-1\">");
            ret.append("\n<input type=\"hidden\" name=\"hfreqanswerid\" value=\"-1\">");
            ret.append("\n<input type=\"hidden\" name=\"hseccion\" value=\"-1\">");
            
            ret.append("\n<fieldset>");
            ret.append("\n<legend>"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNuevaPregunta")+"</legend>");
            ret.append("\n<table border=0 cellspacing=0 cellpadding=2 width=\"100%\">");
            paramS = "";
            if (request.getParameter("indice")!=null) paramS=request.getParameter("indice");
            ret.append("<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNumeroPregunta")+":</td>");
            ret.append("\n<td ><input class=campos type=text size=3 name=\"indice\"  value=\""+paramS+"\" maxlength=4></td></tr>");

            if (request.getParameter("description")!=null) paramS=request.getParameter("description");
            ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgDescripcion")+":</td>");
            ret.append("\n<td ><textarea name=\"description\" cols=\"40\" rows=\"4\" maxlength=255>"+paramS.replaceAll("<br>","\r")+"</textarea></td></tr>");

            if (request.getParameter("question")!=null) paramS=request.getParameter("question");
            ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgPregunta")+":</td>");
            ret.append("\n<td ><input class=campos type=\"text\" name=\"question\" value=\""+paramS+"\"  size=\"60\" maxlength=4000></td></tr>");

             paramS = "";
             String tempTVal = " style=\"visibility:hidden\" ";

                if (request.getParameter("validate")!=null  ){
                    paramS="checked";
                    tempTVal = "";
                }

            ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgValidacion")+":</td>");
            ret.append("\n<td ><input type=\"checkbox\" name=\"validate\" value=\"1\" "+paramS+" onclick=\"recarga(forma);\" >");

            paramI = 0;
            if (request.getParameter("textid")!=null){
                if(Integer.parseInt(request.getParameter("htextid"))!=-1) paramI=Integer.parseInt(request.getParameter("htextid"));
                else paramI=Integer.parseInt(request.getParameter("textid"));
            }
            ret.append("\n"+objG.getCatalogTypeList(paramI,tempTVal)+"<input type=\"button\" value=\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNuevaValidacion")+"\" name=\"btn_textid\" onclick=\"ventana('"+urlact+"?regreso=1','width=550, height=500');\" "+tempTVal+" class=boton></td></tr>");

            paramI = 0;
            if (request.getParameter("groupqid")!=null){
                if (Integer.parseInt(request.getParameter("hgroupqid"))!=-1) paramI=Integer.parseInt(request.getParameter("hgroupqid"));
                else paramI=Integer.parseInt(request.getParameter("groupqid"));
            }
            ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgCategoriaPregunta")+":</td>");
            ret.append("\n<td >"+objG.getGroupQuestionList(paramI) +"<input type=\"button\" value=\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNuevaCategoria")+"\" name=\"btn_groupq\" onclick=\"ventana('"+urlmgq+"?regreso=1','width=400, height=290');\" class=boton></td></tr>");

            paramS = "";
            if (request.getParameter("required")!=null) paramS="checked";
            ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgRequerida")+":</td>");
            ret.append("\n<td ><input type=\"checkbox\" name=\"required\" value=\"1\" "+paramS+"></td></tr>");

            int tempControl = 1;
            paramI = 0;
            if (request.getParameter("controlid")!=null) {
                paramI=Integer.parseInt(request.getParameter("controlid"));
                tempControl = paramI;
            }
            ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgTipoControl")+":</td>");
            ret.append("\n<td valign=top >"+objG.getControlCatalogList(paramI," onchange=\"recarga(forma);\"")+"</td></tr>");
            long tmpFAnswer=0;
            if(request.getParameter("freqanswerid")!=null){
                if(Integer.parseInt(request.getParameter("hfreqanswerid"))!=-1) tmpFAnswer = Long.parseLong(request.getParameter("hfreqanswerid"));
                else tmpFAnswer = Long.parseLong(request.getParameter("freqanswerid"));
            }
            paramS = "";
            String bandera =" disabled ";
            String bandera2 = "";
            if(tempControl==1||tempControl==2||tempControl==7){
                    bandera = "";
                    bandera2 = " disabled ";
                    tmpFAnswer=1;
                    if(request.getParameter("answer")!=null)paramS = request.getParameter("answer");
            }
            ret.append("<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgRespuestaCorrecta")+":</td>");
            ret.append("\n<td ><textarea title=\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgCapturaRespuestaCorrectaPreguntaAbierta")+" \"  rows=3 cols=40 name=\"answer\" "+bandera+" maxlength=500>"+paramS.replaceAll("<br>","\r")+"</textarea></td></tr>");
            String strTemp=" style=\"visibility:hidden\" ";
            if(tmpFAnswer!=1 && tmpFAnswer!=0) strTemp = " ";
            ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgSetRespuestas")+":</td>");
            ret.append("\n<td >"+objG.getFreqAnswerList((int)tmpFAnswer,"onchange=\"recarga(forma);\" "+bandera2)+"<input type=\"button\" value=\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNuevoSet")+"\" name=\"btn_set\" onclick=\"ventana('"+urlmfa+"?regreso=1','width=580, height=520');\" "+bandera2+" class=boton>&nbsp;<input type=\"button\" value=\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgEditarSet")+"\" name=\"btn_eset\" onclick=\"ventana('"+urlmfa+"?regreso=1&id="+tmpFAnswer+"','width=550, height=500');\" "+strTemp+" class=boton></td></tr>");

            paramS= "0";
            if (request.getParameter("validoptions")!=null){
                paramS=request.getParameter("validoptions");
            }
            else{
                if(tempControl==1||tempControl==2||tempControl==7) paramS="0";
            }
            ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNumeroOpcionesValidas")+":</td>");
            ret.append("\n<td ><input type=\"text\" name=\"validoptions\" value=\""+paramS+"\" title=\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNumerico")+"\" size=3 "+bandera2+" maxlength=3></td></tr>");

            paramS = "";
            if (request.getParameter("reuse")!=null) paramS="checked";
            ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgReutilizarPregunta")+":</td>");
            ret.append("\n<td ><input id=\"reuse\" type=\"checkbox\" checked name=\"reuse\" value=\"1\" "+paramS+"></td></tr>");

             RecSurvey oRS = new RecSurvey();
             oRS.setIdtm(idtm);
                oRS.setResID(base.getId());
                 oRS.load();
            paramI = 0;
            if (request.getParameter("seccion")!=null){
                if (Integer.parseInt(request.getParameter("hseccion"))!=-1) paramI=Integer.parseInt(request.getParameter("hseccion"));
                else paramI=Integer.parseInt(request.getParameter("seccion"));
            }
            ret.append("<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgGrupo")+":</td>");
            ret.append("\n<td >"+oRS.getSubjectList((int)paramI,"  ", idtm)); 
            ret.append("<input   type=\"button\" name=\"btnnewsection\" value=\""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNuevoGrupo")+"\" onclick=\"ventana('"+urlas+"?regreso=1','width=580, height=490');\" class=boton></td></tr>");
            ret.append("\n<tr><td colspan=2>");
            ret.append("\n<table border=0 cellspacing=1 cellpadding=1 width=100%>");
            strTemp=" style=\"visibility:hidden\" ";
            if(tmpFAnswer!=1 && tmpFAnswer!=0) strTemp = " ";
            ret.append("\n<tr><td colspan=3>");
            ret.append("<tr><td colspan=3>");
            if(!booOpEval) {
                ret.append("<tr><td >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgOpcion")+"</td>");
                ret.append("<td >");
                if(tmpFAnswer!=1) ret.append(""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgValor")+" %");
                else ret.append("&nbsp;");
                ret.append("</td>");
                ret.append("<td >");
                if(tmpFAnswer!=1) ret.append(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgOrden"));
                else ret.append("&nbsp;");
                ret.append("</td></tr>");
            }
            else{
                ret.append("<tr><td  >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgOpcion")+"</td>");
                ret.append("<td align=center>");
                ret.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                ret.append("</td>");
                ret.append("<td align=center >");
                if(tmpFAnswer!=1) ret.append(""+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgOrden"));
                ret.append("&nbsp;</td></tr>");
            }


            // se agrega la parte de las opciones
            if(tmpFAnswer==0)tmpFAnswer=objG.get1stFreqAnswerID();
            RecFreqAnswer oFA = new RecFreqAnswer();
            oFA.setIdtm(idtm);
            oFA.setFreqanswerid(tmpFAnswer);
            oFA.load();
            try{
                    String strxml=new String(oFA.getStringxml()) ;
                    org.w3c.dom.Document  docxml = SWBUtils.XML.getNewDocument(); //db.newDocument();
                    Element resource;
                    if (strxml != null){
                        docxml = SWBUtils.XML.xmlToDom(strxml);
                        if (docxml!=null){
                            resource = (Element) docxml.getFirstChild();
                            org.w3c.dom.NodeList nodes = resource.getChildNodes();
                            for (int i = 0; i < nodes.getLength(); i++) {
                                org.w3c.dom.Node node = nodes.item(i);
                                String val = node.getFirstChild().getNodeValue();
                                String att = node.getAttributes().getNamedItem("id").getNodeValue();
                                ret.append("\n<tr><td >");
                                ret.append("<input type=hidden name=refer value=\""+att+"\">"+att+")&nbsp;" + val + "</td>");
                                String strReadOnly = "";
                                String str100 = "0";
                                if(tmpFAnswer==1){
                                    strReadOnly = " readonly ";
                                    str100 = "100";
                                }

                                String strVer =" style=\"font-family:verdana; font-size:9; width:100; height:18\" ";

                                if(tmpFAnswer==1){
                                    strVer =" style=\"font-family:verdana; font-size:9; width:100; height:18; visibility:hidden\" ";
                                        if (strOpEval.equals("")){
                                            strOpEval = "; visibility:hidden";
                                        }
                                }



                                ret.append("<td><input size=4 type=text name=valor style=\"font-family:verdana; font-size:9"+strOpEval+"\" value=\""+str100+"\" "+strReadOnly+" maxlength=3>&nbsp;</td>");
                                ret.append("<td><input size=3 type=text name=orden value=\""+att+"\"  "+strVer+" size=\"3\" maxlength=3></td></tr>");
                            }
                           ret.append("<input type=\"hidden\" name=\"xml\">");
                           //ret.append("<tr><td colspan=6 align=center></td></tr></table></td></tr>");
                           ret.append("</table></td></tr>");
                            oRS = null;
                        }
                    }
                }
                catch(Exception e){log.error(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_logErrorCargarDomSetRespuestas"),e);}
            ret.append("</table>");
            ret.append("\n</fieldset>");
            ret.append("\n<fieldset>");
            ret.append("\n<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"enviar\">"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_btnEnviar")+"</button>");
            if(request.getSession().getAttribute("retq")!=null){
               ret.append("&nbsp;<button dojoType=\"dijit.form.Button\" type=\"button\" onclick=\"window.close();\">"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_btnCancelar")+"</button>"); //window.opener.regresa();
            }
            else
            {
                SWBResourceURL urlBack = paramsRequest.getRenderUrl();
            urlBack.setAction("select_p");
            ret.append("&nbsp;<button dojoType=\"dijit.form.Button\" type=\"button\" name=\"btn_cancelar\" onclick=\"javascript:window.location='"+urlBack+"'\">"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgRegresar")+"</button>");
            }
            ret.append("\n</fieldset>");
            ret.append("</form>");
            ret.append("\n</div>");
            
        }

        if (accion.equalsIgnoreCase("delete_p")) {
                ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgPreguntaEliminada"));
            Connection con = null;
            PreparedStatement sst =null;
            ResultSet rs =null;
            PreparedStatement dst =null;
            try{
                con = SWBUtils.DB.getDefaultConnection();
                sst = con.prepareStatement("select * from sr_orderquestion where questionid = ? and idtm=?");
                sst.setLong(1,Long.parseLong(request.getParameter("id")));
                sst.setString(2,idtm);
                rs = sst.executeQuery();
                if (rs.next()) {
                    ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgNoPuedeBorrarPregunta")+"\n"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgExistenRegistrosHaciendoReferencia")+"<br>");
                }
                else{
                    dst = con.prepareStatement("delete from sr_question where questionid = ? and idtm=? ");
                    dst.setLong(1, Long.parseLong(request.getParameter("id")));
                    dst.setString(2,idtm);
                    dst.executeUpdate();
                    if(dst!=null)dst.close();
                    log.error(paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgEliminaPreguntaId")+": "+request.getParameter("id")+" "+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgDelCatalogo"));
                }
                if(rs!=null)rs.close();
                if(sst!=null)sst.close();
                if(con!=null)con.close();
        }
        catch(Exception e){ log.error(paramsRequest.getLocaleString("errormsg_Question_getAdmHtml_logErrorBorrarRegistroDBQuestion"),e); }
        finally{
                dst=null;
                rs=null;
                sst=null;
                con=null;
            }
        accion="select_p";
        } 

        if (accion.equalsIgnoreCase("select_p")) {
            // secci�n de selecci�n de pregunta a editar

            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<form action=\""+urlup+"\" method=\"GET\">"); //update_p
            ret.append("\n<fieldset>");
            ret.append("<table border=0 width=100% cellpadding=2 cellspacing=0>");
            Connection con = null;
            PreparedStatement st =null;
            ResultSet rs =null;
            try{
                ret.append("\n<tr><td colspan=5>&nbsp;</td></tr>");
                ret.append("\n<tr><th width=\"70\" >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAction")+"</th>");
                ret.append("\n<th width=\"25\" >Id</th>");
                ret.append("\n<th >"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgPregunta")+"</th>");
                ret.append("\n<th >"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgCreacion")+"</th>");
                ret.append("\n<th >"+paramsRequest.getLocaleString("usrmsg_Question_LastUpdate")+"</th>");
                ret.append("\n</tr>");
                
                con = SWBUtils.DB.getDefaultConnection();
                st = con.prepareStatement("select * from sr_question where idtm=?");
                st.setString(1,idtm);
                rs = st.executeQuery();
                String rowColor="";
                boolean cambiaColor = true;
                while(rs.next()){
                    rowColor="bgcolor=\"#EFEDEC\"";
                    if(!cambiaColor) rowColor="";
                    cambiaColor = !(cambiaColor);
                    ret.append("\n<tr "+rowColor+"><td>");
                    long questionID =rs.getLong("questionid");
                    String question ="";
                    String tmpquestion=SWBUtils.IO.readInputStream(rs.getAsciiStream("question"));
                    //        rs.getString("question");
                    if(tmpquestion.length()>60) question=tmpquestion.substring(0,57)+" ... ";
                    else question=tmpquestion;
                    
                    
		    RecQuestion rque=new RecQuestion();
                    rque.setIdtm(idtm);
                    rque.setQuestionID((int)questionID);
                    rque.load();
                    
                    SWBResourceURL urlu = paramsRequest.getRenderUrl();
                    urlu.setAction("update_p");
                    urlu.setParameter("id",Long.toString(questionID));
                    SWBResourceURL urle = paramsRequest.getRenderUrl();
                    urle.setAction("delete_p");
                    urle.setParameter("id",Long.toString(questionID));
                    ret.append("\n<a href=\""+urle+"\" onclick=\"if(confirm('"+paramsRequest.getLocaleString("errormsg_ShureEraseQuestion")+"?')){ return (true);} else { return (false);}\" ><img src=\"/swbadmin/images/delete.gif\" border=0></a><a href=\""+urlu+"\" ><img src=\"/swbadmin/icons/editar_1.gif\" border=0></a>");
                    ret.append("\n</td><td>");
                    ret.append("\n"+questionID);
                    ret.append("\n</td><td>");
                    ret.append("\n"+question);
                    ret.append("\n</td><td>");
                    ret.append("\n"+sdf.format(rque.getCreated()));
                    ret.append("\n</td><td>");
                    ret.append("\n"+sdf.format(rque.getLastUpdate()));
                    ret.append("</td></tr>");
                }
                if(rs!=null)rs.close();
                if(st!=null)st.close();
                if(con!=null)con.close();
            }
            catch(Exception e){ log.error(paramsRequest.getLocaleString("errormsg_Question_getAdmHtml_msgErrorConsultarDBQuestion"),e); }
            finally{
                rs=null;
                st=null;
                con=null;
            }
            ret.append("</table>");
            ret.append("\n</fieldset>");
            ret.append("\n<fieldset>");
            ret.append("<button dojoType=\"dijit.form.Button\" type=\"button\" name=\"enviar\" onclick=\"javascript:window.location='"+urlap+"';\">"+paramsRequest.getLocaleString("usrmsg_Question_getAdmHtml_msgAgregar")+"</button>");
            ret.append("\n</fieldset>");
            ret.append("</form>");
            ret.append("\n</div>");
            
        }
        
        
        return ret.toString();
    }
}
