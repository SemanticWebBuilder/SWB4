/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import org.semanticwb.model.Portlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Role;
import org.semanticwb.model.RoleRef;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author juan.fernandez
 */
public class SWBARolesToPortlets extends GenericResource {

    private Logger log=SWBUtils.getLogger(SWBARolesToPortlets.class);
    private HashMap hhashmap = new HashMap();
    
    /** Creates a new instance of WBARoles2Resources */
    public SWBARolesToPortlets() {
    }

    
    /** User view of WBARoles2Resources Portlet
     * @param request input parameters
     * @param response an answer to the request
     * @param paramRequest a list of objects (WebPage, user, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */ 
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
    
        PrintWriter out=response.getWriter();
        String action=paramRequest.getAction();
        
        User user=paramRequest.getUser();
        SWBResourceURL urlResAct=paramRequest.getActionUrl();
        
        String tmsid = request.getParameter("tm");
        String id = request.getParameter("id");
        if(tmsid==null) tmsid = paramRequest.getTopic().getWebSiteId();
        WebSite tm = SWBContext.getWebSite(tmsid);
        String repo = tm.getUserRepository().getId();//user.getRepository();
        
        Portlet rec = tm.getPortlet(id);
        
        Iterator iteAsoc = rec.listRoleRefs();
        int numroles =0;
        while(iteAsoc.hasNext()){
            String rol = ((Integer) iteAsoc.next()).toString();
            hhashmap.put(rol,rol);
            numroles++;
        }
        
        if(action==null) action="roles";
        if(!action.equals("add")){
            out.println("<script>");
            out.println("function evaloccurrence(forma)");
            out.println(" {");
            out.println("   forma.action='"+paramRequest.getActionUrl().setAction("updateMode").toString()+"';");
            out.println("   forma.submit();");
            out.println(" }");
            
            out.println("</script>");
            out.println("<FORM name=\"descr\" action=\""+paramRequest.getRenderUrl().setAction("add").toString()+"\" class=\"box\">");
            out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<tr  class=\"datos\">");
            out.println("<td colspan=6 ><font class=\"tabla\">"+paramRequest.getLocaleString("msgEvalMode")+": </font>");
            //ResourceSrv resrv = new ResourceSrv();
            //TODO: getRoleCriteria
            //String modo = resrv.getRoleCriteria(rec);
            String modo = "_criterial_or";
            
            String strCheckedAnd="";
            String strCheckedOr="";
            if(modo!=null&&modo.equals("_criterial_and")) strCheckedAnd=" checked ";
            else strCheckedOr=" checked ";
            out.println("<font class=\"datos\">"+paramRequest.getLocaleString("msgAND")+"<input type=\"radio\" name=\"evalmode\" value=\"and\" onclick=\"evaloccurrence(this.form);\" "+strCheckedAnd+">&nbsp;"+paramRequest.getLocaleString("msgOR")+"<input type=\"radio\" name=\"evalmode\" value=\"or\" onclick=\"evaloccurrence(this.form);\" "+strCheckedOr+"></font></td>");
            out.println("</tr>");
            out.println("<tr  class=\"datos\">");
            out.println("<td class=\"datos\" colspan=6><HR size=\"1\" noshade></td>");
            out.println("</tr>");
            out.println("<tr  class=\"datos\">");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHAction")+"</th>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHId")+"</th>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHName")+"</th>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHDescription")+"</th>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHLastUpdate")+"</th>");
            out.println("</tr>");
            String name="",identifier="",update="",description="", msg="";
            Iterator<RoleRef> ittmp = rec.listRoleRefs();
            
            WebSite tmadmin = SWBContext.getAdminWebSite();
            WebPage tpinfo = tmadmin.getWebPage("WBAd_sysi_RolesInfo");
            
            String urlTopic = tpinfo.getUrl();
 
            String rowColor="";
            boolean cambiaColor = true;
            while(ittmp.hasNext()){
                RoleRef rr = ittmp.next();
                String strroleid = rr.getId();
                Role rRole;
                msg = "";
                try {
                    rRole = tm.getUserRepository().getRole(strroleid);
                    name = rRole.getTitle();
                    identifier = rRole.getId();
                    //TODO: dateFormat()
                    update = rRole.getUpdated().toString();
                    description = rRole.getDescription();
                    
                    urlResAct.setParameter("tm",tmsid);
                    urlResAct.setParameter("id",id);
                    urlResAct.setParameter("roleid",String.valueOf(rRole.getId()));
                    urlResAct.setParameter("action","remove");
                    
                    rowColor="#EFEDEC";
                    if(!cambiaColor) rowColor="#FFFFFF";
                    cambiaColor = !(cambiaColor);
                    
                    out.println("<tr bgcolor=\""+rowColor+"\">");
                    out.println("<td class=\"valores\">");
                    out.println("<a href=\""+urlResAct.toString()+"\" class=\"link\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("msgAlertShureRemoveRole") +"?'))return true; else return false;\"><img border=0 src=\""+SWBPlatform.getContextPath()+"wbadmin/images/eliminar.gif\" alt=\""+paramRequest.getLocaleString("msgErase")+"\"></a>");
                    out.println("</td>");
                    out.println("<td class=\"valores\">" + identifier + "</td>");
                    out.println("<td class=\"valores\">");
                    boolean acceso = false;
                    
                    //TODO: Falta implementar AdminFilterManager.haveAccess2UserRep
//                    if(AdmFilterMgr.getInstance().haveAccess2UserRep(user,repo)==2){
//                        out.println("<a href=\""+urlTopic+"?tm="+repo+"&id="+identifier+"\" class=link onclick=\"javascript:wbTree_executeAction('gotopath.userreps."+repo+"."+identifier+"');wbTree_reload();\">");
//                    }
                    out.println( name);
                    if(acceso){ 
                        out.println("</a>");
                    }
                    out.println("</td>");
                    out.println("<td class=\"valores\">" + description + "</td>");
                    out.println("<td class=\"valores\">" + update + "</td>");
                    out.println("</tr>");
                } catch (Exception e) {log.error(e); }
            }
            
            out.println("<TR bgcolor=\"#FFFFFF\">");
            out.println("<TD colspan=\"6\" bgcolor=\"#FFFFFF\" align=\"right\"><HR size=\"1\" noshade>");
            out.println("<INPUT type=\"hidden\" name=\"id\" value=\""+id+"\">");
            out.println("<INPUT type=\"hidden\" name=\"tm\" value=\""+tmsid+"\">");
            out.println("<INPUT type=\"submit\" class=\"boton\" name=\"agregar\" value=\""+paramRequest.getLocaleString("btnAdd")+"\">");
            out.println("</TD>");
            out.println("</TR>");
            out.println("</TABLE>");
            out.println("</FORM>");
            
        }else
            if(action.equals("add")){
                //String repositorio = tm.getUserRepository().getId();
                Iterator<Role> enumRoles=tm.getUserRepository().listRoles();
                
                int faltanRoles=0;
                while (enumRoles.hasNext()) {
                    Role rRole = enumRoles.next();
                    if(!FindRole(rRole.getId())) faltanRoles++;
                }
                out.println("<form name=\"AssignRoles\" action=\"" + paramRequest.getActionUrl().setAction("add").toString() + "\" class=\"box\">");
                out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                if((faltanRoles)==0){
                    out.println("<tr  class=\"datos\">");
                    out.println("<td colspan=2 class=\"tabla\">"+paramRequest.getLocaleString("msgNoMoreRolesToAdd")+"</td>");
                    out.println("</tr>");
                }
                else{
                    if(faltanRoles>0){
                        out.println("<tr  class=\"datos\">");
                        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgRoles")+"</td>");
                        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgDescription")+"</td>");
                        out.println("</tr>");
                        enumRoles=null;
                        enumRoles = tm.getUserRepository().listRoles();
                        String rowColor="";
                        boolean cambiaColor = true;
                        while (enumRoles.hasNext()) {
                            Role rRole = enumRoles.next();
                            if(!FindRole(rRole.getId())){
                                rowColor="#EFEDEC";
                                if(!cambiaColor) rowColor="#FFFFFF";
                                cambiaColor = !(cambiaColor);
                                out.println("<tr bgcolor=\""+rowColor+"\" valign=\"top\">");
                                out.println("<td align=left valign=\"top\" class=\"datos\">");
                                out.println("<input type=\"radio\" style=\"background:"+rowColor+";\" name=\"roleid\" value=\"" + rRole.getId() + "\">" + rRole.getTitle() );
                                out.println("</td>");
                                out.println("<td align=left class=\"datos\">" + rRole.getDescription() + "</td>");
                                out.println("</tr>");
                            }
                        }
                    }
                    
                }
                
                out.println("<tr>");
                out.println("<td colspan=\"4\" align=\"right\" class=\"datos\"><HR size=\"1\" noshade><input type=hidden name=tm value=\""+tmsid+"\"><input type=hidden name=id value=\""+id+"\">");
                
                out.println("<input type=\"button\" size=\"50\" class=\"boton\" name=\"Regresar\" value=\""+paramRequest.getLocaleString("btnBack")+"\" onClick=\"javascript:history.go(-1);\">");
                
                if((faltanRoles)>0)
                    out.println("<input type=\"button\" size=\"50\" class=\"boton\" name=\"envia\" value=\""+paramRequest.getLocaleString("btnSend")+"\" onClick=\"javascript:valida(this.form);\">");
                
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</form>");
                out.println("<Script Language=\"JavaScript\">");
                out.println("   function valida(forma) { ");
                out.println("          var flag=false;");
                out.println("          var tot_campos = forma.elements.length;");
                out.println("          for (j=0; j < tot_campos; j++){");
                out.println("              if(forma.elements[j].name==\"roleid\"){");
                out.println("                 if(forma.elements[j].checked==true && forma.elements[j].value!=\"\"){");
                out.println("                     flag=true;");
                out.println("                     break;");
                out.println("                 }");
                out.println("              }");
                out.println("         }");
                out.println("         if(flag){");
                out.println("           forma.submit();");
                out.println("         }");
                out.println("         else {");
                out.println("           alert('"+paramRequest.getLocaleString("msgAlertSelectRoleFromList")+ "');");
                out.println("         }");
                out.println("    }");
                out.println("</Script>");
                
            }
        hhashmap.clear();
        
    }

      /**Process the requested action of the WBARoles2Resources Portlet
     * @param request input parameters
     * @param response an answer to the request and a list of objects (WebPage, user, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */ 
   @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
    
        String action=request.getParameter("action");
        if(action==null) action=response.getAction();
        //String userid = response.getUser().getId();
        //WebPage tp = response.getTopic();
        User user = response.getUser();
        String tmsid = request.getParameter("tm");
        String id= request.getParameter("id");
        WebSite ws = SWBContext.getWebSite(tmsid);
        Portlet res = ws.getPortlet(id);
        String roleid = "0";
        if(request.getParameter("roleid")!=null)
            roleid = request.getParameter("roleid");
        
        if(action!=null&&action.equals("add")){
            //ResourceSrv rSrv = new ResourceSrv();
            //rSrv.addRole2Resource( tmsid,  id, roleid, TopicMgr.getInstance().getTopicMap(tmsid).getDbdata().getRepository(), userid );
            Role rol = ws.getUserRepository().getRole(roleid);
            
            RoleRef rref = ws.createRoleRef();
            rref.setRole(rol);
            
            res.addRoleRef(rref);
            res.setModifiedBy(user);
            
        }
        if(action!=null&&action.equals("remove")){
            
            ws.removeRoleRef(roleid);
            ws.setModifiedBy(user);
            
        }
        if(action!=null&&action.equals("updateMode")){
            //TODO: falta implementa setRoleCriteria
//            ResourceSrv resrv = new ResourceSrv();
            String criteria = request.getParameter("evalmode");
            //resrv.setRoleCriteria(res, criteria, userid);
            
        }
        response.setRenderParameter("tm",tmsid);
        response.setRenderParameter("id",id);
        response.setAction("roles");
    }
    
    /** Find a Role assigned to the actual template
     * @param id :Role identifier
     * @return :true or false, if the role exists in the hashmap
     */
    private boolean FindRole(String id) {
        boolean regresa = false;
        if(hhashmap.get(id)!=null) {
            regresa = true;
        }
        return regresa;
    }
 
    
}
