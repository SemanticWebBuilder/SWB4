
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
