/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources.BORRAR;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SentimentalLearningPhrase;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialPFlow;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.admin.resources.reports.PostSummary;
import org.semanticwb.social.util.SWBSocialUtil;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

/**
 *
 * @author jorge.jimenez
 */
public class SocialTopicMsgIn extends GenericResource {

    /**
     * The log.
     */
    private static Logger log = SWBUtils.getLogger(PostSummary.class);
    public static final int PAGE_SIZE = 25; //Líneas por página
    public static final String Mode_JSON = "json";
    public static final String Mode_REVAL = "rv";
    public static final String Mode_RESPONSE = "response";
    public static int xxx = 1000;
    /**
     * The tpl.
     */
    javax.xml.transform.Templates tpl;
    /**
     * The web work path.
     */
    String webWorkPath = "/work";

    /**
     * The path.
     */
    //final String path = SWBPlatform.getContextPath() +"/work/SWBAdmin/jsp/stream/";
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if (Mode_JSON.equals(mode)) {
            doJson(request, response, paramRequest); 
        } else if (Mode_REVAL.equals(mode)) {
            doRevalue(request, response, paramRequest);
        } else if (Mode_RESPONSE.equals(mode)) {
            doResponse(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("post")) {
            doCreatePost(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        final Resource base = getResourceBase();
        final String action = response.getAction();
        if (action.equals("postMessage")) {
            if(request.getParameter("objUri")!=null)
            {
                PostIn postIn=(PostIn)SemanticObject.getSemanticObject(request.getParameter("objUri")).createGenericInstance();
                
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
                
                
                SWBSocialUtil.PostOutUtil.sendNewPost(postIn, postIn.getSocialTopic(), socialPFlow, aSocialNets, wsite, request.getParameter("toPost"), request, response);
                
            }
        }else if (SWBResourceURL.Action_EDIT.equals(action)) {
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

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        if (request.getParameter("doView") == null) {
            doEdit(request, response, paramRequest);
        } else {
            final String myPath = SWBPlatform.getContextPath() + "/work/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/socialTopicMsgIn.jsp";
            if (request != null) {
                RequestDispatcher dis = request.getRequestDispatcher(myPath);
                if (dis != null) {
                    try {
                        request.setAttribute("paramRequest", paramRequest);
                        dis.include(request, response);
                    } catch (Exception ex) {
                        log.error(ex);
                        ex.printStackTrace(System.out);
                    }
                }
            }
        }

    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.getWriter().println("<iframe dojotype=\"dijit.layout.ContentPane\" src=\"" + paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW).setParameter("doView", "doView").setParameter("suri", request.getParameter("suri")) + "\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" >");
        response.getWriter().println("</iframe>");
    }

    private void doJson(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/json;charset=iso-8859-1");

        String lang = paramsRequest.getUser().getLanguage();
        Locale locale = new Locale("es", "mx");
        NumberFormat nf = NumberFormat.getIntegerInstance(locale);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy HH:mm", locale);
        DecimalFormat df = new DecimalFormat("#.#");

        JSONObject jobj = new JSONObject();
        JSONArray jarr = new JSONArray();
        try {
            jobj.put("identifier", "fl");
            jobj.put("label", "fl");
            jobj.put("items", jarr);
        } catch (JSONException jse) {
            jse.printStackTrace(System.out);
        }

        String wsiteId = request.getParameter("wsite");
        //System.out.println("wsiteId en PostSummary:"+wsiteId);
        int pos = wsiteId.indexOf("?");
        if (pos > -1) {
            wsiteId = wsiteId.substring(0, pos);
        }
        WebSite wsite = null;
        List<PostIn> posts = null;
        int size = 0;
        if (wsiteId != null) {
            String sTopic=request.getParameter("sTopic");
            wsite = WebSite.ClassMgr.getWebSite(wsiteId);
            SocialTopic socialTopic=SocialTopic.ClassMgr.getSocialTopic(sTopic, wsite);
            if(socialTopic!=null)
            {
                Iterator<PostIn> itposts = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic);
                posts = SWBUtils.Collections.copyIterator(itposts);
                size = posts.size();
            }
        }

        int ipage;
        try {
            ipage = Integer.parseInt(request.getParameter("ipage"));
        } catch (NumberFormatException nfe) {
            ipage = 1;
        }
        int el = size;
        int paginas = el / PAGE_SIZE;
        if (el % PAGE_SIZE != 0) {
            paginas++;
        }
        int inicio = 0;
        int fin = PAGE_SIZE;

        // Mantiene la consistencia al eliminar elementos   
        if (ipage > paginas) {
            ipage--;
        }

        inicio = (ipage * PAGE_SIZE) - PAGE_SIZE;
        fin = (ipage * PAGE_SIZE);

        if (ipage < 1 || ipage > paginas) {
            ipage = 1;
        }
        if (inicio < 0) {
            inicio = 0;
        }
        if (fin < 0) {
            fin = PAGE_SIZE;
        }
        if (fin > el) {
            fin = el;
        }
        if (inicio > fin) {
            inicio = 0;
            fin = PAGE_SIZE;
        }
        if (fin - inicio > PAGE_SIZE) {
            inicio = 0;
            fin = PAGE_SIZE;
        }

        inicio = Integer.parseInt(request.getParameter("inicio")) - 1;
        fin = Integer.parseInt(request.getParameter("fin"));
        //System.out.println("INICIO en java-J: " +  inicio);
        //System.out.println("FIN en java-J: " + fin);
        //inicio++;
        //////////////////////

        PostIn post;
        for (int i = inicio; i < fin && i < size; i++) {
            post = posts.get(i);
            if (post instanceof MessageIn) {
                MessageIn msg = (MessageIn) post;
                JSONObject obj = new JSONObject();
                try {
                    obj.put("fl", i + 1);
                    try {
                        obj.put("cta", msg.getPostSource());
                    } catch (Exception e) {
                        obj.put("cta", "--");
                    }
                    //  try {
                    //       obj.put("sn",msg.getPostInSocialNetwork().getClass().getSimpleName());
                    //  }catch(Exception e) {
                    //          obj.put("sn","--");
                    //  }
                    try {
                        obj.put("date", sdf.format(msg.getCreated()));
                    } catch (Exception e) {
                        obj.put("date", "--");
                    }
                    obj.put("msg", msg.getMsg_Text());
                    obj.put("feel", msg.getPostSentimentalType());
                    obj.put("eicon", msg.getPostSentimentalEmoticonType());
                    obj.put("int", df.format(msg.getPostIntensityValue()));
                    try {
                        obj.put("rp", msg.getPostRetweets());
                    } catch (Exception e) {
                        obj.put("rp", "0");
                    }
                    try {
                        obj.put("user", msg.getPostInSocialNetworkUser().getSnu_name());
                        obj.put("fllwrs", msg.getPostInSocialNetworkUser().getFollowers());
                        obj.put("frds", msg.getPostInSocialNetworkUser().getFriends());
                    } catch (Exception e) {
                        obj.put("user", "--");
                        obj.put("fllwrs", "0");
                        obj.put("frds", "0");
                    }
                    //System.out.println("En reporte-place:" + msg.getPostPlace());
                    obj.put("plc", msg.getPostPlace() == null ? "--" : msg.getPostPlace());
                    obj.put("postIn", post.getURI());
                    jarr.put(obj);
                } catch (Exception jse) {
                    //jse.printStackTrace(System.out);
                    continue;
                }
            }
        }
        //response.getOutputStream().println(jobj.toString());
        response.getWriter().print(jobj.toString());
    }

    public void doRevalue(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        final String myPath = SWBPlatform.getContextPath() + "/work/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/revalue.jsp";
        if (request != null) {
            RequestDispatcher dis = request.getRequestDispatcher(myPath);
            if (dis != null) {
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
    
    private void doResponse(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        final String myPath = SWBPlatform.getContextPath() + "/work/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/socialTopicMsgInResponse.jsp?showSource=1";
        if (request != null) {
            RequestDispatcher dis = request.getRequestDispatcher(myPath);
            if (dis != null) {
                try {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                    request.setAttribute("sObjPostIn", semObject);
                    request.setAttribute("paramRequest", paramRequest);
                    dis.include(request, response);
                } catch (Exception e) {
                    log.error(e);
                    e.printStackTrace(System.out);
                }
            }
        }
    }
    
    public void doCreatePost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {        
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() +"/work/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/typeOfContent.jsp");
        request.setAttribute("valor", request.getParameter("valor"));
        request.setAttribute("wsite", request.getParameter("wsite"));
        request.setAttribute("objUri", request.getParameter("objUri"));
        //request.setAttribute("action", request.getParameter("action"));
        request.setAttribute("paramRequest", paramRequest);
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al enviar los datos a typeOfContent.jsp " + ex.getMessage());
        }
    }
    
}