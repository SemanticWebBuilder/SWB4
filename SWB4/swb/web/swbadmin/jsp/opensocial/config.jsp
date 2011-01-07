<%@page contentType="text/html"%>
<%@page import="org.w3c.dom.*,org.semanticwb.opensocial.model.*,org.semanticwb.opensocial.resources.*,java.util.Date, java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");    
    Gadget gadget=(Gadget) request.getAttribute("gadget");
    String title=gadget.getTitle();
    String url=gadget.getUrl();
    String description=gadget.getDescription();    
    SWBResourceURL processAction=paramRequest.getActionUrl();
    
%>

<p>
    <%=title%>
</p>
<p>
    <%=description%>
</p>
<form name="frmedit" action="<%=processAction%>" onsubmit="validate(this)">
    <input type="hidden" value="<%=url%>" name="__url__">    
<%
    Document doc=gadget.getDocument();
    NodeList userPrefs=doc.getElementsByTagName("UserPref");
    for(int i=0;i<userPrefs.getLength();i++)
    {
        if(userPrefs.item(i) instanceof Element)
        {
            Element userPref=(Element)userPrefs.item(i);
            String dataType="string";
            if(userPref.getAttribute("datatype")!=null && !userPref.getAttribute("datatype").equals(""))
            {
                dataType=userPref.getAttribute("datatype");
            }
            String name=userPref.getAttribute("name");
            String default_value=userPref.getAttribute("default_value");
            String displayName=userPref.getAttribute("display_name");
            if(displayName==null || displayName.equals(""))
            {
                displayName=name;
            }
            if("string".equals(dataType))
            {
                %>
                <%=displayName%>&nbsp;<input type="text" value="<%=default_value%>" name="<%=name%>"><br>
                <%
            }
            if("hidden".equals(dataType))
            {
                %>
                <input type="hidden" value="<%=default_value%>" name="<%=name%>">
                <%
            }
            if("enum".equals(dataType))
            {
                
                %>
                <%=displayName%>&nbsp;<select name="<%=name%>">
                    <%
                        NodeList enumValues=userPref.getElementsByTagName("EnumValue");
                        for(int j=0;j<enumValues.getLength();j++)
                        {
                            Element enumValue=(Element)enumValues.item(j);
                            String value=enumValue.getAttribute("value");
                            String dp=enumValue.getAttribute("display_value");                            
                            %>
                            <option value="<%=value%>"><%=dp%></option>
                            <%
                        }
                    %>
                </select><br>
                <%
            }
        }
    }
    
%>
<input type="submit" name="add" value="Agregar">
<input type="button" name="cancel" value="cancelar">
</form>

<script type="text/javascript">
    <!--
    function validate(form)
    {
        
    }
    -->
</script>