<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />
<%@page import="javax.xml.parsers.*
        ,java.awt.*
        ,java.io.*
        ,java.util.*
        ,java.util.jar.*
        ,org.xml.sax.*
        ,org.semanticwb.portal.admin.resources.reports.*
        ,org.semanticwb.portal.admin.resources.reports.beans.*
        ,org.semanticwb.portal.admin.resources.reports.jrresources.*
        ,org.semanticwb.portal.admin.resources.reports.beans.IncompleteFilterException
        ,org.semanticwb.portal.admin.resources.reports.jrresources.*
        ,org.semanticwb.portal.admin.resources.reports.jrresources.data.JRSectionAccessDataDetail
        ,org.semanticwb.SWBUtils
        ,org.semanticwb.SWBPortal
        " %>
<%=DocumentBuilder.class.getPackage().getImplementationTitle() %><br>
<%=DocumentBuilder.class.getPackage().getName() %><br>
<%=DocumentBuilder.class.getPackage().getSpecificationVersion()%> <br>
<%=DocumentBuilder.class.getPackage().getImplementationVersion() %>


<%
System.out.println("\n\n");

GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
System.out.println("Headless mode: " + ge.isHeadless());
if(!ge.isHeadless()){
    System.setProperty("java.awt.headless", "true");
}


WBAFilterReportBean filter = new WBAFilterReportBean(paramRequest.getUser().getLanguage());
filter.setSite(paramRequest.getWebPage().getWebSiteId());
filter.setIdaux("Los_doctos");
filter.setType(3);

filter.setGroupedDates(false);
filter.setYearI(2010);
filter.setMonthI(9);
filter.setDayI(2);

System.out.println("1");
JRDataSourceable dataDetail = new JRSectionAccessDataDetail(filter);
System.out.println("2");
JasperTemplate jasperTemplate = JasperTemplate.SECTION_DAILY_HTML;
System.out.println("3");
HashMap<String,String> params = new HashMap();
System.out.println("4");
params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
System.out.println("5");
params.put("site", filter.getSite());
System.out.println("6");
try {
    JRResource jrResource = new JRHtmlResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
    System.out.println("7");
    jrResource.prepareReport();
    System.out.println("8");
    jrResource.exportReport(response);
    System.out.println("9");
}catch (Exception e) {
    System.out.println("\n\nError 1...........................\n"+e);
}


try{
    System.out.println("10");
    DocumentBuilder documentBuilder;
    System.out.println("11");
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    System.out.println("12");
    documentBuilder = factory.newDocumentBuilder();
    System.out.println("13");
}catch(Exception e) {
    System.out.println("\n\nError 2...........................\n"+e);
}%>