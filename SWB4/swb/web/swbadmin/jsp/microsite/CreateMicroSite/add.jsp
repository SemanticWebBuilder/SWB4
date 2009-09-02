<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
%>
<script type="text/javascript">
    dojo.require("dijit.form.ValidationTextBox");
</script>
<div class="soria">
    <fieldset><legend>Nueva comunidad</legend>
<%
    SWBFormMgr mgr=new SWBFormMgr(MicroSite.sclass, wpage.getSemanticObject(), null);
    mgr.setType(mgr.TYPE_DOJO);
    mgr.setAction(paramRequest.getActionUrl().toString());
    mgr.addProperty(Descriptiveable.swb_description);
    mgr.addProperty(WebPage.swb_tags);
    mgr.addProperty(MicroSite.swbcomm_type);
    mgr.addProperty(MicroSite.swbcomm_private);
    mgr.addProperty(MicroSite.swbcomm_moderate);
    mgr.addProperty(MicroSiteType.swbcomm_hasMicroSiteUtil);
    mgr.addButton(SWBFormButton.newSaveButton());
    mgr.addButton(SWBFormButton.newCancelButton().setAttribute("onclick", "window.location='"+paramRequest.getRenderUrl()+"';"));
    mgr.addHiddenParameter("act", "add");
    out.println(mgr.renderForm(request));
%>
    </fieldset>
</div>