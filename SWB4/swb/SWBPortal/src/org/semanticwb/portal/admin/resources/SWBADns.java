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
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.Dns;
import org.semanticwb.model.SWBContext;

import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author juan.fernandez
 */
public class SWBADns extends GenericResource{

    private Logger log=SWBUtils.getLogger(SWBADns.class);
    //TODO: Falta implementar árbol para mostrar WebPages existentes del sitio
    //Tree tree=new Tree();
    
    /** Nombre del recurso */    
    public String strRscType=SWBADns.class.getName();

    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        Dns rec = null;
        //TopicMap tm = null;
        WebSite tm = null;
        //Topic tp = null;
        WebPage tp=null;
        String webpath=SWBPlatform.getContextPath();
        String path1=""+webpath+"wbadmin/wbadns/images/";
        String strSel=request.getParameter("sel");
        String strConfirm=request.getParameter("confirm");
        String strStep=request.getParameter("step");
        String strTm=request.getParameter("tm");
        String strDNSId=request.getParameter("id");
        String act=request.getParameter("act");
        String s_oldname = null;
        String s_dnsname = null;
        String s_tp = null;
        String s_tm = null;
        String s_tmdns = null;
        String iDNSId="0";
        boolean iDefecto=false;
        boolean b_exist = false;

        if(act==null) act="";
        if (strDNSId!=null && !strDNSId.equals("") && !strDNSId.equals("null")){
            iDNSId=strDNSId;
        }

        if (request.getParameter("id")!=null && !request.getParameter("id").equals("")){
            rec=tm.getDns(iDNSId);
        }

        if (request.getParameter("defecto_dns")!=null && request.getParameter("defecto_dns").equals("on")){
            iDefecto=true;
        }

        if(strConfirm != null){
            if(strConfirm.equals("preadd")){
                s_oldname = request.getParameter("old_dns");
                s_dnsname = request.getParameter("nom_dns");
                s_tp = request.getParameter("reptp");
                s_tm = request.getParameter("reptm");
                s_tmdns = request.getParameter("tm");
                out.println("<script language=\"JavaScript\" type=\"text/JavaScript\"> ");
                out.println("   function confirm(){");
                out.println("      window.document.DNSConfirm.submit();");
                out.println("   }");
                out.println("</script>");
                out.println("<div class=\"box\">");
                out.println("<table width=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" bgcolor=\"\"> ");
                out.println("<form name=\"DNSConfirm\" method=\"post\" action=\""+ paramRequest.getActionUrl() + "\"> ");
                out.println("<tr class=\"datos\">");
                out.println("<td width=\"150\">&nbsp;</td>");
                out.println("<td>"+paramRequest.getLocaleString("frmDNSExists1"));
                if(s_oldname != null) out.println(s_oldname + "&nbsp;");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr class=\"datos\">");
                out.println("<td width=\"150\">&nbsp;<br><br></td>");
                out.println("<td>"+paramRequest.getLocaleString("frmDNSExists2")+"</td>");
                out.println("</tr>");
                out.println("<tr class=\"datos\">");
                out.println("<td width=\"150\">&nbsp;<br><br><br></td>");
                out.println("<td align=\"center\">");
                out.println("<input type=\"button\" class=\"boton\" name=\"Aceptar\" onClick=\"javascript:history.back();\" value="+paramRequest.getLocaleString("btnBack")+">&nbsp;");
                out.println("<input type=\"button\" class=\"boton\" name=\"Aceptar\" onClick=\"javascript:confirm();\" value="+paramRequest.getLocaleString("btnConfirm")+">");
                out.println("</td>");
                out.println("</tr>");
                out.println("<input type=hidden name=\"act\" value=\"add\">");
                out.println("<input type=hidden name=\"over\" value=\"1\">");
                out.println("<input type=hidden name=\"defecto_dns\" value=\"on\">");
                out.println("<input type=hidden name=\"nom_dns\" value=\"" + s_dnsname +"\">");
                out.println("<input type=hidden name=\"reptp\" value=\"" + s_tp +"\">");
                out.println("<input type=hidden name=\"reptm\" value=\"" + s_tm +"\">");
                out.println("<input type=hidden name=\"tm\" value=\"" + s_tmdns +"\">");//defecto_dns
                out.println("</table> ");
                out.println("</form>");
                return;
            }
            if(strConfirm.equals("removed")) {
                out.println("<script>wbTree_remove();</script>");
                out.println(paramRequest.getLocaleString("frmDNSRemoved"));
                return;
            }
            if(strConfirm.equals("updated")){
                out.println("<script>");
                out.println("wbTree_executeAction('gotopath."+strTm+".dns."+strDNSId+"');");
                out.println("wbTree_reload();");
                out.println("</script>");
            }
            if(strConfirm.equals("notupdated")){
                out.println("<font face=\"Verdana\" size=\"1\">");
                out.println(paramRequest.getLocaleString("frmDNSnotUpdated"));
                out.println("</font>");
            }
            if(strConfirm.equals("added")){
                out.println("<script>");
                out.println("wbTree_executeAction('gotopath."+strTm+".dns');");
                out.println("wbTree_reload();");
                out.println("wbTree_executeAction('gotopath."+strTm+".dns."+strDNSId+"');");
                out.println("</script>");
            }
            if(strConfirm.equals("notadded")){
                out.println("<font face=\"Verdana\" size=\"1\">");
                out.println(paramRequest.getLocaleString("frmDNSnotAdded"));
                out.println("</font>");
                return;
            }
        }

        out.println("<form name=\"DNS\" method=\"post\" action=\"\"> ");
        // Gets javascript code
        getJavaScript(paramRequest,response);
        //Shows tree for select section
        if (strStep!=null && strStep.equals("map")){
                out.println("<div class=box>");
                out.println("<table width=\"100%\" border=\"0\" cellpadding=\"4\" cellspacing=\"1\" bgcolor=\"\"> ");
                out.println("<tr> ");
                out.println("<tr class=\"datos\">");
                out.println("<td width=\"150\" align=\"right\">&nbsp;</td> ");
                out.println("<td>");
                if (strDNSId!=null && !strDNSId.equals("") && !strDNSId.equals("null")) {
                    request.setAttribute("reptm",rec.getWebSite().getId());
                    request.setAttribute("reptp",rec.getWebPage().getId());
                }
                if(strTm!=null) request.setAttribute("tm",strTm);
                //TODO: falta implentar arbol para mostrar WebPages
                //tree.getHtml(request, response, paramRequest.getUser(), tp, request.getArguments(),tm.getHomePage().getId());
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("<div>");
        }
        else{
            if(act != null){
                String strMap=request.getParameter("reptm");
                String strTopic=request.getParameter("reptp");
                if(strSel==null){
                    if((act.equals("") || act.equals("edit"))){
                        tm = SWBContext.getWebSite(strMap);
                        if(tm != null){
                            tp=tm.getWebPage(strTopic);
                            b_exist = true;
                        }
                    }
                }
                else{
                    if(act.equals("") || act.equals("edit")){
                        tm = SWBContext.getWebSite(strMap);
                        if(tm != null){
                            tp=tm.getWebPage(strTopic);
                            b_exist = true;
                        }
                    }
                }

                if(act.equals("remove") && (strDNSId!=null && !strDNSId.equals("")) ) {
                    out.println("<input type=hidden name=id value=\""+strDNSId+"\"> \n");
                    out.println("<input type=hidden name=tm value=\""+strTm+"\"> \n");
                    out.println("<input type=hidden name=act value=\""+act+"\"> \n");
                    out.println("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
                    out.println("send();");
                    out.println("</script> \n");
                }

                out.println("<div class=\"box\">");
                out.println("<table width=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" bgcolor=\"\"> ");
                if(act.equals("")){
                    if(b_exist){
                        out.println("<tr class=\"datos\">");
                        out.println("<td width=\"150\" align=\"right\">"+paramRequest.getLocaleString("frmDNStoRelate")+":</td> ");
                        out.println("<td>"+rec.getId()+"</td> ");
                        out.println("</tr>");
                        out.println("<tr class=\"datos\"> ");
                        out.println("<td align=\"right\" width=\"150\">"+paramRequest.getLocaleString("frmTopictoRelate")+":</td> ");
                        out.println("<td>"+tp.getTitle()+"</td> ");
                        out.println("</tr> ");
                        //Check box
                        if(strTm != null){
                            if(strTm.equals("WBAGlobal")){
                                out.println("<tr class=\"datos\"> ");
                                out.println("<td width=\"150\" align=\"right\" >"+paramRequest.getLocaleString("frmDefaultDns")+":</td> ");
                                if(rec.isDefault()){
                                    out.println("<td><input name=\"defecto_dns\" type=\"checkbox\" class=\"combo1\" disabled=\"true\" checked></td> ");
                                }
                                if(!rec.isDefault()){
                                    out.println("<td><input name=\"defecto_dns\" type=\"checkbox\" class=\"combo1\" disabled=\"true\"></td> ");
                                }
                                out.println("</tr> ");
                            }
                        }
                        out.println("</table> ");
                        out.println("<input type=hidden name=reptm value=\""+tm.getId()+"\">");
                        out.println("<input type=hidden name=reptp value=\""+tp.getId()+"\"> ");
                    }
                    else{
                        out.println("<tr bgcolor=\"\" class=\"datos\"> ");
                        out.println("<td align=\"center\"><font color=\"red\">"+paramRequest.getLocaleString("frmNot_Exist")+"</font></td> ");
                        out.println("</tr>");
                        out.println("</table> ");
                    }
                }
                if(act.equals("edit")){
                    if(b_exist){
                        out.println("<tr bgcolor=\"\" class=\"datos\"> ");
                        out.println("<td width=\"150\" align=\"right\">"+paramRequest.getLocaleString("frmDNStoRelate")+":</td> ");
                        out.println("<td><input name=\"nom_dns\" type=\"text\" class=\"combo1\" size=\"50\" maxlength=\"40\" value=\""+rec.getTitle()+"\"></td>");
                        out.println("</tr>");
                        out.println("<tr class=\"datos\"> ");
                        out.println("<td align=\"right\" width=\"150\">"+paramRequest.getLocaleString("frmTopictoRelate")+":</td> ");
                        out.println("<td>"+tp.getDisplayName());
                        out.println("</td> ");
                        out.println("</tr> ");
                        // Check box
                        if(strTm != null){
                            if(strTm.equals("WBAGlobal")){
                                out.println("<tr class=\"datos\"> ");
                                out.println("<td width=\"150\" align=\"right\" >"+paramRequest.getLocaleString("frmDefaultDns")+":</td> ");
                                if(rec.isDefault()){
                                    out.println("<td><input name=\"defecto_dns\" type=\"checkbox\" class=\"combo1\" checked></td> ");
                                }
                                if(!rec.isDefault()){
                                    out.println("<td><input name=\"defecto_dns\" type=\"checkbox\" class=\"combo1\"></td> ");
                                }
                                out.println("</tr> ");
                            }
                        }
                        out.println("<tr align=\"center\" bgcolor=\"\" class=\"datos\"> ");
                        out.println("<td colspan=\"2\">");
                        out.println("<input type=button  class=\"boton\" name=\"Aceptar\" onClick='javascript:send();' value="+paramRequest.getLocaleString("btnSave")+">&nbsp;");
                        out.println("<input type=\"button\"  class=\"boton\" name=\"mostrar\" onClick=\"javascript:map();\" value="+paramRequest.getLocaleString("btnTree")+">");
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("</table> ");
                        out.println("<input type=hidden name=reptm value=\""+tm.getURI()+"\">");
                        out.println("<input type=hidden name=reptp value=\""+tp.getURI()+"\"> ");
                    }
                    else{
                        out.println("<tr bgcolor=\"\" class=\"datos\"> ");
                        out.println("<td align=\"center\"><font color=\"red\">"+paramRequest.getLocaleString("frmNot_Exist")+"</font></td> ");
                        out.println("</tr>");
                        out.println("</table> ");
                    }
                }
                if(act.equals("add")){
                    out.println("<tr class=\"datos\">");
                    out.println("<td width=\"150\" align=\"right\">"+paramRequest.getLocaleString("frmDNStoRelate")+":</td> ");
                    if(request.getParameter("nom_dns")!=null){
                        out.println("<td><input name=\"nom_dns\" type=\"text\" class=\"combo1\" size=\"50\" maxlength=\"40\" value=\""+request.getParameter("nom_dns")+"\"></td> ");
                    }
                    else{
                        out.println("<td><input name=\"nom_dns\" type=\"text\" class=\"combo1\" size=\"50\" maxlength=\"40\"></td> ");
                    }
                    out.println("</tr>");
                    out.println("<tr class=\"datos\"> ");
                    out.println("<td align=\"right\" width=\"150\">"+paramRequest.getLocaleString("frmTopictoRelate")+":</td> ");
                    out.println("<td>");
                    if(strTopic==null){
                        out.println(paramRequest.getLocaleString("frmChooseTopic"));
                    }
                    else{
                        out.println("<input type=hidden name=seltp value=\"1\">");
                        tm=SWBContext.getWebSite(strMap);//TopicMgr.getInstance().getTopicMap(strMap);
                        
                        tp=tm.getWebPage(strTopic);//tm.getTopic(strTopic);
                        out.println(tp.getTitle() );//DisplayName()
                    }
                    out.println("</tr>");
                    //Check box
                    if(strTm != null){
                        if(strTm.equals("WBAGlobal")){
                            out.println("<tr class=\"datos\"> ");
                            out.println("<td width=\"150\" align=\"right\" >"+paramRequest.getLocaleString("frmDefaultDns")+":</td> ");
                            out.println("<td><input name=\"defecto_dns\" type=\"checkbox\" class=\"combo1\"></td>");
                            out.println("</tr> ");
                        }
                    }
                    out.println("<tr align=\"center\" bgcolor=\"\" class=\"datos\"> ");
                    out.println("<td colspan=\"2\"> ");
                    out.println("<input type=button  class=\"boton\" name=\"Aceptar\" onClick='javascript:send();' value="+paramRequest.getLocaleString("btnSave")+">&nbsp;");
                    out.println("<input type=\"button\" class=\"boton\" name=\"mostrar\" onClick=\"javascript:map();\" value="+ paramRequest.getLocaleString("btnTree") +">");
                    out.println("</td> ");
                    out.println("</tr> ");
                    out.println("</table> ");
                    if (strSel != null){
                        out.println("<input type=hidden name=reptm value=\""+strMap+"\">");
                        out.println("<input type=hidden name=reptp value=\""+strTopic+"\"> ");
                    }
                }
                out.println("<input type=hidden name=act value=\""+act+"\"> ");
                out.println("<input type=hidden name=step value=\"\"> ");
                out.println("<input type=hidden name=tm value=\""+strTm+"\"> ");
                out.println("<input type=hidden name=id value=\""+strDNSId+"\"> ");
                out.println("</div>");
            }
        }
        out.println("</form> ");
    }

    
    /**
     * @param request
     * @param response
     * @throws SWBResourceException
     * @throws IOException
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String strDns=request.getParameter("nom_dns");
        String strTpSel=request.getParameter("reptp");
        String strTmSel=request.getParameter("reptm");
        String strTmDNS=request.getParameter("tm");
        String act=request.getParameter("act");
        String strOver = request.getParameter("over");
        
        WebSite ws = SWBContext.getWebSite(strTmSel);
        
        Dns rec=null;
        String iIdDns="0";
        boolean iDefecto=false;
        try{
            if(request.getParameter("id")!=null && !request.getParameter("id").equals("")&& !request.getParameter("id").equals("null"))
                iIdDns=request.getParameter("id");
            if (request.getParameter("defecto_dns")!=null && request.getParameter("defecto_dns").equals("on"))
                iDefecto=true;
            if(act != null){
                if(act.equals("add")){
                    Dns rec_def = SWBContext.getWebSite(strTmDNS).getDefaultDns();
                    ws.getDns(act).isDefault();
                    if(iDefecto){
                        //It is a default DNS
                        if(rec_def == null | strOver != null){
                            try{
                                rec = ws.createDns(strDns);
                                rec.setDefault(iDefecto);
                                rec.setWebPage(ws.getWebPage(strTpSel));
                                rec.setCreator(response.getUser());
                                response.setRenderParameter("confirm","added");
                                response.setRenderParameter("id",rec.getId());
                            }
                            catch(Exception e){
                                response.setRenderParameter("confirm","not Added");
                            }
                        }
                        else{
                            response.setRenderParameter("nom_dns",strDns);
                            response.setRenderParameter("reptp",strTpSel);
                            response.setRenderParameter("reptm",strTmSel);
                            response.setRenderParameter("tm",strTmDNS);
                            response.setRenderParameter("confirm","preadd");
                            response.setRenderParameter("old_dns",rec_def.getId());
                        }
                    }
                    else{
                        //It is not a default DNS
                        try{
                            rec = ws.createDns(strDns);
                            rec.setDefault(iDefecto);
                            rec.setWebPage(ws.getWebPage(strTpSel));
                            rec.setCreator(response.getUser());
                            response.setRenderParameter("confirm","added");
                            response.setRenderParameter("id",rec.getId());
                        }
                        catch(Exception e){
                            response.setRenderParameter("confirm","not Added");
                        }
                    }
                }
                if(act.equals("edit")) {
                    try
                    {
                        rec = ws.getDns(iIdDns);
                        //rec.setURI(strDns);
                        rec.setDefault(iDefecto);
                        rec.setWebPage(ws.getWebPage(strTpSel));
                        rec.setModifiedBy(response.getUser());
                        response.setRenderParameter("confirm","updated");
                        response.setRenderParameter("id",iIdDns);
                    }
                    catch(Exception e) {
                        response.setRenderParameter("confirm","notupdated");
                        response.setRenderParameter("id",iIdDns);
                    }
                }
                if(act.equals("remove")) {
                    try
                    {
                        SWBContext.getWebSite(strTmDNS).removeDns(iIdDns);
                        response.setRenderParameter("confirm","removed");
                        response.setRenderParameter("status","true");
                    }
                    catch(Exception e) {
                        response.setRenderParameter("id",iIdDns);
                    }
                }
            }
            response.setRenderParameter("tm",strTmDNS);
            response.setRenderParameter("act","view");
            response.setMode(response.Mode_VIEW);
        }
        catch (Exception e){
            log.error("Error on method processAction() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(), e);
        }
    }


   /**
    * @param paramRequest
    * @param response
    * @throws IOException
    */   
    public void getJavaScript(SWBParamRequest paramRequest, HttpServletResponse response) throws IOException{
        PrintWriter out=response.getWriter();
        SWBResourceURL urlResAct=paramRequest.getActionUrl();

        try {
            out.println("<script language=\"JavaScript\" type=\"text/JavaScript\"> ");
            out.println("function send() {");
            out.println("    if (document.DNS.act.value!='remove') {");
            out.println("       if (!validateForm(document.DNS,document.DNS.id.value))");
            out.println("           return;");
            out.println("       if (document.DNS.seltp!=undefined) {");
            out.println("           document.DNS.action = '"+urlResAct+"';");
            out.println("           document.DNS.submit();");
            out.println("       }");
            out.println("       else {");
            out.println("           if (document.DNS.act.value=='edit') {");
            out.println("               document.DNS.action = '"+urlResAct+"';");
            out.println("               document.DNS.submit();");
            out.println("           }");
            out.println("       else");
            out.println("           alert ('"+paramRequest.getLocaleString("jsTopictoAssociate")+"');");
            out.println("       }");
            out.println("   }");
            out.println("   else {");
            out.println("       document.DNS.action = '"+urlResAct+"';");
            out.println("       document.DNS.submit();");
            out.println("   }");
            out.println("}");

            out.println("function map() {");
            out.println("    document.DNS.step.value = 'map';");
            out.println("    document.DNS.submit();");
            out.println("}");

            out.println(" function validateForm(_f,id) {");
            out.println("    var _regExp=/select/i;");
            out.println("    for (i=0; i<_f.elements.length; i++ ) { ");
            out.println("        if(_f.elements[i]!=undefined) {");
            // Valida los objetos de la forma tipo text o textarea
            out.println("            if(_f.elements[i].type==\"text\" || _f.elements[i].type==\"textarea\") {");
            out.println("                if (_f.elements[i].value=='') {");
            out.println("                    alert ('"+paramRequest.getLocaleString("jsEmptyField")+"');");
            out.println("                    _f.elements[i].focus();");
            out.println("                    return false;");
            out.println("                }");
            out.println("            }");
            out.println("        }");
            out.println("    }");
            out.println("    return true;");
            out.println(" }");
            out.println("</script>");
        }
        catch (Exception e){
            log.error( "Error on method getJavaScript() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(), e);
        }
    }
    
}
