/*
 * SConn.java
 *
 * Created on 12 de mayo de 2003, 13:15
 */
package infotec.app.infochat.server;

import java.io.*;

import java.lang.*;

import java.net.*;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.*;


/**
 *
 * @author  Administrador
 */
public class SConn extends Thread
{
    public String room = "defa";
    public Socket sock = null;
    public ChatServer server = null;
    public boolean running = false;
    public String name = null;
    private String password = null;
    public String community = null;
    
    /** Creates a new instance of SConn */
    public SConn(Socket sock, ChatServer server)
    {
        this.sock = sock;
        this.server = server;
    }
    
    public void accion(String acc)
    {
        //System.out.println(acc);
        try
        {
            StringTokenizer st = new StringTokenizer(acc, "|");
            String comm = st.nextToken();
            
            if (comm.equals("@@@CN"))
            {
                //inicializar cliente
                if (name == null)
                {
                    name = st.nextToken();
                    password = st.nextToken();
                    community = st.nextToken();
                    
                    if (server.communi.get(community) == null)
                    {
                        server.communi.put(community, new HashMap());
                    }
                    
                    HashMap conns = (HashMap) server.communi.get(community);
                    
                    while (conns.containsKey(name))
                    {
                        name += (int) (Math.random() * 9.0);
                    }
                    
                    String aux = "CHNGCN|" + name;
                    sendMsg(aux); //envio de confirmacion de nombre
                    
                    String users = server.getUsers(community);
                    
                    if (users.length() > 0)
                    {
                        users += ("|" + name);
                    } else
                    {
                        users = name;
                    }
                    
                    conns.put(name, this);
                    server.sendMessage("@@@CN|" + name, community); //envio de alta de usuario
                    server.sendMessage("@@@ACTROOM|" + users, community); //envio de usuarios del room
                } else
                {
                }
            } else if (comm.equals("@@@CH"))
            {
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String t = sdf.format(d);
                server.sendMessage(acc + "|" + t + "|", community); //envio de comentario
            } else if (comm.equals("@@@CHMP"))
            {
                String userfrom = st.nextToken();
                String userto = st.nextToken();
                SConn con = (SConn) ((HashMap) server.communi.get(community)).get(userto);
                sendMsg(acc);
                con.sendMsg(acc);
            } else
            {
                server.sendMessage(acc, community); //envio de comentario
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public synchronized void sendMsg(String msg) throws IOException
    {
        String aux = msg + "\r";
        sock.getOutputStream().write(aux.getBytes());
        
        //System.out.print(name+"-->"+aux);
    }
    
    public void run()
    {
        try
        {
            InputStream in = sock.getInputStream();
            byte[] buff = new byte[8192];
            String aux = "";
            
            while (running)
            {
                int r = -1;
                
                if ((r = in.read(buff)) > 0)
                {
                    aux += new String(buff, 0, r);
                    
                    if (aux.lastIndexOf("CCSS") >= 0)
                    {
                        accion(aux.substring(0, aux.length() - 4));
                        aux = "";
                    }
                } else if (r == -1)
                {
                    running = false;
                    
                    break;
                }
                
                this.sleep(100);
            }
            
            ((HashMap) server.communi.get(community)).remove(name);
            server.sendMessage("@@@DCN|" + name, community);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        //System.out.println("out:"+name);
    }
    
    public void start()
    {
        running = true;
        super.start();
    }
}
