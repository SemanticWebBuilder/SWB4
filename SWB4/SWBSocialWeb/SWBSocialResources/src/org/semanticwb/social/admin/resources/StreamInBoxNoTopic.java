/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBComparator;
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
import org.semanticwb.social.SentimentalLearningPhrase;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.Stream;
import org.semanticwb.social.VideoIn;
import org.semanticwb.social.util.SWBSocialUtil;

/**
 *
 * @author jorge.jimenez
 */
public class StreamInBoxNoTopic extends GenericResource {
    
     /** The log. */
    private Logger log = SWBUtils.getLogger(StreamInBoxNoTopic.class);
    /** The webpath. */
    String webpath = SWBPlatform.getContextPath();
    /** The distributor. */
    String distributor = SWBPlatform.getEnv("wb/distributor");
    /** The Mode_ action. */
    //String Mode_Action = "paction";
    String Mode_PFlowMsg="doPflowMsg";
    String Mode_PreView="preview";
    
    /**
     * Creates a new instance of SWBAWebPageContents.
     */
    public StreamInBoxNoTopic() {
    }
    
    public static final String Mode_REVAL = "rv";
    public static final String Mode_RECLASSBYTOPIC="reclassByTopic";
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if(Mode_REVAL.equals(mode)) {
            doRevalue(request, response, paramRequest);
        } else if(Mode_RECLASSBYTOPIC.equals(mode)) {
            doReClassifyByTopic(request, response, paramRequest);
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
        log.debug("doView(SWBAWebPageContents...)");
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

        Stream stream = (Stream)SemanticObject.getSemanticObject(id).getGenericInstance();    
        
        PrintWriter out = response.getWriter();
        
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
        
        
        SWBResourceURL urls = paramRequest.getRenderUrl();
        urls.setParameter("act", "");
        urls.setParameter("suri", id);
        
        
        out.println("<div class=\"swbform\">");
      
        
        out.println("<fieldset>");
        out.println("<table width=\"98%\" >");
        out.println("<thead>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("action"));
        out.println("</th>");
        
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("post"));
        out.println("</th>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("postType"));
        out.println("</th>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("network"));
        out.println("</th>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("created"));
        out.println("</th>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("sentiment"));
        out.println("</th>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("intensity"));
        out.println("</th>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("emoticon"));
        out.println("</th>");
        
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("replies"));
        out.println("</th>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("user"));
        out.println("</th>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("followers"));
        out.println("</th>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("friends"));
        out.println("</th>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("klout"));
        out.println("</th>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("place"));
        out.println("</th>");
        
        
        out.println("</thead>");
        out.println("<tbody>");
        
        
        Iterator<PostIn> itposts = PostIn.ClassMgr.listPostInByPostInStream(stream);
        
        Set<PostIn> setso = SWBComparator.sortByCreatedSet(itposts, false);
        
        itposts = null;
        int ps = 20;
        int l = setso.size();

        //System.out.println("num cont: "+l);

        int p = 0;
        String page = request.getParameter("page");
        if (page != null) {
            p = Integer.parseInt(page);
        }



        int x = 0;
        itposts = setso.iterator();
        while (itposts.hasNext()) 
        {
            PostIn postIn = itposts.next();
            if(postIn.getSocialTopic()!=null) continue; //Es decir, se listarían solo los que no tengan aun un SocialTopic asociado.
            
            if (x < p * ps) {
                x++;
                continue;
            }
            if (x == (p * ps + ps) || x == l) {
                break;
            }
            x++;
            
            
            out.println("<tr>");
            
            //Show Actions
            out.println("<td>");
        
            //Remove
            SWBResourceURL urlr = paramRequest.getActionUrl();
            urlr.setParameter("suri", id);
            urlr.setParameter("sval", postIn.getURI());
            urlr.setParameter("page", "" + p);
            urlr.setAction("remove");
            
            out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("remove") + "\" onclick=\"if(confirm('" + paramRequest.getLocaleString("confirm_remove") + " " + 
                    SWBUtils.TEXT.scape4Script(postIn.getMsg_Text()) + "?'))" + "{ submitUrl('" + urlr + "',this); } else { return false;}\">"
                    + "<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("remove") + "\"></a>");
            
            
            //ReClasifyByTpic
            SWBResourceURL urlreClasifybyTopic=paramRequest.getRenderUrl().setMode(Mode_RECLASSBYTOPIC).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());  
            out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("reclasifyByTopic") + "\" onclick=\"showDialog('" + urlreClasifybyTopic + "','" + 
                    paramRequest.getLocaleString("reclasifyByTopic") + "'); return false;\">ReT</a>");
            
            /*
            //Respond
            SWBResourceURL urlresponse=paramRequest.getRenderUrl().setMode(Mode_RESPONSE).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());  
            out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("respond") + "\" onclick=\"showDialog('" + urlresponse + "','" + paramRequest.getLocaleString("respond") 
                    + "'); return false;\">R</a>");
                    * */
            
            out.println("</td>");
            
            //Show Message
            out.println("<td>");
            out.println(postIn.getMsg_Text());
            out.println("</td>");
            
            
            //Show PostType
            out.println("<td>");
            out.println(postIn instanceof MessageIn?paramRequest.getLocaleString("message"):postIn instanceof PhotoIn?paramRequest.getLocaleString("photo"):postIn instanceof VideoIn?paramRequest.getLocaleString("video"):"---");
            out.println("</td>");
            
            //SocialNetwork
            out.println("<td>");
            out.println(postIn.getPostInSocialNetwork().getDisplayTitle(lang));
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
                out.println("<img src=\""+SWBPortal.getContextPath()+"/swbadmin/images/feelpos.png"+"\">");
            }else if(postIn.getPostSentimentalType()==2)
            {
                out.println("<img src=\""+SWBPortal.getContextPath()+"/swbadmin/images/feelneg.png"+"\">");
            }else 
            {
                out.println("XXX");
            }
            out.println("</td>");
            
            //Intensity
            out.println("<td>");
            out.println(postIn.getPostIntesityType()==0?paramRequest.getLocaleString("low"):postIn.getPostSentimentalType()==1?paramRequest.getLocaleString("medium"):postIn.getPostSentimentalType()==2?paramRequest.getLocaleString("high"):"---");
            out.println("</td>");
            
            //Emoticon
            out.println("<td align=\"center\">");
            if(postIn.getPostSentimentalEmoticonType()==1)
            {
                out.println("<img src=\""+SWBPortal.getContextPath()+"/swbadmin/images/emopos.png"+"/>");
            }else if(postIn.getPostSentimentalEmoticonType()==2)
            {
                out.println("<img src=\""+SWBPortal.getContextPath()+"/swbadmin/images/emoneg.png"+"/>");
            }else if(postIn.getPostSentimentalEmoticonType()==0)
            {
                out.println("---");
            }else{
                out.println("XXX");
            }
            out.println("</td>");
            
            
            //Replicas
            out.println("<td align=\"center\">");
            out.println(postIn.getPostRetweets());
            out.println("</td>");
            
            
            //User
            out.println("<td>");
            out.println(postIn.getPostInSocialNetworkUser().getSnu_name());
            out.println("</td>");
            
            //Followers
            out.println("<td align=\"center\">");
            out.println(postIn.getPostInSocialNetworkUser().getFollowers());
            out.println("</td>");
            
            //Friends
            out.println("<td align=\"center\">");
            out.println(postIn.getPostInSocialNetworkUser().getFriends());
            out.println("</td>");
            
             //Klout
            out.println("<td align=\"center\">");
            out.println(postIn.getPostInSocialNetworkUser().getSnu_klout());
            out.println("</td>");
            
            //Place
            out.println("<td>");
            out.println(postIn.getPostPlace() == null ? "---" : postIn.getPostPlace());
            out.println("</td>");
            
            
          out.println("</tr>");
        }
        out.println("</tbody>");  
        out.println("</table>");  
        out.println("</fieldset>");
        
        
        System.out.println("J-P:"+p);
        System.out.println("J-X:"+x);
        
        if (p > 0 || x < l) //Requiere paginacion
        {
            out.println("<fieldset>");
            out.println("<center>");

            //int pages=(int)(l+ps/2)/ps;

            int pages = (int) (l / ps);
            if ((l % ps) > 0) {
                pages++;
            }

            for (int z = 0; z < pages; z++) {
                SWBResourceURL urlNew = paramRequest.getRenderUrl();
                urlNew.setParameter("suri", id);
                urlNew.setParameter("page", "" + z);
                System.out.println("J2-P:"+p);
                System.out.println("J2-X:"+x);
                if(p==0 && z==0) continue;
                if (z != p) {
                    out.println("<a href=\"#\" onclick=\"submitUrl('" + urlNew + "',this); return false;\">" + (z + 1) + "</a> ");
                } else {
                    System.out.println("J3-P:"+p);
                    System.out.println("J3-X:"+x);
                    out.println((z + 1) + " ");
                }
            }
            out.println("</center>");
            out.println("</fieldset>");
        }
        
        
        out.println("</div>");  
     
    }
    
    /*
     * Reclasifica por SocialTopic un PostIn
     */
    private void doReClassifyByTopic(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
    {
        final String path = SWBPlatform.getContextPath() + "/work/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/classifybyTopic.jsp";
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
        final String myPath = SWBPlatform.getContextPath() +"/work/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/revalue.jsp";
        if (request != null) {
            RequestDispatcher dis = request.getRequestDispatcher(myPath);
            if(dis != null) {
                try {
                    request.setAttribute("paramRequest", paramRequest);
                    dis.include(request, response);
                } catch (Exception e) {
                    log.error(e);
                    e.printStackTrace(System.out);
                }
            }
        }
    }
    
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        final Resource base = getResourceBase();
        String action = response.getAction();
        System.out.println("Entra a InBox_processAction-1:"+action);
        if(action.equals("changeSocialTopic"))
        {
            if(request.getParameter("postUri")!=null && request.getParameter("newSocialTopic")!=null)
            {
                System.out.println("processAction/1");
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
                response.setRenderParameter("dialog","close");
                response.setRenderParameter("reloadTap","1");
                response.setRenderParameter("suri", stOld.getURI());
            }
        }else if (SWBResourceURL.Action_EDIT.equals(action)) 
            {
                WebSite wsite = base.getWebSite();
                try {
                    String[] phrases = request.getParameter("fw").split(";");
                    int nv = Integer.parseInt(request.getParameter("nv"));
                    int dpth = Integer.parseInt(request.getParameter("dpth"));
                    SentimentalLearningPhrase slp;
                    for (String phrase : phrases) {
                        phrase = phrase.toLowerCase().trim();
                        slp = SentimentalLearningPhrase.getSentimentalLearningPhrasebyPhrase(phrase, wsite);
                        if (slp == null) {
                            phrase = SWBSocialUtil.Classifier.normalizer(phrase).getNormalizedPhrase();
                            phrase = SWBSocialUtil.Classifier.getRootWord(phrase);
                            phrase = SWBSocialUtil.Classifier.phonematize(phrase);
                            slp = SentimentalLearningPhrase.ClassMgr.createSentimentalLearningPhrase(wsite);
                            slp.setPhrase(phrase);
                            slp.setSentimentType(nv);
                            slp.setIntensityType(dpth);
                        } else {
                            slp.setSentimentType(nv);
                            slp.setIntensityType(dpth);
                        }
                    }
                    response.setRenderParameter("alertmsg", "Revaluación correcta");
                } catch (Exception e) {
                    response.setRenderParameter("alertmsg", "Inténtalo de nuevo");
                    log.error(e);
                }
            }
         
    }
    
}