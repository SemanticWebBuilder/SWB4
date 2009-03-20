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
public class SWBARulesToWebPage extends GenericResource {

    private Logger log=SWBUtils.getLogger(SWBARulesToWebPage.class);
    private HashMap hhashmap = new HashMap();
    private String webpath = SWBPlatform.getContextPath();
    
    /** Creates a new instance of WBARulesAssigned */
    public SWBARulesToWebPage()
    {
    }

        /** User view, assign rules to topic
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
        WebSite tm=null;
        
        tp=paramRequest.getTopic();
        if(tp!=null) tm=tp.getWebSite();
        
        UserRepository repo = user.getUserRepository();
        WebSite tmadmin = SWBContext.getAdminWebSite();
        WebPage tpinfo = tmadmin.getWebPage("WBAd_sysi_RulesInfo");
        String urlTopic = tpinfo.getUrl();
        
        Iterator<RuleRef> iteOcc= tp.listRuleRefs();//tp.getOccurrencesOfType("CNF_WBRule");
        int numrules =0;
        int no_parent=0;
        int activas=0;
        while(iteOcc.hasNext())
        {
            RuleRef occ = iteOcc.next();
            if(occ!=null)
            {
                hhashmap.put(occ.getRule().getId(),occ);
                if(occ.isActive()) activas++;
            }
            //TODO: Parent , no parent ??
            //if(occ.getResourceRef()!=null && occ.getResourceRef().equals("_noparent")) no_parent++;
            numrules++;
        }
        
        //System.out.println("no_parent (num):"+no_parent);
        
        if(action==null) action="rules";
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
            out.println("<td  colspan=6><font class=\"tabla\">"+paramRequest.getLocaleString("msgEvalMode")+": </font>");
            //TopicSrv tpsrv = new TopicSrv();
            //TODO: getRuleCriteria() ?? en donde se guarda y de donde se obtiene esto ???
            //String modo = tpsrv.getRuleCriteria(tp);
            String modo = ""; // Temporal
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
            //System.out.println("modo: "+modo);
            out.println("<font class=\"datos\">"+paramRequest.getLocaleString("msgAND")+
            "<input type=\"radio\" name=\"evalmode\" value=\"and\" onclick=\"evaloccurrence(this.form);\" "+strCheckedAnd+">&nbsp;"+paramRequest.getLocaleString("msgOR")+
            "<input type=\"radio\" name=\"evalmode\" value=\"or\" onclick=\"evaloccurrence(this.form);\" "+strCheckedOr+"></font>" +
            checkHeredar +" </td>");
            out.println("</tr>");
            out.println("<tr  class=\"datos\">");
            out.println("<td class=\"datos\" colspan=6><HR size=\"1\" noshade></td>");
            out.println("</tr>");
            out.println("<tr  class=\"tabla\">");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHAction")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHId")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHTitle")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHDescription")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHLastUpdate")+"</td>");
            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgTHStatus")+"</td>");
            out.println("</tr>");
            String name="",identifier="",update="",description="";
            int status=0;
            Iterator tpocurren = hhashmap.keySet().iterator();
            String msg = "";
            if(activas==0&&no_parent==0&&!paramRequest.getTopic().getWebSite().getHomePage().equals(paramRequest.getTopic())) //hhashmap.isEmpty()&&!paramRequest.getTopic().getMap().getHome().equals(paramRequest.getTopic())
            {
                msg = paramRequest.getLocaleString("msgFromFather");
                out.println("<tr>");
                out.println("<td colspan=5 class=\"datos\">" +paramRequest.getLocaleString("msgInheritRules") + " " + msg + "</td>");
                out.println("</tr>");
                
                //TODO: Revisar getConfigData() ???
                tpocurren = tp.listRuleRefs();//getConfigData("CNF_WBRule");
                int numhereda=0;
                String rowColor="";
                boolean cambiaColor = true;
                while (tpocurren.hasNext())
                {
                    String strruleid = (String) tpocurren.next();
                    Rule rRule;
                    msg = "";
                    try
                    {
                        rRule = tm.getRule(strruleid);
                        name = rRule.getTitle();
                        identifier = rRule.getId();
                        //TODO: dateFormat()
                        //update = WBUtils.dateFormat(rRule.getLastupdate());
                        update = rRule.getUpdated().toString();
                        description = rRule.getDescription();
                        rowColor="#EFEDEC";
                        if(!cambiaColor) rowColor="#FFFFFF";
                        cambiaColor = !(cambiaColor);
                        out.println("<tr bgcolor=\""+rowColor+"\">");
                        out.println("<td class=\"valores\">&nbsp;&nbsp;&nbsp;&nbsp;</td>");
                        out.println("<td class=\"valores\" align=center>" + identifier + "</td>");
                        out.println("<td class=\"valores\">");
                        boolean acceso = false;
                        //TODO: AdmFilterMgr.getInstance().haveAccess2Rules(user,rRule.getTopicMapId())
                        //if(AdmFilterMgr.getInstance().haveAccess2Rules(user,rRule.getTopicMapId())==2)
                        if(2==2) // Temporal, quitar despues de que se tenga lo anterior
                        {
                            out.println("<a href=\""+urlTopic+"?tm="+rRule.getWebSite().getId()+"&id="+identifier+"\" class=link onclick=\"javascript:wbTree_executeAction('gotopath."+rRule.getWebSite().getId()+".rules."+identifier+"');\">");
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
                        numhereda++;
                    } catch (Exception e)
                    {log.error(e); }
                }
                
                
            }
            
            if(!hhashmap.isEmpty())
            {
                msg = paramRequest.getLocaleString("msgSectionRules");//"Reglas de la sección";
                out.println("<tr>");
                out.println("<td colspan=5 class=\"datos\">" + msg + "</td>");
                out.println("</tr>");
                tpocurren = hhashmap.keySet().iterator();
                String rowColor="";
                boolean cambiaColor = true;
                while (tpocurren.hasNext())
                {
                    String strruleid = (String) tpocurren.next();
                    RuleRef ocurre = (RuleRef) hhashmap.get(strruleid) ;
                    Rule rRule;
                    msg = "";
                    try
                    {
                        rRule = ocurre.getRule();
                        name = rRule.getTitle();
                        identifier = rRule.getId();
                        //TODO: dateFormat()
                        //update = WBUtils.dateFormat(rRule.getLastupdate());
                        update = rRule.getUpdated().toString();
                        description = rRule.getDescription();
                        
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
                        //if(AdmFilterMgr.getInstance().haveAccess2Rules(user,rRule.getTopicMapId())==2)
                        if(2==2) //Temporal, despues cambiar cuando este listo lo anterior
                        {
                            out.println("<a href=\""+urlTopic+"?tm="+rRule.getWebSite().getId()+"&id="+identifier+"\" class=link onclick=\"javascript:wbTree_executeAction('gotopath."+rRule.getWebSite().getId()+".rules."+identifier+"');\">");
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
                        String altImagen = paramRequest.getLocaleString("msgUnActive");
                        if(ocurre.isActive())
                        {
                            imagen = "negro.gif";
                            altImagen=paramRequest.getLocaleString("msgActive");
                        }
                        SWBResourceURL urlStatus = paramRequest.getActionUrl();
                        urlStatus.setParameter("action","status");
                        urlStatus.setParameter("ruleid",String.valueOf(rRule.getId()));
                        urlStatus.setParameter("status",Boolean.toString(ocurre.isActive()));
                        
                        out.println("<td class=\"valores\" align=center><a href=\""+urlStatus.toString()+"\" class=\"link\" ><img src=\""+webpath+"wbadmin/images/"+imagen+"\" border=0 alt=\""+altImagen+"\"></a></td>");
                        out.println("</tr>");
                    } catch (Exception e)
                    {log.error(e); }
                }
            }
            
            out.println("<TR bgcolor=\"#FFFFFF\">");
            out.println("<TD colspan=\"6\" bgcolor=\"#FFFFFF\" align=\"right\"><HR size=\"1\" noshade>");
            out.println("<INPUT type=\"submit\" name=\"agregar\" class=\"boton\" value=\""+paramRequest.getLocaleString("btnAdd")+"\">");
            out.println("</TD>");
            out.println("</TR>");
            out.println("</TABLE>");
            out.println("</FORM>");
            
        }else
            if(action.equals("add"))
            {
                //boolean flag = false;
                //boolean flag1=false;
                //UserRepository repositorio = paramRequest.getTopic().getWebSite().getUserRepository();
                Iterator<Rule> enumRules=tm.listRules(); //global
                
                int faltanRules=0;
                while (enumRules.hasNext())
                {
                    Rule rRule = enumRules.next();
                    if(!FindRule(rRule.getId())) faltanRules++;
                }
                out.println("<form name=\"AssignRules\" action=\"" + paramRequest.getActionUrl().setAction("add").toString() + "\" class=\"box\">");
                out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                if((faltanRules)==0)
                {
                    out.println("<tr  class=\"datos\">");
                    out.println("<td colspan=2 class=\"tabla\">"+paramRequest.getLocaleString("msgNoMoreRulesToAdd")+"</td>");
                    out.println("</tr>");
                }
                else
                {
                    if(faltanRules>0)
                    {
                        out.println("<tr  >");
                        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgRules")+"</td>");
                        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("msgDescription")+"</td>");
                        out.println("</tr>");
                        enumRules=null;
                        enumRules = tm.listRules();
                        String rowColor="";
                        boolean cambiaColor = true;
                        while (enumRules.hasNext())
                        {
                            Rule rRule = enumRules.next();
                            if(!FindRule(rRule.getId()))
                            {
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
                out.println("<td colspan=\"4\" align=\"right\" class=\"datos\"><HR size=\"1\" noshade>");
                
                out.println("<input type=\"button\" size=\"50\" class=\"boton\" name=\"Regresar\" value=\""+paramRequest.getLocaleString("btnBack")+"\" onClick=\"javascript:history.go(-1);\">");
                
                if((faltanRules)>0)
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

        /** Assign or unassign rules to topics
     * @param request input parameters
     * @param response an answer to the request and a list of objects (topic, user, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action=response.getAction();
        if(action==null) action=request.getParameter("action");
        User user = response.getUser();
        WebPage topic = response.getTopic();
        if(action!=null&&action.equals("add"))
        {
            String ruleid = request.getParameter("ruleid");
            WebSite ws = topic.getWebSite();
            Rule rule = ws.getRule(ruleid);
            RuleRef rr = ws.createRuleRef();
            rr.setActive(true);
            rr.setRule(rule);
            topic.addRuleRef(rr);
            topic.setModifiedBy(user);
            //tpsrv.addRule2Topic(ruleid, topic, userid);
        }
        if(action!=null&&action.equals("remove"))
        {
            String ruleid = request.getParameter("ruleid");
            WebSite ws = topic.getWebSite();
            Iterator<RuleRef> ite_rr= topic.listRuleRefs();
            while(ite_rr.hasNext())
            {
                RuleRef rr = ite_rr.next();
                if(rr.getRule().getId().equals(ruleid))
                {
                    topic.removeRuleRef(rr);
                    topic.setModifiedBy(user);
                    break;
                }
            }
        }
        if(action!=null&&action.equals("status"))
        {
            
            String ruleid = request.getParameter("ruleid");
            boolean activo = request.getParameter("status")!=null&&request.getParameter("status").equals("1")?false:true;
            WebSite ws = topic.getWebSite();
            Iterator<RuleRef> ite_rr= topic.listRuleRefs();
            while(ite_rr.hasNext())
            {
                RuleRef rr = ite_rr.next();
                if(rr.getRule().getId().equals(ruleid))
                {
                    rr.setActive(activo);
                    topic.setModifiedBy(user);
                    break;
                }
            }
            //tpsrv.changeStatusRule2Topic(ruleid, activo, topic, userid);
        }
        if(action!=null&&action.equals("updateMode"))
        {
            //TopicSrv tpsrv = new TopicSrv();
            String criteria = request.getParameter("evalmode");
            //TODO: setRuleCriteria()
            //tpsrv.setRuleCriteria(topic, criteria, userid);
            
        }
        
        WebSite tm = topic.getWebSite();
        if(action!=null && action.equals("_noparent"))
        {
            //TODO: Herencia ??? Como se va a manejar
//            Occurrence occ = new Occurrence(tm.getTopic("CNF_WBRule"),null);
//            occ.setResourceRef("_noparent");
//            topic.addOccurrence(occ);
            //tm.update2DB();
        }
        
        
        if(action!=null && action.equals("_parent"))
        {
            Iterator<RuleRef> itParent=topic.listRuleRefs();    //getOccurrencesOfType("CNF_WBRule");
            while(itParent.hasNext())
            {
                RuleRef occparent=itParent.next();
                //TODO: Herencia ??? Como se va a manejar
//                if(occparent.isResourceRef() && occparent.getResourceRef().equals("_noparent"))
//                {
//                    topic.removeOccurrence(occparent);
//                    //tm.update2DB();
//                }
            }
        }
        
        
        response.setAction("rules");
    }
    
    /** Find a Rule assigned to the actual topic
     * @param id :Rule identifier
     * @return :true or false, if the rule exists in the hashmap
     */
    private boolean FindRule(String id)
    {
        boolean regresa = false;
        if(hhashmap.get(id)!=null)
        {
            regresa = true;
        }
        return regresa;
    }
}
