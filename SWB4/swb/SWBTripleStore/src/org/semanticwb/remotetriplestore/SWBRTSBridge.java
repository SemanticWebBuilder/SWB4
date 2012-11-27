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
