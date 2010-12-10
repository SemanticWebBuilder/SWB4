<%@page import="org.semanticwb.portal.api.SWBResourceURLImp"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.resources.sem.forumcat.QuestionSubscription"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>

<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
User user = paramRequest.getUser();
WebPage wp = paramRequest.getWebPage();
ArrayList<QuestionSubscription> questions = (ArrayList<QuestionSubscription>) request.getAttribute("listSubscriptions");
if (user.isSigned()) {
    %><h1>Mis Suscripciones</h1><%
    if (questions != null && !questions.isEmpty()) {
        Iterator<QuestionSubscription> it_q = questions.iterator();
        %>
        <ul>
        <%
        while (it_q.hasNext()) {
            QuestionSubscription qs = it_q.next();
            SWBResourceURL delUrl = paramRequest.getActionUrl().setAction("deleteSubscription").setParameter("uri", qs.getEncodedURI());
            SWBResourceURL url = new SWBResourceURLImp(request, qs.getQuestionObj().getForumResource().getResource(), wp, SWBResourceURL.UrlType_RENDER);
            String name = qs.getQuestionObj().getQuestion();
            url.setAction("showDetail");
            url.setParameter("uri", qs.getQuestionObj().getEncodedURI());
            %>
            <li>
                <a href="<%=url%>"><%=name%></a>[<a href="<%=delUrl%>">Eliminar</a>]
            </li>
            <%
        }
        %>
        </ul>
    <%
    } else {
        %>No tiene suscripciones<%
    }
}
%>