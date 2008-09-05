/*
 * WBResourceCache.java
 *
 * Created on 21 de marzo de 2005, 12:55 PM
 */

package org.semanticwb.portal.api;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Javier Solis Gonzalez
 */
public interface SWBResourceCache
{
    public String getResourceCacheID(HttpServletRequest request, SWBParamRequest paramRequest)
        throws SWBResourceException, java.io.IOException;
}
