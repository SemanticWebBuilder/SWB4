<%@page import="org.w3c.dom.*,org.semanticwb.portal.community.*,java.util.*,org.semanticwb.model.WebPage,org.semanticwb.platform.SemanticObject"%><%@page import="org.semanticwb.model.*,org.semanticwb.SWBPortal,org.semanticwb.SWBPlatform,org.semanticwb.platform.*,org.semanticwb.portal.api.SWBResourceURL"%><%!
    private Element addAtribute(Element ele, String name, String value)
    {
        Document doc = ele.getOwnerDocument();
        Element n = doc.createElement(name);
        ele.appendChild(n);
        n.appendChild(doc.createTextNode(value));
        return n;
    }
%><%!    public String getTitle(WebPage webpage)
    {

        if (webpage instanceof MicroSite)
        {
            String title = webpage.getTitle();
            return title;
        }
        else
        {
            webpage = webpage.getParent();
            if (webpage != null && webpage instanceof MicroSite)
            {
                String title = webpage.getTitle();
                return title;
            }
        }
        return "";
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
                String pageUri = "/swbadmin/jsp/microsite/rss/rss.jsp";
                String siteid = site.getId();
%><li><a class="rss" href="<%=pageUri%>?user=<%=userURI%>&site=<%=siteid%>" >Mis artículos publicados (<%=count%>)</a></li><%
            }
            else
            {
                if (request.getParameter("user") != null && request.getParameter("site") != null)
                {
                    WebSite site = WebSite.ClassMgr.getWebSite(request.getParameter("site"));
                    SemanticObject semObj = SemanticObject.createSemanticObject(request.getParameter("user"));
                    User user = (User) semObj.createGenericInstance();
                    if (user != null && site != null)
                    {
                        response.setContentType("application/rss+xml");
                        Document doc = org.semanticwb.SWBUtils.XML.getNewDocument();
                        Element rss = doc.createElement("rss");
                        rss.setAttribute("version", "2.0");
                        doc.appendChild(rss);

                        Element channel = doc.createElement("channel");
                        rss.appendChild(channel);
                        String url = site.getWebPage("perfil").getUrl() + "?user=" + java.net.URLEncoder.encode(user.getURI());
                        addAtribute(channel, "title", "Artículos publicados de " + user.getFullName());
                        addAtribute(channel, "link", url);
                        addAtribute(channel, "description", "Artículos publicados de " + user.getFullName());



                        Iterator<MicroSiteElement> elements = PostElement.listMicroSiteElementByCreator(user, site);
                        while (elements.hasNext())
                        {
                            Object obj = elements.next();
                            if (obj instanceof PostElement)
                            {
                                Element item = doc.createElement("item");
                                channel.appendChild(item);
                                PostElement element = (PostElement) obj;
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
                else if (request.getParameter("event") != null)
                {
                    String eventURI = request.getParameter("event");
                    SemanticObject eventObj = SemanticObject.createSemanticObject(eventURI);
                    if (eventObj != null)
                    {
                        WebPage eventwebpage = new WebPage(eventObj);


                        response.setContentType("application/rss+xml");
                        Document doc = org.semanticwb.SWBUtils.XML.getNewDocument();
                        Element rss = doc.createElement("rss");
                        rss.setAttribute("version", "2.0");
                        doc.appendChild(rss);

                        Element channel = doc.createElement("channel");
                        rss.appendChild(channel);
                        String title=getTitle(eventwebpage);
                        addAtribute(channel, "title", "Eventos de la comunidad "+title);
                        addAtribute(channel, "link", eventwebpage.getUrl());
                        addAtribute(channel, "description", "Eventos de la comunidad "+title);



                        Iterator<EventElement> elements = EventElement.listEventElementByEventWebPage(eventwebpage);
                        while (elements.hasNext())
                        {
                            Object obj = elements.next();
                            if (obj instanceof EventElement)
                            {
                                Element item = doc.createElement("item");
                                channel.appendChild(item);
                                EventElement element = (EventElement) obj;
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
                else if (request.getParameter("photo") != null)
                {
                    String eventURI = request.getParameter("photo");
                    SemanticObject eventObj = SemanticObject.createSemanticObject(eventURI);
                    if (eventObj != null)
                    {
                        WebPage photowebpage = new WebPage(eventObj);


                        response.setContentType("application/rss+xml");
                        Document doc = org.semanticwb.SWBUtils.XML.getNewDocument();
                        Element rss = doc.createElement("rss");
                        rss.setAttribute("version", "2.0");
                        doc.appendChild(rss);

                        Element channel = doc.createElement("channel");
                        rss.appendChild(channel);
                        String title=getTitle(photowebpage);
                        addAtribute(channel, "title", "Fotos de la comunidad "+title);
                        addAtribute(channel, "link", photowebpage.getUrl());
                        addAtribute(channel, "description", "Fotos de la comunidad "+title);



                        Iterator<PhotoElement> elements = PhotoElement.listPhotoElementByPhotoWebPage(photowebpage);
                        while (elements.hasNext())
                        {
                            Object obj = elements.next();
                            if (obj instanceof PhotoElement)
                            {
                                Element item = doc.createElement("item");
                                channel.appendChild(item);
                                PhotoElement element = (PhotoElement) obj;
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
                else if (request.getParameter("news") != null)
                {
                    String eventURI = request.getParameter("news");
                    SemanticObject eventObj = SemanticObject.createSemanticObject(eventURI);
                    if (eventObj != null)
                    {
                        WebPage newswebpage = new WebPage(eventObj);


                        response.setContentType("application/rss+xml");
                        Document doc = org.semanticwb.SWBUtils.XML.getNewDocument();
                        Element rss = doc.createElement("rss");
                        rss.setAttribute("version", "2.0");
                        doc.appendChild(rss);

                        Element channel = doc.createElement("channel");
                        rss.appendChild(channel);
                        String title=getTitle(newswebpage);
                        addAtribute(channel, "title", "Noticias de la comunidad "+title);
                        addAtribute(channel, "link", newswebpage.getUrl());
                        addAtribute(channel, "description", "Noticias de la comunidad "+title);



                        Iterator<NewsElement> elements = NewsElement.listNewsElementByNewsWebPage(newswebpage);
                        while (elements.hasNext())
                        {
                            Object obj = elements.next();
                            if (obj instanceof NewsElement)
                            {
                                Element item = doc.createElement("item");
                                channel.appendChild(item);
                                NewsElement element = (NewsElement) obj;
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
                else if (request.getParameter("blog") != null)
                {
                    String blogURI = request.getParameter("blog");
                    SemanticObject objBlog = SemanticObject.createSemanticObject(blogURI);
                    if (objBlog != null)
                    {
                        Blog blog = new Blog(objBlog);



                        response.setContentType("application/rss+xml");
                        Document doc = org.semanticwb.SWBUtils.XML.getNewDocument();
                        Element rss = doc.createElement("rss");
                        rss.setAttribute("version", "2.0");
                        doc.appendChild(rss);

                        Element channel = doc.createElement("channel");
                        rss.appendChild(channel);
                        //String url = site.getWebPage("perfil").getUrl() + "?user=" + java.net.URLEncoder.encode(user.getURI());
                        addAtribute(channel, "title", blog.getTitle());
                        addAtribute(channel, "link", blog.getWebPage().getUrl());
                        addAtribute(channel, "description", blog.getDescription());



                        Iterator<PostElement> elements = blog.listPostElements();
                        while (elements.hasNext())
                        {
                            Object obj = elements.next();
                            if (obj instanceof PostElement)
                            {
                                Element item = doc.createElement("item");
                                channel.appendChild(item);
                                PostElement element = (PostElement) obj;
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