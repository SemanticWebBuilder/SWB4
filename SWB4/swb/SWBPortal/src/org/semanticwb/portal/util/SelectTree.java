
package org.semanticwb.portal.util;

import java.io.IOException;
import java.util.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.SWBResourceException;

public class SelectTree {
    private static Logger log = SWBUtils.getLogger(WebSiteSectionTree.class);
    protected static final String pathImages = SWBPlatform.getContextPath() + "/swbadmin/icons/";

    public SelectTree() {
    }
       
    public String renderXHTML(String site, HashMap request, String url) throws SWBResourceException, IOException {
        StringBuilder html = new StringBuilder();
        StringBuilder params = new StringBuilder("&site="+site);
        String whoOpen = "";        
        WebSite tm = null;

        try {
            WebPage tpsite=null, tpid=null;
            String style;
            
            if(request.containsKey("reptm") && request.get("reptm")!=null) {
                tm=SWBContext.getWebSite((String)request.get("reptm"));
                
                if(tm!=null) {                
                    tpsite=tm.getHomePage();

                    if(request.containsKey("reptp") && request.get("reptp")!=null && !((String)request.get("reptp")).trim().equals("")) {
                        if(tm.getWebPage((String)request.get("reptp"))!=null) {
                            tpid=tm.getWebPage((String)request.get("reptp"));
                        }
                    }
                }
            }
            
            WebSite tmit = SWBContext.getWebSite(site);            
            WebPage tmhome=tmit.getHomePage();
            
            boolean toggleopen = Boolean.parseBoolean(request.get(tmhome.getId())==null?"false":((String)request.get(tmhome.getId())).equals("1")?"true":"false");
           
            html.append("<div class=\"sitesectiontree\" id=\"tree_"+site+"\">");
            html.append("<ul class=\"treeres\">");
            html.append("<li>");
            html.append("<img src=\""+pathImages+"/icon_sitea.gif\" />");
            html.append(tmit.getId());
            html.append("<ul class=\"treeres\">");
            html.append("<li>");
            
            if(tpid!=null && tpid.getId().equalsIgnoreCase(tmhome.getId())) {
                style=" style=\"color:#FF6600; font-weight:bold\" ";                
                if(toggleopen) {
                    params.append("&"+tmhome.getId()+"=0");
                    html.append("<img src=\""+pathImages+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+site+"')\" />");
                    toggleopen = false;
                }else {
                    params.append("&"+tmhome.getId()+"=1");
                    html.append("<img src=\""+pathImages+"/minus.gif\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+site+"')\" />");
                    toggleopen = true;
                }
            }else {
                style="";
                if(toggleopen) {
                    params.append("&"+tmhome.getId()+"=1");
                    html.append("<img src=\""+pathImages+"/minus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+site+"')\" />");
                }else {
                    params.append("&"+tmhome.getId()+"=0");
                    html.append("<img src=\""+pathImages+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+site+"')\" />");
                }
            }
            
            html.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+whoOpen+params+"','tree_'+'"+site+"')\" "+style+">");
            html.append("<img src=\""+pathImages+"/icon_homea.gif\" />");
            html.append("<span>"+tmhome.getDisplayName()+"</span>");
            html.append("</a>");
            
            if(tpsite!=null && toggleopen) {                
                html.append(addChild(request, tmit, tmhome, tpid, url, params));
            }
            html.append("</li>");
            html.append("</ul>");
            html.append("</div>");
            

        }
        catch(Exception e) {
            log.error("Error on method WebSiteSectionTree.render()", e);
        }
        return html.toString();
    }

    protected String addChild(HashMap request, WebSite tmit, WebPage pageroot, WebPage tpid, String url, StringBuilder params) {
        String style;
        boolean toggleopen;
        
        StringBuilder html = new StringBuilder("<ul class=\"treeres\">");
        Iterator<WebPage> childs=pageroot.listChilds();
        while(childs.hasNext()) {
            WebPage webpage = childs.next();
            if(webpage.getId()!=null) {
                                
                toggleopen = Boolean.parseBoolean(request.get(webpage.getId())==null?"false":((String)request.get(webpage.getId())).equals("1")?"true":"false");
                
                if(webpage.listChilds().hasNext()) {
                    html.append("<li>");
                    if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                        style=" style=\"color:#FF6600; font-weight:bold\" ";
                        if(toggleopen) {
                            params.append("&"+webpage.getId()+"=0");
                            html.append("<img src=\""+pathImages+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\" />");
                            toggleopen = false;
                        }else {
                            params.append("&"+webpage.getId()+"=1");
                            html.append("<img src=\""+pathImages+"/minus.gif\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\" />");
                            toggleopen = true;
                        }
                    }else {
                        style="";
                        if(toggleopen) {
                            params.append("&"+webpage.getId()+"=1");
                            html.append("<img src=\""+pathImages+"/minus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\" />");
                        }else {
                            params.append("&"+webpage.getId()+"=0");
                            html.append("<img src=\""+pathImages+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\" />");
                        }                        
                    }                            

                    html.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\" "+style+">");
                    html.append("<img src=\""+pathImages+"/icon-section.gif\" />");
                    html.append("<span>"+webpage.getDisplayName()+"</span>");
                    html.append("</a>");

                    if(toggleopen) {
                        html.append(addChild(request, tmit, webpage, tpid, url, params));
                    }

                    html.append("</li>");
                }else {
                    if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())){
                        style=" style=\"color:#FF6600; font-weight:bold\" ";
                    }else {
                        style="";
                    }
                    
                    html.append("<li>");
                    html.append("<img src=\""+pathImages+"/trans.gif\" />");
                    html.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\" "+style+">");
                    html.append("<img src=\""+pathImages+"/icon-section.gif\" />");
                    html.append("<span>"+webpage.getDisplayName()+"</span>");
                    html.append("</a>");
                    html.append("</li>");
                }
            }
        }        
        html.append("</ul>");
        
        return html.toString();
    }

}
