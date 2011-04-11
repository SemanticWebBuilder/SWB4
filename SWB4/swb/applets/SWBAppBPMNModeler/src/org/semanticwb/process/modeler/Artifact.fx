/*
 * Artifact.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

public def TYPE_ANNOTATION="annotationartifact";
public def TYPE_GROUP="groupartifact";
public class Artifact extends GraphicalElement
{
    public override function canAttach(parent:GraphicalElement):Boolean
    {
        var ret=false;
        if(parent instanceof Pool or parent instanceof Lane)
        {
            ret=true;
        }
        return ret;
    }

    public override function canStartLink(link:ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);
        if (not(link instanceof AssociationFlow)) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError3");
        }
        return ret;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);

        if (not(link instanceof AssociationFlow)) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError3");
        } else if (link.ini instanceof Artifact or link.ini instanceof DataObject) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError5");
        }
        return ret;
    }
}