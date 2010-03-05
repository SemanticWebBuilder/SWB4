/*
 * NoAuthorizeLink.fx
 *
 * Created on 1/03/2010, 05:11:17 PM
 */
package org.semanticwb.publishflow;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import java.lang.Void;

/**
 * @author victor.lorenzana
 */
public class NoAuthorizeLink extends LinkConnection {

    public override function create(): Node {
        color = Styles.style_connection_not_authorize;
        color_row = Styles.style_connection_arrow_not_authorize;
        authorized=false;
        published=false;
        type="unauthorized";
        return super.create();
    }

    public override function mouseExited(e: MouseEvent): Void {
        super.mouseExited(e);
        color = Styles.style_connection_not_authorize;
        color_row = Styles.style_connection_arrow_not_authorize;
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
            dialogEditLink.showPublicar=false;
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
