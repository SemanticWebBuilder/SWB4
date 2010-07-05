<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="java.util.Iterator,org.semanticwb.model.WebPage,org.semanticwb.model.User,java.util.*" %><%
       User user=paramRequest.getUser();
       ArrayList webPage=new ArrayList();
       ArrayList proPage=new ArrayList();
       Iterator<WebPage> childPag=paramRequest.getWebPage().listVisibleChilds(paramRequest.getUser().getLanguage());
       while(childPag.hasNext())
        {
            WebPage page1=childPag.next();
            String namewp=page1.getSemanticObject().getSemanticClass().getName();
            if(namewp.equals("WebPage"))
               webPage.add(page1);
            else if(namewp.equals("Project"))
               proPage.add(page1);
        }
       if(!proPage.isEmpty())
        out.println(printPage(proPage,paramRequest.getLocaleString("projects")));
       if(!webPage.isEmpty())
        out.println(printPage(webPage,paramRequest.getLocaleString("titleSections")));
%><%!
    private String printPage(ArrayList mpag, String title)
    {
        Iterator itpr=mpag.iterator();
        StringBuffer strb = new StringBuffer();
        WebPage wpage;
        strb.append("");
        if(itpr.hasNext())
        {
            strb.append("                        <h3>"+title+"</h3>\n");
            strb.append("                           <ul>\n");
            while(itpr.hasNext()){
                wpage=(WebPage)itpr.next();
                strb.append("                              <li>\n                                 <a href=\""+wpage.getUrl()+"\">"+wpage.getDisplayName()+"</a>\n                              </li>\n");
            }
            strb.append("                           </ul>");
        }
        return strb.toString();
    }
%>