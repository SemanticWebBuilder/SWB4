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
 * WBMessageServer.java
 *
 * Created on 3 de octubre de 2002, 14:54
 */

package com.infotec.appfw.lib.mail;

import java.util.*;

import com.infotec.appfw.util.AFUtils;

/**
 * objecto: encargado de procesar y enviar correos electronicos.
 * @author Javier Solis Gonzalez
 */
public class AFMailSender extends java.lang.Thread
{

    LinkedList emails = null;


    /** Creates a new instance of WBMessageServer
     * @param center
     * @throws java.net.SocketException  */
    public AFMailSender() throws java.net.SocketException
    {
        emails = new LinkedList();
    }

    public void run()
    {
        //System.out.println("procesor running...");
        while (true)
        {
            try
            {
                this.currentThread().sleep(20000);
            }catch(Exception e){AFUtils.log(e);}
            try
            {
                while (!emails.isEmpty())
                {
                    Object obj=emails.removeLast();
                    try
                    {
                        if(obj instanceof AFMailData)
                        {
                            AFMailData email = (AFMailData) obj;
                            //AFUtils.getInstance().sendEmail(email.getToEmail(),email.getSubject(),email.getDescription());
                            AFUtils.getInstance().sendEmail(email.getFromEmail(), email.getToEmail(), email.getCcEmail(), email.getBccEmail(), email.getSubject(), email.getContentType(), email.getPriority(), email.getData());
                        }else if(obj instanceof MailMessage)
                        {
                            AFUtils.getInstance().sendEmail((MailMessage)obj);
                        }
                        this.currentThread().sleep(10);
                    } catch (Exception e)
                    {
                        AFUtils.log(e, AFUtils.getLocaleString("locale_wb2_lib", "error_WBMailSender_error"), true);
                    }
                }
            } catch (Exception e)
            {
                AFUtils.log(e, AFUtils.getLocaleString("locale_wb2_lib", "error_WBMailSender_error"), true);
            }
        }
    }

    public void addEmail(com.infotec.appfw.lib.mail.AFMailData data)
    {
        emails.addFirst(data);
    }
    
    public void addMessage(com.infotec.appfw.lib.mail.MailMessage message)
    {
        emails.addFirst(message);
    }    

}

