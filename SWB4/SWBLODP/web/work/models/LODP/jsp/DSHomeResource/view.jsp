<%--
    Document   : view Recurso DataHomeResource
    Created on : 22/05/2013
    Author     : juan.fernandez
--%>

<%@page import="com.infotec.lodp.swb.Developer"%>
<%@page import="com.infotec.lodp.swb.Publisher"%>
<%@page import="java.util.TreeSet"%>
<%@page import="com.infotec.lodp.swb.DatasetLog"%>
<%@page import="com.infotec.lodp.swb.DatasetVersion"%>
<%@page import="java.util.Date"%>
<%@page import="com.infotec.lodp.swb.Tag"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="com.infotec.lodp.swb.utils.LODPUtils"%>
<%@page import="com.infotec.lodp.swb.Application"%>
<%@page import="com.infotec.lodp.swb.Sector"%>
<%@page import="com.infotec.lodp.swb.Topic"%>
<%@page import="com.infotec.lodp.swb.Institution"%>
<%@page import="org.semanticwb.model.Ontology"%>
<%@page import="com.infotec.lodp.swb.resources.DataSetResource"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURLImp"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.model.GenericObject"%>
<%@page import="org.semanticwb.model.VersionInfo"%>
<%@page import="org.semanticwb.model.Versionable"%>
<%@page import="org.semanticwb.platform.SemanticOntology"%>
<%@page import="org.semanticwb.portal.SWBFormButton"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.Set"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.Role"%>
<%@page import="org.semanticwb.model.Resource"%> 
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.SWBUtils"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />

<%

    WebPage wpage = paramRequest.getWebPage();
    WebSite wsite = wpage.getWebSite();
    User usr = paramRequest.getUser();
    Resource base = paramRequest.getResourceBase();
    String roladmin = base.getAttribute("rolid", "");
    Role role = wsite.getUserRepository().getRole(roladmin);
    String datosWP = base.getAttribute("datosid", "Datos");

    long intSize = 0;

    String strNumItems = base.getAttribute("numpag", "10");
    String npage = request.getParameter("page");
    String orderby = request.getParameter("order");
    String filteruri = request.getParameter("filteruri");
    String action = request.getParameter("act");

    SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
    SemanticObject so = null;
    GenericObject go = null;

    int numPages = 10;
    try {
        numPages = Integer.parseInt(strNumItems);
    } catch (Exception e) {
        numPages = 10;
    }

    if (orderby == null) {
        orderby = "date";
    }
    if (action == null) {
        action = "";
    }

    //12/junio/2013
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MMMMM/yyyy", new Locale("es"));
    if (request.getParameter("alertmsg") != null) {
        String strMsg = request.getParameter("alertmsg");
        strMsg = strMsg.replace("<br>", "\\n\\r");
%>
<script type="text/javascript">
    alert('<%=strMsg%>');
</script>
<%
    }

        if (paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY) {
            // llamada como estrategia
        } else {
            // llamado como contenido
            if (action.equals("")) {
                // ordenamiento orderby y filtrado de DataSets
                Iterator<Dataset> itds1 = null;
                if (null != filteruri && filteruri.trim().length() > 0) {
                    go = ont.getGenericObject(filteruri);
                    if (go != null) {
                        if (go instanceof Topic) { 
                            itds1 = Dataset.ClassMgr.listDatasetByTopic((Topic) go,wsite);
                        } else {
                            itds1 = Dataset.ClassMgr.listDatasets(wsite);
                        }
                    } else {
                        itds1 = Dataset.ClassMgr.listDatasets(wsite);
                    }
                } else {
                    itds1 = Dataset.ClassMgr.listDatasets(wsite);
                }
                // dejo en hm los ds
                HashMap<String, Dataset> hmcp = new HashMap<String, Dataset>();
                while(itds1.hasNext()){
                    Dataset ds = itds1.next();
                    hmcp.put(ds.getURI(), ds);
                }
                Iterator<Dataset> itds = null;
                if(hmcp.size()>0){
                    itds = DataSetResource.orderDS(hmcp.values().iterator(), orderby);
                    intSize=hmcp.size();
                } else {
                intSize = 0;
                }

    %>
    <div class="izq_tema">
        <ul>
            <li><h3><%//=paramRequest.getLocaleString("lbl_topicFilter")%></h3></li>
                <%
                    Iterator<Topic> ittop = Topic.ClassMgr.listTopics(wsite);
                    while (ittop.hasNext()) {
                        Topic inst = ittop.next();
                        SWBResourceURL url = paramRequest.getRenderUrl();
                        url.setParameter("filteruri", inst.getURI());
                        if (null != orderby) {
                            url.setParameter("order", orderby);
                        }
                %>
            <li><a href="<%=url.toString()%>" title="<%=inst.getTopicDescription() != null ? inst.getTopicDescription().trim() : inst.getTopicTitle()%>"><%=inst.getTopicTitle()%></a></li> 
                <%
                    }
                %>
        </ul>  
    </div>
</div>
<div class="derecho">
    <div class="derecho_ordena">
        <%
            SWBResourceURL urlorder = paramRequest.getRenderUrl();
            urlorder.setParameter("act", "");
            if (null != filteruri) {
                urlorder.setParameter("filteruri", filteruri);
            }

            String ckdCreated = "", ckdView = "", ckdDownload = "", ckdRank = "";
            if (null != orderby) {
                //urlorder.setParameter("order", orderby);
                if (orderby.equals(DataSetResource.ORDER_CREATED)) {
                    ckdCreated = "checked";
                } else if (orderby.equals(DataSetResource.ORDER_VIEW)) {
                    ckdView = "checked";
                } else if (orderby.equals(DataSetResource.ORDER_DOWNLOAD)) {
                    ckdDownload = "checked";
                } else if (orderby.equals(DataSetResource.ORDER_RANK)) {
                    ckdRank = "checked";
                }
            }

        %>
        <label><%=paramRequest.getLocaleString("lbl_orderby")%></label>
        <p>
            <input type="radio" id="ordercreated" name="order" value="created" <%=ckdCreated%> onclick="window.location = '<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_CREATED%>';"><label for="ordercreated"><%=paramRequest.getLocaleString("lbl_byrecent")%></label> 
            <input type="radio" id="orderview" name="order" value="view" <%=ckdView%> onclick="window.location = '<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_VIEW%>';"><label for="orderview"><%=paramRequest.getLocaleString("lbl_byvisited")%></label> 
            <input type="radio" id="orderdownload" name="order" value="download" <%=ckdDownload%> onclick="window.location = '<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_DOWNLOAD%>';"><label for="orderdownload"><%=paramRequest.getLocaleString("lbl_bydownload")%></label>
            <input type="radio" id="orderrank" name="order" value="value" <%=ckdRank%> onclick="window.location = '<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_RANK%>';"><label for="orderrank"><%=paramRequest.getLocaleString("lbl_byvaluated")%></label>
        </p>
    </div>
    <%

        //PAGINACION
        long l = intSize;
        int x = 0;
    %>
    <div class="listadataset">
        <ul>
            <%
                if (intSize == 0) {
            %>
            <li><h3><%=paramRequest.getLocaleString("lbl_notDSfound")%></h3></li>
                    <%                                } else {
                        String wpurl = wsite.getWebPage(datosWP).getUrl()+"?act=detail&suri=";
                        while (itds.hasNext()) { 

                            //NUMERO DE LEMENTOS A MOSTRAR ////////////////////
                            if ( x == l) {
                                break;
                            }
                            x++;
                            /////////////////////////////////

                            Dataset ds = itds.next(); 
                            //SWBResourceURL urldet = paramRequest.getRenderUrl();
                            //urldet.setParameter("act", "detail");
                            //urldet.setParameter("suri", ds.getEncodedURI());
                           
                    %>
            <li>
                <label><a title="<%=ds.getDatasetDescription()%>" href="<%=wpurl+ds.getEncodedURI()%>"><%=ds.getDatasetTitle()%></a></label> 
                <%=paramRequest.getLocaleString("lbl_publisher")%>:<%=ds.getInstitution().getInstitutionTitle()%><br/>
            </li>
            <%
                    }
                }
            %>
        </ul>
    </div>
</div>
    <%
        }  
    }
    // Termina llamado como contenido
%>

