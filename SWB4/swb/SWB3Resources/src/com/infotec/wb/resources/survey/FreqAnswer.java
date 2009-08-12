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
import com.infotec.wb.resources.survey.db.RecGroupAnswer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Esta clase proporciona la administracion de las respuestas a las preguntas
 *
 * This class displays the administration part of categories
 *
 * Created by Juan Anonio Fernández Arias
 */

public class FreqAnswer
{

    private static Logger log = SWBUtils.getLogger(FreqAnswer.class);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss");
    Resource base = null;

    /**
     * Constructor
     */
    public FreqAnswer()
    {

    }

    /**
     * Shows the administration of questions category
     * @param base input Resource object
     */
    public void setResourceBase(Resource base)
    {
        this.base = base;
    }

    /**
     * Set resource base with a new value
     * @param request input parameters
     * @param response an answer to the request
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws SWBResourceException an AF Exception
     * @throws IOException an IO Exception
     * @return a string value with html code
     */
    public String doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        String idtm = base.getWebSiteId();
        StringBuffer ret = null;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String pid = null;
        String paramid = null;
        String filter = null;
        String accion = null;
        int ifil = -1;

        User user = paramsRequest.getUser();
        /* xml */
        Document dom = null;
        Document docxml = null;
        Element resource = null;
        Element opcion = null;
        /* xml */

        ret = new StringBuffer();
        paramid = request.getParameter("paramid");
        filter = request.getParameter("hdnfilter");
        accion = paramsRequest.getAction();
        response.setContentType("text/html");

        if (accion.equalsIgnoreCase("agregar_fa"))
        {
            try
            {
                dom = SWBUtils.XML.getNewDocument();
                resource = dom.createElement("resource");
                opcion = dom.createElement("option");
                opcion.appendChild(dom.createTextNode(""));  // aqu� ir�a el texto de la opci�n
                dom.appendChild(resource);
                String strOption=request.getParameter("hdnOption");
                StringTokenizer strToken=new StringTokenizer(strOption,"|");
                int i = 1;
                while(strToken.hasMoreTokens())
                {
                    strOption=strToken.nextToken();
                    String ival = Integer.toString(i);
                    opcion.getFirstChild().setNodeValue(strOption);
                    opcion.setAttribute("id",ival);
                    Element newOp = (Element)opcion.cloneNode(true);
                    resource.appendChild(newOp);
                    i++;
                }
                String strxml= null;
                strxml = new String(SWBUtils.XML.domToXml(dom));
                RecFreqAnswer objFreq=new RecFreqAnswer();
                objFreq.setIdtm(idtm);
                objFreq.setTitle(request.getParameter("txtTitulo"));
                objFreq.setStringxml(strxml);
                objFreq.setIsreuse(1);
                objFreq.setGroupaid(Integer.parseInt(paramid));    // Cambiar por parametro *******************
                if(objFreq.create())
                {
                    if(request.getSession().getAttribute("regreso")!=null)
                    {
                        ret.append("\n<script language=javascript>");
                        ret.append("\n    window.opener.regreso('freqanswerid="+objFreq.getFreqanswerid()+"');");
                        ret.append("\n    window.close();");
                        ret.append("</script>");
                        request.getSession().setAttribute("regreso",null);
                    }
                    else
                    {
                        long freqanswerid = objFreq.getFreqanswerid();

                        SWBResourceURL urlrfa = paramsRequest.getRenderUrl();
                        urlrfa.setMode(SWBResourceURL.Mode_ADMIN);
                        urlrfa.setAction("result_fa");
                        urlrfa.setParameter("id",Long.toString(freqanswerid));
                        urlrfa.setParameter("hdnfilter","0");
                        urlrfa.setParameter("paramid",paramid);
                        ret.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + urlrfa+ "\">");
                    }
                }
                else
                {
                    SWBResourceURL urlrfa = paramsRequest.getRenderUrl();
                    urlrfa.setMode(urlrfa.Mode_ADMIN);
                    urlrfa.setAction("modificar_fa");
                    ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgNoPudoAgregarRegistroCatalogo")  +" <a href=\""+urlrfa+"\">"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgRegresar")  +"</a>");
                }
            }
            catch(Exception e)
            {
                log.error("Error while trying to generate an xml document, class - FreqAnswer, method - doAdmin",e);
            }
        }
        /*Esta parte actualiza la informaci�n*/
        if (accion.equalsIgnoreCase("update_fa"))
        {
            pid = null;
            pid = request.getParameter("id");
            //System.out.println(pid+" - "+accion);
            if (pid!=null)
            {
                try
                {
                    dom = SWBUtils.XML.getNewDocument();
                    resource = dom.createElement("resource");
                    opcion = dom.createElement("option");
                    opcion.appendChild(dom.createTextNode(""));  // aqu� ir�a el texto de la opci�n
                    dom.appendChild(resource);
                    String strOption=request.getParameter("hdnOption");
                    String strReuse="1";
                    if(request.getParameter("chkReuse") == null) strReuse="0";
                    StringTokenizer strToken=new StringTokenizer(strOption,"|");
                    int i = 1;
                    while(strToken.hasMoreTokens())
                    {
                        strOption=strToken.nextToken();
                        String ival = Integer.toString(i);
                        opcion.getFirstChild().setNodeValue(strOption);
                        opcion.setAttribute("id",ival);
                        Element newOp = (Element)opcion.cloneNode(true);
                        resource.appendChild(newOp);
                        i++;
                    }
                    String strxml= null;
                    strxml = new String(SWBUtils.XML.domToXml(dom));

                    RecFreqAnswer newFreq=new RecFreqAnswer();
                    newFreq.setIdtm(idtm);
                    newFreq.setTitle(request.getParameter("txtTitulo"));
                    newFreq.setStringxml(strxml);
                    newFreq.setGroupaid(Integer.parseInt(paramid));
                    newFreq.setIsreuse(Integer.parseInt(strReuse));

                    RecFreqAnswer objFreq=new RecFreqAnswer();
                    objFreq.setFreqanswerid(Long.parseLong(pid));
                    objFreq.setIdtm(idtm);
                    objFreq.load();

                    long tempoSetid=objFreq.getFreqanswerid();
                    boolean igual=true;
                    if(objFreq.getIsreuse()!= newFreq.getIsreuse())
                    {
                        objFreq.setIsreuse(newFreq.getIsreuse());
                    }
                    if(!objFreq.getStringxml().equals(newFreq.getStringxml())) igual = false;

                    if(!igual)
                    {
                        // la informaci�n del set fue cambiada se revisa si s�lo est� asociada a esta pregunta o a varias
                        //Revisar que s�lo esta asociada a esta pregunta

                        try
                        {
                            conn = SWBUtils.DB.getDefaultConnection();
                            String strQuery = "select questionid from sr_question where freqanswerid = ? and idtm=?";
                            st = conn.prepareStatement(strQuery);
                            st.setLong(1,Long.parseLong(pid));
                            st.setString(2,idtm);
                            if(request.getParameter("questionid")!=null)
                            {
                                strQuery = "select questionid from sr_question where freqanswerid = ? and idtm=? and questionid <> ? ";
                                st.setLong(3,Long.parseLong(request.getParameter("questionid")));
                            }

                            rs = st.executeQuery();

                            int num = 0;
                            while(rs.next())
                            {
                                num++;
                            }

                            if (num>1)
                            {
                                //duplicar set
                                if(!newFreq.create())
                                    tempoSetid=newFreq.getFreqanswerid();
                            }
                            else
                            {
                                //actualizar set
                                objFreq.setTitle(request.getParameter("txtTitulo"));
                                objFreq.setStringxml(strxml);
                                objFreq.setGroupaid(Integer.parseInt(paramid));
                                objFreq.setIsreuse(Integer.parseInt(strReuse));
                                objFreq.update();
                            }
                            if(rs != null) rs.close();
                            if(st != null) st.close();
                            if(conn != null) conn.close();
                        }
                        catch(Exception e)
                        {
                            log.error("Error while trying to load records from sr_question, class - FreqAnswer, method - doAdmin",e);
                        }
                        finally
                        {
                            rs = null;
                            st = null;
                            conn = null;
                        }
                    }
                    else
                    {
                        objFreq.setTitle(request.getParameter("txtTitulo"));
                        objFreq.setGroupaid(Integer.parseInt(paramid));
                        objFreq.setStringxml(strxml);
                        objFreq.update();
                    }
                    if(request.getSession().getAttribute("regreso")!=null)
                    {
                        ret.append("\n<script language=javascript>");
                        ret.append("\n    window.opener.regreso('freqanswerid="+tempoSetid+"');");
                        ret.append("\n    window.close();");
                        ret.append("</script>");
                        request.getSession().setAttribute("regreso",null);
                    }
                    else
                    {
                        SWBResourceURL urlefa = paramsRequest.getRenderUrl();
                        urlefa.setAction("result_fa");
                        urlefa.setParameter("id",Long.toString(tempoSetid));
                        urlefa.setParameter("hdnfilter","0");
                        urlefa.setParameter("paramid",paramid);
                        ret.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + urlefa + "\">");
                    }
                }
                catch(Exception e)
                {
                    log.error("Error while trying to update answers' set, class - FreqAnswer, method - doAdmin",e);
                }
            }
        }

        if (accion.equalsIgnoreCase("eliminar_fa"))
        {
            pid = null;
            pid = request.getParameter("id");
            try
            {
                if(pid != null)
                {
                    conn = SWBUtils.DB.getDefaultConnection();
                    st = conn.prepareStatement("select questionid from sr_question where freqanswerid = ? and idtm=?" ); //+ pid
                    st.setLong(1,Long.parseLong(pid));
                    st.setString(2,idtm);
                    rs = st.executeQuery();
                    if (rs.next())
                    {
                        ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgNoBorrarConjuntoRespuestasExistenPreguntasAsociadas")  +
                        "<br><br> "+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgExistenRegistrosHaciendoReferencia") );
                    }
                    else
                    {
                        RecFreqAnswer objFreq=new RecFreqAnswer();
                        objFreq.setFreqanswerid(Long.parseLong(pid));
                        objFreq.setIdtm(idtm);
                        objFreq.remove();
                        //ret.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + paramsRequest.getRenderUrl() + "/edit_fa?paramid=" + paramid + "\">");
                        accion="edit_fa";
                    }
                    if(st != null) st.close();
                    if(rs != null) rs.close();
                    if(conn != null) conn.close();
                }
            }
            catch(Exception e)
            {
                log.error("Error while trying to delete records from sr_freqanswer, class - FreqAnswer, method - doAdmin",e);
            }
            finally
            {
                rs = null;
                st = null;
                conn = null;
            }
        }

        ret.append("\n<link href=\"/swbadmin/css/swb.css\" rel=\"stylesheet\" type=\"text/css\" />");
        ret.append("\n<div class=\"swbform\">");
        if(request.getSession().getAttribute("regreso")==null)
        {
            if (filter != null)
            {
                ifil = Integer.valueOf(filter).intValue();
            }
            if(ifil != 0)
            {
                SWBResourceURL urlefa = paramsRequest.getRenderUrl();
                urlefa.setAction("edit_fa");
                
                ret.append("\n<fieldset>");
                ret.append("\n<legend>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgSelCategoriaRespuestas")  +"</legend>");
                ret.append("\n<form action=\""+urlefa+"\" action=\"GET\">");
                ret.append("\n<table border=0 cellpadding=2 cellspacing=0 width=100%>");
                ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgCategoria")  +":</td>");
                ret.append("\n<td  colspan=2><select name=\"paramid\">");
                if(paramid == null)
                {
                    ret.append("\n<option value=\"0\" selected> --- "+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgCategoria")  +" --- </option>");
                }
                try
                {
                    conn = SWBUtils.DB.getDefaultConnection();
                    st = conn.prepareStatement("select groupaid, title from sr_groupanswer where idtm=?");
                    st.setString(1,idtm);
                    rs = st.executeQuery();
                    while(rs.next())
                    {
                        long id = rs.getLong("groupaid");
                        if(id!=1)
                        {
                            String titulo = rs.getString("title");
                            ret.append("\n<option value=\""+id+"\"");
                            String lid = Long.toString(id);
                            if (lid.equals(paramid))
                            {
                                ret.append(" selected ");
                            }
                            ret.append(">"+titulo+"</option>");
                        }
                    }
                    if(rs != null) rs.close();
                    if(st != null) st.close();
                    if(conn != null) conn.close();
                }
                catch(Exception e)
                {
                    log.error("Error while trying to load records from sr_groupanswer, class - FreqAnswer, method - doAdmin",e);
                }
                finally
                {
                    rs = null;
                    st = null;
                    conn = null;
                }
                ret.append("\n</select>");
                ret.append("\n&nbsp;<input type=\"submit\" value=\""+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_btnContinuar")  +"\" name=\"Continue\" ></td></tr>");
                ret.append("\n</table></form>");
                ret.append("\n</fieldset>");

                if(paramid != null)
                {
                    ret.append("\n<fieldset>");
                    ret.append("\n<legend>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgListadoConjuntoRespuestas")  +"</legend>");
                    ret.append("\n<table  border=0 cellpadding=2 cellspacing=0 width=100%>");
                    ret.append("\n<tr><th>"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgAction") +"</th>");
                    ret.append("\n<th>Id</th>");
                    ret.append("\n<th>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgTitulo")  +"</th>");
                    ret.append("\n<th>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgReutilizable")  +"</th>");
                    ret.append("\n<th>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgCreado")  +"</th>");
                    ret.append("\n<th>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgActualizado")  +"</th>");
                    ret.append("\n</tr>");

                    try
                    {
                        String reuse=null;
                        conn = SWBUtils.DB.getDefaultConnection();
                        st = conn.prepareStatement("select * from sr_freqanswer where groupaid=? and idtm=?"); // + paramid
                        st.setInt(1, Integer.parseInt(paramid));
                        st.setString(2,idtm);
                        rs = st.executeQuery();
                        String rowColor="";
                        boolean cambiaColor = true;
                        while(rs.next())
                        {
                            long id = rs.getLong("freqanswerid");
                            String titulo = rs.getString("title");
                            int ireuse = rs.getInt("isreuse");
                            Timestamp creado = rs.getTimestamp("created");
                            Timestamp actualizado = rs.getTimestamp("lastupdate");
                            if(ireuse == 1)
                            {
                                reuse=paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgSi")  ;
                            }
                            else
                            {
                                reuse=paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgNo")  ;
                            }
                            rowColor="bgcolor=\"#EFEDEC\"";
                            if(!cambiaColor) rowColor="";
                            cambiaColor = !(cambiaColor);
                            ret.append("\n<tr "+rowColor+">");
                            ret.append("\n<td width=\"70\" >");
                            SWBResourceURL urldel = paramsRequest.getRenderUrl();
                            urldel.setAction("eliminar_fa");
                            urldel.setMode(SWBResourceURL.Mode_ADMIN);
                            urldel.setParameter("id",Long.toString(id));
                            urldel.setParameter("paramid",paramid);
                            SWBResourceURL urledit = paramsRequest.getRenderUrl();
                            urledit.setAction("modificar_fa");
                            urledit.setMode(SWBResourceURL.Mode_ADMIN);
                            urledit.setParameter("id",Long.toString(id));
                            urledit.setParameter("hdnfilter","0");
                            ret.append("\n<a href=\""+urldel+"\" onclick=\"if(confirm('"+paramsRequest.getLocaleString("usrmsg_ShureEraseAnswerSet")+"?')){ return (true);} else { return (false);}\" ><img src=\"/swbadmin/images/delete.gif\" border=0></a>");
                            ret.append("<a href=\""+urledit+"\" ><img src=\"/swbadmin/icons/editar_1.gif\" border=0></a></td>");
                            //                            ret.append("\n<a href=\""+urldel + "\">"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgEliminar")  +"</a>");
                            //                            ret.append("\n<a href=\""+urledit+"\">" + id + "</a>");
                            ret.append("\n<td width=30 >" + id + "</td>");
                            ret.append("\n<td >" + titulo + "</td>");
                            ret.append("\n<td >" + reuse + "</td>");
                            ret.append("\n<td >" + sdf.format(creado) + "</td>");
                            ret.append("\n<td >" + sdf.format(actualizado) + "</td></tr>");

                        }
                        if(rs != null) rs.close();
                        if(st != null) st.close();
                        if(conn != null) conn.close();
                    }
                    catch(Exception e)
                    {
                        log.error("Error while trying to load records from sr_freqanswer, class - FreqAnswer, method - doAdmin",e);
                    }
                    finally
                    {
                        rs = null;
                        st = null;
                        conn = null;
                    }
                    ret.append("\n</table>");
                    ret.append("\n</fieldset>");
                    ret.append("\n<fieldset>");
                    SWBResourceURL urlmfa = paramsRequest.getRenderUrl();
                    urlmfa.setAction("modificar_fa");
                    ret.append("\n<form action=\""+urlmfa+"\" action=\"GET\">");
                    ret.append("\n<input type=\"hidden\" name=\"hdnfilter\" value=\"0\">");
                    ret.append("\n<input type=\"hidden\" name=\"paramid\" value=\"" + paramid + "\">");
                    ret.append("\n<button type=\"submit\" name=\"add\" >"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_btnNuevo")  +"</button>");
                    ret.append("\n</form>");
                    ret.append("\n</fieldset>");
                    
                }
                
                ret.append("\n</div>");
            }

        }


        /*Esta parte edita la informaci�n del control*/
        if (accion.equalsIgnoreCase("modificar_fa"))
        {
            pid = null;
            pid = request.getParameter("id");

            if(pid != null)
            {
                try
                {
                    RecFreqAnswer objFreq=new RecFreqAnswer();
                    objFreq.setFreqanswerid(Long.parseLong(pid));
                    objFreq.setIdtm(idtm);
                    objFreq.load();
                    String strxml = null;
                    strxml = objFreq.getStringxml();
                    int igroup = objFreq.getGroupaid();
                    paramid = Integer.toString(igroup);
                    SWBResourceURL urlu = paramsRequest.getRenderUrl();
                    urlu.setAction("update_fa");
                    urlu.setMode(SWBResourceURL.Mode_ADMIN);
                    ret.append("\n<div class=\"swbform\">");
                    ret.append("\n<fieldset>");
                    ret.append("\n<legend>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgIntroduzcaInfoRequeridaRespuesta")  +"</legend>");
                    ret.append("\n<form action=\""+urlu+"\" action=\"GET\"><input type=\"hidden\" name=\"hdnfilter\" value=\"0\">");
                    ret.append("\n<table  border=0 cellpadding=2 cellspacing=0 width=100%>");
                    
                    ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgTitulo")  +":</td>");
                    ret.append("\n<td><input type=\"text\" name=\"txtTitulo\" size=\"50\" value=\""+ objFreq.getTitle() +"\" maxlength=255></td></tr>");
                    ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgCategoria")  +":</td>");
                    ret.append("\n<td><select name=\"paramid\">");
                    try
                    {
                        conn = SWBUtils.DB.getDefaultConnection();
                        st = conn.prepareStatement("select groupaid, title from sr_groupanswer where idtm=?");
                        st.setString(1,idtm);
                        rs=st.executeQuery();
                        while(rs.next())
                        {
                            long id = rs.getLong("groupaid");
                            String titulo = rs.getString("title");
                            if(id!=1)
                            {
                                ret.append("\n<option value=\""+id+"\"");
                                String lid = Long.toString(id);
                                String strgroup=Integer.toString(igroup);
                                if (lid.equals(strgroup))
                                {
                                    ret.append(" selected ");
                                }
                                ret.append(">"+titulo+"</option>");
                            }
                        }
                        if(rs != null) rs.close();
                        if(st != null) st.close();
                        if(conn != null) conn.close();
                    }
                    catch(Exception e)
                    {
                        log.error("Error while trying to load records from sr_groupanswer, class - FreqAnswer, method - doAdmin",e);
                    }
                    finally
                    {
                        rs = null;
                        st = null;
                        conn = null;
                    }
                    ret.append("\n</select></td></tr>");
                    ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgReutilizable")  +":</td>");
                    ret.append("\n<td ><input type=\"checkbox\" name=\"chkReuse\" value=\"1\"");
                    if (objFreq.getIsreuse() == 0)
                    {
                        ret.append("></td></tr>");
                    }
                    else
                    {
                        ret.append(" checked></td></tr>");
                    }
                    ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgOpcion")  +":</td>");
                    ret.append("\n<td><textarea rows=5 cols=50 id=\"txtOption\" name=\"txtOption\"></textarea>");
                    ret.append("\n<input type=\"hidden\" name=\"hdnOption\"  value=\"\">");
                    ret.append("\n<input type=\"hidden\" name=\"id\"  value=\"" + pid + "\">");
                    ret.append("\n</td></tr>");
                    ret.append("\n<tr><td  align=center colspan=2>");
                    ret.append("\n<input type=\"button\" name=\"btnAdd\" value=\""+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_btnAgregar")  +"\" onClick=\"jsAdd(this.form.selOption, this.form.txtOption)\" >");
                    ret.append("\n&nbsp;&nbsp;<input type=\"button\" name=\"btnEdit\" value=\""+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_btnGuardarCambios")  +"\" onClick=\"jsUpdate(this.form.selOption, this.form.txtOption)\" >");
                    ret.append("\n&nbsp;&nbsp;<input type=\"button\" name=\"btnDel\" value=\""+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_btnEliminar")  +"\"  onClick=\"jsDelete(this.form.selOption, this.form.txtOption)\" >");
                    ret.append("\n</td></tr>");
                    ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgOpciones")  +":</td>");
                    ret.append("\n<td ><select name=\"selOption\" size=5 multiple onChange=\"jsEdit(this.form.selOption, this.form.txtOption)\">");
                    if (strxml != null)
                    {
                        docxml = SWBUtils.XML.xmlToDom(strxml);
                        if (docxml!=null)
                        {
                            resource = (Element) docxml.getFirstChild();
                            org.w3c.dom.NodeList nodes = resource.getChildNodes();
                            for (int i = 0; i < nodes.getLength(); i++)
                            {
                                org.w3c.dom.Node node = nodes.item(i);
                                String val = node.getFirstChild().getNodeValue();
                                ret.append("\n<option value=\"" + val + "\">" + val + "</option>");
                            }
                        }
                    }

                    ret.append("\n</select></td></tr>");
                    ret.append("\n</table>");
                    ret.append("\n</fieldset>");
                    ret.append("\n<fieldset>");
                    if(request.getSession().getAttribute("regreso")==null)
                    {
                        ret.append("<button type=\"button\" name=\"btnCan\" onClick=\"jsCancel();\" >"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_btnCancelar")  +"</button>&nbsp;");
                    }else
                    {
                        ret.append("<button type=\"button\" name=\"cancel\" onclick=\"javascript:window.close();\">"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgCerrarVentanaCancelar")  +"</button>&nbsp;"); //window.opener.regreso();
                    }
                    ret.append("&nbsp;<button type=submit name=btnSave onClick=\"if(jsValida(this.form)) return true; else return false;\" >"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_btnGuardarOpciones")  +"</button>");
                    ret.append("\n</fieldset>");
                    ret.append("\n</form>");
                }
                catch(Exception e)
                {
                    log.error("Error while trying to query DBSurvey, class - FreqAnswer, method - doAdmin",e);
                }
                
                ret.append("\n</div>");
            }
            else
            {
                String pgrouptext = "";
                if (paramid != null)
                {
                    RecGroupAnswer objGA = new RecGroupAnswer();
                    objGA.setGroupaid(Integer.parseInt(paramid));
                    objGA.setIdtm(idtm);
                    objGA.load();
                    pgrouptext = objGA.getTitle();
                }
                SWBResourceURL urlafa = paramsRequest.getRenderUrl();
                urlafa.setAction("agregar_fa");
                urlafa.setMode(urlafa.Mode_ADMIN);
                ret.append("\n<form action=\""+urlafa+"\" method=\"GET\"><input type=\"hidden\" name=\"hdnfilter\" value=\"0\">");
                ret.append("\n<table border=0 width=100% cellspacing=0 cellpadding=2>");
                ret.append("\n<tr><td  colspan=2 >"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgIntroduzcaInfoRequeridaRespuesta.")  +"</td></tr>");
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgTitulo")  +":</td>");
                ret.append("\n<td><input type=\"text\" name=\"txtTitulo\" size=\"50\" value=\"\" maxlength=255></td></tr>");
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgCategoria")  +":</td>");
                ret.append("\n<td >" );
                if(request.getSession().getAttribute("regreso")!=null)
                {
                    ret.append("<select name=\"paramid\">");
                    try
                    {
                        conn = SWBUtils.DB.getDefaultConnection();
                        st = conn.prepareStatement("select groupaid, title from sr_groupanswer where idtm=?");
                        st.setString(1,idtm);
                        rs=st.executeQuery();
                        while(rs.next())
                        {
                            long id = rs.getLong("groupaid");
                            if(id!=1)
                            {
                                String titulo = rs.getString("title");
                                ret.append("\n<option value=\""+id+"\"");
                                String lid = Long.toString(id);
                                ret.append(">"+titulo+"</option>");
                            }
                        }
                        if(rs != null) rs.close();
                        if(st != null) st.close();
                        if(conn != null) conn.close();
                    }
                    catch(Exception e)
                    {
                        log.error("Error while trying to load records from sr_groupanswer, class - FreqAnswer, method - doAdmin",e);
                    }
                    finally
                    {
                        rs = null;
                        st = null;
                        conn = null;
                    }
                    ret.append("\n</select></td></tr>");
                }
                else
                {
                    ret.append(pgrouptext);
                    ret.append("<input type=\"hidden\" name=\"paramid\"  value=\"" + paramid + "\">");
                }
                ret.append("</td></tr>");
                //ret.append("\n<tr><td></td><td></td></tr>");
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgOpcion")  +":<input type=\"checkbox\" name=\"chkReuse\" value=\"1\" checked disabled=\"true\" style=\"visibility:hidden\"></td>");
                ret.append("\n<td><textarea rows=5 cols=50 id=\"txtOption\" name=\"txtOption\"></textarea><input type=\"hidden\" name=\"hdnOption\"  value=\"\"></td></tr>");
                ret.append("\n<tr><td colspan=2 align=center><input type=\"button\" name=\"btnAdd\" value=\""+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_btnAgregar")  +"\" onClick=\"jsAdd(this.form.selOption, this.form.txtOption)\" >&nbsp;&nbsp;<input type=\"button\" name=\"btnEdit\" value=\""+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_btnGuardarCambios")  +"\" onClick=\"jsUpdate(this.form.selOption, this.form.txtOption)\" >&nbsp;&nbsp;<input type=\"button\" name=\"btnDel\" value=\""+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_btnEliminar")  +"\"  onClick=\"jsDelete(this.form.selOption, this.form.txtOption)\" ></td></tr>");
                ret.append("\n<tr>");
                ret.append("\n<td   width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgOpciones")  +":</td>");
                ret.append("\n<td ><select name=\"selOption\" size=5 multiple onChange=\"jsEdit(this.form.selOption, this.form.txtOption)\" ></td></tr>");
                ret.append("\n<tr><td colspan=2 align=right ><hr noshade size=1>");
                if(request.getSession().getAttribute("regreso")==null)
                {
                    ret.append("<input type=\"button\" name=\"btnCan\" value=\""+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_btnCancelar")  +"\"  onClick=\"jsCancel()\" >&nbsp;");
                }else
                {
                    ret.append("<input type=\"button\" name=\"cancel\" value=\""+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_btnCerrarVentanaCancelar")  +"\" onclick=\"javascript:window.close();\" >&nbsp;"); //window.opener.regreso();
                }
                ret.append("&nbsp;<input type=submit name=btnSave value=\""+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_btnGuardarOpciones")  +"\" onClick=\"if(jsValida(this.form)) return true; else return false;\" >");
                ret.append("</td></tr>");
                ret.append("\n</table></form>");
            }
            /*C�digo de javascript*/
            ret.append("\n<script>");
            ret.append("\n\nvar swOk=0, optionObj;");
            ret.append("\n\nfunction jsAdd(pInSel, pInTxt)");
            ret.append("\n{");
            ret.append("\n  jsDuplicate(pInSel, pInTxt);");
            ret.append("\n  if(swOk!=1)");
            ret.append("\n  {");
            ret.append("\n      optionObj = new Option(pInTxt.value, pInTxt.value);");
            ret.append("\n      pInSel.options[pInSel.length]=optionObj;");
            ret.append("\n      pInTxt.value='';");
            ret.append("\n      pInTxt.focus();");
            ret.append("\n      pInTxt.select();");
            ret.append("\n  }");
            ret.append("\n\n}");

            ret.append("\n\nfunction jsDuplicate(pInSel, pInTxt)");
            ret.append("\n{");
            ret.append("\n  swOk=0;");
            ret.append("\n  if(pInTxt.value==null || pInTxt.value=='' || pInTxt.value==' ')");
            ret.append("\n  {");
            ret.append("\n      alert('"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgAlertNoAgregarOpcionVacia")  +"');");
            ret.append("\n      swOk=1;");
            ret.append("\n  }");
            ret.append("\n  for(var i=0; i<pInSel.length; i++)");
            ret.append("\n  {");
            ret.append("\n      if(pInSel.options[i].value==pInTxt.value)");
            ret.append("\n      {");
            ret.append("\n          alert('"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgAlertExisteOpcion")  +" '+ pInTxt.value);");
            ret.append("\n          swOk=1;");
            ret.append("\n          break;");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n\n}");

            SWBResourceURL urledfa = paramsRequest.getRenderUrl();
            urledfa.setAction("edit_fa");
            urledfa.setMode(urledfa.Mode_ADMIN);
            urledfa.setParameter("paramid",paramid);

            ret.append("\n\nfunction jsCancel()");
            ret.append("\n{");
            //ret.append("\n  window.document.location.href =\"" +paramsRequest.getRenderUrl()+"/edit_fa?paramid=" + paramid + "\";");
            ret.append("\n  window.document.location.href =\"" +urledfa + "\";");
            ret.append("\n\n}");


            ret.append("\n\nfunction jsEdit(pInSel, pInTxt)");
            ret.append("\n{");
            ret.append("\n  pInTxt.value=pInSel.options[pInSel.selectedIndex].value;");
            ret.append("\n\n}");

            ret.append("\n\nfunction jsUpdate(pInSel, pInTxt)");
            ret.append("\n{");
            ret.append("\n  jsDuplicate(pInSel, pInTxt);");
            ret.append("\n  if(swOk!=1)");
            ret.append("\n  {");
            ret.append("\n      if(confirm('"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgAlertDeseaActualizarOpcion")  +" ' + pInSel.options[pInSel.selectedIndex].value + '?'))");
            ret.append("\n      pInSel.options[pInSel.selectedIndex].value=pInTxt.value;");
            ret.append("\n      pInSel.options[pInSel.selectedIndex].text=pInTxt.value;");
            ret.append("\n  }");
            ret.append("\n\n}");

            ret.append("\n\nfunction jsDelete(pInSel, pInTxt)");
            ret.append("\n{");
            ret.append("\n  var aryEle = new Array();");
            ret.append("\n  if(confirm('"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgAlertEliminarOpcionesSeleccionadas")  +"'))");
            ret.append("\n  {");
            ret.append("\n      pInTxt.value='';");
            ret.append("\n      for(var i=0, j=0; i<pInSel.length; i++)");
            ret.append("\n      {");
            ret.append("\n          if(!pInSel[i].selected)");
            ret.append("\n          {");
            ret.append("\n              aryEle[j]=pInSel.options[i].value;");
            ret.append("\n              j++;");
            ret.append("\n          }");
            ret.append("\n      }");
            ret.append("\n      while(pInSel.length!=0)");
            ret.append("\n      {");
            ret.append("\n          for( i=1;i<=pInSel.length;i++)");
            ret.append("\n          {");
            ret.append("\n              pInSel.options[0]=null;");
            ret.append("\n          }");
            ret.append("\n      }");
            ret.append("\n      for(var i=0; i<aryEle.length; i++)");
            ret.append("\n      {");
            ret.append("\n          optionObj = new Option(aryEle[i], aryEle[i]);");
            ret.append("\n          pInSel.options[pInSel.length]=optionObj;");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n\n}");

            ret.append("\n\nfunction jsValida(pForm)");
            ret.append("\n{");
            ret.append("\n  if(pForm.txtTitulo.value==null || pForm.txtTitulo.value=='' || pForm.txtTitulo.value==' ')");
            ret.append("\n  {");
            ret.append("\n      alert('"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgAlertDefinirTituloConjuntoOpciones")  +"');");
            ret.append("\n      pForm.txtTitulo.focus();");
            ret.append("\n      return false;");
            ret.append("\n  }");
            ret.append("\n  if(pForm.selOption.length < 1)");
            ret.append("\n  {");
            ret.append("\n      alert('"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgAlertDefinirUnaOpcion")  +"');");
            ret.append("\n      pForm.txtOption.focus();");
            ret.append("\n      return false;");
            ret.append("\n  }");
            ret.append("\n  pForm.hdnOption.value='';");
            ret.append("\n  for(var i=0; i<pForm.selOption.length; i++)");
            ret.append("\n  {");
            ret.append("\n      if(i>0) pForm.hdnOption.value+=\"|\";");
            ret.append("\n      pForm.hdnOption.value+=pForm.selOption.options[i].value;");
            ret.append("\n  }");
            //ret.append("\n      alert(pForm.hdnOption.value);");
            ret.append("\n  return true;");
            ret.append("\n\n}");

            ret.append("\n</script>");
        }



        if (accion.equalsIgnoreCase("result_fa"))
        {
            pid = null;
            pid = request.getParameter("id");
            RecFreqAnswer objFreq=new RecFreqAnswer();
            objFreq.setFreqanswerid(Long.parseLong(pid));
            objFreq.setIdtm(idtm);
            objFreq.load();
            String strxml = null;
            strxml = objFreq.getStringxml();

            if(pid != null)
            {
                try
                {
                    SWBResourceURL urlu = paramsRequest.getRenderUrl();
                    urlu.setAction("update_fa");
                    urlu.setMode(urlu.Mode_ADMIN);
                    ret.append("\n<form action=\""+urlu+"\" action=\"GET\">");
                    ret.append("\n<table border=0 width=100% cellspacing=0 cellpadding=2>");
                    ret.append("\n<tr><td >"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgDespliegueConjuntoOpcionesRespuesta")  +"</td></tr>");
                    ret.append("\n</table>");
                    ret.append("\n<table border=0 width=100% cellspacing=0 cellpadding=2>");
                    ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgTitulo")  +":</td>");
                    ret.append("\n<td  align=left>" + objFreq.getTitle().trim() + "</td></tr>");
                    ret.append("\n<tr><td   width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_msgOpciones")  +":</td><td >");
                    ret.append("<table border=0 width=100% cellpadding=2 cellspacing=0>");
                    if (strxml != null)
                    {
                        docxml = SWBUtils.XML.xmlToDom(strxml);
                        if (docxml!=null)
                        {
                            resource = (Element) docxml.getFirstChild();
                            org.w3c.dom.NodeList nodes = resource.getChildNodes();
                            for (int i = 0; i < nodes.getLength(); i++)
                            {
                                org.w3c.dom.Node node = nodes.item(i);
                                String val = node.getFirstChild().getNodeValue();
                                String att = node.getAttributes().getNamedItem("id").getNodeValue();
                                ret.append("\n<tr><td  align=left>"+att+")&nbsp;" + val + "</td></tr>");
                            }
                        }
                    }
                    ret.append("\n</table></td></tr>");
                    ret.append("\n<tr><td  align=right colspan=2><hr noshade size=1>");
                    ret.append("\n<input type=\"button\" name=btnContinue value=\""+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_btnContinuar")  +"\" onClick=\"jsContinue()\" >&nbsp;");
                    ret.append("\n<input type=\"button\" name=\"btnBack\" value=\""+paramsRequest.getLocaleString("usrmsg_FreqAnswer_getAdmHtml_btnRegresar")  +"\"  onClick=\"jsBack()\" ></td></tr>");
                    ret.append("\n</table></form>");

                    /*C�digo de javascript*/
                    ret.append("\n<script>");
                    ret.append("\n\nfunction jsBack()");
                    ret.append("\n{");
                    SWBResourceURL urlmfa = paramsRequest.getRenderUrl();
                    urlmfa.setAction("modificar_fa");
                    urlmfa.setMode(urlmfa.Mode_ADMIN);
                    urlmfa.setParameter("id",pid);
                    urlmfa.setParameter("hdnfilter","0");
                    urlmfa.setParameter("paramid",paramid);
                    //ret.append("\n  window.document.location.href =\"" +paramsRequest.getRenderUrl()+"/modificar_fa?id=" + pid +"&hdnfilter=0&paramid=" + paramid + "\";");
                    ret.append("\n  window.document.location.href =\"" +urlmfa + "\";");
                    ret.append("\n\n}");
                    ret.append("\n\nfunction jsContinue()");
                    ret.append("\n{");
                    SWBResourceURL urlefa = paramsRequest.getRenderUrl();
                    urlefa.setAction("edit_fa");
                    urlefa.setMode(urlefa.Mode_ADMIN);
                    urlefa.setParameter("paramid",paramid);
                    //ret.append("\n  window.document.location.href =\"" +paramsRequest.getRenderUrl()+"/edit_fa?paramid=" + paramid + "\";");
                    ret.append("\n  window.document.location.href =\"" +urlefa+ "\";");
                    ret.append("\n\n}");
                    ret.append("\n</script>");
                }
                catch(Exception e)
                {
                    log.error("Error while trying to update answers' set, class - FreqAnswer, method - doAdmin",e);
                }
            }
        }

        return ret.toString();
    }
}
