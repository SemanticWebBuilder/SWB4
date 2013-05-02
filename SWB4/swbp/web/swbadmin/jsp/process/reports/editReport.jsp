<%-- 
    Document   : editReport
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
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SWBResourceURL url = paramRequest.getRenderUrl();
    SWBResourceURL urlAction = paramRequest.getActionUrl();
    Report obj = Report.ClassMgr.getReport(request.getParameter("idReport"), paramRequest.getWebPage().getWebSite());
    Iterator<ItemAware> ia = obj.getProcessName().listRelatedItemAware().iterator();
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
%>
<div class="bandeja-combo">
    <ul><li><a href="<%=url.setMode(SWBResourceURL.Mode_VIEW)%>">Regresar</a></li></ul><br/>
    <h2>Datos generales</h2>
    <form dojoType="dijit.form.Form" action="<%=urlAction.setAction(SWBResourceURL.Action_EDIT)%>" method="post">
        <%out.println(tipo.getFormHiddens());%>
        <table class="tabla-bandeja">
            <tr><td align="right" width="200px">
                    <%out.println(tipo.renderLabel(request, obj.swb_title, SWBFormMgr.MODE_CREATE));%></td>
                <td><%out.println(tipo.renderElement(request, obj.swb_title, SWBFormMgr.MODE_CREATE));%></td></tr>
            <tr><td align="right" width="200px">
                    <%out.println(tipo.renderLabel(request, obj.rep_processName, SWBFormMgr.MODE_CREATE));%></td>
                <td><%out.println(tipo.renderElement(request, obj.rep_processName, SWBFormMgr.MODE_VIEW));%></td></tr>
            <tr><td align="right" width="200px">
                    <%out.println(tipo.renderLabel(request, obj.rep_pagingSize, SWBFormMgr.MODE_CREATE));%></td>
                <td><%out.println(tipo.renderElement(request, obj.rep_pagingSize, SWBFormMgr.MODE_CREATE));%></td></tr>
            <tr><td align="right" width="200px"></td>
                <td>
                    <input type="hidden" name="idReport" value="<%=obj.getId()%>"/>
                    <input type="submit" value="Actualizar" title="Actualizar"/>
                    <input type="button" value="Cancelar" title="Cancelar" onclick="javascript:document.back.submit()"/>
                </td></tr>
        </table>
    </form>
</div>
<div class="bandeja-combo">
    <h2>Configuración de columnas</h2>
    <form dojoType="dijit.form.Form" action="<%=urlAction.setAction("addColumn")%>" method="post">
        <select multiple dojoType="dijit.form.Select" name="property" style="width: 300px">
            <%try {
                    while (ia.hasNext()) {
                        ItemAware iaw = ia.next();
                        //System.out.println("el iaw: " + iaw);
                        Iterator<SemanticProperty> sp = iaw.getItemSemanticClass().listProperties();
                        while (sp.hasNext()) {
                            SemanticProperty spr = sp.next();
                            //System.out.println("getPropId: " + spr.getPropId() + "\tgetName: " + spr.getName());
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            %>
        </select>
        <input type="hidden" name="idReport" value="<%=obj.getId()%>"/>
        <%if (control != 0) {%>
        <input type="submit" value="Agregar columna" title="Agregar columna"/>
        <%} else {%>
        <label>No hay propiedades disponibles</label>
        <%}%>
    </form>
    <form dojoType="dijit.form.Form" action="<%=urlAction.setAction("updateColumn")%>" method="post">
        <table class="tabla-bandeja">
            <thead>
            <th>Eliminar</th><th>Ordenar</th><th>Propiedad</th><th>Título de columna</th><th>Habilitar ordenamiento</th><th>Visible</th><th>Valor</th>
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
                    <a href="<%=urlAction.setAction("moveDown").setParameter("idColumn", colu.getId())%>" title="Bajar"><img src="/swbadmin/jsp/process/reports/down.png"></a>
                        <%} else if (contador < total && total > 1) {%>
                    <a href="<%=urlAction.setAction("moveUp").setParameter("idColumn", colu.getId())%>" title="Subir"><img src="/swbadmin/jsp/process/reports/up.png"></a>
                    <a href="<%=urlAction.setAction("moveDown").setParameter("idColumn", colu.getId())%>" title="Bajar"><img src="/swbadmin/jsp/process/reports/down.png"></a>
                        <%} else if (contador == total && total > 1) {%>
                    <a href="<%=urlAction.setAction("moveUp").setParameter("idColumn", colu.getId())%>" title="Subir"><img src="/swbadmin/jsp/process/reports/up.png"></a><%}%></td>
                <td><%=objeto + "." + sp.getName()%></td>
                <td><input type="text" name="title<%=colu.getURI()%>" value="<%=colu.getTitleColumn() == null ? sp.getDisplayName(lang) : colu.getTitleColumn()%>"></input></td>
                <td style="text-align: center;">
                    <input name="enabledOrder<%=colu.getURI()%>" type="checkbox" <%if (colu.isEnabledOrder()) {%> checked="true"<%}%>/></td>
                <td style="text-align: center;">
                    <input name="columnVisible<%=colu.getURI()%>" type="checkbox" <%if (colu.isColumnVisible()) {%> checked="true"<%}%>/></td>
                <td style="text-align: center;">
                    <%if(sp.isDataTypeProperty()){%>
                    <input name="defaultValue<%=colu.getURI()%>" type="text"  value="<%=colu.getDefaultValue() == null ? "" : colu.getDefaultValue()%>"/>
                    <%if(sp.isNumeric() || sp.isDate()) {%>
                    <input name="defaultValueMax<%=colu.getURI()%>" type="text"  value="<%=colu.getDefaultValueMax() == null ? "" : colu.getDefaultValueMax()%>"/>
                    <%}}%>
                </td>
            </tr>
            <%
                }
            %>

        </table>
        </br>
        <%if (contador > 0) {%>
        <input type="hidden" name="idReport" value="<%=obj.getId()%>"/>
        <input type="submit" value="Actualizar columnas" title="Actualizar columnas"/>
        <%}%>
    </form>
</div>
<div class="bandeja-combo">
    <button title="Regresar" onclick="javascript:document.back.submit()">Regresar</button> 
    <form method="post" action="<%=paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)%>" name="back"></form>    
</div>           