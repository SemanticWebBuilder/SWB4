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
package org.semanticwb.portal.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.UUID;
import javax.activation.MimetypesFileTypeMap;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Administra la descarga de contenidos, en forma de archivos de file system,
 * incrementando un contador de las descargas realizadas del archivo asociado.
 *
 * @author Jorge Alberto Jiménez
 */
public class FileDownload extends GenericAdmResource
{

    static class FileInfo
    {

        String content_length;
        String content_type;
    }

    /*private static boolean isURL(String urlDownloadnew)
     {
     try
     {
     new URL(urlDownloadnew).;
     return true;
     }
     catch (MalformedURLException e)
     {
     return false;
     }
     }*/
    private static final long K = 1024;
    private static final long M = K * K;
    private static final long G = M * K;
    private static final long T = G * K;

    public static String convertToStringRepresentation(final long value)
    {
        final long[] dividers = new long[]
        {
            T, G, M, K, 1
        };
        final String[] units = new String[]
        {
            "TB", "GB", "MB", "KB", "B"
        };
        if (value < 1)
        {
            throw new IllegalArgumentException("Invalid file size: " + value);
        }
        String result = null;
        for (int i = 0; i < dividers.length; i++)
        {
            final long divider = dividers[i];
            if (value >= divider)
            {
                result = format(value, divider, units[i]);
                break;
            }
        }
        return result;
    }

    private static String format(final long value,
            final long divider,
            final String unit)
    {
        final double result
                = divider > 1 ? (double) value / (double) divider : (double) value;
        return new DecimalFormat("#,##0.#").format(result) + " " + unit;
    }

    private static String getFileName(URL url)
    {
        String file;
        try
        {
            file = url.toURI().getPath();
        }
        catch (URISyntaxException ex)
        {
            return null;
        }

        int pos2 = file.lastIndexOf("/");
        if (pos2 != -1)
        {
            file = file.substring(pos2 + 1);
            if (file.contains("."))
            {
                return file;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }

    }

    private static boolean isURL(String urlDownloadnew)
    {
        try
        {            
            new URL(urlDownloadnew);
            return true;
        }
        catch (MalformedURLException e)
        {
            return false;
        }

    }

    private static void download(URL url, OutputStream out)
    {
        HttpURLConnection connection;
        try
        {
            connection = (HttpURLConnection) url.openConnection();
        }
        catch (IOException ex)
        {
            log.debug(ex);
            return;
        }
        try
        {
            connection.setRequestMethod("GET");
        }
        catch (ProtocolException ex)
        {
            log.debug(ex);
            return;
        }
        connection.setConnectTimeout(60000); //60 secs
        connection.setReadTimeout(60000); //60 secs
        try
        {
            if (connection.getResponseCode() != 200)
            {
                return;
            }
        }
        catch (IOException ex)
        {
            log.debug(ex);
            return;
        }
        InputStream stream;
        try
        {
            stream = connection.getInputStream();
        }
        catch (IOException ex)
        {
            log.debug(ex);
            return;
        }
        byte[] buffer = new byte[1024];
        int read;
        try
        {
            read = stream.read(buffer);
        }
        catch (IOException ex)
        {
            log.debug(ex);
            return;
        }
        while (read != -1)
        {
            try
            {
                out.write(buffer, 0, read);
            }
            catch (IOException ex)
            {
                log.debug(ex);
                return;
            }
            try
            {
                read = stream.read(buffer);
            }
            catch (IOException ex)
            {
                log.debug(ex);
                return;
            }
        }
        try
        {
            stream.close();
        }
        catch (IOException ex)
        {
            log.debug(ex);
            return;
        }
        try
        {
            out.close();
        }
        catch (IOException ex)
        {
            log.debug(ex);            
        }
    }

    private static FileInfo getInfo(URL urlDownloadnew)
    {
        try
        {
            //URL urlDownloadnew = new URL("http://www.diputados.gob.mx/LeyesBiblio/pdf/111.pdf");
            HttpURLConnection connection = (HttpURLConnection) urlDownloadnew.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(60000); //60 secs
            connection.setReadTimeout(60000); //60 secs
            if (connection.getResponseCode() != 200)
            {
                return null;
            }
            String contentLength = connection.getHeaderField("Content-Length");
            String contentType = connection.getHeaderField("Content-Type");
            /*System.out.println("c: " + contentLength);
             System.out.println("contentType: " + contentType);*/
            FileInfo info = new FileInfo();
            info.content_length = contentLength;
            info.content_type = contentType;
            return info;
        }
        catch (IOException e)
        {
            return null;
        }
    }
    javax.xml.transform.Templates tpl;

    private static final Logger log = SWBUtils.getLogger(FileDownload.class);

    @Override
    public void setResourceBase(Resource base)
    {
        try
        {
            super.setResourceBase(base);
        }
        catch (SWBResourceException e)
        {
            log.error("Error while setting resource base: " + base.getId() + "-" + base.getTitle(), e);
        }
        if (!"".equals(base.getAttribute("template", "").trim()))
        {
            try
            {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() + "/" + base.getAttribute("template").trim()));
            }
            catch (TransformerConfigurationException e)
            {
                log.error("Error while loading resource template: " + base.getId(), e);
            }
            catch (SWBException e)
            {
                log.error("Error while loading resource template: " + base.getId(), e);
            }
        }
        //System.out.println("template: " + tpl);
        if (tpl == null)
        {
            try
            {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/FileDownload/WBFileDownload.xslt"));
                //System.out.println("template por defecto: " + tpl);
            }
            catch (TransformerConfigurationException e)
            {
                log.error("Error while loading default resource template: " + base.getId(), e);
            }
        }
    }

    //@Override
    public void doView(javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response,
            SWBParamRequest reqParams) throws SWBResourceException, java.io.IOException
    {
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();

        Resource base = this.getResourceBase();
        try
        {
            String fileName = base.getAttribute("fileName", "").trim();
            if (reqParams.getAction() == null || !reqParams.getAction().equals("download"))
            {
                SWBResourceURL wburl = reqParams.getRenderUrl();
                wburl.setCallMethod(SWBResourceURL.Call_DIRECT);
                wburl.setAction("download");

                String text = base.getAttribute("text", "");
                String architecture = base.getAttribute("architecture", "");
                String historyUrl = base.getAttribute("historyUrl", "");
                String fileName1 = base.getAttribute("fileName1", null);

                if (fileName1 != null && !fileName1.trim().isEmpty() && (fileName1.startsWith("http://") || fileName1.startsWith("https://")) && isURL(fileName1))
                {
                    URL url = new URL(fileName1);
                    String nameFileURL = getFileName(url);
                    if (nameFileURL != null)
                    {
                        FileInfo info = getInfo(url);
                        Document dom = SWBUtils.XML.getNewDocument();
                        Element el = dom.createElement("FileDownload");
                        el.setAttribute("text", text);
                        el.setAttribute("name", nameFileURL);
                        if (base.getAttribute("fileName1_uri", null) == null)
                        {
                            String uuid = UUID.randomUUID().toString();
                            base.setAttribute("fileName1_uri", uuid);
                        }
                        String uuid = base.getAttribute("fileName1_uri");
                        el.setAttribute("path", wburl.toString() + "/uridoc/" + uuid);
                        int length = 0;
                        if (info.content_length != null)
                        {
                            length = Integer.parseInt(info.content_length);
                        }
                        String ext = "SIN EXTENSION";
                        int pos = nameFileURL.lastIndexOf(".");
                        if (pos != -1)
                        {
                            ext = nameFileURL.substring(pos);
                        }
                        String size = "0";
                        if (length > 0)
                        {
                            size = convertToStringRepresentation(length);
                        }
                        el.setAttribute("length", size);
                        el.setAttribute("nHits", "" + base.getHits());
                        el.setAttribute("url", historyUrl);
                        el.setAttribute("tArchitecture", architecture);
                        el.setAttribute("textension", ext);
                        dom.appendChild(el);
                        if (tpl == null)
                        {

                            if (!"".equals(base.getAttribute("template", "").trim()))
                            {
                                try
                                {
                                    tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() + "/" + base.getAttribute("template").trim()));
                                }
                                catch (TransformerConfigurationException e)
                                {
                                    log.error("Error while loading resource template: " + base.getId(), e);
                                }
                                catch (SWBException e)
                                {
                                    log.error("Error while loading resource template: " + base.getId(), e);
                                }
                            }
                            else
                            {
                                //System.out.println("template: " + tpl);
                                try
                                {
                                    InputStream input = SWBPortal.getAdminFileStream("/swbadmin/xsl/FileDownload/WBFileDownload.xslt");
                                    if (input != null)
                                    {
                                        tpl = SWBUtils.XML.loadTemplateXSLT(input);
                                        //} else {
                                        //    System.out.println("\nInputStream NULO!, no encuentra: /swbadmin/xsl/FileDownload/WBFileDownload.xslt");
                                    }
                                }
                                catch (TransformerConfigurationException e)
                                {
                                    log.error("Error while loading default resource template: " + base.getId(), e);
                                }
                            }
                        }

                        response.getWriter().print(SWBUtils.XML.transformDom(tpl, dom));
                    }
                }
                else
                {

                    if(fileName==null || fileName.trim().isEmpty())
                    {
                        return;
                    }
                    fileName = SWBPortal.getWorkPath() + base.getWorkPath() + "/" + fileName;
                    java.io.File file = new java.io.File(fileName);
                    if(!file.exists())
                    {
                        return;
                    }
                    int pos = fileName.lastIndexOf("/");
                    if (pos > - 1)
                    {
                        fileName = fileName.substring(pos + 1);
                    }
                    String ext = "";                    
                    pos = fileName.lastIndexOf(".");
                    if (pos > -1)
                    {
                        ext = fileName.substring(pos);
                    }
                    Document dom = SWBUtils.XML.getNewDocument();
                    Element el = dom.createElement("FileDownload");
                    el.setAttribute("text", text);
                    el.setAttribute("name", fileName);
                    el.setAttribute("path", wburl.toString() + "/doc/" + base.getAttribute("fileName", "").trim());
                    String size = "0";
                    if (file.length() > 0)
                    {
                        size = convertToStringRepresentation(file.length());
                    }
                    el.setAttribute("length", size);
                    el.setAttribute("nHits", "" + base.getHits());
                    el.setAttribute("url", historyUrl);
                    el.setAttribute("tArchitecture", architecture);
                    el.setAttribute("textension", ext);
                    dom.appendChild(el);
                    //System.out.println("El DOM a utilizar: " + SWBUtils.XML.domToXml(dom));
                    /**
                     * Por ahora *
                     */
                    if (tpl == null)
                    {

                        if (!"".equals(base.getAttribute("template", "").trim()))
                        {
                            try
                            {
                                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() + "/" + base.getAttribute("template").trim()));
                            }
                            catch (TransformerConfigurationException e)
                            {
                                log.error("Error while loading resource template: " + base.getId(), e);
                            }
                            catch (SWBException e)
                            {
                                log.error("Error while loading resource template: " + base.getId(), e);
                            }
                        }
                        else
                        {
                            //System.out.println("template: " + tpl);
                            try
                            {
                                InputStream input = SWBPortal.getAdminFileStream("/swbadmin/xsl/FileDownload/WBFileDownload.xslt");
                                if (input != null)
                                {
                                    tpl = SWBUtils.XML.loadTemplateXSLT(input);
                                    //} else {
                                    //    System.out.println("\nInputStream NULO!, no encuentra: /swbadmin/xsl/FileDownload/WBFileDownload.xslt");
                                }
                            }
                            catch (TransformerConfigurationException e)
                            {
                                log.error("Error while loading default resource template: " + base.getId(), e);
                            }
                        }
                    }

                    response.getWriter().print(SWBUtils.XML.transformDom(tpl, dom));
                }

            }
            else
            {
                int pos = request.getRequestURI().indexOf("/uridoc/");
                if (pos != -1)
                {
                    String uri = request.getRequestURI().substring(pos + 8);
                    if (uri == null || uri.trim().isEmpty())
                    {
                        return;
                    }
                    if (base.getAttribute("fileName1_uri", null) == null)
                    {
                        String uuid = UUID.randomUUID().toString();
                        base.setAttribute("fileName1_uri", uuid);
                    }
                    String uuid2 = base.getAttribute("fileName1_uri", null);
                    if (!uri.equals(uuid2))
                    {
                        return;
                    }
                    String fileName1 = base.getAttribute("fileName1", null);
                    URL url = new URL(fileName1);
                    if (fileName1 != null && !fileName1.trim().isEmpty() && isURL(fileName1))
                    {
                        String nameFileURL = getFileName(url);
                        if (nameFileURL != null)
                        {
                            FileInfo info = getInfo(url);
                            String contentLength = "0";
                            if (info.content_length != null)
                            {
                                contentLength = "" + Integer.parseInt(info.content_length);
                            }
                            String contentType;
                            if (info.content_type != null)
                            {
                                contentType = info.content_type;
                            }
                            else
                            {
                                contentType=mimeTypesMap.getContentType(nameFileURL);
                                if(contentType==null)
                                {
                                    contentType = "application/binary";
                                }
                            }
                            response.setHeader("Content-Length", contentLength);
                            response.setHeader("Content-Disposition", "attachment;filename=" + nameFileURL);
                            response.setContentType(contentType);
                            base.addHit(request, reqParams.getUser(), reqParams.getWebPage());
                            download(url, response.getOutputStream());
                            return;
                        }
                        else
                        {
                            return;
                        }
                    }
                    else
                    {
                        return;
                    }
                }
                pos = request.getRequestURI().indexOf("/doc/");
                if (pos > -1)
                {
                    fileName = request.getRequestURI().substring(pos + 5);
                }
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                fileName = SWBPortal.getWorkPath() + base.getWorkPath() + "/" + fileName;
                java.io.File file = new java.io.File(fileName);
                response.setHeader("Content-Length", file.length() + "");
                String mimeType=mimeTypesMap.getContentType(file);
                if(mimeType==null)
                {
                    mimeType="application/binary";
                }
                response.setContentType(mimeType);
                
                base.addHit(request, reqParams.getUser(), reqParams.getWebPage());
                SWBUtils.IO.copyStream(new FileInputStream(fileName), response.getOutputStream());
            }
        }
        catch (IOException e)
        {
            log.error("At responding file download request", e);
        }
        catch (NumberFormatException e)
        {
            log.error("At responding file download request", e);
        }
        catch (TransformerException e)
        {
            log.error("At responding file download request", e);
        }
        catch (DOMException e)
        {
            log.error("At responding file download request", e);
        }
    }

}
