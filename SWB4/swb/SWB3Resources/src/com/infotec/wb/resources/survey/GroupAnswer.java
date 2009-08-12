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

import com.infotec.wb.resources.survey.db.RecGroupAnswer;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;

import java.sql.*;
import java.text.SimpleDateFormat;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;


/**
 * User: Juan Antonio Fernández Arias
 * 
 */
public class GroupAnswer
{
    private static Logger log = SWBUtils.getLogger(GroupAnswer.class);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss");

    Resource base=null;
    
    public GroupAnswer()
    {}
    
    public void setResourceBase(Resource base)
    {
        this.base = base;
    }
    
    public String doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        String idtm=base.getWebSiteId();
        String accion = paramsRequest.getAction();
        User user = paramsRequest.getUser();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        StringBuffer ret = new StringBuffer();
        String pid;
        SWBResourceURL url = paramsRequest.getRenderUrl();
        url.setAction("edit_ga");

        if (accion.equalsIgnoreCase("eliminar_ga"))
        {
            try
            {
                pid = null;
                pid = request.getParameter("id");
                conn = SWBUtils.DB.getDefaultConnection("Recurso Formulario.GroupAnswer.doAdmin()");
                st = conn.prepareStatement("select groupaid from sr_freqanswer where groupaid = ? and idtm=? ");
                st.setInt(1,Integer.parseInt(pid));
                st.setString(2,idtm);
                rs = st.executeQuery();
                if (rs.next())
                {
                    ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgNoBorrarRegistro")+"<br><br> "+
                    paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgExistenRegistrosHaciendoReferencia"));
                }
                else
                {
                    if(st != null)
                    {
                        st.close();
                        st = null;
                    }
                    st = conn.prepareStatement("delete from sr_groupanswer where groupaid = ? and idtm=? ");
                    st.setInt(1,Integer.parseInt(pid));
                    st.setString(2,idtm);
                    st.executeUpdate();
                    //ret.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + urlega + "\">");                    
                }
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(conn != null) conn.close();
                log.error(paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgEliminoGrupoRespuestasId")+": "+pid);
            }
            catch(Exception e)
            {
                log.error(paramsRequest.getLocaleString("errormsg_GroupAnswer_getAdmHtml_logErrorGroupAnswergetAdmHTMLeliminar_ga"),e);
            }
            finally
            {
                rs = null;
                st = null;
                conn = null;
            }
        }
        
        if (accion.equalsIgnoreCase("agregar_ga"))
        {
            // secci�n de agregar control
            RecGroupAnswer objGAnswer=new RecGroupAnswer();
            objGAnswer.setIdtm(idtm);
            objGAnswer.setDescription(request.getParameter("descripcion") );
            objGAnswer.setTitle(request.getParameter("titulo"));
            if(objGAnswer.create())
            {
                //ret.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + urlega + "\">");
                accion="edit_ga";
            }
            else ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgNoAgregarRegistroCatalogo")+" <a class=link href=\""+url+"\">"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgRegresar")+"</a>");
            log.error(paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgAgregoGrupoRespuestasId")+": "+objGAnswer.getGroupaid());
        }
        
        /*Esta parte actualiza la informaci�n*/
        if (accion.equalsIgnoreCase("update_ga"))
        {
        SWBResourceURL urlmod=paramsRequest.getRenderUrl();
        urlmod.setAction("modificar_ga");
        urlmod.setMode(urlmod.Mode_ADMIN);
            pid = null;
            pid = request.getParameter("id");
            if (pid!=null)
            {
                RecGroupAnswer objGAnswer=new RecGroupAnswer();
                objGAnswer.setIdtm(idtm);
                objGAnswer.setGroupaid(Integer.parseInt(pid));
                objGAnswer.load();
                objGAnswer.setDescription(request.getParameter("descripcion"));
                objGAnswer.setTitle(request.getParameter("titulo"));
                if(objGAnswer.update())
                {
                    //ret.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + urlega + "\">");
                    accion="edit_ga";
                }
                else ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgNoActualizarRegistroCatalogo")+" <a href=\""+urlmod+"\">"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgRegresar")+"</a>");
                log.error(paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgActualizoGrupoRespuestasId")+": "+pid);
            }
        }
        
        
        //if (accion.equalsIgnoreCase("edit_cc")) {
        response.setContentType("text/html");

        ret.append("\n<link href=\"/swbadmin/css/swb.css\" rel=\"stylesheet\" type=\"text/css\" />");
        ret.append("\n<div class=\"swbform\">");
        ret.append("\n<fieldset>");
        ret.append("\n<table border=0 width=100% cellpadding=2 cellspacing=0>");
        //ret.append("\n<tr><td colspan=5 >"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgListadoCategoriaRespuestas")+"</td></tr>");
        ret.append("\n<tr><td colspan=5 >"+"&nbsp;"+"</td></tr>");
        ret.append("\n<tr >");
        ret.append("\n<th width=\"60\">"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgAction")+"</th>");
        ret.append("\n<th width=\"30\">Id</th>");
        ret.append("\n<th>"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgTitulo")+"</th>");
        ret.append("\n<th>"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgDescripcion")+"</th>");
        ret.append("\n<th>"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgCreacion")+"</th>");
        ret.append("\n</tr>");
        try
        {
            conn = SWBUtils.DB.getDefaultConnection();
            st = conn.prepareStatement("select * from sr_groupanswer where idtm=? ");
            st.setString(1,idtm);
            rs = st.executeQuery();
            String rowColor="";
            boolean cambiaColor = true;
            while(rs.next())
            {
                long id = rs.getLong("groupaid");
                String titulo = rs.getString("title");
                String descripcion = rs.getString("description");
                Timestamp creado = rs.getTimestamp("lastupdate");
                SWBResourceURL urlega=paramsRequest.getRenderUrl();
                urlega.setAction("eliminar_ga");
                urlega.setParameter("id",Long.toString(id));
                SWBResourceURL urlmga=paramsRequest.getRenderUrl();
                urlmga.setAction("modificar_ga");
                urlmga.setParameter("id",Long.toString(id));
                rowColor="bgcolor=\"#EFEDEC\"";
                if(!cambiaColor) rowColor="";
                cambiaColor = !(cambiaColor);
                ret.append("\n<tr "+rowColor+">");
                ret.append("\n<td><a href=\""+urlega+"\" onclick=\"if(confirm('"+paramsRequest.getLocaleString("usrmsg_ShureEraseAnswerGroup")+"?')){ return (true);} else { return (false);}\" ><img src=\"/swbadmin/images/delete.gif\" border=0></a>");
                ret.append("\n<a href=\""+urlmga+"\" ><img src=\"/swbadmin/icons/editar_1.gif\" border=0></a></td>");
                ret.append("\n<td >" + id + "</td>");
                ret.append("\n<td >" + titulo + "</td>");
                ret.append("\n<td >" + descripcion + "</td>");
                ret.append("\n<td >" + sdf.format(creado) + "</td>");
                ret.append("\n</tr>");
            }
            if(rs != null) rs.close();
            if(st != null) st.close();
            if(conn != null) conn.close();
        }
        catch(Exception e)
        {
            log.error(paramsRequest.getLocaleString("errormsg_GroupAnswer_getAdmHtml_logErrorGroupAnswergetAdmHTML"),e);
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
        SWBResourceURL urlmga=paramsRequest.getRenderUrl();
        urlmga.setAction("modificar_ga");
        ret.append("\n<form action=\""+urlmga+"\" method=\"GET\"><button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"btnadd\">"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_btnAgregar")+"</button></form>");

        ret.append("\n</fieldset>");

        /*Esta parte edita la informaci�n del control*/
        if (accion.equalsIgnoreCase("modificar_ga"))
        {
            pid = null;
            pid = request.getParameter("id");
            if(pid != null)
            {
                SWBResourceURL urluga=paramsRequest.getRenderUrl();
                urluga.setAction("update_ga");
                urluga.setMode(urluga.Mode_ADMIN);
                ret.append("\n<fieldset>");
                ret.append("\n<legend>"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgEdicionRegistro")+"</legend>");
                ret.append("\n<form action=\""+urluga+"\" action=\"GET\">");
                ret.append("\n<table border=0 width=100% cellpadding=2 cellspacing=0>");
                
                try
                {
                    RecGroupAnswer objGAnswer=new RecGroupAnswer();
                    objGAnswer.setIdtm(idtm);
                    objGAnswer.setGroupaid(Integer.parseInt(pid));
                    objGAnswer.load();
                    ret.append("\n<tr><td>"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgTitulo")+":</td><td ><input type=\"text\" name=\"titulo\" value=\""+objGAnswer.getTitle()+"\" maxlength=50></td></tr>");
                    ret.append("\n<tr><td>"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgDescripcion")+":</td><td ><input type=\"text\" name=\"descripcion\" value=\""+objGAnswer.getDescription()+"\" maxlength=255></td></tr>");
                }
                catch(Exception e)
                {
                    log.error(paramsRequest.getLocaleString("errormsg_GroupAnswer_getAdmHtml_logErrorGroupAnswergetAdmHTMLmodificar_ga"),e);
                }
                ret.append("\n</table>");
                ret.append("\n</fieldset>");
                ret.append("\n<fieldset>");
                ret.append("\n<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"btnactualizar\">"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgActualizar")+"</button>");
                ret.append("\n<input type=\"hidden\" name=\"id\" value=\"" + pid + "\">");
                ret.append("\n</fieldset>");
                ret.append("\n</form>");
            }
            else
            {
                SWBResourceURL urlaga=paramsRequest.getRenderUrl();
                urlaga.setAction("agregar_ga");
                urlaga.setMode(urlaga.Mode_ADMIN);
                ret.append("\n<form action=\""+urlaga+"\" action=\"GET\">");
                ret.append("\n<fieldset>");
                ret.append("\n<legend>"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgAgregarCategoriaRespuestas")+"</legend>");
                ret.append("\n<table border=0 width=100% cellpadding=2 cellspacing=0>");
                try
                {
                    ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgTitulo")+":</td><td ><input type=\"text\" name=\"titulo\" value=\"\" maxlength=50></td></tr>");
                    ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgDescripcion")+":</td><td ><input type=\"text\" name=\"descripcion\" value=\"\" maxlength=255></td></tr>");
                    //ret.append("\n</table>");
                }
                catch(Exception e)
                { log.error(paramsRequest.getLocaleString("errormsg_GroupAnswer_getAdmHtml_logErrorConsultarDBSurvey"),e); }
                ret.append("\n</table>");
                ret.append("\n</fieldset>");
                ret.append("\n<fieldset>");
                ret.append("\n<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"btnagregar\">"+paramsRequest.getLocaleString("usrmsg_GroupAnswer_getAdmHtml_msgAgregar")+"</button>");
                ret.append("\n</fieldset>");
                ret.append("\n</form>");
            }
        }
        
        // termina de generar el c�digo y lo regresa al navegador
        return ret.toString();
    }
    
}
