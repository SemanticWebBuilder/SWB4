<%@page import="com.infotec.eworkplace.swb.CentroCosto"%>
<%@page import="mx.com.infotec.intranet.login.Services"%>
<%@page import="com.infotec.eworkplace.swb.formelements.Currency"%>
<%@page import="org.semanticwb.model.FormElement"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.model.SWBClass"%>
<%@page import="org.semanticwb.process.model.ItemAwareReference"%>
<%@page import="com.infotec.rh.syr.swb.SeguimientoSolicitudRecurso"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.semanticwb.portal.SWBForms"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="com.infotec.cvi.swb.SolicitudRecurso"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.SWBProcessFormMgr"%>
<%@page import="java.util.Date"%>
<%@page import="org.semanticwb.process.model.FlowNodeInstance"%>
<%@page import="com.infotec.rh.syr.swb.PartidaPresupuesto"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.infotec.rh.syr.swb.CapituloPresupuesto"%>
<%@page import="com.infotec.eworkplace.swb.Proyecto"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticOntology"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>

<%!
private String getAdscriptionAreaName(int adscriptionNumber, WebSite model) {
    String ret = String.valueOf(adscriptionNumber);
    boolean found = false;
    
    Iterator<CentroCosto> areas = CentroCosto.ClassMgr.listCentroCostos(model);
    while(areas.hasNext() && !found) {
        CentroCosto area = areas.next();
        if (area.getNumeroArea().trim().equals(ret)) {
            ret = area.getTitle();
            found = true;
        }
    }
    return ret;
}
%>

<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
User user = paramRequest.getUser();
WebSite site = paramRequest.getWebPage().getWebSite();
SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
String varSolicitud = "solicitudRecurso";
String varSeguimiento = "seguimientoSR";

//Manejo de peticiones directas
if (paramRequest.getCallMethod() == SWBParamRequest.Call_DIRECT) {
    String act = paramRequest.getAction();
    String ret = "No disponible";
    
    if (act.equals("GETAP")) {
        response.setContentType("text/html; charset=UTF-8");
        String pUri = request.getParameter("pUri");
        Proyecto proy = (Proyecto)ont.getGenericObject(pUri);
        
        if (proy != null) {
            if (act.equals("GETAP") && proy.getAdminsitradorDelProyecto() != null) ret = proy.getAdminsitradorDelProyecto().getFullName();
        } 
    } else if (act.equals("GETPART")) {
        response.setContentType("text/html; charset=UTF-8");
        String capUri = request.getParameter("cUri");
        
        CapituloPresupuesto cap = (CapituloPresupuesto) ont.getGenericObject(capUri);
        if (cap != null) {
            ret = "{identifier:'uri', label:'name', items:[";
            Iterator<PartidaPresupuesto> partidas = cap.listPartidaPresupuestos();
            while (partidas.hasNext()) {
                PartidaPresupuesto part = partidas.next();
                ret = ret + "{name:'" + part.getTitle() + "', uri:'" + part.getURI() + "'}";
                
                if (partidas.hasNext()) ret = ret + ",";
            }
            ret = ret + "]}";
        }
    }
    out.print(ret);
    out.flush();
    return;
} else {
    //Verificar parámetro para recuperar instancia de tarea
    String suri = request.getParameter("suri");
    if (suri == null) {
        out.println("Parámetro no definido...");
        return;
    }

    //Recuperar instancia de tarea
    FlowNodeInstance foi = (FlowNodeInstance) ont.getGenericObject(suri);
    
    //Obtención de objetos de datos
    SolicitudRecurso sr = null;
    SeguimientoSolicitudRecurso ssr = null;
    Iterator<ItemAwareReference> it = foi.listHeraquicalItemAwareReference().iterator();
    while (it.hasNext()) {
        ItemAwareReference ref = it.next();
        SWBClass obj = ref.getProcessObject();
        if (ref.getItemAware().getName().equals(varSolicitud)) {
            sr = (SolicitudRecurso)obj;
        }
        if (ref.getItemAware().getName().equals(varSeguimiento)) {
            ssr = (SeguimientoSolicitudRecurso)obj;
        }
    }
    
    //Validar redirección
    if (null != request.getSession(true).getAttribute("msg")) {
        String message = (String) request.getSession(true).getAttribute("msg");
        if (message.equals("redirect")) {
            sr.setMontoTotal(sr.getSueldoBruto()*sr.getPeriodoContrato());
            sr.setSalarioMax(sr.getSueldoBruto());
            request.getSession(true).removeAttribute("msg");
            response.sendRedirect(foi.getUserTaskInboxUrl());
            return;
        }
    }

    //Revisar asignación a usuario
    User asigned = foi.getAssignedto();
    if(asigned == null) {
        foi.setAssigned(new Date());
        foi.setAssignedto(user);
    } else if(!asigned.equals(user)) {
        out.println("Tarea asignada previamente a otro usuario...");
        return;
    }

    //Creación e inicialización del formulario de procesos
    SWBProcessFormMgr mgr = new SWBProcessFormMgr(foi);
    mgr.setAction(paramRequest.getActionUrl().setAction("process").toString());
    mgr.clearProperties();

    SWBResourceURL urlact = paramRequest.getActionUrl();
    urlact.setAction("process");

    //Agregar propiedades particulares al ProcesformMgr
    mgr.addProperty(SolicitudRecurso.intranet_proyectoAsignado, varSolicitud, SWBFormMgr.MODE_EDIT);
    mgr.addProperty(SolicitudRecurso.intranet_funcionPrincipal, varSolicitud, SWBFormMgr.MODE_EDIT);
    mgr.addProperty(SolicitudRecurso.intranet_contratacion, varSolicitud, SWBFormMgr.MODE_EDIT);
    mgr.addProperty(SolicitudRecurso.intranet_capituloSuficiencia, varSolicitud, SWBFormMgr.MODE_EDIT);
    mgr.addProperty(SolicitudRecurso.intranet_partidaSuficiencia, varSolicitud, SWBFormMgr.MODE_EDIT);
    mgr.addProperty(SolicitudRecurso.intranet_sueldoBruto, varSolicitud, SWBFormMgr.MODE_EDIT);
    mgr.addProperty(SolicitudRecurso.intranet_fechaInicioContrato, varSolicitud, SWBFormMgr.MODE_EDIT);
    mgr.addProperty(SolicitudRecurso.intranet_fechaFinContrato, varSolicitud, SWBFormMgr.MODE_EDIT);
    mgr.addProperty(SolicitudRecurso.intranet_periodoContrato, varSolicitud, SWBFormMgr.MODE_EDIT);
    mgr.addProperty(SolicitudRecurso.intranet_montoTotal, varSolicitud, SWBFormMgr.MODE_EDIT);
    mgr.addProperty(SolicitudRecurso.intranet_notaSolicitud, varSolicitud, SWBFormMgr.MODE_EDIT);
    mgr.addProperty(SolicitudRecurso.intranet_centroDeCosto, varSolicitud, SWBFormMgr.MODE_EDIT);
    mgr.addProperty(SolicitudRecurso.intranet_especialidadRecurso, varSolicitud, SWBFormMgr.MODE_EDIT);
    
    SWBProcessFormMgr fmgr = new SWBProcessFormMgr(foi);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    SimpleDateFormat sdfDojo = new SimpleDateFormat("yyyy-MM-dd");
    String year = sdf.format(new Date(System.currentTimeMillis()));

    Services services = new Services();
    %>
    <%=SWBForms.DOJO_REQUIRED%>
    <div id="processForm">
        <form id="<%=foi.getId()%>/form" dojoType="dijit.form.Form" class="swbform" action="<%=urlact%>" method="post" onSubmit="return validaForma(this);">
            <input type="hidden" name="suri" value="<%=suri%>"/>
            <input type="hidden" name="smode" value="edit"/>
            <fieldset>
                <table>
                    <tr>
                        <td width="200px" align="right"><label for="title">Nombre del solicitante</label>
                        <td>
                            <span><%=foi.getProcessInstance().getCreator().getFullName()%></span>
                        </td>
                    </tr>
                    <tr>
                        <td width="200px" align="right"><label for="title">&Aacute;rea de adscripci&oacute;n</label>
                        <td>
                            <span><%=getAdscriptionAreaName(services.getAreaAdscripcion(user.getLogin()), site)%></span>
                        </td>
                    </tr>
                    <tr>
                        <td width="200px" align="right"><label for="title"><%=fmgr.renderLabel(request, SolicitudRecurso.intranet_proyectoAsignado , varSolicitud, SWBFormMgr.MODE_VIEW)%></label>
                        <td>
                            <select id="proyecto_<%=foi.getId()%>" onchange="updateProjectFields(dijit.byId('proyecto_<%=foi.getId()%>').attr('value'))" name="<%=varSolicitud + "." + SolicitudRecurso.intranet_proyectoAsignado.getName()%>" dojoType="dijit.form.FilteringSelect" autoComplete="true" invalidMessage="Proyecto es requerido." required="true" >
                                <%
                                Iterator<Proyecto> proyectos = Proyecto.ClassMgr.listProyectos(site);
                                while (proyectos.hasNext()) {
                                    Proyecto proy = proyectos.next();
                                    if (proy.isValid()) {
                                        %><option value="<%=proy.getURI()%>" <%=(sr.getProyectoAsignado() != null && sr.getProyectoAsignado().equals(proy))?"selected":""%>><%=proy.getNombreNumero()%></option><%
                                    }
                                }
                                %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td width="200px" align="right"><label for="title"><%=fmgr.renderLabel(request, Proyecto.intranet_adminsitradorDelProyecto, varSolicitud, SWBFormMgr.MODE_VIEW)%></label></td>
                        <td>
                            <span id="proyecto_AP<%=foi.getId()%>">No disponible</span>
                        </td>
                    </tr>
                    <tr>
                        <td width="200px" align="right"><label for="title"><%=fmgr.renderLabel(request, SolicitudRecurso.intranet_funcionPrincipal, varSolicitud, SWBFormMgr.MODE_VIEW)%></label></td>
                        <td>
                            <%=fmgr.renderElement(request, varSolicitud, SolicitudRecurso.intranet_funcionPrincipal, SWBFormMgr.MODE_EDIT)%>
                        </td>
                    </tr>
                    <tr>
                        <td width="200px" align="right"><label for="title"><%=fmgr.renderLabel(request, SolicitudRecurso.intranet_contratacion, varSolicitud, SWBFormMgr.MODE_VIEW)%></label></td>
                        <td>
                            <%=fmgr.renderElement(request, varSolicitud, SolicitudRecurso.intranet_contratacion, SWBFormMgr.MODE_EDIT)%>
                        </td>
                    </tr>
                    <tr>
                        <td width="200px" align="right"><label for="title"><%=fmgr.renderLabel(request, SolicitudRecurso.intranet_centroDeCosto, varSolicitud, SWBFormMgr.MODE_VIEW)%></label></td>
                        <td>
                            <%=fmgr.renderElement(request, varSolicitud, SolicitudRecurso.intranet_centroDeCosto, SWBFormMgr.MODE_EDIT)%>
                        </td>
                    </tr>
                    <tr>
                        <td width="200px" align="right"><label for="title"><%=fmgr.renderLabel(request, SolicitudRecurso.intranet_especialidadRecurso, varSolicitud, SWBFormMgr.MODE_VIEW)%></label></td>
                        <td>
                            <%=fmgr.renderElement(request, varSolicitud, SolicitudRecurso.intranet_especialidadRecurso, SWBFormMgr.MODE_EDIT)%>
                        </td>
                    </tr>
                    <tr>
                        <td width="200px" align="right"><label for="title"><%=fmgr.renderLabel(request, SolicitudRecurso.intranet_capituloSuficiencia, varSolicitud, SWBFormMgr.MODE_VIEW)%></label></td>
                        <td>
                            <select id="capitulo_<%=foi.getId()%>" onchange="updatePartidas(dijit.byId('capitulo_<%=foi.getId()%>').attr('value'))" name="<%=varSolicitud+"."+SolicitudRecurso.intranet_capituloSuficiencia.getName()%>" dojoType="dijit.form.FilteringSelect" autoComplete="true" invalidMessage="Capítulo es requerido." required="true" >
                                <%
                                Iterator<CapituloPresupuesto> caps = CapituloPresupuesto.ClassMgr.listCapituloPresupuestos(site);
                                while (caps.hasNext()) {
                                    CapituloPresupuesto cap = caps.next();
                                    %><option value="<%=cap.getURI()%>" <%=(sr.getCapituloSuficiencia() != null && sr.getCapituloSuficiencia().equals(cap))?"selected":""%>><%=cap.getTitle()%></option><%
                                }
                                %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td width="200px" align="right"><label for="title"><%=fmgr.renderLabel(request, SolicitudRecurso.intranet_partidaSuficiencia, varSolicitud, SWBFormMgr.MODE_VIEW)%></label></td>
                        <td>
                            <select id="partida_<%=foi.getId()%>" name="<%=varSolicitud+"."+SolicitudRecurso.intranet_partidaSuficiencia.getName()%>" dojoType="dijit.form.FilteringSelect" autoComplete="true" invalidMessage="Partida es requerido." required="true" >
                                <%
                                Iterator<PartidaPresupuesto> parts = PartidaPresupuesto.ClassMgr.listPartidaPresupuestos(site);
                                while (parts.hasNext()) {
                                    PartidaPresupuesto part = parts.next();
                                    %><option value="<%=part.getURI()%>" <%=(sr.getPartidaSuficiencia() != null && sr.getPartidaSuficiencia().equals(part))?"selected":""%>><%=part.getTitle()%></option><%
                                }
                                %>
                            </select>
                        </td>
                        
                    </tr>
                    <tr>
                        <td width="200px" align="right"><label for="title"><%=fmgr.renderLabel(request, SolicitudRecurso.intranet_sueldoBruto, varSolicitud, SWBFormMgr.MODE_VIEW)%></label></td>
                        <%
                        String regexp = "\\d(\\.\\d{0,2})?";
                        SemanticObject sofe = ont.getSemanticObject("http://www.infotec.com.mx/intranet#CurrencySueldo");
                        if (null != sofe) {
                            FormElement frme = (FormElement) sofe.createGenericInstance();
                            regexp = ((Currency)frme).getFormat();
                        }
                        %>
                        <td>
                            <input type="text" value="<%=sr.getSueldoBruto()%>" regExp="<%=regexp%>" dojoType="dijit.form.ValidationTextBox" onBlur="calcMontoTotal();" id="sueldo_<%=foi.getId()%>" name="<%=varSolicitud+"."+SolicitudRecurso.intranet_sueldoBruto.getName()%>" required="true" style="width:300px"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"><b>Periodo de Contrataci&oacute;n</b></td>
                    </tr>
                    <tr>
                        <td width="200px" align="right"><label for="title"><%=fmgr.renderLabel(request, SolicitudRecurso.intranet_fechaInicioContrato, varSolicitud, SWBFormMgr.MODE_VIEW)%></label></td>
                        <td>
                            <input type="text" value="<%=sr.getFechaInicioContrato()!=null?sdfDojo.format(sr.getFechaInicioContrato()):""%>" dojoType="dijit.form.DateTextBox" name="<%=varSolicitud+"."+SolicitudRecurso.intranet_fechaInicioContrato.getName()%>" onChange="dijit.byId('fechaFin_<%=foi.getId()%>').constraints.min = arguments[0];" required="true" constraints="{min:'<%=year%>-01-01'}">
                        </td>
                    </tr>
                    <tr>
                        <td width="200px" align="right"><label for="title"><%=fmgr.renderLabel(request, SolicitudRecurso.intranet_fechaFinContrato, varSolicitud, SWBFormMgr.MODE_VIEW)%></label></td>
                        <td>
                            <input value="<%=sr.getFechaFinContrato()!=null?sdfDojo.format(sr.getFechaFinContrato()):""%>" id="fechaFin_<%=foi.getId()%>" type="text" dojoType="dijit.form.DateTextBox" name="<%=varSolicitud+"."+SolicitudRecurso.intranet_fechaFinContrato.getName()%>" required="true" constraints="{max:'<%=year%>-12-31'}">
                        </td>
                    </tr>
                    <tr>
                        <td width="200px" align="right"><label for="title"><%=fmgr.renderLabel(request, SolicitudRecurso.intranet_periodoContrato, varSolicitud, SWBFormMgr.MODE_VIEW)%></label></td>
                        <td>
                            <input type="text" value="<%=sr.getPeriodoContrato()%>"dojoType="dijit.form.ValidationTextBox" onBlur="calcMontoTotal();" id="periodo_<%=foi.getId()%>" name="<%=varSolicitud+"."+SolicitudRecurso.intranet_periodoContrato.getName()%>" required="true" style="width:300px"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="200px" align="right"><label for="title"><%=fmgr.renderLabel(request, SolicitudRecurso.intranet_montoTotal, varSolicitud, SWBFormMgr.MODE_VIEW)%></label></td>
                        <td>
                            <input type="text" disabled="disabled" value="<%=sr.getMontoTotal()%>" dojoType="dijit.form.ValidationTextBox" id="monto_<%=foi.getId()%>" name="<%=varSolicitud+"."+SolicitudRecurso.intranet_montoTotal.getName()%>" required="true" style="width:300px"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="200px" align="right"><label for="title"><%=fmgr.renderLabel(request, SeguimientoSolicitudRecurso.intranet_notaConsultaPresupuesto, varSeguimiento, SWBFormMgr.MODE_VIEW)%></label></td>
                        <td>
                            <%=fmgr.renderElement(request, varSeguimiento, SeguimientoSolicitudRecurso.intranet_notaConsultaPresupuesto, SWBFormMgr.MODE_EDIT)%>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <fieldset>
                <span align="center">
                <button dojoType="dijit.form.Button" type="submit">Guardar</button>
                <button dojoType="dijit.form.Button" name="accept" type="submit">Concluir Tarea</button>
                <button dojoType="dijit.form.Button" name="reject" type="submit">Rechazar Tarea</button>
                <button dojoType="dijit.form.Button" onclick="window.location='<%=foi.getUserTaskInboxUrl()%>?suri=<%=suri%>'">Regresar</button>
                </span>
            </fieldset>
        </form>
    </div>            
    <script type="text/javascript">
        dojo.require("dojo.data.ItemFileReadStore");
        dojo.addOnLoad(function() {
            updateProjectFields(dijit.byId('proyecto_<%=foi.getId()%>').attr('value'))
        });
        
        function updateSelectAjax(elementId, callUrl, callParamName, callParamValue) {
            var urlToCall = callUrl+callParamName+encodeURIComponent(callParamValue);
            setSelectStoreAjax(elementId, urlToCall);
        }
        
        function updateTextValueAjax(elementId, callUrl, callParamName, callParamValue) {
            var urlToCall = callUrl+callParamName+encodeURIComponent(callParamValue);
            setValueAjax(elementId, urlToCall);
        }
        
        function validaForma(forma) {
            if (forma.validate()) {
                return true;
            } else {
                alert('Algunos de los datos no son válidos. Verifique la información proporcionada.');
                return false;
            }
        }
        
        function fcomplete (items, request) {
            var Lvalue = request.store.getValue(items[0], 'uri');
            var el = dijit.byId('partida_<%=foi.getId()%>'); 
            el.attr('value', Lvalue);
            el.setDisabled(false);
        }
        
        function setSelectStoreAjax (elementId, callUrl) {
            var store = new dojo.data.ItemFileReadStore({url: callUrl, clearOnClose: true});
            var ele = dijit.byId(elementId);
            
            ele.store = store;
            
            store.fetch({onComplete: fcomplete});
            ele.startup();
        }
        
        function setValueAjax(elementId, callUrl) {
            var xhrArgs = {
                url: callUrl,
                handleAs: "text",
                load: function(data){
                    document.getElementById(elementId).innerHTML = data;
                },
                error: function(error){
                    document.getElementById(elementId).innerHTML = "No disponible";
                }
            }
            // Call the asynchronous xhrPost
            document.getElementById(elementId).innerHTML = "Obteniendo datos..."
            var deferred = dojo.xhrGet(xhrArgs);
        }
        
        function updateProjectFields(pUri) {
            <%
            SWBResourceURL apUrl = paramRequest.getRenderUrl().setCallMethod(SWBParamRequest.Call_DIRECT).setAction("GETAP");
            %>
            updateTextValueAjax("proyecto_AP<%=foi.getId()%>", '<%=apUrl%>', '?pUri=', pUri);
        }
        
        function updatePartidas(cUri) {
            <%
            SWBResourceURL caUrl = paramRequest.getRenderUrl().setCallMethod(SWBParamRequest.Call_DIRECT).setAction("GETPART");
            %>
            //console.log(dijit.byId('partida_<%=foi.getId()%>'));
            updateSelectAjax('partida_<%=foi.getId()%>', '<%=caUrl%>', '?cUri=', cUri);
        }
        
        function calcMontoTotal () {
            var sueldo = 0;
            var periodo = 0;
            sueldo = dojo.byId('sueldo_<%=foi.getId()%>').value;
            periodo = dojo.byId('periodo_<%=foi.getId()%>').value;
            
            if (sueldo == "" || sueldo == "undefined") sueldo = 0;
            if (sueldo.indexOf("$") != -1) sueldo = sueldo.replace("$", "").trim();
            if (periodo == "" || periodo == "undefined") periodo = 0; 
           
            dojo.byId('monto_<%=foi.getId()%>').value = sueldo * periodo;
        }
    </script>
<%
}
%>