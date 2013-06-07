<%-- 
    Document   : CarouselDataSets
    Created on : 20/05/2013, 01:31:50 PM
    Author     : Sabino Pariente
--%>
<%@page import="java.util.HashMap"%>
<%@page import="com.infotec.lodp.swb.resources.DataSetResource"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="com.infotec.lodp.swb.Institution"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.model.Resource"%>
<header>
    <script type="text/javascript" src="/work/models/LODP/jsp/CarouselDataSets/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/work/models/LODP/jsp/CarouselDataSets/js/jquery.bxslider.js"></script>
    <script type="text/javascript" src="/work/models/LODP/jsp/CarouselDataSets/js/jquery.bxslider.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/work/models/LODP/jsp/CarouselDataSets/js/jquery.bxslider.css" />
    <script>
        $(document).ready(function() {
            $('.slider4').bxSlider({
                slideWidth: 220,
                minSlides: 2,
                maxSlides: 3,
                moveSlides: 1,
                slideMargin: 10
            });
        });
    </script>
    <jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />
    <%        
        WebPage wpage = paramRequest.getWebPage();
        WebSite wsite = wpage.getWebSite();       
        Iterator<Dataset> listDataset= Dataset.ClassMgr.listDatasets(wsite);        
        boolean intSize = false;
        Iterator<Dataset> listDatasetOrd  = null;
        if(listDataset.hasNext()){
            listDatasetOrd = DataSetResource.orderDS(listDataset, DataSetResource.ORDER_VIEW);  
            intSize = true;
        } else {
            intSize = false;
        }      
        Resource base = paramRequest.getResourceBase();
        String strNumItems = base.getAttribute("numds");
        int maxDS = 10;
        try {
            maxDS = Integer.parseInt(strNumItems);
        } catch (Exception e) {
            maxDS = 10;
        }        
        int count = 0 ;
    %>
</header> 
<body>        
    <div  class="slider4">       
        <%if(intSize){
            while(listDatasetOrd.hasNext() && count < maxDS){
                Dataset dataset = listDatasetOrd.next();
                Institution institution = dataset.getInstitution();
                String urlBase = "/work"+institution.getWorkPath();
                String nameLogo= "institutionLogo_"+institution.getId()+"_"+institution.getInstitutionLogo();
                String urlLogo = urlBase + "/"+nameLogo;
                String urlData = wsite.getWebPage("Datos").getUrl();
                String urlDataSet = urlData+"?suri="+dataset.getShortURI()+"&act=detail";
        %>
        <div class="slide">
            <a href="<%=urlDataSet%>">
                <img src="<%=urlLogo%>" alt="<%=urlLogo%>"  width="200" height="150" />
                <div>Instituci&oacute;n: <%=institution.getInstitutionTitle()%></div>
                <div>DataSet: <%=dataset.getDatasetTitle()%></div>
                <div>N&uacute;mero de vistas: <%=dataset.getViews()%></div>
            </a>
        </div>
        <%
                count++;
            }            
        }else{%>
            Información no disponible
        <%
        }
        %>
    </div>
</body>