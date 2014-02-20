/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.triplestore.rep;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author javier.solis.g
 */
public class Client
{
    private int port = 9494;
    private boolean running = false;
    private Socket sclient = null;    

    public Client()
    {
    }
    
    public ClientConnection getConnection(String graph)
    {
        ClientConnection con=null;
        try
        {
            con=new ClientConnection("localhost", port, graph, this);
            con.start();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return con;
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Client c=new Client();
        ClientConnection con=c.getConnection("ts1");
        for(int x=0;x<1000;x++)
        {
            con.sendMessage("Hola esta es una prueba..."+x);
        }
        con.close();
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public int getPort()
    {
        return port;
    }
}
