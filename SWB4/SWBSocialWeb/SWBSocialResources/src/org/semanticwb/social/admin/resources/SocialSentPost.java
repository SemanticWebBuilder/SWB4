/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
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
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Activeable;
import org.semanticwb.model.CalendarRef;
import org.semanticwb.model.PFlowInstance;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Trashable;
import org.semanticwb.model.User;
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
import org.semanticwb.social.FastCalendar;
import org.semanticwb.social.Message;
import org.semanticwb.social.Photo;
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
    private Logger log = SWBUtils.getLogger(SocialSentPost.class);
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
                System.out.println("Error reprt:" + e);
            }
        } else if (Mode_ShowPhotos.equals(mode)) {
            
            doShowPhotos(request, response, paramRequest);

        }  else {
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

        out.println("<fieldset class=\"barra\">");
        out.println("<div class=\"barra\">");

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

        String swbSocialUser = request.getParameter("swbSocialUser");

        int nPage;
        try {
            nPage = Integer.parseInt(request.getParameter("page"));
        } catch (Exception ignored) {
            nPage = 1;
        }

        HashMap hmapResult = filtros(swbSocialUser, wsite, searchWord, request, socialTopic, nPage);

        long nRec = ((Long) hmapResult.get("countResult")).longValue();
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

            if (userCanRemoveMsg) {
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
                    out.println("<a class=\"swbIconFC\" href=\"#\" onclick=\"showDialog('" + urlFastCalendars.setParameter("postUri", postOut.getURI()) + "','" + paramRequest.getLocaleString("associatedFastCalendar") + "'); return false;\">FC</a>");
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
                out.println("<a class=\"swbIconCA\" href=\"#\"  onclick=\"addNewTab('" + postOut.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + msgText + "');return false;\" title=\"" + msgText + "\">CA</a>");
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
                                out.println("<a class=\"status6\" href=\"#\" onclick=\"showStatusURL('" + urlu + "'); \" /><strong>" + paramRequest.getLocaleString("publish") + "</strong></a>");
                            }
                        }
                    } else {    //El PostOut ya se envío
                        //System.out.println("SOCIALSENTPOST2");
                        if (!isInFlow && needAuthorization && !isAuthorized) {
                            String sFlowRejected = "---";
                            if (postOut.getPflowInstance() != null && postOut.getPflowInstance().getPflow() != null) {
                                sFlowRejected = postOut.getPflowInstance().getPflow().getDisplayTitle(lang);
                            }
                            out.println("<span class=\"status4\" title=\""+paramRequest.getLocaleString("rejected")+"\"><strong>"+paramRequest.getLocaleString("rejected") + "(" + sFlowRejected + ")</strong></span>");
                        } else if (isInFlow && needAuthorization && !isAuthorized) {
                            //System.out.println("postOut.getPflowInstance().getStatus():"+postOut.getPflowInstance().getStatus());
                            out.println("<span class=\"status7\" title=\""+paramRequest.getLocaleString("onFlow")+"\"><strong>"+paramRequest.getLocaleString("onFlow") + "(" + postOut.getPflowInstance().getPflow().getDisplayTitle(lang) + "/" + postOut.getPflowInstance().getStep() + ")</strong></span>");
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
        System.out.println("SocialSentPost/processAction-1:" + action);
        try {
            if (action.equals("postMessage") || action.equals("uploadPhoto") || action.equals("uploadVideo")) {
                try {
                    //System.out.println("SocialSentPost/processAction-2");
                    ArrayList aSocialNets = new ArrayList();
                    WebSite wsite = WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));
                    String objUri = request.getParameter("objUri");
                    SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
                    PostOut postOut = (PostOut) semanticObject.createGenericInstance();
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
                        SWBSocialUtil.PostOutUtil.editPostOut(postOut, spflow, aSocialNets, wsite, toPost, request, response);
                        response.setMode(SWBResourceURL.Mode_EDIT);
                        response.setRenderParameter("dialog", "close");
                        response.setRenderParameter("statusMsg", SWBUtils.TEXT.encode(response.getLocaleLogString("postModified"), "utf8"));
                        response.setRenderParameter("reloadTab", postOut.getSocialTopic().getURI());
                        response.setRenderParameter("suri", postOut.getSocialTopic().getURI());
                        //System.out.println("SocialSentPost/processAction-J6-suri:"+postOut.getSocialTopic().getURI());
                    } else {
                        response.setMode(SWBResourceURL.Mode_EDIT);
                        response.setRenderParameter("dialog", "close");
                        response.setRenderParameter("statusMsg", response.getLocaleLogString("postTypeNotDefined"));
                        response.setRenderParameter("reloadTab", postOut.getSocialTopic().getURI());
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    e.printStackTrace();
                }


                ///////////////////////////////
                response.setRenderParameter("statmsg", response.getLocaleString("statmsg1"));
                response.setMode(response.Mode_EDIT);
                response.setRenderParameter("act", "");
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
                System.out.println("PostOut/ProcessActionSentPost:"+request.getParameter("postOut"));
                if(request.getParameter("postOut")!=null)
                {
                    SemanticObject semObj=SemanticObject.getSemanticObject(request.getParameter("postOut"));
                    PostOut postOut=(PostOut)semObj.getGenericInstance();
                    
                    String postOutInitDate=request.getParameter("postOut_inidate");
                    String postOutInitHour=request.getParameter("postOut_starthour");
                    
                    System.out.println("postOutInitDate-Jorge en SocialSentPost:"+postOutInitDate+",postOutInitHour:"+postOutInitHour);
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
                        
                        System.out.println("postOutInitDate-1**:"+postOutInitDate+",postOutInitHour-1**:"+postOutInitHour);
                    
                        
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
                        System.out.println("H a poner:"+h);
                        System.out.println("M a poner:"+m);
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
                            System.out.println("postOutNet:" + postOutNet);
                            postOutwithPostOutNets = true;
                            if (postOutNet.getStatus() == 0) {
                                System.out.println("postOutNet-1/status:" + postOutNet.getStatus());
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
                            System.out.println("postOutNet:" + postOutNet);
                            postOutwithPostOutNets = true;
                            if (postOutNet.getStatus() == 0) {
                                System.out.println("postOutNet-1/status:" + postOutNet.getStatus());
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
                    System.out.println("Toma solo la página..:"+nPage);
                    if (searchWord != null && searchWord.trim().length()>0) {
                        socialTopicPostOut=Integer.parseInt(getPostOutTopicbyWord_Query(0, 0, true, socialTopic, searchWord.trim()));
                        sQuery=getPostOutTopicbyWord_Query(Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic, searchWord.trim()); 
                        aListFilter=SWBSocial.executeQueryArray(sQuery, wsite); 
                    }else if(request.getParameter("orderBy")!=null)
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
                            System.out.println("socialTopicPostOut en origenUp:"+socialTopicPostOut);
                            sQuery=getPostOutSource_Query(null, Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), false, socialTopic);
                        } else if (request.getParameter("orderBy").equals("origenDown")) {
                            //socialTopicPostOut=Integer.parseInt(getPostOutSource_Query("down", Integer.valueOf((nPage * RECPERPAGE) - RECPERPAGE).longValue(), Integer.valueOf((RECPERPAGE)).longValue(), true, socialTopic));
                            System.out.println("socialTopicPostOut en  origenDown:"+socialTopicPostOut);
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
                        System.out.println("sQuery a Ejecutar..:"+sQuery+"...FIN...");
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
                
                System.out.println("streamPostOuts-Antes de:"+socialTopicPostOut);
                /*
                if(socialTopicPostOut==0L)
                {
                    socialTopicPostOut=Integer.parseInt(getAllPostOutSocialTopic_Query(0, 0, true, socialTopic));
                }*/
                System.out.println("StreamPostOuts:"+socialTopicPostOut);

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
     * Metodo que obtiene todos los PostIns
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
        System.out.println("getPostInType_SparqlQuery/offset:"+offset);
        System.out.println("getPostInType_SparqlQuery/limit:"+limit);
        System.out.println("getPostInType_SparqlQuery/isCount:"+isCount);
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
               System.out.println("Query Count:"+query);
               WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
               query=SWBSocial.executeQuery(query, wsite);
           }
           System.out.println("return query"+query);
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
        System.out.println("getPostInType_SparqlQuery/orderType:"+orderType);
        System.out.println("getPostInType_SparqlQuery/offset:"+offset);
        System.out.println("getPostInType_SparqlQuery/limit:"+limit);
        System.out.println("getPostInType_SparqlQuery/isCount:"+isCount);
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
        System.out.println("getPostInType_SparqlQuery/orderType:"+orderType);
        System.out.println("getPostInType_SparqlQuery/offset:"+offset);
        System.out.println("getPostInType_SparqlQuery/limit:"+limit);
        System.out.println("getPostInType_SparqlQuery/isCount:"+isCount);
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

    private void doDeletePhoto(HttpServletRequest request, SWBActionResponse response) {
        String idPhoto = request.getParameter("idPhoto");
        String po = request.getParameter("postOut");

        SemanticObject semObj = SemanticObject.getSemanticObject(po);

        Photo photo = (Photo) semObj.getGenericInstance();
        Iterator i = photo.listPhotos();

        while (i.hasNext()) {
            String ca =  (String) i.next();
            if (ca.equals(idPhoto)) {
                photo.removePhoto(idPhoto);
                break;
            }                      
            
            response.setMode("showPhotos");
            response.setRenderParameter("postOut", request.getParameter("postOut"));
        }
    }
    
}