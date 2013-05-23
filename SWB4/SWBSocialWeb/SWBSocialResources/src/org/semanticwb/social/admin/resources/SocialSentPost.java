/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Activeable;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.PFlowInstance;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Trashable;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.PFlowManager;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Message;
import org.semanticwb.social.Photo;
import org.semanticwb.social.PostOut;
import org.semanticwb.social.SocialFlow.SocialPFlowMgr;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialPFlow;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.Video;
import org.semanticwb.social.util.SWBSocialUtil;
import org.semanticwb.social.util.SocialLoader;

/**
 *
 * @author jorge.jimenez
 */
public class SocialSentPost extends GenericResource {

    /** The log. */
    private Logger log = SWBUtils.getLogger(SocialSentPost.class);
    /** The webpath. */
    String webpath = SWBPlatform.getContextPath();
    /** The distributor. */
    String distributor = SWBPlatform.getEnv("wb/distributor");
    /** The Mode_ action. */
    String Mode_Action = "paction";
    String Mode_PFlowMsg="doPflowMsg";
    String Mode_PreView="preview";

    /**
     * Creates a new instance of SWBAWebPageContents.
     */
    public SocialSentPost() {
    }
    /** The MOD e_ id request. */
    static String MODE_IdREQUEST = "FORMID";
    public static final String Mode_SOURCE = "source";
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if (Mode_SOURCE.equals(mode)) {
            doShowSource(request, response, paramRequest);
        }else if(Mode_PFlowMsg.equals(mode))
        {
            doPFlowMessage(request, response, paramRequest);
        }
        else if(Mode_Action.equals(mode))
        {
            doAction(request, response, paramRequest);
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
        System.out.println("SocialSentPost/Edit/id:"+id);
        if(id==null) return;

        SocialTopic socialTopic = (SocialTopic)SemanticObject.getSemanticObject(id).getGenericInstance();    
        String wsiteId = socialTopic.getSemanticObject().getModel().getName();
        
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        User user = paramRequest.getUser();
        
        /*
        String busqueda = request.getParameter("search");
        if (null == busqueda) {
            busqueda = "";
        }*/
        

        if (request.getParameter("dialog") != null && request.getParameter("dialog").equals("close")) {
            out.println("<script type=\"javascript\">");
            out.println(" hideDialog(); ");
            out.println(" reloadTab('" + id + "'); ");
            out.println("</script>");
            return;
        }

        String action = request.getParameter("act");
        
        SWBResourceURL urls = paramRequest.getRenderUrl();
        urls.setParameter("act", "");
        urls.setParameter("suri", id);
        
        
        out.println("<div class=\"swbform\">");
        /*
        out.println("<fieldset>");
        out.println("<form id=\"" + id + "/fsearchwp\" name=\"" + id + "/fsearchwp\" method=\"post\" action=\"" + urls + "\" onsubmit=\"submitForm('" + id + "/fsearchwp');return false;\">");
        out.println("<div align=\"right\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
        out.println("<label for=\"" + id + "_searchwp\">" + paramRequest.getLocaleString("searchInProperties") + ": </label><input type=\"text\" name=\"search\" id=\"" + id + "_searchwp\" value=\"" + busqueda + "\">");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("btnSearch") + "</button>"); //
        out.println("</div>");
        out.println("</form>");
        out.println("</fieldset>");
        * */
        out.println("<fieldset>");
        out.println("<table width=\"98%\" >");
        out.println("<thead>");
        out.println("<tr>");
        
        
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
        out.println(paramRequest.getLocaleString("networks"));
        out.println("</th>");
        
        /*
        out.println("<th>");
        out.println(paramRequest.getLocaleString("shared"));
        out.println("</th>");
        * */
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("source"));
        out.println("</th>");
        
        /*
        out.println("<th>");
        out.println(paramRequest.getLocaleString("flow"));
        out.println("</th>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("step"));
        out.println("</th>");
        * */
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("created"));
        out.println("</th>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("updated"));
        out.println("</th>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("status"));
        out.println("</th>");
        
        
        out.println("</thead>");
        out.println("<tbody>");
        
        SocialPFlowMgr pfmgr = SocialLoader.getPFlowManager();
        boolean isInFlow = false;
        boolean isAuthorized = false;
        boolean needAuthorization = false;
        boolean send2Flow = false;
        
        Iterator<PostOut> itposts = PostOut.ClassMgr.listPostOutBySocialTopic(socialTopic);
        
        Set<PostOut> setso = SWBComparator.sortByCreatedSet(itposts, false);
        
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
            
            if (x < p * ps) {
                x++;
                continue;
            }
            if (x == (p * ps + ps) || x == l) {
                break;
            }
            x++;
            
            PostOut sobj = itposts.next();
            
             // revisando contenido en flujo de publicación
             // validacion de botones en relación a los flujos

            isInFlow = false;
            isAuthorized = false;
            needAuthorization = false;
            send2Flow = false;

            isInFlow = pfmgr.isInFlow(sobj);

            //System.out.println("Recurso esta en flujo: "+isInFlow);

            needAuthorization = pfmgr.needAnAuthorization(sobj);

            //System.out.println("Necesita autorización: "+needAuthorization);

            /*
            if (!isInFlow && !needAuthorization) {
                activeButton = true;
            }*/
            if (!isInFlow && needAuthorization) {
                //activeButton = false;
                send2Flow = true;
            }
            
            if (isInFlow) {
                isAuthorized = pfmgr.isAuthorized(sobj);
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
            
            System.out.println("isInFlow:"+isInFlow);
            System.out.println("needAuthorization:"+needAuthorization);
            System.out.println("isAuthorized:"+isAuthorized);
            
            out.println("<tr>");
            
            //Show Actions
            out.println("<td>");
            
            SWBResourceURL urlr = paramRequest.getActionUrl();
            urlr.setParameter("suri", id);
            urlr.setParameter("sval", sobj.getURI());
            urlr.setParameter("page", "" + p);
            urlr.setAction("remove");
            
            out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("remove") + "\" onclick=\"if(confirm('" + paramRequest.getLocaleString("confirm_remove") + " " + SWBUtils.TEXT.scape4Script(sobj.getMsg_Text()) + "?')){ submitUrl('" + urlr + "',this); } else { return false;}\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("remove") + "\"></a>");
            
            
            SWBResourceURL urlpre = paramRequest.getRenderUrl();
            urlpre.setParameter("suri", id);
            urlpre.setParameter("page", "" + p);
            urlpre.setParameter("sval", sobj.getURI());
            urlpre.setParameter("preview", "true");
            out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("previewdocument") + "\" onclick=\"submitUrl('" + urlpre + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/preview.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("previewdocument") + "\"></a>");
            
            //if (canEdit) {
                out.println("<a href=\"#\"  title=\"" + paramRequest.getLocaleString("documentAdmin") + "\" onclick=\"\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"" + "documentAdmin" + "\"></a>");
            //} else {
            //    out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"" + "documentAdmin" + "\">");
            //}
             
            
                
            if (send2Flow) {
                boolean canSend2Flow = Boolean.TRUE;
                String pfid = null;
                SocialPFlow[] arrPf = pfmgr.getFlowsToSendContent(sobj);
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
                    url2flow.setParameter("sval", sobj.getURI());
                    url2flow.setParameter("page", "" + p);
                    url2flow.setParameter("pfid", pfid);
                    out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("senddocument2flow") + "\" onclick=\"showDialog('" + url2flow + "','" + paramRequest.getLocaleString("comentary") + "'); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/enviar-flujo.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("senddocument2flow") + "\"></a>");
                } else {
                    out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/enviar-flujo.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("senddocument2flow") + "\" title=\"" + paramRequest.getLocaleString("canNOTsenddocument2flow") + "\">");
                }
            } else if (isInFlow && !isAuthorized) {
                out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/espera_autorizacion.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("documentwaiting") + "\">");
            } else if (isInFlow && isAuthorized) {
                out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/enlinea.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("Caccepted") + "\">");
            }
            out.println("</td>");
            
            //Show 30 firsts characters of Msg PostOut
            out.println("<td>");
            out.println(SWBUtils.TEXT.cropText(sobj.getMsg_Text(), 30));
            out.println("</td>");
            
            //Show PostType
            out.println("<td>");
            if(sobj instanceof Message)
            {
                out.println(paramRequest.getLocaleString("message"));
            }else if(sobj instanceof Photo)
            {
                out.println(paramRequest.getLocaleString("photo"));
            }else if(sobj instanceof Video)
            {
                out.println(paramRequest.getLocaleString("video"));
            }
            out.println("</td>");
            
            //Show Networks
            out.println("<td>");
            String nets="---";
            boolean firstTime=true;
            Iterator<SocialNetwork> itPostSocialNets=sobj.listSocialNetworks();
            while(itPostSocialNets.hasNext())
            {
                SocialNetwork socialNet=itPostSocialNets.next();
                String sSocialNet=socialNet.getTitle(lang);
                if(sSocialNet!=null && sSocialNet.trim().length()>0)
                {
                    if(firstTime) 
                    {
                        nets=""+socialNet.getTitle(lang);
                        firstTime=false;
                    }
                    else nets+="|"+socialNet.getDisplayTitle(lang);
                }
            }
            out.println(nets);
            out.println("</td>");
            
            //PostIn Source 
            out.println("<td>");
            if(sobj.getPostInSource()!=null)
            {
                SWBResourceURL url=paramRequest.getRenderUrl().setMode(Mode_SOURCE).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", sobj.getPostInSource().getURI());  
                //out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("senddocument2flow") + "\" onclick=\"showDialog('" + url2flow + "','" + paramRequest.getLocaleString("comentary") + "'); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/enviar-flujo.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("senddocument2flow") + "\"></a>");
                out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("source") + "\" onclick=\"showDialog('" + url + "','" + paramRequest.getLocaleString("source") + "'); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/enviar-flujo.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("source") + "\"></a>");
                
                //paramRequest.getRenderUrl().setMode(Mode_SOURCE).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", sobj.getURI()) +"\">Origen</a>");
            }else{
                out.println("---");
            }
            out.println("</td>");
            
            //PostOut creation
            out.println("<td>");
            out.println(sobj.getCreated());
            out.println("</td>");
            
            //PostOut lastUpdate
            out.println("<td>");
            out.println(sobj.getUpdated());
            out.println("</td>");
            
            out.println("<td>");
            //El PostOut No se ha enviado, aqui se daría la posibilidad de que un usuario lo envíe.
            System.out.println("msg..:"+sobj.getMsg_Text());
            System.out.println("Ya esta publicado..:"+sobj.isPublished());
            
            if (!sobj.isPublished() && !needAuthorization) {
                SWBResourceURL urlu = paramRequest.getRenderUrl();
                urlu.setMode(Mode_Action);
                urlu.setParameter("suri", sobj.getURI());
                urlu.setParameter("act", "updstatus");
                //out.println("J1");
                out.println("<a href=\"#\" onclick=\"showStatusURL('" + urlu + "'); \" />Publicar</a>");
            } else {    //El PostOut ya se envío
               if(!isInFlow && !needAuthorization && !isAuthorized)
               {
                    out.println("Publicado");
               }if(!isInFlow && needAuthorization && !isAuthorized)
               {
                   out.println("Rechazado");
               }
               else if(isInFlow && needAuthorization && !isAuthorized){
                    out.println("En Flujo");
               }
               //out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/enviar-flujo.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("publish") + "\" title=\"" + paramRequest.getLocaleString("canNOTsenddocument2flow") + "\">");
            }
            out.println("</td>");
            
          out.println("</tr>");   
        }
        
        
        out.println("</tbody>");
        out.println("</table>");
        out.println("</fieldset>");
        
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
                if (z != p) {
                    out.println("<a href=\"#\" onclick=\"submitUrl('" + urlNew + "',this); return false;\">" + (z + 1) + "</a> ");
                } else {
                    out.println((z + 1) + " ");
                }
            }
            out.println("</center>");
            out.println("</fieldset>");
        }
        
        out.println("</div>");
        
        
        if (request.getParameter("preview") != null && request.getParameter("preview").equals("true")) {
            if (request.getParameter("sval") != null) {
                try {
                    doPreview(request, response, paramRequest);
                } catch (Exception e) {
                    out.println("Preview not available...");
                }
            } else {
                out.println("Preview not available...");
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
        System.out.println("Entra a doPreview");
        String id = request.getParameter("sval");
        PrintWriter out = response.getWriter();
        out.println("<fieldset>");
        out.println("<legend>" + paramRequest.getLocaleString("previewdocument") + "</legend>");
        try {
            /*
            SWBResource res = SWBPortal.getResourceMgr().getResource(id);
            ((SWBParamRequestImp) paramRequest).setResourceBase(res.getResourceBase());
            res.render(request, response, paramRequest);
            **/
        } catch (Exception e) {
            log.error("Error while getting content string ,id:" + id, e);
        }
        out.println("</fieldset>");
    }

    /**
     * Show the list of the pflows to select one and send the element to the selected publish flow.
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
        
        System.out.println("Site:"+paramRequest.getWebPage().getWebSite());
        
        System.out.println("id:"+id);
        System.out.println("resid:"+resid);
        
        SocialTopic socialTopic = (SocialTopic)SemanticObject.getSemanticObject(id).getGenericInstance();    
        String wsiteId = socialTopic.getSemanticObject().getModel().getName();
        
        SemanticObject semObj=SemanticObject.getSemanticObject(resid);
        if(semObj==null) return;
        
        PostOut postOut=(PostOut)semObj.createGenericInstance();
        
        PrintWriter out = response.getWriter();

        User user = paramRequest.getUser();

        
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
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
            out.println("<option value=\"" + arrPf[i].getURI() + "\" " + (i == 0 ? "selected" : "") + ">" + arrPf[i].getDisplayTitle(user.getLanguage()) + "</option>");
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

    /**
     * Do a specific action like add, remove, send to a publish flow, delete the reference between WebPage and Content.
     *
     * @param request , this holds the parameters
     * @param response , an answer to the user request, and a list of objects like user, webpage, Resource, ...
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String id = request.getParameter("suri");
        String sprop = request.getParameter("sprop");
        String sproptype = request.getParameter("sproptype");
        String action = response.getAction();

        //System.out.println("Action: "+action);

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = SemanticObject.createSemanticObject(id); //WebPage
        SemanticClass cls = obj.getSemanticClass();

        if ("new".equals(action)) {
            log.debug("ProcessAction(new) ");

            //id = request.getParameter("suri");
            //sprop = request.getParameter("sprop");
            //sproptype = request.getParameter("sproptype");
            String sobj = request.getParameter("sobj");

            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(sprop);
            log.debug("ProcessAction(new): sobj: " + sobj);

            SemanticObject wpage = null;
            wpage = obj;//ont.getSemanticObject(id);

            SWBFormMgr fmgr = new SWBFormMgr(Resource.swb_Resource, wpage, null);
            try {
                SemanticObject nso = fmgr.processForm(request);

                SemanticObject ptype = SemanticObject.createSemanticObject(sobj);
                nso.setObjectProperty(Resource.swb_resourceType, ptype);

                //System.out.println("nso:"+nso);
                //System.out.println("ptype:"+ptype);
                //System.out.println("obj:"+obj);

                if (prop.getName().startsWith("has")) {
                    obj.addObjectProperty(prop, nso);
                } else {
                    obj.setObjectProperty(prop, nso);
                }

                if (id != null) {
                    response.setRenderParameter("suri", id);
                }
                if (sprop != null) {
                    response.setRenderParameter("sprop", sprop);
                }
                if (sproptype != null) {
                    response.setRenderParameter("sproptype", sproptype);
                }
                if (nso != null) {
                    response.setRenderParameter("nsuri", nso.getURI());
                }
            } catch (FormValidateException e) {
                throw new SWBResourceException("Error to process form...", e);
            }

            response.setRenderParameter("statmsg", response.getLocaleString("statmsg1"));
            response.setMode(response.Mode_EDIT);
            response.setRenderParameter("act", "");
        } else if ("remove".equals(action)) //suri, prop
        {
            String sval = request.getParameter("sval");
            SemanticObject so = SemanticObject.createSemanticObject(sval);
            log.debug("processAction(remove PostOut):"+so);
            so.remove();
           
            response.setRenderParameter("closetab", sval);
            response.setRenderParameter("statmsg", response.getLocaleString("statmsg2"));
            response.setMode(SWBActionResponse.Mode_EDIT);
        } else if ("send2flow".equals(action)) {
            SocialPFlowMgr pfmgr = SocialLoader.getPFlowManager();
            String sval = request.getParameter("sval"); // id resource
            String pfid = request.getParameter("pfid"); // id pflow
            String usermessage = request.getParameter("usrmsg"); // mensaje del usuario
            SocialPFlow pf = (SocialPFlow) ont.getGenericObject(pfid);
            PostOut res = (PostOut) ont.getGenericObject(sval);
            pfmgr.sendResourceToAuthorize(res, pf, usermessage);
            response.setRenderParameter("dialog", "close");
            response.setMode(SWBActionResponse.Mode_EDIT);
        } else if ("deleteall".equals(action)) {
            log.debug("processAction(deleteall)" + sprop);
            //System.out.println("processAction(deleteall)");
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
        }
        if (id != null) {
            response.setRenderParameter("suri", id);
        }
        if (sprop != null) {
            response.setRenderParameter("sprop", sprop);
        }
        if (request.getParameter("search") != null && request.getParameter("search").trim().length() > 0) {
            response.setRenderParameter("search", request.getParameter("search"));
        }
    }

    /**
     * Do an update, update status, active or unactive action of a Content element requested by the user.
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
        log.debug("doAction()");
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
            System.out.println("va a publicar Post..:"+obj);
            if(obj!=null)
            {
                PostOut postOut=(PostOut)obj.createGenericInstance();
                try
                {
                    SWBSocialUtil.PostOutUtil.publishPost(postOut, request, null);
                    postOut.setPublished(true); 
                    System.out.println("Post Publicado...");
                    so = postOut.getSocialTopic().getSemanticObject(); 
                }catch(Exception se)
                {
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
                    if (!isAuthorized || instance.getStatus()==3) {  // estatus 3 = rechazado
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
    
    /*
     * Show the source message of One PostOut that comes as a parameter "postUri"
     */
    public void doShowSource(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        System.out.println("Entra a doShowSourceJ");
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        final String myPath = SWBPlatform.getContextPath() + "/work/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/socialSentPost.jsp";
        if (request != null) {
            RequestDispatcher dis = request.getRequestDispatcher(myPath);
            if (dis != null) {
                try {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                    request.setAttribute("sObjPostOut", semObject);
                    request.setAttribute("paramRequest", paramRequest);
                    dis.include(request, response);
                } catch (Exception e) {
                    log.error(e);
                    e.printStackTrace(System.out);
                }
            }
        }
    }

}