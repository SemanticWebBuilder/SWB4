package org.semanticwb.remotetriplestore;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.protocol.EOT;
import org.semanticwb.remotetriplestore.protocol.Response;
import org.semanticwb.remotetriplestore.protocol.SWBRTSCmd;

/**
 *
 * @author serch
 */
class SWBRTSUtil implements Callable<Response> {
    private static Logger log = SWBUtils.getLogger(SWBRTSUtil.class);
    private Socket sock;
    private OutputStream os_connect;
    private InputStream in_connect;
    private SWBRTSCmd command;
    private String[] params=null;
    
    
    public static LinkedList<SWBRTSUtil> list=new LinkedList();
    
    private static int buffer=5;
    private static boolean loading=false;
    
    public static synchronized SWBRTSUtil getInstance(final InetAddress byName, final int port)
    {
        SWBRTSUtil ret=null;
        if(buffer>0)
        {
            System.out.println("getInstance:"+list.size()+" "+loading);
            if(!list.isEmpty())ret=list.pollFirst();
            //System.out.print(" ret:"+ret);
            if(ret==null || !ret.isAlive())
            {
                //System.out.print(" new");
                ret=new SWBRTSUtil(byName, port);   
            }
            if(list.size()<buffer && !loading)
            {
                System.out.println("reload start");
                loading=true;
                new Thread()
                {
                    @Override
                    public void run() {
                        for(int x=0;x<buffer;x++)
                        {
                            list.add(new SWBRTSUtil(byName, port));
                        }
                        System.out.println("reload end");
                        loading=false;
                    }            
                }.start();            
            }
            //System.out.println("");
        }else
        {
            ret=new SWBRTSUtil(byName, port);   
        }
        return ret;        
    }
    

    SWBRTSUtil(InetAddress byName, int port)
    {
        try
        {
            sock = new Socket(byName, port);
            sock.setKeepAlive(true);
            os_connect = sock.getOutputStream();
            in_connect = sock.getInputStream();
        }catch (IOException ioe){
            log.error("Can't connect to SWBTS Server",ioe);
        }
    }
    
    public boolean isAlive()
    {
        return !sock.isClosed();
    }

    public void setCommand(SWBRTSCmd command)
    {
        this.command=command;
    }

    public Response call() throws Exception
    {
        System.out.println("Call:"+sock);
        ObjectOutputStream outData = new ObjectOutputStream(os_connect);
        outData.writeObject(command);
        //System.out.println("SWBRTSUtil: cmd "+command.cmd+" #"+command.paramNumber);
        if (null!=params)
        {
            for (int i=0; i<params.length; i++)
            {
                outData.writeObject(params[i]);
                //System.out.println("wrote param: "+params[i]);
            }
            
        }
        outData.writeObject(new EOT());
        System.out.println("wrote EOT");
        outData.flush();
        ObjectInputStream inData = new ObjectInputStream(in_connect);
        
        Response resp = (Response)inData.readObject();
        System.out.println("Read Response "+resp.data);
        outData.close();;
        inData.close();
        sock.close();
        
        return resp;

    }

    void setParams(String[] params)
    {
        this.params=params;
    }
}
