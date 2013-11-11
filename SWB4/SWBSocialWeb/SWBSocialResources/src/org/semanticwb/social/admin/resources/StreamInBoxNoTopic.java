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
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
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
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
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
import org.semanticwb.social.SWBSocial;
import org.semanticwb.social.SentimentalLearningPhrase;
import org.semanticwb.social.SocialNetworkUser;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.SocialUserExtAttributes;
import org.semanticwb.social.Stream;
import org.semanticwb.social.VideoIn;
import org.semanticwb.social.admin.resources.util.SWBSocialResUtil;
import org.semanticwb.social.util.SWBSocialUtil;

/**
 *
 * @author jorge.jimenez
 */
public class StreamInBoxNoTopic extends GenericResource {

    /**
     * The log.
     */
    private Logger log = SWBUtils.getLogger(StreamInBoxNoTopic.class);
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
    //String Mode_PreView="preview";
    String Mode_showTags = "showTags";
    private static final int RECPERPAGE=20; //Number of records by Page, could be dynamic later
    private static final int PAGES2VIEW = 15; //Number of pages 2 display in pagination.

    /**
     * Creates a new instance of SWBAWebPageContents.
     */
    public StreamInBoxNoTopic() {
    }
    public static final String Mode_REVAL = "rv";
    public static final String Mode_RECLASSBYTOPIC = "reclassByTopic";
    public static final String Mode_ShowUsrHistory = "showUsrHistory";
    public static final String Mode_PREVIEW = "preview";

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if (Mode_PREVIEW.equals(mode)) {
            doPreview(request, response, paramRequest);
        } else if (Mode_REVAL.equals(mode)) {
            doRevalue(request, response, paramRequest);
        } else if (Mode_ShowUsrHistory.equals(mode)) {
            doShowUserHistory(request, response, paramRequest);
        } else if (Mode_RECLASSBYTOPIC.equals(mode)) {
            doReClassifyByTopic(request, response, paramRequest);
        } else if (Mode_showTags.equals(mode)) {
            doShowTags(request, response, paramRequest);
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

        Stream stream = (Stream) SemanticObject.getSemanticObject(id).getGenericInstance();
        WebSite wsite = WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());

        PrintWriter out = response.getWriter();

        if (request.getParameter("dialog") != null && request.getParameter("dialog").equals("close")) {
            out.println("<script type=\"javascript\">");
            out.println(" hideDialog(); ");
            if (request.getParameter("statusMsg") != null) {
                out.println("   showStatus('" + request.getParameter("statusMsg") + "');");
            }
            if (request.getParameter("reloadTap") != null) {
                //out.println("var tabId =  '" +id  + "/bh_StreamInBoxNoTopic';");
                //out.println("alert(dijit.byId(tabId));");
                //out.println(" reloadTab(tabId);");
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

        //System.out.println("search word que llega sin:"+request.getParameter("search"));
        String searchWord = request.getParameter("search");
        String swbSocialUser = request.getParameter("swbSocialUser");

        String page = request.getParameter("page");
        if (page == null && request.getParameter("noSaveSess") == null) //Cuando venga page!=null no se mete nada a session, ni tampoco se manda return.
        {
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

        SWBResourceURL urls = paramRequest.getRenderUrl();
        urls.setParameter("act", "");
        urls.setParameter("suri", id);

        out.println("<div class=\"swbform\">");

        out.println("<fieldset class=\"barra\">");
        out.println("<div class=\"barra\">"); 
        /*
        out.println("<span  class=\"spanFormat\">");
        out.println("<form id=\"" + id + "/fsearchNoTopic\" name=\"" + id + "/fsearchNoTopic\" method=\"post\" action=\"" + urls + "\" onsubmit=\"submitForm('" + id + "/fsearchNoTopic');return false;\">");
        out.println("<div align=\"right\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
        out.println("<input type=\"hidden\" name=\"noSaveSess\" value=\"1\">");
        out.println("<label for=\"" + id + "_fsearchNoTopic\">" + paramRequest.getLocaleString("searchPost") + ": </label><input type=\"text\" name=\"search\" id=\"" + id + "_fsearchNoTopic\" value=\"" + searchWord + "\">");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("btnSearch") + "</button>"); //
        out.println("</div>");
        out.println("</form>");
        out.println("</span>");
        * */
        /*
        out.println("<span  class=\"spanFormat\">");
        out.println("<button dojoType='dijit.form.Button'  onclick=\"showDialog('" + tagUrl + "','" + paramRequest.getLocaleString("tagLabel") + "'); return false;\">" + paramRequest.getLocaleString("btnCloud") + "</button>");
        out.println("</span>");
        */
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
        */
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
        tagUrl.setParameter("noTopic", "true");
        tagUrl.setMode(Mode_showTags);
        out.println("<a href=\"#\" onclick=\"showDialog('" + tagUrl + "','" + paramRequest.getLocaleString("tagLabel") + "'); return false;\" class=\"btnCloud\">"+paramRequest.getLocaleString("btnCloud")+"</a>");
        //ENDS TAG CLOUD
        
        //out.println("<span  class=\"spanFormat\">");
        out.println("<form id=\"" + id + "/fsearchwp\" name=\"" + id + "/fsearchwp\" method=\"post\" action=\"" + urls.setMode(SWBResourceURL.Mode_EDIT) + "\" onsubmit=\"submitForm('" + id + "/fsearchwp');return false;\">");
        out.println("<div align=\"right\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
        out.println("<input type=\"hidden\" name=\"noSaveSess\" value=\"1\">");
        out.println("<input type=\"text\" name=\"search\" id=\"" + id + "_searchwp\" value=\"" + searchWord + "\" placeholder=\""+paramRequest.getLocaleString("searchPost")+"\">");
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


        out.println("<th  class=\"mensaje\">");
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


        int nPage;
        try {
            nPage = Integer.parseInt(request.getParameter("page"));
        } catch (Exception ignored) {
            nPage = 1;
        }
        
        HashMap hmapResult=filtros(swbSocialUser, wsite, searchWord, request, stream, nPage);
        
        long nRec=((Long)hmapResult.get("countResult")).longValue();
        Iterator<PostIn> itposts = (Iterator)hmapResult.get("itResult"); 

        //Filtros




        //Aquí hago una iteración para sacar los elementos que no tienen SocialTopic, esto para que al momento de la páginación
        //ya se tenga exactamente cuantos elementos son.

        //System.out.println("setso Jorge:"+setso+", size:"+setso.size());
        /*
        ArrayList<PostIn> setsoFinal = new ArrayList();;
        Iterator<PostIn> itTmp = itposts;
        while (itTmp.hasNext()) {
            PostIn postIn = itTmp.next();
            if (postIn.getSocialTopic() == null) {
                setsoFinal.add(postIn);
            }
        }*/


        //Manejo de permisos
        User user=paramRequest.getUser();
        boolean userCanRemoveMsg=false;
        boolean userCanRetopicMsg=false;
        boolean userCanRevalueMsg=false;
        //boolean userCanRespondMsg=false;
        SocialUserExtAttributes socialUserExtAttr=SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
        if(socialUserExtAttr!=null)
        {
            userCanRemoveMsg=socialUserExtAttr.isUserCanRemoveMsg();
            userCanRetopicMsg=socialUserExtAttr.isUserCanReTopicMsg();
            userCanRevalueMsg=socialUserExtAttr.isUserCanReValueMsg();
            //userCanRespondMsg=socialUserExtAttr.isUserCanRespondMsg();
        }
        

        //Una vez que ya se cuantos elementos son, ya que ya se hizo una primera iteración sobre todos los PostIn, hago una segunda
        //iteración ya para mostrar esos ultimos elementos, esto de hacer 2 iteraciones no es muy bueno, TODO: ver con Javier si vemos
        //otra mejor opción.
        //itposts = setsoFinal.iterator();
        while (itposts!=null &&  itposts.hasNext()) {
            PostIn postIn = itposts.next();
            /*
             if(postIn.getSocialTopic()!=null) {
             //Tengo el problema con la paginación porque los resto al vuelo, entonces conforme se va acercando a la ultima página es como se hace 
             //exacto, lo que tendría que hacer es saber el tamaño desde el principio de "l", para que lo pusiera exacto en la páginación desde un inicio.
             l=l-1;                      
             continue;
             } //Es decir, se listarían solo los que no tengan aun un SocialTopic asociado.
             * */

           
            out.println("<tr>");

            //Show Actions
            out.println("<td class=\"accion\">");

            //Remove
            SWBResourceURL urlr = paramRequest.getActionUrl();
            urlr.setParameter("suri", id);
            urlr.setParameter("sval", postIn.getURI());
            urlr.setParameter("page", "" + nPage);
            urlr.setAction("remove");

            String text = SWBUtils.TEXT.scape4Script(postIn.getMsg_Text());

            text = SWBSocialResUtil.Util.replaceSpecialCharacters(text, false);

            if(userCanRemoveMsg)
            {
                out.println("<a href=\"#\" class=\"eliminar\" title=\"" + paramRequest.getLocaleString("remove") + "\" onclick=\"if(confirm('" + paramRequest.getLocaleString("confirm_remove") + " "
                        + text + "?'))" + "{ submitUrl('" + urlr + "',this); } else { return false;}\"></a>");
            }


            //Preview
            SWBResourceURL urlPrev = paramRequest.getRenderUrl().setMode(Mode_PREVIEW).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());
            out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("previewdocument") + "\" class=\"ver\" onclick=\"showDialog('" + urlPrev + "','" + paramRequest.getLocaleString("previewdocument")
                    + "'); return false;\"></a>");

            
            //ReClasifyByTpic
            if(userCanRetopicMsg)
            {
                SWBResourceURL urlreClasifybyTopic = paramRequest.getRenderUrl().setMode(Mode_RECLASSBYTOPIC).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());
                out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("reclasifyByTopic") + "\" class=\"retema\"  onclick=\"showDialog('" + urlreClasifybyTopic + "','"
                        + paramRequest.getLocaleString("reclasifyByTopic") + "'); return false;\"></a>");
            }

            if(userCanRevalueMsg)
            {
                //ReClasyfyBySentiment & Intensity
                SWBResourceURL urlrev = paramRequest.getRenderUrl().setMode(Mode_REVAL).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());
                out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("reeval") + "\" class=\"reevaluar\" onclick=\"showDialog('" + urlrev + "','" + paramRequest.getLocaleString("reeval")
                        + "'); return false;\"></a>");
            }

            /*
             //Respond
             SWBResourceURL urlresponse=paramRequest.getRenderUrl().setMode(Mode_RESPONSE).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());  
             out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("respond") + "\" onclick=\"showDialog('" + urlresponse + "','" + paramRequest.getLocaleString("respond") 
             + "'); return false;\">R</a>");
             * */

            out.println("</td>");

            //Show Message
            out.println("<td class=\"mensaje\">");
            if (postIn.getMsg_Text() != null) {
                if (postIn.getMsg_Text().length() > 200) {
                    String msg2Show = postIn.getMsg_Text().substring(0, 200);
                    msg2Show = SWBSocialResUtil.Util.createHttpLink(msg2Show);
                    out.println(msg2Show);
                } else {
                    out.println(postIn.getMsg_Text());
                }
            } /*else if (postIn.getDescription() != null) {
                if (postIn.getDescription().length() > 200) {
                    out.println(postIn.getDescription().substring(0, 200));
                } else {
                    out.println(postIn.getDescription());
                }
            } */else if (postIn.getTags() != null) {
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
            out.println(postIn instanceof MessageIn ? "<img title=\"Texto\" title=\"Texto\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/tipo-txt.jpg\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("message") + "  \">" : postIn instanceof PhotoIn ? "<img title=\"Imagen\" title=\"Imagen\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/tipo-img.jpg\" border=\"0\" title=\"  " + paramRequest.getLocaleString("photo") + "  \">" : postIn instanceof VideoIn ? "<img title=\"Video\" alt=\"Video\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/tipo-vid.jpg\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("video") + "  \">" : "---");
            out.println("</td>");

            //SocialNetwork
            out.println("<td>");
            out.println(postIn.getPostInSocialNetwork().getDisplayTitle(lang));
            out.println("</td>");

            //Show Creation Time
            out.println("<td>");
            out.println(SWBUtils.TEXT.getTimeAgo(postIn.getPi_created(), lang));
            out.println("</td>");

            //Sentiment
            out.println("<td align=\"center\">");
            if (postIn.getPostSentimentalType() == 0) {
                out.println("---");
            } else if (postIn.getPostSentimentalType() == 1) {
                out.println("<img title=\"Positivo\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/pos.png" + "\">");
            } else if (postIn.getPostSentimentalType() == 2) {
                out.println("<img title=\"Negativo\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/neg.png" + "\">");
            } else {
                out.println("XXX");
            }
            out.println("</td>");

            //Intensity
            out.println("<td>");
            out.println(postIn.getPostIntesityType() == 0 ? "<img src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/ibaja.png\" border=\"0\" title=\"  " + paramRequest.getLocaleString("low") + "  \">" : postIn.getPostIntesityType() == 1 ? "<img src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/imedia.png\" border=\"0\" title=\"  " + paramRequest.getLocaleString("medium") + "  \">" : postIn.getPostIntesityType() == 2 ? "<img src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/ialta.png\" border=\"0\" title=\" " + paramRequest.getLocaleString("high") + "  \">" : "---");
            out.println("</td>");

            //Emoticon
            out.println("<td align=\"center\">");
            if (postIn.getPostSentimentalEmoticonType() == 1) {
                out.println("<img title=\"Positivo\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/emopos.png" + "\"/>");
            } else if (postIn.getPostSentimentalEmoticonType() == 2) {
                out.println("<img alt=\"Negativo\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/emoneg.png" + "\"/>");
            } else if (postIn.getPostSentimentalEmoticonType() == 0) {
                out.println("<img title=\"Neutro\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/emoneu.png" + "\"/>");
            } else {
                out.println("XXX");
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
            out.println(postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFollowers() : "---");
            out.println("</td>");

            //Friends
            out.println("<td align=\"center\">");
            out.println(postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFriends() : "---");
            out.println("</td>");

            //Klout
            out.println("<td align=\"center\">");
            out.println(postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getSnu_klout() : "---");
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


        //System.out.println("J-P:"+p);
        //System.out.println("J-X:"+x);
        //System.out.println("J-L:"+l);

        if (nRec>0) {
            int totalPages=1;
            if(nRec>RECPERPAGE)
            {
                totalPages=Double.valueOf(nRec/20).intValue();
                if((nRec % RECPERPAGE)>0){
                    totalPages=Double.valueOf(nRec/20).intValue()+1;
                }
            }
            out.println("<div id=\"page\">");
            out.println("<div id=\"pagSumary\">"+paramRequest.getLocaleString("page")+":"+nPage+" "+paramRequest.getLocaleString("of") +" "+totalPages+"</div>");
            
            SWBResourceURL pageURL = paramRequest.getRenderUrl();
            //pageURL.setParameter("page", "" + (countPage));
            pageURL.setParameter("suri", id);
            pageURL.setParameter("search", (searchWord.trim().length() > 0 ? searchWord : ""));
            pageURL.setParameter("swbSocialUser", swbSocialUser);
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
                pageURL.setParameter("swbSocialUser", swbSocialUser);
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
     * Reclasifica por SocialTopic un PostIn
     */
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

    /*
     * Reevalua un PostIn en cuanto a sentimiento e intensidad
     */
    public void doRevalue(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/reValue.jsp";
        System.out.println("doRevalue/myPath:" + myPath);
        if (request != null && request.getParameter("postUri") != null) {
            RequestDispatcher dis = request.getRequestDispatcher(myPath);
            if (dis != null) {
                try {
                    SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("postUri"));
                    request.setAttribute("paramRequest", paramRequest);
                    request.setAttribute("postUri", semObj);
                    dis.include(request, response);
                } catch (Exception e) {
                    log.error(e);
                    e.printStackTrace(System.out);
                }
            }
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
                //System.out.println("processAction/1");
                SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("postUri"));
                PostIn post = (PostIn) semObj.createGenericInstance();
                Stream stOld = post.getPostInStream();
                if (request.getParameter("newSocialTopic").equals("none")) {
                    post.setSocialTopic(null);
                } else {
                    SemanticObject semObjSocialTopic = SemanticObject.getSemanticObject(request.getParameter("newSocialTopic"));
                    if (semObjSocialTopic != null) {
                        SocialTopic socialTopic = (SocialTopic) semObjSocialTopic.createGenericInstance();
                        post.setSocialTopic(socialTopic);
                        //Cambiamos de tema a los PostOut asociados al PostIn que acabamos de reclasificar
                        Iterator<PostOut> itPostOuts=post.listpostOutResponseInvs();
                        while(itPostOuts.hasNext())
                        {
                            PostOut postOut=itPostOuts.next();
                            postOut.setSocialTopic(socialTopic);
                        }
                        //
                    }
                }
                response.setMode(SWBActionResponse.Mode_EDIT);
                response.setRenderParameter("dialog", "close");
                response.setRenderParameter("reloadTap", "1");
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
     * Method which calls a jsp to generate a report based on the result of records in this class
     */
    private void doGenerateReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {

        String pages = request.getParameter("pages");
        int page = Integer.parseInt(pages);
        String searchWord = request.getParameter("search") == null ? "" : request.getParameter("search");
        String swbSocialUser = request.getParameter("swbSocialUser");
        String id = request.getParameter("suri");
        Stream stream = (Stream) SemanticObject.getSemanticObject(id).getGenericInstance();
        WebSite webSite = WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());

        

        HashMap hmapResult=filtros(swbSocialUser, webSite, searchWord, request, stream, page);
        
        Iterator<PostIn> setso=((Iterator)hmapResult.get("itResult"));

        try {

            //StreamInBox sInBox = new StreamInBox();
            //sInBox.createExcel(setso, paramRequest, page, response, stream);
            createExcel(setso, paramRequest, page, response, stream);

        } catch (Exception e) {
            log.error(e);
        }
    }

    /*
     * Method which controls the filters allowed in this class
     */
    private HashMap filtros(String swbSocialUser, WebSite wsite, String searchWord, HttpServletRequest request, Stream stream, int nPage) {
        System.out.println("Stream:"+stream.getURI());
        long streamPostIns=0L;
        String sQuery=null;
        ArrayList<PostIn> aListFilter = new ArrayList();
        HashMap hampResult=new HashMap();
        Iterator<PostIn> itposts=null;
        if (swbSocialUser != null) {
            SocialNetworkUser socialNetUser = SocialNetworkUser.ClassMgr.getSocialNetworkUser(swbSocialUser, wsite);
            streamPostIns=Integer.parseInt(getAllPostInbyNetUser_Query(Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), true, stream, socialNetUser));
            sQuery=getAllPostInbyNetUser_Query(Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream, socialNetUser);
            aListFilter=SWBSocial.executeQueryArray(sQuery, wsite);
            hampResult.put("countResult", Long.valueOf(streamPostIns));
        } else {
                if (nPage != 0) 
                {
                    if (searchWord != null && searchWord.trim().length()>0) {
                        streamPostIns=Integer.parseInt(getPostInStreambyWord_Query(0, 0, true, stream, searchWord.trim()));
                        sQuery=getPostInStreambyWord_Query(Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream, searchWord.trim()); 
                        aListFilter=SWBSocial.executeQueryArray(sQuery, wsite); 
                    }else if(request.getParameter("orderBy")!=null)
                    {
                        if(request.getParameter("orderBy").equals("PostTypeUp"))    //Tipo de Mensaje Up
                        {
                            sQuery=getPostInType_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        }else if(request.getParameter("orderBy").equals("PostTypeDown"))    //Tipo de Mensaje Down
                        {
                            sQuery=getPostInType_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        }else if (request.getParameter("orderBy").equals("networkUp")) {
                            sQuery=getPostInNet_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("networkDown")) {
                            sQuery=getPostInNet_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("topicUp")) {
                            streamPostIns=Integer.parseInt(getPostInTopic_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), true, stream));
                            sQuery=getPostInTopic_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("topicDown")) {
                            streamPostIns=Integer.parseInt(getPostInTopic_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), true, stream));
                            sQuery=getPostInTopic_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("cretedUp")) {
                            sQuery=getPostInCreated_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("cretedDown")) {
                            sQuery=getPostInCreated_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("sentimentUp")) {
                            sQuery=getPostInSentimentalType_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("sentimentDown")) {
                            sQuery=getPostInSentimentalType_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("intensityUp")) {
                            sQuery=getPostInIntensityType_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("intensityDown")) {
                            sQuery=getPostInIntensityType_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("emoticonUp")) {
                            streamPostIns=Integer.parseInt(getPostInEmotType_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), true, stream));
                            sQuery=getPostInEmotType_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("emoticonDown")) {
                            streamPostIns=Integer.parseInt(getPostInEmotType_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), true, stream));
                            sQuery=getPostInEmotType_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("userUp")) {
                            sQuery=getPostInUserName_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("userDown")) {
                            sQuery=getPostInUserName_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("followersUp")) {
                            
                        } else if (request.getParameter("orderBy").equals("followersDown")) {
                            
                        } else if (request.getParameter("orderBy").equals("repliesUp")) {
                            //setso = SWBSocialComparator.sortByReplies(itposts, true);
                        } else if (request.getParameter("orderBy").equals("repliesDown")) {
                            //setso = SWBSocialComparator.sortByReplies(itposts, false);
                        } else if (request.getParameter("orderBy").equals("friendsUp")) {
                            //setso = SWBSocialComparator.sortByFriends(itposts, true);
                        } else if (request.getParameter("orderBy").equals("friendsDown")) {
                            //setso = SWBSocialComparator.sortByFriends(itposts, false);
                        } else if (request.getParameter("orderBy").equals("kloutUp")) {
                            //setso = SWBSocialComparator.sortByKlout(itposts, true);
                        } else if (request.getParameter("orderBy").equals("kloutDown")) {
                            //setso = SWBSocialComparator.sortByKlout(itposts, false);
                        } else if (request.getParameter("orderBy").equals("placeUp")) {
                            streamPostIns=Integer.parseInt(getPostInPlace_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), true, stream));
                            sQuery=getPostInPlace_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("placeDown")) {
                            streamPostIns=Integer.parseInt(getPostInPlace_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), true, stream));
                            sQuery=getPostInPlace_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("prioritaryUp")) {
                            sQuery=getPostInPriority_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        } else if (request.getParameter("orderBy").equals("prioritaryDown")) {
                            sQuery=getPostInPriority_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        }
                        
                        //Termina Armado de Query
                        //System.out.println("sQuery a Ejecutar..:"+sQuery+"...FIN...");
                        if(sQuery!=null)
                        {
                            //streamPostIns=Integer.parseInt(getPostInPlace_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), true, stream));
                            aListFilter=SWBSocial.executeQueryArray(sQuery, wsite);
                            streamPostIns=aListFilter.size();
                        }
                    }else{  //No seleccionaron ningún ordenamiento
                        streamPostIns=Integer.parseInt(getPostInWithOutTopic_Query(0, 0, true, stream));
                        sQuery=getPostInWithOutTopic_Query(Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        aListFilter=SWBSocial.executeQueryArray(sQuery, wsite);                        
                    }
                } else { //Traer todo, NPage==0, en teoría jamas entraría a esta opción.
                    streamPostIns=Integer.parseInt(getPostInWithOutTopic_Query(0, 0, true, stream));
                    if(streamPostIns>0)
                    {
                        sQuery=getPostInWithOutTopic_Query(0L, Integer.valueOf((RECPERPAGE)).longValue(), false, stream);
                        aListFilter=SWBSocial.executeQueryArray(sQuery, wsite);   
                    }
                }
            
            System.out.println("streamPostIns FINAL:"+streamPostIns);
            hampResult.put("countResult", Long.valueOf(streamPostIns));
        }
        

        if (aListFilter.size() > 0) {
            itposts = aListFilter.iterator();
            //System.out.println("Entra a ORDEBAR -2");
            //setso = SWBSocialComparator.convertArray2TreeSet(itposts);
        }/*else{
            sQuery=getPostInWithOutTopic_Query(0, 0, stream);
            aListFilter=executeQueryArray(sQuery, wsite);
            streamPostIns=aListFilter.size();
            sQuery=getPostInWithOutTopic_Query(Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), stream);
            aListFilter=executeQueryArray(sQuery, wsite);        
            hampResult.put("countResult", Long.valueOf(streamPostIns));
        }*/
        hampResult.put("itResult", itposts);

        return hampResult;

    }

    //////////////SPARQL FILTERS//////////////////////
    
    private String getPostInWithOutTopic_Query(long offset, long limit, boolean isCount, Stream stream)
    {
        System.out.println("getPostInType_SparqlQuery/offset:"+offset);
        System.out.println("getPostInType_SparqlQuery/limit:"+limit);
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
           "  ?postUri social:postInStream <"+ stream.getURI()+">. \n" + 
           "  ?postUri social:pi_created ?postInCreated. \n" +
           "  OPTIONAL {" +
                "?postUri social:socialTopic ?postInTopic. \n" +
           "    } \n" +
           "  FILTER(!bound(?postInTopic)) \n" +
           "  }\n";
           query+="ORDER BY desc(?postInCreated) \n ";
           
           query+="OFFSET "+offset +"\n";
           if(limit>0)
           {
             query+="LIMIT "+limit;   
           }
           if(isCount)
           {
               System.out.println("Query Count:"+query);
               WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;
    }
    
    
    private String getPostInStreambyWord_Query(long offset, long limit, boolean isCount, Stream stream, String word)
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
           "  ?postUri social:postInStream <"+ stream.getURI()+">. \n" + 
           "  ?postUri social:msg_Text ?msgText. \n" +       
           "  ?postUri social:pi_created ?postInCreated. \n" +
           "  FILTER regex(?msgText, \""+word+"\", \"i\"). " + 
           "  }\n";

           query+="ORDER BY desc(?postInCreated) \n";
           
           query+="OFFSET "+offset +"\n";
           if(limit>0)
           {
             query+="LIMIT "+limit;   
           }
           if(isCount)
           {
               System.out.println("Query Count:"+query);
               WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;
    }
    
    
    /*
     * gets all PostIn by specific SocialNetUser
     */
    private String getAllPostInbyNetUser_Query(long offset, long limit, boolean isCount, Stream stream, SocialNetworkUser socialNetUser)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           {
               query+="select count(*)\n";
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:postInStream <"+ stream.getURI()+">. \n" + 
           "  ?postUri social:postInSocialNetworkUser <"+socialNetUser.getURI()+">." +"\n" +
           "  ?postUri social:pi_created ?postInCreated." + "\n" +
           "  }\n";

           query+="ORDER BY desc(?postInCreated) \n";
           
           query+="OFFSET "+offset +"\n";
           if(limit>0)
           {
             query+="LIMIT "+limit;   
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;
    }
    
    
    private String getPostInType_Query(String orderType, long offset, long limit, boolean isCount, Stream stream)
    {
        System.out.println("getPostInType_SparqlQuery/orderType:"+orderType);
        System.out.println("getPostInType_SparqlQuery/offset:"+offset);
        System.out.println("getPostInType_SparqlQuery/limit:"+limit);
        System.out.println("getPostInType_SparqlQuery/isCount:"+isCount);
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           {
               query+="select count(*)\n";
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:postInStream <"+ stream.getURI()+">. \n" + 
           "  ?postUri social:pi_type ?postInType. \n" +
           "  ?postUri social:pi_created ?postInCreated. \n" +
           "  OPTIONAL {" +
           "    ?postUri social:socialTopic ?postInTopic. \n" +
           "    } \n" +
           "  FILTER(!bound(?postInTopic)) \n" +
           "  }\n";

           if(orderType==null || orderType.equalsIgnoreCase("up"))
           {
               query+="ORDER BY asc(?postInType) ";
           }else
           {
               query+="ORDER BY desc(?postInType) ";
           }
           query+="desc(?postInCreated) \n";
           
           query+="OFFSET "+offset +"\n";
           if(limit>0)
           {
             query+="LIMIT "+limit;   
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;
    }
    
    
    private String getPostInNet_Query(String orderType, long offset, long limit, boolean isCount, Stream stream)
    {
        System.out.println("getPostInType_SparqlQuery/orderType:"+orderType);
        System.out.println("getPostInType_SparqlQuery/offset:"+offset);
        System.out.println("getPostInType_SparqlQuery/limit:"+limit);
        System.out.println("getPostInType_SparqlQuery/isCount:"+isCount);
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           {
               query+="select count(*)\n";
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:postInStream <"+ stream.getURI()+">. \n" + 
           "  ?postUri social:postInSocialNetwork ?postInSocialNet. \n" +
           "  ?postUri social:pi_created ?postInCreated. \n" +
           "  OPTIONAL {" +
           "    ?postUri social:socialTopic ?postInTopic. \n" +
           "    } \n" +
           "  FILTER(!bound(?postInTopic)) \n" +        
           "  }\n";

           if(orderType==null || orderType.equalsIgnoreCase("up"))
           {
               query+="ORDER BY asc(?postInSocialNet) ";
           }else
           {
               query+="ORDER BY desc(?postInSocialNet) ";
           }
           query+="desc(?postInCreated) \n";
           
           query+="OFFSET "+offset +"\n";
           if(limit>0)
           {
             query+="LIMIT "+limit;   
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
    }
          
    private String getPostInTopic_Query(String orderType, long offset, long limit, boolean isCount, Stream stream)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           { 
               query+="select count(*)\n";
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:postInStream <"+ stream.getURI()+">. \n" + 
           "  ?postUri social:socialTopic ?socialTopic." + "\n" + 
           "  ?postUri social:pi_created ?postInCreated. \n" +
           "  OPTIONAL {" +
           "    ?postUri social:socialTopic ?postInTopic. \n" +
           "    } \n" +
           "  FILTER(!bound(?postInTopic)) \n" +        
           "  }\n";

           if(orderType==null || orderType.equalsIgnoreCase("up"))
           {
               query+="ORDER BY asc(?socialTopic) ";
           }else
           {
               query+="ORDER BY desc(?socialTopic) ";
           }
           query+="desc(?postInCreated) \n";
           
           query+="OFFSET "+offset +"\n";
           if(limit>0)
           {
             query+="LIMIT "+limit;   
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
        
    }
    
    private String getPostInCreated_Query(String orderType, long offset, long limit, boolean isCount, Stream stream)
    {
        System.out.println("getPostInType_SparqlQuery/orderType:"+orderType);
        System.out.println("getPostInType_SparqlQuery/offset:"+offset);
        System.out.println("getPostInType_SparqlQuery/limit:"+limit);
        System.out.println("getPostInType_SparqlQuery/isCount:"+isCount);
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           { 
               query+="select count(*)\n";
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:postInStream <"+ stream.getURI()+">. \n" + 
           "  ?postUri social:pi_created ?postInCreated." + "\n" + 
           "  OPTIONAL {" +
           "    ?postUri social:socialTopic ?postInTopic. \n" +
           "    } \n" +
           "  FILTER(!bound(?postInTopic)) \n" +        
           "  }\n";

           if(orderType==null || orderType.equalsIgnoreCase("up"))
           {
               query+="ORDER BY asc(?postInCreated) \n";
           }else
           {
               query+="ORDER BY desc(?postInCreated) \n";
           }
           query+="OFFSET "+offset +"\n";
           if(limit>0)
           {
             query+="LIMIT "+limit;   
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
        
    }
    
    private String getPostInSentimentalType_Query(String orderType, long offset, long limit, boolean isCount, Stream stream)
    {
        System.out.println("getPostInType_SparqlQuery/orderType:"+orderType);
        System.out.println("getPostInType_SparqlQuery/offset:"+offset);
        System.out.println("getPostInType_SparqlQuery/limit:"+limit);
        System.out.println("getPostInType_SparqlQuery/isCount:"+isCount);
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           { 
               query+="select count(*)\n";
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:postInStream <"+ stream.getURI()+">. \n" + 
           "  ?postUri social:postSentimentalType ?postSentimentalType." + "\n" + 
           "  ?postUri social:pi_created ?postInCreated. \n" +
           "  OPTIONAL {" +
           "    ?postUri social:socialTopic ?postInTopic. \n" +
           "    } \n" +
           "  FILTER(!bound(?postInTopic)) \n" +        
           "  }\n";

           if(orderType==null || orderType.equalsIgnoreCase("up"))
           {
               query+="ORDER BY asc(?postSentimentalType) ";
           }else
           {
               query+="ORDER BY desc(?postSentimentalType) ";
           }
           query+="desc(?postInCreated) \n";
           
           query+="OFFSET "+offset +"\n";
           if(limit>0)
           {
             query+="LIMIT "+limit;   
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
    }
    
    private String getPostInIntensityType_Query(String orderType, long offset, long limit, boolean isCount, Stream stream)
    {
        System.out.println("getPostInType_SparqlQuery/orderType:"+orderType);
        System.out.println("getPostInType_SparqlQuery/offset:"+offset);
        System.out.println("getPostInType_SparqlQuery/limit:"+limit);
        System.out.println("getPostInType_SparqlQuery/isCount:"+isCount);
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           { 
               query+="select count(*)\n";
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:postInStream <"+ stream.getURI()+">. \n" + 
           "  ?postUri social:postIntesityType ?postIntensityType." + "\n" + 
           "  ?postUri social:pi_created ?postInCreated. \n" +
           "  OPTIONAL {" +
           "    ?postUri social:socialTopic ?postInTopic. \n" +
           "    } \n" +
           "  FILTER(!bound(?postInTopic)) \n" +
           "  }\n";

           if(orderType==null || orderType.equalsIgnoreCase("up"))
           {
               query+="ORDER BY asc(?postIntensityType) ";
           }else
           {
               query+="ORDER BY desc(?postIntensityType) ";
           }
           query+="desc(?postInCreated) \n";
           
           query+="OFFSET "+offset +"\n";
           if(limit>0)
           {
             query+="LIMIT "+limit;   
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
    }
    
    private String getPostInEmotType_Query(String orderType, long offset, long limit, boolean isCount, Stream stream)
    {
        System.out.println("getPostInType_SparqlQuery/orderType:"+orderType);
        System.out.println("getPostInType_SparqlQuery/offset:"+offset);
        System.out.println("getPostInType_SparqlQuery/limit:"+limit);
        System.out.println("getPostInType_SparqlQuery/isCount:"+isCount);
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           { 
               query+="select count(*)\n";
           }else query+="select *\n";
           
           query+=
            "where {\n" +
           "  ?postUri social:postInStream <"+ stream.getURI()+">. \n" + 
           "  ?postUri social:pi_created ?postInCreated. \n" +
           "  OPTIONAL { \n" +
           "    ?postUri social:postSentimentalEmoticonType ?feelingEmot." + "\n" + 
           "    }" +
           "  }\n";

           if(orderType==null || orderType.equalsIgnoreCase("up"))
           {
               query+="ORDER BY asc(?feelingEmot) ";
           }else
           {
               query+="ORDER BY desc(?feelingEmot) ";
           }
           query+="desc(?postInCreated) \n";
           
           query+="OFFSET "+offset +"\n";
           if(limit>0)
           {
             query+="LIMIT "+limit;   
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
    }
    
    private String getPostInUserName_Query(String orderType, long offset, long limit, boolean isCount, Stream stream)
    {
        System.out.println("getPostInType_SparqlQuery/orderType:"+orderType);
        System.out.println("getPostInType_SparqlQuery/offset:"+offset);
        System.out.println("getPostInType_SparqlQuery/limit:"+limit);
        System.out.println("getPostInType_SparqlQuery/isCount:"+isCount);
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           { 
               query+="select count(*)\n";
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:postInStream <"+ stream.getURI()+">. \n" + 
           "  ?postUri social:postInSocialNetworkUser ?postInuserNetwork." + "\n" + 
           "  ?postInuserNetwork social:snu_name ?userName." + "\n" + 
           "  ?postUri social:pi_created ?postInCreated. \n" +
           "  }\n";

           if(orderType==null || orderType.equalsIgnoreCase("up"))
           {
               query+="ORDER BY asc(?userName) ";
           }else
           {
               query+="ORDER BY desc(?userName) ";
           }
           query+="desc(?postInCreated) \n";
           
           query+="OFFSET "+offset +"\n";
           if(limit>0)
           {
             query+="LIMIT "+limit;   
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
    }
    
    private String getPostInPlace_Query(String orderType, long offset, long limit, boolean isCount, Stream stream)
    {
        System.out.println("getPostInType_SparqlQuery/orderType:"+orderType);
        System.out.println("getPostInType_SparqlQuery/offset:"+offset);
        System.out.println("getPostInType_SparqlQuery/limit:"+limit);
        System.out.println("getPostInType_SparqlQuery/isCount:"+isCount);
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           { 
               query+="select count(*)\n";
           }else query+="select *\n";
           
           query+=
            "where {\n" +
           "  ?postUri social:postInStream <"+ stream.getURI()+">. \n" + 
           "  ?postUri social:pi_created ?postInCreated. \n" +
           "  OPTIONAL { \n" +
           "  ?postUri social:postPlace ?postInPlace." + "\n" + 
           "     }" +
           "  }\n";

           if(orderType==null || orderType.equalsIgnoreCase("up"))
           {
               query+="ORDER BY asc(?postInPlace) ";
           }else
           {
               query+="ORDER BY desc(?postInPlace) ";
           }
           query+="desc(?postInCreated) \n";
           
           query+="OFFSET "+offset +"\n";
           if(limit>0)
           {
             query+="LIMIT "+limit;   
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
        return query;   
    }
            
    
   private String getPostInPriority_Query(String orderType, long offset, long limit, boolean isCount, Stream stream)
    {
        System.out.println("getPostInType_SparqlQuery/orderType:"+orderType);
        System.out.println("getPostInType_SparqlQuery/offset:"+offset);
        System.out.println("getPostInType_SparqlQuery/limit:"+limit);
        System.out.println("getPostInType_SparqlQuery/isCount:"+isCount);
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>" +
           "\n";
           if(isCount)
           { 
               query+="select count(*)\n";
           }else query+="select *\n";
           
           query+=
           "where {\n" +
           "  ?postUri social:postInStream <"+ stream.getURI()+">. \n" + 
           "  ?postUri social:isPrioritary ?isPriority." + "\n" + 
           "  ?postUri social:pi_created ?postInCreated. \n" +
           "  OPTIONAL {" +
           "    ?postUri social:socialTopic ?postInTopic. \n" +
           "    } \n" +
           "  FILTER(!bound(?postInTopic)) \n" +               
           "  }\n";

           if(orderType==null || orderType.equalsIgnoreCase("up"))
           {
               query+="ORDER BY asc(?isPriority) ";
           }else
           {
               query+="ORDER BY desc(?isPriority) ";
           }
           query+="desc(?postInCreated) \n";
           
           query+="OFFSET "+offset +"\n";
           if(limit>0)
           {
             query+="LIMIT "+limit;   
           }
           if(isCount)
           {
               WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
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
        int cont=0;
        System.out.println("Va a Ejecutar executeQueryArray:"+query);
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
                        //System.out.println("ValGeorgeResource:"+val);
                        SemanticObject semObj=SemanticObject.createSemanticObject(val, wsite.getSemanticModel()); 
                        //System.out.println("semObj:"+semObj);
                        PostIn postIn=(PostIn)semObj.createGenericInstance();
                        //System.out.println("semObj/PostIn:"+postIn);
                        if(qs.get("POSTINTOPIC")==null)
                        {
                            cont++;
                            System.out.println("cont:"+cont);
                            aResult.add(postIn);
                        }else{
                            System.out.println("NO ES NULO:"+qs.get("POSTINTOPIC"));
                        }
                    }
                }
            }
        }
        return aResult;
   }*/
   
 public void createExcel(Iterator<PostIn> setso, SWBParamRequest paramRequest, int page, HttpServletResponse response, Stream stream) {
        try {
            // Defino el Libro de Excel
           // Iterator v = setso.iterator();
            String title = stream.getTitle();


            HSSFWorkbook wb = new HSSFWorkbook();

            // Creo la Hoja en Excel
            Sheet sheet = wb.createSheet("Mensajes " + title);


            sheet.setDisplayGridlines(false);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));

            // creo una nueva fila
            Row trow = sheet.createRow((short) 0);
            createTituloCell(wb, trow, 0, CellStyle.ALIGN_CENTER,
                    CellStyle.VERTICAL_CENTER, "Mensajes " + title);

            // Creo la cabecera de mi listado en Excel
            Row row = sheet.createRow((short) 2);

            // Creo las celdas de mi fila, se puede poner un diseño a la celda

            CellStyle cellStyle = wb.createCellStyle();

            createHead(wb, row, 0, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Mensaje");
            createHead(wb, row, 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Tipo");
            createHead(wb, row, 2, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Red");
            createHead(wb, row, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Tema");
            createHead(wb, row, 4, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Creación");
            createHead(wb, row, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Sentimiento");
            createHead(wb, row, 6, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Intensidad");
            createHead(wb, row, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Emot");
            createHead(wb, row, 8, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "RT/Likes");
            createHead(wb, row, 9, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Usuario");
            createHead(wb, row, 10, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Seguidores");
            createHead(wb, row, 11, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Amigos");
            //createHead(wb, row, 12, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Klout");
            createHead(wb, row, 12, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Lugar");
            createHead(wb, row, 13, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Prioritario");

            String lang = paramRequest.getUser().getLanguage();

            //Número de filas
            int i = 3;


            while (setso!= null && setso.hasNext()) {
                PostIn postIn = (PostIn) setso.next();

                Row troww = sheet.createRow((short) i);

                if (postIn.getMsg_Text() != null) {
                     if (postIn.getMsg_Text().length() > 2000) {
                         createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, postIn.getMsg_Text().substring(0,2000));
                        
                    } else {
                        createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, postIn.getMsg_Text());
                    }                   

                } /*else if (postIn.getDescription() != null) {
                    if (postIn.getDescription().length() > 200) {
                        createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, postIn.getDescription().substring(0, 200));

                    } else {
                        createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, postIn.getDescription());
                    }
                } */else if (postIn.getTags() != null) {
                    if (postIn.getTags().length() > 200) {
                        createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, postIn.getTags().substring(0, 200));

                    } else {
                        createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, postIn.getTags());
                    }
                } else {
                    createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, "---");

                }
                createCell(cellStyle, wb, troww, 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn instanceof MessageIn ? paramRequest.getLocaleString("message") : postIn instanceof PhotoIn ? paramRequest.getLocaleString("photo") : postIn instanceof VideoIn ? paramRequest.getLocaleString("video") : "---");
                createCell(cellStyle, wb, troww, 2, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostInSocialNetwork().getDisplayTitle(lang));


                if (postIn.getSocialTopic() != null) {
                    createCell(cellStyle, wb, troww, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getSocialTopic().getDisplayTitle(lang));
                } else {
                    createCell(cellStyle, wb, troww, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "---" );
                }
                createCell(cellStyle, wb, troww, 4, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, SWBUtils.TEXT.getTimeAgo(postIn.getPi_created(), lang) );

                String path = "";

                if (postIn.getPostSentimentalType() == 0) {
                    createCell(cellStyle, wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "----");
                } else if (postIn.getPostSentimentalType() == 1) {
                    createCell(cellStyle, wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Positivo");
                } else if (postIn.getPostSentimentalType() == 2) {
                    createCell(cellStyle, wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Negativo");
                }
                createCell(cellStyle, wb, troww, 6, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostIntesityType() == 0 ? paramRequest.getLocaleString("low") : postIn.getPostIntesityType() == 1 ? paramRequest.getLocaleString("medium") : postIn.getPostIntesityType() == 2 ? paramRequest.getLocaleString("high") : "---");

                if (postIn.getPostSentimentalEmoticonType() == 1) {
                    createCell(cellStyle, wb, troww, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Positivo");

                } else if (postIn.getPostSentimentalEmoticonType() == 2) {
                    createCell(cellStyle, wb, troww, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Negativo");
                } else if (postIn.getPostSentimentalEmoticonType() == 0) {

                    createCell(cellStyle, wb, troww, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "---");
                }
                int postS = postIn.getPostShared();
                String postShared = Integer.toString(postS);
                createCell(cellStyle, wb, troww, 8, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postShared);
                createCell(cellStyle, wb, troww, 9, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getSnu_name() : paramRequest.getLocaleString("withoutUser"));
                Serializable foll = postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFollowers() : paramRequest.getLocaleString("withoutUser");
                createCell(cellStyle, wb, troww, 10, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, foll.toString());
                Serializable amigos = postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFriends() : paramRequest.getLocaleString("withoutUser");
                createCell(cellStyle, wb, troww, 11, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, amigos.toString());

                Serializable klout = postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getSnu_klout() : paramRequest.getLocaleString("withoutUser");

                //createCell(cellStyle, wb, troww, 12, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, klout.toString());
                createCell(cellStyle, wb, troww, 12, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostPlace() == null ? "---" : postIn.getPostPlace());
                createCell(cellStyle, wb, troww, 13, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.isIsPrioritary() ? "SI" : "NO");

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
    
   
   
   
}