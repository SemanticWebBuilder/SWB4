<%@page contentType="text/html;charset=windows-1252"%>
<%@page import="com.infotec.wb.core.db.*"%>
<%@page import="com.infotec.wb.core.*"%>
<%@page import="com.infotec.wb.util.*"%>
<%@page import="com.infotec.topicmaps.db.*"%>
<%@page import="java.util.*"%>
<%@page import="com.infotec.appfw.util.db.*"%>
<%@page import="java.io.*"%>
<html>
<head><title>JSP Page</title></head>
<body>

<%-- <jsp:useBean id="beanInstanceName" scope="session" class="package.class" /> --%>
<%-- <jsp:getProperty name="beanInstanceName"  property="propertyName" /> --%>
<pre>
<%
    //RecResource
    BufferedReader in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/RecResource.rec")));
    String aux=null;
    int x=0;
    
    while((aux=in.readLine())!=null)
    {
        RecResource ru = new RecResource(new ObjectDecoder(aux));
        x++;
        ru.create();
    }
    in.close();
    out.println("RecResource imported:"+x);
    System.out.println("RecResource imported:"+x);

    //RecGrpTemplate
    in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/RecGrpTemplate.rec")));
    aux=null;
    x=0;
    while((aux=in.readLine())!=null)
    {
        RecGrpTemplate ru = new RecGrpTemplate(new ObjectDecoder(aux));
        x++;
        ru.create();
    }
    in.close();
    out.println("RecGrpTemplate imported:"+x);
    System.out.println("RecGrpTemplate imported:"+x);

    //RecLanguage
    in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/RecLanguage.rec")));
    aux=null;
    x=0;
    while((aux=in.readLine())!=null)
    {
        RecLanguage ru = new RecLanguage(new ObjectDecoder(aux));
        x++;
        ru.create();
    }
    in.close();
    out.println("RecGrpTemplate imported:"+x);
    System.out.println("RecGrpTemplate imported:"+x);


    //RecResourceType
    in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/RecResourceType.rec")));
    aux=null;
    x=0;
    while((aux=in.readLine())!=null)
    {
        RecResourceType ru = new RecResourceType(new ObjectDecoder(aux));
        x++;
        ru.create();
    }
    in.close();
    out.println("RecResourceType imported:"+x);
    System.out.println("RecResourceType imported:"+x);

    //RecTemplate
    in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/RecTemplate.rec")));
    aux=null;
    x=0;
    while((aux=in.readLine())!=null)
    {
        RecTemplate ru = new RecTemplate(new ObjectDecoder(aux));
        x++;
        ru.create();
    }
    in.close();
    out.println("RecTemplate imported:"+x);
    System.out.println("RecTemplate imported:"+x);

    //RecTopicMap
    in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/RecTopicMap.rec")));
    aux=null;
    x=0;
    while((aux=in.readLine())!=null)
    {
        RecTopicMap ru = new RecTopicMap(new ObjectDecoder(aux));
        x++;
        ru.create();
    }
    in.close();
    out.println("RecTopicMap imported:"+x);
    System.out.println("RecTopicMap imported:"+x);

    //RecTopic
    in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/RecTopic.rec")));
    aux=null;
    x=0;
    while((aux=in.readLine())!=null)
    {
        RecTopic ru = new RecTopic(new ObjectDecoder(aux));
        x++;
        ru.create();
    }
    in.close();
    out.println("RecTopic imported:"+x);
    System.out.println("RecTopic imported:"+x);

    //RecAssociation
    in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/RecAssociation.rec")));
    aux=null;
    x=0;
    while((aux=in.readLine())!=null)
    {
        RecAssociation ru = new RecAssociation(new ObjectDecoder(aux));
        x++;
        ru.create();
    }
    in.close();
    out.println("RecAssociation imported:"+x);
    System.out.println("RecAssociation imported:"+x);

    //RecOccurrence
    in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/RecOccurrence.rec")));
    aux=null;
    x=0;
    while((aux=in.readLine())!=null)
    {
        RecOccurrence ru = new RecOccurrence(new ObjectDecoder(aux));
        x++;
        ru.create();
    }
    in.close();
    out.println("RecOccurrence imported:"+x);
    System.out.println("RecOccurrence imported:"+x);


    //RecRule
    in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/RecRule.rec")));
    aux=null;
    x=0;
    while((aux=in.readLine())!=null)
    {
        RecRule ru = new RecRule(new ObjectDecoder(aux));
        x++;
        ru.create();
    }
    in.close();
    out.println("RecRule imported:"+x);
    System.out.println("RecRule imported:"+x);
%>
</pre>

</body>
</html>
