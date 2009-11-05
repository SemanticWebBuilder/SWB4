<%@page import="org.w3c.dom.*,org.semanticwb.portal.community.*,java.util.*,org.semanticwb.model.WebPage,org.semanticwb.platform.SemanticObject"%><%@page import="org.semanticwb.model.*,org.semanticwb.SWBPortal,org.semanticwb.SWBPlatform,org.semanticwb.platform.*,org.semanticwb.portal.api.SWBResourceURL"%><%!
    private Element addAtribute(Element ele, String name, String value)
    {
        Document doc = ele.getOwnerDocument();
        Element n = doc.createElement(name);
        ele.appendChild(n);
        n.appendChild(doc.createTextNode(value));
        return n;
    }
%><%

            if (request.getAttribute("webpage") != null)
            {
                int count = 0;
                WebSite site = ((WebPage) request.getAttribute("webpage")).getWebSite();
                User owner = (User) request.getAttribute("user");
                User user = owner;
                if (request.getParameter("user") != null)
                {
                    SemanticObject semObj = SemanticObject.createSemanticObject(request.getParameter("user"));
                    user = (User) semObj.createGenericInstance();
                }
                Iterator<MicroSiteElement> elements = PostElement.listMicroSiteElementByCreator(user, site);
                while (elements.hasNext())
                {
                    if (elements.next() instanceof PostElement)
                    {
                        count++;
                    }
                }
                String userURI = java.net.URLEncoder.encode(user.getURI());
                String pageUri="/swbadmin/jsp/microsite/perfil/showBlogsofUser.jsp";
                String siteid=site.getId();
%><li><a class="rss" href="<%=pageUri%>?user=<%=userURI%>&site=<%=siteid%>" >Mis artículos publicados (<%=count%>)</a></li><%
            }
            else
            {
                if (request.getParameter("user") != null && request.getParameter("site") != null)
                {
                    WebSite site=WebSite.ClassMgr.getWebSite(request.getParameter("site"));
                    SemanticObject semObj = SemanticObject.createSemanticObject(request.getParameter("user"));
                    User user = (User) semObj.createGenericInstance();
                    if (user != null && site!=null)
                    {
                        response.setContentType("application/rss+xml");
                        Document doc = org.semanticwb.SWBUtils.XML.getNewDocument();
                        Element rss = doc.createElement("rss");
                        rss.setAttribute("version", "2.0");
                        doc.appendChild(rss);

                        Element channel = doc.createElement("channel");
                        rss.appendChild(channel);

                        addAtribute(channel, "title", "Artículos publicados de " + user.getFullName());
                        addAtribute(channel, "link", "link");
                        addAtribute(channel, "description", "Artículos publicados de " + user.getFullName());


                       
                        Iterator<MicroSiteElement> elements = PostElement.listMicroSiteElementByCreator(user,site);
                        while (elements.hasNext())
                        {
                            Object obj=elements.next();
                            if (obj instanceof PostElement)
                            {
                                 Element item = doc.createElement("item");
                                channel.appendChild(item);
                                PostElement element=(PostElement)obj;
                                addAtribute(item, "title", element.getTitle());
                                addAtribute(item, "link", element.getURL());
                                addAtribute(item, "description", element.getDescription());
                                addAtribute(item, "pubDate", element.getCreated().toGMTString());                                
                                addAtribute(item, "guid", "cd_digital" + element.getURL() + "#rid" + element.getId());
                            }
                        }
                        out.write(org.semanticwb.SWBUtils.XML.domToXml(doc));
                    }
                    else
                    {
                        response.sendError(404);
                    }
                }
                else
                {
                    response.sendError(404);
                }
            }
%>