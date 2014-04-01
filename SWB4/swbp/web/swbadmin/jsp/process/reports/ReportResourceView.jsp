<%-- 
    Document   : ReportResourceView
    Created on : 19/03/2013, 09:21:16 AM
    Author     : carlos.alvarez
--%>

<%@page import="org.semanticwb.process.resources.reports.ColumnReport"%>
<%@page import="org.semanticwb.process.resources.reports.Report"%>
<%@page import="org.semanticwb.SWBUtils.Collections"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="java.io.DataOutputStream"%>
<%@page import="java.io.DataOutput"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.process.model.ItemAwareReference"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="org.semanticwb.process.model.ItemAware"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    boolean isSaveOnSystem = Boolean.parseBoolean(request.getAttribute("isSaveOnSystem").toString());
    //SWBResourceURL urlReport = paramRequest.getRenderUrl().setMode("generate").setCallMethod(SWBParamRequest.Call_DIRECT);
    SWBResourceURL url = paramRequest.getRenderUrl();
    String lang = null;
    if (paramRequest.getUser() != null) {
        lang = paramRequest.getUser().getLanguage();
        if (lang == null) {
            lang = "es";
        }
    }
    Report obj = Report.ClassMgr.getReport(request.getParameter("idReport"), paramRequest.getWebPage().getWebSite());
    String uriObject = "";
    String idProperty = "";
    String dataType = "";
    String des = "";
    if (request.getParameter("des") != null) {
        des = request.getParameter("des").toString();
    }
    SWBResourceURL urlDialog = paramRequest.getRenderUrl().setMode("dialog").setCallMethod(SWBResourceURL.Call_DIRECT);
    urlDialog.setParameter("idReport", obj.getId());
    urlDialog.setParameter("action", "export");
    Iterator<ColumnReport> colum = SWBComparator.sortSortableObject(obj.listColumnReports());
%>
<div>
    <%url = paramRequest.getRenderUrl();
        url.setMode(SWBResourceURL.Mode_VIEW);%>

    <br/>
    <br/>
</div>
<div id="out" class="alert alert-dismissable alert-warning" style="display: none; width: 100%; alignment-adjust: central;">
    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
    <a id="a" href="<%=url%>"
       data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("reports") + " " + paramRequest.getLocaleString("saved")%>">
        <%=paramRequest.getLocaleString("reports") + " " + paramRequest.getLocaleString("saved")%> <span id="count" class="badge"></span></a>
</div>
<h2>
    <a class="btn"
       onclick="javascript:document.back.submit();"
       data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("back")%>">
        <li class="fa fa-mail-reply"></li>
    </a>
    <%=paramRequest.getLocaleString("report") + " " + obj.getTitle()%></h2>
    <%if (colum.hasNext()) {%>
<ul class="list-unstyled list-inline">
    <%
        if (isSaveOnSystem) {
    %>
    <li>
        <a href="<%=urlDialog%>" data-toggle="modal" data-target="#modalDialog"
           class="btn  btn-default fa fa-save" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("save") + " " + paramRequest.getLocaleString("report") + " " + obj.getTitle()%>">
        </a>
    </li>
    <%} else {%>
    <li>
        <a href="<%=urlDialog%>" data-toggle="modal" data-target="#modalDialog" class="btn btn-default fa fa-file"
           data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("generate") + " " + paramRequest.getLocaleString("report") + " " + obj.getTitle()%>">
        </a>
    </li>
    <%}%>
    <li>
        <a class="btn btn-default fa fa-wrench" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("configureReport") + " " + obj.getTitle()%>" href="<%=url.setMode(SWBResourceURL.Mode_EDIT).setParameter("idReport", obj.getId())%>">
        </a>  
    </li>
</ul>
<div class="row">
    <div class="table-responsive">
        <table class="table table-hover swbp-table">
            <thead>
                <tr>
                    <%
                        while (colum.hasNext()) {
                            ColumnReport colu = colum.next();
                            SemanticProperty sp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(colu.getNameProperty().substring(colu.getNameProperty().indexOf("|") + 1));
                            SWBResourceURL order = paramRequest.getRenderUrl();
                            order.setParameter("idReport", obj.getId());
                            uriObject = colu.getNameProperty().substring(0, colu.getNameProperty().lastIndexOf("|"));
                            idProperty = colu.getNameProperty().substring((colu.getNameProperty().lastIndexOf("|") + 1), colu.getNameProperty().length());
                            order.setParameter("sort", idProperty);
                            order.setParameter("uriObject", uriObject);
                            //order.setParameter("page", request.getParameter("page"));
                            if (sp.isDataTypeProperty()) {
                                order.setParameter("dataType", "isDataTypeProperty");
                            } else {
                                order.setParameter("dataType", "isObjectProperty");
                            }

                            if (des.equals("des")) {
                                order.setParameter("des", "asc");
                            } else {
                                order.setParameter("des", "des");
                            }
                            if (colu.isColumnVisible()) {
                    %>
                    <th class="tban-id" title="<%=colu.getTitleColumn() == null ? sp.getDisplayName(lang) : colu.getTitleColumn()%>">
                        <a <%if (colu.isEnabledOrder()) {%>href="<%=order%>"<%}%> >
                            <%if (colu.isEnabledOrder()) {%>
                            <span class="fa fa-sort"></span>
                            <%}%>
                            
                            <%=colu.getTitleColumn() == null ? sp.getDisplayName(lang) : colu.getTitleColumn()%>
                        </a>
                    </th>
                    <%}
                        }%>
                </tr>
            </thead>
            <%
                ArrayList<ProcessInstance> pinstances = (ArrayList<ProcessInstance>) request.getAttribute("pi");
                Iterator<ProcessInstance> pi = pinstances.iterator();
                while (pi.hasNext()) {
            %>
            <tr>
                <%
                    ProcessInstance pins = (ProcessInstance) pi.next();
                    Iterator<ColumnReport> columna = SWBComparator.sortSortableObject(obj.listColumnReports());
                    while (columna.hasNext()) {
                        ColumnReport cr = columna.next();
                        if (cr.isColumnVisible()) {
                            String[] array = cr.getNameProperty().split("\\|");
                            ItemAware ite = (ItemAware) SWBPlatform.getSemanticMgr().getOntology().getSemanticObject(array[0]).createGenericInstance();
                            Iterator<ItemAwareReference> iar = pins.listAllItemAwareReferences();
                            while (iar.hasNext()) {
                                ItemAwareReference iarr = (ItemAwareReference) iar.next();
                                if (iarr.getItemAware() != null && pins.getItemAwareReference().getProcessObject() != null) {
                                    if (iarr.getItemAware().equals(ite)) {
                                        SemanticProperty spt = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(array[1]);
                %>
                <td>
                    <%
                        SWBFormMgr forMgr = new SWBFormMgr(iarr.getProcessObject().getSemanticObject(), null, SWBFormMgr.MODE_VIEW);
                        forMgr.setType(SWBFormMgr.TYPE_DOJO);
                        if (!spt.isInverseOf() && spt.isDataTypeProperty()) {
                            if (iarr.getProcessObject().getSemanticObject().getProperty(spt) != null) {
                                out.print(forMgr.renderElement(request, spt, SWBFormMgr.MODE_VIEW));
                            } else {
                                out.print("--");
                            }
                        } else if (!spt.isInverseOf() && spt.isObjectProperty()) {
                            if (iarr.getProcessObject().getSemanticObject().getObjectProperty(spt) != null) {
                                String ou = forMgr.renderElement(request, spt, SWBFormMgr.MODE_VIEW);
                                out.print(forMgr.renderElement(request, spt, SWBFormMgr.MODE_VIEW));
                            } else {
                                out.print("--");
                            }
                        }
                    %></td><%
                                        }
                                    }
                                }
                            }
                        }
                    %>
            </tr>
            <%}%>
        </table>
    </div>
    <!--/BEGIN PAGINATION-->
    <%
        String sort = request.getParameter("sort");
        if (sort != null && sort.length() > 0) {
            sort = "sort|" + sort;
        } else {
            sort = "";
        }
        dataType = request.getParameter("dataType");
        if (dataType != null && dataType.length() > 0) {
            dataType = "dataType|" + dataType;
        } else {
            dataType = "";
        }
        String idReport = "idReport|" + obj.getId();
        uriObject = "uriObject|" + uriObject;
        des = "des|" + des;
    %>
    <jsp:include page="/swbadmin/jsp/process/commons/pagination.jsp" flush="true">
        <jsp:param name="navUrlParams" value="<%=sort%>"/>
        <jsp:param name="navUrlParams" value="<%=idReport%>"/>
        <jsp:param name="navUrlParams" value="<%=dataType%>"/>
        <jsp:param name="navUrlParams" value="<%=uriObject%>"/>
        <jsp:param name="navUrlParams" value="<%=des%>"/>
        <jsp:param name="showPageOfPage" value="true"/>
        <jsp:param name="pageParam" value="page"/>
    </jsp:include>  
    <!--/END PAGINATION-->
</div>
<%} else {%>
<ul class="list-unstyled list-inline">
    <li><a class="btn btn-default fa fa-wrench" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("configureReport") + " " + obj.getTitle()%>" href="<%=url.setMode(SWBResourceURL.Mode_EDIT).setParameter("idReport", obj.getId())%>">
        </a></li>
</ul>
<div class="alert alert-block alert-warning fade in">
    <p><%=paramRequest.getLocaleString("msgNoColumns")%></p>
</div>  
<%}%>
<form method="post" action="<%=paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)%>" name="back"></form>  
<script type="text/javascript">
           var count = 0;
           function submitUrl(url, reference) {
               var extension = document.getElementById("extension");
               url = url + '?idReport=' + document.getElementById('idReport').value + '&extension=' + extension.options[extension.selectedIndex].value + '&reportName=' + document.getElementById('reportName').value;
               dojo.xhrGet({
                   url: url,
                   load: function(response, ioArgs)
                   {
                       count++;
                       document.getElementById('out').style.display = 'block';
                       document.getElementById('count').innerHTML = count;
                       return response;
                   },
                   error: function(response, ioArgs) {
                       setInterval(function() {
                           myTimer2()
                       }, 2000);
                       return response;
                   },
                   handleAs: "text"
               });
           }
</script>