/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * FileDownload.java
 *
 * Created on 12 de noviembre de 2004, 09:55 AM
 */

package applets.ftp;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 * Clase que hace la descarga de archivos de un servidor, esta asociada a la clase
 * FDownload, que la presentación.
 * @author Victor Lorenzana
 */
public class FileDownload {
    
    /** Creates a new instance of FileDownload */
    JProgressBar progressbar;
    java.io.File f;    
    URL url;
    String path;
    String jsess;
    Locale locale;
    public FileDownload(String path,JProgressBar progressbar,java.io.File f,String jsess,URL url,Locale locale) {
        this.f=f;
        this.progressbar=progressbar;        
        this.path=path;
        this.jsess=jsess;
        this.url=url;
        this.locale=locale;
    }
    
    public void getFile() {
        try
        {            
            FileOutputStream out=new FileOutputStream(f);
            URLConnection con=url.openConnection();
            if(jsess!=null)con.setRequestProperty("Cookie","JSESSIONID="+jsess);
            con.addRequestProperty("PATHFILEWB",path);
            con.setDoInput(true);
            InputStream in=con.getInputStream();
            byte[] bcont=new byte[8192];
            String contentLength=con.getHeaderField("Content-Length");            
            int size=0;
            try
            {
                size=Integer.parseInt(contentLength);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            int packs=(size/8192)+1;
            this.progressbar.setMaximum(packs);
            this.progressbar.setMinimum(0);
            this.progressbar.setValue(0);
            int ipack=0;
            int ret=in.read(bcont);
            while(ret!=-1)
            {
                out.write(bcont,0,ret);
                ipack++;
                this.progressbar.setValue(ipack);
                ret=in.read(bcont);
            }
            this.progressbar.setValue(0);            
            in.close();
            out.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),java.util.ResourceBundle.getBundle("applets/ftp/ftp",locale).getString("title"),JOptionPane.ERROR_MESSAGE);            
        }        
    }
    
}
