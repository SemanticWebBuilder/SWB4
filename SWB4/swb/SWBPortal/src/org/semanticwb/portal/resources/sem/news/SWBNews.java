package org.semanticwb.portal.resources.sem.news;

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

public class SWBNews extends org.semanticwb.portal.resources.sem.news.base.SWBNewsBase
{

    //private static Calendar calendar = Calendar.getInstance();
    private static final Logger log = SWBUtils.getLogger(SWBNews.class);


    public SWBNews()
    {
    }

    public SWBNews(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static Map<String, List<SWBNewContent>> getNewsByMonth(List<SWBNewContent> contents)
    {
        Calendar calendar = Calendar.getInstance();
        TreeMap<String, List<SWBNewContent>> getNewsByMonth = new TreeMap<String, List<SWBNewContent>>();
        for (SWBNewContent content : contents)
        {
            if(content.getPublishDate()!=null)
            {
                calendar.setTime(content.getPublishDate());
                int newmonth = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                String key=newmonth+"_"+year;
                List<SWBNewContent> contentsMonth=getNewsByMonth.get(key);
                if(contentsMonth==null)
                {
                    contentsMonth=new ArrayList<SWBNewContent>();
                    getNewsByMonth.put(key, contents);
                }
                contentsMonth.add(content);
            }
        }
        return getNewsByMonth;
    }


    public static String[] getYears(List<SWBNewContent> contents)
    {
        HashSet<String> getyears=new HashSet<String>();
        Calendar calendar = Calendar.getInstance();
        for (SWBNewContent content : contents)
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

    public static boolean hasNews(List<SWBNewContent> contents, int month,int year)
    {
        Calendar calendar = Calendar.getInstance();
        for (SWBNewContent content : contents)
        {
            if(content.getPublishDate()!=null)
            {
                calendar.setTime(content.getPublishDate());                
                if (month == calendar.get(Calendar.MONTH) && year==calendar.get(Calendar.YEAR))
                {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if (paramRequest.getMode().equals("month"))
        {
            doShowNewsByMoth(request, response, paramRequest);
        }
        else if(paramRequest.getMode().equals("rss"))
        {
            doRss(request, response, paramRequest);
        }
        else
        {
            super.processRequest(request, response, paramRequest);
        }
    }
    private List<SWBNewContent> getNews(String uri,User user)
    {
        List<SWBNewContent> news = new ArrayList<SWBNewContent>();
        GenericIterator<Resource> resources = null;
        if (this.getCollection() != null && this.getCollection().getResourceCollectionType().getResourceClassName().equals(SWBNewContent.class.getCanonicalName()))
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
                    SWBNewContent object = (SWBNewContent)resource.getResourceData().createGenericInstance();
                    if (uri == null)
                    {
                        try
                        {
                            object.setResourceBase(resource);
                            news.add(object);
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
                                news.add(object);
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
        return news;
    }
    public void doShowNewsByMoth(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String basePath="/work/models/"+paramRequest.getWebPage().getWebSite().getId()+"/jsp/"+ this.getClass().getSimpleName() +"/";
        if(request.getParameter("month")!=null )
        {
            int month=-1;
            int year=Calendar.getInstance().get(Calendar.YEAR);
            if(request.getParameter("year")!=null)
            {
                year=Integer.parseInt(request.getParameter("year"));
            }
            try
            {
                month=Integer.parseInt(request.getParameter("month"));                
                String key=month+"_"+year;
                if(month>=0 && month<=12)
                {
                    List<SWBNewContent> news = getNews(null,paramRequest.getUser());
                    news=getNewsByMonth(news).get(key);
                    String path = basePath+"newsByMonth.jsp";
                    RequestDispatcher dis = request.getRequestDispatcher(path);
                    try
                    {
                        request.setAttribute("paramRequest", paramRequest);
                        request.setAttribute("news", news);
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
    public void doRss(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String basePath="/work/models/"+paramRequest.getWebPage().getWebSite().getId()+"/jsp/"+ this.getClass().getSimpleName() +"/";
        List<SWBNewContent> news=getNews(null,paramRequest.getUser());
        String path = basePath+"rss.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        /*SWBResourceURL url=paramRequest.getRenderUrl();
        url.setCallMethod(url.Call_CONTENT);
        url.setMode(url.Mode_VIEW);*/
        String url=paramRequest.getWebPage().getUrl();
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("news", news);
            request.setAttribute("url", url);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

    

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String basePath="/work/models/"+paramRequest.getWebPage().getWebSite().getId()+"/jsp/"+ this.getClass().getSimpleName() +"/";
        String path = basePath+"content.jsp";
        if (this.getResourceBase().getProperty("mode") != null && "rss".equals(this.getResourceBase().getProperty("mode")))
        {
            doRss(request, response, paramRequest);
            return;
        }
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

        List<SWBNewContent> news=getNews(uri,paramRequest.getUser());
        if (uri != null && paramRequest.getCallMethod() == paramRequest.Call_CONTENT)
        {            
            // busca el objeto
            for (SWBNewContent content : news)
            {                
                if (content.getResourceBase().getURI().equals(uri))
                {
                    if (content.getResourceBase().isValid() && paramRequest.getUser().haveAccess(content.getResourceBase()))
                    {
                        path = basePath+"shownew.jsp";
                        RequestDispatcher dis = request.getRequestDispatcher(path);
                        try
                        {
                            request.setAttribute("paramRequest", paramRequest);
                            request.setAttribute("content", content);
                            request.setAttribute("this", this);
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
        // ordena por fecha

        SortNews sort = new SortNews();
        Collections.sort(news, sort);



        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("news", news);
            request.setAttribute("this", this);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }
}

class SortNews implements java.util.Comparator<SWBNewContent>
{

    public int compare(SWBNewContent o1, SWBNewContent o2)
    {
        if(o1.getPublishDate()!=null && o2.getPublishDate()!=null)
        {
            return o2.getPublishDate().compareTo(o1.getPublishDate());
        }
        return 0;
    }
}
