<%@page contentType="text/html"%>
<%@page import="java.net.*,org.semanticwb.opensocial.model.*,org.semanticwb.opensocial.resources.*,java.util.Date, java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    WebSite site=paramRequest.getWebPage().getWebSite();
    User user=paramRequest.getUser();
    SocialUser socialuser=SocialContainer.getSocialUser(user, session,site);
    SWBResourceURL proxy=paramRequest.getRenderUrl();
    proxy.setMode(SocialContainer.Mode_PROXY);
    proxy.setCallMethod(SWBResourceURL.Call_DIRECT);
    ArrayList<Gadget> _gadgets=new ArrayList<Gadget>();
    {
        Iterator<Gadget> gadgets=Gadget.ClassMgr.listGadgets(site);
        while(gadgets.hasNext())
        {
            Gadget gadget=gadgets.next();
            if(socialuser.canAdd(gadget))
            {
                _gadgets.add(gadget);
            }
        }
    }
    if(!_gadgets.isEmpty())
    {
        %>
        <table width="100%" cellpadding="2" cellspacing="2">
        <%        
        int rows=_gadgets.size()/3;
        if(_gadgets.size()%3!=0)
        {
            rows++;
        }
        for(int i=0;i<rows;i++)
        {
            Gadget g1=null;
            Gadget g2=null;
            Gadget g3=null;
            int index=i*3;
            if(_gadgets.size()>index)
            {
                g1=_gadgets.get(index);
            }
            index++;
            if(_gadgets.size()>index)
            {
                g2=_gadgets.get(index);
            }
            index++;
            if(_gadgets.size()>index)
            {
                g3=_gadgets.get(index);
            }
            
            %>
            <tr>
            <%
            if(g1!=null)
            {

                String title=g1.getDirectoryTitle(socialuser);
                if(title==null)
                {
                    title=g1.getTitle(socialuser);
                }                
                String img = SWBPortal.getContextPath() + "/swbadmin/jsp/opensocial/sinfoto.png";
                if(g1.getThumbnail()!=null)
                {
                    img=g1.getThumbnail().toString();
                    try
                    {
                        URL url=new URL(img);
                        if(!Gadget.existImage(url))
                        {
                            img = SWBPortal.getContextPath() + "/swbadmin/jsp/opensocial/sinfoto.png";
                        }
                    }
                    catch(Exception e)
                    {
                        img = SWBPortal.getContextPath() + "/swbadmin/jsp/opensocial/sinfoto.png";
                    }
                }
                SWBResourceURL edit=paramRequest.getRenderUrl();
                edit.setMode(SocialContainer.Mode_CONFIGGADGET);
                edit.setCallMethod(SWBResourceURL.Call_DIRECT);
                edit.setParameter("url", g1.getUrl());
                %>
                <td><img alt="<%=title%>"  width="120" height="60" src="<%=img%>"><a href="<%=edit%>"><br><%=title%></a></td>
                <%
            }
            else
            {
                %>
                <td>&nbsp;</td>
                <%
            }
            
            if(g2!=null)
            {
                String title=g2.getDirectoryTitle(socialuser);
                if(title==null)
                {
                    title=g2.getTitle(socialuser);
                }               
                String img = SWBPortal.getContextPath() + "/swbadmin/jsp/opensocial/sinfoto.png";
                if(g2.getThumbnail()!=null)
                {
                    img=g2.getThumbnail().toString();
                    try
                    {
                        URL url=new URL(img);
                        if(!Gadget.existImage(url))
                        {
                            img = SWBPortal.getContextPath() + "/swbadmin/jsp/opensocial/sinfoto.png";
                        }
                    }
                    catch(Exception e)
                    {
                        img = SWBPortal.getContextPath() + "/swbadmin/jsp/opensocial/sinfoto.png";
                    }
                }
                SWBResourceURL edit=paramRequest.getRenderUrl();
                edit.setMode(SocialContainer.Mode_CONFIGGADGET);
                edit.setCallMethod(SWBResourceURL.Call_CONTENT);

                

                edit.setParameter("url", g2.getUrl());
                %>
                <td><img alt="<%=title%>"  width="120" height="60" src="<%=img%>"><a href="<%=edit%>"><br><%=title%></a></td>
                <%
            }
            else
            {
                %>
                <td>&nbsp;</td>
                <%
            }
            if(g3!=null)
            {
                String title=g3.getDirectoryTitle(socialuser);
                if(title==null)
                {
                    title=g3.getTitle(socialuser);
                }                
                String img = SWBPortal.getContextPath() + "/swbadmin/jsp/opensocial/sinfoto.png";
                if(g3.getThumbnail()!=null)
                {
                    img=g3.getThumbnail().toString();
                    try
                    {
                        URL url=new URL(img);
                        if(Gadget.existImage(url))
                        {
                            img=proxy.toString()+"?url="+URLEncoder.encode(img);
                        }
                        else
                        {
                            img = SWBPortal.getContextPath() + "/swbadmin/jsp/opensocial/sinfoto.png";
                        }
                    }
                    catch(Exception e)
                    {
                        img = SWBPortal.getContextPath() + "/swbadmin/jsp/opensocial/sinfoto.png";
                    }
                }
                SWBResourceURL edit=paramRequest.getRenderUrl();
                edit.setMode(SocialContainer.Mode_CONFIGGADGET);
                edit.setCallMethod(SWBResourceURL.Call_CONTENT);
                edit.setParameter("url", g3.getUrl());
                %>
                <td><img alt="<%=title%>"  width="120" height="60" src="<%=img%>"><a href="<%=edit%>"><br><%=title%></a></td>
                <%
             }
            else
            {
                %>
                <td>&nbsp;</td>
                <%
            }

            %>
                </tr>
                <tr>
                    <td colspan="3">&nbsp;</td>
                </tr>
                <%
        }
        %>
        </table>
        <%
    }
%>
            