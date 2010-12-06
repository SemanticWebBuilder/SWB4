/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import org.json.JSONObject;

/**
 *
 * @author victor.lorenzana
 */
public interface JSONResponse extends RepresentationResponse {

    public JSONObject getJSONResponse();
}
