<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();

    int nwp = 0;

    Iterator<WebPage> itso = wpage.listChilds(user.getLanguage(),true,false,false,false);
    if(itso.hasNext())
    {
        while(itso.hasNext())
        {
            WebPage so = itso.next();
            if(so.getSemanticObject().getGenericInstance() instanceof WebPage && !(so.getSemanticObject().getGenericInstance() instanceof MicroSite) )
            {
                nwp++;
                break;
            }
        }
    }

    if(user.isRegistered()&&nwp==0)
    {
        SWBResourceURL urlAdd = paramRequest.getRenderUrl();
        urlAdd.setParameter("act", "add");
        urlAdd.setWindowState(SWBResourceURL.WinState_NORMAL);
        %>
        <div class="adminTools" >
            <a class="adminTool" href="<%=urlAdd%>">Crear Comunidad</a>
        </div>
        <%
    }
%>