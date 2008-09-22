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
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/** Muestra la lista de roles que deben de tener los usuarios para poder ver está
 * sección, permite agregar o eliminar roles del tópico.
 *
 * It shows the list of roles that must have the users to be able to see the
 * section, allows to add or to eliminate roles of the WebPage.
 * @author Juan Antonio Fernández Arias
 */
public class SWBARolesAssigned extends GenericResource{

    private Logger log=SWBUtils.getLogger(SWBARolesAssigned.class);
    private HashMap hhashmap = new HashMap();
    private String webpath = SWBPlatform.getContextPath();
    
    /** Creates a new instance of WBARolesAssigned */
    public SWBARolesAssigned()
    {
    }

    /** User view of WBARolesAssigned resource
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
        
        WebPage tp=null;
        WebSite tm=null;
        
        tp=paramRequest.getTopic();
        if(tp!=null) tm=tp.getWebSite();
        
        Iterator iteOcc= tp.listRoleRefs(); //getOccurrencesOfType("CNF_WBRole");
        int numroles =0;
        int no_parent=0;
        int activas=0;
        while(iteOcc.hasNext())
        {
            RoleRef occ = (RoleRef) iteOcc.next();
            if(occ!=null)
            {
                hhashmap.put(occ.getRole().getId(),occ);
                if(occ.isActive()) activas++;
            }
            //TODO: Revisar
            //if(occ.getResourceRef()!=null && occ.getResourceRef().equals("_noparent")) no_parent++;
            numroles++;
        }
        
        String repo = tm.getUserRepository().getId();//user.getRepository();
        WebSite tmadmin = SWBContext.getAdminWebSite();
        WebPage tpinfo = tmadmin.getWebPage("WBAd_sysi_RolesInfo");
        String urlWebPage = tpinfo.getUrl();
        
        if(action==null) action="roles";
        if(!action.equals("add"))
        {
            out.println("<Script Language=\"JavaScript\">");
            out.println("function evaloccurrence(forma)");
            out.println(" {");
            out.println("   forma.action='"+paramRequest.getActionUrl().setAction("updateMode").toString()+"';");
            out.println("   forma.submit();");
            out.println(" }");
            out.println("function valida1(forma)");
            out.println("{");
            out.println("    if(forma.heredar.checked==false) {");
            out.println("    forma.action='"+paramRequest.getActionUrl().setAction("_noparent")+"'; }");
            out.println("    else { ");
            out.println("       forma.action='"+paramRequest.getActionUrl().setAction("_parent")+"'; ");
            out.println("    }");
            out.println("    forma.submit();");
            out.println("}");
            out.println("</Script>");
            out.println("<FORM name=\"descr\" action=\""+paramRequest.getRenderUrl().setAction("add").toString()+"\" class=\"box\">");
            out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<tr  class=\"datos\">");
            out.println("<td colspan=6 ><font class=\"tabla\">"+paramRequest.getLocaleString("msgEvalMode")+": </font>");
            //WebPageSrv tpsrv = new WebPageSrv();
            //TODO: getRoleCriteria
            //String modo = tpsrv.getRoleCriteria(tp);
            String modo = "_criterial_or";
            String strCheckedAnd="";
            String strCheckedOr="";
            
            String heredar = "checked";
            if(no_parent>0)
            {
                heredar = "";
            }
            String checkHeredar = "<br><font class=tabla>"+paramRequest.getLocaleString("msgTHInheritParent")+": </font><input type=\"checkbox\" name=\"heredar\"" + heredar+" onClick=\"javascript:valida1(this.form);\">";
            if(tp.getId().equals(tp.getWebSite().getHomePage().getId())||activas>0)
            {
                checkHeredar = "";
            }
            
            if(modo.equals("_criterial_and")) strCheckedAnd=" checked ";
            else strCheckedOr=" checked ";

            out.println("<font class=\"datos\">"
            +paramRequest.getLocaleString("msgAND")+"<input type=\"radio\" name=\"evalmode\" value=\"and\" onclick=\"evaloccurrence(this.form);\" "+strCheckedAnd+">&nbsp;"
            +paramRequest.getLocaleString("msgOR")+"<input type=\"radio\" name=\"evalmode\" value=\"or\" onclick=\"evaloccurrence(this.form);\" "+strCheckedOr+"></font>" +
            checkHeredar +"</td>");
            
            out.println("</tr>");
            out.println("<tr  class=\"datos\">");
            out.println("<td class=\"datos\" colspan=6><HR size=\"1\" noshade></td>");
            out.println("</tr>");
            out.println("<tr  class=\"datos\">");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHAction")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHId")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHName")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHDescription")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHLastUpdate")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHStatus")+"</td>");
            out.println("</tr>");
            String name="",identifier="",update="",description="";
            int status=0;
            Iterator tpocurren = hhashmap.keySet().iterator();
            
            int numheredados=0;
            String msg = "";
            if(activas==0&&no_parent==0&&!paramRequest.getTopic().getWebSite().getHomePage().equals(paramRequest.getTopic())) //hhashmap.isEmpty()&&!paramRequest.getWebPage().getMap().getHome().equals(paramRequest.getWebPage())
            {
                msg = paramRequest.getLocaleString("msgFromFather");
                
                out.println("<tr>");
                out.println("<td colspan=5 class=\"valores\">" +paramRequest.getLocaleString("msgInheritRoles") + " " + msg + "</td>");
                out.println("</tr>");
                
                //TODO: Revisar
                //
                //tpocurren = tp.getConfigData("CNF_WBRole");
                String rowColor="";
                boolean cambiaColor = true;
                while (tpocurren.hasNext())
                {
                    String strroleid = (String) tpocurren.next();
                    Role rRole;
                    msg = "";
                    try
                    {
                        rRole = tm.getUserRepository().getRole(strroleid);
                        name = rRole.getTitle();
                        identifier = rRole.getId();
                        //TODO: dateformat
                        update = rRole.getUpdated().toString();
                        description = rRole.getDescription();
                        rowColor="#EFEDEC";
                        if(!cambiaColor) rowColor="#FFFFFF";
                        cambiaColor = !(cambiaColor);
                        out.println("<tr bgcolor=\""+rowColor+"\">");
                        out.println("<td class=\"valores\">&nbsp;&nbsp;&nbsp;&nbsp;</td>");
                        out.println("<td class=\"valores\" align=center>" + identifier + "</td>");
                        out.println("<td class=\"valores\">");
                        boolean acceso = false;
                        //TODO: AdmFilterMgr.getInstance().haveAccess2UserRep(user,repo)
                        //if(AdmFilterMgr.getInstance().haveAccess2UserRep(user,repo)==2)
                        if(true) //temporal
                        {
                            out.println("<a href=\""+urlWebPage+"?tm="+repo+"&id="+identifier+"\" class=link onclick=\"javascript:wbTree_executeAction('gotopath.userreps."+repo+"."+identifier+"');wbTree_reload();\">");
                        }
                        out.println( name);
                        if(acceso)
                        {
                            out.println("</a>");
                        }
                        out.println("</td>");
                        out.println("<td class=\"valores\">" + description + "</td>");
                        out.println("<td class=\"valores\">" + update + "</td>");
                        out.println("<td class=\"valores\" align=center><img src=\""+webpath+"wbadmin/images/negro.gif\" border=0 alt=\""+paramRequest.getLocaleString("msgActive")+"\"></td>");
                        out.println("</tr>");
                        numheredados++;
                    } catch (Exception e)
                    {log.error(e); }
                }
                
            }
            if(!hhashmap.isEmpty())
            {
                msg = paramRequest.getLocaleString("msgSectionsRoles");//"Roles de la sección";
                out.println("<tr>");
                out.println("<td colspan=5 class=\"datos\">" + msg + "</td>");
                out.println("</tr>");
                tpocurren = hhashmap.keySet().iterator();
                String rowColor="";
                boolean cambiaColor = true;
                while (tpocurren.hasNext())
                {
                    String strroleid = (String) tpocurren.next();
                    RoleRef ocurre = (RoleRef) hhashmap.get(strroleid) ;
                    Role rRole;
                    msg = "";
                    try
                    {
                        rRole = tm.getUserRepository().getRole(strroleid);
                        name = rRole.getTitle();
                        identifier = rRole.getId();
                        //TODO: dateFormat()
                        update = rRole.getUpdated().toString();
                        description = rRole.getDescription();
                        
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
                        //TODO: AdmFilterMgr.getInstance().haveAccess2UserRep(user,repo)==2
                        //if(AdmFilterMgr.getInstance().haveAccess2UserRep(user,repo)==2)
                        if(true) //Temporal
                        {
                            out.println("<a href=\""+urlWebPage+"?tm="+repo+"&id="+identifier+"\" class=link onclick=\"javascript:wbTree_executeAction('gotopath.userreps."+repo+"."+identifier+"');wbTree_reload();\">");
                        }
                        out.println( name);
                        if(acceso)
                        {
                            out.println("</a>");
                        }
                        out.println("</td>");
                        out.println("<td class=\"valores\">" + description + "</td>");
                        out.println("<td class=\"valores\">" + update + "</td>");
                        String imagen = "negro2.gif";
                        String altImagen = paramRequest.getLocaleString("msgUnActive") ;
                        if(ocurre.isActive())
                        {
                            imagen = "negro.gif";
                            altImagen = paramRequest.getLocaleString("msgActive");
                        }
                        SWBResourceURL urlStatus = paramRequest.getActionUrl();
                        urlStatus.setParameter("occid",ocurre.getId());
                        urlStatus.setParameter("action","status");
                        urlStatus.setParameter("roleid",String.valueOf(rRole.getId()));
                        urlStatus.setParameter("status",ocurre.isActive()?"1":"0");
                        
                        out.println("<td class=\"valores\" align=center><a href=\""+urlStatus.toString()+"\" class=\"link\" ><img src=\""+webpath+"wbadmin/images/"+imagen+"\" border=0 alt=\""+altImagen+"\"></a></td>");
                        out.println("</tr>");
                    } catch (Exception e)
                    {log.error(e); }
                }
            }
            out.println("<TR bgcolor=\"#FFFFFF\">");
            out.println("<TD colspan=\"6\" bgcolor=\"#FFFFFF\" align=\"right\"><HR size=\"1\" noshade>");
            out.println("<INPUT type=\"submit\" class=\"boton\" name=\"agregar\" value=\""+paramRequest.getLocaleString("btnAdd")+"\">");
            out.println("</TD>");
            out.println("</TR>");
            out.println("</TABLE>");
            out.println("</FORM>");
            
        }else
            if(action.equals("add"))
            {
                //boolean flag = false;
                //boolean flag1=false;
                String repositorio = paramRequest.getTopic().getWebSite().getUserRepository().getId();
                Iterator<Role> enumRoles=paramRequest.getTopic().getWebSite().getUserRepository().listRoles(); //global
                
                int faltanRoles=0;
                while (enumRoles.hasNext())
                {hhashmap.clear();
                 Role rRole = enumRoles.next();
                 if(!FindRole(rRole.getId())) faltanRoles++;
                }
                out.println("<form name=\"AssignRoles\" action=\"" + paramRequest.getActionUrl().setAction("add").toString() + "\" class=\"box\">");
                out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                if((faltanRoles)==0)
                {
                    out.println("<tr class=\"datos\">");
                    out.println("<td colspan=2 class=\"tabla\">"+paramRequest.getLocaleString("msgNoMoreRolesToAdd")+"</td>");
                    out.println("</tr>");
                }
                else
                {
                    if(faltanRoles>0)
                    {
                        out.println("<tr class=\"datos\">");
                        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgRoles")+"</td>");
                        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgDescription")+"</td>");
                        out.println("</tr>");
                        enumRoles=null;
                        enumRoles = paramRequest.getTopic().getWebSite().getUserRepository().listRoles();
                        String rowColor="";
                        boolean cambiaColor = true;
                        while (enumRoles.hasNext())
                        {
                            Role rRole = enumRoles.next();
                            if(!FindRole(rRole.getId()))
                            {
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
                out.println("<td colspan=\"4\" align=\"right\" class=\"datos\"><HR size=\"1\" noshade>");
                
                out.println("<input type=\"button\" size=\"50\" class=\"boton\" name=\"Regresar\" value=\""+paramRequest.getLocaleString("btnBack")+"\" onClick=\"javascript:history.go(-1);\">");
                
                if((faltanRoles)>0)
                    out.println("<input type=\"button\" size=\"50\" class=\"boton\" name=\"envia\" value=\""+paramRequest.getLocaleString("btnSend")+"\" onClick=\"javascript:valida(this.form);\">");
                
                
                out.println("</TD>");
                out.println("</TR>");
                out.println("</TABLE>");
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

    /** Requested action of the WBaRolesAssigned resource
     * @param request input parameters
     * @param response an answer to the request, and a list of objects (WebPage, user, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action=response.getAction();
        if(action==null) action=request.getParameter("action");
        User user = response.getUser();
        WebPage topic = response.getTopic();
        String roleid = request.getParameter("roleid");
        if(action!=null&&action.equals("add"))
        {
            //String roleid = request.getParameter("roleid");
            RoleRef rolref = topic.getWebSite().createRoleRef();
            rolref.setRole(topic.getWebSite().getUserRepository().getRole(roleid));
            rolref.setActive(true);
            topic.addRoleRef(rolref);
            topic.setModifiedBy(user);
            //tpsrv.addRole2WebPage(roleid, WebPage, userid);
        }
        if(action!=null&&action.equals("remove"))
        {
            //int roleid = Integer.parseInt(request.getParameter("roleid"));
            Role rol = topic.getWebSite().getUserRepository().getRole(roleid);
            Iterator<RoleRef> itrr = topic.listRoleRefs();
            while(itrr.hasNext())
            {
                RoleRef rr = itrr.next();
                if(rol==rr.getRole())
                {
                    topic.removeRoleRef(rr);
                    topic.setModifiedBy(user);
                    break;
                }
            }
            //tpsrv.removeRoleFromWebPage(roleid, topic, userid);
        }
        if(action!=null&&action.equals("status"))
        {
            //WebPageSrv tpsrv = new WebPageSrv();
            //int roleid = Integer.parseInt(request.getParameter("roleid"));
            int activo = Integer.parseInt(request.getParameter("status"));
            boolean active = false;
            if(activo==1) active=false;
            else active=true;
            Role rol = topic.getWebSite().getUserRepository().getRole(roleid);
            Iterator<RoleRef> itrr = topic.listRoleRefs();
            while(itrr.hasNext())
            {
                RoleRef rr = itrr.next();
                if(rol==rr.getRole())
                {
                    rr.setActive(active);
                    topic.setModifiedBy(user);
                    break;
                }
            }
            //tpsrv.changeStatusRole2WebPage(roleid, activo, WebPage, userid);
            
        }
        
        if(action!=null&&action.equals("updateMode"))
        {
            //WebPageSrv tpsrv = new WebPageSrv();
            String criteria = request.getParameter("evalmode");
            //TODO: setRoleCriteria
            //
            //tpsrv.setRoleCriteria(WebPage, criteria, userid);
            
        }
        
        //TODO: falta implementar, saber como no heredar...
//        WebSite tm = topic.getWebSite();
//        if(action!=null && action.equals("_noparent"))
//        {
//            Occurrence occ = new Occurrence(tm.getWebPage("CNF_WBRole"),null);
//            occ.setResourceRef("_noparent");
//            WebPage.addOccurrence(occ);
//            tm.update2DB();
//        }
        
        //TODO: Esto supuestamente se hace en automático, faltaría revisarlo
//        if(action!=null && action.equals("_parent"))
//        {
//            Iterator itParent=WebPage.getOccurrencesOfType("CNF_WBRole");
//            while(itParent.hasNext())
//            {
//                Occurrence occparent=(Occurrence)itParent.next();
//                if(occparent.isResourceRef() && occparent.getResourceRef().equals("_noparent"))
//                {
//                    WebPage.removeOccurrence(occparent);
//                    tm.update2DB();
//                }
//            }
//        }
        response.setAction("roles");
    }
    
    /** Find a Role assigned to the actual WebPage
     * @param id :Role identifier
     * @return :true or false, if the role exists in the hashmap
     */
    private boolean FindRole(String id)
    {
        boolean regresa = false;
        if(hhashmap.get(id)!=null)
        {
            regresa = true;
        }
        return regresa;
    }
}
