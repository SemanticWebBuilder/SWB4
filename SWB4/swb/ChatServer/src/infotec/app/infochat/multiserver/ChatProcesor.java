/*
 * ChatProcesor.java
 *
 * Created on 2 de junio de 2003, 02:22 PM
 */
package infotec.app.infochat.multiserver;
import com.infotec.appfw.lib.AFObserver;
import com.infotec.wb.core.WBMessageCenter;
import java.io.*;
import java.net.*;
import java.util.*;


/**
 *
 * @author  Administrador
 */
public class ChatProcesor implements AFObserver
{
    ChatServer server = null;
    
    /** Creates a new instance of ChatProcesor */
    public ChatProcesor(ChatServer server)
    {
        this.server = server;
        
        // debe traer el lista de los demás usuarios en otros chats de la misma red
        java.util.Date d = new java.util.Date();
        System.out.println(d.toString() + ": Iniciando Chat Procesor...");
    }
    
    private void AgregaUsuario(String name, String comm)
    {
        //String comunidad=this.server.communi.get(comm);
    }
    
    private void AgregarUsuario(String linea)
    {
        StringTokenizer st = new StringTokenizer(linea, "|");
        String communidad = st.nextToken();
        
        while (st.hasMoreElements())
        {
            String name = (String) st.nextToken();
            
            if (name != null)
            {
                AgregaUsuario(name, communidad);
            }
        }
    }
    
    public void sendDBNotify(String str, Object obj)
    {
        // se manda un mensaje de actualización
        //str=chat
        // Object=String=
        if (str.equals("cht"))
        {
            StringTokenizer st = new StringTokenizer((String) obj, "|");
            String type = (String) st.nextToken();
            
            if (type.equals("cht"))
            {
                String fecha = (String) st.nextToken();
                String ipmsg = (String) st.nextToken();
                String portmsg = (String) st.nextToken();
                String comm = (String) st.nextToken();
                
                try
                {
                    InetAddress ip = java.net.InetAddress.getLocalHost();
                    
                    if (!ip.getHostAddress().startsWith("127."))
                    {
                        if (!ip.getHostAddress().equals(ipmsg))
                        {
                            String port = "" + server.getPort();
                            
                            if (portmsg.equals(port))
                            {
                                //ChatServer server=WBAppChatServer.getInstance().getServer();
                                SConn scon = new SConn(new Socket(), server);
                                String msg = "";
                                
                                while (st.hasMoreElements())
                                {
                                    msg += ((String) st.nextToken() + "|");
                                }
                                
                                if (msg.endsWith("|"))
                                {
                                    msg = msg.substring(0, msg.length() - 1);
                                }
                                
                                StringTokenizer st2 = new StringTokenizer(msg,
                                "|");
                                String cmd = (String) st2.nextToken();
                                
                                //System.out.println("Mensaje recibido por el ChatProcesor: "+ msg +"");
                                if (cmd.equals("@@@DCN")) // eliminación del usuario
                                {
                                    String name = (String) st2.nextToken();
                                    ((HashMap) server.communi.get(comm)).remove(name);
                                    
                                    //System.out.println("Usuario Eliminado: "+ name +"");
                                    String users = server.getUsers(comm);
                                    server.sendMessage("@@@DCN|" + name, comm);
                                } else if (cmd.equals("@@@CN")) //alta de usuario
                                {
                                    scon.accion(msg, false);
                                } else // mensaje
                                {
                                    //agrega el usuario si no existe
                                    scon.setComunity(comm);
                                    
                                    String name = (String) st2.nextToken();
                                    scon.setNameUser(name);
                                    
                                    if (server.communi.get(comm) == null)
                                    {
                                        server.communi.put(comm, new HashMap());
                                    }
                                    
                                    HashMap conns = (HashMap) server.communi.get(comm);
                                    
                                    if (!conns.containsKey(name))
                                    {
                                        String users = server.getUsers(comm);
                                        
                                        if (users.length() > 0)
                                        {
                                            users += ("|" + name);
                                        } else
                                        {
                                            users = name;
                                        }
                                        
                                        conns.put(name, scon);
                                    }
                                    
                                    scon.accion(msg, false);
                                }
                                
                                String users = server.getUsers(comm);
                                server.sendMessage("@@@ACTROOM|" + users, comm);
                                
                                //System.out.println("@@@ACTROOM|"+users);
                            }
                        }
                    }
                } catch (Exception e)
                {
                }
            }
        }
    }
}
