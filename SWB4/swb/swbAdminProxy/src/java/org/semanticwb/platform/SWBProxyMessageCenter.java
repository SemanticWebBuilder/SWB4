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
import java.util.StringTokenizer;
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
    private InetAddress rmtAddr = null;
    private int rmtPort = 0;
    private DatagramSocket sock = null;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DelayQueue<DelayedMessage> upStream = new DelayQueue();
    private SWBProxyThreadProcessor upStreamThread;
    private DelayQueue<DelayedMessage> downStream = new DelayQueue();
    private SWBProxyThreadProcessor downStreamThread;
    private SWBMessageServer lserver;
    private SWBMessageServer rserver;
    private Timer timer = new Timer("MessageSynchronizer", true);
    private long period = 1000 * 60 * 5;
    private String synchMess = null;
    private int delay=5;
    private TimeUnit delayUnit=TimeUnit.MINUTES;


    public SWBProxyMessageCenter() {
        init();
    }
    
    private void init(){
        log.event("Initializing SWBProxyMessageCenter...");
        String sdelay = SWBProxyAdminFilter.getEnv("swb/delayPeriod");
        log.event("found swb/delayPeriod:"+sdelay);
        String sUnit = SWBProxyAdminFilter.getEnv("swb/delayUnit");
        log.event("found swb/delayUnit:"+sUnit);
        String localAddr = SWBProxyAdminFilter.getEnv("swb/localMessageAddress");
        log.event("found swb/localMessageAddress:"+localAddr);
        String serverAddr = SWBProxyAdminFilter.getEnv("swb/remoteServerMessageAddress");
        log.event("found swb/remoteServerMessageAddress:"+serverAddr);
        String remoteAddr = SWBProxyAdminFilter.getEnv("swb/remoteMessageAddress");
        log.event("found swb/remoteMessageAddress:"+remoteAddr);
        try {
            delay = Integer.parseInt(sdelay);
            delayUnit = TimeUnit.valueOf(sUnit);
        } catch (Exception e){
            log.error(e);
        }
        log.event("Delay configured to "+delay+ " "+delayUnit);
        if (localAddr != null && serverAddr != null && remoteAddr != null) //Nueva version
        {
            int i = localAddr.lastIndexOf(":"); //MAPS74 Ajuste para IPV6
            String ipaddr = localAddr.substring(0, i);
            myPort = Integer.parseInt(localAddr.substring(i + 1)); //System.out.println("ipadd1:"+ipaddr+" "+port);
            
            i = serverAddr.lastIndexOf(":"); //MAPS74 Ajuste para IPV6
            String sipaddr = serverAddr.substring(0, i);
            srvrPort = Integer.parseInt(serverAddr.substring(i + 1));//System.out.println("ipadd2:"+sipaddr+" "+sport);

            i = remoteAddr.lastIndexOf(":"); //MAPS74 Ajuste para IPV6
            String rmtaddr = remoteAddr.substring(0, i);
            rmtPort = Integer.parseInt(remoteAddr.substring(i + 1));//System.out.println("ipadd2:"+sipaddr+" "+sport);
            
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
                
                if (rmtaddr.equalsIgnoreCase("localhost")) {
                    rmtAddr = InetAddress.getLocalHost();
                } else {
                    rmtAddr = InetAddress.getByName(rmtaddr);
                }
                
                log.trace("localAddres: "+myAddr.toString()+":"+myPort);
                log.trace("serverAddres: "+srvrAddr.toString()+":"+srvrPort);
                log.trace("remoteAddres: "+rmtAddr.toString()+":"+rmtPort);
//                addLocalAddress(myAddr, myPort);
                addRemoteAddress(srvrAddr, srvrPort);
//                addRemoteAddress(rmtAddr, rmtPort);
                lserver = new SWBMessageServer(this, myAddr, myPort, SWBMessageServer.FromNetwork.LOCAL);
                log.trace("lServer: "+lserver);
                lserver.start();
                rserver = new SWBMessageServer(this, rmtAddr, rmtPort, SWBMessageServer.FromNetwork.REMOTE);
                log.trace("rServer: "+rserver);
                rserver.start();
                DatagramSocket aux = new DatagramSocket();   //optener una puerto de salida valido...
                int x = aux.getLocalPort();
                aux.close();
                sock = new DatagramSocket(x, rmtAddr);
                String message = "ini|hel|"+rmtAddr.getHostAddress()+":"+rmtPort;
                synchMess = "syn|hel|"+rmtAddr.getHostAddress()+":"+rmtPort;
                try {
                    sendMessage(message, remoteNetwork);
                } catch (IOException ioe){
                    log.error("Synchronizing to Server:"+message,ioe);
                }
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
        log.debug("addLocalAddress:"+addr+":"+port);
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

    void incomingMessage(String message, String addr, SWBMessageServer.FromNetwork from) {
        TimeUnit unit = delayUnit;
        if (!(message.startsWith("ini")||message.startsWith("syn"))){
            if (message.startsWith("log")||message.startsWith("hit")||message.startsWith("ses")||message.startsWith("lgn")) {
                unit = TimeUnit.MILLISECONDS;
            }
            StringBuilder logbuf = new StringBuilder(message.length() + 20);
            logbuf.append(message.substring(0, 4));
            logbuf.append(df.format(new Date()));
            logbuf.append(message.substring(3));
            DelayedMessage dm = new DelayedMessage(message, delay, unit);
            log.trace("Adding Message: ("+dm.getMessage()+") delay:"+dm.getDelay(TimeUnit.MILLISECONDS)+" unit:"+unit);
            if (SWBMessageServer.FromNetwork.LOCAL.equals(from)){
                upStream.add(dm);
            } else if (SWBMessageServer.FromNetwork.REMOTE.equals(from)){
                downStream.add(dm);
            }
        }
        else {
            int i = message.indexOf('|');
            String ini = message.substring(0, i);
            if(ini.equals("ini") || ini.equals("syn"))
                    {
                        try {
                            StringTokenizer st=new StringTokenizer(message, "|");
                            String init=st.nextToken();
                            //String time=st.nextToken();
                            String aux=st.nextToken();
                            //log.trace("init:"+init);
                            //log.trace("time:"+time);
                            //log.trace("aux:"+aux);
                            if(aux.equals("hel"))
                            {
                                String maddr=st.nextToken();
                                log.info("Registering Message Client:"+maddr+" to "+from);

                                //System.out.println("Registering Message Client:"+addr);

                                int j=maddr.lastIndexOf(":"); //MAPS74 IPV6
                                if (SWBMessageServer.FromNetwork.LOCAL.equals(from)){
                                    addLocalAddress(InetAddress.getByName(maddr.substring(0,j)),Integer.parseInt(maddr.substring(j+1)));
                                } else if (SWBMessageServer.FromNetwork.REMOTE.equals(from)){
                                    addRemoteAddress(InetAddress.getByName(maddr.substring(0,j)),Integer.parseInt(maddr.substring(j+1)));
                                }
    //                            if(!SWBPortal.isClient())
    //                            {
    //                                //System.out.println("Server...");
    //                                center.sendMessage("ini|upd|"+center.getListAddress());
    //                            }
                            }
    //                        else if(aux.equals("upd"))
    //                        {
    //                            if(SWBPortal.isClient())
    //                            {
    //                                while(st.hasMoreTokens())
    //                                {
    //                                    String maddr=st.nextToken();
    //                                    int j=maddr.lastIndexOf(":"); //MAPS74 IPV6
    //                                    center.addAddress(InetAddress.getByName(maddr.substring(0,j)),Integer.parseInt(maddr.substring(j+1)));
    //                                }
    //                                
    //                            }
    //                        }
                        } catch (Exception mex) {
                            log.error("SWBProxyMessageCenter: incommingMessage",mex);
                        }
                    }
        }
        
        //log.trace("Message received from " + addr + ":(" + message+") "+from);
    }
    
    public void sendMessage(final String message, final HashMap<String, DatagramPacket> network) throws IOException{
        log.trace("Sending: ("+message+")");
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
        lserver.setFlag(false);
        rserver.setFlag(false);
        upStreamThread.setFlag(false);
        downStreamThread.setFlag(false);
    }
    
}
