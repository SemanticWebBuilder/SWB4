<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.social.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

<%
    WebPage wpage=paramRequest.getWebPage();
    String words2Monitor=SWBSocialUtil.words2Monitor.getWords2Monitor(",", wpage.getWebSite());
    out.println("words2Monitor:"+words2Monitor);
%>