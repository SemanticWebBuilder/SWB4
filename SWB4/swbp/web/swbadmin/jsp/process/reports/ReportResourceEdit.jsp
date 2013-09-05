<%-- 
    Document   : ReportResourceEdit
    Created on : 11/03/2013, 05:23:28 PM
    Author     : carlos.alvarez
--%>
<%@page import="org.semanticwb.process.resources.reports.ColumnReport"%>
<%@page import="org.semanticwb.process.resources.reports.Report"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.process.model.ItemAware"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.SWBFormButton"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page contentType="text/html"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SWBResourceURL url = paramRequest.getRenderUrl();
    SWBResourceURL urlViewReport = paramRequest.getRenderUrl();
    SWBResourceURL urlAction = paramRequest.getActionUrl();
    Report obj = Report.ClassMgr.getReport(request.getParameter("idReport"), paramRequest.getWebPage().getWebSite());
    boolean isSaveOnSystem = Boolean.parseBoolean(request.getAttribute("isSaveOnSystem").toString());
    Iterator<ItemAware> ia = obj.getProcessName().listRelatedItemAware().iterator();
    Integer modeExport = Integer.parseInt(request.getAttribute("modeExport").toString());
    SWBResourceURL urlReport = paramRequest.getRenderUrl().setMode("generate").setCallMethod(SWBParamRequest.Call_DIRECT);
    String propiedades = "";
    Iterator<ColumnReport> columna = ColumnReport.ClassMgr.listColumnReportByReportName(obj);
    Integer total = 0;
    while (columna.hasNext()) {
        ColumnReport colu = columna.next();
        if (propiedades.length() > 0) {
            propiedades += "|";
        }
        total++;
        propiedades += colu.getNameProperty();
    }
    SWBFormMgr tipo = new SWBFormMgr(obj.getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
    String lang = "";
    if (paramRequest.getUser() != null) {
        lang = paramRequest.getUser().getLanguage();
    }
    tipo.setType(SWBFormMgr.TYPE_DOJO);
    tipo.setLang(lang);
    Integer control = 0;
    Integer contador = 0;
    String objeto = "";
    SWBResourceURL urlDialog = paramRequest.getRenderUrl().setMode("dialog").setCallMethod(SWBResourceURL.Call_DIRECT);
    urlDialog.setParameter("idReport", obj.getId());
    urlDialog.setParameter("action", "export");

%>

<div id="out" style="display: none; width: 100%; alignment-adjust: central;">
    <%url = paramRequest.getRenderUrl();
        url.setMode(SWBResourceURL.Mode_VIEW);%>
    <a id="a" href="<%=url%>"
       data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("reports") + " " + paramRequest.getLocaleString("saved")%>">
        <%=paramRequest.getLocaleString("reports") + " " + paramRequest.getLocaleString("saved")%> <span id="count" class="badge"></span></a>
    <br/>
    <br/>
</div>
<div class="row">
    <div class="panel panel-success">
        <div class="panel-heading">
            <table style="width: 100%;">
                <tr><td>
                        <div class="panel-title"><strong><%=paramRequest.getLocaleString("configureReport") + " " + obj.getTitle()%></strong></div>  
                    </td><td style="text-align: right;">
                        <a class="btn btn-default btn-sm"
                           onclick="javascript:document.back.submit();"
                           data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("back")%>">
                            <li class="icon-mail-reply icon-large"></li>
                        </a>
                        <%if (modeExport == 2) {%>
                        <a class="btn btn-default btn-sm" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("view") + " " + paramRequest.getLocaleString("report") + " " + obj.getTitle()%>"
                           href="<%=urlViewReport.setMode("viewReport").setParameter("idReport", obj.getId())%>">
                            <li class="icon-eye-open icon-large"></li>
                        </a>
                        <%} else {%>
                        <%
                            if (isSaveOnSystem) {
                        %>
                        <a href="<%=urlDialog%>" data-toggle="modal" data-target="#modalDialog"
                           class="btn btn-default btn-sm" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("save") + " " + paramRequest.getLocaleString("report") + " " + obj.getTitle()%>">
                            <li class="icon-save icon-large"></li>
                        </a>
                        <%} else {%>
                        <a href="<%=urlDialog%>" data-toggle="modal" data-target="#modalDialog" class="btn btn-default btn-sm"
                           data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("generate") + " " + paramRequest.getLocaleString("report") + " " + obj.getTitle()%>">
                            <li class="icon-file icon-large"></li>
                        </a>
                        <%}%>
                        <%}%>
                    </td></tr>
            </table>
        </div>
        <div class="panel-body">
            <div class="col-xs-0 col-sm-1 col-md-3"></div>
            <form class="col-xs-12 col-sm-10 col-md-6" action="<%=urlAction.setAction(SWBResourceURL.Action_EDIT)%>" method="post">
                <%out.println(tipo.getFormHiddens());%>
                <div class="table-responsive">
                    <table class="table table-hover swbp-table">
                        <tr>
                            <td style="text-align: right; vertical-align: middle;"><strong><%=paramRequest.getLocaleString("title")%></strong></td>
                            <td><input name="title" class="form-control input-sm"  placeholder="<%=paramRequest.getLocaleString("title")%>"  type="text" value="<%=obj.getTitle()%>"></td>
                        </tr>
                        <tr>
                            <td style="text-align: right; vertical-align: middle;"><strong><%=paramRequest.getLocaleString("process")%></strong></td>
                            <td><strong><%=obj.getProcessName().getTitle()%></strong></td>
                        </tr>
                        <tr>
                            <td style="text-align: right; vertical-align: middle;"><strong><%=paramRequest.getLocaleString("pagingSize")%></strong></td>
                            <td><input name="pagingSize" placeholder="<%=paramRequest.getLocaleString("pagingSize")%>" class="form-control input-sm" type="text" value="<%=obj.getPagingSize()%>"></td>
                        </tr>
                        <tr>
                            <td style="text-align: right; vertical-align: middle;" colspan="2">
                                <button class="btn btn-default btn-sm" type="submit"
                                        data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("save")%>">
                                    <li class="icon-save icon-large"></li>
                                </button> 
                            </td>
                        </tr>
                    </table>
                </div>
                <input type="hidden" name="idReport" value="<%=obj.getId()%>"/>
            </form>
        </div>
        <div class="col-xs-0 col-sm-1 col-md-3"></div>
        <div class="panel-heading">
            <div class="panel-title"><strong><%=paramRequest.getLocaleString("configure")%></strong></div>
        </div>
        <div class="panel-body">
            <div class="col-xs-0 col-sm-1 col-md-3"></div>
            <form class="col-xs-12 col-sm-10 col-md-6" dojoType="dijit.form.Form" action="<%=urlAction.setAction("addColumn")%>" method="post">
                <table class="table table-hover swbp-table" style="width: 500px;">
                    <tr><td>
                            <select multiple name="property" style="" class="form-control">
                                <%try {
                                        while (ia.hasNext()) {
                                            ItemAware iaw = ia.next();
                                            if (iaw.getItemSemanticClass() != null) {
                                                Iterator<SemanticProperty> sp = iaw.getItemSemanticClass().listProperties();
                                                while (sp.hasNext()) {
                                                    SemanticProperty spr = sp.next();
                                                    if (!propiedades.contains(iaw.getId() + "|" + spr.getPropId())) {
                                                        if (!spr.getPropId().equals("swb:valid")) {
                                                            control++;
                                %>
                                <option value="<%=iaw.getURI() + "|" + spr.getPropId()%>"> <%=iaw.getDisplayTitle(lang) + "." + spr.getName()%></option>
                                <%
                                                            objeto = iaw.getDisplayTitle(lang);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                %>
                            </select>
                        </td>
                        <td style="text-align: left; vertical-align: middle;">
                            <input type="hidden" name="idReport" value="<%=obj.getId()%>"/>
                            <button type="submit" class="btn btn-default btn-sm <%if (control == 0) {%>disabled<%}%>" 
                                    data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("add") + " " + paramRequest.getLocaleString("column")%>">
                                <li class="icon-plus-sign-alt icon-large"></li>
                            </button>
                        </td></tr>
                </table>
            </form>
            <div class="col-xs-0 col-sm-1 col-md-3"></div>
        </div>
        <div class="panel-body">
            <form dojoType="dijit.form.Form" action="<%=urlAction.setAction("updateColumn")%>" method="post">
                <div class="table-responsive">
                    <table class="table table-hover swbp-table">
                        <thead>
                        <th><%=paramRequest.getLocaleString("remove")%></th>
                        <th><%=paramRequest.getLocaleString("order")%></th>
                        <th><%=paramRequest.getLocaleString("property")%></th>
                        <th><%=paramRequest.getLocaleString("title")%></th>
                        <th><%=paramRequest.getLocaleString("enabledOrder")%></th>
                        <th><%=paramRequest.getLocaleString("visible")%></th>
                        <th><%=paramRequest.getLocaleString("filter")%></th>
                        </thead>
                        <%
                            Iterator<ColumnReport> colum = SWBComparator.sortSortableObject(obj.listColumnReports());
                            while (colum.hasNext()) {
                                ColumnReport colu = colum.next();
                                contador++;
                                SemanticProperty sp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(colu.getNameProperty().substring(colu.getNameProperty().indexOf("|") + 1));

                                SemanticObject semObj = SemanticObject.createSemanticObject(colu.getNameProperty().substring(0, colu.getNameProperty().indexOf("|")));
                                ItemAware ias = (ItemAware) semObj.createGenericInstance();
                                objeto = ias.getTitle();
                        %>
                        <tr>
                            <td style="text-align: center;"><input type="checkbox" name="delete<%=colu.getURI()%>"/></td>
                            <td style="text-align: center;"><%if (contador == 1 && total > 1) {%>
                                <a href="<%=urlAction.setAction("moveDown").setParameter("idColumn", colu.getId())%>"
                                   data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("down")%>">
                                    <li class="icon-arrow-down icon-large"></li>
                                </a>
                                <%} else if (contador < total && total > 1) {%>
                                <a href="<%=urlAction.setAction("moveUp").setParameter("idColumn", colu.getId())%>" 
                                   data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("up")%>">
                                    <li class="icon-arrow-up icon-large"></li>
                                </a>
                                <a href="<%=urlAction.setAction("moveDown").setParameter("idColumn", colu.getId())%>"
                                   data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("down")%>">
                                    <li class="icon-arrow-down icon-large"></li>
                                </a>
                                <%} else if (contador == total && total > 1) {%>
                                <a href="<%=urlAction.setAction("moveUp").setParameter("idColumn", colu.getId())%>" 
                                   data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("up")%>">
                                    <li class="icon-arrow-up icon-large"></li>    
                                </a><%}%></td>
                            <td><%=objeto + "." + sp.getName()%></td>
                            <td><input class="form-control input-sm" placeholder="<%=paramRequest.getLocaleString("title")%>" type="text" name="title<%=colu.getURI()%>" value="<%=colu.getTitleColumn() == null ? sp.getDisplayName(lang) : colu.getTitleColumn()%>"></input></td>
                            <td style="text-align: center;">
                                <input name="enabledOrder<%=colu.getURI()%>" type="checkbox" <%if (colu.isEnabledOrder()) {%> checked="true"<%}%>/></td>
                            <td style="text-align: center;">
                                <input name="columnVisible<%=colu.getURI()%>" type="checkbox" <%if (colu.isColumnVisible()) {%> checked="true"<%}%>/></td>
                            <td style="text-align: center;">
                                <%if (sp.isDataTypeProperty()) {%>
                                <input class="form-control input-sm" name="defaultValue<%=colu.getURI()%>" type="text"  value="<%=colu.getDefaultValue() == null ? "" : colu.getDefaultValue()%>"/>
                                <%if (sp.isNumeric() || sp.isDate()) {%>
                                <input class="form-control input-sm" name="defaultValueMax<%=colu.getURI()%>" type="text"  value="<%=colu.getDefaultValueMax() == null ? "" : colu.getDefaultValueMax()%>"/>
                                <%}
                                    }%>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                        <tr>
                            <td style="text-align: center; vertical-align: middle;" colspan="7">
                                <a class="btn btn-default btn-sm"
                                   onclick="javascript:document.back.submit();"
                                   data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("back")%>">
                                    <li class="icon-mail-reply icon-large"></li>
                                </a>
                                <button class="btn btn-default btn-sm <%if (contador == 0) {%>disabled<%}%>" type="submit"
                                        data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("save")%>">
                                    <li class="icon-save icon-large"></li>
                                </button> 
                            </td>
                        </tr>
                    </table>
                </div>
                <input type="hidden" name="idReport" value="<%=obj.getId()%>"/>
            </form>
        </div>
    </div>
</div>
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
                                           //alert(count);
                                           document.getElementById('count').innerHTML = count;
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