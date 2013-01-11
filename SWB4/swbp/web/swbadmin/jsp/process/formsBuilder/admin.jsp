<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="org.semanticwb.model.DisplayProperty"%>
<%@page import="org.semanticwb.model.FormElement"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.process.model.ItemAwareReference"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticOntology"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.process.model.ItemAware"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.process.resources.FormsBuilderResource"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute(FormsBuilderResource.ATT_PARAMREQUEST);
UserTask task = (UserTask) request.getAttribute(FormsBuilderResource.ATT_TASK);
User user = paramRequest.getUser();
String lang = "es";

if (user != null && user.getLanguage() != null) {
    lang = user.getLanguage();
}

if (task == null) {
    return;
}

Resource base = (Resource) request.getAttribute(FormsBuilderResource.ATT_RBASE);
WebSite site = paramRequest.getWebPage().getWebSite();
boolean btnAccept = false;
boolean btnReject = false;
boolean btnCancel = false;
boolean btnSave = false;
boolean useSign = false;

if (base.getAttribute("btnAccept") != null) {
    btnAccept = true;
}
if (base.getAttribute("btnReject") != null) {
    btnReject = true;
}
if (base.getAttribute("btnCancel") != null) {
    btnCancel = true;
}
if (base.getAttribute("btnSave") != null) {
    btnSave = true;
}
if (base.getAttribute("useSign") != null) {
    useSign = true;
}

HashMap<String, SemanticProperty> allprops = new HashMap();
HashMap<String, SemanticClass> hmclass = new HashMap();
ArrayList<String> baseProps = new ArrayList<String>();
SemanticOntology ont = SWBPlatform.getSemanticMgr().getSchema();

if (task != null) {
    Iterator<ItemAware> it = task.listHerarquicalRelatedItemAwarePlusNullOutputs().iterator();
    while (it.hasNext()) {
        ItemAware item = it.next();
        SemanticClass cls = item.getItemSemanticClass();
        if (cls != null) {
            Iterator<SemanticProperty> itp = cls.listProperties();
            while (itp.hasNext()) {
                SemanticProperty prop = itp.next();
                String name = item.getName() + "|" + prop.getPropId();
                if (!prop.getPropId().equals("swb:valid") && !allprops.containsKey(name)) {
                    allprops.put(name, prop);
                    if (!hmclass.containsKey(item.getName())) {
                        hmclass.put(item.getName(), item.getItemSemanticClass());
                    }
                }
            }
        }
    }
}

int max = 1;
while (!base.getAttribute("prop" + max, "").equals("")) {
    String val = base.getAttribute("prop" + max++);
    HashMap<String, String> map = FormsBuilderResource.getPropertiesMap(val);
    String key = map.get("varName") + "|" + map.get("propId");
    if (allprops.containsKey(key)) {
        baseProps.add(val);
    }
}

String admMode = base.getAttribute(FormsBuilderResource.PARAM_ADMMODE, FormsBuilderResource.ADM_MODESIMPLE);
SWBResourceURL toggle = paramRequest.getActionUrl().setAction(FormsBuilderResource.ACT_TOGGLEBUTTON);
SWBResourceURL updMode = paramRequest.getActionUrl().setAction(FormsBuilderResource.ACT_UPDADMINMODE);
SWBResourceURL admUrl = paramRequest.getRenderUrl().setMode(SWBParamRequest.Mode_ADMIN);
String disabled = "";
if (FormsBuilderResource.ADM_MODESIMPLE.equals(admMode)) {
    updMode.setParameter(FormsBuilderResource.PARAM_ADMMODE, FormsBuilderResource.ADM_MODEADVANCED);
} else if (FormsBuilderResource.ADM_MODEADVANCED.equals(admMode)) {
    disabled = "disabled=\"true\"";
    updMode.setParameter(FormsBuilderResource.PARAM_ADMMODE, FormsBuilderResource.ADM_MODESIMPLE);
}
%>
<div dojoType="dijit.layout.BorderContainer" id="mainContainer" style="width:100%; height:1300px;">
    <div dojoType="dijit.Toolbar" region="top">
        <form action="<%=toggle%>" method="post">
            <button iconClass="propIcon" <%=disabled%> dojoType="dijit.form.Button" onclick="showFormDialog('elePropsDialog','','Agregar Propiedad')">Propiedad</button>
            <span dojoType="dijit.ToolbarSeparator"></span>
            <button name="btns" <%=disabled%> iconClass="acceptIcon" onclick="window.location='<%=toggle%>?btns=accept'; return false;" <%=btnAccept?"checked":""%> dojoType="dijit.form.ToggleButton">Bot&oacute;n aceptar</button>
            <button name="btns" <%=disabled%> iconClass="rejectIcon" onclick="window.location='<%=toggle%>?btns=reject'; return false;" <%=btnReject?"checked":""%> dojoType="dijit.form.ToggleButton">Bot&oacute;n rechazar</button>
            <button name="btns" <%=disabled%> iconClass="cancelIcon" onclick="window.location='<%=toggle%>?btns=cancel'; return false;" <%=btnCancel?"checked":""%> dojoType="dijit.form.ToggleButton">Bot&oacute;n cacelar</button>
            <button name="btns" <%=disabled%> iconClass="saveIcon" onclick="window.location='<%=toggle%>?btns=save'; return false;" <%=btnSave?"checked":""%> dojoType="dijit.form.ToggleButton">Bot&oacute;n guardar</button>
            <span dojoType="dijit.ToolbarSeparator"></span>
            <button name="btns" iconClass="signIcon" onclick="window.location='<%=toggle%>?btns=sign'; return false;" <%=base.getAttribute("useSign") != null ? "checked" : ""%> dojoType="dijit.form.ToggleButton">Usar firmado</button>
            <button iconClass="advancedIcon" onclick="window.location='<%=updMode%>'" <%=admMode.equals(FormsBuilderResource.ADM_MODEADVANCED) ? "checked" : ""%> dojoType="dijit.form.ToggleButton">Modo avanzado</button>
        </form>
    </div>
    <div dojoType="dijit.layout.ContentPane" region="center" id="workspace">
        <%
        if (FormsBuilderResource.ADM_MODESIMPLE.equals(admMode)) {
            %>
            <div class="formBody container" autoSync="true" dojoType="dojo.dnd.Source" skipForm="true">
                <div id="header1">
                    <h1><%=task.getTitle()%></h1>
                    <hr>
                </div>
                <div id="separator1" class="separator dojoDndItem">
                    <div class="lineSeparator"></div>
                </div>
                <form class="swbform" dojoType="dijit.form.Form">
                    <%
                    if (!baseProps.isEmpty()) {
                        Iterator<String> props = baseProps.iterator();
                        %>
                        <fieldset>
                        <table>
                            <tbody>
                                <%
                                max = 1;
                                while (props.hasNext()) {
                                    String val = props.next();
                                    HashMap<String, String> map = FormsBuilderResource.getPropertiesMap(val);
                                    String varName = map.get("varName");
                                    String fe = map.get("fe");
                                    String modo = map.get("mode");
                                    String label = map.get("label");
                                    String propKey = varName + "|" + map.get("propId");

                                    SemanticProperty sprop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(map.get("propId"));
                                    if (allprops.containsKey(propKey)) {
                                        allprops.remove(propKey);
                                    }
                                    if (sprop != null) {
                                        SemanticClass sclass = hmclass.get(map.get("varName"));
                                        if (sclass != null) {
                                            %>
                                            <tr>
                                                <%
                                                SemanticObject virtual = new SemanticObject(site.getSemanticModel(), sclass);
                                                SWBFormMgr mgr = new SWBFormMgr(virtual, null, SWBFormMgr.MODE_CREATE);
                                                mgr.setType(SWBFormMgr.TYPE_DOJO);
                                                mgr.setLang("es");
                                                if (map.get("label") == null) {
                                                    map.put("label", sprop.getDisplayName(lang));
                                                }
                                                FormElement ele = mgr.getFormElement(sprop);
                                                ele.setLabel(map.get("label"));
                                                SWBResourceURL urlmove = paramRequest.getActionUrl();
                                                urlmove.setAction(FormsBuilderResource.ACT_SWAP);
                                                urlmove.setParameter(FormsBuilderResource.PARAM_PROPIDX, "" + max);
                                                urlmove.setParameter(FormsBuilderResource.PARAM_DIR, "up");
                                                %>
                                                <td>
                                                    <%
                                                    if (max != 1) {
                                                        %>
                                                        <a href="#" onclick="window.location='<%=urlmove%>'; return false;" title="Subir"><img src="/swbadmin/images/up.jpg"/></a>
                                                        <%
                                                    }
                                                    if (max < baseProps.size()) {
                                                        urlmove.setParameter(FormsBuilderResource.PARAM_DIR, "down");
                                                        %>
                                                        <a href="#" onclick="window.location='<%=urlmove%>'; return false;" title="Bajar"><img src="/swbadmin/images/down.jpg"/></a>
                                                        <%
                                                    }
                                                    %>
                                                </td>
                                                <td width="200px" align="right"><%=ele.renderLabel(request, null, sprop, varName + "." + sprop.getName(), SWBFormMgr.TYPE_DOJO, SWBFormMgr.MODE_CREATE, "es")%></td>
                                                    <%
                                                    SemanticObject sofe = ont.getSemanticObject(fe);
                                                    if (sofe != null) {
                                                        FormElement frme = (FormElement) sofe.createGenericInstance();
                                                        frme.setModel(virtual.getModel());
                                                        if (modo.equals("view")) {
                                                            if (sprop.isBoolean()) {
                                                                virtual.setBooleanProperty(sprop, false);
                                                            } else if (sprop.isDate() || sprop.isDateTime()) {
                                                                virtual.setDateProperty(sprop, new Date(System.currentTimeMillis()));
                                                            } else if (sprop.isDouble() || sprop.isFloat()) {
                                                                virtual.setDoubleProperty(sprop, 0.0);
                                                            } else if (sprop.isInt() || sprop.isLong() || sprop.isShort()) {
                                                                virtual.setIntProperty(sprop, 0);
                                                            } else if (sprop.isString()) {
                                                                virtual.setProperty(sprop, "Texto de " + label);
                                                            } else if (sprop.isObjectProperty()) {
                                                                SemanticClass range = null;
                                                                range = sprop.getRangeClass();
                                                                if (range != null) {
                                                                    Iterator<SemanticObject> instance = SWBPlatform.getSemanticMgr().getOntology().listInstancesOfClass(range);
                                                                    if (instance.hasNext()) {
                                                                        SemanticObject inst = instance.next();
                                                                        virtual.setObjectProperty(sprop, inst);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        try {
                                                            %>
                                                            <td><%=frme.renderElement(request, virtual, sprop, SWBFormMgr.TYPE_DOJO, modo, "es")%></td>
                                                            <%
                                                        } catch (Exception ex) {
                                                            ex.printStackTrace();
                                                        }
                                                    } else {
                                                        %>
                                                        <td><%=mgr.renderElement(request, sprop, modo)%></td>
                                                        <%
                                                    }
                                                    SWBResourceURL delUrl = paramRequest.getActionUrl().setAction(FormsBuilderResource.ACT_REMOVEPROP);
                                                    SWBResourceURL editUrl = paramRequest.getRenderUrl().setMode(FormsBuilderResource.MODE_EDITPROP).setCallMethod(SWBParamRequest.Call_DIRECT);
                                                    delUrl.setParameter(FormsBuilderResource.PARAM_PROPIDX, String.valueOf(max));
                                                    editUrl.setParameter(FormsBuilderResource.PARAM_PROPIDX, String.valueOf(max));
                                                    %>
                                                <td>
                                                    <a href="#" title="Eliminar propiedad" onclick="window.location='<%=delUrl%>';return false;"><img src="/swbadmin/images/delete.gif"/></a>
                                                    <a href="#" title="Editar Configuración" onclick="showDialog('<%=editUrl%>','Editar'); return false;"><img src="/swbadmin/icons/editar_1.gif"/></a>
                                                </td>
                                            </tr>
                                        <%
                                        }
                                    }
                                    max++;
                                }
                                %>
                            </tbody>
                        </table>
                    </fieldset>
                    <%
                    }
                    %>
                </form>
            </div>
            <div class="swbform">
                <%
                if (btnAccept || btnCancel || btnReject || btnSave) {
                    %>
                    <fieldset>
                    <%
                }
                if (btnAccept) {
                    %>
                    <button dojoType="dijit.form.Button">Aceptar</button>
                    <%
                }
                if (btnReject) {
                    %>
                    <button dojoType="dijit.form.Button">Rechazar</button>
                    <%
                }
                if (btnCancel) {
                    %>
                    <button dojoType="dijit.form.Button">Cancelar</button>
                    <%
                }
                if (btnSave) {
                    %>
                    <button dojoType="dijit.form.Button">Guardar</button>
                    <%
                }
                if (btnAccept || btnCancel || btnReject || btnSave) {
                    %>
                    </fieldset>
                    <%
                }
                %>
            </div>
            <div dojoType="dijit.Dialog" style="display:none;" id="configDialog" title="Agregar">
                <div dojoType="dojox.layout.ContentPane" id="configDialogImp" executeScripts="true">
                    Cargando...
                </div>
            </div>
            <div id="elePropsDialog" style="display:none;" dojoType="dijit.Dialog" title="Agregar propiedad">
                <%
                SWBResourceURL urladd = paramRequest.getActionUrl().setAction(FormsBuilderResource.ACT_ADDPROPS);
                ArrayList<String> list = new ArrayList(allprops.keySet());
                if (!list.isEmpty()) {
                    %>
                    <script type="text/javascript">
                        dojo.require("dijit.form.MultiSelect");
                    </script>
                    <form class="swbform" action="<%=urladd%>" method="post">
                        <fieldset>
                            <select multiple size="10" name="properties" style="width:250px;">
                                <%
                                Collections.sort(list);
                                Iterator<String> its = list.iterator();
                                while (its.hasNext()) {
                                    String str = its.next();
                                    String varName = "";
                                    StringTokenizer stoken = new StringTokenizer(str, "|");
                                    if (stoken.hasMoreTokens()) {
                                        varName = stoken.nextToken();
                                    }
                                    SemanticProperty sp = allprops.get(str);
                                    %>
                                    <option value="<%=str%>"><%=varName + "." + sp.getPropertyCodeName()%>
                                    </option>
                                    <%
                                }
                                %>
                            </select>
                        </fieldset>
                        <fieldset>
                            <button dojoType="dijit.form.Button" type="submit">Agregar seleccionadas</button>
                            <button dojoType="dijit.form.Button" onclick="hideDialog('elePropsDialog');">Cancelar</button>
                        </fieldset>
                    </form>
                    <%
                } else {
                    %>No hay propiedades disponibles<%
                }
                %>
            </div>
            <%
        } else if (FormsBuilderResource.ADM_MODEADVANCED.equals(admMode)) {
            String basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
            String value = SWBUtils.IO.getFileFromPath(basepath + "code.xml");
            SWBResourceURL urladd = paramRequest.getActionUrl().setAction(FormsBuilderResource.ACT_UPDATEXML);
            %>
            <form dojoType="dijit.form.Form" action="<%=urladd%>" method="post">
                <button dojoType="dijit.form.Button" onclick="if (confirm('Se volverá a generar el XML.\nLos cambios que haya hecho se perderán. ¿Desea continuar?')) {window.location='<%=urladd%>';} else {return false;}">Generar XML</button>
                <hr>
            </form>
            <%
            if (null != value && value.trim().length() > 0) {
                SWBResourceURL url = paramRequest.getActionUrl().setAction(FormsBuilderResource.ACT_SAVEXML);
                %>
                <div>
                    <script type="text/javascript" src="<%=SWBPlatform.getContextPath() + "/swbadmin/js/editarea/edit_area/edit_area_full.js" %>" ></script>
                    <script type="text/javascript" charset="UTF-8">
                        editAreaLoader.init({
                            id: "resource<%=base.getId()%>",
                            language: "<%=lang%>",
                            syntax: "xml",
                            start_highlight: true,
                            toolbar: "save, |, search, go_to_line, |, undo, redo, |, select_font,|, change_smooth_selection, highlight, reset_highlight, |, help",
                            is_multi_files: false,
                            save_callback: "my_save",
                            allow_toggle: false
                        });

                        function my_save(id, content) {
                            var elemento = document.getElementById("hiddencode");
                            elemento.value = content;
                            document.xmledition.submit();
                        }
                    </script>
                    <form name="xmledition" action="<%=url%>" method="post">
                        <input type="hidden" id="hiddencode" name="hiddencode" value=""/>
                        <textarea id="resource<%=base.getId()%>" name="resource<%=base.getId()%>" rows="25" cols="120"><%=value%></textarea>
                    </form>
                </div>
                <%
            }
        }
        %>
    </div>
</div>
<script type="text/javascript">
    dojo.require("dijit.Dialog");
    function showFormDialog(dialogId, url, title) {
        var dialog = dijit.byId(dialogId);
        if (url != null && url != "") {
            dialog.attr('href',url);
        }
        dialog.attr('title',title);
        dialog.show();
    }

    function hideDialog(id) {
        dijit.byId(id).hide();
    }

    function showDialog(url, title)
    {
        //alert("url:"+url);
        dojo.xhrGet({
            url: url,
            load: function(response, ioArgs){
                //alert(response);
                //dijit.byId('swbDialogImp').attr('content',response);
                dijit.byId('configDialogImp').attr('content',response);
                dijit.byId('configDialog').show();
                if(title)dijit.byId('configDialog').titleNode.innerHTML=title;
                return response;
            },
            error: function(response, ioArgs){
                showStatus('Error:'+response);
                //dijit.byId('swbDialogImp').attr('content','Error: '+response);
                //dijit.byId('swbDialog').show();
                return response;
            },
            handleAs: "text"
        });
    }

    function reloadAdmin() {
        window.location='<%=admUrl%>';
    }

    function submitForm(formid)
    {
        var obj=dojo.byId(formid);
        var objd=dijit.byId(formid);
        var fid=formid;
        //alert("id:"+formid+" "+"dojo:"+obj +" dijit:"+objd);
        if(!obj && objd) //si la forma esta dentro de un dialog
        {
            obj=objd.domNode;
            fid=obj;
        }

        if(!objd || objd.isValid())
        {
            try
            {
                //dojo.fadeOut({node: formid, duration: 1000}).play();
                dojo.fx.wipeOut({node: formid, duration: 500}).play();
            }catch(noe){}

            try {
                var framesNames = "";
                for (var i = 0; i < window.frames.length; i++) {
                    framesNames += window.frames[i].name;
                    if (framesNames && framesNames.indexOf("_editArea") != -1) {
                        area = window.frames[framesNames].editArea;
                        id = framesNames.substr(6);
                        document.getElementById(id).value = area.textarea.value;
                    }
                }
            } catch (ex) {}

            //alert("entra2");
            dojo.xhrPost({
                // The page that parses the POST request
                contentType: "application/x-www-form-urlencoded; charset=utf-8",

                //handleAs: "text",

                url: obj.action,

                // Name of the Form we want to submit
                form: fid,

                // Loads this function if everything went ok
                load: function (data)
                {
                    reloadAdmin();
                },
                // Call this function if an error happened
                error: function (error) {
                    alert('Error: ', error);
                }
            });
        }else
        {
            alert("Datos Inválidos...");
        }
    }

    function setCSS() {
        var cssNode = document.createElement('link');
        cssNode.setAttribute("type","text/css");
        cssNode.setAttribute("rel","stylesheet");
        cssNode.setAttribute("href","/swbadmin/jsp/process/formsBuilder.css");
        document.getElementsByTagName("head")[0].appendChild(cssNode);
    }
    setCSS();
</script>
