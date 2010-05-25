/*
 * StartEvent.fx
 *
 * Created on 13/02/2010, 11:29:53 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * @author javier.solis
 */

public class StartEvent extends CatchEvent
{
    public override function create(): Node
    {
         var ret=super.create();
         stroke=Color.web(Styles.color_iniEvent);

         colorAdjust.hue=0.52;
         colorAdjust.brightness=-0.19;
         colorAdjust.contrast=0.25;
         colorAdjust.saturation=1;

         return ret;
    }

    override public function canEndLink(link:ConnectionObject) : Boolean
    {
        var ret = super.canEndLink(link);

        if (not (link instanceof AssociationFlow)) {
            ret = false;
            ModelerUtils.setErrorMessage("StartEvent cannot have incoming flows");
        }

        if (link instanceof DirectionalAssociation) {
            ret = false;
            ModelerUtils.setErrorMessage("StartEvent cannot be linked using DirectionalAssociation");
        }
        
        if (link instanceof AssociationFlow and not (link.ini instanceof AnnotationArtifact)) {
            ret = false;
            ModelerUtils.setErrorMessage("StartEvent can only be linked to AnnotationArtifact");
        }

        return ret;
    }

    override public function canStartLink(link:ConnectionObject) : Boolean
    {
        var ret = super.canStartLink(link);
        if(link instanceof MessageFlow) {
            ret = false;
            ModelerUtils.setErrorMessage("StartEvent cannot have outgoing MessageFlow");
        }
        return ret;
    }

    override public function canAttach(parent:GraphicalElement):Boolean
    {
        return super.canAttach(parent);
    }
}
