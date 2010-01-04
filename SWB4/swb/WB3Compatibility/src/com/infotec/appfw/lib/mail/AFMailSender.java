/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al público (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


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

