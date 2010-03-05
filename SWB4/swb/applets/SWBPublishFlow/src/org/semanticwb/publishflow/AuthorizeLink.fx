/*
 * AuthorizeLink.fx
 *
 * Created on 1/03/2010, 05:10:45 PM
 */
package org.semanticwb.publishflow;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;

/**
 * @author victor.lorenzana
 */
public class AuthorizeLink extends LinkConnection {

    public override function create(): Node {
        color = Styles.style_connection_authorize;
        color_row = Styles.style_connection_arrow_authorize;
        authorized=true;
        type="authorized";
        return super.create();
    }

    public override function mouseExited(e: MouseEvent): Void {
        super.mouseExited(e);
        color = Styles.style_connection_authorize;
        color_row = Styles.style_connection_arrow_authorize;
    }

    public override function mouseClicked(e: MouseEvent)
      {
        super.mouseClicked(e);
        if (e.clickCount == 2 and e.button == MouseButton.PRIMARY) {
            
            var dialogEditLink: DialogEditLink;
            dialogEditLink=new DialogEditLink();
            dialogEditLink.published=published;
            dialogEditLink.roles=roles;
            dialogEditLink.users=users;
            dialogEditLink.showPublicar=true;
            dialogEditLink.init();
            dialogEditLink.setVisible(true);
            if(not dialogEditLink.cancel)
            {
                this.published=published;
                this.users=users;
                this.roles=roles;
            }

        }
    }

}
