/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticIterator;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Message;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.Photo;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.Post;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.PostOut;
import org.semanticwb.social.SentimentalLearningPhrase;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialNetworkUser;
import org.semanticwb.social.SocialPFlow;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.SocialUserExtAttributes;
import org.semanticwb.social.Stream;
import org.semanticwb.social.Video;
import org.semanticwb.social.VideoIn;
import org.semanticwb.social.util.SWBSocialComparator;
import org.semanticwb.social.util.SWBSocialUtil;
import org.semanticwb.social.util.SocialLoader;

/**
 *
 * @author jorge.jimenez
 */
public class SocialTopicInBox extends GenericResource {

    /**
     * The log.
     */
    private Logger log = SWBUtils.getLogger(SocialTopicInBox.class);
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
    //String Mode_Action = "paction";
    String Mode_PFlowMsg = "doPflowMsg";
    String Mode_PreView = "preview";
    String Mode_showTags = "showTags";
    private static final int RECPERPAGE = 20; //Number of records by Page, could be dynamic later
    private static final int PAGES2VIEW = 15; //Number of pages 2 display in pagination.
    
    /**
     * Creates a new instance of SWBAWebPageContents.
     */
    public SocialTopicInBox() {
    }
    public static final String Mode_RECLASSBYTOPIC = "reclassByTopic";
    public static final String Mode_RESPONSE = "response";
    public static final String Mode_PREVIEW = "preview";
    public static final String Mode_ShowUsrHistory = "showUsrHistory";
    public static final String Mode_RESPONSES = "responses";
    public static final String Mode_SHOWPOSTOUT = "showPostOut";

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if (Mode_RECLASSBYTOPIC.equals(mode)) {
            doReClassifyByTopic(request, response, paramRequest);
        } else if (Mode_ShowUsrHistory.equals(mode)) {
            doShowUserHistory(request, response, paramRequest);
        } else if (Mode_PREVIEW.equals(mode)) {
            doPreview(request, response, paramRequest);
        } else if (Mode_RESPONSE.equals(mode)) {
            doResponse(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("post")) {
            doCreatePost(request, response, paramRequest);
        } else if (Mode_showTags.equals(mode)) {
            doShowTags(request, response, paramRequest);
        } else if (Mode_RESPONSES.equals(mode)) {
            doShowResponses(request, response, paramRequest);
        } else if (Mode_SHOWPOSTOUT.equals(mode)) {
            doShowPostOut(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("exportExcel")) {
            try {
                doGenerateReport(request, response, paramRequest);
            } catch (Exception e) {
                log.error(e);
            }
        } else {
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
        log.debug("doEdit()");

        String id = request.getParameter("suri");
        if (id == null) {
            return;
        }

        SocialTopic socialTopic = (SocialTopic) SemanticObject.getSemanticObject(id).getGenericInstance();
        WebSite wsite = WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());

        PrintWriter out = response.getWriter();
        //Resource base = getResourceBase();
        //User user = paramRequest.getUser();

        /*
         String busqueda = request.getParameter("search");
         if (null == busqueda) {
         busqueda = "";
         }*/


        if (request.getParameter("leyendReconfirm") != null) {

            //Remove
            SWBResourceURL urlrConfirm = paramRequest.getActionUrl();
            urlrConfirm.setParameter("suri", id);
            urlrConfirm.setParameter("sval", request.getParameter("postUri"));
            urlrConfirm.setAction("removeConfirm");

            out.println("<form name=\"formStreamInBox_removePostIn\" id=\"formStreamInBox_removePostIn\" class=\"swbform\" method=\"post\" action=\"" + urlrConfirm + "\" onsubmit=\"alert('entra a onSubmit k...');submitForm('formStreamInBox_removePostIn');return false;\">");
            out.println("</form>");

            out.println("<script type=\"javascript\">");
            out.println("   if(confirm('" + request.getParameter("leyendReconfirm") + "," + paramRequest.getLocaleString("deleteAnyWay") + "?')) { submitForm('formStreamInBox_removePostIn');}");
            out.println("</script>");
        }

        //if (request.getParameter("statusMsg") != null) {
        out.println("<script type=\"javascript\">");
        if (request.getParameter("dialog") != null && request.getParameter("dialog").equals("close")) {
            out.println(" hideDialog(); ");
        }
        if (request.getParameter("statusMsg") != null) {
            out.println("   showStatus('" + request.getParameter("statusMsg") + "');");
        }
        if (request.getParameter("reloadTap") != null) {
            out.println(" reloadTab('" + id + "'); ");
        }
        out.println("</script>");
        //return;
        //}

        out.println("<style type=\"text/css\">");
        out.println(".spanFormat");
        out.println("{");
        out.println("  text-align: right;");
        out.println("  display: table-cell;");
        out.println("  min-width: 10px;");
        out.println("  padding-right: 10px;");
        out.println("}");
        out.println("</style>");

        //String action = request.getParameter("act");

        SWBResourceURL urls = paramRequest.getRenderUrl();
        urls.setParameter("act", "");
        urls.setParameter("suri", id);

        String searchWord = request.getParameter("search");
        String swbSocialUser = request.getParameter("swbSocialUser");

        String page = request.getParameter("page");
        if (page == null && request.getParameter("noSaveSess") == null) //Cuando venga page!=null no se mete nada a session, ni tampoco se manda return.
        {
            //System.out.println("Entra a Cuestiones de Sesión-Jorge---");
            HttpSession session = request.getSession(true);
            if (null == searchWord) {
                searchWord = "";
                if (session.getAttribute(id + this.getClass().getName() + "search") != null) {
                    searchWord = (String) session.getAttribute(id + this.getClass().getName() + "search");
                    session.removeAttribute(id + this.getClass().getName() + "search");
                }
            } else {//Add word to session var
                session.setAttribute(id + this.getClass().getName() + "search", searchWord);//Save the word in the session var
                return;
            }
            if (null == swbSocialUser) {
                if (session.getAttribute(id + this.getClass().getName() + "swbSocialUser") != null) {
                    swbSocialUser = (String) session.getAttribute(id + this.getClass().getName() + "swbSocialUser");
                    session.removeAttribute(id + this.getClass().getName() + "swbSocialUser");
                }
            } else {//Add word to session var
                session.setAttribute(id + this.getClass().getName() + "swbSocialUser", swbSocialUser);//Save the word in the session var
                return;
            }
        }



        out.println("<div class=\"swbform\">");

        out.println("<fieldset class=\"barra\">");
        out.println("<div class=\"barra\">"); 
        
        /*
        out.println("<span  class=\"spanFormat\">");
        out.println("<form id=\"" + id + "/fsearchSocialT\" name=\"" + id + "/fsearchSocialT\" method=\"post\" action=\"" + urls + "\" onsubmit=\"submitForm('" + id + "/fsearchSocialT');return false;\">");
        out.println("<div align=\"right\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
        out.println("<input type=\"hidden\" name=\"noSaveSess\" value=\"1\">");
        out.println("<label for=\"" + id + "_fsearchSocialT\">" + paramRequest.getLocaleString("searchPost") + ": </label><input type=\"text\" name=\"search\" id=\"" + id + "_fsearchSocialT\" value=\"" + searchWord + "\">");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("btnSearch") + "</button>"); //
        out.println("</div>");
        out.println("</form>");
        out.println("</span>");
        * */
        /*
        out.println("<span  class=\"spanFormat\">");
        out.println("<button dojoType='dijit.form.Button'  onclick=\"showDialog('" + tagUrl + "','" + paramRequest.getLocaleString("tagLabel") + "'); return false;\">" + paramRequest.getLocaleString("btnCloud") + "</button>");
        out.println("</span>");
        * */
        if (page == null) {
            page = "1";
        }
        String orderBy = request.getParameter("orderBy");

        /*
        out.println("<span  class=\"spanFormat\">");
        out.println("<form id=\"" + id + "/importCurrentPage\" name=\"" + id + "/importCurrentPage\" method=\"post\" action=\"" + urls.setMode("exportExcel").setParameter("pages", page).setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("orderBy", orderBy) + "\" >");
        out.println("<div align=\"right\">");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("importCurrentPage") + "</button>"); //
        out.println("</div>");
        out.println("</form>");
        out.println("</span>");
        * */
        out.println("<a href=\""+urls.setMode("exportExcel").setParameter("pages", page).setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("orderBy", orderBy)+"\" class=\"excel\">"+paramRequest.getLocaleString("importCurrentPage")+"</a>");

        /*
        out.println("<span  class=\"spanFormat\">");
        out.println("<form id=\"" + id + "/importAll\" name=\"" + id + "/importAll\" method=\"post\" action=\"" + urls.setMode("exportExcel").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("pages", "0").setParameter("orderBy", orderBy) + "\" >");
        out.println("<div align=\"right\">");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("importAll") + "</button>"); //
        out.println("</div>");
        out.println("</form>");
        out.println("</span>");
        * */
        out.println("<a href=\""+urls.setMode("exportExcel").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("pages", "0").setParameter("orderBy", orderBy)+"\" class=\"excelall\">"+paramRequest.getLocaleString("importAll")+"</a>");
        
        //TAG CLOUD
        SWBResourceURL tagUrl = paramRequest.getRenderUrl();
        tagUrl.setParameter("suri", id);
        tagUrl.setMode(Mode_showTags);
        out.println("<a href=\"#\" onclick=\"showDialog('" + tagUrl + "','" + paramRequest.getLocaleString("tagLabel") + "'); return false;\" class=\"btnCloud\">"+paramRequest.getLocaleString("btnCloud")+"</a>");
        //ENDS TAG CLOUD
        
        //out.println("<span  class=\"spanFormat\">");
        out.println("<form id=\"" + id + "/fsearchSocialT\" name=\"" + id + "/fsearchSocialT\" method=\"post\" action=\"" + urls + "\" onsubmit=\"submitForm('" + id + "/fsearchSocialT');return false;\">");
        out.println("<div align=\"right\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
        out.println("<input type=\"hidden\" name=\"noSaveSess\" value=\"1\">");
        out.println("<input type=\"text\" name=\"search\" id=\"" + id + "_fsearchSocialT\" value=\"" + searchWord + "\" placeholder=\""+paramRequest.getLocaleString("searchPost")+"\">");
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
        out.println(paramRequest.getLocaleString("action"));
        out.println("</th>");


        out.println("<th class=\"mensaje\">");
        out.println(paramRequest.getLocaleString("post"));
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


        String nameClassNetwork = "ascen";
        String typeOrderNetwork = "Ordenar Ascendente";
        urlOderby.setParameter("orderBy", "networkDown");
        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("networkUp") || request.getParameter("orderBy").equals("networkDown")) {
                if (request.getParameter("nameClassNetwork") != null) {
                    if (request.getParameter("nameClassNetwork").equals("descen")) {
                        nameClassNetwork = "ascen";
                    } else {
                        nameClassNetwork = "descen";
                        urlOderby.setParameter("orderBy", "networkUp");
                        typeOrderNetwork = "Ordenar Descendente";
                    }
                }
            }
        }
        out.println("<th>");
        urlOderby.setParameter("nameClassNetwork", nameClassNetwork);
        out.println("<a href=\"#\" class=\"" + nameClassNetwork + "\" title=\"" + typeOrderNetwork + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
        out.println("<span>" + paramRequest.getLocaleString("network") + "</span>");
        out.println("</a>");
        out.println("</th>");

        String nameClassStream = "ascen";
        String typeOrderStream = "Ordenar Ascendente";//request.getParameter("typeOrderTopic") == null ? "Ordenar Ascendente" :request.getParameter("typeOrderTopic") ;
        urlOderby.setParameter("orderBy", "streamUp");
        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("topicDown") || request.getParameter("orderBy").equals("topicUp")) {
                if (request.getParameter("nameClassStream") != null) {
                    if (request.getParameter("nameClassStream").equals("descen")) {
                        nameClassStream = "ascen";
                    } else {
                        nameClassStream = "descen";
                        urlOderby.setParameter("orderBy", "streamDown");
                        nameClassStream = "Ordenar Descendente";
                    }
                }
            }
        }
        out.println("<th>");
        urlOderby.setParameter("nameClassStream", nameClassStream);
        out.println("<a href=\"#\" class=\"ascen\" title=\"" + paramRequest.getLocaleString("stream") + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
        out.println("<span>" + paramRequest.getLocaleString("stream") + "</span>");
        out.println("</a>");
        out.println("</th>");




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

        String nameClassEmoticon = "ascen";
        String typeOrderEmoticon = "Ordenar Ascendente";
        urlOderby.setParameter("orderBy", "emoticonDown");
        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("emoticonUp") || request.getParameter("orderBy").equals("emoticonDown")) {
                if (request.getParameter("nameClassEmoticon") != null) {
                    if (request.getParameter("nameClassEmoticon").equals("descen")) {
                        nameClassEmoticon = "ascen";
                    } else {
                        nameClassEmoticon = "descen";
                        urlOderby.setParameter("orderBy", "emoticonUp");
                        typeOrderEmoticon = "Ordenar Descendente";
                    }
                }
            }
        }
        out.println("<th>");
        urlOderby.setParameter("nameClassEmoticon", nameClassEmoticon);
        out.println("<a href=\"#\" class=\"" + nameClassEmoticon + "\" title=\"" + typeOrderEmoticon + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
        out.println("<span>" + paramRequest.getLocaleString("emoticon") + "</span>");
        out.println("</a>");
        out.println("</th>");


        String nameClassReplies = "ascen";
        String typeOrderReplies = "Ordenar Ascendente";
        urlOderby.setParameter("orderBy", "repliesDown");
        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("repliesUp") || request.getParameter("orderBy").equals("repliesDown")) {
                if (request.getParameter("nameClassReplies") != null) {
                    if (request.getParameter("nameClassReplies").equals("descen")) {
                        nameClassReplies = "ascen";
                    } else {
                        nameClassReplies = "descen";
                        urlOderby.setParameter("orderBy", "repliesUp");
                        typeOrderReplies = "Ordenar Descendente";
                    }
                }
            }
        }
        out.println("<th>");
        urlOderby.setParameter("nameClassReplies", nameClassReplies);
        out.println("<a href=\"#\" class=\"" + nameClassReplies + "\" title=\"" + typeOrderReplies + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
        out.println("<span>" + paramRequest.getLocaleString("replies") + "</span>");
        out.println("<small>Descendente</small>");
        out.println("</a>");
        out.println("</th>");

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
        out.println("<small>Descendente</small>");
        out.println("</a>");
        out.println("</th>");


        String nameClassFollowers = "ascen";
        String typeOrderFollowers = "Ordenar Ascendente";
        urlOderby.setParameter("orderBy", "followersDown");
        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("followersUp") || request.getParameter("orderBy").equals("followersDown")) {
                if (request.getParameter("nameClassFollowers") != null) {
                    if (request.getParameter("nameClassFollowers").equals("descen")) {
                        nameClassFollowers = "ascen";
                    } else {
                        nameClassFollowers = "descen";
                        urlOderby.setParameter("orderBy", "followersUp");
                        typeOrderFollowers = "Ordenar Descendente";
                    }
                }
            }
        }
        out.println("<th>");
        urlOderby.setParameter("nameClassFollowers", nameClassFollowers);
        out.println("<a href=\"#\" class=\"" + nameClassFollowers + "\" title=\"" + typeOrderFollowers + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
        out.println("<span>" + paramRequest.getLocaleString("followers") + "</span>");
        out.println("<small>Descendente</small>");
        out.println("</a>");
        out.println("</th>");


        String nameClassFriends = "ascen";
        String typeOrderFriends = "Ordenar Ascendente";
        urlOderby.setParameter("orderBy", "friendsDown");
        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("friendsUp") || request.getParameter("orderBy").equals("friendsDown")) {
                if (request.getParameter("nameClassFriends") != null) {
                    if (request.getParameter("nameClassFriends").equals("descen")) {
                        nameClassFriends = "ascen";
                    } else {
                        nameClassFriends = "descen";
                        urlOderby.setParameter("orderBy", "friendsUp");
                        typeOrderFriends = "Ordenar Descendente";
                    }
                }
            }
        }
        out.println("<th>");
        urlOderby.setParameter("nameClassFriends", nameClassFriends);
        out.println("<a href=\"#\" class=\"" + nameClassFriends + "\" title=\"" + typeOrderFriends + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
        out.println("<span>" + paramRequest.getLocaleString("friends") + "</span>");
        out.println("<small>Descendente</small>");
        out.println("</a>");
        out.println("</th>");


        String nameClassKlout = "ascen";
        String typeOrderKlout = "Ordenar Ascendente";
        urlOderby.setParameter("orderBy", "kloutDown");
        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("kloutUp") || request.getParameter("orderBy").equals("kloutDown")) {
                if (request.getParameter("nameClassKlout") != null) {
                    if (request.getParameter("nameClassKlout").equals("descen")) {
                        nameClassKlout = "ascen";
                    } else {
                        nameClassKlout = "descen";
                        urlOderby.setParameter("orderBy", "kloutUp");
                        typeOrderKlout = "Ordenar Descendente";
                    }
                }
            }
        }
        out.println("<th>");
        urlOderby.setParameter("nameClassKlout", nameClassKlout);
        out.println("<a href=\"#\" class=\"" + nameClassKlout + "\" title=\"" + typeOrderKlout + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
        out.println("<span>" + paramRequest.getLocaleString("klout") + "</span>");
        out.println("<small>Descendente</small>");
        out.println("</a>");
        out.println("</th>");


        String nameClassPlace = "ascen";
        String typeOrderPlace = "Ordenar Ascendente";
        urlOderby.setParameter("orderBy", "placeDown");
        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("placeUp") || request.getParameter("orderBy").equals("placeDown")) {
                if (request.getParameter("nameClassPlace") != null) {
                    if (request.getParameter("nameClassPlace").equals("descen")) {
                        nameClassPlace = "ascen";
                    } else {
                        nameClassPlace = "descen";
                        urlOderby.setParameter("orderBy", "placeUp");
                        typeOrderPlace = "Ordenar Descendente";
                    }
                }
            }
        }
        out.println("<th>");
        urlOderby.setParameter("nameClassPlace", nameClassPlace);
        out.println("<a href=\"#\" class=\"" + nameClassPlace + "\" title=\"" + typeOrderPlace + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
        out.println("<span>" + paramRequest.getLocaleString("place") + "</span>");
        out.println("<small>Descendente</small>");
        out.println("</a>");
        out.println("</th>");

        String nameClassPrioritary = "ascen";
        String typeOrderPrioritary = "Ordenar Ascendente";
        urlOderby.setParameter("orderBy", "prioritaryDown");
        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("prioritaryUp") || request.getParameter("orderBy").equals("prioritaryDown")) {
                if (request.getParameter("nameClassPrioritary") != null) {
                    if (request.getParameter("nameClassPrioritary").equals("descen")) {
                        nameClassPrioritary = "ascen";
                    } else {
                        nameClassPrioritary = "descen";
                        urlOderby.setParameter("orderBy", "prioritaryUp");
                        typeOrderPrioritary = "Ordenar Descendente";
                    }
                }
            }
        }
        out.println("<th>");
        urlOderby.setParameter("nameClassPrioritary", nameClassPrioritary);
        out.println("<a href=\"#\" class=\" " + nameClassPrioritary + "\" title=\"" + typeOrderPrioritary + "\" onclick=\"submitUrl('" + urlOderby + "',this); return false;\">");
        out.println("<span>" + paramRequest.getLocaleString("prioritary") + "</span>");
        out.println("<small>Descendente</small>");
        out.println("</a>");

        out.println("</th>");
        out.println("</tr>");

        out.println("</thead>");
        out.println("<tbody>");


        //setso = filtros(swbSocialUser, wsite, itposts, searchWord, request, setso, socialTopic, paramRequest);


        int nPage;
        try {
            nPage = Integer.parseInt(request.getParameter("page"));
        } catch (Exception ignored) {
            nPage = 1;
        }

        HashMap hmapResult = filtros(swbSocialUser, wsite, searchWord, request, socialTopic, nPage);

        long nRec = ((Long) hmapResult.get("countResult")).longValue();
        Set<PostIn> setso = ((Set) hmapResult.get("itResult"));
        
         //Manejo de permisos
        //Manejo de permisos
        User user=paramRequest.getUser();
        boolean userCanRemoveMsg=false;
        boolean userCanRetopicMsg=false;
        //boolean userCanRevalueMsg=false;
        boolean userCanRespondMsg=false;
        SocialUserExtAttributes socialUserExtAttr=SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
        if(socialUserExtAttr!=null)
        {
            userCanRemoveMsg=socialUserExtAttr.isUserCanRemoveMsg();
            userCanRetopicMsg=socialUserExtAttr.isUserCanReTopicMsg();
            //userCanRevalueMsg=socialUserExtAttr.isUserCanReValueMsg();
            userCanRespondMsg=socialUserExtAttr.isUserCanRespondMsg();
        }

        Iterator<PostIn> itposts = setso.iterator();
        while (itposts.hasNext()) {
            PostIn postIn = itposts.next();

            System.out.println("postIn en InBox:" + postIn);


            out.println("<tr>");

            //Show Actions
            out.println("<td class=\"accion\">");

            //Remove
            SWBResourceURL urlr = paramRequest.getActionUrl();
            urlr.setParameter("suri", id);
            urlr.setParameter("postUri", postIn.getURI());
            urlr.setParameter("page", "" + nPage);
            urlr.setAction(SWBResourceURL.Action_REMOVE);

            String text = SWBUtils.TEXT.scape4Script(postIn.getMsg_Text());

            text = SWBSocialUtil.Util.replaceSpecialCharacters(text, false);

            if(userCanRemoveMsg)
            {
                out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("remove") + "\" class=\"eliminar\" onclick=\"if(confirm('" + paramRequest.getLocaleString("confirm_remove") + " "
                        + text + "?'))" + "{ submitUrl('" + urlr + "',this); } else { return false;}\"></a>");
            }


            //Preview
            /*
             SWBResourceURL urlpre = paramRequest.getRenderUrl();
             urlpre.setParameter("suri", id);
             urlpre.setParameter("page", "" + p);
             urlpre.setParameter("sval", postIn.getURI());
             urlpre.setParameter("preview", "true");
             * */
            //urlpre.setParameter("orderBy", (request.getParameter("orderBy")!=null && request.getParameter("orderBy").trim().length() > 0 ? request.getParameter("orderBy") : ""));
            //out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("previewdocument") + "\" onclick=\"submitUrl('" + urlpre + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/preview.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("previewdocument") + "\"></a>");
            SWBResourceURL urlPrev = paramRequest.getRenderUrl().setMode(Mode_PREVIEW).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());
            out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("previewdocument") + "\" class=\"ver\" onclick=\"showDialog('" + urlPrev + "','" + paramRequest.getLocaleString("previewdocument")
                    + "'); return false;\"></a>");


            //ReClasifyByTpic
            if(userCanRetopicMsg)
            {
                SWBResourceURL urlreClasifybyTopic = paramRequest.getRenderUrl().setMode(Mode_RECLASSBYTOPIC).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());
                out.println("<a href=\"#\" class=\"retema\" title=\"" + paramRequest.getLocaleString("reclasifyByTopic") + "\" onclick=\"showDialog('" + urlreClasifybyTopic + "','"
                        + paramRequest.getLocaleString("reclasifyByTopic") + "'); return false;\"></a>");
            }


            if(userCanRespondMsg)
            {
                //Respond
                SWBResourceURL urlresponse = paramRequest.getRenderUrl().setMode(Mode_RESPONSE).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());
                out.println("<a href=\"#\" class=\"answ\" title=\"" + paramRequest.getLocaleString("respond") + "\"  onclick=\"showDialog('" + urlresponse + "','" + paramRequest.getLocaleString("respond")
                        + "'); return false;\"></a>");
            }

            //Respuestas que posee un PostIn
            if (postIn.listpostOutResponseInvs().hasNext()) {
                SWBResourceURL urlresponses = paramRequest.getRenderUrl().setMode(Mode_RESPONSES).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());
                out.println("<a href=\"#\" class=\"answver\" title=\"" + paramRequest.getLocaleString("answers") + "\"  onclick=\"showDialog('" + urlresponses + "','" + paramRequest.getLocaleString("answers")
                        + "'); return false;\"></a>");
            }

            out.println("</td>");

            //Show Message
            out.println("<td class=\"mensaje\">");
            if (postIn.getMsg_Text() != null) {
                if (postIn.getMsg_Text().length() > 200) {
                    String msg2Show = postIn.getMsg_Text().substring(0, 200);
                    msg2Show = SWBSocialUtil.Util.createHttpLink(msg2Show);
                    out.println(msg2Show);
                } else {
                    out.println(postIn.getMsg_Text());
                }
            } else if (postIn.getDescription() != null) {
                if (postIn.getDescription().length() > 200) {
                    out.println(postIn.getDescription().substring(0, 200));
                } else {
                    out.println(postIn.getDescription());
                }
            } else if (postIn.getTags() != null) {
                if (postIn.getTags().length() > 200) {
                    out.println(postIn.getTags().substring(0, 200));
                } else {
                    out.println(postIn.getTags());
                }
            } else {
                out.println("---");
            }
            out.println("</td>");


            //Show PostType
            out.println("<td>");
            out.println(postIn instanceof MessageIn ? "<img src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/tipo-txt.jpg\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("message") + "  \">" : postIn instanceof PhotoIn ? "<img src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/tipo-img.jpg\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("photo") + "  \">" : postIn instanceof VideoIn ? "<img src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/tipo-vid.jpg\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("video") + "  \">" : "---");
            out.println("</td>");

            //SocialNetwork
            out.println("<td>");
            out.println(postIn.getPostInSocialNetwork().getDisplayTitle(lang));
            out.println("</td>");

            //Stream
            out.println("<td>");
            out.println(postIn.getPostInStream() != null ? postIn.getPostInStream().getDisplayTitle(lang) : "---");
            out.println("</td>");

            //created
            out.println("<td>");
            out.println(SWBUtils.TEXT.getTimeAgo(postIn.getCreated(), lang));
            out.println("</td>");

            //Sentiment
            //Sentiment
            out.println("<td align=\"center\">");
            if (postIn.getPostSentimentalType() == 0) {
                out.println("---");
            } else if (postIn.getPostSentimentalType() == 1) {
                out.println("<img src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/pos.png" + "\">");
            } else if (postIn.getPostSentimentalType() == 2) {
                out.println("<img src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/neg.png" + "\">");
            }
            out.println("</td>");

            //Intensity
            out.println("<td>");
            out.println(postIn.getPostIntesityType() == 0 ? "<img src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/ibaja.png\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("low") + "  \">" : postIn.getPostIntesityType() == 1 ? "<img src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/imedia.png\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("medium") + "  \">" : postIn.getPostIntesityType() == 2 ? "<img src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/ialta.png\" border=\"0\" alt=\" " + paramRequest.getLocaleString("high") + "  \">" : "---");
            out.println("</td>");

            //Emoticon
            out.println("<td>");
            if (postIn.getPostSentimentalEmoticonType() == 1) {
                out.println("<img src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/emopos.png" + "\"/>");
            } else if (postIn.getPostSentimentalEmoticonType() == 2) {
                out.println("<img src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/emoneg.png" + "\"/>");
            } else if (postIn.getPostSentimentalEmoticonType() == 0) {
                out.println("Neutro");
            }
            out.println("</td>");


            //Replicas
            out.println("<td align=\"center\">");
            out.println(postIn.getPostShared());
            out.println("</td>");


            //User
            out.println("<td>");
            SWBResourceURL urlshowUsrHistory = paramRequest.getRenderUrl().setMode(Mode_ShowUsrHistory).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("suri", id);
            out.println(postIn.getPostInSocialNetworkUser() != null ? "<a href=\"#\" onclick=\"showDialog('" + urlshowUsrHistory.setParameter("swbSocialUser", postIn.getPostInSocialNetworkUser().getURI()) + "','" + paramRequest.getLocaleString("userHistory") + "'); return false;\">" + postIn.getPostInSocialNetworkUser().getSnu_name() + "</a>" : paramRequest.getLocaleString("withoutUser"));
            out.println("</td>");

            //Followers
            out.println("<td align=\"center\">");
            out.println(postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFollowers():"---");
            out.println("</td>");

            //Friends
            out.println("<td align=\"center\">");
            out.println(postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFriends():"---");
            out.println("</td>");

            //Klout
            out.println("<td align=\"center\">");
            out.println(postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getSnu_klout():"---");
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
        out.println("</tbody>");
        out.println("</table>");
        out.println("</fieldset>");


        if (nRec > 0) {
            System.out.println("nRec SocialTopicInBox/Jorge:"+nRec+",RECPERPAGE:"+RECPERPAGE);
            int totalPages=1;
            if(nRec>RECPERPAGE)
            {
                totalPages=Double.valueOf(nRec/20).intValue();
                System.out.println("totalPages SocialTopicInBox/totalPages-1:"+totalPages);
                if((nRec % RECPERPAGE)>0){
                    totalPages=Double.valueOf(nRec/20).intValue()+1;
                    System.out.println("totalPages SocialTopicInBox/totalPages-2:"+totalPages);
                }
            }
            System.out.println("totalPages SocialTopicInBox/Jorge-3:"+totalPages);
            out.println("<div id=\"pagSumary\">"+paramRequest.getLocaleString("page")+":"+nPage+" "+paramRequest.getLocaleString("of") +" "+totalPages+"</div>");
            
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
            
            out.println(SWBSocialUtil.Util.getContentByPage(totalPages, nPage, PAGES2VIEW, paramRequest.getLocaleString("pageBefore"), paramRequest.getLocaleString("pageNext"), pageURL));
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

    private void doReClassifyByTopic(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
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

    private void doResponse(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
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

    private void doShowResponses(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
        final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/showpostInResponses.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        if (dis != null) {
            try {
                System.out.println("doShowResponses/postUri:" + request.getParameter("postUri"));
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                System.out.println("doShowResponses/semObject:" + semObject);
                request.setAttribute("postUri", semObject);
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    private void doShowPostOut(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
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

    private void doShowUserHistory(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
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

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        final Resource base = getResourceBase();
        String action = response.getAction();
        //System.out.println("Entra a InBox_processAction-1:"+action);
        if (action.equals("changeSocialTopic")) {
            if (request.getParameter("postUri") != null && request.getParameter("newSocialTopic") != null) {
                SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("postUri"));
                Post post = (Post) semObj.createGenericInstance();
                SocialTopic stOld = post.getSocialTopic();
                if (request.getParameter("newSocialTopic").equals("none")) {
                    post.setSocialTopic(null);
                } else {
                    SemanticObject semObjSocialTopic = SemanticObject.getSemanticObject(request.getParameter("newSocialTopic"));
                    if (semObjSocialTopic != null) {
                        SocialTopic socialTopic = (SocialTopic) semObjSocialTopic.createGenericInstance();
                        post.setSocialTopic(socialTopic);
                    }
                }
                response.setMode(SWBActionResponse.Mode_EDIT);
                response.setRenderParameter("dialog", "close");
                response.setRenderParameter("statusMsg", response.getLocaleString("msgTopicChanged"));
                response.setRenderParameter("reloadTap", "1");
                response.setRenderParameter("suri", stOld.getURI());
            }
        } else if (action.equals("postMessage") || action.equals("uploadPhoto") || action.equals("uploadVideo")) {
            //System.out.println("Entra a InBox_processAction-2:"+request.getParameter("objUri"));
            if (request.getParameter("objUri") != null) {
                //System.out.println("Entra a InBox_processAction-3");
                PostIn postIn = (PostIn) SemanticObject.getSemanticObject(request.getParameter("objUri")).createGenericInstance();
                SocialTopic stOld = postIn.getSocialTopic();
                ///
                WebSite wsite = WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));
                String socialUri = "";
                int j = 0;
                Enumeration<String> enumParams = request.getParameterNames();
                while (enumParams.hasMoreElements()) {
                    String paramName = enumParams.nextElement();
                    if (paramName.startsWith("http://")) {//get param name starting with http:// -> URIs
                        if (socialUri.trim().length() > 0) {
                            socialUri += "|";
                        }
                        socialUri += paramName;
                        j++;
                    }
                }
                
                ArrayList aSocialNets = new ArrayList();//Social nets where the post will be published
                String[] socialUris = socialUri.split("\\|");  //Dividir valores
                if( j > 0 && wsite != null){
                    for (int i = 0; i < socialUris.length; i++) {
                        String tmp_socialUri = socialUris[i];
                        SemanticObject semObject = SemanticObject.createSemanticObject(tmp_socialUri, wsite.getSemanticModel());
                        SocialNetwork socialNet = (SocialNetwork) semObject.createGenericInstance();
                        //Se agrega la red social de salida al post
                        aSocialNets.add(socialNet);
                    }
                }
                
                /*SocialNetwork socialNet = (SocialNetwork) SemanticObject.getSemanticObject(request.getParameter("socialNetUri")).createGenericInstance();
                ArrayList aSocialNets = new ArrayList();
                aSocialNets.add(socialNet);

                WebSite wsite = WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));*/

                //En este momento en el siguiente código saco uno de los SocialPFlowRef que tiene el SocialTopic del PostIn que se esta contestando,
                //Obviamente debo de quitar este código y el SocialPFlowRef debe llegar como parametro, que es de acuerdo al SocialPFlow que el usuario
                //desee enviar el PostOut que realizó.
                /**
                 * SocialPFlow socialPFlow=null; Iterator<SocialPFlowRef>
                 * itflowRefs=socialTopic.listPFlowRefs();
                 * while(itflowRefs.hasNext()) { SocialPFlowRef
                 * socialPflowRef=itflowRefs.next();
                 * socialPFlow=socialPflowRef.getPflow(); }*
                 */
                String toPost = request.getParameter("toPost");
                String socialFlow = request.getParameter("socialFlow");
                SocialPFlow socialPFlow = null;
                if (socialFlow != null && socialFlow.trim().length() > 0) {
                    socialPFlow = (SocialPFlow) SemanticObject.createSemanticObject(socialFlow).createGenericInstance();
                    //Revisa si el flujo de publicación soporte el tipo de postOut, de lo contrario, asinga null a spflow, para que no 
                    //asigne flujo al mensaje de salida., Esto también esta validado desde el jsp typeOfContent
                    if ((toPost.equals("msg") && !SocialLoader.getPFlowManager().isManagedByPflow(socialPFlow, Message.sclass))
                            || (toPost.equals("photo") && !SocialLoader.getPFlowManager().isManagedByPflow(socialPFlow, Photo.sclass))
                            || (toPost.equals("video") && !SocialLoader.getPFlowManager().isManagedByPflow(socialPFlow, Video.sclass))) {
                        socialPFlow = null;
                    }
                }

                //System.out.println("Entra a InBox_processAction-4");
                SWBSocialUtil.PostOutUtil.sendNewPost(postIn, postIn.getSocialTopic(), socialPFlow, aSocialNets, wsite, toPost, request, response);

                //System.out.println("Entra a InBox_processAction-5");
                response.setMode(SWBActionResponse.Mode_EDIT);
                response.setRenderParameter("dialog", "close");
                response.setRenderParameter("statusMsg", response.getLocaleString("msgResponseCreated"));
                response.setRenderParameter("suri", stOld.getURI());
            }
        } else if (SWBResourceURL.Action_EDIT.equals(action)) {
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
        } else if (action.equals(SWBActionResponse.Action_REMOVE)) {
            if (request.getParameter("suri") != null && request.getParameter("postUri") != null) {
                String sval = request.getParameter("postUri");
                SemanticObject so = SemanticObject.createSemanticObject(sval);
                WebSite wsite = WebSite.ClassMgr.getWebSite(so.getModel().getName());
                PostIn postIn = (PostIn) so.getGenericInstance();

                if (PostOut.ClassMgr.listPostOutByPostInSource(postIn, wsite).hasNext()) {
                    response.setRenderParameter("leyendReconfirm", response.getLocaleString("postOutExist"));
                    response.setRenderParameter("suri", request.getParameter("suri"));
                    response.setRenderParameter("postUri", postIn.getURI());
                } else {
                    so.remove();
                    //response.setRenderParameter("dialog", "close");
                    response.setRenderParameter("suri", request.getParameter("suri"));
                    response.setRenderParameter("statusMsg", response.getLocaleString("postDeleted"));
                }
                response.setMode(SWBActionResponse.Mode_EDIT);
            }
        } else if ("removeConfirm".equals(action)) {
            String sval = request.getParameter("sval");
            SemanticObject so = SemanticObject.createSemanticObject(sval);
            so.remove();
            response.setMode(SWBActionResponse.Mode_EDIT);
            response.setRenderParameter("reloadTap", request.getParameter("suri"));
            response.setRenderParameter("suri", request.getParameter("suri"));
            response.setRenderParameter("statusMsg", response.getLocaleString("postDeleted"));
        }

    }

    public void doShowTags(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jspResponse = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialNetworks/tagCloud.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error("Error in doShowTags() for requestDispatcher", e);
        }
    }

    /*
     * Method which calls a jsp to generate a report based on the result of registers in this class
     */
    private void doGenerateReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {

        String pages = request.getParameter("pages");
        int page = Integer.parseInt(pages);
        String searchWord = request.getParameter("search");
        String swbSocialUser = request.getParameter("swbSocialUser");
        String id = request.getParameter("suri");

        SocialTopic socialTopic = (SocialTopic) SemanticObject.getSemanticObject(id).getGenericInstance();
        WebSite webSite = WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());


        HashMap hmapResult = filtros(swbSocialUser, webSite, searchWord, request, socialTopic, page);

        //long nRec=((Long)hmapResult.get("countResult")).longValue();
        Set<PostIn> setso = ((Set) hmapResult.get("itResult"));

        try {

            createExcel(setso, paramRequest, page, response, socialTopic);


        } catch (Exception e) {
            log.error(e);
        }
    }

    /*
     * Method which controls the filters allowed in this class
     */
    private HashMap filtros(String swbSocialUser, WebSite wsite, String searchWord, HttpServletRequest request, SocialTopic socialTopic, int nPage) {
        Set<PostIn> setso = null;
        ArrayList<PostIn> aListFilter = new ArrayList();
        HashMap hampResult = new HashMap();
        Iterator<PostIn> itposts = null;
        if (swbSocialUser != null) {
            SocialNetworkUser socialNetUser = SocialNetworkUser.ClassMgr.getSocialNetworkUser(swbSocialUser, wsite);
            //itposts = socialNetUser.listPostInInvs();
            long StreamPostIns = wsite.getSemanticModel().countStatements(null, PostIn.social_postInSocialNetworkUser.getRDFProperty(), socialNetUser.getSemanticObject().getRDFResource(), PostIn.sclass.getClassGroupId());
            hampResult.put("countResult", Long.valueOf(StreamPostIns));
            itposts = new GenericIterator(new SemanticIterator(wsite.getSemanticModel().listStatements(null, PostIn.social_postInSocialNetworkUser.getRDFProperty(), socialNetUser.getSemanticObject().getRDFResource(), PostIn.sclass.getClassGroupId(), Integer.valueOf((nPage * RECPERPAGE)).longValue(), Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), "timems desc"), true));
        } else {
            long StreamPostIns=0L;
            if (searchWord != null && searchWord.trim().length()>0) {
                System.out.println("ES POR BUSQUEDA:"+searchWord);
                itposts = new GenericIterator(new SemanticIterator(wsite.getSemanticModel().listStatements(null, PostIn.social_socialTopic.getRDFProperty(), socialTopic.getSemanticObject().getRDFResource(), PostIn.sclass.getClassGroupId(), StreamPostIns, 0L, "timems desc"), true));
                while (itposts.hasNext()) {
                    PostIn postIn = itposts.next();
                    if (postIn.getTags() != null && postIn.getTags().toLowerCase().indexOf(searchWord.toLowerCase()) > -1) {
                        StreamPostIns++;
                        aListFilter.add(postIn);
                    } else if (postIn.getMsg_Text() != null && postIn.getMsg_Text().toLowerCase().indexOf(searchWord.toLowerCase()) > -1) {
                        StreamPostIns++;
                        aListFilter.add(postIn);
                    }
                }
            }else{
                StreamPostIns = wsite.getSemanticModel().countStatements(null, PostIn.social_socialTopic.getRDFProperty(), socialTopic.getSemanticObject().getRDFResource(), PostIn.sclass.getClassGroupId());
                System.out.println("NO ES POR BUSQUEDA,nPage:"+nPage);
                if (nPage != 0) {
                    itposts = new GenericIterator(new SemanticIterator(wsite.getSemanticModel().listStatements(null, PostIn.social_socialTopic.getRDFProperty(), socialTopic.getSemanticObject().getRDFResource(), PostIn.sclass.getClassGroupId(), Integer.valueOf((RECPERPAGE)).longValue(), Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), "timems desc"), true));
                } else { 
                    System.out.println("Toma Todo...Jorge");
                    itposts = new GenericIterator(new SemanticIterator(wsite.getSemanticModel().listStatements(null, PostIn.social_socialTopic.getRDFProperty(), socialTopic.getSemanticObject().getRDFResource(), PostIn.sclass.getClassGroupId(), StreamPostIns, 0L, "timems desc"), true));
                }
            }
            hampResult.put("countResult", Long.valueOf(StreamPostIns));
        }
        //Termina Filtros

        if (aListFilter.size() > 0) {
            itposts = aListFilter.iterator();
        }


        //Ordenamientos
        System.out.println("orderBy k Llega SocialTopicInBoxxx:" + request.getParameter("orderBy"));

        if (request.getParameter("orderBy") != null) {
            if (request.getParameter("orderBy").equals("PostTypeUp")) {
                setso = SWBSocialComparator.sortByPostType(itposts, true);
            } else if (request.getParameter("orderBy").equals("PostTypeDown")) {
                setso = SWBSocialComparator.sortByPostType(itposts, false);
            } else if (request.getParameter("orderBy").equals("networkUp")) {
                setso = SWBSocialComparator.sortByNetwork(itposts, true);
            } else if (request.getParameter("orderBy").equals("networkDown")) {
                setso = SWBSocialComparator.sortByNetwork(itposts, false);
            } else if (request.getParameter("orderBy").equals("streamUp")) {
                setso = SWBSocialComparator.sortByStream(itposts, true);
            } else if (request.getParameter("orderBy").equals("streamDown")) {
                setso = SWBSocialComparator.sortByStream(itposts, false);
            } else if (request.getParameter("orderBy").equals("cretedUp")) {
                setso = SWBComparator.sortByCreatedSet(itposts, true);
            } else if (request.getParameter("orderBy").equals("cretedDown")) {
                setso = SWBComparator.sortByCreatedSet(itposts, false);
            } else if (request.getParameter("orderBy").equals("sentimentUp")) {
                setso = SWBSocialComparator.sortBySentiment(itposts, false);
            } else if (request.getParameter("orderBy").equals("sentimentDown")) {
                setso = SWBSocialComparator.sortBySentiment(itposts, true);
            } else if (request.getParameter("orderBy").equals("intensityUp")) {
                setso = SWBSocialComparator.sortByIntensity(itposts, true);
            } else if (request.getParameter("orderBy").equals("intensityDown")) {
                setso = SWBSocialComparator.sortByIntensity(itposts, false);
            } else if (request.getParameter("orderBy").equals("emoticonUp")) {
                setso = SWBSocialComparator.sortByEmoticon(itposts, false);
            } else if (request.getParameter("orderBy").equals("emoticonDown")) {
                setso = SWBSocialComparator.sortByEmoticon(itposts, true);
            } else if (request.getParameter("orderBy").equals("userUp")) {
                setso = SWBSocialComparator.sortByUser(itposts, true);
            } else if (request.getParameter("orderBy").equals("userDown")) {
                setso = SWBSocialComparator.sortByUser(itposts, false);
            } else if (request.getParameter("orderBy").equals("followersUp")) {
                setso = SWBSocialComparator.sortByFollowers(itposts, true);
            } else if (request.getParameter("orderBy").equals("followersDown")) {
                setso = SWBSocialComparator.sortByFollowers(itposts, false);
            } else if (request.getParameter("orderBy").equals("repliesUp")) {
                setso = SWBSocialComparator.sortByReplies(itposts, true);
            } else if (request.getParameter("orderBy").equals("repliesDown")) {
                setso = SWBSocialComparator.sortByReplies(itposts, false);
            } else if (request.getParameter("orderBy").equals("friendsUp")) {
                setso = SWBSocialComparator.sortByFriends(itposts, true);
            } else if (request.getParameter("orderBy").equals("friendsDown")) {
                setso = SWBSocialComparator.sortByFriends(itposts, false);
            } else if (request.getParameter("orderBy").equals("kloutUp")) {
                setso = SWBSocialComparator.sortByKlout(itposts, true);
            } else if (request.getParameter("orderBy").equals("kloutDown")) {
                setso = SWBSocialComparator.sortByKlout(itposts, false);
            } else if (request.getParameter("orderBy").equals("placeUp")) {
                setso = SWBSocialComparator.sortByPlace(itposts, true);
            } else if (request.getParameter("orderBy").equals("placeDown")) {
                setso = SWBSocialComparator.sortByPlace(itposts, false);
            } else if (request.getParameter("orderBy").equals("prioritaryUp")) {
                setso = SWBSocialComparator.sortByPrioritary(itposts, true);
            } else if (request.getParameter("orderBy").equals("prioritaryDown")) {
                setso = SWBSocialComparator.sortByPrioritary(itposts, false);
            }
        } else {
            setso = SWBComparator.sortByCreatedSet(itposts, false);
        }
        hampResult.put("itResult", setso);
        return hampResult;
    }

    public void createExcel(Set<PostIn> setso, SWBParamRequest paramRequest, int page, HttpServletResponse response, SocialTopic socialTopic) { // era stream
        try {
            // Defino el Libro de Excel
            Iterator v = setso.iterator();
            String title = socialTopic.getTitle();


            HSSFWorkbook wb = new HSSFWorkbook();

            // Creo la Hoja en Excel
            Sheet sheet = wb.createSheet("Mensajes " + title);


            sheet.setDisplayGridlines(false);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));

            // creo una nueva fila
            Row trow = sheet.createRow((short) 0);
            createTituloCell(wb, trow, 0, CellStyle.ALIGN_CENTER,
                    CellStyle.VERTICAL_CENTER, "Mensajes " + title);

            // Creo la cabecera de mi listado en Excel
            Row row = sheet.createRow((short) 2);

            // Creo las celdas de mi fila, se puede poner un diseño a la celda
            createHead(wb, row, 0, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Mensaje");
            createHead(wb, row, 1, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "Tipo");
            createHead(wb, row, 2, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Red");
            createHead(wb, row, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Tema");
            createHead(wb, row, 4, CellStyle.ALIGN_CENTER,  CellStyle.VERTICAL_CENTER, "Creación");
            createHead(wb, row, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Sentimiento");
            createHead(wb, row, 6, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Intensidad");
            createHead(wb, row, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Emot");
            createHead(wb, row, 8, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "RT/Likes");
            createHead(wb, row, 9, CellStyle.ALIGN_CENTER,  CellStyle.VERTICAL_CENTER, "Usuario");
            createHead(wb, row, 10, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "Seguidores");
            createHead(wb, row, 11, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "Amigos");
            //createHead(wb, row, 12, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "Klout");
            createHead(wb, row, 12, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "Lugar");
            createHead(wb, row, 13, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "Prioritario");


            String lang = paramRequest.getUser().getLanguage();
        

            //Número de filas
            int i = 3;
            CellStyle cellStyle = wb.createCellStyle();

            while (v.hasNext()) {
                PostIn postIn = (PostIn) v.next();
                             
                    Row troww = sheet.createRow((short) i);
                

                    if (postIn.getMsg_Text() != null) {
                        if (postIn.getMsg_Text().length() > 2000) {
                            createCell(cellStyle,wb, troww, 0, CellStyle.ALIGN_LEFT,
                                    CellStyle.VERTICAL_CENTER, postIn.getMsg_Text().substring(0, 2000));

                        } else {
                            createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT,
                                    CellStyle.VERTICAL_CENTER, postIn.getMsg_Text());
                        }
                    } else if (postIn.getDescription() != null) {
                        if (postIn.getDescription().length() > 200) {
                            createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT,
                                    CellStyle.VERTICAL_CENTER, postIn.getDescription().substring(0, 200));

                        } else {
                            createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT,
                                    CellStyle.VERTICAL_CENTER, postIn.getDescription());
                        }
                    } else if (postIn.getTags() != null) {
                        if (postIn.getTags().length() > 200) {
                            createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT,
                                    CellStyle.VERTICAL_CENTER, postIn.getTags().substring(0, 200));

                        } else {
                            createCell(cellStyle,wb, troww, 0, CellStyle.ALIGN_LEFT,
                                    CellStyle.VERTICAL_CENTER, postIn.getTags());
                        }
                    } else {
                        createCell(cellStyle,wb, troww, 0, CellStyle.ALIGN_LEFT,
                                CellStyle.VERTICAL_CENTER, "---");

                    }
                    createCell(cellStyle,wb, troww, 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn instanceof MessageIn ? paramRequest.getLocaleString("message") : postIn instanceof PhotoIn ? paramRequest.getLocaleString("photo") : postIn instanceof VideoIn ? paramRequest.getLocaleString("video") : "---");
                    createCell(cellStyle,wb, troww, 2, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostInSocialNetwork().getDisplayTitle(lang));

                    if (postIn.getSocialTopic() != null) {
                        createCell(cellStyle,wb, troww, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getSocialTopic().getDisplayTitle(lang));
                    } else {
                        createCell(cellStyle,wb, troww, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "---");
                    }
                    createCell(cellStyle,wb, troww, 4, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, SWBUtils.TEXT.getTimeAgo(postIn.getCreated(), lang));

                    if (postIn.getPostSentimentalType() == 0) {
                        createCell(cellStyle,wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "----");
                    } else if (postIn.getPostSentimentalType() == 1) {
                        createCell(cellStyle,wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Positivo");
                    } else if (postIn.getPostSentimentalType() == 2) {
                        createCell(cellStyle,wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Negativo");
                    }
                    createCell(cellStyle,wb, troww, 6, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostIntesityType() == 0 ? paramRequest.getLocaleString("low") : postIn.getPostIntesityType() == 1 ? paramRequest.getLocaleString("medium") : postIn.getPostIntesityType() == 2 ? paramRequest.getLocaleString("high") : "---");

                    if (postIn.getPostSentimentalEmoticonType() == 1) {
                        createCell(cellStyle,wb, troww, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Positivo");

                    } else if (postIn.getPostSentimentalEmoticonType() == 2) {
                        createCell(cellStyle,wb, troww, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Negativo");
                    } else if (postIn.getPostSentimentalEmoticonType() == 0) {

                        createCell(cellStyle,wb, troww, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "---");
                    }
                    int postS = postIn.getPostShared();
                    String postShared = Integer.toString(postS);
                    createCell(cellStyle,wb, troww, 8, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postShared);
                    createCell(cellStyle,wb, troww, 9, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getSnu_name() : paramRequest.getLocaleString("withoutUser"));
                    Serializable foll = postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFollowers() : paramRequest.getLocaleString("withoutUser");
                    createCell(cellStyle,wb, troww, 10, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, foll.toString());
                    Serializable amigos = postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFriends() : paramRequest.getLocaleString("withoutUser");
                    createCell(cellStyle,wb, troww, 11, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, amigos.toString());
                    Serializable klout = postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getSnu_klout() : paramRequest.getLocaleString("withoutUser");
                    //createCell(cellStyle,wb, troww, 12, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, klout.toString());
                    createCell(cellStyle,wb, troww, 12, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostPlace() == null ? "---" : postIn.getPostPlace());
                    createCell(cellStyle,wb, troww, 13, CellStyle.ALIGN_CENTER,
                            CellStyle.VERTICAL_CENTER, postIn.isIsPrioritary() ? "SI" : "NO");

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
                ssheet.autoSizeColumn(11);
                ssheet.autoSizeColumn(12);
                ssheet.autoSizeColumn(13);
                //ssheet.autoSizeColumn(14);


                OutputStream ou = response.getOutputStream();
                response.setHeader("Cache-Control", "no-cache");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Content-Disposition", "attachment; filename=\"Mensajes.xls\";");
                response.setContentType("application/octet-stream");
                wb.write(ou);
                ou.close();



            }  catch (Exception e) {
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
}
