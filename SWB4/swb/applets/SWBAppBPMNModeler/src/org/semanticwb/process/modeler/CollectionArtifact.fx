/*
 * CollectionArtifact.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class CollectionArtifact extends Artifact
{
    override public function create(): Node
    {
        type=TYPE_COLLECTION;
        return super.create();
    }
}