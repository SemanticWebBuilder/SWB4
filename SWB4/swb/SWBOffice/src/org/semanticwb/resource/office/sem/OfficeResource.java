package org.semanticwb.resource.office.sem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.*;

public class OfficeResource extends org.semanticwb.resource.office.sem.base.OfficeResourceBase
{

    public OfficeResource()
    {
        super();
    }

    public OfficeResource(SemanticObject obj)
    {
        super(obj);
    }
    private static Logger log = SWBUtils.getLogger(OfficeResource.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
    }
    public static void validatePropertyValue(HashMap<SemanticProperty, Object> valuesToValidate)
    {

    }
    public static void clean(String dir)
    {
        File fdir = new File(SWBPlatform.getWorkPath() + dir);
        if (fdir.exists())
        {
            for (File file : fdir.listFiles())
            {
                if (file.isDirectory())
                {
                    clean(file.getAbsolutePath());
                }
                else
                {
                    file.delete();
                }
            }
            fdir.delete();
        }
    }

    private void clean(File dir)
    {
        if (dir.exists() && dir.listFiles() != null)
        {
            for (File file : dir.listFiles())
            {
                if (file.isDirectory())
                {
                    clean(file);
                }
                else
                {
                    file.delete();
                }
            }
        }
    }

    public void clean()
    {
        File dir = new File(SWBPlatform.getWorkPath() + getResourceBase().getWorkPath());
        clean(dir);
    }

    public void loadContent(InputStream in)
    {
        clean();
        File zipFile = null;
        try
        {
            if (in != null)
            {
                String name = UUID.randomUUID().toString() + ".zip";
                SWBPlatform.writeFileToWorkPath(getResourceBase().getWorkPath() + "\\" + name, in, "");
                zipFile = new File(SWBPlatform.getWorkPath() + getResourceBase().getWorkPath() + "\\" + name);
                ZipFile zip = new ZipFile(zipFile);
                Enumeration entries = zip.entries();
                while (entries.hasMoreElements())
                {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    if (!entry.isDirectory())
                    {
                        InputStream inEntry = zip.getInputStream(entry);
                        SWBPlatform.writeFileToWorkPath(getResourceBase().getWorkPath() + "\\" + entry.getName(), inEntry, "");
                    }
                }
                zip.close();
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
        finally
        {
            if (zipFile != null && zipFile.exists())
            {
                zipFile.delete();
            }
        }

    }

    public static void loadContent(InputStream in, String dir)
    {
        File zipFile = null;
        try
        {
            if (in != null)
            {
                String name = UUID.randomUUID().toString() + ".zip";
                SWBPlatform.writeFileToWorkPath(dir + "\\" + name, in, "");
                zipFile = new File(SWBPlatform.getWorkPath() + dir + "\\" + name);
                ZipFile zip = new ZipFile(zipFile);
                Enumeration entries = zip.entries();
                while (entries.hasMoreElements())
                {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    if (!entry.isDirectory())
                    {
                        InputStream inEntry = zip.getInputStream(entry);
                        SWBPlatform.writeFileToWorkPath(dir + "\\" + entry.getName(), inEntry, "");
                    }
                }
                zip.close();
            }
        }
        catch (Exception e)
        {
            log.error(e);
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
