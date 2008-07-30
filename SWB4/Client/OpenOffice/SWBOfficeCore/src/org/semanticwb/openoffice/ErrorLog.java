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
