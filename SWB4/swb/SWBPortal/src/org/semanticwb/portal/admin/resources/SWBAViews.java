/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.portal.api.*;
import org.w3c.dom.*;

/**
 *
 * @author juan.fernandez
 */
public class SWBAViews extends GenericResource { //WBATree
    
    private Logger log=SWBUtils.getLogger(SWBAViews.class);
    static final String[] pathValids={"getServer","getTopic","getTopicMap"};
    static final String[] namevalids={"node","config","icons","icon","res","events","willExpand"};
    /** Creates a new instance of WBAViews */
    public SWBAViews() {
    }
//
//    @Override
//    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        if(paramRequest.getMode().equals("gateway"))
//        {
//            doGateway(request,response,paramRequest);
//        }else super.processRequest(request,response,paramRequest);
//    }
//    
//    public Document initTree(User user, Document src)
//    {
//        Document doc=super.initTree(user,src);        
//        RevisaNodo(doc.getFirstChild());                 
//        return doc;
//    }
//    
//    /**
//     * @param user
//     * @param src
//     * @return
//     */    
//    public Document initTreeFilter(User user, Document src)
//    {
//        Document doc=super.initTreeFilter(user,src);                
//        RevisaNodo(doc.getFirstChild());                 
//        return doc;
//    }    
//    
//    public void RevisaNodo(Node ele)
//    {        
//        Vector vnodes=new Vector();
//        NodeList nodes=ele.getChildNodes();
//        for(int i=0;i<nodes.getLength();i++)
//        {
//            vnodes.add(nodes.item(i));
//        }
//        for(int i=0;i<vnodes.size();i++)
//        {            
//            if(vnodes.elementAt(i) instanceof Element)
//            {
//                Element e=(Element)vnodes.elementAt(i);
//                if(!isNameValid(e) || !isValid(e.getAttribute("reload")))
//                {                     
//                    ele.removeChild((Node)vnodes.elementAt(i));
//                }         
//                else
//                {
//                    RevisaNodo(e);                
//                }
//            }
//            else
//            {
//                RevisaNodo((Node)vnodes.elementAt(i));
//            }
//        }     
//    }
//    public boolean isNameValid(Element e)
//    {       
//        
//        for(int i=0;i<this.namevalids.length;i++)
//        {
//            if(e.getNodeName().equals(this.namevalids[i]))
//            {
//                return true;
//            }
//        }                
//        return false;
//    }
//    public boolean isValid(String path)
//    {
//        if(path==null)
//        {
//            return true;
//        }
//        StringTokenizer st=new StringTokenizer(path,".");
//        if(st.countTokens()>0)
//        {
//            String pathinit=st.nextToken();            
//            for(int i=0;i<this.pathValids.length;i++)
//            {            
//                if(pathinit.equals(this.pathValids[i]))
//                {
//                    return true;
//                }
//            }
//        }
//        else
//        {            
//            return true;
//        }
//        return false;
//    }   
//    
//    
//    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
//    {
//        PrintWriter out=response.getWriter();
//        ServletInputStream in = request.getInputStream();
//        Document dom = SWBUtils.XML.xmlToDom(in);
//        if (!dom.getFirstChild().getNodeName().equals("req"))
//        {
//            response.sendError(404, request.getRequestURI());
//            return;
//        }
//        String cmd = null;
//        if (dom.getElementsByTagName("cmd").getLength() > 0)
//            cmd = dom.getElementsByTagName("cmd").item(0).getFirstChild().getNodeValue();
//
//        if (cmd == null)
//        {
//            response.sendError(404, request.getRequestURI());
//            return;            
//        }                
//        String ret="";
//
//        try
//        {
//            Document res=null;
//            if(cmd.equals("getFilter"))
//            {
//                res = getFilter(cmd, dom, paramRequest.getUser(), request, response);                
//            }  
//            else if(cmd.equals("update"))
//            {
//                res = updateFilter(cmd, dom, paramRequest.getUser(), request, response);                
//            }
//            else
//            {
//                res = getService(cmd, dom, paramRequest.getUser(), request, response);
//            }
//            if (res == null)
//            {
//                ret = SWBUtils.XML.domToXml(getError(3));
//            } else
//                ret = SWBUtils.XML.domToXml(res, true);
//        }catch(Exception e){log.error(e);}
//        
//        out.print(new String(ret.getBytes()));
//        
//    }   
//    public Document getFilter(String cmd,Document src,User user,HttpServletRequest request,HttpServletResponse response)
//    {
//        
//        Document docres=null;
//        try
//        {   
//            String topicmap=src.getElementsByTagName("tm").item(0).getFirstChild().getNodeValue();
//            WebSite map= SWBContext.getWebSite(topicmap);
//            docres=SWBUtils.XML.getNewDocument();            
//            Element res=docres.createElement("res");
//            docres.appendChild(res);
//            if(src.getElementsByTagName("id").getLength()>0)
//            {
//                Element eid=(Element)src.getElementsByTagName("id").item(0);
//                Text etext=(Text)eid.getFirstChild();
//                String id=etext.getNodeValue();
//                RecAdmFilter filter=DBAdmFilter.getInstance().getAdmFilter(Integer.parseInt(id), map.getId());
//                Document exmlfilter=SWBUtils.XML.xmlToDom(filter.getXml());
//                Node node=docres.importNode(exmlfilter.getFirstChild(),true);
//                res.appendChild(node);
//                NodeList nodes=docres.getElementsByTagName("node");
//                for(int i=0;i<nodes.getLength();i++)
//                {
//                    Element enode=(Element)nodes.item(i);
//                    String topicid=enode.getAttribute("id");
//                    String path=topicid;                    
//                    WebSite topicMap=SWBContext.getWebSite(topicmap);
//                    if(topicMap!=null)
//                    {
//                        WebPage topic=topicMap.getWebPage(topicid);                    
//                        if(topic!=null)
//                        {
//                            while(topic.getParent()!=null)
//                            {
//                                path=topic.getParent().getId()+"|"+path;
//                                topic=topic.getParent();
//                            }                              
//                            enode.setAttribute("reload","getTopic."+ topicmap + "."+ topicid);                            
//                        }                        
//                    }                    
//                    enode.setAttribute("path",path);
//                }                
//            }            
//        }
//        catch(Exception e)
//        {
//            //e.printStackTrace(System.out);
//            log.error(e);
//        }
//        return docres;
//    }
//    public Document updateFilter(String cmd,Document src,User user,HttpServletRequest request,HttpServletResponse response)
//    {
//        if(src.getElementsByTagName("filtermap").getLength()>0)
//        {
//            Element efilter=(Element)src.getElementsByTagName("filtermap").item(0);
//            if(efilter.getAttribute("id")==null || efilter.getAttribute("id").equals(""))
//            {
//                return add(cmd, src, user, request, response);
//            }
//            else
//            {
//                return update(cmd, src, user, request, response);
//            }
//        }
//        return null;
//    }
//    public Document add(String cmd,Document src,User user,HttpServletRequest request,HttpServletResponse response)
//    {
//        Document doc=null;
//        try
//        {
//            doc=SWBUtils.XML.getNewDocument();
//            Element res=doc.createElement("res");
//            doc.appendChild(res);
//            if(src.getElementsByTagName("filtermap").getLength()>0)
//            {            
//                Element efilter=(Element)src.getElementsByTagName("filtermap").item(0);
//                String description="";
//                if(efilter.getElementsByTagName("description").getLength()>0)
//                {
//                    Element edescription=(Element)efilter.getElementsByTagName("description").item(0);
//                    Text etext=(Text)edescription.getFirstChild();
//                    description=etext.getNodeValue();
//                }
//                String name=efilter.getAttribute("name");
//                RecAdmFilter filter=new RecAdmFilter();
//                filter.setName(name);
//                filter.setDescription(description);
//                filter.setTopicMapId(efilter.getAttribute("topicmap"));
//                filter.setLastupdate(new Timestamp(System.currentTimeMillis()));
//                Document xmlfilter=SWBUtils.XML.getNewDocument();
//                Element newnode=(Element)xmlfilter.importNode(efilter, true);
//                xmlfilter.appendChild(newnode);
//                filter.setXml(SWBUtils.XML.domToXml(xmlfilter));
//                try
//                {
//                    filter.create();
//                    newnode.setAttribute("id",String.valueOf(filter.getId()));
//                    filter.setXml(SWBUtils.XML.domToXml(xmlfilter));
//                    filter.update();                    
//                    addElement("filtermap",String.valueOf(filter.getId()) , res);                    
//                    return doc;
//                }
//                catch(Exception afe)
//                {
//                    //afe.printStackTrace(System.out);
//                    addElement("err", afe.getMessage(), res);
//                    log.error(afe);
//                }
//            }
//            else
//            {
//                addElement("err","The element filter was not found", res);
//            }
//        }
//        catch(Exception e)
//        {
//            //e.printStackTrace(System.out);
//            log.error(e);
//        }
//        return doc;
//    }
//    public Document update(String cmd,Document src,User user,HttpServletRequest request,HttpServletResponse response)
//    {
//       Document doc=null;
//        try
//        {
//            doc=SWBUtils.XML.getNewDocument();
//            Element res=doc.createElement("res");
//            doc.appendChild(res);
//            if(src.getElementsByTagName("filtermap").getLength()>0)
//            {            
//                Element efilter=(Element)src.getElementsByTagName("filtermap").item(0);
//                String description="";
//                if(efilter.getElementsByTagName("description").getLength()>0)
//                {
//                    Element edescription=(Element)efilter.getElementsByTagName("description").item(0);
//                    Text etext=(Text)edescription.getFirstChild();
//                    description=etext.getNodeValue();
//                }
//                String name=efilter.getAttribute("name");
//                RecAdmFilter filter=DBAdmFilter.getInstance().getAdmFilter(Integer.parseInt(efilter.getAttribute("id")),efilter.getAttribute("topicmap"));
//                filter.setName(name);
//                filter.setDescription(description);
//                filter.setTopicMapId(efilter.getAttribute("topicmap"));
//                filter.setLastupdate(new Timestamp(System.currentTimeMillis()));
//                Document xmlfilter=SWBUtils.XML.getNewDocument();
//                Element newnode=(Element)xmlfilter.importNode(efilter, true);
//                xmlfilter.appendChild(newnode);
//                filter.setXml(SWBUtils.XML.domToXml(xmlfilter));
//                try
//                {                    
//                    newnode.setAttribute("id",String.valueOf(filter.getId()));
//                    filter.setXml(SWBUtils.XML.domToXml(xmlfilter));
//                    filter.update();                    
//                    addElement("filtermap",String.valueOf(filter.getId()) , res);
//                }
//                catch(Exception afe)
//                {
//                    //afe.printStackTrace(System.out);
//                    addElement("err", afe.getMessage(), res);
//                    log.error(afe);
//                }
//            }
//            else
//            {
//                addElement("err","The element filter was not found", res);
//            }
//        }
//        catch(Exception e)
//        {
//            //e.printStackTrace(System.out);
//            log.error(e);
//        }
//        return doc;
//    }
//
//    @Override
//    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        WebSite map=null;
//        if(request.getParameter("tm")!=null)
//        {
//            map=SWBContext.getWebSite(request.getParameter("tm"));
//            
//        }
//        
//        User user=paramRequest.getUser();
//        PrintWriter out=response.getWriter();        
//        String act="view";
//        if(request.getParameter("act")!=null)
//        {
//            act=request.getParameter("act");
//        }
//        if(act.equals("remove") && request.getParameter("id")!=null && map!=null)
//        {
//            //  TODO:
//            // Borrar filtros aplicados a los uaurios
//            WebSite mapadmin=SWBContext.getAdminWebSite();
//            String repository=mapadmin.getUserRepository().getId();
//            Iterator<User> users=SWBContext.getUserRepository(repository).listUsers();
//            while(users.hasNext())
//            {
//                User recuser=users.next();
//                
//            }
//            int id=Integer.parseInt(request.getParameter("id"));
//            RecAdmFilter filter=DBAdmFilter.getInstance().getAdmFilter(id, map.getId());
//            filter.remove();
//            act="view";       
//        }
//        else if(act.equals("add") && map!=null)
//        {
//            SWBResourceURL url=paramRequest.getRenderUrl();
//            url.setMode("gateway");        
//            url.setCallMethod(url.Call_DIRECT);
//            out.println("<APPLET id=\"editview\" name=\"editfilter\" code=\"applets.mapviews.EditView.class\" codebase=\""+SWBPlatform.getContextPath()+"\" ARCHIVE=\"wbadmin/lib/mapviews.jar, wbadmin/lib/WBCommons.jar\" width=\"100%\" height=\"100%\">");                        
//            out.println("<PARAM NAME =\"cgipath\" VALUE=\""+url+"\">");            
//            out.println("<PARAM NAME =\"locale\" VALUE=\""+user.getLanguage()+"\">");            
//            out.println("<PARAM NAME =\"tm\" VALUE=\""+map.getId()+"\">");
//            out.println("</APPLET>");           
//            out.println("\r\n<script>\r\n");            
//            out.println("\r\nfunction doView(){\r\n");  
//            url=paramRequest.getRenderUrl();
//            url.setMode(url.Mode_VIEW);            
//            out.println("location='"+ url   +"';\r\n");            
//            out.println("\r\n}\r\n");            
//            out.println("</script>\r\n");
//        }      
//        else if(act.equals("edit") && request.getParameter("id")!=null && map!=null)
//        {
//            
//            out.println("<APPLET id=\"editfilter\" name=\"editfilter\" code=\"applets.mapviews.EditView.class\" codebase=\""+SWBPlatform.getContextPath()+"\" ARCHIVE=\"wbadmin/lib/mapviews.jar, wbadmin/lib/WBCommons.jar\" width=\"100%\" height=\"100%\">");
//            SWBResourceURL url=paramRequest.getRenderUrl();
//            url.setMode("gateway");
//            url.setCallMethod(url.Call_DIRECT);
//            out.println("<PARAM NAME =\"idview\" VALUE=\""+request.getParameter("id")+"\">");
//            out.println("<PARAM NAME =\"cgipath\" VALUE=\""+url+"\">");            
//            out.println("<PARAM NAME =\"locale\" VALUE=\""+user.getLanguage()+"\">");            
//            out.println("<PARAM NAME =\"tm\" VALUE=\""+map.getId()+"\">");
//            out.println("</APPLET>");            
//            out.println("\r\n<script>\r\n");            
//            out.println("\r\nfunction doView(){\r\n");             
//            url=paramRequest.getRenderUrl();
//            url.setMode(url.Mode_VIEW);            
//            out.println("location='"+ url   +"';\r\n");            
//            out.println("\r\n}\r\n");            
//            out.println("</script>\r\n");
//        } 
//        if(act.equals("view"))
//        {
//            SWBResourceURL url=paramRequest.getRenderUrl();
//            url.setMode(url.Mode_VIEW);
//            
//            String topicmap=null;   
//            if(request.getParameter("tm")!=null)
//            {
//                map=SWBContext.getWebSite(request.getParameter("tm"));
//            }
//            out.println("<p class=\"box\" align='center' >");  
//            out.println("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" >");
//            out.println("<tr>");            
//            out.println("<td align='center'>");            
//            
//
//            out.println("<form>");            
//            out.println("<select name=\"topicmap\">");
//            Vector topicmaps=new Vector();
//            Iterator<WebSite> it=SWBContext.listWebSites();
//            while(it.hasNext())
//            {                
//                WebSite tm=(WebSite)it.next();    
//                if(!tm.getId().equals(SWBContext.WEBSITE_ADMIN))
//                {
//                    if(AdmFilterMgr.getInstance().haveAccess2TopicMap(user,tm.getId())!=0 && !tm.getId().equals(TopicMgr.getInstance().getGlobalTopicMap().getId()))
//                    {
//                        topicmaps.add(tm);                    
//                    }
//                }
//            }
//            
//            for(int i=0;i<topicmaps.size();i++)
//            {
//                WebSite tm=(WebSite)topicmaps.elementAt(i);
//                if(!tm.getId().equals(SWBContext.WEBSITE_ADMIN))
//                {
//                    String selected="";
//                    if(request.getParameter("topicmap")!=null)
//                    {
//                        if(request.getParameter("topicmap").equals(tm.getId()))
//                        {
//                            selected="selected";
//                            topicmap=tm.getId();
//                        }
//                        else
//                        {
//                            selected="";
//                        }
//                    }
//                    else if(request.getParameter("tm")!=null)
//                    {
//                        if(request.getParameter("tm").equals(tm.getId()))
//                        {
//                            selected="selected";
//                            topicmap=tm.getId();
//                        }
//                        else
//                        {
//                            selected="";
//                        }
//                    }
//                    else
//                    {
//                        if(i==0)
//                        {
//                            selected="selected";
//                            topicmap=tm.getId();
//                        }
//                        else
//                        {
//                            selected="";
//                        }
//                    }
//                    out.println("<option "+ selected +" value=\""+ tm.getId() +"\">"+ tm.getTitle() +"</option>");
//                }
//            }           
//            out.println("</select>");
//            out.println("<input type='submit' name='enviar' class=boton value='"+paramRequest.getLocaleString("ver")+"'>");            
//            out.println("</form>");
//            
//            out.println("</td>");
//            out.println("</tr>");
//            out.println("</table>");  
//            out.println("</p>");            
//            if(topicmap!=null && map==null)
//            {
//                map=SWBContext.getWebSite(topicmap);
//            }           
//            if(map!=null)
//            {
//                 
//                out.println("<p class=\"box\">");
//                out.println("<table width='100%' cellpadding=\"10\" cellspacing=\"0\">");     
//                out.println("<tr>");     
//
//                out.println("<td class=\"tabla\" colspan=2 align=\"center\">");     
//                out.println(paramRequest.getLocaleString("msgAction"));     
//                out.println("</td>"); 
//
//                out.println("<td class=\"tabla\">");     
//                out.println(paramRequest.getLocaleString("msgIdentifier"));     
//                out.println("</td>");     
//
//                out.println("<td class=\"tabla\">");     
//                out.println(paramRequest.getLocaleString("msgFilter"));     
//                out.println("</td>");   
//
//                out.println("<td class=\"tabla\">");     
//                out.println(paramRequest.getLocaleString("msgDescription"));     
//                out.println("</td>");   
//
//                out.println("</tr>");   
//
//                String rowColor="";
//                boolean cambiaColor = true;
//                Enumeration filters=DBAdmFilter.getInstance().getAdmFilters(map.getId());            
//                while(filters.hasMoreElements())
//                {
//                    RecAdmFilter filter=(RecAdmFilter)filters.nextElement();
//
//                    rowColor="#EFEDEC";
//                    if(!cambiaColor) rowColor="#FFFFFF";
//                    cambiaColor = !(cambiaColor);
//
//                    out.println("<tr bgcolor=\""+rowColor+"\">");     
//
//                    out.println("<td class=\"valores\" colspan=2 align=center>");    
//
//                    SWBResourceURL urlRemove = paramRequest.getRenderUrl();
//                    urlRemove.setParameter("act","remove");
//                    urlRemove.setParameter("id",Integer.toString(filter.getId()));
//                    urlRemove.setParameter("tm",filter.getTopicMapId());
//                    out.println("<a class=\"link\" href=\""+urlRemove.toString()+"\" onclick=\"return ( confirm('"+paramRequest.getLocaleString("msgAlertShureRemoveFilter")+"?') );\"><img src=\""+SWBPlatform.getContextPath()+"wbadmin/images/eliminar.gif\" border=0 alt=\""+paramRequest.getLocaleString("msgLinkRemove")+"\"></a>&nbsp;");
//
//                    SWBResourceURL urlEdit = paramRequest.getRenderUrl();
//                    urlEdit.setParameter("act","edit");
//                    urlEdit.setParameter("id",Integer.toString(filter.getId()));
//                    urlEdit.setParameter("tm",filter.getTopicMapId());
//                    out.println("<a class=\"link\" href=\""+urlEdit.toString()+"\" ><img src=\""+SWBPlatform.getContextPath()+"wbadmin/images/i_contenido.gif\" border=0 alt=\""+paramRequest.getLocaleString("msgLinkEdit")+"\"></a>");
//
//                    out.println("</td>");
//
//                    out.println("<td class=\"valores\">");     
//                    out.println(filter.getId());     
//                    out.println("</td>");     
//
//                    out.println("<td class=\"valores\">");     
//                    out.println(filter.getName());     
//                    out.println("</td>");   
//
//                    out.println("<td class=\"valores\">");     
//                    out.println(filter.getDescription());     
//                    out.println("</td>");   
//
//                    out.println("</tr>");                       
//                }
//                out.println("<tr  class=\"valores\">");                 
//                out.println("<td colspan='5' align=right><HR size=\"1\" noshade>"); 
//
//                out.println("<form action='"+ url  +"'>");     
//                out.println("<input class=\"boton\" type='submit' name='op' value='"+paramRequest.getLocaleString("msgBtnAdd")+"'>");                 
//                out.println("<input type='hidden' name='act' value='add'>");                 
//                out.println("<input type='hidden' name='tm' value='"+ map.getId() +"'>");                 
//                out.println("</form>");   
//                out.println("</td>"); 
//                out.println("</tr>");               
//                out.println("</table>");   
//                out.println("</p>"); 
//            }
//              
//        } 
//    }
}
