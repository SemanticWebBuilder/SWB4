<%-- 
    Document   : view
    Created on : 28/05/2013, 10:55:19 AM
    Author     : Lennin
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="com.infotec.lodp.swb.resources.ApplicationResource"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.infotec.lodp.swb.Application"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.Resource"%> 

<!DOCTYPE html>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />

<%

    WebPage wpage = paramRequest.getWebPage();
    WebSite wsite = wpage.getWebSite();
    Resource base = paramRequest.getResourceBase();
    String appWP = base.getAttribute("appid", "Aplicaciones");
     SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMMMM-yyyy", new Locale("es"));

    long intSize = 0;

    ;
    String strNumNewAPP = base.getAttribute("numapp", "3");
    String orderby = request.getParameter("order");
    String action = request.getParameter("act");

    int numAPP = 3;
    try {
        numAPP = Integer.parseInt(strNumNewAPP);
    } catch (Exception e) {
        numAPP = 3;
    }
    if (orderby == null) {
        orderby = "date";
    }
    if (action == null) {
        action = "";
    }

        // llamada como estrategia
        // mostrar los más nuevos

        // ordenamiento orderby y filtrado de DataSets
        Iterator<Application> itapp1 = Application.ClassMgr.listApplications(wsite);

        // dejo en hm las app
        HashMap<String, Application> hmcp = new HashMap<String, Application>();
        while (itapp1.hasNext()) {
            Application app = itapp1.next();
            if(app.isApproved() && app.isReviewed()){
             hmcp.put(app.getURI(), app);
            }
        }

        Iterator<Application> itapp = null;
        if (hmcp.size() > 0) {
            itapp = ApplicationResource.orderDS(hmcp.values().iterator(), orderby);
            intSize = hmcp.size();
        } else {
            intSize = 0;
        }

        //num elementos a mostrar
        long l = numAPP;
        int x = 0;
%>
<div>
    <ul>
        <%
            if (intSize == 0) {
        %>
        <li><p><%=paramRequest.getLocaleString("lbl_notAPPFound")%></p></li>
                <%  
            } else {
                
                String wpurl = wsite.getWebPage(appWP).getUrl() + "?act=detail&suri=";
                while (itapp.hasNext()) {

                    //NUMERO DE LEMENTOS A MOSTRAR ////////////////////
                    if (x == l) {
                        break;
                    }
                    x++;
                    /////////////////////////////////

                    Application app = itapp.next();                
                %>
        
        <li>
            <a title="<%=app.getAppTitle()%>" href="<%=wpurl + app.getShortURI()%>"><%=app.getAppTitle()%></a>
            <strong><%=app.getAppDescription()%></strong>
            <%=sdf2.format(app.getAppCreated())%>
        </li>
        
        <%}}%>
    </ul>
</div>

