<%-- 
    Document   : view
    Created on : 24/05/2013, 01:05:00 PM
    Author     : rene.jara
--%>
<%@page import="org.semanticwb.base.util.URLEncoder"%>
<%@page import="com.infotec.lodp.swb.resources.RankingResource"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURLImp"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" /><html>
<%
    WebPage wpage = paramRequest.getWebPage();
    Resource base =paramRequest.getResourceBase();
    String suri=request.getParameter("suri");
  //              if(suri==null)
   //                 suri = "http://www.LODP.swb#lodpcg_Dataset:3";
               //    uri"http://www.LODP.swb#lodpcg_Application:2";

   SWBResourceURLImp urlrank = new SWBResourceURLImp(request, base, wpage, paramRequest.UrlType_ACTION);
   urlrank.setCallMethod(paramRequest.Call_DIRECT);
   urlrank.setMode(RankingResource.Mode_AJAX);
   urlrank.setAction(RankingResource.Action_RANK);
   if(suri!=null&&!suri.equals("")){
%>
<script type="text/javascript">
    //<!--
    function rank(value){
        getHtml("<%=urlrank%>?suri=<%=URLEncoder.encode(suri)+"&rank="%>"+value, "ranking", false, false);
    }
   // -->
</script>
<div id="ranking">
    <ul>
        <li><a href="" onclick="javascript:rank('1'); return false;">1</a></li>
        <li><a href="" onclick="javascript:rank('2'); return false;">2</a></li>
        <li><a href="" onclick="javascript:rank('3'); return false;">3</a></li>
        <li><a href="" onclick="javascript:rank('4'); return false;">4</a></li>
        <li><a href="" onclick="javascript:rank('5'); return false;">5</a></li>
    </ul>
</div>
<%
}
   %>