/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;
import org.semanticwb.portal.api.*;

/**
 *
 * @author juan.fernandez
 */
public class SWBAGrpTemplates extends GenericResource{

    private Logger log = SWBUtils.getLogger(SWBAGrpTemplates.class);
    /** Creates a new instance of SWBAGrpTemplates */
    public SWBAGrpTemplates() {
    }

    /** User View of WBAGrpTemplates Resource
     * @param request parameters
     * @param response answer to the request
     * @param paramRequest a list of objects (user, topic, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        PrintWriter out=response.getWriter();
        User user = paramRequest.getUser();
        String strTm = request.getParameter("tm");
        //if(user==null) user = new User(paramRequest.getTopic().getMap().getDbdata().getRepository());
        if((request.getParameter("act")!=null&&(request.getParameter("act").equals("add")||request.getParameter("act").equals("edit")))|(request.getParameter("act")==null&&request.getParameter("id")!=null)){
            doEdit(request,response,paramRequest);
        }
        if(request.getParameter("act")!=null&&request.getParameter("act").equals("remove")){
            String id="0";
            try{
                if(request.getParameter("id")!=null) id = request.getParameter("id");
                
                Iterator<Template> enuTemp = SWBContext.getWebSite(strTm).listTemplates();//DBTemplate.getInstance().getTemplates(strTm);
                int cuenta=0;
                while(enuTemp.hasNext()){
                    Template rt = enuTemp.next();
                    if(rt.getGroup().getId().equals(id)){
                        cuenta++;
                        break;
                    }
                }
                if(cuenta==0){
                    WebSite ws = SWBContext.getWebSite(strTm);
                    ws.removeTemplateGroup(id);
                    ws.setModifiedBy(user);
//                    rGTemplate.remove(user.getId(),paramRequest.getLocaleString("TempGroupDelid")+": "+id);

                    out.println("<script>wbTree_remove();</script>");
                    out.println(paramRequest.getLocaleString("TempGroupDelid"));
                }
                else{
                    out.println(paramRequest.getLocaleString("CantDelTempGroupID")+": "+id+", "+paramRequest.getLocaleString("TempGroupAssocTemp"));
                }
            }
            catch(Exception e){
                log.error(paramRequest.getLocaleString("ErrorDelTempGroupID")+":"+id,e);
                out.println(paramRequest.getLocaleString("ErrorDelTempGroupID")+": "+id);
            }
            
        }
    }

    /** Edition view of WBAGrpTemplates Resource
     * @param request parameters
     * @param response answer to the request
     * @param paramRequest a list of objects (user, topic, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */    
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String act = null;
        String id = request.getParameter("id");
        PrintWriter out = response.getWriter();
        String strTm = request.getParameter("tm");
        if(request.getParameter("act")!=null) act = request.getParameter("act");
        if(request.getParameter("tree")!=null&&request.getParameter("tree").equals("reload")){
            if(request.getParameter("flag")!=null){
                if(request.getParameter("flag").equals("add")){
                    out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tmid")+".templates');wbTree_reload();wbTree_executeAction('gotopath."+request.getParameter("tmid")+".templates."+request.getParameter("id")+"');</script>");
                }
                if(request.getParameter("flag").equals("update")){
                    out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("lastTM")+".templates');wbTree_reload();</script>");
                    out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tmid")+".templates."+request.getParameter("id")+"');wbTree_reload();</script>");
                }
            }
        }
        out.println("<script language=javascript>");
        out.println("   function valida(forma) {");
        out.println("      var tempo = forma.title.value;");
        out.println("      trim(forma.title);");
        out.println("      if(forma.title.value==''){");
        out.println("          alert('"+paramRequest.getLocaleString("FaltaTituloTempGr")+"');");
        out.println("          return (false);");
        out.println("      }");
        out.println("      forma.title.value=tempo;");
        out.println("      tempo=forma.description.value;");
        out.println("      trim(forma.description);");
        out.println("      if(forma.description.value==''){");
        out.println("          alert('"+paramRequest.getLocaleString("FaltaDesc")+"');");
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
            out.println("<tr ><td colspan=2 class=\"tabla\">"+paramRequest.getLocaleString("AddTempGr")+"</td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("Title")+":</td><td class=\"valores\"><input type=text class=\"campos\" name=\"title\" value=\"\" ></td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("Description")+":</td><td class=\"valores\"><input type=text class=\"campos\" name=\"description\" value=\"\"></td></tr>");
            out.println("<tr ><td colspan=2 class=\"valores\" align=right><HR size=\"1\" noshade><input type=submit class=\"boton\" value=\""+paramRequest.getLocaleString("add_btn")+"\" ><input type=hidden name=tmnid value=\""+request.getParameter("tm")+"\"></td></tr>");
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
                    out.println("<TD width=\"150\" align=\"right\" class=\"datos\"> "+paramRequest.getLocaleString("Identificador")+"</TD>");
                    out.println("<TD class=\"valores\">");
                    out.println(id);
                    out.println("</TD>");
                    out.println("</TR>");
                    out.println("</TABLE></P>");
                    TemplateGroup rGTemp = SWBContext.getWebSite(strTm).getTemplateGroup(id);
                    out.println("<form name=\"forma\" onSubmit=\"return (valida(forma));\"  action=\""+paramRequest.getActionUrl().setAction("update").toString()+"\" method=\"post\" class=\"box\">");
                    out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                    out.println("<tr ><td colspan=2><input type=hidden name=\"id\" value=\""+id+"\"></td></tr>");
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("Title")+":</td><td class=\"valores\"><input type=text class=\"campos\" value=\""+rGTemp.getTitle()+"\" name=title></td></tr>");
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("Description")+":</td><td class=\"valores\"><input type=text class=\"campos\" name=description value=\""+rGTemp.getDescription()+"\"></td></tr>");
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("Sitio")+":</td><td class=\"valores\"><input type=hidden name=tmnid value=\""+rGTemp.getWebSite().getId()+"\">");
                    String strSel="";
                    String lastTM="";
                    /* <select name=tmnid>
                    Enumeration enutm = DBTopicMap.getInstance().getTopicMaps();
                    while(enutm.hasMoreElements()){
                        RecTopicMap rTM = (RecTopicMap) enutm.nextElement();
                        strSel="";
                        
                        if(rGTemp.getTopicMapId()==rTM.getId()){
                            strSel =" selected ";
                            lastTM=rTM.getId();
                        }
                        out.println("<option value=\""+rTM.getId()+"\" "+strSel+">"+rTM.getTitle()+"</option>");
                    }
                     *</select>
                     **/
                    out.println(rGTemp.getWebSite().getTitle());
                    out.println("<input type=hidden name=lastTM value=\""+lastTM+"\"></td></tr>");
                    //TODO: dateFormat()
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("Last_modification")+":</td><td class=\"valores\">"+rGTemp.getUpdated()+"</td></tr>");
                    out.println("<tr ><td colspan=2 align=right class=\"datos\"><HR size=\"1\" noshade><input type=submit class=\"boton\" value=\""+paramRequest.getLocaleString("update_btn")+"\" ></td></tr>");
                    out.println("</table>");
                    out.println("</form>");
                }
                else{
                    out.println("<p align=center class=\"box\">");
                    out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                    out.println("<TR>");
                    out.println("<TD width=\"150\" align=\"right\" class=\"datos\"> "+paramRequest.getLocaleString("Identificador")+"</TD>");
                    out.println("<TD class=\"valores\">");
                    out.println(id);
                    out.println("</TD>");
                    out.println("</TR>");
                    out.println("</TABLE></P>");
                    //System.out.println("Params --- "+strTm+", "+id);
                    TemplateGroup rGTemp = SWBContext.getWebSite(strTm).getTemplateGroup(id);//DBCatalogs.getInstance().getGrpTemplate(strTm,Integer.parseInt(id));
                    out.println("<p align=center class=\"box\">");
                    out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                    out.println("<tr ><td colspan=2><input type=hidden name=\"id\" value=\""+id+"\"></td></tr>");
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("Title")+":</td><td class=\"valores\">"+rGTemp.getTitle()+"</td></tr>");
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("Description")+":</td><td class=\"valores\">"+rGTemp.getDescription()+"</td></tr>");
                    //TODO: dateFormat()
                    out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("Last_modification")+":</td><td class=\"valores\">"+rGTemp.getUpdated()+"</td></tr>");
                    out.println("</TABLE></P>");
                }
            }
        }
    }

    /** Add and Update action of WBAGrpTemplates information
     * @param request parameters
     * @param response answer to the request
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String accion = response.getAction();
        String id = request.getParameter("id");
        User user = response.getUser();
        
        //if(user==null) user=new User(response.getTopic().getMap().getDbdata().getRepository());
        try{
            if(accion.equals("update")){
                //GrpTemplateSrv grpSrv = new GrpTemplateSrv();
               // int tmnid = 0;
                //tmnid=TopicMgr.getInstance().getTopicMap(request.getParameter("tmnid")).getDbdata().getNId();
                String strtm= request.getParameter("tmnid");
                WebSite ws = SWBContext.getWebSite(strtm);
                TemplateGroup tpl = ws.getTemplateGroup(id);
                tpl.setTitle(request.getParameter("title"));
                tpl.setDescription(request.getParameter("description"));
                tpl.setModifiedBy(user);
                //System.out.println(strtm+" --- "+id+" --- "+request.getParameter("title")+" --- " + request.getParameter("description")+" --- "+user.getId());
                //grpSrv.updateTemplateGrp(strtm,  Integer.parseInt(id), request.getParameter("title"), request.getParameter("description"), user.getId());
                
                response.setMode(response.Mode_EDIT);
                response.setRenderParameter("id",id);
                if(strtm!=null) {
                    response.setRenderParameter("tmid",strtm);
                    response.setRenderParameter("tm",strtm);
                }
                if(request.getParameter("lastTM")!=null) response.setRenderParameter("lastTM",request.getParameter("lastTM"));
                response.setRenderParameter("flag","update");
                response.setRenderParameter("tree","reload");
                //response.setMode(response.Mode_EDIT);
            }
            if(accion.equals("add")){
                String idGrpT="0";
                String strtm =null;
                try{
                    strtm = request.getParameter("tmnid");
                    WebSite ws = SWBContext.getWebSite(strtm);
                    
                    //tmnid=TopicMgr.getInstance().getTopicMap(strtm).getDbdata().getNId();
                    //TODO: Id templateGroup generator ???
                    TemplateGroup rGTemp = ws.createTemplateGroup("idTPLGrp");
                    rGTemp.setTitle(request.getParameter("title"));
                    rGTemp.setDescription(request.getParameter("description"));
                    rGTemp.setCreator(user);
                    //grpSrv.addTemplateGrp(strtm,  request.getParameter("title"), request.getParameter("description"), user.getId());
                    idGrpT=rGTemp.getId();
                    
                }
                catch(Exception e){
                    
                    log.error(response.getLocaleString("ErrorCreateTempGroup")+" WBAGrpTemplates.processAction.add",e); 
                    
                }
                response.setMode(response.Mode_EDIT);
                response.setRenderParameter("id",idGrpT);
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
