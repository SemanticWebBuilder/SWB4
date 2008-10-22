/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.*;
import java.util.*;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;

import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.PFlow;
import org.semanticwb.model.Permission;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.Role;
import org.semanticwb.model.Rule;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.portal.api.*;
import org.w3c.dom.*;



/**
 *
 * @author juan.fernandez
 */
public class SWBARole extends GenericResource {

    private Logger log=SWBUtils.getLogger(SWBARole.class);
    public static final String WBPERMISS="WBAd_Permissions";
    /** Creates a new instance of WBTree */
    public SWBARole() {
    }

    
    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
            
        if(paramRequest.getMode().equals("gateway")) 
        {
            doGateway(request,response,paramRequest);
        }
        else if(paramRequest.getMode().equals("script")) 
        {
            doScript(request,response,paramRequest);
        }
        else
        {
            super.processRequest(request,response,paramRequest);
        }
        
        
    }
    
    private Document getService(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response) {
        return getDocument(user, src, cmd);
    }
    
    
    /**
     * @param map
     * @param res
     * @param root
     * @param user
     */
    public void getPermisosGral(WebSite map,Element res,WebPage root, User user) {
        Iterator<WebPage> it = root.listVisibleChilds(user.getLanguage());
        while(it.hasNext()) {
            WebPage child=it.next();
            Element etopic=addNode("topic", child.getId(), child.getTitle(user.getLanguage()), res);
            etopic.setAttribute("topicmap",map.getId());
            getPermisosGral(map,etopic,child, user);
        }
    }
    
    /**
     * @param res
     * @param user
     */
    public void getPermisosGral(Element res, User user) {
        WebSite map=SWBContext.getAdminWebSite();
        WebPage root=map.getWebPage(WBPERMISS);
        Iterator<WebPage> it=root.listVisibleChilds(user.getLanguage());
        while(it.hasNext()) {
            WebPage child=it.next();
            Element etopic=addNode("topic", child.getId(), child.getTitle(user.getLanguage()), res);
            etopic.setAttribute("topicmap",map.getId());
            getPermisosGral(map,etopic,child, user);
        }
    }
    /**
     * @param res
     * @param src
     */
    public void getCatalog(Element res,Document src) {
        Element rep=(Element)src.getElementsByTagName("repository").item(0);
        Text etext=(Text)rep.getFirstChild();
        String repository=etext.getNodeValue();
        
        Element eroleId=(Element)src.getElementsByTagName("roleid").item(0);
        etext=(Text)eroleId.getFirstChild();
        String sroleId=etext.getNodeValue();
        String roleId=sroleId;
        
        Iterator<Role> it=SWBContext.getUserRepository(repository).listRoles();
        while(it.hasNext()) {
            Role role=it.next();
            if(role.getId().equals(roleId)) {
                
                Element erole=addNode("role", ""+role.getId(), role.getTitle(), res);
                addElement("lastupdate",role.getUpdated().toString(), erole);
                Iterator<Permission> it_per = role.listPermissions();
                while(it_per.hasNext())
                {
                    Permission per = it_per.next();
                    //TODO: De que manera se obtienen el permiso de administrador y el del usuario
                    //if(role.getXml()!=null && !role.getXml().equals("")) {
//                    if(per!=null) {
//                        Document doc=SWBUtils.XML.xmlToDom(role.getXml());
//                        Node permiss=res.getOwnerDocument().importNode(doc.getFirstChild(),true);
//                        Element exml=res.getOwnerDocument().createElement("xml");
//                        erole.appendChild(exml);
//                        exml.appendChild(permiss);
//                    }
                }
                addElement("description",role.getDescription(), erole);
            }
        }
    }
    
    /**
     * @param res
     * @param src
     * @param user
     */
    public void update(Element res,Document src,User user) {
        Element req=(Element)src.getFirstChild();
        Element erole=(Element)req.getElementsByTagName("role").item(0);
        String id=erole.getAttribute("id");
        
        try {
            String repository=((Element)req.getElementsByTagName("role").item(0)).getAttribute("repository");
            Role recrole=SWBContext.getUserRepository(repository).getRole(id);
            String description=((Text)((Element)req.getElementsByTagName("description").item(0)).getFirstChild()).getNodeValue();
            recrole.setDescription(description);
            recrole.setTitle(erole.getAttribute("name"));
            String xml="";
            //recrole.setXml(xml);
            Document doc=SWBUtils.XML.getNewDocument();
            //TODO: Revisar para generar Permiss
            Node exml=doc.importNode(req.getElementsByTagName("permiss").item(0),true);
            doc.appendChild(exml);
            //TODO: ver donde se guardan los permisos de admin y superusuario????
//            String xmlrole=SWBUtils.XML.domToXml(doc);
//            recrole.setXml(xmlrole);
            
            try {
                //recrole.update(user.getId(),"The role "+ id +" was updated");
                recrole.setModifiedBy(user);
                addNode("role", ""+recrole.getId(), recrole.getTitle(),res);
            }
            catch(Exception afe) {
                afe.printStackTrace(System.out);
                log.error(afe);
                addElement("error", afe.getMessage(), res);
            }
        }
        catch(Exception e) {
            e.printStackTrace(System.out);
            log.error(e);
            addElement("error", e.getMessage(), res);
        }
    }
    /**
     * @param user
     * @param src
     * @param cmd
     * @return
     */
    public Document getDocument(User user, Document src, String cmd) {
        Document dom = null;
        try {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);
            
            if(cmd.equals("getcatRoles")) {
                getCatalog(res,src);
            }
            else if(cmd.equals("getPermisosGral")) {
                getPermisosGral(res, user);
            }
            else if(cmd.equals("update")) {
                
                update(res,src,user);
            }
        } catch (Exception e) {
            log.error(e);
            return getError(3);
        }
        return dom;
    }
    
    
    /**
     * @param user
     * @param src
     * @param action
     * @return
     */
    public Document getPath(User user, Document src, String action) {
        Document dom = null;
        try {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);
            
            StringTokenizer st=new StringTokenizer(action,".");
            String cmd=st.nextToken();
            
            if(cmd.equals("topic")) {
                String tmid=st.nextToken();
                String tpid=st.nextToken();
                
                StringBuffer ret=new StringBuffer();
                WebPage tp=SWBContext.getWebSite(tmid).getWebPage(tpid);
                ret.append(tp.getId());
                while(tp!=tp.getWebSite().getHomePage()) {
                    tp=tp.getParent();
                    ret.insert(0,tp.getId()+".");
                }
                ret.insert(0,tmid+".");
                res.appendChild(dom.createTextNode(ret.toString()));
            }
            
            
        } catch (Exception e) {
            log.error(e);
            return getError(3);
        }
        return dom;
    }
    
    
    
    
    private Element addNode(String node, String id, String name, Element parent) {
        Element ret=addElement(node,null,parent);
        if(id!=null)ret.setAttribute("id",id);
        if(name!=null)ret.setAttribute("name",name);
        return ret;
    }
    
    private Element addElement(String name, String value, Element parent) {
        Document doc = parent.getOwnerDocument();
        Element ele = doc.createElement(name);
        if (value != null) ele.appendChild(doc.createTextNode(value));
        parent.appendChild(ele);
        return ele;
    }
    
    private Document getError(int id) {
        Document dom = null;
        try {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);
            Element err = dom.createElement("err");
            res.appendChild(err);
            addElement("id", "" + id, err);
            if (id == 0) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getService_loginfail") + "...", err);
            } else if (id == 1) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getService_nouser") + "...", err);
            } else if (id == 2) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getService_noservice") + "...", err);
            } else if (id == 3) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getService_serviceprocessfail") + "...", err);
            } else if (id == 4) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getService_parametersprocessfail") + "...", err);
            } else if (id == 5) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getService_noTopicmap") + "...", err);
            } else if (id == 6) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getService_noTopic") + "...", err);
            } else if (id == 7) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getService_usernopermiss") + "...", err);
            } else if (id == 8) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getService_TopicAlreadyexist") + "...", err);
            } else if (id == 9) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getService_byImplement") + "...", err);
            } else if (id == 10) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getService_TopicMapAlreadyExist") + "...", err);
            } else if (id == 11) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getService_FileNotFound") + "...", err);
            } else if (id == 12) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getService_noversions") + "...", err);
            } else if (id == 13) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getError_xmlinconsistencyversion") + "...", err);
            } else if (id == 14) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getError_noResourcesinMemory") + "...", err);
            } else if (id == 15) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getError_noTemplatesinMemory") + "...", err);
            } else if (id == 16) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getError_TemplatenotRemovedfromFileSystem") + "...", err);
            } else if (id == 17) {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getError_adminUsernotCreated") + "...", err);
            } else {
                addElement("message", SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "usrmsg_Gateway_getService_errornotfound") + "...", err);
            }
        } catch (Exception e) {
            log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBARole", "error_Gateway_getService_documentError") + "...", e);
        }
        return dom;
    }
    
    
    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        ServletInputStream in = request.getInputStream();
        Document dom = SWBUtils.XML.xmlToDom(in);
        if (!dom.getFirstChild().getNodeName().equals("req")) {
            response.sendError(404, request.getRequestURI());
            return;
        }
        String cmd = null;
        if (dom.getElementsByTagName("cmd").getLength() > 0)
            cmd = dom.getElementsByTagName("cmd").item(0).getFirstChild().getNodeValue();
        
        if (cmd == null) {
            response.sendError(404, request.getRequestURI());
            return;
        }
        String ret="";
        try {
            Document res = getService(cmd, dom, paramRequest.getUser(), request, response);
            if (res == null) {
                ret = SWBUtils.XML.domToXml(getError(3));
            } else
                ret = SWBUtils.XML.domToXml(res, true);
        }catch(Exception e){log.error(e);}
        out.print(new String(ret.getBytes()));
        
    }
    
    public void doScript(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        PrintWriter out=response.getWriter();
         out.println("<LINK href=\"/work/sites/WBAdmin/templates/7/1/images/wb3.css\" rel=\"stylesheet\" type=\"text/css\" >");
        out.println("<BODY bgcolor=\"#EDEFEC\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" >");
        out.println("<p class=\"status\">&nbsp;&nbsp;");
        out.println("</p>");
        out.println("</BODY>");
        out.println("</HTML>");
       
        out.println("<script language=\"javascript\" >");
        out.println("function wbTree_executeAction(action)");
        out.println("{");
        out.println("if(top.tree!=null)top.tree.document.applets[0].executeAction(action);");
        out.println("}");
        out.println("function wbTree_remove()");
        out.println("{");
        out.println("wbTree_executeAction('remove');");
        out.println("}");
        out.println("function wbTree_reload()");
        out.println("{");
        out.println("wbTree_executeAction('reload');");
        out.println("}");
        out.println("function wbStatus(msg)");
        out.println("{");
        out.println("top.frames[4].location='/wb/WBAdmin/WBAd_int_Status?stmsg='+msg;");	
        out.println("}");
        //out.println("wbStatus('');");
        out.println("</script>");
        
        out.println("\r\n<script>\r\n");
        //out.println("\r\nfunction UpdateTreeWF(id){\r\n");
        out.println("wbTree_executeAction('gotopath.userreps."+ request.getParameter("repository") +"');\r\n");
        out.println("wbTree_reload();\r\n");
        out.println("wbTree_executeAction('gotopath.userreps."+request.getParameter("repository")+"."+ request.getParameter("id") +"');\r\n");
        out.println("wbTree_reload();\r\n");
        //out.println("\r\n}\r\n");
        out.println("</script>\r\n");
    }
    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            User user=paramRequest.getUser();
            PrintWriter out=response.getWriter();
            String act="view";
            if(request.getParameter("act")!=null) {
                act=request.getParameter("act");
            }
            if((act.equals("remove")||act.equals("removes")||act.equals("removeit")) && request.getParameter("confirm")==null) {
                if(request.getParameter("id")==null || request.getParameter("id").equals("")
                || request.getParameter("tm")==null || request.getParameter("tm").equals("")) {
                    return;
                }
                String id=request.getParameter("id");
                String rep=request.getParameter("tm");
                
                if(act.equals("remove")){
                    try{
                        Role rol = SWBContext.getUserRepository(rep).getRole(id);
                        Iterator iobjs = rol.listRelatedObjects();                        
                        if(iobjs.hasNext()){
                            SWBResourceURL urlWin = paramRequest.getRenderUrl();
                            urlWin.setCallMethod(urlWin.Call_DIRECT);
                            urlWin.setParameter("act","removes");
                            urlWin.setParameter("id",id);
                            urlWin.setParameter("tm",rep);
                            out.println("<script language=javascript>");
                            out.println("window.open('"+urlWin.toString()+"','relatedElements','height=400,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=400');");
                            out.println("</script>");
                        }
                        else{
                            SWBResourceURL url = paramRequest.getRenderUrl();
                            url.setParameter("act","removeit");
                            if(request.getParameter("id")!=null)
                                url.setParameter("id",id);
                            url.setParameter("tm",rep);
                            out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + url.toString()+ "\">");
                            
                        }
                    }
                    catch(Exception e){ log.error("Error while display asociated elements with the role with id:"+id+" in repository:"+rep,e);}
                }
                else if(act.equals("removes")){
                    try{
                        Role rol = SWBContext.getUserRepository(rep).getRole(id);
                        Iterator iobjs = rol.listRelatedObjects();   
                        if(iobjs.hasNext()){ // si existen elementos asociados a la regla se muestran
                            
                            Role recRole = SWBContext.getUserRepository(rep).getRole(id);
                            SWBResourceURL url = paramRequest.getRenderUrl();
                            url.setCallMethod(url.Call_CONTENT);
                            out.println("<HTML>");
                            out.println("<META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html\" >");
                            out.println("<META HTTP-EQUIV=\"CACHE-CONTROL\" CONTENT=\"NO-CACHE\" >");
                            out.println("<META HTTP-EQUIV=\"PRAGMA\" CONTENT=\"NO-CACHE\" >");
                            out.println("<HEAD>");
                            out.println("<TITLE>INFOTEC WebBuilder</TITLE>");
                            
                            out.println("<LINK REL=\"SHORTCUT ICON\" HREF=\"/wbadmin/images/wb.ico\" >");
                            out.println("<LINK href=\"/work/sites/WBAdmin/templates/3/1/images/wb3.css\" rel=\"stylesheet\" type=\"text/css\" >");
                            out.println("<body>");
                            out.println("<table border=0 cellpadding=5 cellspacing=0 width=\"100%\" align=center class=box>");
                            out.println("<tr>");
                            out.println("<td colspan=2 class=tabla>");
                            out.println(paramRequest.getLocaleString("msgAsocElementsList")+" - <b>"+recRole.getTitle()+"</b>");
                            out.println("</td>");
                            out.println("</tr>");
                            
                            boolean entroUser = false;
                            boolean entroTopic = false;
                            boolean entroTemplate = false;
                            boolean entroRecurso = false;
                            boolean entroRegla = false;
                            String rowColor="";
                            boolean cambiaColor = true;
                            while(iobjs.hasNext()){ //esta siendo usado por objetos
                                
                                Object obj=(Object)iobjs.next();
                                if(obj instanceof User){ //el objeto en el que esta siendo usado es un Topic
                                    User recUser=(User)obj;
                                    if(!entroUser){
                                        out.println("<tr>");
                                        out.println("<td colspan=2 class=tabla><HR size=2 noshade>");
                                        out.println(paramRequest.getLocaleString("msgAsocUsers"));
                                        out.println("</td>");
                                        out.println("</tr>");
                                        entroUser = true;
                                        cambiaColor = true;
                                    }
                                    rowColor="#EFEDEC";
                                    if(!cambiaColor) rowColor="#FFFFFF";
                                    cambiaColor = !(cambiaColor);
                                    out.println("<tr bgcolor=\""+rowColor+"\">");
                                    out.println("<td colspan=2 class=valores>");
                                    out.println(recUser.getName());
                                    out.println("</td>");
                                    out.println("</tr>");
                                    
                                }else  if(obj instanceof Template){ //el objeto en el que esta siendo usado es un Template
                                    Template recTpl=(Template)obj;
                                    if(!entroTemplate){
                                        out.println("<tr>");
                                        out.println("<td colspan=2 class=tabla><HR size=2 noshade>");
                                        out.println(paramRequest.getLocaleString("msgAsocTemplates"));
                                        out.println("</td>");
                                        out.println("</tr>");
                                        entroTemplate = true;
                                        cambiaColor = true;
                                    }
                                    rowColor="#EFEDEC";
                                    if(!cambiaColor) rowColor="#FFFFFF";
                                    cambiaColor = !(cambiaColor);
                                    out.println("<tr bgcolor=\""+rowColor+"\">");
                                    out.println("<td colspan=2 class=valores>");
                                    out.println(recTpl.getTitle());
                                    out.println("</td>");
                                    out.println("</tr>");
                                }else if(obj instanceof Portlet){ //el objeto en el que esta siendo usado es un Resource
                                    Portlet recRes=(Portlet)obj;
                                    if(!entroRecurso){
                                        out.println("<tr>");
                                        out.println("<td colspan=2 class=tabla><HR size=2 noshade>");
                                        out.println(paramRequest.getLocaleString("msgAsocResources"));
                                        out.println("</td>");
                                        out.println("</tr>");
                                        entroRecurso = true;
                                        cambiaColor = true;
                                    }
                                    rowColor="#EFEDEC";
                                    if(!cambiaColor) rowColor="#FFFFFF";
                                    cambiaColor = !(cambiaColor);
                                    out.println("<tr bgcolor=\""+rowColor+"\">");
                                    out.println("<td colspan=2 class=valores>");
                                    out.println(recRes.getTitle());
                                    out.println("</td>");
                                    out.println("</tr>");
                                }
                                
                                // Ya no se utiliza RecOccurrence  -------- Revisar
                                else if(obj instanceof WebPage){ //el objeto en el que esta siendo usado es un Topic
                                    WebPage recOcc=(WebPage)obj;
                                    if(!entroTopic){
                                        out.println("<tr>");
                                        out.println("<td colspan=2 class=tabla><HR size=2 noshade>");
                                        out.println(paramRequest.getLocaleString("msgAsocTopics"));
                                        out.println("</td>");
                                        out.println("</tr>");
                                        entroTopic = true;
                                        cambiaColor = true;
                                    }
                                    rowColor="#EFEDEC";
                                    if(!cambiaColor) rowColor="#FFFFFF";
                                    cambiaColor = !(cambiaColor);
                                    out.println("<tr bgcolor=\""+rowColor+"\">");
                                    out.println("<td colspan=2 class=valores>");
                                    WebPage tpAsoc = recOcc;
                                    out.println(tpAsoc.getTitle(user.getLanguage()));
                                    out.println("</td>");
                                    out.println("</tr>");
                                    
                                }
                                
                                else  if(obj instanceof Rule){ //el objeto en el que esta siendo usado es un Resource
                                    Rule rRule=(Rule)obj;
                                    if(!entroRegla){
                                        out.println("<tr>");
                                        out.println("<td colspan=2 class=tabla><HR size=2 noshade>");
                                        out.println(paramRequest.getLocaleString("msgAsocRules"));
                                        out.println("</td>");
                                        out.println("</tr>");
                                        entroRegla = true;
                                        cambiaColor = true;
                                    }
                                    rowColor="#EFEDEC";
                                    if(!cambiaColor) rowColor="#FFFFFF";
                                    cambiaColor = !(cambiaColor);
                                    out.println("<tr bgcolor=\""+rowColor+"\">");
                                    out.println("<td colspan=2 class=valores>");
                                    out.println(rRule.getTitle());
                                    out.println("</td>");
                                    out.println("</tr>");
                                }
                            }
                            out.println("<tr>");
                            out.println("<td colspan=2 align=right><HR size=2 noshade>");
                            
                            url.setParameter("id",id);
                            url.setParameter("tm",rep);
                            url.setParameter("act","removeit");
                            
                            out.println("<A href=\"\" class=boton style=\"text-decoration:none;\" onclick=\"javascript:window.close();\">&nbsp;&nbsp;"+paramRequest.getLocaleString("msgLinkCancel")+"&nbsp;&nbsp;</A>&nbsp;<A target=\"status\" href=\""+url.toString()+"\"class=boton style=\"text-decoration:none;\" name=btn_submit  onclick=\"javascript:if(confirm('"+paramRequest.getLocaleString("msgConfirmShureRemoveRole")+"?')) { window.close(); return (true);} else { window.close(); return (false);} \" >&nbsp;&nbsp;"+paramRequest.getLocaleString("msgBTNEliminateRole")+"&nbsp;&nbsp;</A>");
                            
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<td colspan=2 class=datos>");
                            out.println("* "+paramRequest.getLocaleString("msgNote")+": "+paramRequest.getLocaleString("msgNoteMsg"));
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("</table></body></html>");
                        }
                        else{
                            
                            SWBResourceURL url = paramRequest.getRenderUrl();
                            url.setParameter("act","removeit");
                            if(request.getParameter("id")!=null)
                                url.setParameter("id",id);
                            url.setParameter("tm",rep);
                            out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + url.toString()+ "\">");
                            
                        }
                        
                        
                    }catch(Exception e){
                        log.error("Error while display asociated elements with the roll with id:"+id+" in repository:"+ rep);
                    }
                }
                
                else if(act.equals("removeit")){
 
                    Role recrole=SWBContext.getUserRepository(rep).getRole(id);
                    String repository=recrole.getUserRepository().getId();
                    try {
                        HashSet oflujos=new HashSet();
                        Iterator<WebSite> maps=SWBContext.listWebSites();
                        while(maps.hasNext()) {
                            WebSite map=maps.next();
                            if(repository.equals(map.getUserRepository().getId())) {
                                Iterator<PFlow> flows=SWBContext.getWebSite(map.getId()).listPFlows();
                                while(flows.hasNext()) {
                                    PFlow pflow=flows.next();
                                    Document doc=SWBUtils.XML.xmlToDom(pflow.getXml());
                                    NodeList roles=doc.getElementsByTagName("role");
                                    for(int i=0;i<roles.getLength();i++) {
                                        Element erole=(Element)roles.item(i);
                                        if(erole.getAttribute("id").equals(String.valueOf(id))) {
                                            oflujos.add(pflow);
                                        }
                                    }
                                }
                            }
                        }
                        if(oflujos.size()>0) {
                            out.println("<script>");
                            String alert="var resp=confirm(\""+paramRequest.getLocaleString("msgAlertFlow1")+"\\r\\n"+paramRequest.getLocaleString("msgAlertFlow2")+"?\\r\\n";
                            Iterator it=oflujos.iterator();
                            while(it.hasNext()) {
                                PFlow pflow=(PFlow)it.next();
                                alert+="\\r\\* "+pflow.getTitle().replace('\"','\'');
                            }
                            alert+="\");";
                            out.println(alert);
                            out.println("if(resp){");
                            SWBResourceURL url=paramRequest.getRenderUrl();
                            url.setMode(url.Mode_VIEW);
                            out.println("location='"+ url +"?id="+ request.getParameter("id") +"&tm="+ request.getParameter("tm") +"&act="+request.getParameter("act")+"&confirm=true';");
                            out.println("}");
                            out.println("</script>");
                        }
                        else {
                            //recrole.remove(user.getId(),paramRequest.getLocaleString("msgLogRole")+ id + paramRequest.getLocaleString("msgLogWasDeleted"));
                            try {
                                maps=SWBContext.listWebSites();
                                while(maps.hasNext()) {
                                    WebSite map=maps.next();
                                    if(repository.equals(map.getUserRepository().getId())) {
                                        Iterator<PFlow> flows=map.listPFlows();
                                        while(flows.hasNext()) {
                                            PFlow pflow=flows.next();
                                            Document doc=SWBUtils.XML.xmlToDom(pflow.getXml());
                                            NodeList roles=doc.getElementsByTagName("role");
                                            for(int i=0;i<roles.getLength();i++) {
                                                Element erole=(Element)roles.item(0);
                                                if(erole.getAttribute("id").equals(String.valueOf(id))) {
                                                    erole.getParentNode().removeChild(erole);
                                                    try {
                                                        pflow.setXml(SWBUtils.XML.domToXml(doc));
                                                        //pflow.getRecPFlow().update();
                                                    }
                                                    catch(Exception e) {
                                                        e.printStackTrace(System.out);
                                                        log.error(e);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            catch(Exception e) {
                                e.printStackTrace(System.out);
                                log.error(e);
                            }
                            
                            UserRepository urep = SWBContext.getUserRepository(rep);
                            urep.removeRole(id);
                            urep.setModifiedBy(user);
                            out.println(paramRequest.getLocaleString("mnsRoleRemoved"));
                            out.println("\r\n<script>\r\n");
                            out.println("wbTree_executeAction('gotopath.userreps."+ repository +"');\r\n");
                            out.println("wbTree_reload();\r\n");
                            out.println("</script>\r\n");
                            return;
                        }
                    }
                    catch(Exception e) {
                        e.printStackTrace(System.out);
                        log.error(e);
                    }
                }
            }
            else if(act.equals("remove") && request.getParameter("confirm")!=null) {
                if(request.getParameter("id")==null || request.getParameter("id").equals("")
                || request.getParameter("tm")==null || request.getParameter("tm").equals("")) {
                    return;
                }
                String id=request.getParameter("id");
                String rep=request.getParameter("tm");
                UserRepository urep = SWBContext.getUserRepository(rep);
                Role recrole=urep.getRole(id);
                String repository=recrole.getUserRepository().getId();
                urep.removeRole(id);
                urep.setModifiedBy(user);
                try {
                    Iterator<WebSite> maps=SWBContext.listWebSites();
                    while(maps.hasNext()) {
                        WebSite map=maps.next();
                        if(repository.equals(map.getUserRepository().getId())){
                            Iterator<PFlow> flows=map.listPFlows();
                            while(flows.hasNext()) {
                                PFlow pflow=(PFlow)flows.next();
                                Document doc=SWBUtils.XML.xmlToDom(pflow.getXml());
                                NodeList roles=doc.getElementsByTagName("role");
                                for(int i=0;i<roles.getLength();i++) {
                                    Element erole=(Element)roles.item(i);
                                    if(erole.getAttribute("id").equals(String.valueOf(id))) {
                                        erole.getParentNode().removeChild(erole);
                                        try {
                                            pflow.setXml(SWBUtils.XML.domToXml(doc));
                                            pflow.setModifiedBy(user);
                                        }
                                        catch(Exception e) {
                                            e.printStackTrace(System.out);
                                            log.error(e);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                catch(Exception e) {
                    e.printStackTrace(System.out);
                    log.error(e);
                }
                out.println("\r\n<script>\r\n");
                out.println("wbTree_executeAction('gotopath.userreps."+ repository +"');\r\n");
                out.println("wbTree_reload();\r\n");
                out.println("</script>\r\n");
            }else if(act.equals("add")) {
                if(request.getParameter("id")==null || request.getParameter("id").equals("")) {
                    return;
                }
                
                out.println("<form class=\"box\">");
                out.println("<table width=100% cellpadding=10 cellspacing=0>");
                
                out.println("<tr>");
                out.println("<td class=\"datos\" width=150 align=rigth>");
                out.println(paramRequest.getLocaleString("msgName")+":");
                out.println("</td>");
                out.println("<td>");
                
                out.println("<input class=\"campos\" name='name' size=50>");
                
                out.println("</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td class=\"datos\" width=150 align=rigth>");
                out.println(paramRequest.getLocaleString("msgDescription")+":");
                out.println("</td>");
                out.println("<td>");
                
                out.println("<textarea name='description'  class=\"campos\" rows=10 cols=30></textarea>");
                
                out.println("</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td colspan='2' align='right'><HR size=1 noshade>");
                out.println("<input type='button' class=\"boton\" name='guardar' value='"+paramRequest.getLocaleString("msgBtnSave")+"' onClick=\"javascript:Valida(this.form)\">");
                out.println("<input type='hidden' name='act' value='addrole'>");
                out.println("<input type='hidden' name='id' value='"+ request.getParameter("id") +"'>");
                out.println("</td>");
                
                out.println("</tr>");
                
                
                out.println("</table>");
                out.println("</form>");
                
                out.println("<script>");
                out.println("function Valida(forma)");
                out.println("{");
                out.println("\tif(forma.name.value=='')");
                out.println("\t{");
                out.println("\talert('"+paramRequest.getLocaleString("msgAlertRolName")+"');");
                out.println("\treturn;");
                out.println("\t}");
                out.println("\tif(forma.description.value=='')");
                out.println("\t{");
                out.println("\talert('"+paramRequest.getLocaleString("msgAlertRolDescription")+"');");
                out.println("\treturn;");
                out.println("\t}");
                out.println("forma.submit();");
                out.println("}");
                out.println("</script>");
                
                
            }
            else if(act.equals("addrole")) {
                if(request.getParameter("name")!=null && request.getParameter("description")!=null && !request.getParameter("name").trim().equals("") && !request.getParameter("description").trim().equals("")) {
                    String rep = request.getParameter("tm");
                    UserRepository urep = SWBContext.getUserRepository(rep);
                    
                    String name=request.getParameter("name");
                    String description=request.getParameter("description");
                    
                    Role recrole = urep.createRole();
                    recrole.setTitle(name);
                    recrole.setDescription(description);
                    recrole.setCreator(user);

                    try {
                        out.println("\r\n<script>\r\n");
                        out.println("wbTree_executeAction('gotopath.userreps."+ request.getParameter("id") +"');\r\n");
                        out.println("wbTree_reload();\r\n");
                        String rolid=""+recrole.getId();
                        out.println("wbTree_executeAction('gotopath.userreps."+ request.getParameter("id") +"."+ rolid +"');\r\n");
                        out.println("</script>\r\n");
                    }
                    catch(Exception e) {
                        e.printStackTrace(System.out);
                        e.printStackTrace(out);
                    }
                    out.println("<script>");
                    out.println("location='"+ request.getRequestURI() +"?act=view&id="+ recrole.getId() +"&tm="+ request.getParameter("id")  +"'");
                    out.println("</script>");
                }
                
            }
            else if(act.equals("view") && request.getParameter("id")!=null && request.getParameter("tm")!=null) {
                String id=request.getParameter("id");
                String rep=request.getParameter("tm");
                Role role=SWBContext.getUserRepository(rep).getRole(id);
                out.println("<p class=\"box\">");
                out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                out.println("<TR>");
                out.println("<TD width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgIdentifier")+"</TD>");
                out.println("<TD class=\"valores\">");
                out.println(role.getId());
                out.println("</TD>");
                out.println("</TR>");
                out.println("</TABLE></P>");
                
                out.println("<P class=\"box\">");
                out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                out.println("<tr>");
                out.println("<td align=\"right\" width=150 class=\"datos\">"+paramRequest.getLocaleString("msgName")+":</td>");
                out.println("<td class=\"valores\">");
                out.println(role.getTitle());
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\" width=150 class=\"datos\">"+paramRequest.getLocaleString("msgDescription")+":</td>");
                out.println("<td class=\"valores\">");
                out.println(role.getDescription());
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</p>");
            }
            else if(act.equals("edit") && request.getParameter("id")!=null && request.getParameter("tm")!=null) {
                boolean per=false;
                String id=request.getParameter("id");
                String rep=request.getParameter("tm");
                Role role=SWBContext.getUserRepository(rep).getRole(id);
                String repository=role.getUserRepository().getId();
                if(SWBContext.getAdminWebSite().getUserRepository().getId().equals(repository)) {
                    per=true;
                }
                out.println("<APPLET id=\"apptree\" name=\"apptree\" code=\"applets.adminrole.PermissRole.class\" codebase=\""+SWBPlatform.getContextPath()+"\" ARCHIVE=\"wbadmin/lib/PermissRole.jar, wbadmin/lib/WBCommons.jar\" width=\"100%\" height=\"300\">");
                SWBResourceURL url=paramRequest.getRenderUrl();
                url.setMode("gateway");
                url.setCallMethod(url.Call_DIRECT);
                out.println("<PARAM NAME =\"id\" VALUE=\""+request.getParameter("id")+"\">");
                out.println("<PARAM NAME =\"cgipath\" VALUE=\""+url+"\">");
                out.println("<PARAM NAME =\"locale\" VALUE=\""+user.getLanguage()+"\">");
                out.println("<PARAM NAME =\"repository\" VALUE=\""+repository+"\">");
                out.println("<PARAM NAME =\"showper\" VALUE=\""+per+"\">");
                url=paramRequest.getRenderUrl();
                url.setMode("script");
                url.setCallMethod(url.Call_DIRECT);                
                out.println("<PARAM NAME =\"script\" VALUE=\""+url+"\">");
                out.println("</APPLET>");
                out.println("</APPLET>");
                out.println("\r\n<script>\r\n");
                out.println("\r\nfunction UpdateTreeWF(id){\r\n");
                out.println("wbTree_executeAction('gotopath.userreps."+ repository +"');\r\n");
                out.println("wbTree_reload();\r\n");
                out.println("wbTree_executeAction('gotopath.userreps."+repository+"."+ id +"');\r\n");
                out.println("wbTree_reload();\r\n");
                out.println("\r\n}\r\n");
                out.println("</script>\r\n");
            }  
        }
        catch(Exception e) {
            e.printStackTrace(System.out);
            log.error(e);
        }
    }
}
