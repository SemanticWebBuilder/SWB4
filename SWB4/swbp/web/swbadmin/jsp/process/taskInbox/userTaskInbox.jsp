<%-- 
    Document   : userTaskInbox
    Created on : 2/08/2011, 10:10:59 AM
    Author     : Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
--%>

<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="java.util.Date"%>
<%@page import="org.semanticwb.process.resources.taskinbox.UserTaskInboxResource"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.process.model.MessageStartEvent"%>
<%@page import="org.semanticwb.process.model.StartEventNode"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.ProcessGroup"%>
<%@page import="org.semanticwb.process.model.StartEvent"%>
<%@page import="org.semanticwb.process.model.Instance"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.Activity"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="org.semanticwb.process.model.SubProcessInstance"%>
<%@page import="org.semanticwb.process.model.FlowNodeInstance"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.net.URLEncoder"%>
<!--%@page contentType="text/html" pageEncoding="UTF-8"%-->

<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
User user = paramRequest.getUser();
WebPage statusWp = (WebPage) request.getAttribute("statusWp");
String lang = user.getLanguage();
String sortType = request.getParameter("sort");
String pFilter = request.getParameter("pf");
String sFilter = request.getParameter("sf");
String pNum = request.getParameter("p");
Resource base = (Resource) request.getAttribute("base");
WebSite site = paramRequest.getWebPage().getWebSite();

boolean showPwpLink = false;
if (request.getAttribute("showPWpLink") != null) {
    showPwpLink = (Boolean) request.getAttribute("showPWpLink");
}

boolean allowForward = false;
if (request.getAttribute("allowForward") != null) {
    allowForward = (Boolean) request.getAttribute("allowForward");
}
int maxPages = (Integer) request.getAttribute("maxPages");
int pageNum = 1;
boolean applyFilter = true;

if (user.getLanguage() != null) {
    lang = user.getLanguage();
}
if (pNum != null && !pNum.trim().equals("")) {
    pageNum = Integer.valueOf(pNum);
    if (pageNum > maxPages) {
        pageNum = maxPages;
    }
}
if (sortType != null && !sortType.trim().equals("")) {
    sortType = sortType.trim();
} else {
    sortType = "1";
}
if (pFilter == null || pFilter.trim().equals("")) {
    pFilter = "";
    applyFilter = false;
}
if (sFilter == null || sFilter.trim().equals("")) {
    sFilter = String.valueOf(ProcessInstance.STATUS_PROCESSING);
}

ArrayList<String> cols = new ArrayList<String>();
int i = 1;
while(!base.getAttribute(UserTaskInboxResource.ATT_COLS+i, "").equals("")) {
    String val = base.getAttribute(UserTaskInboxResource.ATT_COLS+i);
    cols.add(val);
    i++;
}

ArrayList<FlowNodeInstance> tinstances = (ArrayList<FlowNodeInstance>) request.getAttribute("instances");
SWBResourceURL optsUrl = paramRequest.getRenderUrl();
optsUrl.setParameter("sort", sortType);
if (applyFilter) {
    optsUrl.setParameter("pf", pFilter);
}
optsUrl.setParameter("sf", String.valueOf(FlowNodeInstance.STATUS_PROCESSING));
SWBResourceURL createPiUrl = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
createPiUrl.setMode(UserTaskInboxResource.MODE_CREATEPI);

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
    if (paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY) {
        %>
        <a href="<%=createPiUrl%>" class="btn btn-sm btn-success swbp-btn-start" data-toggle="modal" data-target="#modalDialog"><span class="fa fa-play-circle fa-lg fa-fw"></span> <%=paramRequest.getLocaleString("createCase")%></a>
        <div class="swbp-left-menu">
            <ul class="list-unstyled">
                <li>
                    <a href="<%=optsUrl%>"><%=paramRequest.getLocaleString("pendingTasks")%></a>
                </li>
                <li>
                    <a href="<%=optsUrl.setParameter("sf", String.valueOf(FlowNodeInstance.STATUS_CLOSED))%>"><%=paramRequest.getLocaleString("closedTasks")%></a>
                </li>
                <li>
                    <a href="<%=optsUrl.setParameter("sf", String.valueOf(FlowNodeInstance.STATUS_ABORTED))%>"><%=paramRequest.getLocaleString("abortedTasks")%></a>
                </li>
            </ul>
        </div>
        <%
    } else {
        if (null != request.getSession(true).getAttribute("msg")) {
            String message = (String) request.getSession(true).getAttribute("msg");
            if (message.startsWith("OK")) {
                String id = message.substring(2);
                %>
                <div class="alert alert-success alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <span class="fa fa-check"></span> <strong><%=paramRequest.getLocaleString("msgCreated")+" "+id%></strong>
                </div>
                <%
            }
            request.getSession(true).removeAttribute("msg");
        }
        %>
        <ul class="list-unstyled list-inline">
            <li>
                <div class="dropdown">
                    <a class="btn btn-default" data-toggle="dropdown">
                        <span class="fa fa-sort-amount-asc"></span> <%=paramRequest.getLocaleString("sortLabel")%> <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li class="dropdown-header" role="menuitem"><%=paramRequest.getLocaleString("sortDate")%></li>
                        <%
                            optsUrl = paramRequest.getRenderUrl();
                            if (applyFilter) {
                                optsUrl.setParameter("pf", pFilter);
                            }
                            optsUrl.setParameter("sf", sFilter);
                        %>
                        <li role="menuitem">
                            <a href="<%=optsUrl.setParameter("sort", "1") %>"><span class="fa fa-sort-numeric-asc"></span> <%=sortType.equals("1")?"<strong>":""%><%=paramRequest.getLocaleString("sortLatest")%><%=sortType.equals("1")?"</strong>":""%></a>
                        </li>
                        <li role="menuitem">
                            <a href="<%=optsUrl.setParameter("sort", "2") %>"><span class="fa fa-sort-numeric-desc"></span> <%=sortType.equals("2")?"<strong>":""%><%=paramRequest.getLocaleString("sortOldest")%><%=sortType.equals("2")?"</strong>":""%></a>
                        </li>
                        <li class="divider" role="menuitem"></li>
                        <li class="dropdown-header" role="menuitem"><%=paramRequest.getLocaleString("sortProcess")%></li>
                        <li role="menuitem">
                            <a href="<%=optsUrl.setParameter("sort", "3") %>"><span class="fa fa-sort-alpha-asc"></span> <%=sortType.equals("3")?"<strong>":""%><%=paramRequest.getLocaleString("sortNameAsc")%><%=sortType.equals("3")?"</strong>":""%></a>
                        </li>
                        <li role="menuitem">
                            <a href="<%=optsUrl.setParameter("sort", "4") %>"><span class="fa fa-sort-alpha-desc"></span> <%=sortType.equals("4")?"<strong>":""%><%=paramRequest.getLocaleString("sortNameDes")%><%=sortType.equals("4")?"</strong>":""%></a>
                        </li>
                    </ul>
                </div>
            </li>
            <li>
                <div class="dropdown">
                    <a class="btn btn-default" data-toggle="dropdown">
                        <span class="fa fa-filter"></span> <%=paramRequest.getLocaleString("filteringLabel")%> <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li class="dropdown-header" role="menuitem"><%=paramRequest.getLocaleString("sortStatus")%></li>
                        <%
                            optsUrl = paramRequest.getRenderUrl();
                            optsUrl.setParameter("sort", sortType);
                            if (applyFilter) {
                                optsUrl.setParameter("pf", pFilter);
                            }
                        %>
                        <li role="menuitem">
                            <a href="<%=optsUrl.setParameter("sf", String.valueOf(FlowNodeInstance.STATUS_PROCESSING))%>"><span class="fa fa-flag-checkered"></span> <%=sFilter.equals(String.valueOf(FlowNodeInstance.STATUS_PROCESSING))?"<strong>":""%><%=paramRequest.getLocaleString("pendingTasks")%><%=sFilter.equals(String.valueOf(FlowNodeInstance.STATUS_PROCESSING))?"</strong>":""%></a>
                        </li>
                        <li role="menuitem">
                            <a href="<%=optsUrl.setParameter("sf", String.valueOf(FlowNodeInstance.STATUS_CLOSED))%>"><span class="fa fa-flag"></span> <%=sFilter.equals(String.valueOf(FlowNodeInstance.STATUS_CLOSED))?"<strong>":""%><%=paramRequest.getLocaleString("closedTasks")%><%=sFilter.equals(String.valueOf(FlowNodeInstance.STATUS_CLOSED))?"</strong>":""%></a>
                        </li>
                        <li role="menuitem">
                            <a href="<%=optsUrl.setParameter("sf", String.valueOf(FlowNodeInstance.STATUS_ABORTED))%>"><span class="fa fa-flag-o"></span> <%=sFilter.equals(String.valueOf(FlowNodeInstance.STATUS_ABORTED))?"<strong>":""%><%=paramRequest.getLocaleString("abortedTasks")%><%=sFilter.equals(String.valueOf(FlowNodeInstance.STATUS_ABORTED))?"</strong>":""%></a>
                        </li>
                    </ul>
                </div>
            </li>
            <li>
            <%
            optsUrl = paramRequest.getRenderUrl(); 
            optsUrl.setParameter("sort", sortType);
            optsUrl.setParameter("sf", sFilter);
            %>
            <select class="form-control" onchange="loadPageUrl('<%=optsUrl%>', 'pf', this.options[this.selectedIndex].value)">
                <option value="" <%=pFilter.equals("")?"selected":""%>><%=paramRequest.getLocaleString("allProcesses")%></option>
                <%
                Iterator<ProcessGroup> itgroups = SWBComparator.sortByDisplayName(ProcessGroup.ClassMgr.listProcessGroups(paramRequest.getWebPage().getWebSite()), lang);
                while (itgroups.hasNext()) {
                    ProcessGroup pgroup = itgroups.next();
                    Iterator<Process> processes = SWBComparator.sortByDisplayName(pgroup.listProcesses(), lang);
                    ArrayList<Process> alProcesses = new ArrayList<Process>();

                    while (processes.hasNext()) {
                        Process process = processes.next();
                        if (process.isValid()) {
                            alProcesses.add(process);
                        }
                    }

                    if (!alProcesses.isEmpty()) {
                        processes = alProcesses.iterator();
                        %>
                        <optgroup label="<%=pgroup.getDisplayTitle(lang)%>">
                        <%
                        while (processes.hasNext()) {
                            Process process = processes.next();
                            String selected = "";
                            if (pFilter.equals(process.getId())) selected = "selected";
                                %>
                                <option value="<%=process.getId()%>" <%=selected%>><%=process.getDisplayTitle(lang)%></option>
                                <%
                            }
                            %>
                        </optgroup>
                        <%
                    }
                }
                %>
            </select>
            </li>
            <li>
                <a href="<%=createPiUrl%>" class="btn btn-default hidden-lg" data-toggle="modal" data-target="#modalDialog"><span class="fa fa-play-circle fa-lg fa-fw"></span> <%=paramRequest.getLocaleString("createCase")%></a>
            </li>
        </ul>
        <% if (tinstances != null && tinstances.size() > 0) { %>
            <div class="table-responsive">
                <table class="table table-hover swbp-table">
                    <thead>
                        <tr>
                            <%Iterator<String> itCols = cols.iterator();
                            while(itCols.hasNext()) {
                                String val = itCols.next();
                                String []conf = val.split("\\|");
                                if (conf.length == 2) {
                                    %><th><%=conf[1]%></th><%
                                }
                            }
                            %>
                        </tr>
                    </thead>
                    <tbody>
                        <%Iterator<FlowNodeInstance> instances = tinstances.iterator();
                        while(instances.hasNext()) {
                            FlowNodeInstance instance = instances.next();
                            if (instance.getProcessInstance().getCreator() != null) {
                                if (instance.getProcessInstance().getStatus() == Instance.STATUS_PROCESSING) {
                                    String status = "--";
                                    %>
                                    <tr>
                                        <%
                                        itCols = cols.iterator();
                                        while(itCols.hasNext()) {
                                            String val = itCols.next();
                                            String []conf = val.split("\\|");
                                            if (conf.length == 2) {
                                                if (conf[0].equals(UserTaskInboxResource.COL_IDPROCESS)) {
                                                    %><td><%=instance.getProcessInstance().getId()%><%
                                                } else if (conf[0].equals(UserTaskInboxResource.COL_IDTASK)) {
                                                    %><td><%=instance.getId()%><%
                                                } else if (conf[0].equals(UserTaskInboxResource.COL_NAMEPROCESS)) {
                                                    String pName = instance.getFlowNodeType().getProcess().getDisplayTitle(lang);
                                                    %><td><%=pName%><%
                                                } else if (conf[0].equals(UserTaskInboxResource.COL_NAMETASK)) {
                                                    %><td><%=instance.getFlowNodeType().getDisplayTitle(lang)%><%
                                                } else if (conf[0].equals(UserTaskInboxResource.COL_STARTPROCESS)) {
                                                    %><td><%=SWBUtils.TEXT.getStrDate(instance.getProcessInstance().getCreated(), lang, "dd/mm/yy - hh:%m")%><%
                                                } else if (conf[0].equals(UserTaskInboxResource.COL_STARTTASK)) {
                                                    %><td><%=SWBUtils.TEXT.getStrDate(instance.getCreated(), lang, "dd/mm/yy - hh:%m")%><%
                                                } else if (conf[0].equals(UserTaskInboxResource.COL_ENDPROCESS)) {
                                                    String ended = "--";
                                                    if (instance.getProcessInstance().getStatus() == Instance.STATUS_CLOSED || instance.getProcessInstance().getStatus() == Instance.STATUS_ABORTED) {
                                                        if (instance.getProcessInstance().getEnded() != null) {
                                                            ended = SWBUtils.TEXT.getStrDate(instance.getProcessInstance().getEnded(), lang, "dd/mm/yy - hh:%m");
                                                        }
                                                    }
                                                    %><td><%=ended%><%
                                                } else if (conf[0].equals(UserTaskInboxResource.COL_ENDTASK)) {
                                                    String ended = "--";
                                                    if (instance.getStatus() == Instance.STATUS_CLOSED || instance.getStatus() == Instance.STATUS_ABORTED) {
                                                        if (instance.getEnded() != null) {
                                                            ended = SWBUtils.TEXT.getStrDate(instance.getEnded(), lang, "dd/mm/yy - hh:%m");
                                                        }
                                                    }
                                                    %><td><%=ended%><%
                                                } else if (conf[0].equals(UserTaskInboxResource.COL_CREATORPROCESS)) {
                                                    String screator = paramRequest.getLocaleString("autoCreation");
                                                    User creator = instance.getProcessInstance().getCreator();
                                                    if (creator != null) {
                                                        if (creator.getFullName() != null && !creator.getFullName().trim().equals("")) {
                                                            screator = creator.getFullName();
                                                        } else {
                                                            screator = creator.getLogin();
                                                        }
                                                    }
                                                    %><td><%=screator%><%
                                                } else if (conf[0].equals(UserTaskInboxResource.COL_CREATORTASK)) {
                                                    String screator = paramRequest.getLocaleString("autoCreation");
                                                    User creator = instance.getCreator();
                                                    if (creator != null) {
                                                        if (creator.getFullName() != null && !creator.getFullName().trim().equals("")) {
                                                            screator = creator.getFullName();
                                                        } else {
                                                            screator = creator.getLogin();
                                                        }
                                                    }
                                                    %><td><%=screator%><%
                                                } else if (conf[0].equals(UserTaskInboxResource.COL_STATUSPROCESS)) {
                                                    int st = instance.getProcessInstance().getStatus();
                                                    if (st == Instance.STATUS_ABORTED) {
                                                        status = paramRequest.getLocaleString("pStatusAborted");
                                                    } else if (st == Instance.STATUS_CLOSED) {
                                                        status = paramRequest.getLocaleString("pStatusClosed");
                                                    } if (st == Instance.STATUS_INIT) {
                                                        status = paramRequest.getLocaleString("pStatusInit");
                                                    } if (st == Instance.STATUS_OPEN || st == Instance.STATUS_PROCESSING) {
                                                        status = paramRequest.getLocaleString("pStatusPending");
                                                    } if (st == Instance.STATUS_STOPED) {
                                                        status = paramRequest.getLocaleString("pStatusStopped");
                                                    }
                                                    %><td><%=status%><%
                                                } else if (conf[0].equals(UserTaskInboxResource.COL_STATUSTASK)) {
                                                    int st = instance.getStatus();
                                                    if (st == Instance.STATUS_ABORTED) {
                                                        status = paramRequest.getLocaleString("tStatusAborted");
                                                    } else if (st == Instance.STATUS_CLOSED) {
                                                        status = paramRequest.getLocaleString("tStatusClosed");
                                                    } if (st == Instance.STATUS_INIT) {
                                                        status = paramRequest.getLocaleString("tStatusInit");
                                                    } if (st == Instance.STATUS_OPEN || st == Instance.STATUS_PROCESSING) {
                                                        status = paramRequest.getLocaleString("tStatusPending");
                                                    } if (st == Instance.STATUS_STOPED) {
                                                        status = paramRequest.getLocaleString("tStatusStopped");
                                                    }
                                                    %><td><%=status%><%
                                                } else if (conf[0].equals(UserTaskInboxResource.COL_ACTIONS)) {%>
                                                    <td class="swbp-actions">
                                                        <%UserTask utask = (UserTask) instance.getFlowNodeType();
                                                        if (instance.getStatus() == ProcessInstance.STATUS_PROCESSING) {%>
                                                            <a href="<%=utask.getTaskWebPage().getUrl()%>?suri=<%=instance.getEncodedURI()%>" class="btn btn-default" title="<%=paramRequest.getLocaleString("actTake")%>"><span class="fa fa-external-link-square"></span></a>
                                                            <%if (allowForward && instance.getAssignedto() != null) {
                                                                SWBResourceURL forward = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(UserTaskInboxResource.MODE_FWD);
                                                                %><a href="<%=forward.setParameter("suri", instance.getEncodedURI())%>" class="btn btn-default" data-toggle="modal" data-target="#modalDialog" title="<%=paramRequest.getLocaleString("actFwd")%>" ><span class="fa fa-exchange"></span></a><%
                                                            }
                                                        }
                                                        if (statusWp != null) {%>
                                                            <a href="<%=statusWp.getUrl()%>?suri=<%=instance.getProcessInstance().getProcessType().getEncodedURI()%>" class="btn btn-default" title="<%=paramRequest.getLocaleString("actMap")%>"><span class="fa fa-cogs"></span></a><%
                                                        }
                                                        if(showPwpLink) {
                                                            SWBResourceURL detailUrl = paramRequest.getRenderUrl().setMode(UserTaskInboxResource.MODE_PROCESSDETAIL);
                                                            detailUrl.setParameter("suri", instance.getFlowNodeType().getProcess().getURI());%>
                                                            <a href="<%=detailUrl%>" class="btn btn-default" title="<%=paramRequest.getLocaleString("actDetail")%>"><span class="fa fa-bar-chart-o"></span></a><%
                                                        }
                                                        %>
                                                    </td>
                                                    <%
                                                } else if (conf[0].equals(UserTaskInboxResource.COL_TASKSUBJECT)) {
                                                    String subject = "--";
                                                    if (instance.getSubject() != null) {
                                                        subject = instance.getSubject();
                                                    }
                                                    %><td><%=subject%></td><%
                                                } else if (conf[0].equals(UserTaskInboxResource.COL_FLAGTASK)) {
                                                    UserTask utask = (UserTask)instance.getFlowNodeType();
                                                    int delay = utask.getNotificationTime();
                                                    boolean delayed = false;
                                                    //String delayTitle = paramRequest.getLocaleString("lblOntime");

                                                    if (delay > 0) {
                                                        long today = System.currentTimeMillis();
                                                        long cr = instance.getCreated().getTime();
                                                        if (today - cr > (1000*60*delay)) {
                                                            delayed = true;
                                                            //delayTitle = paramRequest.getLocaleString("lblDelayed");
                                                        }
                                                    }
                                                    %>
                                                    <td class="swbp-status">
                                                    <%
                                                    if (delayed) {
                                                        %><span class="swbp-delayed"><%
                                                    } else {
                                                        %><span class="swbp-ontime"><%
                                                    }
                                                    %><span class="fa fa-clock-o"></span></span>
                                                    </td><%
                                                }
                                            }
                                        }
                                        %>
                                    </tr>
                                <%
                                }
                            }
                        }%>
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
                          nav.setParameter("sf", sFilter);
                          nav.setParameter("sort", sortType);
                          
                          if (applyFilter) {
                              nav.setParameter("pf", pFilter);
                          }

                          if (sliceIdx != 1) {
                              nav.setParameter("p", String.valueOf(pageNum-1));
                              %><li><a href="<%=nav%>">&laquo;</a></li><%
                          }

                          for(int k = start; k <= end; k++) {
                              nav.setParameter("p", String.valueOf(k));
                              %>
                              <li <%=(k==pageNum?"class=\"active\"":"")%>><a href="<%=nav%>"><%=k%></a></li>
                              <%
                          }

                          if (end < maxPages) {
                              nav.setParameter("p", String.valueOf(pageNum+1));
                              %><li><a href="<%=nav%>">&raquo;</a></li><%
                          }
                      }%>
                    </ul>
                </div>
            </div>
        <%
        } else {
            %>
            <div class="alert alert-warning">
                <span class="fa fa-exclamation-triangle"></span> <strong><%=paramRequest.getLocaleString("noTasks")%></strong>
            </div>
            <%
        }
    }
}
%>