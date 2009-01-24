<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<pre>
<%
    String lang="es";
    Iterator<Device> itd=SWBContext.getWebSite("sep").listDevices();
    while(itd.hasNext())
    {
        out.println("Device:"+itd.next().getTitle());
    }

    Iterator<SemanticClass> it=SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
    while(it.hasNext())
    {
        SemanticClass cls=it.next();
        out.println("Class:"+cls.getClassId()+" "+cls.getDisplayName(lang));
        Iterator<SemanticProperty> it2=cls.listProperties();
        while(it2.hasNext())
        {
            SemanticProperty prop=it2.next();
            out.println("--->Prop:"+prop.getPropId()+" "+prop.getDisplayName(lang));
        }
    }
%>
</pre>