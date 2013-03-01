/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.resources.reports;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 *
 * @author carlos.alvarez
 */
public class test extends GenericForwardComposer<Component> {

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp); //To change body of generated methods, choose Tools | Templates.
        System.out.println("test");
    }
}
