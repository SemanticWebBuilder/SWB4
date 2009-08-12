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

import com.infotec.wb.resources.survey.db.RecValidateCode;
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
 * INFOTEC
 * 
 */
public class CatalogType
{
    Resource base=null;
    private static Logger log = SWBUtils.getLogger(CatalogType.class);

    public CatalogType()
    {}
    
    /**
     *
     * @param base
     */    
    public void setResourceBase(Resource base)
    {
        this.base = base;
    }
    
    /**
     *
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     * @return
     */    
    public String doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        String accion = paramsRequest.getAction();
        User user = paramsRequest.getUser();
        StringBuffer ret = new StringBuffer();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String idtm=request.getParameter("tm");
        if(null==idtm) idtm=paramsRequest.getWebPage().getWebSiteId();
        
        response.setContentType("text/html");
         
        ret.append("\n<LINK href=\"/swbadmin/css/swb.css\" rel=\"stylesheet\" type=\"text/css\" >");
        
        if (accion.equalsIgnoreCase("updatecatalogtype_ct"))
        {
            // secci�n de actualizaci�n
            if (request.getParameter("id")!=null)
            {
                String idG =request.getParameter("id");
                try
                {
                    RecValidateCode objG = new RecValidateCode();
                    objG.setCodeid(Integer.parseInt(idG));
                    objG.setIdtm(idtm);
                    objG.load();
                    objG.setDescription(request.getParameter("descripcion"));
                    objG.setTitle(request.getParameter("titulo"));
                    objG.setValidationcode(request.getParameter("validacion"));
                    log.error(paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_logAdminId")  +": "+user.getId()+" "+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_logActualizoValidacionId")  +": "+idG);
                    objG.update();
                }
                catch(Exception e)
                {
                    log.error(paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_logNoPudoActualizarGrupoId")  +" " + request.getParameter("id"),e );
                }
            }
        }
        if (accion.equalsIgnoreCase("update_ct")||accion.equalsIgnoreCase("updatecatalogtype_ct"))
        {
            // secci�n de actualizaci�n
            ret.append("\n       <script language=\"javascript\">     ");
            ret.append("\n            function valida(forma){     ");
            ret.append("\n                      ");
            ret.append("\n                 var tmp = forma.titulo.value;     ");
            ret.append("\n                      ");
            ret.append("\n                 trim(forma.titulo);     ");
            ret.append("\n                      ");
            ret.append("\n                 if(forma.titulo.value==\"\"){     ");
            ret.append("\n                      alert(\""+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgAlertTituloValidacion")  +"\");     ");
            ret.append("\n                      forma.titulo.value=tmp;     ");
            ret.append("\n                      forma.titulo.focus();     ");
            ret.append("\n                      forma.titulo.select();     ");
            ret.append("\n                      return(false);     ");
            ret.append("\n                 }     ");
            ret.append("\n                 tmp = forma.descripcion.value;     ");
            ret.append("\n                      ");
            ret.append("\n                 trim(forma.descripcion);     ");
            ret.append("\n                      ");
            ret.append("\n                 if(forma.descripcion.value==\"\"){     ");
            ret.append("\n                      alert(\""+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgAlertDescripcionValidacion")  +"\");     ");
            ret.append("\n                      forma.descripcion.value=tmp;     ");
            ret.append("\n                      forma.descripcion.focus();     ");
            ret.append("\n                      forma.descripcion.select();     ");
            ret.append("\n                      return(false);     ");
            ret.append("\n                 }     ");
            ret.append("\n                 tmp = forma.validacion.value;     ");
            ret.append("\n                      ");
            ret.append("\n                 trim(forma.validacion);     ");
            ret.append("\n                      ");
            ret.append("\n                 if(forma.validacion.value==\"\"){     ");
            ret.append("\n                      alert(\""+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgAlertDebesProporcionarValidacion")  +"\");     ");
            ret.append("\n                      forma.validacion.value=tmp;     ");
            ret.append("\n                      forma.validacion.focus();     ");
            ret.append("\n                      forma.validacion.select();     ");
            ret.append("\n                      return(false);     ");
            ret.append("\n                 }     ");
            ret.append("\n                 //alert(\""+"Datos capturados correctamente"+"\");     ");
            ret.append("\n                 return(true);     ");
            ret.append("\n            }     ");
            ret.append("\n                 ");
            ret.append("\n             function trim(field) {         ");
            ret.append("\n                 var retVal = \"\";         ");
            ret.append("\n                 var start = 0;         ");
            ret.append("\n                 while ((start < field.value.length) && (field.value.charAt(start) == ' ')) {         ");
            ret.append("\n                   ++start;         ");
            ret.append("\n                 }         ");
            ret.append("\n                 var end = field.value.length;         ");
            ret.append("\n                 while ((end > 0) && (field.value.charAt(end - 1) == ' ')) {         ");
            ret.append("\n                   --end;         ");
            ret.append("\n                 }         ");
            ret.append("\n                 retVal = field.value.substring(start, end);         ");
            ret.append("\n                 if (end == 0)         ");
            ret.append("\n                  field.value=\"\";         ");
            ret.append("\n                 else         ");
            ret.append("\n                  field.value=retVal;          ");
            ret.append("\n                }     ");
            ret.append("\n       </script>     ");
            if (request.getParameter("id")!=null)
            {
                String idG =request.getParameter("id");
                RecValidateCode objG = new RecValidateCode();
                objG.setCodeid(Integer.parseInt(idG));
                objG.setIdtm(idtm);
                objG.load();
                SWBResourceURL url = paramsRequest.getRenderUrl();
                url.setAction("updatecatalogtype_ct");
                ret.append("\n<div class=\"swbform\">");
                ret.append("\n<fieldset>");
                ret.append("\n<form action=\""+url+"\" method=\"GET\" name=\"forma\" onsubmit=\"return valida(forma);\">");
                ret.append("\n<table border=0 cellspacing=0 width=100% cellpadding=2>");
                ret.append("\n<tr><td colspan=2 >"+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgInfoTipoSeleccionado")  +"</td></tr>");
                ret.append("\n<input type=hidden name=id value=\""+objG.getCodeid()+"\">");
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgTitulo")  +":</a></td><td><input class=campos type=\"text\" name=\"titulo\" value=\""+objG.getTitle()+"\" size=\"60\" maxlength=50></td></tr>");
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgDescripcion")  +":</a></td><td><input class=campos type=\"text\" name=\"descripcion\" value=\""+objG.getDescription()+"\" size=\"60\" maxlength=255></td></tr>");
                ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgCodigoValidacion")  +":</a></td><td><textarea name=\"validacion\" rows=15 cols=50 style=\"FONT-WEIGHT:normal; FONT-SIZE:10pt; COLOR:#000000; FONT-FAMILY:verdana,arial,helvetica,sans-serif; BACKGROUND-COLOR:#ffffff\" maxlength=4000>"+objG.getValidationcode()+"</textarea></td></tr>");
                ret.append("\n<tr><td colspan=2 align=right><hr noshade size=1><input type=\"submit\" name=\"enviar\" value=\""+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_btnActualizar")  +"\" class=boton>");
                SWBResourceURL urla = paramsRequest.getRenderUrl();
                urla.setAction("select_ct");
                ret.append("&nbsp;<input type=\"button\" name=\"cancel\" value=\""+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_btnCancelar")  +"\" onclick=\"javascript:window.location='"+urla+"';\" class=boton></td></tr>");
                ret.append("</table></form>");
                ret.append("\n</fieldset>");
                ret.append("\n</div>");
            }
        }
        if (accion.equalsIgnoreCase("agregar_ct"))
        {
            // secci�n de agregar grupo
            RecValidateCode objG=new RecValidateCode();
            try
            {
                objG.setIdtm(idtm);
                objG.setDescription(request.getParameter("descripcion") );
                objG.setTitle(request.getParameter("titulo"));
                objG.setValidationcode(request.getParameter("validacion"));
                objG.create();
            }
            catch(Exception e)
            {
                log.error(paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgNoPudoAgregarTipoTexto") ,e);
                ret.append("<br>"+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgNoPudoAgregarTipoTexto")  );
            }
            if(request.getSession().getAttribute("regreso")!=null)
            {
                ret.append("\n<script language=javascript>");
                ret.append("\n    window.opener.regreso('textid="+objG.getCodeid()+"');");
                ret.append("\n    window.close();");
                ret.append("</script>");
                request.getSession().setAttribute("regreso",null);
            }
        }
        if (accion.equalsIgnoreCase("add_ct"))
        {
            // secci�n de agregar catalog type
            ret.append("\n       <script language=\"javascript\">     ");
            ret.append("\n            function valida(forma){     ");
            ret.append("\n                      ");
            ret.append("\n                 var tmp = forma.titulo.value;     ");
            ret.append("\n                      ");
            ret.append("\n                 trim(forma.titulo);     ");
            ret.append("\n                      ");
            ret.append("\n                 if(forma.titulo.value==\"\"){     ");
            ret.append("\n                      alert(\""+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgAlertProporcionarTituloValidacion")  +"\");     ");
            ret.append("\n                      forma.titulo.value=tmp;     ");
            ret.append("\n                      forma.titulo.focus();     ");
            ret.append("\n                      forma.titulo.select();     ");
            ret.append("\n                      return(false);     ");
            ret.append("\n                 }     ");
            ret.append("\n                 tmp = forma.descripcion.value;     ");
            ret.append("\n                      ");
            ret.append("\n                 trim(forma.descripcion);     ");
            ret.append("\n                      ");
            ret.append("\n                 if(forma.descripcion.value==\"\"){     ");
            ret.append("\n                      alert(\""+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgAlertDebesProporcionarDescripcionValidacion")  +"\");     ");
            ret.append("\n                      forma.descripcion.value=tmp;     ");
            ret.append("\n                      forma.descripcion.focus();     ");
            ret.append("\n                      forma.descripcion.select();     ");
            ret.append("\n                      return(false);     ");
            ret.append("\n                 }     ");
            ret.append("\n                 tmp = forma.validacion.value;     ");
            ret.append("\n                      ");
            ret.append("\n                 trim(forma.validacion);     ");
            ret.append("\n                      ");
            ret.append("\n                 if(forma.validacion.value==\"\"){     ");
            ret.append("\n                      alert(\""+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgAlertDebesProporcionarValidacion")  +"\");     ");
            ret.append("\n                      forma.validacion.value=tmp;     ");
            ret.append("\n                      forma.validacion.focus();     ");
            ret.append("\n                      forma.validacion.select();     ");
            ret.append("\n                      return(false);     ");
            ret.append("\n                 }     ");
            ret.append("\n                 //alert(\""+"Datos capturados correctamente"+"\");     ");
            ret.append("\n                 return(true);     ");
            ret.append("\n            }     ");
            ret.append("\n                 ");
            ret.append("\n             function trim(field) {         ");
            ret.append("\n                 var retVal = \"\";         ");
            ret.append("\n                 var start = 0;         ");
            ret.append("\n                 while ((start < field.value.length) && (field.value.charAt(start) == ' ')) {         ");
            ret.append("\n                   ++start;         ");
            ret.append("\n                 }         ");
            ret.append("\n                 var end = field.value.length;         ");
            ret.append("\n                 while ((end > 0) && (field.value.charAt(end - 1) == ' ')) {         ");
            ret.append("\n                   --end;         ");
            ret.append("\n                 }         ");
            ret.append("\n                 retVal = field.value.substring(start, end);         ");
            ret.append("\n                 if (end == 0)         ");
            ret.append("\n                  field.value=\"\";         ");
            ret.append("\n                 else         ");
            ret.append("\n                  field.value=retVal;          ");
            ret.append("\n                }     ");
            ret.append("\n       </script>     ");
            SWBResourceURL url = paramsRequest.getRenderUrl();
            url.setAction("agregar_ct");
            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<fieldset>");
            ret.append("\n<form action=\""+url+"\" method=\"GET\" name=\"forma\" onsubmit=\"return valida(forma);\">");
            ret.append("\n<table border=0 cellspacing=1 width=100%>");
            ret.append("\n<tr><td colspan=2 >"+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgNuevoTipoValidacion")  +"</td></tr>");
            ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgTitulo")  +":</td><td><input type=\"text\" name=\"titulo\" value=\"T�tulo\" size=\"60\" maxlength=50 class=campos></td></tr>");
            ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgDescripcion")  +":</td><td><input type=\"text\" name=\"descripcion\" value=\"\"  size=\"60\" maxlength=255 class=campos></td></tr>");
            ret.append("\n<tr><td  width=200 align=right>"+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgCodigoValidacion")  +":</td><td><textarea name=\"validacion\" rows=15 cols=50 style=\"FONT-WEIGHT:normal; FONT-SIZE:10pt; COLOR:#000000; FONT-FAMILY:verdana,arial,helvetica,sans-serif; BACKGROUND-COLOR:#ffffff\" maxlength=4000></textarea></td></tr>");
            ret.append("\n<tr><td colspan=2 align=right><hr noshade size=1><input type=\"submit\" name=\"enviar\" value=\""+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_btnEnviar")  +"\" class=boton>");
            if(request.getSession().getAttribute("regreso")!=null)
            {
                ret.append("&nbsp;<input type=\"button\" name=\"cancel\" value=\""+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_btnCancelar")  +"\" onclick=\"javascript:window.close();\" class=boton>"); //window.opener.regreso();
            }
            else
            {
                SWBResourceURL urla = paramsRequest.getRenderUrl();
                urla.setAction("select_ct");
                ret.append("&nbsp;<input type=\"button\" name=\"cancel\" value=\""+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_btnCancelar")  +"\" onclick=\"javascript:window.location='"+urla+"';\" class=boton>");
            }
            ret.append("</td></tr></table></form>");
            ret.append("\n</fieldset>");
            ret.append("\n</div>");
        }
        if (accion.equalsIgnoreCase("delete_ct"))
        {
            try
            {
                con = SWBUtils.DB.getDefaultConnection();
                st = con.prepareStatement("select * from sr_question where codeid = ? and idtm=? ");
                st.setInt(1,Integer.parseInt(request.getParameter("id")));
                st.setString(2,idtm);
                rs = st.executeQuery();
                if (rs.next())
                {
                    log.error(paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgNoBorrarTipoExistenRegistrosHaciendoReferencia"));
                }
                else
                {
                    PreparedStatement pst = con.prepareStatement("delete from sr_validatecode where codeid = ? and idtm=?");
                    pst.setLong(1,(long)Integer.parseInt(request.getParameter("id")));
                    pst.setString(2,idtm);
                    pst.executeUpdate();
                    if(pst != null)
                    {
                        pst.close();
                        pst = null;
                    }
                }
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(con != null) con.close();
            }
            catch(Exception e)
            {
                log.error(paramsRequest.getLocaleString("errormsg_CatalogType_getAdmHtml_logErrorCatalogTypegetAdmHtml_delete_ct") ,e);
            }
            finally
            {
                rs = null;
                st = null;
                con = null;
            }
        }
        if (accion.equalsIgnoreCase("select_ct")||accion.equalsIgnoreCase("delete_ct")||accion.equalsIgnoreCase("agregar_ct"))
        {
            SWBResourceURL urla = paramsRequest.getRenderUrl();
            urla.setAction("add_ct");
            // secci�n de selecci�n de grupo a editar
            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<fieldset>");
            ret.append("\n<form action=\""+urla+"\" method=\"GET\">");
            ret.append("\n<table border=0 cellspacing=0 cellpadding=2 width=100%>");
            ret.append("\n<tr ><td>");
            ret.append(paramsRequest.getLocaleString("usrmsg_Survey_getAdmHtml_msgAction"));
            ret.append("\n</td><td>");
            ret.append("\nid");
            ret.append("\n</td><td>");
            ret.append(paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgTitulo"));
            ret.append("\n</td><td>");
            ret.append(paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgDescripcion"));
            ret.append("\n</td><td>");
            ret.append(paramsRequest.getLocaleString("usrmsg_Section_getAdmHtml_msgUltimaModificacion"));
            ret.append("\n</td></tr>");
            try
            {
                con = SWBUtils.DB.getDefaultConnection();
                st = con.prepareStatement("select * from sr_validatecode where idtm=?");
                st.setString(1,idtm);
                rs = st.executeQuery();
                String rowColor="";
                boolean cambiaColor = true;
                while(rs.next())
                {
                    int textid =rs.getInt("codeid");
                    RecValidateCode rvc = new RecValidateCode();
                    rvc.setCodeid(textid);
                    rvc.setIdtm(idtm);
                    rvc.load();
                    rowColor="#EFEDEC";
                    if(!cambiaColor) rowColor="#FFFFFF";
                    cambiaColor = !(cambiaColor);
                    ret.append("\n<tr  bgcolor=\""+rowColor+"\"><td>");
                    SWBResourceURL urlu = paramsRequest.getRenderUrl();
                    urlu.setAction("update_ct");
                    urlu.setParameter("id",Integer.toString(rvc.getCodeid()));
                    SWBResourceURL urle = paramsRequest.getRenderUrl();
                    urle.setAction("delete_ct");
                    urle.setParameter("id",Integer.toString(rvc.getCodeid()));
                    ret.append("\n<a href=\""+urle+"\" onclick=\"if(confirm('"+paramsRequest.getLocaleString("usrmsg_ShureEraseValidationCode")+"?')){ return (true);} else { return (false);}\" ><img src=\"/swbadmin/images/delete.gif\" border=0></a><a href=\""+urlu+"\" ><img src=\"/swbadmin/icons/editar_1.gif\" border=0></a>");
                    ret.append("\n</td><td>");
                    ret.append("\n"+textid);
                    ret.append("\n</td><td>");
                    ret.append("\n"+rvc.getTitle());
                    ret.append("\n</td><td>");
                    ret.append("\n"+rvc.getDescription());
                    ret.append("\n</td><td>");
                    ret.append("\n"+SWBUtils.TEXT.getStrDate(rvc.getLastupdate(),user.getLanguage()));
                    ret.append("\n</td></tr>");
                }
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(con != null)con.close();
            }
            catch(Exception e)
            {
                log.error(paramsRequest.getLocaleString("errormsg_CatalogType_getAdmHtml_msgErrorCatalogTypegetAdmHTMLSelect")  ,e);
            }
            finally
            {
                rs = null;
                st = null;
                con = null;
            }
            ret.append("<tr><td colspan=5 align=right><hr noshade size=1><input type=\"submit\" name=\"enviar\" value=\""+paramsRequest.getLocaleString("usrmsg_CatalogType_getAdmHtml_msgAgregar")+"\" class=boton></td></tr>");
            ret.append("</table></form>");
            ret.append("\n</fieldset>");
            ret.append("\n</div>");
        }
         
        return ret.toString();
    }
    
}
