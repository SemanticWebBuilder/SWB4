
/*
 * SWBMessageCenter.java
 *
 * Created on 3 de octubre de 2002, 14:26
 */

package org.semanticwb.portal;

import java.util.*;
import java.io.*;

import java.net.*;
import java.text.SimpleDateFormat;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBObserver;


/**
 * Objeto: Se encarga de la recepcion y envio de mensajes UDP, para la sincronizacion de servidores.
 * Object: One is in charge of the reception and shipment of messages UDP, for the synchronization of servers.
 * @author Javier Solis Gonzalez
 */
public class SWBMessageCenter
{
    public static Logger log = SWBUtils.getLogger(SWBMessageCenter.class);

    //private WeakHashMap observers=new WeakHashMap();
    private HashMap observers = new HashMap();

    private boolean sa = true;                        //standalon

    static private SWBMessageCenter instance;       // The single instance
    private SWBMessageServer server = null;
    private SWBMessageProcesor procesor = null;

    private DatagramSocket sock = null;
    private ArrayList packets=new ArrayList();
    //private DatagramPacket packet = null;
    private InetAddress addr=null;

    private LinkedList messages = null;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String localaddr = "127.0.0.1";


    /** Creates a new instance of SWBMessageCenter */
    public SWBMessageCenter()
    {
        log.event("Initializing SWBMessageCenter");
        messages = new LinkedList();
    }

    /**
     * @return  */
    static public SWBMessageCenter getInstance()
    {
        if (instance == null)
        {
            instance = new SWBMessageCenter();
            instance.init();
        }
        return instance;
    }

    public void destroy()
    {
        instance=null;
        log.event("Destroy SWBMessageCenter...");
        if (sock != null) sock.close();
        if (server != null) server.stop();
    }

    public void init()
    {
        try
        {
            String confCS = SWBPlatform.getEnv("swb/clientServer");
            if (!confCS.equalsIgnoreCase("SASC")) sa = false;

            //System.out.println("before procesor start...");

            procesor = new SWBMessageProcesor(this);
            procesor.init();

            //System.out.println("after procesor start...");

            if (!sa)
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
        } catch (Exception e)
        {
            log.error("SWBMessageCenter Init Error...",e);
        }
    }
    

    public void refresh()
    {
    }

    /**
     * @param message  */
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
     * @param message
     * @param addr  */
    public void incomingMessage(String message, String addr)
    {
        Date dt = new Date();
        StringBuffer logbuf = new StringBuffer(message.length() + 20);
        logbuf.append(message.substring(0, 4));
        logbuf.append(df.format(new Date()));
        logbuf.append(message.substring(3));
        pushMessage(logbuf.toString());
        log.debug("Message from " + addr + ":(" + message+")");
    }

    /**
     * @param message  */
    public void pushMessage(String message)
    {
        synchronized(messages)
        {
            messages.addFirst(message);
        }
    }

    /**
     * @return  */
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

    /** registra el objeto observador para que pueda recibir notoficaciones de cambios
     * @param obs   */
    public synchronized void registerObserver(String key, SWBObserver obs)
    {
        observers.put(key, obs);
    }

    public synchronized void removeObserver(String key)
    {
        observers.remove(key);
    }

    public Iterator getObservers()
    {
        return new ArrayList(observers.values()).iterator();
    }

    public SWBObserver getObserver(String key)
    {
        return (SWBObserver) observers.get(key);
    }

    /**
     * @return  */
    public boolean hasMessages()
    {
        return !messages.isEmpty();
    }

    public int messageSize()
    {
        return messages.size();
    }

    /**
     * @return  */
    public String getAddress()
    {
        if (sock != null)
            return sock.getLocalAddress().getHostAddress();
        else
            return localaddr;
    }

    public SWBMessageProcesor getMessageProcesor()
    {
        return procesor;
    }

    public SWBMessageServer getMessageServer()
    {
        return server;
    }

}
