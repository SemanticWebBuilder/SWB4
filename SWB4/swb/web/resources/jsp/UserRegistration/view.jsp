<%@page import="java.util.Iterator, org.semanticwb.model.PropertyGroup,
org.semanticwb.model.SWBComparator, org.semanticwb.platform.SemanticProperty,
org.semanticwb.model.FormElement, org.semanticwb.platform.SemanticObject,
org.semanticwb.model.DisplayProperty, org.semanticwb.model.GenericFormElement"
%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"
/><jsp:useBean id="data" scope="request" class="java.util.HashMap<org.semanticwb.model.PropertyGroup, java.util.TreeSet>"
/><%!
private FormElement getFormElement(SemanticProperty prop)
    {
        SemanticObject obj=prop.getDisplayProperty();
        FormElement ele=null;
        if(obj!=null)
        {
            DisplayProperty disp=new DisplayProperty(obj);
            SemanticObject feobj=disp.getFormElement();
            if(feobj!=null)
            {
                ele=(FormElement)feobj.createGenericInstance();
            }
        }
        //System.out.println("obj:"+obj+" prop:"+prop+" ele:"+ele);
        if(ele==null)ele=new GenericFormElement();
        return ele;
    }
%>VIEW
<form method="post" action="<%=paramRequest.getActionUrl()%>">
    <input type="hidden" name="type" value="first" />
<%
String lang = paramRequest.getUser().getLanguage();
if (null!=paramRequest.getUser().getSemanticObject().getId()){
    %><input type="hidden" name="suri" value="<%=paramRequest.getUser().getSemanticObject().getId()%>" /><%
}
try {
    
    Iterator<PropertyGroup> itgp=SWBComparator.sortSortableObject(data.keySet().iterator());
            while(itgp.hasNext())
            {
                PropertyGroup group=itgp.next();
                String nombre="Defecto";
                if (null !=group && null != group.getSemanticObject()) {
                    nombre = group.getSemanticObject().getDisplayName(lang);
                %>
	<fieldset>
        <legend><%=nombre%></legend>
	    <table>
<%
                Iterator<SemanticProperty> it=data.get(group).iterator();
                while(it.hasNext())
                {
                    SemanticProperty prop=it.next();
                    FormElement ele=getFormElement(prop);
                    %><tr><td><%=ele.renderLabel(paramRequest.getUser().getSemanticObject(), prop, null, null, lang)%></td>
                    <td><%=ele.renderElement(paramRequest.getUser().getSemanticObject(), prop, "web", "create", lang)%></td></tr>
<%  
                }%>
	    </table>
	</fieldset>
<%           } } }catch(Throwable es)
        {es.printStackTrace();}
if (paramRequest.getTopic().getWebSite().getUserRepository().getUserTypes().hasNext())
{
    String tipoUsu = "Tipos de usuario";
    %><fieldset>
        <legend><%=tipoUsu%></legend>
	    <table>
<%
Iterator itusrTypes = paramRequest.getTopic().getWebSite().getUserRepository().getUserTypes();
String labTU = "Tipo de usuario";
%><tr><td><label><%=labTU%></label></td><td>
            <SELECT NAME="wb_usr_type" ID="wb_usr_type" MULTIPLE="yes">
                <option value=""></option><%
            while (itusrTypes.hasNext()){
            String tipo=(String)itusrTypes.next();
            %><option value="<%=tipo%>" <% if (paramRequest.getUser().isRegistered() && paramRequest.getUser().hasUserType(tipo)) out.println("selected=\"yes\"");   %>><%=
                paramRequest.getTopic().getWebSite().getUserRepository().getUserType(tipo).getDisplayName(lang)
            %></option>
            <%
            }

            %></SELECT></td></tr></table></fieldset><%
            } else {System.out.println("Sin tipos");}

            %><fieldset><span align=\"center\"><input type="submit" value="Guardar" /></span></fieldset>
</form>