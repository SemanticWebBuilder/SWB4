/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Role;
import org.semanticwb.model.RoleRef;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
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
public class SWBARolesToTemplates extends GenericResource {
    private Logger log=SWBUtils.getLogger(SWBARolesToTemplates.class);
    private HashMap hhashmap = new HashMap();
    
    /** Creates a new instance of WBARoles2Templates*/
    public SWBARolesToTemplates() {
    }

    /** User view of WBARoles2Templates resource
     * @param request input parameters
     * @param response an answer to the request
     * @param paramRequest a list of objects (topic, user, action, ...)
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
        WebSite tm=SWBContext.getWebSite(tmsid);
        Template tmp = tm.getTemplate(request.getParameter("id"));
        String repo = tm.getUserRepository().getId();//user.getRepository();
        
        Iterator<RoleRef> iteAsoc = tmp.listRoleRefs();
        int numroles =0;
        while(iteAsoc.hasNext()){
            RoleRef rr = iteAsoc.next();
            String rol = rr.getRole().getId();
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
            //TODO: getRoleCriteria() de donde se obtiene???
            //TemplateSrv tplsrv = new TemplateSrv();
            //String modo = tplsrv.getRoleCriteria(tmp);
            String modo = "_criterial_or"; //Temporal
            
            String strCheckedAnd="";
            String strCheckedOr="";
            if(modo!=null&&modo.equals("_criterial_and")) strCheckedAnd=" checked ";
            else strCheckedOr=" checked ";
            out.println("<font class=\"datos\">"+paramRequest.getLocaleString("msgAND")+"<input type=\"radio\" name=\"evalmode\" value=\"and\" onclick=\"evaloccurrence(this.form);\" "+strCheckedAnd+">&nbsp;"+paramRequest.getLocaleString("msgOR")+"<input type=\"radio\" name=\"evalmode\" value=\"or\" onclick=\"evaloccurrence(this.form);\" "+strCheckedOr+"></font></td>");
            out.println("</tr>");
            out.println("<tr  class=\"datos\">");
            out.println("<td class=\"datos\" colspan=6><HR size=\"1\" noshade></td>");
            out.println("</tr>");
            out.println("<tr bgcolor=\"#FFFFFF\" >");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHAction")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHId")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHName")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHDescription")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHLastUpdate")+"</td>");
            out.println("</tr>");
            String name="",identifier="",update="",description="", msg="";
            Iterator<RoleRef> ittmp = tmp.listRoleRefs();
            
            WebSite tmadmin = SWBContext.getAdminWebSite();
            WebPage tpinfo = tmadmin.getWebPage("WBAd_sysi_RolesInfo");
            
            String urlTopic = tpinfo.getUrl();
            
            
            String rowColor="";
            boolean cambiaColor = true;
            while(ittmp.hasNext()){
                RoleRef rr = ittmp.next();
                String strroleid = rr.getRole().getId();
                Role rRole;
                msg = "";
                try {
                    rRole = rr.getRole();
                    name = rRole.getTitle();
                    identifier = rRole.getId();
                    //TODO: dateFormat()
                    //update = WBUtils.dateFormat(rRole.getLastupdate());
                    update = rRole.getUpdated().toString();
                    description = rRole.getDescription();
                    
                    urlResAct.setParameter("tm",request.getParameter("tm"));
                    urlResAct.setParameter("id",request.getParameter("id"));
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
                    //TODO: AdmFilterMgr.haveAccess2UserRep()
                    //if(AdmFilterMgr.getInstance().haveAccess2UserRep(user,repo)==2){
                    if(2==2){ //temporal
                        out.println("<a href=\""+urlTopic+"?tm="+repo+"&id="+identifier+"\" class=link onclick=\"javascript:wbTree_executeAction('gotopath.userreps."+repo+"."+identifier+"');wbTree_reload();\">");
                    }
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

            out.println("<tr><td align=right colspan=5><HR size=\"1\" noshade>");
            out.println("<INPUT type=\"hidden\" class=\"boton\" name=\"id\" value=\""+request.getParameter("id")+"\">");
            out.println("<INPUT type=\"hidden\" class=\"boton\" name=\"tm\" value=\""+request.getParameter("tm")+"\">");
            out.println("<INPUT type=\"submit\" class=\"boton\" name=\"agregar\" value=\""+paramRequest.getLocaleString("btnAdd")+"\">");
            out.println("</td></tr>");
            out.println("</TABLE>");
            out.println("</FORM>");
            
        }else
            if(action.equals("add")){
                UserRepository repositorio = SWBContext.getWebSite(tmsid).getUserRepository();//paramRequest.getTopic().getMap().getDbdata().getRepository();
                Iterator<Role> enumRoles=repositorio.listRoles();
                
                int faltanRoles=0;
                while (enumRoles.hasNext()) {
                    Role rRole = enumRoles.next();
                    if(!FindRole(rRole.getId())) faltanRoles++;
                }
                out.println("<form name=\"AssignRoles\" action=\"" + paramRequest.getActionUrl().setAction("add").toString() + "\" class=\"box\">");
                out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                if((faltanRoles)==0){
                    out.println("<tr bgcolor=\"#FFFFFF\">");
                    out.println("<td colspan=2 class=\"tabla\">"+paramRequest.getLocaleString("msgNoMoreRolesToAdd")+"</td>");
                    out.println("</tr>");
                }
                else{
                    if(faltanRoles>0){
                        out.println("<tr bgcolor=\"#FFFFFF\" >");
                        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgRoles")+"</td>");
                        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgDescription")+"</td>");
                        out.println("</tr>");
                        enumRoles=null;
                        enumRoles = repositorio.listRoles();
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
                out.println("<tr><td align=right colspan=2><HR size=\"1\" noshade><input type=hidden name=tm value=\""+request.getParameter("tm")+"\"><input type=hidden name=id value=\""+request.getParameter("id")+"\">");
                
                out.println("<input type=\"button\" class=\"boton\" size=\"50\" name=\"Regresar\" value=\""+paramRequest.getLocaleString("btnBack")+"\" onClick=\"javascript:history.go(-1);\">");
                
                if((faltanRoles)>0)
                    out.println("<input type=\"button\" class=\"boton\" size=\"50\" name=\"envia\" value=\""+paramRequest.getLocaleString("btnSend")+"\" onClick=\"javascript:valida(this.form);\">");
                
                out.println("</td></tr>");
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

    /** Requested action of the WBARoles2template resource
     * @param request input parameters
     * @param response an answer to the request and a list of objects (topic, user, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */ 
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
    
        String action=request.getParameter("action");
        if(action==null) action=response.getAction();
        User user = response.getUser();
        WebPage topic = response.getTopic();
        
        String tmsid = request.getParameter("tm");
        String id=request.getParameter("id");
        WebSite ws = SWBContext.getWebSite(id);
        if(action!=null&&action.equals("add")){
            //TemplateSrv tplsrv = new TemplateSrv();
            String roleid = request.getParameter("roleid");
            Template tpl = ws.getTemplate(id);
            RoleRef rr = ws.createRoleRef();
            rr.setRole(ws.getUserRepository().getRole(roleid));
            rr.setActive(true);
            tpl.addRoleRef(rr);
            tpl.setModifiedBy(user);
            //tplsrv.addRole2Template(tmsid,Integer.parseInt(id),roleid, userid);
        }
        if(action!=null&&action.equals("remove")){
            //TemplateSrv tplsrv = new TemplateSrv();
            Template tpl = ws.getTemplate(id);
            Iterator<RoleRef> iterr = tpl.listRoleRefs(); 
            while(iterr.hasNext())
            {
                RoleRef rr = iterr.next();
                if(rr.getRole().getId().equals(request.getParameter("roleid"))){
                    tpl.removeRoleRef(rr);
                    tpl.setModifiedBy(user);
                    break;
                }
            }
//            int roleid = Integer.parseInt(request.getParameter("roleid"));
//            tplsrv.removeRoleFromTemplate(tmsid,Integer.parseInt(id),roleid, userid);
        }
        if(action!=null&&action.equals("updateMode")){
            //TODO: setRoleCriteria() en donde va a quedar ????
//            TemplateSrv tplsrv = new TemplateSrv();
            String criteria = request.getParameter("evalmode");
//            Template templ = TemplateMgr.getInstance().getTemplate(tmsid,id);
//            tplsrv.setRoleCriteria(templ, criteria, userid);
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
