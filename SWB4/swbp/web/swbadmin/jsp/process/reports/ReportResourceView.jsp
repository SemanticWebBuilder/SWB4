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
<div class="bandeja-combo">
    <%
        SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
        boolean isSaveOnSystem = Boolean.parseBoolean(request.getAttribute("isSaveOnSystem").toString());
        SWBResourceURL urlReport = paramRequest.getRenderUrl().setMode("generate").setCallMethod(SWBParamRequest.Call_DIRECT);
        Integer contador = 0;
        String lang = null;
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
            if (lang == null) {
                lang = "es";
            }
        }
        Report obj = Report.ClassMgr.getReport(request.getParameter("idReport"), paramRequest.getWebPage().getWebSite());
        int pageNum = 1;
        int maxPages = (Integer) request.getAttribute("maxPages");
        if (request.getParameter("page") != null && !request.getParameter("page").trim().equals("")) {
            pageNum = Integer.valueOf(request.getParameter("page"));
        }
        String sortType = null;
        if (request.getAttribute("sort") != null) {
            sortType = request.getAttribute("sort").toString();
        }
        String dataType = null;
        if (request.getAttribute("dataType") != null) {
            dataType = request.getAttribute("dataType").toString();
        }
        String des = "";
        if (request.getAttribute("des") != null) {
            des = request.getAttribute("des").toString();
        }
    %>
    <a href="#" onclick="showDialog('<%=obj.getId()%>');
            return false;" title="Exportar reporte <%=obj.getTitle()%>">
        <img src="/swbadmin/jsp/process/reports/images/generate.png">Reporte: <%=obj.getTitle()%></a>
    <br/>
    <br/>
    <table class="tabla-bandeja">
        <thead>
            <tr>
                <%
                    Iterator<ColumnReport> colum = SWBComparator.sortSortableObject(obj.listColumnReports());
                    while (colum.hasNext()) {
                        ColumnReport colu = colum.next();
                        SemanticProperty sp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(colu.getNameProperty().substring(colu.getNameProperty().indexOf("|") + 1));
                        SWBResourceURL order = paramRequest.getRenderUrl();
                        order.setParameter("idReport", obj.getId());
                        order.setParameter("sort", colu.getNameProperty());
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
                    <a <%if (colu.isEnabledOrder()) {%>href="<%=order%>" style="color: white; text-decoration: none;">
                        <img src="/swbadmin/jsp/process/reports/images/arrows.png">
                        <%}%>
                        <label style="color: white; text-decoration: none;cursor: pointer;"><%=colu.getTitleColumn() == null ? sp.getDisplayName(lang) : colu.getTitleColumn()%></label>
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
                            if (iarr.getItemAware() != null  && pins.getItemAwareReference().getProcessObject() != null) {
                                contador++;
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

    <%
        if (contador == 0) {%>
    <h3>Sin columnas</h3>
    <%} else {%>

    <div class="paginado">
        <table class="tabla-bandeja">
            <tbody>
                <tr>
                    <td style="width:60%;">
                        P&aacute;gina: <%=pageNum%> de <%=maxPages%>
                    </td>
                    <td style="text-align:right;">
                        <%
                            if (maxPages > 1) {
                                SWBResourceURL first = paramRequest.getRenderUrl();
                                first.setParameter("sort", sortType);
                                first.setParameter("des", des);
                                first.setParameter("dataType", dataType);
                                first.setParameter("idReport", obj.getId());
                                first.setParameter("page", "1");
                        %><a href="<%=first%>">Primer p&aacute;gina</a><%
                            }
                            if (pageNum - 1 > 0) {
                                SWBResourceURL back = paramRequest.getRenderUrl();
                                back.setParameter("des", des);
                                back.setParameter("dataType", dataType);
                                back.setParameter("sort", sortType);
                                back.setParameter("idReport", obj.getId());
                                back.setParameter("page", String.valueOf(pageNum - 1));
                        %><a href="<%=back%>">Anterior</a><%
                            }
                            if (pageNum + 1 <= maxPages) {
                                SWBResourceURL forward = paramRequest.getRenderUrl();
                                forward.setParameter("des", des);
                                forward.setParameter("dataType", dataType);
                                forward.setParameter("sort", sortType);
                                forward.setParameter("idReport", obj.getId());
                                forward.setParameter("page", String.valueOf(pageNum + 1));
                        %><a href="<%=forward%>">Siguiente</a><%
                            }
                            if (maxPages > 1 && pageNum < maxPages) {
                                SWBResourceURL last = paramRequest.getRenderUrl();
                                last.setParameter("des", des);
                                last.setParameter("dataType", dataType);
                                last.setParameter("sort", sortType);
                                last.setParameter("idReport", obj.getId());
                                last.setParameter("page", String.valueOf(maxPages));
                        %><a href="<%=last%>">&Uacute;ltima p&aacute;gina</a><%
                            }
                        %>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <%}%>
</div>

<div dojoType="dijit.Dialog" style="display:none;" id="configDialog" title="Generar reporte">
    <div dojoType="dijit.layout.ContentPane" id="configDialogImp" executeScripts="true">
        <form method="post" <%if (!isSaveOnSystem) {%>action="<%=urlReport%>"<%}%>>
            <input type="hidden" id="idReport" name="idReport" value="<%=obj.getId()%>"/>
            Nombre de archivo:
            <input type="text" id="reportName" name="reportName" dojoType="dijit.form.ValidationTextBox" 
                   required="true" trim="true" promptMessage="Nombre de archivo"
                   regExp="[a-zA-Z0-9_\ %-]+[a-zA-Z0-9_\ %-]" />
            <br/>
            Formato:
            <select id="extension" name="extension" style="width: 200px;">
                <option value="xls" selected="true">Excel</option>
                <option value="pdf">PDF</option>
            </select>
            <br/>
            <input type="submit" dojoType="dijit.form.Button" label="Generar" <%if (isSaveOnSystem) {%>onclick="submitUrl('<%=urlReport%>', this);
            return false;"<%} else {%> onclick="close();"<%}%> title="Generar"/>
        </form>
    </div>
</div>
<script type="text/javascript">
        var count = 0;
        function showDialog(idReport) {
            document.getElementById('idReport').setAttribute("value", "" + idReport);
            dijit.byId('configDialog').show();
        }
        function close() {
            dijit.byId('configDialog').hide();
        }
        function submitUrl(url, reference) {
            var extension = document.getElementById("extension");
            dijit.byId('configDialog').hide();
            url = url + '?idReport=' + document.getElementById('idReport').value + '&extension=' + extension.options[extension.selectedIndex].value + '&reportName=' + document.getElementById('reportName').value;
            dojo.xhrGet({
                url: url,
                load: function(response, ioArgs)
                {
                    count++;
                    document.getElementById('out').style.display = 'block';
                    //alert(count);
                    document.getElementById('a').innerHTML = "Reportes nuevos: " + count;
                    //setInterval(function(){myTimer()},2000);
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
<script type="text/javascript">
    dojo.require("dijit.Dialog");
    dojo.require("dojo.parser");
    dojo.require("dijit._Calendar");
    dojo.require("dijit.ProgressBar");
    dojo.require("dijit.Editor");
    dojo.require("dijit.form.Form");
    dojo.require("dijit.form.CheckBox");
    dojo.require("dijit.form.Textarea");
    dojo.require("dijit.form.FilteringSelect");
    dojo.require("dijit.form.TextBox");
    dojo.require("dijit.form.DateTextBox");
    dojo.require("dijit.form.TimeTextBox");
    dojo.require("dijit.form.Button");
    dojo.require("dijit.form.NumberSpinner");
    dojo.require("dijit.form.Slider");
    dojo.require("dojox.form.BusyButton");
    dojo.require("dojox.form.TimeSpinner");
</script>