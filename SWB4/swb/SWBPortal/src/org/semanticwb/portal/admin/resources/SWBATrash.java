/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.*;
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
public class SWBATrash extends GenericResource {

    private Logger log=SWBUtils.getLogger(SWBATrash.class);
    private boolean debugvar=true;
    
    /** Creates a new instance of WBATrash */
    public SWBATrash() { }

    /** Admin view of a WBATrash resource
     * @param request parameters
     * @param response answer to the request
     * @param paramRequest list of objects
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        Portlet base=getResourceBase();
        PrintWriter out = response.getWriter();
        String selected = "section";
        if(base.getAttribute("view")!=null) selected = base.getAttribute("view");
        out.println("<form action=\""+paramRequest.getActionUrl().setAction("update").toString()+"\" method=\"post\" class=\"box\">");
        out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
        out.println("<TR>");
        out.println("<TD width=\"150\" align=\"right\" class=\"datos\"> "+paramRequest.getLocaleString("TrashType")+"</TD>");
        out.println("<TD class=\"valores\"><select name=\"view\" class=\"valores\">");
        String strSelect ="";
        if(selected.equals("content")) strSelect = " selected ";
        out.println("<option value=\"content\" "+strSelect+">"+paramRequest.getLocaleString("selectContent")+"</option>");
        strSelect ="";
        if(selected.equals("resource")) strSelect = " selected ";
        out.println("<option value=\"resource\" "+strSelect+">"+paramRequest.getLocaleString("selectResource")+"</option>");
        strSelect ="";
        if(selected.equals("section")) strSelect = " selected ";
        out.println("<option value=\"section\" "+strSelect+">"+paramRequest.getLocaleString("selectSection")+"</option>");
        strSelect ="";
        if(selected.equals("site")) strSelect = " selected ";
        out.println("<option value=\"site\" "+strSelect+">"+paramRequest.getLocaleString("selectSite")+"</option>");
        strSelect ="";
        if(selected.equals("template")) strSelect = " selected ";
        out.println("<option value=\"template\" "+strSelect+">"+paramRequest.getLocaleString("selectTemplate")+"</option>");
        out.println("</select></TD>");
        out.println("</TR>");
        out.println("<TR>");
        out.println("<TD colspan=2 align=\"right\" class=\"datos\"><HR size=\"1\" noshade><input type=submit class=\"boton\" value=\""+paramRequest.getLocaleString("btnSend")+"\"></TD>");
        out.println("</TR>");
        out.println("</TABLE>");
        out.println("</TD>");
        out.println("</TR>");
        out.println("</TABLE></form></P>");
        
    }

    /** User view of WBATrash resource
     * @param request parameters
     * @param response answer to the request
     * @param paramRequest list of objects
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        User user = paramRequest.getUser();
        
        Portlet base=getResourceBase();
        StringBuffer ret = new StringBuffer("");
        WebPage topic = paramRequest.getTopic();
        int num =0;
        if(request.getParameter("ax")==null){
            
            String view = "section";
            if(base.getAttribute("view")!=null) view=base.getAttribute("view");
            
            String tab_list_1 = null;
            String tab_list_2 = null;
            
            String viewSite = request.getParameter("site");
            if(request.getParameter("tm")!=null) viewSite=request.getParameter("tm");
            SWBResourceURL urlSection = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
            urlSection.setParameter("view","section");
            SWBResourceURL urlTemplate = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
            urlTemplate.setParameter("view","template");
            SWBResourceURL urlContent = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
            urlContent.setParameter("view","content");
            SWBResourceURL urlResource = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
            urlResource.setParameter("view","resource");
            SWBResourceURL urlSite = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
            urlSite.setParameter("view","site");
            
            String strSection = "<a href=\""+urlSection.toString()+"\">"+paramRequest.getLocaleString("linkSection")+"</a>";
            String strTemplate = "<a href=\""+urlTemplate.toString()+"\">"+paramRequest.getLocaleString("linkTemplate")+"</a>";
            String strContent = "<a href=\""+urlContent.toString()+"\">"+paramRequest.getLocaleString("linkContent")+"</a>";
            String strResource = "<a href=\""+urlResource.toString()+"\">"+paramRequest.getLocaleString("linkResource")+"</a>";
            String strSite = "<a href=\""+urlSite.toString()+"\">"+paramRequest.getLocaleString("linkSite")+"</a>";
            
            if (view == null || view.equals("")){
                // se ven las secciones por default
                view = "section";
                strSection = "<b>"+paramRequest.getLocaleString("msgSection")+"</b>";
            }
            StringBuffer sbtm = new StringBuffer("");
            if (view.equals("section") || view.equals("template") || view.equals("content") || view.equals("resource") ){
                // se arma lista de sitios, el primer sitio de la lista sería el de default
                
                Iterator<WebSite> etm = SWBContext.listWebSites();
                
                num = 0;
                String control = "\n<tr><td colspan=7 class=\"datos\">"+paramRequest.getLocaleString("msgExistingSites")+" <select name=\"sitios\" onchange=\"javascript:go(forma);\" class=\"valores\">";
                String piecontrol = "\n</select></td></tr>";
                if(request.getParameter("tm")!=null){
                    control = "<input type=hidden name=\"sitios\" class=\"valores\" value=\""+request.getParameter("tm")+"\">";
                    piecontrol="";
                }
                sbtm.append(control);
                if(!piecontrol.equals("")) {
                    while(etm.hasNext()){
                        WebSite rtm = etm.next();
                        num++;
                        int permiso = 0;
                        //TODO: AdmFilterMgr.getInstance().haveAccess2TopicMap
                        //permiso = AdmFilterMgr.getInstance().haveAccess2TopicMap(paramRequest.getUser(),viewSite);
                        permiso = 1; //Temporal
                        if(viewSite == null | viewSite.equals("")) viewSite = rtm.getId();
                        String opcion = "";
                        if(!rtm.isDeleted())
                            if(viewSite.equals(rtm.getId()))
                                opcion = "\n<option value=\""+topic.getUrl()+"?view="+view+"&site="+rtm.getId()+"\" selected>"+rtm.getTitle()+"</option> ";
                            else
                                opcion = "\n<option value=\""+topic.getUrl()+"?view="+view+"&site="+rtm.getId()+"\">"+rtm.getTitle()+"</option> ";
                        
                        if(permiso>0){
                            
                            if( (!view.equals("content") && !view.equals("section")) ){
                                sbtm.append(opcion);
                            }
                            else{
                                if( (view.equals("content") || view.equals("section")) && !rtm.getId().equals(SWBContext.WEBSITE_GLOBAL))
                                    sbtm.append(opcion);
                            }
                            
                        }
                    }
                }
                sbtm.append(piecontrol);
            }
            
            if(view.equals("site"))
                if(view.equals("site")) strSite = "<b>"+paramRequest.getLocaleString("msgSite")+"</b>";
            tab_list_1 ="";// "<tr><td colspan=6>"+strSection + " \\ " + strTemplate + " \\ " + strContent + " \\ " + strResource + " \\ " + strSite + " \\</td></tr><tr><td colspan=6><hr></td></tr>";
            tab_list_2 = sbtm.toString();
            // Armado de enlistado de elementos borrados
            
            String strHeader = "";
            StringBuffer strTable = new StringBuffer("");
            StringBuffer strAll = new StringBuffer("");
            
            //int permisoTM =0;
            //TODO: AdmFilterMgr.getInstance().haveAccess2TopicMap()
            //permisoTM = AdmFilterMgr.getInstance().haveAccess2TopicMap(paramRequest.getUser(),viewSite);
            StringBuffer formAll = new StringBuffer("");
            
            SWBResourceURL urlAction = paramRequest.getActionUrl();
            
            formAll.append("\n\n<form name=\"frmAll\" method=\"post\" action=\""+urlAction+"\">");
            formAll.append("\n<input type=\"hidden\" name=\"view\" value=\""+view+"\">");
            if(!view.equals("site")) formAll.append("\n<input type=\"hidden\" name=\"site\" value=\""+viewSite+"\">");
            formAll.append("\n<input type=\"hidden\" name=\"ax\" value=\"\">");
            
            
            // lista con todos los elementos encontrados
            //StringBuffer strList = new StringBuffer("");
            if(view.equals("section")){
                
                Iterator<WebPage> enumT = SWBContext.getWebSite(viewSite).listWebPages();
                num = 0;
                String rowColor="";
                boolean cambiaColor = true;
                while (enumT.hasNext()){
                    WebPage rT = enumT.next();
                    WebSite thisTM = SWBContext.getWebSite(viewSite);
                    WebPage thisTopic = thisTM.getWebPage(rT.getId());
                    if(rT.isDeleted()){
                        //TODO: AdmFilterMgr.getInstance().haveAccess2Topic()
                        //if(AdmFilterMgr.getInstance().haveAccess2Topic(user,thisTopic))
                        {
                            SWBResourceURL urlRecover=paramRequest.getActionUrl();   //?view="+view+"&site="+viewSite+"&ax=recover&id="+rT.getId()+"
                            urlRecover.setParameter("view",view);
                            urlRecover.setParameter("site",viewSite);
                            urlRecover.setParameter("ax","recover");
                            urlRecover.setParameter("id",rT.getId().toString());
                            
                            SWBResourceURL urlRemove=paramRequest.getActionUrl();   //?view="+view+"&site="+viewSite+"&ax=eliminate&id="+rT.getId()+",
                            urlRemove.setParameter("view",view);
                            urlRemove.setParameter("site",viewSite);
                            urlRemove.setParameter("ax","eliminate");
                            urlRemove.setParameter("id",rT.getId().toString());
                            
                            rowColor="#EFEDEC";
                            if(!cambiaColor) rowColor="#FFFFFF";
                            cambiaColor = !(cambiaColor);
                            strTable.append("\n<tr bgcolor=\""+rowColor+"\">");
                            strTable.append("\n<td class=\"valores\">"+rT.getId()+"</td>");
                            strTable.append("\n<td class=\"valores\">"+ thisTopic.getTitle() +"</td>");
                            //TODO: dateFormat()
                            strTable.append("\n<td class=\"valores\">"+rT.getCreated()+"</td>");
                            strTable.append("\n<td class=\"valores\">"+rT.getUpdated()+"</td>");
                            strTable.append("\n<td class=\"valores\"><a href=\""+urlRecover.toString()+"\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/recover.gif\" border=0 title=\""+paramRequest.getLocaleString("titleRecover")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureRecoverSection")+"?'))return true; else return false;\"></a></td>");
                            strTable.append("\n<td class=\"valores\" colspan=2><a href=\""+urlRemove.toString()+"\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/trash_vacio.gif\" border=0  title=\""+paramRequest.getLocaleString("titleEliminate")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureDeleteSection")+"?'))return true; else return false;\"></a></td>");
                            strTable.append("\n</tr>");
                            strAll.append(rT.getId()+",");
                            formAll.append("\n<input type=\"hidden\" name=\"idlist\" value=\""+rT.getId()+"\">");
                        }
                        num++;
                    }
                }

                strHeader = "<tr  ><td class=\"tabla\">"+paramRequest.getLocaleString("thID")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thName")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thCreated")+"</td><td class=\"tabla\" >"+paramRequest.getLocaleString("thLastUpdate")+"</td><td colspan=3 class=\"valores\"><a href=\"#\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/recover2.gif\" border=0  title=\""+paramRequest.getLocaleString("titleRecoverAll")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureRecoverAllSections")+"?')){delRec('recover',"+num+");} else {return (false);}\"></a>  <a href=\"#\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/trash_vacio2.gif\" border=0 title=\""+paramRequest.getLocaleString("titleEliminateAll")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureDeleteAllSections")+"?')){delRec('eliminate',"+num+");} else return false;\"></a></td></tr>";
            }
            
            if(view.equals("template")){
                WebSite ws = SWBContext.getWebSite(viewSite);
                Iterator<Template> enumT = ws.listTemplates();
                num=0;
                String rowColor="";
                boolean cambiaColor = true;
                while (enumT.hasNext()){
                    Template rT = enumT.next();
                    if(rT.isDeleted()){
                        //TODO: AdmFilterMgr.getInstance().haveAccess2GrpTemplate()
                        //if(AdmFilterMgr.getInstance().haveAccess2GrpTemplate(user,viewSite,rT.getTemplateGroup().getId())==2)
                        {
                            //System.out.println("TM param: "+viewSite+ ", DBCatalog: " + DBCatalogs.getInstance().getGrpTemplate(viewSite,rT.getGrpid()).getTopicMapId());
                            if(viewSite.equals(rT.getTemplateGroup().getWebSite().getId())) {
                                SWBResourceURL urlRecover=paramRequest.getActionUrl();   //?view="+view+"&site="+viewSite+"&ax=recover&id="+rT.getId()+"
                                urlRecover.setParameter("view",view);
                                urlRecover.setParameter("site",viewSite);
                                urlRecover.setParameter("ax","recover");
                                urlRecover.setParameter("id",rT.getId());
                                
                                SWBResourceURL urlRemove=paramRequest.getActionUrl();   //?view="+view+"&site="+viewSite+"&ax=eliminate&id="+rT.getId()+",
                                urlRemove.setParameter("view",view);
                                urlRemove.setParameter("site",viewSite);
                                urlRemove.setParameter("ax","eliminate");
                                urlRemove.setParameter("id",rT.getId());
                                rowColor="#EFEDEC";
                                if(!cambiaColor) rowColor="#FFFFFF";
                                cambiaColor = !(cambiaColor);
                                strTable.append("<tr bgcolor=\""+rowColor+"\"><td class=\"valores\">"+rT.getId()+"</td>");
                                strTable.append("<td class=\"valores\">"+rT.getTitle()+"</td>");
                                strTable.append("<td class=\"valores\">"+rT.getDescription()+"</td>");
                                //TODO: dateFormat()
                                strTable.append("<td class=\"valores\">"+rT.getCreated()+"</td>");
                                strTable.append("<td class=\"valores\">"+rT.getUpdated()+"</td>");
                                strTable.append("<td class=\"valores\" colspan=3><a href=\""+urlRecover.toString()+"\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/recover.gif\" border=0  title=\""+paramRequest.getLocaleString("titleRecover")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureRecoverTemplate")+"?'))return true; else return false;\"></a></td><td><a href=\""+urlRemove.toString()+"\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/trash_vacio.gif\" border=0  title=\""+paramRequest.getLocaleString("titleEliminate")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureDeleteTemplate")+"?'))return true; else return false;\"></a></td>");
                                strTable.append("</tr>");
                                strAll.append(rT.getId()+",");
                                formAll.append("\n<input type=\"hidden\" name=\"idlist\" value=\""+rT.getId()+"\">");
                                num++;
                            }
                        }
                    }
                }

                strHeader = "<tr ><td class=\"tabla\">"+paramRequest.getLocaleString("thID")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thName")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thDescription")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thCreated")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thLastUpdate")+"</td><td colspan=2 class=\"valores\"><a href=\"#\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/recover2.gif\" border=0  title=\""+paramRequest.getLocaleString("titleRecoverAll")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureRecoverAllTemplates")+"?')){delRec('recover',"+num+");} else {return (false);}\"></a>  <a href=\"#\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/trash_vacio2.gif\" border=0 title=\""+paramRequest.getLocaleString("titleEliminateAll")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureDeleteAllTemplates")+"?')){delRec('eliminate',"+num+");} else return false;\"></a></td></tr>";
            }
            
            if(view.equals("content")){
                WebSite otm = SWBContext.getWebSite(viewSite);
                try{
                    Iterator<WebPage> itKS = otm.listWebPages();
                    num=0;
                    String rowColor="";
                    boolean cambiaColor = true;
                    while(itKS.hasNext()){
                        WebPage Tp = itKS.next();
                        //if(AdmFilterMgr.getInstance().haveAccess2Topic(user,Tp))  //TODO:
                        {
                            //ArrayList alOcc = Tp.getOccurrences();
                            //Iterator itOcc = alOcc.iterator();
                            Iterator<PortletRef> itOcc = Tp.listPortletRefs();
                            while(itOcc.hasNext()){

                                PortletRef rOcc =  itOcc.next();
                                String strTopicAsoc=Tp.getTitle(); //TopicMgr.getInstance().getTopicMap(rOcc.getIdtm()).getTopic(rOcc.getIdtp()).getDisplayName();
                                String strTopicAsocID=Tp.getId(); //rOcc.getIdtp();
                                if(rOcc.isDeleted()){
                                    num++;
                                    Iterator<Portlet> enuRes = SWBContext.getWebSite(viewSite).listPortlets();//DBResource.getInstance().getResources(viewSite);
                                    boolean pasa = false;
                                    while(enuRes.hasNext()){
                                        Portlet recRes = enuRes.next();
                                        if(recRes.getId().equals(rOcc.getPortlet().getId())) pasa=true;
                                    }
                                    if(pasa){
                                        Portlet rRes = rOcc.getPortlet(); //SWBContext.getWebSite(viewSite).getPortlet(rOcc.getResourceData());
                                        if(rRes!=null&&!rRes.equals(null)){
                                            
                                            SWBResourceURL urlRecover=paramRequest.getActionUrl();   //"?view="+view+"&site="+viewSite+"&ax=recover&id="+rOcc.getId()+
                                            urlRecover.setParameter("view",view);
                                            urlRecover.setParameter("site",viewSite);
                                            urlRecover.setParameter("ax","recover");
                                            urlRecover.setParameter("id",rOcc.getId());
                                            
                                            SWBResourceURL urlRemove=paramRequest.getActionUrl();   //"?view="+view+"&site="+viewSite+"&ax=eliminate&id="+rRes.getId()+"&idocc="+rOcc.getId()+
                                            urlRemove.setParameter("view",view);
                                            urlRemove.setParameter("site",viewSite);
                                            urlRemove.setParameter("ax","eliminate");
                                            urlRemove.setParameter("id",rRes.getId());
                                            urlRemove.setParameter("idocc",rOcc.getId());
                                            rowColor="#EFEDEC";
                                            if(!cambiaColor) rowColor="#FFFFFF";
                                            cambiaColor = !(cambiaColor);
                                            strTable.append("<tr bgcolor=\""+rowColor+"\">");
                                            strTable.append("<td class=\"valores\">"+rRes.getId()+"</td>");
                                            strTable.append("<td class=\"valores\">"+ rRes.getTitle()+"</td>");
                                            strTable.append("<td class=\"valores\">"+rRes.getDescription()+"</td>");
                                            strTable.append("<td class=\"valores\">"+strTopicAsoc+" | "+strTopicAsocID+"</td>");//strTopicAsoc
                                            //TODO: dateFormat()
                                            strTable.append("<td class=\"valores\">"+rRes.getCreated()+"</td>");
                                            strTable.append("<td class=\"valores\">"+rRes.getUpdated()+"</td>");
                                            strTable.append("<td class=\"valores\"><a href=\""+urlRecover.toString()+"\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/recover.gif\" border=0  title=\""+paramRequest.getLocaleString("titleRecover")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureRecoverContent")+"?'))return true; else return false;\"></a></td>");
                                            strTable.append("<td><a href=\""+urlRemove.toString()+"\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/trash_vacio.gif\" border=0  title=\""+paramRequest.getLocaleString("titleEliminate")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureDeleteContent")+"?'))return true; else return false;\"></a></td>");
                                            strTable.append("</tr>");
                                            strAll.append(rRes.getId()+"|"+rOcc.getId()+",");
                                            formAll.append("\n<input type=\"hidden\" name=\"idlist\" value=\""+rRes.getId()+"|"+rOcc.getId()+"\">");
                                        }
                                    }
                                }
                            }
                        }
                    }

                    strHeader = "<tr ><td class=\"tabla\">"+paramRequest.getLocaleString("thID")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thName")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thDescription")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thSection")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thCreated")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thLastUpdate")+"</td><td colspan=2 class=\"tabla\"><a href=\"#\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/recover2.gif\" border=0  title=\""+paramRequest.getLocaleString("titleRecoverAll")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureRecoverAllContents")+"?')){delRec('recover',"+num+");} else {return (false);}\"></a>  <a href=\"#\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/trash_vacio2.gif\" border=0 title=\""+paramRequest.getLocaleString("titleEliminateAll")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureDeleteAllContents")+"?')){delRec('eliminate',"+num+");} else return false;\"></a></td></tr>";
                }
                catch(Exception e){ log.error( paramRequest.getLocaleString("msgErrorTrash")+". WBATrash.doView.content ",e); }
                otm = null;
                
            }
            if(view.equals("resource")){
                Iterator<Portlet> enumT = SWBContext.getWebSite(viewSite).listPortlets();
                
                try{
                    num=0;
                    String rowColor="";
                    boolean cambiaColor = true;
                    while (enumT.hasNext()){
                        Portlet rRes = enumT.next();
                        int oType = rRes.getPortletType().getPortletMode();
                        //try{
                        PortletType oRec = rRes.getPortletType(); //DBResourceType.getInstance().getResourceType(rRes.getTypeMap(),oType);
                        if(rRes!=null && oRec!=null && rRes.isDeleted() && (oRec.getPortletMode()==2 || oRec.getPortletMode()==3)){
                            //TODO: AdmFilterMgr.getInstance().haveAccess2ResourceType()
                            //if(AdmFilterMgr.getInstance().haveAccess2ResourceType(user,viewSite,Integer.parseInt(rRes.getId()),rRes.getPortletType().getWebSite().getId())==2)
                            {
                                SWBResourceURL urlRecover=paramRequest.getActionUrl();   //"?view="+view+"&site="+viewSite+"&ax=recover&id="+rRes.getId()+
                                urlRecover.setParameter("view",view);
                                urlRecover.setParameter("site",viewSite);
                                urlRecover.setParameter("ax","recover");
                                urlRecover.setParameter("id",rRes.getId());
                                
                                SWBResourceURL urlRemove=paramRequest.getActionUrl();   // "?view="+view+"&site="+viewSite+"&ax=eliminate&id="+rRes.getId()+
                                urlRemove.setParameter("view",view);
                                urlRemove.setParameter("site",viewSite);
                                urlRemove.setParameter("ax","eliminate");
                                urlRemove.setParameter("id",rRes.getId());
                                rowColor="#EFEDEC";
                                if(!cambiaColor) rowColor="#FFFFFF";
                                cambiaColor = !(cambiaColor);
                                strTable.append("<tr bgcolor=\""+rowColor+"\">");
                                strTable.append("<td class=\"valores\">"+rRes.getId()+"</td>");
                                strTable.append("<td class=\"valores\">"+rRes.getTitle()+"</td>");
                                strTable.append("<td class=\"valores\">"+rRes.getDescription()+"</td>");
                                //TODO: dateFormat()
                                strTable.append("<td class=\"valores\">"+rRes.getCreated()+"</td>");
                                strTable.append("<td class=\"valores\">"+rRes.getUpdated()+"</td>");
                                strTable.append("<td class=\"valores\"><a href=\""+urlRecover.toString()+"\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/recover.gif\" border=0  title=\""+paramRequest.getLocaleString("titleRecover")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureRecoverResource")+"?'))return true; else return false;\"></a></td>");
                                strTable.append("<td class=\"valores\"><a href=\""+urlRemove.toString()+"\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/trash_vacio.gif\" border=0  title=\""+paramRequest.getLocaleString("titleEliminate")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureDeleteResource")+"?'))return true; else return false;\"></a></td>");
                                strTable.append("</tr>");
                                strAll.append(rRes.getId()+",");
                                formAll.append("\n<input type=\"hidden\" name=\"idlist\" value=\""+rRes.getId()+"\">");
                            }
                            num++;
                        }
                        
                    }
                    
                    strHeader = "<tr ><td class=\"tabla\">"+paramRequest.getLocaleString("thID")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thName")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thDescription")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thCreated")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thLastUpdate")+"</td><td colspan=2 class=\"tabla\"><a href=\"#\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/recover2.gif\" border=0  title=\""+paramRequest.getLocaleString("titleRecoverAll")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureRecoverAllResources")+"?')){delRec('recover',"+num+");} else {return (false);}\"></a>  <a href=\"#\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/trash_vacio2.gif\" border=0 title=\""+paramRequest.getLocaleString("titleEliminateAll")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureDeleteAllResources")+"?')){delRec('eliminate',"+num+");} else return false;\"></a></td></tr>";
                }
                catch(Exception e){  log.error(paramRequest.getLocaleString("msgErrorTrash")+". WBATrash.doView.resource ",e);   }
            }
            if(view.equals("site")){
                Iterator<WebSite> enumTM = SWBContext.listWebSites();
                num=0;
                String rowColor="";
                boolean cambiaColor = true;
                while (enumTM.hasNext()){
                    WebSite rTM = enumTM.next();
                    if(rTM.isDeleted()){
                        //TODO: AdmFilterMgr.getInstance().haveAccess2TopicMap()
                        //if(AdmFilterMgr.getInstance().haveAccess2TopicMap(paramRequest.getUser(),rTM.getId())==2)
                        {
                            SWBResourceURL urlRecover=paramRequest.getActionUrl();   // "?view="+view+"&ax=recover&id="+rTM.getId()+
                            urlRecover.setParameter("view",view);
                            urlRecover.setParameter("ax","recover");
                            urlRecover.setParameter("id",rTM.getId());
                            
                            SWBResourceURL urlRemove=paramRequest.getActionUrl();   // "?view="+view+"&ax=eliminate&id="+rTM.getId()+
                            urlRemove.setParameter("view",view);
                            urlRemove.setParameter("ax","eliminate");
                            urlRemove.setParameter("id",rTM.getId());
                            rowColor="#EFEDEC";
                            if(!cambiaColor) rowColor="#FFFFFF";
                            cambiaColor = !(cambiaColor);
                            strTable.append("<tr bgcolor=\""+rowColor+"\">");
                            strTable.append("<td class=\"valores\">"+rTM.getId()+"</td>");
                            strTable.append("<td class=\"valores\">"+rTM.getTitle()+"</td><td class=\"valores\">"+rTM.getDescription()+"</td>");
                            //TODO: dateFormat()
                            strTable.append("<td class=\"valores\">"+rTM.getCreated()+"</td>");
                            strTable.append("<td class=\"valores\">"+rTM.getUpdated()+"</td>");
                            strTable.append("<td class=\"valores\"><a href=\""+urlRecover.toString()+"\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/recover.gif\" border=0  title=\""+paramRequest.getLocaleString("titleRecover")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureRecoverSite")+"?'))return true; else return false;\"></a></td>");
                            strTable.append("<td class=\"valores\" colspan=2><a href=\""+urlRemove.toString()+"\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/trash_vacio.gif\" border=0  title=\""+paramRequest.getLocaleString("titleEliminate")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureDeleteSite")+"?'))return true; else return false;\"></a></td>");
                            strTable.append("</tr>");
                            strAll.append(rTM.getId()+",");
                            formAll.append("\n<input type=\"hidden\" name=\"idlist\" value=\""+rTM.getId()+"\">");
                        }
                        num++;
                    }
                }

                String tempLink = "";
                if(num>0)
                {
                    tempLink = "";
                }
                
                
                strHeader = "<tr><td class=\"tabla\">"+paramRequest.getLocaleString("thID")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thName")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thDescription")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thCreated")+"</td><td class=\"tabla\">"+paramRequest.getLocaleString("thLastUpdate")+"</td><td colspan=2 class=\"tabla\"><a href=\"#\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/recover2.gif\" border=0  title=\""+paramRequest.getLocaleString("titleRecoverAll")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureRecoverAllSites")+"?')){delRec('recover',"+num+");} else {return (false);}\"></a>  <a href=\"#\"><image src=\""+SWBPlatform.getContextPath()+"wbadmin/images/trash_vacio2.gif\" border=0 title=\""+paramRequest.getLocaleString("titleEliminateAll")+"\" onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("confirmShureDeleteAllSites")+"?')){delRec('eliminate',"+num+");} else return false;\"></a></td></tr>";
            }
            
            strTable.append("</table>");
            
            if(view.equals("section")){
                if( request.getParameter("tree")!=null && request.getParameter("tree").equals("reload") ){
                    WebSite tm = SWBContext.getWebSite(request.getParameter("tmp"));
                    WebPage tp = null;
                    if(request.getParameter("tpp")!=null) {
                        tp = tm.getWebPage(request.getParameter("tpp"));
                        ret.append("<script>");
                        ret.append("wbTree_executeAction('gotonode.topic."+tm.getId()+"."+tp.getId()+"'); ");
                        ret.append("wbTree_reload();");
                        ret.append("wbTree_executeAction('gotonode.topic."+tm.getId()+"."+tp.getId()+"'); ");
                        ret.append("</script>");
                    }
                }
            }
            if(view.equals("template")){
                if( request.getParameter("tree")!=null && request.getParameter("tree").equals("reload") ){
                    
                    if(request.getParameter("idtmp")!=null) {
                        Template temp = SWBContext.getWebSite(viewSite).getTemplate(request.getParameter("idtmp"));
                        
                        TemplateGroup rgr = temp.getTemplateGroup();
                        
                        String ttm = ""; //"global";
                        ttm = rgr.getWebSite().getId();
                        
                        ret.append("\n<script>");
                        ret.append("wbTree_executeAction('gotopath."+ttm+".templates."+temp.getTemplateGroup().getId()+"'); ");
                        ret.append("wbTree_reload();");
                        ret.append("wbTree_executeAction('gotopath."+ttm+".templates."+temp.getTemplateGroup().getId()+"."+temp.getId()+"'); ");
                        ret.append("</script>");
                    }
                }
            }
            if(view.equals("resource")){
                if( request.getParameter("tree")!=null && request.getParameter("tree").equals("reload") ){
                    if(request.getParameter("idres")!=null) {
                        try{
                            Portlet rRes = SWBContext.getWebSite(viewSite).getPortlet(request.getParameter("idres"));
                            int oType = rRes.getPortletType().getPortletMode();
                            PortletType oRec = rRes.getPortletType();
                            String nodo = "sysresources";
                            if(oRec!=null&&oRec.getPortletMode()==2) nodo = "advresources";
                            ret.append("<script>");
                            ret.append("wbTree_executeAction('gotopath."+nodo+"."+rRes.getPortletType().getPortletMode()+"');");
                            ret.append("wbTree_reload();");
                            ret.append("wbTree_executeAction('gotopath."+nodo+"."+rRes.getPortletType().getPortletMode()+"."+rRes.getId()+"'); ");
                            ret.append("</script>");
                        }
                        catch(Exception e){log.error(e);}
                    }
                }
            }
            if(view.equals("site")){
                if( request.getParameter("tree")!=null && request.getParameter("tree").equals("reload") ){
                    if(request.getParameter("tmp")!=null) {
                        ret.append("<script>");
                        ret.append("wbTree_executeAction('gotopath."+request.getParameter("tmp")+"'); ");
                        ret.append("wbTree_reload();");
                        ret.append("wbTree_executeAction('gotopath."+request.getParameter("tmp")+"'); ");
                        ret.append("</script>");
                    }
                    else {
                        ret.append("<script>");
                        ret.append("wbTree_executeAction('gotopath'); ");
                        ret.append("wbTree_reload();");
                        ret.append("</script>");
                    }
                }
            }
            
            formAll.append("\n</form>\n");
            
            ret.append("\n<form name=forma method=\"post\" class=\"box\">");
            ret.append("\n<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            if(request.getParameter("tm")==null) ret.append(tab_list_2);
            ret.append(strHeader);
            ret.append(strTable.toString());
            ret.append("\n</form>");
            
            ret.append(formAll.toString());
            SWBResourceURL urlAdmin = paramRequest.getRenderUrl().setMode(paramRequest.Mode_ADMIN);
            ret.append("\n<script language=\"javascript\">");
            ret.append("\n function go(forma)");
            ret.append("\n {");
            ret.append("\n  document.forma.action =  document.forma.sitios.value;");
            ret.append("\n  document.forma.submit();");
            ret.append("\n }");
            ret.append("\n function delRec(accion,num)");
            ret.append("\n {");
            ret.append("\n  if(num>0)");
            ret.append("\n      {");
            ret.append("\n          document.frmAll.ax.value =  accion;");
            ret.append("\n          document.frmAll.submit();");
            ret.append("\n      }");
            ret.append("\n }");
            ret.append("\n </script>");
        }
        response.getWriter().print(ret.toString());
    }

    /** Recover or Eliminate an WB objects like topic, topicmpas, contents, resources.
     * @param request parameters
     * @param response answer to the request
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        
        
        Portlet base=getResourceBase();
        String view="section";
        if(base.getAttribute("view")!=null) view = base.getAttribute("view");
        String viewSite=request.getParameter("site");
        String ax=request.getParameter("ax");
        //String id=request.getParameter("id");
        String idocc=request.getParameter("idocc");
        String idlist=request.getParameter("idlist");
        //StringTokenizer strToken=null;
        String[] strSections=null;
        String thisToken = null;
        User user = response.getUser();
//        if(user==null) user = new User(response.getTopic().getWebSite().getUserRepository());
        WebPage topic = response.getTopic();
        String accion = response.getAction();
        if(viewSite!=null) response.setRenderParameter("tm",viewSite);
        if(accion!=null && accion.equals("update")){
            try{
                if(request.getParameter("view")!=null){
                    base.setAttribute("view", request.getParameter("view"));
                    base.updateAttributesToDB();
                }
                else{
                    base.setAttribute("view", "section");
                    base.updateAttributesToDB();
                }
            }
            catch(Exception e){log.error(e);}
            response.setMode(response.Mode_VIEW);
        }
        else{
            if(idlist!=null)  strSections=request.getParameterValues("idlist"); //strToken = new StringTokenizer(idlist,",");
            else strSections=request.getParameterValues("id"); //strToken = new StringTokenizer(id,",");
            if(!view.equals(null) && !view.equals("")){
                if(view.equals("section")){
                    try{
                        if(!ax.equals(null)){
                            if(ax.equals("recover")){
                                int cuenta =0;
                                for(int i=0; i<strSections.length;i++){
                                    cuenta++;
                                    thisToken = strSections[i];
                                    WebPage rT = SWBContext.getWebSite(viewSite).getWebPage(thisToken);
                                    rT.setDeleted(false);
                                    //rT.update();
                                    rT.setModifiedBy(user);
                                    rT=null;
                                }
                                if(cuenta==1){
                                    response.setRenderParameter("tpp",thisToken);
                                    response.setRenderParameter("tmp",viewSite);
                                    response.setRenderParameter("tree","reload");
                                }
                            }
                            if(ax.equals("eliminate")){
                                WebSite tm = SWBContext.getWebSite(viewSite);
                                for(int i=0; i<strSections.length;i++){
                                    thisToken = strSections[i];
                                    tm.removeWebPage(thisToken);
                                    //WebPage tp = tm.getWebPage(thisToken); //tm.getTopic(thisToken,true);
                                    //WebPageSrv tpRem = new WebPageSrv();
                                    //tpRem.removeWebPageFromWastebasket(tp,user);
                                    //tpRem=null;
                                    //tp=null;
                                }
                                tm=null;
                            }
                        }
                        response.setRenderParameter("view","section");
                        response.setRenderParameter("site",viewSite);
                        response.setMode(response.Mode_VIEW);
                        
                    }
                    catch(Exception e){ log.error(response.getLocaleString("msgErrorUpdateDeleteTopic")+", WBATrash.processAction.section",e); }
                }
                if(view.equals("template")){
                    try{
                        if(!ax.equals(null)){
                            if(ax.equals("recover")){
                                int cuenta=0;
                                String grpTemID="0";
                                for(int i=0; i<strSections.length;i++){
                                    cuenta++;
                                    thisToken = strSections[i];
                                    Template rTemplate = SWBContext.getWebSite(viewSite).getTemplate(thisToken);
                                    rTemplate.setDeleted(false);
                                    //rTemplate.update();
                                    rTemplate.setModifiedBy(user);
                                    grpTemID = rTemplate.getTemplateGroup().getId();
                                    rTemplate=null;
                                }  
                                if(cuenta==1){
                                    response.setRenderParameter("idtmp",thisToken);
                                    response.setRenderParameter("tree","reload");
                                }
                                
                            }
                            if(ax.equals("eliminate"))
                            {
                                for(int i=0; i<strSections.length;i++){
                                    thisToken = strSections[i];
                                    //TemplateSrv tser = new TemplateSrv();
                                    try{
                                        SWBContext.getWebSite(viewSite).removeTemplate(thisToken);
                                        //tser.removeTemplate(viewSite,Integer.parseInt(thisToken),user.getId());
                                    }
                                    catch(Exception e){ log.error(response.getLocaleString("msgErrorDeleteTemplate")+", WBATrash.processAction",e);}
                                    //tser=null;
                                }
                            }
                        }
                        response.setRenderParameter("view","template");
                        response.setRenderParameter("site",viewSite);
                    }
                    catch(Exception e){ log.error(response.getLocaleString("msgErrorUpdateDeleteTemplate")+", WBATrash.processAction.template",e); }
                }
                
                if(view.equals("content")){
                    try{
                        if(!ax.equals(null)){
                            if(ax.equals("recover"))
                            {
                                for(int i=0; i<strSections.length;i++){
                                    WebSite localTM = topic.getWebSite();
                                    //localTM.update2DB();
                                    thisToken = strSections[i];
                                    if(idlist!=null){
                                        StringTokenizer localToken = new StringTokenizer(thisToken,"|");
                                        thisToken=localToken.nextToken();
                                        thisToken=localToken.nextToken();
                                    }
                                    PortletRef rOcc = SWBContext.getWebSite(viewSite).getPortletRef(thisToken);
                                    rOcc.setDeleted(false);
                                    //rOcc.update();
                                    rOcc=null;
                                }
                            }
                            if(ax.equals("eliminate"))
                            {
                                for(int i=0; i<strSections.length;i++){
                                    thisToken = strSections[i];
                                    if(idlist!=null){
                                        StringTokenizer localToken = new StringTokenizer(thisToken,"|");
                                        thisToken=localToken.nextToken();
                                        idocc=localToken.nextToken();
                                    }
                                    SWBContext.getWebSite(viewSite).removePortletRef(idocc.trim());
                                }
                            }
                            response.setRenderParameter("view","content");
                            response.setRenderParameter("site",viewSite);
                        }
                    }
                    catch(Exception e){ log.error(response.getLocaleString("msgErrorUpdateEraseContent")+". WBATrash.processAction.content", e); }
                }
                
                if(view.equals("resource")){
                    try{
                        if(!ax.equals(null)){
                            if(ax.equals("recover")){
                                int cuenta=0;
                                for(int i=0; i<strSections.length;i++){
                                    cuenta++;
                                    thisToken = strSections[i];
                                    Portlet rRes = SWBContext.getWebSite(viewSite).getPortlet(thisToken);
                                    rRes.setDeleted(false);
                                    rRes.setModifiedBy(user);
                                    //rRes.update(user.getId(),response.getLocaleString("msgLogResourceUndeleted"));
                                }
                                if(cuenta==1){
                                    response.setRenderParameter("idres",thisToken);
                                    response.setRenderParameter("tree","reload");
                                }
                            }
                            if(ax.equals("eliminate"))
                            {
                                for(int i=0; i<strSections.length;i++){
                                    thisToken = strSections[i];
                                    //ResourceSrv resSrv = new ResourceSrv();
                                    //resSrv.removeResource(viewSite, Long.parseLong(thisToken), request, response.getUser());
                                    SWBContext.getWebSite(viewSite).removePortlet(thisToken);
                                    //RecResource rRes = DBResource.getInstance().getResource(viewSite,Long.parseLong(thisToken));
                                    //WBResource wbRes = ResourceMgr.getInstance().getResource(viewSite,Long.parseLong(thisToken));
                                    //rRes.remove(user.getId(),response.getLocaleString("msgLogResourceDeleted"));
                                    //WBResourceUtils.getInstance().removeResource(wbRes.getResourceBase());
                                    //rRes=null;
                                    //resSrv=null;
                                }
                            }
                            response.setRenderParameter("view","resource");
                            response.setRenderParameter("site",viewSite);
                        }
                    }
                    catch(Exception e){ log.error(response.getLocaleString("msgErrorUpdateEraseResource")+", WBATrash.processAction.resource",e); }
                }
                if(view.equals("site")){
                    try{
                        if(!ax.equals(null)){
                            if(ax.equals("recover")){
                                int cuenta = 0;
                                for(int i=0; i<strSections.length;i++){
                                    cuenta++;
                                    thisToken = strSections[i];
                                    WebSite rTM = SWBContext.getWebSite(thisToken);
                                    rTM.setDeleted(false);
                                    rTM.setModifiedBy(user);
                                    //rTM.update();
                                    rTM=null;
                                }
                                if(cuenta==1){
                                    response.setRenderParameter("tmp",thisToken);
                                    response.setRenderParameter("tree","reload");
                                }
                            }
                            if(ax.equals("eliminate")){
                                for(int i=0; i<strSections.length;i++){
                                    thisToken = strSections[i];
                                    SWBContext.removeWebSite(thisToken);
                                    //WebSiteSrv tmserv = new WebSitepSrv();
                                    //tmserv.removeWebSite(thisToken, user,request);
                                    //tmserv = null;
                                }
                                response.setRenderParameter("tree","reload");
                            }
                            response.setRenderParameter("view","site");
                        }
                    }
                    catch(Exception e){ log.error(response.getLocaleString("msgErrorUpdateEraseTopicMap")+", WBATrash.processAction.site",e); }
                }
            }
        }
    }
}
