/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.base.util;

import java.util.*;

import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;

/**
 * objecto: encargado de procesar y enviar correos electronicos.
 * @author jei
 */
public class SWBMailSender extends java.lang.Thread
{

    private static Logger log=SWBUtils.getLogger(SWBMailSender.class);
    
    LinkedList emails = null;


    /** Creates a new instance of WBMessageServer
     * @param center
     * @throws java.net.SocketException  */
    public SWBMailSender() throws java.net.SocketException
    {
        emails = new LinkedList();
    }

    @Override
    public void run()
    {
        
        while (true)
        {
            try
            {
                this.currentThread().sleep(20000);
            }catch(Exception e){log.error(e);}
            try
            {
                while (!emails.isEmpty())
                {
                    Object obj=emails.removeLast();
                    try
                    {
                        if(obj instanceof SWBMail)
                        {
                            SWBMail email = (SWBMail) obj;
                            SWBUtils.EMAIL.setSMTPServer(email.getHostName());
                            SWBUtils.EMAIL.sendMail(email.getFromEmail(), email.getFromName(), email.getAddresses(), email.getCcEmail(), email.getBccEmail(),email.getSubject(), email.getContentType(), email.getData(), email.getLogin(), email.getPassword(), email.getAttachments());
                        }
                    } catch (Exception e)
                    {
                        log.error(e);
                    }
                }
            } catch (Exception e)
            {
                log.error(e);
            }
        }
    }

    public void addEMail(SWBMail email)
    {
        emails.addFirst(email);
    }
}
