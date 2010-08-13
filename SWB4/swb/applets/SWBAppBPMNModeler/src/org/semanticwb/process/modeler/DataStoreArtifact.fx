/*
 * Pool.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.Cursor;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.ArcTo;

/**
 * @author javier.solis
 */

public class DataStoreArtifact extends Artifact
{
    override public function create(): Node
    {
        cursor=Cursor.HAND;
        w=80;
        h=60;
        stkw=1;
        stkwo=2;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y+10
            width: bind w
            height: bind h
        }

        shape=Path
        {
            elements: [
                MoveTo { x: 0  y: bind h/7 },
                LineTo { x: 0  y: bind h-h/7 },
                ArcTo { x: bind w  y: bind h-h/7  radiusX: bind w/2  radiusY: bind h/7  sweepFlag: false },
                LineTo { x: bind w  y: bind h/7 },
                ArcTo { x: 0  y: bind h/7  radiusX: bind w/2  radiusY: bind h/7  sweepFlag: false },
                //ClosePath{},
                //MoveTo { x: 0  y: 0 },
                //ArcTo { x: bind w  y: 0  radiusX: 100  radiusY: 100  sweepFlag: true }
            ]
            style:Styles.style_artifact
            translateX:bind x-w/2
            translateY:bind y-h/2
        }

//        Rectangle{
//            x:bind x-w/2
//            y:bind y-h/2
//            width:w
//            height:h
//            style:Styles.style_artifact
//        }

        setType(type);

        return Group
        {
            content: [
                shape,Path
                {
                    elements: [
                        MoveTo { x: bind w  y: bind h/7 },
                        ArcTo { x: 0  y: bind h/7  radiusX: bind w/2  radiusY: bind h/7  sweepFlag: true }
                    ]
                    stroke:bind shape.stroke
                    translateX:bind x-w/2
                    translateY:bind y-h/2
                },text
            ]
            scaleX: bind s;
            scaleY: bind s;
            effect: Styles.dropShadow
            visible: bind canView()
        };
    }

}