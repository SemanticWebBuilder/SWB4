/*
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
 */
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Camp;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBACamp.
 * 
 * @author juan.fernandez
 */
public class SWBACamp extends GenericResource {


    /** The str rsc type. */
    String strRscType=SWBACamp.class.getName();
    
    /** The log. */
    private Logger log=SWBUtils.getLogger(SWBACamp.class);
    
    /** The base. */
    Resource base=null;
 
 /**
  * Creates a new instance of SWBACamp.
  */
    public SWBACamp() {
    }
    
    /**
     * User View of SWBACamp Resource.
     * 
     * @param request parameters
     * @param response answer to the request
     * @param paramRequest a list of objects (user, topic, action, ...)
     * @throws IOException an IO Exception
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
    
        base = getResourceBase();
        PrintWriter out=response.getWriter();
        User user = paramRequest.getUser();
        String strTm = request.getParameter("tm");
        
        if(user==null) user = new User(paramRequest.getWebPage().getWebSite().getUserRepository().getSemanticObject());
        if((request.getParameter("act")!=null&&(request.getParameter("act").equals("add")||request.getParameter("act").equals("edit")))||(request.getParameter("act")==null&&request.getParameter("id")!=null)){
            doEdit(request,response,paramRequest);
        }
        if(request.getParameter("act")!=null&&request.getParameter("act").equals("remove")){
            String id="0";
            try{
                if(request.getParameter("id")!=null) id =request.getParameter("id");
                
                if(strTm!=null){
                    try{
                        WebSite ws = SWBContext.getWebSite(strTm);
                        ws.removeCamp(id);
                        ws.setModifiedBy(user);
                        out.println("<script>wbTree_remove();</script>");
                        out.println(paramRequest.getLocaleString("msgOkRemove"));
                        
                    }
                    catch(Exception e)
                    {
                        out.println(paramRequest.getLocaleString("msgErrRemove")+": "+id+", "+paramRequest.getLocaleString("msgErrRemoveCause"));
                        log.error(e);
                    }
                }
                else{
                    out.println(paramRequest.getLocaleString("msgErrRemove")+": "+id+", "+paramRequest.getLocaleString("msgErrRemoveCause"));
                }
            }
            catch(Exception e){
                log.error(paramRequest.getLocaleString("msgExcRemove")+":"+id);
                out.println(paramRequest.getLocaleString("msgExcRemove")+": "+id);
            }
            
        }else{
            if(request.getParameter("act")!=null&&(request.getParameter("act").equals("active")||request.getParameter("act").equals("unactive"))){
                
                String id="0";
                boolean active = false;
                String strActive =paramRequest.getLocaleString("msgUnActivated");
                if(request.getParameter("id")!=null) id = request.getParameter("id");
                try{
                    if(request.getParameter("act").equals("active")){
                        active=true;
                        strActive =paramRequest.getLocaleString("msgActivated");
                    }

                    WebSite ws = SWBContext.getWebSite(strTm);
                    Camp camp = ws.getCamp(id);
                    camp.setActive(active);
                    camp.setModifiedBy(user);
                    
                    out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tm")+".camps."+request.getParameter("id")+"');");
                    out.println("wbTree_reload();</script>");
                    
                    out.println(paramRequest.getLocaleString("msgCampID")+": "+id+" "+paramRequest.getLocaleString("msgWas")+" "+strActive);
                }
                catch(Exception e){
                    log.error(paramRequest.getLocaleString("ErrorChangeActiveStatusCampID")+": "+id);
                    out.println(paramRequest.getLocaleString("ErrorChangeActiveStatusCampID")+": "+id);
                }
            }
        }
    }

    
    /**
     * Edition view of SWBACamp Resource.
     * 
     * @param request parameters
     * @param response answer to the request
     * @param paramRequest a list of objects (user, topic, action, ...)
     * @throws IOException an IO Exception
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
    
        String act = null;
        String id = request.getParameter("id");
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String strTm = request.getParameter("tm");
        if(request.getParameter("act")!=null) act = request.getParameter("act");
        if(request.getParameter("tree")!=null&&request.getParameter("tree").equals("reload")){
            if(request.getParameter("flag")!=null){
                if(request.getParameter("flag").equals("add")){
                    out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tmid")+".camps');wbTree_reload();wbTree_executeAction('gotopath."+request.getParameter("tmid")+".camps."+request.getParameter("id")+"');</script>");
                }
                if(request.getParameter("flag").equals("update")){
                    out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tmid")+".camps');wbTree_reload();</script>");
                    out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tmid")+".camps."+request.getParameter("id")+"');wbTree_reload();</script>");
                }
            }
        }
        out.println("<script language=javascript>");
        out.println("   function valida(forma) {");
        out.println("      var tempo = forma.title.value;");
        out.println("      trim(forma.title);");
        out.println("      if(forma.title.value==''){");
        out.println("          alert('"+paramRequest.getLocaleString("msgTitleRequired")+"');");
        out.println("          return (false);");
        out.println("      }");
        out.println("      forma.title.value=tempo;");
        out.println("      tempo=forma.description.value;");
        out.println("      trim(forma.description);");
        out.println("      if(forma.description.value==''){");
        out.println("          alert('"+paramRequest.getLocaleString("msgDescriptionRequired")+"');");
        out.println("          return (false);");
        out.println("      }");
        out.println("      forma.description.value=tempo;");
        out.println("}");
        out.println("");
        out.println("    function trim(field) {     ");
        out.println("         var retVal = '';     ");
        out.println("         var start = 0;     ");
        out.println("         while ((start < field.value.length) && (field.value.charAt(start) == ' ')) {     ");
        out.println("              ++start;     ");
        out.println("         }     ");
        out.println("         var end = field.value.length;     ");
        out.println("         while ((end > 0) && (field.value.charAt(end - 1) == ' ')) {     ");
        out.println("              --end;     ");
        out.println("         }     ");
        out.println("         retVal = field.value.substring(start, end);     ");
        out.println("         if (end == 0)     ");
        out.println("              field.value='';     ");
        out.println("         else     ");
        out.println("              field.value=retVal;          ");
        out.println("    }              ");
        out.println("");
        out.println("</script>");
        
        if(act!=null&&act.equals("add")){
            out.println("<form name=\"forma\" onSubmit=\"return (valida(forma));\"  action=\""+paramRequest.getActionUrl().setAction("add").toString()+"\" method=\"post\" class=\"box\">");
            out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<tr ><td colspan=2 class=\"tabla\">"+paramRequest.getLocaleString("msgAddCampaign")+"</td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgTitle")+":</td><td class=\"valores\"><input type=text class=\"campos\" name=\"title\" value=\"\" ></td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgDescription")+":</td><td class=\"valores\"><input type=text class=\"campos\" name=\"description\" value=\"\"></td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgSite")+":</td><td class=\"valores\">");
            out.println(SWBContext.getWebSite(request.getParameter("tm")).getDisplayTitle(user.getLanguage()));
            out.println("</td></tr>");
            out.println("<tr ><td colspan=2 class=\"valores\" align=right><HR size=\"1\" noshade><input type=submit class=\"boton\" value=\""+paramRequest.getLocaleString("btnSave")+"\" ><input type=hidden name=tmnid value=\""+request.getParameter("tm")+"\"></td></tr>");
            out.println("</table>");
            out.println("</form>");
        }
        
        if((act==null&&id!=null)||(act.equals("edit")&&id!=null)){
            if(request.getParameter("id")!=null){
                id = request.getParameter("id");
                if(act!=null){
                    out.println("<p align=center class=\"box\">");
                    out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                    out.println("<TR>");
                    out.println("<TD width=\"150\" align=\"right\" class=\"datos\"> "+paramRequest.getLocaleString("msgID")+"</TD>");
                    out.println("<TD class=\"valores\">");
                    out.println(id);
                    out.println("</TD>");
                    out.println("</TR>");
                    out.println("</TABLE></P>");
                    Camp rC = SWBContext.getWebSite(strTm).getCamp(id);
                    out.println("<form name=\"forma\" onSubmit=\"return (valida(forma));\"  action=\""+paramRequest.getActionUrl().setAction("update").toString()+"\" method=\"post\" class=\"box\">");
                    out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                    out.println("<tr ><td colspan=2><input type=hidden name=\"id\" value=\""+id+"\"></td></tr>");
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgTitle")+":</td><td class=\"valores\"><input type=text class=\"campos\" value=\""+rC.getDisplayTitle(user.getLanguage())+"\" name=title></td></tr>");
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgDescription")+":</td><td class=\"valores\"><input type=text class=\"campos\" name=description value=\""+rC.getDescription()+"\"></td></tr>");
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgSite")+":</td><td class=\"valores\"><input type=hidden name=tmnid value=\""+rC.getWebSite().getId()+"\">");
                    out.println(rC.getWebSite().getDisplayTitle(user.getLanguage()));
                    out.println("</td></tr>");
                    //TODO: Falta dateFormat
                    //out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgLastUpdate")+":</td><td class=\"valores\">"+WBUtils.dateFormat(rC.getLastupdate())+"</td></tr>");
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgLastUpdate")+":</td><td class=\"valores\">"+rC.getUpdated()+"</td></tr>");
                    String selActive ="";
                    String selUnActive="";
                    if(rC.isActive()){
                        selActive="checked";
                    }
                    else{
                        selUnActive="checked";
                    }
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgStatus")+":</td><td class=\"valores\">"+paramRequest.getLocaleString("msgActive")+"<input type=radio name=activar value=\"1\" "+selActive+">"+paramRequest.getLocaleString("msgUnActive")+"<input type=radio name=activar value=\"0\"  "+selUnActive+"></td></tr>");
                    out.println("<tr ><td colspan=2 align=right class=\"datos\"><HR size=\"1\" noshade><input type=submit class=\"boton\" value=\""+paramRequest.getLocaleString("btnSave")+"\" ></td></tr>");
                    out.println("</table>");
                    out.println("</form>");
                }
                else{
                    out.println("<p align=center class=\"box\">");
                    out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                    out.println("<TR>");
                    out.println("<TD width=\"150\" align=\"right\" class=\"datos\"> "+paramRequest.getLocaleString("msgID")+"</TD>");
                    out.println("<TD class=\"valores\">");
                    out.println(id);
                    out.println("</TD>");
                    out.println("</TR>");
                    out.println("</TABLE></P>");
                    
                    Camp rC = SWBContext.getWebSite(strTm).getCamp(id);
                    out.println("<p align=center class=\"box\">");
                    out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                    out.println("<tr ><td colspan=2><input type=hidden name=\"id\" value=\""+id+"\"></td></tr>");
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgTitle")+":</td><td class=\"valores\">"+rC.getDisplayTitle(user.getLanguage())+"</td></tr>");
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgDescription")+":</td><td class=\"valores\">"+rC.getDescription()+"</td></tr>");
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgSite")+":</td><td class=\"valores\">");
                    out.println(SWBContext.getWebSite(request.getParameter("tm")).getDisplayTitle(user.getLanguage()));
                    out.println("</td></tr>");
                    //TODO: Falta implementar dateFormat
                    //out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgLastUpdate")+":</td><td class=\"valores\">"+WBUtils.dateFormat(rC.getLastupdate())+"</td></tr>");
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgLastUpdate")+":</td><td class=\"valores\">"+rC.getUpdated()+"</td></tr>");
                    String msgActive ="";
                    if(rC.isActive()){
                        msgActive=paramRequest.getLocaleString("msgActive");
                    }
                    else{
                        msgActive=paramRequest.getLocaleString("msgUnActive");
                    }
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgStatus")+":</td><td class=\"valores\">"+msgActive+"</td></tr>");
                    out.println("</TABLE></P>");
                }
            }
        }
    }

    /**
     * Add and Update action of WBAGrpTemplates information.
     * 
     * @param request parameters
     * @param response answer to the request
     * @throws IOException an IO Exception
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
    
        String accion = response.getAction();
        String id = request.getParameter("id");
        User user = response.getUser();
        if(user==null) user=new User(response.getWebPage().getWebSite().getUserRepository().getSemanticObject());
        try{
            if(accion.equals("update")){
                String strtm= request.getParameter("tmnid");
                String activo = request.getParameter("activar");
                
                WebSite ws = SWBContext.getWebSite(strtm);
                Camp camp = ws.getCamp(id);
                camp.setActive(activo!=null&&activo.equals("1")?true:false);
                camp.setTitle(request.getParameter("title"));
                camp.setDescription(request.getParameter("description"));
                camp.setModifiedBy(user);
                
                response.setMode(response.Mode_EDIT);
                response.setRenderParameter("id",id);
                if(strtm!=null) {
                    response.setRenderParameter("tmid",strtm);
                    response.setRenderParameter("tm",strtm);
                }
                if(request.getParameter("lastTM")!=null) response.setRenderParameter("lastTM",request.getParameter("lastTM"));
                response.setRenderParameter("flag","update");
                response.setRenderParameter("tree","reload");
            }

            if(accion.equals("add")){
                String idC="0";
                String strtm =null;
                try{
                    strtm = request.getParameter("tmnid");
                    WebSite ws = SWBContext.getWebSite(strtm);
                    Camp camp = ws.createCamp();
                    camp.setActive(false);
                    camp.setTitle(request.getParameter("title"));
                    camp.setDescription(request.getParameter("description"));
                    camp.setCreator(user);
                    idC=camp.getId();
                }
                catch(Exception e)
                { 
                    log.error("Error while trying to create a campaign. "+" WBACamp.processAction.add",e);
                }
                response.setMode(response.Mode_EDIT);
                response.setRenderParameter("id",idC);
                if(strtm!=null){
                    response.setRenderParameter("tmid",strtm);
                    response.setRenderParameter("tm",strtm);
                }
                response.setRenderParameter("flag","add");
                response.setRenderParameter("tree","reload");
            }
        }
        catch(Exception e){log.error(e);}
    }

}
