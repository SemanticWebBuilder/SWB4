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

package org.semanticwb.portal;

import java.net.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/** Objeto: Servidos de mensajes UDP de sincronizacion de hits.
 * @author Javier Solis Gonzalez
 * @version 1.0
 */
public class SWBMessageServer extends java.lang.Thread
{
    public static Logger log = SWBUtils.getLogger(SWBMessageServer.class);

    private static final int PKSIZE = 1024;

    private DatagramSocket s = null;
    private DatagramPacket packet = null;
    private SWBMessageCenter center = null;

    /**
     * @param center
     * @throws java.net.SocketException  */
    public SWBMessageServer(SWBMessageCenter center) throws java.net.SocketException
    {
        this.center = center;
        InetAddress addr = null;
        int port = 1500;
        try
        {
            port = Integer.parseInt((String) SWBPlatform.getEnv("swb/reciveMessagePort"));
            String ipaddr = (String) SWBPlatform.getEnv("swb/MessageIPAddr");
            if (!ipaddr.equalsIgnoreCase("localhost"))
                addr = InetAddress.getByName(ipaddr);
        } catch (Exception e)
        {
            log.error(e);
        }
        if (addr != null)
            s = new DatagramSocket(port, addr);
        else
            s = new DatagramSocket(port);
        log.event("Message Serrver en:"+"\t"+ s.getLocalAddress().getHostAddress() + ":" + port);
    }

    public void run()
    {
        log.info("Message Server Running...");
        DatagramPacket packet = new DatagramPacket(new byte[PKSIZE], PKSIZE);
        while (true)
        {
            try
            {
                //DatagramPacket packet = new DatagramPacket(new byte [PKSIZE], PKSIZE);
                s.receive(packet);
                String msg = new String(packet.getData(), 0, packet.getLength());
                log.debug("UDP Msg:"+msg+" ");
                center.incomingMessage(msg, packet.getAddress().getHostAddress());
            } catch (Exception e)
            {
                log.error(e);
            }
        }
    }

}
