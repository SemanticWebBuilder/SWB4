package org.semanticwb.util;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.semanticwb.base.util.SWBSoftkHashMap;

/**
 *
 * @author serch
 */
public class UploaderFileCacheUtils
{

    static private Random psr = new Random();
    static private String homepath;

    public static void setHomepath(String homepath)
    {
        UploaderFileCacheUtils.homepath = homepath;
    }

    public static String getHomepath()
    {
        return homepath;
    }

    static private final Map<String, List<UploadedFile>> referencias = new SWBSoftkHashMap<String, List<UploadedFile>>(50);
    static private final Map<String, UploadFileRequest> solicitudes = new SWBSoftkHashMap<String, UploadFileRequest>(50);

    static public List<UploadedFile> get(String key)
    {
        return referencias.get(key);
    }

    static public void put(String key, List<UploadedFile> lista)
    {
        referencias.put(key, lista);
    }

    static public UploadFileRequest getRequest(String key)
    {
        return solicitudes.get(key);
    }

    static public void putRequest(String key, UploadFileRequest fileRequest)
    {
        solicitudes.put(key, fileRequest);
    }

    static public String uniqueCad()
    {
        //obtener cadena unica y evadir colisiones
        int mks = 0;
        synchronized (psr)
        {
            mks = Math.abs(psr.nextInt());
        }
        mks = mks - (mks / 10) * 10;
        long base = Math.abs(System.nanoTime() / 1000);
        base = base - (base / 100000000) * 100000000;
        //Cad tiene cadena unica y sin colisiones del estilo fu385761322
        return "fu" + base + mks;
    }

    static public void clean(String cad)
    {
        File toDel = new File(homepath +"/tmp/"+ cad);
        if (toDel.exists())
        {
            delete(toDel);
        }
    }

    static public void delete(File file)
    {
        if (file.isFile())
        {
            file.delete();
        } else if (file.isDirectory())
        {
            File[] childs = file.listFiles();
            for (File tmpfile : childs)
            {
                delete(tmpfile);
            }
            file.delete();
        }
    }
}
