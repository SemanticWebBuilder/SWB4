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
 * MailMessage.java
 *
 * Created on 28 de marzo de 2006, 01:01 PM
 */

package com.infotec.appfw.lib.mail;

import com.infotec.appfw.exception.AFException;
import com.infotec.appfw.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import net.larsan.email.EmailAddress;
import net.larsan.email.InvalidFormatException;
import net.larsan.email.Message;
import net.larsan.email.smtp.SMTPException;
import net.larsan.email.smtp.SMTPSender;
import net.larsan.email.util.MIMEMessageCreator;

/**
 * Objeto utilizado para crear y enviar correos electr�nicos soportando
 * el env�o de archivos.
 * Email message object thats support the file attachment.
 * @author Javier Solis Gonzalez
 */
public class MailMessage
{
    MIMEMessageCreator message=null;
    
    /**
     * Crea una nueva instancia de MailMessage utilizando el 
     * c�digo de caracteres "ISO-8859-1" por defecto.
     * Create a new MailMessage. The message creator
     * will use the default character set "ISO-8859-1".
     */
    
    public MailMessage()
    {
        message=new MIMEMessageCreator();
    }
    
    
    /**
     * Crea una nueva instancia de MailMessage utilizando el par�metro del
     * c�digo de caracteres a utilizar.
     * Create a new MailMessage. The message creator
     * will use the parameter character set.
     * @param charset Charset code to use in the message.
     */
    
    public MailMessage(String charset)
    {
        message=new MIMEMessageCreator(charset);
    }
    
    
    /**
     * Agrega la direcci�n de correo del que env�a el mensaje.
     * Set message sender address.
     * @param address The sender email address.
     * @throws com.infotec.appfw.exception.AFException Throws an Application frameWork Exception.
     */
    
    public void setFrom(String address) throws AFException
    {
        try
        {
            message.setFrom(new EmailAddress(address));
        }catch(InvalidFormatException e){throw new AFException("Invalid Address Format","setFrom",e);}
    }
    
    
    /**
     * Agrega el asunto del mensaje.
     * Set message subject.
     * @param subject A subject of the message.
     */
    
    public void setSubject(String subject)
    {
        message.setSubject(subject);
    }
    
    
    /**
     * Agrega direcci�n de correo para el env�o del mensaje.
     * Add a recipient type TO.
     * @param address An email address to send the message
     * @throws com.infotec.appfw.exception.AFException Throws an Application FrameWork Exception
     */
    
    public void addTo(String address) throws AFException
    {
        try
        {
            message.addTo(new EmailAddress(address));
        }catch(InvalidFormatException e){throw new AFException("Invalid Address Format","addTo",e);}
    }
    
    
    /**
     * Agrega direcci�n de correo para enviar copia del mensaje.
     * Add a recipient type CC.
     * @param address An email address to send the message
     * @throws com.infotec.appfw.exception.AFException Throws an Application FrameWork Exception
     */
    
    public void addCc(String address) throws AFException
    {
        try
        {
            message.addCc(new EmailAddress(address));
        }catch(InvalidFormatException e){throw new AFException("Invalid Address Format","addCc",e);}
    }
    
    /**
     * Agrega direcciones de correo ocultas al mensaje.
     * Add a recipient type BCC.
     * @param address An email address to send the message
     * @throws com.infotec.appfw.exception.AFException Throws an Application FrameWork Exception
     */
    
    public void addBcc(String address) throws AFException
    {
        try
        {
            message.addBcc(new EmailAddress(address));
        }catch(InvalidFormatException e){throw new AFException("Invalid Address Format","addBcc",e);}
    }
    
    
    /**
     * Actualiza el c�digo de caract�res del mensaje. El de defecto es "ISO-8859-1".
     * Set costum charset other than the default "ISO-8859-1".
     * @param charset Charset code.
     */
    
    public void setCharset(String charset)
    {
        message.setCharset(charset);
    }
    
    
    /**
     * Obtiene el c�digo de caract�res actual del mensaje.
     * Get current charset.
     * @return The charset of the message.
     */
    
    public String getCharset()
    {
        return message.getCharset();
    }
    
    
    /**
     * Agrega una determinada fecha del mesaje. 
     * Insert date to the message.
     * @param date The date of the message.
     */
    
    public void insertDate(Date date)
    {
        message.insertDate(date);
    }
    
    
    /**
     * Agrega el contenido del mensaje en texto plano.
     * Add plain text to the message.
     * @param text Plain text content of the message.
     */
    
    public void addText(String text)
    {
        message.addText(text);
    }
    
    
    /**
     * Agrega html plano al mensaje. 
     * Add plain html to the message.
     * @param html Html format of the message content.
     */
    
    public void addHtml(String html)
    {
        message.addHtml(html);
    }
    
    
    /**
     * Add a message to this message. This method will insert a body of content type message/rfc822
     * in the message with the parameter message as content.
     */
    
//    public void addMessage(Message msg)
//    {
//        message.addMessage(msg);
//    }
    
    
    /**
     * Agrega el contenido del mensaje en formato html o en de texto de la misma informaci�n. El 
     * texto no ser� visible para recipientes que soportan formato HTML.
     * Add html to the message including a text version of the same information.
     * The text will not be visible for recipients who can handle the html.
     * @param html Html version of the message content.
     * @param text A text version of the message content.
     */
    
    public void addHtml(String html, String text)
    {
        message.addHtml(html,text);
    }
    
    
    /**
     * Adjunta una imagen de tipo GIF al mensaje en formato de arreglo de bytes. 
     * Add a GIF image to the message. This image will most probably be regarded
     * as an attachment by the recipient since it is not a part of the protocoll standard.
     * @param image The image to attach in bytes array format to the message.
     * @param name The image name.
     */
    
    public void addGIFImage(byte[] image, String name)
    {
        message.addGIFImage(image,name);
    }
    
    
    /**
     * Adjunta una imagen de tipo JPG al mensaje en formato de arreglo de bytes. 
     * Add a JPG image to the message. This image will most probably be regarded
     * as an attachment by the recipient since it is not a part of the protocoll standard.
     * @param image The image to attach in bytes array format to the message.
     * @param name The image name.
     */
    
    public void addJPEGImage(byte[] image, String name)
    {
        message.addJPEGImage(image,name);
    }
    
    
    /**
     * Adjunta un archivo en formato de arreglo de bytes al mensaje.
     * Add a bytes array attachment to the mail message.
     * @param file A file into bytes array to attach to the message
     * @param name The name of the file to attach to the message.
     */
    
    public void addAttachment(byte[] file, String name)
    {
        message.addAttachment(file,name);
    }
    
    /**
     * Archivo de tipo File para adjuntarlo al mensaje. 
     * Add a binary attachment to the message.
     * @param file A file object to attach to the message.
     * @param name The file name to attach to the message.
     * @throws java.io.FileNotFoundException Throws a File Not Found Exception if the file doesn't exist.
     * @throws java.io.IOException Throws an IO Exception.
     */
    
    public void addAttachment(File file, String name) throws FileNotFoundException, IOException
    {
        message.addAttachment(AFUtils.readFile(file),name);
    }    
    
    
    /**
     * Add a file attachment to the message.
     * @param file A file in a String format to attach to the message.
     * @param name The file name to attach.
     */
    
    public void addAttachment(String file, String name)
    {
        message.addAttachment(file,name);
    }
    
    /**
     * M�todo utilizado para enviar el correo electr�nico.
     * Method to send the email message.
     * @param host SMTP server to use for send the email message.
     * @throws com.infotec.appfw.exception.AFException Throws an Application Framework Exception
     * @throws java.io.IOException Throws an IO Exception.
     */
    public void sendMessage(String host) throws AFException, IOException
    {
        try
        {
            Message msg = message.getMessage();
            SMTPSender sender = new SMTPSender(host);
            sender.connect();
            sender.send(msg);
            sender.disconnect();         
        }catch(SMTPException e)
        {
            throw new AFException("SMTPException","sendMessage",e);
        }
    }
    
    
//    /**
//     * Reset message. This method should be called between the creation of multiple
//     * messages. User's wishing to change character st should also call this method
//     * to make sure the change is reflected in the message being produced.
//     */
//    
//    public void reset()
//    {
//        body = new MixedBody(getBoundary(""));
//        msg = new DefaultMessage(body, charset);
//        msg.getHeader().set(new EmailHeaderField("MIME-Version", "1.0"));
//    }
    
    
//    /**
//     * Get the finished message.
//     */
//    
//    public Message getMessage()
//    {
//        return checkMessage();
//    }
    
    
//    /** This method only check if the message contains more than one body. */
//    
//    private Message checkMessage()
//    {
//        
//        int count = 0;
//        
//        // is there more than one body part ?
//        
//        for(Iterator i = body.getBodyParts(); i.hasNext();)
//        {
//            
//            if(count > 1) break;
//            
//            i.next();
//            count++;
//            
//        }
//        
//        if(count == 1)
//        {
//            
//            // extract subject, sender, charset and recipients
//            // and insert them into the new message object
//            
//            Iterator it = body.getBodyParts();
//            
//            Header header = body.getHeader();
//            
//            header.remove("Content-Type");
//            
//            Body newBody = (Body)it.next();
//            
//            HeaderField[] all = header.getAll();
//            
//            for(int i = 0; i < all.length; i++) newBody.getHeader().set(all[i]);
//            
//            return new DefaultMessage(newBody, charset);
//            
//        } else return msg;
//    }
    
    
//    /** Create a new boundary that does not equals the enclosing one. */
//    
//    private String getBoundary(String enclosing)
//    {
//        
//        String start = "=__Next_Part_";
//        
//        String end = "__=";
//        
//        StringBuffer answer = new StringBuffer(start);
//        answer.append(new Date().getTime()).append(end);
//        
//        // check for equality
//        
//        if((answer.toString()).equalsIgnoreCase(enclosing))
//        {
//            
//            answer = new StringBuffer(start);
//            answer.append(new Date().getTime());
//            answer.append(end);
//        }
//        
//        return answer.toString();
//    }
    
    
}
