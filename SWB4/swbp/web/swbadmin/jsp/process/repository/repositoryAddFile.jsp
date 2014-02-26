<%-- 
    Document   : repoitoryAddFile.jsp
    Created on : 3/09/2013, 11:41:28 AM
    Author     : Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
--%>

<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.process.model.ItemAwareStatus"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.VersionInfo"%>
<%@page import="org.semanticwb.process.model.RepositoryURL"%>
<%@page import="org.semanticwb.process.model.RepositoryFile"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.process.model.RepositoryElement"%>
<%@page import="org.semanticwb.process.resources.ProcessFileRepository"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
String validFiles = (String) request.getAttribute(ProcessFileRepository.VALID_FILES);
WebSite site = paramRequest.getWebPage().getWebSite();
User user = paramRequest.getUser();
RepositoryElement re = null;
String lang ="es";
String actualStatus ="";

if (user != null && user.getLanguage() != null) {
    lang = user.getLanguage();
}

String fid = request.getParameter("fid");
String type = request.getParameter("type");
VersionInfo vi = null;

if (fid != null && fid.length() > 0) {
    if (type != null) {
        if ("url".equals(type)) {
            re = RepositoryURL.ClassMgr.getRepositoryURL(fid, site);
        } else if ("file".equals(type)) {
            re = RepositoryFile.ClassMgr.getRepositoryFile(fid, site);
        }
    }
}

if (re != null) {
    vi = re.getLastVersion();
    actualStatus = re.getStatus()!=null?re.getStatus().getId():"";
}

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
    SWBResourceURL viewURL = paramRequest.getRenderUrl().setMode(SWBParamRequest.Mode_VIEW);
    SWBResourceURL createURL = paramRequest.getActionUrl().setAction(ProcessFileRepository.ACT_NEWFILE);
    
    String comments = "";
    String description = "";
    if (re != null && vi.getVersionComment() != null) {
        comments = vi.getVersionComment();
    }
    
    if (re != null && re.getDisplayDescription(lang) != null) description = re.getDisplayDescription(lang);
    %>
    <script src="<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/repository/fileRepositoryUtils.js" charset="utf-8"></script>
    <div class="col-lg-offset-3 col-lg-6">
    <div class="panel panel-default swbp-panel">
        <div class="panel-heading swbp-panel-title">
            <h1 class="panel-title"><strong><%=paramRequest.getLocaleString("msgAdd")%>&nbsp;<%=re != null?paramRequest.getLocaleString("msgVersionOf")+" ":""%> <%=(re != null && re instanceof RepositoryURL)?paramRequest.getLocaleString("msgDocLink"):paramRequest.getLocaleString("msgFile")%></strong></h1>
        </div>
        <form class="form-horizontal" role="form" action="<%=createURL%>" method="post" enctype="multipart/form-data">
            <div class="panel-body">
                <%if (re != null) {%>
                <input type="hidden" name="fid" value="<%=re.getURI()%>"/>
                <%}%>
                <div class="form-group">
                    <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("msgTitle")%> *</label>
                    <div class="col-lg-6">
                        <input type="text" name="ftitle" id="ftitle" value="<%=(re != null)?re.getDisplayTitle(lang):""%>" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("msgDescription")%> *</label>
                    <div class="col-lg-6">
                        <textarea name="fdescription" id="fdescription" class="form-control"><%=description%></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("msgComments")%></label>
                    <div class="col-lg-6">
                        <textarea name="fcomment" id="fcomment" class="form-control"><%=comments%></textarea>
                    </div>
                </div>
                <%if (re == null) {%>
                    <div class="form-group">
                        <label class="col-lg-4 control-label"><%=paramRequest.getLocaleString("msgFileType")%> *</label>
                        <div class="col-lg-6">
                            <div class="radio-inline">
                              <label>
                                  <input type="radio" id="fileToggleRadio" onclick="toggleShow('fileSelect', true);toggleShow('linkSelect', false);" checked name="hftype" value="file"> <%=paramRequest.getLocaleString("msgFile")%>
                              </label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" id="urlToggleRadio" onclick="toggleShow('fileSelect', false);toggleShow('linkSelect', true);"name="hftype" value="url"> <%=paramRequest.getLocaleString("lblLink")%>
                                </label>
                            </div>
                        </div>
                    </div>
                <%
                } else {
                    %><input type="hidden" name="hftype" id="hftype" value="<%=type%>"/><%
                }
                if (re == null || (re != null && re instanceof RepositoryFile)) {%>
                    <div id="fileSelect" class="form-group">
                        <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("msgFile")%> *</label>
                        <div class="col-lg-6">
                            <div class="input-group">
                                <span class="input-group-btn">
                                    <span class="btn btn-success btn-file">
                                        <%=paramRequest.getLocaleString("msgSearchFile")%> <input type="file" name="ffile" id="ffile" class="form-control" />
                                    </span>
                                </span>
                                <input type="text" class="form-control" disabled/>
                            </div>
                        </div>
                    </div>
                <%
                }
                if (re == null || (re != null && re instanceof RepositoryURL)) {
                    String val = "";
                    if (re != null) {
                        val = vi.getVersionFile().startsWith("http://")?vi.getVersionFile().replace("http://", ""):vi.getVersionFile();
                    }
                    %>
                    <div id="linkSelect" class="form-group">
                        <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("lblLink")%> *</label>
                        <div class="col-lg-6">
                            <div class="input-group">
                                <span class="input-group-addon">http://</span>
                                <input type="text" name="extfile" id="extfile" value="<%=val%>" class="form-control" />
                                </span>
                            </div>
                        </div>
                    </div>
                <%}%>
                <div class="form-group">
                    <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("msgTHStatus")%></label>
                    <div class="col-lg-6">
                        <select name="itemAwStatus" id="itemAwStatus" class="form-control">
                            <option value="" <%=(actualStatus.equals("")?"selected":"")%>><%=paramRequest.getLocaleString("msgSelNone")%></option>
                            <%
                            Iterator<ItemAwareStatus> ititwstst = SWBComparator.sortByDisplayName(ItemAwareStatus.ClassMgr.listItemAwareStatuses(site), lang);
                            while (ititwstst.hasNext()) {
                                ItemAwareStatus itemAwareStatus = ititwstst.next();
                                %>
                                <option value="<%=itemAwareStatus.getId()%>" <%=(actualStatus.equals(itemAwareStatus.getId())?"selected":"")%>><%=itemAwareStatus.getDisplayTitle(lang)%></option>
                                <%
                            }
                            %>
                        </select>
                    </div>
                </div>
                <%if (re != null) {%>
                    <div class="form-group">
                        <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("msgVersion")%> *</label>
                        <div class="col-lg-6">
                            <select name="newVersion" id="itemAwStatus" class="form-control">
                                <%
                                float fver = Float.parseFloat(vi.getVersionValue());
                                fver = fver + 0.1F;

                                int iver = (int) fver;
                                iver = iver + 1;
                                %>
                                <option value="fraction"><%=fver%></option>
                                <option value="nextInt"><%=(float)iver%></option>
                            </select>
                        </div>
                    </div>
                <%}%>
                </div>
                <div class="panel-footer text-right">
                    <!--label for="" class="col-lg-2"></label-->
                    <!--div class="col-lg-3 text-right"-->
                        <button type="button" onclick="window.location='<%=viewURL%>';" class="btn btn-default"><%=paramRequest.getLocaleString("msgBTNCancel")%></button>
                        <button type="button" class="btn btn-success" onclick="if(checkfiles('<%=validFiles%>')){this.form.submit();} else {return false;};"><%=paramRequest.getLocaleString("msgAdd")%></button>
                    <!--/div-->
                </div>
            </form>
        </div>
    </div>
    <script>
        <%if (re == null) {%>
        function toggleShow(elementId, show) {
            var el = document.getElementById(elementId);
            if (el !== null) {
                if (show) {
                    el.style.display="block";
                } else {
                    el.style.display="none";
                }
            }
        }
        
        toggleShow("linkSelect", false);
        <%}%>
        
        function checkfiles(pExt) {
            var ftit = document.getElementById('ftitle');
            var ftval = ftit.value;
            ftval = ftval.replace(' ' , '');
            if(ftval.length === 0) {
                alert('<%=paramRequest.getLocaleString("msgMissingTitle")%>');
                ftit.focus();
                return false;
            }
            
            var fdesc = document.getElementById('fdescription');
            ftval = fdesc.value;
            ftval = ftval.replace(' ' , '');
            if(ftval.length === 0) {
                alert('<%=paramRequest.getLocaleString("msgMissingDesc")%>');
                fdesc.focus();
                return false;
            }
            <%if (re == null) {%>
                var fileradio = document.getElementById('fileToggleRadio');
                var ftype = fileradio.checked?"file":"url";
            <%} else {%>
                var ftype = document.getElementById('hftype').value;
            <%}%>
            if(ftype === "url") {
                var urlfilec = document.getElementById('extfile');
                var urlfile = urlfilec.value;
                urlfile = urlfile.replace(' ' , '');
                if(urlfile.length === 0) {
                    alert('<%=paramRequest.getLocaleString("msgMissingLink")%>');
                    urlfilec.focus();
                    return false;
                } else {
                    return true;
                }
            } else {
                var fup = document.getElementById('ffile');
                var fileName = fup.value;
                if(fileName.length === 0) {
                    alert('<%=paramRequest.getLocaleString("msgMissingFile")%>');
                    fup.focus();
                    return false;
                }
                if(isFileType(fileName, pExt)) {
                    return true;
                } else {
                    var ptemp = pExt;
                    ptemp = ptemp.replace('|',',');
                    alert('<%=paramRequest.getLocaleString("msgBadFileType")%> '+ptemp);
                    fup.focus();
                    return false;
                }
            }
        }

        function isFileType(pFile, pExt) {
            if(pFile.length > 0 && pExt.length > 0) {
                var swFormat=pExt + '|';
                var sExt=pFile.substring(pFile.indexOf('.')).toLowerCase();
                var sType='';
                while(swFormat.length > 0 ) {
                    sType= swFormat.substring(0, swFormat.indexOf('|'));
                    if(sExt.indexOf(sType)!==-1) return true;
                    swFormat=swFormat.substring(swFormat.indexOf('|')+1);
                }
                while(pExt.indexOf('|')!==-1) pExt=pExt.replace('|',',');
                    return false;
            } else {
                return true;
            }
        }
    </script>
    <%
}
%>