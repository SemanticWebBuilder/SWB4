
package org.semanticwb.portal.util;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.SWBResourceException;


public class WebSiteSectionTree {
    private static Logger log = SWBUtils.getLogger(WebSiteSectionTree.class);
    
    protected static final String webcontext = SWBPlatform.getContextPath() + "/swbadmin/icons/";
    
    public String render(String selectedsite, HttpServletRequest request, User user, String url) throws SWBResourceException, IOException {
        StringBuilder sb_ret = new StringBuilder();
        StringBuilder params = new StringBuilder("&site="+selectedsite);
        String whoOpen = "";        
        WebSite tm = null;

        try {
            WebPage tpsite=null, tpid=null;
            String style;
            
            if(request.getParameter("reptm")!=null) {
                tm=SWBContext.getWebSite(request.getParameter("reptm"));
                
                if(tm!=null) {                
                    tpsite=tm.getHomePage();

                    if(request.getParameter("reptp")!=null && !request.getParameter("reptp").trim().equals("")) {
                        if(tm.getWebPage(request.getParameter("reptp"))!=null) {
                            tpid=tm.getWebPage(request.getParameter("reptp"));
                        }
                    }
                }
            }
            
            WebSite tmit = SWBContext.getWebSite(selectedsite);            
            WebPage tmhome=tmit.getHomePage();
            
            boolean toggleopen = Boolean.parseBoolean(request.getParameter(tmhome.getId())==null?"false":request.getParameter(tmhome.getId()).equals("1")?"true":"false");
           
            sb_ret.append("<div id=\"sitesectiontree\">");
            sb_ret.append("<ul class=\"treeres\">");
            sb_ret.append("<li>");
            sb_ret.append("<img src=\""+webcontext+"/icon_sitea.gif\" />");
            sb_ret.append(tmit.getId());
            sb_ret.append("<ul class=\"treeres\">");
            sb_ret.append("<li>");
            
            if(tpid!=null && tpid.getId().equalsIgnoreCase(tmhome.getId())) {
                style=" style=\"color:#FF6600; font-weight:bold\" ";                
                if(toggleopen) {
                    params.append("&"+tmhome.getId()+"=0");
                    sb_ret.append("<img src=\""+webcontext+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','slave')\" />");
                    toggleopen = false;
                }else {
                    params.append("&"+tmhome.getId()+"=1");
                    sb_ret.append("<img src=\""+webcontext+"/minus.gif\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','slave')\" />");
                    toggleopen = true;
                }
            }else {
                style="";
                if(toggleopen) {
                    params.append("&"+tmhome.getId()+"=1");
                    sb_ret.append("<img src=\""+webcontext+"/minus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','slave')\" />");
                }else {
                    params.append("&"+tmhome.getId()+"=0");
                    sb_ret.append("<img src=\""+webcontext+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','slave')\" />");
                }
            }
            
            sb_ret.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"/?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+whoOpen+params+"','slave')\""+style+">");
            sb_ret.append("<img src=\""+webcontext+"/icon_homea.gif\" />");
            sb_ret.append(tmhome.getDisplayName());            
            sb_ret.append("</a>");
            
            if(tpsite!=null && toggleopen) {
                sb_ret.append("<ul class=\"treeres\">");
                
                Iterator<WebPage> childs=tpsite.listChilds();
                while(childs.hasNext()) {
                    WebPage webpage = childs.next();
                    if(webpage.getId()!=null) {                        
                        toggleopen = Boolean.parseBoolean(request.getParameter(webpage.getId())==null?"false":request.getParameter(webpage.getId()).equals("1")?"true":"false");
                        
                        if(webpage.listChilds().hasNext()) {
                            sb_ret.append("<li>");
                            if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                                style=" style=\"color:#FF6600; font-weight:bold\" ";
                                if(toggleopen) {
                                    params.append("&"+webpage.getId()+"=0");
                                    sb_ret.append("<img src=\""+webcontext+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\" />");
                                    toggleopen = false;
                                }else {
                                    params.append("&"+webpage.getId()+"=1");
                                    sb_ret.append("<img src=\""+webcontext+"/minus.gif\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\" />");
                                    toggleopen = true;
                                }
                            }else {
                                style="";
                                if(toggleopen) {
                                    params.append("&"+webpage.getId()+"=1");
                                    sb_ret.append("<img src=\""+webcontext+"/minus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + webpage.getId()+params+"','slave')\" />");
                                }else {
                                    params.append("&"+webpage.getId()+"=0");
                                    sb_ret.append("<img src=\""+webcontext+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + webpage.getId()+params+"','slave')\" />");
                                }                                
                            }                            
                            
                            sb_ret.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\""+style+">");
                            sb_ret.append("<img src=\""+webcontext+"/icon-section.gif\" />");
                            sb_ret.append(webpage.getDisplayName());
                            sb_ret.append("</a>");
                            
                            if(toggleopen) {
                                sb_ret.append(getChild(request, tmit, webpage, tpid, url, params, user));
                            }
                            
                            sb_ret.append("</li>");
                        }else {
                            if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                                style=" style=\"color:#FF6600; font-weight:bold\" ";
                            }else {
                                style="";
                            }
                            
                            sb_ret.append("<li>");
                            sb_ret.append("<img src=\""+webcontext+"/trans.gif\" />");
                            sb_ret.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\""+style+">");
                            sb_ret.append("<img src=\""+webcontext+"/icon-section.gif\" />");
                            sb_ret.append(webpage.getDisplayName());
                            sb_ret.append("</a>");
                            sb_ret.append("</li>");
                        }
                    }                    
                }                
                sb_ret.append("</ul>");
            }
            sb_ret.append("</li>");
            sb_ret.append("</ul>");
            sb_ret.append("</div>");
            

        }
        catch(Exception e) {
            log.error("Error on method WebSiteSectionTree.render()", e);
        }
        return sb_ret.toString();
    }

    protected String getChild(HttpServletRequest request,WebSite tmit, WebPage pageroot, WebPage tpid, String url, StringBuilder params, User user) {
        StringBuilder sb_ret = new StringBuilder("<ul class=\"treeres\">");
        String style;
        boolean toggleopen;
        
                
        Iterator<WebPage> childs=pageroot.listChilds();
        while(childs.hasNext()) {
            WebPage webpage = childs.next();
            if(webpage.getId()!=null) {
                                
                toggleopen = Boolean.parseBoolean(request.getParameter(webpage.getId())==null?"false":request.getParameter(webpage.getId()).equals("1")?"true":"false");
                
                if(webpage.listChilds().hasNext()) {
                    sb_ret.append("<li>");
                    if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                        style=" style=\"color:#FF6600; font-weight:bold\" ";
                        if(toggleopen) {
                            params.append("&"+webpage.getId()+"=0");
                            sb_ret.append("<img src=\""+webcontext+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\" />");
                            toggleopen = false;
                        }else {
                            params.append("&"+webpage.getId()+"=1");
                            sb_ret.append("<img src=\""+webcontext+"/minus.gif\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\" />");
                            toggleopen = true;
                        }
                    }else {
                        style="";
                        if(toggleopen) {
                            params.append("&"+webpage.getId()+"=1");
                            sb_ret.append("<img src=\""+webcontext+"/minus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\" />");
                        }else {
                            params.append("&"+webpage.getId()+"=0");
                            sb_ret.append("<img src=\""+webcontext+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\" />");
                        }                        
                    }                            

                    sb_ret.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\""+style+">");
                    sb_ret.append("<img src=\""+webcontext+"/icon-section.gif\" />");
                    sb_ret.append(webpage.getDisplayName());
                    sb_ret.append("</a>");

                    if(toggleopen) {
                        sb_ret.append(getChild(request, tmit, webpage, tpid, url, params, user));
                    }

                    sb_ret.append("</li>");
                }else {
                    if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())){
                        style=" style=\"color:#FF6600; font-weight:bold\" ";
                    }else {
                        style="";
                    }
                    
                    sb_ret.append("<li>");
                    sb_ret.append("<img src=\""+webcontext+"/trans.gif\" />");
                    sb_ret.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\""+style+">");
                    sb_ret.append("<img src=\""+webcontext+"/icon-section.gif\" />");
                    sb_ret.append(webpage.getDisplayName());
                    sb_ret.append("</a>");
                    sb_ret.append("</li>");
                }
            }
        }
        
        sb_ret.append("</ul>");
        return sb_ret.toString();
    }
}
