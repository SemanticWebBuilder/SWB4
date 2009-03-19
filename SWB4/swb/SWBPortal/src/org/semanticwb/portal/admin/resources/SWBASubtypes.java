/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author juan.fernandez
 */
public class SWBASubtypes extends GenericResource{

    private Logger log = SWBUtils.getLogger(SWBASubtypes.class);
    Resource base=null;
    /** Creates a new instance of WBASubtypes */
    public SWBASubtypes() {
    }

    /**
     * Muestra el html final del recurso
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out=response.getWriter();
        User user = paramRequest.getUser();
        //if(user==null) user = new User();
        
        if(request.getParameter("act")==null && request.getParameter("id")!=null) {
            ResourceSubType recSubType=SWBContext.getWebSite(request.getParameter("idsmap")).getResourceSubType(request.getParameter("id"));
            
            out.println("<p  class=box>");
            out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<TR>");
            out.println("<TD width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("id")+"</TD>");
            out.println("<TD class=\"valores\">");
            out.println(request.getParameter("id"));
            out.println("</TD>");
            out.println("</TR>");
            out.println("</TABLE></P>");
            
            out.println("<p class=box>");
            out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("title")+":</td><td class=\"valores\">"+recSubType.getTitle()+"</td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("descr")+"</td><td class=\"valores\">"+recSubType.getDescription()+"</td></tr>");
            //TODO: dateFormat()
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("lastupdate")+"</td><td class=\"valores\">"+recSubType.getUpdated().toString()+"</td></tr>");
            out.println("</TABLE></P>");
        }else if((request.getParameter("act").equals("add") || request.getParameter("act").equals("edit")) && request.getParameter("id")!=null){
            doEdit(request,response,paramRequest);
        }else if(request.getParameter("act").equals("remove") && request.getParameter("id")!=null) {
            String id="0";
            try{
                //System.out.println("Entra a borrar subtype");
                if(request.getParameter("id")!=null) id = request.getParameter("id");
                
                //SubtypeSrv stSrv=new SubtypeSrv();
                WebSite ws = SWBContext.getWebSite(request.getParameter("idsmap"));
                ws.removeResourceSubType(id);
                ws.setModifiedBy(user);
                
                //if(stSrv.removeSubType(request.getParameter("idsmap"),id, user, request)){
                if(ws.getResourceSubType(id)==null){
                    //System.out.println("Entra a borrar subtype-1");
                    out.println("<script>wbTree_remove();</script>");
                    out.println(paramRequest.getLocaleString("subtyperemoved"));
                }
                else{
                    //System.out.println("Entra a borrar subtype-2");
                    out.println("<font face=\"verdana,arial\" size=\"2\" color=\"red\">"+paramRequest.getLocaleString("nosubtyperemoved")+":"+id+", "+paramRequest.getLocaleString("nosubtyperemoved2")+".</font>");
                }
            }
            catch(Exception e){
                log.error("Error while trying to Delete subtype with id:"+id,e);
                out.println("<font class=\"datos\">"+paramRequest.getLocaleString("nosubtyperemoved")+":"+id+"</font>");
            }
            
        }
    }
    
    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
     @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
   
        String act = null;
        String id = request.getParameter("id");
        String tm = request.getParameter("tm");
        String idsmap=request.getParameter("idsmap");
        if(idsmap==null)idsmap=tm;
        PrintWriter out = response.getWriter();
        if(request.getParameter("act")!=null) act = request.getParameter("act");
        if(request.getParameter("tree")!=null&&request.getParameter("tree").equals("reload")){
            if(request.getParameter("flag")!=null){
                if(request.getParameter("flag").equals("add")){
                    ResourceSubType recSubType = SWBContext.getWebSite(idsmap).getResourceSubType(id);
                    //ResourceType restype=DBResourceType.getInstance().getResourceType(recSubType.getTypeMap(),recSubType.getType());
                    ResourceType restype=recSubType.getType();
                    String type=""+restype.getId();
                    if(!restype.getWebSite().getId().equals(request.getParameter("tm")))type="0"+type;
                    
                    if(restype.getResourceMode()==2){ // de tipo adversiting
                        out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tm")+".advresources."+type+"');");
                        out.println("wbTree_reload();");
                        out.println("wbTree_executeAction('gotopath."+request.getParameter("tm")+".advresources."+type+"."+id+"');</script>");
                    }else if(restype.getResourceMode()==3){ // de tipo sistema
                        out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tm")+".sysresources."+type+"');");
                        out.println("wbTree_reload();");
                        out.println("wbTree_executeAction('gotopath."+request.getParameter("tm")+".sysresources."+type+"."+id+"');</script>");
                    }
                }
                if(request.getParameter("flag").equals("update")){
                    ResourceSubType recSubType = SWBContext.getWebSite(idsmap).getResourceSubType(id);
                    ResourceType restype=recSubType.getType();
                    String type=""+restype.getId();
                    if(!restype.getWebSite().getId().equals(request.getParameter("tm")))type="0"+type;
                    
                    if(restype.getResourceMode()==2){ // de tipo adversiting
                        out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tm")+".advresources."+ type +"."+id+"');");
                        out.println("wbTree_reload();</script>");
                    }else if(restype.getResourceMode()==3){ // de tipo sistema
                        out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tm")+".sysresources."+ type +"."+id+"');");
                        out.println("wbTree_reload();</script>");
                    }
                }
            }
        }
        out.println("<script language=javascript>");
        out.println("   function valida(forma) {");
        out.println("      var tempo = forma.title.value;");
        out.println("      trim(forma.title);");
        out.println("      if(forma.title.value==''){");
        out.println("          alert('"+paramRequest.getLocaleString("noemptytytle")+"');");
        out.println("          return (false);");
        out.println("      }");
        out.println("      forma.title.value=tempo;");
        out.println("      tempo=forma.description.value;");
        out.println("      trim(forma.description);");
        out.println("      if(forma.description.value==''){");
        out.println("          alert('"+paramRequest.getLocaleString("noemptydescr")+"');");
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
            out.println("<form name=\"forma\" onSubmit=\"return (valida(forma));\"  action=\""+paramRequest.getActionUrl().setAction("add").toString()+"\" method=\"post\" class=box>");
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<tr ><td colspan=2 class=\"tabla\">"+paramRequest.getLocaleString("msgAddSubtype")+"</td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("title")+":</td><td class=\"valores\"><input class=campos type=\"text\" name=\"title\" value=\"\" ></td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("descr")+":</td><td class=\"valores\"><input class=campos type=\"text\" name=\"description\" value=\"\"></td></tr>");
            out.println("<tr ><td colspan=2 class=\"valores\" align=\"right\"><HR noshade size=1><input class=boton type=\"submit\" value=\""+paramRequest.getLocaleString("msgBTNadd")+"\"></td></tr>");
            out.println("</table>");
            out.println("<input type=\"hidden\" name=\"id\" value=\""+id+"\">");
            out.println("<input type=\"hidden\" name=\"typemap\" value=\""+request.getParameter("typemap")+"\">");
            out.println("<input type=\"hidden\" name=\"tm\" value=\""+tm+"\">");
            out.println("</form>");
        }
        
        if((act==null&&id!=null)||(act.equals("edit")&&id!=null)){
            if(request.getParameter("id")!=null){
                id = request.getParameter("id");
                out.println("<p class=box><TABLE width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                out.println("<TR>");
                out.println("<TD width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("id")+"</TD>");
                out.println("<TD class=\"valores\">");
                out.println(id);
                out.println("</TD>");
                out.println("</TR>");
                out.println("</TABLE></P>");
                ResourceSubType recSubType = SWBContext.getWebSite(idsmap).getResourceSubType(id);
                //Modificacion en el update
                //ResourceSubType recSubType = DBCatalogs.getInstance().getSubType(typemap,Integer.parseInt(id));
                out.println("<form name=\"forma\" onSubmit=\"return (valida(forma));\"  action=\""+paramRequest.getActionUrl().setAction("update").toString()+"\" method=\"post\" class=box>");
                out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                //out.println("<tr ><td colspan=\"2\"></td></tr>");
                out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("title")+":</td><td class=\"valores\"><input class=campos type=\"text\" value=\""+recSubType.getTitle()+"\" name=\"title\"></td></tr>");
                out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("descr")+":</td><td class=\"valores\"><input class=campos type=\"text\" name=\"description\" value=\""+recSubType.getDescription()+"\"></td></tr>");
                //TODO: dateFormat()
                out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("lastupdate")+":</td><td class=\"valores\">"+recSubType.getUpdated().toString()+"</td></tr>");
                out.println("<tr ><td colspan=\"2\" align=\"right\" class=\"datos\"><HR noshade size=1><input class=boton type=\"submit\" value=\""+paramRequest.getLocaleString("msgBTNupdate")+"\"></td></tr>");
                out.println("</table>");
                out.println("<input type=\"hidden\" name=\"id\" value=\""+id+"\"><input type=\"hidden\" name=\"tm\" value=\""+tm+"\"><input type=\"hidden\" name=\"idsmap\" value=\""+idsmap+"\">");
                out.println("</form>");
            }
        }
    }

    /**
     * Operaciones del recurso
     * @param request
     * @param response
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String accion = response.getAction();
        String id = request.getParameter("id");
        String tm = request.getParameter("tm");
        String idsmap = request.getParameter("idsmap");
        WebSite ws = SWBContext.getWebSite(idsmap);
        User user = response.getUser();
        //System.out.println("idsmap:"+idsmap+" id:"+id+" tm:"+tm);
        //if(user==null) user=new User(response.getTopic().getMap().getDbdata().getRepository());
        try{
            if(accion.equals("update")){
                ResourceSubType pst = ws.getResourceSubType(id);
                pst.setTitle(request.getParameter("title"));
                pst.setDescription(request.getParameter("description"));
                pst.setModifiedBy(user);
                //subtSrv.updateSubType(idsmap,Integer.parseInt(id), request.getParameter("title"), request.getParameter("description"), user.getId());
                response.setMode(response.Mode_EDIT);
                response.setRenderParameter("id",id);
                response.setRenderParameter("idsmap",idsmap);
                response.setRenderParameter("tm",tm);
                response.setRenderParameter("flag","update");
                response.setRenderParameter("tree","reload");
            }
            else if(accion.equals("add")){
                
                ResourceSubType pst=null;
                try{
                    WebSite tpm = SWBContext.getWebSite(tm);
                    if(tpm!=null) {
                        //revisar Logica
                        pst = tpm.createResourceSubType(id);
                        pst.setCreator(user);
                        pst.setDescription(request.getParameter("description"));
                        pst.setTitle(request.getParameter("title"));
                        ResourceType pt = tpm.getResourceType(request.getParameter("typemap"));
                        pst.setType(pt);
                        //recSbt=subtSrv.createSubType(tm,Integer.parseInt(id),request.getParameter("typemap"),request.getParameter("title"), request.getParameter("description"),user.getId());
                    }
                }
                catch(Exception e){ log.error("Error while trying to create a subtype. WBASubtypes.processAction.add",e);}
                response.setMode(response.Mode_EDIT);
                response.setRenderParameter("idsmap",pst.getWebSite().getId());
                response.setRenderParameter("id",pst.getId());
                response.setRenderParameter("tm",tm);
                response.setRenderParameter("flag","add");
                response.setRenderParameter("tree","reload");
            }
        }
        catch(Exception e){log.error(e);}
    }
}
