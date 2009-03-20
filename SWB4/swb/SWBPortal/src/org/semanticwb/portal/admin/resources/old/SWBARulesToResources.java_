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
import org.semanticwb.model.Resource;
import org.semanticwb.model.Rule;
import org.semanticwb.model.RuleRef;
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
public class SWBARulesToResources extends GenericResource {

    private Logger log = SWBUtils.getLogger(SWBARulesToResources.class);
    private HashMap hhashmap = new HashMap();
    
    /** Creates a new instance of WBARoles2Resources */
    public SWBARulesToResources() {
    }

    /** User view, assign rules to resources
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
        String id = request.getParameter("id");
        
        if(tmsid==null) tmsid = paramRequest.getTopic().getWebSiteId();
        WebSite tm = SWBContext.getWebSite(tmsid);
        
        Resource rec = tm.getResource(id);
        
        String repo = user.getUserRepository().getId();
        WebSite tmadmin = SWBContext.getAdminWebSite();
        WebPage tpinfo = tmadmin.getWebPage("WBAd_sysi_RulesInfo");
        String urlTopic = tpinfo.getUrl();
        
        Iterator<RuleRef>  iteAsoc = rec.listRuleRefs();
        int numrules =0;
        while(iteAsoc.hasNext()){
            RuleRef rr = iteAsoc.next();
            String rule = rr.getRule().getId();
            hhashmap.put(rule,rule);
            numrules++;
        }
        
        if(action==null) action="rules";
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
            //TODO: getRuleCriteria(rec)
            //String modo = resrv.getRuleCriteria(rec);
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
            out.println("<tr  class=\"datos\">");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHAction")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHId")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHTitle")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHDescription")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHLastUpdate")+"</td>");
            out.println("</tr>");
            String name="",identifier="",update="",description="", msg="";
            Iterator<RuleRef> ittmp = rec.listRuleRefs();
            String rowColor="";
            boolean cambiaColor = true;
            while(ittmp.hasNext()){
                RuleRef rr = ittmp.next();
                String strruleid = rr.getRule().getId();
                Rule rRule;
                msg = "";
                try {
                    rRule = SWBContext.getWebSite(tmsid).getRule(strruleid);
                    name = rRule.getTitle();
                    identifier = rRule.getId();
                    //TODO: dateFormat()
                    //update = WBUtils.dateFormat(rRule.getLastupdate());
                    update = rRule.getUpdated().toString();
                    description = rRule.getDescription();
                    
                    urlResAct.setParameter("tm",tmsid);
                    urlResAct.setParameter("id",id);
                    urlResAct.setParameter("ruleid",String.valueOf(rRule.getId()));
                    urlResAct.setParameter("action","remove");
                    rowColor="#EFEDEC";
                    if(!cambiaColor) rowColor="#FFFFFF";
                    cambiaColor = !(cambiaColor);
                    out.println("<tr bgcolor=\""+rowColor+"\">");
                    out.println("<td class=\"valores\">");
                    out.println("<a href=\""+urlResAct.toString()+"\" class=\"link\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("msgAlertShureRemoveRule") +"?'))return true; else return false;\"><img border=0 src=\""+SWBPlatform.getContextPath()+"wbadmin/images/eliminar.gif\" alt=\""+paramRequest.getLocaleString("msgErase")+"\"></a>");
                    out.println("</td>");
                    out.println("<td class=\"valores\">" + identifier + "</td>");
                    out.println("<td class=\"valores\">");
                    boolean acceso = false;
                    //TODO: AdmFilterMgr.getInstance().haveAccess2Rules(user,rRule.getTopicMapId())
                    //if(AdmFilterMgr.getInstance().haveAccess2Rules(user,rRule.getTopicMapId())==2){
                    if(2==2){
                        out.println("<a href=\""+urlTopic+"?tm="+rRule.getWebSite().getId()+"&id="+rRule.getId()+"\" class=link onclick=\"javascript:wbTree_executeAction('gotopath."+rRule.getWebSite().getId()+".rules."+rRule.getId()+"');\">");
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
                //String repositorio = tm.getDbdata().getRepository();
                WebSite ws = SWBContext.getWebSite(tmsid);
                Iterator<Rule> enumRules=ws.listRules();
                
                int faltanRules=0;
                while (enumRules.hasNext()) {
                    Rule rRule = enumRules.next();
                    if(!FindRule(rRule.getId())) faltanRules++;
                }
                out.println("<form name=\"AssignRules\" action=\"" + paramRequest.getActionUrl().setAction("add").toString() + "\" class=\"box\">");
                out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                if((faltanRules)==0){
                    out.println("<tr  class=\"datos\">");
                    out.println("<td colspan=2 class=\"tabla\">"+paramRequest.getLocaleString("msgNoMoreRulesToAdd")+"</td>");
                    out.println("</tr>");
                }
                else{
                    if(faltanRules>0){
                        out.println("<tr  class=\"datos\">");
                        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgRules")+"</td>");
                        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgDescription")+"</td>");
                        out.println("</tr>");
                        enumRules=null;
                        enumRules = ws.listRules();
                        String rowColor="";
                        boolean cambiaColor = true;
                        while (enumRules.hasNext()) {
                            Rule rRule = enumRules.next();
                            if(!FindRule(rRule.getId())){
                                rowColor="#EFEDEC";
                                if(!cambiaColor) rowColor="#FFFFFF";
                                cambiaColor = !(cambiaColor);
                                out.println("<tr bgcolor=\""+rowColor+"\" valign=\"top\">");
                                out.println("<td align=left valign=\"top\" class=\"datos\">");
                                out.println("<input type=\"radio\" style=\"background:"+rowColor+";\" name=\"ruleid\" value=\"" + rRule.getId() + "\">" + rRule.getTitle() );
                                out.println("</td>");
                                out.println("<td align=left class=\"datos\">" + rRule.getDescription() + "</td>");
                                out.println("</tr>");
                            }
                        }
                    }
                    
                }
                
                out.println("<tr>");
                out.println("<td colspan=\"4\" align=\"right\" class=\"datos\"><HR size=\"1\" noshade><input type=hidden name=tm value=\""+tmsid+"\"><input type=hidden name=id value=\""+id+"\">");
                
                out.println("<input type=\"button\" size=\"50\"  class=\"boton\" name=\"Regresar\" value=\""+paramRequest.getLocaleString("btnBack")+"\" onClick=\"javascript:history.go(-1);\">");
                
                if((faltanRules)>0)
                    out.println("<input type=\"button\"  class=\"boton\" size=\"50\" name=\"envia\" value=\""+paramRequest.getLocaleString("btnSend")+"\" onClick=\"javascript:valida(this.form);\">");
                
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</form>");
                out.println("<Script Language=\"JavaScript\">");
                out.println("   function valida(forma) { ");
                out.println("          var flag=false;");
                out.println("          var tot_campos = forma.elements.length;");
                out.println("          for (j=0; j < tot_campos; j++){");
                out.println("              if(forma.elements[j].name==\"ruleid\"){");
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
                out.println("           alert('"+paramRequest.getLocaleString("msgAlertSelectRuleFromList")+ "');");
                out.println("         }");
                out.println("    }");
                out.println("</Script>");
                
            }
        hhashmap.clear();
        
    }

    /** Assign, unassign rules to resources
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
        WebSite ws = SWBContext.getWebSite(tmsid);
        String id= request.getParameter("id");
        String ruleid ="0";
        if(request.getParameter("ruleid")!=null)
            ruleid = request.getParameter("ruleid");
        
        if(action!=null&&action.equals("add")){
            //ResourceSrv rSrv = new ResourceSrv();
            Resource p = ws.getResource(id);
            RuleRef rr = ws.createRuleRef();
            Rule rule = ws.getRule(ruleid);
            rr.setRule(rule);
            rr.setActive(true);
            p.addRuleRef(rr);
            p.setModifiedBy(user);
            //rSrv.addRule2Resource( tmsid,  id, ruleid, userid );
        }
        if(action!=null&&action.equals("remove")){
            //ResourceSrv rSrv = new ResourceSrv();
            Resource p = ws.getResource(id);
            
            Iterator<RuleRef> ite_rref = p.listRuleRefs();
            while(ite_rref.hasNext())
            {
                RuleRef rr = ite_rref.next();
                if(rr.getRule().getId().equals(ruleid))
                {
                    p.removeRuleRef(rr);
                    p.setModifiedBy(user);
                    break;
                }
            }
            //rSrv.removeRuleFromResource(tmsid, id, ruleid, userid);
        }
        if(action!=null&&action.equals("updateMode")){
            //ResourceSrv resrv = new ResourceSrv();
            String criteria = request.getParameter("evalmode");
            Resource res = ws.getResource(id);
            //TODO: setRuleCriteria() ?? en donde se va a guardar y obtener esto ??
            //resrv.setRuleCriteria(res, criteria, userid);
        }
        response.setRenderParameter("tm",tmsid);
        response.setRenderParameter("id",id);
        response.setAction("rules");
    }
    
    /** Find a Rule assigned to the actual template
     * @param id :Rule identifier
     * @return :true or false, if the rule exists in the hashmap
     */
    private boolean FindRule(String id) {
        boolean regresa = false;
        if(hhashmap.get(id)!=null) {
            regresa = true;
        }
        return regresa;
    }
    
}
