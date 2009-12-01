<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%
    User useradmin=SWBContext.getAdminUser();
    if(useradmin==null)
    {
        response.sendError(403);
        return;
    }
%>
<%
            WebSite site = WebSite.ClassMgr.getWebSite("Ciudad_Digital");
            WebPage pagedemo = site.getWebPage("Int_Educacion");
            User user = SWBContext.getSessionUser();//User) request.getAttribute("user");
            if (user == null)
            {
%>
Debe correrse con un usuario del sitio
<%
                return;
            }

            for (int i = 1; i <= 100; i++)
            {
                String id = "" + i;
                MicroSite ms = MicroSite.ClassMgr.createMicroSite(id, site);
                ms.setParent(pagedemo);
                ms.setTitle("Comunidad " + i);
                ms.setDescription("Comunidad " + i);
                ms.setTags("Comunidad " + i);
                ms.setActive(Boolean.TRUE);
                ms.setCreated(new Date(System.currentTimeMillis()));
                ms.setCreator(user);
            }
//
//blogs
            for (int i = 1; i <= 100; i++)
            {
                String id = "" + i;
                MicroSite ms = MicroSite.ClassMgr.getMicroSite(id, site);
                Blog blog = Blog.ClassMgr.createBlog(id + "_" + ms.getId(), site);
                blog.setTitle("Blog " + i + "_" + ms.getId());
                blog.setDescription("Blog " + i + "_" + ms.getId());
                blog.setWebPage(ms);
                blog.setCreated(new Date(System.currentTimeMillis()));
                blog.setCreator(user);
                for (int j = 1; j <= 100; j++)
                {
                    PostElement element = PostElement.ClassMgr.createPostElement(j + "_" + i + "_" + ms.getId(), site);
                    element.setBlog(blog);
                    element.setContent("Contenido de prueba");
                    element.setDescription("Contenido de prueba");
                    element.setTitle("Contenido de prueba");
                    element.setCreated(new Date(System.currentTimeMillis()));
                    element.setCreator(user);

                }
            }
            // Eventos
            for (int i = 1; i <= 100; i++)
            {
                String id = "" + i;
                MicroSite ms = MicroSite.ClassMgr.getMicroSite(id, site);
                for (int j = 1; j <= 100; j++)
                {
                    EventElement element = EventElement.ClassMgr.createEventElement(j + "_" + i, site);
                    element.setEventWebPage(ms);
                    element.setCreated(new Date(System.currentTimeMillis()));
                    element.setCreator(user);
                    element.setDescription("Contenido de prueba");
                    element.setTitle("Contenido de prueba");
                    java.util.Calendar cal=java.util.Calendar.getInstance();
                    cal.setTime(new Date(System.currentTimeMillis()));
                    cal.add(cal.MONTH, 1);
                    element.setStartDate(cal.getTime());
                    element.setEndDate(cal.getTime());
                    element.setPlace("contenido de prueba");
                    element.setAudienceType("Todos");
                }
            }


            // Noticias
            for (int i = 1; i <= 100; i++)
            {
                String id = "" + i;
                MicroSite ms = MicroSite.ClassMgr.getMicroSite(id, site);
                for (int j = 1; j <= 100; j++)
                {
                    NewsElement element = NewsElement.ClassMgr.createNewsElement(j + "_" + i, site);
                    element.setNewsWebPage(ms);
                    element.setCreated(new Date(System.currentTimeMillis()));
                    element.setCreator(user);
                    element.setDescription("Contenido de prueba");
                    element.setTitle("Contenido de prueba");
                    element.setNewsImage("Quiero_viajar_al_Municipio.jpg");
                    element.setNewsThumbnail("Quiero_viajar_al_Municipio.jpg");
                    java.util.Calendar cal=java.util.Calendar.getInstance();
                    cal.setTime(new Date(System.currentTimeMillis()));
                    cal.add(cal.MONTH, 1);
                    element.setAuthor("Contenido de prueba");
                    element.setCitation("Contenido de prueba");
                    element.setFullText("Contenido de prueba");
                }
            }


            // Fotos
            for (int i = 1; i <= 100; i++)
            {
                String id = "" + i;
                MicroSite ms = MicroSite.ClassMgr.getMicroSite(id, site);
                for (int j = 1; j <= 100; j++)
                {
                    PhotoElement element = PhotoElement.ClassMgr.createPhotoElement(j + "_" + i, site);
                    element.setPhotoWebPage(ms);
                    element.setCreated(new Date(System.currentTimeMillis()));
                    element.setCreator(user);
                    element.setDescription("Contenido de prueba");
                    element.setTitle("Contenido de prueba");
                    element.setImageURL("Quiero_viajar_al_Municipio.jpg");
                    element.setPhotoThumbnail("Quiero_viajar_al_Municipio.jpg");
                    java.util.Calendar cal=java.util.Calendar.getInstance();
                    cal.setTime(new Date(System.currentTimeMillis()));
                    cal.add(cal.MONTH, 1);
                    element.setTags("Contenido de prueba");
                }
            }

            // Videos
            for (int i = 1; i <= 100; i++)
            {
                String id = "" + i;
                MicroSite ms = MicroSite.ClassMgr.getMicroSite(id, site);
                for (int j = 1; j <= 100; j++)
                {
                    VideoElement element = VideoElement.ClassMgr.createVideoElement(j + "_" + i, site);
                    element.setWebPage(ms);
                    element.setCreated(new Date(System.currentTimeMillis()));
                    element.setCreator(user);
                    element.setDescription("Contenido de prueba");
                    element.setTitle("Contenido de prueba");
                    java.util.Calendar cal=java.util.Calendar.getInstance();
                    cal.setTime(new Date(System.currentTimeMillis()));
                    cal.add(cal.MONTH, 1);
                    element.setTags("Contenido de prueba");
                }
            }




%>
Fin de creación