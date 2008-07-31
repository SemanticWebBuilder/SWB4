<%@page contentType="text/html"%>
<%@page import="org.semanticwb.*"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <h1>Code Generator</h1>
    <pre>
<%
    SemanticMgr mgr=SWBInstance.getSemanticMgr();
    Iterator<SemanticClass> tpcit=mgr.getVocabulary().listSemanticClasses();
    while(tpcit.hasNext())
    {
        SemanticClass tpc=tpcit.next();
        out.println("Class:"+tpc.getName()+"\t");
        Iterator<SemanticProperty> tppit=tpc.listProperties();
        while(tppit.hasNext())
        {
            SemanticProperty tpp=tppit.next();
            if(tpp.isObjectProperty())
            {
                out.println("-->ObjectProp:"+tpp.getName()+"\t"+tpp.getRangeClass());
            }else if(tpp.isDataTypeProperty())
            {
                out.println("-->DataTypeProp:"+tpp.getName()+"\t"+tpp.getRangeDataType());
            }
        }
        
        Iterator<SemanticObject> tpit=tpc.listInstances();
        while(tpit.hasNext())
        {
            SemanticObject tp=tpit.next();
            out.println("---->Instance:"+tp.getName());
            //out.println("------>Prop_deleted:"+tp.getProperty(mgr.getVocabulary().getSemanticProperty(SemanticVocabulary.URI+"deleted")));
        }        
        
    }
%>    
    </pre>
    
    </body>
</html>
