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

import com.infotec.wb.resources.survey.db.RecCategory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 * Esta clase proporciona la administracion de las categorias
 *
 * This class displays the administration part of categories
 *
 * Created by Juan Antonio Fernández Arias
 * 
 */
public class Category
{
    private static Logger log = SWBUtils.getLogger(Category.class);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss");
    Resource base = null;

    /**
     * Constructor
     */
    public Category()
    {

    }

    /**
     * Set resource base with a new value
     * @param base input Resource object
     */
    public void setResourceBase(Resource base)
    {
        this.base = base;
    }

    /**
     * Shows the administration of questions category
     * @param request request input parameters
     * @param response an answer to the request
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     * @return a string value with html code
     */
    public String doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        StringBuffer ret = null;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String pid = null;
        String accion = null;

        ret = new StringBuffer();
        accion = paramsRequest.getAction();
        pid = request.getParameter("id");
        response.setContentType("text/html");

        String idtm=request.getParameter("tm");
        if(null==idtm) idtm=paramsRequest.getWebPage().getWebSiteId();
        //System.out.println("regreso: "+request.getParameter("regreso"));

        ret.append("\n<link href=\"/swbadmin/css/swb.css\" rel=\"stylesheet\" type=\"text/css\" />");

        /*Esta parte actualiza la información*/
        if (accion.equalsIgnoreCase("update_gq"))
        {
            pid = null;
            pid = request.getParameter("id");
            if (pid!=null)
            {
                RecCategory objCategory = new RecCategory();
                objCategory.setCategoryid(Integer.parseInt(pid));
                objCategory.setIdtm(idtm);
                objCategory.setDescription(request.getParameter("descripcion"));
                objCategory.setTitle(request.getParameter("titulo"));
                if(objCategory.update())
                {
                    //ret.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + paramsRequest.getRenderUrl() + "/edit_gq\">");
                    accion="edit_gq";
                }
                else
                {
                    SWBResourceURL urlmgq = paramsRequest.getRenderUrl();
                    urlmgq.setAction("modificar_gq");
                    urlmgq.setMode(SWBResourceURL.Mode_ADMIN);
                    ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgNoActualizarRegistroCatalogo")+" <a href=\""+urlmgq+"\">"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgRegresar")+"</a>");
                }
            }
        }

        if (accion.equalsIgnoreCase("eliminar_gq"))
        {
            try
            {
                pid = request.getParameter("id");
                conn = SWBUtils.DB.getDefaultConnection("Recurso Formulario.Category.doAdmin()");
                st = conn.prepareStatement("select categoryid from sr_question where categoryid = ? and idtm=?");
                st.setInt(1,Integer.parseInt(pid));
                st.setString(2,idtm);
                rs = st.executeQuery();
                if (rs.next())
                {
                    ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgNoBorrarRegistro")+"<br><br> "+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgExistenRegistrosReferenciaEste"));
                }
                else
                {
                    RecCategory  objCategory = new RecCategory();
                    objCategory.setCategoryid(Integer.parseInt(pid));
                    objCategory.setIdtm(idtm);
                    objCategory.remove();
                    //ret.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + paramsRequest.getRenderUrl() + "/edit_gq\">");
                    accion="edit_gq";
                }
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(conn != null) conn.close();
            }
            catch(Exception e)
            {
                log.error("Error while trying to delete records from sr_category, class - Category, method - doAdmin",e);
            }
            finally
            {
                rs = null;
                st = null;
                conn = null;
            }
        }

        if (accion.equalsIgnoreCase("agregar_gq"))
        {
            RecCategory objCategory = new RecCategory();
            objCategory.setIdtm(idtm);
            objCategory.setDescription(request.getParameter("descripcion") );
            objCategory.setTitle(request.getParameter("titulo"));
            if(objCategory.create())
            {
                if(request.getSession().getAttribute("regreso")!=null)
                {
                    ret.append("\n<script type=\"javascript\">");
                    ret.append("\n    window.opener.regreso('groupqid="+objCategory.getCategoryid()+"');");
                    ret.append("\n    window.close();");
                    ret.append("</script>");
                    request.getSession().setAttribute("regreso",null);
                }
                else
                {
                    //ret.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + paramsRequest.getRenderUrl() + "/edit_gq\">");
                    accion="edit_gq";
                }
            }
            else
            {
                SWBResourceURL urlmgq = paramsRequest.getRenderUrl();
                urlmgq.setAction("modificar_gq");
                urlmgq.setMode(SWBResourceURL.Mode_ADMIN);
                ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgNoAgregarRegistroCatalogo")+" <a href=\""+urlmgq+"\">"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgRegresar")+"</a>");
            }
        }

        if (request.getSession().getAttribute("regreso")==null)
        {
            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<fieldset>");
            ret.append("\n<table border=0 cellspacing=0 cellpadding=2 width=100%>");
            ret.append("\n<tr><th width=\"70\" >"+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAction")+"</th>");
            ret.append("\n<th width=\"25\" >Id</th>");
            ret.append("\n<th >"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgTitulo")+"</th>");
            ret.append("\n<th >"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgDescripcion")+"</th>");
            ret.append("\n<th >"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgCreacion")+"</th>");
            ret.append("\n</tr>");
            try
            {
                conn = SWBUtils.DB.getDefaultConnection("Recurso Formulario.Category.doAdmin()");
                st = conn.prepareStatement("select * from sr_category where idtm=?");
                st.setString(1,idtm);
                rs = st.executeQuery();
                String rowColor="";
                boolean cambiaColor = true;
                while(rs.next())
                {
                    long id = rs.getLong("categoryid");
                    String titulo = rs.getString("title");
                    String descripcion = rs.getString("description");
                    Timestamp creado = rs.getTimestamp("lastupdate");
                    rowColor="bgcolor=\"#EFEDEC\"";
                    if(!cambiaColor) rowColor="";
                    cambiaColor = !(cambiaColor);
                    ret.append("\n<tr  "+rowColor+">");
                    ret.append("\n<td width=\"70\" >");
                    SWBResourceURL urldel = paramsRequest.getRenderUrl();
                    urldel.setAction("eliminar_gq");
                    urldel.setMode(SWBResourceURL.Mode_ADMIN);
                    urldel.setParameter("id",Long.toString(id));
                    SWBResourceURL urledit = paramsRequest.getRenderUrl();
                    urledit.setAction("modificar_gq");
                    urledit.setMode(SWBResourceURL.Mode_ADMIN);
                    urledit.setParameter("id",Long.toString(id));
                    ret.append("\n<a href=\""+urldel+"\" onclick=\"if(confirm('¿"+"Estás seguro de eliminar esta categoría"+"?')){ return (true);} else { return (false);}\"><img src=\"/swbadmin/images/delete.gif\" border=0 alt=\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgQuitar")+"\"></a>");
                    ret.append("\n<a href=\""+urledit+"\" ><img src=\"/swbadmin/icons/editar_1.gif\" border=0 alt=\"Detail group\nid: "+id+"\"></a>");
                    //                    ret.append("\n<a href=\""+paramsRequest.getRenderUrl()+"/eliminar_gq?id=" + id +"\">"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgEliminar")+"</a>");
                    //                    ret.append("\n<a href=\""+paramsRequest.getRenderUrl()+"/modificar_gq?id=" + id +"\">" + id + "</a>");
                    ret.append("\n</td>");
                    ret.append("\n<td width=\"25\" >"+id+"</td>");
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
                log.error("Error while trying to load records from sr_category, class - Category, method - doAdmin",e);
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
            SWBResourceURL urled = paramsRequest.getRenderUrl();
            urled.setAction("modificar_gq");
            urled.setMode(SWBResourceURL.Mode_ADMIN);
            ret.append("\n<form action=\""+urled+"\" method=\"GET\"><button dojoType=\"dijit.form.Button\" type=\"submit\"  name=\"btnadd\">"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_btnAgregar")+"</button></form>");
            ret.append("\n</fieldset>");
            ret.append("\n</div>");
        }



        /*Esta parte edita la información del control*/
        if (accion.equalsIgnoreCase("modificar_gq"))
        {
            if(pid != null)
            {
                SWBResourceURL urlugq = paramsRequest.getRenderUrl();
                urlugq.setAction("update_gq");
                urlugq.setMode(SWBResourceURL.Mode_ADMIN);


                ret.append("\n<div class=\"swbform\">");
                ret.append("\n<form action=\""+urlugq+"\" action=\"GET\">");
                ret.append("\n<fieldset>");
                ret.append("\n<legend>"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgEdicionRegistro")+"</legend>");
                ret.append("\n<table border=0 cellspacing=0 cellpadding=2 width=100%>");
                
                try
                {
                    RecCategory objCategory = new RecCategory();
                    objCategory.setCategoryid(Integer.parseInt(pid));
                    objCategory.setIdtm(idtm);
                    objCategory.load();
                    ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgTitulo")+":</td>");
                    ret.append("\n<td ><input type=\"text\" name=\"titulo\" value=\""+objCategory.getTitle()+"\" maxlength=50></td></tr>");
                    ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgDescripcion")+":</td>");
                    ret.append("\n<td ><input type=\"text\" name=\"descripcion\" value=\""+objCategory.getDescription()+"\" maxlength=255></td></tr>");
                }
                catch(Exception e)
                {
                    log.error("Error on action modificar_qq, class - Category, method - doAdmin",e);
                }
                ret.append("\n</table>");
                ret.append("\n</fieldset>");
                ret.append("\n<fieldset>");
                ret.append("\n<button dojoType=\"dijit.form.Button\"  type=\"submit\" name=\"btnactualizar\">"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_btnActualizar")+"</button>");
                ret.append("\n<input type=\"hidden\" name=\"id\" value=\"" + pid + "\">");
                ret.append("\n</fieldset>");
                ret.append("\n</form>");
                ret.append("\n</div>");
            }
            else
            {
                SWBResourceURL urlagq = paramsRequest.getRenderUrl();
                urlagq.setAction("agregar_gq");
                urlagq.setMode(SWBResourceURL.Mode_ADMIN);
                ret.append("\n<div class=\"swbform\">");
                ret.append("\n<form action=\""+urlagq+"\" action=\"GET\">");
                ret.append("\n<fieldset>");
                ret.append("\n<legend>"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgNuevaCategoriaPregunta")+"</legend>");
                ret.append("\n<table border=0 cellspacing=0 cellpadding=2 width=100%>");
                ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgTitulo")+":</td>");
                ret.append("\n<td ><input type=\"text\" name=\"titulo\" value=\"\" maxlength=50></td></tr>");
                ret.append("\n<tr><td width=200 align=right >"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_msgDescripcion")+":</td>");
                ret.append("\n<td ><input type=\"text\" name=\"descripcion\" value=\"\" maxlength=255></td></tr>");
                ret.append("\n</table>");
                ret.append("\n</fieldset>");
                ret.append("\n<fieldset>");
                ret.append("\n<button dojoType=\"dijit.form.Button\"  type=\"submit\" name=\"btnagregar\">"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_btnAgregar")+"</button>");
                if(request.getSession().getAttribute("regreso")!=null)
                {
                    ret.append("&nbsp;<button dojoType=\"dijit.form.Button\" type=\"button\" name=\"cancel\" onclick=\"javascript:window.close();\">"+paramsRequest.getLocaleString("usrmsg_GroupQuestion_getAdmHtml_btnCancelar")+"</button>"); //window.opener.regreso();
                }
                ret.append("\n</fieldset>");
                ret.append("\n</form>");
                ret.append("\n</div>");
            }
        }

        return ret.toString();

    }
}
