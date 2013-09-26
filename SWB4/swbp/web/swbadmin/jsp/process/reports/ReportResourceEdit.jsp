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
<div id="out" class="alert alert-dismissable alert-warning" style="display: none; width: 100%; alignment-adjust: central;">
    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
    <a id="a" href="<%=url.setMode(SWBResourceURL.Mode_VIEW)%>"
       data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("reports") + " " + paramRequest.getLocaleString("saved")%>">
        <%=paramRequest.getLocaleString("reports") + " " + paramRequest.getLocaleString("saved")%> <span id="count" class="badge"></span></a>
</div>
<div class="row">
    <div class="panel panel-default">
        <div class="panel-heading">
            <table style="width: 100%;">
                <tr><td>
                        <div class="panel-title"><strong><%=paramRequest.getLocaleString("configureReport") + " " + obj.getTitle()%></strong></div>  
                    </td><td style="text-align: right;">
                        <a class="btn btn-default btn-sm"
                           onclick="javascript:document.back.submit();"
                           data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("back")%>">
                            <li class="icon-mail-reply"></li> <%=paramRequest.getLocaleString("back")%>
                        </a>
                        <%if (modeExport == 2) {%>
                        <a class="btn btn-default btn-sm" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("view") + " " + paramRequest.getLocaleString("report") + " " + obj.getTitle()%>"
                           href="<%=urlViewReport.setMode("viewReport").setParameter("idReport", obj.getId())%>">
                            <li class="icon-eye-open"></li> <%=paramRequest.getLocaleString("view")%>
                        </a>
                        <%} else {%>
                        <%
                            if (isSaveOnSystem) {
                        %>
                        <a href="<%=urlDialog%>" data-toggle="modal" data-target="#modalDialog"
                           class="btn btn-default btn-sm" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("save") + " " + paramRequest.getLocaleString("report") + " " + obj.getTitle()%>">
                            <li class="icon-save"></li> <%=paramRequest.getLocaleString("save")%>
                        </a>
                        <%} else {%>
                        <a href="<%=urlDialog%>" data-toggle="modal" data-target="#modalDialog" class="btn btn-default btn-sm"
                           data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("generate") + " " + paramRequest.getLocaleString("report") + " " + obj.getTitle()%>">
                            <li class="icon-file"></li> <%=paramRequest.getLocaleString("generate")%>
                        </a>
                        <%}%>
                        <%}%>
                    </td></tr>
            </table>
        </div>
        <div class="row">
            <div class="col-xs-0 col-sm-1 col-md-3"></div>
            <div class="col-xs-12 col-sm-10 col-md-6">       
                <form action="<%=urlAction.setAction(SWBResourceURL.Action_EDIT)%>"
                      ethod="post" class="form-horizontal" role="form" enctype="multipart/form-data">
                    <div class="panel-body">
                        <%out.println(tipo.getFormHiddens());%>
                        <div class="form-group">
                            <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("title")%> *</label>
                            <div class="col-lg-6">
                                <input type="text" name="title" id="title" class="form-control" value="<%=obj.getTitle()%>"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("process")%> *</label>
                            <div class="col-lg-6">
                                <input class="form-control" value="<%=obj.getProcessName().getTitle()%>" disabled="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("pagingSize")%> *</label>
                            <div class="col-lg-6">
                                <input type="text" name="pagingSize" value="<%=obj.getPagingSize()%>" class="form-control" placeholder="<%=paramRequest.getLocaleString("pagingSize")%>">
                            </div>
                        </div>
                    </div>
                    <div class="panel-footer text-right">
                        <a class="btn btn-default btn-sm"
                           onclick="javascript:document.back.submit();"
                           data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("back")%>">
                            <li class="icon-mail-reply"></li> Regresar
                        </a>
                        <button class="btn btn-success btn-sm" type="submit"
                                data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("save")%>">
                            <li class="icon-save"></li> <%=paramRequest.getLocaleString("save")%>
                        </button>
                    </div>
                    <input type="hidden" name="idReport" value="<%=obj.getId()%>"/>
                </form> 
            </div>

            <div class="col-xs-0 col-sm-1 col-md-3"></div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="panel-title"><strong><%=paramRequest.getLocaleString("configure")%></strong></div>
            </div>
            <div class="panel-body">
                <div class="row">
                    <form class="col-xs-12 col-sm-10 col-md-6" action="<%=urlAction.setAction("addColumn")%>" method="post">
                        <table style="width: 500px;">
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
                                        <li class="icon-plus"></li> <%=paramRequest.getLocaleString("add")%>
                                    </button>
                                </td></tr>
                        </table>
                    </form>
                </div>
                <div class="row">
                    <%
                        Iterator<ColumnReport> colum = SWBComparator.sortSortableObject(obj.listColumnReports());
                        if (colum.hasNext()) {%>
                    <form action="<%=urlAction.setAction("updateColumn")%>" method="post">
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
                                            <li class="icon-arrow-down"></li>
                                        </a>
                                        <%} else if (contador < total && total > 1) {%>
                                        <a href="<%=urlAction.setAction("moveUp").setParameter("idColumn", colu.getId())%>" 
                                           data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("up")%>">
                                            <li class="icon-arrow-up"></li>
                                        </a>
                                        <a href="<%=urlAction.setAction("moveDown").setParameter("idColumn", colu.getId())%>"
                                           data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("down")%>">
                                            <li class="icon-arrow-down"></li>
                                        </a>
                                        <%} else if (contador == total && total > 1) {%>
                                        <a href="<%=urlAction.setAction("moveUp").setParameter("idColumn", colu.getId())%>" 
                                           data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("up")%>">
                                            <li class="icon-arrow-up"></li>    
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
                                        <button class="btn btn-default btn-sm" type="submit"
                                                data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("save")%>">
                                            <li class="icon-save"></li> <%=paramRequest.getLocaleString("save")%>
                                        </button> 
                                    </td>
                                </tr>

                            </table>
                        </div>
                        <input type="hidden" name="idReport" value="<%=obj.getId()%>"/>
                    </form>
                </div>
                <%}%>
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
    <script>document.getElementById("title").focus();</script> 