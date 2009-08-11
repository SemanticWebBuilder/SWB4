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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.semanticwb.openoffice.ui.dialogs.DialogError;

/**
 *
 * @author victor.lorenzana
 */
public class ErrorLog
{

    public static final String CONFIGURATION = "org.semanticwb.openoffice.configuration.ErrorLog";
    private static final ErrorLog instance;
    private static final Calendar cal = Calendar.getInstance();
    private static final SimpleDateFormat dateFormatLog;
    private static final SimpleDateFormat dateFormatMessage;
    private File fileLog;
    private FileOutputStream fout;

    static
    {
        dateFormatLog = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss'_openofficeerror.log'");
        dateFormatMessage = new SimpleDateFormat("dd/MM/yyyy/ HH:mm:ss ");
        instance = new ErrorLog();
    }

    private ErrorLog()
    {
        new Configuration();
        String path = System.getProperty(CONFIGURATION, ".");
        String name = dateFormatLog.format(cal.getTime());
        fileLog = new File(path + File.separator + name);
        fileLog.delete();
        try
        {
            fout = new FileOutputStream(fileLog);
        }
        catch ( FileNotFoundException fnfe )
        {
            fnfe.printStackTrace(System.out);
        }
    }

    protected void finalize() throws Throwable
    {
        fout.close();
    }

    public static void show(Exception e,String title)
    {
        DialogError error = new DialogError(new javax.swing.JFrame("Error"), true, e);
        error.setTitle(title);
        error.setLocationRelativeTo(null);
        error.setVisible(true);
    }

    public static void log(String message)
    {
        instance.saveMessage(message);
    }

    public static void log(Throwable exception)
    {
        instance.saveMessage(exception);
    }

    private void saveMessage(String message)
    {
        try
        {

            String time = dateFormatMessage.format(cal.getTime());
            StringBuilder messageBuilder = new StringBuilder(time);
            messageBuilder.append(message);
            messageBuilder.append("\r\n");
            byte[] bmessage = messageBuilder.toString().getBytes();
            fout.write(bmessage);
            fout.flush();

        }
        catch ( IOException ioe )
        {
            ioe.printStackTrace(System.out);
        }
    }

    private void saveMessage(Throwable exception)
    {
        this.saveMessage(exception.getMessage());
        for ( StackTraceElement element : exception.getStackTrace() )
        {
            this.saveMessage(element.toString());
        }
        if ( exception.getCause() != null )
        {
            this.saveMessage(exception.getCause());
        }
    }
}
