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
 * FileUpload.java
 *
 * Created on 11 de noviembre de 2004, 05:47 PM
 */

package applets.ftp;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 * Clase que realiza la funci�n de env�ar un archivo al servidor, esta asociada
 * esta clase con la clase FUpload, que es la representaci�n grafica de esta
 * opci�n.
 * @author Victor Lorenzana
 */
public class FileUpload {
    
    /** Creates a new instance of FileUpload */
       
    URL url;
    String jsess;
    Vector listeners=new Vector();
    Locale locale;
    public FileUpload(String jsess,URL url,Locale locale) {                
        this.jsess=jsess;
        this.url=url;
        this.locale=locale;
    }    
    public void addSendListener(FileUploadListener listener)
    {
        listeners.add(listener);
    }
    public void fireSend(int size,int value)
    {
        Iterator it=listeners.iterator();
        while(it.hasNext())
        {
            FileUploadListener fl=(FileUploadListener)it.next();
            fl.onSend(size,value);
        }
    }
    public boolean sendFile(String path,java.io.File f) {  
        try
        {            
            int max=(int)(f.length()/2048);
            max++;            
            if(f.exists())
            {

                try
                {
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    con.setUseCaches(false);
                    con.setDefaultUseCaches(false);   
                    con.setFixedLengthStreamingMode((int)f.length());
                    if(jsess!=null)con.setRequestProperty("Cookie","JSESSIONID="+jsess);
                    con.addRequestProperty("PATHFILEWB",path);
                    con.setDoOutput(true);                
                    OutputStream out=con.getOutputStream();
                    FileInputStream fin=new FileInputStream(f);
                    byte[] bcont=new byte[2048];
                    int ret=fin.read(bcont);
                    int ivalue=0;
                    while(ret!=-1)
                    {
                        out.write(bcont,0,ret);                    
                        ivalue++;           
                        fireSend(max, ivalue);
                        ret=fin.read(bcont);
                        out.flush();
                    }
                    out.close();
                    fin.close();                                      
                    String resp=con.getHeaderField(0);
                    
                    StringTokenizer st=new StringTokenizer(resp," ");
                    if(st.countTokens()>=2)
                    {
                        String intcode=st.nextToken();
                        intcode=st.nextToken();
                        if(intcode.equals("200"))
                        {
                            return true;
                        }
                        else
                        {
                            if(st.countTokens()>=3)
                            {
                                System.out.println("fileupload http code: "+resp);
                                JOptionPane.showMessageDialog(null,st.nextToken(),java.util.ResourceBundle.getBundle("applets/ftp/ftp",locale).getString("title"),JOptionPane.ERROR_MESSAGE);            
                            }
                        }
                    }

                }
                catch(Exception e)
                {           
                    e.printStackTrace(System.out);
                    if(e.getMessage()!=null)
                    {
                        JOptionPane.showMessageDialog(null,"error: "+e.getMessage(),java.util.ResourceBundle.getBundle("applets/ftp/ftp",locale).getString("title"),JOptionPane.ERROR_MESSAGE);            
                    }
                    
                }
            }
        }
        catch(Exception err)
        {
            System.out.println("error: "+err.getMessage());
        }
        return false;
        
    }
    
}
