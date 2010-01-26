/**
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
**/


/*
 * WBZip.java
 *
 * Created on 3 de junio de 2002, 03:18 PM
 */

package com.infotec.appfw.util;

import java.io.*;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Genera un archivo ZIP en base a un directorio
 * @author Javier Solis Gonzalez
 * @version
 */
public class WBZip
{

    /** Creates new WBZip */
    public WBZip()
    {
    }

    public void createZip(String targetfile, String sourcefiles[])
    {
        try
        {
            ZipOutputStream zipoutputstream = new ZipOutputStream(new FileOutputStream(targetfile));
            for (int i = 0; i < sourcefiles.length; i++)
            {
                File file = new File(sourcefiles[i]);
                if (!file.isDirectory())
                {
                    ZipEntry zipentry = new ZipEntry(sourcefiles[i]);
                    zipoutputstream.setMethod(8);
                    zipoutputstream.setLevel(-1);
                    zipoutputstream.putNextEntry(zipentry);
                    File file1 = new File(sourcefiles[i]);
                    DataInputStream datainputstream = new DataInputStream(new FileInputStream(sourcefiles[i]));
                    byte abyte0[] = new byte[4096];
                    int j;
                    while ((j = datainputstream.read(abyte0, 0, 4096)) != -1)
                        zipoutputstream.write(abyte0, 0, j);
                    zipoutputstream.closeEntry();
                }
            }

            zipoutputstream.close();
        } catch (Exception exception)
        {
            System.out.println(exception.toString());
        }
    }

    /*public boolean Almacena(String s)
    {
        boolean flag = false;
        Date date = new Date(System.currentTimeMillis());
        String s1 = "" + (date.getYear() + 1900);
        s1 = s1 + "-" + date.getMonth()+1;
        s1 = s1 + "-" + date.getDate();
        s1 = s1 + "-" + date.getHours();
        s1 = s1 + "-" + date.getMinutes();
        String s2 = s + "backup/";
        String s3 = s + "imagenes/";
        File file = new File(s);
        if(!file.isDirectory())
            return false;
        File file1 = new File(s2);
        if(!file1.exists())
            file1.mkdir();
        int i = 0;
        File file2 = new File(s3);
        i = file.list().length;
        i += file2.list().length;
        if(file1.exists())
        {
            String as[] = new String[i];
            for(int j = 0; j < file.list().length; j++)
                as[j] = s + file.list()[j];

            int k = 0;
            for(int l = file.list().length; l < file2.list().length + file.list().length; l++)
            {
                as[l] = s3 + file2.list()[k];
                k++;
            }

            createZip(s2 + s1 + ".zip", as);
            return true;
        } else
        {
            return false;
        }
    }*/

}
