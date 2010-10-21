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
import com.infotec.wb.resources.survey.db.RecQuestion;
import com.infotec.wb.resources.survey.db.RecResponseUser;
import com.infotec.wb.resources.survey.db.RecSurvey;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Enumeration;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.Element;

/**
 * Created by Juan Antonio Fernández Arias
 * 
 */
public class Reports
{
    private static Logger log = SWBUtils.getLogger(Reports.class);
    Resource base = null;
    
    public Reports()
    {
        
    }
    
    public void setResourceBase(Resource base)
    {
        this.base = base;
    }
    
    public String doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        String idtm=base.getWebSiteId();
        StringBuffer ret = null;
        String accion = null;
        Locale local = new Locale(paramsRequest.getUser().getLanguage());
        ret = new StringBuffer();
        accion = paramsRequest.getAction();
        if(accion.equals("re_general"))
        {
            
            SWBResourceURL url = paramsRequest.getRenderUrl();
            url.setMode(url.Mode_ADMIN);
            url.setAction("re_general");
            
            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<fieldset>");
            ret.append("\n<form name=\"forma\" action=\""+url+"\" method=\"POST\">");
            ret.append("\n<table width=100% cellpadding=2 cellspacing=0>");
            ret.append("\n<tr><td colspan=2 >");
            ret.append("\n"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgSeleccionaTipoFormulario")+": ");
            ret.append("\n</td></tr>");
            ret.append("\n<tr><td  align=right width=200>");
            ret.append("\n<select name=\"tipo\">");
            String temp1 = " selected ";
            String temp2 = " ";
            if(request.getParameter("tipo")!=null)
            {
                if(request.getParameter("tipo").equals("2"))
                {
                    temp1 = " ";
                    temp2 = " selected ";
                }
            }
            ret.append("\n<option value=\"1\" "+temp1+">"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgOpinion")+"</option>");
            ret.append("\n<option value=\"2\" "+temp2+">"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgEvaluacion")+"</option>");
            ret.append("\n</select></td><td><input type=\"submit\" value=\""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_btnEnviar")+"\" name=\"btn_submit\" ></td></tr></table></form>");
            
            if(request.getParameter("tipo")!=null)
            {
                Connection conn =null;
                PreparedStatement pst = null;
                ResultSet rs = null;
                try
                {
                    conn = SWBUtils.DB.getDefaultConnection();
                    int num=0;
                    String strSQL = new String("select count(*) as num " +
                            " from sr_survey " +
                            " where " +
                            " sr_survey.typeid = ? and idtm=?");
                    pst = conn.prepareStatement(strSQL);
                    pst.setLong(1,Long.parseLong(request.getParameter("tipo") ));
                    pst.setString(2, idtm);
                    rs = pst.executeQuery();
                    if(rs.next()) num = rs.getInt("num");
                    if(rs!=null)rs.close();
                    if(pst!=null)pst.close();
                    if(num==0)
                    {
                        ret.append("\n<table width=100% cellpadding=2 cellspacing=0>");
                        ret.append("\n<tr><td colspan=2 >");
                        ret.append("\n"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgNoFormulariosResueltosTipoSeleccionado"));
                        ret.append("\n</td></tr></table>");
                        
                    }
                    else
                    {
                        
                        strSQL = "select * from sr_survey  where  typeid = ? and idtm=?";
                        pst = conn.prepareStatement(strSQL);
                        pst.setLong(1,Long.parseLong(request.getParameter("tipo") ));
                        pst.setString(2,idtm);
                        rs = pst.executeQuery();
                        
                        SWBResourceURL urlf = paramsRequest.getRenderUrl();
                        urlf.setMode(urlf.Mode_ADMIN);
                        urlf.setAction("re_formulario");
                        
                        ret.append("\n<form name=\"forma\" action=\""+urlf+"\" method=\"POST\">");
                        ret.append("\n<table width=100% cellpadding=2 cellspacing=0>");
                        ret.append("\n<tr><td colspan=2 >");
                        ret.append("\n"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgSeEncontraron")+" "+num+" "+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgFormulariosEsteTipo"));
                        ret.append("\n</td></tr>");
                        ret.append("\n<tr><td colspan=2 >");
                        ret.append("\n"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgSeleccionaFormularioLista"));
                        ret.append("\n</td></tr>");
                        ret.append("\n<tr><td  align=right width=200>");
                        ret.append("\n<select name=\"surveyid\">");
                        RecSurvey oRStmp=null;
                        while(rs.next())
                        {
                            oRStmp = new RecSurvey();
                            oRStmp.setIdtm(idtm);
                            oRStmp.setSurveyID(rs.getLong("surveyid"));
                            oRStmp.load();
                            
                            Resource rbase = paramsRequest.getWebPage().getWebSite().getResource(oRStmp.getResID());
                            ret.append("\n<option value=\""+rs.getLong("surveyid")+"\">"+ rbase.getTitle() +"</option>");
                            oRStmp = null;
                        }
                        ret.append("\n</select>");
                        ret.append("\n</td><td><input type=\"submit\" name=\"btn_submit\" value=\""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_btnEnviar")+"\" >");
                        ret.append("\n</td></tr></table></form>");
                        ret.append("\n</fieldset>");
                        ret.append("\n</div>");
                        if(rs!=null)rs.close();
                        if(pst!=null)pst.close();
                        if(conn!=null)conn.close();
                    }
                }
                catch(Exception e)
                {
                    log.error("Error while trying to load records from sr_survey, class - Reports, method - doAdmin",e);
                }
                finally
                {
                    rs=null;
                    pst=null;
                    conn=null;
                }
                
            }
        }
        
        if(accion.equals("re_formulario"))
        {
            long idRS = 0;
            if(request.getParameter("surveyid")!=null) idRS = Long.parseLong(request.getParameter("surveyid"));
            RecSurvey oRSL = new RecSurvey();
            oRSL.setIdtm(idtm);
            oRSL.setSurveyID(idRS);
            oRSL.load();
            //ret.append("\n<script language=\"JavaScript\" src=\""+SWBPlatform.getContextPath()+"swbadmin/js/calendar.js\"></script>");
            ret.append("\n<script type=\"text/javascript\"> ");
            ret.append("\n                                                         ");
            ret.append("\n  dojo.require(\"dijit.form.DateTextBox\"); ");
            ret.append("\n  dojo.require(\"dojo.parser\");  ");
            ret.append("\n                                                         ");
            ret.append("\nfunction habilita(valor){                 ");
            ret.append("\n                                                         ");
            ret.append("\n    if(valor==\"pregunta\"){          ");
            ret.append("\n      forma.tipo_pregunta.disabled = false;");
            ret.append("\n    }                                                   ");
            ret.append("\n    else{                                             ");
            ret.append("\n      forma.tipo_pregunta.disabled = true;");
            ret.append("\n    }                                      ");
            ret.append("\n                                            ");
            ret.append("\n}                                          ");
            ret.append("\n                                            ");
            ret.append("\nfunction validar(forma){    ");
            ret.append("\n    if(forma.tipo_reporte[0].checked==false && forma.tipo_reporte[1].checked==false && forma.tipo_reporte[2].checked==false){  ");
            ret.append("\n        alert(\""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgAlertSeleccionaTipoConsulta")+"\");      ");
            ret.append("\n        return(false);     ");
            ret.append("\n    }                    ");
            ret.append("\n    if(forma.tipo_revisar[0].checked==false && forma.tipo_revisar[1].checked==false && forma.tipo_revisar[2].checked==false){     ");
            ret.append("\n        alert(\""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgAlertSeleccionaFormulariosQuieresVer")+"\");     ");
            ret.append("\n        return(false);      ");
            ret.append("\n    }                              ");
            ret.append("\n    if(forma.tipo_reporte[1].checked==true){  ");
            
            SWBResourceURL urlg = paramsRequest.getRenderUrl();
            urlg.setMode(urlg.Mode_ADMIN);
            urlg.setAction("reGeneral");
            urlg.setCallMethod(urlg.Call_DIRECT);
            
            ret.append("\n        forma.action='"+urlg+"';      ");
            ret.append("\n        forma.content_type.value='excel';                            ");
            ret.append("\n        window.open('','reporteGeneral','scrollbars,resizable,alwaysRaised,width=400,height=500,menubar');                               ");
            ret.append("\n        forma.target=\"reporteGeneral\";      ");
            ret.append("\n    }                    ");
            ret.append("\n    else{                    ");
            ret.append("\n       if(forma.tipo_reporte[2].checked==true){  ");
            
            SWBResourceURL urlrr = paramsRequest.getRenderUrl();
            urlrr.setMode(urlrr.Mode_ADMIN);
            urlrr.setAction("re_revisar");
            
            ret.append("\n           forma.action='"+urlrr+"';      ");
            ret.append("\n        forma.content_type.value='';                            ");
            ret.append("\n           //window.open('','reporteGeneral','scrollbars,resizable,alwaysRaised,width=400,height=500');                               ");
            ret.append("\n           forma.target=\"_self\";      ");
            ret.append("\n       }                        ");
            ret.append("\n    else{                    ");
            
            SWBResourceURL urlc = paramsRequest.getRenderUrl();
            urlc.setMode(urlc.Mode_ADMIN);
            urlc.setAction("re_consulta");
            
            ret.append("\n       forma.action='"+urlc+"';                 ");
            ret.append("\n       forma.content_type.value='';                            ");
            ret.append("\n       forma.target=\"_self\";                 ");
            ret.append("\n    }                    ");
            ret.append("\n   }                    ");
            ret.append("\n  return(true);             ");
            ret.append("\n}                                  ");
            ret.append("\n                                    ");
            ret.append("\n</script>                    ");

            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<fieldset>");
            ret.append("\n<form name=\"forma\" action=\""+urlc+"\" method=\"POST\" onsubmit=\"return validar(forma);\" >");
            ret.append("\n<input type=\"hidden\" name=\"surveyid\" value=\""+idRS+"\">");
            ret.append("\n<input type=\"hidden\" name=\"jsession\" value=\""+request.getSession().getId()+"\">");
            ret.append("<table border=0 cellpadding=2 cellspacing=0 width=100%>");
            ret.append("<tr><td colspan=2 >");
            Resource rBase = paramsRequest.getWebPage().getWebSite().getResource(oRSL.getResID());
            ret.append(paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgFormulario")+": "+rBase.getTitle());
            ret.append("</td></tr>");
            ret.append("<tr><td colspan=2 >");
            ret.append("\n"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgTipoConsulta")+"");
            ret.append("</td></tr>");
            ret.append("<tr><td width=\"200\" align=right >");
            ret.append("\n"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgRespuestasPregunta")+" </td><td><input type=\"radio\" name=\"tipo_reporte\" value=\"pregunta\" onclick=\"habilita(this.value)\">");
            ret.append("<select name=\"tipo_pregunta\" disabled><option value=\"No abiertas\">"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgNoAbiertas")+"</option><option value=\"Abiertas\">"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgAbiertas")+"</option></select>");
            ret.append("</td></tr>");
            ret.append("<tr><td width=\"200\" align=right >");
            ret.append(""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgGenerarExcelGeneral")+"  </td><td><input type=\"radio\" name=\"tipo_reporte\" value=\"general\" onclick=\"habilita(this.value)\">");
            ret.append("</td></tr>");
            ret.append("<tr><td width=\"200\" align=right >");
            ret.append(""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgRevisarFormulario")+"  </td><td><input type=\"radio\" name=\"tipo_reporte\" value=\"revisar\" onclick=\"habilita(this.value)\">");
            ret.append("</td></tr>");
            ret.append("<tr><td colspan=\"2\" >");
            ret.append("\n"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgPeriodoConsultar")+"");
            ret.append("</td></tr>");
            ret.append("<tr><td width=\"200\" align=right >");
            ret.append("\n"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgFechaInicial")+": </td>");
            ret.append("\n<td><input dojoType=\"dijit.form.DateTextBox\" type=text name=\"fecha_ini\" >"); //"<td><input dojoType=\"dijit.form.DateTextBox\" type=text name=\"fecha_ini\" size=15 readonly><a href=\"javascript:show_calendar('forma.fecha_ini');\" onmouseover=\"window.status='Selecciona fecha';return true;\" onmouseout=\"window.status='';return true;\"><img src=\""+SWBPlatform.getContextPath()+"swbadmin/images/show-calendar.gif\" border=\"0\"></a>");
            ret.append("</td></tr>");
            ret.append("<tr><td width=\"200\" align=right >");
            //ret.append("\n"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgFechaFinal")+": </td><td><input type=text name=\"fecha_fin\" size=15 readonly><a href=\"javascript:show_calendar('forma.fecha_fin');\" onmouseover=\"window.status='Selecciona fecha';return true;\" onmouseout=\"window.status='';return true;\"><img src=\""+SWBPlatform.getContextPath()+"swbadmin/images/show-calendar.gif\" border=\"0\"></a>");
            ret.append("\n"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgFechaFinal")+": </td>");
            ret.append("\n<td><input dojoType=\"dijit.form.DateTextBox\" type=text name=\"fecha_fin\">");
            ret.append("</td></tr>");
            ret.append("<tr><td colspan=\"2\" >");
            ret.append("\n"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgVerFormularios"));
            ret.append("</td></tr>");
            ret.append("<tr><td width=\"200\" align=right >");
            ret.append(""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgRevisados")+"  </td><td><input type=\"radio\" name=\"tipo_revisar\" value=\"revisados\">");
            ret.append("</td></tr>");
            ret.append("<tr><td width=\"200\" align=right >");
            ret.append(""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgNoRevisados")+"  </td><td><input type=\"radio\" name=\"tipo_revisar\" value=\"no revisados\">");
            ret.append("</td></tr>");
            ret.append("<tr><td width=\"200\" align=right >");
            ret.append(""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgTodos")+"  </td><td><input type=\"radio\" name=\"tipo_revisar\" value=\"todos\">");
            ret.append("</td></tr>");
            ret.append("<tr><td colspan=\"2\" align=right><hr noshade size=1>");
            
            SWBResourceURL urlsur = paramsRequest.getRenderUrl();
            urlsur.setMode(urlsur.Mode_ADMIN);
            urlsur.setAction("update_en");
            ret.append("<input type=\"submit\" value=\""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgRealizarConsulta")+"\" name=\"btn_submit\" >&nbsp;<input type=button value=\""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgAtras")+"\" onclick=\"javascript:window.location='"+urlsur+"'\">");
            ret.append("</td></tr></table>");
            ret.append("\n<input type=\"hidden\" name=\"content_type\" value=\"\">");
            ret.append("\n</form>");
            ret.append("\n</fieldset>");
            ret.append("\n</div>");
            
        }
        
        if(accion.equals("re_consulta"))
        {
            
            ret.append("\n<script type=\"text/javascript\"> ");
            ret.append("\n                                                         ");
            
            ret.append("\nfunction validar(forma){    ");
            ret.append("\n     var seleccionados = 0;   ");
            ret.append("\nvar strquestions ='';  ");
            ret.append("\nfor (i=0; i<forma.questionid.length; i++){    ");
            ret.append("\n   if(forma.questionid[i].selected==true){    ");
            ret.append("\n      seleccionados++;     ");
            ret.append("\n      strquestions += forma.questionid[i].value +'|';    ");
            ret.append("\n   }         ");
            ret.append("\n   }          ");
            ret.append("\n   forma.questions.value=strquestions;       ");
            ret.append("\n   if(seleccionados==0){         ");
            ret.append("\n      alert('"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgAlertSeleccionarUnaPreguntaLista")+"');    ");
            ret.append("\n      return(false);       ");
            ret.append("\n   }        ");
            ret.append("\n      window.open('','reportes','scrollbars,resizable,allwaysRaised,width=520,height=500,menubar');                               ");
            ret.append("\n      forma.target=\"reportes\";      ");
            ret.append("\n  return(true);             ");
            ret.append("\n}                                  ");
            ret.append("\n                                    ");
            ret.append("\n</script>                    ");
            
            SWBResourceURL urlrep=paramsRequest.getRenderUrl();
            urlrep.setMode(urlrep.Mode_ADMIN);
            urlrep.setCallMethod(urlrep.Call_DIRECT);
            urlrep.setAction("re_reporte");
            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<fieldset>");
            ret.append("\n<form name=\"forma\" action=\""+urlrep+"\" method=\"POST\" onsubmit=\"return validar(forma);\" >");
            
            ret.append("\n<input type=\"hidden\" name=\"fecha_ini\" value=\""+request.getParameter("fecha_ini")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"fecha_fin\" value=\""+request.getParameter("fecha_fin")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"tipo_reporte\" value=\""+request.getParameter("tipo_reporte")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"tipo_pregunta\" value=\""+request.getParameter("tipo_pregunta")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"surveyid\" value=\""+request.getParameter("surveyid")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"tipo_revisar\" value=\""+request.getParameter("tipo_revisar")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"jsession\" value=\""+request.getSession().getId()+"\">");
            ret.append("\n<input type=\"hidden\" name=\"questions\" value=\"\">");
            
            ret.append("\n<table border=\"0\" cellpadding=\"2\" cellspacing=\"0\" width=100%>");
            
            RecSurvey oRS = new RecSurvey();
            oRS.setIdtm(idtm);
            oRS.setSurveyID(Long.parseLong(request.getParameter("surveyid")));
            oRS.load();
            
            Resource rBase = paramsRequest.getWebPage().getWebSite().getResource(oRS.getResID());
            
            ret.append("\n<tr><td colspan=2 >"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgDatosRecibidosConsultas")+"</td></tr>");
            ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgFormulario")+":</td><td >"+rBase.getTitle()+"</td></tr>");
            ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgTipoConsulta")+":</td><td >"+request.getParameter("tipo_reporte")+"</td></tr>");
            String strControl="";
            if(request.getParameter("tipo_pregunta")!=null)
            {
                ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgDeLasPreguntas")+":</td><td >"+request.getParameter("tipo_pregunta")+"</td></tr>");
                strControl = " sr_question.controlid in (1,2,7) and ";
                if(request.getParameter("tipo_pregunta").equals("No abiertas"))
                {
                    strControl = " sr_question.controlid in (3,4,5,6) and ";
                    
                }
            }
            String strFecha_fin="";
            String strFecha_ini="";
            java.sql.Timestamp tempIni = new java.sql.Timestamp(System.currentTimeMillis());
            java.sql.Timestamp tempFin = tempIni;
            
            if(request.getParameter("fecha_ini").length()>0)
            {
                
                String fecha_ini = request.getParameter("fecha_ini");
                int year_i;
                int month_i;
                int day_i;

//                month_i=Integer.parseInt(fecha_ini.substring(0,fecha_ini.indexOf("/")));
//                day_i=Integer.parseInt(fecha_ini.substring(fecha_ini.indexOf("/")+1,fecha_ini.lastIndexOf("/")));
//                year_i= Integer.parseInt(fecha_ini.substring(fecha_ini.lastIndexOf("/")+1,fecha_ini.length()));

                year_i= Integer.parseInt(fecha_ini.substring(0,fecha_ini.indexOf("-")));
                month_i=Integer.parseInt(fecha_ini.substring(fecha_ini.indexOf("-")+1,fecha_ini.lastIndexOf("-")));
                day_i=Integer.parseInt(fecha_ini.substring(fecha_ini.lastIndexOf("-")+1,fecha_ini.length()));
                
                Calendar calendario = Calendar.getInstance(local);
                calendario.set(year_i,month_i-1,day_i,0,0,0);
                java.sql.Date fecha_ini_D = new java.sql.Date(calendario.getTimeInMillis());
                tempIni  =  new java.sql.Timestamp(fecha_ini_D.getTime()) ;
                strFecha_ini = " and  created >= ?  ";
                
               ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgDelDia")+":</td><td >"+tempIni.toString().substring(0,19));
            }
            else
            {
                ret.append("\n<tr><td colspan=2 >"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgTodosFormularios")+" ");
            }
            
            if(request.getParameter("fecha_fin").length()>0)
            {
                String fecha_fin = request.getParameter("fecha_fin");
                int year_f;
                int month_f;
                int day_f;

//                month_f=Integer.parseInt(fecha_fin.substring(0,fecha_fin.indexOf("/")));
//                day_f=Integer.parseInt(fecha_fin.substring(fecha_fin.indexOf("/")+1,fecha_fin.lastIndexOf("/")));
//                year_f= Integer.parseInt(fecha_fin.substring(fecha_fin.lastIndexOf("/")+1,fecha_fin.length()));

                year_f= Integer.parseInt(fecha_fin.substring(0,fecha_fin.indexOf("-")));
                month_f=Integer.parseInt(fecha_fin.substring(fecha_fin.indexOf("-")+1,fecha_fin.lastIndexOf("-")));
                day_f=Integer.parseInt(fecha_fin.substring(fecha_fin.lastIndexOf("-")+1,fecha_fin.length()));

                Calendar calendariof = Calendar.getInstance();
                calendariof.set(year_f,month_f-1,day_f,23,59,59);
                java.sql.Date fecha_fin_D = new java.sql.Date(calendariof.getTimeInMillis());
                tempFin  =   new java.sql.Timestamp(fecha_fin_D.getTime()) ;
                
                strFecha_fin = " and created <= ? ";
                
                ret.append("\n  "+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgAl")+" "+tempFin.toString().substring(0,19)+"</td></tr>");
            }
            else
            {
                ret.append("\n "+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgFechaActual")+"</td></tr>");
            }
            ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgGenerarConsultaFORMULARIOS")+":</td><td >"+request.getParameter("tipo_revisar")+"</td></tr>");
            ret.append("\n");
            
            // Se cargan las preguntas del formulario
            
            int num=0;
            Connection conexion =null;
            PreparedStatement pst =null;
            ResultSet rs =null;
            try
            {
                conexion = SWBUtils.DB.getDefaultConnection();
                String tempReview = "";
                if(request.getParameter("tipo_revisar").equals("revisados") ) tempReview = " and review = 1 ";
                else
                {
                    if(request.getParameter("tipo_revisar").equals("no revisados")) tempReview = " and review = 0 ";
                }
                
                pst = conexion.prepareStatement("" +
                        " select count(*) as num from " +
                        " sr_responseuser where " +
                        " surveyid = ? and idtm=? and " +
                        " statistic = 1 " + tempReview + strFecha_ini + strFecha_fin);
                
                pst.setLong(1,Long.parseLong(request.getParameter("surveyid")));
                pst.setString(2,idtm);
                if(request.getParameter("fecha_ini").length()>0 && request.getParameter("fecha_fin").length()==0)
                {
                    pst.setTimestamp(3,tempIni);
                }
                else
                {
                    if(request.getParameter("fecha_ini").length()>0 && request.getParameter("fecha_fin").length()>0)
                    {
                        pst.setTimestamp(3,tempIni);
                        pst.setTimestamp(4,tempFin);
                    }
                    else
                    {
                        if(request.getParameter("fecha_ini").length()==0 && request.getParameter("fecha_fin").length()>0)
                        {
                            pst.setTimestamp(3,tempFin);
                        }
                    }
                }
                rs = pst.executeQuery();
                
                if(rs.next()) num = rs.getInt("num");
                if(rs!=null)rs.close();
                if(pst!=null)pst.close();
                if(conexion!=null)conexion.close();
            }
            catch(Exception e)
            {
                log.error("Error while trying to load records from sr_responseuser, class - Reports, method - doAdmin",e);
            }
            finally
            {
                rs=null;
                pst=null;
                conexion=null;
            }
            
            if(num==0)
            {
                ret.append("\n<tr><td  colspan=2 >"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgNoFormulariosContestados")+"</td></tr>");
            }
            else
            {
                if(num==1)
                {
                    ret.append("\n<tr><td  colspan=2 >"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgEncontrUnFormularioContestado")+"</td></tr>");
                }
                else
                {
                    ret.append("\n<tr><td  colspan=2 >"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgSeEncontraron")+" "+num+" "+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgFormulariosContestados")+"</td></tr>");
                }
            }
            
            if(num>0)
            {
                Connection conexion1 =null;
                PreparedStatement pst1 =null;
                ResultSet rs1 =null;
                try
                {
                    conexion1 = SWBUtils.DB.getDefaultConnection();
                    String strSQL = new String("select sr_orderquestion.ordernum, sr_question.questionid, sr_question.question " +
                            " from sr_question, sr_orderquestion where " +
                            " sr_orderquestion.surveyid = ? and sr_question.idtm=? and " + strControl +
                            " sr_orderquestion.questionid = sr_question.questionid " +
                            " order by sr_orderquestion.ordernum asc");
                    pst1 = conexion1.prepareStatement(strSQL);
                    
                    pst1.setLong(1,Long.parseLong(request.getParameter("surveyid")));
                    pst1.setString(2,idtm);
                    rs1 = pst1.executeQuery();
                    
                    if(request.getParameter("tipo_reporte").equals("pregunta"))
                    {
                        if(request.getParameter("tipo_pregunta")!=null)
                        {
                            ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgPreguntas")+":</td><td >"+request.getParameter("tipo_pregunta")+"</td></tr><tr><td width=200 >&nbsp;</td><td ><select name=\"questionid\" size=10 style=\"font-size:10; font-face:verdana\" multiple>");
                            while(rs1.next())
                            {
                                String sqtion = SWBUtils.IO.readInputStream(rs1.getAsciiStream("question"));
                                ret.append("\n<option value=\""+rs1.getLong("questionid")+"\">"+sqtion+"</option>");
                            }
                            ret.append("\n</select>");
                            ret.append("\n</td></tr>");
                        }
                    }
                    if(rs1!=null)rs1.close();
                    if(pst1!=null)pst1.close();
                    if(conexion1!=null)conexion1.close();
                }
                
                catch(Exception e)
                {
                    log.error("Error while trying to load questions, class - GeneralReport, method - doAdmin",e);
                }
                finally
                {
                    rs1=null;
                    pst1=null;
                    conexion1=null;
                }
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgExcel")+":</td><td ><input type=\"checkbox\" value=\"excel\" name=\"content_type\"></td></tr>");
                
                
                SWBResourceURL urlrf=paramsRequest.getRenderUrl();
                urlrf.setMode(urlrf.Mode_ADMIN);
                urlrf.setAction("re_formulario");
                urlrf.setParameter("surveyid",request.getParameter("surveyid"));
                ret.append("\n<tr><td  colspan=2 align=right><hr noshade size=1><input type=\"submit\" value=\""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_btnVerResultado")+"\" name=\"btn_submit\" >&nbsp;<input type=\"button\" value=\""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgAtras")+"\" name=\"btn_back\" onClick=\"javascript:window.location='"+urlrf+"'\"></td></tr>");
            }
            ret.append("\n</table></form>");
            ret.append("\n</fieldset>");
            ret.append("\n</div>");
        }
        
        if(accion.equals("re_reporte"))
        {
            
            if(request.getParameter("content_type")==null) response.setContentType("text/html");
            
            String strFecha_fin="";
            String strFecha_ini="";
            java.sql.Timestamp tempIni = new java.sql.Timestamp(System.currentTimeMillis());
            java.sql.Timestamp tempFin = tempIni;
            
            if(request.getParameter("fecha_ini").trim().length()>0)
            {
                String fecha_ini = request.getParameter("fecha_ini");
                int year_i;
                int month_i;
                int day_i;

//                month_i=Integer.parseInt(fecha_ini.substring(0,fecha_ini.indexOf("/")));
//                day_i=Integer.parseInt(fecha_ini.substring(fecha_ini.indexOf("/")+1,fecha_ini.lastIndexOf("/")));
//                year_i= Integer.parseInt(fecha_ini.substring(fecha_ini.lastIndexOf("/")+1,fecha_ini.length()));

                year_i= Integer.parseInt(fecha_ini.substring(0,fecha_ini.indexOf("-")));
                month_i=Integer.parseInt(fecha_ini.substring(fecha_ini.indexOf("-")+1,fecha_ini.lastIndexOf("-")));
                day_i=Integer.parseInt(fecha_ini.substring(fecha_ini.lastIndexOf("-")+1,fecha_ini.length()));
                
                Calendar calendario = Calendar.getInstance();
                calendario.set(year_i,month_i-1,day_i,0,0,0);
                
                java.sql.Date fecha_ini_D = new java.sql.Date(calendario.getTimeInMillis());
                tempIni  =  new java.sql.Timestamp(fecha_ini_D.getTime()) ;
                strFecha_ini = " and  created >= ?  ";
                
            }
            
            if(null!=request.getParameter("fecha_fin")&&request.getParameter("fecha_fin").trim().length()>0)
            {
                strFecha_fin = " and created <= ? ";
                String fecha_fin = request.getParameter("fecha_fin");
                int year_f;
                int month_f;
                int day_f;

//                month_f=Integer.parseInt(fecha_fin.substring(0,fecha_fin.indexOf("/")));
//                day_f=Integer.parseInt(fecha_fin.substring(fecha_fin.indexOf("/")+1,fecha_fin.lastIndexOf("/")));
//                year_f= Integer.parseInt(fecha_fin.substring(fecha_fin.lastIndexOf("/")+1,fecha_fin.length()));

                year_f= Integer.parseInt(fecha_fin.substring(0,fecha_fin.indexOf("-")));
                month_f=Integer.parseInt(fecha_fin.substring(fecha_fin.indexOf("-")+1,fecha_fin.lastIndexOf("-")));
                day_f=Integer.parseInt(fecha_fin.substring(fecha_fin.lastIndexOf("-")+1,fecha_fin.length()));

                Calendar calendariof = Calendar.getInstance();
                calendariof.set(year_f,month_f-1,day_f,23,59,59);
                java.sql.Date fecha_fin_D = new java.sql.Date(calendariof.getTimeInMillis());
                tempFin  =   new java.sql.Timestamp(fecha_fin_D.getTime()) ;
            }
            
            String tempResponse="";
            int k=0;
            if (null!=request.getParameter("questionid"))
            {
                StringTokenizer strToken = new StringTokenizer(request.getParameter("questions"),"|" );
                String questionid= "";
                int contador=0;
                Connection conn3 =null;
                PreparedStatement pst3 =null;
                ResultSet rs3 =null;
                try
                {
                    conn3 = SWBUtils.DB.getDefaultConnection();
                    String tempReview = "";
                    if(request.getParameter("tipo_revisar").equals("revisados") )
                        tempReview = " and review = 1 ";
                    else if(request.getParameter("tipo_revisar").equals("no revisados"))
                        tempReview = " and review = 0 ";
                    
                    String strSQL3 = " select responseid from sr_responseuser where " +
                            " surveyid = ? and idtm=? and statistic = ? " + tempReview + strFecha_ini + strFecha_fin;
                    pst3 = conn3.prepareStatement(strSQL3);
                    pst3.setLong(1,Long.parseLong(request.getParameter("surveyid")));
                    pst3.setString(2,idtm);
                    pst3.setInt(3,1);
                    if(request.getParameter("fecha_ini").length()>0 && request.getParameter("fecha_fin").length()==0)
                    {
                        pst3.setTimestamp(4,tempIni);
                    }
                    else if(request.getParameter("fecha_ini").length()>0 && request.getParameter("fecha_fin").length()>0)
                    {
                        pst3.setTimestamp(4,tempIni);
                        pst3.setTimestamp(5,tempFin);
                    }
                    else  if(request.getParameter("fecha_ini").length()==0 && request.getParameter("fecha_fin").length()>0)
                    {
                        pst3.setTimestamp(4,tempFin);
                    }

                    rs3 = pst3.executeQuery();
                    contador=0;
                    
                    while(rs3.next())
                    {
                        tempResponse += rs3.getLong("responseid") + " ,";
                        contador++;
                    }
                    if(rs3!=null)rs3.close();
                    if(pst3!=null)pst3.close();
                    if(conn3!=null)conn3.close();
                    
                    if(tempResponse.length()>0) tempResponse = tempResponse.substring(0,tempResponse.lastIndexOf(","));
                }
                
                catch(Exception e)
                {
                    log.error("Error while trying to load records from sr_responseuser, class - Reports, method - doAdmin",e);
                }
                finally
                {
                    rs3=null;
                    pst3=null;
                    conn3=null;
                }
                
                Survey sur01 = new Survey();
                sur01.setResourceBase(base);
                ret.append(sur01.getWB3AdminStyle());
                ret.append("\n<div class=\"swbform\">");
                ret.append("\n<fieldset>");
                ret.append("\n<table width=100% >");
                ret.append("\n<tr><td >");
                ret.append("\n<b>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgEstadisticaPregunta")+"</b></td></tr>");
                ret.append("\n<tr><td ><font size=\"1\">"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgSeEncontraron")+" "+ contador +" "+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgFormulariosContestadosParametrosSolicitados")+"</td></tr>");
                ret.append("\n<tr><td >");
                boolean bandera = true;
                while(strToken.hasMoreTokens())
                {
                    questionid=strToken.nextToken();
                    
                    RecQuestion ObjRQ = new RecQuestion();
                    ObjRQ.setIdtm(idtm);
                    ObjRQ.setQuestionID(Integer.parseInt(questionid));
                    ObjRQ.load();
                    String strMultiple="";
                    if(ObjRQ.getControlID()==3||ObjRQ.getControlID()==5)
                    {
                        if(bandera)
                        {
                            ret.append("\n<font size=\"1\" color=\"red\">"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgNumeroRespuestasOpcion")+"&nbsp;<b>(&nbsp;)&nbsp;</b></font><br><br>");
                            bandera = false;
                        }
                        strMultiple="<font size=1> - "+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgMultiple")+"</font>";
                    }
                    else ret.append("<br>");
                    
                    if(tempResponse.length()>0)
                    {
                        String faXML = new String("");
                        Element opciones=null;
                        org.w3c.dom.NodeList nodes = null;
                        Connection conn4 =null;
                        PreparedStatement pst4 =null;
                        ResultSet rs4 =null;
                        PreparedStatement pst5 =null;
                        ResultSet rs5 =null;
                        Connection conn6 =null;
                        PreparedStatement pst6 =null;
                        ResultSet rs6 =null;
                        try
                        {
                            // se crea el hash map con las opciones del set de respuestas
                            if(request.getParameter("tipo_pregunta").equalsIgnoreCase("No abiertas") )
                            {
                                conn4 = SWBUtils.DB.getDefaultConnection();
                                pst4 = conn4.prepareStatement("" +
                                        "  select sr_freqanswer.stringxml " +
                                        "  from sr_question, sr_freqanswer " +
                                        "  where questionid = ? and sr_question.idtm=? and " +
                                        "  sr_question.freqanswerid = sr_freqanswer.freqanswerid ");
                                pst4.setLong(1,Long.parseLong(questionid));
                                pst4.setString(2,idtm);
                                rs4 = pst4.executeQuery();
                                org.w3c.dom.Document dom = SWBUtils.XML.getNewDocument();
                                if(rs4.next())
                                {
                                    faXML = SWBUtils.IO.readInputStream(rs4.getAsciiStream("stringxml"));
                                }
                                dom = SWBUtils.XML.xmlToDom(faXML);
                                opciones = (Element)dom.getFirstChild();
                                nodes = opciones.getChildNodes();
                                HashMap val = new HashMap();
                                for(int i = 0; i<nodes.getLength();i++)
                                {
                                    org.w3c.dom.Node node = nodes.item(i);
                                    val.put(node.getAttributes().getNamedItem("id").getNodeValue(),"0");
                                }
                                if(rs4!=null)rs4.close();
                                if(pst4!=null)pst4.close();
                                pst5 = conn4.prepareStatement("" +
                                        " select sr_answer.stringxml " +
                                        " from sr_answer where responseid in ( " + tempResponse + " ) and " +
                                        " questionid = ? and idtm=? ");
                                pst5.setLong(1,Long.parseLong(questionid));
                                pst5.setString(2,idtm);
                                rs5 = pst5.executeQuery();
                                int numrespuestas =0;
                                while(rs5.next())
                                {
                                    org.w3c.dom.Document  domA = SWBUtils.XML.getNewDocument();
                                    
                                    String xmlAnswer = SWBUtils.IO.readInputStream(rs5.getAsciiStream("stringxml"));
                                    if(null==xmlAnswer) xmlAnswer = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><resource/>";
                                    
                                    domA = SWBUtils.XML.xmlToDom(xmlAnswer);
                                    
                                    Element respuestas = (Element) domA.getFirstChild();
                                    org.w3c.dom.NodeList nodeA = respuestas.getChildNodes();
                                    
                                    if(nodeA.getLength()>0)
                                    {
                                        for(int j=0; j<nodeA.getLength();j++)
                                        {
                                            org.w3c.dom.Node node = nodeA.item(j);
                                            String temporal = (String)val.get(node.getFirstChild().getNodeValue());
                                            int tempoInt = Integer.parseInt(temporal)+1;
                                            val.remove(node.getFirstChild().getNodeValue());
                                            val.put(node.getFirstChild().getNodeValue(),Integer.toString(tempoInt));
                                            numrespuestas += tempoInt;
                                        }
                                    }
                                    nodeA = null;
                                    respuestas = null;
                                    domA = null;
                                }
                                
                                if(rs5!=null)rs5.close();
                                if(pst5!=null)pst5.close();
                                if(conn4!=null)conn4.close();
                                RecQuestion oRQ = new RecQuestion();
                                oRQ.setIdtm(idtm);
                                oRQ.setQuestionID(Integer.parseInt(questionid));
                                oRQ.load();
                                
                                ret.append("\n<table border=\"0\" cellpadding=\"0\" width=\"450\">");
                                ret.append("\n<tr><td width=\"100\"><font size=\"1\">"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgPregunta")+strMultiple+": </font></td><td colspan=\"3\"><font size=\"1\"><b>"+oRQ.getQuestion()+"</b></font></td></tr>");
                                ret.append("\n<tr><td width=\"100\"><font size=\"1\"><b>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgOpcion")+"</b></font></td><td colspan=\"3\" align=\"center\"><font size=\"1\"><b>&nbsp;</b></font></td></tr>");
                                String bgcolor="";
                                String webpath=SWBPlatform.getContextPath();
                                
                                for(int i = 0; i<nodes.getLength();i++)
                                {
                                    org.w3c.dom.Node node = nodes.item(i);
                                    int porciento = Integer.parseInt((String)val.get(Integer.toString(i+1)));
                                    
                                    double contesto=0;
                                    RecQuestion obRQ = new RecQuestion();
                                    obRQ.setIdtm(idtm);
                                    obRQ.setQuestionID(Integer.parseInt(questionid));
                                    obRQ.load();
                                    if(obRQ.getRequired()==0)
                                    {
                                        contesto = 1000000000*((double)porciento/(double)numrespuestas)/10000000;
                                        contador=numrespuestas;
                                    }
                                    obRQ=null;
                                    contesto = 1000000000*((double)porciento/(double)contador)/10000000;
                                    
                                    double p_barra = contesto * 0.9;
                                    if(contesto<10) p_barra=(contesto * 0.9)+1;
                                    
                                    k++;
                                    switch(k)
                                    {
                                        case 1:
                                            bgcolor="#FF0000";
                                            break;
                                        case 2:
                                            bgcolor="#339900";
                                            break;
                                        case 3:
                                            bgcolor="#003333";
                                            break;
                                        case 4:
                                            bgcolor="#0000FF";
                                            break;
                                        case 5:
                                            bgcolor="#CC6633";
                                            break;
                                        case 6:
                                            bgcolor="#009900";
                                            break;
                                        case 7:
                                            bgcolor="#CC0000";
                                            break;
                                        case 8:
                                            bgcolor="#3300CC";
                                            k=0;
                                            break;
                                            
                                    }
                                    
                                    int numCuadros = Integer.parseInt(Double.toString(contesto).substring(0,Double.toString(contesto).lastIndexOf(".")));
                                    String strBarra = "";
                                    if(numCuadros==0) numCuadros=1;
                                    int x;
                                    for( x=0; x<Math.round(numCuadros*0.8);x++) strBarra += "&#2558;";
                                    ret.append("\n<tr>" +
                                            "<td><font size=\"1\"><b>"+Integer.toString(i+1)+"</b> - "+node.getFirstChild().getNodeValue()+"</font></td>");
                                    if(request.getParameter("content_type")==null)
                                        ret.append("<td align=left colspan=3><img src=\""+SWBPlatform.getContextPath()+"swbadmin/images/lineavacio.gif\" width=\"" + Double.toString(p_barra).substring(0,Double.toString(p_barra).lastIndexOf(".")) + "\" height=\"12\" name=\"azul\" ><font size=1>&nbsp;&nbsp;<b>"+Double.toString(contesto).substring(0,Double.toString(contesto).lastIndexOf(".")+2)+" %</b>&nbsp;<font color=red>(<b>"+(porciento)+"</b>)</font></font></td>");
                                    else ret.append("<td><font size=1 color=red><b>"+(porciento)+"</b></font></td><td colspan=2  align=left><b><font size=1 color=\""+bgcolor+"\" height=\"14\">"+strBarra+"</font><font size=1 color=\"#000000\">&nbsp;&nbsp;"+Double.toString(contesto).substring(0,Double.toString(contesto).lastIndexOf(".")+2)+" %</font></b></td>");
                                    
                                    ret.append("</tr>");
                                }
                                
                                ret.append("\n<tr><td>&nbsp;</td><td colspan=3><font size=1 ><b>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgNumeroTotalRespuestas")+":&nbsp;"+contador+"</b></font></td></tr>");
                                ret.append("\n</table>");
                                
                                val=null;
                            }
                            else
                            {
                                // seccion para el reporte de preguntas abiertas
                                RecQuestion oRQ = new RecQuestion();
                                oRQ.setIdtm(idtm);
                                oRQ.setQuestionID(Integer.parseInt(questionid));
                                oRQ.load();
                                
                                ret.append("\n<table border=0 cellpadding=0 width=450>");
                                ret.append("\n<tr><td width=100><font size=1>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgPregunta")+": </font></td><td width><font size=1><b>"+oRQ.getQuestion()+"</b></font></td></tr>");
                                ret.append("\n<tr><td  colspan=4 align=left><font size=1>");
                                conn6 = SWBUtils.DB.getDefaultConnection();
                                pst6 = conn6.prepareStatement("" +
                                        " select sr_answer.stringxml, sr_answer.responseid " +
                                        " from sr_answer where responseid in ( " + tempResponse + " ) and " +
                                        " questionid = ? and idtm = ? ");
                                pst6.setLong(1,Long.parseLong(questionid));
                                pst6.setString(2,idtm);
                                rs6 = pst6.executeQuery();
                                ret.append("<table width=\"100%\" border=\"1\" cellpadding=\"1\" cellspacing=\"0\">");
                                ret.append("\n<tr><td><font size=\"1\"><b>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgRespuesta")+"</b></font></td><td><font size=\"1\"><b>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgUsuario")+"</b></font></td></tr>");
                                while(rs6.next())
                                {
                                    org.w3c.dom.Document dom3 = SWBUtils.XML.getNewDocument();
                                    String temporalString = SWBUtils.IO.readInputStream(rs6.getAsciiStream("stringxml"));
                                    if(temporalString!=null&&!temporalString.equals(""))
                                    {
                                        dom3 = SWBUtils.XML.xmlToDom(temporalString);
                                        Element respuestas3 = (Element) dom3.getFirstChild();
                                        org.w3c.dom.NodeList nodes3 = respuestas3.getElementsByTagName("answer");
                                        if(nodes3.getLength()>0)
                                        {
                                            org.w3c.dom.Node node31 = nodes3.item(0);
                                            String temporal = (String)node31.getFirstChild().getNodeValue();
                                            RecResponseUser oRU = new RecResponseUser();
                                            oRU.setIdtm(idtm);
                                            oRU.setResponseID(rs6.getLong("responseid"));
                                            oRU.load();
                                            
                                            String correo = oRU.getUser();
                                            if(!oRU.getUser().equals("Anonimo")&&!oRU.getUser().equals("Anónimo")&&!oRU.getUser().startsWith("0_"))
                                            {
                                                User oRUser = paramsRequest.getUser().getUserRepository().getUser(oRU.getUser());
                                                correo = oRUser.getName();
                                                oRUser=null;
                                            }
                                            else
                                            {
                                                correo = paramsRequest.getLocaleString("usrmsg_MainSurvey_setResourceBase_msgAnonimo");
                                            }
                                            ret.append("\n<tr><td><font size=1>"+temporal+"</font></td><td><font size=1>"+correo+"</font></td></tr>");
                                            oRU=null;
                                        }
                                        nodes3 = null;
                                        respuestas3 = null;
                                        dom3 = null;
                                    }
                                }
                                ret.append("</td></tr></table>");
                                if(rs6!=null)rs6.close();
                                if(pst6!=null)pst6.close();
                                if(conn6!=null)conn6.close();
                                ret.append("</font></td></tr></table>");
                            }
                        }
                        catch(Exception e)
                        {
                            log.error("Error while trying to create hashmap or querying database, class - Reports, method - doAdmin",e);
                        }
                        finally
                        {
                            rs4 =null;
                            pst4 =null;
                            rs5 =null;
                            pst5 =null;
                            conn4 =null;
                            rs6=null;
                            pst6=null;
                            conn6 =null;
                        }
                    }
                    else
                    {
                        ret.append("\n"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgNoEncontroNingunFormulario")+"  \" <b>"+ request.getParameter("tipo_revisar").toUpperCase()+" \"</b>");
                    }
                    ret.append("<br><hr><br>");
                }
                ret.append("\n</td></tr>");
                ret.append("\n</table>");
                ret.append("\n</fieldset>");
                ret.append("\n</div>");

            }
        }
        
        if(accion.equals("re_revisar"))
        {
            ret.append("\n<script language=\"javascript\"> ");
            ret.append("\n     function validarBuscar(forma){");
            ret.append("\n          if(forma.buscar.value!='todos'){");
            ret.append("\n             var tempQuery = forma.query.value;");
            ret.append("\n             trimB(forma.query);");
            ret.append("\n             if(forma.query.value==''){");
            ret.append("\n                if(forma.buscar.value=='nombre'){");
            ret.append("\n                   alert('"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgAlertPonerUsuarioBuscar")+"');");
            ret.append("\n                }      ");
            ret.append("\n                if(forma.buscar.value=='apellido'){");
            ret.append("\n                   alert('"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgPonerApellidoPaternoBuscar")+"');");
            ret.append("\n                }      ");
            ret.append("\n                if(forma.buscar.value=='email'){");
            ret.append("\n                   alert('"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgPonerEmailBuscar")+"');");
            ret.append("\n                }      ");
            ret.append("\n                if(forma.buscar.value=='id'){");
            ret.append("\n                   alert('"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgPonerIdBuscar")+"');");
            ret.append("\n                }      ");
            ret.append("\n              forma.query.value=tempQuery;   ");
            ret.append("\n              forma.query.focus();  ");
            ret.append("\n              forma.query.select(); ");
            ret.append("\n              return(false);   ");
            ret.append("\n            }");
            ret.append("\n            else{ ");
            ret.append("\n                  if(forma.buscar.value=='id'){");
            ret.append("\n                      if(isNaN(forma.query.value)){");
            ret.append("\n                          alert('"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgAlertBuscarIdNumerosPorFavor")+"');    ");
            ret.append("\n                          forma.query.value=tempQuery;   ");
            ret.append("\n                          forma.query.focus();  ");
            ret.append("\n                          forma.query.select(); ");
            ret.append("\n                          return(false);   ");
            ret.append("\n                      }  ");
            ret.append("\n                } ");
            ret.append("\n            } ");
            ret.append("\n           forma.query.value=tempQuery;   ");
            ret.append("\n          }");
            ret.append("\n          forma.hresponseid.value='null';");
            ret.append("\n      return(true);");
            ret.append("\n     }");
            ret.append("\n     function habilita(valor){");
            ret.append("\n          if(valor=='todos'){");
            ret.append("\n              forma.query.style.visibility='hidden'; ");
            ret.append("\n          }");
            ret.append("\n          else{");
            ret.append("\n              forma.query.style.visibility='visible'; ");
            ret.append("\n          }");
            ret.append("\n     }");
            ret.append("\n");
            ret.append("\n    function trimB(field) {    ");
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
            ret.append("\n");
            ret.append("\n                                                         ");
            ret.append("\n         function ventana(accion,sizze){    ");
            ret.append("\n             ");
            SWBResourceURL url_email = paramsRequest.getRenderUrl();
            url_email.setCallMethod(url_email.Call_DIRECT);
            url_email.setAction("re_email");
            ret.append("\n              window.open(\""+url_email+"/\"+accion+\"&jsession="+request.getSession().getId()+" \",\"EmailWindow\",sizze);    ");
            ret.append("\n         }    ");
            ret.append("\n        ");
            ret.append("\n        function borrar(forma){");
            ret.append("\n           forma.hresponseid.value=forma.responseid.value;       ");
            
            SWBResourceURL urlbf = paramsRequest.getRenderUrl();
            urlbf.setMode(urlbf.Mode_ADMIN);
            urlbf.setAction("re_borrarformulario");
            
            ret.append("\n           forma.action='"+urlbf+"';       ");
            ret.append("\n           forma.submit();       ");
            ret.append("\n        }");
            ret.append("\n</SCRIPT>");
            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<fieldset>");
            ret.append("\n<font face=verdana size=1>");
            
            SWBResourceURL urlrr = paramsRequest.getRenderUrl();
            urlrr.setMode(urlrr.Mode_ADMIN);
            urlrr.setAction("re_revisar");
            
            ret.append("\n<form name=\"forma\" action=\""+urlrr+"\" onsubmit=\"return validarBuscar(forma);\" >");
            ret.append("\n<input type=\"hidden\" name=\"fecha_ini\" value=\""+request.getParameter("fecha_ini")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"fecha_fin\" value=\""+request.getParameter("fecha_fin")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"surveyid\" value=\""+request.getParameter("surveyid")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"tipo_revisar\" value=\""+request.getParameter("tipo_revisar")+"\">");
            ret.append("\n<input type=\"hidden\" name=\"jsession\" value=\""+request.getSession().getId()+"\">");
            ret.append("\n<input type=\"hidden\" name=\"hresponseid\">");
            ret.append("\n<table border=0 cellpadding=2 cellspacing=0 width=\"100%\">");
            RecSurvey oRS = new RecSurvey();
            oRS.setIdtm(idtm);
            oRS.setSurveyID(Long.parseLong(request.getParameter("surveyid")));
            oRS.load();
            
            Resource rbase = paramsRequest.getWebPage().getWebSite().getResource(oRS.getResID());
            
            ret.append("\n<tr><td colspan=3 >"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgRevisarFormularios")+"</td></tr>");
            ret.append("\n<tr><td  align=right>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgFormulario")+":</td><td colspan=2 >"+rbase.getTitle()+"</td></tr>");
            String strSeluserName ="";
            String strSeluserLast ="";
            String strSeluserEmail ="";
            String strSelid ="";
            String strSeltodos ="";
            String strVerQuery = " ";
            String strQuery="";
            if(request.getParameter("buscar")!=null)
            {
                if(request.getParameter("buscar").equals("nombre"))
                {
                    strSeluserName = " selected ";
                    strQuery = request.getParameter("query");
                }
                if(request.getParameter("buscar").equals("apellido"))
                {
                    strSeluserLast = " selected ";
                    strQuery = request.getParameter("query");
                }
                if(request.getParameter("buscar").equals("email"))
                {
                    strSeluserEmail = " selected ";
                    strQuery = request.getParameter("query");
                }
                else
                {
                    if(request.getParameter("buscar").equals("id"))
                    {
                        strSelid = " selected ";
                        strQuery = request.getParameter("query");
                    }
                    else
                    {
                        if(request.getParameter("buscar").equals("todos"))
                        {
                            strSeltodos = " selected ";
                            strVerQuery = "style=\"visibility:hidden\"";
                        }
                    }
                    
                }
            }
            ret.append("\n<tr><td  align=right width=200>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgBuscarPor")+":</td><td colspan=2 >" +
                    "<select name=\"buscar\" onchange=\"habilita(this.value)\">" +
                    "<option value=\"nombre\" "+strSeluserName+">"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgUsuarioNombre")+"</option>" +
                    "<option value=\"apellido\" "+strSeluserLast+">"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgUsuarioApellido")+"</option>" +
                    "<option value=\"email\" "+strSeluserEmail+">"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgUsuarioEmail")+"</option>" +
                    "<option value=\"id\" "+strSelid+">Id</option>" +
                    "<option value=\"todos\" "+strSeltodos+">"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgTodos")+"</option>" +
                    "</select>" +
                    "<input type=\"text\" name=\"query\" size=10 "+strVerQuery+" value=\""+strQuery+"\" maxlength=60><input type=\"submit\" value=\""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_btnBuscar")+"\" name=\"btn_submit\" ></td></tr>");
            
            String strPeriodo="";
            if(request.getParameter("fecha_ini").length()>0&&request.getParameter("fecha_fin").length()>0)
            {
                strPeriodo = request.getParameter("fecha_ini") +" al "+ request.getParameter("fecha_fin");
            }
            else
            {
                if(request.getParameter("fecha_ini").length()==0&&request.getParameter("fecha_fin").length()>0)
                {
                    strPeriodo = paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgDelInicioAl")+" "+ request.getParameter("fecha_fin");
                }
                else
                {
                    if(request.getParameter("fecha_ini").length()>0&&request.getParameter("fecha_fin").length()==0)
                    {
                        strPeriodo = request.getParameter("fecha_ini") +" "+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgAFecha");
                        
                    }
                    else
                    {
                        strPeriodo = paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgTodoPeriodo");
                    }
                }
                
            }
            
            ret.append("\n<tr><td   align=right width=200>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgPeriodoDel")+": </td><td colspan=2 >"+strPeriodo+"</td></tr>");
            ret.append("\n</table>");
            ret.append("\n</form></font>");
            ret.append("\n</fieldset>");
            ret.append("\n</div>");
            
            if(request.getParameter("buscar")!=null)
            {
                
                if(request.getParameter("hsave")!=null)
                {
                    Evaluation objEval = null;
                    objEval = new Evaluation();
                    objEval.setResourceBase(base);
                    String sres = (String) request.getParameter("responseid");
                    
                    String ssum = "0";
                    if (request.getParameter("sum")!=null) ssum =request.getParameter("sum");
                    RecResponseUser objRes = null;
                    
                    RecAnswer objAns = null;
                    
                    int answerid = 0;
                    int icurr = 0;
                    RecSurvey objSur = new RecSurvey();
                    objSur.setIdtm(idtm);
                    objSur.setSurveyID(Long.parseLong(request.getParameter("surveyid")));
                    objSur.load();
                    if(objSur.getTypeID()==2)
                    {     // si es de evaluacion guarda calificaciones
                        Enumeration enume = request.getParameterNames();
                        while(enume.hasMoreElements())
                        {
                            String key = (String)enume.nextElement();
                            int keylen = key.length();
                            if (keylen >=5)
                            {
                                String value = request.getParameter(key);
                                String realkey = key.substring(0,5);
                                if(realkey.equalsIgnoreCase("score"))
                                {
                                    String questid = key.substring(5,keylen);
                                    try
                                    {
                                        answerid = objEval.getAnswerId(questid,sres);
                                        if(answerid != 0)
                                        {
                                            objAns=new RecAnswer();
                                            objAns.setIdtm(idtm);
                                            objAns.setAnswerid(answerid);
                                            objAns.load();
                                            objAns.setScore(Integer.parseInt(value));
                                            objAns.update();
                                            icurr++;
                                        }
                                    }
                                    catch(Exception e)
                                    {
                                        log.error("Error while trying to generate a dom object, class - Reports, method - doAdmin",e);
                                    }
                                }
                            }
                        }
                    }
                    objRes = new RecResponseUser();
                    objRes.setIdtm(idtm);
                    objRes.setResponseID(Long.parseLong(sres));
                    objRes.load();
                    objRes.setReview(Integer.parseInt(request.getParameter("review")));
                    objRes.setStatistic(Integer.parseInt(request.getParameter("statistic")));
                    objRes.setComments(request.getParameter("comments"));
                    objRes.setScore(Integer.parseInt(ssum));
                    objRes.setCurrentQuestion(icurr);
                    objRes.update();
                }
                
                
                
                String strFecha_fin="";
                String strFecha_ini="";
                java.sql.Timestamp tempIni = new java.sql.Timestamp(new java.util.Date().getTime());
                java.sql.Timestamp tempFin = tempIni;
                
                if(request.getParameter("fecha_ini").length()>0)
                {
                    String fecha_ini = request.getParameter("fecha_ini");
                    
                    int year_i;
                    int month_i;
                    int day_i;

                    year_i= Integer.parseInt(fecha_ini.substring(0,fecha_ini.indexOf("-")));
                    month_i=Integer.parseInt(fecha_ini.substring(fecha_ini.indexOf("-")+1,fecha_ini.lastIndexOf("-")));
                    day_i=Integer.parseInt(fecha_ini.substring(fecha_ini.lastIndexOf("-")+1,fecha_ini.length()));

                    Calendar calendario = Calendar.getInstance();
                    calendario.set(year_i,month_i-1,day_i,0,0,0);
                    java.sql.Date fecha_ini_D = new java.sql.Date(calendario.getTimeInMillis());
                    tempIni  =  new java.sql.Timestamp(fecha_ini_D.getTime()) ;
                    strFecha_ini = " and  created >= ?  ";
                }
                
                if(request.getParameter("fecha_fin").length()>0)
                {
                    String fecha_fin = request.getParameter("fecha_fin");
                    
                    int year_f;
                    int month_f;
                    int day_f;

                    year_f= Integer.parseInt(fecha_fin.substring(0,fecha_fin.indexOf("-")));
                    month_f=Integer.parseInt(fecha_fin.substring(fecha_fin.indexOf("-")+1,fecha_fin.lastIndexOf("-")));
                    day_f=Integer.parseInt(fecha_fin.substring(fecha_fin.lastIndexOf("-")+1,fecha_fin.length()));

//                    month_f=Integer.parseInt(fecha_fin.substring(0,fecha_fin.indexOf("/")));
//                    day_f=Integer.parseInt(fecha_fin.substring(fecha_fin.indexOf("/")+1,fecha_fin.lastIndexOf("/")));
//                    year_f= Integer.parseInt(fecha_fin.substring(fecha_fin.lastIndexOf("/")+1,fecha_fin.length()));
                    
                    Calendar calendariof = Calendar.getInstance();
                    calendariof.set(year_f,month_f-1,day_f,23,59,59);
                    java.sql.Date fecha_fin_D = new java.sql.Date(calendariof.getTimeInMillis());
                    tempFin  =   new java.sql.Timestamp(fecha_fin_D.getTime()) ;
                    strFecha_fin = " and created <= ? ";
                }
                Connection conn3 =null;
                PreparedStatement pst3 =null;
                ResultSet rsCuenta =null;
                ResultSet rs3 =null;
                try
                {
                    String tempReview = "";
                    String tempResp = "";
                    String tempSearch ="";
                    if(request.getParameter("tipo_revisar").equals("revisados") ) tempReview = " and review = 1 ";
                    else
                    {
                        if(request.getParameter("tipo_revisar").equals("no revisados")) tempReview = " and review = 0 ";
                    }
                    if(request.getParameter("buscar").equals("id") ) tempResp = " and responseid = "+request.getParameter("query");
                    if(!request.getParameter("buscar").equals("id")&&!request.getParameter("buscar").equals("todos"))
                    {
                        if(request.getParameter("buscar").equals("nombre") )
                        {
                            tempSearch ="";
                            
                            String strSQL4 = "select * from wbuser where usrFirstName like '%"+request.getParameter("query")+"%'";
                            Connection conn4 =null;
                            Statement st4 =null;
                            ResultSet rs4 =null;
                            try
                            {
                                conn4 = SWBUtils.DB.getDefaultConnection();
                                st4 = conn4.createStatement();
                                rs4 = st4.executeQuery(strSQL4);
                                StringBuffer sbTemp = new StringBuffer("");
                                while(rs4.next())
                                {
                                    sbTemp.append("'"+rs4.getLong("usrId")+"_"+rs4.getString("usrRepository")+"',");
                                }
                                if (sbTemp.toString().length()>0)
                                {
                                    tempSearch = " and wbuser in ( "+ sbTemp.toString().substring(0,sbTemp.toString().lastIndexOf(","))+" ) ";
                                }
                                if(rs4!=null)rs4.close();
                                if(st4!=null)st4.close();
                                if(conn4!=null)conn4.close();
                            }
                            catch(Exception e)
                            {
                                log.error("Error while trying to load records from wbuser, class - Reports, method - doAdmin",e);
                            }
                            finally
                            {
                                rs4=null;
                                st4=null;
                                conn4=null;
                            }
                        }
                        else
                        {
                            if(request.getParameter("buscar").equals("apellido") )
                            {
                                tempSearch ="";
                                String strSQL4 = "select * from wbuser where usrLastName like '%"+request.getParameter("query")+"%'";
                                Connection conn4 = null;
                                Statement st4 = null;
                                ResultSet rs4 =null;
                                try
                                {
                                    conn4 = SWBUtils.DB.getDefaultConnection();
                                    st4 = conn4.createStatement();
                                    rs4 = st4.executeQuery(strSQL4);
                                    StringBuffer sbTemp = new StringBuffer("");
                                    while(rs4.next())
                                    {
                                        sbTemp.append("'"+rs4.getLong("usrId")+"_"+rs4.getString("usrRepository")+"',");
                                    }
                                    if (sbTemp.toString().length()>0)
                                    {
                                        tempSearch = " and wbuser in ( "+ sbTemp.toString().substring(0,sbTemp.toString().lastIndexOf(","))+" ) ";
                                    }
                                    if(rs4!=null)rs4.close();
                                    if(st4!=null)st4.close();
                                    if(conn4!=null)conn4.close();
                                }
                                catch(Exception e)
                                {
                                    log.error("Error while trying to load records from wbuser, class - Reports, method - doAdmin",e);
                                }
                                finally
                                {
                                    rs4=null;
                                    st4=null;
                                    conn4=null;
                                }
                            }
                            else
                            {
                                if(request.getParameter("buscar").equals("email") )
                                {
                                    String strSQLx = "select * from wbuser where usrEmail like '%"+request.getParameter("query")+"%'";
                                    Connection connx = null;
                                    Statement stx = null;
                                    ResultSet rsx =null;
                                    try
                                    {
                                        connx = SWBUtils.DB.getDefaultConnection();
                                        stx = connx.createStatement();
                                        rsx = stx.executeQuery(strSQLx);
                                        StringBuffer sbTemp = new StringBuffer("");
                                        while(rsx.next())
                                        {
                                            sbTemp.append("'"+rsx.getLong("usrId")+"_"+rsx.getString("usrRepository")+"',");
                                        }
                                        if (sbTemp.toString().length()>0)
                                        {
                                            tempSearch = " and wbuser in ( "+ sbTemp.toString().substring(0,sbTemp.toString().lastIndexOf(","))+" ) ";
                                        }
                                        if(rsx!=null)rsx.close();
                                        if(stx!=null)stx.close();
                                        if(connx!=null)connx.close();
                                    }
                                    catch(Exception e)
                                    {
                                        //WBUtils.getInstance().log(WBUtils.getLocaleString("locale_wb2_resources","errormsg_Reports_getAdmHtml_msgErrorConsultarDBUSER"),true);
                                        log.error("Error while trying to load records from wbuser, class - Reports, method - doAdmin",e);
                                    }
                                    finally
                                    {
                                        rsx=null;
                                        stx=null;
                                        connx=null;
                                    }
                                }
                            }
                        }
                    }
                    
                    if(tempSearch.trim().length()==0&&request.getParameter("buscar")!=null&&!request.getParameter("buscar").equals("todos"))
                        tempSearch = " and wbuser = '"+ request.getParameter("query")+"' ";
                    
                    String strSQL3 = new String("" +
                            " select responseid, wbuser from " +
                            " sr_responseuser where " +
                            " surveyid = ? and idtm=? " +
                            tempResp +
                            tempSearch +
                            tempReview +
                            strFecha_ini +
                            strFecha_fin);

                    System.out.println(strSQL3);

                    conn3 = SWBUtils.DB.getDefaultConnection();
                    pst3 = conn3.prepareStatement(strSQL3);
                    pst3.setLong(1,Long.parseLong(request.getParameter("surveyid")));
                    pst3.setString(2,idtm);
                    
                    if(request.getParameter("fecha_ini").length()>0 && request.getParameter("fecha_fin").length()==0)
                    {
                        pst3.setTimestamp(3,tempIni);
                    }
                    else
                    {
                        if(request.getParameter("fecha_ini").length()>0 && request.getParameter("fecha_fin").length()>0)
                        {
                            pst3.setTimestamp(3,tempIni);
                            pst3.setTimestamp(4,tempFin);
                        }
                        else
                        {
                            if(request.getParameter("fecha_ini").length()==0 && request.getParameter("fecha_fin").length()>0)
                            {
                                pst3.setTimestamp(3,tempFin);
                            }
                        }
                    }
                    ret.append("\n<div class=\"swbform\">");
                    ret.append("\n<fieldset>");
                    ret.append("\n<form name=\"forma2\" action=\""+urlrr+"\">");
                    ret.append("\n<input type=\"hidden\" name=\"fecha_ini\" value=\""+request.getParameter("fecha_ini")+"\">");
                    ret.append("\n<input type=\"hidden\" name=\"fecha_fin\" value=\""+request.getParameter("fecha_fin")+"\">");
                    ret.append("\n<input type=\"hidden\" name=\"surveyid\" value=\""+request.getParameter("surveyid")+"\">");
                    ret.append("\n<input type=\"hidden\" name=\"tipo_revisar\" value=\""+request.getParameter("tipo_revisar")+"\">");
                    ret.append("\n<input type=\"hidden\" name=\"jsession\" value=\""+request.getSession().getId()+"\">");
                    ret.append("\n<input type=\"hidden\" name=\"hresponseid\">");
                    ret.append("\n<input type=\"hidden\" name=\"buscar\" value=\""+request.getParameter("buscar")+"\">");
                    ret.append("\n<input type=\"hidden\" name=\"query\" value=\""+request.getParameter("query")+"\">");
                    ret.append("\n<table border=0 cellpadding=1 cellspacing=0 width=\"100%\">");
                    rsCuenta = pst3.executeQuery();
                    int numRegistros = 0;
                    while(rsCuenta.next()) numRegistros++;
                    if(rsCuenta!=null)rsCuenta.close();
                    rs3 = pst3.executeQuery();
                    ret.append("\n<tr><td colspan=3 >"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgResultadoBusqueda")+"</td></tr>");
                    if(numRegistros==0) ret.append("\n<tr><td  align=right width=200 colspan=3>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgNoEncontraronRegistros")+"</td></tr>");
                    else
                    {
                        ret.append("\n<tr><td  align=right width=200 >"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgSeEncontraron")+": </td><td colspan=2 >"+numRegistros+" "+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgFormulariosContestados")+"</td></tr>");
                        ret.append("\n<tr><td  align=right width=200>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgSelecciona")+":</td><td colspan=2 align=left><select name=\"responseid\">");
                        while(rs3.next())
                        {
                            String tmpCorreo=rs3.getString("wbuser");
                            String strSelected ="";
                            if(!tmpCorreo.equals("Anonimo")&&!tmpCorreo.equals("Anónimo")&&!tmpCorreo.startsWith("0_")&&!tmpCorreo.startsWith("0"))
                            {
                                User oRUser = paramsRequest.getUser().getUserRepository().getUser(tmpCorreo);
                                tmpCorreo = oRUser.getName();
                            }
                            else
                            {
                                tmpCorreo = paramsRequest.getLocaleString("usrmsg_MainSurvey_setResourceBase_msgAnonimo");
                            }
                            if(request.getParameter("responseid")!=null)
                            {
                                if(Integer.parseInt(request.getParameter("responseid"))==rs3.getInt("responseid")) strSelected = " selected ";
                            }
                            ret.append("\n<option value=\""+rs3.getInt("responseid")+"\" "+strSelected+" >"+rs3.getInt("responseid")+" - "+tmpCorreo+"</option>");
                        }
                        ret.append("</select><input type=\"submit\" value=\""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_btnRevisar")+"\" >");
                        ret.append("</td></tr>");
                    }
                    rsCuenta=null;
                    if(rs3!=null)rs3.close();
                    if(pst3!=null)pst3.close();
                    if(conn3!=null)conn3.close();
                }
                catch(Exception e)
                {
                    log.error("Error while trying to search users, class - Reports, method - doAdmin",e);
                }
                finally
                {
                    rsCuenta=null;
                    rs3=null;
                    pst3=null;
                    conn3=null;
                }
                if(request.getParameter("responseid")!=null)
                {
                    RecResponseUser oRRU = new RecResponseUser();
                    oRRU.setIdtm(idtm);
                    oRRU.setResponseID(Long.parseLong(request.getParameter("responseid")));
                    oRRU.load();
                    ret.append("\n<tr><td  align=right width=200>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgContestadoEl")+": </td><td colspan=2 >"+oRRU.getCreated()+"</td></tr>");
                    String temp_S ="";
                    if(oRRU.getReview()==1) temp_S=" checked ";
                    ret.append("\n<tr><td  align=right width=200>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgRevisado")+"</td><td colspan=2 ><input type=\"checkbox\" name=\"review\" value=\"1\" "+temp_S+"></td></tr>");
                    temp_S ="";
                    if(oRRU.getStatistic()==1) temp_S=" checked ";
                    ret.append("\n<tr><td  align=right width=200>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgTomarParaEstadistica")+"</td><td colspan=2 ><input type=\"checkbox\" name=\"statistic\" value=\"1\" "+temp_S+"></td></tr>");
                    boolean enviarEmail = false;
                    String strUser = oRRU.getUser();
                    User oRUser = null;
                    if(!oRRU.getUser().equals("Anonimo")&&!oRRU.getUser().equals("Anónimo")&&!oRRU.getUser().startsWith("0_"))
                    {
                        oRUser = paramsRequest.getUser().getUserRepository().getUser(oRRU.getUser());
                        strUser = oRUser.getName(); //oRUser.getFirstName()+" "+oRUser.getLastName();
                        enviarEmail = true;
                    }
                    else
                    {
                        strUser=paramsRequest.getLocaleString("usrmsg_MainSurvey_setResourceBase_msgAnonimo");
                    }
                    ret.append("\n<tr><td  align=right width=200>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgUsuario")+": </td><td colspan=2 >"+strUser);
                    if(enviarEmail) ret.append("\n&nbsp;&nbsp;<input type=\"button\" value=\""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_btnEnviarEmail")+"\" name=\"btn_email\" onclick=\"ventana('?created="+oRRU.getCreated()+"&comments='+comments.value+'&email="+oRUser.getEmail()+"','width=480, height=450')\" >");
                    ret.append("\n&nbsp;<input type=\"button\" value=\""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_btnBorrarFormulario")+"\" name=\"btn_borrar\" onclick=\"if(confirm('�"+"Est�s seguro de eliminar este c�digo de validaci�n"+"?')){ borrar(this.form);} else { return (false);}\" ></td></tr>");
                    temp_S="";
                    if(oRRU.getComments()!=null) temp_S=oRRU.getComments();
                    ret.append("\n<tr><td  align=right width=200>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgComentario")+":</td><td colspan=2><textarea name=\"comments\" cols=40 rows=5 maxlength=4000>"+temp_S+"</textarea></td></tr>");
                    oRRU=null;
                }
                ret.append("\n</table>");
                ret.append("\n</form>");
                ret.append("\n</fieldset>");
                ret.append("\n</div>");

                
                /*Eval*/
                String sres = null;
                sres = request.getParameter("responseid");
                if(sres != null)
                {
                    String straddr = paramsRequest.getRenderUrl().setMode(paramsRequest.Mode_ADMIN).setAction("re_revisar").toString();
                    Evaluation objEval = null;
                    objEval = new Evaluation();
                    objEval.setResourceBase(base);
                    String baseid = base.getId();
                    ret.append(objEval.getEvaluation(sres, straddr, request, baseid, paramsRequest));
                }
                else
                {
                    SWBResourceURL urlsur = paramsRequest.getRenderUrl();
                    urlsur.setMode(urlsur.Mode_ADMIN);
                    urlsur.setAction("update_en");
                    
                    ret.append("\n<table width=100% cellpadding=2 cellspacing=0 border=0><tr><td align=right><hr noshade size=1>");
                    ret.append("\n<input type=button value=\"Regresar\" onclick=\"javascript:window.location='"+urlsur+"'\">");
                    ret.append("\n</td></tr></table>");
                }
                /*Eval*/
            }
            else
            {
                SWBResourceURL urlsur = paramsRequest.getRenderUrl();
                urlsur.setMode(urlsur.Mode_ADMIN);
                urlsur.setAction("update_en");
                
                ret.append("\n<table width=100% cellpadding=2 cellspacing=0 border=0><tr><td align=right><hr noshade size=1>");
                ret.append("\n<input type=button value=\"Regresar\" onclick=\"javascript:window.location='"+urlsur+"'\">");
                ret.append("\n</td></tr></table>");
            }
        }
        if(accion.equals("re_email"))
        {
            SWBResourceURL urlre = paramsRequest.getRenderUrl();
            urlre.setMode(urlre.Mode_ADMIN);
            urlre.setAction("re_enviaemail");
            Survey objSur = new Survey();
            ret.append(objSur.getWB3AdminStyle());
            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<fieldset>");
            ret.append("\n<form name=\"forma\" action=\""+urlre+"\" method=\"POST\" >");
            ret.append("\n<input type=\"hidden\" name=\"email\" value=\""+request.getParameter("email")+"\">");
            ret.append("\n<table width=\"100%\" border=0 cellspacing=0 cellpadding=2>");
            ret.append("\n<tr><td colspan=2 >"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgEnvioEmail")+"</td></tr>");
            ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgPara")+":</td><td >"+request.getParameter("email")+"</td></tr>");
            ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgAsunto")+":</td><td ><textarea name=\"asunto\" cols=40 rows=5 maxlength=500>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgRetroFormularioContestado")+" "+request.getParameter("created")+"</textarea></td></tr>");
            ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgComentario")+":</td><td ><textarea name=\"comments\" cols=\"40\" rows=\"12\" maxlength=4000>"+request.getParameter("comments")+"</textarea></td></tr>");
            ret.append("\n<tr><td colspan=2 align=right><hr noshade size=1><input type=\"submit\" name=\"btn_submit\" value=\""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgEnviarEmail")+"\" ><input type=\"button\" name=\"btn_cancelar\" value=\""+paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_btnCancelar")+"\" onclick=\"window.close();\" ></td></tr>");
            ret.append("\n</form>");
            ret.append("\n</fieldset>");
            ret.append("\n</div>");
        }
        
        if(accion.equals("re_enviaemail"))
        {
            if(request.getParameter("email")!=null)
            {
                String asunto = paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgSinTitulo");
                String comentario = paramsRequest.getLocaleString("usrmsg_Reports_getAdmHtml_msgSinTexto");
                if(request.getParameter("asunto")!=null) asunto = request.getParameter("asunto");
                if(request.getParameter("comments")!=null) comentario = request.getParameter("comments");
                SWBUtils.EMAIL.sendBGEmail(request.getParameter("email"),asunto,comentario);
            }
            ret.append("\n<script language=javascript>");
            ret.append("\n window.close();");
            ret.append("\n</script>");
        }
        
        if(accion.equals("re_borrarformulario"))
        {
            if(request.getParameter("hresponseid")!=null)
            {
                Connection conn =null;
                PreparedStatement pst =null;
                PreparedStatement pst2 =null;
                try
                {
                    conn = SWBUtils.DB.getDefaultConnection("Recurso Formulario.Reports");
                    pst = conn.prepareStatement("" +
                            "Delete from sr_answer where responseid = ? and idtm=? ");
                    pst.setLong(1,Long.parseLong(request.getParameter("hresponseid")));
                    pst.setString(2,idtm);
                    pst.executeUpdate();
                    if(pst!=null)pst.close();
                    pst2 = conn.prepareStatement("" +
                            "Delete from sr_responseuser where responseid = ? and idtm=? ");
                    pst2.setLong(1,Long.parseLong(request.getParameter("hresponseid")));
                    pst2.setString(2,idtm);
                    pst2.executeUpdate();
                    if(pst2!=null)pst2.close();
                    if(conn!=null)conn.close();
                }
                catch(Exception e)
                {
                    log.error("Error while trying to delete records from sr_answer or sr_responseuser, class - Reports, method - doAdmin",e);
                }
                finally
                {
                    pst2=null;
                    pst=null;
                    conn=null;
                }
                
                SWBResourceURL urlrev = paramsRequest.getRenderUrl();
                urlrev.setMode(urlrev.Mode_ADMIN);
                urlrev.setAction("re_revisar");
                
                ret.append("\n<form name=\"forma\" action=\""+urlrev+"\" onsubmit=\"return validar(forma);\">");
                ret.append("\n<input type=\"hidden\" name=\"fecha_ini\" value=\""+request.getParameter("fecha_ini")+"\">");
                ret.append("\n<input type=\"hidden\" name=\"fecha_fin\" value=\""+request.getParameter("fecha_fin")+"\">");
                ret.append("\n<input type=\"hidden\" name=\"surveyid\" value=\""+request.getParameter("surveyid")+"\">");
                ret.append("\n<input type=\"hidden\" name=\"tipo_revisar\" value=\""+request.getParameter("tipo_revisar")+"\">");
                ret.append("\n<input type=\"hidden\" name=\"jsession\" value=\""+request.getSession().getId()+"\">");
                ret.append("\n<input type=\"hidden\" name=\"buscar\" value=\""+request.getParameter("buscar")+"\">");
                ret.append("\n<input type=\"hidden\" name=\"query\" value=\""+request.getParameter("query")+"\">");
                ret.append("\n</form>");
            }
            ret.append("\n<script language=javascript>");
            ret.append("\n forma.submit();");
            ret.append("\n</script>");
        }
        return ret.toString();
    }
}
