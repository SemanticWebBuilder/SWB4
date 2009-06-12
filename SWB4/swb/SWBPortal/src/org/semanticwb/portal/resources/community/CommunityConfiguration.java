/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources.community;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.catalogs.LocationEntity;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.resources.blog.BlogResource;
import org.semanticwb.portal.resources.googlegadgets.GoogleGadget;
import org.semanticwb.model.comm.*;

/**
 *
 * @author victor.lorenzana
 */
public class CommunityConfiguration extends GenericResource
{

    public static final String COMMUNITY_CONTAINER_ID = "Comunidades";
    public static final String[] resources;
    public static final String FORO_RESOURCE_TYPE_ID = "Forum";
    public static final String BLOG_RESOURCE_TYPE_ID = "BlogResource";
    public static final String WIKI_RESOURCE_TYPE_ID = "Wiki";
    public static final String GOOGLE_RESOURCE_TYPE_ID = "GoogleGadget";
    public static final String CONFIGURATION_RESOURCE_TYPE_ID = "CommunityConfiguration";


    static
    {
        resources = new String[4];
        resources[0] = FORO_RESOURCE_TYPE_ID;
        resources[1] = BLOG_RESOURCE_TYPE_ID;
        resources[2] = WIKI_RESOURCE_TYPE_ID;
        resources[3] = GOOGLE_RESOURCE_TYPE_ID;
    }
    private static Logger log = SWBUtils.getLogger(CommunityConfiguration.class);

    public static void createComunnityPerson(WebSite site, String title, LocationEntity locationentity, WebPage topic, String resourceByDefault)
    {
        WebPage communityContainer = site.getWebPage(COMMUNITY_CONTAINER_ID);
        PersonalComm pageTopic = PersonalComm.createPersonalComm(UUID.randomUUID().toString(), site);
        pageTopic.setParent(communityContainer);
        pageTopic.setTitle(title);
        pageTopic.setLocatedIn(locationentity);
        pageTopic.setAbout(topic);
        ResourceType resourcetype = site.getResourceType(CONFIGURATION_RESOURCE_TYPE_ID);
        Resource resource = site.createResource("conf_" + UUID.randomUUID().toString());
        resource.setActive(true);
        resource.setTitle("Recurso de configuración");
        pageTopic.addResource(resource);
        resource.setResourceType(resourcetype);

        pageTopic.setActive(true);

        TemplateRef tplref=site.createTemplateRef();
        tplref.setTemplate(site.getTemplate("12"));
        pageTopic.addTemplateRef(tplref);
        

        WebPage google = createGoogleGadget(site, title, pageTopic, resourceByDefault);
        TemplateRef tplrefGoogle=site.createTemplateRef();
        tplrefGoogle.setTemplate(site.getTemplate("13"));
        tplrefGoogle.setActive(true);
        google.addTemplateRef(tplrefGoogle);

        WebPage foro = createForo(site, title, pageTopic, resourceByDefault);
        TemplateRef tplrefForo=site.createTemplateRef();
        tplrefForo.setTemplate(site.getTemplate("13"));
        tplrefForo.setActive(true);
        foro.addTemplateRef(tplrefForo);

        WebPage blog = createBlog(site, title, pageTopic, resourceByDefault);
        TemplateRef tplrefBlog=site.createTemplateRef();
        tplrefBlog.setTemplate(site.getTemplate("13"));
        tplrefBlog.setActive(true);
        blog.addTemplateRef(tplrefBlog);

        WebPage wiki = createWiki(site, title, pageTopic, resourceByDefault);
        TemplateRef tplrefWiki=site.createTemplateRef();
        tplrefWiki.setTemplate(site.getTemplate("13"));
        tplrefWiki.setActive(true);
        wiki.addTemplateRef(tplrefWiki);
    }

    public static void createComunnityOrganization(WebSite site, String title, LocationEntity locationentity, WebPage topic, String resourceByDefault)
    {

        WebPage communityContainer = site.getWebPage(COMMUNITY_CONTAINER_ID);
        OrganizationComm pageTopic = OrganizationComm.createOrganizationComm(UUID.randomUUID().toString(), site);
        pageTopic.setAbout(topic);
        pageTopic.setLocatedIn(locationentity);

        ResourceType resourcetype = site.getResourceType(CONFIGURATION_RESOURCE_TYPE_ID);
        Resource resource = site.createResource("conf_" + UUID.randomUUID().toString());
        resource.setActive(true);
        resource.setTitle("Recurso de configuración");
        pageTopic.addResource(resource);
        resource.setResourceType(resourcetype);

        pageTopic.setParent(communityContainer);
        pageTopic.setTitle(title);
        pageTopic.setActive(true);

        TemplateRef tplref=site.createTemplateRef();
        tplref.setTemplate(site.getTemplate("12"));
        pageTopic.addTemplateRef(tplref);

        WebPage google = createGoogleGadget(site, title, pageTopic, resourceByDefault);
        TemplateRef tplrefGoogle=site.createTemplateRef();
        tplrefGoogle.setTemplate(site.getTemplate("13"));
        tplrefGoogle.setActive(true);
        google.addTemplateRef(tplrefGoogle);
        
        WebPage foro = createForo(site, title, pageTopic, resourceByDefault);
        TemplateRef tplrefForo=site.createTemplateRef();
        tplrefForo.setTemplate(site.getTemplate("13"));
        tplrefForo.setActive(true);
        foro.addTemplateRef(tplrefForo);
        
        WebPage blog = createBlog(site, title, pageTopic, resourceByDefault);
        TemplateRef tplrefBlog=site.createTemplateRef();
        tplrefBlog.setTemplate(site.getTemplate("13"));
        tplrefBlog.setActive(true);
        blog.addTemplateRef(tplrefBlog);
        
        WebPage wiki = createWiki(site, title, pageTopic, resourceByDefault);
        TemplateRef tplrefWiki=site.createTemplateRef();
        tplrefWiki.setTemplate(site.getTemplate("13"));
        tplrefWiki.setActive(true);
        wiki.addTemplateRef(tplrefWiki);
        
    }

    public static void createCommunityTopic(WebSite site, String title, LocationEntity locationentity, WebPage topic, String resourceByDefault,String description)
    {
        WebPage communityContainer = site.getWebPage(COMMUNITY_CONTAINER_ID);
        TematicComm pageTopic = TematicComm.createTematicComm(UUID.randomUUID().toString(), site);
        pageTopic.setDescription(description);
        pageTopic.setAbout(topic);
        pageTopic.setLocatedIn(locationentity);

        ResourceType resourcetype = site.getResourceType(CONFIGURATION_RESOURCE_TYPE_ID);
        Resource resource = site.createResource("conf_" + UUID.randomUUID().toString());
        resource.setActive(true);
        resource.setTitle("Recurso de configuración");
        pageTopic.addResource(resource);
        resource.setResourceType(resourcetype);


        pageTopic.setParent(communityContainer);
        pageTopic.setTitle(title);
        pageTopic.setActive(true);

        TemplateRef tplref=site.createTemplateRef();
        tplref.setTemplate(site.getTemplate("12"));
        tplref.setActive(true);
        pageTopic.addTemplateRef(tplref);

       /*
        createGoogleGadget(site, title, pageTopic, resourceByDefault);
        createForo(site, title, pageTopic, resourceByDefault);
        createBlog(site, title, pageTopic, resourceByDefault);
        createWiki(site, title, pageTopic, resourceByDefault);
        * */
        WebPage google = createGoogleGadget(site, title, pageTopic, resourceByDefault);
        TemplateRef tplrefGoogle=site.createTemplateRef();
        tplrefGoogle.setTemplate(site.getTemplate("13"));
        tplrefGoogle.setActive(true);
        google.addTemplateRef(tplrefGoogle);

        WebPage foro = createForo(site, title, pageTopic, resourceByDefault);
        TemplateRef tplrefForo=site.createTemplateRef();
        tplrefForo.setTemplate(site.getTemplate("13"));
        tplrefForo.setActive(true);
        foro.addTemplateRef(tplrefForo);

        WebPage blog = createBlog(site, title, pageTopic, resourceByDefault);
        TemplateRef tplrefBlog=site.createTemplateRef();
        tplrefBlog.setTemplate(site.getTemplate("13"));
        tplrefBlog.setActive(true);
        blog.addTemplateRef(tplrefBlog);

        WebPage wiki = createWiki(site, title, pageTopic, resourceByDefault);
        TemplateRef tplrefWiki=site.createTemplateRef();
        tplrefWiki.setTemplate(site.getTemplate("13"));
        tplrefWiki.setActive(true);
        wiki.addTemplateRef(tplrefWiki);
        
    }

    public static WebPage createForo(WebSite site, String title, WebPage parent, String resourceByDefault)
    {
        WebPage foro = null;
        if (FORO_RESOURCE_TYPE_ID.equals(resourceByDefault))
        {
            foro = parent;
        }
        else
        {
            if (existsWebPage(FORO_RESOURCE_TYPE_ID, parent))
            {
                foro = getWebPage(FORO_RESOURCE_TYPE_ID, parent);
            }
            else
            {
                foro = WebPage.createWebPage(FORO_RESOURCE_TYPE_ID + UUID.randomUUID().toString(), site);
                foro.setParent(parent);
                foro.setActive(true);
                foro.setHidden(true);
                foro.setCreated(new java.util.Date(System.currentTimeMillis()));
                foro.setTitle("Foro de " + title);
            }
        }

        if (!existsResource(FORO_RESOURCE_TYPE_ID, foro))
        {
            ResourceType resourceType = site.getResourceType(FORO_RESOURCE_TYPE_ID);
            Resource resource = site.createResource();
            resource.setResourceType(resourceType);
            resource.setTitle("Foro de " + title);
            resource.setActive(true);
            foro.addResource(resource);
        }

        return foro;
    }

    public static WebPage createWiki(WebSite site, String title, WebPage parent, String resourceByDefault)
    {
        WebPage wiki = null;
        if (WIKI_RESOURCE_TYPE_ID.equals(resourceByDefault))
        {
            wiki = parent;
        }
        else
        {
            if (existsWebPage(WIKI_RESOURCE_TYPE_ID, parent))
            {
                wiki = getWebPage(WIKI_RESOURCE_TYPE_ID, parent);
            }
            else
            {
                wiki = WebPage.createWebPage(WIKI_RESOURCE_TYPE_ID + UUID.randomUUID().toString(), site);
                wiki.setTitle("Wiki de " + title);
                wiki.setParent(parent);
                wiki.setHidden(true);
                wiki.setCreated(new java.util.Date(System.currentTimeMillis()));
                wiki.setActive(true);
            }
        }

        if (!existsResource(WIKI_RESOURCE_TYPE_ID, wiki))
        {
            ResourceType resourceType = site.getResourceType(WIKI_RESOURCE_TYPE_ID);
            Resource resource = site.createResource();
            resource.setResourceType(resourceType);
            resource.setTitle("Wiki de " + title);
            resource.setActive(true);
            wiki.addResource(resource);
        }
        return wiki;
    }

    public static WebPage createBlog(WebSite site, String title, WebPage parent, String resourceByDefault)
    {
        WebPage blog = null;
        if (BLOG_RESOURCE_TYPE_ID.equals(resourceByDefault))
        {
            blog = parent;
        }
        else
        {
            if (existsWebPage(BLOG_RESOURCE_TYPE_ID, parent))
            {
                blog = getWebPage(BLOG_RESOURCE_TYPE_ID, parent);
            }
            else
            {
                blog = WebPage.createWebPage(BLOG_RESOURCE_TYPE_ID + UUID.randomUUID().toString(), site);
                blog.setParent(parent);
                blog.setTitle("Blog de " + title);
                blog.setHidden(true);
                blog.setCreated(new java.util.Date(System.currentTimeMillis()));
                blog.setActive(true);
            }
        }

        if (!existsResource(BLOG_RESOURCE_TYPE_ID, blog))
        {
            ResourceType resourceType = site.getResourceType(BLOG_RESOURCE_TYPE_ID);
            Resource resource = site.createResource();
            resource.setResourceType(resourceType);
            resource.setTitle("Blog de " + title);
            resource.setActive(true);
            blog.addResource(resource);
            BlogResource blogResource = new BlogResource();
            try
            {
                blogResource.setResourceBase(resource);
                blogResource.createBlog("Blog de " + title, true);
                blogResource.asignRole("role_*", 3, Integer.parseInt(blogResource.getResourceBase().getId()));
                resource.setActive(true);
            }
            catch (Exception e)
            {
            }
        }

        return blog;
    }

    private static WebPage createGoogleGadget(WebSite site, String title, WebPage parent, String resourceByDefault)
    {
        WebPage googlegadget = null;
        if (GOOGLE_RESOURCE_TYPE_ID.equals(resourceByDefault))
        {
            googlegadget = parent;
        }
        else
        {
            if (existsWebPage(GOOGLE_RESOURCE_TYPE_ID, parent))
            {
                googlegadget = getWebPage(GOOGLE_RESOURCE_TYPE_ID, parent);
            }
            else
            {
                googlegadget = WebPage.createWebPage(GOOGLE_RESOURCE_TYPE_ID + UUID.randomUUID().toString(), site);
                googlegadget.setParent(parent);
                googlegadget.setTitle("Gadget de " + title);
                googlegadget.setHidden(true);
                googlegadget.setCreated(new java.util.Date(System.currentTimeMillis()));
                googlegadget.setActive(true);
            }
        }


        if (existsResource(GOOGLE_RESOURCE_TYPE_ID, parent))
        {
            ResourceType resourceType = site.getResourceType(GOOGLE_RESOURCE_TYPE_ID);
            if (resourceType != null)
            {
                Resource resource = site.createResource();
                resource.setResourceType(resourceType);
                resource.setTitle("Gadget de " + title);
                googlegadget.addResource(resource);
                resource.setActive(true);
                GoogleGadget googleGadget = new GoogleGadget();
                try
                {
                    googleGadget.setResourceBase(resource);
                    //googleGadget.asignGoogleGadget("http%3A%2F%2Fthrottled.org%2Fgooglegadgets%2Fyoutubesearch.xml");
                    Hashtable<String, String> values = new Hashtable<String, String>();
                    values.put("w", "320");
                    values.put("h", "250");
                    values.put("title", "Búsqueda en YouTube");
                    googleGadget.asignGoogleGadget("http://throttled.org/googlegadgets/youtubesearch.xml", values);
                    resource.setActive(true);
                }
                catch (Exception e)
                {
                    log.error(e);
                }

            }
        }
        return googlegadget;
    }

    public static boolean existsWebPage(String resourceID, WebPage parent)
    {
        GenericIterator<WebPage> childs = parent.listChilds();
        while (childs.hasNext())
        {
            WebPage child = childs.next();
            if (child.getId().startsWith(resourceID))
            {
                return true;
            }
        }
        return false;
    }

    public static WebPage getWebPage(String resourceID, WebPage parent)
    {
        GenericIterator<WebPage> childs = parent.listChilds();
        while (childs.hasNext())
        {
            WebPage child = childs.next();
            if (child.getId().startsWith(resourceID))
            {
                return child;
            }
        }
        return null;
    }

    public static boolean existsResource(String resourceId, WebPage parent)
    {
        Iterator<Resource> resourcesInWebPage = parent.listResources();
        while (resourcesInWebPage.hasNext())
        {
            Resource resource = resourcesInWebPage.next();
            if (resource.getResourceType().getId().equals(resourceId))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        try{
        String[] values = request.getParameterValues("type");
        WebPage page = response.getTopic();
        WebSite site = page.getWebSite();
        for (String resourceTypeId : values)
        {
            ResourceType type = site.getResourceType(resourceTypeId);
            if (type != null)
            {
                if (!existsWebPage(resourceTypeId, page))
                {
                    WebPage pageRec=null;
                    if(resourceTypeId.equals(FORO_RESOURCE_TYPE_ID)){
                        pageRec = createForo(site, page.getTitle(), page, "foro");
                    }else if(resourceTypeId.equals(BLOG_RESOURCE_TYPE_ID)){
                        pageRec = createBlog(site, page.getTitle(), page, "blog");
                    }else if(resourceTypeId.equals(WIKI_RESOURCE_TYPE_ID)){
                        pageRec = createWiki(site, page.getTitle(), page, "wiki");
                    }else if(resourceTypeId.equals(GOOGLE_RESOURCE_TYPE_ID)){
                        pageRec = createGoogleGadget(site, page.getTitle(), page, "gadget");
                    }
                    TemplateRef tplrefRec=site.createTemplateRef();
                    tplrefRec.setTemplate(site.getTemplate("13"));
                    tplrefRec.setActive(true);
                    pageRec.addTemplateRef(tplrefRec);
                }
            }
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        response.setAction(response.Mode_VIEW);
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        StringBuffer strbf=new StringBuffer();
        boolean flag=false;
        strbf.append("<form action='" + paramRequest.getActionUrl() + "' method='post'>");
        strbf.append("<table>");
        strbf.append("<tr><td><b>Seleccione las utilerias que desea agregar:</b></td></tr>");
        WebPage page = paramRequest.getTopic();
        HashSet<String> resourcesAlreadyExists = getResources(page);
        for (String resourceId : resources)
        {
            boolean exists = false;
            for (String resource : resourcesAlreadyExists)
            {
                if (resource.startsWith(resourceId))
                {
                    exists = true;
                    break;
                }
            }
            if (!exists)
            {
                strbf.append("<tr>");
                strbf.append("<td>"+resourceId+"</td>");
                strbf.append("<td><input name='type' value='" + resourceId + "' type='checkbox'></td>");
                strbf.append("</tr>");
                flag=true;
            }
        }
        strbf.append("<tr>");
        strbf.append("<td><button type='submit'>Enviar</button></td>");
        strbf.append("</tr>");
        strbf.append("</table>");
        strbf.append("</form>");
        if(flag) out.println(strbf.toString());
  }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        doAdmin(request, response, paramRequest);
    }

    public HashSet<String> getResources(WebPage page)
    {
        HashSet<String> resourcesPage = new HashSet<String>();
        Iterator<WebPage> childs = page.listChilds();
        while (childs.hasNext())
        {
            WebPage child = childs.next();
            //resourcesPage.addAll(getResources(child));
            resourcesPage.add(child.getId());
        }
        return resourcesPage;
    }

}
