/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.menu;

import org.zkoss.zul.Include;
import org.zkoss.zk.ui.select.annotation.Wire;

/**
 *
 * @author jorge.jimenez
 */
public class menuEvents implements org.zkoss.zk.ui.event.EventListener {

        private Include content;

        public menuEvents(Include content)
        {
            this.content=content;
        }

        public void onEvent(org.zkoss.zk.ui.event.Event event) {
            try {
                    System.out.println("Event NameJ:"+event.getName());
                    System.out.println("EntraJG:"+content);
                    content.setSrc("/hello.zul");
                    content.getPage();
                    System.out.println("Entra2J");
            } catch (Exception ex) {
                System.out.println("Error:"+ex.getMessage());
                ex.printStackTrace();
            }

        }

}
