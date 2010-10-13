/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.util.Set;
import org.w3c.dom.Document;

/**
 *
 * @author victor.lorenzana
 */
public interface ServiceListener {

    public void onGET(ServiceInfo service,Document response,Set<RestParameter> parameters);
    public void onPUT(ServiceInfo service,Set<RestParameter> parameters);
    public void onPOST(ServiceInfo service,Set<RestParameter> parameters);
    public void onDELETE(ServiceInfo service,Set<RestParameter> parameters);
}
