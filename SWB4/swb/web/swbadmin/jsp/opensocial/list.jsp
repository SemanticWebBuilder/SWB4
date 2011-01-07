<%@page contentType="text/html"%>
<%@page import="org.semanticwb.opensocial.model.*,org.semanticwb.opensocial.resources.*,java.util.Date, java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    WebSite site=paramRequest.getWebPage().getWebSite();
    User user=paramRequest.getUser();
    Iterator<Gadget> gadgets=Gadget.ClassMgr.listGadgets(site);
    while(gadgets.hasNext())
    {
        Gadget gadget=gadgets.next();        
        String title=gadget.getTitle();
        String img="#";
        if(gadget.getThumbnail()!=null)
        {
            img=gadget.getThumbnail().toString();
        }
        SWBResourceURL edit=paramRequest.getRenderUrl();
        edit.setMode(SocialContainer.Mode_CONFIGGADGET);
        edit.setCallMethod(SWBResourceURL.Call_CONTENT);
        edit.setParameter("url", gadget.getUrl());
        SocialUser socialuser=SocialContainer.getSocialUser(user, session);
        if(socialuser.canAdd(gadget,site))
        {
            %>
            <p><img alt="<%=title%>"  src="<%=img%>"><a href="<%=edit%>"><%=title%></a></p>
            <%
        }
    }
%>
