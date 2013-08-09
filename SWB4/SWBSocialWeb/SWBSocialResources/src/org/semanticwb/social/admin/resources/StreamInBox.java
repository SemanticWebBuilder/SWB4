/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.PostOut;
import org.semanticwb.social.SentimentalLearningPhrase;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialNetworkUser;
import org.semanticwb.social.SocialPFlow;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.Stream;
import org.semanticwb.social.VideoIn;
import org.semanticwb.social.util.SWBSocialComparator;
import org.semanticwb.social.util.SWBSocialUtil;

/**
 *
 * @author jorge.jimenez
 */
public class StreamInBox extends GenericResource {
    
     /** The log. */
    private Logger log = SWBUtils.getLogger(StreamInBox.class);
    /** The webpath. */
    String webpath = SWBPlatform.getContextPath();
    /** The distributor. */
    String distributor = SWBPlatform.getEnv("wb/distributor");
    /** The Mode_ action. */
    //String Mode_Action = "paction";
    String Mode_PFlowMsg="doPflowMsg";
    String Mode_PreView="preview";
    String Mode_showTags="showTags";
    
    /**
     * Creates a new instance of SWBAWebPageContents.
     */
    public StreamInBox() {
    }
    
    public static final String Mode_REVAL = "rv";
    public static final String Mode_PREVIEW = "preview";
    public static final String Mode_RECLASSBYTOPIC="reclassByTopic";
    public static final String Mode_RECLASSBYSENTIMENT="revalue";
    public static final String Mode_RESPONSE="response";
    public static final String Mode_ShowUsrHistory="showUsrHistory";
    public static final String Mode_RESPONSES="responses";
    public static final String Mode_SHOWPOSTOUT="showPostOut";
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if(Mode_REVAL.equals(mode)) {
            doRevalue(request, response, paramRequest);
        }if(Mode_PREVIEW.equals(mode)) {
            doPreview(request, response, paramRequest);
        }else if(Mode_ShowUsrHistory.equals(mode)){
             doShowUserHistory(request, response, paramRequest);
        }else if(Mode_RESPONSE.equals(mode)){
            doResponse(request, response, paramRequest);
        }else if (paramRequest.getMode().equals("post")) {
            doCreatePost(request, response, paramRequest);
        }else if(Mode_RECLASSBYTOPIC.equals(mode)) {
            doReClassifyByTopic(request, response, paramRequest);
        }else if(Mode_RECLASSBYSENTIMENT.equals(mode)){
            doRevalue(request, response, paramRequest);
        }else if(Mode_showTags.equals(mode)){
            doShowTags(request, response, paramRequest);
        }else if(Mode_RESPONSES.equals(mode)){
            doShowResponses(request, response, paramRequest);
        }else if(Mode_SHOWPOSTOUT.equals(mode)){ 
            doShowPostOut(request, response, paramRequest);
        }else{
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * User view of the resource, this call to a doEdit() mode.
     *
     * @param request , this holds the parameters
     * @param response , an answer to the user request
     * @param paramRequest , a list of objects like user, webpage, Resource, ...
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        doEdit(request, response, paramRequest);
    }

    /**
     * User edit view of the resource, this show a list of contents related to a webpage, user can add, remove, activate, deactivate contents.
     *
     * @param request , this holds the parameters
     * @param response , an answer to the user request
     * @param paramRequest , a list of objects like user, webpage, Resource, ...
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        String lang=paramRequest.getUser().getLanguage(); 
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doEdit()");
        
        String id = request.getParameter("suri");
        if(id==null) return;
        
        //System.out.println("Stream-id/doEdit:"+id);

        Stream stream = (Stream)SemanticObject.getSemanticObject(id).getGenericInstance();    
        WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        
        PrintWriter out = response.getWriter();
        
        
        if(request.getParameter("leyendReconfirm")!=null)
        {
            
            //Remove
            SWBResourceURL urlrConfirm = paramRequest.getActionUrl();
            urlrConfirm.setParameter("suri", id);
            urlrConfirm.setParameter("sval", request.getParameter("postUri"));
            urlrConfirm.setAction("removeConfirm");
            
            out.println("<form name=\"formStreamInBox_removePostIn\" id=\"formStreamInBox_removePostIn\" class=\"swbform\" method=\"post\" action=\""+urlrConfirm+"\" onsubmit=\"alert('entra a onSubmit k...');submitForm('formStreamInBox_removePostIn');return false;\">");
            out.println("</form>");
            
            out.println("<script type=\"javascript\">");
            out.println("   if(confirm('" + request.getParameter("leyendReconfirm") + ","+ paramRequest.getLocaleString("deleteAnyWay")+ "?')) { submitForm('formStreamInBox_removePostIn');}");
            out.println("</script>");
        }
        
        if (request.getParameter("dialog") != null && request.getParameter("dialog").equals("close")) {
            out.println("<script type=\"javascript\">");
            out.println(" hideDialog(); ");
            if(request.getParameter("statusMsg")!=null) {
                out.println("   showStatus('" + request.getParameter("statusMsg") + "');");
            }
            if(request.getParameter("reloadTap")!=null)
            {
                out.println(" reloadTab('" + id + "'); ");
            }
            out.println("</script>");
    
        }
        
        out.println("<style type=\"text/css\">");
        out.println(".spanFormat");
        out.println("{");
        out.println("  text-align: right;");
        out.println("  display: table-cell;");
        out.println("  min-width: 10px;");
        out.println("  padding-right: 10px;");
        out.println("}");
        out.println("</style>");
        
        String searchWord = request.getParameter("search");
        String swbSocialUser=request.getParameter("swbSocialUser");
        
        String page = request.getParameter("page");
        if(page==null && request.getParameter("noSaveSess")==null)  //Cuando venga page!=null no se mete nada a session, ni tampoco se manda return.
        {
            HttpSession session = request.getSession(true);
            if (null == searchWord) {
                searchWord = "";
                if(session.getAttribute(id + this.getClass().getName() +"search") != null){
                    searchWord = (String)session.getAttribute(id + this.getClass().getName() +"search");
                    session.removeAttribute(id + this.getClass().getName() +"search");
                }
            }else{//Add word to session var
                session.setAttribute(id + this.getClass().getName() +"search", searchWord);//Save the word in the session var
                return;
            }
            if (null == swbSocialUser) {
                if(session.getAttribute(id + this.getClass().getName() +"swbSocialUser")!=null)
                {
                    swbSocialUser = (String)session.getAttribute(id + this.getClass().getName() +"swbSocialUser");
                    session.removeAttribute(id + this.getClass().getName() +"swbSocialUser");
            }
            }else{//Add word to session var
                session.setAttribute(id + this.getClass().getName() +"swbSocialUser", swbSocialUser);//Save the word in the session var
                return;
            }
        }
        
        SWBResourceURL urls = paramRequest.getRenderUrl();
        urls.setParameter("act", "");
        urls.setParameter("suri", id);
        
        SWBResourceURL tagUrl = paramRequest.getRenderUrl();
        tagUrl.setParameter("suri", id);
        tagUrl.setMode(Mode_showTags);
        
        out.println("<div class=\"swbform\">");
      
        out.println("<fieldset>");
        out.println("<span  class=\"spanFormat\">");        
        out.println("<form id=\"" + id + "/fsearchwp\" name=\"" + id + "/fsearchwp\" method=\"post\" action=\"" + urls + "\" onsubmit=\"submitForm('" + id + "/fsearchwp');return false;\">");
        out.println("<div align=\"right\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
        out.println("<input type=\"hidden\" name=\"noSaveSess\" value=\"1\">");
        out.println("<label for=\"" + id + "_searchwp\">" + paramRequest.getLocaleString("searchPost") + ": </label><input type=\"text\" name=\"search\" id=\"" + id + "_searchwp\" value=\"" + searchWord + "\">");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("btnSearch") + "</button>"); //
        out.println("</div>");
        out.println("</form>");
        out.println("</span>");
        out.println("<span  class=\"spanFormat\">");
        out.println("<button dojoType='dijit.form.Button'  onclick=\"showDialog('" + tagUrl + "','" + paramRequest.getLocaleString("tagLabel") + "'); return false;\">" + paramRequest.getLocaleString("btnCloud") + "</button>");
        out.println("</span>");
        out.println("</fieldset>");
        
        
        out.println("<fieldset>");
        
        out.println("<table width=\"98%\" >");
        out.println("<thead>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("action"));
        out.println("</th>");
        
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("post"));
        out.println("</th>");
        
        SWBResourceURL urlOderby = paramRequest.getRenderUrl();
        urlOderby.setParameter("act", "");
        urlOderby.setParameter("suri", id);
        
        urlOderby.setParameter("orderBy", "PostTypeUp");
        out.println("<th>");
        out.println("<table><tr><td>");
        out.print(paramRequest.getLocaleString("postType")); 
        out.print("</td><td>");
        out.println("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_down.png\" height=\"16\"/></a>");
        urlOderby.setParameter("orderBy", "PostTypeDown"); 
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_up.png\" height=\"16\"/></a>");
        out.print("</td></tr></table>");
        //out.println("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getWebWorkPath()+"models/"+SWBContext.getAdminWebSite().getId()+"/css/images/ARW01UP.png"+"\"></a>");
        //out.println("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getWebWorkPath()+"models/"+SWBContext.getAdminWebSite().getId()+"/css/images/ARROW9B.png"+"\"></a>");
        out.println("</th>");
        
        urlOderby.setParameter("orderBy", "networkUp");
        out.println("<th>");
        out.println("<table><tr><td>");
        out.println(paramRequest.getLocaleString("network"));
        out.print("</td><td>");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_down.png\" height=\"16\"/></a>");
        urlOderby.setParameter("orderBy", "networkDown");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_up.png\" height=\"16\"/></a>");
        out.print("</td></tr></table>");
        out.println("</th>");
        
        urlOderby.setParameter("orderBy", "topicUp");
        out.println("<th>");
        out.println("<table><tr><td>");
        out.println(paramRequest.getLocaleString("topic"));
        out.print("</td><td>");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_down.png\" height=\"16\"/></a>");
        urlOderby.setParameter("orderBy", "topicDown");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_up.png\" height=\"16\"/></a>");
        out.print("</td></tr></table>");
        out.println("</th>");
        
        urlOderby.setParameter("orderBy", "cretedUp");
        out.println("<th>");
        out.println("<table><tr><td>");
        out.println(paramRequest.getLocaleString("created"));
        out.print("</td><td>");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_down.png\" height=\"16\"/></a>");
        urlOderby.setParameter("orderBy", "cretedDown");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_up.png\" height=\"16\"/></a>");
        out.print("</td></tr></table>");
        out.println("</th>");
        
        urlOderby.setParameter("orderBy", "sentimentUp");
        out.println("<th>");
        out.println("<table><tr><td>");
        out.println(paramRequest.getLocaleString("sentiment"));
        out.print("</td><td>");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_down.png\" height=\"16\"/></a>");
        urlOderby.setParameter("orderBy", "sentimentDown");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_up.png\" height=\"16\"/></a>");
        out.print("</td></tr></table>");
        out.println("</th>");
        
        urlOderby.setParameter("orderBy", "intensityUp");
        out.println("<th>");
        out.println("<table><tr><td>");
        out.println(paramRequest.getLocaleString("intensity"));
        out.print("</td><td>");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_down.png\" height=\"16\"/></a>");
        urlOderby.setParameter("orderBy", "intensityDown");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_up.png\" height=\"16\"/></a>");
        out.print("</td></tr></table>");
        out.println("</th>");
        
        urlOderby.setParameter("orderBy", "emoticonUp");
        out.println("<th>");
        out.println("<table><tr><td>");
        out.println(paramRequest.getLocaleString("emoticon"));
        out.print("</td><td>");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_down.png\" height=\"16\"/></a>");
        urlOderby.setParameter("orderBy", "emoticonDown");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_up.png\" height=\"16\"/></a>");
        out.print("</td></tr></table>");
        out.println("</th>");
        
        
        urlOderby.setParameter("orderBy", "repliesUp");
        out.println("<th>");
        out.println("<table><tr><td>");
        out.println(paramRequest.getLocaleString("replies"));
        out.print("</td><td>");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_down.png\" height=\"16\"/></a>");
        urlOderby.setParameter("orderBy", "repliesDown");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_up.png\" height=\"16\"/></a>");
        out.print("</td></tr></table>");
        out.println("</th>");
       
        urlOderby.setParameter("orderBy", "userUp");
        out.println("<th>");
        out.println("<table width=\"1\"><tr><td>");
        out.println(paramRequest.getLocaleString("user"));
        out.print("</td><td>");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_down.png\" height=\"16\"/></a>");
        urlOderby.setParameter("orderBy", "userDown");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_up.png\" height=\"16\"/></a>");
        out.print("</td></tr></table>");
        out.println("</th>");
        
        urlOderby.setParameter("orderBy", "followersUp");
        out.println("<th>");
        out.println("<table><tr><td>");
        out.println(paramRequest.getLocaleString("followers"));
        out.print("</td><td>");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_down.png\" height=\"16\"/></a>");
        urlOderby.setParameter("orderBy", "followersDown");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_up.png\" height=\"16\"/></a>");
        out.print("</td></tr></table>");
        out.println("</th>");
        
        urlOderby.setParameter("orderBy", "friendsUp");
        out.println("<th>");
        out.println("<table><tr><td>");
        out.println(paramRequest.getLocaleString("friends"));
        out.print("</td><td>");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_down.png\" height=\"16\"/></a>");
        urlOderby.setParameter("orderBy", "friendsDown");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_up.png\" height=\"16\"/></a>");
        out.print("</td></tr></table>");
        out.println("</th>");
        
        urlOderby.setParameter("orderBy", "kloutUp");
        out.println("<th>");
        out.println("<table><tr><td>");
        out.println(paramRequest.getLocaleString("klout"));
        out.print("</td><td>");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_down.png\" height=\"16\"/></a>");
        urlOderby.setParameter("orderBy", "kloutDown");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_up.png\" height=\"16\"/></a>");
        out.print("</td></tr></table>");
        out.println("</th>");
        
        urlOderby.setParameter("orderBy", "placeUp");
        out.println("<th>");
        out.println("<table><tr><td>");
        out.println(paramRequest.getLocaleString("place"));
        out.print("</td><td>");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_down.png\" height=\"16\"/></a>");
        urlOderby.setParameter("orderBy", "placeDown");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_up.png\" height=\"16\"/></a>");
        out.print("</td></tr></table>");
        out.println("</th>");
        
        urlOderby.setParameter("orderBy", "prioritaryUp");
        out.println("<th>");
        out.println("<table><tr><td>");
        out.println(paramRequest.getLocaleString("prioritary"));
        out.print("</td><td>");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_down.png\" height=\"16\"/></a>");
        urlOderby.setParameter("orderBy", "prioritaryDown");
        out.print("<a href=\"#\"  onclick=\"submitUrl('" + urlOderby + "',this); return false;\"><img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/arrow_up.png\" height=\"16\"/></a>");
        out.print("</td></tr></table>");
        out.println("</th>");
        
        
        out.println("</thead>");
        out.println("<tbody>");
        
        
        Iterator<PostIn> itposts = null;
        
        //Filtros
        ArrayList<PostIn> aListFilter=new ArrayList();
        if(swbSocialUser!=null)
        {
            SocialNetworkUser socialNetUser=SocialNetworkUser.ClassMgr.getSocialNetworkUser(swbSocialUser, wsite);
            itposts=socialNetUser.listPostInInvs();
        }else
        {
            itposts=PostIn.ClassMgr.listPostInByPostInStream(stream);
            if(searchWord!=null)
            {
                while(itposts.hasNext())
                {
                    PostIn postIn=itposts.next();
                    if(postIn.getTags()!=null && postIn.getTags().toLowerCase().indexOf(searchWord.toLowerCase())>-1)
                    {
                        aListFilter.add(postIn);
                    }else if(postIn.getMsg_Text()!=null && postIn.getMsg_Text().toLowerCase().indexOf(searchWord.toLowerCase())>-1)
                    {
                        aListFilter.add(postIn);
                    }
                }
            }
        }
        
        
        //Termina Filtros
        
        if(aListFilter.size()>0) 
        {
            itposts=aListFilter.iterator();
        }
         
        //Ordenamientos
        //System.out.println("orderBy k Llega:"+request.getParameter("orderBy"));
        Set<PostIn> setso=null;
        if(request.getParameter("orderBy")!=null)
        {
            if(request.getParameter("orderBy").equals("PostTypeUp"))
            {
                setso = SWBSocialComparator.sortByPostType(itposts, true);
            }if(request.getParameter("orderBy").equals("PostTypeDown"))
            {
                setso = SWBSocialComparator.sortByPostType(itposts, false);
            }else if(request.getParameter("orderBy").equals("networkUp"))
            {
                setso = SWBSocialComparator.sortByNetwork(itposts, true);
            }else if(request.getParameter("orderBy").equals("networkDown"))
            {
                setso = SWBSocialComparator.sortByNetwork(itposts, false);
            }else if(request.getParameter("orderBy").equals("topicUp"))
            {
                setso = SWBSocialComparator.sortByTopic(itposts, true);
            }else if(request.getParameter("orderBy").equals("topicDown"))
            {
                setso = SWBSocialComparator.sortByTopic(itposts, false);
            }else if(request.getParameter("orderBy").equals("cretedUp"))
            {
                setso = SWBComparator.sortByCreatedSet(itposts,true);
            }else if(request.getParameter("orderBy").equals("cretedDown"))
            {
                setso = SWBComparator.sortByCreatedSet(itposts,false);
            }else if(request.getParameter("orderBy").equals("sentimentUp"))
            {
                setso = SWBSocialComparator.sortBySentiment(itposts, false);
            }else if(request.getParameter("orderBy").equals("sentimentDown"))
            {
                setso = SWBSocialComparator.sortBySentiment(itposts, true);
            }else if(request.getParameter("orderBy").equals("intensityUp"))
            {
                setso = SWBSocialComparator.sortByIntensity(itposts, true);
            }else if(request.getParameter("orderBy").equals("intensityDown"))
            {
                setso = SWBSocialComparator.sortByIntensity(itposts, false);
            }else if(request.getParameter("orderBy").equals("emoticonUp"))
            {
                setso = SWBSocialComparator.sortByEmoticon(itposts, true);
            }else if(request.getParameter("orderBy").equals("emoticonDown"))
            {
                setso = SWBSocialComparator.sortByEmoticon(itposts, false);
            }else if(request.getParameter("orderBy").equals("userUp"))
            {
                setso = SWBSocialComparator.sortByUser(itposts, true);
            }else if(request.getParameter("orderBy").equals("userDown"))
            {
                setso = SWBSocialComparator.sortByUser(itposts, false);
            }else if(request.getParameter("orderBy").equals("followersUp"))
            {
                setso = SWBSocialComparator.sortByFollowers(itposts, true);
            }else if(request.getParameter("orderBy").equals("followersDown"))
            {
                setso = SWBSocialComparator.sortByFollowers(itposts, false);
            }else if(request.getParameter("orderBy").equals("repliesUp"))
            {
                setso = SWBSocialComparator.sortByReplies(itposts, true);
            }else if(request.getParameter("orderBy").equals("repliesDown"))
            {
                setso = SWBSocialComparator.sortByReplies(itposts, false);
            }else if(request.getParameter("orderBy").equals("friendsUp"))
            {
                setso = SWBSocialComparator.sortByFriends(itposts, true);
            }else if(request.getParameter("orderBy").equals("friendsDown"))
            {
                setso = SWBSocialComparator.sortByFriends(itposts, false);
            }else if(request.getParameter("orderBy").equals("kloutUp"))
            {
                setso = SWBSocialComparator.sortByKlout(itposts, true);
            }else if(request.getParameter("orderBy").equals("kloutDown"))
            {
                setso = SWBSocialComparator.sortByKlout(itposts, false);
            }else if(request.getParameter("orderBy").equals("placeUp"))
            {
                setso = SWBSocialComparator.sortByPlace(itposts, true);
            }else if(request.getParameter("orderBy").equals("placeDown"))
            {
                setso = SWBSocialComparator.sortByPlace(itposts, false);
            }else if(request.getParameter("orderBy").equals("prioritaryUp"))
            {
                setso = SWBSocialComparator.sortByPrioritary(itposts, true);
            }else if(request.getParameter("orderBy").equals("prioritaryDown"))
            {
                setso = SWBSocialComparator.sortByPrioritary(itposts, false);
            }
            
        }else
        {
            setso = SWBComparator.sortByCreatedSet(itposts, false);
        }
        
        
        itposts = null;
       
        
        int recPerPage=20;//if(resBase.getItemsbyPage()>0) recPerPage=resBase.getItemsbyPage();            
        int nRec = 0;
        int nPage;
        try {
            nPage = Integer.parseInt(request.getParameter("page"));
        } catch (Exception ignored) {
             nPage = 1;
        }
        boolean paginate = false;
        
        
        itposts = setso.iterator();
        while (itposts.hasNext()) 
        {
            PostIn postIn = itposts.next();
            
             
             nRec++;
             if ((nRec > (nPage - 1) * recPerPage) && (nRec <= (nPage) * recPerPage)) 
             {
                paginate = true;  
            
                
                out.println("<tr>");

                //Show Actions
                out.println("<td>");

                //Remove
                SWBResourceURL urlr = paramRequest.getActionUrl();
                urlr.setParameter("suri", id);
                urlr.setParameter("sval", postIn.getURI());
                urlr.setParameter("page", "" + nPage);
                urlr.setAction("remove");

                out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("remove") + "\" onclick=\"if(confirm('" + paramRequest.getLocaleString("confirm_remove") + " " + 
                        SWBUtils.TEXT.scape4Script(postIn.getMsg_Text()) + "?'))" + "{ submitUrl('" + urlr + "',this); } else { return false;}\">"
                        + "<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("remove") + "\"></a>");

                //Preview
                /*
                SWBResourceURL urlpre = paramRequest.getRenderUrl();
                urlpre.setParameter("suri", id);
                urlpre.setParameter("page", "" + p);
                urlpre.setParameter("sval", postIn.getURI());
                urlpre.setParameter("preview", "true");
                out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("previewdocument") + "\" onclick=\"submitUrl('" + urlpre + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/preview.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("previewdocument") + "\"></a>");
                * */
                SWBResourceURL urlPrev=paramRequest.getRenderUrl().setMode(Mode_PREVIEW).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());  
                out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("previewdocument") + "\" onclick=\"showDialog('" + urlPrev + "','" + paramRequest.getLocaleString("previewdocument") 
                        + "'); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/preview.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("previewdocument") + "\"></a>");




                //ReClasifyByTpic
                SWBResourceURL urlreClasifybyTopic=paramRequest.getRenderUrl().setMode(Mode_RECLASSBYTOPIC).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());  
                out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("reclasifyByTopic") + "\" onclick=\"showDialog('" + urlreClasifybyTopic + "','" + 
                        paramRequest.getLocaleString("reclasifyByTopic") + "'); return false;\">RT</a>");


                //ReClasyfyBySentiment & Intensity
                SWBResourceURL urlrev=paramRequest.getRenderUrl().setMode(Mode_RECLASSBYSENTIMENT).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());  
                out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("reeval") + "\" onclick=\"showDialog('" + urlrev + "','" + paramRequest.getLocaleString("reeval") 
                        + "'); return false;\">RV</a>");

                //Respond
                if(postIn.getSocialTopic()!=null)
                {
                    SWBResourceURL urlresponse=paramRequest.getRenderUrl().setMode(Mode_RESPONSE).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());  
                    out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("respond") + "\" onclick=\"showDialog('" + urlresponse + "','" + paramRequest.getLocaleString("respond") 
                            + "'); return false;\">R</a>");
                }
                
                //Respuestas que posee un PostIn
                if(postIn.listpostOutResponseInvs().hasNext())
                {
                    SWBResourceURL urlresponses=paramRequest.getRenderUrl().setMode(Mode_RESPONSES).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());  
                    out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("answers") + "\" onclick=\"showDialog('" + urlresponses + "','" + paramRequest.getLocaleString("answers") 
                            + "'); return false;\">A</a>");
                }

                out.println("</td>");

                //Show Message
                out.println("<td>");
                if(postIn.getMsg_Text()!=null){
                    if(postIn.getMsg_Text().length()>200)
                    {
                        out.println(postIn.getMsg_Text().substring(0, 200));
                    }else{
                        out.println(postIn.getMsg_Text());
                    }
                }else if(postIn.getDescription()!=null){
                    if(postIn.getDescription().length()>200)
                    {
                        out.println(postIn.getDescription().substring(0, 200));
                    }else{
                        out.println(postIn.getDescription());
                    }
                }else if(postIn.getTags()!=null){
                    if(postIn.getTags().length()>200)
                    {
                        out.println(postIn.getTags().substring(0, 200));
                    }else{
                        out.println(postIn.getTags());
                    }
                }else{
                     out.println("---");
                }
                out.println("</td>");


                //Show PostType
                out.println("<td>");
                out.println(postIn instanceof MessageIn?paramRequest.getLocaleString("message"):postIn instanceof PhotoIn?paramRequest.getLocaleString("photo"):postIn instanceof VideoIn?paramRequest.getLocaleString("video"):"---");
                out.println("</td>");

                //SocialNetwork
                out.println("<td>");
                out.println(postIn.getPostInSocialNetwork().getDisplayTitle(lang));
                out.println("</td>");


                 //SocialTopic
                out.println("<td>");
                if(postIn.getSocialTopic()!=null)
                {
                    out.println(postIn.getSocialTopic().getDisplayTitle(lang));
                }else{
                    out.println("---");
                }
                out.println("</td>");

                  //Show Creation Time
                out.println("<td>");
                out.println(SWBUtils.TEXT.getTimeAgo(postIn.getCreated(), lang));
                out.println("</td>");

                //Sentiment
                out.println("<td align=\"center\">");
                if(postIn.getPostSentimentalType()==0)
                {
                    out.println("---");
                }else if(postIn.getPostSentimentalType()==1)
                {
                    out.println("<img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/feelpos.png"+"\">");
                }else if(postIn.getPostSentimentalType()==2)
                {
                    out.println("<img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/feelneg.png"+"\">");
                }
                out.println("</td>");

                //Intensity
                out.println("<td>");
                out.println(postIn.getPostIntesityType()==0?paramRequest.getLocaleString("low"):postIn.getPostIntesityType()==1?paramRequest.getLocaleString("medium"):postIn.getPostIntesityType()==2?paramRequest.getLocaleString("high"):"---");
                out.println("</td>");

                //Emoticon
                out.println("<td align=\"center\">");
                if(postIn.getPostSentimentalEmoticonType()==1)
                {
                    out.println("<img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/emopos.png"+"\"/>");
                }else if(postIn.getPostSentimentalEmoticonType()==2)
                {
                    out.println("<img src=\""+SWBPortal.getContextPath()+"/swbadmin/css/images/emoneg.png"+"\"/>");
                }else if(postIn.getPostSentimentalEmoticonType()==0)
                {
                    out.println("---");
                }
                out.println("</td>");


                //Replicas
                out.println("<td align=\"center\">");
                out.println(postIn.getPostShared());
                out.println("</td>");


                //Nunca debería un PostIn no tener un usuario, porque obvio las redes sociales simpre tienen un usuario que escribe los mensajes
                //User
                out.println("<td>");
                SWBResourceURL urlshowUsrHistory=paramRequest.getRenderUrl().setMode(Mode_ShowUsrHistory).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("suri", id);  
                out.println(postIn.getPostInSocialNetworkUser()!=null?"<a href=\"#\" onclick=\"showDialog('" + urlshowUsrHistory.setParameter("swbSocialUser", postIn.getPostInSocialNetworkUser().getURI()) + "','" + paramRequest.getLocaleString("userHistory") + "'); return false;\">"+postIn.getPostInSocialNetworkUser().getSnu_name()+"</a>":paramRequest.getLocaleString("withoutUser"));
                out.println("</td>");

                //Followers
                out.println("<td align=\"center\">");
                out.println(postIn.getPostInSocialNetworkUser()!=null?postIn.getPostInSocialNetworkUser().getFollowers():paramRequest.getLocaleString("withoutUser"));
                out.println("</td>");

                //Friends
                out.println("<td align=\"center\">");
                out.println(postIn.getPostInSocialNetworkUser()!=null?postIn.getPostInSocialNetworkUser().getFriends():paramRequest.getLocaleString("withoutUser"));
                out.println("</td>");

                 //Klout
                out.println("<td align=\"center\">");
                out.println(postIn.getPostInSocialNetworkUser()!=null?postIn.getPostInSocialNetworkUser().getSnu_klout():paramRequest.getLocaleString("withoutUser"));
                out.println("</td>");

                //Place
                out.println("<td>");
                out.println(postIn.getPostPlace() == null ? "---" : postIn.getPostPlace());
                out.println("</td>");

                //Priority
                out.println("<td align=\"center\">");
                out.println(postIn.isIsPrioritary() ? "SI" : "NO");
                out.println("</td>");


              out.println("</tr>");
            }
            
        }
        
         out.println("</tbody>");  
         out.println("</table>");  
         out.println("</fieldset>");
        
        if (paginate) 
        {
            out.println("<div id=\"pagination\">");
            out.println("<span>P&aacute;ginas:</span>");
            for (int countPage = 1; countPage < (Math.ceil((double) nRec / (double) recPerPage) + 1); countPage++) {
                SWBResourceURL pageURL = paramRequest.getRenderUrl();
                pageURL.setParameter("page", "" + (countPage));
                pageURL.setParameter("suri", id);
                pageURL.setParameter("search", (searchWord.trim().length() > 0 ? searchWord : ""));
                pageURL.setParameter("swbSocialUser", swbSocialUser);
                if(request.getParameter("orderBy")!=null) pageURL.setParameter("orderBy", request.getParameter("orderBy"));
                if (countPage != nPage) {
                    out.println("<a href=\"#\" onclick=\"submitUrl('" + pageURL + "',this); return false;\">"+countPage+"</a> ");
                } else {
                    out.println(countPage+ " ");
                }
            }
            out.println("</div>");
        }
        
        
        out.println("</div>");  
      
    }
    
    
     /**
     * Shows the preview of the content.
     *
     * @param request , this holds the parameters
     * @param response , an answer to the user request
     * @param paramRequest , a list of objects like user, webpage, Resource, ...
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPreview(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String postUri=request.getParameter("postUri");
        try {
            final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/showPostIn.jsp";
            if (request != null) {
                RequestDispatcher dis = request.getRequestDispatcher(path);
                if (dis != null) {
                    try {
                        SemanticObject semObject = SemanticObject.createSemanticObject(postUri);
                        request.setAttribute("postIn", semObject);
                        request.setAttribute("paramRequest", paramRequest);
                        dis.include(request, response);
                    } catch (Exception e) {
                        log.error(e);
                        e.printStackTrace(System.out);
                    }
                }
            }
            
        } catch (Exception e) {
            log.error("Error while getting content string ,id:" + postUri, e);
        }
    }
    
    
    /*
     * Reclasifica por SocialTopic un PostIn
     */
    private void doReClassifyByTopic(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
    {
        final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/classifybyTopic.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        if (dis != null) {
            try {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                request.setAttribute("postUri", semObject);
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
    
    /*
     * Reevalua un PostIn en cuanto a sentimiento e intensidad
     */
    
    public void doRevalue(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        final String myPath = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/reValue.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        if(dis != null) {
            try {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                request.setAttribute("postUri", semObject);
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
                e.printStackTrace(System.out);
            }
        }
    }
    
    private void doResponse(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
    {
        final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/postInResponse.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        if (dis != null) {
            try {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                request.setAttribute("postUri", semObject);
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
    
    private void doShowResponses(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
    {
        final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/showpostInResponses.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        if (dis != null) {
            try {
                System.out.println("doShowResponses/postUri:"+request.getParameter("postUri"));
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                System.out.println("doShowResponses/semObject:"+semObject);
                request.setAttribute("postUri", semObject);
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
            
    
    private void doShowPostOut(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
    {
        final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/showPostOut.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        if (dis != null) {
            try {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postOut"));
                request.setAttribute("postOut", semObject);
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
    
    private void doShowUserHistory(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
    {
        final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/userHistory.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        if (dis != null) {
            try {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("swbSocialUser"));
                request.setAttribute("swbSocialUser", semObject);
                request.setAttribute("suri", request.getParameter("suri"));
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
    
    
    public void doCreatePost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {        
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/typeOfContent.jsp");
        request.setAttribute("contentType", request.getParameter("valor"));
        request.setAttribute("wsite", request.getParameter("wsite"));
        request.setAttribute("objUri", request.getParameter("objUri"));
        request.setAttribute("paramRequest", paramRequest);
        
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al enviar los datos a typeOfContent.jsp " + ex.getMessage());
        }
    }
    
    
    public void doShowTags(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/tagCloud.jsp";        
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error("Error in doShowTags() for requestDispatcher" , e);
        }
    }
    
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        final Resource base = getResourceBase();
        String action = response.getAction();
        //System.out.println("Entra a StreamInBox/processAJ-1:"+action);
        if(action.equals("changeSocialTopic"))
        {
            if(request.getParameter("postUri")!=null && request.getParameter("newSocialTopic")!=null)
            {
                SemanticObject semObj=SemanticObject.getSemanticObject(request.getParameter("postUri"));
                PostIn post=(PostIn)semObj.createGenericInstance();
                Stream stOld=post.getPostInStream();
                if(request.getParameter("newSocialTopic").equals("none"))
                {
                    post.setSocialTopic(null);
                }else {
                    SemanticObject semObjSocialTopic=SemanticObject.getSemanticObject(request.getParameter("newSocialTopic"));
                    if(semObjSocialTopic!=null)
                    {
                        SocialTopic socialTopic=(SocialTopic)semObjSocialTopic.createGenericInstance();
                        post.setSocialTopic(socialTopic);
                    }
                }
                response.setMode(SWBActionResponse.Mode_EDIT);
                response.setRenderParameter("statusMsg", response.getLocaleString("socialTopicModified"));
                response.setRenderParameter("dialog","close");
                response.setRenderParameter("reloadTap","1");
                response.setRenderParameter("suri", stOld.getURI());
            }
        }else if (action.equals("reValue"))  
        {
            SemanticObject semObj=SemanticObject.getSemanticObject(request.getParameter("postUri"));
            PostIn post=(PostIn)semObj.createGenericInstance();
            Stream stOld=post.getPostInStream();
            try {
                String[] phrases = request.getParameter("fw").split(";");
                ///System.out.println("Entra a processA/reValue-2:"+phrases);
                int nv = Integer.parseInt(request.getParameter("nv"));
                //System.out.println("Entra a processA/reValue-3:"+nv);
                int dpth = Integer.parseInt(request.getParameter("dpth"));
                //System.out.println("Entra a processA/reValue-4:"+dpth);
                SentimentalLearningPhrase slp;
                for (String phrase : phrases) {
                    phrase = phrase.toLowerCase().trim();
                    //System.out.println("Entra a processA/reValue-4.1:"+phrase);
                    phrase = SWBSocialUtil.Classifier.normalizer(phrase).getNormalizedPhrase();
                    //System.out.println("Entra a processA/reValue-4.2--J:"+phrase);
                    phrase = SWBSocialUtil.Classifier.getRootPhrase(phrase);
                    //System.out.println("Entra a processA/reValue-4.3--J:"+phrase);
                    phrase = SWBSocialUtil.Classifier.phonematize(phrase);
                    //System.out.println("Entra a processA/reValue-4.4:"+phrase);
                    //Se Buscan y se crean las frases de aprendizaje del sistema en el sitio de Admin, para que el sistema aprenda independientemente del
                    //sitio, así también si se elimina un sitio, las palabras aprendidas por el sistema para el clasificador, aun siguen sirviendo para los demas
                    //sitios.
                    slp = SentimentalLearningPhrase.getSentimentalLearningPhrasebyPhrase(phrase, SWBContext.getAdminWebSite());    
                    if (slp == null) {
                        //System.out.println("Entra a processA/reValue-5:"+phrase);
                        phrase = SWBSocialUtil.Classifier.normalizer(phrase).getNormalizedPhrase();
                        phrase = SWBSocialUtil.Classifier.getRootPhrase(phrase);
                        phrase = SWBSocialUtil.Classifier.phonematize(phrase);
                        slp = SentimentalLearningPhrase.ClassMgr.createSentimentalLearningPhrase(SWBContext.getAdminWebSite());
                        //System.out.println("Entra a processA/reValue-5-1:"+phrase);
                        slp.setPhrase(phrase);
                        slp.setSentimentType(nv);
                        slp.setIntensityType(dpth);
                    } else {
                        //System.out.println("Entra a processA/reValue-6:"+slp);
                        slp.setSentimentType(nv);
                        slp.setIntensityType(dpth);
                    }
                }
                response.setMode(SWBActionResponse.Mode_EDIT);
                response.setRenderParameter("dialog","close");
                response.setRenderParameter("statusMsg", response.getLocaleString("phrasesAdded"));
                //response.setRenderParameter("reloadTap","1");
                response.setRenderParameter("suri", stOld.getURI());
            } catch (Exception e) {
                log.error(e);
            }
        } else if ("remove".equals(action)) //suri, prop
        {
            String sval = request.getParameter("sval");
            SemanticObject so = SemanticObject.createSemanticObject(sval);
            WebSite wsite=WebSite.ClassMgr.getWebSite(so.getModel().getName());
            PostIn postIn=(PostIn)so.getGenericInstance();
            
            if(PostOut.ClassMgr.listPostOutByPostInSource(postIn, wsite).hasNext())
            {
                response.setRenderParameter("leyendReconfirm", response.getLocaleString("postOutExist"));
                response.setRenderParameter("suri", request.getParameter("suri"));
                response.setRenderParameter("postUri", postIn.getURI());
            }else{
                so.remove();
                //response.setRenderParameter("dialog", "close");
                response.setRenderParameter("suri", request.getParameter("suri"));
                response.setRenderParameter("statusMsg", response.getLocaleString("postDeleted"));
            }
            response.setMode(SWBActionResponse.Mode_EDIT);
        }else if ("removeConfirm".equals(action)) 
        {
            String sval = request.getParameter("sval");
            SemanticObject so = SemanticObject.createSemanticObject(sval);
            so.remove();
            response.setMode(SWBActionResponse.Mode_EDIT);
            response.setRenderParameter("dialog","close");
            response.setRenderParameter("reloadTap",  request.getParameter("suri"));
            response.setRenderParameter("suri", request.getParameter("suri"));
            response.setRenderParameter("statusMsg", response.getLocaleString("postDeleted"));
        }else if (action.equals("postMessage") || action.equals("uploadPhoto") || action.equals("uploadVideo")) 
        {
                //System.out.println("Entra a Strean_processAction-2:"+request.getParameter("objUri"));
                if(request.getParameter("objUri")!=null)
                {
                    //System.out.println("Entra a InBox_processAction-3");
                    PostIn postIn=(PostIn)SemanticObject.getSemanticObject(request.getParameter("objUri")).createGenericInstance();
                    Stream stOld=postIn.getPostInStream(); 
                    SocialNetwork socialNet=(SocialNetwork)SemanticObject.getSemanticObject(request.getParameter("socialNetUri")).createGenericInstance();
                    ArrayList aSocialNets=new ArrayList();
                    aSocialNets.add(socialNet);

                    WebSite wsite=WebSite.ClassMgr.getWebSite(request.getParameter("wsite")); 

                    //En este momento en el siguiente código saco uno de los SocialPFlowRef que tiene el SocialTopic del PostIn que se esta contestando,
                    //Obviamente debo de quitar este código y el SocialPFlowRef debe llegar como parametro, que es de acuerdo al SocialPFlow que el usuario
                    //desee enviar el PostOut que realizó.
                    /**
                    SocialPFlow socialPFlow=null;
                    Iterator<SocialPFlowRef> itflowRefs=socialTopic.listPFlowRefs();
                    while(itflowRefs.hasNext())
                    {
                        SocialPFlowRef socialPflowRef=itflowRefs.next();
                        socialPFlow=socialPflowRef.getPflow();
                    }**/
                    String socialFlow=request.getParameter("socialFlow");
                    SocialPFlow socialPFlow=null;
                    if(socialFlow!=null && socialFlow.trim().length()>0)
                    {
                        socialPFlow=(SocialPFlow)SemanticObject.createSemanticObject(socialFlow).createGenericInstance();
                    }

                    //System.out.println("Entra a InBox_processAction-4");
                    SWBSocialUtil.PostOutUtil.sendNewPost(postIn, postIn.getSocialTopic(), socialPFlow, aSocialNets, wsite, request.getParameter("toPost"), request, response);
                    
                    //System.out.println("Entra a InBox_processAction-5");
                    response.setMode(SWBActionResponse.Mode_EDIT);
                    response.setRenderParameter("dialog","close");
                    response.setRenderParameter("statusMsg", response.getLocaleString("msgResponseCreated"));
                    response.setRenderParameter("suri", stOld.getURI());
                }
        }
         
    }
    
}
