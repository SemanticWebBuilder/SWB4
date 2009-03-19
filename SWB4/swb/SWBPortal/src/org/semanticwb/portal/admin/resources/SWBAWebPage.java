/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.portal.api.*;
//import org.semanticwb.portal.services.WebPageSrv;

/**
 *
 * @author juan.fernandez
 */
public class SWBAWebPage extends GenericResource {

    private Logger log=SWBUtils.getLogger(SWBAWebPage.class);
    Resource base=null;
    String imgPath =(String)SWBPlatform.getContextPath()+"wbadmin/";
    
    /** Creates a new instance of WebPage */
    public SWBAWebPage() {
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
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {    PrintWriter out=response.getWriter();
        //WebPageSrv tpsrv=new WebPageSrv();
        WebPage tp=paramRequest.getTopic();
        if(request.getParameter("tm")!=null && request.getParameter("tp")!=null) {
            WebSite tm=SWBContext.getWebSite(request.getParameter("tm"));
            if(tm!=null) {
                if(request.getParameter("ntp")!=null) {
                    tp=tm.getWebPage(request.getParameter("ntp"));
                }else {
                    tp=tm.getWebPage(request.getParameter("tp"));
                }
            }
        }
        //if(tp==null) tp=paramRequest.getTopic();
        if(tp==null) return;
        
        if(request.getParameter("WBAGotoTree")!=null) {
            out.print("<script>");
            out.print("wbTree_executeAction('gotonode.topic."+tp.getWebSite().getId()+"."+tp.getId()+"');");
            out.print("wbTree_reload();");
            out.println("</script>");
        }
        
        WebSite tm=tp.getWebSite();
        User user=paramRequest.getUser();
        
        String acc=request.getParameter("act");
        if(acc==null || acc.equals("view")){
            String tree=request.getParameter("tree");
            if(tree!=null && tree.equals("reload")) {
                out.println("<script>");
                if(request.getParameter("ntp")!=null) {
                    out.println("wbTree_executeAction('gotonode.topic."+tp.getWebSite().getId()+"."+tp.getParent().getId()+"');");
                    out.println("wbTree_reload();");
                    out.println("wbTree_executeAction('gotonode.topic."+tp.getWebSite().getId()+"."+tp.getId()+"');");
                    //out.println("wbTree_reload();");
                }else {
                    out.println("wbTree_executeAction('gotonode.topic."+request.getParameter("tm")+"."+request.getParameter("tp")+"');");
                    out.println("wbTree_reload();");
                }
                out.println("</script>");
            }
            
            String sfchanelsize=request.getParameter("fchanelsize");
            SWBResourceURL wbresurl=paramRequest.getRenderUrl();
            wbresurl.setMode(wbresurl.Mode_VIEW);
            wbresurl.setParameter("act","view");
            wbresurl.setParameter("fchanelsize","1");
            SWBResourceURL urlResAct=paramRequest.getActionUrl();
            out.println("<FORM name=\"info\" action=\""+urlResAct.toString()+"\" >");
            out.println("<p class=\"box\">");
            out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<TR>");
            out.println("<TD width=\"150\" align=\"right\" class=\"datos\"> "+paramRequest.getLocaleString("msgIdentifier")+"</TD>");
            out.println("<TD class=\"valores\">");
            out.println(tp.getId());
            out.println("<INPUT name=\"tpid\" type=\"hidden\" value=\""+ tp.getId()+"\">");
            out.println("<INPUT name=\"tmid\" type=\"hidden\" value=\""+ tm.getId()+"\">");
            out.println("</TD>");
            out.println("</TR>");
            out.println("</TABLE></P>");
            out.println("<P class=\"box\">");
            out.println("    <TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\">"+paramRequest.getLocaleString("msgStatus")+"</TD>");
            out.println("        <TD class=\"valores\">");
            if(tp.isActive()==true) out.println(paramRequest.getLocaleString("msgVisible"));
            else out.println(paramRequest.getLocaleString("msgInvisible"));
            out.println("        </TD>");
            out.println("      </TR>");
            
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\">"+paramRequest.getLocaleString("msgIndexer")+"</TD>");
            out.println("        <TD class=\"valores\">");
            if(tp.isIndexable()==true) out.println(paramRequest.getLocaleString("msgIndexable"));
            else out.println(paramRequest.getLocaleString("msgNotIndexable"));
            out.println("        </TD>");
            out.println("      </TR>");
            
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\">"+paramRequest.getLocaleString("topicHidden")+"</TD>");
            out.println("        <TD class=\"valores\">");
            if(tp.isHidden()==true) out.println(paramRequest.getLocaleString("msgNotHidden"));
            else out.println(paramRequest.getLocaleString("msgHidden"));
            out.println("        </TD>");
            out.println("      </TR>");
            
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgNavigationRute")+" </TD>");
            String st = "";
            HashMap arg = new HashMap();
            arg.put("separator", "\\");
            arg.put("links", "false");
            st=tp.getPath(arg);
            
            out.println("        <TD class=\"valores\">"+st+"</TD>");
            out.println("      </TR>");
            String ruta_tp=tp.getUrl();
            if(ruta_tp.startsWith("furl"))
            {
                ruta_tp = ruta_tp.substring(ruta_tp.indexOf("furl:")+5);
            }
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgAccessRute")+" </TD>");
            out.println("        <TD class=\"valores\"><a href=\"" + ruta_tp + "\" target=\"_new\">" + ruta_tp + "</A></TD>");
            out.println("      </TR>");
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgURLType")+" </TD>");
            String virtpath="";
            String tmpvpath = "";
            boolean isFriendlyURL = false;
            //TODO: Falta implementar WebPage.getSubjectIdentity()
            //if(tp.getSubjectIdentity()!=null)
            if(tp!=null)
            {
                //virtpath=""+tp.getSubjectIdentity();
                virtpath=""+tp.getId();
                if(virtpath.startsWith("furl:"))
                {
                    tmpvpath = virtpath.substring(virtpath.indexOf("furl:")+5);
                    isFriendlyURL=true;
                }
            }
            out.println("        <TD class=\"valores\" valign=\"top\">"+(!isFriendlyURL?paramRequest.getLocaleString("msgRedirect"):paramRequest.getLocaleString("msgFriendlyURL"))+"</TD>");
            out.println("      </TR>");
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgVirtuelRute")+" </TD>");
//            virtpath="";
//            if(tp.getSubjectIdentity()!=null)virtpath=""+tp.getSubjectIdentity();
            out.println("        <TD class=\"valores\">"+(!isFriendlyURL?virtpath:tmpvpath)+"</TD>");
            out.println("      </TR>");
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgHostory")+" </TD>");
            //TODO: dateFormat()
            //out.println("        <TD class=\"valores\">"+paramRequest.getLocaleString("msgCreationDate")+":&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+WBUtils.dateFormat(tp.getDbdata().getCreated()) +"<BR>");
            out.println("        <TD class=\"valores\">"+paramRequest.getLocaleString("msgCreationDate")+":&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+tp.getCreated() +"<BR>");
            //out.println("        "+paramRequest.getLocaleString("msgModificationDate")+": "+ SWBUtils.dateFormat(tp.getDbdata().getLastupdate())+"</TD>");
            out.println("        "+paramRequest.getLocaleString("msgModificationDate")+": "+ tp.getUpdated()+"</TD>");
            out.println("      </TR>");
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgZIndex")+" </TD>");
            out.println("        <TD class=\"valores\">");
            //TODO: getSortName con parámetros
            //String sort=tp.getSortName(null,false);
            String sort=tp.getSortName();
            if(sort==null)sort="";
            out.println(sort);
            out.println("        </TD>");
            out.println("      </TR>");
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgDiscSpace")+":</TD>");
            //TODO: falta implementar getWebPageHDSize
            //out.println("        <TD class=\"valores\">"+tpsrv.getTopicHDSize(tp,tp.getWebSite().getId(),0));
            out.println("        <TD class=\"valores\">"+"??.??");
            if(sfchanelsize==null) out.println("  (<a href=\""+wbresurl.toString()+"\" class=\"link\">"+paramRequest.getLocaleString("msgSpaceSize")+"</a>)");
            out.println("      </TR>");
            if(sfchanelsize!=null && sfchanelsize.equals("1")){
                out.println("      <TR>");
                out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgTotalSpaceChannel")+":</TD>");
                //TODO: falta implementar getWebPageHDSize()
                //out.println("        <TD class=\"valores\">"+tpsrv.getTopicHDSize(tp,tp.getWebSite().getId(),1)+"</TD>");
                out.println("        <TD class=\"valores\">"+"??.??"+"</TD>");
                out.println("      </TR>");
            }
            out.println("</TABLE></P>");
            out.println("</FORM>");
        }else if(acc.equals("edit")) {
            String tree=request.getParameter("tree");
            if(tree!=null && tree.equals("reload")) {
                out.println("<script>");
                if(request.getParameter("ntp")!=null) {
                    out.println("wbTree_executeAction('gotonode.topic."+tp.getWebSite().getId()+"."+tp.getParent().getId()+"');");
                    out.println("wbTree_reload();");
                    out.println("wbTree_executeAction('gotonode.topic."+tp.getWebSite().getId()+"."+tp.getId()+"');");
                }else {
                    out.println("wbTree_executeAction('gotonode.topic."+request.getParameter("tm")+"."+request.getParameter("tp")+"');");
                    out.println("wbTree_reload();");
                }
                out.println("</script>");
            }
            
            String sfchanelsize=request.getParameter("fchanelsize");
            SWBResourceURL wbresurl=paramRequest.getRenderUrl();
            wbresurl.setMode(wbresurl.Mode_VIEW);
            wbresurl.setParameter("act","view");
            wbresurl.setParameter("fchanelsize","1");
            SWBResourceURL urlResAct=paramRequest.getActionUrl();
            out.println("<FORM name=\"info\" action=\""+urlResAct.toString()+"\">");
            out.println("<P class=\"box\"><TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<TR>");
            out.println("<TD width=\"150\" align=\"right\" class=\"datos\"> "+paramRequest.getLocaleString("msgIdentifier")+"</TD>");
            out.println("<TD class=\"valores\">");
            out.println(tp.getId());
            out.println("<INPUT name=\"tpid\" type=\"hidden\" value=\""+ tp.getId()+"\">");
            out.println("<INPUT name=\"tmid\" type=\"hidden\" value=\""+ tm.getId()+"\">");
            out.println("</TD>");
            out.println("</TR>");
            out.println("</TABLE></P>");
            out.println("   <P class=\"box\">");
            out.println("    <TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\">"+paramRequest.getLocaleString("msgStatus")+"</TD>");
            out.println("        <TD class=\"valores\">");
            if(tp.isActive()==true){
                //out.println("        <INPUT name=\"status\" type=\"radio\" value=\"1\" checked>");
                out.println("<INPUT type=\"checkbox\" name=\"status\" checked>");
            }
            else {
                out.println("<INPUT type=\"checkbox\" name=\"status\">");
            }
            out.println("        </TD>");
            out.println("      </TR>");
            
            
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\">"+paramRequest.getLocaleString("msgIndexer")+"</TD>");
            out.println("        <TD class=\"valores\">");
            if(tp.isIndexable()==true){
                out.println("<INPUT type=\"checkbox\" name=\"indexable\" checked>");
                //out.println("        <INPUT name=\"indexable\" type=\"radio\" value=\"1\" checked>");
                //out.println("        <INPUT name=\"indexable\" type=\"radio\" value=\"0\">"+paramRequest.getLocaleString("msgNotIndexable"));
            }
            else {
                out.println("<INPUT type=\"checkbox\" name=\"indexable\">");
                //out.println("        <INPUT name=\"indexable\" type=\"radio\" value=\"1\">");
                //out.println("          "+paramRequest.getLocaleString("msgIndexable")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                ");
                //out.println("        <INPUT name=\"indexable\" type=\"radio\" value=\"0\" checked>"+paramRequest.getLocaleString("msgNotIndexable"));
            }
            out.println("        </TD>");
            out.println("      </TR>");
            
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\">"+paramRequest.getLocaleString("topicHidden")+"</TD>");
            out.println("        <TD class=\"valores\">");
            if(tp.isHidden()==true){
                out.println("<INPUT type=\"checkbox\" name=\"hidden\" checked>");
                //out.println("        <INPUT name=\"hidden\" type=\"radio\" value=\"1\" checked>");
                //out.println("          "+paramRequest.getLocaleString("msgHidden")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                ");
                //out.println("        <INPUT name=\"hidden\" type=\"radio\" value=\"0\">"+paramRequest.getLocaleString("msgNotHidden"));
            }
            else {
                out.println("<INPUT type=\"checkbox\" name=\"hidden\">");
                //out.println("        <INPUT name=\"hidden\" type=\"radio\" value=\"1\">");
                //out.println("          "+paramRequest.getLocaleString("msgHidden")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                ");
                //out.println("        <INPUT name=\"hidden\" type=\"radio\" value=\"0\" checked>"+paramRequest.getLocaleString("msgNotHidden"));
            }
            out.println("        </TD>");
            out.println("      </TR>");
            
            
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgNavigationRute")+" </TD>");
            String st = "";
            HashMap arg = new HashMap();
            arg.put(new String("separator"), new String("\\"));
            arg.put(new String("links"), new String("false"));
            st=tp.getPath(arg);
            out.println("        <TD class=\"valores\">"+st+"</A></TD>");
            out.println("      </TR>");
            String ruta_tp=tp.getUrl();
            if(ruta_tp.startsWith("furl"))
            {
                ruta_tp = ruta_tp.substring(ruta_tp.indexOf("furl:")+5);
            }
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgAccessRute")+" </TD>");
            out.println("        <TD class=\"valores\"><a href=\"" + ruta_tp + "\" target=\"_new\" class=\"link\">" + ruta_tp + "</A></TD>");
            out.println("      </TR>");
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" class=\"datos\" valign=\"middle\"> "+paramRequest.getLocaleString("msgURLType")+" </TD>");
            String virtpath="";
            String tmpvpath = "";
            boolean isFriendlyURL = false;
            //TODO: Falta implementar WebPage.getSubjectIdentity()
            if(tp!=null)
            {
                //virtpath=""+tp.getSubjectIdentity();
                virtpath=""+tp.getId();
                if(virtpath.startsWith("furl:"))
                {
                    tmpvpath = virtpath.substring(virtpath.indexOf("furl:")+5);
                    isFriendlyURL=true;
                }
            }
            out.println("        <TD class=\"valores\" valign=\"middle\"><table border=\"0\" class=\"valores\"><tr><td valign=\"middle\" align=\"right\">"+paramRequest.getLocaleString("msgRedirect")+"</td><td valign=\"middle\" align=\"right\"><input type=\"radio\" name=\"friendlyURL\" value=\"0\" "+(!isFriendlyURL?"checked":"")+"></td><td valign=\"middle\" align=\"right\">"+paramRequest.getLocaleString("msgFriendlyURL")+"</td><td valign=\"middle\" align=\"right\"><input type=\"radio\" name=\"friendlyURL\" value=\"1\" "+(isFriendlyURL?"checked":"")+"></td></tr></table></TD>");
            out.println("      </TR>");
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgVirtualRute")+" </TD>");
//            String virtpath="";
//            if(tp.getSubjectIdentity()!=null)virtpath=""+tp.getSubjectIdentity();
            out.println("        <TD class=\"valores\"><input type=\"text\" class=\"campos\" size=50 name=\"virtpath\" value=\""+(!isFriendlyURL?virtpath:tmpvpath)+"\"></TD>");
            out.println("      </TR>");
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgHistory")+" </TD>");
            //out.println("        <TD class=\"valores\">"+paramRequest.getLocaleString("msgCreationDate")+":&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+WBUtils.dateFormat(tp.getDbdata().getCreated()) +"<BR>");
            out.println("        <TD class=\"valores\">"+paramRequest.getLocaleString("msgCreationDate")+":&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+tp.getCreated() +"<BR>");
            //out.println("        "+paramRequest.getLocaleString("msgModificationDate")+": "+ WBUtils.dateFormat(tp.getDbdata().getLastupdate())+"</TD>");
            out.println("        "+paramRequest.getLocaleString("msgModificationDate")+": "+ tp.getUpdated()+"</TD>");
            out.println("      </TR>");
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgZIndex")+" </TD>");
            out.println("        <TD class=\"valores\">");
            //TODO: getSortName(null,false);
            //String sort=tp.getSortName(null,false);
            String sort=tp.getSortName();
            if(sort==null)sort="";
            out.println("          <INPUT name=\"sortname\" class=\"campos\" type=\"text\" value=\""+sort+"\" size=\"10\">");
            out.println("        </TD>");
            out.println("      </TR>");
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgDiscSpace")+":</TD>");
            //TODO: falta implementar getWebPageHDSize()
            //out.println("        <TD class=\"valores\">"+tpsrv.getTopicHDSize(tp,tp.getWebSite().getId(),0));
            out.println("        <TD class=\"valores\">"+"??.??");
            if(sfchanelsize==null) out.println("  (<a href=\""+wbresurl.toString()+"\" class=\"link\">"+paramRequest.getLocaleString("msgSpaceSize")+"</a>)");
            out.println("      </TR>");
            if(sfchanelsize!=null && sfchanelsize.equals("1")){
                out.println("      <TR>");
                out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgTotalSpaceChannel")+":</TD>");
                //TODO: falta implementar getWebPageHDSize()
                //out.println("        <TD class=\"valores\">"+tpsrv.getTopicHDSize(tp,tp.getWebSite().getId(),1)+"</TD>");
                out.println("        <TD class=\"valores\">"+"??.??"+"</TD>");
                out.println("      </TR>");
            }
            out.println("<TR>");
            out.println("<TD colspan=2 align=right><HR size=\"1\" noshade><INPUT type=\"submit\" class=\"boton\" name=\"enviar\" value=\""+paramRequest.getLocaleString("btnSend")+"\"></TD>");
            out.println("</TR>");
            out.println("</TABLE></P>");
            out.println("</FORM>");
        }else if(acc.equals("remove")) {
            try {
                //tpsrv.removeTopic(tp, 1, user.getId());
                tm.removeWebPage(tp.getId());
                tm.setModifiedBy(user);
                //tpsrv.removeWebPage(tm, tp.getId(), user);
                out.println("<script>wbTree_remove();</script>");
                //out.println("<font face=\"Verdana\" size=\"1\">");
                out.println(paramRequest.getLocaleString("msgSectionRemoved")+"...");
                //out.println("</font>");
            }catch(Exception e){
                //out.println("<font face=\"Verdana\" color=\"red\" size=\"1\">");
                out.println(paramRequest.getLocaleString("msgErrorRemovingSection")+"...");
                //out.println("</font>");
                log.error(e);
            }
        }else if(acc.equals("active")) {
            try {
                tp.setActive(true);
                tp.setModifiedBy(user);
                //tpsrv.setActive(tp,1,user.getId());
                out.println("<script>wbTree_reload();</script>");
                //out.println("<font face=\"Verdana\" size=\"1\">");
                out.println(paramRequest.getLocaleString("msgSectionActivated")+"...");
                //out.println("</font>");
            }catch(Exception e){
                //out.println("<font face=\"Verdana\" color=\"red\" size=\"1\">");
                out.println(paramRequest.getLocaleString("msgErrorActivatingSection")+"...");
                //out.println("</font>");
                log.error(e);
            }
        }else if(acc.equals("unactive")) {
            try {
                tp.setActive(false);
                //tpsrv.setActive(tp,0,user.getId());
                out.println("<script>wbTree_reload();</script>");
                //out.println("<font face=\"Verdana\" size=\"1\">");
                out.println(paramRequest.getLocaleString("msgSectionUnactivated")+"...");
                //out.println("</font>");
            }catch(Exception e){
                //out.println("<font face=\"Verdana\" color=\"red\" size=\"1\">");
                out.println(paramRequest.getLocaleString("msgErrorUnactivatingSection")+"...");
                //out.println("</font>");
                log.error(e);
            }
        }else if(acc.equals("add")) {
            String tpname="";
            if(request.getParameter("tpname")!=null) tpname=request.getParameter("tpname");
            SWBResourceURL wbresurl=paramRequest.getRenderUrl();
            wbresurl.setMode(wbresurl.Mode_VIEW);
            wbresurl.setParameter("act","createTp");
            out.println("<FORM name=\"createTp\" action=\""+wbresurl.toString()+"\" class=\"box\">");
            out.println("    <TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgTitle")+": </TD>");
            out.println("        <TD class=\"valores\">");
            out.println("          <INPUT name=\"tpname\" class=\"campos\" type=\"text\" size=\"30\" value=\""+tpname+"\">");
            out.println("        </TD>");
            out.println("      </TR>");
            out.println("      <TR>");
            out.println("        <TD colspan=\"2\" align=\"right\"><HR size=\"1\" noshade>");
            out.println("          <INPUT type=\"button\" class=\"boton\" name=\"enviar\" value=\""+paramRequest.getLocaleString("btnSend")+"\" onClick=\"javascript:valida(this.form);\">");
            out.println("        </TD>");
            out.println("      </TR>");
            out.println("      <INPUT name=\"tp\" type=\"hidden\" value=\""+ tp.getId()+"\">");
            out.println("      <INPUT name=\"tpcomm\" type=\"hidden\" value=\""+ request.getParameter("tpcomm")+"\">");
            out.println("      <INPUT name=\"tm\" type=\"hidden\" value=\""+ tm.getId()+"\">");
            out.println("      <INPUT name=\"act\" type=\"hidden\" value=\"createTp\">");
            out.println("    </TABLE>");
            out.println("</FORM>");
            out.println("<Script Language=\"JavaScript\">");
            out.println("function valida(forma)");
            out.println("{");
            out.println("   var flag=true;");
            out.println("   if (forma.tpname.value.replace(/^\\s*|\\s*$/g,\"\").length<1) ");
            out.println("   {");
            out.println("       alert(\"" + paramRequest.getLocaleString("alertMsgNoEmptyAllowed") + "\");");
            out.println("       forma.tpname.focus();");
            out.println("       forma.tpname.select();");
            out.println("       flag=false;");
            out.println("   }");
            out.println("   if(flag)");
            out.println("   {");
            out.println("       forma.submit();");
            out.println("   }else{return false;}");
            out.println("}");
            out.println("</Script>");
        }else if(acc.equals("createTp")){
            SWBResourceURL urlResAct=paramRequest.getActionUrl();
            urlResAct.setParameter("act","createTp");
            String tpidgen="";
            //TODO: getIdGenerator()
            //if(request.getParameter("tpname")!=null) tpidgen=TopicMgr.getInstance().getIdGenerator().getID(request.getParameter("tpname"),tm.getId(),false);
            if(request.getParameter("tpname")!=null) tpidgen=tm.getId()+"_"+request.getParameter("tpname");
            out.println("<FORM name=\"createTp\" action=\""+urlResAct.toString()+"\" class=\"box\">");
            out.println("    <TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("      <TR>");
            out.println("        <TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgIdentifier")+": </TD>");
            out.println("        <TD class=\"valores\">");
            out.println("          <INPUT name=\"tpc\" class=\"campos\" type=\"text\" size=\"50\" maxlength=\"50\"  value=\""+tpidgen+"\">");
            out.println("        </TD>");
            out.println("      </TR>");
            out.println("      <tr class=\"datos\">");
            out.println("        <td align=\"right\" class=\"valores\" colspan=\"2\"><HR size=\"1\" noshade>");
            out.println("          <input type=\"button\" class=\"boton\" value=\""+paramRequest.getLocaleString("btnPrevious")+"\" onClick=\"javaScript:send();\">");
            out.println("          <INPUT type=\"button\" class=\"boton\" name=\"enviar\" value=\""+paramRequest.getLocaleString("btnSend")+"\" onClick=\"javascript:valida(this.form);\">");
            out.println("        </TD>");
            out.println("      </TR>");
            out.println("      <INPUT name=\"tpid\" type=\"hidden\" value=\""+ tp.getId()+"\">");
            out.println("      <INPUT name=\"tmid\" type=\"hidden\" value=\""+ tm.getId()+"\">");
            out.println("      <INPUT name=\"userid\" type=\"hidden\" value=\""+paramRequest.getUser().getId() +"\">");
            out.println("      <INPUT name=\"tpname\" type=\"hidden\" value=\""+ request.getParameter("tpname")+"\">");
            out.println("      <INPUT name=\"tpcomm\" type=\"hidden\" value=\""+ request.getParameter("tpcomm")+"\">");
            out.println("      <INPUT  type=\"hidden\" name=\"act\" value=\" \">");
            out.println("    </TABLE>");
            out.println("</FORM>");
            if(request.getParameter("tpexist")!=null && request.getParameter("tpexist").equals("1")){
                out.println("<script language=\"JavaScript\">");
                out.println("  alert(\'"+paramRequest.getLocaleString("tpidexist")+"');");
                out.println("  document.createTp.tpc.focus();");
                out.println("</script>");
            }else if(request.getParameter("tpexist")!=null && request.getParameter("tpexist").equals("2")){
                out.println("<script language=\"JavaScript\">");
                out.println("  alert(\'"+paramRequest.getLocaleString("tpbadchars")+"');");
                out.println("  document.createTp.tpc.focus();");
                out.println("</script>");
            }
            SWBResourceURL wbresurl=paramRequest.getRenderUrl();
            out.println("<Script Language=\"JavaScript\">");
            out.println("function send() ");
            out.println("{ ");
            out.println("   document.createTp.action='"+wbresurl.toString()+"';");
            out.println("   document.createTp.act.value=\'add\';");
            out.println("   document.createTp.submit(); ");
            out.println("} ");
            
            out.println("function valida(forma)");
            out.println("{");
            out.println("   var flag=true;");
            
            
            out.println("   if (forma.tpc.value.replace(/^\\s*|\\s*$/g,\"\").length<1) ");
            out.println("   {");
            out.println("       alert(\"" +  paramRequest.getLocaleString("alertMsgNoEmptyAllowed")+ "\");");
            out.println("       forma.tpc.focus();");
            out.println("       forma.tpc.select();");
            out.println("       flag=false;");
            out.println("   }");
            out.println("   else if (forma.tpc.value.length>0)");
            out.println("   { ");
            out.println("       var valid=\"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_\"");
            out.println("       var ok = \"yes\";");
            out.println("       var temp;");
            out.println("       for (var i=0; i<forma.tpc.value.length; i++)");
            out.println("       {");
            out.println("           temp = \"\" + forma.tpc.value.substring(i, i+1);");
            out.println("           if (valid.indexOf(temp) == \"-1\") { ok = \"no\"; break; }");
            out.println("       }");
            out.println("       if (ok == \"no\")");
            out.println("       {");
            out.println("          alert(\""+paramRequest.getLocaleString("alertMsgInvalidCharacter")+":[0-9],[a-z],_)\");");
            out.println("          forma.tpc.focus();");
            out.println("          forma.tpc.select();");
            out.println("          flag=false;");
            out.println("       }");
            out.println("   }");
            out.println(" if(flag){");
            out.println("   forma.tpc.value = forma.tpc.value.replace(/\\s+/gm, \"_\");");
            out.println("   forma.submit();");
            out.println(" }");
            out.println("}");
            out.println("</Script>");
        }else if(acc!=null && acc.equals("copy")) {
            base=paramRequest.getResourceBase();
            String check = "",checkChilds="";
            if(request.getParameter("childs")!=null && request.getParameter("childs").equals("on")) checkChilds = "checked";
            if(request.getParameter("tpElements")!=null && request.getParameter("tpElements").equals("on")) check = "checked";
            out.println("<form name=\"forma\" action=\""+paramRequest.getRenderUrl().toString()+"\" method=\"post\">");
            out.println("<p align=center class=\"box\">");
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<tr><td colspan=2 class=\"datos\">"+paramRequest.getLocaleString("msgCopyTopic")+":"+tp.getTitle()+"</td></tr>");
            out.println("<tr><td colspan=2 class=\"datos\"><input type=\"checkbox\" name=\"childs\" " + checkChilds + ">" + paramRequest.getLocaleString("withchilds")+"</td></tr>");
            out.println("<tr><td colspan=2 class=\"datos\"><input type=\"checkbox\" name=\"tpElements\" " + check + " onClick=\"javascript:valida1(this.form);\">" + paramRequest.getLocaleString("elements"));
            out.println("<input type=\"hidden\" name=\"topicID\" value=\""+tp.getId()+"\">");
            out.println("<input type=\"hidden\" name=\"act\" value=\"copy\">");
            out.println("<tr><td>&nbsp;</td>");
            out.println("<td>");
            out.println("<table>");
            
            out.println("<tr>");
            out.println("<td class=\"datos\"><input type=\"checkbox\" name=\"contents\" "+check+">" + paramRequest.getLocaleString("contents")+"</td>");
            out.println("<td class=\"datos\"><input type=\"checkbox\" name=\"descriptions\" "+check+">" + paramRequest.getLocaleString("descriptions")+"</td>");
            out.println("<td class=\"datos\"><input type=\"checkbox\" name=\"templates\" "+check+">" + paramRequest.getLocaleString("templates")+"</td>");
            out.println("</tr>");
            
            out.println("<tr>");
            out.println("<td class=\"datos\"><input type=\"checkbox\" name=\"pflows\" "+check+">" + paramRequest.getLocaleString("pflows")+"</td>");
            out.println("<td class=\"datos\"><input type=\"checkbox\" name=\"schedules\" "+check+">" + paramRequest.getLocaleString("schedules")+"</td>");
            out.println("<td class=\"datos\"><input type=\"checkbox\" name=\"permissions\" "+check+">" + paramRequest.getLocaleString("permissions")+"</td>");
            out.println("</tr>");
            
            out.println("<tr>");
            out.println("<td class=\"datos\"><input type=\"checkbox\" name=\"rules\" "+check+">" + paramRequest.getLocaleString("rules")+"</td>");
            out.println("<td class=\"datos\"><input type=\"checkbox\" name=\"innerrules\" "+check+">" + paramRequest.getLocaleString("innerrules")+"</td>");
            out.println("<td class=\"datos\"><input type=\"checkbox\" name=\"roles\" "+check+">" + paramRequest.getLocaleString("roles")+"</td>");
            out.println("</tr>");
            
            out.println("<tr>");
            out.println("<td class=\"datos\"><input type=\"checkbox\" name=\"actions\" "+check+">" + paramRequest.getLocaleString("actions")+"</td>");
            out.println("</tr>");
            
            out.println("</table>");
            out.println("</td>");
            
            out.println("<tr ><td class=\"valores\" align=\"right\" colspan=\"2\"><HR size=\"1\" noshade><br><input type=\"button\" class=\"boton\" value=\""+paramRequest.getLocaleString("send")+"\" onClick=\"javascript:valida(this.form);\"></td></tr>");
            out.println("</table>");
            out.println("</P>");
            out.println("</form>");
            
            out.println("<Script Language=\"JavaScript\">");
            out.println("function valida1(forma)");
            out.println("{");
            out.println("  forma.submit();");
            out.println("}");
            
            out.println("function valida(forma)");
            out.println("{");
            SWBResourceURL actionUrl=paramRequest.getActionUrl();
            actionUrl.setAction("copyTopic");
            out.println("  forma.action='"+actionUrl.toString()+"';");
            out.println("  forma.submit();");
            out.println("}");
            
            out.println("</Script>");
        }if(acc!=null && acc.equals("ok")){
            out.println(paramRequest.getLocaleString("copyTopicOk"));
        }else if(acc!=null && acc.equals("Notok")){
            out.println(paramRequest.getLocaleString("NotCopyTopicOk"));
        }
        
    }

    
    /**
     *
     * @param request
     * @param response
     * @throws AFException
     * @throws IOException
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String tmid=request.getParameter("tmid");
        String tpid=request.getParameter("tpid");
        String sortname=request.getParameter("sortname");
        String virtpath=request.getParameter("virtpath");
        String friendlyURL=request.getParameter("friendlyURL");
        String ntpid=null;
        WebPage topic=null;
        User user = response.getUser();
        //WebPageSrv tpSrv=new WebPageSrv();
        if (tmid!=null && tpid!=null) {
            WebSite tm = SWBContext.getWebSite(tmid);
            WebPage tp=tm.getWebPage(tpid);
            //RecTopic recTp=tp.getDbdata();
            String tpc=request.getParameter("tpc");
            if(tpc==null){
                //recTp.setActive(Integer.parseInt(request.getParameter("status")));
//                WebPageSrv tpSrv=new WebPageSrv();
                //tpSrv.setActive(tp,Integer.parseInt(request.getParameter("status")),response.getUser().getId());
                //tpSrv.setIndexable(tp,Integer.parseInt(request.getParameter("indexable")),response.getUser().getId()); 
                //tpSrv.setHidden(tp,Integer.parseInt(request.getParameter("hidden")),response.getUser().getId());
                boolean istatus=false;
                boolean iindexable=false;
                boolean ihidden=false;
                int ifriendly=0;
                if(request.getParameter("status")!=null && request.getParameter("status").equals("on")) istatus=true;
                if(request.getParameter("indexable")!=null && request.getParameter("indexable").equals("on")) iindexable=true;
                if(request.getParameter("hidden")!=null && request.getParameter("hidden").equals("on")) ihidden=true;
                if(friendlyURL!=null && friendlyURL.equals("1"))
                {
                  if(virtpath!=null&&virtpath.trim().length()>0) virtpath = "furl:"+virtpath;  
                }
                else
                {
                  if(virtpath!=null&&virtpath.startsWith("furl:")) virtpath = virtpath.substring(virtpath.indexOf("furl:")+5);  
                }
                
                tp.setActive(istatus);
                tp.setIndexable(iindexable);
                tp.setHidden(ihidden);
                tp.setModifiedBy(user);
                //tpSrv.setActiveIndexHidden(tp,istatus,iindexable,ihidden,response.getUser().getId());
                
                String tpsort=(tp.getSortName()!=null?tpsort=tp.getSortName():"");
                //TODO: Falta implementar getSubjectIdentity()
                //String tpvirt="";if(tp.getSubjectIdentity()!=null)tpvirt=tp.getSubjectIdentity().toString();
                String tpvirt="";
                
                if(!sortname.equals(tpsort)||!virtpath.equals(tpvirt)) {
                    //TODO: Falta implementar BaseNames tp.getBaseNames()
//                    Iterator bns = tp.getBaseNames().iterator();
//                    while (bns.hasNext()) {
//                        BaseName bn = (BaseName) bns.next();
//                        if (bn.getScope() != null && bn.getScope().getTopicRefs().containsKey(WebSite.CNF_WBSortName)) {
//                            bns.remove();
//                        }
//                    }
//                    
//                    if(sortname.length()>0) {
//                        BaseName sn=new BaseName(sortname);
//                        sn.setScope(new Scope(tp.getWebSite().getTopic(WebSite.CNF_WBSortName)));
//                        tp.addBaseName(sn);
//                    }
//                    
//                    if(virtpath.length()>0) {
//                        tp.setSubjectIdentity(new SubjectIdentity(virtpath));
//                    }else {
//                        tp.setSubjectIdentity(null);
//                    }
                    

                }
                response.setRenderParameter("act","edit");
            }else if(request.getParameter("tpname")!=null){
//                TopicSrv tpsrv=new TopicSrv();
//                Language tplang=tp.getWebSite().getLanguage();
                if(tp.getWebSite().getWebPage(request.getParameter("tpc"))!=null){
                    response.setRenderParameter("act","createTp");
                    response.setRenderParameter("tpc",request.getParameter("tpc"));
                    response.setRenderParameter("tpname",request.getParameter("tpname"));
                    response.setRenderParameter("tpexist","1");
                }else{
                    String newtpid=request.getParameter("tpc");
                    String valid="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_";
                    boolean badchar=false;
                    for (int i=0;i<newtpid.length();i++){
                        String temp = newtpid.substring(i, i+1);
                        if (valid.indexOf(temp) == -1) { badchar=true; break; }
                    }
                    if(!badchar){
                        //topic=tpsrv.createTopic(tp, request.getParameter("tpc"),tplang.getId(),request.getParameter("tpname"),request.getParameter("userid"));
                        
                        //TODO: falta lo del id generator para el nuevo WebPage
                        WebPage nwp = tm.createWebPage(tm.getId()+"_"+tp.getId()+"_"+request.getParameter("tpname"));
                        nwp.setCreator(user);
                        nwp.setTitle(request.getParameter("tpname"));
                        nwp.setParent(tp);
                        nwp.setActive(false);
                        nwp.setIndexable(true);
                    }   
                    else{
                        response.setRenderParameter("act","createTp");
                        response.setRenderParameter("tpc",request.getParameter("tpc"));
                        response.setRenderParameter("tpname",request.getParameter("tpname"));
                        response.setRenderParameter("tpexist","2");
                    }
                    ntpid=request.getParameter("tpc");
                    if(topic!=null) {
                        response.setRenderParameter("act","edit");
                        response.setRenderParameter("ntp",ntpid);
                        WebPage adm=response.getAdminTopic();
                        ((SWBActionResponseImp)response).setVirtualTopic(topic);
                        ((SWBActionResponseImp)response).setAdminTopic(adm);
                    }else if(!badchar) {
                        response.setRenderParameter("act","createTp");
                        response.setRenderParameter("tpc",request.getParameter("tpc"));
                        response.setRenderParameter("tpname",request.getParameter("tpname"));
                        //george cambiar por error interno
                        response.setRenderParameter("tpexist","1");
                    }
                }
            }
        }
        
        String action = response.getAction();
        if(action!=null && action.equals("copyTopic")) {
            String topicID=request.getParameter("topicID");
            WebPage topico=SWBContext.getWebSite(response.getTopic().getWebSite().getId()).getWebPage(topicID);
            boolean bchilds=false,bcontents=false,bdescriptions=false,btemplates=false,bpflows=false,bschedules=false,
            bpermissions=false,brules=false,binnerrules=false,broles=false,bactions=false;
            if(request.getParameter("childs")!=null && request.getParameter("childs").equals("on")) bchilds=true;
            if(request.getParameter("contents")!=null && request.getParameter("contents").equals("on")) bcontents=true;
            if(request.getParameter("descriptions")!=null && request.getParameter("descriptions").equals("on")) bdescriptions=true;
            if(request.getParameter("templates")!=null && request.getParameter("templates").equals("on")) btemplates=true;
            if(request.getParameter("pflows")!=null && request.getParameter("pflows").equals("on")) bpflows=true;
            if(request.getParameter("schedules")!=null && request.getParameter("schedules").equals("on")) bschedules=true;
            if(request.getParameter("permissions")!=null && request.getParameter("permissions").equals("on")) bpermissions=true;
            if(request.getParameter("rules")!=null && request.getParameter("rules").equals("on")) brules=true;
            if(request.getParameter("innerrules")!=null && request.getParameter("innerrules").equals("on")) binnerrules=true;
            if(request.getParameter("roles")!=null && request.getParameter("roles").equals("on")) broles=true;
            if(request.getParameter("actions")!=null && request.getParameter("actions").equals("on")) bactions=true;
            //TODO: Falta implementar copyWebPage()
            //WebPage rootTp=tpSrv.copyTopic(topico, bchilds, bcontents, bdescriptions, btemplates, bpflows, bschedules, bpermissions, brules, binnerrules, broles, bactions,response.getUser().getId());
            WebPage rootTp=null;
            if(rootTp!=null) {
                tmid=rootTp.getWebSite().getId();
                tpid=rootTp.getId();
                response.setAction("ok");
                //response.sendRedirect(response.getAdminTopic().getUrl(rootTp));
            }
            else response.setAction("Notok");
        }
        response.setMode(response.Mode_VIEW);
        if(topic==null) {
            response.setRenderParameter("tp",tpid);
            response.setRenderParameter("tm",tmid);
        }
        response.setRenderParameter("tree","reload");        
    }

}
