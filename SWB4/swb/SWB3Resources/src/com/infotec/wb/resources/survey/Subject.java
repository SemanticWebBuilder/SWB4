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

import com.infotec.wb.resources.survey.db.RecSubject;
import java.io.IOException;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 * Created by
 * User: Juan Antonio Fernández Arias
 * 
 */
public class Subject
{
    private static Logger log = SWBUtils.getLogger(Subject.class);
    Resource base=null;
        
    public Subject()
    {
        
    }
    
    public void setResourceBase(Resource base)
    {
        this.base = base;
    }
    
    public String doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        String idtm = base.getWebSiteId();
        StringBuffer ret = new StringBuffer();
        String accion = paramsRequest.getAction();
        SWBResourceURL url = paramsRequest.getRenderUrl();
        User user = paramsRequest.getUser();
        Survey oSur = new Survey();
        ret.append(oSur.getWB3AdminStyle());
        
        SWBResourceURL urlas = paramsRequest.getRenderUrl();
        urlas.setAction("add_s");
        urlas.setMode(SWBResourceURL.Mode_ADMIN);
        
        SWBResourceURL urlss = paramsRequest.getRenderUrl();
        urlss.setAction("select_s");
        urlss.setMode(SWBResourceURL.Mode_ADMIN);
        
        SWBResourceURL urlds = paramsRequest.getRenderUrl();
        urlds.setAction("deleteselect_s");
        urlds.setMode(SWBResourceURL.Mode_ADMIN);
        
        if (accion.equalsIgnoreCase("agregar_s"))
        {
            // secci�n de agregar grupo
            RecSubject objG = new RecSubject();
            objG.setIdtm(idtm);
            //objG.load();
            objG.setDescription(request.getParameter("descripcion") );
            objG.setTitle(request.getParameter("titulo"));
            try
            {
                if(!objG.create()) //ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgAgregoGrupoCatalogo"));
                ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgNoPudoAgregarGrupoCatalogo"));
            }
            catch(Exception e)
            {
                log.error(paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgNoPudoAgregarGrupoCatalogo"));
            }
            if(request.getSession().getAttribute("regreso")!=null)
            {
                ret.append("\n<script language=javascript>");
                ret.append("\n    window.opener.regreso('seccion="+objG.getSubjectid()+"');");
                ret.append("\n    window.close();");
                ret.append("</script>");
                request.getSession().setAttribute("regreso",null);
            }
            log.error(paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgAgregoGrupoId")+": "+objG.getSubjectid());
        }
        
        if (accion.equalsIgnoreCase("updatesection_s"))
        {
            // secci�n de actualizaci�n
            if (request.getParameter("id")!=null)
            {
                String idG =request.getParameter("id");
                RecSubject objG = new RecSubject();
                objG.setIdtm(idtm);
                objG.setSubjectid(Long.parseLong(idG));
                objG.load();
                objG.setDescription(request.getParameter("descripcion"));
                objG.setTitle(request.getParameter("titulo"));
                try
                {
                    if (!objG.update())
                    {
                        log.error(paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_logNoPudoActualizarGrupoId") + request.getParameter("id"));
                    }
                    log.error(paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgActualizoGrupoId")+": "+idG);
                }
                catch(Exception e)
                {log.error("Can't update sr_subject");}
            }
        }
        
        if (accion.equalsIgnoreCase("update_s"))
        {
            // secci�n de actualizaci�n
            if (request.getParameter("id")!=null)
            {
                String idG =request.getParameter("id");
                RecSubject objG = new RecSubject();
                objG.setIdtm(idtm);
                objG.setSubjectid(Long.parseLong(idG));
                objG.load();
                ret.append("\n<script language=javascript>");
                ret.append("\n    function jsValida(pForm){      ");
                ret.append("\n       replaceChars(pForm.descripcion);");
                ret.append("\n       return(true);     ");
                ret.append("\n    }");
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
                ret.append("</script>");
                
                SWBResourceURL urlus = paramsRequest.getRenderUrl();
                urlus.setAction("updatesection_s");
                urlus.setMode(SWBResourceURL.Mode_ADMIN);
                
                ret.append("\n<form name=\"forma\" action=\""+urlus+"\" method=\"GET\" onSubmit=\"if(jsValida(forma)) return true; else return false;\">");
                ret.append("\n<input type=hidden name=id value=\""+objG.getSubjectid()+"\">");
                ret.append("\n<table border=0 cellspacing=0 cellpadding=2 width=100%><tr><td colspan=2 >"+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgEdicionGrupo")+"</td></tr>");
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgTitulo")+":</td><td ><input type=\"text\" name=\"titulo\" size=60 value=\""+objG.getTitle()+"\" maxlength=50></td></tr>");
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgDescripcion")+":</td><td ><textarea name=\"descripcion\" cols=50 rows=15 maxlength=255>"+objG.getDescription().replaceAll("<br>","\r")+"</textarea></td></tr>");
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgCreado")+":</td><td ><input type=\"text\" name=\"creado\" readonly value=\""+objG.getCreated()+"\"></td></tr>");
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgUltimaModificacion")+":</td><td ><input type=\"text\" name=\"modificado\" readonly value=\""+objG.getLastUpdate()+"\"></td></tr>");
                ret.append("\n<tr><td colspan=2 align=right><hr noshade size=1><input type=\"submit\" name=\"enviar\" value=\""+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_btngActualizar")+"\" class=boton>");
                ret.append("&nbsp;<input type=\"button\" name=\"cancel\" value=\""+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_btnCancelar")+"\" onclick=\"javascript:window.location='"+urlss+"';\" class=boton>"); //window.opener.regreso();
                ret.append("\n</td></tr>");
                ret.append("</tr></table></form>");
            }
        }
 
        if (accion.equalsIgnoreCase("add_s"))
        {
           
            // secci�n de agregar grupo
            ret.append("\n<script language=javascript>");
            ret.append("\n    function jsValida(pForm){      ");
            ret.append("\n       replaceChars(pForm.descripcion);");
            ret.append("\n       return(true);     ");
            ret.append("\n    }");
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
            ret.append("</script>");
            
            SWBResourceURL urlags = paramsRequest.getRenderUrl();
            urlags.setAction("agregar_s");
            urlags.setMode(SWBResourceURL.Mode_ADMIN);
            
            ret.append("\n<form name=\"forma\" action=\""+urlags+"\" method=\"GET\" onSubmit=\"if(jsValida(forma)) return true; else return false;\">");
            ret.append("\n<table border=0 cellspacing=0 cellpadding=2 width=100%>");
            ret.append("\n<tr><td colspan=2 >"+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgNuevoGrupo")+"</td></tr>");
            ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgTitulo")+":</td><td ><input type=\"text\" name=\"titulo\" value=\"T�tulo\" size=60 maxlength=50></td></tr>");
            ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgDescripcion")+":</td><td ><textarea name=\"descripcion\" cols=50 rows=15 maxlength=255></textarea></td></tr>");
            ret.append("\n<tr><td colspan=2 align=right ><hr noshade size=1><input type=\"submit\" name=\"enviar\" value=\""+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_btnEnviar")+"\" class=boton>");
            if(request.getSession().getAttribute("regreso")!=null)
            {
                ret.append("&nbsp;<input type=\"button\" name=\"cancel\" value=\""+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_btnCancelar")+"\" onclick=\"javascript:window.close();\" class=boton>"); //window.opener.regreso();
            }      
            else
            {
                ret.append("&nbsp;<input type=\"button\" name=\"cancel\" value=\""+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_btnCancelar")+"\" onclick=\"javascript:window.location='"+urlss+"';\" class=boton>"); //window.opener.regreso();
            }   
            
            ret.append("</td></tr>");
            ret.append("</tr></table></form>");
            
        }
        
        if (accion.equalsIgnoreCase("delete_s"))
        {
            Connection con = null;
            PreparedStatement sst =null;
            ResultSet rs =null;
            PreparedStatement pst =null;
            try
            {
                con = SWBUtils.DB.getDefaultConnection();
                sst = con.prepareStatement("select * from sr_orderquestion where subjectid = ? and idtm=?"); //+request.getParameter("id")
                sst.setInt(1,Integer.parseInt(request.getParameter("id")));
                sst.setString(2,idtm);
                rs = sst.executeQuery();
                if (rs.next())
                {
                    ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgNoPuedeBorrarGrupo")+"\n"+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgExistenRegistrosHaciendoReferencia")+"<br>");
                }
                else
                {
                    pst = con.prepareStatement("delete from sr_subject where subjectid = ? and idtm=?");
                    pst.setLong(1,(long)Integer.parseInt(request.getParameter("id")));
                    pst.setString(2,idtm);
                    pst.executeUpdate();
                    if(pst!=null)pst.close();
                    log.error(paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgAdministradorId")+": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgEliminoGrupoId")+": "+request.getParameter("id"));
                    //ret.append(paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgGrupoEliminado"));
                }
                if(sst!=null)sst.close();
                if(rs!=null)rs.close();
                if(con!=null)con.close();
                
            }
            catch(Exception e)
            { log.error(paramsRequest.getLocaleString("errormsg_Section_getAdmHtml_logErrorBorrarRegistroDBSection_delete_s"),e); }
            finally
            {
                pst=null;
                rs=null;
                sst=null;
                con=null;
            }           
        }
        
        if (accion.equalsIgnoreCase("select_s")||accion.equalsIgnoreCase("delete_s")||accion.equalsIgnoreCase("agregar_s")||accion.equalsIgnoreCase("updatesection_s"))
        {
            SWBResourceURL urlupt = paramsRequest.getRenderUrl();
            urlupt.setAction("add_s");
            urlupt.setMode(SWBResourceURL.Mode_ADMIN);

            // secci�n de selecci�n de grupo a editar
            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<fieldset>");
            ret.append("\n<form action=\""+urlupt+"\" method=\"GET\">");
            ret.append("<table border=0 cellspacing=0 cellpadding=2 width=100%>");
            Connection con = null;
            PreparedStatement st =null;
            ResultSet rs =null;
            ret.append("\n<thead>");
            ret.append("\n<tr>");
            ret.append("\n<th>Acción</th>");
            ret.append("\n<th>Id</th>");
            ret.append("\n<th>Título</th>");
            ret.append("\n<th>Descripción</th>");
            ret.append("\n<th>Creado</th>");
            ret.append("\n<th>Actualizado</th>");
            ret.append("\n</tr>");
            ret.append("\n</thead>");
            ret.append("\n<tbody>");
            try
            {
                con = SWBUtils.DB.getDefaultConnection();
                st = con.prepareStatement("select * from sr_subject where idtm=?");
                st.setString(1,idtm);
                rs = st.executeQuery();
                String rowColor="";
                boolean cambiaColor = true;
                while(rs.next())
                {
                    long sectionID =rs.getLong("subjectid");
                    RecSubject rsub = new RecSubject();
                    rsub.setIdtm(idtm);
                    rsub.setSubjectid(sectionID);
                    rsub.load();
                    rowColor="bgcolor=\"#EFEDEC\"";
                    if(!cambiaColor) rowColor="";
                    cambiaColor = !(cambiaColor);
                    ret.append("\n<tr "+rowColor+">");
                    ret.append("\n<td >");
                    SWBResourceURL urldel = paramsRequest.getRenderUrl();
                    urldel.setAction("delete_s");
                    urldel.setMode(SWBResourceURL.Mode_ADMIN);
                    urldel.setParameter("id",Long.toString(sectionID));
                    SWBResourceURL urledit = paramsRequest.getRenderUrl();
                    urledit.setAction("update_s");
                    urledit.setMode(SWBResourceURL.Mode_ADMIN);
                    urledit.setParameter("id",Long.toString(sectionID));
                    ret.append("\n<a href=\""+urldel+"\" onclick=\"if(confirm('"+paramsRequest.getLocaleString("usrmsg_Subject_ShureEraseSubject")+"?')){ return (true);} else { return (false);}\"><img src=\"/swbadmin/images/delete.gif\" border=0 alt=\""+paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgQuitar")+"\"></a>");
                    ret.append("\n<a href=\""+urledit+"\" ><img src=\"/swbadmin/icons/editar_1.gif\" border=0 alt=\"Detail group\nid: "+sectionID+"\"></a>");
                    ret.append("\n</td>");
                    ret.append("\n<td >"+rsub.getSubjectid()+"</td>");
                    ret.append("\n<td >"+rsub.getTitle()+"</td>");
                    ret.append("\n<td >"+rsub.getDescription()+"</td>");
                    ret.append("\n<td >"+SWBUtils.TEXT.getStrDate(rsub.getCreated(),user.getLanguage())+"</td>");
                    ret.append("\n<td >"+SWBUtils.TEXT.getStrDate(rsub.getLastUpdate(),user.getLanguage())+"</td>");
                    ret.append("\n</tr>");
                }
                if(rs!=null)rs.close();
                if(st!=null)st.close();
                if(con!=null)con.close();
            }
            catch(Exception e)
            { log.error(paramsRequest.getLocaleString("errormsg_Section_getAdmHtml_logErrorConsultarDBSection"),e); }
            finally
            {
                rs=null;
                st=null;
                con=null;
            }
            ret.append("\n</tbody>");
            ret.append("</table></form>");
            ret.append("\n</fieldset>");
            ret.append("\n<fieldset>");
            ret.append("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"enviar\">"+paramsRequest.getLocaleString("usrmsg_ControlCatalog_getAdmHtml_msgAgregar")+"</button>");
            ret.append("\n</fieldset>");
            ret.append("\n</div>");
        }

        return ret.toString();
    }
    
}
