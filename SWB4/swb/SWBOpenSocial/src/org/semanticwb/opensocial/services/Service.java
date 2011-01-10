/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.opensocial.services;

import org.json.JSONObject;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.data.Person;

/**
 *
 * @author victor.lorenzana
 */
public interface Service {

    public JSONObject get(Person person,JSONObject params,WebSite site,Gadget gadget);
    public void update(Person person,JSONObject params,WebSite site,Gadget gadget);
    public void delete(Person person,JSONObject params,WebSite site,Gadget gadget);
    public void create(Person person,JSONObject params,WebSite site,Gadget gadget);
}
