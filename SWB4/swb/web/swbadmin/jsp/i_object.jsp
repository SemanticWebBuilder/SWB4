<%@page contentType="text/html"%>
<%@page pageEncoding="ISO8859-1"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    SemanticVocabulary voc=SWBPlatform.getSemanticMgr().getVocabulary();
    String id=request.getParameter("suri");
    
    if(id==null)return;
    
    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    SemanticObject obj=ont.getSemanticObject(id);
    SemanticClass cls=obj.getSemanticClass();    
    
    String title=obj.getProperty(SWBContext.getVocabulary().title);
    
%>
    <div id="<%=id%>" title="<%=title%>" class="panel" selected="true">
        <h2>Propiedades</h2>
        <fieldset>
<%
    Iterator<SemanticProperty> it=cls.listProperties();
    while(it.hasNext())
    {
        SemanticProperty prop=it.next();
        if(prop.isDataTypeProperty())
        {
            String value=obj.getProperty(prop);
            if(prop.isInt())value=""+obj.getIntProperty(prop);
            if(value==null)value="";
            
            String label=prop.getName();
            if(prop.getLabel()!=null)label=prop.getLabel();
%>
            <div class="row">
                <label><%=label%></label>
<%
                if(prop.isBoolean())
                {
%>    
                <div class="toggle" onclick="" toggled="<%=value%>"><span class="thumb"></span><span class="toggleOn">ON</span><span class="toggleOff">OFF</span></div>
<%
                }else{
%>  
                <input type="text" name="<%=prop.getName()%>" value="<%=value%>"/>
<%
                }
%>                
            </div>
<%      }
    }
%>
        </fieldset>

        <h2>Objetos</h2>
        <fieldset>
<%
    it=cls.listProperties();
    while(it.hasNext())
    {
        SemanticProperty prop=it.next();
        if(prop.isObjectProperty())
        {
            String label=prop.getName();
            if(prop.getLabel()!=null)label=prop.getLabel();
            
            if(prop.getName().startsWith("has"))
            {
                
            }else
            {
                SemanticObject sobj=obj.getObjectProperty(prop);
                if(sobj==null)
                {
%>
            <div class="row">
                <label><%=label%></label>
                <select name="<%=prop.getName()%>">     
                    <option>No seleccionada</option>
                </select>                
            </div>
<%
                }else
                {
%>
            <div class="row">
                <label><%=label%></label>
                <a href="i_object.jsp?suri=<%=sobj.getEncodedURI()%>"><%=sobj.getRDFName()%></a>
            </div>
<%
                }
            }
        }
    }
%>
        </fieldset>
        <input type="button" value="Eliminar"/>
    </div>   