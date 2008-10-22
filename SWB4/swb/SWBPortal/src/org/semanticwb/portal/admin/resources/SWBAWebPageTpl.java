/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.portal.api.*;

/**
 *
 * @author juan.fernandez
 */
public class SWBAWebPageTpl extends GenericResource {

    private Logger log=SWBUtils.getLogger(SWBAWebPageTpl.class);
    String webpath = SWBPlatform.getContextPath();
    String distributor = SWBPlatform.getEnv("wb/distributor");
    Vector vvector = new Vector();
    
    /** Creates a new instance of SWBAWebPageTpl */
    public SWBAWebPageTpl() {
    }

    /**
     * Muestra el html final del recurso
     */
//    @Override
//    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        //StringBuffer sbRet=new StringBuffer();
//        PrintWriter out=response.getWriter();
//        String action=request.getParameter("action");
//        if(action==null && paramRequest.getAction()!=null && paramRequest.getAction().equals("add")) action=paramRequest.getAction();
//        //TopicSrv tpsrv=new TopicSrv();
//        WebPage tp=paramRequest.getTopic();
//        if(request.getParameter("tm")!=null && request.getParameter("tp")!=null){
//            tp=SWBContext.getWebSite(request.getParameter("tm")).getWebPage(request.getParameter("tp"));
//        }
//        if(tp!=null){
//            
//            if(request.getParameter("WBAGotoTree")!=null) {
//                out.print("<script>");
//                out.print("wbTree_executeAction('gotonode.topic."+tp.getWebSiteId()+"."+tp.getId()+"');");
//                out.print("wbTree_reload();");
//                out.println("</script>");
//            }
//            
//            WebPage tp1=paramRequest.getTopic();
//            WebSite tm1=tp1.getWebSite();
//            WebSite tm=tp.getWebSite();
//            User user=paramRequest.getUser();
//            SWBResourceURL urlResAct=paramRequest.getActionUrl();
//            urlResAct.setParameter("tm",String.valueOf(tm.getId()));
//            urlResAct.setParameter("tp",String.valueOf(tp.getId()));
//            urlResAct.setParameter("userid",String.valueOf(user.getId()));
//            String resprec = SWBPlatform.getEnv("wb/response");
//            String baserut = SWBPlatform.getContextPath();
//            String path= baserut + resprec + "/"+ tp1.getWebSiteId() + "/" + tp1.getId() + "/" + paramRequest.getResourceBase().getId();
//            if(action==null && request.getParameter("noEdit")==null){
//                path=path+"/?action=add"+"&&tm="+tm.getId()+"&&tp="+tp.getId();
//                out.println("<style type=\"text/css\">");
//                out.println("* {scrollbar-3d-light-color:#EBEFF3; scrollbar-arrow-color:#5D6B8D;");
//                out.println("scrollbar-base-color:#5D6B8D; scrollbar-dark-shadow-color:#EBEFF3;");
//                out.println("scrollbar-face-color:#EBEFF3; scrollbar-highlight-color:#EBEFF3;");
//                out.println("scrollbar-shadow-color:#EBEFF3; scrollbar-track-color: #EBEFF3;}");
//                out.println("</style>");
//                out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
//                out.println("<script language=\"JavaScript\">");
//                out.println("<!--");
//                out.println("function MM_preloadImages() { //v3.0");
//                out.println("var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();");
//                out.println("var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)");
//                out.println("if (a[i].indexOf(\"#\")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}");
//                out.println("}");
//                
//                out.println("function MM_findObj(n, d) { //v4.0");
//                out.println("var p,i,x;  if(!d) d=document; if((p=n.indexOf(\"?\"))>0&&parent.frames.length) {");
//                out.println("d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}");
//                out.println("if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];");
//                out.println("for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);");
//                out.println("if(!x && document.getElementById) x=document.getElementById(n); return x;");
//                out.println("}");
//                
//                out.println("function MM_nbGroup(event, grpName) { //v3.0");
//                out.println("var i,img,nbArr,args=MM_nbGroup.arguments;");
//                out.println("if (event == \"init\" && args.length > 2) {");
//                out.println("if ((img = MM_findObj(args[2])) != null && !img.MM_init) {");
//                out.println("img.MM_init = true; img.MM_up = args[3]; img.MM_dn = img.src;");
//                out.println("if ((nbArr = document[grpName]) == null) nbArr = document[grpName] = new Array();");
//                out.println("nbArr[nbArr.length] = img;");
//                out.println("for (i=4; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {");
//                out.println("if (!img.MM_up) img.MM_up = img.src;");
//                out.println("img.src = img.MM_dn = args[i+1];");
//                out.println("nbArr[nbArr.length] = img;");
//                out.println("} }");
//                out.println("} else if (event == \"over\") {");
//                out.println("document.MM_nbOver = nbArr = new Array();");
//                out.println("for (i=1; i < args.length-1; i+=3) if ((img = MM_findObj(args[i])) != null) {");
//                out.println("if (!img.MM_up) img.MM_up = img.src;");
//                out.println("img.src = (img.MM_dn && args[i+2]) ? args[i+2] : args[i+1];");
//                out.println("nbArr[nbArr.length] = img;");
//                out.println("}");
//                out.println("} else if (event == \"out\" ) {");
//                out.println("for (i=0; i < document.MM_nbOver.length; i++) {");
//                out.println("img = document.MM_nbOver[i]; img.src = (img.MM_dn) ? img.MM_dn : img.MM_up; }");
//                out.println("} else if (event == \"down\") {");
//                out.println("if ((nbArr = document[grpName]) != null)");
//                out.println("for (i=0; i < nbArr.length; i++) { img=nbArr[i]; img.src = img.MM_up; img.MM_dn = 0; }");
//                out.println("document[grpName] = nbArr = new Array();");
//                out.println("for (i=2; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {");
//                out.println("if (!img.MM_up) img.MM_up = img.src;");
//                out.println("img.src = img.MM_dn = args[i+1];");
//                out.println("nbArr[nbArr.length] = img;");
//                out.println("} }");
//                out.println("}");
//                
//                out.println("function MM_openBrWindow(theURL,winName,features) { //v2.0");
//                out.println("window.open(theURL,winName,features);");
//                out.println("}");
//                out.println("//-->");
//                out.println("</script>");
//                
//                out.println("<FORM name=\"descr\" action=\""+paramRequest.getRenderUrl().setAction("add").toString()+"\" class=\"box\">");
//                out.println("<input type=\"hidden\" name=\"tm\" value=\"" + tm.getId() + "\">");
//                out.println("<input type=\"hidden\" name=\"tp\" value=\"" + tp.getId() + "\">");
//                out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\" >");
//                
//                out.println("<tr class=\"datos\">");
//                out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("Action")+"</td>");
//                out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("Title")+"</td>");
//                out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("Creation_Date")+"</td>");
//                out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("Last_Update")+"</td>");
//                out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("Author")+"</td>");
//                out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("ownerTpl")+"</td>");
//                out.println("<td class=\"tabla\" align=\"left\">"+paramRequest.getLocaleString("Priority")+"</td>");
//                out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("Status")+"</td>");
//                out.println("</tr>");
//                
//                StringBuffer owntpls=new StringBuffer();
//                String name="",created="",update="",author="",nombreC="",email="",srule="";
//                int status=0;
//                java.math.BigInteger cont=new java.math.BigInteger("0");
//                int mNum=0;
//                boolean entrar = false;
//                boolean bactive=false;
//                boolean bparent=true;
//                Iterator<TemplateRef> tpocurren = tp.listTemplateRefs();//("CNF_WBTemplate");
//                while (tpocurren.hasNext()) {
//                    TemplateRef ocurre =  tpocurren.next();
//                    if (ocurre.getTemplate().isDeleted()) { //!ocurre.isResourceRef() && 
//                        Template templ;
//                        String msg = "";
//                        try {
//                            templ = ocurre.getTemplate(); //TemplateMgr.getInstance().getTemplate(tm.getId(),ocurre.getResourceData());
//                            if(templ!=null && !templ.isDeleted() && templ.isActive()){
//                                name = templ.getTitle();
//                                if (templ.getCreated().toString() != null) ;
//                                created = templ.getCreated().toString();
//                                if (templ.getUpdated().toString() != null)
//                                    update = templ.getUpdated().toString();
//                                Iterator<RuleRef> Irules = templ.listRuleRefs(); //getRules();
//                                boolean flag3 = true;
//                                while (Irules.hasNext()) {
//                                    Rule temprule = Irules.next().getRule(); //DBRule.getInstance().getRule(templ.getTopicMapId(),Integer.parseInt(Irules.next().toString()));
//                                    if (flag3)
//                                        srule = temprule.getTitle();
//                                    else
//                                        srule += "," + temprule.getTitle();
//                                    flag3 = false;
//                                }
//                                Template recTpl = templ;
//                                String idauthor = recTpl.getCreator().getId();
//                                if (idauthor.length() > 1) {
//                                    User recadmuser = recTpl.getCreator(); //DBUser.getInstance().getUserById(idauthor);
//                                    if (recadmuser!=null && recadmuser.getName() != null) author = recadmuser.getName();
//                                    if (recadmuser!=null && recadmuser.getUsrEmail() != null) email = recadmuser.getUsrEmail();
//                                }
////                                status = ocurre.isActive();
////                                if(status==1) 
//                                    bactive=ocurre.isActive();
//                                
//                                boolean isglobal=false;
//                                String stype=paramRequest.getLocaleString("theSitio");
//                                if(templ.getWebSiteId().equals(SWBContext.WEBSITE_GLOBAL)) isglobal=true;
//                                
//                                if(isglobal){
//                                    urlResAct.setParameter("tplid",String.valueOf(templ.getId())+"|"+SWBContext.WEBSITE_GLOBAL);
//                                    stype=paramRequest.getLocaleString("tplglobal");
//                                }
//                                else{
//                                    urlResAct.setParameter("tplid",String.valueOf(templ.getId()));
//                                }
//                                
//                                urlResAct.setParameter("action","remove");
//                                
//                                //manejo de tr color
//                                mNum=+2;
//                                cont=new java.math.BigInteger(String.valueOf(cont.intValue()+1));
//                                if(cont.intValue()==1){ cont=new java.math.BigInteger(String.valueOf(cont.intValue()+2));}
//                                if(cont.mod(new java.math.BigInteger(String.valueOf(mNum))).intValue()==0) {owntpls.append("<TR bgcolor=\"#EFEDEC\">");}
//                                else {owntpls.append("<TR bgcolor=\"#FFFFFF\">");}
//                                
//                                //Acciones
//                                
//                                //eliminar template de sección
//                                owntpls.append("<td class=\"valores\">");
//                                owntpls.append("<a href=\""+urlResAct.toString()+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("msgRemove") +"?'))return true; else return false;\"><img border=0 src=\""+SWBPlatform.getContextPath()+"wbadmin/images/eliminar.gif\" alt=\""+paramRequest.getLocaleString("remove")+"\"></a>");
//                                
//                                //preview de template
//                                SWBResourceURL url=paramRequest.getRenderUrl();
//                                url.setParameter("action1","preview");
//                                url.setParameter("tp",tp.getId());
//                                url.setParameter("tm",tm.getId());
//                                url.setParameter("tmtpl",templ.getWebSiteId());
//                                url.setParameter("id",""+templ.getId());
//                                owntpls.append("<a href=\""+url.toString()+"\" class=\"link\">");
//                                owntpls.append("<img src=\"" + webpath + "wbadmin/images/preview.gif\" border=0 alt=\""+paramRequest.getLocaleString("previewdocument")+"\">");
//                                owntpls.append("</a>");
//                                owntpls.append("</td>");
//                                
//                                boolean tplaccess=false;
//                                owntpls.append("<td class=\"valores\">");
//                                
//                                if(AdmFilterMgr.getInstance().haveAccess2GrpTemplate(paramRequest.getUser(), templ.getWebSiteId(), templ.getTemplateGroup().getId())>0){
//                                    SWBResourceURLImp url1= (SWBResourceURLImp)paramRequest.getRenderUrl();
//                                    url1.setParameter("action1","reenvia");
//                                    url1.setParameter("tp",tp.getId());
//                                    url1.setParameter("tm",tm.getId());
//                                    url1.setParameter("tmtpl",templ.getWebSiteId());
//                                    url1.setParameter("id",""+templ.getId());
//                                    url1.setParameter("noEdit","1");
//                                    
//                                    owntpls.append("<a href=\""+url1.toString()+"\"\">");
//                                    tplaccess=true;
//                                    owntpls.append(name);
//                                    owntpls.append("</a> "); ////////////////////////////////////////////////////////
//                                }else{
//                                    owntpls.append(name);
//                                }
//                                owntpls.append("</td>");
//                                
//                                owntpls.append("<td class=\"valores\">" + created + "</td>");
//                                owntpls.append("<td class=\"valores\">" + update + "</td>");
//                                owntpls.append("<td class=\"valores\"><a href=\"mailto:" + email + "\">");
//                                owntpls.append(author);
//                                owntpls.append("</a></td><td class=\"valores\">"+ stype +"</td>");
//                                
//                                owntpls.append("<td class=\"valores\" align=\"left\">");
//                                String sname=""+templ.getId();
//                                if(isglobal)sname+="|"+SWBContext.WEBSITE_GLOBAL;
//                                owntpls.append("<select name=\"priority" + sname + "\" onChange=\"cambia(this.form,\'" + sname + "\');\">");
//                                if (ocurre.getPriority() == 1)
//                                    owntpls.append("<option value=\"1\" Selected>" + paramRequest.getLocaleString("defecto") + "</option>");
//                                else
//                                    owntpls.append("<option value=\"1\">" + paramRequest.getLocaleString("defecto") + "</option>");
//                                if (ocurre.getPriority() == 2)
//                                    owntpls.append("<option value=\"2\" Selected>" + paramRequest.getLocaleString("low") + "</option>");
//                                else
//                                    owntpls.append("<option value=\"2\">" + paramRequest.getLocaleString("low") + "</option>");
//                                if (ocurre.getPriority() == 3)
//                                    owntpls.append("<option value=\"3\" Selected>" + paramRequest.getLocaleString("media") + "</option>");
//                                else
//                                    owntpls.append("<option value=\"3\">" + paramRequest.getLocaleString("media") + "</option>");
//                                if (ocurre.getPriority() == 4)
//                                    owntpls.append("<option value=\"4\" Selected>" + paramRequest.getLocaleString("high") + "</option>");
//                                else
//                                    owntpls.append("<option value=\"4\">" + paramRequest.getLocaleString("high") + "</option>");
//                                if (ocurre.getPriority() == 5)
//                                    owntpls.append("<option value=\"5\" Selected>" + paramRequest.getLocaleString("priority") + "</option>");
//                                else
//                                    owntpls.append("<option value=\"5\">" + paramRequest.getLocaleString("priority") + "</option>");
//                                owntpls.append("</select>");
//                                owntpls.append("</td>");
//                                
//                                
//                                owntpls.append("<td class=\"valores\">");
//                                if (status == 0) {
//                                    urlResAct.setParameter("action","status");
//                                    urlResAct.setParameter("sttpl","1");
//                                    if(isglobal) urlResAct.setParameter("tplid",String.valueOf(templ.getId())+"|"+SWBContext.WEBSITE_GLOBAL);
//                                    else urlResAct.setParameter("tplid",String.valueOf(templ.getId()));
//                                    owntpls.append("<A href=\"" + urlResAct.toString() + "\" onmousemove=\"window.status='" + paramRequest.getLocaleString("status") + "';return true;\" >");
//                                    owntpls.append("<IMG alt=\"" + paramRequest.getLocaleString("offLine") + "\" border=0 src=\"" + webpath + "wbadmin/images/offline.gif\"></A>");
//                                } else {
//                                    urlResAct.setParameter("action","status");
//                                    urlResAct.setParameter("sttpl","0");
//                                    if(isglobal) urlResAct.setParameter("tplid",String.valueOf(templ.getId())+"|"+SWBContext.WEBSITE_GLOBAL);
//                                    else urlResAct.setParameter("tplid",String.valueOf(templ.getId()));
//                                    owntpls.append("<A onmousemove=\"window.status='" + paramRequest.getLocaleString("status") + "';return true;\" href=\"" + urlResAct.toString() + "\">");
//                                    owntpls.append("<IMG alt=\"" + paramRequest.getLocaleString("onLine") + "\" border=0 src=\"" + webpath + "wbadmin/images/online.gif\"></A>");
//                                }
//                                owntpls.append("</td>");
//                                owntpls.append("</tr>");
//                                owntpls.append("<Script Language=\"JavaScript\">");
//                                owntpls.append("function cambia(forma,tplid) ");
//                                owntpls.append("{ ");
//                                owntpls.append("   forma.tplid.value=tplid;");
//                                owntpls.append("   forma.tplPriorityAction.value='tplPriorityAction';");
//                                owntpls.append("   forma.action='"+urlResAct.toString()+"';");
//                                owntpls.append("   forma.submit(); ");
//                                owntpls.append("} ");
//                                owntpls.append("</Script>");
//                            }
//                            
//                        } catch (Exception e) {
//                            log.error("Error while showing templates for topic:"+tp.getId(),e);
//                        }
//                    }else if(ocurre!=null && ocurre.getResourceRef().equals("_noparent")){
//                        bparent=false;
//                    }
//                }
//                
//                if(!bactive){
//                    String check="";
//                    if(bparent) {
//                        check="checked";
//                    }
//                    out.println("<input type=\"hidden\" name=\"tm\" value=\"\">");
//                    out.println("<input type=\"hidden\" name=\"tp\" value=\"\">");
//                    
//                    //out.println("<input type=\"checkbox\" name=\"ineritedtpls\"" + check+" onClick=\"javascript:valida1(this.form);\">"+paramRequest.getLocaleString("_parent"));
//                    
//                    String sparent="parent";
//                    out.println("<Script Language=\"JavaScript\">");
//                    out.println("function valida1(forma)");
//                    out.println("{");
//                    out.println("    if(forma.ineritedtpls.checked==false) {");
//                    out.println("    forma.action='"+paramRequest.getActionUrl().setAction("_noparent")+"'; }");
//                    out.println("    else { forma.action='"+paramRequest.getActionUrl().setAction("_parent")+"'; }");
//                    out.println("    forma.tm.value='"+tm.getId()+"';");
//                    out.println("    forma.tp.value='"+tp.getId()+"';");
//                    out.println("    forma.submit();");
//                    out.println("}");
//                    out.println("</Script>");
//                }
//                
//                if(!bactive && bparent){
//                    boolean ponerMSG = false;
//                    //Iterator herTempl = tp.getConfigData("CNF_WBTemplate");
//                    OccTransformer transformer=new OccTransformer() {
//                        public Object transform(Occurrence occ) {
//                            try {
//                                Template tpl = null;
//                                String aux=occ.getResourceData();
//                                if(aux.indexOf('|')>-1) {
//                                    String ids=aux.substring(0,aux.indexOf('|'));
//                                    String tms=aux.substring(aux.indexOf('|')+1);
//                                    int t = Integer.parseInt(ids);
//                                    tpl = TemplateMgr.getInstance().getTemplate(tms,t);
//                                }else {
//                                    int t = Integer.parseInt(aux);
//                                    tpl = TemplateMgr.getInstance().getTemplate(occ.getDbdata().getIdtm(),t);
//                                }
//                                //***************************************
//                                if (tpl!=null && tpl.getActive() == 1 && tpl.getDeleted()==0) {
//                                    return tpl;
//                                }
//                            }catch(Exception e){log.error(e);}
//                            return null;
//                        }
//                    };
//                    Iterator herTempl = tp.getConfigObjects(tp.getWebSite().getWebPage("CNF_WBTemplate"),transformer);
//                    
//                    
//                    if (herTempl != null) {
//                        out.println("<tr><td class=\"valores\" valign=\"top\">"+paramRequest.getLocaleString("inheritedtpls")+"</td></tr>");
//                        boolean flag = false;
//                        while (herTempl.hasNext()) {
//                            //String templ = (String) herTempl.next();
//                            Template tpl=(Template)herTempl.next();
//                            
//                            try {
//                                flag = true;
//                                name = "";
//                                String description = "";
//                                created = "";
//                                update = "";
//                                author = "";
//                                nombreC = "";
//                                email = "";
//                                srule = "";
//                                String tmtpl = tm.getId();
//                                name = tpl.getTitle();
//                                description = tpl.getDescription();
//                                //TODO: dateFormat()
//                                if (tpl.getCreated().toString() != null) 
//                                //created = WBUtils.dateFormat(tpl.getCreated());
//                                    created = tpl.getCreated().toString();
//                                if (tpl.getUpdated().toString() != null)
//                                    update = tpl.getUpdated().toString();
//                                Iterator<RuleRef> Irules = tpl.listRuleRefs(); //getRules();
//                                boolean flag3 = true;
//                                while (Irules.hasNext()) {
//                                    Rule temprule = Irules.next().getRule();//DBRule.getInstance().getRule(tpl.getTopicMapId(), Integer.parseInt(Irules.next().toString()));
//                                    if (flag3)
//                                        srule = temprule.getTitle();
//                                    else
//                                        srule += "," + temprule.getTitle();
//                                    flag3 = false;
//                                }
//                                
//                                //String idauthor = tpl.getCreator().getId();
//                                User recadmuser =  tpl.getCreator(); //DBUser.getInstance().getUserById(idauthor);
//                                if (recadmuser!=null && recadmuser.getName() != null) author = recadmuser.getName();
//                                if (recadmuser!=null && recadmuser.getUsrEmail() != null) email = recadmuser.getUsrEmail();
//                                
//                                
//                                out.println("<tr bgcolor=\"#FFFFFF\">");
//                                //preview de template
//                                SWBResourceURL url=paramRequest.getRenderUrl();
//                                url.setParameter("action1","preview");
//                                url.setParameter("tp",tp.getId());
//                                url.setParameter("tm",tm.getId());
//                                url.setParameter("tmtpl",tpl.getWebSiteId());
//                                url.setParameter("id",""+tpl.getId());
//                                out.println("<td class=valores>");
//                                out.println("<a href=\""+url.toString()+"\" class=\"link\">");
//                                out.println("<img src=\"" + webpath + "wbadmin/images/preview.gif\" border=0 alt=\""+paramRequest.getLocaleString("previewdocument")+"\">");
//                                out.println("</a>");
//                                out.println("</td>");
//                                
//                                //Url del template
//                                if(AdmFilterMgr.getInstance().haveAccess2GrpTemplate(paramRequest.getUser(), tpl.getWebSiteId(), tpl.getTemplateGroup().getId())>0){
//                                    SWBResourceURLImp url1= (SWBResourceURLImp)paramRequest.getRenderUrl();
//                                    url1.setParameter("action1","reenvia");
//                                    url1.setParameter("tp",tp.getId());
//                                    url1.setParameter("tm",tm.getId());
//                                    url1.setParameter("tmtpl",tpl.getWebSiteId());
//                                    url1.setParameter("id",""+tpl.getId());
//                                    url1.setParameter("noEdit","1");
//                                    out.println("<td class=valores>");
//                                    out.println("<a href=\""+url1.toString()+"\"\">");
//                                    out.println(name);
//                                    out.println("</td>");
//                                    out.println("</a> "); ////////////////////////////////////////////////////////
//                                }else{
//                                    out.println("<td class=valores>" + name + "</td>");
//                                }
//                                
//                                
//                                out.println("<td class=valores>" + created + "</td>");
//                                out.println("<td class=valores>" + update + "</td>");
//                                out.println("<td class=valores>");
//                                out.println("<a class=link href=\"mailto:" + email + "\">");
//                                out.println(author);
//                                out.println("</a></td>");
//                                out.println("<td class=valores>&nbsp;</td>");
//                                out.println("<td class=valores>&nbsp;</td>");
//                                out.println("<td class=\"valores\" valign=\"top\"><img src=\""+webpath+"wbadmin/images/negro.gif\" border=0 alt=\""+paramRequest.getLocaleString("msgActive")+"\"></td>");
//                                out.println("</tr>");
//                                ponerMSG = true;
//                            } catch (Exception e) {
//                                log.error("Error al mostrar templates heredados",e);
//                            }
//                        }
//                        out.println("<tr>");
//                        out.println("</tr>");
//                        if (!flag) {
//                            out.println("<tr bgcolor=\"#FFFFFF\">");
//                            out.println("<td colspan=6 class=tabla>");
//                            out.println(paramRequest.getLocaleString("add"));
//                            out.println("</td>");
//                            out.println("</tr>");
//                        }
//                    }
//                }
//                
//                if(owntpls.length()>0){ //hay flujos propios y activos
//                    out.println("<tr><td class=\"valores\" valign=\"top\">"+paramRequest.getLocaleString("owntpls")+"</td></tr>");
//                    out.println(owntpls.toString());
//                }
//                out.println("<input type=\"hidden\" name=\"tplid\" value=\" \">");
//                out.println("<input type=\"hidden\" name=\"tplPriorityAction\" value=\" \">");
//                out.println("<input type=\"hidden\" name=\"userid\" value=\""+user.getId()+"\">");
//                out.println("<TR bgcolor=\"#FFFFFF\">");
//                out.println("<td align=right class=\"valores\" colspan=7><HR size=\"1\" noshade>");
//                out.println("<INPUT type=\"submit\" name=\"agregar\" value=\""+paramRequest.getLocaleString("msgAddButton")+"\" class=\"boton\">");
//                out.println("</TD>");
//                out.println("</TR>");
//                out.println("</TABLE>");
//                out.println("</FORM>");
//                
//                
//            }else if(action!=null && (action.indexOf("add"))>-1){
//                if(action.equals("add")){
//                    boolean flag = false;
//                    out.println("<form name=\"GrpTemplate\" action=\"" + paramRequest.getRenderUrl().setAction("add1").toString() + "\" class=\"box\">");
//                    out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
//                    out.println("<tr class=\"datos\"> ");
//                    out.println("<td class=\"tabla\">&nbsp; "+paramRequest.getLocaleString("globalgrp")+"</td>");
//                    out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("description")+"</td>");
//                    out.println("</tr>");
//                    boolean flag1=false;
//                    //Hashtable hgrptempl = SWBContext.getGlobalWebSite().listTemplateGroups(); //DBCatalogs.getInstance().getGrpTemplates(TopicMgr.TM_GLOBAL);
//                    java.math.BigInteger cont=new java.math.BigInteger("0");
//                    int mNum=0;
//                    Iterator<TemplateGroup> IgrpKeys = SWBContext.getGlobalWebSite().listTemplateGroups();  
//                    out.println("<tr><td class=\"valores\" valign=\"top\">"+paramRequest.getLocaleString("inheritedtpls")+"</td></tr>");
//                    while (IgrpKeys.hasNext()) {
//                        //Integer igrp = (Integer) IgrpKeys.next();
//                        TemplateGroup recgrptempl = IgrpKeys.next();
//                        flag1=true;
//                        //manejo de tr color
//                        mNum=+2;
//                        cont=new java.math.BigInteger(String.valueOf(cont.intValue()+1));
//                        if(cont.intValue()==1){ cont=new java.math.BigInteger(String.valueOf(cont.intValue()+2));}
//                        if(cont.mod(new java.math.BigInteger(String.valueOf(mNum))).intValue()==0) {out.println("<TR bgcolor=\"#EFEDEC\">");}
//                        else {out.println("<TR bgcolor=\"#FFFFFF\">");}
//                        
//                        out.println("<td align=left valign=\"top\">");
//                        out.println("<input type=\"radio\" name=\"grpTemplate\" value=\"0" + recgrptempl.getId() + "\"><font size=\"1\" face=\"Verdana, Arial, Helvetica, sans-serif\">" + recgrptempl.getTitle() + "</font>");
//                        out.println("</td>");
//                        out.println("<td align=left><font size=\"1\" face=\"Verdana, Arial, Helvetica, sans-serif\">" + recgrptempl.getDescription() + "</font></td>");
//                        out.println("</tr>");
//                    }
//                    //hgrptempl = DBCatalogs.getInstance().getGrpTemplates(tm.getId());
//                    mNum=0;
//                    IgrpKeys = SWBContext.getWebSite(tm.getId()).listTemplateGroups();//hgrptempl.keySet().iterator();
//                    while (IgrpKeys.hasNext()) {
//                        //Integer igrp = (Integer) IgrpKeys.next();
//                        TemplateGroup recgrptempl = IgrpKeys.next();
//                        if(flag1) {
//                            out.println("<tr class=\"datos\"> ");
//                            out.println("<td class=\"tabla\">&nbsp; "+paramRequest.getLocaleString("sitegrp")+"</td>");
//                            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("description")+"</td>");
//                            out.println("</tr>");
//                            flag1=false;
//                        }
//                        
//                        mNum=+2;
//                        cont=new java.math.BigInteger(String.valueOf(cont.intValue()+1));
//                        if(cont.intValue()==1){ cont=new java.math.BigInteger(String.valueOf(cont.intValue()+2));}
//                        if(cont.mod(new java.math.BigInteger(String.valueOf(mNum))).intValue()==0) {out.println("<TR bgcolor=\"#EFEDEC\">");}
//                        else {out.println("<TR bgcolor=\"#FFFFFF\">");}
//                        
//                        out.println("<td align=left valign=\"top\">");
//                        out.println("<input type=\"radio\" name=\"grpTemplate\" value=\"" + recgrptempl.getId() + "\"><font size=\"1\" face=\"Verdana, Arial, Helvetica, sans-serif\">" + recgrptempl.getTitle() + "</font>");
//                        out.println("</td>");
//                        out.println("<td align=left><font size=\"1\" face=\"Verdana, Arial, Helvetica, sans-serif\">" + recgrptempl.getDescription() + "</font></td>");
//                        out.println("</tr>");
//                    }
//                    out.println("<tr>");
//                    out.println("<td align=right class=\"valores\" colspan=\"4\"><HR size=\"1\" noshade>");
//                    out.println("<input type=\"button\" size=\"50\" name=\"Regresar\" value=\""+paramRequest.getLocaleString("back")+"\" onClick=\"javascript:history.go(-1);\" class=\"boton\">");
//                    out.println("<input type=\"button\" size=\"50\" name=\"envia\" value=\""+paramRequest.getLocaleString("send")+"\" onClick=\"javascript:valida(this.form);\" class=\"boton\">");
//                    out.println("<input type=\"hidden\" name=\"tm\" value=\"" + tm.getId() + "\">");
//                    out.println("<input type=\"hidden\" name=\"tp\" value=\"" + tp.getId() + "\">");
//                    out.println("<input type=\"hidden\" name=\"action\" value=\"add1\">");
//                    out.println("</TD>");
//                    out.println("</TR>");
//                    out.println("</TABLE>");
//                    out.println("</form>");
//                    out.println("<Script Language=\"JavaScript\">");
//                    out.println("function valida(forma)");
//                    out.println("{");
//                    out.println("var flag=false;");
//                    out.println("var tot_campos = forma.elements.length;");
//                    out.println("for (j=0; j < tot_campos; j++)");
//                    out.println("{");
//                    out.println("if(forma.elements[j].name==\"grpTemplate\")");
//                    out.println("{");
//                    out.println("if(forma.elements[j].checked==true && forma.elements[j].value!=\"\"){");
//                    out.println("flag=true;");
//                    out.println("break;");
//                    out.println("}");
//                    out.println("}");
//                    out.println("}");
//                    out.println("if(flag)");
//                    out.println("{");
//                    out.println("forma.submit();");
//                    out.println("}");
//                    out.println("else");
//                    out.println("{");
//                    out.println("  alert('"+SWBUtils.TEXT.getLocaleString("locale_admin", "usrmsg_TopicWorkArea_processRequest_seltemplategroup") + "');");
//                    out.println("}");
//                    out.println("}");
//                    out.println("</Script>");
//                }else if(action.equals("add1")){
//                    if (!request.getParameter("grpTemplate").equals("padre")) {
//                        out.println("<form name=\"AddTemplate\" action=\"" + urlResAct.toString() + "\" class=\"box\">");
//                        out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#FFFFFF\">");
//                        out.println("<tr class=\"datos\"> ");
//                        out.println("<td class=\"tabla\">&nbsp; "+paramRequest.getLocaleString("templatename")+"</td>");
//                        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("description")+"</td>");
//                        out.println("</tr>");
//                        Iterator<TemplateRef> tpocurren = tp.listTemplateRefs(); //tp.getOccurrencesOfType("CNF_WBTemplate");
//                        vvector.clear();
//                        while (tpocurren.hasNext()) {
//                            //Occurrence ocurre = (Occurrence) tpocurren.next();
//                            TemplateRef ocurre = tpocurren.next();
//                            if (ocurre != null) {
////                                if (!ocurre.isResourceRef()) {
//                                    try {
//                                        Template templ = ocurre.getTemplate();//TemplateMgr.getInstance().getTemplate(tm.getId(),ocurre.getResourceData());
//                                        if(ocurre.getResourceData().indexOf("|")>-1)
//                                            vvector.addElement("0"+String.valueOf(templ.getId()));
//                                        else vvector.addElement(String.valueOf(templ.getId()));
//                                    } catch (Exception e) {
//                                    }
////                                }
//                            }
//                        }
//                        
//                        String grptemplate=request.getParameter("grpTemplate");
//                        boolean isglobal=false;
//                        if(grptemplate!=null && grptemplate.startsWith("0")){
//                            grptemplate=grptemplate.substring(1);
//                            isglobal=true;
//                        }
//                        
//                        Iterator<Template> en=null;
//                        String stm=tm.getId();
//                        if(!isglobal){
//                            en = tm.listTemplates(); 
//                        }else {
//                            en = SWBContext.getGlobalWebSite().listTemplates();
//                            stm=SWBContext.WEBSITE_GLOBAL;
//                        }
//                        boolean abretableint = true;
//                        int itabla = 0,cont = 0;
//                        java.math.BigInteger contTr=new java.math.BigInteger("0");
//                        int mNum=0;
//                        if(en!=null){
//                            while (en.hasNext()) {
//                                Template rectempl = en.next();
//                                if (rectempl.getTemplateGroup().getId().equals(grptemplate) && rectempl.getWebSiteId().equals(stm) && rectempl.isActive() && !rectempl.isDeleted()) {
//                                    if ((!isglobal && !FindVector(String.valueOf(rectempl.getId())) || isglobal && !FindVector("0"+String.valueOf(rectempl.getId())))) {
//                                        cont = cont + 1;
//                                        
//                                        //manejo de tr color
//                                        mNum=+2;
//                                        contTr=new java.math.BigInteger(String.valueOf(contTr.intValue()+1));
//                                        if(contTr.intValue()==1){ contTr=new java.math.BigInteger(String.valueOf(contTr.intValue()+2));}
//                                        if(contTr.mod(new java.math.BigInteger(String.valueOf(mNum))).intValue()==0) {out.println("<TR bgcolor=\"#EFEDEC\">");}
//                                        else {out.println("<TR bgcolor=\"#FFFFFF\">");}
//                                        
//                                        out.println("<td class=\"valores\">");
//                                        if(isglobal)
//                                            out.println("<input type=\"radio\" name=\"tplid\" value=\"0" + rectempl.getId() + "\">" + rectempl.getTitle());
//                                        else
//                                            out.println("<input type=\"radio\" name=\"tplid\" value=\"" + rectempl.getId() + "\">" + rectempl.getTitle());
//                                        out.println("</td>");
//                                        out.println("<td class=\"valores\">" + rectempl.getDescription() + "</td>");
//                                        out.println("</tr>");
//                                    }
//                                }
//                            }
//                        }
//                        if(cont==0){
//                            out.println("<tr bgcolor=\"#FFFFFF\" valign=\"top\">");
//                            out.println("<td align=\"left\" colspan=\"2\"><font size=\"1\" face=\"Verdana, Arial, Helvetica, sans-serif\">"+paramRequest.getLocaleString("NomoreTemplatesingrp")+"</font></td>");
//                            out.println("</tr>");
//                        }
//                        out.println("<input type=\"hidden\" name=\"tp\" value=\"" + tp.getId() + "\">");
//                        out.println("<input type=\"hidden\" name=\"tm\" value=\"" + tm.getId() + "\">");
//                        out.println("<input type=\"hidden\" name=\"action\" value=\"add\">");
//                        out.println("<input type=\"hidden\" name=\"userid\" value=\""+user.getId()+"\">");
//                        out.println("<tr>");
//                        out.println("<td align=right class=\"valores\" colspan=\"2\"><HR size=\"1\" noshade>");
//                        out.println("&nbsp; &nbsp; <input type=\"button\" size=\"50\" name=\"vergrupos\" value=\""+paramRequest.getLocaleString("seegroups")+"\" onClick=\"javascript:history.go(-1);\" class=\"boton\">");
//                        out.println("<input type=\"button\" size=\"50\" name=\"envia\" value=\""+paramRequest.getLocaleString("send")+"\" onClick=\"javascript:valida(this.form);\" class=\"boton\">");
//                        out.println("<input type=\"hidden\" name=\"topic\" value=\"" + tp + "\">");
//                        out.println("</td>");
//                        out.println("</tr>");
//                        out.println("</table>");
//                        out.println("</form>");
//                        out.println("<Script Language=\"JavaScript\">");
//                        out.println("function valida(forma)");
//                        out.println("{");
//                        out.println("var flag=false;");
//                        out.println("var tot_campos = forma.elements.length;");
//                        out.println("for (j=0; j < tot_campos; j++)");
//                        out.println("{");
//                        out.println("if(forma.elements[j].name==\"tplid\")");
//                        out.println("{");
//                        out.println("if(forma.elements[j].checked==true && forma.elements[j].value!=\"\"){");
//                        out.println("flag=true;");
//                        out.println("break;");
//                        out.println("}");
//                        out.println("}");
//                        out.println("}");
//                        out.println("if(flag)");
//                        out.println("{");
//                        out.println("forma.submit();");
//                        out.println("}");
//                        out.println("else");
//                        out.println("{");
//                        out.println("   alert('"+ paramRequest.getLocaleString("seltemplatefirst") + "');");
//                        out.println("}");
//                        out.println("}");
//                        out.println("</Script>");
//                    } else { //heredar del padre desde grupos
//                        Iterator<TemplateRef> tpocurren = tp.listTemplateRefs(); //getOccurrencesOfType("CNF_WBTemplate");
//                        while (tpocurren.hasNext()) {
//                            TemplateRef ocurre = tpocurren.next();
//                            if (ocurre!=null) {
//                                tp.removeTemplateRef(ocurre);
//                                tp.setModifiedBy(user);
//                            }
//                        }
//                        Template occ = new Occurrence(tm.getTopic("CNF_WBTemplate"), "");
//                        occ.setResourceData(null);
//                        occ.setResourceRef("_parent");
//                        occ.getDbdata().setActive(1);
//                        tp.addOccurrence(occ);
//                        tm.update2DB();
//                        DBAdmLog.getInstance().saveTopicLog(user.getId(), tm.getId(), tp.getId(), "atemplate", 0, com.infotec.appfw.util.AFUtils.getLocaleString("locale_admin", "admlog_TopicWorkArea_processRequest_templatesassignedbyher"));
//                    }
//                }
//            }
//            if(request.getParameter("action1")!=null && request.getParameter("action1").equals("preview")){
//                Template templ=SWBContext.getWebSite(request.getParameter("tmtpl")).getTemplate(request.getParameter("id")); 
//                templ.build(request,response,paramRequest.getUser(),tp);
//            }else if(request.getParameter("action1")!=null && request.getParameter("action1").equals("reenvia")){
//                Template templ=SWBContext.getWebSite(request.getParameter("tmtpl")).getTemplate(request.getParameter("id"));
//                out.println("<script>wbTree_executeAction('gotopath."+templ.getWebSiteId()+".templates');wbTree_reload();wbTree_executeAction('gotopath."+templ.getWebSiteId()+".templates."+templ.getTemplateGroup().getId()+"."+templ.getId()+"');</script>");
//                
//                
//                WebPage topico = SWBContext.getAdminWebSite().getWebPage("WBAd_sysi_TemplatesInfo");
//                
//                out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + topico.getUrl()+"?id="+templ.getId()+"&tm="+templ.getWebSiteId()+"\">");
//            }
//        }
//        //response.getWriter().print(sbRet.toString());
//    }
//
//    /**
//     * Operaciones del recurso
//     */
//    @Override
//    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
//        try{
//            String tmid=request.getParameter("tm");
//            String tpid=request.getParameter("tp");
//            String userid=request.getParameter("userid");
//            String tplid=request.getParameter("tplid");
//            String sttpl=request.getParameter("sttpl");
//            String action=request.getParameter("action");
//            String actionreq=response.getAction();
//            
//            Enumeration en0 = request.getParameterNames();
//            String priorvalue = "";
//            boolean flag = false;
//            String nombrev = "priority" + tplid;
//            while (en0.hasMoreElements()) //lee parametro de prioridad
//            {
//                if (flag)
//                    break;
//                String paramN = en0.nextElement().toString();
//                if (paramN.length() > 6) {
//                    if (paramN.equals(nombrev)) {
//                        String[] paramval = request.getParameterValues(paramN);
//                        for (int i = 0; i < paramval.length; i++) {
//                            priorvalue = paramval[i];
//                            flag = true;
//                        }
//                    }
//                }
//            }
//            
//            if (tmid!=null && tpid!=null && tplid!=null) {
//                //TopicSrv topicsrv=new TopicSrv();
//                WebSite tm=SWBContext.getWebSite(tmid);
//                WebPage tp=tm.getWebPage(tpid);
//                if(request.getParameter("tplPriorityAction")==null && action.equals("status")) //Modificar estatus de templates en un topico
//                {
//                    try{
//                        topicsrv.changeStatusTemplate2Topic(tp, tplid, Integer.parseInt(sttpl), userid);
//                    }catch(Exception e){log.error(e, "Error while unassign template width id:"+tplid+", to topic:"+tp.getId(),true);}
//                }else if(request.getParameter("tplPriorityAction")==null && action.equals("remove")) { //otra cosa que no sea cambiar el estatus del template
//                    try{
//                        topicsrv.removeTemplateFromTopic(tplid,tp,userid);
//                    }catch(Exception e){log.error(e, "Error while unassign template width id:"+tplid+", to topic:"+tp.getId(),true);}
//                }else if(request.getParameter("tplPriorityAction")==null && action.equals("add")){ //asigna templates al topico dado
//                    try{
//                        if(tplid!=null && tplid.startsWith("0")){
//                            tplid=tplid.substring(1)+"|"+SWBContext.WEBSITE_GLOBAL;
//                            topicsrv.addTemplate2Topic(tplid,tp,userid);
//                        }
//                        else {
//                            topicsrv.addTemplate2Topic(tplid,tp,userid);
//                        }
//                    }catch(Exception e){log.error( "Error while assign template width id:"+tplid+", to topic:"+tp.getId(),e);}
//                    response.setRenderParameter("refresh","1");
//                }else if(request.getParameter("tplPriorityAction")!=null && request.getParameter("tplPriorityAction").equals("tplPriorityAction")){ //modificar prioridad
//                    topicsrv.changePriorityTemplate2Topic(tp, tplid, Integer.parseInt(priorvalue), userid);
//                }
//                response.setRenderParameter("tm",tm.getId());
//                response.setRenderParameter("tp",tp.getId());
//            }else if(tmid!=null && tpid!=null){
//                try{
//                    WebSite tm=SWBContext.getWebSite(tmid);
//                    WebPage tp=tm.getWebPage(tpid);
//                    if(actionreq!=null && actionreq.equals("_noparent")){
//                        Occurrence occ = new Occurrence(tm.getTopic("CNF_WBTemplate"),null);
//                        occ.setResourceRef("_noparent");
//                        tp.addOccurrence(occ);
//                        tm.update2DB();
//                    }else if(actionreq!=null && actionreq.equals("_parent")){
//                        Iterator itParent=tp.getOccurrencesOfType("CNF_WBTemplate");
//                        while(itParent.hasNext()){
//                            Occurrence occparent=(Occurrence)itParent.next();
//                            if(occparent.isResourceRef() && occparent.getResourceRef().equals("_noparent")){
//                                tp.removeOccurrence(occparent);
//                                tm.update2DB();
//                            }
//                        }
//                    }
//                    response.setAction(response.Action_EDIT);
//                    response.setRenderParameter("tm",tm.getId());
//                    response.setRenderParameter("tp",tp.getId());
//                }catch(Exception e){
//                    log.error("Error while process _parent-_noparent occurrence in templates:WBATopicTpl/processAction",e);
//                }
//            }
//            response.setMode(response.Mode_VIEW);
//        }catch(Exception e){
//            log.error("Error in processAction of WBATopicTpl",e);
//        }
//    }
//    
//
//    /** Obtiene el resourcedata de un recurso tipo contenido
//     * @param tp :Objeto topico
//     * @param topicmap :Objeto topicmap
//     * @param tipoocurre :Tipo de ocurrencia
//     * @return :Resource Data de un recurso tipo contenido
//     */
//    private String getResourceData(WebPage tp, WebSite topicmap, String tipoocurre) {
//        Iterator tpocurren = tp.getOccurrences().iterator();
//        String res = null;
//        while (tpocurren.hasNext()) {
//            Occurrence ocurre = (Occurrence) tpocurren.next();
//            if ((ocurre.getInstanceOf().getTopicRef() != null) && (ocurre.getInstanceOf().getTopicRef().getId().equals(tipoocurre) && ocurre.getDbdata().getDeleted() == 0))
//                res = ocurre.getResourceData();
//        }
//        return res;
//    }
//    
//    /** Busca un dato en un vector
//     * @param id :id de elemento a buscar en un vector
//     * @return :regresa true o false, dependiendo si existe el id
//     * de entrada en el vector
//     */
//    private boolean FindVector(String id) {
//        boolean regresa = false;
//        for (int i = 0; i < vvector.size(); i++) {
//            if (id.equals(vvector.elementAt(i)))
//                regresa = true;
//        }
//        return regresa;
//    }
}
