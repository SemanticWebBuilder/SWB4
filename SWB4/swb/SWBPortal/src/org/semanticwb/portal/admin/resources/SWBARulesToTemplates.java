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
import org.semanticwb.model.Rule;
import org.semanticwb.model.RuleRef;
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
public class SWBARulesToTemplates extends GenericResource {

    private Logger log=SWBUtils.getLogger(SWBARulesToTemplates.class);
    private HashMap hhashmap = new HashMap();
    
    /** Creates a new instance of WBARules2Templates */
    public SWBARulesToTemplates() {
    }

        /** User view, assign rules to templates
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
        
        WebPage tp=null;
        //WebSite tm=null;
        
        tp=paramRequest.getTopic();
        //if(tp!=null) tm=tp.getWebSite();
        String tmsid = request.getParameter("tm");
        Template tmp = SWBContext.getWebSite(tmsid).getTemplate(request.getParameter("id"));
        
        UserRepository repo = user.getUserRepository();
        WebSite tmadmin = SWBContext.getAdminWebSite();
        WebPage tpinfo = tmadmin.getWebPage("WBAd_sysi_RulesInfo");
        String urlTopic = tpinfo.getUrl();
        
        Iterator<RuleRef> iteAsoc = tmp.listRuleRefs();
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
            //TemplateSrv tplsrv = new TemplateSrv();
            //TODO: getRuleCriteria() ??De donde se obtiene o se guarda esto ???
            //String modo = tplsrv.getRuleCriteria(tmp);
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
            out.println("<tr bgcolor=FFFFFF class=\"datos\">");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHAction")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHId")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHTitle")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHDescription")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHLastUpdate")+"</td>");
            out.println("</tr>");
            String name="",identifier="",update="",description="", msg="";
            Iterator<RuleRef> ittmp = tmp.listRuleRefs();
            String rowColor="";
            boolean cambiaColor = true;
            while(ittmp.hasNext()){
                RuleRef rr = ittmp.next(); 
                //String strruleid = rr.getRule().getId();
                Rule rRule;
                msg = "";
                try {
                    rRule = rr.getRule();
                    name = rRule.getTitle();
                    identifier = rRule.getId();
                    //TODO: dateFormat()
                    //update = WBUtils.dateFormat(rRule.getLastupdate());
                    update = rRule.getUpdated().toString();
                    description = rRule.getDescription();
                    
                    urlResAct.setParameter("tm",request.getParameter("tm"));
                    urlResAct.setParameter("id",request.getParameter("id"));
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
                    if(2==2){ //TOD: Temporal, después cambiarlo por el comentado arriba
                        out.println("<a href=\""+urlTopic+"?tm="+rRule.getWebSite().getId()+"&id="+identifier+"\" class=link onclick=\"javascript:wbTree_executeAction('gotopath."+rRule.getWebSite().getId()+".rules."+identifier+"');\">");
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
            
            out.println("");
            out.println("<tr><td align=right colspan=5><HR size=\"1\" noshade>");
            out.println("<INPUT type=\"hidden\" name=\"id\" value=\""+request.getParameter("id")+"\">");
            out.println("<INPUT type=\"hidden\" name=\"tm\" value=\""+request.getParameter("tm")+"\">");
            out.println("<INPUT type=\"submit\" name=\"agregar\" value=\""+paramRequest.getLocaleString("btnAdd")+"\" class=\"boton\">");
            out.println("</td></tr>");
            out.println("</TABLE>");
            out.println("</FORM>");
            
        }else
            if(action.equals("add")){
                //String repositorio = paramRequest.getTopic().getMap().getDbdata().getRepository();
                Iterator<Rule> enumRules=SWBContext.getWebSite(tmsid).listRules();
                
                int faltanRules=0;
                while (enumRules.hasNext()) {
                    Rule rRule = enumRules.next();
                    if(!FindRule(rRule.getId())) faltanRules++;
                }
                out.println("<form name=\"AssignRules\" action=\"" + paramRequest.getActionUrl().setAction("add").toString() + "\" class=\"box\">");
                out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                if((faltanRules)==0){
                    out.println("<tr bgcolor=\"#FFFFFF\" class=\"datos\">");
                    out.println("<td class=\"tabla\" colspan=2>"+paramRequest.getLocaleString("msgNoMoreRulesToAdd")+"</th>");
                    out.println("</tr>");
                }
                else{
                    if(faltanRules>0){
                        out.println("<tr bgcolor=\"#FFFFFF\" class=\"datos\">");
                        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgRules")+"</th>");
                        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgDescription")+"</th>");
                        out.println("</tr>");
                        enumRules=null;
                        enumRules = SWBContext.getWebSite(tmsid).listRules();
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

                out.println("<tr ><td align=right colspan=2><HR size=\"1\" noshade>");
                out.println("<input type=hidden name=tm value=\""+request.getParameter("tm")+"\"><input type=hidden name=id value=\""+request.getParameter("id")+"\">");
                
                out.println("<input type=\"button\" size=\"50\" class=\"boton\" name=\"Regresar\" value=\""+paramRequest.getLocaleString("btnBack")+"\" onClick=\"javascript:history.go(-1);\">");
                
                if((faltanRules)>0)
                    out.println("<input type=\"button\" class=\"boton\" size=\"50\" name=\"envia\" value=\""+paramRequest.getLocaleString("btnSend")+"\" onClick=\"javascript:valida(this.form);\">");
                
                out.println("</td></tr>");
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

        /** Assign or unassign rules to templates
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
        //WebPage topic = response.getTopic();
        
        String tmsid = request.getParameter("tm");
        WebSite ws = SWBContext.getWebSite(tmsid);
        String id=request.getParameter("id");
        
        if(action!=null&&action.equals("add")){
            String ruleid = request.getParameter("ruleid");
            Rule rule = ws.getRule(ruleid);
            Template tpl = ws.getTemplate(id);
            RuleRef rr = ws.createRuleRef();
            rr.setRule(rule);
            tpl.addRuleRef(rr);
            tpl.setModifiedBy(user);
        }
        if(action!=null&&action.equals("remove")){
            String ruleid = request.getParameter("ruleid");
            Template tpl = ws.getTemplate(id);
            Iterator<RuleRef> ite_rr = tpl.listRuleRefs();
            while(ite_rr.hasNext())
            {
                RuleRef rr = ite_rr.next();
                if(rr.getRule().getId().equals(ruleid))
                {
                    tpl.removeRuleRef(rr);
                    tpl.setModifiedBy(user);
                    break;
                }
            }
        }
        if(action!=null&&action.equals("updateMode")){
            
            String criteria = request.getParameter("evalmode");
            Template templ = ws.getTemplate(id);
            //TODO: setRuleCriteria() ?? En donde va a quedar esto y de donde se obtiene ??
            //tplsrv.setRuleCriteria(templ, criteria, userid);   
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
