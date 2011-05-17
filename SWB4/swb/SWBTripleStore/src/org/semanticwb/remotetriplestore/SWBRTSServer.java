
package org.semanticwb.remotetriplestore;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBAppObject;

/**
 *
 * @author serch
 */
public class SWBRTSServer implements SWBAppObject {

    private static Logger log = SWBUtils.getLogger(SWBRTSServer.class);
    public void init()
    {
        int port = Integer.parseInt(SWBPlatform.getEnv("swb/tripleremoteport", "6666"));
        SWBRTSBridge server = new SWBRTSBridge();
        server.setPort(port);
        server.start();
    }

    public void destroy()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void refresh()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
