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
    private ServerSocket sserv = null;
    private boolean running = false;
    private ExecutorService pool = Executors.newCachedThreadPool();


    public void setPort(int port)
    {
        this.port=port;
    }

    @Override
    public void run()
    {
        try
        {
            sserv = new ServerSocket(port);
            while (running)
            {
                Socket sock = sserv.accept();
              // System.out.println("**************** Receive Socket...");
                pool.submit(new SWBRTSConn(sock));
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
