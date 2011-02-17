/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;

/**
 *
 * @author jei
 */
public class TestObservable {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        WebPage.swb_active.registerObserver(new SemanticObserver() {
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
                //Implementacion de notificacion
            }
        });
    }


}
