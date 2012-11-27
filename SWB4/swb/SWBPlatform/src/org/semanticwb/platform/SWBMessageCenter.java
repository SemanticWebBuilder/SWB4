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
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.platform;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBObserver;
//import org.semanticwb.platform.SWBMessageProcesor;


// TODO: Auto-generated Javadoc
/**
 * Objeto: Se encarga de la recepcion y envio de mensajes UDP, para la sincronizacion de servidores.
 * Object: One is in charge of the reception and shipment of messages UDP, for the synchronization of servers.
 * @author Javier Solis Gonzalez
 */
public class SWBMessageCenter
{
    
    /** The log. */
    public static Logger log = SWBUtils.getLogger(SWBMessageCenter.class);

    //private WeakHashMap observers=new WeakHashMap();
    /** The observers. */
    private HashMap observers = new HashMap();

    /** The sa. */
    private boolean sa = true;                        //standalon

    /** The server. */
    private SWBMessageServer server = null;
    
//    /** The procesor. */
//    private SWBMessageProcesor procesor = null;

    /** The sock. */
    private DatagramSocket sock = null;
    
    /** The packets. */
    private ArrayList packets=new ArrayList();
    //private DatagramPacket packet = null;
    /** The addr. */
    private InetAddress addr=null;

    /** The messages. */
    private LinkedList messages = null;

    /** The df. */
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /** The localaddr. */
    private String localaddr = "127.0.0.1";
    
    /** The Timer Sync **/
    private Timer timer = new Timer("MessageSynchronizer", true);
    
    /** The Period **/
    private long period = 1000 * 60 * 5;
    
    /** The Message **/
    private String synchMess = null;


    /**
     * Creates a new instance of SWBMessageCenter.
     */
    public SWBMessageCenter()
    {
        log.event("Initializing SWBMessageCenter...");
        messages = new LinkedList();
    }

    /**
     * Destroy.
     */
    public void destroy()
    {
        log.event("Destroy SWBMessageCenter...");
        if (sock != null) sock.close();
        if (server != null) server.stop();
    }

    /**
     * Inits the.
     */
    public void init()
    {
        try
        {
            String confCS = SWBPlatform.getEnv("swb/clientServer");
            if (!confCS.equalsIgnoreCase("SASC")) sa = false;

            //System.out.println("before procesor start...");

//            procesor = new SWBMessageProcesor(this);
//            procesor.init();

            //System.out.println("after procesor start...");
            
            
            if (!sa)
            {
                String localAddr=SWBPlatform.getEnv("swb/localMessageAddress"); 
                String serverAddr=SWBPlatform.getEnv("swb/serverMessageAddress");
                
                if(localaddr!=null && serverAddr!=null) //Nueva version
                {
                    int i=localAddr.lastIndexOf(":"); //MAPS74 Ajuste para IPV6
                    String ipaddr=localAddr.substring(0, i);
                    int port=Integer.parseInt(localAddr.substring(i+1)); //System.out.println("ipadd1:"+ipaddr+" "+port);
                    
                    i=serverAddr.lastIndexOf(":"); //MAPS74 Ajuste para IPV6
                    String sipaddr=serverAddr.substring(0, i);
                    int sport=Integer.parseInt(serverAddr.substring(i+1));//System.out.println("ipadd2:"+sipaddr+" "+sport);
                    
                    InetAddress saddr=null;
                    try
                    {
                        if (ipaddr.equalsIgnoreCase("localhost"))
                            addr = InetAddress.getLocalHost();
                        else
                            addr = InetAddress.getByName(ipaddr);
                        
                        if (sipaddr.equalsIgnoreCase("localhost"))
                            saddr = InetAddress.getLocalHost();
                        else
                            saddr = InetAddress.getByName(sipaddr);
                        
                    } catch (Exception e)
                    {
                        log.error("SWBMessage Server IP Error:",e);
                    }                   
                    
                    //System.out.println("addr:"+addr+" "+port);
                    //System.out.println("saddr:"+saddr+" "+sport);
                    
                    addAddress(addr, port);
                    addAddress(saddr, sport);                                        
                    
                    String message = "ini|hel|"+addr.getHostAddress()+":"+port;
                    
                    synchMess = "syn|hel|"+addr.getHostAddress()+":"+port;
                    
                    
                    server = new SWBMessageServer(this,addr,port);
                    server.start();               
                                        
                    sendMessage(message);
                    timer.schedule(new TimerTask(){

                        @Override
                        public void run() {
                            sendMessage(synchMess);
                        }
                    
                    }, period, period);
                    
                }else  //Version Enterior de Registro de Mensajes
                {
                    String message = "ini|MessageServer Iniciado...";
                    byte[] data = message.getBytes();

                    //System.out.println("before server start...");

                    server = new SWBMessageServer(this);
                    server.start();

                    //System.out.println("after server start...");


                    try
                    {
                        String ipaddr = SWBPlatform.getEnv("swb/MessageIPAddr");
                        if (ipaddr.equalsIgnoreCase("localhost"))
                            addr = InetAddress.getLocalHost();
                        else
                            addr = InetAddress.getByName(ipaddr);
                    } catch (Exception e)
                    {
                        log.error("SWBMessage Server IP Error:",e);
                    }

                    //get send address
                    int port=Integer.parseInt(SWBPlatform.getEnv("swb/sendMessagePort"));
                    String sendAddr=SWBPlatform.getEnv("swb/sendMessageIPAddrs");
                    if(sendAddr==null)
                    {
                        String ip = addr.getHostAddress();
                        InetAddress saddr = InetAddress.getByName(ip.substring(0, ip.lastIndexOf('.')) + ".255");
                        log.info("BroadCast Addr:"+saddr+":"+port);
                        packets.add(new DatagramPacket(data, data.length, saddr, port));
                    }else
                    {
                        DatagramPacket packet=null;
                        String ip=null;
                        int aport;
                        boolean fp=false;
                        StringTokenizer st=new StringTokenizer(sendAddr,":,;",true);
                        while(st.hasMoreTokens())
                        {
                            String aux=st.nextToken();
                            try
                            {
                                if(aux.equals(":"))
                                {
                                    fp=true;
                                }else if(aux.equals(",") || aux.equals(";"))
                                {
                                    fp=false;
                                }else if(aux.trim().length()>0)
                                {
                                    if(fp)
                                    {
                                        aport=Integer.parseInt(aux.trim());
                                        packet.setPort(aport);
                                    }else
                                    {
                                        InetAddress saddr = InetAddress.getByName(aux.trim());
                                        packet=new DatagramPacket(data, data.length, saddr, port);
                                        packets.add(packet);
                                    }
                                }
                            }catch(Exception e){log.error(e);}
                        }
                        Iterator it=packets.iterator();
                        while(it.hasNext())
                        {
                            DatagramPacket apacket=(DatagramPacket)it.next();
                            log.info("Send Address "+apacket.getAddress()+":"+apacket.getPort());
                        }                    
                    }

                    /*
                    if (addr != null)
                    {
                        DatagramSocket aux = new DatagramSocket();   //optener una puerto de salida valido...
                        int x = aux.getLocalPort();
                        aux.close();
                        sock = new DatagramSocket(x, addr);
                    } else
                        sock = new DatagramSocket();
                    sock.send(packet);
                    */
                    sendMessage(message);
                }
            }
        } catch (Exception e)
        {
            log.error("SWBMessageCenter Init Error...",e);
        }
    }
    

    /**
     * Refresh.
     */
    public void refresh()
    {
    }

    /**
     * Send message.
     * 
     * @param message the message
     */
    public void sendMessage(String message)
    {
        if (!sa && packets.size()>0)
        {
            //System.out.println("SendMessage: "+message +" to "+packet.getAddress().getHostAddress());
            try
            {
                if (sock != null)
                {
                    byte[] data = message.getBytes();
                    Iterator it=packets.iterator();
                    while(it.hasNext())
                    {
                        DatagramPacket refPacket=(DatagramPacket)it.next();
                        DatagramPacket packet=new DatagramPacket(data, data.length, refPacket.getAddress(), refPacket.getPort());
                        //packet.setData(data, 0, data.length);
                        sock.send(packet);
                    }
                } else
                {
                    if (addr != null)
                    {
                        DatagramSocket aux = new DatagramSocket();   //optener una puerto de salida valido...
                        int x = aux.getLocalPort();
                        aux.close();
                        sock = new DatagramSocket(x, addr);
                    } else
                    {
                        sock = new DatagramSocket();                    
                    }
                    byte[] data = message.getBytes();

                    Iterator it=packets.iterator();
                    while(it.hasNext())
                    {
                        DatagramPacket refPacket=(DatagramPacket)it.next();
                        DatagramPacket packet=new DatagramPacket(data, data.length, refPacket.getAddress(), refPacket.getPort());
                        //packet.setData(data, 0, data.length);
                        sock.send(packet);
                    }
                }
            } catch (IOException e)
            {
                log.error("SWBMessageCenter SendMessage Error:" + message, e);
            }
        } else
        {
            incomingMessage(message, localaddr);
        }
    }

    /**
     * Incoming message.
     * 
     * @param message the message
     * @param addr the addr
     */
    public void incomingMessage(String message, String addr)
    {
        //Date dt = new Date(); death storage
        StringBuilder logbuf = new StringBuilder(message.length() + 20);
        logbuf.append(message.substring(0, 4));
        logbuf.append(df.format(new Date()));
        logbuf.append(message.substring(3));
        pushMessage(logbuf.toString());
        log.debug("Message from " + addr + ":(" + message+")");
    }

    /**
     * Push message.
     * 
     * @param message the message
     */
    public void pushMessage(String message)
    {
        synchronized(messages)
        {
            messages.addFirst(message);
        }
    }

    /**
     * Pop message.
     * 
     * @return the string
     * @return
     */
    public String popMessage()
    {
        try
        {
            synchronized(messages)
            {
                return (String) messages.removeLast();
            }
        } catch (Exception e)
        {
            synchronized(messages)
            {
                messages.clear();
            }
            log.error("SWBMessageCenter Pop Message Error...", e);
        }
        return "";
    }

    /**
     * registra el objeto observador para que pueda recibir notoficaciones de cambios.
     * 
     * @param key the key
     * @param obs the obs
     */
    public synchronized void registerObserver(String key, SWBObserver obs)
    {
        observers.put(key, obs);
    }

    /**
     * Removes the observer.
     * 
     * @param key the key
     */
    public synchronized void removeObserver(String key)
    {
        observers.remove(key);
    }

    /**
     * Gets the observers.
     * 
     * @return the observers
     */
    public Iterator getObservers()
    {
        return new ArrayList(observers.values()).iterator();
    }

    /**
     * Gets the observer.
     * 
     * @param key the key
     * @return the observer
     */
    public SWBObserver getObserver(String key)
    {
        return (SWBObserver) observers.get(key);
    }

    /**
     * Checks for messages.
     * 
     * @return true, if successful
     * @return
     */
    public boolean hasMessages()
    {
        return !messages.isEmpty();
    }

    /**
     * Message size.
     * 
     * @return the int
     */
    public int messageSize()
    {
        return messages.size();
    }

    /**
     * Gets the address.
     * 
     * @return the address
     * @return
     */
    public String getAddress()
    {
        if (sock != null)
            return sock.getLocalAddress().getHostAddress();
        else
            return localaddr;
    }

//    /**
//     * Gets the message procesor.
//     * 
//     * @return the message procesor
//     */
//    public SWBMessageProcesor getMessageProcesor()
//    {
//        return procesor;
//    }

    /**
     * Gets the message server.
     * 
     * @return the message server
     */
    public SWBMessageServer getMessageServer()
    {
        return server;
    }
    
    public synchronized boolean addAddress(InetAddress addr, int port)
    {  
        //System.out.println("addAddress:"+addr+" "+port);
        Iterator<DatagramPacket> it=packets.iterator();
        
        boolean contains=false;
        while (it.hasNext())
        {
            DatagramPacket datagramPacket = it.next();
            if(datagramPacket.getAddress().equals(addr) && datagramPacket.getPort()==port)
            {
                contains=true;
            }
            //System.out.println("--Addr:"+datagramPacket.getAddress().getHostAddress()+" "+datagramPacket.getPort());
        }
        
        if(!contains)
        {
            byte[] data = "".getBytes();
            packets.add(new DatagramPacket(data, data.length, addr, port));
            //System.out.println("--Addr:"+addr.getHostAddress()+" "+port);
        }    
        return !contains;
    }
    
    public String getListAddress()
    {
        StringBuffer ret=new StringBuffer();
        Iterator<DatagramPacket> it=packets.iterator();
        while (it.hasNext())
        {
            DatagramPacket datagramPacket = it.next();
            ret.append(datagramPacket.getAddress().getHostAddress());
            ret.append(":");
            ret.append(datagramPacket.getPort());
            if(it.hasNext())ret.append("|");
        }   
        return ret.toString();
    }

}
