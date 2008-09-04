
/*
 * WBResourceCache.java
 *
 * Created on 21 de marzo de 2005, 12:55 PM
 */

package org.semanticwb.portal.api;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBException;

/**
 *
 * @author Javier Solis Gonzalez
 */
public interface SWBResourceWindow
{
    public String[] getModes(HttpServletRequest request, SWBParamRequest paramRequest)
        throws SWBException, java.io.IOException;

    public String[] getWindowStates(HttpServletRequest request, SWBParamRequest paramRequest)
        throws SWBException, java.io.IOException;
    
    public String getTitle(HttpServletRequest request, SWBParamRequest paramRequest)
        throws SWBException, java.io.IOException;
    
    public boolean windowSupport(HttpServletRequest request, SWBParamRequest paramRequest)
        throws SWBException, java.io.IOException;
}
