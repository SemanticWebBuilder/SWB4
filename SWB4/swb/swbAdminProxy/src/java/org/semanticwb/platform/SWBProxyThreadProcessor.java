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
 * http://www.semanticwebbuilder.org
 */
package org.semanticwb.platform;

import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author serch
 */
public class SWBProxyThreadProcessor implements Runnable {
    static private Logger log = SWBUtils.getLogger(SWBProxyThreadProcessor.class);
    final private BlockingQueue<DelayedMessage> queue;
    final private SWBProxyMessageCenter center;
    final private HashMap<String, DatagramPacket> network;
    private volatile boolean flag = true;

    public SWBProxyThreadProcessor(BlockingQueue<DelayedMessage> queue, SWBProxyMessageCenter center, HashMap<String, DatagramPacket> network) {
        this.queue = queue;
        this.center = center;
        this.network = network;
    }
    
    @Override
    public void run() {
        while(flag){
            try {
                DelayedMessage message = queue.poll();
                center.sendMessage(message.getMessage(), network);
            } catch (Exception err){
                log.error("SWBProxyThreadProcessor: sending udp message", err);
            }
        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
}
