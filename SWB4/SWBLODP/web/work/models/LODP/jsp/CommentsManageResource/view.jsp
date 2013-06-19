<%-- 
Document   : view
Created on : 14/05/2013, 07:26:08 PM
Author     : rene.jara
--%>
<%@page import="com.infotec.lodp.swb.resources.CommentsManageResource"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.infotec.lodp.swb.Developer"%>
<%@page import="com.infotec.lodp.swb.Comment"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.platform.SemanticOntology"%>
<%@page import="com.infotec.lodp.swb.Application"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" /><html>
    <%
                WebPage wpage = paramRequest.getWebPage();
                WebSite wsite = wpage.getWebSite();
                User usr = paramRequest.getUser();
                String contextPath = SWBPlatform.getContextPath();
                String context = SWBPortal.getContextPath();
                Resource base = paramRequest.getResourceBase();
                String repositoryId = wpage.getWebSite().getUserRepository().getId();

                SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
                SemanticObject sobj = null;

                GenericObject gobj = null;
                Iterator<Comment> itco = Comment.ClassMgr.listComments(wsite);
                if (request.getParameter("msg") != null) {
                    String strMsg = request.getParameter("msg");
    %>
    <div>
        <%=strMsg%>
    </div>
    <%
                }
                String name = "";
                String email = "";
                gobj = usr.getSemanticObject().getGenericInstance();
                if (gobj instanceof Developer) {
                    Developer de = (Developer) gobj;
                    name = de.getFullName();
                    email = de.getEmail();
                }
                if (itco != null) {
    %>
    <div>
        <%
            ArrayList<Comment> alco = CommentsManageResource.listComments(Comment.ClassMgr.listComments(wsite));
//            ArrayList<Comment> alco = CommentsManageResource.listCommentsByDatasetApplication(wsite);
            int tRec = alco.size();
            int nPag = 0;
            try {
                nPag = Integer.parseInt(request.getParameter("npag"));
            } catch (NumberFormatException ignored) {
                nPag = 0;
            }
            int nRecPPag = 0;
            try {
                nRecPPag = Integer.parseInt(base.getAttribute("num_comments_p_page", "5"));
            } catch (NumberFormatException ignored) {
                nRecPPag = 5;
            }
            int tPag = (int) (tRec / nRecPPag);
            if ((tRec % nRecPPag) > 0) {
                tPag++;
            }
            if(nPag>(tPag-1)&&tPag>0){
                nPag=tPag-1;
            }
            int iRec = ((nPag) * nRecPPag);
            int fRec = iRec + nRecPPag;

            SWBResourceURLImp urlapv = new SWBResourceURLImp(request, base, wpage, SWBResourceURLImp.UrlType_ACTION);
            urlapv.setAction(CommentsManageResource.Action_APPROVE);
            SWBResourceURLImp urlrvw = new SWBResourceURLImp(request, base, wpage, SWBResourceURLImp.UrlType_ACTION);
            urlrvw.setAction(CommentsManageResource.Action_REVIEWED);
            if(tRec==0){
            %>
            <div class="comentario"><%=paramRequest.getLocaleString("lblNoComments")%>
            </div>
            <%
            }
            for (int x = iRec; x < fRec && x < tRec; x++) {
                Comment co = alco.get(x);
                urlapv.setParameter("cid", co.getId());
                urlapv.setParameter("npag", nPag + "");
                urlrvw.setParameter("cid", co.getId());
                urlrvw.setParameter("npag", nPag + "");
        %>
        <div class="comentario">
            <p class="comentador"><strong><%=co.getCommUserName()%></strong><em><%=co.getCommUserEmail()%></em></p>
            <p class="comentariotxt"><%=co.getComment()%></p>
            <p class="inapropiado">
                <a href="<%=urlapv%>" title="<%=paramRequest.getLocaleString("lblApproved")%>"><span><%=paramRequest.getLocaleString("lblCommentApproved")%></span></a>
                <a href="<%=urlrvw%>" title="<%=paramRequest.getLocaleString("lblReviewed")%>"><span><%=paramRequest.getLocaleString("lblCommentReviewed")%></span></a>
            </p>
        </div>
        <%
            }
        %>
        <div class="paginacom">
            <ul>
                <%
                    SWBResourceURL rurl = paramRequest.getRenderUrl();
                    for (int x = 0; x < tPag; x++) {
                        if (x == nPag) {
                %>
                <li><span><%=(x + 1)%></span></li>
                <%
                } else {
                    rurl.setParameter("npag", x + "");
                %>
                <li><a href="<%=rurl.toString()%>"><%=(x + 1)%></a></li>
                <%
                        }
                    }
                %>
            </ul>
        </div>
    </div>
    <%
                }
    %>