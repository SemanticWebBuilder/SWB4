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
    <form name="formSites" id="formSites">
    <table width="50%" border="0px">            
       <tr>
           <td colspan="3" style="text-align: center;"><b>Seleccione una marca:</b></td>        
       </tr>
       <tr>
           <td colspan="3" style="text-align: center;">&nbsp;</td>        
       </tr>
       <tr>
            <td style="text-align: center;">
                <select name="socialSite" id="socialSite" onchange="javascript:postHtml('<%=url.setMode("afterChooseSite")%>?socialSite='+escape(document.formSites.socialSite[document.formSites.socialSite.selectedIndex].value), 'socialTopics');"> 
                     <option value="" selected="selected">Seleccione una marca...</option>
                    <%
                        Iterator<SocialSite> itSocialSites=sortByDisplayNameSet(SocialSite.ClassMgr.listSocialSites(), user.getLanguage());  
                        while(itSocialSites.hasNext())
                        {
                            SocialSite socialSite=itSocialSites.next(); 
                            %>
                                <option value="<%=socialSite.getURI()%>"><%=socialSite.getDisplayTitle(user.getLanguage())%></option>
                            <%
                        }
                    %>
                </select>
            </td>
            
       </tr>
       </form>
    </table>
</div>
<div id="socialTopics" dojoType="dijit.layout.ContentPane" style="width:100%; height:100%;">
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
