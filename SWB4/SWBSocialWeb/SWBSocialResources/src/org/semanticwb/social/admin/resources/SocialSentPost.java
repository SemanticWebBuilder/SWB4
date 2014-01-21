/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Activeable;
import org.semanticwb.model.CalendarRef;
import org.semanticwb.model.PFlowInstance;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.Trashable;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.PFlowManager;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Facebook;
import org.semanticwb.social.FastCalendar;
import org.semanticwb.social.Message;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.Photo;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.PostOut;
import org.semanticwb.social.PostOutNet;
import org.semanticwb.social.PostOutPrivacyRelation;
import org.semanticwb.social.SocialFlow.SocialPFlowMgr;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialPFlow;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.SocialUserExtAttributes;
import org.semanticwb.social.Video;
import org.semanticwb.social.util.SWBSocialUtil;
import org.semanticwb.social.util.SocialLoader;
import org.semanticwb.social.SWBSocial;
import org.semanticwb.social.SocialNetworkUser;
import org.semanticwb.social.Twitter;
import org.semanticwb.social.VideoIn;
import org.semanticwb.social.Youtube;
import org.semanticwb.social.admin.resources.util.SWBSocialResUtil;

/**
 *
 * @author jorge.jimenez
 */
public class SocialSentPost extends GenericResource {

    /**
     * The log.
     */
    private static Logger log = SWBUtils.getLogger(SocialSentPost.class);
    /**
     * The webpath.
     */
    String webpath = SWBPlatform.getContextPath();
    /**
     * The distributor.
     */
    String distributor = SWBPlatform.getEnv("wb/distributor");
    /**
     * The Mode_ action.
     */
    String Mode_Action = "paction";
    String Mode_PFlowMsg = "doPflowMsg";
    String Mode_PreView = "preview";
    
    /**
     * Creates a new instance of SWBAWebPageContents.
     */
    public SocialSentPost() {
    }
    /**
     * The MOD e_ id request.
     */
    static String MODE_IdREQUEST = "FORMID";
    public static final String Mode_SOURCE = "source";
    public static final String Mode_EDITWindow = "editWindow";
    public static final String Mode_PREVIEW = "preview";
    public static final String Mode_ShowPostOutNets = "postOutLog";
    public static final String Mode_ShowPhotos = "showPhotos";
    private static final int RECPERPAGE = 20; //Number of records by Page, could be dynamic later
    private static final int PAGES2VIEW = 15; //Number of pages 2 display in pagination.
    private static final String Mode_ShowUsrHistory = "showUsrHistory";
    private static final String Mode_ShowMoreNets = "showMoreNets";
    private static final String Mode_ShowFastCalendar = "showFastCalendar";
    public static final String Mode_MsgComments="msgComments";
    public static final String Mode_RecoverComments="recoverComments";
    public static final String Mode_AllComments="allComments";
    public static final String Mode_CommentVideo="commentVideo";
    public static final String Mode_ReplyPost="replyPost";
    public static final String Mode_ShowFullProfile="fullProfile";
    public static final String Mode_PostSent="postSent";
    public static int DEFAULT_VIDEO_COMMENTS = 5;

    public static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if (Mode_SOURCE.equals(mode)) {
            doShowSource(request, response, paramRequest);
        } else if (Mode_PREVIEW.equals(mode)) {
            doPreview(request, response, paramRequest);
        } else if (Mode_EDITWindow.equals(mode)) {
            doEditPost(request, response, paramRequest);
        } else if (Mode_PFlowMsg.equals(mode)) {
            doPFlowMessage(request, response, paramRequest);
        } else if (Mode_Action.equals(mode)) {
            doAction(request, response, paramRequest);
        } else if (Mode_ShowPostOutNets.equals(mode)) {
            doShowPostOutLog(request, response, paramRequest);
        } else if (Mode_ShowUsrHistory.equals(mode)) {
            doShowUserHistory(request, response, paramRequest);
        } else if (Mode_ShowMoreNets.equals(mode)) {
            doShowMoreNets(request, response, paramRequest);
        } else if(Mode_ShowFastCalendar.equals(mode)){
            doShowFastCalendar(request, response, paramRequest);
        }else if (mode.equals("exportExcel")) {
            try {
                doGenerateReport(request, response, paramRequest);
            } catch (Exception e) {
                log.error(e);
            }
        }else if (Mode_ShowPhotos.equals(mode)) {
            doShowPhotos(request, response, paramRequest);
        }else if (Mode_MsgComments.equals(mode)) {
            doShowMsgComments(request, response, paramRequest);
        }else if(Mode_RecoverComments.equals(mode)){
            doShowRecoveredComments(request, response, paramRequest);
        }else if(Mode_AllComments.equals(mode)){
            doGetAllComments(request, response, paramRequest);
        }else if(Mode_CommentVideo.equals(mode)){//Displays dialog to create a comment
            doCommentVideo(request, response, paramRequest);
        }else if (Mode_ReplyPost.equals(mode)) {//Displays dialog to create post
            doReplyPost(request, response, paramRequest);
        }else if (Mode_ShowFullProfile.equals(mode)) {//Show user or page profile in dialog
            doShowFullProfile(request, response, paramRequest);
        }else if (paramRequest.getMode().equals("post")) {
            doCreatePost(request, response, paramRequest);
        }else if (Mode_PostSent.equals(mode)) {//Hides dialog used to create Post
            doPostSent(request, response, paramRequest);
        }else if(mode.equals("showUserProfile")){
            response.setContentType("text/html; charset=ISO-8859-1");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            RequestDispatcher dis = request.getRequestDispatcher(SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/youtubeUserProfile.jsp");
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error("Error in processRequest() for requestDispatcher" , e);
            }
        }else {
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
     * User edit view of the resource, this show a list of contents related to a
     * webpage, user can add, remove, activate, deactivate contents.
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
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String lang = paramRequest.getUser().getLanguage();
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        //System.out.println("Llega a doEdit:"+request.getParameter("suri"));

        String id = request.getParameter("suri");
        //System.out.println("SocialSentPost/Edit/id:"+id);
        if (id == null) {
            return;
        }

        SocialTopic socialTopic = (SocialTopic) SemanticObject.getSemanticObject(id).getGenericInstance();

        /*
         Iterator<SocialPFlowRef> itSocialPFlowRefs=socialTopic.listInheritPFlowRefs();
         if(itSocialPFlowRefs.hasNext())
         {
         itSocialPFlowRefs.next();
         }*/


        WebSite wsite = WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
        boolean classifyBySentiment = socialTopic.isCheckSentPostSentiment();

        PrintWriter out = response.getWriter();
        //Resource base = getResourceBase();
        //User user = paramRequest.getUser();

        
        //System.out.println("Entra a Edit, reload:"+request.getParameter("dialog"));
        out.println("<script type=\"javascript\">");
        if (request.getParameter("dialog") != null && request.getParameter("dialog").equals("close")) {
            out.println(" hideDialog(); ");
        }
        if (request.getParameter("statusMsg") != null) {
            out.println("   showStatus('" + request.getParameter("statusMsg") + "');");
        }
        if (request.getParameter("reloadTab") != null) {
            out.println(" reloadTab('" + id + "'); ");
        }
        out.println("</script>");


        //Agregado busqueda
        /*
         SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
         SemanticObject obj = ont.getSemanticObject(id);
         String idp = request.getParameter("sprop");
         System.out.println("idp que llega:"+idp);
         SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp);
         System.out.println("prop--JJ:"+prop+" idp:"+idp);
         SemanticClass clsprop = prop.getRangeClass();
         log.debug("class: " + clsprop.getClassName());
         HashMap<SemanticProperty, SemanticProperty> hmprop = new HashMap();
         Iterator<SemanticProperty> ite_sp = clsprop.listProperties();
         while (ite_sp.hasNext()) {
         SemanticProperty sp = ite_sp.next();
         log.debug("propiedad:" + sp.getDisplayName() + "---" + sp.getName());
         hmprop.put(sp, sp);
         }
         SemanticProperty sptemp = null;

         String busqueda = request.getParameter("search");
         if (null == busqueda) {
         busqueda = "";
         }
         */
        //Termina Agregado busqueda



        SWBResourceURL urls = paramRequest.getRenderUrl();
        urls.setParameter("act", "");
        urls.setParameter("suri", id);

        String searchWord = request.getParameter("search");
        if (null == searchWord) {
            searchWord = "";
        }


        out.println("<div class=\"swbform\">");

        String swbSocialUser = request.getParameter("swbSocialUser");

        int nPage;
        try {
            nPage = Integer.parseInt(request.getParameter("page"));
        } catch (Exception ignored) {
            nPage = 1;
        }
        
        HashMap hmapResult = filtros(swbSocialUser, wsite, searchWord, request, socialTopic, nPage);
        
        long numSocialTopicPOComments=0L;
        String snumSocialTopicPOComments=getSumTotPostOutComments(socialTopic);
        if(snumSocialTopicPOComments!=null && !snumSocialTopicPOComments.isEmpty())
        {
            try{
                int pos=snumSocialTopicPOComments.indexOf(".");
                if(pos>-1)
                {
                    snumSocialTopicPOComments=snumSocialTopicPOComments.substring(0, pos);
                }
                numSocialTopicPOComments=Long.parseLong(snumSocialTopicPOComments);
            }catch(Exception e)
            {
                numSocialTopicPOComments=0L;
            }
        }                 

        long nRec = ((Long) hmapResult.get("countResult")).longValue();
        
        NumberFormat nf2 = NumberFormat.getInstance(Locale.US);
        
        SWBResourceURL urlRefresh = paramRequest.getRenderUrl();
        urlRefresh.setParameter("suri", id);
      
        out.println("<fieldset class=\"barra\">");
        out.println("<div class=\"barra\">");

        out.println("<a href=\"#\" class=\"countersBar\" title=\"Refrescar Tab\" onclick=\"submitUrl('" + urlRefresh.setMode(SWBResourceURL.Action_EDIT) + "',this); return false;\">"+nf2.format(nRec)+"/"+nf2.format(numSocialTopicPOComments)+" mensajes/respuestas</a>");

        /*
         out.println("<span  class=\"spanFormat\">");
         out.println("<form id=\"" + id + "/fsearchwp\" name=\"" + id + "/fsearchwp\" method=\"post\" action=\"" + urls + "\" onsubmit=\"submitForm('" + id + "/fsearchwp');return false;\">");
         out.println("<div align=\"right\">");
         out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
         out.println("<label for=\"" + id + "_searchwp\">" + paramRequest.getLocaleString("searchPost") + ": </label><input type=\"text\" name=\"search\" id=\"" + id + "_searchwp\" value=\"" + searchWord + "\">");
         out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("btnSearch") + "</button>"); //
         out.println("</div>");
         out.println("</form>");
         out.println("</span>");
         * */
        String page = request.getParameter("page");
        if (page == null) {
            page = "1";
        }
        String orderBy = request.getParameter("orderBy");
        /*
         out.println("<span  class=\"spanFormat\">");
         out.println("<form id=\"" + id + "/importCurrentPage\" name=\"" + id + "/importCurrentPage\" method=\"post\" action=\"" + urls.setMode("exportExcel").setParameter("pages", page).setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("orderBy", orderBy) + "\" >");
         out.println("<div align=\"left\">");
         out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("importCurrentPage") + "</button>"); //
         out.println("</div>");
         out.println("</form>");
         out.println("</span>");
         * */
        out.println("<a href=\"" + urls.setMode("exportExcel").setParameter("pages", page).setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("orderBy", orderBy) + "\" class=\"excel\">" + paramRequest.getLocaleString("importCurrentPage") + "</a>");

        /*
         out.println("<span  class=\"spanFormat\">");
         out.println("<form id=\"" + id + "/importAll\" name=\"" + id + "/importAll\" method=\"post\" action=\"" + urls.setMode("exportExcel").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("pages", "0").setParameter("orderBy", orderBy) + "\" >");
         out.println("<div align=\"left\">");
         out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("importAll") + "</button>"); //
         out.println("</div>");
         out.println("</form>");
         out.println("</span>");
         out.println("</fieldset>");
         * */
        out.println("<a href=\"" + urls.setMode("exportExcel").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("pages", "0").setParameter("orderBy", orderBy) + "\" class=\"excelall\">" + paramRequest.getLocaleString("importAll") + "</a>");
        
        
        SWBResourceURL urlScheduledPost = paramRequest.getRenderUrl();
        urls.setParameter("act", "");
        urls.setParameter("suri", id);
        
        out.println("<form id=\"" + id + "/fviewScheduledPost\" name=\"" + id + "/fviewScheduledPost\" method=\"post\" action=\"" + urlScheduledPost.setMode(SWBResourceURL.Mode_EDIT).setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", id) + "\" onsubmit=\"submitForm('" + id + "/fviewScheduledPost');return false;\">");
        out.println(paramRequest.getLocaleString("showComboFilters")+":<select name=\"orderBy\">");
        out.println("<option value=\"\">"+paramRequest.getLocaleString("all")+"</option>");
        out.println("<option value=\"viewFCPost\">"+paramRequest.getLocaleString("indivCal")+"</option>");
        out.println("<option value=\"viewACPost\">"+paramRequest.getLocaleString("globalCal")+"</option>");
        out.println("</select>");
        out.println("<input type=\"submit\" name\"fviewScheduledPost_Go\" value=\""+paramRequest.getLocaleString("btnGo") + "\"/>"); //
        out.println("</form>");
        
        /*
        if(request.getParameter("orderBy").equals("viewScheduled"))
        {
            out.println("<a href=\"" + urls.setParameter("orderBy", "viewScheduled").setCallMethod(SWBResourceURL.Call_DIRECT) + "\" class=\"viewScheduled\">" + paramRequest.getLocaleString("viewScheduled") + "</a>");
        }else{
            out.println("<a href=\"" + urls.setCallMethod(SWBResourceURL.Call_DIRECT) + "\" class=\"viewAllPostOut\">" + paramRequest.getLocaleString("viewAllPostOuts") + "</a>");
        }*/
        

        //out.println("<span  class=\"spanFormat\">");
        out.println("<form id=\"" + id + "/fsearchwp\" name=\"" + id + "/fsearchwp\" method=\"post\" action=\"" + urls.setMode(SWBResourceURL.Mode_EDIT) + "\" onsubmit=\"submitForm('" + id + "/fsearchwp');return false;\">");
        out.println("<div align=\"right\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
        out.println("<input type=\"text\" name=\"search\" id=\"" + id + "_searchwp\" value=\"" + searchWord + "\" placeholder=\"" + paramRequest.getLocaleString("searchPost") + "\">");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("btnSearch") + "</button>"); //
        out.println("</div>");
        out.println("</form>");
        //out.println("</span>");

        out.println("</div>");
        out.println("</fieldset>");
       
        
        out.println("<fieldset>");
        
        out.println("<table class=\"tabla1\" >");
        out.println("<thead>");
        out.println("<tr>");


        out.println("<th class=\"accion\">");
        out.println("<span>" + paramRequest.getLocaleString("action") + "</span>");
        out.println("</th>");


        out.println("<th class=\"mensaje\">");
        out.println("<span>" + paramRequest.getLocaleString("post") + "</span>");
        out.println("</th>");

        SWBResourceURL urlOderby = paramRequest.getRenderUrl();
        urlOderby.setParameter("act", "");
        urlOderby.setParameter("suri", id);

        String typeOrder = "Ordenar Ascendente";
        String nameClass = "ascen";
        urlOderby.setParameter("orderBy", "PostTypeDown");
        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("PostTypeUp") || request.getParameter("orderBy").equals("PostTypeDown")) {

                if (request.getParameter("nameClass") != null) {
                    if (request.getParameter("nameClass").equals("descen")) {
                        nameClass = "ascen";
                    } else {
                        nameClass = "descen";
                        urlOderby.setParameter("orderBy", "PostTypeUp");
                        typeOrder = "Ordenar Descendente";
                    }
                }
            }
        }
        out.println("<th>");
        urlOderby.setParameter("nameClass", nameClass);
        out.println("<a href=\"#\" class=\"" + nameClass + "\" title=\"" + typeOrder + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
        out.println("<span>" + paramRequest.getLocaleString("postType") + "</span>");
        out.println("</a>");
        out.println("</th>");




        out.println("<th class=\"mensaje\">");
        out.println(paramRequest.getLocaleString("networks"));
        out.println("</th>");


        String nameClassOrigen = "ascen";
        String typeOrderOrigen = "Ordenar Ascendente";
        urlOderby.setParameter("orderBy", "origenUp");
        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("origenUp") || request.getParameter("orderBy").equals("origenDown")) {

                if (request.getParameter("nameClassOrigen") != null) {
                    if (request.getParameter("nameClassOrigen").equals("descen")) {
                        nameClassOrigen = "ascen";
                    } else {
                        nameClassOrigen = "descen";
                        urlOderby.setParameter("orderBy", "origenDown");
                        typeOrderOrigen = "Ordenar Descendente";
                    }
                }
            }
        }
        out.println("<th>");
        urlOderby.setParameter("nameClassOrigen", nameClassOrigen);
        out.println("<a href=\"#\" class=\"" + nameClassOrigen + "\" title=\"" + typeOrderOrigen + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
        out.println("<span>" + paramRequest.getLocaleString("source") + "</span>");
        out.println("</a>");
        out.println("</th>");


        //User
        String nameClassUser = "ascen";
        String typeOrderUser = "Ordenar Ascendente";
        urlOderby.setParameter("orderBy", "userUp");
        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("userUp") || request.getParameter("orderBy").equals("userDown")) {
                if (request.getParameter("nameClassUser") != null) {
                    if (request.getParameter("nameClassUser").equals("descen")) {
                        nameClassUser = "ascen";
                    } else {
                        nameClassUser = "descen";
                        urlOderby.setParameter("orderBy", "userDown");
                        typeOrderUser = "Ordenar Descendente";
                    }
                }
            }
        }
        out.println("<th>");
        urlOderby.setParameter("nameClassUser", nameClassUser);
        out.println("<a href=\"#\" class=\"" + nameClassUser + "\" title=\"" + typeOrderUser + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
        out.println("<span>" + paramRequest.getLocaleString("user") + "</span>");
        out.println("</a>");
        out.println("</th>");
        //Ends User


        String nameClassCreted = "ascen";
        String typeOrderCreted = "Ordenar Ascendente";
        urlOderby.setParameter("orderBy", "cretedDown");
        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("cretedUp") || request.getParameter("orderBy").equals("cretedDown")) {

                if (request.getParameter("nameClassCreted") != null) {
                    if (request.getParameter("nameClassCreted").equals("descen")) {
                        nameClassCreted = "ascen";
                    } else {
                        nameClassCreted = "descen";
                        urlOderby.setParameter("orderBy", "cretedUp");
                        typeOrderCreted = "Ordenar Descendente";
                    }
                }
            }
        }
        out.println("<th>");
        urlOderby.setParameter("nameClassCreted", nameClassCreted);
        out.println("<a href=\"#\" class=\"" + nameClassCreted + "\" title=\"" + typeOrderCreted + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
        out.println("<span>" + paramRequest.getLocaleString("created") + "</span>");
        out.println("</a>");
        out.println("</th>");


        String nameClassUpdate = "ascen";
        String typeOrderUpdate = "Ordenar Ascendente";
        urlOderby.setParameter("orderBy", "updatedDown");
        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("updatedUp") || request.getParameter("orderBy").equals("updatedDown")) {
                if (request.getParameter("nameClassUpdate") != null) {
                    if (request.getParameter("nameClassUpdate").equals("descen")) {
                        nameClassUpdate = "ascen";
                    } else {
                        nameClassUpdate = "descen";
                        urlOderby.setParameter("orderBy", "updatedUp");
                        typeOrderUpdate = "Ordenar Descendente";
                    }
                }
            }
        }

        out.println("<th>");
        urlOderby.setParameter("nameClassUpdate", nameClassUpdate);
        out.println("<a  href=\"#\" class=\"" + nameClassUpdate + "\" title=\"" + typeOrderUpdate + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
        out.println("<span>" + paramRequest.getLocaleString("updated") + "</span>");
        out.println("</a>");
        out.println("</th>");

        if (classifyBySentiment) {
            String nameClassSentiment = "ascen";
            String typeOrderSentiment = "Ordenar Ascendente";
            urlOderby.setParameter("orderBy", "sentimentDown");
            if (request.getParameter("orderBy") != null) {
                if (request.getParameter("orderBy").equals("sentimentUp") || request.getParameter("orderBy").equals("sentimentDown")) {
                    if (request.getParameter("nameClassSentiment") != null) {
                        if (request.getParameter("nameClassSentiment").equals("descen")) {
                            nameClassSentiment = "ascen";
                        } else {
                            nameClassSentiment = "descen";
                            urlOderby.setParameter("orderBy", "sentimentUp");
                            typeOrderSentiment = "Ordenar Descendente";
                        }
                    }
                }
            }
            out.println("<th>");
            urlOderby.setParameter("nameClassSentiment", nameClassSentiment);
            out.println("<a  href=\"#\" class=\"" + nameClassSentiment + "\" title=\"" + typeOrderSentiment + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
            out.println("<span>" + paramRequest.getLocaleString("sentiment") + "</span>");
            out.println("</a>");
            out.println("</th>");

            String nameClassIntensity = "ascen";
            String typeOrderIntensity = "Ordenar Ascendente";
            urlOderby.setParameter("orderBy", "intensityDown");
            if (request.getParameter("orderBy") != null) {
                if (request.getParameter("orderBy").equals("intensityUp") || request.getParameter("orderBy").equals("intensityDown")) {
                    if (request.getParameter("nameClassIntensity") != null) {
                        if (request.getParameter("nameClassIntensity").equals("descen")) {
                            nameClassIntensity = "ascen";
                        } else {
                            nameClassIntensity = "descen";
                            urlOderby.setParameter("orderBy", "intensityUp");
                            typeOrderIntensity = "Ordenar Descendente";
                        }
                    }
                }
            }
            out.println("<th>");
            urlOderby.setParameter("nameClassIntensity", nameClassIntensity);
            out.println("<a href=\"#\" class=\"" + nameClassIntensity + "\" title=\"" + typeOrderIntensity + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
            out.println("<span>" + paramRequest.getLocaleString("intensity") + "</span>");
            out.println("<small>Descendente</small>");
            out.println("</a>");
            out.println("</th>");
        }

        String nameClassStatus = "ascen";
        String typeOrderSentiment = "Ordenar Ascendente";
        urlOderby.setParameter("orderBy", "statusUp");
        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("statusUp") || request.getParameter("orderBy").equals("statusDown")) {
                if (request.getParameter("nameClassStatus") != null) {
                    if (request.getParameter("nameClassStatus").equals("descen")) {
                        nameClassStatus = "ascen";
                    } else {
                        nameClassStatus = "descen";
                        urlOderby.setParameter("orderBy", "statusDown");
                        typeOrderSentiment = "Ordenar Descendente";
                    }
                }
            }
        }
        out.println("<th>");
        urlOderby.setParameter("nameClassStatus", nameClassStatus);
        out.println("<a href=\"#\" class=\"" + nameClassStatus + "\" title=\"" + typeOrderSentiment + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
        out.println("<span>" + paramRequest.getLocaleString("status") + "</span>");
        out.println("</a>");
        out.println("</th>");


        out.println("</thead>");
        out.println("<tbody>");

        SocialPFlowMgr pfmgr = SocialLoader.getPFlowManager();
        boolean isInFlow = false;
        boolean isAuthorized = false;
        boolean needAuthorization = false;
        boolean send2Flow = false;

        //Agregado busqueda
        /*
         busqueda = busqueda.trim();
         HashMap<String, SemanticObject> hmbus = new HashMap();
         HashMap<String, SemanticObject> hmfiltro = new HashMap();
         SemanticObject semO = null;
         Iterator<SemanticProperty> itcol = null;
         Iterator<SemanticObject> itso = obj.listObjectProperties(prop);
        


         if (!busqueda.equals("")) {
         while (itso.hasNext()) {
         semO = itso.next();
         boolean del = false;
         if (semO.instanceOf(Trashable.swb_Trashable)) {
         del = semO.getBooleanProperty(Trashable.swb_deleted, false);
         }
         if (del) {
         continue;
         }

         hmbus.put(semO.getURI(), semO);
         itcol = hmprop.keySet().iterator();
         String occ = "";
         while (itcol.hasNext()) {
         SemanticProperty sprop = itcol.next();
         occ = occ + reviewSemProp(sprop, semO, paramRequest);
         }
         //System.out.println("occ:"+occ);
         occ = occ.toLowerCase();
         if (occ.indexOf(busqueda.toLowerCase()) > -1) {
         hmfiltro.put(semO.getURI(), semO);
         }
         }
         } else {
         while (itso.hasNext()) {
         semO = itso.next();
         boolean del = false;
         if (semO.instanceOf(Trashable.swb_Trashable)) {
         del = semO.getBooleanProperty(Trashable.swb_deleted, false);
         }
         if (del) {
         continue;
         }

         hmbus.put(semO.getURI(), semO);
         }
         }

         if (busqueda.trim().length() == 0) {  //hmfiltro.isEmpty()&&
         itso = hmbus.values().iterator(); //obj.listObjectProperties(prop);
         } else {
         itso = hmfiltro.values().iterator();
         }
         Set<SemanticObject> setso = SWBComparator.sortByCreatedSet(itso, false);
         */
        //Termina Agregado busqueda


        //Funcionan bien sin busqueda
        //Iterator<PostOut> itposts = PostOut.ClassMgr.listPostOutBySocialTopic(socialTopic);

        //System.out.println("searchWord en SentPost:"+searchWord);

        //Filtros

        //Manejo de permisos
        User user = paramRequest.getUser();
        boolean userCanRemoveMsg = false;
        /*
         boolean userCanRetopicMsg=false;
         boolean userCanRevalueMsg=false;
         boolean userCanRespondMsg=false;
         * */
        SocialUserExtAttributes socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
        if (socialUserExtAttr != null) {
            userCanRemoveMsg = socialUserExtAttr.isUserCanRemoveMsg();
            /*
             userCanRetopicMsg=socialUserExtAttr.isUserCanReTopicMsg();
             userCanRevalueMsg=socialUserExtAttr.isUserCanReValueMsg();
             userCanRespondMsg=socialUserExtAttr.isUserCanRespondMsg();
             * */
        }
        //UserGroup userAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("admin");
        UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");

        
        //Set<PostOut> setso = ((Set) hmapResult.get("itResult"));
        Iterator<PostOut> itposts = (Iterator)hmapResult.get("itResult"); 

        //Iterator<PostOut> itposts = setso.iterator();
        while (itposts!=null && itposts.hasNext()) {
            PostOut postOut = (PostOut) itposts.next();


            // revisando contenido en flujo de publicación
            // validacion de botones en relación a los flujos

            isInFlow = false;
            isAuthorized = false;
            needAuthorization = false;
            send2Flow = false;

            //System.out.println("sobj PostOut..JJUri:"+postOut.getEncodedURI()+",Is Published:"+postOut.isPublished());

            isInFlow = pfmgr.isInFlow(postOut);


            //System.out.println("Recurso esta en flujo: "+isInFlow);

            needAuthorization = pfmgr.needAnAuthorization(postOut);

            //System.out.println("Necesita autorización: " + needAuthorization + ",postOut:" + postOut);

            /*
             if (!isInFlow && !needAuthorization) {
             activeButton = true;
             }*/
            if (!isInFlow && needAuthorization) {
                //activeButton = false;
                send2Flow = true;
            }

            if (isInFlow) {
                isAuthorized = pfmgr.isAuthorized(postOut);
                /*
                 SocialPFlowInstance instance = sobj.getPflowInstance();
                 if (!isAuthorized || instance.getStatus()==3) { //rechazado
                 activeButton = false;
                 }
                 if (isAuthorized) {
                 activeButton = true;
                 }**/
            }

            // fin validación de botones en relacion a flujos
            /*
            boolean readyToPublish = false;
            if (!postOut.isPublished() && !needAuthorization) {
                readyToPublish = true;
            }*/

            //System.out.println("isInFlow:"+isInFlow);
            //System.out.println("needAuthorization:"+needAuthorization);
            //System.out.println("isAuthorized:"+isAuthorized);

            out.println("<tr>");

            //Show Actions
            out.println("<td class=\"accion\">");

            SWBResourceURL urlr = paramRequest.getActionUrl();
            urlr.setParameter("suri", id);
            urlr.setParameter("sval", postOut.getURI());
            urlr.setParameter("page", "" + nPage);
            urlr.setAction("remove");

            String msgText = postOut.getURI();
            if (postOut.getMsg_Text() != null) {
                msgText = SWBUtils.TEXT.scape4Script(postOut.getMsg_Text());
                msgText = SWBSocialResUtil.Util.replaceSpecialCharacters(msgText, false);
            }

            if (userCanRemoveMsg || user.hasUserGroup(userSuperAdminGrp)) {
                out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("remove") + "\" class=\"eliminar\" onclick=\"if(confirm('" + paramRequest.getLocaleString("confirm_remove") + " " + msgText + "?')){ submitUrl('" + urlr + "',this); } else { return false;}\"></a>");
            }

            /*
             SWBResourceURL urlpre = paramRequest.getRenderUrl();
             urlpre.setParameter("suri", id);
             urlpre.setParameter("page", "" + p);
             urlpre.setParameter("sval", postOut.getURI());
             urlpre.setParameter("preview", "true");
             urlpre.setParameter("orderBy", (request.getParameter("orderBy")!=null && request.getParameter("orderBy").trim().length() > 0 ? request.getParameter("orderBy") : ""));

             out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("previewdocument") + "\" onclick=\"submitUrl('" + urlpre + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/preview.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("previewdocument") + "\"></a>");
             */

            SWBResourceURL urlPrev = paramRequest.getRenderUrl().setMode(Mode_PREVIEW).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postOut.getURI());
            out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("previewdocument") + "\" class=\"ver\" onclick=\"showDialog('" + urlPrev + "','" + paramRequest.getLocaleString("previewdocument")
                    + "'); return false;\"></a>");


            //Nuevo agregado por Jorge el 15/Oct/2013
            //System.out.println("PostUri-GyA:" + postOut.getURI());
            boolean postOutwithPostOutNets = false;
            boolean someOneIsNotPublished = false;
            SWBResourceURL urlPostOutNets = paramRequest.getRenderUrl().setMode(Mode_ShowPostOutNets).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postOut", postOut.getURI()).setParameter("suri", id);
            if (!postOut.isPublished()) {
                Iterator<PostOutNet> itPostOutNets = PostOutNet.ClassMgr.listPostOutNetBySocialPost(postOut, wsite);
                while (itPostOutNets.hasNext()) {
                    PostOutNet postOutNet = itPostOutNets.next();
                    //System.out.println("postOutNet-GyA:" + postOutNet);
                    postOutwithPostOutNets = true;
                    if (postOutNet.getStatus() == 0) {
                        //System.out.println("postOutNet-1/status-GyA:" + postOutNet.getStatus());
                        someOneIsNotPublished = true;
                        break;
                    }
                }

                if (!isInFlow && postOutwithPostOutNets && !someOneIsNotPublished) {
                    postOut.setPublished(true);
                }
            }

            //Termina agregado

            if (!postOut.isPublished()) {
                if (send2Flow) {    //Social:Solo cuando se puede enviar el documento a flujo, se muestra la opción de editar, si el documento esta en flujo no se muestra.
                    //if (canEdit) {
                    SWBResourceURL urlEdit = paramRequest.getRenderUrl().setMode(Mode_EDITWindow).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postOut", postOut.getURI()).setParameter("wsite", postOut.getSemanticObject().getModel().getName());
                    out.println("<a class=\"editar\" href=\"#\" title=\"" + paramRequest.getLocaleString("documentAdmin") + "\" onclick=\"showDialog('" + urlEdit + "','" + paramRequest.getLocaleString("source") + "'); return false;\"></a>");

                    //out.println("<a href=\"#\"  title=\"" + paramRequest.getLocaleString("documentAdmin") + "\" onclick=\"\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"" + "documentAdmin" + "\"></a>");
                    //} else {
                    //    out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"" + "documentAdmin" + "\">");
                    //}

                    boolean canSend2Flow = Boolean.TRUE;
                    String pfid = null;
                    SocialPFlow[] arrPf = pfmgr.getFlowsToSendContent(postOut);
                    if (arrPf.length == 1) {
                        pfid = arrPf[0].getId();
                    }

                    /*
                     GenericObject gores = sobj.createGenericInstance();
                     if (gores != null && gores instanceof Versionable) {
                     Versionable vgo = (Versionable) gores;
                     if (vgo.getActualVersion() == null || vgo.getLastVersion() == null) {
                     canSend2Flow = Boolean.FALSE;
                     }
                     }**/

                    if (canSend2Flow) {
                        SWBResourceURL url2flow = paramRequest.getRenderUrl();
                        url2flow.setParameter("suri", id);
                        url2flow.setMode(Mode_PFlowMsg);
                        url2flow.setParameter("sval", postOut.getURI());
                        url2flow.setParameter("page", "" + nPage);
                        url2flow.setParameter("pfid", pfid);
                        //out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("senddocument2flow") + "\" onclick=\"showDialog('" + url2flow + "','" + paramRequest.getLocaleString("comentary") + "'); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/enviar-flujo.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("senddocument2flow") + "\"></a>");
                        out.println("<a class=\"aflujo\" href=\"#\" title=\"" + paramRequest.getLocaleString("senddocument2flow") + "\" onclick=\"showDialog('" + url2flow + "','" + paramRequest.getLocaleString("comentary") + "'); return false;\"></a>");
                    } else {    //TODOSOCIAL:VER CUANDO PUEDE PASAR ESTA OPCIÓN (ELSE).
                        out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/enviar-flujo.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("senddocument2flow") + "\" title=\"" + paramRequest.getLocaleString("canNOTsenddocument2flow") + "\">");
                    }
                /*} else if (isInFlow && !isAuthorized) {
                    if (!readyToPublish) {
                        out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/espera_autorizacion.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("documentwaiting") + "\">");
                    }*/
                } else if (isInFlow && isAuthorized) {
                    out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/enlinea.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("Caccepted") + "\">");
                }
                if(postOut.getFastCalendar()!=null)
                {
                    SWBResourceURL urlFastCalendars = paramRequest.getRenderUrl().setMode(Mode_ShowFastCalendar).setCallMethod(SWBResourceURL.Call_DIRECT);
                    out.println("<a class=\"swbIconFC\" title=\""+paramRequest.getLocaleString("indivCalendar")+"\" href=\"#\" onclick=\"showDialog('" + urlFastCalendars.setParameter("postUri", postOut.getURI()) + "','" + paramRequest.getLocaleString("associatedFastCalendar") + "'); return false;\"></a>");
                }
            }
            boolean oneCalendarIsActive=false;
            Iterator <CalendarRef> itCalendarsRefs=postOut.listCalendarRefs();
            while(itCalendarsRefs.hasNext())
            {
                CalendarRef calRef=itCalendarsRefs.next();
                if(calRef.isValid())
                {
                    oneCalendarIsActive=true;
                    break;
                }
            }
            if(oneCalendarIsActive)
            {
                out.println("<a class=\"swbIconCA\" href=\"#\"  onclick=\"addNewTab('" + postOut.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + msgText + "');return false;\" title=\"" + paramRequest.getLocaleString("globalCalendar") + "\"></a>");
            }

            if(postOut.getPostInSource() == null && postOut.getNumTotNewResponses()>=0L)
            {
                SWBResourceURL postOutCommentsUrl = paramRequest.getRenderUrl().setMode(Mode_MsgComments).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postOut.getURI());
                out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("msgComments") + "\" class=\"msgComments\" onclick=\"showDialog('" + postOutCommentsUrl + "','" + paramRequest.getLocaleString("msgComments")
                        + "'); return false;\">"+Math.round(postOut.getNumTotNewResponses())+"</a>");
            }

            out.println("</td>");

            //Show 30 firsts characters of Msg PostOut
            out.println("<td>");
            //out.println(SWBUtils.TEXT.cropText(sobj.getMsg_Text(), 30));
            if (postOut.getMsg_Text() != null) {
                msgText = SWBUtils.TEXT.cropText(SWBUtils.TEXT.scape4Script(postOut.getMsg_Text()), 25);
                msgText = msgText.replaceAll("\n", " ");
            }
            
            if (!postOut.isPublished()) {
                out.println("<a href=\"#\"  onclick=\"addNewTab('" + postOut.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + msgText + "');return false;\" title=\"" + msgText + "\">" + msgText + "</a>");
            } else {  //Si ya esta publicado
                out.println("<a href=\"#\"  onclick=\"addNewTab('" + postOut.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp?publish=true" + "','" + msgText + "');return false;\" title=\"" + msgText + "\">" + msgText + "</a>");
            }
            out.println("</td>");

            //Show PostType
            out.println("<td>");
            out.println(postOut instanceof Message ? "<img title=\"Texto\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/tipo-txt.jpg\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("message") + "  \">" : postOut instanceof Photo ? "<img title=\"Imagen\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/tipo-img.jpg\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("photo") + "  \">" : postOut instanceof Video ? "<img title=\"Video\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/tipo-vid.jpg\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("video") + "  \">" : "---");
            out.println("</td>");

            //Show Networks
            out.println("<td>");
            String nets = "---";
            //System.out.println("socialNet:"+postOut.getSocialNetwork()+",redes:"+postOut.listSocialNetworks().hasNext());
            int cont = 0;
            Iterator<SocialNetwork> itPostSocialNets = postOut.listSocialNetworks();
            while (itPostSocialNets.hasNext()) {
                cont++;
                if (cont > 1) {
                    break; //Determinamos que solo se mostrara una y se mostrara un "ver mas" en dado caso que fueran mas redes sociales.
                }
                SocialNetwork socialNet = itPostSocialNets.next();
                //System.out.println("socialNet-1:"+socialNet);
                String sSocialNet = socialNet.getDisplayTitle(lang);
                String netIcon = "";
                if(socialNet instanceof Youtube){
                    netIcon = "<img class=\"swbIconYouTube\" src=\"/swbadmin/js/dojo/dojo/resources/blank.gif\"/>";
                }else{
                    netIcon = "<img class=\"swbIcon" + socialNet.getClass().getSimpleName() + "\" src=\"/swbadmin/js/dojo/dojo/resources/blank.gif\"/>";
                }
                //System.out.println("socialNet-2:"+sSocialNet);
                if (sSocialNet != null && sSocialNet.trim().length() > 0) {
                    //System.out.println("socialNet-3:"+sSocialNet);
                    //Sacar privacidad
                    String sPrivacy = null;
                    //Si es necesario, cambiar esto por querys del Jei despues.
                    Iterator<PostOutPrivacyRelation> itpostOutPriRel = PostOutPrivacyRelation.ClassMgr.listPostOutPrivacyRelationByPopr_postOut(postOut, wsite);
                    while (itpostOutPriRel.hasNext()) {
                        PostOutPrivacyRelation poPrivRel = itpostOutPriRel.next();
                        if (poPrivRel.getPopr_socialNetwork().getURI().equals(socialNet.getURI())) {
                            sPrivacy = poPrivRel.getPopr_privacy().getTitle(lang);
                        }
                    }
                    if (sPrivacy == null) {
                        Iterator<PostOutNet> itpostOutNet = PostOutNet.ClassMgr.listPostOutNetBySocialPost(postOut, wsite);
                        while (itpostOutNet.hasNext()) {
                            PostOutNet postOutnet = itpostOutNet.next();
                            if (postOutnet.getSocialNetwork().getURI().equals(socialNet.getURI()) && postOutnet.getPo_privacy() != null) {
                                sPrivacy = postOutnet.getPo_privacy().getTitle(lang);
                            }
                        }
                    }
                    if (sPrivacy == null) {
                        sPrivacy = paramRequest.getLocaleString("public");
                    }

                    //Termina privacidad
                    if (cont == 1) {
                        nets = "<p>" + netIcon + sSocialNet + "(" + sPrivacy + ")" + "</p>";
                    } else {//Nunca entraría aquí con lo que se determinó, de solo mostrar la primera red social y un "ver mas", en caso de haber mas, se deja este códigp por si cambia esta regla en lo futuro.
                        nets += "<p>" + sSocialNet + "(" + sPrivacy + ")" + "</p>";
                    }
                }
            }
            out.println(nets);
            if (cont > 1) {
                SWBResourceURL urlshowmoreNets = paramRequest.getRenderUrl().setMode(Mode_ShowMoreNets).setCallMethod(SWBResourceURL.Call_DIRECT);
                out.println("<p><a href=\"#\" onclick=\"showDialog('" + urlshowmoreNets.setParameter("postUri", postOut.getURI()) + "','" + paramRequest.getLocaleString("associatedSocialNets") + "'); return false;\">" + paramRequest.getLocaleString("watchMore") + "</a></p>");
            }
            out.println("</td>");

            //PostIn Source 
            out.println("<td>");
            if (postOut.getPostInSource() != null) {
                SWBResourceURL url = paramRequest.getRenderUrl().setMode(Mode_SOURCE).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postOut.getPostInSource().getURI());
                out.println("<img href=\"#\" title=\"" + paramRequest.getLocaleString("source") + "\" onclick=\"showDialog('" + url + "','" + paramRequest.getLocaleString("source") + "'); return false;\" src=\"" +SWBPlatform.getContextPath() + "/swbadmin/css/images/ico-origen.png\">");
            } else {
                out.println("---");
            }
            out.println("</td>");


            //User
            out.println("<td>");
            SWBResourceURL urlshowUsrHistory = paramRequest.getRenderUrl().setMode(Mode_ShowUsrHistory).setCallMethod(SWBResourceURL.Call_DIRECT);
            out.println(postOut.getCreator() != null ? "<a href=\"#\" onclick=\"showDialog('" + urlshowUsrHistory.setParameter("swbAdminUser", postOut.getCreator().getURI()) + "','" + paramRequest.getLocaleString("userHistory") + "'); return false;\">" + postOut.getCreator().getFullName() + "</a>" : paramRequest.getLocaleString("withoutUser"));
            out.println("</td>");


            //PostOut creation
            out.println("<td>");
            out.println(SWBUtils.TEXT.getTimeAgo(postOut.getCreated(), lang));
            out.println("</td>");

            //PostOut lastUpdate
            out.println("<td>");
            out.println(SWBUtils.TEXT.getTimeAgo(postOut.getUpdated(), lang));
            out.println("</td>");

            if (classifyBySentiment) {
                //Sentiment
                out.println("<td align=\"center\">");
                if (postOut.getPostSentimentalType() == 0) {
                    out.println("---");
                } else if (postOut.getPostSentimentalType() == 1) {
                    out.println("<img alt=\"Positivo\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/pos.png" + "\">");
                } else if (postOut.getPostSentimentalType() == 2) {
                    out.println("<img alt=\"Negativo\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/neg.png" + "\">");
                }
                out.println("</td>");

                //Intensity
                out.println("<td>");
                out.println(postOut.getPostIntesityType() == 0 ? "<img alt=\"Baja\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/ibaja.png\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("low") + "  \">" : postOut.getPostIntesityType() == 1 ? "<img alt=\"Media\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/imedia.png\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("medium") + "  \">" : postOut.getPostIntesityType() == 2 ? "<img alt=\"Alta\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/ialta.png\" border=\"0\" alt=\" " + paramRequest.getLocaleString("high") + "  \">" : "---");
                out.println("</td>");
            }



            out.println("<td class=\"status\">");
            //El PostOut No se ha enviado, aqui se daría la posibilidad de que un usuario lo envíe.
            //System.out.println("msg..:"+postOut.getMsg_Text());
            //System.out.println("Ya esta publicado..:"+postOut.isPublished());

           //System.out.println("PostUri:" + postOut.getURI()+", published:"+postOut.isPublished());

            if (!postOut.isPublished()) {

                //Si todos los PostOutNet referentes al PostOut estan con estatus de 1 o simplemente diferente de 0, quiere decir que ya estan publicados, 
                //probablente se revisaron desde el MonitorMgr y en el metodo isPublished de c/red social de tipo MonitorAble se reviso el estatus en la red socal
                // y la misma respondio que ya estaba publicado, por lo cual se le colocó en dicho metodo el estatus 1 (publicado) al PostOutNet de dicho PostOut,
                //por lo tanto, ya podemos aqui poner el estatus de dicho PostOut como publicado en todas las redes sociales a las que se envíó, esto lo hacemos solo
                //con colocar la porpiedad published del mismo=true, de esta manera la proxima vez entrara al if de los publicados y ya no se revisara en sus PostOutNets.


                //Esto no es cierto, puede que si el flujo no manda a publicar durectamente, aun no haya ningun PostOutNet para un PostOut, y aunque no se haya enviado 
                //a publicar aun, con la siguiente condición va a decir que ya esta publicado, revisar mañana, ya que ahorita ya estoy cansado.

                //System.out.println("Aver esto-isInFlow:"+isInFlow+", aver esto otro-someOneIsNotPublished:"+someOneIsNotPublished);
                if (!isInFlow && postOutwithPostOutNets && !someOneIsNotPublished) //Se supone que por lo menos, hay publicado un PostOutNet del Post                         
                {
                    //System.out.println("SE SUPONE QUE ESTA PUBLICADO...");
                    postOut.setPublished(true);
                    out.println("<a class=\"status3\" href=\"#\" title=\"" + paramRequest.getLocaleString("postOutLog") + "\" onclick=\"showDialog('" + urlPostOutNets + "','" + paramRequest.getLocaleString("postOutLog") + "'); return false;\"><strong>Publicado</strong></a>"); //Publicado
                } else {
                    //System.out.println("SOCIALSENTPOST1");
                    if (!needAuthorization || postOut.getPflowInstance().getStatus() == 3) {
                        SWBResourceURL urlu = paramRequest.getRenderUrl();
                        urlu.setMode(Mode_Action);
                        urlu.setParameter("suri", postOut.getURI());
                        urlu.setParameter("act", "updstatus");
                        /*
                         if(postOut.getPflowInstance()!=null)
                         {
                         System.out.println("postOut.getPflowInstance():"+postOut.getPflowInstance()+",step:"+postOut.getPflowInstance().getStep()+",status:"+postOut.getPflowInstance().getStatus());
                         }else{
                         System.out.println("postOut.getPflowInstance()==NULL");
                         }*/
                        ///System.out.println("isInFlow:"+isInFlow+",needAuthorization:"+needAuthorization+",postOutwithPostOutNets:"+postOutwithPostOutNets+",someOneIsNotPublished:"+someOneIsNotPublished+",FlowStatus:"+postOut.getPflowInstance()!=null?postOut.getPflowInstance().getStatus():"NO TIENE FLUJO");
                        if (someOneIsNotPublished) {
                            //System.out.println("ENTRA SENTPOST-STATUS-1");
                            out.println("<a class=\"status1\" href=\"#\" title=\"" + paramRequest.getLocaleString("postOutLog") + "\" onclick=\"showDialog('" + urlPostOutNets + "','" + paramRequest.getLocaleString("postOutLog") + "'); return false;\"><strong>" + paramRequest.getLocaleString("toReview") + "</strong></a>"); //No ha sido publicado en todas las redes sociales que debiera, abrir dialogo para mostrar todos los PostOutNtes del PostOut
                        } else if (isInFlow && needAuthorization && postOut.getPflowInstance() != null && postOut.getPflowInstance().getStatus() == 3) {
                            if(postOut.getFastCalendar()!=null)
                            {
                                //System.out.println("ENTRA SENTPOST-STATUS-2");
                                out.println("<span class=\"status5\" title=\"Cumplir Calendario\"><strong>Cumplir Calendario</strong></span>");
                            }else{
                                //System.out.println("ENTRA SENTPOST-STATUS-2");
                                out.println("<a class=\"status6\" href=\"#\" onclick=\"showStatusURL('" + urlu + "'); \" title=\""+ paramRequest.getLocaleString("publish") + "\" /></a>");
                            }
                        } else if (!isInFlow && !needAuthorization && !postOutwithPostOutNets) {
                            //System.out.println("ENTRA SENTPOST-STATUS-4");
                            if(postOut.getFastCalendar()!=null)
                            {
                                out.println("<span class=\"status5\" title=\"Cumplir Calendario\"><strong>Cumplir Calendario</strong></span>");
                            }else{
                                //out.println("<span class=\"status2\" title=\"Publicando\"><strong>"+paramRequest.getLocaleString("publishing") +"</strong></span>");    //Aqui no debe haber liga, que lo cheque roger desde estilo
                                out.println("<a class=\"status6\" href=\"#\" onclick=\"showStatusURL('" + urlu + "'); \" / title=\""+ paramRequest.getLocaleString("publish") + "\"></a>");
                            }
                        } else {
                            //System.out.println("ENTRA SENTPOST-STATUS-5");
                            if(postOut.getFastCalendar()!=null)
                            {
                                //System.out.println("ENTRA SENTPOST-STATUS-6");
                                out.println("<span class=\"status5\" title=\"Cumplir Calendario\"><strong>Cumplir Calendario</strong></span>");
                            }else{
                                //System.out.println("ENTRA SENTPOST-STATUS-7");
                                out.println("<a class=\"status6\" title=\""+paramRequest.getLocaleString("publish")+"\" href=\"#\" onclick=\"showStatusURL('" + urlu + "'); \" /><strong>" + paramRequest.getLocaleString("publish") + "</strong></a>");
                            }
                        }
                    } else {    //El PostOut ya se envío
                        //System.out.println("SOCIALSENTPOST2");
                        if (!isInFlow && needAuthorization && !isAuthorized) {
                            String sFlowRejected = "---";
                            if (postOut.getPflowInstance() != null && postOut.getPflowInstance().getPflow() != null) {
                                sFlowRejected = postOut.getPflowInstance().getPflow().getDisplayTitle(lang);
                            }
                            out.println("<span class=\"status4\" title=\""+paramRequest.getLocaleString("rejected")+"\"><strong>"+paramRequest.getLocaleString("rejected") + "</strong>" + sFlowRejected + "</span>");
                        } else if (isInFlow && needAuthorization && !isAuthorized) {
                            //System.out.println("postOut.getPflowInstance().getStatus():"+postOut.getPflowInstance().getStatus());
                            out.println("<span class=\"status7\" title=\""+paramRequest.getLocaleString("onFlow")+"\"><strong>"+paramRequest.getLocaleString("onFlow") + "</strong>"  + postOut.getPflowInstance().getStep() + ":"+postOut.getPflowInstance().getPflow().getDisplayTitle(lang)+"</span>");
                        }
                    }
                }
            } else {
                //System.out.println("ESE POST ESTA PUBLICADO..");
                out.println("<a class=\"status3\" href=\"#\" title=\"" + paramRequest.getLocaleString("postOutLog") + "\" onclick=\"showDialog('" + urlPostOutNets + "','" + paramRequest.getLocaleString("postOutLog") + "'); return false;\"><strong>" + paramRequest.getLocaleString("published") + "</strong></a>");
            }
            out.println("</td>");

            out.println("</tr>");
        }


        out.println("</tbody>");
        out.println("</table>");
        out.println("</fieldset>");

        if (nRec > 0) {
            int totalPages = 1;
            if (nRec > RECPERPAGE) {
                totalPages = Double.valueOf(nRec / 20).intValue();
                if ((nRec % RECPERPAGE) > 0) {
                    totalPages = Double.valueOf(nRec / 20).intValue() + 1;
                }
            }
            out.println("<div id=\"page\">");
            out.println("<div id=\"pagSumary\">" + paramRequest.getLocaleString("page") + ":" + nPage + " " + paramRequest.getLocaleString("of") + " " + totalPages + "</div>");

            SWBResourceURL pageURL = paramRequest.getRenderUrl();
            //pageURL.setParameter("page", "" + (countPage));
            pageURL.setParameter("suri", id);
            pageURL.setParameter("search", (searchWord.trim().length() > 0 ? searchWord : ""));
            if (request.getParameter("orderBy") != null) {
                pageURL.setParameter("orderBy", request.getParameter("orderBy"));
            }
            /*
             out.println("<div id=\"pagination\">");
             out.println("<span>P&aacute;ginas:</span>");
             for (int countPage = 1; countPage < (Math.ceil((double) nRec / (double) RECPERPAGE) + 1); countPage++) {
             SWBResourceURL pageURL = paramRequest.getRenderUrl();
             pageURL.setParameter("page", "" + (countPage));
             pageURL.setParameter("suri", id);
             pageURL.setParameter("search", (searchWord.trim().length() > 0 ? searchWord : ""));
             if (request.getParameter("orderBy") != null) {
             pageURL.setParameter("orderBy", request.getParameter("orderBy"));
             }
             if (countPage != nPage) {
             out.println("<a href=\"#\" onclick=\"submitUrl('" + pageURL + "',this); return false;\">" + countPage + "</a> ");
             } else {
             out.println(countPage + " ");
             }
             }
             out.println("</div>");
             * */
            //out.println(SWBSocialUtil.Util.getContentByPage(totalPages, nPage, PAGES2VIEW, paramRequest.getLocaleString("pageBefore"), paramRequest.getLocaleString("pageNext"), pageURL));
            out.println(SWBSocialResUtil.Util.getContentByPage(totalPages, nPage, PAGES2VIEW, pageURL));
            out.println("</div>");
        }

        out.println("</div>");

    }

    /*
     * Show the PostOutNets related to a PostOut PostOut that comes as a parameter "postUri"
     */
    public void doShowPostOutLog(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        /////////////
        String postUri = request.getParameter("postOut");
        try {
            final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/showPostOutLog.jsp";
            if (request != null) {
                RequestDispatcher dis = request.getRequestDispatcher(path);
                if (dis != null) {
                    try {
                        SemanticObject semObject = SemanticObject.createSemanticObject(postUri);
                        request.setAttribute("postOut", semObject);
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
     * Show the source message of One PostOut that comes as a parameter "postUri"
     */
    public void doShowSource(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        /////////////
        String postUri = request.getParameter("postUri");
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
        String postUri = request.getParameter("postUri");
        try {
            final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/showPostOut.jsp";
            if (request != null) {
                RequestDispatcher dis = request.getRequestDispatcher(path);
                if (dis != null) {
                    try {
                        SemanticObject semObject = SemanticObject.createSemanticObject(postUri);
                        request.setAttribute("postOut", semObject);
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

    /**
     * Show the list of the pflows to select one and send the element to the
     * selected publish flow.
     *
     * @param request , this holds the parameters, an input data
     * @param response , an answer to the user request
     * @param paramRequest , a list of objects like user, webpage, Resource, ...
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPFlowMessage(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String id = request.getParameter("suri"); // id recurso
        String resid = request.getParameter("sval"); // id recurso

        //System.out.println("Site:"+paramRequest.getWebPage().getWebSite());

        //System.out.println("id:"+id);
        //System.out.println("resid:"+resid);


        SemanticObject semObj = SemanticObject.getSemanticObject(resid);
        if (semObj == null) {
            return;
        }

        PostOut postOut = (PostOut) semObj.createGenericInstance();
        String postOutFlowUri = null;
        if (postOut.getPflowInstance() != null && postOut.getPflowInstance().getPflow() != null) {
            postOutFlowUri = postOut.getPflowInstance().getPflow().getURI();
        }
        //System.out.println("postOutFlowUri ---GGG--:"+postOutFlowUri);

        PrintWriter out = response.getWriter();

        User user = paramRequest.getUser();


        //SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SocialPFlowMgr pfmgr = SocialLoader.getPFlowManager();

        String pfid = "";

        SocialPFlow[] arrPf = pfmgr.getFlowsToSendContent(postOut);
        if (arrPf.length == 1) {
            pfid = arrPf[0].getId();
        }
        SWBResourceURL url2flow = paramRequest.getActionUrl();
        url2flow.setAction("send2flow");

        out.println("<div class=\"swbform\">");
        out.println("<form id=\"" + resid + "/" + getResourceBase().getId() + "/PFComment\" action=\"" + url2flow + "\" method=\"post\" onsubmit=\"submitForm('" + resid + "/" + getResourceBase().getId() + "/PFComment');return false;\">"); //reloadTab('" + id + "');
        out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
        out.println("<input type=\"hidden\" name=\"sval\" value=\"" + resid + "\">");
        out.println("<fieldset>");
        out.println("<table>");
        out.println("<tbody>");
        out.println("<tr>");
        out.println("<td>");
        out.println(paramRequest.getLocaleString("comentary"));
        out.println("</td>");
        out.println("<td>");
        out.println("<input type=\"text\" name=\"usrmsg\" value=\"\" dojoType=\"dijit.form.TextBox\" required=\"true\"	");
        out.println(" promptMessage=\"" + paramRequest.getLocaleString("commentsend2flow") + "\"  />");
        out.println("</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>");
        out.println(paramRequest.getLocaleString("publishflow"));
        out.println("</td>");
        out.println("<td>");
        out.println("<select name=\"pfid\">");
        for (int i = 0; i < arrPf.length; i++) {
            //System.out.println("arrPf[i].getURI():"+arrPf[i].getURI());
            String select = "";
            if (postOutFlowUri != null && postOutFlowUri.equals(arrPf[i].getURI())) {
                select = "selected";
            }

            out.println("<option value=\"" + arrPf[i].getURI() + "\"" + select + ">" + arrPf[i].getDisplayTitle(user.getLanguage()) + "</option>");
        }
        out.println("</select>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</tbody>");
        out.println("</table>");
        out.println("</filedset>");
        out.println("<filedset>");

        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" >" + paramRequest.getLocaleString("btnSend2flow") + "</button>"); //_onclick=\"submitForm('"+id+"/"+idvi+"/"+base.getId()+"/FVIComment');return false;\"

        out.println("<button dojoType=\"dijit.form.Button\" onclick=\"hideDialog(); return false;\">" + paramRequest.getLocaleString("btnCancel") + "</button>"); //submitUrl('" + urlb + "',this.domNode); hideDialog();
        out.println("</filedset>");
        out.println("</form>");
        out.println("</div>");

    }

    /*
     * Show the source message of One PostOut that comes as a parameter "postUri"
     */
    /*
     public void doShowSource(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
     System.out.println("Entra a doShowSourceJ-21/Jun-1");
     response.setContentType("text/html;charset=iso-8859-1");
     response.setHeader("Cache-Control", "no-cache");
     response.setHeader("Pragma", "no-cache");
     final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/showPostIn.jsp";
     if (request != null) {
     RequestDispatcher dis = request.getRequestDispatcher(myPath);
     if (dis != null) {
     try {
     SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
     System.out.println("Entra a doShowSourceJ-21/Jun-1:"+semObject);
     request.setAttribute("postIn", semObject);
     request.setAttribute("paramRequest", paramRequest);
     dis.include(request, response);
     } catch (Exception e) {
     log.error(e);
     e.printStackTrace(System.out);
     }
     }
     }
     }
     */
    /*
     * Show the source message of One PostOut that comes as a parameter "postUri"
     */
    public void doEditPost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (request.getParameter("postOut") != null && request.getParameter("wsite") != null) {
            response.setContentType("text/html;charset=iso-8859-1");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");

            final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/post/typeOfContent.jsp";

            RequestDispatcher dis = request.getRequestDispatcher(myPath);
            if (dis != null) {
                try {
                    request.setAttribute("objUri", request.getParameter("postOut"));
                    request.setAttribute("wsite", request.getParameter("wsite"));
                    request.setAttribute("paramRequest", paramRequest);
                    dis.include(request, response);
                } catch (Exception e) {
                    log.error(e);
                    e.printStackTrace(System.out);
                }
            }
        }
    }

    /**
     * Gets the string of display name property of a semantic object.
     *
     * @param obj the obj
     * @param lang the lang
     * @return a string value of the DisplayName property
     */
    public String getDisplaySemObj(SemanticObject obj, String lang) {
        String ret = obj.getRDFName();
        try {
            ret = obj.getDisplayName(lang);
        } catch (Exception e) {
            ret = "---";
            //ret = obj.getProperty(Descriptiveable.swb_title);
        }
        return ret;
    }

    /*
     * Muestra los datos de un usuario
     */
    private void doShowUserHistory(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
        final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/adminUserHistory.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        if (dis != null) {
            try {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("swbAdminUser"));
                request.setAttribute("swbAdminUser", semObject);
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    /*
     * Muestra todas las redes sociales a las que se envío el mensaje de salida
     */
    private void doShowMoreNets(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
        final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/showMoreNets.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        if (dis != null) {
            try {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                request.setAttribute("postOut", semObject);
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
            
    /*
     * Muestra FastCalendar de un PostOut, en dado caso de que tenga uno
     */
    private void doShowFastCalendar(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
        final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/showFastCalendar.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        if (dis != null) {
            try {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                request.setAttribute("postOut", semObject);
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }            
            

    /**
     * Review sem prop.
     *
     * @param prop the prop
     * @param obj the obj
     * @param paramsRequest the params request
     * @return the string
     */
    public String reviewSemProp(SemanticProperty prop, SemanticObject obj, SWBParamRequest paramsRequest) {
        String ret = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.", new Locale(paramsRequest.getUser().getLanguage()));
            if (prop.isDataTypeProperty()) {
                if (prop.isBoolean()) {
                    boolean bvalue = obj.getBooleanProperty(prop);
                    if (bvalue) {
                        ret = paramsRequest.getLocaleString("booleanYes");
                    } else {
                        ret = paramsRequest.getLocaleString("booleanNo");
                    }

                }
                if (prop.isInt() || prop.isDouble() || prop.isLong()) {
                    ret = Long.toString(obj.getLongProperty(prop));
                }
                if (prop.isString()) {
                    ret = obj.getProperty(prop);
                }
                if (prop.isFloat()) {
                    ret = Float.toString(obj.getFloatProperty(prop));
                }
                if (prop.isDate() || prop.isDateTime()) {
                    ret = sdf.format(obj.getDateTimeProperty(prop));
                }
            } else if (prop.isObjectProperty()) {
                SemanticObject so = obj.getObjectProperty(prop);
                if (null != so) {
                    ret = so.getDisplayName(paramsRequest.getUser().getLanguage());
                }
            }
            if (null == ret || (ret != null && ret.trim().equals("null"))) {
                ret = "";
            }

        } catch (Exception e) {
        }
        return ret;
    }

    /**
     * Do an update, update status, active or unactive action of a Content
     * element requested by the user.
     *
     * @param request , this holds the parameters
     * @param response , an answer to the user request
     * @param paramRequest , a list of objects like user, webpage, Resource, ...
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doAction(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        //log.debug("doAction()");
        User user = paramRequest.getUser();
        PrintWriter out = response.getWriter();
        String id = request.getParameter("suri");
        String sprop = request.getParameter("sprop");
        //String sproptype = request.getParameter("sproptype");
        String action = request.getParameter("act");
        String errormsg = "", actmsg = "";
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(id); //WebPage
        SemanticClass cls = obj.getSemanticClass();

        StringBuffer sbreload = new StringBuffer("");
        SemanticObject so = null;
        if ("update".equals(action)) {
            try {
                if (request.getParameter("sval") != null) {
                    obj = ont.getSemanticObject(request.getParameter("sval"));
                }
                cls = obj.getSemanticClass();
                Iterator<SemanticProperty> it = cls.listProperties();
                while (it.hasNext()) {
                    SemanticProperty prop = it.next();
                    if (prop.isDataTypeProperty()) {
                        String value = request.getParameter(prop.getName()); //prop.getName()
                        log.debug("doAction(update): " + prop.getName() + " -- >" + value);
                        if (value != null) {
                            if (value.length() > 0) {
                                if (prop.isBoolean()) {
                                    if (value.equals("true") || value.equals("1")) {
                                        obj.setBooleanProperty(prop, true);
                                    } else if (value.equals("false") || value.equals("0")) {
                                        obj.setBooleanProperty(prop, false);
                                    }
                                }
                                if (prop.isInt()) {
                                    obj.setIntProperty(prop, Integer.parseInt(value));
                                }
                                if (prop.isLong()) {
                                    obj.setLongProperty(prop, Long.parseLong(value));
                                }
                                if (prop.isString()) {
                                    obj.setProperty(prop, value);
                                }
                                if (prop.isFloat()) {
                                    obj.setFloatProperty(prop, Float.parseFloat(value));
                                }

                            } else {
                                obj.removeProperty(prop);
                            }
                        }
                    }
                }
                so = obj;
                actmsg = paramRequest.getLocaleString("upd_priority");
            } catch (Exception e) {
                log.error(e);
                errormsg = paramRequest.getLocaleString("statERRORmsg1");
            }
        } else if ("updstatus".equals(action)) {
            if (obj != null) {
                PostOut postOut = (PostOut) obj.createGenericInstance();
                try {
                    SWBSocialUtil.PostOutUtil.publishPost(postOut);
                    //TODOSOCIAL:Probar si con esto funciona
                    //System.out.println("En SocialSentPost/doAction-postOut:"+postOut+",postOut.getPflowInstance():"+postOut.getPflowInstance()+",status:"+postOut.getPflowInstance());
                    if (postOut.getPflowInstance() != null) {
                        postOut.getPflowInstance().setStatus(2);
                        postOut.getPflowInstance().setStep(null);
                    }
                    if(postOut.getFastCalendar()!=null)
                    {
                        try
                        {
                            org.semanticwb.social.FastCalendar fastCalendar=postOut.getFastCalendar();
                            postOut.removeFastCalendar();
                            fastCalendar.remove();
                        }catch(Exception ignore)
                        {
                            
                        }
                    }
                    //Termina
                    //postOut.setPublished(true); 
                    //System.out.println("Post Publicado...");
                    so = postOut.getSocialTopic().getSemanticObject();
                } catch (Exception se) {
                    actmsg = (paramRequest.getLocaleString("postOutNotPublished"));
                    log.error(se);
                }
            }
            /*
             String soid = request.getParameter("sval");
             String value = request.getParameter("val");
             try {
             if (value == null) {
             value = "0";
             }
             SemanticObject sobj = ont.getSemanticObject(soid);
             sobj.setBooleanProperty(Activeable.swb_active, value.equals("true") ? true : false);

             SemanticClass scls = sobj.getSemanticClass();
             log.debug("doAction(updstatus):" + scls.getClassName() + ": " + value);
             so = sobj;
             actmsg = (value.equals("true") ? paramRequest.getLocaleString("upd_active") : paramRequest.getLocaleString("upd_unactive"));
             } catch (Exception e) {
             log.error(e);
             errormsg = (value.equals("true") ? paramRequest.getLocaleString("statERRORmsg2") : paramRequest.getLocaleString("statERRORmsg3"));
             }
             * */
        } // revisar para agregar nuevo semantic object
        else if ("activeall".equals(action)) {
            log.debug("doAction(activeeall)" + sprop);
            String value = request.getParameter("sval");
            //System.out.println("doAction(activeeall)"+value);
            boolean bstat = false;
            if (value != null && "true".equals(value)) {
                bstat = true;
            }


            //System.out.println("processAction(deleteall)" + value);

            PFlowManager pfmgr = SWBPortal.getPFlowManager();
            Resource res = null;
            boolean isInFlow = false;
            boolean isAuthorized = false;
            boolean needAuthorization = false;
            boolean activeButton = false;

            SemanticProperty sem_p = ont.getSemanticProperty(sprop);
            so = obj.getObjectProperty(sem_p);
            Iterator<SemanticObject> itso = obj.listObjectProperties(sem_p);
            SemanticObject soc = null;
            while (itso.hasNext()) {
                //System.out.println("revisando deleteAll (ListObjectsProperties)");
                soc = itso.next();

                isInFlow = false;
                isAuthorized = false;
                needAuthorization = false;
                activeButton = true;
                //send2Flow = false;

                res = (Resource) soc.createGenericInstance();

                isInFlow = pfmgr.isInFlow(res);
                needAuthorization = pfmgr.needAnAuthorization(res);

                if (!isInFlow && !needAuthorization) {
                    activeButton = true;
                }
                if (!isInFlow && needAuthorization) {
                    activeButton = false;
                    //send2Flow = true;
                }

                if (isInFlow) {
                    isAuthorized = pfmgr.isAuthorized(res);
                    PFlowInstance instance = res.getPflowInstance();
                    if (!isAuthorized || instance.getStatus() == 3) {  // estatus 3 = rechazado
                        activeButton = false;
                    }
                    if (isAuthorized) {
                        activeButton = true;
                    }
                }

                try {
                    if (activeButton) {
                        if (bstat) {
                            soc.setBooleanProperty(Activeable.swb_active, true);
                        } else {
                            soc.removeProperty(Activeable.swb_active);
                        }
                        sbreload.append("\n reloadTab('" + soc.getURI() + "'); ");
                        sbreload.append("\n setTabTitle('" + soc.getURI() + "','" + SWBUtils.TEXT.scape4Script(soc.getDisplayName(user.getLanguage())) + "','" + SWBContext.UTILS.getIconClass(soc) + "');");

                    }
                } catch (Exception e) {
                    log.error(e);
                    errormsg = (value.equals("true") ? paramRequest.getLocaleString("statERRORmsg2") : paramRequest.getLocaleString("statERRORmsg3"));
                }
            }
            so = obj;
            actmsg = (value.equals("true") ? paramRequest.getLocaleString("upd_active") : paramRequest.getLocaleString("upd_unactive"));
        }

        if (errormsg.length() == 0) {
            out.println("<script type=\"text/javascript\">");
            out.println(" reloadTab('" + so.getURI() + "');");//so
            out.println(" setTabTitle('" + so.getURI() + "','" + SWBUtils.TEXT.scape4Script(so.getDisplayName(user.getLanguage())) + "','" + SWBContext.UTILS.getIconClass(so) + "')");
            out.println(sbreload.toString());
            out.println("   showStatus('" + actmsg + "');");
            out.println("</script>");
            //out.println(actmsg);
        } else {
            out.println(errormsg);
        }
    }

    /**
     * Do a specific action like add, remove, send to a publish flow, delete the
     * reference between WebPage and Content.
     *
     * @param request , this holds the parameters
     * @param response , an answer to the user request, and a list of objects
     * like user, webpage, Resource, ...
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {

        String action = response.getAction();
        //System.out.println("SocialSentPost/processAction-1:" + action);
        try {
            if (action.equals("postMessage") || action.equals("uploadPhoto") || action.equals("uploadVideo")) {
                try {
                    //System.out.println("SocialSentPost/processAction-2");
                    ArrayList aSocialNets = new ArrayList();
                    WebSite wsite = WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));
                    String objUri = request.getParameter("objUri");
                    SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
                    PostIn postIn = null;
                    PostOut postOut = null;
                    if(semanticObject.createGenericInstance() instanceof PostIn){
                        postIn = (PostIn) SemanticObject.getSemanticObject(request.getParameter("objUri")).createGenericInstance();
                    }else if(semanticObject.createGenericInstance() instanceof PostOut){
                        postOut = (PostOut) semanticObject.createGenericInstance();
                    }                    
                    
                    //System.out.println("SocialSentPost/processAction-3:"+postOut);
                    SocialPFlow spflow = null;
                    //System.out.println("processA/socialFlow:"+request.getParameter("socialFlow"));
                    if (request.getParameter("socialFlow") != null && request.getParameter("socialFlow").trim().length() > 0) {
                        SemanticObject semObjSFlow = SemanticObject.getSemanticObject(request.getParameter("socialFlow"));
                        spflow = (SocialPFlow) semObjSFlow.createGenericInstance();
                    }

                    String toPost = request.getParameter("toPost");

                    String socialUri = "";
                    int j = 0;
                    Enumeration<String> enumParams = request.getParameterNames();
                    while (enumParams.hasMoreElements()) {
                        String paramName = enumParams.nextElement();
                        if (paramName.startsWith("http://")) {
                            if (socialUri.trim().length() > 0) {
                                socialUri += "|";
                            }
                            socialUri += paramName;
                            j++;
                        }
                    }
                    //System.out.println("SocialSentPost/processAction-4:"+socialUri);
                    if (socialUri.trim().length() > 0) // La publicación por lo menos se debe enviar a una red social
                    {
                        String[] socialUris = socialUri.split("\\|");  //Dividir valores
                        for (int i = 0; i < socialUris.length; i++) {
                            String tmp_socialUri = socialUris[i];
                            SemanticObject semObject = SemanticObject.createSemanticObject(tmp_socialUri, wsite.getSemanticModel());
                            SocialNetwork socialNet = (SocialNetwork) semObject.createGenericInstance();
                            //Se agrega la red social de salida al post
                            aSocialNets.add(socialNet);
                        }
                        //SWBSocialUtil.PostOutUtil.publishPost(postOut, request, response);
                        //System.out.println("SocialSentPost/processAction-J5");
                        //SWBSocialUtil.PostOutUtil.editPostOut(postOut, spflow, aSocialNets, wsite, toPost, request, response);
                        if(postOut != null){
                            SWBSocialUtil.PostOutUtil.editPostOut(postOut, spflow, aSocialNets, wsite, toPost, request, response);
                            response.setMode(SWBResourceURL.Mode_EDIT);
                            response.setRenderParameter("dialog", "close");
                            response.setRenderParameter("statusMsg", SWBUtils.TEXT.encode(response.getLocaleLogString("postModified"), "utf8"));
                            response.setRenderParameter("reloadTab", postOut.getSocialTopic().getURI());
                            response.setRenderParameter("suri", postOut.getSocialTopic().getURI());
                        }else if(postIn != null){
                            SWBSocialUtil.PostOutUtil.sendNewPost(postIn, postIn.getSocialTopic(), spflow, aSocialNets, wsite, toPost, request, response);
                        }
                        //System.out.println("SocialSentPost/processAction-J6-suri:"+postOut.getSocialTopic().getURI());
                    } else {
                        if(postOut != null){
                            response.setMode(SWBResourceURL.Mode_EDIT);
                            response.setRenderParameter("dialog", "close");
                            response.setRenderParameter("statusMsg", response.getLocaleLogString("postTypeNotDefined"));
                            response.setRenderParameter("reloadTab", postOut.getSocialTopic().getURI());
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    e.printStackTrace();
                }


                ///////////////////////////////
                //response.setRenderParameter("statmsg", response.getLocaleString("statmsg1"));
                //response.setMode(response.Mode_EDIT);
                //response.setRenderParameter("act", "");
                response.setMode("postSent");
            } else if ("remove".equals(action)) //suri, prop
            {
                String sval = request.getParameter("sval");
                //System.out.println("SocialSentPost-REMOVEj1:"+sval);
                SemanticObject so = SemanticObject.createSemanticObject(sval);

                PostOut postOut = (PostOut) so.getGenericInstance();

                //System.out.println("SocialSentPost-REMOVEj2:"+postOut);

                postOut.remove();

                //System.out.println("SocialSentPost-REMOVEj3-LISTO");

                response.setRenderParameter("dialog", "close");
                response.setRenderParameter("suri", request.getParameter("suri"));
                response.setRenderParameter("statmsg", response.getLocaleString("postDeleted"));
                response.setMode(SWBActionResponse.Mode_EDIT);
            } else if ("send2flow".equals(action)) {
                String id = request.getParameter("suri");
                SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
                //SemanticObject obj = SemanticObject.createSemanticObject(id); //WebPage
                //SemanticClass cls = obj.getSemanticClass();
                SocialPFlowMgr pfmgr = SocialLoader.getPFlowManager();
                String sval = request.getParameter("sval"); // id resource
                String pfid = request.getParameter("pfid"); // id pflow
                String usermessage = request.getParameter("usrmsg"); // mensaje del usuario
                SocialPFlow pf = (SocialPFlow) ont.getGenericObject(pfid);
                PostOut res = (PostOut) ont.getGenericObject(sval);

                pfmgr.sendResourceToAuthorize(res, pf, usermessage);

                response.setMode(SWBResourceURL.Mode_EDIT);
                response.setRenderParameter("dialog", "close");
                response.setRenderParameter("statusMsg", SWBUtils.TEXT.encode(response.getLocaleLogString("sendContent2Flow"), "utf8"));
                response.setRenderParameter("reloadTab", id);
                response.setRenderParameter("suri", id);
            } else if ("deleteall".equals(action)) {
                String id = request.getParameter("suri");
                String sprop = request.getParameter("sprop");
                String sproptype = request.getParameter("sproptype");
                SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
                SemanticObject obj = SemanticObject.createSemanticObject(id); //WebPage
                SemanticClass cls = obj.getSemanticClass();
                SemanticProperty sem_p = ont.getSemanticProperty(sprop);
                SemanticObject so = obj.getObjectProperty(sem_p);
                Iterator<SemanticObject> itso = obj.listObjectProperties(sem_p);
                SemanticObject soc = null;
                while (itso.hasNext()) {
                    //System.out.println("revisando deleteAll (ListObjectsProperties)");
                    soc = itso.next();
                    Iterator<SemanticProperty> it = cls.listProperties();
                    while (it.hasNext()) {
                        SemanticProperty prop = it.next();
                        //System.out.println("revisando (ListProperties("+sprop+")) "+ prop.getName());
                        String value = prop.getName();
                        //System.out.println(sem_p.getURI() + ":" + sprop + "----" + (prop.getURI().equals(sprop) ? "true" : "false"));
                        if (value != null && value.equals(sem_p.getName())) { //se tiene que validar el valor por si es más de una
                            //obj.removeObjectProperty(prop, soc);
                            if (sem_p.getName().equalsIgnoreCase("userrepository")) {
                                obj.setObjectProperty(prop, ont.getSemanticObject("urswb"));
                            }

                            boolean trash = false;
                            if (soc.instanceOf(Trashable.swb_Trashable)) {
                                boolean del = soc.getBooleanProperty(Trashable.swb_deleted);
                                if (!del) {
                                    trash = true;
                                }
                            }
                            if (!trash) {
                                soc.remove();
                            } else {
                                soc.setBooleanProperty(Trashable.swb_deleted, true);
                            }


                            //                        if(soc.getSemanticClass().isSubClass(Trashable.swb_Trashable))
                            //                        {
                            //                            System.out.println("Es trashable.. delAll.."+soc.getURI());
                            //                            soc.setIntProperty(Trashable.swb_deleted, 1);
                            //                        }
                            //                        else
                            //                        {
                            //                            soc.remove();
                            //                        }

                            break;
                        }
                    }

                }
                if (sproptype != null) {
                    response.setRenderParameter("sproptype", sproptype);
                }
                response.setMode(SWBActionResponse.Mode_EDIT);
                response.setRenderParameter("suri", id);
                response.setRenderParameter("sprop", sprop);
            } else if (action.equals("publicByPostOut")) {
                if (request.getParameter("postOutUri") != null) {
                    SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("postOutUri"));
                    if (semObj != null) {
                        PostOutNet postOutNet = (PostOutNet) semObj.getGenericInstance();
                        PostOut postOut = postOutNet.getSocialPost();
                        SocialNetwork socialNet = postOutNet.getSocialNetwork();
                        if (postOut != null && socialNet != null) {
                            SWBSocialUtil.PostOutUtil.publishPostOutNet(postOut, socialNet);
                        }
                        response.setMode(SWBResourceURL.Mode_EDIT);
                        response.setRenderParameter("dialog", "close");
                        response.setRenderParameter("statusMsg", SWBUtils.TEXT.encode(response.getLocaleLogString("postOutPublished"), "utf8"));
                        response.setRenderParameter("reloadTab", postOut.getSocialTopic().getURI());
                        response.setRenderParameter("suri", postOut.getSocialTopic().getURI());
                    }
                }
            } else if (action.equals("removePostOutNet") && request.getParameter("postOutNetUri") != null) {
                //System.out.println("SocialSentPost/postOutNetUri");
                SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("postOutNetUri"));
                PostOutNet postOutNet = (PostOutNet) semObj.getGenericInstance();
                if (postOutNet != null) {
                    //System.out.println("SocialSentPost/postOutNetUri a la goma");
                    postOutNet.remove();
                }
                response.setRenderParameter("suri", request.getParameter("suri"));
                response.setRenderParameter("postOut", request.getParameter("postOut"));
            } else if (action.equals("showPhotos")) {
                response.setMode("showPhotos");
                response.setRenderParameter("postOut", request.getParameter("postOut"));
                
            }else if (action.equals("deletePhoto")) {
                response.setRenderParameter("postOut", request.getParameter("postOut"));
                doDeletePhoto(request, response);
            }else if (action.equals("uploadFastCalendar"))
            {
                if(request.getParameter("postOut")!=null)
                {
                    SemanticObject semObj=SemanticObject.getSemanticObject(request.getParameter("postOut"));
                    PostOut postOut=(PostOut)semObj.getGenericInstance();
                    
                    String postOutInitDate=request.getParameter("postOut_inidate");
                    String postOutInitHour=request.getParameter("postOut_starthour");
                    
                    if(postOutInitDate!=null && postOutInitDate.trim().length()>0)
                    {
                        FastCalendar fastCalendar=null;
                        if(postOut.getFastCalendar()!=null)
                        {
                            fastCalendar=postOut.getFastCalendar();
                        }
                        if(fastCalendar==null)
                        {
                           WebSite wsite=WebSite.ClassMgr.getWebSite(postOut.getSemanticObject().getModel().getName());
                           fastCalendar=FastCalendar.ClassMgr.createFastCalendar(wsite);
                        }
                            
                        postOutInitDate=SWBSocialUtil.Util.changeFormat(postOutInitDate, 1);
                        postOutInitHour = postOutInitHour.substring(1, 6);
                        
                        //System.out.println("postOutInitDate-1**:"+postOutInitDate+",postOutInitHour-1**:"+postOutInitHour);
                    
                        
                        Date date2SendPostOut=new Date(postOutInitDate);
                        
                        StringTokenizer st   = new StringTokenizer(postOutInitHour, ":");
                        int             h    = 0,
                                        m    = 0;
                        try {
                            h = Integer.parseInt(st.nextToken());

                            if (st.hasMoreTokens()) {
                                m = Integer.parseInt(st.nextToken());
                            }                           
                        } catch (Exception noe) {
                            // No error
                        }
                        //System.out.println("H a poner:"+h);
                        //System.out.println("M a poner:"+m);
                        date2SendPostOut.setHours(h);
                        date2SendPostOut.setMinutes(m);
                        
                        fastCalendar.setFc_date(date2SendPostOut);
                        postOut.setFastCalendar(fastCalendar);
                    }else{
                        if(postOut.getFastCalendar()!=null)
                        {
                            FastCalendar fastCalendar=postOut.getFastCalendar();
                            postOut.removeFastCalendar();
                            fastCalendar.remove();
                        }
                    }
                    response.setMode(SWBResourceURL.Mode_EDIT);
                    response.setRenderParameter("suri", postOut.getSocialTopic().getURI());
                    response.setRenderParameter("dialog", "close");
                    response.setRenderParameter("statusMsg", response.getLocaleLogString("calendarUpdated"));
                    response.setRenderParameter("reloadTab", postOut.getSocialTopic().getURI());
                }
            }
            
        } catch (Exception e) {
            log.error(e);
        }
    }

    /*
     * Method which calls a jsp to generate a report based on the result of registers in this class
     */
    private void doGenerateReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {

        String pages = request.getParameter("pages");
        int page = Integer.parseInt(pages);
        String searchWord = request.getParameter("search");
        if (null == searchWord) {
            searchWord = "";
        }
        String swbSocialUser = request.getParameter("swbSocialUser");
        String id = request.getParameter("suri");

        SocialTopic socialTopic = (SocialTopic) SemanticObject.getSemanticObject(id).getGenericInstance();
        WebSite webSite = WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());

        HashMap hmapResult = filtros(swbSocialUser, webSite, searchWord, request, socialTopic, page);

        //long nRec = ((Long) hmapResult.get("countResult")).longValue();
        Iterator<PostOut> setso = ((Iterator) hmapResult.get("itResult"));


        String classifyBySentiment = SWBSocialUtil.Util.getModelPropertyValue(webSite, SWBSocialUtil.CLASSIFYSENTMGS_PROPNAME);


        try {

            createExcel(setso, paramRequest, classifyBySentiment, response, socialTopic);

        } catch (Exception e) {
            log.error(e);
        }
    }

    private void createExcel(Iterator<PostOut> setso, SWBParamRequest paramRequest, String classifyBySentiment, HttpServletResponse response, SocialTopic socialTopic) {
        try {

            // Defino el Libro de Excel
            //Iterator v = setso.iterator();
            String title = socialTopic.getTitle();

            HSSFWorkbook wb = new HSSFWorkbook();

            // Creo la Hoja en Excel
            Sheet sheet = wb.createSheet("Mensajes " + title);


            sheet.setDisplayGridlines(false);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

            // creo una nueva fila
            Row trow = sheet.createRow((short) 0);
            createTituloCell(wb, trow, 0, CellStyle.ALIGN_CENTER,
                    CellStyle.VERTICAL_CENTER, "Mensajes " + title);

            // Creo la cabecera de mi listado en Excel
            Row row = sheet.createRow((short) 2);

            // Creo las celdas de mi fila, se puede poner un diseño a la celda
            createHead(wb, row, 0, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Post");
            createHead(wb, row, 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Tipo");
            createHead(wb, row, 2, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Redes");
            createHead(wb, row, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Origen");
            createHead(wb, row, 4, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Creación");
            createHead(wb, row, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Última actualización");
            if (classifyBySentiment != null && classifyBySentiment.equalsIgnoreCase("true")) {
                createHead(wb, row, 6, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Sentimiento");
                createHead(wb, row, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Intensidad");
                createHead(wb, row, 8, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Estatus");
            } else {

                createHead(wb, row, 6, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Estatus");
            }




            String lang = paramRequest.getUser().getLanguage();


            //Número de filas
            int i = 3;
            CellStyle cellStyle = wb.createCellStyle();

            SocialPFlowMgr pfmgr = SocialLoader.getPFlowManager();
            boolean isInFlow = false;
            boolean isAuthorized = false;
            boolean needAuthorization = false;
            //boolean send2Flow = false;

            while (setso != null && setso.hasNext()) {
                PostOut postIn = (PostOut) setso.next();

                Row troww = sheet.createRow((short) i);

                if (postIn.getMsg_Text() != null) {
                    createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, postIn.getMsg_Text());

                }
                createCell(cellStyle, wb, troww, 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn instanceof Message ? paramRequest.getLocaleString("message") : postIn instanceof Photo ? paramRequest.getLocaleString("photo") : postIn instanceof Video ? paramRequest.getLocaleString("video") : "---");

                String nets = "---";
                boolean firstTime = true;
                Iterator<SocialNetwork> itPostSocialNets = postIn.listSocialNetworks();
                while (itPostSocialNets.hasNext()) {
                    SocialNetwork socialNet = itPostSocialNets.next();
                    //System.out.println("socialNet-1:"+socialNet);
                    String sSocialNet = socialNet.getDisplayTitle(lang);
                    //System.out.println("socialNet-2:"+sSocialNet);
                    if (sSocialNet != null && sSocialNet.trim().length() > 0) {
                        //System.out.println("socialNet-3:"+sSocialNet);
                        if (firstTime) {
                            nets = "" + sSocialNet;
                            firstTime = false;
                        } else {
                            nets += "|" + sSocialNet;
                        }
                    }
                }

                createCell(cellStyle, wb, troww, 2, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, nets);

                if (postIn.getPostInSource() != null) {
                    createCell(cellStyle, wb, troww, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Origen(Image)");

                } else {
                    createCell(cellStyle, wb, troww, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "----");
                }


                createCell(cellStyle, wb, troww, 4, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, SWBUtils.TEXT.getTimeAgo(postIn.getCreated(), lang));

                createCell(cellStyle, wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, SWBUtils.TEXT.getTimeAgo(postIn.getUpdated(), lang));


                if (classifyBySentiment != null && classifyBySentiment.equalsIgnoreCase("true")) {
                    //Sentiment

                    if (postIn.getPostSentimentalType() == 0) {
                        createCell(cellStyle, wb, troww, 6, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "----");
                    } else if (postIn.getPostSentimentalType() == 1) {
                        createCell(cellStyle, wb, troww, 6, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Positivo");
                    } else if (postIn.getPostSentimentalType() == 2) {
                        createCell(cellStyle, wb, troww, 6, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Negativo");
                    }



                    if (postIn.getPostIntesityType() == 1) {
                        createCell(cellStyle, wb, troww, 7, CellStyle.ALIGN_CENTER,
                                CellStyle.VERTICAL_CENTER, "Medium");

                    } else if (postIn.getPostIntesityType() == 2) {
                        createCell(cellStyle, wb, troww, 7, CellStyle.ALIGN_CENTER,
                                CellStyle.VERTICAL_CENTER, "High");
                    } else if (postIn.getPostIntesityType() == 0) {

                        createCell(cellStyle, wb, troww, 7, CellStyle.ALIGN_CENTER,
                                CellStyle.VERTICAL_CENTER, "---");
                    }

                    if (!postIn.isPublished()) {

                        boolean postOutwithPostOutNets = false;
                        boolean someOneIsNotPublished = false;
                        Iterator<PostOutNet> itPostOutNets = PostOutNet.ClassMgr.listPostOutNetBySocialPost(postIn, paramRequest.getWebPage().getWebSite());
                        while (itPostOutNets.hasNext()) {
                            PostOutNet postOutNet = itPostOutNets.next();
                            postOutwithPostOutNets = true;
                            if (postOutNet.getStatus() == 0) {
                                someOneIsNotPublished = true;
                                break;
                            }
                        }
                        if (!isInFlow && postOutwithPostOutNets && !someOneIsNotPublished) //Se supone que por lo menos, hay publicado un PostOutNet del Post                         
                        {
                            createCell(cellStyle, wb, troww, 8, CellStyle.ALIGN_CENTER,
                                    CellStyle.VERTICAL_CENTER, "Publicado");
                        } else {
                            if (!needAuthorization) {


                                if (someOneIsNotPublished) {
                                    createCell(cellStyle, wb, troww, 8, CellStyle.ALIGN_CENTER,
                                            CellStyle.VERTICAL_CENTER, "A revisar");
                                } else {
                                    createCell(cellStyle, wb, troww, 8, CellStyle.ALIGN_CENTER,
                                            CellStyle.VERTICAL_CENTER, "Publicar");
                                }
                            } else {    //El PostOut ya se envío
                                if (!isInFlow && needAuthorization && !isAuthorized) {
                                    String sFlowRejected = "---";
                                    if (postIn.getPflowInstance() != null && postIn.getPflowInstance().getPflow() != null) {
                                        sFlowRejected = postIn.getPflowInstance().getPflow().getDisplayTitle(lang);
                                    }
                                    createCell(cellStyle, wb, troww, 8, CellStyle.ALIGN_CENTER,
                                            CellStyle.VERTICAL_CENTER, "Rechazado");
                                } else if (isInFlow && needAuthorization && !isAuthorized) {
                                    createCell(cellStyle, wb, troww, 8, CellStyle.ALIGN_CENTER,
                                            CellStyle.VERTICAL_CENTER, "En flujo");
                                }
                            }
                        }
                    } else {
                        //System.out.println("ESE POST ESTA PUBLICADO..");
                        createCell(cellStyle, wb, troww, 8, CellStyle.ALIGN_CENTER,
                                CellStyle.VERTICAL_CENTER, "Publicado");
                    }
                } else {


                    if (!postIn.isPublished()) {

                        boolean postOutwithPostOutNets = false;
                        boolean someOneIsNotPublished = false;
                        Iterator<PostOutNet> itPostOutNets = PostOutNet.ClassMgr.listPostOutNetBySocialPost(postIn, paramRequest.getWebPage().getWebSite());
                        while (itPostOutNets.hasNext()) {
                            PostOutNet postOutNet = itPostOutNets.next();
                            postOutwithPostOutNets = true;
                            if (postOutNet.getStatus() == 0) {
                                someOneIsNotPublished = true;
                                break;
                            }
                        }
                        if (!isInFlow && postOutwithPostOutNets && !someOneIsNotPublished) //Se supone que por lo menos, hay publicado un PostOutNet del Post                         
                        {
                            createCell(cellStyle, wb, troww, 6, CellStyle.ALIGN_CENTER,
                                    CellStyle.VERTICAL_CENTER, "Publicado");
                        } else {
                            if (!needAuthorization) {


                                if (someOneIsNotPublished) {
                                    createCell(cellStyle, wb, troww, 6, CellStyle.ALIGN_CENTER,
                                            CellStyle.VERTICAL_CENTER, "A revisar");
                                } else {
                                    createCell(cellStyle, wb, troww, 6, CellStyle.ALIGN_CENTER,
                                            CellStyle.VERTICAL_CENTER, "Publicar");
                                }
                            } else {    //El PostOut ya se envío
                                if (!isInFlow && needAuthorization && !isAuthorized) {
                                    String sFlowRejected = "---";
                                    if (postIn.getPflowInstance() != null && postIn.getPflowInstance().getPflow() != null) {
                                        sFlowRejected = postIn.getPflowInstance().getPflow().getDisplayTitle(lang);
                                    }
                                    createCell(cellStyle, wb, troww, 6, CellStyle.ALIGN_CENTER,
                                            CellStyle.VERTICAL_CENTER, "Rechazado");
                                } else if (isInFlow && needAuthorization && !isAuthorized) {
                                    createCell(cellStyle, wb, troww, 6, CellStyle.ALIGN_CENTER,
                                            CellStyle.VERTICAL_CENTER, "En flujo");
                                }
                            }
                        }
                    } else {
                        //System.out.println("ESE POST ESTA PUBLICADO..");
                        createCell(cellStyle, wb, troww, 6, CellStyle.ALIGN_CENTER,
                                CellStyle.VERTICAL_CENTER, "Publicado");
                    }


                }

                i++;


            }

            // Definimos el tamaño de las celdas, podemos definir un tamaña especifico o hacer que 
            //la celda se acomode según su tamaño
            Sheet ssheet = wb.getSheetAt(0);
            //ssheet.setColumnWidth(0, 256 * 40);
            ssheet.autoSizeColumn(0);
            ssheet.autoSizeColumn(1);
            ssheet.autoSizeColumn(2);
            ssheet.autoSizeColumn(3);
            ssheet.autoSizeColumn(4);
            ssheet.autoSizeColumn(5);
            ssheet.autoSizeColumn(6);
            ssheet.autoSizeColumn(7);
            ssheet.autoSizeColumn(8);
            ssheet.autoSizeColumn(9);
            ssheet.autoSizeColumn(10);


            OutputStream ou = response.getOutputStream();
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Content-Disposition", "attachment; filename=\"Mensajes.xls\";");
            response.setContentType("application/octet-stream");
            wb.write(ou);
            ou.close();


        } catch (Exception e) {
            log.error(e);
        }
    }

    public static void createTituloCell(HSSFWorkbook wb, Row row, int column, short halign, short valign, String strContenido) {


        CreationHelper ch = wb.getCreationHelper();
        Cell cell = row.createCell(column);
        cell.setCellValue(ch.createRichTextString(strContenido));

        HSSFFont cellFont = wb.createFont();
        cellFont.setFontHeightInPoints((short) 11);
        cellFont.setFontName(HSSFFont.FONT_ARIAL);
        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setFont(cellFont);
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cell.setCellStyle(cellStyle);

    }

    public static void createHead(HSSFWorkbook wb, Row row, int column, short halign, short valign, String strContenido) {


        CreationHelper ch = wb.getCreationHelper();
        Cell cell = row.createCell(column);
        cell.setCellValue(ch.createRichTextString(strContenido));

        HSSFFont cellFont = wb.createFont();
        cellFont.setFontHeightInPoints((short) 11);
        cellFont.setFontName(HSSFFont.FONT_ARIAL);
        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setFont(cellFont);
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setBottomBorderColor((short) 8);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setLeftBorderColor((short) 8);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setRightBorderColor((short) 8);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setTopBorderColor((short) 8);

        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cell.setCellStyle(cellStyle);

    }

    public static void createCell(CellStyle cellStyle, Workbook wb, Row row, int column, short halign, short valign, String strContenido) {


        CreationHelper ch = wb.getCreationHelper();
        Cell cell = row.createCell(column);

        cell.setCellValue(ch.createRichTextString(strContenido));
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
        cellStyle.setBottomBorderColor((short) 8);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
        cellStyle.setLeftBorderColor((short) 8);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
        cellStyle.setRightBorderColor((short) 8);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
        cellStyle.setTopBorderColor((short) 8);


        cell.setCellStyle(cellStyle);
    }
    
    
    ////////////////FILTROS/////////////////////
    
    /*
     * Method which controls the filters allowed in this class
     */
    private HashMap filtros(String swbSocialUser, WebSite wsite, String searchWord, HttpServletRequest request, SocialTopic socialTopic, int nPage) {
        long socialTopicPostOut=0L;
        String sQuery=null;
        ArrayList<PostOut> aListFilter = new ArrayList();
        HashMap hampResult = new HashMap();
        Iterator<PostOut> itposts = null;
        if (swbSocialUser != null) {
            User user = User.ClassMgr.getUser(swbSocialUser, SWBContext.getAdminWebSite());
            socialTopicPostOut=Integer.parseInt(getAllPostOutbyAdminUser_Query(Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), true, socialTopic, user));
            sQuery=getAllPostOutbyAdminUser_Query(Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic, user);
            aListFilter=SWBSocial.executeQueryArrayPostOuts(sQuery, wsite);  
            hampResult.put("countResult", Long.valueOf(socialTopicPostOut));
        } else {
                if (nPage != 0) 
                {
                    if(request.getParameter("orderBy")!=null && (request.getParameter("orderBy").equals("viewFCPost") || request.getParameter("orderBy").equals("viewACPost")))
                    {
                        if(request.getParameter("orderBy").equals("viewFCPost"))
                        {   //Si se selecciono que mostrara solo los que estan con FastCalendars
                            socialTopicPostOut=Integer.parseInt(getAllPostOutFC_Query(0, 0, true, socialTopic));
                            sQuery=getAllPostOutFC_Query(Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic); 
                            aListFilter=SWBSocial.executeQueryArrayPostOuts(sQuery, wsite);
                        }else if(request.getParameter("orderBy").equals("viewACPost"))
                        {   //Si se selecciono que mostrara solo los que estan con Advance Calendars
                            socialTopicPostOut=Integer.parseInt(getAllPostOutAC_Query(0, 0, true, socialTopic));
                            sQuery=getAllPostOutAC_Query(Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic); 
                            aListFilter=SWBSocial.executeQueryArrayPostOuts(sQuery, wsite);
                        }
                    }else if (searchWord != null && searchWord.trim().length()>0) {
                        socialTopicPostOut=Integer.parseInt(getPostOutTopicbyWord_Query(0, 0, true, socialTopic, searchWord.trim()));
                        sQuery=getPostOutTopicbyWord_Query(Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic, searchWord.trim()); 
                        aListFilter=SWBSocial.executeQueryArray(sQuery, wsite); 
                    }else if(request.getParameter("orderBy")!=null && !request.getParameter("orderBy").isEmpty())
                    {
                        socialTopicPostOut=Integer.parseInt(getAllPostOutSocialTopic_Query(socialTopic));
                        if(request.getParameter("orderBy").equals("PostTypeUp"))    //Tipo de Mensaje Up
                        {
                            sQuery=getPostOutType_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        }else if(request.getParameter("orderBy").equals("PostTypeDown"))    //Tipo de Mensaje Down
                        {
                            sQuery=getPostOutType_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } else if (request.getParameter("orderBy").equals("origenUp")) {
                            //socialTopicPostOut=Integer.parseInt(getPostOutSource_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), true, socialTopic));
                            sQuery=getPostOutSource_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } else if (request.getParameter("orderBy").equals("origenDown")) {
                            //socialTopicPostOut=Integer.parseInt(getPostOutSource_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), true, socialTopic));
                            sQuery=getPostOutSource_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } else if (request.getParameter("orderBy").equals("cretedUp")) {
                            sQuery=getPostOutCreated_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } else if (request.getParameter("orderBy").equals("cretedDown")) {
                            sQuery=getPostOutCreated_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } else if (request.getParameter("orderBy").equals("sentimentUp")) {
                            sQuery=getPostOutSentimentalType_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } else if (request.getParameter("orderBy").equals("sentimentDown")) {
                            sQuery=getPostOutSentimentalType_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } else if (request.getParameter("orderBy").equals("intensityUp")) {
                            sQuery=getPostOutIntensityType_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } else if (request.getParameter("orderBy").equals("intensityDown")) {
                            sQuery=getPostOutIntensityType_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } else if (request.getParameter("orderBy").equals("updatedUp")) {
                            //socialTopicPostOut=Integer.parseInt(getPostOutUpdated_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), true, socialTopic));
                            sQuery=getPostOutUpdated_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } else if (request.getParameter("orderBy").equals("updatedDown")) {
                            //socialTopicPostOut=Integer.parseInt(getPostOutUpdated_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), true, socialTopic));
                            sQuery=getPostOutUpdated_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } else if (request.getParameter("orderBy").equals("userUp")) {
                            sQuery=getPostOutUserName_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } else if (request.getParameter("orderBy").equals("userDown")) {
                            sQuery=getPostOutUserName_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } else if (request.getParameter("orderBy").equals("statusUp")) {
                            sQuery=getPostOutStatus_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } else if (request.getParameter("orderBy").equals("statusDown")) {
                            sQuery=getPostOutStatus_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } 
                        //Termina Armado de Query
                        if(sQuery!=null)
                        {
                           aListFilter=SWBSocial.executeQueryArrayPostOuts(sQuery, wsite);
                        }
                        }else{  //No seleccionaron ningún ordenamiento
                            /*       
                            itposts = new GenericIterator(new SemanticIterator(wsite.getSemanticModel().listStatements(null, PostOut.social_socialTopic.getRDFProperty(), socialTopic.getSemanticObject().getRDFResource(), PostOut.sclass.getClassGroupId(), Integer.valueOf((RECPERPAGE)).longValue(), Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), "timems desc"), true));
                            setso = SWBSocialComparator.sortByPostOutCreatedSet(itposts, false); 
                            itposts = setso.iterator();*/
                            socialTopicPostOut=Integer.parseInt(getAllPostOutSocialTopic_Query(0, 0, true, socialTopic));
                            sQuery=getAllPostOutSocialTopic_Query(Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic); 
                            aListFilter=SWBSocial.executeQueryArrayPostOuts(sQuery, wsite);
                        }
                    } else { //Traer todo, NPage==0, en teoría jamas entraría a esta opción.
                        socialTopicPostOut=Integer.parseInt(getAllPostOutSocialTopic_Query(0, 0, true, socialTopic));
                        if(socialTopicPostOut>0)
                        {
                            sQuery=getAllPostOutSocialTopic_Query(0L, socialTopicPostOut, false, socialTopic); 
                            aListFilter=SWBSocial.executeQueryArrayPostOuts(sQuery, wsite);
                        }
                    }
                
                //System.out.println("streamPostOuts-Antes de:"+socialTopicPostOut);
                /*
                if(socialTopicPostOut==0L)
                {
                    socialTopicPostOut=Integer.parseInt(getAllPostOutSocialTopic_Query(0, 0, true, socialTopic));
                }*/
                //System.out.println("StreamPostOuts:"+socialTopicPostOut);

                hampResult.put("countResult", Long.valueOf(socialTopicPostOut));
            }
            //Termina Filtros
        
        
            if (aListFilter.size() > 0) {
                itposts = aListFilter.iterator();
                //System.out.println("Entra a ORDEBAR -2");
                //setso = SWBSocialComparator.convertArray2TreeSet(itposts);
            }/*else{
                int socialTopicPostOut=Integer.parseInt(getAllPostOutSocialTopic_Query(0, 0, true, socialTopic));
                sQuery=getAllPostOutSocialTopic_Query(Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic); 
                aListFilter=executeQueryArray(sQuery, wsite);
                itposts = aListFilter.iterator();
                hampResult.put("countResult", Long.valueOf(socialTopicPostOut));
            }*/
            hampResult.put("itResult", itposts);

            return hampResult;

    }
    
    
    //////////////SPARQL FILTERS//////////////////////
    
    
     /*
     * Metodo que obtiene todos los PostOuts
     */
    private String getAllPostOutSocialTopic_Query(SocialTopic socialTopic)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" + "\n";
           query+="select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
           query+=
           "where {\n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
           "  ?postUri social:pout_created ?postOutCreated." + "\n" + 
           "  }\n";
            WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
            query=SWBSocial.executeQuery(query, wsite);
        return query;
    }
    
    
    private String getAllPostOutSocialTopic_Query(long offset, long limit, boolean isCount, SocialTopic socialTopic)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           {
               //query+="select count(*)\n";
               query+="select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
           "  ?postUri social:pout_created ?postOutCreated." + "\n" + 
           "  }\n";
           
           if(!isCount)
           {
                query+="ORDER BY desc(?postOutCreated) ";

                query+="OFFSET "+offset +"\n";
                if(limit>0)
                {
                  query+="LIMIT "+limit;   
                }
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;
    }
    
    
    private String getPostOutTopicbyWord_Query(long offset, long limit, boolean isCount, SocialTopic socialTopic, String word)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           {
               //query+="select count(*)\n";    
               query+="select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
           "  ?postUri social:msg_Text ?msgText. \n" +       
           "  ?postUri social:pout_created ?postOutCreated. \n" +
           "  FILTER regex(?msgText, \""+word+"\", \"i\"). " + 
           "  }\n";

           if(!isCount)
           {
                query+="ORDER BY desc(?postOutCreated) \n";

                query+="OFFSET "+offset +"\n";
                if(limit>0)
                {
                  query+="LIMIT "+limit;   
                }
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;
    }
    
    /*
     * gets all PostIn by specific SocialNetUser
     */
    private String getAllPostOutbyAdminUser_Query(long offset, long limit, boolean isCount, SocialTopic socialTopic, User user)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "PREFIX swb: <http://www.semanticwebbuilder.org/swb4/ontology#>" +    
           "\n";
           if(isCount)
           {
               //query+="select count(*)\n";
               query+="select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
           "  ?postUri swb:creator <"+user.getURI()+">." +"\n" +
           "  ?postUri social:pout_created ?postOutCreated." + "\n" +
           "  }\n";

           if(!isCount)
           {
                query+="ORDER BY desc(?postOutCreated) \n";

                query+="OFFSET "+offset +"\n";
                if(limit>0)
                {
                  query+="LIMIT "+limit;   
                }
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;
    }
    
    private String getPostOutType_Query(String orderType, long offset, long limit, boolean isCount, SocialTopic socialTopic)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           {
               //query+="select count(*)\n";
               query+="select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
           "  ?postUri social:po_type ?posOutType. \n" +
           "  ?postUri social:pout_created ?postOutCreated." + "\n" + 
           "  }\n";

           if(!isCount)
           {
                if(orderType==null || orderType.equalsIgnoreCase("up"))
                {
                    query+="ORDER BY asc(?posOutType) ";
                }else
                {
                    query+="ORDER BY desc(?posOutType) ";
                }
                query+="desc(?postOutCreated) \n";

                query+="OFFSET "+offset +"\n";
                if(limit>0)
                {
                  query+="LIMIT "+limit;   
                }
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;
    }
    
    
    private String getPostOutSource_Query(String orderType, long offset, long limit, boolean isCount, SocialTopic socialTopic)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           {
               //query+="select count(*)\n";
               query+="select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
           "  ?postUri social:pout_created ?postOutCreated." + "\n" + 
           "  OPTIONAL { \n" +
           "  ?postUri social:postInSource ?posOutSource. \n" +
           "         }" +         
           "  }\n";

           if(!isCount)
           {
                if(orderType==null || orderType.equalsIgnoreCase("up"))
                {
                    query+="ORDER BY asc(?posOutSource) ";
                }else
                {
                    query+="ORDER BY desc(?posOutSource) ";
                }
                query+="desc(?postOutCreated) \n";

                query+="OFFSET "+offset +"\n";
                if(limit>0)
                {
                  query+="LIMIT "+limit;   
                }
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
    }
    
    
    private String getPostOutCreated_Query(String orderType, long offset, long limit, boolean isCount, SocialTopic socialTopic)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           { 
               //query+="select count(*)\n";
               query+="select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
           "  ?postUri social:pout_created ?postOutCreated." + "\n" + 
           "  }\n";

           if(!isCount)
           {
                if(orderType==null || orderType.equalsIgnoreCase("up"))
                {
                    query+="ORDER BY asc(?postOutCreated) \n";
                }else
                {
                    query+="ORDER BY desc(?postOutCreated) \n";
                }
                query+="OFFSET "+offset +"\n";
                if(limit>0)
                {
                  query+="LIMIT "+limit;   
                }
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
    }
    
    private String getPostOutUpdated_Query(String orderType, long offset, long limit, boolean isCount, SocialTopic socialTopic)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "PREFIX swb: <http://www.semanticwebbuilder.org/swb4/ontology#>" +          
           "\n";
           if(isCount)
           { 
               //query+="select count(*)\n";
               query+="select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
           "  ?postUri social:pout_created ?postOutCreated." + "\n" + 
           "  ?postUri swb:updated ?postOutUpdated." + "\n" + 
           "  }\n";

           if(!isCount)
           {
                if(orderType==null || orderType.equalsIgnoreCase("up"))
                {
                    query+="ORDER BY asc(?postOutUpdated) \n";
                }else
                {
                    query+="ORDER BY desc(?postOutUpdated) \n";
                }
                query+="OFFSET "+offset +"\n";
                if(limit>0)
                {
                  query+="LIMIT "+limit;   
                }
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
    }
    
    private String getPostOutSentimentalType_Query(String orderType, long offset, long limit, boolean isCount, SocialTopic socialTopic)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           { 
               //query+="select count(*)\n";
               query+="select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
           "  ?postUri social:pout_created ?postOutCreated."+ "\n" +
           "  OPTIONAL { \n" +
           "  ?postUri social:postSentimentalType ?postSentimentalType." + "\n" +
           "         }" + 
           "  }\n";

           if(!isCount)
           {
                if(orderType==null || orderType.equalsIgnoreCase("up"))
                {
                    query+="ORDER BY asc(?postSentimentalType) ";
                }else
                {
                    query+="ORDER BY desc(?postSentimentalType) ";
                }
                query+="desc(?postOutCreated) \n";

                query+="OFFSET "+offset +"\n";
                if(limit>0)
                {
                  query+="LIMIT "+limit;   
                }
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
    }
    
    private String getPostOutIntensityType_Query(String orderType, long offset, long limit, boolean isCount, SocialTopic socialTopic)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           { 
               //query+="select count(*)\n";
               query+="select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
           "  ?postUri social:pout_created ?postOutCreated."+ "\n" +
           "  OPTIONAL { \n" +
           "  ?postUri social:postIntesityType ?postIntensityType." + "\n" + 
           "         }" +    
           "  }\n";

           if(!isCount)
           {
                if(orderType==null || orderType.equalsIgnoreCase("up"))
                {
                    query+="ORDER BY asc(?postIntensityType) ";
                }else
                {
                    query+="ORDER BY desc(?postIntensityType) ";
                }
                query+="desc(?postOutCreated) \n";

                query+="OFFSET "+offset +"\n";
                if(limit>0)
                {
                  query+="LIMIT "+limit;   
                }
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
    }
    
    
    private String getPostOutUserName_Query(String orderType, long offset, long limit, boolean isCount, SocialTopic socialTopic)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "PREFIX swb: <http://www.semanticwebbuilder.org/swb4/ontology#>" +      
           "\n";
           if(isCount)
           { 
               //query+="select count(*)\n";
               query+="select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
           "  ?postUri social:pout_created ?postOutCreated." + "\n" + 
           "  ?postUri swb:creator ?postOutCreator." + "\n" + 
           "  ?postUri social:pout_created ?postOutCreated." + "\n" + 
           "  }\n";

           if(!isCount)
           {
                if(orderType==null || orderType.equalsIgnoreCase("up"))
                {
                    query+="ORDER BY asc(?postOutCreator) ";
                }else
                {
                    query+="ORDER BY desc(?postOutCreator) ";
                }
                query+="desc(?postOutCreated) \n";

                query+="OFFSET "+offset +"\n";
                if(limit>0)
                {
                  query+="LIMIT "+limit;   
                }
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
    }

    
   private String getPostOutStatus_Query(String orderType, long offset, long limit, boolean isCount, SocialTopic socialTopic)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           { 
               //query+="select count(*)\n";
               query+="select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
           "  ?postUri social:pout_created ?postOutCreated." + "\n" + 
           "  OPTIONAL { \n" +
           "  ?postUri social:published ?postOutStatus." + "\n" + 
           "         }" +            
           "  }\n";

           if(!isCount)
           {
                if(orderType==null || orderType.equalsIgnoreCase("up"))
                {
                    query+="ORDER BY asc(?postOutStatus) ";
                }else
                {
                    query+="ORDER BY desc(?postOutStatus) ";
                }
                query+="desc(?postOutCreated) \n";

                query+="OFFSET "+offset +"\n";
                if(limit>0)
                {
                  query+="LIMIT "+limit;   
                }
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
    }
   
   private String getAllPostOutFC_Query(long offset, long limit, boolean isCount, SocialTopic socialTopic)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           { 
               //query+="select count(*)\n";
               query+="select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
           "  ?postUri social:fastCalendar ?fastCalendar." + "\n" + 
           "  ?fastCalendar social:fc_date ?fc_date. " + "\n" + 
           "  }\n";

           if(!isCount)
           {
                query+="ORDER BY asc(?fc_date) ";
                
                query+="OFFSET "+offset +"\n";
                if(limit>0)
                {
                  query+="LIMIT "+limit;   
                }
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
    }
   
   
   private String getAllPostOutAC_Query(long offset, long limit, boolean isCount, SocialTopic socialTopic)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#> \n" +
           "PREFIX swb: <http://www.semanticwebbuilder.org/swb4/ontology#> \n" +
           "\n";
           if(isCount)
           { 
               query+="select DISTINCT (COUNT(?postUri) AS ?c1) \n";    //Para Gena
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
           "  ?postUri swb:hasCalendarRef ?hasAdvCal." + "\n" + 
           "  ?postUri social:pout_created ?postOutCreated. " + "\n" + 
           "  }\n";

           if(!isCount)
           {
                query+="ORDER BY asc(?postOutCreated) ";
                
                query+="OFFSET "+offset +"\n";
                if(limit>0)
                {
                  query+="LIMIT "+limit;   
                }
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
    }
   
   
   private String getSumTotPostOutComments(SocialTopic socialTopic)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#> \n";
           query+="select (SUM(DISTINCT ?totMsgs) AS ?c1) \n";
           query+=
           "where {\n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
           "  ?postUi social:numTotNewResponses ?totMsgs." + "\n" + 
           "  }\n";

           WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
           query=SWBSocial.executeQuery(query, wsite);
           
        return query;   
    }
   
    
   /*
   private String executeQuery(String query, WebSite wsite)
   {
        System.out.println("Entra a executeQuery..:"+query);
        if(query!=null)
        {
            QueryExecution qe=new SWBQueryExecution(wsite.getSemanticModel().getRDFModel(), query);
            ResultSet rs=qe.execSelect();
            while(rs.hasNext())
            {
                QuerySolution qs=rs.next();
                Iterator<String> it=rs.getResultVars().iterator();
                while(it.hasNext())
                {
                    String name=it.next();
                    if(name.equalsIgnoreCase("c1"))
                    {
                        System.out.println("sQuery a Ejecutar..name:"+name);
                        RDFNode node=qs.get(name);
                        String val="";
                        if(node.isLiteral())val=node.asLiteral().getLexicalForm();
                        System.out.println("val:"+val);
                        return val;
                    }
                }
            }
        }
        return "0";
   }
    
   
   private ArrayList executeQueryArray(String query, WebSite wsite)
   {
        ArrayList aResult=new ArrayList();
        QueryExecution qe=new SWBQueryExecution(wsite.getSemanticModel().getRDFModel(), query);
        ResultSet rs=qe.execSelect();
        while(rs!=null && rs.hasNext())
        {
            QuerySolution qs=rs.next();
            Iterator<String> it=rs.getResultVars().iterator();
            while(it.hasNext())
            {
                String name=it.next();
                if(name.equalsIgnoreCase("postUri"))
                {
                    System.out.println("sQuery a Ejecutar..name:"+name);
                    RDFNode node=qs.get(name);
                    String val="";
                    if(node.isResource()){
                        val=node.asResource().getURI();
                        System.out.println("ValGeorgeResource:"+val);
                        SemanticObject semObj=SemanticObject.createSemanticObject(val, wsite.getSemanticModel()); 
                        System.out.println("semObj:"+semObj);
                        PostOut postOut=(PostOut)semObj.createGenericInstance();
                        System.out.println("semObj/PostIn:"+postOut);
                        aResult.add(postOut);
                    }
                }
            }
        }
        return aResult;
   }
    */

    private void doShowPhotos(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/showPhotos.jsp";

        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        if (dis != null) {
            try {                
                request.setAttribute("postOut", request.getParameter("postOut"));
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
                e.printStackTrace(System.out);
            }
        }
    }
    //--
    /*
     * Shows the comments of a specific PostOut
     */
    private void doShowMsgComments(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/commentsHistory.jsp";
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        if (dis != null) {
            try {                
                request.setAttribute("postOut", request.getParameter("postUri"));
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
                e.printStackTrace(System.out);
            }
        }
    }
    
    public void doShowRecoveredComments(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
            
        PrintWriter out = response.getWriter();
        String postOutUri = request.getParameter("postOutUri");
        String postOutNetwork = request.getParameter("postOutNetNetwork");
        SemanticObject sPost = SemanticObject.createSemanticObject(postOutUri);
        PostOut postOut = (PostOut)sPost.createGenericInstance();
        
        SemanticObject sNet = SemanticObject.createSemanticObject(postOutNetwork);
        SocialNetwork socialNetwork = (SocialNetwork)sNet.createGenericInstance();
        
        //PostOutNet.ClassMgr.lis                
        //System.out.println("--------------------");
        //System.out.println("POST OUT:" + postOut + "->" + postOut.getMsg_Text() + "</br>");
        ArrayList postOutSocialNet = SWBSocialUtil.sparql.getPostOutNetsPostOut(postOut, socialNetwork);
        
        SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
        //out.println(this.comments(postOutNet.getPo_socialNetMsgID(), (Facebook)postOutNet.getSocialNetwork()));//String tabSuffix, Facebook facebook, SWBModel model, SocialUserExtAttributes socialUserExtAttr){
        org.semanticwb.model.User user = paramRequest.getUser();
        SocialUserExtAttributes socialUserExtAttr = null;
        if(user.isSigned()){
            socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
        }
        
        for(int i=0; i<postOutSocialNet.size(); i++){
            PostOutNet postOutNet = (PostOutNet)((SemanticObject)postOutSocialNet.get(i)).createGenericInstance();
            if(postOutNet.getSocialNetwork() instanceof Twitter){
            out.println("THIS IS A TWITTER");
            }else if(postOutNet.getSocialNetwork() instanceof Facebook){
                //out.println("THIS IS A FACEBOOK:" + postOutNet.getPo_socialNetMsgID() +"</br>");
                if(postOutNet.getStatus() == 1){
                    //System.out.println("OUTNET:" + postOutNet.getPo_socialNetMsgID());
                    HashMap<String, String> params = new HashMap<String, String>(3);    
                    params.put("q", "{\"comments\": \"SELECT id, text, time, fromid, attachment, can_like, can_remove, likes, user_likes from comment where post_id='" + postOutNet.getPo_socialNetMsgID() +"' ORDER BY time DESC limit 10 offset 0\", \"usernames\": \"SELECT uid, name FROM user WHERE uid IN (SELECT fromid FROM #comments)\", \"pages\":\"SELECT page_id, name FROM page WHERE page_id IN (SELECT fromid FROM #comments)\"}");
                    params.put("access_token", ((Facebook)postOutNet.getSocialNetwork()).getAccessToken());

                    String fbComments = null;
                    try{
                     fbComments = getRequest(params, "https://graph.facebook.com/fql",
                                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
                     //System.out.println("all the comments:" + fbComments);
                    }catch(Exception e){
                        log.error("Error getting user information" , e);
                    }
                    doPrintPost(out, this.comments(postOutNet.getPo_socialNetMsgID(), (Facebook)postOutNet.getSocialNetwork()), request, paramRequest, "", (Facebook)socialNetwork, model, socialUserExtAttr, fbComments);
                }
            }else if(postOutNet.getSocialNetwork() instanceof Youtube){
                if(postOutNet.getStatus() == 1){
                    doPrintVideo(request, response, paramRequest, out, postOutUri, socialUserExtAttr, this.comments(postOutNet.getPo_socialNetMsgID(), (Youtube)postOutNet.getSocialNetwork()), (Youtube)postOutNet.getSocialNetwork());
                }
            }
            postOutNet.setPo_numResponses(0);
        }        
    }
    //--
    private void doDeletePhoto(HttpServletRequest request, SWBActionResponse response) {
        String idPhoto = request.getParameter("idPhoto");
        String po = request.getParameter("postOut");

        SemanticObject semObj = SemanticObject.getSemanticObject(po);
        
        if(semObj.createGenericInstance() instanceof Photo)
        {
            Photo photo = (Photo) semObj.getGenericInstance();
            Iterator i = photo.listPhotos();

            while (i.hasNext()) {
                String ca =  (String) i.next();
                if (ca.equals(idPhoto)) {
                    photo.removePhoto(idPhoto);
                    File file=new File(SWBPortal.getWorkPath()+photo.getWorkPath()+"/"+idPhoto);
                    if(file.exists())
                    {
                        file.delete();
                        File directory=file.getParentFile();
                        if(directory.isDirectory())
                        {
                            if(directory.listFiles().length==0)
                            {
                                directory.delete();
                            }
                        }
                    }
                    break;
                }                      
                response.setMode("showPhotos");
                response.setRenderParameter("postOut", request.getParameter("postOut"));
            }
        }else if(semObj.createGenericInstance() instanceof Message)
        {
            Message message = (Message) semObj.getGenericInstance();
            Iterator i = message.listFiles();

            while (i.hasNext()) {
                String ca =  (String) i.next();
                if (ca.equals(idPhoto)) {
                    message.removeFile(idPhoto);
                    File file=new File(SWBPortal.getWorkPath()+message.getWorkPath()+"/"+idPhoto);
                    if(file.exists())
                    {
                        file.delete();
                        File directory=file.getParentFile();
                        if(directory.isDirectory())
                        {
                            if(directory.listFiles().length==0)
                            {
                                directory.delete();
                            }
                        }
                    }
                    break;
                }                      
                response.setMode("showPhotos");
                response.setRenderParameter("postOut", request.getParameter("postOut"));
            }
        }
    }
    //--
    private JSONObject comments(String postID, Facebook facebook){
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", facebook.getAccessToken());        
        //params.put("fields","comments.summary(true),likes.summary(true)");
        JSONObject response = new JSONObject();
        try{
            String fbResponse = getRequest(params, "https://graph.facebook.com/" + postID,
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
            //System.out.println("THIS IS THE RESPONSE:" + fbResponse);
            response = new JSONObject(fbResponse);
            //System.out.println("FACEBOOK:" + postID + " AND THE CONTENT:" + fbResponse);
        }catch(Exception e){
            log.error("Facebook: Not data found for ->" + postID);
        }
        return response;      
    }
    
    /**
     * Realiza una peticion get a la URL especificada
     *
     * @param params arreglo de nombres de parámetro-valor
     * @param url url destino
     * @param userAgent user agent
     * @return la respuesta del servidor o el mensaje de error obtenido de la
     * petición
     * @throws IOException
     */
    private String getRequest(Map<String, String> params, String url,
            String userAgent) throws IOException {

        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = new URL(url + "?" + paramString);

        HttpURLConnection conex = null;
        InputStream in = null;
        String response = null;

        try {
            conex = (HttpURLConnection) serverUrl.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);
            }
            conex.setConnectTimeout(30000);
            conex.setReadTimeout(60000);
            conex.setRequestMethod("GET");
            conex.setDoOutput(true);
            conex.connect();
            in = conex.getInputStream();
            response = getResponse(in);

        } catch (java.io.IOException ioe) {
            response = getResponse(conex.getErrorStream());
            ioe.printStackTrace();
        } finally {
            close(in);
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }
    
    /**
     * En base al contenido de la colecci&oacute;n recibida, arma una secuencia
     * de caracteres compuesta de los pares:
     * <p>{@code Entry.getKey()} {@code equals} {@code Entry.getKey()} </p> Si
     * en la colecci&oacute;n hay m&aacute;s de una entrada, los pares (como el
     * anterior), se separan por {@code delimiter}.
     *
     * @param entries la colecci&oacute;n con la que se van a formar los pares
     * @param delimiter representa el valor con que se van a separar los pares a
     * representar
     * @param equals representa el valor con el que se van a relacionar los
     * elementos de cada par a representar
     * @param doEncode indica si el valor representado en cada par, debe ser
     * codificado (UTF-8) o no
     * @return la secuencia de caracteres que representa el conjunto de pares
     * @throws UnsupportedEncodingException en caso de ocurrir algun problema en
     * la codificaci&oacute;n a UTF-8 del valor de alg&uacute;n par, si
     * as&iacute; se indica en {@code doEncode}
     */
    private CharSequence delimit(Collection<Map.Entry<String, String>> entries,
            String delimiter, String equals, boolean doEncode)
            throws UnsupportedEncodingException {

        if (entries == null || entries.isEmpty()) {
            return null;
        }
        StringBuilder buffer = new StringBuilder(64);
        boolean notFirst = false;
        for (Map.Entry<String, String> entry : entries) {
            if (notFirst) {
                buffer.append(delimiter);
            } else {
                notFirst = true;
            }
            CharSequence value = entry.getValue();
            buffer.append(entry.getKey());
            buffer.append(equals);
            buffer.append(doEncode ? encode(value) : value);
        }
        return buffer;
    }

    /**
     * Codifica el valor de {@code target} de acuerdo al c&oacute;digo de
     * caracteres UTF-8
     *
     * @param target representa el texto a codificar
     * @return un {@code String} que representa el valor de {@code target} de
     * acuerdo al c&oacute;digo de caracteres UTF-8
     * @throws UnsupportedEncodingException en caso de ocurrir algun problema en
     * la codificaci&oacute;n a UTF-8
     */
    private String encode(CharSequence target) throws UnsupportedEncodingException {

        String result = "";
        if (target != null) {
            result = target.toString();
            result = URLEncoder.encode(result, "UTF8");
        }
        return result;
    }

    /**
     * Lee un flujo de datos y lo convierte en un {@code String} con su
     * contenido codificado en UTF-8
     *
     * @param data el flujo de datos a convertir
     * @return un {@code String} que representa el contenido del flujo de datos
     * especificado, codificado en UTF-8
     * @throws IOException si ocurre un problema en la lectura del flujo de
     * datos
     */
    private static String getResponse(InputStream data) throws IOException {

        Reader in = new BufferedReader(new InputStreamReader(data, "UTF-8"));
        StringBuilder response = new StringBuilder(256);
        char[] buffer = new char[1000];
        int charsRead = 0;
        while (charsRead >= 0) {
            response.append(buffer, 0, charsRead);
            charsRead = in.read(buffer);
        }
        in.close();
        return response.toString();
    }

    /**
     * Cierra el objeto recibido
     *
     * @param c cualquier objeto que tenga la factultad de cerrarse
     */
    private void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ex) {
                log.error("Error at closing object: " + c.getClass().getName(),
                        ex);
            }
        }
    }
    
    public static void doPrintPost(Writer writer, JSONObject postsData, HttpServletRequest request, SWBParamRequest paramRequest, String tabSuffix, Facebook facebook, SWBModel model, SocialUserExtAttributes socialUserExtAttr, String fbComments){
        try{
            //System.out.println("THE POST:" + postsData);
            SWBResourceURL actionURL = paramRequest.getActionUrl();                        
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            String objUri = facebook.getURI();//request.getParameter("suri");
            actionURL.setParameter("suri",objUri);
            renderURL.setParameter("suri",objUri);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");    
            formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
            String postType = "";
            
            if(!postsData.isNull("type")){
                postType = postsData.getString("type");
            }else if(!postsData.isNull("picture") && !postsData.isNull("source")){
                postType = "video";
            }else if(!postsData.isNull("picture")){
                postType = "photo";
            }

            String story = "";
            String message = "";
            String caption = "";
            boolean isAppCreated = false;
            
            JSONObject applicationCreated = null;
                        
            
            //If the posts is empty and is application-created don't show it
            if(postsData.isNull("message") && postsData.isNull("story") && postsData.isNull("name") && postsData.isNull("picture") 
                    && postsData.isNull("link") && postsData.isNull("description") && !postsData.isNull("application")){
                return;
            }
            
            if(postType.equals("photo")){
                if(!postsData.isNull("story")){
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "" ;
                    if(!postsData.isNull("story_tags")){//Users tagged in story
                        story = FacebookWall.getTagsFromPost(postsData.getJSONObject("story_tags"), story, renderURL);
                    }
                }

                if(!postsData.isNull("message")){
                    message = postsData.getString("message");
                    if(!postsData.isNull("message_tags")){//Users tagged in story
                        message = FacebookWall.getTagsFromPost(postsData.getJSONObject("message_tags"), message, renderURL);
                    }
                }
                if(!postsData.isNull("caption")){
                    caption = postsData.getString("caption").replace("\n", "</br>");
                }
                if(postsData.has("application")){
                    if(postsData.getJSONObject("application").getString("name").equals("Foursquare")){
                        return;
                    }
                }
                //Story or message or both!!
                //or "status_type": "shared_story", tagged_in_photo
                
            }else if(postType.equals("link")){
                //"status_type": "app_created_story",
                if(!postsData.isNull("story")){
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "" ;
                    if(!postsData.isNull("story_tags")){//Users tagged in story
                        story = FacebookWall.getTagsFromPost(postsData.getJSONObject("story_tags"), story, renderURL);
                    }
                    if(story.contains("is going to an event") && postsData.has("link")){//If the link is an event
                        return;                       
                    }
                    if(story.contains("likes a photo")){
                        return;
                    }else if(story.contains("likes a link")){
                        return;
                    }else if(story.contains("likes a status")){
                        return;
                    }else if(story.contains("commented on")){
                        return;
                    }else if(story.contains("likes")){
                        return;
                    }else if(story.contains("is going to")){
                        return;
                    }else if(story.contains("created an event")){
                        return;
                    }
                }
                if(!postsData.isNull("message")){
                    message = postsData.getString("message");
                    if(!postsData.isNull("message_tags")){//Users tagged in story
                        message = FacebookWall.getTagsFromPost(postsData.getJSONObject("message_tags"), message, renderURL);
                    }
                }
            }else if(postType.equals("status")){
                
                if(postsData.has("story")){//Do not print the posts when 'User X likes a post'
                    if(postsData.getString("story").contains("likes a post")){
                        return;
                    }
                }
                if(!postsData.isNull("status_type")){
                    if(postsData.getString("status_type").equals("wall_post")){
                        JSONObject toUser = null;
                        if(postsData.has("to")){
                            toUser = postsData.getJSONObject("to").getJSONArray("data").getJSONObject(0);
                            story = " to " +  "<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"hideDialog(); showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", toUser.getLong("id")+"") + "','" + toUser.getString("name") + "'); return false;\">" + toUser.getString("name") + "</a>";
                        }                        
                    }
                }
                if(!postsData.isNull("message")){
                    message = postsData.getString("message");
                    if(!postsData.isNull("message_tags")){//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("message_tags");
                        message = FacebookWall.getTagsFromPost(storyTags, message, renderURL);
                    }
                }else if(!postsData.isNull("story")){
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "" ;
                    if(!postsData.isNull("story_tags")){//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("story_tags");
                        story = FacebookWall.getTagsFromPost(storyTags, story, renderURL);
                    }
                    if(story.contains("likes a photo")){
                        return;
                    }else if(story.contains("likes a link")){//Do not print the posts when 'User X likes a link'
                        return;
                    }else if(story.contains("likes a status")){
                        return;
                    }else if(story.contains("commented on")){
                        return;
                    }else if(story.contains("likes")){//USER likes PAGE
                        return;
                    }else if(story.contains("is going to")){//events
                        return;
                    }else if(story.contains("created an event")){
                        return;
                    }
                }else{//Status must have message OR Story
                     return;
                }
            }else if(postType.equals("video")){
                if(!postsData.isNull("message")){
                    message = postsData.getString("message").replace("\n", "</br>");
                }
                
                if(!postsData.isNull("name")){
                    message = postsData.getString("name");
                }
                
                if(!postsData.isNull("story")){
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "" ;
                    if(!postsData.isNull("story_tags")){//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("story_tags");
                        story = FacebookWall.getTagsFromPost(storyTags, story, renderURL);
                    }
                }
            }else if(postType.equals("checkin")){
                
                if(!postsData.isNull("message")){
                    message = postsData.getString("message");
                    if(!postsData.isNull("message_tags")){//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("message_tags");
                        message = FacebookWall.getTagsFromPost(storyTags, message, renderURL);
                    }
                }else{
                    message= postsData.getJSONObject("from").getString("name") +  " checked in ";
                }
            }else if(postType.equals("swf")){
                if(!postsData.isNull("message")){
                    message = postsData.getString("message");
                    if(!postsData.isNull("message_tags")){//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("message_tags");
                        message = FacebookWall.getTagsFromPost(storyTags, message, renderURL);
                    }
                }            
            }
            
            if(postsData.has("place") && !postsData.isNull("place")){
                if(postsData.getJSONObject("place").has("name")){
                message = message + " at " + "<a href=\"http://facebook.com/" + postsData.getJSONObject("place").getString("id") + "\" target=\"_blank\">" + postsData.getJSONObject("place").getString("name") + "</a>";
                }                
            }
            
            writer.write("<div class=\"timeline timelinefacebook\">");
            //Username and story
            writer.write("<p>");
            writer.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"hideDialog(); showDialog('" +renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", postsData.getJSONObject("from").getLong("id")+"")  + "','" + postsData.getJSONObject("from").getString("name") + "'); return false;\">" + postsData.getJSONObject("from").getString("name") + "</a> " + story);
            writer.write("</p>");
            
            //User image and message
            writer.write("<div class=\"timelineusr\">");
            writer.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"hideDialog(); showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", postsData.getJSONObject("from").getLong("id")+"") + "','" + postsData.getJSONObject("from").getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + postsData.getJSONObject("from").getLong("id") +"/picture\"/></a>");

            writer.write("<p>");
            if(message.isEmpty()){
                writer.write("&nbsp;");
            }else{                
                writer.write(message.replace("\n", "</br>"));
            }
            writer.write("</p>");
            writer.write("</div>");            
            
            //Picture if exists, start
            if(postsData.has("picture") || isAppCreated){
                String picture = "";
               if(isAppCreated){
                    if(applicationCreated.has("data")){
                        if(applicationCreated.getJSONObject("data").has("object")){
                            picture = applicationCreated.getJSONObject("data").getJSONObject("object").optString("url") + "media";
                        }
                    }
                }else{
                    picture = postsData.getString("picture").replace("_s.", "_n.");
                }
                //Post image
                writer.write("<div class=\"timelineimg\">");
                if(postType.equals("video") || postType.equals("swf")){                    
                    writer.write("      <span id=\"vid" + tabSuffix + facebook.getId() + postsData.getString("id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                    writer.write("      <a href=\"#\" onclick=\"hideDialog(); showDialog('"+ renderURL.setMode("displayVideo").setParameter("videoUrl", URLEncoder.encode(postsData.getString("source"), "UTF-8")) +
                            "','Video from " + postsData.getJSONObject("from").getString("name") + "'); return false;\"><img src=\"" + picture + "\" style=\"position: relative;\" onerror=\"this.src ='" + picture.replace("_n.", "_s.") + "'\" onload=\"imageLoad(" + "this, 'vid" + tabSuffix + facebook.getId() + postsData.getString("id") + "');\"/></a>");
                    writer.write("      </span>");
                }else{
                    if(postType.equals("link")){//If the post is a link -> it has link and name
                        if(postsData.has("name") && postsData.has("link")){
                            writer.write("      <span id=\"img" + tabSuffix + facebook.getId() + postsData.getString("id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                            writer.write("      <a href=\"" + postsData.getString("link") + "\" target=\"_blank\">" + "<img src=\"" + picture + "\" style=\"position: relative;\" onerror=\"this.src ='" + picture.replace("_n.", "_s.") + "'\" onerror=\"this.src ='" + picture.replace("_n.", "_s.") + "'\" onload=\"imageLoad(" + "this, 'img" + tabSuffix +facebook.getId() + postsData.getString("id") + "');\"/></a>");
                            writer.write("      </span>");
                        }
                    }else{
                        writer.write("      <span id=\"img" + tabSuffix + facebook.getId() + postsData.getString("id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                        writer.write("      <a href=\"#\" onclick=\"hideDialog(); showDialog('" + renderURL.setMode("displayPicture").setParameter("pictureUrl", URLEncoder.encode(picture, "UTF-8")) +
                                "','Picture from " + postsData.getJSONObject("from").getString("name") + "'); return false;\"><img src=\"" + picture + "\" style=\"position: relative;\" onerror=\"this.src ='" + picture.replace("_n.", "_s.") + "'\" onload=\"imageLoad(" + "this, 'img" + tabSuffix +facebook.getId() + postsData.getString("id") + "');\"/></a>");
                        writer.write("      </span>");
                    }
                }
                
                writer.write("<p class=\"imgtitle\">");
                if(postsData.has("link") && postsData.has("name")){
                    writer.write(    "<a href=\"" + postsData.getString("link") + "\" target=\"_blank\">" +  postsData.getString("name") + "</a>");
                }else{ 
                    writer.write("&nbsp;");
                }
                writer.write("</p>");
                                
                writer.write("<p class =\"imgdesc\">");
                writer.write( postsData.has("description") ?  postsData.getString("description").replace("\n", "</br>") :"&nbsp;");
                writer.write("</p>");
                
                if(!caption.isEmpty()){
                    writer.write("<p class=\"imgfoot\">");
                    writer.write(caption);
                    writer.write("</p>");
                }
                writer.write("<div class=\"clear\"></div>");
                writer.write("</div>");
            }
            //Picture if exists, end
            
            //GET THE COMMETS
            /*writer.write("comments: " + fbComments);*/
            
            JSONObject phraseResp = new JSONObject(fbComments);
            JSONArray commentsData = null;
            JSONArray userData = null;
            JSONArray pageData = null;

            for(int i = 0; i < phraseResp.getJSONArray("data").length(); i++){
                if(phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("comments")){//All the posts
                    commentsData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                }else if(phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("pages")){//All the pages
                    pageData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                }else if(phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("usernames")){//All the users
                    userData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                }
            }
            
            HashMap<Long, String> users = new HashMap<Long, String>();
            HashMap<Long, String> pages = new HashMap<Long, String>();            
            for(int i= 0; i< userData.length(); i++){
                users.put(userData.getJSONObject(i).getLong("uid"), userData.getJSONObject(i).getString("name"));
            }            
            for(int i= 0; i< pageData.length(); i++){
                pages.put(pageData.getJSONObject(i).getLong("page_id"), pageData.getJSONObject(i).getString("name"));
            }
            
            if(commentsData.length() > 0){
                writer.write("<ul id=\"" + facebook.getId() + postsData.getString("id") + "/allComments\">");//writer.write("<ul>");
            }
            
            if(commentsData.length() >= 10){
                writer.write("<li class=\"timelinemore\">");
                SWBResourceURL commentsURL = paramRequest.getRenderUrl().setMode(Mode_AllComments).setParameter("suri", request.getParameter("postOutNetNetwork")).setParameter("postId", postsData.getString("id"));
                //commentsURL = commentsURL.setParameter("after", pagingComments.getJSONObject("cursors").getString("after")).setParameter("currentTab", currentTab);
                writer.write("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode.parentNode).innerHTML = '<img src=/swbadmin/icons/loading.gif>';}catch(noe){} postHtml('" + commentsURL
                    + "','" + facebook.getId() + postsData.getString("id") + "/allComments'); return false;\"><span>+</span>" + "Ver todos los comentarios" + "</a></a>");
                writer.write("</li>");
            }
            for(int i= 0; i<commentsData.length(); i++){
                JSONObject comment = commentsData.getJSONObject(i);
                String username = null;
                if(users.get(comment.getLong("fromid")) != null){
                    username = users.get(comment.getLong("fromid"));
                }else if(pages.get(comment.getLong("fromid")) != null){
                    username = pages.get(comment.getLong("fromid"));
                }
                writer.write("<li>");
                writer.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"hideDialog(); showDialog('" + renderURL.setMode("fullProfile").setParameter("type","noType").setParameter("id",comment.getLong("fromid")+"") + "','" + username + "'); return false;\"><img src=\"http://graph.facebook.com/" + comment.getLong("fromid") +"/picture?width=30&height=30\" width=\"30\" height=\"30\"/></a>");

                writer.write("<p>");
                writer.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"hideDialog(); showDialog('" + renderURL.setMode("fullProfile").setParameter("type","noType").setParameter("id", comment.getLong("fromid")+"") + "','" + username + "'); return false;\">" + username + "</a>:");
                writer.write(       comment.getString("text").replace("\n", "</br>") + "</br>");
                writer.write("</p>");

                Date commentTime = new java.util.Date((long)comment.getLong("time")*1000);

                writer.write("<p class=\"timelinedate\">");
                writer.write("<span dojoType=\"dojox.layout.ContentPane\">");

                writer.write("<em>" + FacebookWall.facebookHumanFriendlyDate(commentTime, paramRequest) + "</em>");
                if(comment.has("likes")){
                    writer.write("<strong>");
                    writer.write("<span>Likes:</span> " + comment.getInt("likes") );
                    writer.write("</strong>");
                }
                writer.write("</span>");
                writer.write("</p>");
                writer.write("</li>");
            }
           
            if(commentsData.length() > 0){
                writer.write("</ul>");
            }            
            //Comments, end

            writer.write("<div class=\"clear\"></div>");
            Date postTime = formatter.parse(postsData.getString("created_time"));
            
            writer.write("<div class=\"timelineresume\" dojoType=\"dijit.layout.ContentPane\">");
            if(!postsData.isNull("icon")){
                writer.write("<img src=\"" + postsData.getString("icon") + "\"/>");
            }
            writer.write("<span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + FacebookWall.INFORMATION + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
            writer.write("<em>" + FacebookWall.facebookHumanFriendlyDate(postTime, paramRequest) + "</em>");
            boolean iLikedPost = false;
            writer.write("<strong><span> Likes: </span>");
            if(postsData.has("likes")){
                JSONArray likes = postsData.getJSONObject("likes").getJSONArray("data");
                int postLikes = 0;
                if(!postsData.getJSONObject("likes").isNull("summary")){
                    if(!postsData.getJSONObject("likes").getJSONObject("summary").isNull("total_count")){
                        postLikes = postsData.getJSONObject("likes").getJSONObject("summary").getInt("total_count");
                    }
                }
                
                writer.write(String.valueOf(postLikes));
                
                for (int k = 0; k < likes.length(); k++) {
                    if(likes.getJSONObject(k).getString("id").equals(facebook.getFacebookUserId())){
                        //My User id is in 'the likes' of this post
                        iLikedPost = true;
                    }
                }

                if((likes.length() < postLikes) && (iLikedPost == false)){
                    System.out.println("Look for postLike!!!");
                    HashMap<String, String> params = new HashMap<String, String>(3);    
                    params.put("q", "SELECT post_id FROM like WHERE user_id=me() AND post_id=\"" + postsData.getString("id") + "\"");
                    params.put("access_token", facebook.getAccessToken());
                    String fbLike = null;

                    try{
                        fbLike = FacebookWall.getRequest(params, "https://graph.facebook.com/fql",
                                   "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
                        //System.out.println("fbLike:" + fbLike);
                        JSONObject likeResp = new JSONObject(fbLike);
                        if(likeResp.has("data")){
                            JSONArray likesArray = likeResp.getJSONArray("data");

                            if(likesArray.length() == 1){//There is one result, I liked this post
                                iLikedPost = true;
                            }
                        }
                    }catch(Exception e){
                        log.error("Error getting like information for Facebook post " + postsData.getString("id") , e);
                    }
                }
            }else{
                writer.write("0");
            }
            writer.write("</strong>");
            writer.write("</span>");
            //-- FALTA
            //Show like/unlike and reply (comment)
            JSONArray actions = postsData.has("actions") ? postsData.getJSONArray("actions") : null;
            if(actions != null && actions.length() > 0){//Available actions for the post
                for (int i = 0; i < actions.length(); i++) {
                    if((actions.getJSONObject(i).getString("name").equals("Comment") || postType.equals("photo") || postType.equals("video")) && socialUserExtAttr.isUserCanRespondMsg()){//I can comment
                        writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + FacebookWall.REPLY + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                            writer.write(" <a class=\"clasifica\" href=\"\" onclick=\"hideDialog(); showDialog('" + renderURL.setMode(Mode_ReplyPost).setParameter("postID", postsData.getString("id")) + "','Reply to " + postsData.getJSONObject("from").getString("name") + "');return false;\"><span>Reply</span></a>  ");
                        writer.write("   </span>");

                    }else if(actions.getJSONObject(i).getString("name").equals("Like") || postType.equals("photo") || postType.equals("video")){//I can like
                        /**writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + FacebookWall.LIKE + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");                        
                        if(iLikedPost){
                            writer.write(" <a href=\"#\" class=\"nolike\" onclick=\"postSocialHtml('" + actionURL.setAction("doUnlike").setParameter("postID", postsData.getString("id")).setParameter("currentTab", tabSuffix) + "','" + facebook.getId() +  postsData.getString("id") + FacebookWall.INFORMATION + tabSuffix + "');return false;" +"\"><span>" + paramRequest.getLocaleString("undoLike") +"</span></a>");
                        }else{
                            writer.write(" <a href=\"#\" class=\"like\" onclick=\"postSocialHtml('" + actionURL.setAction("doLike").setParameter("postID", postsData.getString("id")).setParameter("currentTab", tabSuffix) + "','" + facebook.getId() + postsData.getString("id") + FacebookWall.INFORMATION + tabSuffix + "');return false;" +"\"><span>" + paramRequest.getLocaleString("like") +"</span></a>");
                        }
                        writer.write("   </span>");**/
                    }else{//Other unknown action
                        //writer.write("other:" + actions.getJSONObject(i).getString("name"));
                    }
                }
            }else{
                if((postType.equals("photo") || postType.equals("video")) && socialUserExtAttr.isUserCanRespondMsg()){//I can comment
                    writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + FacebookWall.REPLY + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                        writer.write(" <a class=\"clasifica\" href=\"\" onclick=\"hideDialog(); showDialog('" + renderURL.setMode(Mode_ReplyPost).setParameter("postID", postsData.getString("id")) + "','Reply to " + postsData.getJSONObject("from").getString("name") + "');return false;\"><span>Reply</span></a>  ");
                    writer.write("   </span>");

                }
                if(postType.equals("photo") || postType.equals("video")){//I can like
                    /**writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + FacebookWall.LIKE + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");                        
                    if(iLikedPost){
                        writer.write(" <a href=\"#\" class=\"nolike\" onclick=\"postSocialHtml('" + actionURL.setAction("doUnlike").setParameter("postID", postsData.getString("id")).setParameter("currentTab", tabSuffix) + "','" + facebook.getId() +  postsData.getString("id") + FacebookWall.INFORMATION + tabSuffix + "');return false;" +"\"><span>" + paramRequest.getLocaleString("undoLike") +"</span></a>");
                    }else{
                        writer.write(" <a href=\"#\" class=\"like\" onclick=\"postSocialHtml('" + actionURL.setAction("doLike").setParameter("postID", postsData.getString("id")).setParameter("currentTab", tabSuffix) + "','" + facebook.getId() + postsData.getString("id") + FacebookWall.INFORMATION + tabSuffix + "');return false;" +"\"><span>" + paramRequest.getLocaleString("like") +"</span></a>");
                    }
                    writer.write("   </span>");**/
                }
            }

            writer.write("  </div>");
            writer.write("</div>");
        }catch(Exception e){
            log.error("Error printing post:" , e);
            e.printStackTrace();
        }
    }
    
    public JSONObject comments(String videoId, Youtube youtube){
        JSONObject videoResp = new JSONObject();
        try{
            String video = getFullVideoFromId(videoId, youtube.getAccessToken());
            videoResp = new JSONObject(video);
            if(!videoResp.isNull("entry")){
                videoResp = videoResp.getJSONObject("entry");
            }
        }catch(Exception e){
            log.error("Youtube: Not data found for ->" + videoId );
        }
        return videoResp;
    }
    
    public String getFullVideoFromId(String id, String accessToken){
        HashMap<String, String> params = new HashMap<String, String>(3);    
        params.put("v", "2");
        params.put("alt","json");
    
        String response = null;
        try{
         response = getRequest(params, "https://gdata.youtube.com/feeds/api/videos/" + id,
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", accessToken);

        }catch(Exception e){
            log.error(e);
        }
        return response;
    }
    
    public String getcommentsFromVideoId(String id, String accessToken){
        HashMap<String, String> params = new HashMap<String, String>(3);    
        params.put("v", "2");
        params.put("alt","json");//alt
        params.put("start-index","1");//alt
        params.put("max-results","1");//alt
    
        String response = null;
        try{
            response = getRequestVideo(params, "https://gdata.youtube.com/feeds/api/videos/" + id +"/comments",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", accessToken);
        }catch(Exception e){
            log.error(e);
        }
        return response;
    }
    
    public String getRequestVideo(Map<String, String> params, String url,
            String userAgent, String accessToken) throws IOException {
        
        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = new URL(url + "?" +  paramString);       
        
        HttpURLConnection conex = null;
        InputStream in = null;
        String response = null;
       
        try {
            conex = (HttpURLConnection) serverUrl.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);                
            }
            ///Validate if i am looking for the default user or another
            if(accessToken != null){
                conex.setRequestProperty("Authorization", "Bearer " + accessToken);
            }
            ///
            conex.setConnectTimeout(30000);
            conex.setReadTimeout(60000);
            conex.setRequestMethod("GET");
            conex.setDoOutput(true);
            conex.connect();
            in = conex.getInputStream();
            response = getResponse(in);
            //System.out.println("RESPONSE:" + response);
                        
        } catch (java.io.IOException ioe) {
            if (conex.getResponseCode() >= 400) {
                response = getResponse(conex.getErrorStream());
                System.out.println("\n\n\nERROR:" +   response);
            }
            ioe.printStackTrace();
        } finally {
            close(in);
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }
    
    public void doPrintVideo(HttpServletRequest request, HttpServletResponse response, 
             SWBParamRequest paramRequest, java.io.Writer out, String postURI, SocialUserExtAttributes socialUserExtAttr, JSONObject video, Youtube youtube) throws SWBResourceException, IOException {
        ///out.write("VIDEO:" + video);
        HashMap<String, String> paramsComments = new HashMap<String, String>(3);
        paramsComments.put("v", "2");
        paramsComments.put("max-results", "5");
        paramsComments.put("start-index", "1");
        paramsComments.put("alt", "json");
        
        HashMap<String, String> paramsUsr = new HashMap<String, String>(3);
        paramsUsr.put("v", "2");
        paramsUsr.put("fields", "media:thumbnail");
        paramsUsr.put("alt", "json");
        
        String objUri = youtube.getURI();//request.getParameter("suri");
        SocialNetwork socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
        SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Youtube semanticYoutube = (Youtube) semanticObject.createGenericInstance();        
        
        try{
                String videoId = video.getJSONObject("media$group").getJSONObject("yt$videoid").getString("$t");
                JSONObject usrThumbProfile = null;
                if(!video.getJSONObject("media$group").isNull("yt$uploaderId")){
                    String commentProfile = getRequest(paramsUsr, "http://gdata.youtube.com/feeds/api/users/" + video.getJSONObject("media$group").getJSONObject("yt$uploaderId").getString("$t"),
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", null);
                    usrThumbProfile = new JSONObject(commentProfile);
                }
                
                String username = "";
                if(!video.isNull("author")){
                    JSONArray author = video.getJSONArray("author");
                    if(author.length() == 1){
                        username = author.getJSONObject(0).getJSONObject("name").getString("$t");
                    }
                }
                out.write("<div id=\"" + semanticYoutube.getId() +"/" + videoId + "\" class=\"timeline timelinefacebook\">");
                //Username and story

                out.write("<div class=\"timelineusr\">");
                out.write("<a href=\"#\"><img src=\"" + usrThumbProfile.getJSONObject("entry").getJSONObject("media$thumbnail").getString("url") + "\" width=\"50\" height=\"50\"/></a>");
                out.write("<p>"  + username + "</p>");
                out.write("</div>");

                out.write("<div class=\"timelineimg\">");
                out.write(" <span>");
                String imgPath = "";
                JSONArray thumb = video.getJSONObject("media$group").getJSONArray("media$thumbnail");
                for(int i = 0; i < thumb.length(); i++){
                    if(!thumb.getJSONObject(i).isNull("yt$name")){
                        if(thumb.getJSONObject(i).getString("yt$name").equalsIgnoreCase("mqdefault")||
                                thumb.getJSONObject(i).getString("yt$name").equalsIgnoreCase("hqdefault")){
                            imgPath = thumb.getJSONObject(i).getString("url");
                        }
                    }
                }

                out.write("      <span id=\"img" + semanticYoutube.getId() + videoId + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                    out.write("      <a href=\"#\" onclick=\"hideDialog(); showDialog('"+ paramRequest.getRenderUrl().setMode("displayVideo").setParameter("videoUrl", URLEncoder.encode("http://www.youtube.com/v/" + videoId, "UTF-8")) +
                            "','" + video.getJSONObject("title").getString("$t") + "'); return false;\"><img src=\"" + imgPath + "\" style=\"position: relative;\" onerror=\"this.src ='" + imgPath + "'\" onload=\"imageLoad(" + "this, 'img" + semanticYoutube.getId() + videoId + "');\"/></a>");
                    out.write("      </span>");
                out.write(" </span>");
                
                out.write("<p class=\"imgtitle\">");
                out.write(  video.getJSONObject("title").getString("$t"));
                out.write("</p>");

                
                String desc = "";
                if(!video.isNull("media$group")){
                    if(!video.getJSONObject("media$group").isNull("media$description")){
                        desc = video.getJSONObject("media$group").getJSONObject("media$description").getString("$t");
                    }
                }

                out.write("<p class =\"imgdesc\">");
                out.write( desc.isEmpty() ?  "&nbsp;" : desc.replace("\n", "</br>"));
                out.write("</p>");
                out.write("</div>");//End First section
                
                out.write("<div class=\"clear\"></div>");//Clear
                
                
                //With the changes of November 2013
                //I can not longer request the comments of a private video
                boolean isPrivate = false;
                if(!video.getJSONObject("media$group").isNull("yt$private")){
                    isPrivate = true;
                }
                
                //Comments,start
                String ytComments = "";
                //if(!video.isNull("commentCount") && video.getInt("commentCount")>0 && !isPrivate){
                    //System.out.println("URL for comments:" );
                    //System.out.println("token:" + semanticYoutube.getAccessToken());
                    ytComments= this.getRequest(paramsComments, "https://gdata.youtube.com/feeds/api/videos/" + videoId + "/comments",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", semanticYoutube.getAccessToken());
                    ///out.write("</br></br>comments:" + ytComments);
                    JSONObject jsonComments = new JSONObject(ytComments);
                    JSONArray arrayComments = null;
                    if(!jsonComments.isNull("feed")){
                        if(!jsonComments.getJSONObject("feed").isNull("entry")){
                            arrayComments = jsonComments.getJSONObject("feed").getJSONArray("entry");
                        }
                    }                    
                    if(arrayComments != null && arrayComments.length() > 0){
                        out.write("<ul id=\"" + semanticYoutube.getId() + videoId + "/allComments\">");
                        int totalComments = 0;
                        if(!jsonComments.getJSONObject("feed").isNull("openSearch$totalResults")){
                            totalComments = jsonComments.getJSONObject("feed").getJSONObject("openSearch$totalResults").getInt("$t");
                        }
                        if(totalComments > DEFAULT_VIDEO_COMMENTS){
                            out.write("<li class=\"timelinemore\">");
                            out.write("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode.parentNode).innerHTML = '<img src=/swbadmin/icons/loading.gif>';}catch(noe){} postHtml('" + paramRequest.getRenderUrl().setMode(Mode_AllComments).setParameter("suri", request.getParameter("postOutNetNetwork")).setParameter("videoId", videoId).setParameter("totalComments",totalComments+"")
                                    + "','" + semanticYoutube.getId() + videoId +"/allComments'); return false;\"><span>+</span>" + "Ver todos los comentarios" +"</a>");
                            out.write("</li>");
                        }
                            
                        
                        for(int c = 0; c < arrayComments.length(); c++){                            
                            totalComments++;
                            JSONObject comment = arrayComments.getJSONObject(c);
                            JSONObject usrCommentProfile = null;
                            if(!comment.isNull("author")){
                                if(!comment.getJSONArray("author").getJSONObject(0).isNull("yt$userId")){
                                    if(comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t").equalsIgnoreCase("__NO_YOUTUBE_ACCOUNT__")){
                                        continue;
                                    }
                                    String commentProfile = getRequest(paramsUsr, "http://gdata.youtube.com/feeds/api/users/" + comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t"),
                                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", null);
                                    usrCommentProfile = new JSONObject(commentProfile);
                                    
                                }
                            }
                            out.write("<li>");
                            out.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"hideDialog(); showDialog('" + paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("id", comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t")) + "','" + paramRequest.getLocaleString("viewProfile") + "'); return false;\"><img src=\"" + usrCommentProfile.getJSONObject("entry").getJSONObject("media$thumbnail").getString("url") + "\" width=\"50\" height=\"50\"/></a>");

                            out.write("<p>");
                            out.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"hideDialog(); showDialog('" + paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("id", comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t")) + "','" + paramRequest.getLocaleString("viewProfile") + "'); return false;\">" + comment.getJSONArray("author").getJSONObject(0).getJSONObject("name").getString("$t") + "</a>:");
                            out.write(       comment.getJSONObject("content").getString("$t").replace("\n", "</br>"));
                            out.write("</p>");

                            out.write("<p class=\"timelinedate\">");
                            //out.write("<span dojoType=\"dojox.layout.ContentPane\">");
                            out.write("<span>");

                            Date date = formatter.parse(comment.getJSONObject("published").getString("$t"));
                            out.write("<em>" + YoutubeWall.humanFriendlyDate(date, paramRequest) +  "</em>");
                            out.write("</span>");
                            String comentarioId = comment.getJSONObject("id").getString("$t");
                            out.write("   <span class=\"inline\">");
                            out.write(" <a href=\"\" onclick=\"hideDialog(); showDialog('" + paramRequest.getRenderUrl().setMode("commentComment").setParameter("suri", objUri).setParameter("videoId", videoId).setParameter("commentId", comentarioId.substring(comentarioId.indexOf("comment") + 8)) + "','Comment to " + comment.getJSONObject("content").getString("$t").replace("\n", "</br>") + "');return false;\">Comment</a>");
                            out.write("   </span>");
                            out.write("</p>");
                            out.write("</li>");
                        }
                        /*if(!video.isNull("commentCount") && video.getInt("commentCount") > DEFAULT_VIDEO_COMMENTS && totalComments == DEFAULT_VIDEO_COMMENTS){//Link to get more comments
                            //getMoreComments(video.getString("id"), out);
                            out.write("<li class=\"timelinemore\">");
                            out.write("<label><a href=\"#\" onclick=\"appendHtmlAt('" + paramRequest.getRenderUrl().setMode("getMoreComments").setParameter("videoId", videoId).setParameter("startIndex", totalComments + "").setParameter("totalComments",video.getInt("commentCount")+"").setParameter("suri", objUri)
                                    + "','" + semanticYoutube.getId() + videoId +"/comments', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild( this.parentNode.parentNode );}catch(noe){}; return false;\"><span>+</span>" + paramRequest.getLocaleString("moreComments") +"</a></label>");
                            out.write("</li>");
                        }*/
                        out.write("</ul>");
                    }
                //}
                //Comments
                
                out.write("<div class=\"timelineresume\" dojoType=\"dijit.layout.ContentPane\">");//timelineresume
                out.write("<span id=\"" + semanticYoutube.getId() + videoId + "INFORMATION" + "\" class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                Date date = formatter.parse(video.getJSONObject("published").getString("$t"));
                out.write("<em>" + YoutubeWall.humanFriendlyDate(date, paramRequest) + "</em>");
                
                
                if(!video.isNull("yt$statistics")){
                    out.write(paramRequest.getLocaleString("views") + ":" + video.getJSONObject("yt$statistics").getInt("viewCount") + " ");
                }
                /*out.write(" <strong><span> " + paramRequest.getLocaleString("likes") + ": </span>");
                if(video.has("likeCount")){
                    out.write(video.getInt("likeCount") +" ");           
                }else{
                    out.write("0 ");
                }
                
                out.write(" " + paramRequest.getLocaleString("dislikes") + ": ");
                if(video.has("likeCount") && video.has("ratingCount")){
                    out.write(video.getInt("ratingCount") - video.getInt("likeCount") + " ");
                }else{
                    out.write("0 ");
                }
                
                if(!video.isNull("yt$statistics")){
                    out.write(" " + paramRequest.getLocaleString("favorites") + ": " + video.getJSONObject("yt$statistics").getInt("favoriteCount"));
                }
                */
                out.write("</strong>");
                out.write("</span>");
                
                out.write("   <span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                out.write(" <a class=\"clasifica\" href=\"\" onclick=\"hideDialog(); showDialog('" + paramRequest.getRenderUrl().setMode(Mode_CommentVideo).setParameter("suri", objUri).setParameter("videoId", videoId) + "','Comment to " + "MISSING" + "');return false;\"><span>Comment</span></a>  ");                    
                out.write("   </span>");

                postURI = null;
                PostIn post = PostIn.getPostInbySocialMsgId(model, videoId);
                if(post != null){
                    postURI = post.getURI();
                }
                /*
                out.write("   <span class=\"inline\" id=\"" + semanticYoutube.getId() + videoId + "TOPIC"  + "\" dojoType=\"dojox.layout.ContentPane\">");
                if(socialUserExtAttr != null && socialUserExtAttr.isUserCanReTopicMsg()){
                    if(postURI != null){//If post already exists
                        SWBResourceURL clasifybyTopic = paramRequest.getRenderUrl().setMode("doReclassifyTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("videoId", videoId).setParameter("postUri", postURI).setParameter("suri", objUri);
                        out.write("<a href=\"#\" class=\"clasifica\" title=\"" + paramRequest.getLocaleString("reclassify") + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                            + paramRequest.getLocaleString("reclassify") + "'); return false;\"><span>" + paramRequest.getLocaleString("reclassify") + "</span></a>");
                    }else{//If posts does not exists 
                        SWBResourceURL clasifybyTopic = paramRequest.getRenderUrl().setMode("doShowTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", videoId).setParameter("postUri", postURI).setParameter("suri", objUri);
                        out.write("<a href=\"#\" class=\"clasifica\" class=\"clasifica\" title=\"" + paramRequest.getLocaleString("classify") + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                            + paramRequest.getLocaleString("classify") +"'); return false;\"><span>" + paramRequest.getLocaleString("classify") +"</span></a>");
                    }
                }else{
                    out.write("&nbsp;");
                }
                out.write("   </span>");
                */
                
                /**out.write("   <span id=\"" + semanticYoutube.getId() + videoId +  "LIKE" + "\" class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                out.write("<a href=\"#\" class=\"like\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + paramRequest.getActionUrl().setAction("doLike").setParameter("suri", objUri).setParameter("action", "like").setParameter("videoId", videoId) + "','" + semanticYoutube.getId() +  videoId + "INFORMATION" + "'); return false;\">" + paramRequest.getLocaleString("like") + "</a>");
                out.write("   </span>");**/
                
                /*out.write("   <span id=\"" + semanticYoutube.getId() + videoId +  "/edit" + "\" class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                SWBResourceURL editVideo = paramRequest.getRenderUrl().setMode("editVideo").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("videoId", videoId).setParameter("suri", objUri);
                out.write("<a href=\"#\" class=\"editarYoutube\" title=\"" + paramRequest.getLocaleString("edit") + "\" onclick=\"showDialog('" + editVideo + "','"
                    + paramRequest.getLocaleString("edit") +"'); return false;\"><span>" + paramRequest.getLocaleString("edit") +"</span></a>");
                out.write("   </span>");
                
                out.write("   <span id=\"" + semanticYoutube.getId() + videoId +  "/delete" + "\" class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                out.write("<a href=\"#\" class=\"eliminarYoutube\" onclick=\"if(confirm('" + paramRequest.getLocaleString("confirmDelete") + "')){ postSocialHtml('" + paramRequest.getActionUrl().setAction("doDeleteVideo").setParameter("suri", objUri).setParameter("videoId", videoId) + "','" + semanticYoutube.getId() + "/" +  videoId + "');} return false;\">" + paramRequest.getLocaleString("deleteVideo") + "</a>");
                out.write("   </span>");*/
                
                out.write("</div>");//timelineresume
                
                out.write("</div>");
        }catch(Exception e){
            log.error("Problema imprimiendo video ", e);
        }
         
     }
    
    public String getRequest(Map<String, String> params, String url,
            String userAgent, String accessToken) throws IOException {
        
        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = new URL(url + "?" +  paramString);       
        //System.out.println("URL:" +  serverUrl);
        
        HttpURLConnection conex = null;
        InputStream in = null;
        String response = null;
       
        try {
            conex = (HttpURLConnection) serverUrl.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);                
            }
            ///Validate if i am looking for the default user or another
            if(accessToken != null){
                conex.setRequestProperty("Authorization", "Bearer " + accessToken);
            }

            conex.setConnectTimeout(30000);
            conex.setReadTimeout(60000);
            conex.setRequestMethod("GET");
            conex.setDoOutput(true);
            conex.connect();
            in = conex.getInputStream();
            response = getResponse(in);
        } catch (java.io.IOException ioe) {
            if (conex.getResponseCode() >= 400) {
                response = getResponse(conex.getErrorStream());
                log.error(response);
            }
            ioe.printStackTrace();
        } finally {
            close(in);
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }
    /**
     * Gets all the comments of a post or video (Youtube)
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException 
     */
    public void doGetAllComments(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        String suri = request.getParameter("suri");
        System.out.println("suri:" + request.getParameter("suri"));
        System.out.println("postId:" + request.getParameter("postId"));
        SocialNetwork socialNetwork = (SocialNetwork)SemanticObject.createSemanticObject(suri).createGenericInstance();
        PrintWriter out = response.getWriter();
        if(socialNetwork instanceof Facebook){
            Facebook facebook = (Facebook)socialNetwork;
            String postId = request.getParameter("postId");
            HashMap<String, String> params = new HashMap<String, String>(3);    
            params.put("q", "{\"comments\": \"SELECT id, text, time, fromid, attachment, can_like, can_remove, likes, user_likes from comment where post_id='" + postId +"' ORDER BY time DESC\", \"usernames\": \"SELECT uid, name FROM user WHERE uid IN (SELECT fromid FROM #comments)\", \"pages\":\"SELECT page_id, name FROM page WHERE page_id IN (SELECT fromid FROM #comments)\"}");
            params.put("access_token", facebook.getAccessToken());

            String fbComments = null;
            try{
                fbComments = getRequest(params, "https://graph.facebook.com/fql",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

                SWBResourceURL renderURL = paramRequest.getRenderUrl();
                renderURL.setParameter("suri", suri);
                JSONObject phraseResp = new JSONObject(fbComments);
                JSONArray commentsData = null;
                JSONArray userData = null;
                JSONArray pageData = null;

                for(int i = 0; i < phraseResp.getJSONArray("data").length(); i++){
                    if(phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("comments")){//All the posts
                        commentsData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                    }else if(phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("pages")){//All the pages
                        pageData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                    }else if(phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("usernames")){//All the users
                        userData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                    }
                }

                HashMap<Long, String> users = new HashMap<Long, String>();
                HashMap<Long, String> pages = new HashMap<Long, String>();            
                for(int i= 0; i< userData.length(); i++){
                    users.put(userData.getJSONObject(i).getLong("uid"), userData.getJSONObject(i).getString("name"));
                }            
                for(int i= 0; i< pageData.length(); i++){
                    pages.put(pageData.getJSONObject(i).getLong("page_id"), pageData.getJSONObject(i).getString("name"));
                }

                for(int i= 0; i<commentsData.length(); i++){
                    JSONObject comment = commentsData.getJSONObject(i);
                    String username = null;
                    if(users.get(comment.getLong("fromid")) != null){
                        username = users.get(comment.getLong("fromid"));
                    }else if(pages.get(comment.getLong("fromid")) != null){
                        username = pages.get(comment.getLong("fromid"));
                    }
                    out.write("<li>");
                    out.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"hideDialog(); showDialog('" + renderURL.setMode("fullProfile").setParameter("type","noType").setParameter("id",comment.getLong("fromid")+"") + "','" + username + "'); return false;\"><img src=\"http://graph.facebook.com/" + comment.getLong("fromid") +"/picture?width=30&height=30\" width=\"30\" height=\"30\"/></a>");

                    out.write("<p>");
                    out.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"hideDialog(); showDialog('" + renderURL.setMode("fullProfile").setParameter("type","noType").setParameter("id", comment.getLong("fromid")+"") + "','" + username + "'); return false;\">" + username + "</a>:");
                    out.write(       comment.getString("text").replace("\n", "</br>") + "</br>");
                    out.write("</p>");

                    Date commentTime = new java.util.Date((long)comment.getLong("time")*1000);

                    out.write("<p class=\"timelinedate\">");
                    out.write("<span dojoType=\"dojox.layout.ContentPane\">");

                    out.write("<em>" + FacebookWall.facebookHumanFriendlyDate(commentTime, paramRequest) + "</em>");
                    if(comment.has("likes")){
                        out.write("<strong>");
                        out.write("<span>Likes:</span> " + comment.getInt("likes") );
                        out.write("</strong>");
                    }
                    out.write("</span>");
                    out.write("</p>");
                    out.write("</li>");
                }
            }catch(Exception e){
                log.error("Error getting user information" , e);
            }
        }else if(socialNetwork instanceof Youtube){
            Youtube youtube = (Youtube)socialNetwork;
            String videoId = request.getParameter("videoId");
            String totalCommentsStr = request.getParameter("totalComments");
            

            try{
                HashMap<String, String> paramsUsr = new HashMap<String, String>(3);
                paramsUsr.put("v", "2");
                paramsUsr.put("fields", "media:thumbnail");
                paramsUsr.put("alt", "json");
                
                int totalComments = Integer.parseInt(totalCommentsStr);
                int recoveredComments = 0;
                //Make several request to get all the comments
                do{
                    HashMap<String, String> paramsComments = new HashMap<String, String>(3);
                    paramsComments.put("v", "2");
                    //paramsComments.put("max-results", "10");
                    paramsComments.put("start-index", String.valueOf(recoveredComments + 1));
                    paramsComments.put("alt", "json");
                    String ytComments= getRequest(paramsComments, "https://gdata.youtube.com/feeds/api/videos/" + videoId + "/comments",
                                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", youtube.getAccessToken());
                    JSONObject jsonComments = new JSONObject(ytComments);
                    JSONArray arrayComments = null;

                    if(!jsonComments.isNull("feed")){
                        if(!jsonComments.getJSONObject("feed").isNull("entry")){
                            arrayComments = jsonComments.getJSONObject("feed").getJSONArray("entry");
                        }
                    }

                    if(arrayComments != null && arrayComments.length() > 0){//Only print <li></li> because the HTML will be returned inside <ul></ul
                        int commentCounter = 0;
                        for(int c = 0; c < arrayComments.length(); c++){
                            commentCounter++;
                            JSONObject comment = arrayComments.getJSONObject(c);
                            JSONObject usrCommentProfile = null;
                            if(!comment.isNull("author")){
                                if(!comment.getJSONArray("author").getJSONObject(0).isNull("yt$userId")){
                                    if(comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t").equalsIgnoreCase("__NO_YOUTUBE_ACCOUNT__")){
                                        continue;
                                    }
                                    String commentProfile = getRequest(paramsUsr, "http://gdata.youtube.com/feeds/api/users/" + comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t"),
                                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", null);
                                    usrCommentProfile = new JSONObject(commentProfile);
                                }
                            }
                            out.write("<li>");
                            out.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"hideDialog(); showDialog('" + paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("id", comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t")) + "','" + paramRequest.getLocaleString("viewProfile") + "'); return false;\"><img src=\"" + usrCommentProfile.getJSONObject("entry").getJSONObject("media$thumbnail").getString("url") + "\" width=\"50\" height=\"50\"/></a>");

                            out.write("<p>");
                            out.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"hideDialog(); showDialog('" + paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("id", comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t")) + "','" + paramRequest.getLocaleString("viewProfile") + "'); return false;\">" + comment.getJSONArray("author").getJSONObject(0).getJSONObject("name").getString("$t") + "</a>:");
                            out.write(       comment.getJSONObject("content").getString("$t").replace("\n", "</br>"));
                            out.write("</p>");

                            //Date commentTime = formatter.parse(comments.getJSONObject(k).getString("created_time"));

                            out.write("<p class=\"timelinedate\">");
                            //out.write("<span dojoType=\"dojox.layout.ContentPane\">");
                            out.write("<span>");
                            Date date = formatter.parse(comment.getJSONObject("published").getString("$t"));
                            out.write("<em>" + YoutubeWall.humanFriendlyDate(date, paramRequest) +  "</em>");
                            out.write("</span>");
                            String comentarioId = comment.getJSONObject("id").getString("$t");
                            out.write("   <span class=\"inline\">");
                            out.write(" <a href=\"\" onclick=\"hideDialog(); showDialog('" + paramRequest.getRenderUrl().setMode("commentComment").setParameter("suri", suri).setParameter("videoId",videoId).setParameter("commentId", comentarioId.substring(comentarioId.indexOf("comment") + 8)) + "','Comment to " + comment.getJSONObject("content").getString("$t").replace("\n", "</br>") + "');return false;\">Comment</a>");
                            out.write("   </span>");
                            out.write("</p>");
                            out.write("</li>");
                            recoveredComments ++;
                        }
                        System.out.println("SE OBTUVIERON :" + commentCounter + " COMENTARIOS");                        
                    }else{
                        break;
                    }
                }while (recoveredComments < totalComments);
            }catch(Exception e){
                System.out.println("ERROR GETTING MORE COMMENTS");
                log.error("Problem getting more comments", e);
            }
        }
    }
    
    public void doCreatePost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/post/typeOfContent.jsp");
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
    
    public void doCommentVideo(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        SocialNetwork socialNetwork = null;
        String videoId = request.getParameter("videoId");
        String objUri = request.getParameter("suri");

        try {
            socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
        }catch(Exception e){
            System.out.println("Error getting the SocialNetwork " + e);
            return;
        }

        try {
            SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());

            PostIn postIn = PostIn.getPostInbySocialMsgId(model, videoId);

            if(postIn == null){//Responding for the first time, save the post
                HashMap<String, String> paramsVideo = new HashMap<String, String>(3);
                paramsVideo.put("v", "2");            
                paramsVideo.put("alt", "json");//https://gdata.youtube.com/feeds/api/videos/videoid?v=2
                String ytVideo= getRequest(paramsVideo, "https://gdata.youtube.com/feeds/api/videos/" + videoId,
                                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", ((Youtube)socialNetwork).getAccessToken());
                JSONObject jsonVideo = new JSONObject(ytVideo);

                String title = "";
                String description = "";
                String creatorName = "";
                String creatorId =  "";

                if(jsonVideo.has("entry")){
                    if(jsonVideo.getJSONObject("entry").has("title")){//Title
                        title = jsonVideo.getJSONObject("entry").getJSONObject("title").getString("$t");
                    }

                    if(jsonVideo.getJSONObject("entry").has("media$group")){//Desc
                        if(jsonVideo.getJSONObject("entry").getJSONObject("media$group").has("media$description")){
                            description = jsonVideo.getJSONObject("entry").getJSONObject("media$group").getJSONObject("media$description").getString("$t");
                        }
                    }

                    if(jsonVideo.getJSONObject("entry").has("author")){//User
                        if(jsonVideo.getJSONObject("entry").getJSONArray("author").getJSONObject(0).has("name")){
                            creatorName = jsonVideo.getJSONObject("entry").getJSONArray("author").getJSONObject(0).getJSONObject("name").getString("$t");
                        }

                        if(jsonVideo.getJSONObject("entry").getJSONArray("author").getJSONObject(0).has("yt$userId")){
                            creatorId = jsonVideo.getJSONObject("entry").getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t");
                        }
                    }
                }

                SocialNetworkUser socialNetUser = SocialNetworkUser.getSocialNetworkUserbyIDAndSocialNet(creatorId, socialNetwork, model);

                postIn=VideoIn.ClassMgr.createVideoIn(model);
                postIn.setSocialNetMsgId(videoId);
                postIn.setMsg_Text(title + (description.isEmpty()? "" : " / " + description));
                postIn.setPostInSocialNetwork(socialNetwork);
                postIn.setPostInStream(null);
                Calendar calendario = Calendar.getInstance();
                postIn.setPi_created(calendario.getTime());
                postIn.setPi_type(SWBSocialUtil.POST_TYPE_VIDEO);

                VideoIn videoIn=(VideoIn)postIn;
                videoIn.setVideo(YoutubeWall.BASE_VIDEO_URL + videoId);

                 if(socialNetUser == null){//User does not exist                    
                    System.out.println("USUARIO NO EXISTE EN EL SISTEMA");
                    socialNetUser=SocialNetworkUser.ClassMgr.createSocialNetworkUser(model);//Create a socialNetworkUser
                    socialNetUser.setSnu_id(creatorId);
                    socialNetUser.setSnu_name((creatorName.isEmpty()) ? creatorId : creatorName);
                    socialNetUser.setSnu_SocialNetworkObj(socialNetwork.getSemanticObject());                    
                    socialNetUser.setCreated(new Date());
                    socialNetUser.setFollowers(0);
                    socialNetUser.setFriends(0);
                }else{
                    System.out.println("YA EXISTE EN EL SISTEMA:" + socialNetUser);
                }

                postIn.setPostInSocialNetworkUser(socialNetUser);

                SocialTopic defaultSocialTopic = SocialTopic.ClassMgr.getSocialTopic("DefaultTopic", model);
                if(defaultSocialTopic != null){
                    postIn.setSocialTopic(defaultSocialTopic);//Asigns socialTipic
                    System.out.println("Setting social topic:" + defaultSocialTopic);
                }else{
                    postIn.setSocialTopic(null);
                    System.out.println("Setting to null");
                }
            }

            //The post in has been created
            final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/postInResponse.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(path);
            if (dis != null) {
                try {
                    request.setAttribute("postUri", SemanticObject.createSemanticObject(postIn.getURI()));
                    request.setAttribute("paramRequest", paramRequest);
                    dis.include(request, response);
                } catch (Exception e) {
                    log.error(e);
                }
            }

            System.out.println("POST CREADO CORRECTAMENTE: " + postIn.getId() + " ** " + postIn.getSocialNetMsgId());
        }catch(Exception e){
            System.out.println("Error trying to setSocialTopic");
            log.error("ERROR:", e);
        }
    }
    
    public void doReplyPost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Facebook facebook = null;
        String idPost = request.getParameter("postID");
        String objUri = request.getParameter("suri");
        try {
            facebook = (Facebook) SemanticObject.getSemanticObject(objUri).getGenericInstance();
        } catch (Exception e) {
            log.error("Error getting the SocialNetwork " + e);
            return;
        }

        SocialNetwork socialNetwork = null;
        try {
            socialNetwork = (SocialNetwork) SemanticObject.getSemanticObject(objUri).getGenericInstance();
        } catch (Exception e) {
            log.error("Error getting the SocialNetwork " + e);
            return;
        }
        SocialNetworkUser socialNetUser = null;

        SWBModel model = WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
        PostIn postIn = null;
        try {
            postIn = PostIn.getPostInbySocialMsgId(model, idPost);

            if (postIn == null) {
                JSONObject postData = FacebookWall.getPostFromFullId(idPost, facebook);
                System.out.println("This is the post: " + postData);
                socialNetUser = SocialNetworkUser.getSocialNetworkUserbyIDAndSocialNet(postData.getJSONObject("from").getString("id"), socialNetwork, model);

                if (socialNetUser == null) {
                    System.out.println("\n\nEL USUARIO NO EXISTE");
                } else {
                    System.out.println("\n\nEL USUARIO EXISTE: " + socialNetUser.getSnu_id());
                }

                if (socialNetUser == null) {
                    socialNetUser = SocialNetworkUser.ClassMgr.createSocialNetworkUser(model);//Create a socialNetworkUser
                    socialNetUser.setSnu_id(postData.getJSONObject("from").getString("id"));
                    socialNetUser.setSnu_name(postData.getJSONObject("from").getString("name"));
                    socialNetUser.setSnu_SocialNetworkObj(socialNetwork.getSemanticObject());
                    socialNetUser.setSnu_photoUrl("https://graph.facebook.com/" + postData.getJSONObject("from").getString("id") + "/picture?width=150&height=150");
                    socialNetUser.setCreated(new Date());
                    //TODO: Llamar al getUserInfoById
                    socialNetUser.setFollowers(0);
                    socialNetUser.setFriends(0);
                    System.out.println("YA SE CREO EL USUARIO!!!");
                }

                String postType = "";
                if (postData.has("type")) {
                    postType = postData.getString("type");
                }else if(!postData.isNull("picture") && !postData.isNull("source")){
                    postType = "video";
                }else if(!postData.isNull("picture")){
                    postType = "photo";
                }else if (postData.has("picture") && postData.has("name") && postData.has("link") && postData.has("description")) {
                    postType = "link";
                }
                String message = "";
                String story = "";

                if (postType.equals("status") || postType.equals("link") || postType.equals("checkin")) {
                    postIn = MessageIn.ClassMgr.createMessageIn(model);
                    postIn.setPi_type(SWBSocialUtil.POST_TYPE_MESSAGE);
                    if (postType.equals("status")) {
                        if (!postData.isNull("message")) {
                            message = postData.getString("message");
                        } else if (!postData.isNull("story")) {
                            story = (!postData.isNull("story")) ? postData.getString("story") : "";
                            story = FacebookWall.getTagsFromPost(postData.getJSONObject("story_tags"), story);
                        }
                        if (!message.isEmpty()) {
                            postIn.setMsg_Text(message);
                        } else if (!story.isEmpty()) {
                            postIn.setMsg_Text(story);
                        } else {
                            postIn.setMsg_Text("");
                        }

                        System.out.println("********************STATUS guardado OK");
                    } else if (postType.equals("link")) {
                        if (!postData.isNull("story")) {
                            story = (!postData.isNull("story")) ? postData.getString("story") : "";
                            story = FacebookWall.getTagsFromPost(postData.getJSONObject("story_tags"), story);
                        }
                        if (!postData.isNull("message")) {
                            message = postData.getString("message");
                        }

                        if (!message.isEmpty()) {
                            postIn.setMsg_Text(message);
                        } else if (!story.isEmpty()) {
                            postIn.setMsg_Text(story);
                        } else {
                            postIn.setMsg_Text("");
                        }
                        System.out.println("********************LINK guardado OK");
                    }
                } else if (postType.equals("video") || postType.equals("swf")) {
                    System.out.println("ES UN VIDEO\n\n\n****");
                    postIn = VideoIn.ClassMgr.createVideoIn(model);
                    postIn.setPi_type(SWBSocialUtil.POST_TYPE_VIDEO);
                    //Get message and/or story
                    if (!postData.isNull("message")) {
                        message = postData.getString("message");
                    } else if (!postData.isNull("story")) {
                        story = (!postData.isNull("story")) ? postData.getString("story") : "";
                        story = FacebookWall.getTagsFromPost(postData.getJSONObject("story_tags"), story);
                    }

                    System.out.println("THE MESSAGE******\n" + message);
                    System.out.println("THE STORY******\n" + story);
                    if (!message.isEmpty()) {
                        System.out.println("SETTING MESSAGE");
                        postIn.setMsg_Text(message);
                    } else if (!story.isEmpty()) {
                        System.out.println("SETTING STORY");
                        postIn.setMsg_Text(story);
                    } else {
                        System.out.println("SETTING ONLY THE NAME!!!!!");
                        postIn.setMsg_Text("<a href=\"" + postData.getString("source") + "\" target=\"_blank\">" + postData.getString("name") + "</a>");
                    }

                    if (postData.has("source")) {
                        System.out.println("Setting the VIDEO");
                        VideoIn videoIn = (VideoIn) postIn;
                        videoIn.setVideo(postData.getString("source"));
                    }
                    System.out.println("********************VIDEO guardado OK");
                } else if (postType.equals("photo")) {
                    postIn = PhotoIn.ClassMgr.createPhotoIn(model);
                    postIn.setPi_type(SWBSocialUtil.POST_TYPE_PHOTO);
                    //Get message and/or story
                    if (!postData.isNull("message")) {
                        message = postData.getString("message");
                    } else if (!postData.isNull("story")) {
                        story = (!postData.isNull("story")) ? postData.getString("story") : "";
                        story = FacebookWall.getTagsFromPost(postData.getJSONObject("story_tags"), story);
                    }

                    System.out.println("\tMESSAGE:" + message);
                    System.out.println("\tSTORY:" + story);
                    if (!message.isEmpty()) {
                        postIn.setMsg_Text(message);
                    } else if (!story.isEmpty()) {
                        postIn.setMsg_Text(story);
                    } else {
                        postIn.setMsg_Text("");
                    }

                    if (postData.has("picture")) {
                        String photo = postData.getString("picture");
                        PhotoIn photoIn = (PhotoIn) postIn;
                        photoIn.addPhoto(photo);
                    }
                    System.out.println("********************STATUS guardado OK");
                }

                System.out.println("POSTDATA:" + postData.getString("id"));
                //Information of post IN
                postIn.setSocialNetMsgId(postData.getString("id"));
                postIn.setPostInSocialNetwork(socialNetwork);
                postIn.setPostInStream(null);
                postIn.setPostInSocialNetworkUser(socialNetUser);
                Calendar calendario = Calendar.getInstance();
                postIn.setPi_created(calendario.getTime());

                SocialTopic defaultSocialTopic = SocialTopic.ClassMgr.getSocialTopic("DefaultTopic", model);
                if (defaultSocialTopic != null) {
                    postIn.setSocialTopic(defaultSocialTopic);//Asigns socialTipic
                    System.out.println("Setting social topic:" + defaultSocialTopic);
                } else {
                    postIn.setSocialTopic(null);
                    System.out.println("Setting to null");
                }
            }
        } catch (Exception e) {
            log.error("Error trying to setSocialTopic: ", e);
        }

        final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/postInResponse.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        if (dis != null) {
            try {
                request.setAttribute("postUri", SemanticObject.createSemanticObject(postIn.getURI()));
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
        //Post saved
    }
    
    public void doShowFullProfile(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        String profileType = request.getParameter("type") == null ? "" : (String) request.getParameter("type");
        String objUri = (String) request.getParameter("suri");
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook) semanticObject.createGenericInstance();
        String jspResponse;

        if (profileType.equals("noType")) {
            try {
                JSONObject profile = new JSONObject(FacebookWall.getProfileFromId(request.getParameter("id"), facebook));
                profile = profile.getJSONArray("data").getJSONObject(0);
                profileType = profile.getString("type");
            } catch (JSONException jsone) {
                log.error("Error getting profile information" + jsone);
                return;
            }
        }
        if (profileType.equals("user")) {
            jspResponse = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialNetworks/facebookUserProfile.jsp";
        } else if (profileType.equals("page")) {
            jspResponse = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialNetworks/facebookPageProfile.jsp";
        } else {
            return;
        }

        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            System.out.println("Error displaying user profile");
        }
    }
    
    public void doPostSent(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<script type=\"text/javascript\">");
        out.println("   hideDialog();");
        out.println("   showStatus('Post sent');");
        out.println("</script>");
    }
    //--
}