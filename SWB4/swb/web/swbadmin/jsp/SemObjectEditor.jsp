<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String id=request.getParameter("suri");
    
    //out.println("suri="+id);
%>
<form action="/swb/swbadmin/jsp/SemObjectEditor.jsp" method="post">
<%
    
    if(id==null)return;
    
    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    SemanticObject obj=ont.getSemanticObject(id);
    SemanticClass cls=obj.getSemanticClass();
    
    String update=request.getParameter("update");
    if(update!=null)
    {
        Iterator<SemanticProperty> it=cls.listProperties();
        while(it.hasNext())
        {
            SemanticProperty prop=it.next();
            if(prop.isDataTypeProperty())
            {
                String value=request.getParameter(prop.getName());
                if(value!=null)
                {
                    if(value.length()>0)
                    {
                        if(prop.isBoolean())obj.setBooleanProperty(prop, Boolean.parseBoolean(value));
                        if(prop.isInt())obj.setLongProperty(prop, Integer.parseInt(value));
                        if(prop.isString())obj.setProperty(prop, value);
                    }else
                    {
                        obj.removeProperty(prop);
                    }
                }
            }
        }
    }
    
    String snew=request.getParameter("new");
    if(snew!=null)
    {
        String sprop=request.getParameter("prop");
        SemanticProperty prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(sprop);
        SemanticClass ncls=prop.getRangeClass();
        if(ncls.isAutogenId())
        {
            long lid=SWBPlatform.getSemanticMgr().getCounter(obj.getModel().getName()+"/"+ncls.getName());
            SemanticObject nobj=obj.getModel().createSemanticObject(obj.getModel().getObjectUri(""+lid,ncls), ncls);
            obj.addObjectProperty(prop, nobj);
            obj=nobj;
            cls=ncls;
        }
    }    
%>
    <input type="hidden" name="suri" value="<%=obj.getURI()%>">
<%
    Iterator<SemanticProperty> it=cls.listProperties();
    while(it.hasNext())
    {
        SemanticProperty prop=it.next();
        if(prop.isDataTypeProperty())
        {

//<!--            Range:<%=prop.getRange()% >-->

            String value=obj.getProperty(prop);
            if(prop.isInt())value=""+obj.getIntProperty(prop);
            if(value==null)value="";
            
            String label=prop.getName();
            if(prop.getLabel()!=null)label=prop.getLabel();
%>
            <%=label%>: <input name="<%=prop.getName()%>" type="text" value="<%=value%>"><br>
<%      }else
        {
%>
            <%=prop.getName()%>: 
<%            
            Iterator<SemanticObject> soit=obj.listObjectProperties(prop);
            while(soit.hasNext())
            {
                SemanticObject obj2=soit.next();
%>            
            <a href="?suri=<%=obj2.getEncodedURI()%>">Objecto</a>
<%            
            }
%>
            <a  href="?suri=<%=obj.getEncodedURI()%>&prop=<%=prop.getEncodedURI()%>&new=true">Add New</a><br>
<%
        }
%>
<%
    }
%>
    <input type="submit" name="update">
</form>
