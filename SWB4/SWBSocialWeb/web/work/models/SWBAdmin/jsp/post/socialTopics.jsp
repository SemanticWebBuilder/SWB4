<%-- 
    Document   : createMenuPost
    Created on : 25/03/2013, 11:31:50 am
    Author     : Jorge.Jimenez
--%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.social.SocialSite,java.util.*,org.semanticwb.social.SocialTopic,org.semanticwb.model.WebSite,org.semanticwb.model.User,org.semanticwb.platform.SemanticObject,org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>


<%    
        WebSite wsite=paramRequest.getWebPage().getWebSite();
        if(request.getParameter("socialSite")==null) return;
        SemanticObject semObj=SemanticObject.getSemanticObject(request.getParameter("socialSite"));  
        SocialSite socialSite=(SocialSite)semObj.createGenericInstance();  
        if(socialSite==null) return;
        User user=paramRequest.getUser();  
        SWBResourceURL url = paramRequest.getRenderUrl();   
%>

<style>
    .campo {
        border: 0px;
        float: left;
        margin: 4px;
        padding: 0px;
        width: 300px;
    }
    .etiqueta {
        border: 0px;
        float: left;
        margin: 4px;
        padding: 0px;
        width: 300px;
        text-align: left;
    }
</style>

<div class="swbform">
    <form name="formTopics" id="formSites">
        <div id="pub-detalle">
    <table width="50%" border="0px">            
       <tr>
           <td colspan="3" style="text-align: center;" class="titulo">Seleccione un Tema:</td>        
       </tr>
       <tr>
           <td colspan="3" style="text-align: center;">&nbsp;</td>        
       </tr>
       <tr>
            <td style="text-align: center;">
                <select name="socialTopic" id="socialTopic" onchange="javascript:postHtml('<%=url.setMode("genericPost")%>?suri='+escape(document.formTopics.socialTopic[document.formTopics.socialTopic.selectedIndex].value), 'createPost');"> 
                     <option value="" selected="selected">Seleccione un Tema...</option>
                    <%
                        Iterator<SocialTopic> itSocialTopics=sortByDisplayNameSet(SocialTopic.ClassMgr.listSocialTopics(socialSite), user.getLanguage()); 
                        while(itSocialTopics.hasNext())
                        {
                            SocialTopic socialTopic=itSocialTopics.next(); 
                            %>
                                <option value="<%=socialTopic.getURI()%>"><%=socialTopic.getDisplayTitle(user.getLanguage())%></option>
                            <%
                        }
                    %>
                </select>
            </td>
            
       </tr>
       </form>
    </table>
        </div>
</div>
<div id="createPost" dojoType="dijit.layout.ContentPane">
</div>


<%!

    /**
     * Sort by display name set.
     *
     * @param it the it
     * @param lang the lang
     * @return the sets the
     */
    public static Iterator sortByDisplayNameSet(Iterator it, String lang) {
        TreeSet set = new TreeSet(new SWBComparator(lang)); 

        while (it.hasNext()) {
            set.add(it.next());
        }

        return set.descendingSet().iterator();
    }

%>