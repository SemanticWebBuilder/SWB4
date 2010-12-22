/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.opensocial.services;

import org.json.JSONObject;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;

/**
 *
 * @author victor.lorenzana
 */
public interface Service {

    public JSONObject get(String userid,JSONObject params,WebSite site,Gadget gadget);
    public void update(String userid,JSONObject params,WebSite site,Gadget gadget);
    /*public void create(String userid,Parameter...agrs);
    public void delete(String userid,String id);
    public Parameter[] update(String userid,JSONObject object);*/
}
