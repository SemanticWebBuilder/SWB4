/*
 * LinkConnection.fx
 *
 * Created on 4/03/2010, 04:13:39 PM
 */

package org.semanticwb.publishflow;
import javafx.scene.Node;

/**
 * @author victor.lorenzana
 */

public class LinkConnection extends ConnectionObject {

    public var users: String[];
    public var roles: String[];
    public var type: String;
    public var authorized:Boolean;
    public var published:Boolean;
    public override function create(): Node {

        return super.create();
    }
}
