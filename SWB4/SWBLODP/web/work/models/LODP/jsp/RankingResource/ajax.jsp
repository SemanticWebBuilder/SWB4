<%-- 
    Document   : ajax
    Created on : 24/05/2013, 01:05:13 PM
    Author     : rene.jara
--%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" /><%
    String rank=request.getParameter("rank");
    int average=0;
    try{
        average=Integer.parseInt(rank);
    }catch(NumberFormatException ignored){
        average=0;
    }
    if(rank!=null){
%><img src="/work/models/LODP/css/images/star-<%=average >= 1?"on":"off"%>.png" width="15" height="14" alt="*">
<img src="/work/models/LODP/css/images/star-<%=average >= 2?"on":"off"%>.png" width="15" height="14" alt="*">
<img src="/work/models/LODP/css/images/star-<%=average >= 3?"on":"off"%>.png" width="15" height="14" alt="*">
<img src="/work/models/LODP/css/images/star-<%=average >= 4?"on":"off"%>.png" width="15" height="14" alt="*">
<img src="/work/models/LODP/css/images/star-<%=average >= 5?"on":"off"%>.png" width="15" height="14" alt="*"><%}%>