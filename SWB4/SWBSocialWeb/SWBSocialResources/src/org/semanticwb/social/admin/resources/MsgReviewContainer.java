/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroupRef;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SentimentalLearningPhrase;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.util.SWBSocialUtil;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

/**
 *
 * @author jorge.jimenez
 */
public class MsgReviewContainer extends GenericAdmResource {

    /**
     * The log.
     */
    private static Logger log = SWBUtils.getLogger(MsgReviewContainer.class);
    public static final int PAGE_SIZE = 25; //Líneas por página
    public static final String Mode_JSON = "json";
    public static final String Mode_REVAL = "rv";
    public static final String Mode_PRINCIPAL="afterChooseSite";
    public static int xxx = 1000;
    /**
     * The tpl.
     */
    javax.xml.transform.Templates tpl;
    /**
     * The web work path.
     */
    String webWorkPath = "/work";

    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        final String mode = paramRequest.getMode();
        if(mode.equals(Mode_PRINCIPAL))
        {
            doPrincipal(request, response, paramRequest); 
        }else if (Mode_JSON.equals(mode)) {
            doJson(request, response, paramRequest);
        } else if (Mode_REVAL.equals(mode)) {
            doRevalue(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }
    
    public void doPrincipal(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try{
            response.setContentType("text/html;charset=iso-8859-1");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            if (request.getParameter("doView") == null) {
                request.setAttribute("socialSite", request.getParameter("socialSite"));
                doEdit(request, response, paramRequest);
            } else {
                final String myPath = SWBPlatform.getContextPath() + "/work/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/msgReviewContainer.jsp";
                if (request != null) {
                    RequestDispatcher dis = request.getRequestDispatcher(myPath);
                    if (dis != null) {
                        try {
                            request.setAttribute("socialSite", request.getParameter("socialSite"));
                            request.setAttribute("paramRequest", paramRequest);
                            dis.include(request, response);
                        } catch (Exception ex) {
                            log.error(ex);
                            ex.printStackTrace(System.out);
                        }
                    }
                }
            }
        }catch(Exception e)
        {
            log.error(e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        final Resource base = getResourceBase();
        final String action = response.getAction();
        if (SWBResourceURL.Action_EDIT.equals(action)) {
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
        String basePath=SWBPlatform.getContextPath() +"/work/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/";
        String jspResponse = basePath+"socialSites.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /*
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        if (request.getParameter("doView") == null) {
            doEdit(request, response, paramRequest);
        } else {
            final String myPath = SWBPlatform.getContextPath() + "/work/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/msgReviewContainer.jsp";
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

    }*/

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.getWriter().println("<iframe dojotype=\"dijit.layout.ContentPane\" src=\"" + paramRequest.getRenderUrl().setMode(Mode_PRINCIPAL).setParameter("doView", "doView").setParameter("socialSite", request.getParameter("socialSite")) + "\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" >"); 
        response.getWriter().println("</iframe>");
    }

    private void doJson(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        User user=paramsRequest.getUser();
        response.setContentType("text/json;charset=iso-8859-1");

        Locale locale = new Locale("es", "mx");
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
        int pos = wsiteId.indexOf("?");
        if (pos > -1) {
            wsiteId = wsiteId.substring(0, pos);
        }
        WebSite wsite = null;
        List<PostIn> posts = null;
        int size = 0;
        if (wsiteId != null) {
            
            wsite = WebSite.ClassMgr.getWebSite(wsiteId);
            /*
            Iterator<PostIn> itposts = PostIn.ClassMgr.listPostIns(wsite);
            posts = SWBUtils.Collections.copyIterator(itposts);
            size = posts.size();
            */
            Iterator <UserGroupRef> itUserGroupRef=UserGroupRef.ClassMgr.listUserGroupRefs(wsite);
            while(itUserGroupRef.hasNext())
            {
                UserGroupRef userGroupRef=itUserGroupRef.next(); 
                if(userGroupRef.getUserGroup().hasUser(user))
                {
                    Iterator <SocialTopic> itSocialTopics=SocialTopic.ClassMgr.listSocialTopicByUserGroupRef(userGroupRef);
                    while(itSocialTopics.hasNext())
                    {
                        SocialTopic socialTopic=itSocialTopics.next();
                        //Extraigo cuales son los mensajes de un SocialTopic
                        Iterator<PostIn> itPostIns=PostIn.ClassMgr.listPostInBySocialTopic(socialTopic);
                        posts = SWBUtils.Collections.copyIterator(itPostIns);
                        size = posts.size(); 
                    }
                }
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
        //System.out.println("INICIO en java: " +  inicio);
        //System.out.println("FIN en java: " + fin);
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
                    // try {
                    //        obj.put("sn",msg.getPostInSocialNetwork().getClass().getSimpleName());
                    //     }catch(Exception e) {
                    //        obj.put("sn","--");
                    //     }
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
                    obj.put("plc", msg.getPostPlace() == null ? "--" : msg.getPostPlace());
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
}
