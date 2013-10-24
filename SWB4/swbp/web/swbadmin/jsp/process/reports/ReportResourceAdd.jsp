<%-- 
    Document   : ReportResourceAdd
    Created on : 11/03/2013, 05:23:28 PM
    Author     : carlos.alvarez
--%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.process.resources.reports.Report"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.portal.SWBFormButton"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page contentType="text/html"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SWBFormMgr tipo = new SWBFormMgr(Report.sclass, paramRequest.getWebPage().getWebSite().getSemanticObject(), SWBFormMgr.MODE_CREATE);
    String lang = "";
    if (paramRequest.getUser() != null) {
        lang = paramRequest.getUser().getLanguage();
    }
    tipo.setType(SWBFormMgr.TYPE_DOJO);
    tipo.setLang(lang);
%>
<div class="row">
    <div class="col-xs-0 col-sm-1 col-md-3"></div>
    <div class="col-xs-12 col-sm-10 col-md-6">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="panel-title"><strong><%=paramRequest.getLocaleString("add") + " " + paramRequest.getLocaleString("report")%></strong></div>
            </div>
            <form action="<%=paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)%>"
                  ethod="post" class="form-horizontal" role="form" enctype="multipart/form-data">
                <div class="panel-body">
                    <%out.println(tipo.getFormHiddens());%>
                    <div class="form-group">
                        <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("title")%> *</label>
                        <div class="col-lg-6">
                            <input type="text" name="title" id="title" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("process")%> *</label>
                        <div class="col-lg-6">
                            <%
                                String processName = tipo.renderElement(request, Report.rep_processName, SWBFormMgr.MODE_CREATE);
                                processName = processName.replace("<select ", "<select class=\"form-control\" ");
                                processName = processName.replace("dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" ", "");
                                out.print(processName);
                            %>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("pagingSize")%> *</label>
                        <div class="col-lg-6">
                            <input type="text" name="pagingSize" class="form-control" placeholder="<%=paramRequest.getLocaleString("pagingSize")%>">
                        </div>
                    </div>

                </div>
                <div class="panel-footer text-right">
                    <a class="btn btn-default btn-sm"
                       onclick="javascript:document.back.submit();"
                       data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("back")%>">
                        <li class="fa fa-mail-reply"></li> Regresar
                    </a>
                    <button class="btn btn-success btn-sm" type="submit"
                            data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("save")%>">
                        <li class="fa fa-save"></li> <%=paramRequest.getLocaleString("save")%>
                    </button>
                </div>
            </form>
        </div>  
    </div>
    <div class="col-xs-0 col-sm-1 col-md-3"></div>
</div>
<form method="post" action="<%=paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)%>" name="back"></form>
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
                           dojo.require("dojox.form.ValidationTextBox");
</script>
<script>document.getElementById("title").focus();</script> 