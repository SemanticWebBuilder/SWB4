<%-- 
    Document   : addStream
    Created on : 05-ago-2014, 18:47:08
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%!
    String toCamelCase(String txt)
    {
        return (""+txt.charAt(0)).toUpperCase()+txt.substring(1);
    }
    
    public String getLocaleString(String key, String lang)
    {
        //return SWBUtils.TEXT.getLocaleString("locale_swb_admin", key, new Locale(lang));
        return SWBSocialResUtil.Util.getStringFromGenericLocale(key, lang); 
    }
%>
<%
    User user=SWBContext.getAdminUser();
    if(user==null)
    {
        response.sendError(403);
        return;
    }
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    String scls=request.getParameter("scls");
    String sref=request.getParameter("sref");
    
    SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(scls);
    SemanticObject obj=SWBPlatform.getSemanticMgr().getOntology().getSemanticObject(sref);
    
    if(request.getParameter("maxStream2add")!=null)
    {
        long maxStream2add=Long.parseLong(request.getParameter("maxStream2add"));
        long classInstNumber=SWBUtils.Collections.sizeOf(obj.getModel().listInstancesOfClass(cls)); 
        if(classInstNumber>=maxStream2add) {
            %>
                Usted ha alcanzado el mimite de Streams para crear de acuerdo a su tipo de licencia.
                Si tiene alguna duda, por favor contacte a su administrador de cuenta.
            <%
            return;
        }
    }    

    String lang=user.getLanguage(); 
 
%>
<form id="<%=scls%>/form" dojoType="dijit.form.Form" class="swbform" action="<%=org.semanticwb.SWBPlatform.getContextPath()%>/swbadmin/jsp/SemObjectEditor.jsp"  onsubmit="submitForm('<%=scls%>/form');return false;" method="post">
    <input type="hidden" name="scls" value="<%=scls%>"/>
    <input type="hidden" name="smode" value="create"/>
    <input type="hidden" name="sref" value="<%=sref%>"/>
	<fieldset>
	    <table>
            <tr>
                <td align="right">
                    <label for="title"><%=getLocaleString("title",lang)%> <em>*</em></label>
                </td><td>
                    <input type="text" id="swb_create_title" name="title" dojoType="dijit.form.TextBox" required="true" promptMessage="<%=getLocaleString("enter",lang)%> <%=getLocaleString("title",lang)%>." invalidMessage="<%=getLocaleString("title",lang)%> <%=getLocaleString("invalid",lang)%>." trim="true"/>
	            </td>
            </tr>
            <tr>
                <td align="center" colspan="2">
                    <button dojoType='dijit.form.Button' type="submit"><%=getLocaleString("save",lang)%></button>
                    <button dojoType='dijit.form.Button' onclick="dijit.byId('swbDialog').hide();"><%=getLocaleString("cancel",lang)%></button>
	            </td>
            </tr>
	    </table>
	</fieldset>
</form>