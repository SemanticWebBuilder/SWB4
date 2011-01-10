/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.opensocial.services;

import org.json.JSONObject;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.data.Person;
import org.semanticwb.opensocial.resources.RPCException;

/**
 *
 * @author victor.lorenzana
 */
public interface Service {

    public JSONObject get(Person person,JSONObject params,WebSite site,Gadget gadget) throws RPCException;
    public JSONObject update(Person person,JSONObject params,WebSite site,Gadget gadget) throws RPCException;
    public JSONObject delete(Person person,JSONObject params,WebSite site,Gadget gadget) throws RPCException;
    public JSONObject create(Person person,JSONObject params,WebSite site,Gadget gadget) throws RPCException;
}
