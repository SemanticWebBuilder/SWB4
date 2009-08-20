<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>


<%!

    private int getExistingCommunities(WebPage wp, User user)
    {
        int numc=0;
        Iterator<WebPage> itwp = wp.listChilds(user.getLanguage(), Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
        while(itwp.hasNext())
        {
            WebPage wpit = itwp.next();
            if(wpit.getSemanticObject().getGenericInstance() instanceof MicroSite)
            {
               numc++;
            }
        }
        return numc;
    }
%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpp=paramRequest.getWebPage();
    //MicroSiteWebPageUtil wputil=MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpp);

        WebPage wpch = null;
        WebPage wpgs = null;

        int numcomm = 0;
        int nums = 0;
        String nummsg = "";
        int wplevel=wpp.getLevel();

            if(wplevel==3)
                {
%>
            <h2 class="tituloInteres">Comunidades de <%=wpp.getDisplayTitle(user.getLanguage())%></h2>
<%
                }
%>

<table width="100%">
<%

        Iterator<WebPage> itwp = wpp.listChilds(user.getLanguage(), Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
        while(itwp.hasNext())
        {
            wpch = itwp.next();
            if(wpch.isActive()&&!wpch.isDeleted())
            {
                if(nums==0) {
%>
    <tr>
<%
                }
                String cssClass1 = "class=\"groupInteres\"";
                nums++;

%>
        <td style="vertical-align:top;">
<%
            if(wplevel==3)
                {
                    cssClass1 = "id=\"gruposInteres\"";
                }
%>
            <div <%=cssClass1%>>
<%

            if(wplevel==3)
                {
%>
                <ul>
<%
                }
            if(wplevel<3)
                {
%>
            <h3 class="titulo"><%=wpch.getDisplayTitle(user.getLanguage())%></h3>
<%

                
                Iterator<WebPage> itwpch = wpch.listChilds(user.getLanguage(), Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
                if(itwpch.hasNext())
                {
%>
            <ul>
<%
                    while(itwpch.hasNext())
                    {
                        wpgs = itwpch.next();
                        if(wpgs.isActive()&&!wpgs.isDeleted())
                        {
                            numcomm= getExistingCommunities(wpgs,user);
                            nummsg = "";
                            if(numcomm>0)  nummsg = "("+numcomm+")";
%>
                <li><a href="<%=wpgs.getUrl()%>"><%=wpgs.getDisplayTitle(user.getLanguage())%>&nbsp;<%=nummsg%></a></li>
<%
                        }
                        numcomm=0;

                    }

%>
            </ul>
<%
                   }
                    //out.println("<p class=\"vermas\"><a href=\""+wpch.getUrl()+"\">Ver todos</a></p>");
                
                }
                else
                {
%>
                <li><a href="<%=wpch.getUrl()%>"><%=wpch.getDisplayTitle(user.getLanguage())%>&nbsp;</a></li>
<%

                }

            if(wplevel==3)
                {
%>
                </ul>
                <div id="clear">&nbsp;</div>
<%
                }
%>
            </div>
<%
            if(wplevel==3)
                {
%>
            <div id="bottomInteres">&nbsp;</div>
<%
                }
%>
                   </td> 
<%
                if(nums==3)
                {
%>
   </tr>
<%
                    nums=0;
                }
            }

        }

                 if(nums<3)
                {
                    if(nums==2)
                    {
%>
                        <td></td>
<%
                    }
                    else if(nums==1)
                    {
%>
                        <td></td><td></td>
<%
                    }
%>
   </tr>
<%
                }

%>
</table>
