
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.pool.impl.StackObjectPool;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier.solis.g
 */
public class TestSocketClient
{
    public static void main(String args[])
    {
        long time=System.currentTimeMillis();
        StackObjectPool pool=new StackObjectPool(new SWBSocketPoolFactory(),600);
        for(int x=0;x<10000;x++)
        {
            try
            {
                SWBSocketClient client=(SWBSocketClient)pool.borrowObject();                
                //for(int y=0;y<10000;y++)
                {
                    client.writeCommand("hola");
                    List r=client.readCommands();
                    //System.out.println(r);
                    client.writeCommand("hola Jei");
                    r=client.readCommands();
                    //System.out.println(r);
                }
                pool.returnObject(client);
            }catch(Exception e)
            {
                e.printStackTrace();
            }        
        }
        
        try
        {
            pool.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Time:"+(System.currentTimeMillis()-time));
    }        
}

class SWBSocketClient
{
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;    
    
    public SWBSocketClient(String net,int port) throws IOException
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
            arr.add(in.readUTF());
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
            out.writeUTF(string);
        }
    }   
    
    public boolean isClosed()
    {
        return socket.isClosed();
    }
    
    public void close() throws IOException
    {
        writeCommand("_end_");
        socket.close();
    }
}

class SWBSocketPoolFactory extends org.apache.commons.pool.BasePoolableObjectFactory
{
    @Override
    public Object makeObject() throws Exception
    {
        return new SWBSocketClient("localhost", 9090);
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
