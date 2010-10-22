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
import com.infotec.wb.resources.survey.db.RecSurvey;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

/**
 * Created by Juan Antonio Fernández Arias
 */
public class GeneralReport
{
    private static Logger log = SWBUtils.getLogger(GeneralReport.class);
    
    Resource base = null;
    
    public GeneralReport()
    {
        
    }
    
    public void setResourceBase(Resource base)
    {
        this.base = base;
    }
    
    public String doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        String idtm=base.getWebSiteId();
        StringBuffer ret = new StringBuffer();
        HashMap hm = new HashMap();
        HashMap hmfaid = new HashMap();
        HashMap hmfaidxml = new HashMap();
        HashMap hmres = null;
        Connection con =null;
        PreparedStatement st =null;
        PreparedStatement pstfa =null;
        ResultSet rsfa = null;
        ResultSet rs =null;
        PreparedStatement pst =null;
        ResultSet rspst =null;
        String survey = null;
        Locale local = new Locale(paramsRequest.getUser().getLanguage());

        /* xml */
        Document doc1 = null;
        Document doc12 = null;
        Element resource = null;
        Element resource2 = null;
        NodeList nodes = null;
        NodeList nodes2 = null;
        /* xml */
        
        //System.out.println("par�metro surveyid: "+request.getParameter("surveyid"));
        
        try
        {
            con = SWBUtils.DB.getDefaultConnection();
            long encuesta = Long.parseLong(request.getParameter("surveyid"));
            String tipo_revisar = request.getParameter("tipo_revisar");
            String strSql = new String("select sr_question.*, sr_orderquestion.ordernum, sr_orderquestion.subjectid from sr_question, sr_orderquestion where sr_question.questionid = sr_orderquestion.questionid and sr_orderquestion.surveyid = ? and sr_question.idtm=sr_orderquestion.idtm and sr_question.idtm=? order by sr_orderquestion.subjectid, sr_orderquestion.ordernum");
            st = con.prepareStatement(strSql);
            st.setLong(1,encuesta);
            st.setString(2,idtm);
            rs = st.executeQuery();
            RecSurvey obj=new RecSurvey();
            obj.setIdtm(idtm);
            obj.setSurveyID(encuesta);
            obj.load();
            
            survey = base.getTitle();

            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<fieldset>");
            ret.append("<table cellspacing=1 cellpadding=1 border=1>");
            ret.append("	  <tr>");
            ret.append("	    <td colspan=3><b><font color=#003399>"+paramsRequest.getLocaleString("usrmsg_ReporteGeneral_getAdmHtml_msgReporteGeneraFormulario")+":</font></b></td>");
            ret.append("	    <td><b><font color=#0000ff>"+survey+"</font></b></td>");
            ret.append("	  </tr>");
            ret.append("	  <tr><td><b><font color=#0000ff>"+paramsRequest.getLocaleString("usrmsg_ReporteGeneral_getAdmHtml_msgUsuario")+"</font></b></td>");
            int contador = 0;
            while (rs.next())
            {
                String question = SWBUtils.IO.readInputStream(rs.getAsciiStream("question"));
                //rs.getString("question");
                ret.append("	    <td aling=center><b><font color=#0000ff>"+question+"</font></b></td>");
                contador++;
                String s_freqanswerid = rs.getString("freqanswerid");
                hm.put(Integer.toString(contador),rs.getString("questionid"));
                hmfaid.put(rs.getString("questionid"),s_freqanswerid);
                
                if(!hmfaidxml.containsKey(s_freqanswerid))
                {
                    RecFreqAnswer objFreq = new RecFreqAnswer();
                    objFreq.setIdtm(idtm);
                    objFreq.setFreqanswerid(Long.parseLong(s_freqanswerid));
                    objFreq.load();
                    hmfaidxml.put(Long.toString(objFreq.getFreqanswerid()),objFreq.getStringxml());
                    objFreq = null;
                }
            }
            if(obj.getTypeID()==2)    ret.append("	    <td aling=center><font color=#0000ff>"+paramsRequest.getLocaleString("usrmsg_ReporteGeneral_getAdmHtml_msgCalificacion")+"</font></strong></td>");
            
            if(rs!=null)rs.close();
            if(st!=null)st.close();
            if(con!=null)con.close();
            ret.append("	  </tr>");
            
            con = SWBUtils.DB.getDefaultConnection();
            int i_review = -1;
            
            strSql = new String("select * from sr_responseuser where sr_responseuser.surveyid = ? and idtm=?  ");
            
            if (tipo_revisar.equals("revisados"))
            { 
                i_review=1;
                strSql += "and sr_responseuser.review = ? ";
            }
            else if(tipo_revisar.equals("no revisados"))
            {
                i_review=0;
                strSql += "and sr_responseuser.review = ? ";
            }
            
            java.sql.Timestamp tempIni = new java.sql.Timestamp(new java.util.Date().getTime());
            java.sql.Timestamp tempFin = tempIni;
            
            if(request.getParameter("fecha_ini").trim().length()>0)
            {
                String fecha_ini = request.getParameter("fecha_ini");
                //System.out.println("Fecha inicial:"+fecha_ini);
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
                strSql =strSql+(" and sr_responseuser.created >= ? ");
            }
            
            if(request.getParameter("fecha_fin").trim().length()>0)
            {
                String fecha_fin = request.getParameter("fecha_fin");
                //System.out.println("Fecha final:"+fecha_fin);
                int year_f;
                int month_f;
                int day_f;

                year_f= Integer.parseInt(fecha_fin.substring(0,fecha_fin.indexOf("-")));
                month_f=Integer.parseInt(fecha_fin.substring(fecha_fin.indexOf("-")+1,fecha_fin.lastIndexOf("-")));
                day_f=Integer.parseInt(fecha_fin.substring(fecha_fin.lastIndexOf("-")+1,fecha_fin.length()));

                Calendar calendariof = Calendar.getInstance();
                calendariof.set(year_f,month_f-1,day_f,23,59,59);
                java.sql.Date fecha_fin_D = new java.sql.Date(calendariof.getTimeInMillis());
                tempFin  =   new java.sql.Timestamp(fecha_fin_D.getTime()) ;
                strSql =strSql+(" and sr_responseuser.created <= ? ");
            }
            int i_param=3;
            st = con.prepareStatement(strSql);
            st.setLong(1,encuesta);
            st.setString(2,idtm);
            if(i_review!=-1)
            {
                st.setInt(i_param,i_review);
                i_param++;
            }
            if(request.getParameter("fecha_ini").trim().length()>0)
            {
             st.setTimestamp(i_param,tempIni);
             i_param++;
            }
            if(request.getParameter("fecha_fin").trim().length()>0)
            {
                st.setTimestamp(i_param,tempFin);
            }

//            System.out.println("strSql:"+strSql);
//
//            System.out.println(" surveyid: "+encuesta);
//            System.out.println("     idtm: "+idtm);
//            System.out.println("   review:"+i_review);
//            System.out.println("fecha ini:"+tempIni);
//            System.out.println("fecha fin:"+tempFin);
//            System.out.println("  revisar:"+tipo_revisar);

            rs = st.executeQuery();
            
            while(rs.next())
            {
                
                long responseid = rs.getLong("responseid");
                String email = rs.getString("wbuser");
                User ruser =null;
                UserRepository usrRep = paramsRequest.getUser().getUserRepository();
                if(email!=null&&!email.equals("Anonimo")&&!email.startsWith("0_")&&!email.startsWith("0"))
                {
                    ruser = usrRep.getUser(email);
                    if(ruser!=null) email = ruser.getName();
                    else email=null;
                }
                if(email==null||email.startsWith("0_")||email.startsWith("0")) email=paramsRequest.getLocaleString("usrmsg_MainSurvey_setResourceBase_msgAnonimo");
                int freqanswerid =0;
                ret.append("	  <tr>");
                ret.append("	    <td>"+email+"</td>");
                pst = con.prepareStatement("select * from sr_answer where responseid = ? and idtm=? ");
                pst.setLong(1,responseid);
                pst.setString(2,idtm);
                rspst = pst.executeQuery();
                hmres = new HashMap();
                while(rspst.next()) hmres.put(rspst.getString("questionid"),SWBUtils.IO.readInputStream(rspst.getAsciiStream("stringxml")));
                if(rspst!=null)rspst.close();
                if(pst!=null) pst.close();
                int x=0;
                for(x=1;x<=hm.size();x++)
                {
                    if(hmres.containsKey(hm.get(Integer.toString(x))) )
                    {
                        freqanswerid = Integer.parseInt((String)hmfaid.get(hm.get(Integer.toString(x))));
                        String stringXML = (String)hmres.get(hm.get(Integer.toString(x)));
                        String answerxml = (String)hmfaidxml.get(Integer.toString(freqanswerid));
                        
                        //dbf=DocumentBuilderFactory.newInstance();
                        //db=dbf.newDocumentBuilder();
                        doc1 = SWBUtils.XML.getNewDocument();
                        String respuesta = "<b>"+paramsRequest.getLocaleString("usrmsg_ReporteGeneral_getAdmHtml_msgNoContesto")+"</b>";
                        if(null!=stringXML&&stringXML.trim().length()>0)
                        {
                            doc1 = SWBUtils.XML.xmlToDom(stringXML);
                            
                            resource = (Element) doc1.getFirstChild();
                            nodes = resource.getChildNodes();
                            //org.w3c.dom.Document doc12 = db.newDocument();
                            doc12 = SWBUtils.XML.xmlToDom(answerxml);
                            //if (doc12==null)WBUtils.getInstance().log("Dom nulo\n",true);
                            resource2 = (Element) doc12.getFirstChild();
                            nodes2 = resource2.getChildNodes();
                            
                            
                            if(nodes.getLength()>0)
                            {
                                ret.append("        <td><table>");
                                for (int i = 0; i < nodes.getLength(); i++)
                                {
                                    org.w3c.dom.Node node = nodes.item(i);
                                    if(node.getNodeName().equals("answer"))
                                    {
                                        respuesta = node.getFirstChild().getNodeValue();
                                        if(freqanswerid != 1)
                                        {
                                            for(int j=0; j<nodes2.getLength();j++)
                                            {
                                                Node node2 = nodes2.item(j);
                                                if(node2.getNodeName().equals("option"))
                                                {
                                                    if (node2.getAttributes().getNamedItem("id").getNodeValue().equals(respuesta))
                                                    {
                                                        respuesta = node2.getFirstChild().getNodeValue();
                                                        j=nodes2.getLength();
                                                    }
                                                    else respuesta = node.getFirstChild().getNodeValue();
                                                }
                                                else  respuesta = node.getFirstChild().getNodeValue();
                                            }
                                        }
                                    }
                                    ret.append("	    <tr><td>"+respuesta+"</td></tr>");
                                }
                                ret.append("        </table></td>	");
                            }
                            else  ret.append("<td>"+respuesta+"</td>");
                        }
                        else  ret.append("<td>"+respuesta+"</td>");
                    }
                    else  ret.append("<td><b>"+paramsRequest.getLocaleString("usrmsg_ReporteGeneral_getAdmHtml_msgNoAplico")+"</b></td>");
                }
                if(obj.getTypeID()==2)
                {
                    String color="#000000";    // color negro
                    if(rs.getFloat("score")<obj.getMinScore()) color="#FF0000";  // color rojo
                    ret.append("	    <td aling=center><b><font color=\""+color+"\">"+rs.getString("score")+"</font></b></td>");
                }
                ret.append("	  </tr>	");
//                ret.append("\n</table>");
//                ret.append("\n</fieldset>");
//                ret.append("\n</div>");
            }
            if(rs!=null)rs.close();
            if(st!=null)st.close();
            if(con!=null)con.close();
            ret.append("\n</table>");
            ret.append("\n</fieldset>");
            ret.append("\n</div>");
        }
        catch(Exception e)
        {
            log.error("Error while trying to load records from sr_orderquestion, class - GeneralReport, method - doAdmin",e);
        }
        finally
        {
            rsfa=null;
            pstfa=null;
            rspst=null;
            pst=null;
            rs=null;
            st=null;
            con=null;
        }
        //System.out.println("reporte general .... "+ret.toString());
        return ret.toString();
        
    }
}
