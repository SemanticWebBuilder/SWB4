/*
 * SConn.java
 *
 * Created on 12 de mayo de 2003, 13:15
 */
package infotec.app.infochat.multiserver;

import com.infotec.wb.core.WBMessageCenter;

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
    
    public void RegisterUserServers(String acc)
    {
        if (!acc.startsWith("cht"))
        {
            if ((community != null) && (name != null))
            {
                try
                {
                    InetAddress ip = java.net.InetAddress.getLocalHost();
                    InetAddress[] ips = InetAddress.getAllByName("localhost");
                    
                    if (!ip.getHostAddress().startsWith("127."))
                    {
                        StringTokenizer st = new StringTokenizer(acc, "|");
                        String newaccion = (String) st.nextToken() + "|" +
                        this.name;
                        st.nextToken();
                        
                        while (st.hasMoreElements())
                        {
                            newaccion += ("|" + (String) st.nextToken());
                        }
                        
                        String msg = "cht|" + ip.getHostAddress() + "|" +
                        server.getPort() + "|" + this.community + "|" +
                        newaccion;
                        WBMessageCenter.getInstance().sendMessage(msg);
                    }
                } catch (Exception e)
                {
                }
            }
        }
    }
    
    public void setComunity(String comm)
    {
        this.community = comm;
    }
    
    public void accion(String acc, boolean reenviar)
    {
        //System.out.println(acc);
        String oldacc = acc;
        
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
            
            if (reenviar)
            {
                RegisterUserServers(oldacc);
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    public synchronized void sendMsg(String msg) throws IOException
    {
        String aux = msg + "\r";
        
        try
        {
            sock.getOutputStream().write(aux.getBytes());
        } catch (Exception ue)
        {
            //System.out.print("Error: "+name+" "+ue.getMessage());
        }
        
        //System.out.print(name+"-->"+aux);
    }
    
    public void setNameUser(String name)
    {
        this.name = name;
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
                        accion(aux.substring(0, aux.length() - 4), true);
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
            RegisterUserServers("@@@DCN|" + name);
        } catch (Exception e)
        {
            System.out.println(e);
        }
        
        //System.out.println("out:"+name);
    }
    
    public void start()
    {
        running = true;
        super.start();
    }
}
