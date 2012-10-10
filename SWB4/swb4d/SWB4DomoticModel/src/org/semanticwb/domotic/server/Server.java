/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.domotic.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.SWBContext;

/**
 *
 * @author javier.solis.g
 */
public class Server extends Thread
{
    private int port = 9494;
    private boolean running = false;
    private ServerSocket sserv = null;
    private SWBModel model;
    
    /** Creates a new instance of ChatServer */
    public Server()
    {
        this.model=SWBContext.getWebSite("dom");
    }
    
    public Server(SWBModel model)
    {
        this.model=model;
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
        try
        {
            sserv = new ServerSocket(port);
            
            while (running)
            {
                //System.out.println("running");
                Socket sock = sserv.accept();
                try
                {
                    Connection conn = new Connection(sock, this);
                    conn.start();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                //System.out.println("accept:"+sock);
                //this.sleep(1000);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
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
        int port=9494;
        if(args.length>0)
        {
            try
            {
                port=Integer.parseInt(args[0]);
            }catch(NumberFormatException e){}
        }
        Server server = new Server();
        server.setPort(port);
        server.start();
    }

    public SWBModel getModel()
    {
        return model;
    }

    public void setModel(SWBModel model)
    {
        this.model = model;
    }        
        
}
