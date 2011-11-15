
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.openjena.atlas.iterator.Iterator2;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier.solis.g
 */
public class TestSockets
{
    public static void main(String args[])
    {
        try
        {
            SWBSocketServer server=new SWBSocketServer(9090,100);
            server.serve();
        }catch(Exception e)
        {
            e.printStackTrace();
        }        
    }
}

class SWBSocketServer
{

    private final ServerSocket serverSocket;
    private final ExecutorService pool;

    public SWBSocketServer(int port, int poolSize) throws IOException
    {
        serverSocket = new ServerSocket(port);
        pool = Executors.newCachedThreadPool();
        //pool = Executors.newFixedThreadPool(poolSize);
    }

    public void serve()
    {
        try
        {
            for (;;)
            {
                pool.execute(new SWBHandler(serverSocket.accept()));
            }
        } catch (IOException ex)
        {
            pool.shutdown();
        }
    }
}

class SWBHandler implements Runnable
{

    private final Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    SWBHandler(Socket socket)
    {
        System.out.println("Handler...");
        this.socket = socket;
    }

    public void run()
    {
        System.out.println("Connection inited...");
        try
        {
            this.in=new DataInputStream(socket.getInputStream());
            this.out=new DataOutputStream(socket.getOutputStream());
        }catch(Exception e)
        {
            e.printStackTrace();
        }        
        
        try
        {
            String arr[];
            while(true)
            {
                List list=readCommands();
                if(list.size()==1 && list.get(0).equals("_end_"))break;
                processCommand(list);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        //Close Connection
        try
        {
            out.close();
            socket.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Connection closed...");
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
 
    
    public void processCommand(List list) throws IOException
    {
        list.set(0, list.get(0)+"2");
        writeCommands(list);
    }    
        
}