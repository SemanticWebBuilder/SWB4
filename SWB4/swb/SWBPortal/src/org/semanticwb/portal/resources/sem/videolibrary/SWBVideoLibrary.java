package org.semanticwb.portal.resources.sem.videolibrary;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceCollectionCategory;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.*;

public class SWBVideoLibrary extends org.semanticwb.portal.resources.sem.videolibrary.base.SWBVideoLibraryBase 
{
    private static final Logger log = SWBUtils.getLogger(SWBVideoLibrary.class);
    public SWBVideoLibrary()
    {
    }
    public static boolean hasVideo(List<VideoContent> contents, int month,int year)
    {
        Calendar calendar = Calendar.getInstance();
        for (VideoContent content : contents)
        {
            if(content.getPublishDate()!=null)
            {
                calendar.setTime(content.getPublishDate());
                int newmonth = calendar.get(Calendar.MONTH);
                int new_year=calendar.get(Calendar.YEAR);
                if (month == newmonth && year==new_year)
                {
                    return true;
                }
            }

        }
        return false;
    }
    private Map<Integer, List<VideoContent>> getVideoByMonth(List<VideoContent> contents)
    {
        Calendar calendar = Calendar.getInstance();
        TreeMap<Integer, List<VideoContent>> getVideoByMonth = new TreeMap<Integer, List<VideoContent>>();
        for (VideoContent content : contents)
        {
            if(content.getPublishDate()!=null)
            {
                calendar.setTime(content.getPublishDate());
                int newmonth = calendar.get(Calendar.MONTH);
                List<VideoContent> contentsMonth=getVideoByMonth.get(newmonth);
                if(contentsMonth==null)
                {
                    contentsMonth=new ArrayList<VideoContent>();
                    getVideoByMonth.put(newmonth, contents);
                }
                contentsMonth.add(content);
            }
        }
        return getVideoByMonth;
    }
    public static String[] getYears(List<VideoContent> contents)
    {
        HashSet<String> getyears=new HashSet<String>();
        Calendar calendar = Calendar.getInstance();
        for (VideoContent content : contents)
        {
            if(content.getPublishDate()!=null)
            {
                calendar.setTime(content.getPublishDate());
                int new_year = calendar.get(Calendar.YEAR);
                getyears.add(String.valueOf(new_year));
            }
        }
        return getyears.toArray(new String[getyears.size()]);
    }

    public SWBVideoLibrary(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    private List<VideoContent> getVideos(String uri,User user)
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
                if (resource.isActive() && !resource.isDeleted() && user.haveAccess(resource))
                {
                    VideoContent object = (VideoContent)resource.getResourceData().createGenericInstance();
                    if (uri == null)
                    {
                        try
                        {
                            object.setResourceBase(resource);
                            list.add(object);
                        }
                        catch(Exception e)
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
                            catch(Exception e)
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
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if (paramRequest.getMode().equals("month"))
        {
            doShowVideosByMonth(request, response, paramRequest);
        }
        else
        {
            super.processRequest(request, response, paramRequest);
        }
    }
    public void doShowVideosByMonth(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String basePath="/work/models/"+paramRequest.getWebPage().getWebSite().getId()+"/jsp/"+ this.getClass().getName() +"/";
        if(request.getParameter("month")!=null)
        {
            int month=-1;
            try
            {
                month=Integer.parseInt(request.getParameter("month"));
                if(month>=0 && month<=12)
                {
                    List<VideoContent> list = getVideos(null,paramRequest.getUser());                    
                    List<VideoContent> listMonth=getVideoByMonth(list).get(month);                    
                    String path = basePath+"videoByMonth.jsp";
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
            catch(NumberFormatException nfe)
            {
                log.error(nfe);
            }
        }
        doView(request, response, paramRequest);
    }
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String basePath="/work/models/"+paramRequest.getWebPage().getWebSite().getId()+"/jsp/"+ this.getClass().getName() +"/";
        String path = basePath+"content.jsp";

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
            path = basePath+"strategy.jsp";
            uri = null;
        }
        List<VideoContent> list = getVideos(null,paramRequest.getUser());        
        if (uri != null && paramRequest.getCallMethod() == paramRequest.Call_CONTENT)
        {
            // busca el objeto
            for (VideoContent content : list)
            {
                if (content.getResourceBase().getURI().equals(uri))
                {
                    if (content.getResourceBase().isValid() && paramRequest.getUser().haveAccess(content.getResourceBase()))
                    {
                        path = basePath+ "showvideo.jsp";
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
        if(o1.getPublishDate()!=null && o2.getPublishDate()!=null)
        {
            return o2.getPublishDate().compareTo(o1.getPublishDate());
        }
        return 0;
    }
}
