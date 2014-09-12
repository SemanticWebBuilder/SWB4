/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.resources.sem.videolibrary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.CalendarRef;
import org.semanticwb.model.Device;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceCollectionCategory;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBVideoLibrary.
 */
public class SWBVideoLibrary extends org.semanticwb.portal.resources.sem.videolibrary.base.SWBVideoLibraryBase
{

    /**
     * The Constant log.
     */
    private static final Logger log = SWBUtils.getLogger(SWBVideoLibrary.class);

    /**
     * Instantiates a new sWB video library.
     */
    public SWBVideoLibrary()
    {
    }

    /**
     * Checks for video.
     *
     * @param contents the contents
     * @param month the month
     * @param year the year
     * @return true, if successful
     */
    public static boolean hasVideo(List<VideoContent> contents, int month, int year)
    {
        Calendar calendar = Calendar.getInstance();
        for (VideoContent content : contents)
        {
            if (content.getPublishDate() != null)
            {
                calendar.setTime(content.getPublishDate());
                int newmonth = calendar.get(Calendar.MONTH);
                int new_year = calendar.get(Calendar.YEAR);
                if (month == newmonth && year == new_year)
                {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Gets the video by month.
     *
     * @param contents the contents
     * @return the video by month
     */
    private Map<String, List<VideoContent>> getVideoByMonth(List<VideoContent> contents)
    {
        Calendar calendar = Calendar.getInstance();
        TreeMap<String, List<VideoContent>> getVideoByMonth = new TreeMap<String, List<VideoContent>>();
        for (VideoContent content : contents)
        {
            if (content.getPublishDate() != null)
            {
                calendar.setTime(content.getPublishDate());
                int newmonth = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                String key = newmonth + "_" + year;

                List<VideoContent> contentsMonth = getVideoByMonth.get(key);
                if (contentsMonth == null)
                {
                    contentsMonth = new ArrayList<VideoContent>();
                    getVideoByMonth.put(key, contentsMonth);
                }
                contentsMonth.add(content);
            }
        }
        return getVideoByMonth;
    }

    /**
     * Gets the years.
     *
     * @param contents the contents
     * @return the years
     */
    public static String[] getYears(List<VideoContent> contents)
    {
        HashSet<String> getyears = new HashSet<String>();
        Calendar calendar = Calendar.getInstance();
        for (VideoContent content : contents)
        {
            if (content.getPublishDate() != null)
            {
                calendar.setTime(content.getPublishDate());
                int new_year = calendar.get(Calendar.YEAR);
                getyears.add(String.valueOf(new_year));
            }
        }
        return getyears.toArray(new String[getyears.size()]);
    }

    /**
     * Instantiates a new sWB video library.
     *
     * @param base the base
     */
    public SWBVideoLibrary(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the videos.
     *
     * @param uri the uri
     * @param user the user
     * @return the videos
     */
    private List<VideoContent> getVideos(String uri, User user)
    {
        List<VideoContent> list = new ArrayList<VideoContent>();
        GenericIterator<Resource> resources = null;
        if (this.getCollection() != null && this.getCollection().getResourceCollectionType().getResourceClassName().equals(VideoContent.class.getCanonicalName()))
        {
            if (this.getCategory() == null)
            {
                resources = this.getCollection().listResources();
            }
            else
            {
                boolean isCategoryOfCallection = false;
                GenericIterator<ResourceCollectionCategory> categories = this.getCollection().listCategories();
                while (categories.hasNext())
                {
                    ResourceCollectionCategory category = categories.next();
                    if (category.getURI().equals(this.getCategory().getURI()))
                    {
                        isCategoryOfCallection = true;
                        break;
                    }
                }
                if (isCategoryOfCallection)
                {
                    resources = this.getCategory().listResources();
                }
                else
                {
                    resources = this.getCollection().listResources();
                }
            }
        }

        if (resources != null)
        {
            while (resources.hasNext())
            {
                Resource resource = resources.next();
                Iterator<CalendarRef> refs = resource.listCalendarRefs();
                boolean isOnSchedule = true;
                while (refs.hasNext())
                {
                    CalendarRef ref = refs.next();
                    if (ref.isValid() && ref.isActive() && ref.getCalendar().isActive() && ref.getCalendar().isValid())
                    {
                        if (!ref.getCalendar().isOnSchedule())
                        {
                            isOnSchedule = false;
                        }
                    }
                }
                Device d = resource.getDevice();
                boolean device = true;
                if (d != null)
                {
                    if (!user.hasDevice(d))
                    {
                        device = false;

                    }
                }
                if (device && isOnSchedule && resource.isActive() && !resource.isDeleted() && user.haveAccess(resource) && resource.isValid())
                {
                    VideoContent object = (VideoContent) resource.getResourceData().createGenericInstance();
                    if (uri == null)
                    {
                        try
                        {
                            object.setResourceBase(resource);
                            list.add(object);
                        }
                        catch (Exception e)
                        {
                            log.error(e);

                        }
                    }
                    else
                    {
                        if (uri.equals(resource.getURI()))
                        {

                            try
                            {
                                object.setResourceBase(resource);
                                list.add(object);
                            }
                            catch (Exception e)
                            {
                                log.error(e);

                            }
                        }
                    }

                }
            }
        }
        return list;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if ("month".equals(paramRequest.getMode()))
        {
            doShowVideosByMonth(request, response, paramRequest);
        }
        else
        {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * Do show videos by month.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doShowVideosByMonth(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/jsp/" + this.getClass().getSimpleName() + "/";
        if (request.getParameter("month") != null)
        {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            if (request.getParameter("year") != null)
            {
                year = Integer.parseInt(request.getParameter("year"));
            }
            int month = -1;
            try
            {
                month = Integer.parseInt(request.getParameter("month"));
                String key = month + "_" + year;
                if (month >= 0 && month <= 12)
                {
                    List<VideoContent> list = getVideos(null, paramRequest.getUser());
                    List<VideoContent> listMonth = getVideoByMonth(list).get(key);
                    String path = basePath + "videoByMonth.jsp";
                    RequestDispatcher dis = request.getRequestDispatcher(path);
                    try
                    {
                        request.setAttribute("paramRequest", paramRequest);
                        request.setAttribute("list", listMonth);
                        dis.include(request, response);
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }
                    return;
                }
            }
            catch (NumberFormatException nfe)
            {
                log.error(nfe);
            }
        }
        doView(request, response, paramRequest);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/jsp/" + this.getClass().getSimpleName() + "/";
        String path = basePath + "content.jsp";

        String uri = request.getParameter("uri");
        if (uri != null)
        {
            uri = uri.trim();
            if (uri.equals(""))
            {
                uri = null;
            }
        }
        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY)
        {
            path = basePath + "strategy.jsp";
            uri = null;
        }
        List<VideoContent> list = getVideos(null, paramRequest.getUser());
        if (uri != null && paramRequest.getCallMethod() == paramRequest.Call_CONTENT)
        {
            // busca el objeto
            for (VideoContent content : list)
            {
                if (content.getResourceBase().getURI().equals(uri))
                {
                    Resource resource = content.getResourceBase();
                    User user = paramRequest.getUser();
                    Iterator<CalendarRef> refs = resource.listCalendarRefs();
                    boolean isOnSchedule = true;
                    while (refs.hasNext())
                    {
                        CalendarRef ref = refs.next();
                        if (ref.isValid() && ref.isActive() && ref.getCalendar().isActive() && ref.getCalendar().isValid())
                        {
                            if (!ref.getCalendar().isOnSchedule())
                            {
                                isOnSchedule = false;
                            }
                        }
                    }
                    Device d = resource.getDevice();
                    boolean device = true;
                    if (d != null)
                    {
                        if (!user.hasDevice(d))
                        {
                            device = false;

                        }
                    }
                    //if (content.getResourceBase().isValid() && paramRequest.getUser().haveAccess(content.getResourceBase()))
                    if (device && isOnSchedule && resource.isActive() && !resource.isDeleted() && user.haveAccess(resource) && resource.isValid())
                    {
                        path = basePath + "showvideo.jsp";
                        RequestDispatcher dis = request.getRequestDispatcher(path);
                        try
                        {
                            request.setAttribute("paramRequest", paramRequest);
                            request.setAttribute("content", content);
                            dis.include(request, response);
                        }
                        catch (Exception e)
                        {
                            log.error(e);
                        }
                        return;
                    }
                }
            }
        }
        SortVideos sort = new SortVideos();
        Collections.sort(list, sort);

        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("list", list);
            request.setAttribute("library", this);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return;
    }

}

class SortVideos implements java.util.Comparator<VideoContent>
{

    public int compare(VideoContent o1, VideoContent o2)
    {
        if (o1.getPublishDate() != null && o2.getPublishDate() != null)
        {
            return o2.getPublishDate().compareTo(o1.getPublishDate());
        }
        return 0;
    }
}
