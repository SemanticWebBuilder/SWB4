<%@page contentType="text/xml"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

<%
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String suri=request.getParameter("suri");
    
    if(suri==null)
    {
        String code=SWBUtils.IO.readInputStream(request.getInputStream());
        System.out.println(code);
        return;
    }
    
    out.println(request.getParameter("smode"));
    
    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    SemanticObject obj=ont.getSemanticObject(suri);
    SWBFormMgr frm=new SWBFormMgr(obj, null,SWBFormMgr.MODE_EDIT);
    frm.setAction("/swb/swbadmin/jsp/SemObjectEditor.jsp");
    frm.processForm(request, response);
    out.println(frm.renderForm());
%>
<a href="#" onclick="submitUrl('/swb/swb',this); return false;">click</a>