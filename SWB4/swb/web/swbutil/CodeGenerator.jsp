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
    SemanticMgr mgr=SWBContext.getSemanticMgr();
    Iterator<TopicClass> tpcit=mgr.getVocabulary().listTopicClasses();
    while(tpcit.hasNext())
    {
        TopicClass tpc=tpcit.next();
        out.println("Class:"+tpc.getName()+"\t");
        Iterator<TopicProperty> tppit=tpc.listProperties();
        while(tppit.hasNext())
        {
            TopicProperty tpp=tppit.next();
            if(tpp.isObjectProperty())
            {
                out.println("-->ObejctProp:"+tpp.getName()+"\t"+tpp.getRangeClass());
            }else if(tpp.isDataTypeProperty())
            {
                out.println("-->DataTypeProp:"+tpp.getName()+"\t"+tpp.getRangeDataType());
            }
        }
        
        TopicIterator tpit=tpc.listInstances();
        while(tpit.hasNext())
        {
            Topic tp=tpit.nextTopic();
            out.println("---->Instance:"+tp.getName());
            out.println("------>Prop_deleted:"+tp.getProperty(mgr.getVocabulary().getTopicProperty(SWBVocabulary.URI+"deleted")));
        }        
        
    }
%>    
    </pre>
    
    </body>
</html>
