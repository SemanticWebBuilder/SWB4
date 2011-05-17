package org.semanticwb.remotetriplestore;

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author serch
 */
public class SWBRTSThreadPool {
    private ExecutorService pool = Executors.newCachedThreadPool();
    private int port;
    private InetAddress address;

    public SWBRTSThreadPool(InetAddress address, int port)
    {
        this.address = address;
        this.port= port;
    }



    public InetAddress getAddress()
    {
        return address;
    }

   
    public ExecutorService getPool()
    {
        return pool;
    }

    public int getPort()
    {
        return port;
    }

    

}
