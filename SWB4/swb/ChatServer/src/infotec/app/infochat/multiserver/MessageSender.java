/*
 * MessageSender.java
 *
 * Created on 12 de mayo de 2003, 16:06
 */
package infotec.app.infochat.multiserver;

import java.io.*;

import java.lang.*;

import java.net.*;

import java.util.*;


/**
 *
 * @author  Administrador
 */
public class MessageSender extends Thread
{
    private ChatServer server;
    private boolean running = false;
    private HashMap logs = new HashMap();
    
    /** Creates a new instance of MessageSender */
    public MessageSender(ChatServer server)
    {
        this.server = server;
    }
    
    private void sendMsg(Message msg)
    {
        if (msg.community != null)
        {
            if (((HashMap) server.communi.get(msg.community)).values() != null)
            {
                ArrayList snd = new ArrayList(((HashMap) server.communi.get(
                msg.community)).values());
                Iterator it = snd.iterator();
                
                while (it.hasNext())
                {
                    SConn scon = (SConn) it.next();
                    
                    if (scon != null)
                    {
                        try
                        {
                            scon.sendMsg(msg.message);
                        } catch (Exception e)
                        {
                        }
                    }
                }
            }
        }
    }
    
    public void logMsg(Message msg)
    {
        try
        {
            ArrayList args = new ArrayList();
            StringTokenizer st = new StringTokenizer(msg.message, "|");
            
            while (st.hasMoreTokens())
            {
                args.add(st.nextToken());
            }
            
            if (args.get(0).equals("@@@CH"))
            {
                log(args.get(1) + ": " + args.get(3), msg.community);
            } else if (args.get(0).equals("@@@CN"))
            {
                log("Enter User: " + args.get(1), msg.community);
            } else if (args.get(0).equals("@@@DCN"))
            {
                log("Exit User: " + args.get(1), msg.community);
            }
            
            //System.out.println("cmu:"+msg.community+"msg:"+msg.message);
        } catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
    }
    
    public void log(String msg, String community)
    {
        try
        {
            PrintWriter log = (PrintWriter) logs.get(community);
            
            if (log == null)
            {
                log = new PrintWriter(new FileWriter(server.logPath + "/" +
                community + ".log", true), true);
                logs.put(community, log);
            }
            
            log.println("" + new Date() + ": " + msg);
            
            //System.out.println(""+new Date()+": "+msg);
        } catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
    }
    
    public void run()
    {
        //System.out.println("MessageSender iniciado...");
        try
        {
            while (running)
            {
                while (!server.msgs.isEmpty())
                {
                    Message msg = (Message) server.msgs.get(0);
                    
                    //System.out.println("msg:"+msg);
                    sendMsg(msg);
                    logMsg(msg);
                    server.msgs.remove(0);
                }
                
                this.sleep(100);
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
        
        //System.out.println("MessageSender finalizado...");
    }
    
    public void start()
    {
        running = true;
        super.start();
    }
}
