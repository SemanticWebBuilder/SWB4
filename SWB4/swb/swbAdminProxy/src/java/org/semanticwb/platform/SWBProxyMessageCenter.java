/*
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
 * http://www.semanticwebbuilder.org
 */
package org.semanticwb.platform;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.servlet.SWBProxyAdminFilter;

/**
 *
 * @author serch
 */
public class SWBProxyMessageCenter {

    static private Logger log = SWBUtils.getLogger(SWBProxyMessageCenter.class);
    private HashMap<String, DatagramPacket> localNetwork = new HashMap();
    private HashMap<String, DatagramPacket> remoteNetwork = new HashMap();
    private InetAddress myAddr = null;
    private int myPort = 0;
    private InetAddress srvrAddr = null;
    private int srvrPort = 0;
    private DatagramSocket sock = null;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DelayQueue<DelayedMessage> upStream = new DelayQueue();
    private SWBProxyThreadProcessor upStreamThread;
    private DelayQueue<DelayedMessage> downStream = new DelayQueue();
    private SWBProxyThreadProcessor downStreamThread;
    private SWBMessageServer server;
    private Timer timer = new Timer("MessageSynchronizer", true);
    private long period = 1000 * 60 * 5;
    private String synchMess = null;


    public SWBProxyMessageCenter() {
        init();
    }
    
    private void init(){
        log.event("Initializing SWBProxyMessageCenter...");
        String localAddr = SWBProxyAdminFilter.getEnv("swb/localMessageAddress");
        String serverAddr = SWBProxyAdminFilter.getEnv("swb/serverMessageAddress");

        if (localAddr != null && serverAddr != null) //Nueva version
        {
            int i = localAddr.lastIndexOf(":"); //MAPS74 Ajuste para IPV6
            String ipaddr = localAddr.substring(0, i);
            myPort = Integer.parseInt(localAddr.substring(i + 1)); //System.out.println("ipadd1:"+ipaddr+" "+port);

            i = serverAddr.lastIndexOf(":"); //MAPS74 Ajuste para IPV6
            String sipaddr = serverAddr.substring(0, i);
            srvrPort = Integer.parseInt(serverAddr.substring(i + 1));//System.out.println("ipadd2:"+sipaddr+" "+sport);

            //InetAddress saddr = null;
            try {
                if (ipaddr.equalsIgnoreCase("localhost")) {
                    myAddr = InetAddress.getLocalHost();
                } else {
                    myAddr = InetAddress.getByName(ipaddr);
                }

                if (sipaddr.equalsIgnoreCase("localhost")) {
                    srvrAddr = InetAddress.getLocalHost();
                } else {
                    srvrAddr = InetAddress.getByName(sipaddr);
                }
                addLocalAddress(myAddr, myPort);
                addRemoteAddress(srvrAddr, srvrPort);
                addRemoteAddress(myAddr, myPort);
                server = new SWBMessageServer(this, myAddr, myPort);
                server.start();
                DatagramSocket aux = new DatagramSocket();   //optener una puerto de salida valido...
                int x = aux.getLocalPort();
                aux.close();
                sock = new DatagramSocket(x, myAddr);
                String message = "ini|hel|"+myAddr.getHostAddress()+":"+myPort;
                synchMess = "syn|hel|"+myAddr.getHostAddress()+":"+myPort;
                sendMessage(message, remoteNetwork);
                timer.schedule(new TimerTask(){

                    @Override
                    public void run() {
                        try {
                            sendMessage(synchMess, remoteNetwork);
                        } catch (IOException ioe){
                            log.error("Synchronizing to Server:"+synchMess,ioe);
                        }
                    }

                }, period, period);
                upStreamThread = new SWBProxyThreadProcessor(upStream, this, remoteNetwork);
                (new Thread(upStreamThread, "upStream Thread")).start();
                downStreamThread = new SWBProxyThreadProcessor(downStream, this, localNetwork);
                (new Thread(downStreamThread, "downStream Thread")).start();
            } catch (Exception e) {
                log.error("SWBMessage Server IP Error:", e);
            }
        }
        log.event("SWBProxyMessageCenter Started...");
    }
    
    public synchronized boolean addLocalAddress(InetAddress addr, int port)
    {  
        log.debug("addLocalAddress:"+addr+" "+port);
        String key = addr.getHostAddress()+":"+port;

        boolean contains = localNetwork.containsKey(key);
        if(!contains)
        {
            byte[] data = "".getBytes();
            localNetwork.put(key, new DatagramPacket(data, data.length, addr, port));
            log.debug("--LAddr:"+key);
        }    
        return !contains;
    }
    
    public synchronized boolean addRemoteAddress(InetAddress addr, int port)
    {  
        log.debug("addRemoteAddress:"+addr+" "+port);
        String key = addr.getHostAddress()+":"+port;

        boolean contains = remoteNetwork.containsKey(key);
        if(!contains)
        {
            byte[] data = "".getBytes();
            remoteNetwork.put(key, new DatagramPacket(data, data.length, addr, port));
            log.debug("--RAddr:"+key);
        }    
        return !contains;
    }

    void incomingMessage(String message, String addr) {
        StringBuilder logbuf = new StringBuilder(message.length() + 20);
        logbuf.append(message.substring(0, 4));
        logbuf.append(df.format(new Date()));
        logbuf.append(message.substring(3));
        if (localNetwork.containsKey(addr)){
            upStream.add(new DelayedMessage(message, 5, TimeUnit.MINUTES));
        } else if (remoteNetwork.containsKey(addr)){
            downStream.add(new DelayedMessage(message, 5, TimeUnit.MINUTES));
        }
        log.debug("Message from " + addr + ":(" + message+")");
    }
    
    public void sendMessage(final String message, final HashMap<String, DatagramPacket> network) throws IOException{
        byte[] data = message.getBytes();
        for (DatagramPacket refPacket:network.values()){
            DatagramPacket packet=new DatagramPacket(data, data.length, refPacket.getAddress(), refPacket.getPort());
            sock.send(packet);
        }
    }

    public Collection<String> localAddresses(){
        return localNetwork.keySet();
    }
    
    public Collection<String> remoteAddresses(){
        return remoteNetwork.keySet();
    }
    
    public void destroy() {
        timer.cancel();
        server.setFlag(false);
        upStreamThread.setFlag(false);
        downStreamThread.setFlag(false);
    }
    
}
