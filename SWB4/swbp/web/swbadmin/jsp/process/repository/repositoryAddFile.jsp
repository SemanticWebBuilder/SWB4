<%-- 
    Document   : userTaskInboxNewCase
    Created on : 3/09/2013, 11:41:28 AM
    Author     : Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
--%>

<%@page import="org.semanticwb.process.resources.ProcessFileRepository"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
String validFiles = (String) request.getAttribute(ProcessFileRepository.VALID_FILES);
User user = paramRequest.getUser();
WebSite site = paramRequest.getWebPage().getWebSite();        
if (!user.isSigned()) {
    if (paramRequest.getCallMethod() == SWBParamRequest.Call_CONTENT) {
        %>
        <div class="alert alert-block alert-danger fade in">
            <h4><i class="icon-ban-circle"></i> <%=paramRequest.getLocaleString("msgNoAccessTitle")%></h4>
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
    %>
    <h2>Agregar archivo</h2>
    <form class="form-horizontal" role="form" action="<%=createURL%>" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="" class="col-lg-2 control-label">Título *</label>
            <div class="col-lg-3">
                <input type="text" name="ftitle" id="ftitle" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label for="" class="col-lg-2 control-label">Descripción *</label>
            <div class="col-lg-3">
                <textarea name="fdescription" id="fdescription" class="form-control"></textarea>
            </div>
        </div>
        <div class="form-group">
            <label for="" class="col-lg-2 control-label">Comentario</label>
            <div class="col-lg-3">
                <textarea name="fcomment" id="fcomment" class="form-control"></textarea>
            </div>
        </div>
        <div class="form-group">
            <label for="" class="col-lg-2 control-label">Estatus</label>
            <div class="col-lg-3">
                <select name="itemAwStatus" id="itemAwStatus" class="form-control"></select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2 control-label">Tipo de archivo *</label>
            <div class="col-lg-3">
                <div class="radio-inline">
                  <label>
                      <input type="radio" id="fileToggleRadio" checked name="hftype" value="file"> Archivo
                  </label>
                </div>
                <div class="radio-inline">
                    <label>
                        <input type="radio" id="urlToggleRadio" name="hftype" value="url"> Enlace Web
                    </label>
                </div>
            </div>
        </div>
        <div id="fileSelect" class="form-group">
            <label for="" class="col-lg-2 control-label">Archivo *</label>
            <div class="col-lg-3">
                <div class="input-group">
                    <span class="input-group-btn">
                        <span class="btn btn-success btn-file">
                            Browse... <input type="file" name="ffile" id="ffile" class="form-control" />
                        </span>
                    </span>
                    <input type="text" class="form-control" disabled/>
                </div>
            </div>
        </div>
        <div id="linkSelect" class="form-group" style="display:none">
            <label for="" class="col-lg-2 control-label">Dirección del enlace *</label>
            <div class="col-lg-3">
                <div class="input-group">
                    <span class="input-group-addon">http://</span>
                        <input type="text" name="extfile" id="extfile" class="form-control" />
                    </span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="" class="col-lg-2"></label>
            <div class="col-lg-3 text-right">
                <button type="button" onclick="window.location='<%=viewURL%>';" class="btn btn-default">Cancelar</button>
                <button type="button" class="btn btn-success" onclick="if(checkfiles('<%=validFiles%>')){this.form.submit();} else {return false;};">Agregar</button>
            </div>
        </div>
    </form>
    <script>
        $("#urlToggleRadio").on('click', function() {
           $("#linkSelect").css("display","block");
           $("#fileSelect").css("display","none");
        });

        $("#fileToggleRadio").on('click', function() {
           $("#fileSelect").css("display","block");
           $("#linkSelect").css("display","none");
        });
        
        function checkfiles(pExt) {
            var ftit = document.getElementById('ftitle');
            var ftval = ftit.value;
            ftval = ftval.replace(' ' , '');
            if(ftval.length === 0) {
                alert('Falta poner el titulo del archivo.');
                ftit.focus();
                return false;
            }
            
            var fdesc = document.getElementById('fdescription');
            ftval = fdesc.value;
            ftval = ftval.replace(' ' , '');
            if(ftval.length === 0) {
                alert('Falta poner la descripcion del archivo.');
                fdesc.focus();
                return false;
            }
            var fileradio = document.getElementById('fileToggleRadio');
            var ftype = fileradio.checked?"file":"url";
            if(ftype === "url") {
                var urlfilec = document.getElementById('extfile');
                var urlfile = urlfilec.value;
                urlfile = urlfile.replace(' ' , '');
                if(urlfile.length === 0) {
                    alert('Falta poner la liga del archivo externo.');
                    urlfilec.focus();
                    return false;
                } else {
                    return true;
                }
            } else {
                var fup = document.getElementById('ffile');
                var fileName = fup.value;
                if(fileName.length === 0) {
                    alert('Falta seleccionar el archivo.');
                    fup.focus();
                    return false;
                }
                if(isFileType(fileName, pExt)) {
                    return true;
                } else {
                    var ptemp = pExt;
                    ptemp = ptemp.replace('|',',');
                    alert('Selecciona archivos de tipo '+ptemp+' unicamente.');
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