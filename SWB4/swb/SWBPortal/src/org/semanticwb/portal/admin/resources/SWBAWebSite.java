/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.*;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;
import org.semanticwb.portal.api.*;


/**
 *
 * @author juan.fernandez
 */
public class SWBAWebSite extends GenericResource {
    private Logger log=SWBUtils.getLogger(SWBAWebSite.class);
    Resource base=null;
    /** Creates a new instance of WBATopicMaps */
    public SWBAWebSite() {
    }
    
    /**
     * Muestra el html final del recurso
     */    
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        User user = paramRequest.getUser();
        //WebSiteSrv tmSrv=new WebSiteSrv();
        if((request.getParameter("act")==null || (request.getParameter("act").equals("add")||request.getParameter("act").equals("edit"))) || request.getParameter("act").equals("add1")){
            doEdit(request,response,paramRequest);
        }
        if(request.getParameter("act")!=null&&request.getParameter("act").equals("remove") && request.getParameter("tm")!=null){
            try{
                WebSite recTm=SWBContext.getWebSite(request.getParameter("tm"));
                
                if(recTm!=null) //tmSrv.updateTopicMap(recTm.getId(), null,null,null,null,1,recTm.getActive(),null,null,user.getId()))
                {
                    recTm.setDeleted(true);
                    recTm.setModifiedBy(user);
                    out.println("<script>wbTree_remove();</script>");
                    out.println(paramRequest.getLocaleString("msgTreeTopicMapDeletedID"));
                }
                else{
                    out.println("<font color=\"red\">"+paramRequest.getLocaleString("msgTreeNoDeleteTopicMapID")+": "+recTm.getId()+", "+paramRequest.getLocaleString("msgTreeTopicMapHaveTopicsAssociated")+"</font>");
                }
            }
            catch(Exception e){
                log.error(paramRequest.getLocaleString("msgLogErrorTryingDeleteTMID")+":"+request.getParameter("tm"),e);
                out.println("<font class=\"datos\">"+paramRequest.getLocaleString("msgErrorTryingDeleteTMID")+": "+request.getParameter("tm")+"</font>");
            }
        }else if(request.getParameter("act")!=null && request.getParameter("act").equals("active") && request.getParameter("tm")!=null) {
            try {
                WebSite tm=SWBContext.getWebSite(request.getParameter("tm"));
                tm.setActive(true);
                tm.setModifiedBy(user);
                out.println("<script>wbTree_reload();</script>");
                out.println("<font face=\"Verdana\" size=\"1\">");
                out.println(paramRequest.getLocaleString("msgTreeSiteActivated")+"...");
                out.println("</font>");
            }catch(Exception e){
                out.println("<font face=\"Verdana\" color=\"red\" size=\"1\">");
                out.println(paramRequest.getLocaleString("msgTreeErrorActivatingSite")+"...");
                out.println("</font>");
                log.error(e);
            }
        }else if(request.getParameter("act")!=null && request.getParameter("act").equals("unactive") && request.getParameter("tm")!=null) {
            try {
                WebSite tm=SWBContext.getWebSite(request.getParameter("tm"));
                tm.setActive(false);
                tm.setModifiedBy(user);
                out.println("<script>wbTree_reload();</script>");
                out.println("<font face=\"Verdana\" size=\"1\">");
                out.println(paramRequest.getLocaleString("msgTreeSiteUnactivated")+"...");
                out.println("</font>");
            }catch(Exception e){
                out.println("<font face=\"Verdana\" color=\"red\" size=\"1\">");
                out.println(paramRequest.getLocaleString("msgTreeErrorUnactivatingSite")+"...");
                out.println("</font>");
                log.error(e);
            }
        }
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        
        String act = null;
        PrintWriter out = response.getWriter();
        if(request.getParameter("act")!=null) act = request.getParameter("act");
        //if(act==null && paramRequest.getAction()!=null) act=paramRequest.getAction();
        //if(act==null) act="edit";
        if(request.getParameter("tree")!=null&&request.getParameter("tree").equals("reload")){
            if(request.getParameter("flag")!=null && request.getParameter("tm")!=null ){
                if(request.getParameter("flag").equals("add")){
                    out.println("<script>wbTree_executeAction('gotopath');");
                    out.println("wbTree_executeAction('gotopath."+request.getParameter("tm")+"');");
                    out.println("wbTree_reload();");
                    out.println("wbTree_executeAction('gotopath."+request.getParameter("tm")+"');");
                    out.println("</script>");
                }
                if(request.getParameter("flag").equals("update")){
                    out.println("<script>wbTree_executeAction('gotopath."+request.getParameter("tm")+"');");
                    out.println("wbTree_reload();</script>");
                }
            }
        }
        WebSite tm=null;
        if(request.getParameter("tm")!=null){
            tm=SWBContext.getWebSite(request.getParameter("tm"));
        }
        
        if(act!=null&&act.equals("add")){
            String title="";
            String description="";
            String tmid="";
            String hometitle="";
            String dominio="";
            if(request.getParameter("wsdomain")!=null) dominio=request.getParameter("wsdomain");
            if(request.getParameter("tmtitle")!=null) title=request.getParameter("tmtitle");
            if(request.getParameter("description")!=null) description=request.getParameter("description");
            if(request.getParameter("tmid")!=null) tmid=request.getParameter("tmid");
            if(request.getParameter("hometitle")!=null) hometitle=request.getParameter("hometitle");
            SWBResourceURL urladd=paramRequest.getRenderUrl();
            urladd.setParameter("act","add1");
            out.println("<form name=\"forma\" action=\""+urladd.toString()+"\" method=\"post\" class=\"box\">");
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<tr ><td colspan=2 class=\"tabla\">"+paramRequest.getLocaleString("msgTitleAddTopicMapStep1")+"</td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">Dominio:</td><td class=\"valores\"><input type=\"text\" class=\"campos\" name=\"wsdomain\" value=\""+dominio+"\" ></td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgTitle")+":</td><td class=\"valores\"><input type=\"text\" class=\"campos\" name=\"title\" value=\""+title+"\" ></td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgDescription")+":</td><td class=\"valores\"><textarea class=\"campos\" name=\"description\" cols=\"20\" rows=\"5\">"+description+"</textarea></td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgId")+":</td><td class=\"valores\"><input type=\"text\" class=\"campos\" name=\"tmid\" value=\""+tmid+"\" ></td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgMainSecctionTitle")+":</td><td class=\"valores\"><input type=\"text\" class=\"campos\" name=\"hometitle\" value=\""+hometitle+"\"></td></tr>");
            out.println("<tr ><td class=\"valores\" align=\"right\" colspan=\"2\"><HR size=\"1\" noshade><input type=\"button\"name=\"btn_next\" class=\"boton\" value=\""+paramRequest.getLocaleString("btnNext")+"\" onClick=\"javascript:valida(this.form);\"></td></tr>");
            out.println("</table>");
            out.println("<input type=\"hidden\" name=\"id\" value=\""+request.getParameter("id")+"\">");
            out.println("</form>");
            if(request.getParameter("tmexist")!=null && request.getParameter("tmexist").equals("1")){
                out.println("<script language=\"JavaScript\">");
                out.println("  alert(\'"+paramRequest.getLocaleString("tmexist")+"');");
                out.println("  document.forma.tmid.focus();");
                out.println("</script>");
            }
            out.println("<Script Language=\"JavaScript\">");
            out.println("function valida(forma)");
            out.println("{");
            out.println("   var flag=true;");
            out.println("   if (forma.title.value.replace(/^\\s*|\\s*$/g,\"\").length<1) ");
            out.println("   {");
            out.println("       alert(\"" +paramRequest.getLocaleString("alertNoEmptyAllowed") + "\");");
            out.println("       forma.title.focus();");
            out.println("       forma.title.select();");
            out.println("       flag=false;return false;");
            out.println("   }");
            out.println("   if (forma.tmid.value.replace(/^\\s*|\\s*$/g,\"\").length<1) ");
            out.println("   {");
            out.println("       alert(\"" + paramRequest.getLocaleString("alertNoEmptyAllowed") + "\");");
            out.println("       forma.tmid.focus();");
            out.println("       forma.tmid.select();");
            out.println("       flag=false;return false;");
            out.println("   }");
            out.println("   if (forma.hometitle.value.replace(/^\\s*|\\s*$/g,\"\").length<1) ");
            out.println("   {");
            out.println("       alert(\"" + paramRequest.getLocaleString("alertNoEmptyAllowed") + "\");");
            out.println("       forma.hometitle.focus();");
            out.println("       forma.hometitle.select();");
            out.println("       flag=false;return false;");
            out.println("   }");
            out.println("   if(flag)");
            out.println("   {");
            out.println("   forma.tmid.value = forma.tmid.value.replace(/\\s+/gm, \"_\");");
            out.println("       forma.submit();");
            out.println("   }else{return false;}");
            out.println("}");
            out.println("</Script>");
        }else if(act!=null && act.equals("add1")){
            SWBResourceURL urlsave=paramRequest.getActionUrl();
            urlsave.setParameter("act","add");
            
            SWBResourceURL urladd=paramRequest.getRenderUrl();
            urladd.setParameter("act","add");
            
            out.println("<form name=\"forma\" action=\""+urlsave+"\" method=\"post\" class=\"box\">");
            out.println("<input type=\"hidden\" name=\"wsdomain\" value=\""+request.getParameter("wsdomain")+"\">");
            out.println("<input type=\"hidden\" name=\"tmtitle\" value=\""+request.getParameter("title")+"\">");
            out.println("<input type=\"hidden\" name=\"description\" value=\""+request.getParameter("description")+"\">");
            out.println("<input type=\"hidden\" name=\"tmid\" value=\""+request.getParameter("tmid")+"\">");
            out.println("<input type=\"hidden\" name=\"hometitle\" value=\""+request.getParameter("hometitle")+"\">");
            out.println("<input type=\"hidden\" name=\"tmexist\" value=\"\">");
            
            
            //TODO: Falta implementar IdGenerator
            //String homeidgen=SWBContext.getIdGenerator().getID(request.getParameter("hometitle"),request.getParameter("tmid"),false);
            String homeidgen=request.getParameter("wsdomain")+"#"+request.getParameter("hometitle")+"_"+request.getParameter("tmid");
            
            ///////////////////////////////
            
            if(SWBContext.getWebSite(request.getParameter("tmid"))!=null) {
                out.println("<Script Language=\"JavaScript\">");
                out.println("   document.forma.action='"+urladd.toString()+"';");
                out.println("   document.forma.tmexist.value='1';");
                out.println("   document.forma.submit(); ");
                out.println("</Script>");
            }
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<tr ><td colspan=2 class=\"tabla\">"+paramRequest.getLocaleString("msgAddTopicMapStep2")+"</td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgLanguageDefect")+":</td><td class=\"valores\">");
            // P/languages
            
            Iterator<Language> iteraLang=SWBContext.getGlobalWebSite().listLanguages();
            out.println("<select name=\"language\" class=\"campos\">");
            while(iteraLang.hasNext()){
                Language recLang=iteraLang.next();
                out.println("<option value=\""+recLang.getId()+"\" Selected>"+recLang.getTitle());
                out.println("</option>");
            }
            out.println("</select>");
            out.println("</td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgRespositoryUse")+":</td><td class=\"valores\">");
            // P/Repositorios de usuarios
            Iterator<UserRepository> irepos=SWBContext.listUserRepositorys();
            out.println("<select name=\"repository\" class=\"campos\">");
            while(irepos.hasNext()){
                UserRepository dbUserRep=irepos.next();
                out.println("<option value=\""+dbUserRep.getId()+"\" Selected>"+dbUserRep.getTitle());
                out.println("</option>");
            }
            out.println("</select>");
            out.println("</td></tr>");
            out.println("<TR>");
            out.println("<TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgHomeId")+": </TD>");
            out.println("<TD class=\"valores\">");
            out.println("<INPUT name=\"homeid\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"50\"  value=\""+homeidgen+"\">");
            out.println("</TD>");
            out.println("</TR>");
            
            
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("IndexMgsTm")+":</td><td class=\"valores\">");
            // P/Indexadores
            
            //TODO: falta implementar  indexadores
//            HashMap hIndexers=WBIndexMgr.getInstance().getIndexers();
//            Iterator iIndexers=hIndexers.keySet().iterator();
            out.println("<select name=\"tmindexer\" class=\"campos\">");
            out.println("<option value=\"\">NONE</option>");
//            while(iIndexers.hasNext()){
//                String strIndexer=(String)iIndexers.next();
//                WBIndexer wbLuceneIndexer=(WBIndexer)hIndexers.get(strIndexer);
//                out.println("<option value=\""+wbLuceneIndexer.getName()+"\"");
//                if(tm!=null)
//                {
//                    WBIndexer indexer=WBIndexMgr.getInstance().getTopicMapIndexer(tm.getId());
//                    if(indexer!=null && strIndexer.equals(indexer.getName()))
//                    {
//                        out.println(" Selected");
//                    }
//                }
//                out.println(">"+wbLuceneIndexer.getName()+"</option>");
//            }
            
            ////////////////////////////////////
            
            out.println("</select>");
            out.println("</td></tr>");
            
            
            out.println("      <tr class=\"datos\">");
            out.println("        <td align=\"right\" class=\"valores\" colspan=\"2\"><HR size=\"1\" noshade>");
            out.println("           <input type=\"button\" class=\"boton\" value=\""+paramRequest.getLocaleString("btnPrevious")+"\" onClick=\"javaScript:send();\">");
            out.println("           <input type=\"button\" class=\"boton\" value=\""+paramRequest.getLocaleString("btnFinish")+"\" onClick=\"javascript:valida(this.form);\">");
            out.println("        </td>");
            out.println("       </tr>");
            out.println("</table>");
            out.println("</form>");
            out.println("<Script Language=\"JavaScript\">");
            
            out.println("function send() ");
            out.println("{ ");
            out.println("   document.forma.action='"+urladd.toString()+"';");
            out.println("   document.forma.submit(); ");
            out.println("} ");
            
            out.println("function valida(forma)");
            out.println("{");
            out.println("   var flag=true;");
            out.println("   if (forma.homeid.value.replace(/^\\s*|\\s*$/g,\"\").length<1) ");
            out.println("   {");
            out.println("       alert(\"" +  paramRequest.getLocaleString("alertMsgNoEmptyAllowed") + "\");");
            out.println("       forma.homeid.focus();");
            out.println("       forma.homeid.select();");
            out.println("       flag=false;");
            out.println("   }");
            out.println("   else if (forma.homeid.value.length>0)");
            out.println("   { ");
            out.println("       var valid=\"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_\"");
            out.println("       var ok = \"no\";");
            out.println("       var temp;");
            out.println("       for (var i=0; i<forma.homeid.value.length; i++)");
            out.println("       {");
            out.println("           temp = \"\" + forma.homeid.value.substring(i, i+1);");
            out.println("           if (valid.indexOf(temp) != \"-1\") ok = \"yes\";");
            out.println("           else ok = \"no\";");
            out.println("       }");
            out.println("       if (ok == \"no\")");
            out.println("       {");
            out.println("          alert(\""+paramRequest.getLocaleString("alertMsgInvalidCharacters")+":[0-9],[a-z],_)\");");
            out.println("          forma.homeid.focus();");
            out.println("          forma.homeid.select();");
            out.println("          flag=false;");
            out.println("       }");
            out.println("   }");
            out.println(" if(flag){");
            out.println("   forma.homeid.value = forma.homeid.value.replace(/\\s+/gm, \"_\");");
            out.println("   forma.submit();");
            out.println(" }");
            out.println("}");
            out.println("</Script>");
        }
        if(act!=null && act.equals("edit") && tm!=null){
            SWBResourceURL urledit=paramRequest.getActionUrl();
            urledit.setAction("update");
            out.println("<FORM name=\"info\" action=\""+urledit.toString()+"\">");
            out.println("<p class=\"box\"><TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<TR>");
            out.println("<TD width=\"150\" align=\"right\" class=\"datos\"> "+paramRequest.getLocaleString("msgIdentifier")+"</TD>");
            out.println("<TD class=\"valores\">");
            out.println(tm.getId());
            out.println("<INPUT name=\"tmid\" type=\"hidden\" value=\""+ tm.getId()+"\">");
            out.println("<INPUT name=\"tmborrado\" type=\"hidden\" value=\""+ (tm.isDeleted()?"1":"0")+"\">");
            out.println("</TD>");
            out.println("</TR>");
            out.println("</TABLE></P>");
            out.println("<P class=\"box\">");
            out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<TR>");
            out.println("<TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\">"+paramRequest.getLocaleString("msgStatus")+"</TD>");
            out.println("<TD class=\"valores\">");
            if(tm.isActive()){
                out.println("<INPUT name=\"status\" type=\"radio\" value=\"1\" checked class=\"campos\">");
                out.println(paramRequest.getLocaleString("msgVisible")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                ");
                out.println("<INPUT name=\"status\" type=\"radio\" value=\"0\" class=\"campos\"> "+paramRequest.getLocaleString("msgInvisible"));
            }
            else {
                out.println("<INPUT name=\"status\" type=\"radio\" value=\"1\" class=\"campos\">");
                out.println(paramRequest.getLocaleString("msgVisible")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                ");
                out.println("<INPUT name=\"status\" type=\"radio\" value=\"0\" checked class=\"campos\">"+paramRequest.getLocaleString("msgInvisible"));
            }
            out.println("</TD>");
            out.println("</TR>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">Dominio:</td><td class=\"valores\"><input type=\"text\" class=\"campos\" name=\"tmtitle\" value=\""+tm.getProperty("domain")+"\" ></td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgTitle")+":</td><td class=\"valores\"><input type=\"text\" class=\"campos\" name=\"tmtitle\" value=\""+tm.getTitle()+"\" ></td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgDescription")+":</td><td class=\"valores\"><textarea type=\"text\" class=\"campos\" name=\"description\" cols=\"20\" rows=\"5\">"+tm.getDescription()+"</textarea></td></tr>");
            out.println("<TR>");
            out.println("<TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgHistory")+" </TD>");
            //TODO: falta implementar dateFormat()
            //out.println("<TD class=\"valores\">"+paramRequest.getLocaleString("msgCreationDate")+":&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+SWBUtils.dateFormat(tm.getCreated()) +"<BR>");
            out.println("<TD class=\"valores\">"+paramRequest.getLocaleString("msgCreationDate")+":&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+tm.getCreated() +"<BR>");
            //TODO: falta implementar dateFormat()
            //out.println(paramRequest.getLocaleString("msgModificationDate")+": "+ SWBUtils.dateFormat(tm.getUpdated())+"</TD>");
            out.println(paramRequest.getLocaleString("msgModificationDate")+": "+ tm.getUpdated()+"</TD>");
            out.println("</TR>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgLanguageDefect")+":</td><td class=\"valores\">");
            // P/languages
            Iterator<Language> iteraLang=tm.listLanguages();
            out.println("<select name=\"language\" class=\"campos\">");
            while(iteraLang.hasNext()){
                Language recLang=iteraLang.next();
                String selected="";
                if(tm.getId().equals(recLang.getId())) selected="Selected"; //"IDM_WB"+recLang.getId()
                out.println("<option value=\""+recLang.getId()+"\" "+selected+">"+recLang.getTitle());
                out.println("</option>");
            }
            out.println("</select>");
            out.println("</td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgRespositoryUse")+":</td><td class=\"valores\">");
            // P/Repositorios de usuarios
            Iterator<UserRepository> irepos=SWBContext.listUserRepositorys();
            out.println("<select name=\"repository\" class=\"campos\">");
            while(irepos.hasNext()){
                UserRepository dbUserRep=irepos.next();
                String selected="";
                if(tm.getUserRepository().getId().equals(dbUserRep.getId())) selected="Selected";
                out.println("<option value=\""+dbUserRep.getId()+"\""+ selected + ">"+dbUserRep.getTitle());
                out.println("</option>");
            }
            out.println("</select>");
            out.println("</td></tr>");
            
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("IndexMgsTm")+":</td><td class=\"valores\">");
            // P/Indexadores
            
            //TODO: Implementar cuando esten listo los Indexadores
            
//            HashMap hIndexers=WBIndexMgr.getInstance().getIndexers();
//            WBIndexer wbtmIndexer=WBIndexMgr.getInstance().getTopicMapIndexer(tm.getId());
//            Iterator iIndexers=hIndexers.keySet().iterator();
            out.println("<select name=\"tmindexer\" class=\"campos\">");
            out.println("<option value=\"\">NONE</option>");            
//            while(iIndexers.hasNext()){
//                String strIndexer=(String)iIndexers.next();
//                WBIndexer wbLuceneIndexer=(WBIndexer)hIndexers.get(strIndexer);
//                String sselected="";
//                if(wbtmIndexer!=null && wbtmIndexer.getName().equals(wbLuceneIndexer.getName())) sselected="Selected";
//                out.println("<option value=\""+wbLuceneIndexer.getName()+"\" "+sselected+">"+wbLuceneIndexer.getName()+"</option>");
//            }
            
            ////////////////
            
            out.println("</select>");
            out.println("</td></tr>");
            
            out.println("<tr><td colspan=\"2\" align=\"right\"><HR size=\"1\" noshade><INPUT type=\"submit\" class=\"boton\" name=\"enviar\" value=\""+paramRequest.getLocaleString("btnSend")+"\"></td></tr>");
            out.println("</TABLE></P>");
            out.println("</FORM>");
        }else if(act==null || act.equals("view") && tm!=null){
            out.println("<p class=\"box\"><TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<TR>");
            out.println("<TD width=\"150\" align=\"right\" class=\"datos\"> "+paramRequest.getLocaleString("msgIdentifier")+"</TD>");
            out.println("<TD class=\"valores\">");
            out.println(tm.getId());
            //out.println("<INPUT name=\"tmid\" type=\"hidden\" value=\""+ tm.getId()+"\">");
            //out.println("<INPUT name=\"tmborrado\" type=\"hidden\" value=\""+ tm.getDbdata().getDeleted()+"\">");
            out.println("</TD>");
            out.println("</TR>");
            out.println("</TABLE></P>");
            out.println("<P class=\"box\">");
            out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<TR>");
            out.println("<TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\">"+paramRequest.getLocaleString("msgStatus")+"</TD>");
            out.println("<TD class=\"valores\">");
            if(tm.isActive()){
                out.println(paramRequest.getLocaleString("msgVisible")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                ");
            }
            else {
                out.println(paramRequest.getLocaleString("msgInvisible"));
            }
            out.println("</TD>");
            out.println("</TR>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgTitle")+":</td><td class=\"valores\">"+tm.getTitle()+"</td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgDescription")+":</td><td class=\"valores\">"+tm.getDescription()+"</td></tr>");
            out.println("<TR>");
            out.println("<TD width=\"150\" align=\"right\" valign=\"top\" class=\"datos\"> "+paramRequest.getLocaleString("msgHistory")+" </TD>");
            //TODO: falta implementar dateFormat()
            //out.println("<TD class=\"valores\">"+paramRequest.getLocaleString("msgCreationDate")+":&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+SWBUtils.dateFormat(tm.getCreated()) +"<BR>");
            out.println("<TD class=\"valores\">"+paramRequest.getLocaleString("msgCreationDate")+":&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+tm.getCreated() +"<BR>");
            //TODO: falta implementar dateFormat()
            //out.println(paramRequest.getLocaleString("msgModificationDate")+": "+ SWBUtils.dateFormat(tm.getUpdated())+"</TD>");
            out.println(paramRequest.getLocaleString("msgModificationDate")+": "+ tm.getUpdated()+"</TD>");
            out.println("</TR>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgLanguageDefect")+":</td><td class=\"valores\">");
            // P/languages
            Language lang = tm.getLanguage(); 
            String slang=null;
            int pos=-1;
            if(lang!=null)
            {
                slang=lang.getId();
                pos=slang.indexOf("_WB");
                if(pos>-1) slang=slang.substring(pos+3);
            }
            else slang="es";
            Language recLang=tm.getLanguage(slang);
            if(recLang!=null) out.println(recLang.getTitle());
            else out.println("Español");
            out.println("</td></tr>");
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgRespositoryUse")+":</td><td class=\"valores\">");
            // P/Repositorios de usuarios
            if(tm.getUserRepository()!=null) out.println(tm.getUserRepository().getTitle());
            out.println("</td></tr>");
            
            out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("IndexMgsTm")+":</td><td class=\"valores\">");
            // P/Indexador
            //TODO: Implementar cuando esten Indexadores
            
//            if(tm.getDbdata()!=null) out.println(tm.getDbdata().getIndexer());
            
            
            out.println("Pendiente</td></tr>");
            
            //out.println("<tr><td colspan=\"2\" align=\"right\"><HR size=\"1\" noshade><INPUT type=\"submit\" class=\"boton\" name=\"enviar\" value=\""+paramRequest.getLocaleString("btnSend")+"\"></td></tr>");
            out.println("</TABLE></P>");
            //out.println("</FORM>");
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        
        String accion = response.getAction();
        if(accion==null) accion=request.getParameter("act");
        User user = response.getUser();
        try{
            //WebSiteSrv tmSrv=new WebSiteSrv();
            if(accion.equals("update")){
                
                WebSite ws = SWBContext.getWebSite(request.getParameter("tmid"));
                String title =request.getParameter("tmtitle");
                String description = request.getParameter("description");
                String language = request.getParameter("language");
                String s_usrRepo = request.getParameter("repository");
                String Indice = request.getParameter("tmindexer");
                String deleted = request.getParameter("tmborrado");
                String active = request.getParameter("status");
                String wsdomain = request.getParameter("wsdomain");
                
                Language lang = ws.getLanguage(language);
                ws.setLanguage(lang);
                ws.setTitle(title);
                ws.setDescription(description);
                ws.setActive((active!=null&&active.equals("1")?true:false));
                ws.setDeleted((deleted!=null&&deleted.equals("1")?true:false));
                ws.setProperty("domain", wsdomain);
                ws.setUserRepository(SWBContext.getUserRepository(s_usrRepo));
                ws.setModifiedBy(user);
                        
                //tmSrv.updateWebSite(ws,,,,,,,,,user.getId());
                response.setRenderParameter("tm",request.getParameter("tmid"));
                response.setRenderParameter("flag","update");
                response.setRenderParameter("tree","reload");
                response.setRenderParameter("act","edit");
            }
            if(accion.equals("add")){
                WebSite tm=null;
                try{
                    String title =request.getParameter("tmtitle");
                    String description = request.getParameter("description");
                    String language = request.getParameter("language");
                    String s_usrRepo = request.getParameter("repository");
                    String Indice = request.getParameter("tmindexer");
                    String deleted = request.getParameter("tmborrado");
                    String active = request.getParameter("status");
                    String wsdomain = request.getParameter("wsdomain");

                    Language lang = SWBContext.getGlobalWebSite().getLanguage(language);
                    tm.setLanguage(lang);
                    tm.setTitle(title);
                    tm.setDescription(description);
                    tm.setActive((active!=null&&active.equals("1")?true:false));
                    tm.setDeleted((deleted!=null&&deleted.equals("1")?true:false));
                    tm.setProperty("domain", wsdomain);

                    tm.setUserRepository(SWBContext.getUserRepository(s_usrRepo));
                    tm.setCreator(user);    
                    
                    
                    
                    //tm=tmSrv.createTopicMap(request.getParameter("tmid"), request.getParameter("tmtitle"), request.getParameter("homeid"), request.getParameter("hometitle"), request.getParameter("language"), request.getParameter("description"), request.getParameter("repository"),request.getParameter("tmindexer"),user.getId());
                }
                catch(Exception e){ log.error(response.getLocaleString("msgLogErrorCreateTopicMap")+". WBATopic.processAction.add",e);}
                response.setRenderParameter("tm",tm.getId());
                response.setRenderParameter("flag","add");
                response.setRenderParameter("tree","reload");
                response.setRenderParameter("act","edit");
            }
        }
        catch(Exception e){log.error(e);}
    }
    
    
}
