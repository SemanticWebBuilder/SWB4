<%@page contentType="text/html;charset=windows-1252"%>
<%@page import="com.infotec.wb.core.db.*"%>
<%@page import="com.infotec.wb.core.*"%>
<%@page import="com.infotec.wb.util.*"%>
<%@page import="com.infotec.topicmaps.db.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<html>
<head><title>JSP Page</title></head>
<body>

<%-- <jsp:useBean id="beanInstanceName" scope="session" class="package.class" /> --%>
<%-- <jsp:getProperty name="beanInstanceName"  property="propertyName" /> --%>
<pre>
<%
    //RecResource
    FileOutputStream file=new FileOutputStream(WBUtils.getInstance().getAppPath()+"/WEB-INF/classes/RecResource.rec");
    PrintWriter pout=new PrintWriter(file);
    int recs=0;
    Enumeration en=DBResource.getInstance().getResources(com.infotec.topicmaps.bean.TopicMgr.TM_ADMIN);
    while(en.hasMoreElements())
    {
        RecResource rec=(RecResource)en.nextElement();
        pout.println(rec.getEncoder());
        recs++;
    }
    pout.flush();
    pout.close();
    file.close();
    out.println("RecResource exported:"+recs);

    //RecGrpTemplate
    file=new FileOutputStream(WBUtils.getInstance().getAppPath()+"/WEB-INF/classes/RecGrpTemplate.rec");
    pout=new PrintWriter(file);
    recs=0;
    en=DBCatalogs.getInstance().getGrpTemplates(com.infotec.topicmaps.bean.TopicMgr.TM_ADMIN).elements();
    while(en.hasMoreElements())
    {
        RecGrpTemplate rec=(RecGrpTemplate)en.nextElement();
        pout.println(rec.getEncoder());
        recs++;
    }
    pout.flush();
    pout.close();
    file.close();
    out.println("RecGrpTemplate exported:"+recs);

    //RecLanguage
    file=new FileOutputStream(WBUtils.getInstance().getAppPath()+"/WEB-INF/classes/RecLanguage.rec");
    pout=new PrintWriter(file);
    recs=0;
    en=DBCatalogs.getInstance().getLanguages(com.infotec.topicmaps.bean.TopicMgr.TM_ADMIN).elements();
    while(en.hasMoreElements())
    {
        RecLanguage rec=(RecLanguage)en.nextElement();
        pout.println(rec.getEncoder());
        recs++;
    }
    pout.flush();
    pout.close();
    file.close();
    out.println("RecLanguage exported:"+recs);

    //RecResourceType
    file=new FileOutputStream(WBUtils.getInstance().getAppPath()+"/WEB-INF/classes/RecResourceType.rec");
    pout=new PrintWriter(file);
    recs=0;
    en=DBResourceType.getInstance().getResourceTypes(com.infotec.topicmaps.bean.TopicMgr.TM_ADMIN).elements();
    while(en.hasMoreElements())
    {
        RecResourceType rec=(RecResourceType)en.nextElement();
        pout.println(rec.getEncoder());
        recs++;
    }
    pout.flush();
    pout.close();
    file.close();
    out.println("RecResourceType exported:"+recs);

    //RecTemplate
    file=new FileOutputStream(WBUtils.getInstance().getAppPath()+"/WEB-INF/classes/RecTemplate.rec");
    pout=new PrintWriter(file);
    recs=0;
    en=DBTemplate.getInstance().getTemplates(com.infotec.topicmaps.bean.TopicMgr.TM_ADMIN);
    while(en.hasMoreElements())
    {
        RecTemplate rec=(RecTemplate)en.nextElement();
        pout.println(rec.getEncoder());
        recs++;
    }
    pout.flush();
    pout.close();
    file.close();
    out.println("RecTemplate exported:"+recs);

    //RecTopicMap
    file=new FileOutputStream(WBUtils.getInstance().getAppPath()+"/WEB-INF/classes/RecTopicMap.rec");
    pout=new PrintWriter(file);
    RecTopicMap rec2=DBTopicMap.getInstance().getTopicMap(com.infotec.topicmaps.bean.TopicMgr.TM_ADMIN);
    pout.println(rec2.getEncoder());
    pout.flush();
    pout.close();
    file.close();
    out.println("RecTopicMap exported");

    //RecTopic
    file=new FileOutputStream(WBUtils.getInstance().getAppPath()+"/WEB-INF/classes/RecTopic.rec");
    pout=new PrintWriter(file);
    recs=0;
    en=DBTopicMap.getInstance().getTopics(com.infotec.topicmaps.bean.TopicMgr.TM_ADMIN);
    while(en.hasMoreElements())
    {
        RecTopic rec=(RecTopic)en.nextElement();
        pout.println(rec.getEncoder());
        recs++;
    }
    pout.flush();
    pout.close();
    file.close();
    out.println("RecTopic exported:"+recs);

    //RecAssociation
    file=new FileOutputStream(WBUtils.getInstance().getAppPath()+"/WEB-INF/classes/RecAssociation.rec");
    pout=new PrintWriter(file);
    recs=0;
    en=DBTopicMap.getInstance().getAssociations(com.infotec.topicmaps.bean.TopicMgr.TM_ADMIN);
    while(en.hasMoreElements())
    {
        RecAssociation rec=(RecAssociation)en.nextElement();
        pout.println(rec.getEncoder());
        recs++;
    }
    pout.flush();
    pout.close();
    file.close();
    out.println("RecAssociation exported:"+recs);

    //RecOccurrence
    file=new FileOutputStream(WBUtils.getInstance().getAppPath()+"/WEB-INF/classes/RecOccurrence.rec");
    pout=new PrintWriter(file);
    recs=0;
    en=DBTopicMap.getInstance().getOccurrences(com.infotec.topicmaps.bean.TopicMgr.TM_ADMIN);
    while(en.hasMoreElements())
    {
        RecOccurrence rec=(RecOccurrence)en.nextElement();
        pout.println(rec.getEncoder());
        recs++;
    }
    pout.flush();
    pout.close();
    file.close();
    out.println("RecOccurrence exported:"+recs);


    //RecRule
    file=new FileOutputStream(WBUtils.getInstance().getAppPath()+"/WEB-INF/classes/RecRule.rec");
    pout=new PrintWriter(file);
    recs=0;
    en=DBRule.getInstance().getRules(com.infotec.topicmaps.bean.TopicMgr.TM_ADMIN);
    while(en.hasMoreElements())
    {
        RecRule rec=(RecRule)en.nextElement();
        pout.println(rec.getEncoder());
        recs++;
    }
    pout.flush();
    pout.close();
    file.close();
    out.println("RecRule exported:"+recs);
%>
</pre>

</body>
</html>
