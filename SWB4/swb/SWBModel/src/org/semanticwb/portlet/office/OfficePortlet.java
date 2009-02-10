package org.semanticwb.portlet.office;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

public class OfficePortlet extends org.semanticwb.portlet.office.base.OfficePortletBase
{

    private static Logger log = SWBUtils.getLogger(OfficePortlet.class);

    public OfficePortlet(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
        File dir = new File(SWBPlatform.getWorkPath() + getWorkPath());
        clean(dir);
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

    public void loadContent(InputStream in)
    {
        clean();
        File zipFile = null;
        try
        {
            if (in != null)
            {
                String name = UUID.randomUUID().toString() + ".zip";
                SWBPlatform.writeFileToWorkPath(getWorkPath() + "\\" + name, in, "");
                zipFile = new File(SWBPlatform.getWorkPath() + getWorkPath() + "\\" + name);
                ZipFile zip = new ZipFile(zipFile);
                Enumeration entries = zip.entries();
                while (entries.hasMoreElements())
                {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    if (!entry.isDirectory())
                    {
                        InputStream inEntry = zip.getInputStream(entry);
                        SWBPlatform.writeFileToWorkPath(getWorkPath() + "\\" + entry.getName(), inEntry, "");
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
