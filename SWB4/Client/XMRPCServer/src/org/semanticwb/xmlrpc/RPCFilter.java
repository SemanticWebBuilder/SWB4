/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.xmlrpc;

import org.jdom.Document;

/**
 *
 * @author victor.lorenzana
 */
public interface RPCFilter {
    public void doFilter(Document document) throws Exception;
}
