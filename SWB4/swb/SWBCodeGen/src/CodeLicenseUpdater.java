import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.semanticwb.SWBUtils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier.solis.g
 */
public class CodeLicenseUpdater
{
    String license="/*\n"+  
" * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,\n"+
" * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de\n"+
" * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes\n"+
" * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y\n"+
" * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación\n"+
" * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.\n"+
" *\n"+
" * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),\n"+
" * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;\n"+
" * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,\n"+
" * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización\n"+
" * del SemanticWebBuilder 4.0.\n"+
" *\n"+
" * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,\n"+
" * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar\n"+
" * de la misma.\n"+
" *\n"+
" * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente\n"+
" * dirección electrónica:\n"+
" *  http://www.semanticwebbuilder.org\n"+
" */\n";
       
    
    public void processDir(File dir)
    {
        //System.out.println("Procesando Directorio:"+dir);
        File files[]=dir.listFiles();
        for(int x=0;x<files.length;x++)
        {
            File file=files[x];
            if(file.isDirectory())
            {
                processDir(file);
            }else
            {
                processFile(file);
            }
            
        }
    }
    
    public void processFile(File file)
    {
        if(!file.getName().endsWith(".java"))return;
        try
        {
            FileInputStream in=new FileInputStream(file);
            String content=SWBUtils.IO.readInputStream(in,"utf-8");
            if(!content.startsWith(license))
            {
                int x=content.indexOf("package org.semanticwb");
                int y=content.indexOf("public class");
                if(x>-1 && x<y)
                {
                    content=license+content.substring(x);
                    //System.out.println(content);
                    FileOutputStream out=new FileOutputStream(file);
                    out.write(content.getBytes("utf-8"));
                    System.out.println("Procesando Archivo:"+file);
                    out.flush();
                    out.close();
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public static void main(String args[])
    {
        System.out.println(System.getenv());
        File file=new File("/programming/proys/SWB4/swb4d");
        //File file=new File("/programming/proys/SWB4/swb/SWBBase");
        //File file=new File("/programming/proys/SWB4/temp");

        if(file.isDirectory())
        {
            new CodeLicenseUpdater().processDir(file);
        }else
        {
            new CodeLicenseUpdater().processFile(file);
        }
    }
}
