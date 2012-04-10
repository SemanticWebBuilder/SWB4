/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.linkeddata.spider;



/**
 *
 * @author victor.lorenzana
 */
public class FireEvent implements Runnable{
    private SpiderEventListener listener;
    public FireEvent(SpiderEventListener listener)
    {
        this.listener=listener;
    }

    public void run()
    {

    }
}
