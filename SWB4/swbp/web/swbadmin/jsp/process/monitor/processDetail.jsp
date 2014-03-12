<%-- 
    Document   : userTaskInboxDetail
    Created on : 4/07/2013, 10:14:08 PM
    Author     : Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
--%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.process.model.Activity"%>
<%@page import="org.semanticwb.process.model.ProcessSite"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.process.model.SWBProcessMgr"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.process.model.FlowNodeInstance"%>
<%@page import="org.semanticwb.process.resources.taskinbox.UserTaskInboxResource"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticOntology"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
WebSite site = paramRequest.getWebPage().getWebSite();
User user = paramRequest.getUser();
Resource base = paramRequest.getResourceBase();

String lang = "es";
String pNum = request.getParameter("p");
String suri = request.getParameter("suri");
boolean showGraphs = false;
if (base != null && base.getAttribute(UserTaskInboxResource.ATT_SHOWPERFORMANCE,"").equals("yes")) {
    showGraphs = true;
}

String engine = "d3";

int pageNum = 1;
if (user != null && user.getLanguage() != null) {
    lang = user.getLanguage();
}
SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();

if (!user.isSigned()) {
    if (paramRequest.getCallMethod() == SWBParamRequest.Call_CONTENT) {
        %>
        <div class="alert alert-block alert-danger fade in">
            <h4><span class="fa fa-ban"></span> <%=paramRequest.getLocaleString("msgNoAccessTitle")%></h4>
            <p><%=paramRequest.getLocaleString("msgNoAccess")%></p>
            <p>
                <a class="btn btn-default" href="/login/<%=site.getId()%>/<%=paramRequest.getWebPage().getId()%>"><%=paramRequest.getLocaleString("btnLogin")%></a>
            </p>
        </div>
        <%
    }
} else {
    ArrayList<ProcessInstance> tinstances = (ArrayList<ProcessInstance>) request.getAttribute("instances");
    SWBResourceURL viewUrl = paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW);
    int maxPages = (Integer) request.getAttribute("maxPages");

    if (pNum != null && !pNum.trim().equals("")) {
        pageNum = Integer.valueOf(pNum);
        if (pageNum > maxPages) {
            pageNum = maxPages;
        }
    }

    Process p = (Process)ont.getGenericObject(suri);
    if (p == null) {
        %><script>window.location='<%=viewUrl%>';</script><%
    } else {
        %>
        <h2><a class="btn" data-toggle="tooltip" data-placement="bottom" title="Regresar" href="<%=viewUrl%>"><span class="fa fa-reply"></span></a><%=p.getTitle()%></h2>
        <%
        if (tinstances != null && !tinstances.isEmpty()) {
            %>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><strong><%=paramRequest.getLocaleString("lblPerformance")%></strong></h3>
                </div>
                <div class="panel-body">
                    <%
                    if (engine.equals("google")) {
                        %><jsp:include page="/swbadmin/jsp/process/taskInbox/userTaskInboxGoogleGraphs.jsp" flush="true"/><%
                    } else {
                        %><jsp:include page="/swbadmin/jsp/process/taskInbox/userTaskInboxD3Graphs.jsp" flush="true"/><%
                    }
                    %>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><strong><%=paramRequest.getLocaleString("lblInstances")%></strong></h3>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-hover swbp-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th><%=paramRequest.getLocaleString("lblCreator")%></th>
                                    <th><%=paramRequest.getLocaleString("lblStatus")%></th>
                                    <th><%=paramRequest.getLocaleString("pStatusInit")%></th>
                                    <th><%=paramRequest.getLocaleString("pStatusClosed")%></th>
                                    <th><%=paramRequest.getLocaleString("lblActiveTasks")%></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%Iterator<ProcessInstance> it = tinstances.iterator();
                                while (it.hasNext()) {
                                    ProcessInstance pi = it.next();
                                    String status = "--";

                                    if (pi.getStatus() == ProcessInstance.STATUS_PROCESSING) status = paramRequest.getLocaleString("pStatusPending");
                                    if (pi.getStatus() == ProcessInstance.STATUS_ABORTED) status = paramRequest.getLocaleString("pStatusAborted");
                                    if (pi.getStatus() == ProcessInstance.STATUS_CLOSED) status = paramRequest.getLocaleString("pStatusClosed");
                                    %>
                                    <tr>
                                        <td><%=pi.getId()%></td>
                                        <td><%=pi.getCreator()==null?"--":pi.getCreator().getFullName()%></td>
                                        <td><%=status%></td>
                                        <td><%=SWBUtils.TEXT.getStrDate(pi.getCreated(), lang, "dd/mm/yy - hh:%m:ss")%></td>
                                        <td><%=pi.getEnded()==null?"--":SWBUtils.TEXT.getStrDate(pi.getEnded(), lang, "dd/mm/yy - hh:%m:ss")%></td>
                                        <td>
                                            <%
                                            if (pi.getStatus() == ProcessInstance.STATUS_PROCESSING) {
                                                Iterator<FlowNodeInstance> actit = pi.listAllFlowNodeInstance();
                                                ArrayList<FlowNodeInstance> activities = new ArrayList<FlowNodeInstance>();
                                                if (actit.hasNext()) {
                                                    while(actit.hasNext()) {
                                                        FlowNodeInstance fni = actit.next();
                                                        if (fni.getFlowNodeType() instanceof Activity && fni.getStatus() == FlowNodeInstance.STATUS_PROCESSING) {
                                                            activities.add(fni);
                                                        }
                                                    }
                                                }

                                                if (!activities.isEmpty()) {
                                                    actit = activities.iterator();
                                                    %>
                                                    <ul>
                                                        <%while(actit.hasNext()) {
                                                            FlowNodeInstance fni = actit.next();
                                                            %><li><%=fni.getFlowNodeType().getTitle()%></li><%
                                                        }
                                                        %>
                                                    </ul>
                                                  <%
                                                } else {
                                                    %>--<%
                                                }
                                            } else {
                                                %>--<%
                                            }
                                            %>
                                        </td>
                                    </tr>
                                    <%
                                } 
                                %>
                            </tbody>
                        </table>
                    </div>
                    <div class="swbp-pagination">
                        <span class="swbp-pagination-info pull-left"><%=paramRequest.getLocaleString("pagPage")%> <%=pageNum%> <%=paramRequest.getLocaleString("pagDelim")%> <%=maxPages%></span>
                        <%if (maxPages > 1) {%>
                          <div class="swbp-pagination-nav pull-right">
                              <ul class="pagination pagination-sm">
                                <%
                                  int pagSlice = 5;
                                  int sliceIdx = 1;
                                  int start = 1;
                                  int end = pagSlice * sliceIdx;

                                  if (pageNum > end) {
                                      do {
                                          sliceIdx++;
                                          end = pagSlice * sliceIdx;
                                      } while(pageNum > end);
                                  }
                                  end = pagSlice * sliceIdx;

                                  if (end > maxPages) {
                                      end = maxPages;
                                  }

                                  start = (end-pagSlice)+1;
                                  if (start < 1) {
                                      start = 1;
                                  }

                                  SWBResourceURL nav = paramRequest.getRenderUrl();
                                  nav.setParameter("suri", suri);

                                  if (sliceIdx != 1) {
                                      %><li><a href="<%=nav.setParameter("p", String.valueOf(pageNum-1))%>">&laquo;</a></li><%
                                  }

                                  for(int k = start; k <= end; k++) {
                                      %>
                                      <li <%=(k==pageNum?"class=\"active\"":"")%>><a href="<%=nav.setParameter("p", String.valueOf(k))%>"><%=k%></a></li>
                                      <%
                                  }

                                  if (end < maxPages) {
                                      %><li><a href="<%=nav.setParameter("p", String.valueOf(pageNum+1))%>">&raquo;</a></li><%
                                  }
                              }%>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        <%
        } else {
            %>
            <div class="alert alert-warning">
                <span class="fa fa-exclamation-triangle"></span> <strong><%=paramRequest.getLocaleString("msgNoInstances")%></strong>
            </div>
            <%
        }
    }
}
%>