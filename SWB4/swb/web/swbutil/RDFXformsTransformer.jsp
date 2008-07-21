<%@page contentType="text/html"%>
<%@page import="org.semanticwb.*"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.xforms.*"%>
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
        if(tpc.getName().equals("WebPage"))
        {
            System.out.println("Jsp-1:"+tpc.getName());
            Iterator<Topic> tpit=tpc.listInstances();
            while(tpit.hasNext())
            {
                Topic tp=tpit.next();
                System.out.println("Jsp-2:"+tp.getName());
                RDFXformsTransformer xformMgr=new RDFXformsTransformer();
                xformMgr.createXformsDoc(tpc, tp);
            }
        }
    }
%>    
    </pre>
    
    </body>
</html>
