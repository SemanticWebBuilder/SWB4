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
package org.semanticwb.portal.resources.sem.newslite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import org.semanticwb.portal.api.SWBResourceURL;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBNewsLite.
 * 
 * @author victor.lorenzana
 */
public class SWBNewsLite extends GenericResource
{

    /** The Constant NUMMAX. */
    private static final String NUMMAX = "numax";
    
    /** The Constant dateFormat. */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    /** The Constant log. */
    private static final Logger log = SWBUtils.getLogger(SWBNewsLite.class);

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(HttpServletRequest, SWBActionResponse)
     */
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
                if(params.containsKey("category"))
                {
                    String uri=params.get("category");
                    try
                    {
                        Category category = (Category) SemanticObject.createSemanticObject(uri).createGenericInstance();
                        rec.setCategory(category);
                    }
                    catch(NullPointerException npe)
                    {
                        log.error(npe);
                    }
                }
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
                        //FileInputStream in = new FileInputStream(file);
                        String filename = file.getName();
                        String finalpath = rec.getWorkPath() + "/";
                        String target = realpath + finalpath + filename;
                        File ftarget = new File(target);
                        ftarget.getParentFile().mkdirs();
                        int pos = filename.lastIndexOf('.');
                        if (pos != -1)
                        {
                            filename = filename.substring(0, pos);
                        }
                        filename += ".jpg";
                        ImageResizer.shrinkTo(file, 150, 150, ftarget, "jpeg");
                        /*FileOutputStream out = new FileOutputStream(ftarget);
                        SWBUtils.IO.copyStream(in, out);*/
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
        else if ("addCategory".equals(action))
        {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            if (title != null && !title.trim().equals("") && description != null && !description.trim().equals(""))
            {
                Category category = Category.ClassMgr.createCategory(response.getWebPage().getWebSite());
                category.setTitle(SWBUtils.XML.replaceXMLChars(title));
                category.setDescription(SWBUtils.XML.replaceXMLChars(description));
            }
        }
        else if ("editCategory".equals(action))
        {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String uri = request.getParameter("uri");
            if (uri != null && !uri.trim().equals(""))
            {
                Category category = (Category) SemanticObject.createSemanticObject(uri).createGenericInstance();
                if (category != null && title != null && !title.trim().equals("") && description != null && !description.trim().equals(""))
                {
                    category.setTitle(SWBUtils.XML.replaceXMLChars(title));
                    category.setDescription(SWBUtils.XML.replaceXMLChars(description));
                }
            }
        }
        else if ("removeCategory".equals(action))
        {
            String uri = request.getParameter("uri");
            Category category = (Category) SemanticObject.createSemanticObject(uri).createGenericInstance();
            boolean canDelete=!New.ClassMgr.listNewByCategory(category).hasNext();
            if(canDelete)
            {
                category.remove();
            }
            return;
        }
        else if ("config".equals(action))
        {
            String nummax = request.getParameter(NUMMAX);
            if (nummax != null && !nummax.trim().equals(""))
            {
                this.getResourceBase().setAttribute(NUMMAX, nummax);
            }
            String uri = request.getParameter("category");
            if (uri != null && !uri.trim().equals(""))
            {
                try
                {
                    Category category = (Category) SemanticObject.createSemanticObject(uri).createGenericInstance();
                    if (category == null)
                    {
                        this.getResourceBase().setAttribute("category", null);
                    }
                    else
                    {
                        this.getResourceBase().setAttribute("category", category.getURI());
                    }
                }
                catch(NullPointerException npe)
                {
                    this.getResourceBase().setAttribute("category", null);
                }
            }

            String mode = request.getParameter("modo");
            if (mode != null && !mode.trim().equals(""))
            {
                if ("simplemode".equals(mode))
                {
                    this.getResourceBase().setAttribute("simplemode", "true");
                }
                else
                {
                    this.getResourceBase().setAttribute("simplemode", "false");
                }
            }
            try
            {
                this.getResourceBase().updateAttributesToDB();
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }
        else if ("remove".equals(action))
        {
            String uri = request.getParameter("uri");
            New rec = (New) SemanticObject.createSemanticObject(uri).createGenericInstance();
            rec.remove();
            return;
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if (paramRequest.getMode().equals("detail"))
        {
            doDetail(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("addCategory"))
        {
            doAddCategory(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("editCategory"))
        {
            doEditCategory(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("rss"))
        {
            doRss(request, response, paramRequest);
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
        else if (paramRequest.getMode().equals("config"))
        {
            doConfig(request, response, paramRequest);
        }
        else
        {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * Do edit category.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doEditCategory(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String uri = request.getParameter("uri");
        if (uri == null || uri.trim().equals(""))
        {
            response.sendError(404);
            return;
        }
        String path = "/swbadmin/jsp/SWBNewsLite/editCategory.jsp";
        SemanticObject obj = SemanticObject.createSemanticObject(uri);
        if (obj == null || !obj.getSemanticClass().equals(Category.sclass))
        {
            response.sendError(404);
            return;
        }
        Category category = new Category(obj);
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("category", category);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

    /**
     * Do add category.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doAddCategory(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = "/swbadmin/jsp/SWBNewsLite/addCategory.jsp";
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

    /**
     * Do rss.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doRss(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        List<New> news = new ArrayList<New>();
        String categoryId = this.getResourceBase().getAttribute("category");
        Iterator<New> itNews = null;

        if (categoryId == null || categoryId.trim().equals(""))
        {
            itNews = New.ClassMgr.listNews(paramRequest.getWebPage().getWebSite());
        }
        else
        {
            Category ocategory = (Category) SemanticObject.createSemanticObject(categoryId).createGenericInstance();
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
        String path = "/swbadmin/jsp/SWBNewsLite/rss.jsp";
        SWBResourceURL url=paramRequest.getRenderUrl();
        url.setCallMethod(url.Call_CONTENT);
        url.setMode("detail");
        RequestDispatcher dis = request.getRequestDispatcher(path);
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

    /**
     * Do config.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doConfig(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = "/swbadmin/jsp/SWBNewsLite/config.jsp";
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

    /**
     * Do expired.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doExpired(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        List<New> news = new ArrayList<New>();
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
        Arrays.sort(elements, new ComparatorNews());

        news = new ArrayList<New>();
        for (New onew : elements)
        {
            news.add(onew);
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

    /**
     * Do edit new.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doEditNew(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String uri = request.getParameter("uri");
        if (uri == null || uri.trim().equals(""))
        {
            response.sendError(404);
            return;
        }
        String path = "/swbadmin/jsp/SWBNewsLite/edit.jsp";
        SemanticObject obj = SemanticObject.createSemanticObject(uri);
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

    /**
     * Do add.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
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

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doAdmin(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        List<New> news = new ArrayList<New>();
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

        news = new ArrayList<New>();
        for (New onew : elements)
        {
            news.add(onew);
        }
        List<Category> cats = new ArrayList<Category>();
        Iterator<Category> categories = Category.ClassMgr.listCategories(paramRequest.getWebPage().getWebSite());
        while (categories.hasNext())
        {
            cats.add(categories.next());
        }
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("news", news);
            request.setAttribute("cats", cats);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

    /**
     * Do detail.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doDetail(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = "/swbadmin/jsp/SWBNewsLite/detail.jsp";
        String uri = request.getParameter("uri");
        if (uri == null || uri.trim().equals(""))
        {
            response.sendError(404);
            return;
        }
        SemanticObject obj = SemanticObject.createSemanticObject(uri);
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

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if (this.getResourceBase().getProperty("mode") != null && "rss".equals(this.getResourceBase().getProperty("mode")))
        {
            doRss(request, response, paramRequest);
            return;
        }
        List<New> news = new ArrayList<New>();
        String path = "/swbadmin/jsp/SWBNewsLite/newsview.jsp";
        String smax = this.getResourceBase().getAttribute(NUMMAX);
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
            try
            {
                Category ocategory = (Category) SemanticObject.createSemanticObject(categoryId).createGenericInstance();
                if (ocategory == null)
                {
                    itNews = New.ClassMgr.listNews(paramRequest.getWebPage().getWebSite());
                }
                else
                {
                    itNews = New.ClassMgr.listNewByCategory(ocategory, paramRequest.getWebPage().getWebSite());
                }
            }
            catch(NullPointerException npe)
            {
                itNews = New.ClassMgr.listNews(paramRequest.getWebPage().getWebSite());
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
        Arrays.sort(elements, new ComparatorNews());

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

        news = new ArrayList<New>();
        if (elements.length <= max || max == -1)
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

    /**
     * Upload.
     * 
     * @param request the request
     * @return the hash map
     */
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
                            ImageResizer.shrinkTo(image, 150, 150, thumbnail, "jpeg");

                            params.put("filename", path + filename);
                            params.put("thumbnail", path + "thumbn_" + filename);
                        }
                        catch (StringIndexOutOfBoundsException iobe)
                        {
                            log.error(iobe);
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
        return o2.getCreated().compareTo(o1.getCreated());
    }
}
