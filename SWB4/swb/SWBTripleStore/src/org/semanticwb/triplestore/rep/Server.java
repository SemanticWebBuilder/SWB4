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
package org.semanticwb.triplestore.rep;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author javier.solis.g
 */
public class Server extends Thread
{
    private int port = 9494;
    private boolean running = false;
    private ServerSocket sserv = null;
    
    /** Creates a new instance of ChatServer */
    public Server()
    {
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
        System.out.println("server run");                
        try
        {
            sserv = new ServerSocket(port);
            
            while (running)
            {
                //System.out.println("running");
                Socket sock = sserv.accept();
                try
                {
                    ServerConnection conn = new ServerConnection(sock, this);
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
        System.out.println("server end");                        
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

      
        
}
