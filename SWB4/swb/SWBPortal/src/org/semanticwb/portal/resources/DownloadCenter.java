/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FriendlyURL;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.resources.sem.news.SWBNews;

/**
 *
 * @author victor.lorenzana
 */
public class DownloadCenter extends GenericResource
{

    private final WBAdmResourceUtils admResUtils = new WBAdmResourceUtils();
    private static final Logger log = SWBUtils.getLogger(SWBNews.class);

    

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        //createPath("", paramRequest.getWebPage(), );
        String action = null != request.getParameter("act") && !"".equals(request.getParameter("act").trim()) ? request.getParameter("act").trim() : paramRequest.getAction();

        if (action.equalsIgnoreCase("add") || action.equalsIgnoreCase("edit"))
        {
            String path = "/swbadmin/jsp/DownloadCenter/admin.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(path);
            try
            {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            }
            catch (IOException e)
            {
                log.error(e);
            }
            catch (ServletException e)
            {
                log.error(e);
            }
        }
        else if (action.equalsIgnoreCase("update"))
        {
            // upload settings
            final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
            final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
            final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
            //FileUpload fup = new FileUpload();
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // sets memory threshold - beyond which files are stored in disk 
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            // sets temporary location to store files
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload upload = new ServletFileUpload(factory);

            // sets maximum size of upload file
            upload.setFileSizeMax(MAX_FILE_SIZE);

            // sets maximum size of request (include file + form data)
            upload.setSizeMax(MAX_REQUEST_SIZE);
            try
            {
                Resource base = getResourceBase();
                List<FileItem> formItems = upload.parseRequest(request);
                String jspPath = null != getValue("jspPath", formItems) && !"".equals(getValue("jspPath", formItems).trim()) ? getValue("jspPath", formItems).trim() : null;
                base.setAttribute("jspPath", jspPath);
                String prefix = "fileDC_";
                String value = null != getValue("deleteall", formItems) && !"".equals(getValue("deleteall", formItems).trim()) ? getValue("deleteall", formItems).trim() : null;
                if (value != null)
                {
                    Map<String, String> values = new HashMap<String, String>();
                    Iterator<String> keys = base.getAttributeNames();
                    while (keys.hasNext())
                    {
                        String key = keys.next();
                        String file = base.getAttribute(key, null);
                        if (file != null && key.startsWith(prefix))
                        {
                            values.put(key, file);
                        }
                    }
                    for (String key : values.keySet())
                    {
                        String id = key.split("_")[1];
                        String workPath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
                        File file = new File(workPath + values.get(key));
                        file.delete();
                        base.removeAttribute(key);
                        base.removeAttribute("desc_" + id);
                        base.removeAttribute("title_" + id);
                    }
                }
                List<FileItem> imagesAddBatch = new ArrayList<FileItem>();
                for (FileItem item : formItems)
                {
                    if (item.getFieldName().equals("fileupload"))
                    {
                        imagesAddBatch.add(item);
                    }
                }
                for (FileItem item : imagesAddBatch)
                {
                    value = item.getName();//getFileName(item.getFieldName(), imagesAddBatch);
                    String filenameAttr = item.getFieldName();
                    String id = UUID.randomUUID().toString();
                    String key = prefix + id;
                    if (value != null)
                    {
                        String filename = admResUtils.getFileName(base, value);
                        int intPos = filename.lastIndexOf(".");
                        if (intPos != -1)
                        {
                            filename = SWBUtils.TEXT.replaceSpecialCharactersForFile(filename.substring(0, intPos), ' ', true) + filename.substring(intPos);
                        }
                        if (filename != null && !filename.trim().equals(""))
                        {
                            if (uploadFile(base, filename, filenameAttr, item))
                            {
                                base.setAttribute(key, filename);
                            }
                            else
                            {
                                //msg = paramRequest.getLocaleString("msgErrUploadFile") + " <i>" + value + "</i>.";
                            }

                        }
                        else
                        {
                            //msg = paramRequest.getLocaleString("msgErrUploadFile") + " <i>" + value + "</i>.";
                        }
                    }
                }
                for (FileItem item : formItems)
                {
                    String name = item.getFieldName();
                    if (name.startsWith("fileDC_"))
                    {
                        value = item.getName();//getFileName(item.getFieldName(), imagesAddBatch);
                        String filenameAttr = item.getFieldName();
                        String id = name.split("_")[1];
                        String key = prefix + id;
                        if (value != null)
                        {
                            String filename = admResUtils.getFileName(base, value);
                            int intPos = filename.lastIndexOf(".");
                            if (intPos != -1)
                            {
                                filename = SWBUtils.TEXT.replaceSpecialCharactersForFile(filename.substring(0, intPos), ' ', true) + filename.substring(intPos);
                            }
                            if (filename != null && !filename.trim().equals(""))
                            {
                                if (uploadFile(base, filename, filenameAttr, item))
                                {
                                    base.setAttribute(key, filename);
                                }
                                else
                                {
                                    //msg = paramRequest.getLocaleString("msgErrUploadFile") + " <i>" + value + "</i>.";
                                }

                            }
                            else
                            {
                                //msg = paramRequest.getLocaleString("msgErrUploadFile") + " <i>" + value + "</i>.";
                            }
                        }
                    }
                }
                for (FileItem item : formItems)
                {
                    String name = item.getFieldName();
                    if (name.startsWith("title_") || name.startsWith("desc_"))
                    {
                        String _value = getValue(name, formItems);
                        base.setAttribute(name, _value);
                    }

                }
                for (FileItem item : formItems)
                {
                    String name = item.getFieldName();
                    if (name.startsWith("remove_"))
                    {
                        String id = name.split("_")[2];
                        String file_key = "fileDC_" + id;
                        String fileName = paramRequest.getResourceBase().getAttribute(file_key);
                        paramRequest.getResourceBase().removeAttribute(file_key);
                        paramRequest.getResourceBase().removeAttribute("title_" + id);
                        paramRequest.getResourceBase().removeAttribute("desc_" + id);
                        if (fileName != null)
                        {
                            File file = new File(SWBPortal.getWorkPath() + paramRequest.getResourceBase().getWorkPath() + "/" + fileName);
                            file.delete();
                        }
                    }

                }
            }
            catch (FileUploadException ex)
            {
                log.error(ex);
            }
            try
            {
                paramRequest.getResourceBase().updateAttributesToDB();

            }
            catch (SWBException e)
            {
                log.error(e);
            }    
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\" language=\"JavaScript\">");
            out.println("   alert('" + paramRequest.getLocaleLogString("OK") + "');");            
            out.println("   location='" + paramRequest.getRenderUrl().setAction("edit").toString() + "';");
            out.println("</script>");
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if (paramRequest.getAction() == null || !paramRequest.getAction().equals("download"))
        {
            Resource base = this.getResourceBase();
            String path = base.getAttribute("jspPath", "/swbadmin/jsp/DownloadCenter/view.jsp");
            RequestDispatcher dis = request.getRequestDispatcher(path);
            try
            {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            }
            catch (IOException e)
            {
                log.error(e);
            }
            catch (ServletException e)
            {
                log.error(e);
            }
        }
        else
        {
            MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
            int pos = request.getRequestURI().indexOf("/uridoc/");
            if (pos != -1)
            {
                String id = request.getRequestURI().substring(pos + 8);
                if (id == null || id.trim().isEmpty())
                {
                    return;
                }
                Resource base = paramRequest.getResourceBase();
                if (base.getAttribute("fileDC_" + id, null) != null)
                {
                    String fileName = base.getAttribute("fileDC_" + id);
                    response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                    fileName = SWBPortal.getWorkPath() + base.getWorkPath() + "/" + fileName;
                    java.io.File file = new java.io.File(fileName);
                    response.setHeader("Content-Length", file.length() + "");
                    String mimeType = mimeTypesMap.getContentType(file);
                    if (mimeType == null)
                    {
                        mimeType = "application/binary";
                    }
                    response.setContentType(mimeType);

                    base.addHit(request, paramRequest.getUser(), paramRequest.getWebPage());
                    SWBUtils.IO.copyStream(new FileInputStream(fileName), response.getOutputStream());
                }
            }
        }
    }

    private String getValue(String name, List<FileItem> list)
    {
        for (FileItem fileitem : list)
        {
            if (fileitem.getFieldName().equals(name))
            {
                return fileitem.getString();
            }
        }
        return null;
    }

    private String getFileName(String name, List<FileItem> list)
    {
        for (FileItem fileitem : list)
        {
            if (fileitem.getFieldName().equals(name))
            {
                return fileitem.getName();
            }
        }
        return null;
    }

    private byte[] getFileData(String name, FileItem fileitem) throws IOException
    {

        byte[] content = new byte[(int) fileitem.getSize()];
        InputStream in = fileitem.getInputStream();
        in.read(content);
        return content;

    }

    public boolean uploadFile(Resource base, String fileName, String pInForm, FileItem item)
    {

        String workPath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
        boolean bOk = false;
        try
        {

            if (fileName != null && !fileName.trim().equals(""))
            {
                File file = new File(workPath);

                if (!file.exists())
                {
                    file.mkdirs();
                }
                if (file.exists() && file.isDirectory())
                {
                    String s3 = fileName;
                    int i = s3.lastIndexOf("\\");
                    if (i != -1)
                    {
                        s3 = s3.substring(i + 1);
                    }
                    i = s3.lastIndexOf("/");
                    if (i != -1)
                    {
                        s3 = s3.substring(i + 1);
                    }
                    byte[] content = getFileData(pInForm, item);
                    FileOutputStream fileoutputstream = new FileOutputStream(workPath + s3);
                    fileoutputstream.write(content, 0, content.length);
                    fileoutputstream.close();
                    return true;

                }

            }
        }
        catch (Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_swb_util", "error_WBResource_uploadFile_exc01") + " " + base.getId() + SWBUtils.TEXT.getLocaleString("locale_swb_util", "error_WBResource_uploadFile_exc02") + " " + base.getResourceType() + SWBUtils.TEXT.getLocaleString("locale_swb_util", "error_WBResource_uploadFile_exc03") + " " + fileName + ".");
        }
        return bOk;
    }

}
