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
package org.semanticwb.remotetriplestore;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.pool.impl.StackObjectPool;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.protocol.Command;

/**
 *
 * @author serch
 */
class SWBRTSUtil
{
    private static Logger log = SWBUtils.getLogger(SWBRTSUtil.class);
    private String[] params=null;
    
    private static StackObjectPool pool=null;
    
    public static void initPool(InetAddress byName, int port)
    {
        pool=new StackObjectPool(new SWBSocketPoolFactory(byName, port),600);
    }
    
    public List<String> call() throws Exception
    {
        //long time=System.currentTimeMillis();
        //System.out.println("Call");
        ArrayList<String> arr=new ArrayList();
        for(int x=0;x<params.length;x++)
        {
            arr.add(params[x]);
            //System.out.println("  param:"+params[x]);
        }        
        SWBSocketClient client=(SWBSocketClient)pool.borrowObject();                
        //System.out.println(" client:"+client);
        client.writeCommands(arr);
        //System.out.println("  write:"+(System.currentTimeMillis()-time));
        //time=System.currentTimeMillis();
        //if(arr.size()>2 && arr.get(1).equals("demo"))new Exception().printStackTrace();
        List ret=client.readCommands();
        pool.returnObject(client);
        //System.out.println("  read:"+(System.currentTimeMillis()-time));
        return ret;
    }

    public SWBRTSUtil(String[] params)
    {
        this.params=params;
    }
}


class SWBSocketClient
{
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;    
    
    public SWBSocketClient(InetAddress net,int port) throws IOException
    {
        socket=new Socket(net, port);
        socket.setTcpNoDelay(true);
        this.in=new DataInputStream(socket.getInputStream());
        this.out=new DataOutputStream(socket.getOutputStream());
    }
        
    public void writeCommand(String cmd) throws IOException
    {
        List<String> arr=new ArrayList();
        arr.add(cmd);
        writeCommands(arr);
    } 
    
    public List<String> readCommands() throws IOException
    {
        ArrayList arr=new ArrayList();
        int n=Integer.parseInt(in.readUTF());
        for(int x=0;x<n;x++)
        {
            String s=in.readUTF();
            if(s.equals(Command.NULL))s=null;
            arr.add(s);
        }
        return arr;           
    }
    
    public void writeCommands(List<String> list) throws IOException
    {
        out.writeUTF(String.valueOf(list.size()));
        Iterator<String> it=list.iterator();
        while (it.hasNext())
        {
            String string = it.next();
            if(string==null)string=Command.NULL;
            out.writeUTF(string);
        }
    }   
    
    public boolean isClosed()
    {
        return socket.isClosed();
    }
    
    public void close() throws IOException
    {
        writeCommand(Command.CLOSE);
        socket.close();
    }
}

class SWBSocketPoolFactory extends org.apache.commons.pool.BasePoolableObjectFactory
{
    private InetAddress byName=null;
    private int port=0;
    
    public SWBSocketPoolFactory(InetAddress byName, int port)
    {
        this.byName=byName;
        this.port=port;
    }
    
    @Override
    public Object makeObject() throws Exception
    {
        return new SWBSocketClient(byName, port);
    }

    @Override
    public void destroyObject(Object obj) throws Exception
    {
        try
        {
            ((SWBSocketClient)obj).close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
