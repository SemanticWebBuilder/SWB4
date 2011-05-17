
package org.semanticwb.remotetriplestore;

import org.semanticwb.Logger;
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
        SWBRTSBridge server = new SWBRTSBridge();
        server.setPort(6666);
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
