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
 * Environment.java
 *
 * Created on 3 de junio de 2002, 13:22
 */

package com.infotec.appfw.util;

import com.infotec.appfw.lib.mail.MailMessage;
import java.io.*;
import java.util.*;

import com.infotec.appfw.lib.DBPool.DBConnectionManager;
import com.infotec.appfw.lib.mail.AFMailSender;
import com.infotec.appfw.lib.mail.AFMailData;
import com.infotec.appfw.lib.AFAppObject;

import com.infotec.appfw.exception.*;
import java.net.URLDecoder;

import java.sql.Connection;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.xml.sax.InputSource;


/** objeto: Utilerias de uso comun para desarrolladores de WB.
 *  Object of common utilities for wb developers
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class AFUtils implements AFAppObject
{
    static private Logger log = SWBUtils.getLogger(AFUtils.class);

    private static int bufferSize=8192;    

    static private AFUtils instance;       // The single instance
    public static int debug = 0;
    private AFMailSender mailsender;

    private static Locale locale = Locale.ENGLISH;

    public static int DEBUG_ERROR=1;
    public static int DEBUG_WARNING=2;      //By default
    public static int DEBUG_VERBOSE=3;
    public static int DEBUG_INFO=4;

    /** Creates new utils */
    private AFUtils()
    {
    }

    /** Get Instance.
     * @return  */
    static public AFUtils getInstance()
    {
        if (instance == null)
        {
            makeInstance();
        }
        return instance;
    }
    
    static private synchronized void makeInstance()
    {
        if (instance == null)
        {
            instance = new AFUtils();
        }
    }       

    public void init()
    {
        instance = this;

        try
        {
            mailsender = new AFMailSender();
            mailsender.start();
        } catch (IOException e)
        {
            log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBUtils_nomailthread"), true);
        }

        try
        {
            debug = Integer.parseInt(getEnv("af/debug", "0"));
            //db=dbf.newDocumentBuilder();
        } catch (Exception e)
        {
            log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBUtils_noBuilder"), true);
        }
    }

     /** 
     * Regresa nivel de debug definido en el archivo web.xml o en su defecto, en el archivo web.properties.
     */
    
    static public int getDebugLevel() {return debug;}

    public Properties getWebProperies()
    {
        return SWBPortal.getWebProperties();
    }

    /** Obtiene valor de variable de ambiente declarada en web.xml o web.properties.
     * @param name String nombre de la variable
     * @return
     */
    public static String getEnv(String name)
    {
        return getEnv(name, null);
    }

    /** Obtiene valor de variable de ambiente declarada en web.xml o web.properties.
     * @param name String nombre de la variable
     * @param defect String valor por defecto
     * @return
     */
    public static String getEnv(String name, String defect)
    {
        return  SWBPortal.getEnv(name, defect);
    }

    /** Getter for property workPath.
     * @return Value of property appPath.
     */
    public String getAppPath()
    {
        return  SWBUtils.getApplicationPath();
    }

    /** Writes a message to the log file.
     * @param msg  */
    public static void log(String msg)
    {
        log.debug(msg);
    }

    /** Writes a message to the log file.
     * @param msg
     * @param debug  */
    public static void log(String msg, boolean debug)
    {
        log.debug(msg);
    }

    /** Writes a message with an Exception to the log file.
     * @param e
     * @param msg  */
    public static void log(Throwable e, String msg)
    {
        log.error(msg,e);
    }

    /** Writes a message with an Exception to the log file.
     * @param e
     **/
    public static void log(Throwable e)
    {
        log.error(e);
    }


    /** Writes a message with an Exception to the log file.
     * @param e
     * @param msg
     * @param debug  */
    public static void log(Throwable e, String msg, boolean debug)
    {
        log.error(msg,e);
    }

    /**
     * Regresa un objeto printwriter conteniendo el archivo de log declarado.
     */
    public static PrintWriter getLog()
    {
        return null;
    }

    /**
     * Obtiene la instancia de la clase DBConnectionManager, con la cual podr� crear  y cerrar conexiones del pool.
     */
    public static DBConnectionManager getDBConnectionManager()
    {
        return DBConnectionManager.getInstance();
    }

    /**
     * Crea un directorio con el nombre de ruta especificada
     */
    public static boolean createDirectory(String path)
    {
        return SWBUtils.IO.createDirectory(path);
    }

    /**
     * Elimina directorios completos
     * method that removes complete directories
     * @param path Path to remove
     * @return if the directory was removed
     */
    public static boolean removeDirectory(String path)
    {
        return SWBUtils.IO.removeDirectory(path);
    }

    /**
     * Copia una estructura de directorios completa
     * Copy a complete fyle system path
     * @param source path to copy
     * @param target path where the fyle system in the source parameter will be copied
     * @return if the source was copied
     */    
    public boolean copyStructure(String source,String target) {
        try{
            copy(source,target,false,"","");
        }catch(Exception e){AFUtils.getInstance().log(e,"Error al copiar archivo",true);return false;}
        return true;
    }
    
    /**
     * Copia una estructura de directorios completa
     * Copy a complete fyle system path
     * @param source path to copy
     * @param target path where the fyle system in the source parameter will be copied
     * @param ChangePath indicates if the target files will be parsed
     * @param sourcePath Indicates a path string in the source files to be changed (parsed)
     * @param targetPath Indicates the path string to be included in the target files in place of source path
     * @return if the source directory was copied succefully
     */    
     public boolean copyStructure(String source,String target,boolean ChangePath,String sourcePath,String targetPath) {
        return SWBUtils.IO.copyStructure(source, target, ChangePath, sourcePath, targetPath);
    }
    
     /**
      * Copia un archivo a otro directorio
      * Copy a fyle to an other directory
      * @param source_name File to copy
      * @param dest_name path where the fyle will be copied
      * @param ChangePath indicates if the target files will be parsed
      * @param sourcePath Indicates a path string in the source files to be changed (parsed)
      * @param targetPath Indicates the path string to be included in the target files in place of source path
      */     
    public static void copy(String source_name, String dest_name,boolean ChangePath,String sourcePath,String targetPath) throws IOException 
    {
        SWBUtils.IO.copy(source_name, dest_name, ChangePath, sourcePath, targetPath);
    }
    
    /** Return a enumeration of DBConnectionPool
     * @return Return a enumeration of DBConnectionPool
    */    
    public static Enumeration getDBPools()
    {
        //TODO:
        return null;
    }


    /** Getter for Connection form DBPool.
     * @return Connection from DBPool.
     * @param name  */
    public static Connection getNoPoolDBConnection(String name)
    {
        return SWBUtils.DB.getNoPoolConnection(name);
    }

    /** Getter for Connection form DBPool.
     * @return Connection from DBPool.
     * @param name  */
    public static Connection getDBConnection(String name, String description)
    {
        return SWBUtils.DB.getConnection(name, description);
    }

    /** Getter for Connection form DBPool.
     * @return Connection from DBPool.
     * @param name  */
    public static Connection getDBConnection(String name)
    {
        return SWBUtils.DB.getConnection(name);
    }

    /** Nombre de base de datos.
     * DataBase name
     *  @return String nombre de la base de datos.
     */
    public static String getDBName(String nameconn)
    {
        return SWBUtils.DB.getDatabaseName(nameconn);
    }

    public void destroy()
    {
        try
        {
            mailsender.stop();
        } catch (Exception e)
        {
        }
        instance=null;
    }

    public void refresh()
    {
        try
        {
            debug = Integer.parseInt(getEnv("af/debug", "0"));
        } catch (Exception e)
        {
        }
    }
    
    /**
     * Elimina los nodos hijos del nodo enviado por par�metro que sean de tipo Texto pero que est�n nulos.
     * Removes the childs of node that is sent by parameter that are of text type but are nulls.
     */
    public static void validateNullNode(Node node)
    {
        NodeList nodeL = node.getChildNodes();
        for (int x = 0; x < nodeL.getLength(); x++)
        {
            Node nodeP = nodeL.item(x);
            if(nodeP.getNodeType()==nodeP.TEXT_NODE)
            {
                if(nodeP.getNodeValue()==null)
                {
                    node.removeChild(nodeP);
                }
            }else if(nodeP.hasChildNodes())
            {
                validateNullNode(nodeP);
            }
        }
    }

    /**
     *Crea un objeto String a partir de un objeto Document con cierta codificaci�n especificada y 
     * teniendo la posibilidad de identar la salida, la identaci�n que se tiene especificada en el m�todo es 2.
     * @param dom
     * @param encode
     * @param ident
     * @return  */
    public String DomtoXml(Document dom, String encode, boolean ident)
    {
        return SWBUtils.XML.domToXml(dom, encode, ident);
    }

    /**
     * Crea un objeto String a partir de un objeto Document con codificaci�n UTF-8 y sin identaci�n.
     * @param dom
     * @return  */
    public String DomtoXml(Document dom)
    {
        return DomtoXml(dom, "UTF-8", false);
    }

    /**
     * Crea un objeto String a partir de un objeto Document con codificaci�n UTF-8 y teniendo la posibilidad de
     * identar la salida, la identaci�n que se tiene especificada en el m�todo es 2.
     * @param dom
     * @param ident
     * @return  */
    public String DomtoXml(Document dom, boolean ident)
    {
        return DomtoXml(dom, "UTF-8", ident);
    }

    /**
     * Crea una copia exacta de un objeto Document
     * Creates an exactly copy of Document object
     * @param dom
     * @throws org.w3c.dom.DOMException
     * @throws com.infotec.appfw.exception.AFException
     * @return  */
    public Document copyDom(Document dom) throws DOMException, AFException
    {
        try
        {
            return SWBUtils.XML.copyDom(dom);
        }catch(SWBException e)
        {
            throw new AFException(e.getMessage(),"copyDom",e);
        }
    }

    /**
     * Crea un objeto Document a partir de un objeto String.
     * Creates a document object in base of String object
     * @param xml
     * @return  */
    public Document XmltoDom(String xml)
    {
        return SWBUtils.XML.xmlToDom(xml);
    }
    
    /**
     * Crea un objeto Document a partir de un objeto InputStream.
     * Creates a document object in base of InputStream object
     * @param xml
     * @return  */
    public Document XmltoDom(InputStream xml)
    {
        return SWBUtils.XML.xmlToDom(xml);
    }

    /**
     * Crea un objeto Document a partir de un objeto InputSource.
     * Creates a document object in base of InputSource object
     * @param xml
     * @return  */
    public Document XmltoDom(InputSource xml)
    {
        return SWBUtils.XML.xmlToDom(xml);
    }

    /**
     * Crea un nuevo objeto Document.
     * Creates a new object document
     * @throws com.infotec.appfw.exception.AFException
     * @return  */
    public Document getNewDocument() throws AFException
    {
        return SWBUtils.XML.getNewDocument();
    }

    /**
     * Parsea el contenido de un determinado URI como un documento xml y lo regresa como un objeto Document.
     * @param xml
     * @return  */
    public Document XmlFiletoDom(String xml)
    {
        return XmltoDom(xml);
    }

    /**
     * Obtiene el contenido del objeto Document como xml y 
     * lo env�a a un archivo especificado (serializaci�n) bajo cierta codificaci�n que se especifique e identaci�n de 2.
     * @param dom
     * @param file
     * @param encode  */
    public void DomtoFile(Document dom, String file, String encode)
    {
        SWBUtils.XML.domToFile(dom, file, encode);
    }

    /**
     * Obtiene el contenido del objeto Document como xml y 
     * lo env�a a un archivo especificado (serializaci�n) con codificaci�n UTF-8 e identaci�n de 2.
     * @param dom
     * @param file  */
    public void DomtoFile(Document dom, String file)
    {
        DomtoFile(dom, file, "UTF-8");
    }


    /**
     * Le pone a un objeto String el tipo de codificaci�n especificado por par�metro.
     * @param data
     * @param enc
     * @throws java.io.UnsupportedEncodingException
     * @throws java.io.IOException
     * @return  */
    public static String encode(String data, String enc) throws java.io.UnsupportedEncodingException, java.io.IOException
    {
        return SWBUtils.TEXT.encode(data, enc);
    }

    /**
     * Decodifica un objeto String poni�ndole cierta codificaci�n.
     * @param data
     * @param enc
     * @throws java.io.UnsupportedEncodingException
     * @throws java.io.IOException
     * @return  */
    public static String decode(String data, String enc) throws java.io.UnsupportedEncodingException, java.io.IOException
    {
        return SWBUtils.TEXT.decode(data, enc);
    }

    /**
     * Env�a correos electr�nicos, como par�metros recibe el o los correos de destino, 
     * el asunto y el cuerpo del correo., estos correos con enviados desde el correo que 
     * se encuentre especificado en el archivo web.xml o en su defecto en el archivo web.properties (recomendado) 
     * bajo la propiedad �af/adminEmail�, y con prioridad 0.
     * Sent the email message.
     * @param email Email address to send the message.
     * @param subject Subject of the email message.
     * @param description Text of the email message (body).
     * @return A true value if the email message was succesfully sent.
     */
    public static boolean sendEmail(String email, String subject, String description)
    {
        return AFUtils.getInstance().sendEmail((String) AFUtils.getInstance().getEnv("af/adminEmail"), email, null, null, subject, null, 0, description);
    }

    /**
     * Envia un correo en BackGround, es decir no espera a enviar el correo.
     * Sent the email in background, it doesn�t wait to send the message.
     * @param email Email address to send the message
     * @param subject Subject of the email message
     * @param description Text of the email message 
     */
    public static void sendBGEmail(String email, String subject, String description)
    {
        AFUtils.getInstance().sendBGEmail((String) AFUtils.getInstance().getEnv("af/adminEmail"), email, null, null, subject, null, 0, description);
    }

    /**
     * Env�a correos electr�nicos, como par�metros recibe el correo electr�nico de quien lo env�a, 
     * el o los correos de destino, el o los correos a los cuales se les desea enviar copia, 
     * el o los correos a los cuales se les desean enviar el correo con bcc (blind carbon copy), el asunto, 
     * el content-type en el que se enviara el correo, n�mero de prioridad  y el cuerpo del correo.
     * * Sent the email message.
     * @param fromEmail Sender Email address
     * @param toEmail Email address to send the message
     * @param ccEmail CC email addresses to send the message
     * @param bccEmail BCCemail addresses to send the message
     * @param subject Subject of the email message
     * @param contentType ContentType of the email message
     * @param priority Priprity of the message (number)
     * @param data Text of the email message
     * @return A true value if the message was succesfully sent.
     */
    public static boolean sendEmail(String fromEmail, String toEmail, String ccEmail, String bccEmail,
                                    String subject, String contentType, int priority, String data)
    {
        String host=getEnv("swb/smtpServer");
        String user=getEnv("swb/smtpUser");
        String password=getEnv("swb/smtpPassword");
        
        if(user!=null && password!=null)
        {
            return sendSEmail(fromEmail,toEmail,ccEmail,bccEmail,subject,contentType,priority,data);
        }
        
        try
        {
            sun.net.smtp.SmtpClient sm = new sun.net.smtp.SmtpClient(host);
            sm.from(fromEmail);
            sm.to(toEmail);
            if (ccEmail != null) sm.to(ccEmail);
            if (bccEmail != null) sm.to(bccEmail);
            java.io.PrintStream msg = sm.startMessage();
            msg.println("From: " + fromEmail);
            msg.println("To: " + toEmail);	   // Note dont use + for Performance
            if (ccEmail != null) msg.println("CC: " + ccEmail);
            //if(bccEmail!=null)msg.println("BCC: "+bccEmail);
            msg.println("Subject: " + subject);
            if (priority > 0) msg.println("X-Priority: " + priority);
            if (contentType != null) msg.println("Content-Type: " + contentType);
            //msg.println("X-MSMail-Priority: high");
            msg.println();
            msg.println(data);
            msg.println();
            sm.closeServer();
            return true;
        } catch (Exception e)
        {
            log(AFUtils.getLocaleString("locale_wb2_util", "error_WBUtils_nosendmail") + " " + e.getMessage(), true);
        }
        return false;
    }
    
    
    private static boolean sendSEmail(String fromEmail, String toEmail, String ccEmail, String bccEmail,
                                    String subject, String contentType, int priority, String data)
    {
        try
        {
            String host=getEnv("wb/smtpServer");
            String user=getEnv("wb/smtpUser");
            String password=getEnv("wb/smtpPassword");

            Properties prop = new Properties();
            prop.put("mail.host", host);
            prop.put("mail.user", user);
            prop.put("mail.password", password);
            prop.put("mail.smtp.auth", "true");
            
            javax.mail.Session session1 = javax.mail.Session.getDefaultInstance(prop, null);
            
            // Create new message
            javax.mail.internet.MimeMessage msg = new javax.mail.internet.MimeMessage(session1);
            
            if (subject != null)
                msg.setSubject(subject);
            msg.setFrom(new javax.mail.internet.InternetAddress(fromEmail));
            if(toEmail!= null)
                msg.addRecipient(javax.mail.Message.RecipientType.TO, new javax.mail.internet.InternetAddress(toEmail));
            if(ccEmail!= null)
                msg.addRecipient(javax.mail.Message.RecipientType.CC, new javax.mail.internet.InternetAddress(ccEmail));
            if(bccEmail!=null)
                msg.addRecipient(javax.mail.Message.RecipientType.BCC, new javax.mail.internet.InternetAddress(bccEmail));
            if(contentType!=null)
                msg.setContent(data,contentType);
            else
                msg.setText(data);
            if (priority > 0) 
                msg.setHeader("X-Priority",""+priority);
            
            javax.mail.Session _mailSession = javax.mail.Session.getDefaultInstance(prop, null);
            
            // Send the message
            //Transport.send(msg);
            javax.mail.Transport tr = _mailSession.getTransport("smtp");
            tr.connect(host, user, password);
            msg.saveChanges();
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
            
            // Print a message acknowledging that the message
            // was sent
        }
        catch(Exception e)
        {
            log(e);
            return false;
        }
        return true;
    }
    
    
    
    /** Env�a un mensaje electr�nico en un objeto MailMessage.
     * Send a email message in a MailMessage object.
     *
     * Envio de correo ejemplo:
     * 
     *  MailMessage mm = new MailMessage();
     *  mm.setFrom(new EmailAddress(from));
     *  mm.setSubject("Prueba envio de documento");
     *  mm.addTo("email@webbuilder.com.mx");
     *  mm.addTo("another.email@webbuilder.com.mx");
     *  mm.addHtml("<html><body>Envio de docto html.</body></html>","Envio de docto html en formato de texto");
     * // Agregando documento al mensaje.
     *  mm.addAttachment(WBUtils.getInstance().getFileFromWorkPath2(filename),"500.html");
     *  AFUtils.sendEmail(mm);
     * 
     * @param message MailMessage object to send.
     * @return A true value if the email was succesfully sent.
     */
    
    public static boolean sendEmail(MailMessage message)
    {
        try
        {
            message.sendMessage((String) getEnv("wb/smtpServer"));
            return true;
        } catch (Exception e)
        {
            log(AFUtils.getLocaleString("locale_wb2_util", "error_WBUtils_nosendmail") + " " + e.getMessage(), true);
        }
        return false;
    }
    
    /** 
     * Env�a un mensaje electr�nico en un objeto MailMessagen background.
     * Send a email message in a MailMessage object in background.
     *
     * Envio de correo ejemplo:
     * 
     *  MailMessage mm = new MailMessage();
     *  mm.setFrom(new EmailAddress(from));
     *  mm.setSubject("Prueba envio de documento");
     *  mm.addTo("email@webbuilder.com.mx");
     *  mm.addTo("another.email@webbuilder.com.mx");
     *  mm.addHtml("<html><body>Envio de docto html.</body></html>","Envio de docto html en formato de texto");
     * // Agregando documento al mensaje.
     *  mm.addAttachment(WBUtils.getInstance().getFileFromWorkPath2(filename),"500.html");
     *  AFUtils.sendEmail(mm);
     *
     * @param message MailMessage object to send.
     */
    public static void sendBGEmail(MailMessage message)
    {
        getInstance().mailsender.addMessage(message);
    }    

    /**
     * Envia un correo en BackGround, es decir no espera a enviar el correo.
     * Sent the email in background, it doesn�t wait to send the message.
     * 
     * @param fromEmail Sender Email address
     * @param toEmail Email address to send the message
     * @param ccEmail CC email addresses to send the message
     * @param bccEmail BCCemail addresses to send the message
     * @param subject Subject of the email message
     * @param contentType ContentType of the email message
     * @param priority Priprity of the message (number)
     * @param data Text of the email message
     */
    public static void sendBGEmail(String fromEmail, String toEmail, String ccEmail, String bccEmail,
                                   String subject, String contentType, int priority, String data)
    {
        getInstance().mailsender.addEmail(new AFMailData(fromEmail, toEmail, ccEmail, bccEmail, subject, contentType, priority, data));
    }

    /**
     * Carga un objeto InputStream de un xslt para ser utilizado como plantilla.
     * @param stream
     * @throws javax.xml.transform.TransformerConfigurationException
     * @return  */
    public Templates loadTemplateXSLT(InputStream stream) throws TransformerConfigurationException
    {
        return SWBUtils.XML.loadTemplateXSLT(stream);
    }

    /**
     * Transforma un objeto Document con un Template (xslt) especificado, 
     * regresando un objeto String con dicha transformaci�n y listo para ser desplegado.
     * @param tpl
     * @param doc
     * @throws javax.xml.transform.TransformerException
     * @return  */
    public String transformDom(Templates tpl, Document doc) throws TransformerException
    {
        return SWBUtils.XML.transformDom(tpl, doc);
    }

    /**
     * Reemplaza caracteres especiales de tags en un xml
     * @param txt
     * @return  */
    static public String replaceXMLTags(String txt)
    {
        return SWBUtils.XML.replaceXMLTags(txt);
    }

    /**
     * Reemplaza caracteres especiales en un xml
     * @param str
     * @return  */
    static public String replaceXMLChars(String str)
    {
        return SWBUtils.XML.replaceXMLChars(str);

    }
    /**
     *Regresa arreglo de bytes resultante de leer un archive especificado como parametro.
     */
    public static byte[] readFile(File file)throws FileNotFoundException, IOException
    {
        return SWBUtils.IO.readFile(file);
    }
    
    /**
     * Regresa un objeto String resultante de un objeto InputStream
     */
    public String readInputStream(InputStream in)
    {
        try
        {
            return SWBUtils.IO.readInputStream(in);
        } catch (Exception e)
        {
            log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBUtils_IOInput"), true);
        }
        return null;
    }
    
    /**
     * Regresa un objeto String codificado resultante de un objeto InputStream 
     * enviado por par�metro y un tipo de codificaci�n tambi�n enviado.
     */
    public String readInputStream(InputStream inp, String enc) throws java.io.UnsupportedEncodingException, java.io.IOException
    {
        return SWBUtils.IO.readInputStream(inp, enc);
    }    
    
    /**
     *  Copia el InputStream al OutputStream y al final cierra los streams
     *
     */    
    public static void copyStream(InputStream in, OutputStream out) throws IOException
    {
        SWBUtils.IO.copyStream(in, out);
    }  
    
    /**
     *  Copia el InputStream al OutputStream y al final cierra los streams
     *
     */
    public static void copyStream(InputStream in, OutputStream out, int bufferSize) throws IOException
    {
        SWBUtils.IO.copyStream(in, out, bufferSize);
    }
    
    
    /**
     * Normaliza rutas, sustituyendo  el car�cter �\� por el car�cter �/� y eliminando rutas relativas.
     */
   public static String normalizePath(String path)
    {
        //System.out.println("normalize:"+path);
        if(path == null)
            return null;
        String normalized = path;
        if(normalized == null)
            return null;
        if(normalized.equals("/."))
            return "/";
        if(normalized.indexOf('\\') >= 0)
            normalized = normalized.replace('\\', '/');
        if(!normalized.startsWith("/") && normalized.indexOf(':')<0)
            normalized = "/" + normalized;
        do
        {
            int index = normalized.indexOf("//");
            if(index < 0)
                break;
            normalized = normalized.substring(0, index) + normalized.substring(index + 1);
        } while(true);
        do
        {
            int index = normalized.indexOf("/./");
            if(index < 0)
                break;
            normalized = normalized.substring(0, index) + normalized.substring(index + 2);
        } while(true);
        do
        {
            int index = normalized.indexOf("/../");
            if(index >= 0)
            {
                if(index == 0)
                    return null;
                int index2 = normalized.lastIndexOf('/', index - 1);
                normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
            } else
            {
                return normalized;
            }
        } while(true);
    }        
    
    /*
     * Obtiene un objeto InputStream dado un objeto String.
     */
    public InputStream getStreamFromString(String str)
    {
        return SWBUtils.IO.getStreamFromString(str);
    }
    
    /*
     *Regresa un objeto String conteniendo el nombre del n�mero de d�a en el lenguaje pasado por par�metro., 
     el n�mero de de d�a se env�a por par�metro y comienza con 0 (Domingo).
     */
    public String getStrDay(int day, String lang)
    {
        return SWBUtils.TEXT.getStrDay(day, lang);
    }
    
    /**
     * Regresa un objeto String conteniendo el nombre del n�mero de mes en el lenguaje pasado por par�metro., 
     * el n�mero de de mes se env�a por par�metro y comienza con 0 (Enero).
     */
    public String getStrMonth(int month, String lang)
    {
        return SWBUtils.TEXT.getStrMonth(month, lang);
    }

    /**
     * Da formato a una fecha y la regresa en el lenguaje deseado.
     */
    public String getStrDate(Date date, String lang)
    {
        return getStrDate(date, lang, null);
    }

    /**
     * Da formato a una fecha y la regresa en el lenguaje deseado.
     */
    public String getStrDate(Date date,String lang,String format)
    {
        return SWBUtils.TEXT.getStrDate(date, lang, format);
    }

    /**
     * Regresa el valor de una llave en un archivo de propiedades, 
     * con el lenguaje que se encuentre configurado en la propiedad de clase locale.
     */
    public static String getLocaleString(String Bundle, String key)
    {
        return getLocaleString(Bundle, key, locale);
    }

    /**
     * Regresa el valor de una llave en un archivo de propiedades, con el lenguaje que se envi� como par�metro en la variable locale.
     */
    public static String getLocaleString(String Bundle, String key, Locale locale)
    {
        return getLocaleString(Bundle, key, locale, null);
    }
    
    /**
     * Regresa el valor de una llave en un archivo de propiedades, con el lenguaje que se envi� como par�metro en la variable locale y
     * busc�ndolo en el ClassLoader que se envi� como par�metro en la variable loador.
     */
    public static String getLocaleString(String Bundle, String key, Locale locale, ClassLoader loader)
    {
        return SWBUtils.TEXT.getLocaleString(Bundle, key, locale, loader);
    }    

    /**
     * Regresa el lenguaje con el que se este trabajando en la clase (AFUtils).
     */
    public static Locale getLocale()
    {
        return locale;
    }

    /**
     * Debugea un mensaje con nivel DEBUG_WARNING.
     */
    public static void debug(String msg)
    {
        debug(msg, DEBUG_WARNING);
    }

    /**
     * Debugea un mensaje con nivel que se envi� como par�metro.
     **/
    public static void debug(String msg, int level)
    {
        if (debug >= level) log(msg, true);
    }
    
    /**
     * Debugea un mensaje con el nivel que se envi� como par�metro, si se cumple la condici�n.
     **/
    public static void debugIf(boolean condition, String msg, int level)
    {
        if (condition) debug(msg, level);
    }    
    
    /**
     * Debugea un mensaje con el nivel DEBUG_WARNING, si se cumple la condici�n.
     */
    public static void debugIf(boolean condition, String msg)
    {
        if (condition)debug(msg, DEBUG_WARNING);
    }     
    
    /**
     * Debugea un mensaje con el nivel que se envi� como par�metro, si no se cumple la condici�n.
     **/
    public static void debugAssert(boolean condition, String msg, int level)
    {
        if (!condition) debug(msg, level);
    }    
    
    /**
     * Debugea un mensaje con el nivel DEBUG_WARNING, si no se cumple la condici�n.
     */
    public static void debugAssert(boolean condition, String msg)
    {
        if (!condition)debug(msg, DEBUG_WARNING);
    }     
    
    
    /**
     * Convierte una cadena una convenci�n de nombrado con mayusculas, es decir, la primera letra con may�scula y
     * las siguientes seguidas de caracteres especiales como lo son un espacio, un punto, un gui�n, o un gui�n bajo.
     *  Ejemplo: esta.es.una_prueba-de mensaje
     *  Salida:Esta.Es.Una_Prueba-De Mensaje
    */
    public static String toUpperCaseFL(String str)
    {
        return SWBUtils.TEXT.toUpperCaseFL(str);
    }
    
    /**
     * Regresa un objeto Properties a partir de un nombre de archivo de propiedades enviado por parametro.
     */
    public static Properties getPropertyFile(String name)
    {
        return SWBUtils.TEXT.getPropertyFile(name);
    }
    
    /**
     * Copia los bytes de un archivo de un lugar a otro, ejemplo: para copiar una imagen, 
     * con la opci�n de agregarle una fecha de ultima modificaci�n al nuevo archivo creado.
     */
    public static boolean copyFileFromClassResource(String sourceurl,String targeturl)
    {
        return copyFileFromClassResource(sourceurl,targeturl,null);
    }
    
    /**
     * Copia los bytes de un archivo de un lugar a otro, ejemplo: para copiar una imagen, 
     * con la opci�n de agregarle una fecha de ultima modificaci�n al nuevo archivo creado.
     */
    public static boolean copyFileFromClassResource(String sourceurl ,String targeturl, java.util.Date lastModify)
    {
        boolean ret=false;
        InputStream isource=null;
        FileOutputStream dest=null;
        try {
            isource=AFUtils.getInstance().getClass().getResourceAsStream(sourceurl);
            byte[] bfile=new byte[bufferSize];
            byte[] buffer;
            int bytes_read;
            dest = new FileOutputStream(AFUtils.getInstance().getAppPath()+targeturl);
            buffer = new byte[bufferSize];
            while(true) {
                bytes_read = isource.read(buffer);
                if (bytes_read == -1) break;
                dest.write(buffer, 0, bytes_read);
            }
            ret=true;
        }catch(Exception e){ log(e);}
        finally {
            if (isource != null)
                try
                { isource.close(); } catch (IOException e)
                { ; }
            if (dest != null)
                try
                { dest.close(); } catch (IOException e)
                { ; }
        }
        try {
            if(lastModify!=null)
            {
                File nfile=new File(AFUtils.getInstance().getAppPath()+targeturl);
                if(nfile.exists())
                {
                    nfile.setLastModified(lastModify.getTime());
                }
            }
        }catch(Exception e){ log(e);}
        return ret;
    }    
    

    //version 1.3
    /**
     * Remplaza en una cadena (str) las coincidencias encontradas (match) con otra cadena (replace).
     */
    public static String replaceAll(String str, String match,String replace)
    {
        return SWBUtils.TEXT.replaceAll(str, match, replace);
    }
    
    /**
     *   Regresa iterador con los string que se encuentren estre las cadenas pre y pos
     *   Ejemplo:
     *   String str="Tag uno:{request.getParameter(\"param1\") tag dos:{request.getParameter(\"param2\")}";
     *   
     *   Iterator it=AFUtils.findInterStr(str, "{request.getParameter(\"","\")");
     *   while(it.hasNext())
     *   {
     *       System.out.println("param:("+it.next()+")");
     *   }
     * 
     */
    
    public static Iterator findInterStr(String str, String pre, String pos)
    {
        return SWBUtils.TEXT.findInterStr(str, pre, pos);
    }


    
    /**
     * Regresa un arraylist de strings que fueron delimitados por un delimitador (regexp)
     */
    //version 1.4
    public static ArrayList regExpSplit(String txt, String regexp)
    {
        return SWBUtils.TEXT.regExpSplit(txt, regexp);
    }  
    
    /**
     * Regresa iterador con los errores que se han enviado al log, 
     * este iterador tiene un cierto tama�o el cual como maximo puede ser el que tiene la variable de clase errorElementSize.
     */
    public static Iterator getErrorElements()
    {
        return SWBUtils.ERROR.getErrorElements();
    }
    
    /**
     * Getter for property errorElementSize.
     * @return Value of property errorElementSize.
     */
    public static int getErrorElementSize()
    {
        return SWBUtils.ERROR.getErrorElementSize();
    }    
    
    /**
     * Setter for property errorElementSize.
     * @param errorElementSize New value of property errorElementSize.
     */
    public static void setErrorElementSize(int errorElementSize)
    {
        SWBUtils.ERROR.setErrorElementSize(errorElementSize);
    }
    
    
    /** Asigna un atributo al DOM del recurso.
     * Si no existe el atributo, lo crea y si existe lo modifica
     * @param name String nombre del atributo
     * @param value String valor del atributo
     */
    public static void setDomAttribute(Document dom, String name, String value) 
    {
        SWBUtils.XML.setAttribute(dom, name, value);
    }
    


    /** Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa el valor por defecto defvalue.
     */
    public static String getDomAttribute(Document dom, String name, String defvalue)
    {
        return SWBUtils.XML.getAttribute(dom, name, defvalue);
    }


    /** Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa null.
     */
    public static String getDomAttribute(Document dom, String name)
    {
        return SWBUtils.XML.getAttribute(dom, name);
    }   
    
    /**
     *  Valida si el objeto es String, si es nulo, regresa una cadena vacia
     */
    public static String stringNullValidate(Object obj)
    {
        if(obj==null)return "";
        else return obj.toString();
    }
    
    /**
     * Getter for property bufferSize.
     * @return Value of property bufferSize.
     */
    public static int getBufferSize()
    {
        return bufferSize;
    }
    
    /**
     * Setter for property bufferSize.
     * @param bufferSize New value of property bufferSize.
     */
    public static void setBufferSize(int buffSize)
    {
        bufferSize = buffSize;
    }
    
    
    /**
     * Optiene parametros de un url
     * Regresa Map con parametros 
     *  Keys: Strings
     *  Vals: Strings [] 
     */
    public static Map parseQueryParams(String path)
    {
        return SWBUtils.TEXT.parseQueryParams(path);
    }    
    
    /**
     *  Reemplaza caracteres acentuados y espacios en blanco.
     *  caracteres adicionales son eliminados.
     */
    public static String replaceSpecialCharacters(String txt, boolean replaceSpaces)
    {
        return SWBUtils.TEXT.replaceSpecialCharacters(txt, replaceSpaces);
    }
    
    /**
     *  Deserializa y decodifica una objeto (de String Hexadecimal a objeto)
     */
    public static Object decodeObject(String txt) throws IOException, ClassNotFoundException
    {
        return SWBUtils.IO.decodeObject(txt);
    }
    
    /**
     *  Serializa y codifica una objeto a Hexadecimal
     */
    public static String encodeObject(Object obj) throws IOException
    {
        return SWBUtils.IO.encodeObject(obj);
    }    
    
    static class DOM
    {
        static public Element appendChild(Element ele, String name)
        {
            Document doc=ele.getOwnerDocument();
            Element e=doc.createElement(name);
            ele.appendChild(e);
            return e;
        }
        
        static public Element appendChild(Element ele, String name, String value)
        {
            Document doc=ele.getOwnerDocument();
            Element e=doc.createElement(name);
            e.appendChild(doc.createTextNode(value));
            ele.appendChild(e);
            return e;
        }
    }

}