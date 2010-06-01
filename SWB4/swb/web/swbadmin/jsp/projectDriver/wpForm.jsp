
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="java.util.Iterator" %>
<%@page import="org.semanticwb.model.WebPage" %>
<%@page import="org.semanticwb.model.User" %>
<%@page import="java.util.HashMap" %>

<%
       Iterator it;
       WebPage wp=paramRequest.getWebPage();
       User user=paramRequest.getUser();
       it=wp.listVisibleChilds(user.getLanguage());
       HashMap actPage=new HashMap();
       HashMap webPage=new HashMap();
       HashMap proPage=new HashMap();
       HashMap usPage=new HashMap();
       Iterator<WebPage> childPag;
       WebPage page1;
       String namewp="";
       childPag=paramRequest.getWebPage().listVisibleChilds(paramRequest.getUser().getLanguage());
       while(childPag.hasNext())
        {
            page1=childPag.next();
            namewp=page1.getSemanticObject().getSemanticClass().getName();
            if(namewp.equals("Activity"))
               actPage.put(page1, page1);
            else if(namewp.equals("WebPage"))
               webPage.put(page1, page1);
            else if(namewp.equals("Project"))
               proPage.put(page1, page1);
            else if(namewp.equals("UserWebPage"))
               usPage.put(page1, page1);
        }
%>
<%
       if(!proPage.isEmpty())
        out.println(printPage(proPage,"Proyectos"));
       if(!usPage.isEmpty())
        out.println(printPage(usPage,"Personal Asociado"));
       if(!webPage.isEmpty())
        out.println(printPage(webPage,"Secciones"));
%>
<%!
    private String printPage(HashMap mpag, String title)
    {
        Iterator itpr=mpag.values().iterator();
        StringBuffer strb = new StringBuffer();
        WebPage wpage;
        strb.append("");
        if(itpr.hasNext())
        {
            strb.append("<h3>"+title+"</h3>\n");
            strb.append("   <ul>\n");
            while(itpr.hasNext()){
                wpage=(WebPage)itpr.next();
                strb.append("      <li>\n         <a href=\""+wpage.getUrl()+"\">"+wpage.getDisplayName()+"</a>\n      </li>\n");
            }
            strb.append("   </ul>");
        }
        return strb.toString();
    }
%>