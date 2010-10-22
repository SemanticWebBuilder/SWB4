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

import com.infotec.wb.resources.survey.db.*;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 * Esta clase evalua la encuesta y obtiene la calificaci�n
 *
 * This class evaluates the survey and gets the score
 *
 * User: Juan Antonio Fernández Arias
 * 
 */
public class Evaluation
{

    private static Logger log = SWBUtils.getLogger(Evaluation.class);
    /*Preguntas relacionadas*/
    HashMap hm = null;
    ArrayList arr = null;
    Resource base = null;
    int finalscore = 0;

    /**
     * Set resource base with a new value
     * @param base input Resource object
     */
    public void setResourceBase(Resource base)
    {
        this.base = base;
    }

    /**
     * Obtains user evaluation for the survey
     * @param presponseid input string value
     * @param paddr input string with address value
     * @param request input parameters
     * @param pbaseid input Resource object
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @return a string value with html code
     */
    public String getEvaluation(String presponseid, String paddr, HttpServletRequest request, String pbaseid, SWBParamRequest paramsRequest )
    {
        StringBuffer ret = new StringBuffer();
        StringBuffer sourcetitle = new StringBuffer();
        Hashtable htsec = new Hashtable();
        RecSubject objSec = null;
        RecSurvey objSur = null;
        RecQuestion objQue = null;
        RecResponseUser objRes = null;
        String isec= null;
        String ast = null;
        String email = null;
        String strtype = null;
        String strmode = null;
        
        String idtm=base.getWebSiteId();
        
        long surveyid = 0;
        long typeid = 0;
        int prenum = 0;
        int secnum = 0;
        int ilen = 0;
        int i = 0;
        int score = 0;
        int moderate = 0;

        /*Preguntas relacionadas*/
        int childcontrolid = 0;
        int childrequired = 0;
        int childtextid = 0;
        int childvalidate = 0;
        long childfreqid = 0;
        String childquestionid = null;
        String childquestion = null;
        try
        {
            objRes = new RecResponseUser();
            objRes.setResponseID(Long.parseLong(presponseid));
            objRes.setIdtm(idtm);
            objRes.load();
            email = objRes.getUser();
            if(email.equalsIgnoreCase("Anonimo")||email.equalsIgnoreCase("Anónimo")||email.startsWith("0_")||email.startsWith("0")) email = paramsRequest.getLocaleString("usrmsg_MainSurvey_setResourceBase_msgAnonimo");
            score = objRes.getScore();
            surveyid = objRes.getSurveyId();

            objSur = new RecSurvey();
            objSur.setSurveyID(surveyid);
            objSur.setIdtm(idtm);
            objSur.load();
            moderate = Integer.parseInt( base.getAttribute("moderate","0") ); //objSur.getModerate();
            typeid = objSur.getTypeID(); // objType.getTypeID();

            strtype = paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgOpinion")  ;
            if(typeid == 2)
            {
                strtype =paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgEvaluacion")  ;
                strmode = " "+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgEstricta")  ;
                if(moderate == 1) strmode = " "+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgModerada")  ;
                strtype = strtype + strmode;
            }

            htsec = getSections(presponseid,surveyid);
            ilen = htsec.size();

            /*Preguntas relacionadas*/
            hm = new HashMap();
            CargaHM(surveyid);

            sourcetitle.append("\n<script language=\"javascript\">");
            sourcetitle.append("\n\nfunction DoNext(){");
            sourcetitle.append("\n    window.document.frmevaluation.comments.value=window.document.forma2.comments.value;");
            sourcetitle.append("\n    window.document.frmevaluation.review.value=0;");
            sourcetitle.append("\n  if (window.document.forma2.review.checked==true){");
            sourcetitle.append("\n    window.document.frmevaluation.review.value=1;");
            sourcetitle.append("\n  }");
            sourcetitle.append("\n    window.document.frmevaluation.statistic.value=0;");
            sourcetitle.append("\n  if (window.document.forma2.statistic.checked==true){");
            sourcetitle.append("\n    window.document.frmevaluation.statistic.value=1;");
            sourcetitle.append("\n  }");
            sourcetitle.append("\n   window.document.frmevaluation.submit();");
            sourcetitle.append("\n}");
            sourcetitle.append("\n\nfunction DoExit(){");
            sourcetitle.append("\n  window.document.frmexit.submit();");
            sourcetitle.append("\n}");
            sourcetitle.append("\n\nfunction ValidNumber(field){");
            sourcetitle.append("\n  var valid = \"0123456789\";");
            sourcetitle.append("\n  var ok = \"yes\";");
            sourcetitle.append("\n  var err = \""+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgAlertValorInvalido")  +"!\";");
            sourcetitle.append("\n  var temp;");
            sourcetitle.append("\n  trim(field);");
            sourcetitle.append("\n  if (field.value.length > 0){");
            sourcetitle.append("\n      for (var i=0; i<field.value.length; i++) {");
            sourcetitle.append("\n          temp = \"\" + field.value.substring(i, i+1);");
            sourcetitle.append("\n          if (valid.indexOf(temp) == \"-1\") ok = \"no\";");
            sourcetitle.append("\n      }");
            sourcetitle.append("\n      if ((field.value * 1 == 0) && (field.value != 0)) ok=\"no\";");
            sourcetitle.append("\n      if (field.value >100) ok=\"no\";");
            sourcetitle.append("\n      if (field.value.length <= 0 ) ok=\"no\";");
            sourcetitle.append("\n      if (ok == \"no\") {");
            sourcetitle.append("\n          alert(err);");
            sourcetitle.append("\n          field.focus();");
            sourcetitle.append("\n          field.select();");
            sourcetitle.append("\n          return(false);");
            sourcetitle.append("\n      }");
            sourcetitle.append("\n      else{");
            sourcetitle.append("\n          return(true);");
            sourcetitle.append("\n      }");
            sourcetitle.append("\n  }");
            sourcetitle.append("\n  else{");
            sourcetitle.append("\n          alert(err);");
            sourcetitle.append("\n          field.focus();");
            sourcetitle.append("\n          field.select();");
            sourcetitle.append("\n          return(false);");
            sourcetitle.append("\n  }");
            sourcetitle.append("\n}");
            sourcetitle.append("\n\nfunction trim(field){");
            sourcetitle.append("\n  var retVal = \"\";");
            sourcetitle.append("\n  var start = 0;");
            sourcetitle.append("\n  while ((start < field.value.length) && (field.value.charAt(start) == ' ')) {");
            sourcetitle.append("\n      ++start;");
            sourcetitle.append("\n  }");
            sourcetitle.append("\n  var end = field.value.length;");
            sourcetitle.append("\n  while ((end > 0) && (field.value.charAt(end - 1) == ' ')) {");
            sourcetitle.append("\n      --end;");
            sourcetitle.append("\n  }");
            sourcetitle.append("\n  retVal = field.value.substring(start, end);");
            sourcetitle.append("\n  if (end == 0)");
            sourcetitle.append("\n      field.value=\"\";");
            sourcetitle.append("\n  else");
            sourcetitle.append("\n      field.value=retVal;");
            sourcetitle.append("\n}");
            sourcetitle.append("\n\nfunction getSum(){");
            sourcetitle.append("\n var tot= 0;");
            sourcetitle.append("\n var ans = 0;");
            sourcetitle.append("\n var temp = 0;");
            sourcetitle.append("\n var res = 0;");

            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<fieldset>");
            ret.append("\n<form name=\"frmexit\" id=\"frmexit\" action=\""+ paddr + "\" method=\"Post\">");
            ret.append("\n<input type=\"hidden\" name=\"fecha_ini\" value=\""+request.getParameter("fecha_ini")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"fecha_fin\" value=\""+request.getParameter("fecha_fin")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"surveyid\" value=\""+request.getParameter("surveyid")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"tipo_revisar\" value=\""+request.getParameter("tipo_revisar")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"jsession\" value=\""+request.getSession().getId()+"\">");
            ret.append("\n</form>");
            ret.append("\n<form name=\"frmevaluation\" id=\"frmevaluation\" action=\""+ paddr + "\" method=\"Post\"><input type=\"hidden\" name=\"hsave\" value=\"1\"><input type=\"hidden\" name=\"responseid\" value=\"" + presponseid + "\">");
            ret.append("\n<input type=\"hidden\" name=\"fecha_ini\" value=\""+request.getParameter("fecha_ini")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"fecha_fin\" value=\""+request.getParameter("fecha_fin")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"surveyid\" value=\""+request.getParameter("surveyid")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"tipo_revisar\" value=\""+request.getParameter("tipo_revisar")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"jsession\" value=\""+request.getSession().getId()+"\">");
            ret.append("\n<input type=\"hidden\" name=\"buscar\" value=\""+request.getParameter("buscar")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"query\" value=\""+request.getParameter("query")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"review\" value=\""+request.getParameter("review")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"statistic\" value=\""+request.getParameter("statistic")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"comments\" value=\""+request.getParameter("comments")+"\">");
            ret.append("\n<table border=0 width=\"100%\" cellspacing=0 cellpadding=2 >");
            ret.append("\n<tr><td colspan=4><hr noshade size=1></td></tr>");
            ret.append("\n<tr><td colspan=4 >"+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgTipoFormulario")  +" - "+ strtype + "</td></tr>");
            String tmpName="Anónimo";
            User user =null;
            UserRepository usrRep = paramsRequest.getUser().getUserRepository();
            if(email!=null&&!email.equals("Anonimo")&&!email.equals("Anónimo")&&!email.startsWith("0_")||email.startsWith("0"))
            {
                user = usrRep.getUser(email);
                tmpName=user.getName();
                email = user.getEmail();
            }

            ret.append("\n<tr><td colspan=4 >"+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgUsuario")  +" - "+ tmpName +"<br>&nbsp;</td></tr>");
            ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=3><table width=\"100%\"><tr><td width=\"15\" bgcolor=\"#009900\"></td><td >"+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgRespuestasCorrectas")  +"</td></tr></table></td></tr>");
            ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=3><table width=\"100%\"><tr><td width=\"15\" bgcolor=\"#CC0000\"></td><td >"+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgRespuestasUsuario")  +"</td></tr></table></td></tr>");
            for(i=0; i <ilen;i++)
            {
                isec= (String)htsec.get(Integer.toString(i));
                secnum = i+1;
                objSec = new RecSubject();
                objSec.setSubjectid(Long.parseLong(isec));
                objSec.setIdtm(idtm);
                objSec.load();
                ret.append("\n<tr><td colspan=4>&nbsp;</td></tr>");
                ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=3 align=left ><b>"+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgGrupo")  +" " + secnum + "</b>: " + objSec.getTitle() + "</td></tr>");
                ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=3 align=left >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"  + objSec.getDescription() + "</td></tr>");
                Connection conn=null;
                PreparedStatement st=null;
                ResultSet rs=null;
                conn = SWBUtils.DB.getDefaultConnection();
                rs=getQuestions(conn,st,rs,presponseid,Integer.parseInt(isec),surveyid);
                while(rs.next())
                {
                    prenum ++;
                    long questionid = rs.getLong("questionid");
                    String question = SWBUtils.IO.readInputStream(rs.getAsciiStream("question"));
                    //rs.getString("question");
                    long freqid = rs.getLong("freqanswerid");
                    int controlid = rs.getInt("controlid");
                    int required = rs.getInt("required");
                    ast = "";
                    if(required == 1) ast="*";

                    /*preguntas relacionadas*/
                    arr = new ArrayList();
                    getChilds(Long.toString(questionid));

                    ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=3 align=left ><b>" + prenum + ".</b> " + question + " " + ast + "</td></tr>");
                    ret.append("\n" + getFreqAnswers(freqid,questionid,controlid,required,presponseid,typeid,pbaseid,0,moderate,paramsRequest,0) + "");
                    /*Despliega las preguntas relacionadas*/
                    for(int j=0;j<arr.size();j++)
                    {
                        childquestionid = (String)arr.get(j);
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

                        ast = "";
                        if(childrequired == 1) ast="*";

                        ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=2 align=left >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " + childquestion + " " + ast );
                        ret.append("</td></tr>");
                        ret.append("\n" + getFreqAnswers(childfreqid,Long.parseLong(childquestionid),childcontrolid,childrequired,presponseid,typeid,pbaseid,1,moderate,paramsRequest,0) + "");
                    }

                    sourcetitle.append("\n  temp = window.document.frmevaluation.score" + questionid + ".value;");
                    sourcetitle.append("\n  tot = parseInt(tot) + parseInt(temp);");
                    sourcetitle.append("\n  ans = ans + 1;");
                }
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(conn != null) conn.close();

            }
            if(typeid == 2)
            {
                if(prenum > 0)
                {
                    float tes = finalscore / prenum;
                    finalscore = java.lang.Math.round(tes);
                }
                ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=3 align=right >"+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgCalificacionTotalEvaluacion")  +":&nbsp;<input type=\"text\" name=\"sum\" size=\"3\" maxlength=\"3\" value=\""+ finalscore +"\" onblur=\"javascript: ValidNumber(this)\">&nbsp;<input type=\"button\" name=\"btnSum\" value=\""+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgCalcular")  +"\" onClick=\"getSum()\" ></td></tr>");
            }
            ret.append("\n<tr><td colspan=4 align=right ><hr noshade size=1><input type=\"button\" name=\"btnExit\" value=\""+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgSalir")  +"\" onClick=\"DoExit()\" >&nbsp;<input type=\"button\" name=\"btnSave\" value=\""+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgSiguiente")  +"\" onClick=\"DoNext()\" >&nbsp;");

            SWBResourceURL urlsur = paramsRequest.getRenderUrl();
            urlsur.setMode(SWBResourceURL.Mode_ADMIN);
            urlsur.setAction("update_en");

            ret.append("\n<input type=button value=\"Regresar\" onclick=\"javascript:window.location='"+urlsur+"'\">");
            ret.append("\n</td></tr>");


            ret.append("\n</table></form>");
            ret.append("\n</fieldset>");
            ret.append("\n</div>");
            sourcetitle.append("\n  if(ans > 0){");
            sourcetitle.append("\n      res = Math.round(tot / ans)");
            sourcetitle.append("\n  }");
            sourcetitle.append("\n  window.document.frmevaluation.sum.value = res;");
            sourcetitle.append("\n}");
            sourcetitle.append("\n</script>");
            ret.append(sourcetitle.toString());
        }
        catch(Exception e)
        {
            log.error("Error while creating poll question",e);
        }
        return ret.toString();
    }

    /**
     * Obtains user evaluation and sending by email
     * @param presponseid input string value
     * @param paddr input string with address value
     * @param request input parameters
     * @param pbaseid input Resource object
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @return a string value with html code
     */
    public String getEvaluationForEmail(String presponseid, String paddr, HttpServletRequest request, String pbaseid, SWBParamRequest paramsRequest)
    {
        StringBuffer ret = new StringBuffer();
        StringBuffer sourcetitle = new StringBuffer();
        Hashtable htsec = new Hashtable();
        RecSubject objSec = null;
        RecSurvey objSur = null;
        RecQuestion objQue = null;
        RecResponseUser objRes = null;
        String isec= null;
        String ast = null;
        String email = null;
        String strtype = null;
        String strmode = null;

        Survey oSur = new Survey();

        long surveyid = 0;
        long typeid = 0;
        int prenum = 0;
        int secnum = 0;
        int ilen = 0;
        int i = 0;
        int score = 0;
        int moderate = 0;

        /*Preguntas relacionadas*/
        int childcontrolid = 0;
        int childrequired = 0;
        int childtextid = 0;
        int childvalidate = 0;
        long childfreqid = 0;
        String childquestionid = null;
        String childquestion = null;
        
        String idtm = base.getWebSiteId();
        
        try
        {
            objRes = new RecResponseUser();
            objRes.setResponseID(Long.parseLong(presponseid));
            objRes.setIdtm(idtm);
            objRes.load();
            email = objRes.getUser();
            score = objRes.getScore();
            surveyid = objRes.getSurveyId();

            objSur = new RecSurvey();
            objSur.setSurveyID(surveyid);
            objSur.setIdtm(idtm);
            objSur.load();

            moderate = Integer.parseInt( base.getAttribute("moderate","0"));
            typeid = objSur.getTypeID();

            strtype = paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgOpinion");
            if(typeid == 2)
            {
                strtype =paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgEvaluacion")  ;
                strmode = " "+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgEstricta")  ;
                if(moderate == 1) strmode = " "+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgModerada")  ;
                strtype = strtype + strmode;
            }

            htsec = getSections(presponseid,surveyid);
            ilen = htsec.size();
            /*Preguntas relacionadas*/
            hm = new HashMap();
            CargaHM(surveyid);
            sourcetitle.append(oSur.getWB3AdminStyle());
            sourcetitle.append("\n<script language=\"JavaScript\">");
            sourcetitle.append("\n\nfunction DoNext(){");
            sourcetitle.append("\n    window.document.frmevaluation.comments.value=window.document.forma2.comments.value;");
            sourcetitle.append("\n    window.document.frmevaluation.review.value=0;");
            sourcetitle.append("\n  if (window.document.forma2.review.checked==true){");
            sourcetitle.append("\n    window.document.frmevaluation.review.value=1;");
            sourcetitle.append("\n  }");
            sourcetitle.append("\n    window.document.frmevaluation.statistic.value=0;");
            sourcetitle.append("\n  if (window.document.forma2.statistic.checked==true){");
            sourcetitle.append("\n    window.document.frmevaluation.statistic.value=1;");
            sourcetitle.append("\n  }");
            sourcetitle.append("\n   window.document.frmevaluation.submit();");
            sourcetitle.append("\n}");
            sourcetitle.append("\n\nfunction DoExit(){");
            sourcetitle.append("\n  window.document.frmexit.submit();");
            sourcetitle.append("\n}");
            sourcetitle.append("\n\nfunction ValidNumber(field){");
            sourcetitle.append("\n  var valid = \"0123456789\";");
            sourcetitle.append("\n  var ok = \"yes\";");
            sourcetitle.append("\n  var err = \""+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgAlertValorInvalido")  +"!\";");
            sourcetitle.append("\n  var temp;");
            sourcetitle.append("\n  trim(field);");
            sourcetitle.append("\n  if (field.value.length > 0){");
            sourcetitle.append("\n      for (var i=0; i<field.value.length; i++) {");
            sourcetitle.append("\n          temp = \"\" + field.value.substring(i, i+1);");
            sourcetitle.append("\n          if (valid.indexOf(temp) == \"-1\") ok = \"no\";");
            sourcetitle.append("\n      }");
            sourcetitle.append("\n      if ((field.value * 1 == 0) && (field.value != 0)) ok=\"no\";");
            sourcetitle.append("\n      if (field.value >100) ok=\"no\";");
            sourcetitle.append("\n      if (field.value.length <= 0 ) ok=\"no\";");
            sourcetitle.append("\n      if (ok == \"no\") {");
            sourcetitle.append("\n          alert(err);");
            sourcetitle.append("\n          field.focus();");
            sourcetitle.append("\n          field.select();");
            sourcetitle.append("\n          return(false);");
            sourcetitle.append("\n      }");
            sourcetitle.append("\n      else{");
            sourcetitle.append("\n          return(true);");
            sourcetitle.append("\n      }");
            sourcetitle.append("\n  }");
            sourcetitle.append("\n  else{");
            sourcetitle.append("\n          alert(err);");
            sourcetitle.append("\n          field.focus();");
            sourcetitle.append("\n          field.select();");
            sourcetitle.append("\n          return(false);");
            sourcetitle.append("\n  }");
            sourcetitle.append("\n}");
            sourcetitle.append("\n\nfunction trim(field){");
            sourcetitle.append("\n  var retVal = \"\";");
            sourcetitle.append("\n  var start = 0;");
            sourcetitle.append("\n  while ((start < field.value.length) && (field.value.charAt(start) == ' ')) {");
            sourcetitle.append("\n      ++start;");
            sourcetitle.append("\n  }");
            sourcetitle.append("\n  var end = field.value.length;");
            sourcetitle.append("\n  while ((end > 0) && (field.value.charAt(end - 1) == ' ')) {");
            sourcetitle.append("\n      --end;");
            sourcetitle.append("\n  }");
            sourcetitle.append("\n  retVal = field.value.substring(start, end);");
            sourcetitle.append("\n  if (end == 0)");
            sourcetitle.append("\n      field.value=\"\";");
            sourcetitle.append("\n  else");
            sourcetitle.append("\n      field.value=retVal;");
            sourcetitle.append("\n}");
            sourcetitle.append("\n\nfunction getSum(){");
            sourcetitle.append("\n var tot= 0;");
            sourcetitle.append("\n var ans = 0;");
            sourcetitle.append("\n var temp = 0;");
            sourcetitle.append("\n var res = 0;");

            ret.append("<br>");
            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<fieldset>");
            ret.append("\n<table border=0 width=\"550\">");
            ret.append("\n<form name=\"frmexit\" id=\"frmexit\" action=\""+ paddr + "\" method=\"Post\">");

            ret.append("\n<input type=\"hidden\" name=\"fecha_ini\" value=\""+request.getParameter("fecha_ini")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"fecha_fin\" value=\""+request.getParameter("fecha_fin")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"surveyid\" value=\""+request.getParameter("surveyid")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"tipo_revisar\" value=\""+request.getParameter("tipo_revisar")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"jsession\" value=\""+request.getSession().getId()+"\">");

            ret.append("\n</form>");
            ret.append("\n<form name=\"frmevaluation\" id=\"frmevaluation\" action=\""+ paddr + "\" method=\"Post\"><input type=\"hidden\" name=\"hsave\" value=\"1\"><input type=\"hidden\" name=\"responseid\" value=\"" + presponseid + "\">");
            ret.append("\n<input type=\"hidden\" name=\"fecha_ini\" value=\""+request.getParameter("fecha_ini")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"fecha_fin\" value=\""+request.getParameter("fecha_fin")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"surveyid\" value=\""+request.getParameter("surveyid")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"tipo_revisar\" value=\""+request.getParameter("tipo_revisar")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"jsession\" value=\""+request.getSession().getId()+"\">");
            ret.append("\n<input type=\"hidden\" name=\"buscar\" value=\""+request.getParameter("buscar")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"query\" value=\""+request.getParameter("query")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"review\" value=\""+request.getParameter("review")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"statistic\" value=\""+request.getParameter("statistic")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"comments\" value=\""+request.getParameter("comments")+"\">");
            String tmp_email = email;
            if(null!=email&&(email.startsWith("0_")||email.startsWith("0"))) email = paramsRequest.getLocaleString("usrmsg_Evaluation_Anonimo");
            ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=3 align=left >"+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgTipoFormulario")  +":&nbsp;"+ strtype + "</td></tr>");
            if(objSur.getTypeID()>1)
            {
                ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=3 align=left >"+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgUsuario")  +":&nbsp;"+ email +"</td></tr>");
                ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=3><table width=\"100%\"><tr><td width=\"15\" bgcolor=\"#009900\"></td><td >"+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgRespuestasCorrectas")  +"</td></tr></table></td></tr>");
            }
            ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=3><table width=\"100%\"><tr><td width=\"15\" bgcolor=\"#CC0000\"></td><td >"+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgRespuestasUsuario")  +"</td></tr></table></td></tr>");
            for(i=0; i <ilen;i++)
            {
                isec= (String)htsec.get(Integer.toString(i));
                secnum = i+1;
                objSec = new RecSubject();
                objSec.setSubjectid(Long.parseLong(isec));
                objSec.setIdtm(idtm);
                objSec.load();
                ret.append("\n<tr><td colspan=4>&nbsp;</td></tr>");
                ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=3 align=left ><b>"+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgGrupo")  +" " + secnum + "</b>: " + objSec.getTitle() + "</td></tr>");
                ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=3 align=left >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"  + objSec.getDescription() + "</td></tr>");

                Connection conn=null;
                PreparedStatement st=null;
                ResultSet rs=null;
                conn = SWBUtils.DB.getDefaultConnection();
                rs=getQuestions(conn,st,rs,presponseid,Integer.parseInt(isec),surveyid);
                while(rs.next())
                {
                    prenum ++;
                    long questionid = rs.getLong("questionid");
                    String question = SWBUtils.IO.readInputStream(rs.getAsciiStream("question"));
                    //rs.getString("question");
                    long freqid = rs.getLong("freqanswerid");
                    int controlid = rs.getInt("controlid");
                    int required = rs.getInt("required");
                    ast = "";
                    if(required == 1) ast="*";

                    /*preguntas relacionadas*/
                    arr = new ArrayList();
                    getChilds(Long.toString(questionid));

                    ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=3 align=left  ><b>" + prenum + ".</b> " + question + " " + ast + "</td></tr>");
                    ret.append("\n" + getFreqAnswers(freqid,questionid,controlid,required,presponseid,typeid,pbaseid,0,moderate,paramsRequest,1) + "");
                    /*Despliega las preguntas relacionadas*/
                    for(int j=0;j<arr.size();j++)
                    {
                        childquestionid = (String)arr.get(j);
                        objQue = new RecQuestion();
                        objQue.setQuestionID(Integer.parseInt(childquestionid));
                        objQue.setIdtm(idtm);
                        objQue.load();
                        childquestion = objQue.getQuestion();
                        childfreqid = objQue.getFreqAnswerID();
                        childcontrolid = Integer.parseInt(Long.toString(objQue.getControlID()));
                        childrequired = Integer.parseInt(Long.toString(objQue.getRequired()));
                        childtextid = Integer.parseInt(Long.toString(objQue.getCodeID()));
                        childvalidate = Integer.parseInt(Long.toString(objQue.getValidate()));

                        ast = "";
                        if(childrequired == 1) ast="*";

                        ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=2 align=left >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " + childquestion + " " + ast );
                        ret.append("</td></tr>");
                        ret.append("\n" + getFreqAnswers(childfreqid,Long.parseLong(childquestionid),childcontrolid,childrequired,presponseid,typeid,pbaseid,1,moderate,paramsRequest,1));
                    }

                    sourcetitle.append("\n  temp = window.document.frmevaluation.score" + questionid + ".value;");
                    sourcetitle.append("\n  tot = parseInt(tot) + parseInt(temp);");
                    sourcetitle.append("\n  ans = ans + 1;");
                }
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(conn != null) conn.close();
            }
            /*if(typeid == 2)
            {
                if(prenum > 0)
                {
                    float tes = finalscore / prenum;
                    finalscore = java.lang.Math.round(tes);
                }
                ret.append("\n<tr><td colspan=4>&nbsp;</td></tr>");
                ret.append("\n<tr><td width=\"10\">&nbsp;</td><td colspan=3 align=right >"+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgCalificacionTotalEvaluacion")  +":&nbsp;<input type=\"text\" name=\"sum\" size=\"3\" maxlength=\"3\" value=\""+ finalscore +"\" onblur=\"javascript: ValidNumber(this)\"></font>&nbsp;<input type=\"button\" name=\"btnSum\" value=\""+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgCalcular")  +"\" onClick=\"getSum()\"></td></tr>");
            }*/
            ret.append("\n</form></table>");
            ret.append("\n</fieldset>");
            ret.append("\n</div>");
            sourcetitle.append("\n  if(ans > 0){");
            sourcetitle.append("\n      res = Math.round(tot / ans)");
            sourcetitle.append("\n  }");
            sourcetitle.append("\n  window.document.frmevaluation.sum.value = res;");
            sourcetitle.append("\n}");
            sourcetitle.append("\n</script>");
            ret.append(sourcetitle.toString());
        }
        catch(Exception e)
        {
            log.error("Error while creating poll question, class - Evaluation, method - getEvaluationForEmail" ,e);
        }
        return ret.toString();
    }


    /**
     * Obtains the freqanswers for question requeired
     * @param pfreqid input long freqanswerid value
     * @param pquestionid input long value
     * @param pcontrolid input int value
     * @param prequired input int value
     * @param presponseid input string value
     * @param ptypeid input long value
     * @param pbaseid input long value
     * @param ischild input int value
     * @param pmoderate input int value
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @return a string value with html code
     */
    public String getFreqAnswers(long pfreqid, long pquestionid, int pcontrolid, int prequired, String presponseid, long ptypeid, String pbaseid, int ischild, int pmoderate,SWBParamRequest paramsRequest, int p_mail)
    {
        StringBuffer ans = new StringBuffer();
        org.w3c.dom.Document docxml;
        Element resource;
        RecFreqAnswer objFreq = null;
        HashMap hmans = new HashMap();
        HashMap hmque = new HashMap();
        Hashtable htsortq = new Hashtable();
        Hashtable htfreqa = new Hashtable();
        String strxml = null;
        String strvalue = null;
        String stranswer = null;
        String val = null;
        String att = null;
        String strres = null;
        String strcal = null;
        String strcaltot = null;
        String qid = null;
        int inum = 0;
        int score = 0;
        int scoresum = 0;
        int isco = 0;
        int iparte = 0;

        String idtm = base.getWebSiteId();
        
        try
        {
            strcal = paramsRequest.getLocaleString("usrmsg_Evaluation_getFreqAnswers_msgCalificacion")  ;
            strcaltot = "";
            if(pcontrolid == 1 || pcontrolid == 2 || pcontrolid == 7)
            {
                strvalue = getCorrectText(Long.toString(pquestionid));
                stranswer = getUserTextAnswer(Long.toString(pquestionid), presponseid);

                ans.append("\n<tr><td>&nbsp;</td><td colspan=3 align=left >&nbsp;&nbsp;&nbsp;&nbsp;<font color=\"#009900\">" + strvalue + "</td></tr>");
                ans.append("\n<tr><td>&nbsp;</td><td colspan=3 align=left >&nbsp;&nbsp;&nbsp;&nbsp;<font color=\"#CC0000\">" + stranswer + "</td></tr>");
                if(ptypeid == 2)
                {
                    score = getScoreAnswer(Long.toString(pquestionid), presponseid);
                    ans.append("\n<tr><td>&nbsp;</td><td colspan=3 align=right >");
                    if(ischild == 0)
                    {
                        if(!arr.isEmpty())
                        {
                            isco=1;
                            iparte = score;
                            strcaltot =" "+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgGrupoPreguntas")  ;
                            for(int k=0;k<arr.size();k++)
                            {
                                isco++;
                                qid = (String)arr.get(k);
                                scoresum = getScoreAnswer(qid, presponseid);
                                score = score + scoresum;
                            }
                            if(isco > 1) score = score/isco;
                        }
                        if(p_mail == 0){
                            ans.append(strcal + strcaltot + ":&nbsp;<input type=\"text\" name=\"score" + pquestionid + "\" id=\"score" + pquestionid + "\" size=\"3\" maxlength=\"3\" value=\"" + score + "\" onblur=\"javascript: ValidNumber(this)\">");
                        }
                        else{
                            ans.append(strcal + strcaltot + ":&nbsp;<b>" + score + "</b>");
                        }
                        finalscore = finalscore + score;
                    }
                    else
                    {
                        ans.append( strcal + ":&nbsp;" + score );
                    }
                    ans.append("</td></tr>");
                    if(isco > 0)
                    {
                        ans.append("\n<tr><td>&nbsp;</td><td colspan=3 align=right >" + strcal + ":&nbsp;" + iparte + "</td></tr>");
                    }
                }
            }
            else
            {
                objFreq=new RecFreqAnswer();
                objFreq.setFreqanswerid(pfreqid);
                objFreq.setIdtm(idtm);
                objFreq.load();
                strxml = objFreq.getStringxml();
                if(strxml != null)
                {

                    docxml = SWBUtils.XML.xmlToDom(strxml);
                    if (docxml!=null)
                    {
                        resource = (Element) docxml.getFirstChild();
                        org.w3c.dom.NodeList nodes = resource.getChildNodes();
                        // Llena un hashtable con los valores de las opciones
                        for (int i = 0; i < nodes.getLength(); i++)
                        {
                            org.w3c.dom.Node node = nodes.item(i);
                            val = node.getFirstChild().getNodeValue();
                            att = node.getAttributes().getNamedItem("id").getNodeValue();
                            htfreqa.put(att,val);
                        }
                        // Hace el barrido del hashtable ordenado para escribir las opciones
                        htsortq = sortHashTable(Long.toString(pquestionid));
                        for(int k=0; k < htsortq.size(); k++)
                        {
                            inum++;
                            att = (String) htsortq.get(Integer.toString(inum));
                            val = (String) htfreqa.get(att);
                            ans.append("\n<tr><td width=\"10\">&nbsp;</td><td align=left width=\"510\" ><font color=\"#666699\">"+inum+")&nbsp;" + val + "</font></td><td width=\"15\"");
                            strres = ">&nbsp;";

                            if(ptypeid ==2)
                            {
                                hmque = getCorrectOption(Long.toString(pquestionid));
                                if(!hmque.isEmpty())
                                {
                                    for(int j=0; j < hmque.size(); j++)
                                    {
                                        strvalue = (String) hmque.get(Integer.toString(j));
                                        if(strvalue.equalsIgnoreCase(att))
                                        {
                                            strres = " bgcolor=\"#009900\">&nbsp;";
                                            j= hmque.size();
                                        }
                                    }
                                }
                            }

                            ans.append(strres);
                            ans.append("</td><td  width=\"15\"");
                            strres = ">&nbsp;";
                            hmans = getUserOptionAnswer(Long.toString(pquestionid), presponseid);
                            if(!hmans.isEmpty())
                            {
                                for(int j=0; j < hmans.size(); j++)
                                {
                                    strvalue = (String) hmans.get(Integer.toString(j));
                                    if(strvalue.equalsIgnoreCase(att))
                                    {
                                        strres = " bgcolor=\"#CC0000\">&nbsp;";
                                        j= hmans.size();
                                    }
                                }
                            }
                            ans.append(strres);
                            ans.append("</td></tr>");
                        }
                    }
                    if(ptypeid == 2)
                    {
                        score = getScoreAnswer(Long.toString(pquestionid), presponseid);
                        ans.append("\n<tr><td>&nbsp;</td><td colspan=3 align=right >");
                        if(ischild == 0)
                        {
                            if(!arr.isEmpty())
                            {
                                isco=1;
                                iparte = score;
                                strcaltot =" "+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgGrupoPreguntas")  ;
                                for(int k=0;k<arr.size();k++)
                                {
                                    isco++;
                                    qid = (String)arr.get(k);
                                    scoresum = getScoreAnswer(qid, presponseid);
                                    score = score + scoresum;
                                }
                                if(isco > 1) score = score/isco;
                            }
                            if(p_mail == 0){
                                ans.append(strcal + strcaltot + ":&nbsp;<input type=\"text\" name=\"score" + pquestionid + "\" id=\"score" + pquestionid + "\" size=\"3\" maxlength=\"3\" value=\"" + score + "\" onblur=\"javascript: ValidNumber(this)\">");
                            }
                            else{
                                ans.append(strcal + strcaltot + ":&nbsp;<b>" + score + "</b>");
                            }
                            finalscore = finalscore + score;
                        }
                        else
                        {
                            ans.append(strcal + ":&nbsp;" + score );
                        }
                        ans.append("</td></tr>");
                        if(isco > 0)
                        {
                            ans.append("\n<tr><td>&nbsp;</td><td colspan=3 align=right >" + strcal + ":&nbsp;" + iparte + "</td></tr>");
                        }
                    }
                }
                else
                {
                    ans.append("\n<tr><td colspan=4 >"+paramsRequest.getLocaleString("usrmsg_Evaluation_getEvaluation_msgNoSetRespuestas")  +"</td></tr>");
                }
            }
        }
        catch(Exception e)
        {
            log.error( "Error while trying to freqansewers, class - Evaluation, method - getFreqAnswers",e);
        }

        return ans.toString();
    }


    /**
     * Obtains the user's answer
     * @param pquestionid input string value
     * @param presponseid input string value
     * @return a string value with an xml file
     */
    public String getUserTextAnswer(String pquestionid, String presponseid)
    {
        String idtm = base.getWebSiteId();
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
                    xmlans = SWBUtils.IO.readInputStream(rs.getAsciiStream("stringxml"));
                }
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(conn != null) conn.close();
            }
            catch (Exception e)
            {
                log.error("Error while trying to load records from sr_answer, class - Evaluation, method - getUserTextAnswer",e);
            }
            finally
            {
                rs = null;
                st = null;
                conn = null;
            }

            if(xmlans == null)
            {
                answer = "";
            }
            else
            {
                try
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
                catch(Exception e)
                {
                    log.error( "Error while trying to read XML for question, class - Evaluation, method getUserTextAnswer",e);
                }
            }
        }
        return answer;
    }

    /**
     * Obtains the score for required answer
     * @param pquestionid input string value
     * @param presponseid input string value
     * @return an int value
     */
    public int getScoreAnswer(String pquestionid, String presponseid)
    {
        String idtm = base.getWebSiteId();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int score = 0;
        float f_score = 0;
        if(presponseid.equalsIgnoreCase("0"))
        {
            score = 0;
        }
        else
        {
            try
            {
                conn = SWBUtils.DB.getDefaultConnection();
                st = conn.prepareStatement("select score from sr_answer where questionid=? and responseid =? and idtm=?");
                st.setLong(1,Long.parseLong(pquestionid));
                st.setLong(2,Long.parseLong(presponseid));
                st.setString(3,idtm);
                rs = st.executeQuery();
                if(rs.next())
                {
                    score = rs.getInt("score");
                }
                else
                {
                    score= 0;
                }
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(conn != null) conn.close();
            }
            catch (SQLException e)
            {
                log.error("Error while trying to load records from sr_answer, class - Evaluation, method - getScoreAnswer",e);
            }
            finally
            {
                rs = null;
                st = null;
                conn = null;
            }
        }
        return score;
    }

    /**
     * Obtains the correct text for the questionid that receives
     * @param pquestionid input string value
     * @return a string value
     */
    public String getCorrectText(String pquestionid)
    {
        String idtm= base.getWebSiteId();
        org.w3c.dom.Document docxml;
        Element resource;
        RecQuestion objQue = null;
        String answer = null;
        String xmlque = null;
        String val = null;
        try
        {
            objQue = new RecQuestion();
            objQue.setQuestionID(Integer.parseInt(pquestionid));
            objQue.setIdtm(idtm);
            objQue.load();
            xmlque = objQue.getStringXML();
            if(xmlque == null)
            {
                answer = "";
            }
            else
            {
                docxml = SWBUtils.XML.xmlToDom(xmlque);
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
            log.error("Error while trying to read XML for a question, class - Evaluation, method - getCorrectText",e);
        }
        return answer;
    }

    /**
     * Obtains a hashmap with correct options of the questionid
     * @param pquestionid input string value
     * @return a hashmap object with correct options
     */
    public HashMap getCorrectOption(String pquestionid)
    {
        String idtm = base.getWebSiteId();
        HashMap hmque = new HashMap();
        RecQuestion objQue = null;
        org.w3c.dom.Document docxml;
        Element resource;
        String xmlque = null;
        int inum= 0;
        try
        {
            objQue = new RecQuestion();
            objQue.setQuestionID(Integer.parseInt(pquestionid));
            objQue.setIdtm(idtm);
            objQue.load();
            xmlque = objQue.getStringXML();
            if (xmlque != null)
            {
                docxml = SWBUtils.XML.xmlToDom(xmlque);
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
                            hmque.put(Integer.toString(inum),qrefer);
                            inum++;
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            log.error("Error while trying to read XML for a question, class - Evaluation, method - getCorrectOption",e);
        }
        return hmque;
    }

    /**
     * Obtains answers for a question
     * @param pquestionid input string value
     * @param presponseid input string value
     * @return a hashmap object with
     */
    public HashMap getUserOptionAnswer(String pquestionid, String presponseid)
    {
        String idtm= base.getWebSiteId();
        HashMap hmans = new HashMap();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String xmlans = null;
        String val = null;
        org.w3c.dom.Document docxml;
        Element resource;
        if(!presponseid.equalsIgnoreCase("0"))
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
                    xmlans = SWBUtils.IO.readInputStream(rs.getAsciiStream("stringxml"));
                }
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(conn != null) conn.close();
            }
            catch (Exception e)
            {
                log.error("Error while trying to load sr_answer records, class - Evaluation, method - getUserOptionAnswer",e);
            }
            finally
            {
                rs = null;
                st = null;
                conn = null;
            }

            if(xmlans != null)
            {
                try
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
                            val = node.getFirstChild().getNodeValue();
                            hmans.put(Integer.toString(i),val);
                        }
                    }
                }
                catch(Exception e)
                {
                    log.error("Error while trying to read XML for a question, class - Evaluation, method - getUserOptionAnswer",e);
                }
            }
        }
        return hmans;
    }

    /**
     * Sorts the answers
     * @param pquestionid input string value
     * @return a hashmap object
     */
    public Hashtable sortHashTable(String pquestionid)
    {
        String idtm=base.getWebSiteId();
        Hashtable htsort = new Hashtable();
        Hashtable htque = new Hashtable();
        org.w3c.dom.Document docxml;
        Element resource;
        RecQuestion objQue = null;
        String xmlque = null;
        objQue = new RecQuestion();
        objQue.setQuestionID(Integer.parseInt(pquestionid));
        objQue.setIdtm(idtm);
        objQue.load();
        xmlque = objQue.getStringXML();
        // Obtiene los valores de la pregunta
        try
        {
            if (xmlque != null)
            {
                docxml = SWBUtils.XML.xmlToDom(xmlque);
                if (docxml!=null)
                {
                    resource = (Element) docxml.getFirstChild();
                    org.w3c.dom.NodeList nodes = resource.getChildNodes();
                    for (int i = 0; i < nodes.getLength(); i++)
                    {
                        org.w3c.dom.Node node = nodes.item(i);
                        String qiden = node.getAttributes().getNamedItem("id").getNodeValue();
                        String qrefer = node.getAttributes().getNamedItem("refer").getNodeValue();
                        htque.put(qiden,qrefer);
                    }
                }
            }
        }
        catch(Exception e)
        {
            log.error("Error while trying to read XML for a question, class - Evaluation, method - sortHashTable",e);
        }

        // Ordena el hashtable.
        Vector v = new Vector(htque.keySet());
        Collections.sort(v);

        // Muestra el hashtable ordenado.
        for (Enumeration e = v.elements(); e.hasMoreElements();)
        {
            String key = (String)e.nextElement();
            String val = (String)htque.get(key);
            htsort.put(key,val);
        }
        return htsort;
    }

    /**
     * Obtains the answerid value
     * @param pquestionid input string value
     * @param presponseid input string value
     * @return an int value
     */
    public int getAnswerId(String pquestionid, String presponseid)
    {
        String idtm = base.getWebSiteId();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int answerid = 0;
        try
        {
            conn = SWBUtils.DB.getDefaultConnection();
            st = conn.prepareStatement("select answerid from sr_answer where questionid=? and responseid =? and idtm=?");
            st.setLong(1,Long.parseLong(pquestionid));
            st.setLong(2,Long.parseLong(presponseid));
            st.setString(3,idtm);
            rs=st.executeQuery();
            if(rs.next())
            {
                answerid = rs.getInt("answerid");
            }
            if(rs != null) rs.close();
            if(st != null) st.close();
            if(conn != null) conn.close();
        }
        catch (SQLException e)
        {
            log.error("Error while trying to load records from sr_answer, class - Evaluation, method - getAnswerId",e);
        }
        finally
        {
            rs = null;
            st = null;
            conn = null;
        }
        return answerid;
    }

    /**
     * Obtains the sections of the survey
     * @param presponseid input string value
     * @param psurveyid input long value
     * @return a hashtable object with sections of the survey
     */
    public Hashtable getSections(String presponseid,long psurveyid)
    {
        String idtm = base.getWebSiteId();
        Hashtable ht = new Hashtable();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        StringBuffer strsql = new StringBuffer();
        try
        {
            strsql.append("select sr_orderquestion.subjectid from sr_orderquestion, sr_answer ");
            strsql.append(" where sr_orderquestion.questionid = sr_answer.questionid ");
            strsql.append(" and sr_answer.responseid = ? and sr_orderquestion.surveyid = ? and sr_answer.idtm = sr_orderquestion.idtm and sr_orderquestion.idtm=? ");
            strsql.append(" group by sr_orderquestion.subjectid order by sr_orderquestion.subjectid");
            con = SWBUtils.DB.getDefaultConnection();
            st = con.prepareStatement(strsql.toString());
            st.setLong(1,Long.parseLong(presponseid));
            st.setLong(2,psurveyid);
            st.setString(3,idtm);
            rs = st.executeQuery();
            int isec = 0;
            while(rs.next())
            {
                int sectionId =rs.getInt("subjectid");
                ht.put( Integer.toString(isec),Integer.toString(sectionId));
                isec++;
            }
            if(rs != null) rs.close();
            if(st != null) st.close();
            if(con != null) con.close();
        }
        catch(Exception e)
        {
            log.error("Error while trying load information from database, class - Evaluation, method - getSections",e);
        }
        finally
        {
            rs = null;
            st = null;
            con = null;
        }
        return ht;
    }

    /**
     * This method fills a hasmap
     * @param surveyid input long value
     */
    public void CargaHM(long surveyid)
    {
        String idtm = base.getWebSiteId();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try
        {
            conn = SWBUtils.DB.getDefaultConnection();
            pst = conn.prepareStatement("" +
            "select parentquestion, sonquestion " +
            "from sr_relatedquestion " +
            "where surveyid = ? and idtm=? ");
            pst.setLong(1,surveyid);
            pst.setString(2, idtm);
            rs = pst.executeQuery();
            while(rs.next())
            {
                hm.put(Long.toString(rs.getLong("parentquestion")),Long.toString(rs.getLong("sonquestion")));
            }
            if(rs != null) rs.close();
            if(pst != null) pst.close();
            if(conn != null) conn.close();
        }
        catch(Exception e)
        {
            log.error("Error while trying to load records from sr_relatedquestion, class - Evaluation, method - CargaHM",e);
        }
        finally
        {
            rs = null;
            pst = null;
            conn = null;
        }
    }

    /**
     * This method returns the child question of another question
     * @param parent input string value
     */
    public void getChilds(String parent)
    {
        if(hm.containsKey(parent))
        {
            arr.add(hm.get(parent));
            getChilds((String)hm.get(parent));
        }
    }

    /**
     * This methos returns a resulset object with questions
     * @param conn input connection value
     * @param st input statemente value
     * @param rs input resultset value
     * @param presponseid input string value
     * @param psectionid input int value
     * @param psurveyid input long value
     * @return a resultset object
     */
    public ResultSet getQuestions(Connection conn, PreparedStatement st, ResultSet rs, String presponseid, int psectionid,long psurveyid)
    {
        String idtm = base.getWebSiteId();
        StringBuffer strsql = new StringBuffer();
        String strson = null;
        strson = getChildQuestion(psurveyid);
        try
        {
            strsql.append("select sr_answer.questionid, sr_question.question, sr_question.freqanswerid, sr_question.controlid, sr_question.required from sr_answer, sr_question, sr_orderquestion ");
            strsql.append(" where sr_answer.questionid =  sr_question.questionid and sr_orderquestion.questionid = sr_answer.questionid  ");
            strsql.append(" and sr_answer.idtm=sr_question.idtm and sr_orderquestion.idtm=sr_question.idtm  ");
            //Evalua si tiene preguntas hijas
            if(strson != null)
            {
                strsql.append(strson);
            }
            strsql.append(" and sr_question.idtm=? and sr_answer.responseid = ? and sr_orderquestion.subjectid = ? and sr_orderquestion.surveyid=? order by sr_answer.questionid");
            st = conn.prepareStatement(strsql.toString());
            st.setString(1,idtm);
            st.setLong(2,Long.parseLong(presponseid));
            st.setInt(3,psectionid);
            st.setLong(4,psurveyid);
            rs=st.executeQuery();
        }
        catch(Exception e)
        {
            log.error("Error while trying to load records from sr_answer, sr_question, sr_orderquestion, class - Evaluation, method - getQuestions",e);
        }
        return rs;
    }

    /**
     * This method returns the child questions of a survey
     * @param psurveyid input long value
     * @return a string value
     */
    public String getChildQuestion(long psurveyid)
    {
        String idtm = base.getWebSiteId();
        StringBuffer strinson = new StringBuffer();
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection con = null;
        String strtext = null;
        String strson = null;
        int icon = 0;
        int ison = 0;
        int ssonquestion = 0;
        try
        {
            strinson.append(" and sr_orderquestion.questionid not in(");
            con = SWBUtils.DB.getDefaultConnection();
            st = con.prepareStatement("select sonquestion from sr_relatedquestion where surveyid = ? and idtm=? ");
            st.setLong(1,psurveyid);
            st.setString(2,idtm);
            rs = st.executeQuery();
            while(rs.next())
            {
                icon = 1;
                ssonquestion = rs.getInt("sonquestion");
                strinson.append(Integer.toString(ssonquestion));
                strinson.append(",");
            }
            if(rs != null) rs.close();
            if(st != null) st.close();
            if(con != null) con.close();
            if (icon == 1)
            {
                strson = strinson.toString();
                ison = strson.length();
                strtext = strson.substring(0,ison-1);
                strtext = strtext + ")";
            }
        }
        catch(Exception e)
        {
            log.error("Error while trying to load records from sr_relatedquestion, class - Evaluation, method - getChildQuestion",e);
        }
        finally
        {
            rs = null;
            st = null;
            con = null;
        }
        return strtext;
    }
}
