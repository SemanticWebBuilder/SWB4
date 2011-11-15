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
        ArrayList<String> arr=new ArrayList();
        for(int x=0;x<params.length;x++)
        {
            arr.add(params[x]);
        }        
        SWBSocketClient client=(SWBSocketClient)pool.borrowObject();                
        client.writeCommands(arr);
        //if(arr.size()>2 && arr.get(1).equals("demo"))new Exception().printStackTrace();
        List ret=client.readCommands();
        pool.returnObject(client);
                
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
