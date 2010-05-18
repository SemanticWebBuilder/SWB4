/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources.sem.newslite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.ImageResizer;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author victor.lorenzana
 */
public class SWBNewsLite extends GenericResource
{

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final Logger log = SWBUtils.getLogger(SWBNewsLite.class);

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        String action = request.getParameter("act");
        WebPage page = response.getWebPage();
        final String realpath = SWBPortal.getWorkPath();

        response.setMode(response.Mode_ADMIN);
        if (action == null)
        {
            New rec = null;
            HashMap<String, String> params = upload(request);

            if (params.containsValue("add"))
            {
                rec = New.ClassMgr.createNew(getResourceBase().getWebSite());
                rec.setCreator(response.getUser());
                rec.setCreated(new Date(System.currentTimeMillis()));

            }
            else if (params.containsValue("edit"))
            {
                String uri = params.get("uri");
                if (uri != null && !uri.trim().equals(""))
                {
                    rec = (New) SemanticObject.createSemanticObject(uri).createGenericInstance();
                }
            }
            if (rec != null)
            {
                rec.setTitle(SWBUtils.XML.replaceXMLChars(params.get("title")));
                rec.setDescription(SWBUtils.XML.replaceXMLChars(params.get("description")));
                rec.setBody(SWBUtils.XML.replaceXMLChars(params.get("body")));
                rec.setAuthor(SWBUtils.XML.replaceXMLChars(params.get("author")));
                rec.setSource(SWBUtils.XML.replaceXMLChars(params.get("source")));
                String startDate = params.get("new_endDate");
                try
                {
                    rec.setExpiration(dateFormat.parse(startDate.trim()));
                }
                catch (Exception e)
                {
                    if (rec.getExpiration() == null)
                    {
                        rec.setExpiration(new Date(System.currentTimeMillis()));
                    }
                    log.debug(e);
                }
                if (params.containsKey("filename"))
                {

                    File file = new File(realpath + params.get("filename"));
                    if (file.exists())
                    {
                        FileInputStream in = new FileInputStream(file);
                        String filename = file.getName();
                        String finalpath = rec.getWorkPath() + "/";
                        String target = realpath + finalpath + filename;
                        File ftarget = new File(target);
                        ftarget.getParentFile().mkdirs();
                        FileOutputStream out = new FileOutputStream(ftarget);
                        SWBUtils.IO.copyStream(in, out);
                        file.delete();
                        params.put("filename", finalpath + filename);
                    }
                    rec.setImage(file.getName());
                }
                else
                {
                    //rec.setImage(null);
                }
                if (params.containsKey("thumbnail"))
                {
                    File file = new File(realpath + params.get("thumbnail"));
                    if (file.exists())
                    {
                        FileInputStream in = new FileInputStream(file);
                        String filename = file.getName();
                        String finalpath = rec.getWorkPath() + "/";
                        String target = realpath + finalpath + filename;
                        File ftarget = new File(target);
                        ftarget.getParentFile().mkdirs();
                        FileOutputStream out = new FileOutputStream(ftarget);
                        SWBUtils.IO.copyStream(in, out);
                        file.delete();
                        params.put("thumbnail", finalpath + filename);
                    }
                    rec.setNewsThumbnail(file.getName());
                }
                else
                {
                    //rec.setNewsThumbnail(null);
                }
            }
        }
        else
        {
            String uri = request.getParameter("uri");
            New rec = (New) SemanticObject.createSemanticObject(uri).createGenericInstance();
            rec.remove();
            return;
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if (paramRequest.getMode().equals("detail"))
        {
            doDetail(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("add"))
        {
            doAdd(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("expired"))
        {
            doExpired(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("edit"))
        {
            doEditNew(request, response, paramRequest);
        }
        else
        {
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doExpired(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Set<New> news = new HashSet<New>();
        String path = "/swbadmin/jsp/SWBNewsLite/expirednews.jsp";
        String categoryId = this.getResourceBase().getAttribute("category");
        Iterator<New> itNews = null;

        if (categoryId == null || categoryId.trim().equals(""))
        {
            itNews = New.ClassMgr.listNews(paramRequest.getWebPage().getWebSite());
        }
        else
        {
            Category ocategory = Category.ClassMgr.getCategory(categoryId, paramRequest.getWebPage().getWebSite());
            if (ocategory == null)
            {
                itNews = New.ClassMgr.listNews(paramRequest.getWebPage().getWebSite());
            }
            else
            {
                itNews = New.ClassMgr.listNewByCategory(ocategory, paramRequest.getWebPage().getWebSite());
            }
        }
        // Agrega las expiradas
        while (itNews.hasNext())
        {
            New onew = itNews.next();
            if (!onew.getExpiration().after(new Date(System.currentTimeMillis())))
            {
                news.add(onew);
            }
        }
        // ordena por fecha de creación
        New[] elements = news.toArray(new New[news.size()]);
        Arrays.sort(elements);

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
    }

    public void doEditNew(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String uri = request.getParameter("uri");
        if (uri == null || uri.trim().equals(""))
        {
            response.sendError(404);
            return;
        }
        String path = "/swbadmin/jsp/SWBNewsLite/edit.jsp";
        SemanticObject obj = SemanticObject.getSemanticObject(uri);
        if (obj == null || !obj.getSemanticClass().equals(New.sclass))
        {
            response.sendError(404);
            return;
        }
        New onew = new New(obj);
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("new", onew);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

    public void doAdd(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = "/swbadmin/jsp/SWBNewsLite/add.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Set<New> news = new HashSet<New>();
        String path = "/swbadmin/jsp/SWBNewsLite/menu.jsp";
        String categoryId = this.getResourceBase().getAttribute("category");
        Iterator<New> itNews = null;

        if (categoryId == null || categoryId.trim().equals(""))
        {
            itNews = New.ClassMgr.listNews(paramRequest.getWebPage().getWebSite());
        }
        else
        {
            Category ocategory = Category.ClassMgr.getCategory(categoryId, paramRequest.getWebPage().getWebSite());
            if (ocategory == null)
            {
                itNews = New.ClassMgr.listNews(paramRequest.getWebPage().getWebSite());
            }
            else
            {
                itNews = New.ClassMgr.listNewByCategory(ocategory, paramRequest.getWebPage().getWebSite());
            }
        }
        // Elimina las expiradas
        while (itNews.hasNext())
        {
            New onew = itNews.next();
            if (onew.getExpiration() == null)
            {
                onew.remove();
            }
            else
            {
                if (onew.getExpiration().after(new Date(System.currentTimeMillis())))
                {
                    news.add(onew);
                }
            }
        }
        // ordena por fecha de creación
        New[] elements = news.toArray(new New[news.size()]);
        Arrays.sort(elements, new ComparatorNews());

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
    }

    public void doDetail(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = "/swbadmin/jsp/SWBNewsLite/detail.jsp";
        String uri = request.getParameter("uri");
        if (uri == null || uri.trim().equals(""))
        {
            response.sendError(404);
            return;
        }
        SemanticObject obj = SemanticObject.getSemanticObject(uri);
        if (obj == null || !obj.getSemanticClass().equals(New.sclass))
        {
            response.sendError(404);
            return;
        }
        New onew = new New(obj);
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("new", onew);
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
        Set<New> news = new HashSet<New>();
        String path = "/swbadmin/jsp/SWBNewsLite/newsview.jsp";
        String smax = this.getResourceBase().getAttribute("nummax");
        String simpleMode = this.getResourceBase().getAttribute("simplemode");
        if (Boolean.parseBoolean(simpleMode))
        {
            path = "/swbadmin/jsp/SWBNewsLite/simpleMode.jsp";
        }
        String categoryId = this.getResourceBase().getAttribute("category");
        Iterator<New> itNews = null;

        if (categoryId == null || categoryId.trim().equals(""))
        {
            itNews = New.ClassMgr.listNews(paramRequest.getWebPage().getWebSite());
        }
        else
        {
            Category ocategory = Category.ClassMgr.getCategory(categoryId, paramRequest.getWebPage().getWebSite());
            if (ocategory == null)
            {
                itNews = New.ClassMgr.listNews(paramRequest.getWebPage().getWebSite());
            }
            else
            {
                itNews = New.ClassMgr.listNewByCategory(ocategory, paramRequest.getWebPage().getWebSite());
            }
        }
        // Elimina las expiradas
        while (itNews.hasNext())
        {
            New onew = itNews.next();
            if (onew.getExpiration().after(new Date(System.currentTimeMillis())))
            {
                news.add(onew);
            }
        }
        // ordena por fecha de creación
        New[] elements = news.toArray(new New[news.size()]);
        Arrays.sort(elements);

        int max = -1;
        if (smax != null && !smax.trim().equals(""))
        {
            try
            {
                max = Integer.parseInt(smax);
            }
            catch (NumberFormatException nfe)
            {
                log.debug(nfe);
            }
        }

        news = new HashSet<New>();
        if (elements.length <= max)
        {

            for (New onew : elements)
            {
                news.add(onew);
            }
        }
        else
        {
            for (int i = 0; i < max; i++)
            {
                news.add(elements[i]);
            }
        }


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
    }

    private HashMap<String, String> upload(HttpServletRequest request)
    {
        final String realpath = SWBPortal.getWorkPath() + getResourceBase().getWorkPath() + "/";
        final String path = getResourceBase().getWorkPath() + "/";

        HashMap<String, String> params = new HashMap<String, String>();
        try
        {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart)
            {
                File tmpwrk = new File(SWBPortal.getWorkPath() + "/tmp");
                if (!tmpwrk.exists())
                {
                    tmpwrk.mkdirs();
                }
                FileItemFactory factory = new DiskFileItemFactory(1 * 1024 * 1024, tmpwrk);
                ServletFileUpload upload = new ServletFileUpload(factory);
                ProgressListener progressListener = new ProgressListener()
                {

                    private long kBytes = -1;

                    public void update(long pBytesRead, long pContentLength, int pItems)
                    {
                        long mBytes = pBytesRead / 10000;
                        if (kBytes == mBytes)
                        {
                            return;
                        }
                        kBytes = mBytes;
                        int percent = (int) (pBytesRead * 100 / pContentLength);
                    }
                };
                upload.setProgressListener(progressListener);
                List items = upload.parseRequest(request); /* FileItem */
                FileItem currentFile = null;
                Iterator iter = items.iterator();
                while (iter.hasNext())
                {
                    FileItem item = (FileItem) iter.next();

                    if (item.isFormField())
                    {
                        String name = item.getFieldName();
                        String value = item.getString();
                        params.put(name, value);
                    }
                    else
                    {
                        currentFile = item;
                        File file = new File(path);
                        if (!file.exists())
                        {
                            file.mkdirs();
                        }

                        try
                        {
                            long serial = (new Date()).getTime();
                            String filename = serial + "_" + currentFile.getFieldName() + currentFile.getName().substring(currentFile.getName().lastIndexOf("."));

                            File image = new File(realpath);
                            if (!image.exists())
                            {
                                image.mkdir();
                            }
                            image = new File(realpath + filename);
                            File thumbnail = new File(realpath + "thumbn_" + filename);
                            currentFile.write(image);
                            //ImageResizer.resize(image, 150, true, thumbnail, "jpeg" );
                            ImageResizer.resizeCrop(image, 150, thumbnail, "jpeg");
                            params.put("filename", path + filename);
                            params.put("thumbnail", path + "thumbn_" + filename);
                        }
                        catch (StringIndexOutOfBoundsException iobe)
                        {
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return params;
    }
}

class ComparatorNews implements Comparator<New>
{

    public int compare(New o1, New o2)
    {
        return o1.getExpiration().compareTo(o2.getExpiration());
    }
}
