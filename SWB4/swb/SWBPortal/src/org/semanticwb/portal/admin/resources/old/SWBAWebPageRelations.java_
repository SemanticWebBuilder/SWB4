/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.portal.api.*;



/**
 *
 * @author juan.fernandez
 */
public class SWBAWebPageRelations extends GenericResource {
    
    private Logger log = SWBUtils.getLogger(SWBAWebPageRelations.class);
    String imgPath =SWBPlatform.getContextPath()+"wbadmin/";
    
    /** Creates a new instance of SWBAWebPageRelations */
    public SWBAWebPageRelations() {
    }

//    /**
//     * Muestra el html final del recurso
//     */    
//    @Override
//    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        PrintWriter out=response.getWriter();
//        //TopicSrv tpsrv=new TopicSrv();
//        WebPage tp=paramRequest.getTopic();
//        if(request.getParameter("tm")!=null && request.getParameter("tp")!=null) {
//            WebSite tm=SWBContext.getWebSite(request.getParameter("tm"));
//            if(tm!=null) {
//                if(request.getParameter("ntp")!=null) {
//                    tp=tm.getWebPage(request.getParameter("ntp"));
//                }else {
//                    tp=tm.getWebPage(request.getParameter("tp"));
//                }
//            }
//        }
//        WebSite tm=tp.getWebSite();
//        User user=paramRequest.getUser();
//        String acc=request.getParameter("act");
//        String tree=request.getParameter("tree");
//        if(tree!=null && tree.equals("reload")) {
//            out.println("<script>");
//            out.println("wbTree_executeAction('gotonode.topic."+request.getParameter("tm")+"."+request.getParameter("tp")+"');");
//            out.println("wbTree_reload();");
//            if(request.getParameter("ntp")!=null) {
//                out.println("wbTree_executeAction('gotonode.topic."+request.getParameter("tm")+"."+request.getParameter("ntp")+"');");
//            }
//            out.println("</script>");
//        }
//        SWBResourceURL wbresurl=paramRequest.getRenderUrl();
//        wbresurl.setMode(wbresurl.Mode_VIEW);
//        wbresurl.setAction("view");
//        out.println("<p  class=\"box\"><TABLE width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
//        out.println("<tr class=\"datos\">");
//        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("typerelation")+"</td>");
//        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("rolrelation")+"</td>");
//        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("relatedto")+"</td>");
//        out.println("<td class=\"tabla\">"+paramRequest.getLocaleString("rolrelated")+"</td>");
//        out.println("</tr>");
//        try {
//            //Revisar posiblemente reemplazar por RelatedObjects()
//            Iterator it = tp.listRelatedObjects();//tp.getAssociations().iterator();
//            while (it.hasNext()) {
//                Association ass = (Association) it.next();
//                WebPageAssociations topicAssociations = new WebPageAssociations(tp, ass);
//                //TopicAssociations topicAssociations=new TopicAssociations(tp,ass);
//                out.println("<tr>");
//                out.println("<td class=valores>"+ass.getType().getDisplayName()+"</td>");
//                out.println("<td class=valores>"+topicAssociations.getTopicSectionRol()+"</td>");
//                out.println("<td class=valores>"+topicAssociations.getTopicRelatedTo()+"</td>");
//                out.println("<td class=valores>"+topicAssociations.getTopicRolRelated()+"</td>");
//                out.println("</tr>");
//            }
//            
//        }catch(Exception e){
//            log.error("Error while getting assotiations of topic width id:"+tp.getId(),e);
//        }
//        //termina
//        out.println("</TABLE></p>");
//    }
//    
//
//    /**
//     * Administración del recurso
//     */    
//    @Override
//    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        PrintWriter out=response.getWriter();
//        out.println("This resource does not need to be administrated...");
//    }
//    
//}
//class WebPageAssociations {
//    
//    WebPage tp=null;
//    Association ass=null;
//    WebPage tpsecrol=null;
//    WebPage tprelto=null;
//    WebPage tprolrelated=null;
//    
//    public WebPageAssociations(WebPage tp,Association ass){
//        this.tp=tp;
//        this.ass=ass;
//        init();
//    }
//    
//    /**
//     * Inicializa el recurso
//     */    
//    private void init(){
//            Iterator it2 = ass.getMembers().iterator();
//            String relRol="";
//            String relto="";
//            while (it2.hasNext()) {
//                Member mem = (Member) it2.next();
//                Iterator ittref=mem.getTopicRefs().keySet().iterator();
//                while(ittref.hasNext()){
//                    String tptrefs=(String)ittref.next();
//                    if(tptrefs.equals(tp.getId())) {
//                        tpsecrol=mem.getRoleSpec().getTopicRef();
//                    }
//                    else {
//                        tprelto=tp.getMap().getTopic(tptrefs);
//                        tprolrelated=mem.getRoleSpec().getTopicRef();
//                    }
//                }
//            }
//    }
//    
//    public WebPage getTopicSectionRol(){
//        return tpsecrol;
//    }
//
//    public WebPage getTopicRelatedTo(){
//        return tprelto;
//    }
//
//    public WebPage getTopicRolRelated(){
//        return tprolrelated;
//    }
}
