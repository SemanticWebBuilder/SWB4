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
package org.semanticwb.util;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.semanticwb.base.util.SWBSoftkHashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class UploaderFileCacheUtils.
 * 
 * @author serch
 */
public class UploaderFileCacheUtils
{

    /** The psr. */
    static private Random psr = new Random();
    
    /** The homepath. */
    static private String homepath;

    /**
     * Sets the homepath.
     * 
     * @param homepath the new homepath
     */
    public static void setHomepath(String homepath)
    {
        UploaderFileCacheUtils.homepath = homepath;
    }

    /**
     * Gets the homepath.
     * 
     * @return the homepath
     */
    public static String getHomepath()
    {
        return homepath;
    }

    /** The Constant referencias. */
    static private final Map<String, List<UploadedFile>> referencias = new SWBSoftkHashMap<String, List<UploadedFile>>(50);
    
    /** The Constant solicitudes. */
    static private final Map<String, UploadFileRequest> solicitudes = new SWBSoftkHashMap<String, UploadFileRequest>(50);

    /**
     * Gets the.
     * 
     * @param key the key
     * @return the list
     */
    static public List<UploadedFile> get(String key)
    {
        return referencias.get(key);
    }

    /**
     * Put.
     * 
     * @param key the key
     * @param lista the lista
     */
    static public void put(String key, List<UploadedFile> lista)
    {
        referencias.put(key, lista);
    }

    /**
     * Gets the request.
     * 
     * @param key the key
     * @return the request
     */
    static public UploadFileRequest getRequest(String key)
    {
        return solicitudes.get(key);
    }

    /**
     * Put request.
     * 
     * @param key the key
     * @param fileRequest the file request
     */
    static public void putRequest(String key, UploadFileRequest fileRequest)
    {
        solicitudes.put(key, fileRequest);
    }

    /**
     * Unique cad.
     * 
     * @return the string
     */
    static public String uniqueCad()
    {
        //obtener cadena unica y evadir colisiones
        int mks = 0;
        synchronized (psr)
        {
            mks = Math.abs(psr.nextInt());
            if (mks == Integer.MIN_VALUE) mks =1;
        }
        mks = mks - (mks / 10) * 10;
        long base = Math.abs(System.nanoTime() / 1000);
        base = base - (base / 100000000) * 100000000;
        //Cad tiene cadena unica y sin colisiones del estilo fu385761322
        return "fu" + base + mks;
    }

    /**
     * Clean.
     * 
     * @param cad the cad
     */
    static public void clean(String cad)
    {
        File toDel = new File(homepath +"/tmp/"+ cad);
        if (toDel.exists())
        {
            delete(toDel);
        }
    }

    /**
     * Delete.
     * 
     * @param file the file
     */
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
