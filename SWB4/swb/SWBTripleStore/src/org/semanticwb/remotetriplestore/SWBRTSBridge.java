package org.semanticwb.remotetriplestore;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author serch
 */
public class SWBRTSBridge extends Thread{

    private static Logger log = SWBUtils.getLogger(SWBRTSBridge.class);
    private int port;
    private boolean running = false;
    private ExecutorService pool = Executors.newCachedThreadPool();
    private ServerSocket serverSocket;

    public void setPort(int port)
    {
        this.port=port;
    }

    @Override
    public void run()
    {
        try
        {
            serverSocket = new ServerSocket(port);            
            try
            {
                while (running)
                {
                    pool.execute(new SWBRTSConn(serverSocket.accept()));
                }                
            } catch (IOException ex)
            {
                pool.shutdown();
            }
        } catch (IOException ex)
        {
            log.error("SWBRTSBridge: error receiving remote request",ex);
        }
    }

    @Override
    public synchronized void start()
    {
        running=true;
        super.start();
    }

    public void terminate()
    {
        pool.shutdown();
        running=false;
    }

}
