<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    String suri=request.getParameter("suri");
    

    //out.println(suri+" "+smodel+" "+Thread.currentThread().getName());
    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    SemanticClass clase=ont.createSemanticClass(suri);
    //out.println(clase.getURI());

    out.println("<pre>");
    Iterator<SemanticProperty>itsp = clase.listProperties();
    while(itsp.hasNext()){
        out.println(itsp.next());
    }
    out.println("</pre>");
    SWBFormMgr frm=new SWBFormMgr(clase, null,SWBFormMgr.MODE_VIEW);
        frm.processForm(request);
        frm.setAction("/swb/swbadmin/jsp/SemClassEditor.jsp");
        out.println(frm.renderForm());
%>
