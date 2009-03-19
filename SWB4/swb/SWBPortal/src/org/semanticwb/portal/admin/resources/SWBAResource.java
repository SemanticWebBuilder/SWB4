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
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Camp;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceSubType;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceURLImp;

/**
 *
 * @author juan.fernandez
 */
public class SWBAResource extends GenericResource  {

     private Logger log=SWBUtils.getLogger(SWBAResource.class);
    String webpath = (String) SWBPlatform.getContextPath();
    Resource base=null;
    /** Creates a new instance of WBResources */
    public SWBAResource() {
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out=response.getWriter();
        User user = paramRequest.getUser();
        //ResourceSrv resSrv=new ResourceSrv();
        
        if(request.getParameter("WBAGotoTree")!=null) {
            Resource res=SWBContext.getWebSite(request.getParameter("tm")).getResource(request.getParameter("id"));
            int restype=res.getResourceType().getResourceMode();
            String tmtype="";
            if(!res.getWebSiteId().equals(request.getParameter("tm"))) {
                tmtype="0"+res.getResourceType().getId()+".";
            }else {
                tmtype=res.getResourceType().getId()+".";
            }
            String tmstype="";
            //if(!res.getResourceBase().getSubTypeMap().equals(request.getParameter("tm"))) {
            if(!res.getResourceSubType().getWebSite().getId().equals(request.getParameter("tm"))) {
                if(res.getResourceSubType().getId()!=null){ //1if(res.getResourceBase().getSubType()>0) {
                    tmstype="0"+res.getResourceSubType().getId()+".";
                }
            }else {
                //if(res.getResourceBase().getSubType()>0) {
                if(res.getResourceSubType().getId()!=null) {
                
                    tmstype=res.getResourceSubType().getId()+".";
                }
            }
            out.print("<script>");
            if(restype==2){ // de tipo adversiting
                out.print("wbTree_executeAction('gotopath."+request.getParameter("tm")+".advresources."+tmtype+tmstype+request.getParameter("id")+"');");
            }else if(restype==3){ // de tipo sistema
                out.print("wbTree_executeAction('gotopath."+request.getParameter("tm")+".sysresources."+tmtype+tmstype+request.getParameter("id")+"');");
            }
            out.println("</script>");
        }
        
        
        
        if((request.getParameter("act")==null || request.getParameter("act").equals("add") || request.getParameter("act").equals("edit"))){
            doEdit(request,response,paramRequest);
        }
        if(request.getParameter("act")!=null&&request.getParameter("act").equals("remove") && request.getParameter("id")!=null){
            try{
                Resource recRes = SWBContext.getWebSite(request.getParameter("tm")).getResource(request.getParameter("id"));
                //RecResource recRes=DBResource.getInstance().getResource(request.getParameter("tm"),Integer.parseInt(request.getParameter("id")));
                if(recRes!=null){ //resSrv.setRemoved(recRes, 1, user.getId())) {
                    recRes.setDeleted(true);
                    recRes.setModifiedBy(user);
                    out.println("<script>wbTree_remove();</script>");
                    out.println(paramRequest.getLocaleString("msgOkRemove"));
                }
                else{
                    out.println("<font color=\"red\">"+paramRequest.getLocaleString("msgErrRemove")+" "+recRes.getId()+paramRequest.getLocaleString("msgErrRemoveCause")+"</font>");
                }
            }
            catch(Exception e){
                log.error("Error while trying to Delete topicmap with id:"+request.getParameter("tm"),e);
                out.println("<font class=\"datos\">"+paramRequest.getLocaleString("msgErrRemoveTm")+" "+request.getParameter("tm")+".</font>");
            }
        }else if(request.getParameter("act")!=null && request.getParameter("act").equals("active") && request.getParameter("id")!=null) {
            try {
                Resource recRes = SWBContext.getWebSite(request.getParameter("tm")).getResource(request.getParameter("id"));
                //RecResource recRes=DBResource.getInstance().getResource(request.getParameter("tm"),Long.parseLong(request.getParameter("id")));
                //resSrv.setActive(recRes,1,user.getId());
                recRes.setActive(true);
                recRes.setModifiedBy(user);
                out.println("<script>wbTree_reload();</script>");
                out.println("<font face=\"Verdana\" size=\"1\">");
                out.println(paramRequest.getLocaleString("msgOkActivate"));
                out.println("</font>");
            }catch(Exception e){
                out.println("<font face=\"Verdana\" color=\"red\" size=\"1\">");
                out.println(paramRequest.getLocaleString("msgErrActivate"));
                out.println("</font>");
                log.error(e);
            }
        }else if(request.getParameter("act")!=null && request.getParameter("act").equals("unactive") && request.getParameter("id")!=null) {
            try {
                Resource recRes = SWBContext.getWebSite(request.getParameter("tm")).getResource(request.getParameter("id"));
                //RecResource recRes=DBResource.getInstance().getResource(request.getParameter("tm"),Integer.parseInt(request.getParameter("id")));
                //resSrv.setActive(recRes,0,user.getId());
                recRes.setActive(false);
                recRes.setModifiedBy(user);
                out.println("<script>wbTree_reload();</script>");
                out.println("<font face=\"Verdana\" size=\"1\">");
                out.println(paramRequest.getLocaleString("msgOkUnactivate"));
                out.println("</font>");
            }catch(Exception e){
                out.println("<font face=\"Verdana\" color=\"red\" size=\"1\">");
                out.println(paramRequest.getLocaleString("msgErrUnactivate"));
                out.println("</font>");
                log.error(e);
            }
        }
        else if(request.getParameter("act")!=null && request.getParameter("act").equals("copy") && request.getParameter("id")!=null) {
            try {
                Resource recRes = SWBContext.getWebSite(request.getParameter("tm")).getResource(request.getParameter("id"));
//                RecResource recRes=DBResource.getInstance().getResource(request.getParameter("tm"),Integer.parseInt(request.getParameter("id")));
//                resSrv=new ResourceSrv();
                //TODO: Falta implementar copyResource
                //Resource recRes1=resSrv.copyResource(recRes.getWebSiteId(), recRes.getId(),paramRequest.getLocaleString("msgCopyResource")+" "+recRes.getTitle(),user.getId());
                Resource recRes1=recRes;
                //SWBContext.getWebSite(request.getParameter("tm")).
                
                if(recRes1!=null){
                    out.println("<script>wbTree_reload();</script>");
                    out.println("<font face=\"Verdana\" size=\"1\">");
                    out.println(paramRequest.getLocaleString("msgCopyResourceOk"));
                    out.println("</font>");
                    //
                    request.setAttribute("act", "copyRes");
                    request.setAttribute("user", paramRequest.getUser().getId());
                    request.setAttribute("tp", paramRequest.getTopic().getId());
                    request.setAttribute("id", ""+recRes1.getId());
                    doProcessRedirec(request,response,paramRequest);
                }else{
                    out.println("<font face=\"Verdana\" color=\"red\" size=\"1\">");
                    out.println(paramRequest.getLocaleString("msgCopyResourceFail"));
                    out.println("</font>");
                }
            }catch(Exception e){
                out.println("<font face=\"Verdana\" color=\"red\" size=\"1\">");
                out.println(paramRequest.getLocaleString("msgCopyResourceFail"));
                out.println("</font>");
                log.error(e);
            }
        }
        else if(paramRequest.getAction()!=null && paramRequest.getAction().equals("addRedirec")) {
            request.setAttribute("act", "add");
            request.setAttribute("user", paramRequest.getUser().getId());
            request.setAttribute("tp", paramRequest.getTopic().getId());
            doProcessRedirec(request,response,paramRequest);
        }
    }

        
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String act = null;
        PrintWriter out = response.getWriter();
        if(request.getParameter("act")!=null) act = request.getParameter("act");
        if(act==null && !paramRequest.getAction().equals("edit")) act=paramRequest.getAction();
        if(act==null) act="view";
        if(request.getParameter("tree")!=null && request.getParameter("tree").equals("reload")){
            if(request.getParameter("flag")!=null){
                if(request.getParameter("flag").equals("update")){
                    Resource recRes=SWBContext.getWebSite(request.getParameter("tm")).getResource(request.getParameter("id"));
                    ResourceType recResType=SWBContext.getWebSite(recRes.getWebSiteId()).getResourceType(recRes.getId());
                    int restype=recResType.getResourceMode();
                    String sglobal="";
                    if(recResType.getWebSite().getId().equals(SWBContext.WEBSITE_GLOBAL)) sglobal="0";
                    if(restype==2){ // de tipo adversiting
                        if(request.getParameter("stype")!=null && !request.getParameter("stype").equals("0")){
                            String stypetm="";
                            if(!request.getParameter("stypemap").equals(request.getParameter("tm"))) {
                                stypetm="0";
                            }
                            out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tm")+".advresources."+sglobal+Integer.parseInt(request.getParameter("type"))+"');");
                            out.println("wbTree_reload()");
                            out.println("wbTree_executeAction('gotopath."+request.getParameter("tm")+".advresources."+sglobal+Integer.parseInt(request.getParameter("type"))+"."+stypetm+Integer.parseInt(request.getParameter("stype"))+"."+request.getParameter("id")+"');");
                            out.println("wbTree_reload();</script>");
                        }else{
                            String stype=request.getParameter("stype");
                            out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tm")+".advresources."+sglobal+Integer.parseInt(request.getParameter("type"))+"."+request.getParameter("id")+"');");
                            out.println("wbTree_reload();</script>");
                        }
                    }else if(restype==3){ // de tipo sistema
                        if(request.getParameter("stype")!=null && !request.getParameter("stype").equals("0")){
                            String stypetm="";
                            if(!request.getParameter("stypemap").equals(request.getParameter("tm"))) {
                                stypetm="0";
                            }
                            out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tm")+".sysresources."+sglobal+Integer.parseInt(request.getParameter("type"))+"');");
                            out.println("wbTree_reload()");
                            out.println("wbTree_executeAction('gotopath."+request.getParameter("tm")+".sysresources."+sglobal+Integer.parseInt(request.getParameter("type"))+"."+stypetm+Integer.parseInt(request.getParameter("stype"))+"."+request.getParameter("id")+"');");
                            out.println("wbTree_reload();</script>");
                        }else{
                            String stype=request.getParameter("stype");
                            out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tm")+".sysresources."+sglobal+Integer.parseInt(request.getParameter("type"))+"."+request.getParameter("id")+"');");
                            out.println("wbTree_reload();</script>");
                        }
                    }
                }
            }
        }
        if(act!=null&&act.equals("add")){
            //out.println("<form name=\"forma\" action=\""+paramRequest.getActionUrl().setAction("add").toString()+"\" method=\"post\">");
            out.println("<form name=\"forma\" action=\""+paramRequest.getRenderUrl().setAction("addRedirec").toString()+"\" method=\"post\">");
            out.println("<p align=center class=\"box\">");
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<tr ><td colspan=2 class=\"datos\">"+paramRequest.getLocaleString("msgAddResource")+"</td></tr>");
            out.println("<tr ><td width=\"100\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgTitle")+"</td><td class=\"valores\"><input type=\"text\" name=\"title\" value=\"\" ></td></tr>");
            out.println("<tr ><td width=\"100\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgDescription")+"</td><td class=\"valores\"><input type=\"text\" name=\"description\" value=\"\"></td></tr>");
            out.println("<tr ><td width=\"100\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgPriority")+"</td><td class=\"valores\">");
            out.println("<select name=\"priority\">");
            out.println("<option value=\"1\">"+paramRequest.getLocaleString("msgDefault")+"</option>");
            out.println("<option value=\"2\">"+paramRequest.getLocaleString("msgLow")+"</option>");
            out.println("<option value=\"3\" Selected>"+paramRequest.getLocaleString("msgMedium")+"</option>");
            out.println("<option value=\"4\">"+paramRequest.getLocaleString("msgHigh")+"</option>");
            out.println("<option value=\"5\">"+paramRequest.getLocaleString("msgHighPriority")+"</option>");
            out.println("</select>");
            out.println("</td></tr>");
            //out.println("<TR>");
            out.println("<tr ><td class=\"valores\" align=\"right\" colspan=\"2\"><HR size=\"1\" noshade><br><input type=\"button\" class=boton value=\""+paramRequest.getLocaleString("next")+"\" onClick=\"javascript:valida(this.form);\"></td></tr>");
            out.println("</table>");
            out.println("</P>");
            if(request.getParameter("camp")!=null)out.println("<input type=\"hidden\" name=\"camp\" value=\""+request.getParameter("camp")+"\">");
            if(request.getParameter("id")!=null)out.println("<input type=\"hidden\" name=\"type\" value=\""+request.getParameter("id")+"\">");
            if(request.getParameter("typemap")!=null)out.println("<input type=\"hidden\" name=\"typemap\" value=\""+request.getParameter("typemap")+"\">");
            if(request.getParameter("ids")!=null)out.println("<input type=\"hidden\" name=\"stype\" value=\""+request.getParameter("ids")+"\">");
            if(request.getParameter("tm")!=null)out.println("<input type=\"hidden\" name=\"tm\" value=\""+request.getParameter("tm")+"\">");
            if(request.getParameter("idsmap")!=null)out.println("<input type=\"hidden\" name=\"stypemap\" value=\""+request.getParameter("idsmap")+"\">");
            
            out.println("</form>");
            out.println("<Script Language=\"JavaScript\">");
            out.println("function valida(forma)");
            out.println("{");
            out.println("   var flag=true;");
            out.println("   if (forma.title.value.replace(/^\\s*|\\s*$/g,\"\").length<1) ");
            out.println("   {");
            out.println("       alert(\"" + SWBUtils.TEXT.getLocaleString("locale_admin", "usrmsg_TopicWorkArea_processRequest_noemptyallowed") + "\");");
            out.println("       forma.title.focus();");
            out.println("       forma.title.select();");
            out.println("       flag=false;return false;");
            out.println("   }");
            out.println("   if (forma.description.value.replace(/^\\s*|\\s*$/g,\"\").length<1) ");
            out.println("   {");
            out.println("       alert(\"" + SWBUtils.TEXT.getLocaleString("locale_admin", "usrmsg_TopicWorkArea_processRequest_noemptyallowed") + "\");");
            out.println("       forma.description.focus();");
            out.println("       forma.description.select();");
            out.println("       flag=false;return false;");
            out.println("   }");
            out.println("   if(flag)");
            out.println("   {");
            out.println("       forma.submit();");
            out.println("   }else{return false;}");
            out.println("}");
            out.println("</Script>");
        }else if(act!=null && act.equals("edit") && request.getParameter("id")!=null){
            out.println("<style type=\"text/css\">");
            out.println("* {scrollbar-3d-light-color:#EBEFF3; scrollbar-arrow-color:#5D6B8D;");
            out.println("scrollbar-base-color:#5D6B8D; scrollbar-dark-shadow-color:#EBEFF3;");
            out.println("scrollbar-face-color:#EBEFF3; scrollbar-highlight-color:#EBEFF3;");
            out.println("scrollbar-shadow-color:#EBEFF3; scrollbar-track-color: #EBEFF3;}");
            out.println("</style>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
            out.println("<script language=\"JavaScript\">");
            out.println("<!--");
            out.println("function MM_preloadImages() { //v3.0");
            out.println("var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();");
            out.println("var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)");
            out.println("if (a[i].indexOf(\"#\")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}");
            out.println("}");
            
            out.println("function MM_findObj(n, d) { //v4.0");
            out.println("var p,i,x;  if(!d) d=document; if((p=n.indexOf(\"?\"))>0&&parent.frames.length) {");
            out.println("d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}");
            out.println("if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];");
            out.println("for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);");
            out.println("if(!x && document.getElementById) x=document.getElementById(n); return x;");
            out.println("}");
            
            out.println("function MM_nbGroup(event, grpName) { //v3.0");
            out.println("var i,img,nbArr,args=MM_nbGroup.arguments;");
            out.println("if (event == \"init\" && args.length > 2) {");
            out.println("if ((img = MM_findObj(args[2])) != null && !img.MM_init) {");
            out.println("img.MM_init = true; img.MM_up = args[3]; img.MM_dn = img.src;");
            out.println("if ((nbArr = document[grpName]) == null) nbArr = document[grpName] = new Array();");
            out.println("nbArr[nbArr.length] = img;");
            out.println("for (i=4; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {");
            out.println("if (!img.MM_up) img.MM_up = img.src;");
            out.println("img.src = img.MM_dn = args[i+1];");
            out.println("nbArr[nbArr.length] = img;");
            out.println("} }");
            out.println("} else if (event == \"over\") {");
            out.println("document.MM_nbOver = nbArr = new Array();");
            out.println("for (i=1; i < args.length-1; i+=3) if ((img = MM_findObj(args[i])) != null) {");
            out.println("if (!img.MM_up) img.MM_up = img.src;");
            out.println("img.src = (img.MM_dn && args[i+2]) ? args[i+2] : args[i+1];");
            out.println("nbArr[nbArr.length] = img;");
            out.println("}");
            out.println("} else if (event == \"out\" ) {");
            out.println("for (i=0; i < document.MM_nbOver.length; i++) {");
            out.println("img = document.MM_nbOver[i]; img.src = (img.MM_dn) ? img.MM_dn : img.MM_up; }");
            out.println("} else if (event == \"down\") {");
            out.println("if ((nbArr = document[grpName]) != null)");
            out.println("for (i=0; i < nbArr.length; i++) { img=nbArr[i]; img.src = img.MM_up; img.MM_dn = 0; }");
            out.println("document[grpName] = nbArr = new Array();");
            out.println("for (i=2; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {");
            out.println("if (!img.MM_up) img.MM_up = img.src;");
            out.println("img.src = img.MM_dn = args[i+1];");
            out.println("nbArr[nbArr.length] = img;");
            out.println("} }");
            out.println("}");
            
            out.println("function MM_openBrWindow(theURL,winName,features) { //v2.0");
            out.println("window.open(theURL,winName,features);");
            out.println("}");
            out.println("//-->");
            out.println("</script>");
            
            
            // refresca arbol...
            
            if(request.getParameter("reload")!=null && request.getParameter("reload").equals("1")){
                //DBResourceType.getInstance().getResourceType(request.getParameter("typemap"),Integer.parseInt(request.getParameter("type"))).getType();
                String restype=SWBContext.getWebSite(request.getParameter("typemap")).getResourceType(request.getParameter("type")).getId();
                if(restype.equals("2")){ // de tipo adversiting
                    String tmtype="";
                    if(!request.getParameter("typemap").equals(request.getParameter("tm"))) {
                        tmtype="0";
                    }
                    String stypetm="";
                    if(request.getParameter("stype")!=null && !request.getParameter("stype").equals("0")){
                        if(!request.getParameter("stypemap").equals(request.getParameter("tm"))) {
                            stypetm="0";
                        }
                        out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tm")+".advresources."+tmtype+request.getParameter("type")+"."+stypetm+request.getParameter("stype")+"');");
                        out.println("wbTree_reload();");
                        out.println("wbTree_executeAction('gotopath."+request.getParameter("tm")+".advresources."+tmtype+request.getParameter("type")+"."+stypetm+request.getParameter("stype")+"."+request.getParameter("id")+"');");
                    }else{
                        out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tm")+".advresources."+tmtype+request.getParameter("type")+"');");
                        out.println("wbTree_reload();");
                        out.println("wbTree_executeAction('gotopath."+request.getParameter("tm")+".advresources."+tmtype+request.getParameter("type")+"."+request.getParameter("id")+"');");
                    }
                    out.println("</script>");
                }else if(restype.equals("3")){ // de tipo sistema
                    String tmtype="";
                    if(!request.getParameter("typemap").equals(request.getParameter("tm"))) {
                        tmtype="0";
                    }
                    if(request.getParameter("stype")!=null && !request.getParameter("stype").equals("0")){
                        String stypetm="";
                        if(!request.getParameter("stypemap").equals(request.getParameter("tm"))) {
                            stypetm="0";
                        }
                        out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tm")+".sysresources."+request.getParameter("type")+"."+stypetm+request.getParameter("stype")+"');");
                        out.println("wbTree_reload();");
                        out.println("wbTree_executeAction('gotopath."+request.getParameter("tm")+".sysresources."+tmtype+request.getParameter("type")+"."+stypetm+request.getParameter("stype")+"."+request.getParameter("id")+"');");
                    }else{
                        out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tm")+".sysresources."+request.getParameter("type")+"');");
                        out.println("wbTree_reload();");
                        out.println("wbTree_executeAction('gotopath."+request.getParameter("tm")+".sysresources."+tmtype+request.getParameter("type")+"."+request.getParameter("id")+"');");
                    }
                    out.println("</script>");
                }
            }
            
            
            Resource recRes=SWBContext.getWebSite(request.getParameter("tm")).getResource(request.getParameter("id"));
            out.println("<script src=\""+SWBPlatform.getContextPath()+"wbadmin/js/"+paramRequest.getLocaleString("keyJSCalendar")+"\"></script>");
            out.println("<form id=\"forma\" name=\"forma\" action=\""+paramRequest.getActionUrl().setAction("update").toString()+"\" method=\"post\">");
            out.println("<p class=\"box\">");
            out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<TR>");
            out.println("<TD width=\"100\" align=\"right\" class=\"datos\"> "+paramRequest.getLocaleString("msgID")+"</TD>");
            out.println("<TD class=\"valores\">");
            out.println(recRes.getId());
            out.println("<INPUT name=\"id\" type=\"hidden\" value=\""+recRes.getId()+"\">");
            out.println("<INPUT name=\"type\" type=\"hidden\" value=\""+recRes.getResourceType().getResourceMode()+"\">");
            out.println("<INPUT name=\"typemap\" type=\"hidden\" value=\""+recRes.getResourceType().getWebSite().getId()+"\">");
            out.println("<INPUT name=\"tm\" type=\"hidden\" value=\""+request.getParameter("tm")+"\">");
            out.println("</TD>");
            out.println("</TR>");
            out.println("</TABLE>");
            out.println("</P><P class=\"box\">");
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgTitle")+"</td><td class=\"valores\" align=\"left\"><input type=\"text\" name=\"title\" value=\""+recRes.getTitle()+"\" ></td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgDescription")+"</td><td class=\"valores\" align=\"left\"><input type=\"text\" name=\"description\" value=\""+recRes.getDescription()+"\"></td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgPriority")+"</td><td class=\"valores\" align=\"left\">");
            out.println("<select name=\"priority\">");
            if(recRes.getPriority()==1) out.println("<option value=\"1\" Selected>"+paramRequest.getLocaleString("msgDefault")+"</option>");
            else out.println("<option value=\"1\">"+paramRequest.getLocaleString("msgDefault")+"</option>");
            if(recRes.getPriority()==2)out.println("<option value=\"2\" Selected>"+paramRequest.getLocaleString("msgLow")+"</option>");
            else out.println("<option value=\"2\">"+paramRequest.getLocaleString("msgLow")+"</option>");
            if(recRes.getPriority()==3) out.println("<option value=\"3\" Selected>"+paramRequest.getLocaleString("msgMedium")+"</option>");
            else out.println("<option value=\"3\">"+paramRequest.getLocaleString("msgMedium")+"</option>");
            if(recRes.getPriority()==4) out.println("<option value=\"4\" Selected>"+paramRequest.getLocaleString("msgHigh")+"</option>");
            else out.println("<option value=\"4\">"+paramRequest.getLocaleString("msgHigh")+"</option>");
            if(recRes.getPriority()==5) out.println("<option value=\"5\" Selected>"+paramRequest.getLocaleString("msgHighPriority")+"</option>");
            else out.println("<option value=\"5\">"+paramRequest.getLocaleString("msgHighPriority")+"</option>");
            out.println("</select>");
            out.println("</td></tr>");
            String msg="";
            //TODO: Falta implementar AdmFilterMgr.haveAccess2Camps
            //if(AdmFilterMgr.getInstance().haveAccess2Camps(paramRequest.getUser(),recRes.getWebSiteId())>0) {
            if(true) { // temporal
                out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgCampaing")+"</td><td class=\"valores\" align=\"left\">");
                out.println("<select name=\"camp\">");
                boolean iCampActive=true;
                Iterator<Camp> itCamps=SWBContext.getWebSite(recRes.getWebSiteId()).listCamps();
                while(itCamps.hasNext()){
                    Camp recCamp=itCamps.next();
                    
                    if(recRes.getCamp().getId().equals(recCamp.getId())){
                        iCampActive = recCamp.isActive();
                        out.println("<option value=\""+recCamp.getId()+"\" Selected>"+recCamp.getTitle()+"</option>");
                    }
                    else out.println("<option value=\""+recCamp.getId()+"\">"+recCamp.getTitle()+"</option>");
                }
                if(recRes.getCamp().getId().equals("0")) out.println("<option value=\"0\" Selected>"+paramRequest.getLocaleString("msgWithoutCampaing")+"</option>");
                else out.println("<option value=\"0\">"+paramRequest.getLocaleString("msgWithoutCampaing")+"</option>");
                out.println("</select>");
                if(!iCampActive) msg=paramRequest.getLocaleString("msgCampInactive");
            }else {
                out.println("<INPUT name=\"camp\" type=\"hidden\" value=\""+recRes.getCamp().getId()+"\">");
            }
            out.println("</td><td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgType")+"</td><td class=\"valores\" align=\"left\">"+recRes.getResourceType().getTitle()+"</td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgSubtype")+"</td><td class=\"valores\" align=\"left\">");
            out.println("<select name=\"stype\">");
            //Iterator<ResourceSubType> itSubtypes=DBCatalogs.getInstance().getSubTypes(TopicMgr.TM_GLOBAL,recRes.getType(),recRes.getTypeMap());
            Iterator<ResourceSubType> itSubtypes=SWBContext.getGlobalWebSite().getResourceType(recRes.getResourceType().getId()).listSubTypes();
            while(itSubtypes.hasNext()){
                ResourceSubType recSType=itSubtypes.next();
                if(recSType.getId().equals(recRes.getResourceSubType().getId()) && recSType.getWebSite().getId().equals(recRes.getResourceSubType().getWebSite().getId())) {
                    out.println("<option value=\"0"+recSType.getId()+"\" Selected>"+recSType.getTitle()+"</option>");
                }
                else out.println("<option value=\"0"+recSType.getId()+"\">"+recSType.getTitle()+"</option>");
            }
            //itSubtypes=DBCatalogs.getInstance().getSubTypes(request.getParameter("tm"),recRes.getType(),recRes.getTypeMap());
            itSubtypes=SWBContext.getWebSite(request.getParameter("tm")).getResourceType(recRes.getResourceType().getId()).listSubTypes();
            while(itSubtypes.hasNext()){
                ResourceSubType recSType=itSubtypes.next();
                if(recSType.getId().equals(recRes.getResourceSubType().getId()) && recSType.getWebSite().getId().equals(recRes.getResourceSubType().getId())) {
                    out.println("<option value=\""+recSType.getId()+"\" Selected>"+recSType.getTitle()+"</option>");
                }
                else out.println("<option value=\""+recSType.getId()+"\">"+recSType.getTitle()+"</option>");
            }
            if(recRes.getResourceSubType().getId().equals("0")) out.println("<option value=\"0\" Selected>"+paramRequest.getLocaleString("msgSubtypeDefault")+"</option>");
            else out.println("<option value=\"0\">"+paramRequest.getLocaleString("msgSubtypeDefault")+"</option>");
            out.println("</select>");
            out.println("</td></tr>");
            //TODO: dateFormat
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgCreationDate")+"</td><td class=\"valores\" align=\"left\">"+recRes.getCreated().toString()+"</td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgLastUpdate")+"</td><td class=\"valores\" align=\"left\">"+recRes.getUpdated().toString()+"</td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgResourceAppearances")+"</td><td class=\"valores\" align=\"left\">"+recRes.getViews()+"</td></tr>");
            //out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgAppearances")+"</td><td class=\"valores\" align=\"left\"><input type=\"text\" name=\"views\" value=\""+recRes.getConfAttribute("views","")+"\"></td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgAppearances")+"</td><td class=\"valores\" align=\"left\"><input type=\"text\" name=\"views\" value=\""+recRes.getProperty("views","")+"\"></td></tr>");
            
              String checked="";
            /*
            String dbEvalHits=ResourceMgr.getInstance().getResource(recRes.getTopicMapId(),recRes.getId()).getResourceBase().getConfAttribute("evaluateHits");
            if(dbEvalHits!=null && dbEvalHits.trim().length()>0){
                if(Boolean.valueOf(dbEvalHits).booleanValue()){
                    checked="checked";
                }
            }
            
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgEvaluate")+":</td><td class=\"valores\" align=\"left\">");
            out.println("<input type=\"checkbox\" name=\"evaluateHits\" "+ checked +" onClick=\"refreshForm(this.form);\"></td></tr>");
            
            out.println("<input type=\"hidden\" name=\"appearancesEndDate\" value=\"\">");
            //calendario
            String hitsEndDate="";
            if((request.getParameter("evaluateHits")!=null && request.getParameter("evaluateHits").equals("1")) || checked.equals("checked")){
                sreadOnly="";
                hitsEndDate=ResourceMgr.getInstance().getResource(recRes.getTopicMapId(),recRes.getId()).getResourceBase().getConfAttribute("hitsEndDate","");
            }
            */
            String hitsEndDate="";
            //hitsEndDate=recRes.getConfAttribute("hitsEndDate","");
            hitsEndDate=recRes.getProperty("hitsEndDate","");
            
            out.println("        <tr>");
            out.println("          <td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("hitsEndDate")+":</td>");
            out.println("          <td align=\"left\"><input type=\"text\" name=\"hitsEndDate\" value=\""+hitsEndDate+"\">&nbsp;");
            out.println("           <img name=calendario src=\""+SWBPlatform.getContextPath()+"wbadmin/images/show-calendar.gif\" onclick=\"javascript:show_calendar('forma.hitsEndDate');\" border=0> </td>");
            out.println("        </tr>");
            
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgResourceClicks")+"</td><td class=\"valores\" align=\"left\">"+recRes.getHits()+"</td></tr>");
            
            //out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgHits")+":</td><td class=\"valores\" align=\"left\"><input type=\"text\" name=\"hits\" value=\""+recRes.getConfAttribute("hits","")+"\"></td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgHits")+":</td><td class=\"valores\" align=\"left\"><input type=\"text\" name=\"hits\" value=\""+recRes.getProperty("hits","")+"\"></td></tr>");
            
            
            boolean status=recRes.isActive();
            SWBResourceURL urlResAct=paramRequest.getActionUrl();
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgStatus")+":</td><td class=\"valores\" align=\"left\">");
            urlResAct.setParameter("tm",recRes.getWebSiteId());
            if (!status) {
                urlResAct.setAction("status");
                urlResAct.setParameter("status","1");
                urlResAct.setParameter("id",String.valueOf(recRes.getId()));
                out.println("<A href=\"" + urlResAct.toString() + "\" onmousemove=\"window.status='" + paramRequest.getLocaleString("status") + "';return true;\">");
                out.println("<IMG alt=\"" + paramRequest.getLocaleString("offLine") + "\" border=0 src=\"" + webpath + "wbadmin/images/offline.gif\"></A>");
            } else {
                urlResAct.setAction("status");
                urlResAct.setParameter("status","0");
                urlResAct.setParameter("id",String.valueOf(recRes.getId()));
                out.println("<A onmousemove=\"window.status='" + SWBUtils.TEXT.getLocaleString("locale_admin", "usrmsg_TopicDescr_MuestraInfo_contentstatus") + "';return true;\" href=\"" + urlResAct.toString() + "\">");
                out.println("<IMG alt=\"" + paramRequest.getLocaleString("onLine") + "\" border=0 src=\"" + webpath + "wbadmin/images/online.gif\"></A>");
            }
            out.println("</td></tr>");
            
            //portletWindow
            out.println("<tr>");
            out.println("<td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("showinWindowFunctions")+"</td>");
            //int iportletWindow=Integer.parseInt(recRes.getConfAttribute("portletWindow","0"));
            int iportletWindow=Integer.parseInt(recRes.getProperty("portletWindow","0"));
            checked="";
            if(iportletWindow==1){
                checked="checked";
            }
            out.println("<td class=\"valores\" align=\"left\"><input type=\"checkbox\" name=\"portletWindow\" "+ checked +"></td></tr>");
            out.println("</tr>");
            
             //portletWindow
            out.println("<tr>");
            out.println("<td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("monitorResourceHits")+"</td>");
            
            //int ihitlog=recRes.getHitLog();
            long ihitlog=recRes.getHits();
            checked="";
            if(ihitlog==1){
                checked="checked";
            }
            out.println("<td class=\"valores\" align=\"left\"><input type=\"checkbox\" name=\"hitlog\" "+ checked +"></td></tr>");
            out.println("</tr>");
            
            
            out.println("<tr><td colspan=\"2\"><font color=\"red\">"+msg+"</font></td></tr>");
            /*
            Resource res=ResourceMgr.getInstance().getResource(recRes.getTopicMapId(),recRes.getId()).getResourceBase();
            WBResourceURLImp url=new WBResourceURLImp(request, res, paramRequest.getTopic(), WBResourceURLImp.UrlType_RENDER);
            //WBResourceURLImp url=(WBResourceURLImp)request, res, paramRequest.getTopic(), WBResourceURLImp.UrlType_RENDER);
            url.setParameter("tpcomm","WBAd_mnui_ResourceAdmin");
            url.setWindowState(url.WinState_MAXIMIZED);
            url.setMode(url.Mode_ADMIN);
             */
            SWBResourceURLImp url=(SWBResourceURLImp)paramRequest.getRenderUrl();
            url.setResourceBase(SWBContext.getWebSite(recRes.getWebSiteId()).getResource(recRes.getId()));
            url.setParameter("tpcomm","WBAd_mnui_ResourceAdmin");
            url.setWindowState(SWBResourceURL.WinState_MAXIMIZED);
            url.setMode(SWBResourceURL.Mode_ADMIN);
            
            out.println("<tr><td class=\"valores\" align=\"right\" colspan=\"2\"><HR size=\"1\" noshade><br><input type=\"button\" value=\""+paramRequest.getLocaleString("msgResourceAdministration")+"\" onClick='javascript:send();' class=\"boton\">&nbsp;&nbsp;<input type=\"button\" value=\""+paramRequest.getLocaleString("next")+"\" onClick=\"javascript:valida(this.form);\" class=\"boton\">");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("</P>");
            if(request.getParameter("camp")!=null)out.println("<input type=\"hidden\" name=\"camp\" value=\""+request.getParameter("camp")+"\">");
            if(request.getParameter("type")!=null)out.println("<input type=\"hidden\" name=\"type\" value=\""+request.getParameter("type")+"\">");
            if(request.getParameter("tm")!=null)out.println("<input type=\"hidden\" name=\"tm\" value=\""+request.getParameter("tm")+"\">");
            out.println("</p>");
            out.println("</form>");
            out.println("<Script Language=\"JavaScript\">");
            out.println("function valida(forma)");
            out.println("{");
            out.println("   var flag=true;");
            out.println("   if (forma.title.value.replace(/^\\s*|\\s*$/g,\"\").length<1) ");
            out.println("   {");
            out.println("       alert(\"" + SWBUtils.TEXT.getLocaleString("locale_admin", "usrmsg_TopicWorkArea_processRequest_noemptyallowed") + "\");");
            out.println("       forma.title.focus();");
            out.println("       forma.title.select();");
            out.println("       flag=false;return false;");
            out.println("   }");
            out.println("   if (forma.description.value.replace(/^\\s*|\\s*$/g,\"\").length<1) ");
            out.println("   {");
            out.println("       alert(\"" + SWBUtils.TEXT.getLocaleString("locale_admin", "usrmsg_TopicWorkArea_processRequest_noemptyallowed") + "\");");
            out.println("       forma.description.focus();");
            out.println("       forma.description.select();");
            out.println("       flag=false;return false;");
            out.println("   }");
            out.println("\n   var pCaracter=forma.views.value;");
            out.println("\n   var valid = \"0123456789\";");
            out.println("\n   var ok = true;");
            out.println("\n   if (pCaracter.length>0) ");
            out.println("     { ");
            out.println("\n      for (var i=0; i<pCaracter.length; i++)");
            out.println("\n      { ");
            out.println("\n          var temp = \"\" + pCaracter.substring(i, i+1); ");
            out.println("\n          if (valid.indexOf(temp) == \"-1\") {ok = false; flag=false;} ");
            out.println("\n      } ");
            out.println("\n      if (ok == false) ");
            out.println("\n      { ");
            out.println("\n         alert('"+paramRequest.getLocaleString("msgNumberRequired")+"');");
            out.println("\n         forma.views.focus();");
            out.println("\n         return false;");
            out.println("\n      } ");
            out.println("\n    } ");
            out.println("\n   var pCaracter=forma.hits.value;");
            out.println("\n   var valid = \"0123456789\";");
            out.println("\n   var ok = true;");
            out.println("\n   if (pCaracter.length>0) ");
            out.println("     { ");
            out.println("\n      for (var i=0; i<pCaracter.length; i++)");
            out.println("\n      { ");
            out.println("\n          var temp = \"\" + pCaracter.substring(i, i+1); ");
            out.println("\n          if (valid.indexOf(temp) == \"-1\") {ok = false; flag=false;} ");
            out.println("\n      } ");
            out.println("\n      if (ok == false) ");
            out.println("\n      { ");
            out.println("\n         alert('"+paramRequest.getLocaleString("msgNumberRequired")+"');");
            out.println("\n         forma.hits.focus();");
            out.println("\n         return false;");
            out.println("\n      } ");
            out.println("\n    }");
            /*
            out.println("if(forma.evaluateHits.checked==true && forma.hitlog.checked==false)");
            out.println("{");
            out.println(" alert('"+paramRequest.getLocaleString("msgFieldNeeded")+"');");
            out.println("\n     forma.hitlog.focus();");
            out.println("\n  return false;");
            out.println("}");
             **/
            out.println("   if(flag)");
            out.println("   {");
            out.println("       forma.submit();");
            out.println("   }else{return false;}");
            out.println("}");
            out.println("function send() ");
            out.println("{ ");
            out.println("   document.forma.action='"+url+"';");
            out.println("   document.forma.submit(); ");
            out.println("} ");
            out.println("function refreshForm(forma) ");
            out.println("{ ");
            out.println("   forma.appearancesEndDate.value=1");
            out.println("   forma.submit(); ");
            out.println("} ");
            out.println("</Script>");
        }else if(act!=null && act.equals("view") && request.getParameter("id")!=null){
            Resource recRes=SWBContext.getWebSite(request.getParameter("tm")).getResource(request.getParameter("id"));
            out.println("<form name=\"forma\" action=\""+paramRequest.getActionUrl().setAction("update").toString()+"\" method=\"post\">");
            out.println("<P class=\"box\">");
            out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<TR>");
            out.println("<TD width=\"100\" align=\"right\" class=\"datos\"> "+paramRequest.getLocaleString("msgID")+"</TD>");
            out.println("<TD class=\"valores\">");
            out.println(recRes.getId());
            out.println("</TD>");
            out.println("</TR>");
            out.println("</TABLE>");
            out.println("</P><P class=\"box\">");
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgTitle")+"</td><td class=\"valores\" align=\"left\">"+recRes.getTitle()+"</td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgDescription")+"</td><td class=\"valores\" align=\"left\">"+recRes.getDescription()+"</td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgPriority")+"</td><td class=\"valores\" align=\"left\">");
            if(recRes.getPriority()==1) out.println(""+paramRequest.getLocaleString("msgDefault"));
            if(recRes.getPriority()==2) out.println(""+paramRequest.getLocaleString("msgLow"));
            if(recRes.getPriority()==3) out.println(""+paramRequest.getLocaleString("msgMedium"));
            if(recRes.getPriority()==4) out.println(""+paramRequest.getLocaleString("msgHigh"));
            if(recRes.getPriority()==5) out.println(""+paramRequest.getLocaleString("msgHighPriority"));
            out.println("</td></tr>");
            //TODO: AdmFilterMgr.getInstance().haveAccess2Camps
            //if(AdmFilterMgr.getInstance().haveAccess2Camps(paramRequest.getUser(),recRes.getWebSiteId()))>0) {
            if(1>0) { //temporal
                out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgCampaing")+"</td><td class=\"valores\" align=\"left\">");
                if(recRes.getCamp().getId().equals("0")) out.println(""+paramRequest.getLocaleString("msgWithoutCampaing"));
                else out.println(recRes.getCamp().getTitle());
                out.println("</td></tr>");
            }
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgType")+"</td><td class=\"valores\" align=\"left\">"+recRes.getResourceType().getTitle()+"</td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgSubtype")+"</td><td class=\"valores\" align=\"left\">");
            if(recRes.getResourceSubType().getId().equals("0")) out.println(""+paramRequest.getLocaleString("msgSubtypeDefault"));
            else out.println(recRes.getResourceSubType().getTitle());
            out.println("</td></tr>");
            Resource resource=recRes;
            //TODO: dateFormat
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgCreationDate")+"</td><td class=\"valores\" align=\"left\">"+recRes.getCreated().toString()+"</td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgLastUpdate")+"</td><td class=\"valores\" align=\"left\">"+recRes.getUpdated().toString()+"</td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgResourceAppearances")+"</td><td class=\"valores\" align=\"left\">"+recRes.getViews()+"</td></tr>");
            //out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgAppearancesEstipulated")+"</td><td class=\"valores\" align=\"left\">"+resource.getConfAttribute("views","")+"</td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgAppearancesEstipulated")+"</td><td class=\"valores\" align=\"left\">"+resource.getProperty("views","")+"</td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgResourceClicks")+"</td><td class=\"valores\" align=\"left\">"+recRes.getHits()+"</td></tr>");
            //out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgResourceClicksestipulated")+"</td><td class=\"valores\" align=\"left\">"+resource.getConfAttribute("hits","")+"</td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgResourceClicksestipulated")+"</td><td class=\"valores\" align=\"left\">"+resource.getProperty("hits","")+"</td></tr>");
            /*
            if(resource.getConfAttribute("evaluateHits")!=null && resource.getConfAttribute("evaluateHits").equals("true")){
                out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgResourceEndHits")+"</td><td class=\"valores\" align=\"left\">"+resource.getConfAttribute("hitsEndDate","")+"</td></tr>");
            }else{
                out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgResourceEndHits")+"</td><td class=\"valores\" align=\"left\">"+paramRequest.getLocaleString("NohitsEndDate")+"</td></tr>");
            }*/
            //out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgResourceEndHits")+"</td><td class=\"valores\" align=\"left\">"+resource.getConfAttribute("hitsEndDate","")+"</td></tr>");
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgResourceEndHits")+"</td><td class=\"valores\" align=\"left\">"+resource.getProperty("hitsEndDate","")+"</td></tr>");
            boolean status=recRes.isActive();
            SWBResourceURL urlResAct=paramRequest.getActionUrl();
            out.println("<tr><td align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgStatus")+"</td><td class=\"valores\" align=\"left\">");
            urlResAct.setParameter("tm",recRes.getWebSiteId());
            if (!status) {
                out.println("No activo");
            } else {
                out.println("Activo");
            }
            out.println("</td></tr>");
            SWBResourceURLImp url=(SWBResourceURLImp)paramRequest.getRenderUrl();
            url.setResourceBase(recRes);
            url.setWindowState(SWBResourceURL.WinState_MAXIMIZED);
            url.setMode(SWBResourceURL.Mode_ADMIN);
            
            out.println("</table>");
            out.println("</P>");
            out.println("</form>");
        }
        
    }
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, java.io.IOException {
        String accion = response.getAction();
        User user = response.getUser();
        try{
            WebSite ws = SWBContext.getWebSite(request.getParameter("tm"));
            //ResourceSrv resSrv=new ResourceSrv();
            if(accion.equals("status")){
                //RecResource Recres=DBResource.getInstance().getResource(request.getParameter("tm"),Long.valueOf(request.getParameter("id")).longValue());
                Resource Recres=ws.getResource(request.getParameter("id"));
                Recres.setActive((request.getParameter("status")!=null&&request.getParameter("status").equals("1")?true:false));
                //resSrv.setActive(DBResource.getInstance().getResource(request.getParameter("tm"),Long.valueOf(request.getParameter("id")).longValue()),Integer.parseInt(request.getParameter("status")),user.getId());
                response.setRenderParameter("id",request.getParameter("id"));
                response.setRenderParameter("tm",request.getParameter("tm"));
                response.setRenderParameter("act","edit");
                response.setRenderParameter("reload","1");
                ResourceType ptype = Recres.getResourceType();
                response.setRenderParameter("typemap",ptype.getWebSite().getId());
                response.setRenderParameter("type",""+ptype.getResourceMode());
                ResourceSubType pstype = Recres.getResourceSubType();
                response.setRenderParameter("stype",""+pstype.getId());
                response.setRenderParameter("stypemap",""+pstype.getWebSite().getId());
            }
            else if(accion.equals("update")){
                int camp=0;
                int type=0;
                String stype=null;
                String stypemap=null;
                if(request.getParameter("camp")!=null) camp=Integer.parseInt(request.getParameter("camp"));
                if(request.getParameter("type")!=null) type=Integer.parseInt(request.getParameter("type"));
                if(request.getParameter("stype")!=null) stype=request.getParameter("stype");
                Resource res=SWBContext.getWebSite(request.getParameter("tm")).getResource(request.getParameter("id"));
                if(stype!=null && stype.startsWith("0")) {
                    stypemap=SWBContext.WEBSITE_GLOBAL;
                }else{
                    stypemap=res.getWebSiteId();
                }
                if(request.getParameter("views")!=null && request.getParameter("views").length()>0){
                    //res.setConfAttribute("views", request.getParameter("views"));
                    res.setProperty("views", request.getParameter("views"));
                    //res.updateConfAttributesToDB();
                }else{
                    //res.removeConfAttribute("views");
                    res.removeProperty("views");
                    //res.updateConfAttributesToDB();
                }
                if(request.getParameter("hits")!=null && request.getParameter("hits").length()>0){
                    //res.setConfAttribute("hits", request.getParameter("hits"));
                    res.setProperty("hits", request.getParameter("hits"));
                    //res.updateConfAttributesToDB();
                }else{
                    //res.removeConfAttribute("hits");
                    res.removeProperty("hits");
                    //res.updateConfAttributesToDB();
                }
                if(request.getParameter("hitsEndDate")!=null && request.getParameter("hitsEndDate").length()>0){
                    //res.setConfAttribute("hitsEndDate", request.getParameter("hitsEndDate"));
                    res.setProperty("hitsEndDate", request.getParameter("hitsEndDate"));
                    //res.updateConfAttributesToDB();
                }else{
                    //res.removeConfAttribute("hitsEndDate");
                    res.removeProperty("hitsEndDate");
                    //res.updateConfAttributesToDB();
                }
                /*
                if(request.getParameter("evaluateHits")!=null && request.getParameter("evaluateHits").equals("on")){
                    res.setConfAttribute("evaluateHits", "true");
                    res.updateConfAttributesToDB();
                }else{
                    res.removeConfAttribute("evaluateHits");
                    res.updateConfAttributesToDB();
                }
                 */
                    /*
                    if(request.getParameter("portletWindow")!=null){
                        res.setConfAttribute("portletWindow", request.getParameter("portletWindow").trim());
                        res.updateConfAttributesToDB();
                    }*/
                if(request.getParameter("portletWindow")!=null && request.getParameter("portletWindow").equals("on")){
                    //res.setConfAttribute("portletWindow", "1");
                    res.setProperty("portletWindow", "1");
                    //res.updateConfAttributesToDB();
                }else{
                    //res.removeConfAttribute("portletWindow");
                    res.removeProperty("portletWindow");
                    //res.updateConfAttributesToDB();
                }
                
                int hitlog=0;
                if(request.getParameter("hitlog")!=null && request.getParameter("hitlog").equals("on")){
                    hitlog=1;
                }
                res.setTitle(request.getParameter("title"));
                res.setDescription(request.getParameter("description"));
                res.setPriority(Integer.parseInt(request.getParameter("priority")));
                //res.setCamp(request.getParameter("camp"));
                res.setResourceSubType(SWBContext.getWebSite(stypemap).getResourceSubType(stype));
                res.setModifiedBy(user);
                res.setHits(hitlog);

                //resSrv.updateResource(res.getRecResource().getTopicMapId(),Integer.parseInt(request.getParameter("id")),request.getParameter("title"),request.getParameter("description"),Integer.parseInt(request.getParameter("camp")),Integer.parseInt(stype),stypemap,Integer.parseInt(request.getParameter("priority")),res.getActive(),hitlog,user.getId());
                response.setRenderParameter("id",request.getParameter("id"));
                response.setRenderParameter("type",String.valueOf(type));
                response.setRenderParameter("stype",String.valueOf(stype));
                response.setRenderParameter("stypemap",res.getResourceSubType().getWebSite().getId());
                response.setRenderParameter("flag","update");
                response.setRenderParameter("tree","reload");
                response.setRenderParameter("act","edit");
                response.setRenderParameter("tm",request.getParameter("tm"));
                /*
                if(request.getParameter("appearancesEndDate")!=null && request.getParameter("appearancesEndDate").equals("1")){
                    response.setRenderParameter("evaluateHits","true");
                }*/
            }
        }
        catch(Exception e){log.error("Error while doing an action",e);}
    }
    
    
    public void doProcessRedirec(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        String act=(String)request.getAttribute("act");
        User usr = paramRequest.getUser();
        if(act!=null && (act.equals("add") || act.equals("copyRes"))){
            int camp=0;
            String type="0";
            String typemap=null;
            String stypemap=null;
            String stype="0";
            String user=null;
            String tm = request.getParameter("tm");
            if(request.getParameter("camp")!=null) camp=Integer.parseInt(request.getParameter("camp"));
            if(request.getParameter("type")!=null) type=request.getParameter("type");
            if(request.getParameter("typemap")!=null) typemap=request.getParameter("typemap");
            if(request.getParameter("stypemap")!=null) stypemap=request.getParameter("stypemap");
            if(request.getParameter("stype")!=null) stype=request.getParameter("stype");
            if(request.getAttribute("user")!=null)user=(String)request.getAttribute("user");
            
            WebSite tpm=null;
            Resource recRes=null;
            tpm = SWBContext.getWebSite(tm);
            if(act.equals("add")){
                //ResourceSrv resSrv=new ResourceSrv();
                try{
                    recRes=tpm.createResource();
                    recRes.setTitle(request.getParameter("title"));
                    recRes.setDescription(request.getParameter("description"));
                    recRes.setXmlConf("");
                    recRes.setActive(false);
                    recRes.setDeleted(false);
                    recRes.setCreator(usr);        
                    //recRes=resSrv.createResource(tm,request.getParameter("title"), request.getParameter("description"), "", camp, type,typemap,stypemap,stype,Integer.parseInt(request.getParameter("priority")), 0,user);
                }
                catch(Exception e){ log.error("Error while trying to create a resource. WBSubtypes.processAction.add",e);}
                //if(recRes!=null)response.setRenderParameter("id",String.valueOf(recRes.getId()));
                //SWBContext.getWebSite(recRes.getWebSiteId()).getResource(recRes.getId()).getResourceBase();
                Resource res=recRes;
                SWBResourceURLImp url=new SWBResourceURLImp(request,recRes,paramRequest.getTopic(),SWBResourceURL.UrlType_RENDER);
                url.setResourceBase(res);
                url.setMode(SWBResourceURL.Mode_ADMIN);
                url.setWindowState(SWBResourceURL.WinState_MAXIMIZED);
                url.setAction("add");
                
                url.setParameter("tree","reload");
                url.setParameter("type",String.valueOf(type));
                url.setParameter("typemap",typemap);
                url.setParameter("stypemap",stypemap);
                url.setParameter("stype",String.valueOf(stype));
                url.setParameter("flag","add");
                url.setParameter("tm",tpm.getId());
                url.setParameter("act","edit");
                //url.setAdminTopic(paramRequest.getAdminTopic());
                out.println("<META HTTP-EQUIV=REFRESH CONTENT=\"0; URL=" + url + "\">");
            }else if(act.equals("copyRes") || request.getAttribute("id")!=null){
                recRes = SWBContext.getWebSite(request.getParameter("tm")).getResource((String)request.getAttribute("id"));
                typemap=recRes.getResourceType().getWebSite().getId();
                type=recRes.getResourceType().getId();
                stype=recRes.getResourceSubType().getId();
                stypemap=recRes.getResourceSubType().getWebSite().getId();
            }
            String restype=SWBContext.getWebSite(typemap).getResourceType(type).getId();
            if(restype.equals("2")){ // de tipo adversiting
                String tmtype="";
                if(!typemap.equals(tpm.getId())) {
                    tmtype="0";
                }
                String stypetm="";
                if(!stype.equals("0")){
                    if(!stypemap.equals(tpm.getId())) {
                        stypetm="0";
                    }
                    out.println("<script>");
                    out.println("wbTree_executeAction('gotopath."+tpm.getId()+".advresources."+tmtype+type+"."+stypetm+stype+"');");
                    out.println("wbTree_reload();");
                    out.println("wbTree_executeAction('gotopath."+tpm.getId()+".advresources."+tmtype+type+"."+stypetm+stype+"."+recRes.getId()+"');");
                    out.println("</script>");
                }else{
                    out.println("<script>");
                    out.println("wbTree_executeAction('gotopath."+tpm.getId()+".advresources."+tmtype+type+"');");
                    out.println("wbTree_reload();");
                    out.println("wbTree_executeAction('gotopath."+tpm.getId()+".advresources."+tmtype+type+"."+recRes.getId()+"');");
                    out.println("wbTree_reload();");
                    out.println("</script>");
                }
            }else if(restype.equals("3")){ // de tipo sistema
                String tmtype="";
                if(!typemap.equals(tpm.getId())) {
                    tmtype="0";
                }
                String stypetm="";
                if(!stype.equals("0")){
                    if(!stypemap.equals(tpm.getId())) {
                        stypetm="0";
                    }
                    out.println("<script>");
                    out.println("wbTree_executeAction('gotopath."+tpm.getId()+".sysresources."+tmtype+type+"."+stypetm+stype+"');");
                    out.println("wbTree_reload();");
                    out.println("wbTree_executeAction('gotopath."+tpm.getId()+".sysresources."+tmtype+type+"."+stypetm+stype+"."+recRes.getId()+"');");
                    out.println("</script>");
                }else{
                    out.println("<script>");
                    out.println("wbTree_executeAction('gotopath."+tpm.getId()+".sysresources."+tmtype+type+"');");
                    out.println("wbTree_reload();");
                    out.println("wbTree_executeAction('gotopath."+tpm.getId()+".sysresources."+tmtype+type+"."+recRes.getId()+"');");
                    out.println("</script>");
                }
            }
        }
    }
    
}
