/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation.cache;

import java.util.Hashtable;
import org.semanticwb.jcr283.implementation.NodeImp;

/**
 *
 * @author victor.lorenzana
 */
public class Cache {

    Hashtable<String,NodeImp> cache=new Hashtable<String, NodeImp>();
    public NodeImp getNode(String path)
    {
        return null;
    }   
}
