/*
 * ChatServer.java
 *
 * Created on 12 de mayo de 2003, 12:10
 */
package infotec.app.infochat.multiserver;

import com.infotec.wb.core.WBMessageCenter;

import java.lang.*;

import java.net.*;

import java.util.*;


/**
 *
 * @author  Administrador
 */
public class ChatServer extends Thread
{
    private int port = 5000;
    private boolean running = false;
    ServerSocket sserv = null;
    HashMap communi = new HashMap();
    ArrayList msgs = new ArrayList();
    MessageSender sender = null;
    ChatProcesor procesor = null;
    public String logPath = "";
    
    /** Creates a new instance of ChatServer */
    public ChatServer()
    {
        sender = new MessageSender(this);
        procesor = new ChatProcesor(this);
        WBMessageCenter.getInstance().registerObserver("cht", procesor);
    }
    
    public int getPort()
    {
        return port;
    }
    
    public void setPort(int port)
    {
        this.port = port;
    }
    
    public void run()
    {
        sender.start();
        
        try
        {
            sserv = new ServerSocket(port);
            
            while (running)
            {
                Socket sock = sserv.accept();
                SConn conn = new SConn(sock, this);
                conn.start();
                
                //System.out.println("accept:"+sock);
                //this.sleep(1000);
            }
        } catch (Exception e)
        {
        }
        
        sender.stop();
    }
    
    public void start()
    {
        running = true;
        super.start();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        ChatServer server = new ChatServer();
        server.setPort(9494);
        server.start();
    }
    
    public void sendMessage(String msg, String community)
    {
        msgs.add(msgs.size(), new Message(msg, community));
        
        //System.out.println(msg);
    }
    
    public String getUsers(String community)
    {
        String ret = "";
        boolean first = true;
        
        ArrayList snd = new ArrayList(((HashMap) communi.get(community)).values());
        Iterator it = snd.iterator();
        
        while (it.hasNext())
        {
            SConn scon = (SConn) it.next();
            
            if (first)
            {
                first = false;
            } else
            {
                ret += "|";
            }
            
            ret += scon.name;
        }
        
        return ret;
    }
}
