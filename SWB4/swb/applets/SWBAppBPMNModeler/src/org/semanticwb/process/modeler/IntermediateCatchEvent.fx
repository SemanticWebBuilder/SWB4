/*
 * IntermediateCatchEvent.fx
 *
 * Created on 13/02/2010, 11:30:48 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import org.semanticwb.process.modeler.SequenceFlow;
import javafx.scene.input.MouseEvent;
import org.semanticwb.process.modeler.CallActivity;
import org.semanticwb.process.modeler.ModelerUtils;

/**
 * @author javier.solis
 */

public class IntermediateCatchEvent extends CatchEvent
{
    //public var attachedTo: Activity;
    var px = bind graphParent.x on replace
    {
        onParentXChange();
    }
    var py = bind graphParent.y on replace
    {
        onParentYChange();
    }

    public override var over on replace {
        if (over and not selected) {
            shape.styleClass = "interEventHover";
        } else if (not selected) {
            shape.styleClass = "interEvent";
        }
    }

    public override var selected on replace {
        if (selected) {
            shape.styleClass = "interEventFocused";
        } else {
            shape.styleClass = "interEvent";
        }
    }
    
    public override function create(): Node
    {
        //attachedTo = null;
        blocksMouse = true;
        initializeCustomNode();
        w=30;
        h=30;
        scaleOff=-0.1;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y + 30
            width: bind w + 60
            height: bind h
        }
        colorAdjust.hue=-0.83;
        colorAdjust.brightness=-0.07;
        colorAdjust.contrast=0.25;
        colorAdjust.saturation=1;

        shape= Circle
        {
            centerX: bind x
            centerY: bind y
            radius: bind w/2
            styleClass: "interEvent"
            strokeDashArray: bind if (isInterrupting) null else [2, 5]
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
        };

        setType(type);

        var actions: MenuItem[] = [
            MenuItem {
                status: bind if (cancelActivity) MenuItem.STATUS_ENABLED else MenuItem.STATUS_DISABLED
                caption: bind if (isInterrupting) ##"nonInterrupting" else ##"interrupting"
                action: function (e: MouseEvent) {
                    isInterrupting = not isInterrupting;
                    ModelerUtils.popup.hide();
                }
            },
            MenuItem {isSeparator: true},
            MenuItem {
                caption: ##"actCopy"
                action: function(e: MouseEvent) {
                    ModelerUtils.popup.hide();
                    var t = copy();
                    modeler.setCopyNode(t);
                }
            },
            MenuItem {
                caption: ##"actCut"
                action: function(e: MouseEvent) {
                    ModelerUtils.popup.hide();
                    var t = cut();
                    modeler.setCopyNode(t);
                    ModelerUtils.setResizeNode(null);
                }
            }
        ];
        insert actions before menuOptions[0];

        return Group
        {
            content: [
                shape,
                Circle
                {
                    centerX: bind x
                    centerY: bind y
                    radius: bind w/2-3
                    strokeDashArray: bind if (isInterrupting) null else [2, 5]
                    styleClass: "interEvent"
                    //id: "marker"
                },
                message, text
            ]
            scaleX: bind s;
            scaleY: bind s;
            visible: bind canView()
        };
    }

    public override function canAttach(parent:GraphicalElement):Boolean
    {
        var ret = super.canAttach(parent);
        if(ret or parent instanceof Activity) {
            ret = true;
        }
        if (parent instanceof CallActivity) {
            ret = false;
        }
        
        return ret;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);
        var c = 0;

        for(ele in getInputConnectionObjects()) {
            if(ele instanceof SequenceFlow) {
                c++;
            }
        }

        if (link instanceof SequenceFlow) {
            if (this.getGraphParent() instanceof Activity) {
                ret = false;
                ModelerUtils.setErrorMessage(##"msgError20");
            } else if (c != 0) {
                ret = false;
                ModelerUtils.setErrorMessage(##"msgError21");
            }
        }
        return ret;
    }

    public override function canStartLink(link:ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);
        var c = 0;

        for(ele in getOutputConnectionObjects()) {
            if(ele instanceof SequenceFlow) {
                c++;
            }
        }
        
        if (link instanceof SequenceFlow and c != 0) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError22");
        }
        return ret;
    }

    override public function mouseReleased( e: MouseEvent )
    {
        if (not modeler.isLocked()) {
            var overNode: GraphicalElement = getOverNode();
            setGraphParent(overNode);
            if (overNode instanceof Activity) {
                //if (attachedTo != null) {
                //    attachedTo = null;
                //}
                //var act = overNode as Activity;
                //attachedTo = act;
                (graphParent as Activity).updateAttachedEventsPosition();
            } else { //else if (overNode == null) {
            //    attachedTo = null;
            //}
                snapToGrid();
            }
        }
    }

    override public function onParentXChange() {
       if(graphParent!=null and not graphParent.resizing) {
           x=px+dpx;
           if (graphParent instanceof Activity) {
           //if (attachedTo != null) {
            (graphParent as Activity).updateAttachedEventsPosition();
           }
       }
       //println("Cambiando X");
    }

    override public function onParentYChange() {
        if(graphParent!=null and not graphParent.resizing) {
            y=py+dpy;
            if (graphParent instanceof Activity) {
            //if (attachedTo != null) {
                (graphParent as Activity).updateAttachedEventsPosition();
            }
        }
        //println("Cambiando Y");
    }

    override public function snapToGrid()
    {
        if(not (graphParent instanceof Activity))
        {
            super.snapToGrid();
        }
    }

    override public function copy() : GraphicalElement {
        var t = IntermediateCatchEvent {
            title: this.title
            description: this.description
            type: this.type
            modeler: this.modeler
            container: this.container
            x: this.x + 10
            y: this.y + 10
            uri: "new:interevent:{modeler.toolBar.counter++}"
        }
        return t;
    }
}