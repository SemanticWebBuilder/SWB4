/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.forum.FrmCategory;
import org.semanticwb.forum.FrmForum;
import org.semanticwb.forum.FrmPost;
import org.semanticwb.forum.FrmThread;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.resources.Banner;

/**
 *
 * @author jorge.jimenez
 */
public class SWBForum extends GenericResource {

    private static Logger log = SWBUtils.getLogger(Banner.class);

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("addCategory")) {
            doAddCategory(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("addForum")) {
            doAddForum(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("editForum")) {
            doEditForum(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("addThread")) {
            doAddThread(request, response, paramRequest);
        }else if (paramRequest.getMode().equals("replyPost")) {
            doReplyPost(request, response, paramRequest);
        }else if (paramRequest.getMode().equals("editPost")) {
            doEditPost(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String action=paramRequest.getAction();
        try{
            System.out.println("doView/action:"+action);
            if(action!=null){request.setAttribute("action", action);}
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd=request.getRequestDispatcher("/forum/swbForum.jsp");
            rd.include(request, response);

            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setMode(url.Mode_ADMIN);
            out.println("<a href=\""+url.toString()+"\">administrar foros</a>");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try{
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd=request.getRequestDispatcher("/forum/swbForumAdm.jsp");
            rd.forward(request, response);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void doAddCategory(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        SWBFormMgr mgr=new SWBFormMgr(FrmCategory.frm_FrmCategory, paramRequest.getTopic().getSemanticObject(), null);
        mgr.setLang("es");
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url=paramRequest.getActionUrl();
        url.setAction("addCategory");
        mgr.setAction(url.toString());
        out.println(mgr.renderForm());
    }

    public void doAddForum(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        SemanticObject semObject=paramRequest.getTopic().getSemanticObject();
        String action = paramRequest.getAction();
        if (action.equals("newCatForum")) {
            semObject=SemanticObject.createSemanticObject(request.getParameter("caturi"));
        }
        SWBFormMgr mgr = new SWBFormMgr(FrmForum.frm_FrmForum, semObject, null);
        mgr.setLang("es");
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url=paramRequest.getActionUrl();
        url.setAction("addForum");
        mgr.setAction(url.toString());
        out.println(mgr.renderForm());
    }

    public void doEditForum(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        System.out.println("doEditForum Uri:"+request.getParameter("forumUri"));
        SemanticObject semObject=SemanticObject.createSemanticObject(request.getParameter("forumUri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        mgr.setLang("es");
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url=paramRequest.getActionUrl();
        url.setParameter("forumUri", semObject.getURI());
        url.setAction("editForum");
        mgr.setAction(url.toString());
        out.println(mgr.renderForm());
    }

    public void doAddThread(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        SemanticObject semObject=SemanticObject.createSemanticObject(request.getParameter("forumUri"));
        SWBFormMgr mgr = new SWBFormMgr(FrmThread.frm_FrmThread, semObject, null);
        mgr.setLang("es");
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url=paramRequest.getActionUrl();
        url.setAction("addThread");
        url.setParameter("forumUri", request.getParameter("forumUri"));
        mgr.setAction(url.toString());
        String path=SWBPlatform.getContextPath()+"/forum/images/emotion/";
        out.println("<table class=\"noborder\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">");
//        out.println("<tr>");
//        out.println("  <td colspan=\"3\" align=\"center\" bgcolor=\"#F5F5F5\" style=\"BORDER-RIGHT: 1px inset; BORDER-TOP: 1px inset; BORDER-LEFT: 1px inset; BORDER-BOTTOM: 1px inset\">");
//        out.println("    <span class=\"messageTextBold\">Smilies</span>");
//        out.println("  </td>");
//        out.println("</tr>");
        out.println("<tr align=\"center\">");
        out.println("  <td><a href=\"javascript:smilie('[:)]');\"><img src=\""+path+"smile.gif\" alt=\"smile\" border=\"0\" /></a>&nbsp;</td>");
        out.println("  <td><a href=\"javascript:smilie('[:(]');\"><img src=\""+path+"sad.gif\" alt=\"sad\" border=\"0\" /></a>&nbsp;</td>");
        out.println("  <td><a href=\"javascript:smilie('[:D]');\"><img src=\""+path+"biggrin.gif\" alt=\"big grin\" border=\"0\" /></a>&nbsp;</td>");
        out.println("</tr>");
        out.println("<tr align=\"center\">");
        out.println("  <td><a href=\"javascript:smilie('[:))]');\"><img src=\""+path+"laughing.gif\" alt=\"laughing\" border=\"0\" /></a>&nbsp;</td>");
        out.println("  <td><a href=\"javascript:smilie('[:((]');\"><img src=\""+path+"crying.gif\" alt=\"crying\" border=\"0\" /></a>&nbsp;</td>");
        out.println("  <td><a href=\"javascript:smilie('[;)]');\"><img src=\""+path+"wink.gif\" alt=\"wink\" border=\"0\" /></a>&nbsp;</td>");
        out.println("</tr>");
        out.println("<tr align=\"center\">");
        out.println("  <td><a href=\"javascript:smilie('[:&quot;&gt;]');\"><img src=\""+path+"blushing.gif\" alt=\"blushing\" border=\"0\" /></a>&nbsp;</td>");
        out.println("  <td><a href=\"javascript:smilie('[:p]');\"><img src=\""+path+"tongue.gif\" alt=\"tongue\" border=\"0\" /></a>&nbsp;</td>");
        out.println("  <td><a href=\"javascript:smilie('[B-)]');\"><img src=\""+path+"cool.gif\" alt=\"cool\" border=\"0\" /></a>&nbsp;</td>");
        out.println(" </tr>");
        out.println("<tr align=\"center\">");
        out.println("  <td><a href=\"javascript:smilie('[:x]');\"><img src=\""+path+"love.gif\" alt=\"love struck\" border=\"0\" /></a>&nbsp;</td>");
        out.println("  <td><a href=\"javascript:smilie('[:-/]');\"><img src=\""+path+"confused.gif\" alt=\"confused\" border=\"0\" /></a>&nbsp;</td>");
        out.println("  <td><a href=\"javascript:smilie('[&gt;:)]');\"><img src=\""+path+"devilish.gif\" alt=\"devilish\" border=\"0\" /></a>&nbsp;</td>");
        out.println("</tr>");

//        out.println("<tr>");
//        out.println("  <td colspan=\"3\" align=\"center\" bgcolor=\"#F5F5F5\" style=\"BORDER-RIGHT: 1px inset; BORDER-TOP: 1px inset; BORDER-LEFT: 1px inset; BORDER-BOTTOM: 1px inset\">");
//        out.println("    <span class=\"portlet-font\">Showing: 12 of 60</span>");
//        out.println("    <table>");
//        out.println("      <tr id=\"showlink\">");
//        out.println("        <td align=\"center\"><a href=\"javascript:showMoreEmotion()\" class=\"messageTextBold\">Show More</a></td>");
//        out.println("      </tr>");
//        out.println("      <tr id=\"hidelink\" style=\"display: none;\">");
//        out.println("        <td align=\"center\"><a href=\"javascript:hideMoreEmotion()\" class=\"messageTextBold\">Hide More</a></td>");
//        out.println("      </tr>");
//        out.println("    </table>");
//        out.println("  </td>");
//        out.println("</tr>");

        out.println("</table>");

        out.println(mgr.renderForm());
    }

    public void doReplyPost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        System.out.println("Entra a doReplyPost");
        PrintWriter out=response.getWriter();
        SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
        SemanticObject soPost=null;
        if(request.getParameter("postUri")!=null){
            soPost = SemanticObject.createSemanticObject(request.getParameter("postUri"));
        }
        SWBFormMgr mgr=null;
        if(soPost!=null) {
            mgr = new SWBFormMgr(FrmPost.frm_FrmPost, soPost, null);
        }else{
             mgr = new SWBFormMgr(FrmPost.frm_FrmPost, soThread, null);
        }
        SWBResourceURL url=paramRequest.getActionUrl();
        url.setParameter("threadUri", request.getParameter("threadUri"));
        url.setParameter("postUri", request.getParameter("postUri"));

        mgr.setLang("es");
        mgr.setType(mgr.TYPE_XHTML);
        url.setAction("replyPost");
        mgr.setAction(url.toString());
        out.println(mgr.renderForm());
    }

    public void doEditPost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        System.out.println("doPost Uri:"+request.getParameter("postUri"));
        SemanticObject semObject=SemanticObject.createSemanticObject(request.getParameter("postUri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        mgr.setLang("es");
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url=paramRequest.getActionUrl();
        url.setParameter("threadUri", request.getParameter("threadUri"));
        url.setParameter("postUri", semObject.getURI());
        url.setAction("editPost");
        mgr.setAction(url.toString());
        out.println(mgr.renderForm());
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Enumeration enP=request.getParameterNames();
        while(enP.hasMoreElements()){
            String paramN=(String)enP.nextElement();
            String paramV=request.getParameter(paramN);
            System.out.println("paramN:"+paramN+",paramV:"+paramV);
        }
        String action=response.getAction();
        String scls=request.getParameter("scls");
        if(action.equals("addCategory")){
            SWBFormMgr mgr=new SWBFormMgr(FrmCategory.frm_FrmCategory, response.getTopic().getSemanticObject(), null);
            mgr.processForm(request);
        }else if(action.equals("addForum")){
            System.out.println("entra a addForum");
            SWBFormMgr mgr=new SWBFormMgr(FrmForum.frm_FrmForum, response.getTopic().getSemanticObject(), null);
            SemanticObject semObj=mgr.processForm(request);
            response.setMode("editForum");
            response.setRenderParameter("forumUri", semObj.getURI());
        }else if(action.equals("editForum")){
            System.out.println("entra a editForum:"+request.getParameter("forumUri"));
            SemanticObject semObject=SemanticObject.createSemanticObject(request.getParameter("forumUri"));
            SWBFormMgr mgr=new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
            mgr.processForm(request);
            response.setMode(response.Mode_ADMIN);
        }else if(action.equals("addThread")){
            SemanticObject semObject=SemanticObject.createSemanticObject(request.getParameter("forumUri"));
            FrmForum forum = FrmForum.getFrmForum(semObject.getId(), response.getTopic().getWebSite());
            SWBFormMgr mgr=new SWBFormMgr(FrmThread.frm_FrmThread, response.getTopic().getSemanticObject(), null);
            SemanticObject semObj=mgr.processForm(request);
            FrmThread thread = FrmThread.getFrmThread(semObj.getId(), response.getTopic().getWebSite());
            thread.setForum(forum);
            response.setMode(response.Mode_VIEW);
            response.setAction("viewThreads");
            response.setRenderParameter("forumUri", forum.getURI());
        }else if(action.equals("replyPost")){
            SemanticObject soThread=SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            FrmThread thread = FrmThread.getFrmThread(soThread.getId(), response.getTopic().getWebSite());

            SemanticObject soPost=null;
            FrmPost post=null;
            if(request.getParameter("postUri")!=null){
                soPost=SemanticObject.createSemanticObject(request.getParameter("postUri"));
                post = FrmPost.getFrmPost(soPost.getId(), response.getTopic().getWebSite());
            }

            SWBFormMgr mgr=new SWBFormMgr(FrmPost.frm_FrmPost, response.getTopic().getSemanticObject(), null);
            SemanticObject semObj=mgr.processForm(request);
            FrmPost newPost = FrmPost.getFrmPost(semObj.getId(), response.getTopic().getWebSite());
            newPost.setForum(thread.getForum());
            newPost.setThread(thread);
            if(post!=null){
                newPost.setParentPost(post);
            }
            response.setMode(response.Mode_VIEW);
            response.setAction("viewPost");
            response.setRenderParameter("threadUri", thread.getURI());
        }else if(action.equals("editPost")){
            System.out.println("entra a editPost:"+request.getParameter("postUri"));
            SemanticObject semObject=SemanticObject.createSemanticObject(request.getParameter("postUri"));
            SWBFormMgr mgr=new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
            mgr.processForm(request);
            response.setMode(response.Mode_VIEW);
            System.out.println("threadUri:"+request.getParameter("threadUri"));
            response.setRenderParameter("threadUri", request.getParameter("threadUri"));
            response.setAction("viewPost");
        }
    }
}
