<%@page import="org.semanticwb.SWBPortal" %><%

    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");

    SWBPortal.UTIL p = new SWBPortal.UTIL();
    p.sendValidateImage(request, response, "cs", 7,  null);
%>