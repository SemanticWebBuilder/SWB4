/*
 * NoAuthorizeLink.fx
 *
 * Created on 1/03/2010, 05:11:17 PM
 */
package org.semanticwb.publishflow;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

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

}
