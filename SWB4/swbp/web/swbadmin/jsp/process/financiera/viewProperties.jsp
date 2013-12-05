<%-- 
    Document   : viewPeoperties
    Created on : 25/11/2013, 01:46:50 PM
    Author     : carlos.alvarez
--%>

<%@page import="org.semanticwb.financiera.model.Reference"%>
<%@page import="org.semanticwb.financiera.model.Definition"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.semanticwb.process.model.RepositoryDirectory"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.financiera.model.Format"%>
<%@page import="org.semanticwb.financiera.model.DocumentSection"%>
<%@page import="org.semanticwb.financiera.model.SectionElement"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    WebSite model = paramRequest.getWebPage().getWebSite();
    String lang = paramRequest.getUser().getLanguage();
    String idds = request.getAttribute("idds") != null ? request.getAttribute("idds").toString() : "";
    DocumentSection ds = DocumentSection.ClassMgr.getDocumentSection(idds, model);
    String classuri = request.getAttribute("classuri") != null ? request.getAttribute("classuri").toString() : "";
    SemanticClass cls = (SemanticClass) SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(classuri);
    if (cls != null) {
        String clsName = cls.getDisplayName(lang);
        SWBFormMgr forMgr = new SWBFormMgr(cls, model.getSemanticObject(), SWBFormMgr.MODE_EDIT);
        forMgr.clearProperties();
        Iterator<SemanticProperty> itsp = cls.listProperties();
        while (itsp.hasNext()) {
            SemanticProperty spi = itsp.next();
            if (spi.getDisplayProperty() != null) {
                forMgr.addProperty(spi);
            }
        }
        if (cls.getClassId().equals(Format.sclass.getClassId())
                || cls.getClassId().equals(Definition.sclass.getClassId())
                || cls.getClassId().equals(Reference.sclass.getClassId()) ) {%>
<br/>
<div class="form-group">
    <label for="" class="col-lg-3 control-label">Repository * :</label>
    <div class="col-lg-9">
        <select class="form-control" name="configdata" <% if (ds != null) {%> disabled="true" <%}%>>
            <option selected="true" value="">-- Select repository --</option>
            <%
                Iterator<WebPage> itwp = model.getHomePage().listChilds();
                while (itwp.hasNext()) {
                    WebPage wp = itwp.next();
                    if (wp instanceof RepositoryDirectory) {
                        String titlewp = wp.getTitle();
                        String uriwp = wp.getURI();
            %>
            <option value="<%=uriwp%>" <% if (ds != null && ds.getConfigData().equals(uriwp)) {%> selected="true" <%}%>><%=titlewp%></option>   
            <%}
            }%>
        </select>
    </div>
</div>
<%}
    itsp = forMgr.getProperties().iterator();
    if (itsp.hasNext()) {%>
<h3>Properties of: <%=clsName%></h3>
<table class="table table-hover swbp-table">
    <thead>
    <th>Property</th>
    <th>Label</th>
    <th>Visible</th>
        <%
            Map map = new HashMap();
            if (ds != null) {
                if (ds.getVisibleProperties().length() > 0) {
                    String[] values = ds.getVisibleProperties().split("\\|");
                    for (int i = 0; i < values.length; i++) {
                        String value = values[i];
                        String title = value.substring(0, value.indexOf(";"));
                        String propid = value.substring((value.indexOf(";") + 1), value.length());
                        map.put(propid, title);
                    }
                }
            }
            while (itsp.hasNext()) {
                SemanticProperty sp = itsp.next();
                String titlesp = sp.getDisplayName(lang);
                String idsp = sp.getPropId();
                String label = titlesp;
                if (ds != null && map.containsKey(idsp)) {
                    label = map.get(idsp).toString();
                }
        %>
    <tr>
        <td><%=titlesp%></td>
        <td><input type="text" name="label<%=idsp%>" value="<%=label%>" class="form-control"></td>
        <td>
            <input type="checkbox" name="<%=idsp%>" <% if (ds != null && ds.getVisibleProperties().contains(idsp)) {%> checked="true" <%}%>></td>
    </tr>
    <%}%>
</thead>
</table>
<%}
    }
%>