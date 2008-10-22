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
public class SWBAWebPageContents extends GenericResource{

    private Logger log = SWBUtils.getLogger(SWBAWebPageContents.class);
    String webpath = SWBPlatform.getContextPath();
    String distributor = SWBPlatform.getEnv("wb/distributor");
    /** Creates a new instance of TopicContents */
    public SWBAWebPageContents() {
    }
    
//    public String getHtmlFlujo(String topicid,String topicmap,Occurrence ocurre,SWBParamRequest paramRequest) {
//        
//        WebSite map=SWBContext.getWebSite(topicmap);
//        WebPage topic=map.getWebPage(topicid);
//        Iterator<PFlowRef> tpflows = topic.listPFlowRefs(); //getConfigData("CNF_WBPFlow");
//        while(tpflows.hasNext()) {
//            PFlowRef pfr = tpflows.next();
//            //String sflujo = (String) tpflows.next();
//            PFlow pflow = pfr.getPflow(); //PFlowMgr.getInstance().getPFlow(topicmap,Integer.parseInt(sflujo));
//            Portlet res = map.getPortlet(topicid); //ResourceMgr.getInstance().getResource(map.getId(),Integer.parseInt(ocurre.getResourceData()));
//            int typeresource=res.getResourceBase().getType();
//            String restopicmap=res.getResourceBase().getTypeMap();
//            Document docflow=AFUtils.getInstance().XmltoDom(pflow.getXml());
//            Element workflow=(Element)docflow.getElementsByTagName("workflow").item(0);
//            NodeList resourceTypes=workflow.getElementsByTagName("resourceType");
//            for(int ires=0;ires<resourceTypes.getLength();ires++) {
//                Element eres=(Element)resourceTypes.item(ires);
//                int iresw=Integer.parseInt(eres.getAttribute("id"));
//                
//                if(iresw==typeresource && restopicmap.equals(eres.getAttribute("topicmap"))) {
//                    RecOccurrence recOcurre = ocurre.getDbdata();
//                    switch (recOcurre.getStatus()) {
//                        case 1: //en revisión
//                            return "<a href=\""+paramRequest.getAdminTopic().getMap().getTopic("WBAd_sysi_ContentsInfo").getUrl(paramRequest.getTopic())+"?id="+res.getResourceBase().getId()+"><img src=\"" + webpath + "wbadmin/images/espera_autorizacion.gif\" border=0 alt=\"" + com.infotec.appfw.util.AFUtils.getLocaleString("locale_admin", "usrmsg_TopicDescr_MuestraInfo_revisarflujo") + "\"></a>";
//                        default:
//                            return "";
//                    }                                                        }
//            }
//        }
//        return "";
//    }

//    /**
//     * Muestra el html final del recurso
//     */
//    @Override
//    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        String msg=null;
//        PrintWriter out=response.getWriter();
//        WebPage tp=paramRequest.getTopic();
//        if(request.getParameter("tm")!=null && request.getParameter("tp")!=null){
//            tp=SWBContext.getWebSite(request.getParameter("tm")).getWebPage(request.getParameter("tp"));
//        }
//        try{
//            if(tp!=null){
//                String action=paramRequest.getAction();
//                WebPage tp1=paramRequest.getTopic();
//                //WebSite tm1=tp1.getWebSite();
//                WebSite tm=tp.getWebSite();
//                //String recid=request.getParameter("recid");
//                if(request.getParameter("action1")!=null && !request.getParameter("action1").equals("") && action==null) action=request.getParameter("action1");
//                //TopicSrv tpsrv=new TopicSrv();
//                if(request.getParameter("send2flowSuc")!=null && request.getParameter("send2flowSuc").equals("ok")){
//                    out.println("<script language=\"JavaScript\">");
//                    out.println("  alert(\'"+paramRequest.getLocaleString("sendContent2Flow")+"!!\');");
//                    out.println("</script>");
//                }else if(request.getParameter("send2flowSuc")!=null && request.getParameter("send2flowSuc").equals("Nok")){
//                    out.println("<script language=\"JavaScript\">");
//                    out.println("  alert(\""+paramRequest.getLocaleString("Nsendcontent2Flow")+"!!\");");
//                    out.println("</script>");
//                }else if(request.getParameter("publish")!=null){
//                    if(request.getParameter("publish").equals("2")){
//                        out.println("<script language=\"JavaScript\">");
//                        out.println("  alert('"+paramRequest.getLocaleString("nofinishyet")+"');");
//                        out.println("</script>");
//                    }else if(request.getParameter("publish").equals("4")){
//                        out.println("<script language=\"JavaScript\">");
//                        out.println("  alert('"+paramRequest.getLocaleString("existotherinflow")+"');");
//                        out.println("</script>");
//                    }
//                }else if(request.getParameter("send2Accept")!=null && request.getParameter("send2Accept").equals("ok")){
//                    out.println("<script language=\"JavaScript\">");
//                    out.println("  alert('"+paramRequest.getLocaleString("Caccepted")+"');");
//                    out.println("</script>");
//                }else if(request.getParameter("send2Accept")!=null && request.getParameter("send2Accept").equals("Nok")){
//                    out.println("<script language=\"JavaScript\">");
//                    out.println("  alert('"+paramRequest.getLocaleString("CNaccepted")+"');");
//                    out.println("</script>");
//                }else if(request.getParameter("send2Reject")!=null && request.getParameter("send2Reject").equals("ok")){
//                    out.println("<script language=\"JavaScript\">");
//                    out.println("  alert('"+paramRequest.getLocaleString("Creject")+"');");
//                    out.println("</script>");
//                }else if(request.getParameter("send2Reject")!=null && request.getParameter("send2Reject").equals("Nok")){
//                    out.println("<script language=\"JavaScript\">");
//                    out.println("  alert('"+paramRequest.getLocaleString("CNreject")+"');");
//                    out.println("</script>");
//                }
//                boolean mod = false;
//                User user=paramRequest.getUser();
//                
//                SWBResourceURL urlResAct=paramRequest.getActionUrl();
//                urlResAct.setParameter("tm",String.valueOf(tm.getId()));
//                urlResAct.setParameter("tp",String.valueOf(tp.getId()));
//                urlResAct.setParameter("userid",String.valueOf(user.getId()));
//                
//                SWBResourceURL url=paramRequest.getRenderUrl();
//                url.setParameter("tm",tm.getId());
//                url.setParameter("tp",tp.getId());
//                if(action!=null && action.equals("edit")){
//                    url.setParameter("acc","add");
//                    //String distributor = WBUtils.getInstance().getWebPath()+AFUtils.getInstance().getEnv("wb/distributor")+"/";
//                    out.println("<style type=\"text/css\">");
//                    out.println("* {scrollbar-3d-light-color:#EBEFF3; scrollbar-arrow-color:#5D6B8D;");
//                    out.println("scrollbar-base-color:#5D6B8D; scrollbar-dark-shadow-color:#EBEFF3;");
//                    out.println("scrollbar-face-color:#EBEFF3; scrollbar-highlight-color:#EBEFF3;");
//                    out.println("scrollbar-shadow-color:#EBEFF3; scrollbar-track-color: #EBEFF3;}");
//                    out.println("</style>");
//                    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
//                    out.println("<script language=\"JavaScript\">");
//                    out.println("<!--");
//                    out.println("function MM_preloadImages() { //v3.0");
//                    out.println("var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();");
//                    out.println("var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)");
//                    out.println("if (a[i].indexOf(\"#\")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}");
//                    out.println("}");
//                    
//                    out.println("function MM_findObj(n, d) { //v4.0");
//                    out.println("var p,i,x;  if(!d) d=document; if((p=n.indexOf(\"?\"))>0&&parent.frames.length) {");
//                    out.println("d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}");
//                    out.println("if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];");
//                    out.println("for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);");
//                    out.println("if(!x && document.getElementById) x=document.getElementById(n); return x;");
//                    out.println("}");
//                    
//                    out.println("function MM_nbGroup(event, grpName) { //v3.0");
//                    out.println("var i,img,nbArr,args=MM_nbGroup.arguments;");
//                    out.println("if (event == \"init\" && args.length > 2) {");
//                    out.println("if ((img = MM_findObj(args[2])) != null && !img.MM_init) {");
//                    out.println("img.MM_init = true; img.MM_up = args[3]; img.MM_dn = img.src;");
//                    out.println("if ((nbArr = document[grpName]) == null) nbArr = document[grpName] = new Array();");
//                    out.println("nbArr[nbArr.length] = img;");
//                    out.println("for (i=4; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {");
//                    out.println("if (!img.MM_up) img.MM_up = img.src;");
//                    out.println("img.src = img.MM_dn = args[i+1];");
//                    out.println("nbArr[nbArr.length] = img;");
//                    out.println("} }");
//                    out.println("} else if (event == \"over\") {");
//                    out.println("document.MM_nbOver = nbArr = new Array();");
//                    out.println("for (i=1; i < args.length-1; i+=3) if ((img = MM_findObj(args[i])) != null) {");
//                    out.println("if (!img.MM_up) img.MM_up = img.src;");
//                    out.println("img.src = (img.MM_dn && args[i+2]) ? args[i+2] : args[i+1];");
//                    out.println("nbArr[nbArr.length] = img;");
//                    out.println("}");
//                    out.println("} else if (event == \"out\" ) {");
//                    out.println("for (i=0; i < document.MM_nbOver.length; i++) {");
//                    out.println("img = document.MM_nbOver[i]; img.src = (img.MM_dn) ? img.MM_dn : img.MM_up; }");
//                    out.println("} else if (event == \"down\") {");
//                    out.println("if ((nbArr = document[grpName]) != null)");
//                    out.println("for (i=0; i < nbArr.length; i++) { img=nbArr[i]; img.src = img.MM_up; img.MM_dn = 0; }");
//                    out.println("document[grpName] = nbArr = new Array();");
//                    out.println("for (i=2; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {");
//                    out.println("if (!img.MM_up) img.MM_up = img.src;");
//                    out.println("img.src = img.MM_dn = args[i+1];");
//                    out.println("nbArr[nbArr.length] = img;");
//                    out.println("} }");
//                    out.println("}");
//                    
//                    out.println("function MM_openBrWindow(theURL,winName,features) { //v2.0");
//                    out.println("window.open(theURL,winName,features);");
//                    out.println("}");
//                    out.println("//-->");
//                    out.println("</script>");
//                    
//                    out.println("<FORM name=\"tpcont\" action=\""+urlResAct.toString()+"\" class=\"box\">");
//                    out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFFF\" >");
//                    out.println("<tr class=\"datos\">");
//                    out.println("<td class=\"tabla\" align=\"left\">"+paramRequest.getLocaleString("Action")+"</td>");
//                    out.println("<td class=\"tabla\" align=\"left\">"+paramRequest.getLocaleString("Title")+"</td>");
//                    out.println("<td class=\"tabla\" align=\"left\">"+paramRequest.getLocaleString("Type")+"</td>");
//                    out.println("<td class=\"tabla\" align=\"left\">"+paramRequest.getLocaleString("Creation_Date")+"</td>");
//                    out.println("<td class=\"tabla\" align=\"left\">"+paramRequest.getLocaleString("Last_Update")+"</td>");
//                    out.println("<td class=\"tabla\" align=\"left\">"+paramRequest.getLocaleString("Author")+"</td>");
//                    out.println("<td class=\"tabla\" align=\"left\">"+paramRequest.getLocaleString("Priority")+"</td>");
//                    out.println("<td class=\"tabla\" align=\"left\">"+paramRequest.getLocaleString("Status")+"</td>");
//                    out.println("</tr>");
//                    
//                    boolean haveflow = false;
//                    String sflujo = null;
//                    Iterator<PFlowRef> tpflows = tp.listPFlowRefs(); //getConfigData("CNF_WBPFlow");
//                    while (tpflows.hasNext()) {
//                        haveflow=true;
//                        PFlowRef pfr = tpflows.next();
//                        sflujo = (String) pfr.getPflow().getId();
//                    }
//                    String  name = "", created = "", update = "", author = "", nombreC = "", email = "", srule = "";
//                    //int status = 0;
//                    boolean status = false;
//                    
//                    java.math.BigInteger cont=new java.math.BigInteger("0");
//                    int mNum=0;
//                    //PFlowSrv pflowsrv=new PFlowSrv();
//                    Iterator tpocurren = tp.listRelatedObjects(); //getOccurrences().iterator();
//                    while (tpocurren.hasNext()) {
//                        try {
//                            name = "";
//                            created = "";
//                            update = "";
//                            author = "";
//                            nombreC = "";
//                            email = "";
//                            srule = "";
//                            status = false;
//                            Object obj = tpocurren.next();
//                            if(obj instanceof PortletRef)
//                            {
//                            PortletRef ocurre = (PortletRef) tpocurren.next();
//                            //if (ocurre.getInstanceOf().getTopicRef() != null && ocurre.getInstanceOf().getTopicRef().getId().equals("REC_WBContent") && !ocurre.getPortlet().isDeleted()) {
//                            if ( !ocurre.getPortlet().isDeleted()) {
//                                
//                                try {
//                                    Portlet wbRes = ocurre.getPortlet();
//                                    Portlet res=null;
////                                    if(wbRes!=null) {
//                                        res = wbRes;
////                                    }else {
////                                        res= new Portlet(new RecResource(Integer.parseInt(ocurre.getResourceData()),tm.getId()));
////                                    }
//                                    name = res.getTitle();
//                                    if (res.getCreated() != null) created = "" + res.getCreated();
//                                    if (res.getUpdated() != null) update = "" + res.getUpdated();
//                                    Iterator<RuleRef> Irules = res.listRuleRefs();
//                                    boolean flag3 = true;
//                                    while (Irules.hasNext()) {
//                                        RuleRef rr = Irules.next();
//                                        Rule recrule = rr.getRule();
//                                        if (flag3)
//                                            srule = recrule.getTitle();
//                                        else
//                                            srule += ", " + recrule.getTitle();
//                                        flag3 = false;
//                                    }
//                                    String idauthor = res.getCreator().getId();
//                                    if (idauthor.length() > 1) {
//                                        try {
//                                            User recadmuser = res.getCreator();
//                                            if(recadmuser!=null) {
//                                                if (recadmuser.getName()!=null && !recadmuser.getName().toLowerCase().equals("null")) author = recadmuser.getName();
//                                                if (recadmuser.getUsrEmail()!=null && !recadmuser.getUsrEmail().toLowerCase().equals("null")) email = recadmuser.getUsrEmail();
//                                            }
//                                        } catch (Exception e) {log.error(e); }
//                                    }
//                                    String sstatus = "";
//                                    boolean flagrevisor = false;
//                                    
//                                    status = ocurre.isActive();
//                                    urlResAct.setParameter("contentid",String.valueOf(res.getId()));
//                                    urlResAct.setAction("remove");
//                                    
//                                    //manejo de tr color
//                                    mNum=+2;
//                                    cont=new java.math.BigInteger(String.valueOf(cont.intValue()+1));
//                                    if(cont.intValue()==1){ cont=new java.math.BigInteger(String.valueOf(cont.intValue()+2));}
//                                    if(cont.mod(new java.math.BigInteger(String.valueOf(mNum))).intValue()==0) {out.println("<TR bgcolor=\"#EFEDEC\">");}
//                                    else {out.println("<TR bgcolor=\"#FFFFFF\">");}
//                                    
//                                    //para determinar permiso
//                                    //System.out.println("res:"+res+" resid:"+res.getId()+" tm:"+res.getTopicMapId()+" typeMap:"+res.getTypeMap()+" type:"+res.getType());
//                                    PortletType recResType=res.getPortletType();
//                                    //System.out.println("recType:"+recResType+" tm:"+tm+" User:"+paramRequest.getUser());
//                                    int iresType=0;
//                                    if(recResType!=null){
//                                        iresType=AdmFilterMgr.getInstance().haveAccess2ResourceType(paramRequest.getUser(), tm.getId(),recResType.getId(),recResType.getTopicMapId());
//                                    }
//                                    
//                                    
//                                    //Pintar acciones posibles s/el documento
//                                    out.println("<td class=\"valores\" align=\"left\" width=\"60\">");
//                                    
//                                    out.println("<table width=\"100%\">");
//                                    out.println("<tr>");
//                                    
//                                    //eliminar
//                                    if(iresType>0){
//                                        try{
//                                            out.println("<td width=10>");
//                                            out.println("<a href=\""+urlResAct.toString()+"\" class=\"link\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("msgRemove") +"?'))return true; else return false;\">");
//                                            out.println("<img src=\"" + webpath + "wbadmin/images/eliminar.gif\" border=0 alt=\""+paramRequest.getLocaleString("removedocument")+"\">");
//                                            out.println("</a>");
//                                            out.println("</td>");
//                                            
//                                            //copiar contenido
//                                            SWBResourceURL actionUrl=paramRequest.getActionUrl();
//                                            actionUrl.setAction("copy");
//                                            actionUrl.setParameter("tm",paramRequest.getTopic().getWebSiteId());
//                                            actionUrl.setParameter("contentid",ocurre.getId());
//                                            out.println("<td width=10>");
//                                            out.println("<a href=\""+actionUrl.toString()+"\" class=\"link\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("msgCopy") +"?'))return true; else return false;\">");
//                                            out.println("<img src=\"" + webpath + "wbadmin/images/i_copiar.gif\" border=0 alt=\""+paramRequest.getLocaleString("copydocument")+"\">");
//                                            out.println("</a>");
//                                            out.println("</td>");
//                                            
//                                            
//                                            //Administración de contenido
//                                            String iResource=ocurre.getId();
//                                            Portlet auxres=paramRequest.getTopic().getWebSite().getPortlet(iResource);
//                                            SWBResourceURLImp urlr=new SWBResourceURLImp(request,auxres,tp,SWBResourceURL.UrlType_RENDER);
//                                            urlr.setWindowState(SWBResourceURLImp.WinState_MAXIMIZED);
//                                            urlr.setParameter("wblastversion","1");
//                                            urlr.setAdminTopic(SWBContext.getAdminWebSite().getWebPage("WBAd_sysi_TopicsContents"));
//                                            
//                                            out.println("<td width=10>");
//                                            urlr.setMode(SWBResourceURLImp.Mode_ADMIN);
//                                            
//                                            urlr.setParameter("tpcomm","WBAd_mnui_ContentAdmin");
//                                            
//                                            
//                                            if(auxres.isWb2Resource() ) {
//                                                urlr.setParameter("tm", tp.getWebSiteId());
//                                                urlr.setParameter("topic", tp.getId());
//                                            }
//                                            
//                                            out.println("<a href=\""+urlr+"\" class=\"link\">");
//                                            out.println("<img src=\"" + webpath + "wbadmin/images/i_contenido.gif\" border=0 alt=\""+paramRequest.getLocaleString("documentAdmin")+"\">");
//                                            out.println("</a>");
//                                            out.println("</td>");
//                                            
//                                            //preview
//                                            out.println("<td width=10>");
//                                            SWBResourceURL urlres=paramRequest.getRenderUrl();
//                                            urlres.setParameter("action1","edit");
//                                            //urlres.setParameter("wblastversion","1");
//                                            urlres.setParameter("wbactualversion","1");
//                                            urlres.setParameter("action2","preview");
//                                            urlres.setParameter("tpcomm","WBAd_mnui_ContentPreview");
//                                            urlres.setParameter("idtm",paramRequest.getTopic().getWebSiteId());
//                                            urlres.setParameter("idcontent",ocurre.getId());
//                                            urlres.setParameter("urladmin",urlres.toString());
//                                            out.println("<a href=\""+urlres+"\" class=\"link\">");
//                                            out.println("<img src=\"" + webpath + "wbadmin/images/preview.gif\" border=0 alt=\""+paramRequest.getLocaleString("previewdocument")+"\">");
//                                            out.println("</a>");
//                                            out.println("</td>");
//                                            //termina preview
//                                            
//                                            
//                                            //ver versión que esta en linea(si lastversion!=actualversion)
//                                            if(ocurre.isActive()&& wbRes.getActualVersion().getVersionNumber()!=wbRes.getLastVersion().getVersionNumber()){
//                                                out.println("<td width=10>");
//                                                SWBResourceURL urlres1=paramRequest.getRenderUrl();
//                                                //urlres1.setParameter("wbactualversion","1");
//                                                urlres1.setParameter("wblastversion","1");
//                                                urlres1.setParameter("action1","edit");
//                                                urlres1.setParameter("action2","preview");
//                                                urlres1.setParameter("admin","edit");
//                                                urlres1.setParameter("idtm",paramRequest.getTopic().getWebSiteId());
//                                                urlres1.setParameter("idcontent",ocurre.getId());
//                                                urlres1.setParameter("urladmin",urlres1.toString());
//                                                urlres1.setParameter("tpcomm","WBAd_mnui_ContentFlowPreview");
//                                                out.println("<a href=\""+urlres1+"\" class=\"link\">");
//                                                out.println("<img src=\"" + webpath + "wbadmin/images/ver_en_linea.gif\" border=0 alt=\""+paramRequest.getLocaleString("doconline")+"\">");
//                                                out.println("</a>");
//                                                out.println("</td>");
//                                            }
//                                            
//                                            //Need Autorizathion
//                                            if(pflowsrv.needSendtoPublish(ocurre)){ //enviar a flujo
//                                                out.println("<td width=10>");
//                                                url.setParameter("ocurre",ocurre.getId());
//                                                out.println("<a href=\""+url.setAction("send2Flow")+"\" class=\"link\">");
//                                                out.println("<img src=\"" + webpath + "wbadmin/images/enviar-flujo.gif\" border=0 alt=\""+paramRequest.getLocaleString("senddocument2flow")+"\">");
//                                                out.println("</a>");
//                                                out.println("</td>");
//                                            }
//                                            
//                                            try{
//                                                if(pflowsrv.isInFlow(ocurre) && pflowsrv.isReviewer(ocurre,user)) { //si el usuario debe revisar el contenido
//                                                    out.println("<td width=10>");
//                                                    SWBResourceURL review=paramRequest.getRenderUrl();
//                                                    review.setParameter("action1","edit");
//                                                    review.setParameter("ocurre",ocurre.getId());
//                                                    review.setParameter("idtm",paramRequest.getTopic().getWebSiteId());
//                                                    review.setParameter("idcontent",ocurre.getId());
//                                                    review.setParameter("urladmin",review.toString());
//                                                    review.setParameter("wblastversion","1");
//                                                    review.setParameter("action2","send2Review");
//                                                    out.println("<a href=\""+review+"\" class=\"link\">");
//                                                    out.println("<img src=\"" + webpath + "wbadmin/images/autorizar.gif\" border=0 alt=\""+paramRequest.getLocaleString("documentwaiting")+"\">");
//                                                    out.println("</a>");
//                                                    out.println("</td>");
//                                                }
//                                            }catch(Exception e){
//                                                log.error("Occurrence without flow:"+ocurre.getId(),e);
//                                                if(msg!=null) {
//                                                    msg=msg+"<tr><td colspan=3 class=\"valores\" align=\"left\">"+ocurre.getId()+"</td></tr>";
//                                                }
//                                                else msg="<tr><td colspan=3 class=\"valores\" align=\"left\">"+ocurre.getId()+"</td></tr>";
//                                            }
//                                            
//                                        }catch(Exception e){
//                                            log.error(e);
//                                        }
//                                    }
//                                    out.println("</tr>");
//                                    out.println("</table>");
//                                    out.println("</td>");
//                                    
//                                    //terminan acciones
//                                    
//                                    
//                                    out.println("<td class=\"valores\" align=\"left\">");
//                                    if(iresType>0) out.println("<a href=\""+paramRequest.getAdminTopic().getWebSite().getWebPage("WBAd_sysi_ContentsInfo").getUrl(paramRequest.getTopic())+"?id="+res.getId()+"\">");
//                                    out.println(name);
//                                    if(iresType>0) out.println("</a>");
//                                    /*
//                                    if (pflowsrv.isReviewer(ocurre,user))
//                                    {
//                                        out.println("<a href=\""+paramRequest.getAdminTopic().getMap().getTopic("WBAd_sysi_ContentsInfo").getUrl(paramRequest.getTopic())+"?id="+res.getId()+"><img src=\"" + webpath + "wbadmin/images/espera_autorizacion.gif\" border=0 alt=\"" + com.infotec.appfw.util.AFUtils.getLocaleString("locale_admin", "usrmsg_TopicDescr_MuestraInfo_revisarflujo") + "\"></a>");
//                                    }*/
//                                    /*
//                                    if (res.getActive() == 1 && (res.getActualversion() != res.getLastversion()))
//                                    {
//                                        out.println("<a href=\""+paramRequest.getAdminTopic().getMap().getTopic("WBAd_sysi_ContentsInfo").getUrl(paramRequest.getTopic())+"?id="+res.getId()+"\"><img src=\"" + webpath + "wbadmin/images/enlinea.gif\" border=0 alt=\"" + com.infotec.appfw.util.AFUtils.getLocaleString("locale_admin", "usrmsg_TopicDescr_MuestraInfo_verlineaflujo") + "\"></a>");
//                                    }*/
//                                    out.println("</td>");
//                                    String stipo = "";
//                                    int tipo = res.getPortletType().getPortletMode();
//                                    if (tipo != 0) {
//                                        PortletType recobj = res.getPortletType();
//                                        if (recobj!=null && recobj.getTitle() != null) stipo = recobj.getTitle();
//                                    }
//                                    out.println("<td class=\"valores\" align=\"left\">" + stipo + "</td>");
//                                    out.println("<td class=\"valores\" align=\"left\">" + created + "</td>");
//                                    out.println("<td class=\"valores\" align=\"left\">" + update + "</td>");
//                                    out.println("<td class=\"valores\" align=\"left\"><a href=\"mailto:" + email + "\">");
//                                    out.println(author);
//                                    out.println("</a></td>");
//                                    out.println("<td class=\"valores\" align=\"left\">");
//                                    if(iresType>0){
//                                        out.println("<select name=\"priority" + res.getId() + "\" onChange=\"cambia(this.form,\'" + res.getId() + "\');\">");
//                                        if (res.getPriority() == 1)
//                                            out.println("<option value=\"1\" Selected>" + paramRequest.getLocaleString("defecto") + "</option>");
//                                        else
//                                            out.println("<option value=\"1\">" + paramRequest.getLocaleString("defecto") + "</option>");
//                                        if (res.getPriority() == 2)
//                                            out.println("<option value=\"2\" Selected>" + paramRequest.getLocaleString("low") + "</option>");
//                                        else
//                                            out.println("<option value=\"2\">" + paramRequest.getLocaleString("low") + "</option>");
//                                        if (res.getPriority() == 3)
//                                            out.println("<option value=\"3\" Selected>" + paramRequest.getLocaleString("media") + "</option>");
//                                        else
//                                            out.println("<option value=\"3\">" + paramRequest.getLocaleString("media") + "</option>");
//                                        if (res.getPriority() == 4)
//                                            out.println("<option value=\"4\" Selected>" + paramRequest.getLocaleString("high") + "</option>");
//                                        else
//                                            out.println("<option value=\"4\">" + paramRequest.getLocaleString("high") + "</option>");
//                                        if (res.getPriority() == 5)
//                                            out.println("<option value=\"5\" Selected>" + paramRequest.getLocaleString("priority") + "</option>");
//                                        else
//                                            out.println("<option value=\"5\">" + paramRequest.getLocaleString("priority") + "</option>");
//                                        out.println("</select>");
//                                    }else{
//                                        if (res.getPriority() == 1) out.println(paramRequest.getLocaleString("defecto"));
//                                        if (res.getPriority() == 2) out.println(paramRequest.getLocaleString("low"));
//                                        if (res.getPriority() == 3) out.println(paramRequest.getLocaleString("media"));
//                                        if (res.getPriority() == 4) out.println(paramRequest.getLocaleString("high"));
//                                        if (res.getPriority() == 5) out.println(paramRequest.getLocaleString("priority"));
//                                    }
//                                    out.println("</td>");
//                                    out.println("<td class=\"valores\" align=\"left\">");
//                                    if (!status ) { //== 0
//                                        if(iresType>0){
//                                            urlResAct.setAction("status");
//                                            urlResAct.setParameter("stcont","1");
//                                            urlResAct.setParameter("contentid",String.valueOf(res.getId()));
//                                            urlResAct.setParameter("haveflow",String.valueOf(haveflow));
//                                            out.println("<A href=\"" + urlResAct.toString() + "\" onmousemove=\"window.status='" + paramRequest.getLocaleString("status") + "';return true;\">");
//                                            out.println("<IMG alt=\"" + paramRequest.getLocaleString("offLine") + "\" border=\"0\" src=\"" + webpath + "wbadmin/images/offline.gif\"></A>");
//                                        }else{
//                                            out.println("<IMG alt=\"" + paramRequest.getLocaleString("offLine") + "\" border=\"0\" src=\"" + webpath + "wbadmin/images/offline.gif\">");
//                                        }
//                                    } else {
//                                        if(iresType>0){
//                                            urlResAct.setAction("status");
//                                            urlResAct.setParameter("stcont","0");
//                                            urlResAct.setParameter("contentid",String.valueOf(res.getId()));
//                                            out.println("<A onmousemove=\"window.status='" + paramRequest.getLocaleString("status") + "';return true;\" href=\"" + urlResAct.toString() + "\">");
//                                            out.println("<IMG alt=\"" + paramRequest.getLocaleString("onLine") + "\" border=\"0\" src=\"" + webpath + "wbadmin/images/online.gif\"></A>");
//                                        }else{
//                                            out.println("<IMG alt=\"" + paramRequest.getLocaleString("onLine") + "\" border=\"0\" src=\"" + webpath + "wbadmin/images/online.gif\">");
//                                        }
//                                    }
//                                    out.println("</td>");
//                                    out.println("</tr>");
//                                    out.println("<Script Language=\"JavaScript\">");
//                                    out.println("function cambia(forma,idres) ");
//                                    out.println("{ ");
//                                    out.println("   forma.contentid.value=idres;");
//                                    out.println("   forma.action='"+urlResAct.setAction("priority").toString()+"';");
//                                    out.println("   forma.submit(); ");
//                                    out.println("} ");
//                                    out.println("</Script>");
//                                } catch (Exception e) {
//                                    log.error(e);
//                                }
//                            }
//                            }
//                        } catch (Exception e){log.error("Error while showing topic contents..",e);}
//                    }
//                    out.println("</tr>");
//                    //out.println("<INPUT name=\"userid\" type=\"hidden\" value=\""+ user.getId()+"\">");
//                    out.println("<INPUT name=\"tp\" type=\"hidden\" value=\""+ tp.getId()+"\">");
//                    out.println("<INPUT name=\"tm\" type=\"hidden\" value=\""+ tm.getId()+"\">");
//                    out.println("<input type=\"hidden\" name=\"contentid\" value=\" \">");
//                    out.println("<input type=\"hidden\" name=\"typemap\" value=\" \">");
//                    out.println("<input type=\"hidden\" name=\"acc\" value=\" \">");
//                    
//                    if(AdmFilterMgr.getInstance().haveAccess2Menu(paramRequest.getUser(),SWBContext.getAdminWebSite().getWebPage("WBAd_mnui_ContentAdd"))) {
//                        out.println("<TR bgcolor=\"#FFFFFF\">");
//                        out.println("<td align=\"right\" class=\"valores\" colspan=8><HR size=\"1\" noshade>");
//                        out.println("<INPUT type=\"button\" class=\"boton\" name=\"agregar\" value=\""+paramRequest.getLocaleString("add")+"\" onClick='javascript:send();' ");
//                        out.println("</TD>");
//                        out.println("</TR>");
//                    }
//                    
//                    if(msg!=null){
//                        out.println("<TR>");
//                        out.println("<TD colspan=3 class=\"valores\" align=\"left\">");
//                        out.println(paramRequest.getLocaleString("Invalidoccurreflow")+":");
//                        out.println("</TD>");
//                        out.println("</TR>");
//                        out.print(msg);
//                    }
//                    out.println("\n</TABLE>");
//                    out.println("<Script Language=\"JavaScript\">");
//                    out.println("function send() ");
//                    out.println("{ ");
//                    out.println("   document.tpcont.action='"+paramRequest.getRenderUrl().setAction("add").toString()+"';");
//                    out.println("   document.tpcont.submit(); ");
//                    out.println("} ");
//                    out.println("</Script>");
//                    out.println("</FORM>");
//                }else if(action!=null && (action.indexOf("add")>-1)){
//                    if(action.equals("add")){
//                        url.setAction("add1");
//                        out.println("<html>");
//                        out.println("<body>");
//                        out.println("<form name=\"AddContentType\" action=\""+paramRequest.getRenderUrl()+"\" class=\"box\">");
//                        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">");
//                        out.println("<tr class=\"datos\"> ");
//                        out.println("<td>");
//                        out.println(paramRequest.getLocaleString("selectContent")+"</td>");
//                        out.println("</tr>");
//                        out.println("<tr> ");
//                        out.println("<td > ");
//                        out.println("<table border=\"0\" cellspacing=\"1\" cellpadding=\"4\">");
//                        
//                        //Elementos del sitio global
//                        Iterator<PortletType> en = SWBContext.getGlobalWebSite().listPortletTypes(); //DBResourceType.getInstance().getResourceTypes(TopicMgr.TM_GLOBAL).elements();
//                        int type = 1;
//                        if (request.getParameter("type") != null)
//                            type = Integer.parseInt(request.getParameter("type"));
//                        if(en.hasNext()){
//                            out.println("<tr class=\"datos\"> ");
//                            out.println("<td class=\"tabla\">&nbsp;" + paramRequest.getLocaleString("resname") + "("+paramRequest.getLocaleString("global")+")</td>");
//                            out.println("<td class=\"tabla\">" + paramRequest.getLocaleString("description") + "</td>");
//                            out.println("</tr>");
//                        }
//                        
//                        TreeMap globalContents=new TreeMap();
//                        TreeMap globalPortletContents=new TreeMap();
//                        while (en.hasNext()) {
//                            PortletType recobj = en.next();
//                            if (recobj.getPortletMode() == type && AdmFilterMgr.getInstance().haveAccess2ResourceType(paramRequest.getUser(), tm.getId(), recobj.getId(), recobj.getWebSite().getId())>0) {
//                                String onjname=recobj.getTitle();
//                                globalContents.put(onjname+"|"+recobj.getId(), recobj.getDescription());
//                            }else if (((type==1 && recobj.getPortletMode()==5) || (type==3 && recobj.getPortletMode()==6)) && AdmFilterMgr.getInstance().haveAccess2ResourceType(paramRequest.getUser(), tm.getId(), recobj.getId(), recobj.getWebSite().getId())>0) {
//                                String onjname=recobj.getTitle();
//                                globalPortletContents.put(onjname+"|"+recobj.getId(), recobj.getDescription());
//                            }
//                        }
//                        
//                        Iterator keyTpContents=globalContents.keySet().iterator();
//                        while(keyTpContents.hasNext()){
//                            String sKey=(String)keyTpContents.next();
//                            StringTokenizer strTokenizer=new StringTokenizer(sKey,"|");
//                            int count=0;
//                            String tokenId=null;
//                            String tokenName=null;
//                            while(strTokenizer.hasMoreElements()){
//                                String token=strTokenizer.nextToken();
//                                if(count==0) tokenName=token;
//                                else if(count==1) tokenId=token;
//                                count++;
//                            }
//                            out.println("<tr>");
//                            out.println("<td class=\"valores\">");
//                            out.println("<input type=\"radio\" name=\"tipo\" value=\"Global_"+tokenId + "\">" + tokenName);
//                            out.println("</td>");
//                            out.println("<td class=\"valores\">" + globalContents.get(sKey) + "</td>");
//                            out.println("</tr>");
//                        }
//                        
//                        if(globalPortletContents.size()>0){ //Hay contenidos tipo portlets registrados en el sitio global
//                            out.println("<tr class=\"datos\"> ");
//                            out.println("<td class=\"tabla\">&nbsp;" + paramRequest.getLocaleString("portlets") + "("+paramRequest.getLocaleString("global")+")</td>");
//                            out.println("<td class=\"tabla\">" + paramRequest.getLocaleString("description") + "</td>");
//                            out.println("</tr>");
//                            
//                            keyTpContents=globalPortletContents.keySet().iterator();
//                            while(keyTpContents.hasNext()){
//                                String sKey=(String)keyTpContents.next();
//                                StringTokenizer strTokenizer=new StringTokenizer(sKey,"|");
//                                int count=0;
//                                String tokenId=null;
//                                String tokenName=null;
//                                while(strTokenizer.hasMoreElements()){
//                                    String token=strTokenizer.nextToken();
//                                    if(count==0) tokenName=token;
//                                    else if(count==1) tokenId=token;
//                                    count++;
//                                }
//                                out.println("<tr>");
//                                out.println("<td class=\"valores\">");
//                                out.println("<input type=\"radio\" name=\"tipo\" value=\"Global_"+tokenId + "\">" + tokenName);
//                                out.println("</td>");
//                                out.println("<td class=\"valores\">" + globalPortletContents.get(sKey) + "</td>");
//                                out.println("</tr>");
//                            }
//                        }
//                        
//                        
//                        //Emenentos propios del sitio
//                        en = tm.listPortletTypes();
//                        if(en.hasNext()){
//                            out.println("<tr class=\"datos\"> ");
//                            out.println("<td class=\"tabla\">&nbsp;" + paramRequest.getLocaleString("resname") + "("+paramRequest.getLocaleString("site")+")</td>");
//                            out.println("<td><b>" + paramRequest.getLocaleString("description") + "</td>");
//                            out.println("</tr>");
//                        }
//                        
//                        globalContents=new TreeMap();
//                        globalPortletContents=new TreeMap();
//                        while (en.hasNext()) {
//                            PortletType recobj =en.next();
//                            if (recobj.getPortletMode() == type && AdmFilterMgr.getInstance().haveAccess2ResourceType(paramRequest.getUser(), tm.getId(), recobj.getId(), recobj.getWebSite().getId())>0) {
//                                String onjname=recobj.getTitle();
//                                globalContents.put(onjname+"|"+recobj.getId(), recobj.getDescription());
//                            }else if (((type==1 && recobj.getPortletMode()==5) || (type==3 && recobj.getPortletMode()==6)) && AdmFilterMgr.getInstance().haveAccess2ResourceType(paramRequest.getUser(), tm.getId(), recobj.getId(), recobj.getWebSite().getId())>0) {
//                                String onjname=recobj.getTitle();
//                                globalPortletContents.put(onjname+"|"+recobj.getId(), recobj.getDescription());
//                            }
//                        }
//                        
//                        keyTpContents=globalContents.keySet().iterator();
//                        while(keyTpContents.hasNext()){
//                            String sKey=(String)keyTpContents.next();
//                            StringTokenizer strTokenizer=new StringTokenizer(sKey,"|");
//                            int count=0;
//                            String tokenId=null;
//                            String tokenName=null;
//                            while(strTokenizer.hasMoreElements()){
//                                String token=strTokenizer.nextToken();
//                                if(count==0) tokenName=token;
//                                else if(count==1) tokenId=token;
//                                count++;
//                            }
//                            out.println("<tr>");
//                            out.println("<td class=\"valores\">");
//                            out.println("<input type=\"radio\" name=\"tipo\" value=\"" + tokenId + "\">" + tokenName);
//                            out.println("</td>");
//                            out.println("<td class=\"valores\">" + globalContents.get(sKey) + "</td>");
//                            out.println("</tr>");
//                        }
//                        
//                        if(globalPortletContents.size()>0){ //Hay contenidos tipo portlets registrados en el propio sitio
//                            out.println("<tr class=\"datos\"> ");
//                            out.println("<td class=\"tabla\">&nbsp;" + paramRequest.getLocaleString("portlets") + "("+paramRequest.getLocaleString("site")+")</td>");
//                            out.println("<td class=\"tabla\">" + paramRequest.getLocaleString("description") + "</td>");
//                            out.println("</tr>");
//                            
//                            keyTpContents=globalPortletContents.keySet().iterator();
//                            while(keyTpContents.hasNext()){
//                                String sKey=(String)keyTpContents.next();
//                                StringTokenizer strTokenizer=new StringTokenizer(sKey,"|");
//                                int count=0;
//                                String tokenId=null;
//                                String tokenName=null;
//                                while(strTokenizer.hasMoreElements()){
//                                    String token=strTokenizer.nextToken();
//                                    if(count==0) tokenName=token;
//                                    else if(count==1) tokenId=token;
//                                    count++;
//                                }
//                                out.println("<tr>");
//                                out.println("<td class=\"valores\">");
//                                out.println("<input type=\"radio\" name=\"tipo\" value=\"" + tokenId + "\">" + tokenName);
//                                out.println("</td>");
//                                out.println("<td class=\"valores\">" + globalPortletContents.get(sKey) + "</td>");
//                                out.println("</tr>");
//                            }
//                        }
//                        
//                        
//                        //out.println("<INPUT name=\"userid\" type=\"hidden\" value=\""+ user.getId()+"\">");
//                        out.println("<INPUT name=\"tp\" type=\"hidden\" value=\""+ tp.getId()+"\">");
//                        out.println("<INPUT name=\"tm\" type=\"hidden\" value=\""+ tm.getId()+"\">");
//                        out.println("<input type=\"hidden\" name=\"type\" value=\"\">");
//                        out.println("</table>");
//                        out.println("</td>");
//                        out.println("</tr>");
//                        out.println("<tr>");
//                        out.println("<td align=right class=\"valores\" colspan=2><HR size=\"1\" noshade>");
//                        out.println("<input type=\"button\" class=\"boton\" size=\"50\" name=\"inicio\" value=\""+paramRequest.getLocaleString("inicio")+"\" onClick=\"javascript:senfForm(this.form);\">");
//                        int syscontents=AdmFilterMgr.getInstance().haveAccess2SysResources(paramRequest.getUser(), tm.getId());
//                        if (type == 1 && syscontents>0){
//                            out.println("<input type=\"button\" class=\"boton\" size=\"50\" name=\"envia\" value=\"" + paramRequest.getLocaleString("systemContents") + "\" onClick=\"javascript:valida1(this.form,3,'"+url.toString()+"');\">");
//                        }
//                        else if(type==3 && syscontents>0) {
//                            out.println("<input type=\"button\" class=\"boton\" size=\"50\" name=\"envia\" value=\"" + paramRequest.getLocaleString("contents") + "\" onClick=\"javascript:valida1(this.form,1,'"+url.toString()+"');\">");
//                        }
//                        out.println("<input type=\"button\" class=\"boton\" size=\"50\" name=\"envia\" value=\""+paramRequest.getLocaleString("send")+"\" onClick=\"javascript:valida(this.form,'"+url.toString()+"');\">");
//                        
//                        SWBResourceURL urlInicio=paramRequest.getRenderUrl();
//                        urlInicio.setParameter("tm",tm.getId());
//                        urlInicio.setParameter("tp",tp.getId());
//                        
//                        out.println("</td>");
//                        out.println("</tr>");
//                        out.println("</table>");
//                        out.println("</form>");
//                        out.println("<Script Language=\"JavaScript\">");
//                        out.println("function valida(forma,path)");
//                        out.println("{");
//                        out.println("var flag=false;");
//                        out.println("var tot_campos = forma.elements.length;");
//                        out.println("for (j=0; j < tot_campos; j++)");
//                        out.println("{");
//                        out.println("if(forma.elements[j].name==\"tipo\")");
//                        out.println("{");
//                        out.println("if(forma.elements[j].checked==true && forma.elements[j].value!=\"\"){");
//                        out.println("flag=true;");
//                        out.println("break;");
//                        out.println("}");
//                        out.println("}");
//                        out.println("}");
//                        out.println("if(flag)");
//                        out.println("{");
//                        out.println("  forma.action='"+url.toString()+"';");
//                        out.println("  forma.submit();");
//                        out.println("}");
//                        out.println("else");
//                        out.println("{");
//                        out.println(" alert('"+paramRequest.getLocaleString("seltypefirst")+"');");
//                        out.println("}");
//                        out.println("}");
//                        out.println("function valida1(forma,type,path)");
//                        out.println("{");
//                        out.println("    forma.action='"+url.setAction("add").toString()+"';");
//                        out.println("    forma.type.value=type;");
//                        out.println("    forma.submit();");
//                        out.println("}");
//                        
//                        out.println("function senfForm(forma)");
//                        out.println("{");
//                        out.println("  forma.action='"+urlInicio.toString()+"';");
//                        out.println("  forma.submit();");
//                        out.println("}");
//                        
//                        out.println("</Script>");
//                        
//                        out.println("</body>");
//                        out.println("</html>");
//                    }else if(action.equals("add1")){
//                        urlResAct.setAction("add2");
//                        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" bgcolor=\"#CCCCCC\">");
//                        out.println("<tr>");
//                        out.println("<td>");
//                        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\" bgcolor=\"#FFFFFF\">");
//                        out.println("<tr>");
//                        out.println("<td>");
//                        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">");
//                        out.println("<form name=\"sendadd\" method=\"POST\" action=\"" + urlResAct.toString() + "\" class=\"box\">");
//                        out.println("<tr  bgcolor=CCCCCC class=\"datos\"> ");
//                        out.println("<td>");
//                        out.println(paramRequest.getLocaleString("givedata") + ":</td>");
//                        out.println("</tr>");
//                        out.println("<tr> ");
//                        out.println("<td> ");
//                        out.println("<table border=\"0\" cellspacing=\"1\" cellpadding=\"4\">");
//                        out.println("<tr> ");
//                        out.println("<td align=\"right\" valign=\"top\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\">");
//                        out.println(paramRequest.getLocaleString("contentName") + ":</font>");
//                        out.println("</td>");
//                        out.println("<td valign=\"top\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\">");
//                        out.println("<input type=\"text\" size=\"30\" name=\"namecontent\">");
//                        out.println("<input type=\"hidden\" name=\"contents\" value=\"1\">");
//                        out.println("</td>");
//                        out.println("</tr>");
//                        out.println("<tr>");
//                        out.println("<td align=\"right\" valign=\"top\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\">");
//                        out.println(paramRequest.getLocaleString("description") + ":</font>");
//                        out.println("</td>");
//                        out.println("<td valign=\"top\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\">");
//                        out.println("<TEXTAREA rows=\"5\" cols=\"25\" name=\"description\" wrap=\"off\"></TEXTAREA>");
//                        out.println("</td>");
//                        out.println("</tr>");
//                        out.println("<tr>");
//                        out.println("<td align=right class=\"valores\" colspan=2><HR size=\"1\" noshade>");
//                        out.println("<input type=\"button\" class=\"boton\" size=\"50\" name=\"atras\" value=\""+paramRequest.getLocaleString("back")+"\" onClick=\"javascript:history.go(-1);\">");
//                        out.println("<input type=\"button\" class=\"boton\" size=\"50\" name=\"envia\" value=\""+paramRequest.getLocaleString("send")+"\" onClick=\"javascript:valida(sendadd);\">");
//                        out.println("</td>");
//                        out.println("</tr>");
//                        if(request.getParameter("tipo")!=null){
//                            String type=request.getParameter("tipo");
//                            if(type.startsWith("Global_")) {
//                                type=type.substring(7);
//                                out.println("<input type=\"hidden\" name=\"isglobal\" value=\"true\">");
//                            }
//                            out.println("<input type=\"hidden\" name=\"tipof\" value=" + type + ">");
//                        }
//                        out.println("</table>");
//                        out.println("</td>");
//                        out.println("</tr>");
//                        out.println("</table>");
//                        out.println("</td>");
//                        out.println("</tr>");
//                        out.println("</table>");
//                        out.println("</td>");
//                        out.println("</tr>");
//                        out.println("</table>");
//                        out.println("</form>");
//                        out.println("<script language=\"JavaScript\">");
//                        out.println("function valida(forma)");
//                        out.println("{");
//                        out.println("var v=\"true\";");
//                        out.println("if(forma.namecontent.value==\"\"){");
//                        out.println(" alert(\"" + paramRequest.getLocaleString("minumumcontentName") + "\");");
//                        out.println("v = \"false\";");
//                        out.println("return false;");
//                        out.println("}");
//                        out.println("if (v != \"false\") {");
//                        out.println("forma.submit();");
//                        out.println("}");
//                        out.println("}");
//                        out.println("</script>");
//                    }
//                }else if(action!=null && (action.equals("preview"))){
//                    getPreview(request, response, paramRequest,true);
//                    out.println("<table width=\"100%\">");
//                    out.println("<form>");
//                    out.println("<tr><td align=right class=\"valores\" colspan=\"2\"><HR size=\"1\" noshade>");
//                    out.println("<input type=\"button\" class=\"boton\" size=\"50\" name=\"atras\" value=\""+paramRequest.getLocaleString("back")+"\" onClick=\"javascript:history.go(-1);\">");
//                    out.println("</td></tr>");
//                    out.println("</form>");
//                    out.println("</table>");
//                }else if(action!=null && action.equals("send2Flow")){
//                    try{
//                        java.math.BigInteger cont=new java.math.BigInteger("0");
//                        int mNum=0;
//                        Iterator<PFlowRef> it_pfr = tp.listPFlowRefs();
//                        
//                        PFlowRef occurre=null;//getOccurrence(request.getParameter("ocurre"));
//                        while(it_pfr.hasNext())
//                        {
//                            occurre = it_pfr.next();
//                            if(occurre.getPflow().getId().equals(request.getParameter("ocurre")))
//                            {
//                                break;
//                            }
//                        }
//                        
//                        
//                        //PFlowSrv pflowsrv=new PFlowSrv();
//                        Iterator iteflows=pflowsrv.getFlowsToSendContent(occurre);
//                        int conta=0;
//                        StringBuffer strb=new StringBuffer();
//                        PFlow pflow=null;
//                        while(iteflows.hasNext()){
//                            conta++;
//                            pflow=(PFlow)iteflows.next();
//                            //manejo de tr color
//                            mNum=+2;
//                            cont=new java.math.BigInteger(String.valueOf(cont.intValue()+1));
//                            if(cont.intValue()==1){ cont=new java.math.BigInteger(String.valueOf(cont.intValue()+2));}
//                            if(cont.mod(new java.math.BigInteger(String.valueOf(mNum))).intValue()==0) {strb.append("<TR bgcolor=\"#EFEDEC\">");}
//                            else {strb.append("<TR bgcolor=\"#FFFFFF\">");}
//                            
//                            strb.append("<td class=\"valores\">");
//                            strb.append("<input type=\"radio\" name=\"pflow\" value=\""+occurre.getId()+"|"+pflow.getId()+"|"+pflow.getWebSite().getId()+"\">"+pflow.getTitle()+"");
//                            strb.append("</td>");
//                            strb.append("<td class=\"valores\">");
//                            strb.append(pflow.getDescription());
//                            strb.append("</td>");
//                            strb.append("</tr>");
//                        }
//                        if(conta==1 && pflow!=null){
//                            SWBResourceURL path=paramRequest.getRenderUrl();
//                            path.setAction("saveflow");
//                            path.setParameter("pflow",occurre.getId()+"|"+pflow.getId()+"|"+pflow.getWebSite().getId());
//                            out.println(paramRequest.getLocaleString("sending2flow")+":"+pflow.getTitle());
//                            out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + path +"\">");
//                        }else if(conta>1 && pflow!=null){
//                            out.println("<form name=\"selflow\" action=\""+paramRequest.getRenderUrl().setAction("saveflow")+"\" class=\"box\">");
//                            out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
//                            out.println("<tr><td>");
//                            out.println("<table width=\"30%\">");
//                            out.println("<tr class=\"datos\">");
//                            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("flow")+"</td>");
//                            out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("description")+"</td>");
//                            out.println("</tr>");
//                            out.println(strb.toString());
//                            out.println("</table>");
//                            out.println("</td></tr>");
//                            out.println("<tr><td align=right class=\"valores\"><HR size=\"1\" noshade>");
//                            out.println("<input type=\"button\" class=\"boton\" size=\"50\" name=\"atras\" value=\""+paramRequest.getLocaleString("back")+"\" onClick=\"javascript:history.go(-1);\">");
//                            out.println("<input type=\"submit\" class=\"boton\" size=\"50\" name=\"send\" value=\""+paramRequest.getLocaleString("send")+"\">");
//                            out.println("</td></tr>");
//                            out.println("</table>");
//                            out.println("</form>");
//                        }
//                    }catch(Exception e){
//                        log.error("Error while showing flows to send content...",e);
//                    }
//                }else if(action!=null && (action.equals("saveflow"))){
//                    out.println("<table width=\"100%\">");
//                    SWBResourceURL aurlAccept=paramRequest.getActionUrl();
//                    aurlAccept.setAction("saveflow");
//                    out.println("<form name=\"selflow\" action=\""+aurlAccept.toString()+"\" class=\"box\">");
//                    out.println("<tr><td align=center><b>"+paramRequest.getLocaleString("comentary")+"</b></td></tr>");
//                    out.println("<tr><td align=center><textarea name=\"msg\" cols=\"50\" rows=\"10\" wrap=\"hard\"></textarea></td></tr>");
//                    out.println("<tr><td align=right class=\"valores\" colspan=\"2\"><HR size=\"1\" noshade>");
//                    out.println("<input type=\"submit\" class=\"boton\" size=\"50\" name=\"rechazar\" value=\""+paramRequest.getLocaleString("send")+"\">");
//                    out.println("</td></tr>");
//                    out.println("<input type=\"hidden\" name=\"pflow\" value=\""+request.getParameter("pflow")+"\">");
//                    out.println("</form>");
//                    out.println("</table>");
//                }else if(action!=null && (action.equals("reject"))){
//                    out.println("<table width=\"100%\">");
//                    SWBResourceURL aurlAccept=paramRequest.getActionUrl();
//                    String sOcurre=request.getParameter("ocurre");
//                    aurlAccept.setAction("reject");
//                    out.println("<form name=\"selflow\" action=\""+aurlAccept.toString()+"\" class=\"box\">");
//                    out.println("<tr><td align=center><b>"+paramRequest.getLocaleString("comentary")+"</b></td></tr>");
//                    out.println("<tr><td align=center><textarea name=\"msg\" cols=\"50\" rows=\"10\" wrap=\"hard\"></textarea></td></tr>");
//                    out.println("<tr><td align=right class=\"valores\" colspan=\"2\"><HR size=\"1\" noshade>");
//                    out.println("<input type=\"button\" class=\"boton\" size=\"50\" name=\"atras\" value=\""+paramRequest.getLocaleString("back")+"\" onClick=\"javascript:history.go(-1);\">");
//                    //out.println("<input type=\"submit\" class=\"boton\" size=\"50\" name=\"rechazar\" value=\""+paramRequest.getLocaleString("send")+"\">");
//                    out.println("<input type=\"button\" name=\"rechazar\" value=\""+paramRequest.getLocaleString("send")+"\" onClick=\"javascript:valida(selflow);\" class=\"boton\">");
//                    out.println("</td></tr>");
//                    out.println("<input type=\"hidden\" name=\"ocurre\" value=\""+sOcurre+"\">");
//                    out.println("</form>");
//                    out.println("</table>");
//                    out.println("<script language=\"JavaScript\">");
//                    out.println("function valida(forma)");
//                    out.println("{");
//                    out.println("var v=\"true\";");
//                    out.println("if(forma.msg.value==\"\"){");
//                    out.println("alert(\""+paramRequest.getLocaleString("noemptytag")+"\");");
//                    out.println("v = \"false\";");
//                    out.println("return false;");
//                    out.println("}");
//                    out.println("if (v != \"false\") {");
//                    out.println("forma.submit();");
//                    out.println("}");
//                    out.println("}");
//                    out.println("</script>");
//                }else if(action!=null && (action.equals("accept"))){
//                    out.println("<table width=\"100%\">");
//                    SWBResourceURL aurlAccept=paramRequest.getActionUrl();
//                    String sOcurre=request.getParameter("ocurre");
//                    aurlAccept.setAction("accept");
//                    out.println("<form name=\"selflow\" action=\""+aurlAccept.toString()+"\" class=\"box\">");
//                    out.println("<tr><td align=center><b>"+paramRequest.getLocaleString("comentary")+"</b></td></tr>");
//                    out.println("<tr><td align=center><textarea name=\"msg\" cols=\"50\" rows=\"10\" wrap=\"hard\"></textarea></td></tr>");
//                    out.println("<tr><td align=right class=\"valores\" colspan=\"2\"><HR size=\"1\" noshade>");
//                    out.println("<input type=\"button\" class=\"boton\" size=\"50\" name=\"atras\" value=\""+paramRequest.getLocaleString("back")+"\" onClick=\"javascript:history.go(-1);\">");
//                    out.println("<input type=\"submit\" class=\"boton\" size=\"50\" name=\"rechazar\" value=\""+paramRequest.getLocaleString("send")+"\">");
//                    out.println("</td></tr>");
//                    out.println("<input type=\"hidden\" name=\"ocurre\" value=\""+sOcurre+"\">");
//                    out.println("</form>");
//                    out.println("</table>");
//                }
//                if(request.getParameter("action2")!=null && request.getParameter("action2").equals("preview") && request.getParameter("idcontent")!=null){
//                    Portlet currResource = SWBContext.getWebSite(request.getParameter("idtm")).getPortlet(request.getParameter("idcontent"));
//                    SWBParamRequestImp resParams = new SWBParamRequestImp(request,currResource,paramRequest.getTopic(),paramRequest.getUser());
//                    resParams.setAction(SWBParamRequestImp.Action_EDIT);
//                    resParams.setCallMethod(SWBParamRequestImp.Call_DIRECT);
//                    if(request.getParameter("wblastversion")!=null) {
//                        request.setAttribute("adm","1");
//                        //poner url
//                        SWBResourceURL urlres=paramRequest.getRenderUrl();
//                        urlres.setParameter("wblastversion","1");
//                        urlres.setParameter("action1","edit");
//                        urlres.setParameter("idtm",request.getParameter("idtm"));
//                        urlres.setParameter("action2","preview");
//                        urlres.setParameter("idcontent",request.getParameter("idcontent"));
//                        urlres.setParameter("urladmin",urlres.toString());
//                        request.setAttribute("urladmin",urlres.toString());
//                    }else if(request.getParameter("wbactualversion")!=null) {
//                        SWBResourceURL urlres=paramRequest.getRenderUrl();
//                        urlres.setParameter("action1","edit");
//                        urlres.setParameter("idtm",request.getParameter("idtm"));
//                        urlres.setParameter("action2","preview");
//                        urlres.setParameter("idcontent",request.getParameter("idcontent"));
//                        urlres.setParameter("urladmin",urlres.toString());
//                        request.setAttribute("urladmin",urlres.toString());
//                    }
//                    SWBResource swbres = SWBPortal.getResourceMgr().getResource(currResource.getWebSiteId(), currResource.getId());
//                    SWBPortal.getResourceMgr().getResourceTraceMgr().renderTraced(swbres, request, response, resParams);
//                }else if(request.getParameter("action2")!=null && request.getParameter("action2").equals("send2Review") && request.getParameter("idcontent")!=null){
//                    request.setAttribute("action2","send2Review");
//                    request.setAttribute("urladmin",request.getRequestURI());
//                    getPreview(request, response, paramRequest,true);
//                    out.println("<table width=\"100%\">");
//                    SWBResourceURL aurlAccept=paramRequest.getRenderUrl();
//                    String sOcurre=request.getParameter("ocurre");
//                    aurlAccept.setAction("accept");
//                    out.println("<form name=\"selflow\" action=\""+aurlAccept.toString()+"\" class=\"box\">");
//                    out.println("<tr><td align=right class=\"valores\" colspan=\"2\"><HR size=\"1\" noshade>");
//                    out.println("<input type=\"button\" class=\"boton\" size=\"50\" name=\"atras\" value=\""+paramRequest.getLocaleString("back")+"\" onClick=\"javascript:history.go(-1);\">");
//                    out.println("<input type=\"submit\" class=\"boton\" size=\"50\" name=\"aceptar\" value=\""+paramRequest.getLocaleString("accept")+"\">");
//                    out.println("<input type=\"button\" class=\"boton\" size=\"50\" name=\"rechazar\" value=\""+paramRequest.getLocaleString("reject")+"\" onClick=\"javascript:rechaza(this.form);\">");
//                    out.println("</td></tr>");
//                    out.println("<input type=\"hidden\" name=\"ocurre\" value=\""+sOcurre+"\">");
//                    out.println("</form>");
//                    out.println("</table>");
//                    out.println("<script language=\"JavaScript\">");
//                    out.println("function rechaza(forma)");
//                    out.println("{");
//                    SWBResourceURL aurl=paramRequest.getRenderUrl();
//                    aurl.setParameter("ocurre",request.getParameter("ocurre"));
//                    out.println("  forma.action='"+aurl.setAction("reject")+"';");
//                    out.println("  forma.submit();");
//                    out.println("}");
//                    out.println("</script>");
//                }
//                
//            }
//        }catch(Exception e){
//            log.error("error in SWBAWebPageContents",e);
//        }
//    }
//    
//    
//    public void getPreview(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest,boolean lastv) {
//        try{
//            String id=request.getParameter("idcontent");
//            SWBResource res=SWBPortal.getResourceMgr().getResource(paramRequest.getTopic().getWebSiteId(), id);
//            if(lastv) request.setAttribute("adm", "1");
//            res.render(request, response, paramRequest);
//        }catch(Exception e){
//            log.error("Error while getting content string ,id:"+request.getParameter("id"),e);
//        }
//    }
//
//    /**
//     * Muestra las operaciones del recurso
//     */
//    @Override
//    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
//        Enumeration en0 = request.getParameterNames();
//        String priorvalue = "";
//        boolean flag = false;
//        String nombrev = "priority" + request.getParameter("contentid");
//        User user = response.getUser();
//        while (en0.hasMoreElements()) //lee parametro de prioridad
//        {
//            if (flag)
//                break;
//            String paramN = en0.nextElement().toString();
//            if (paramN.length() > 6) {
//                if (paramN.equals(nombrev)) {
//                    String[] paramval = request.getParameterValues(paramN);
//                    for (int i = 0; i < paramval.length; i++) {
//                        priorvalue = paramval[i];
//                        flag = true;
//                    }
//                }
//            }
//        }
//        String contentid=request.getParameter("contentid");
//        String userid=response.getUser().getId(); // request.getParameter("userid");
//        String stcont=request.getParameter("stcont");
//        String action=response.getAction();
//        String haveflow=request.getParameter("haveflow");
//        String idp=request.getParameter("idp");
//        if (contentid!=null) {
//            WebSite tm=response.getTopic().getWebSite();//TopicMgr.getInstance().getTopicMap(tmid);
//            WebPage tp=response.getTopic();//tm.getTopic(tpid);
//            //ContentSrv contsrv=new ContentSrv();
//            TopicSrv tpsrv=new TopicSrv();
//            if(action.equals("status")) { //Modificar estatus de contenidos en un topico
//                int res=contsrv.PublishContent(tm, tp, contentid, Integer.parseInt(stcont), userid);
//                response.setRenderParameter("publish",""+res);
//            }else if(action.equals("priority")){ //modificar prioridad
//                //RecResource recRes=DBResource.getInstance().getResource(response.getTopic().getMap().getId(), Long.parseLong(request.getParameter("contentid")));
//                Portlet Recres = SWBContext.getWebSite(response.getTopic().getWebSiteId()).getPortlet(request.getParameter("contentid"));
//                Recres.setPriority(Integer.parseInt(priorvalue));
//                Recres.setModifiedBy(user);
//                //Recres.update(userid, "it was modified the content priority:" + Recres.getId());
//                DBAdmLog.getInstance().saveContentLog(userid, tm.getId(), tp.getId(), "update", Recres.getId(), "it was modified the content priority:" + Recres.getId() + " " + "with name" + ":" + Recres.getTitle());
//            }else if(action.equals("remove")) { //otra cosa que no sea cambiar el estatus del template
//                //tm.removePortlet(idp)
//                contsrv.removeContent(Integer.parseInt(contentid),tp, userid);
//            }else if(action.equals("copy")) { //copiar contenido (replicar)
//                //RecResource Recres = DBResource.getInstance().getResource(response.getTopic().getMap().getId(), Long.parseLong(request.getParameter("contentid")));
//                Portlet Recres = SWBContext.getWebSite(response.getTopic().getWebSiteId()).getPortlet(request.getParameter("contentid"));
//                contsrv.copyResource(Recres.getTopicMapId(),Recres.getId(),tp.getId(), response.getLocaleString("copyResource")+" "+Recres.getTitle(),userid);
//            }
//            response.setRenderParameter("tm",tm.getId());
//            response.setRenderParameter("tp",tp.getId());
//            response.setAction("edit");
//        }else if(action.equals("add2")){ //asigna contenidos al topico dado
//            WebSite tm=response.getTopic().getWebSite();//TopicMgr.getInstance().getTopicMap(tmid);
//            WebPage tp=response.getTopic();//tm.getTopic(tpid);
//            String tipo = request.getParameter("tipof");
//            Portlet recres = tm.createPortlet();
//            //RecResource recres = new RecResource(tm.getId());
//            PortletType ptype = tm.getPortletType(tipo);
//            recres.setPortletType(ptype);
//            //recres.setType(tipo);
//            VersionInfo vinf = tm.createVersionInfo();
//            vinf.setVersionNumber(1);
//            vinf.setCreator(user);
//            recres.setActualVersion(vinf);
//            recres.setLastVersion(vinf);
//
//            recres.setPriority(3);
//            recres.setActive(true);
//            
//            response.setAction("callResAdm");
//            response.setRenderParameter("tm",tm.getId());
//            response.setRenderParameter("tp",tp.getId());
//            if(request.getParameter("isglobal")!=null && request.getParameter("isglobal").equals("true")) {
//                recres.setTypeMap(SWBContext.WEBSITE_GLOBAL);
//                response.setRenderParameter("typemap",SWBContext.WEBSITE_GLOBAL);
//            }
//            else {
//                recres.setTypeMap(tm.getId());
//                response.setRenderParameter("typemap",tm.getId());
//            }
//            recres.setTopicMapId(tm.getId());
////            if (userid!=null)
////                recres.setIdAdm(userid);
//            if (request.getParameter("namecontent") != null)
//                recres.setTitle(request.getParameter("namecontent"));
//            String descrip = "";
//            if (request.getParameter("description") != null) descrip = request.getParameter("description");
//            recres.setDescription(descrip);
//            //recres.create(userid, "Content was added with id:"+String.valueOf(recres.getId()));
//            recres.create();
//            DBAdmLog.getInstance().saveContentLog(userid, tm.getId(), tp.getId(), "create", recres.getId(), "Content was added with id:"+recres.getId()+",and title:"+recres.getTitle());
//            ResourceMgr.getInstance().refresh();
//            String sidrec = String.valueOf(recres.getId());
//            Occurrence occ = new Occurrence(tm.getTopic("REC_WBContent"), sidrec);
//            tp.addOccurrence(occ);
//            tm.update2DB();
//            
//            //System.out.println(tm.getClass());
//            //new Exception().printStackTrace();
//            
//            RecOccurrence recOcurre = occ.getDbdata();
//            DBAdmLog.getInstance().saveTopicLog(userid, tm.getId(), tp.getId(), "acontent", recres.getId(), "Content assigned " + recres.getId() + " " + "with name" + ":" + recres.getTitle());
//            response.setRenderParameter("recid",sidrec);
//            
//            Portlet res=ResourceMgr.getInstance().getResource(recres.getTopicMapId(), recres.getId()).getResourceBase();
//            SWBResourceURLImp url=new SWBResourceURLImp(request,ResourceMgr.getInstance().getResource(recres.getTopicMapId(), recres.getId()).getResourceBase(),response.getTopic(),SWBResourceURL.UrlType_RENDER);
//            url.setResourceBase(res);
//            url.setMode(SWBResourceURLImp.Mode_ADMIN);
//            url.setWindowState(SWBResourceURLImp.WinState_MAXIMIZED);
//            url.setAction("add");
//            url.setAdminTopic(response.getAdminTopic());
//            response.sendRedirect(url.toString());
//            
//        }else if(action.equals("saveflow")){
//            String svalue=request.getParameter("pflow");
//            int cont=0;
//            String occurre=null;
//            String pflow=null;
//            String stm=null;
//            StringTokenizer st = new StringTokenizer(svalue,"|");
//            while (st.hasMoreElements()) {
//                String token = st.nextToken();
//                if (token == null) {
//                    continue;
//                }
//                cont++;
//                if(cont==1){ //content occcurrence
//                    occurre=token;
//                }else if(cont==2){ //flow id
//                    pflow=token;
//                }else if(cont==3){ // flow topicmap id
//                    stm=token;
//                }
//            }
//            try{
//                //PFlowSrv pflowsrv=new PFlowSrv();
//                pflowsrv.sendOcurrenceToAuthorize(response.getTopic().getOccurrence(occurre),PFlowMgr.getInstance().getPFlow(stm,Integer.parseInt(pflow)),request.getParameter("msg"),userid);
//                response.setRenderParameter("send2flowSuc","ok");
//            }catch(Exception e){
//                log.error("Error while send content to flow-WBATopicContents:processAction",e);
//                response.setRenderParameter("send2flowSuc","Nok");
//            }
//            response.setRenderParameter("tm",response.getTopic().getWebSiteId());
//            response.setRenderParameter("tp",response.getTopic().getId());
//            response.setAction(response.Action_EDIT);
//        }else if(action.equals("accept")){
//            try{
//                //PFlowSrv pflowsrv=new PFlowSrv();
//                pflowsrv.ApproveOccurrence(response.getTopic().getOccurrence(request.getParameter("ocurre")),response.getUser(),request.getParameter("msg"));
//                response.setRenderParameter("send2Accept","ok");
//            }catch(Exception e){
//                log.error("Error while send content to accept in flow-WBATopicContents:processAction",e);
//                response.setRenderParameter("send2Accept","Nok");
//            }
//            response.setRenderParameter("tm",response.getTopic().getMap().getId());
//            response.setRenderParameter("tp",response.getTopic().getId());
//            response.setAction(response.Action_EDIT);
//        }else if(action.equals("reject")){
//            try{
//                //PFlowSrv pflowsrv=new PFlowSrv();
//                pflowsrv.RejectOccurrence(response.getTopic().getOccurrence(request.getParameter("ocurre")),response.getUser(),request.getParameter("msg"));
//                response.setRenderParameter("send2Reject","ok");
//            }catch(Exception e){
//                log.error("Error while send content to accept in flow-WBATopicContents:processAction",e);
//                response.setRenderParameter("send2Reject","Nok");
//            }
//            response.setRenderParameter("tm",response.getTopic().getWebSiteId());
//            response.setRenderParameter("tp",response.getTopic().getId());
//            response.setAction(response.Action_EDIT);
//        }
//        response.setMode(response.Mode_VIEW);
//    }

    
}
