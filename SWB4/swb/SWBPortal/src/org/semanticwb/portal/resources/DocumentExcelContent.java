/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.office.comunication.OfficeDocument;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portlet.office.ExcelPortlet;
import org.semanticwb.portlet.office.PPTPortlet;
import org.semanticwb.portlet.office.WordPortlet;

/**
 *
 * @author victor.lorenzana
 */
public class DocumentExcelContent extends GenericAdmResource
{

    private static Logger log = SWBUtils.getLogger(DocumentExcelContent.class);

    @Override
    public void setResourceBase(Portlet base)
    {
        loadContent(base);
        try
        {
            super.setResourceBase(base);
        }
        catch (SWBException e)
        {
            log.error(e);
        }
    }

    private void clean(Portlet base)
    {
        String basePath = SWBUtils.getApplicationPath() + base.getWorkPath() + "/";
        File dir = new File(basePath);
        if (dir.exists())
        {
            for (File file : dir.listFiles())
            {
                if (file.isFile())
                {
                    file.delete();
                }
            }
        }
    }

    private void loadContent(Portlet base)
    {
        if (base instanceof ExcelPortlet)
        {
            String basePath = SWBUtils.getApplicationPath() + base.getWorkPath() + "/";
            clean(base);
            ExcelPortlet portlet = (ExcelPortlet) base;
            String contentId = portlet.getContent();
            String repositoryName = portlet.getRepositoryName();
            OfficeDocument document = new OfficeDocument();
            // is a zip file
            File zipFile = null;
            try
            {
                InputStream in = document.getContent(repositoryName, contentId,portlet.getVersionToShow());
                String name = UUID.randomUUID().toString();
                String zipPath = basePath + name + ".zip";
                zipFile = new File(zipPath);
                FileOutputStream out = new FileOutputStream(zipFile);
                byte[] buffer = new byte[2048];
                int read = in.read(buffer);
                while (read != -1)
                {
                    out.write(buffer, 0, read);
                    read = in.read(buffer);
                }
                in.close();
                out.close();
                ZipFile zip = new ZipFile(zipFile);
                Enumeration entries = zip.entries();
                while (entries.hasMoreElements())
                {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    if (!entry.isDirectory())
                    {
                        InputStream inEntry = zip.getInputStream(entry);
                        out = new FileOutputStream(basePath + entry.getName());
                        read = inEntry.read(buffer);
                        while (read != -1)
                        {
                            out.write(buffer, 0, read);
                            read = inEntry.read(buffer);
                        }
                        inEntry.close();
                        out.close();
                    }
                }
            }
            catch (Exception e)
            {
            }
            finally
            {
                if (zipFile != null && zipFile.exists())
                {
                    zipFile.delete();
                }
            }
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, IOException
    {
        if (this.getResourceBase() instanceof ExcelPortlet)
        {
            String basePath = SWBUtils.getApplicationPath() + this.getResourceBase().getWorkPath() + "/";
            ExcelPortlet portlet = (ExcelPortlet) this.getResourceBase();
            String contentId = portlet.getContent();
            String repositoryName = portlet.getRepositoryName();
            OfficeDocument document = new OfficeDocument();
            try
            {
                PrintWriter out = response.getWriter();
                String file = document.getContentFile(repositoryName, contentId,portlet.getVersionToShow());
                file = file.replace(".doc", ".html");
                String path = basePath + file;
                FileInputStream in = new FileInputStream(path);
                byte[] buffer = new byte[2048];
                int read = in.read(buffer);
                while (read != -1)
                {
                    out.write(new String(buffer, 0, read));
                    read = in.read(buffer);
                }
            }
            catch (Exception e)
            {
                log.error(e);
            }

        }
    }
}

