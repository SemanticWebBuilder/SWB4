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

import java.io.*;

import java.net.*;


/**
 *
 * @author  Administrador
 */
public class ClientConnection extends Thread
{
    private Socket sock = null;
    private String graph = null;
    private Client client = null;
    private boolean running = false;
    private PrintWriter writer=null;
    
    
    /** Creates a new instance of SConn */
    public ClientConnection(String addr, int port, String graph, Client client) throws IOException
    {
        this.graph = graph;
        this.sock = new Socket(addr, port);
        this.client = client;
    }
    
    public void accion(String acc)
    {
            try
            {
                System.out.println("scc:"+acc);

            } catch (Exception e)
            {
                e.printStackTrace();
            }            
    }
    
    public void run()
    {
        System.out.println("Client connection run");
        try
        {
            InputStream oin = sock.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(oin));
            byte[] buff = new byte[8192];
            
            while (running)
            {
                String aux=null;
                try
                {
                    aux=in.readLine();
                }catch(IOException e){}
                if (aux!=null)
                {
                    accion(aux);
                } else
                {
                    running = false;                    
                    break;
                }
            }             
        } catch (Exception e)
        {
            e.printStackTrace();
        }             
        running=false;
        System.out.println("Client connection end");        
    }
    
    public void start()
    {
        System.out.println("Client connection start...");
        running = true;
        super.start();
    }
        
    public boolean sendMessage(String msg) 
    {
        System.out.println("Client sendMessage:"+msg);
        if(running)
        {
            try
            {
                if(writer==null)
                {
                    try
                    {
                        //writer=new OutputStreamWriter(sock.getOutputStream(),Charset.forName("utf-8"));
                        writer=new PrintWriter(sock.getOutputStream(),false);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                writer.println(msg);
                writer.flush();        
                
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return false;
    }    
    
    public void close()
    {
        try
        {
            running=false;
            writer.flush();
            writer.close();
            sock.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
        
}
