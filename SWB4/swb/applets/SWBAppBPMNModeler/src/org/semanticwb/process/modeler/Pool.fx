/*
 * Pool.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import org.semanticwb.process.modeler.ModelerUtils;
import javafx.stage.Alert;
import org.semanticwb.process.modeler.MessageFlow;
import javafx.scene.input.MouseEvent;
import javafx.util.Sequences;
import org.semanticwb.process.modeler.GraphicalElement;

/**
 * @author javier.solis
 */

public class Pool extends GraphicalElement
{

    var py=bind y on replace
    {
        updateSize();
    }

    public var lanes:Lane[];

    override public function create(): Node
    {
        setCommonMenuOptions();
        resizeable=true;
        resizeType=ResizeNode.RESIZE_A;
        w=600;
        h=200;
        minW = w;
        minH = h;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x-w/2+10
            y:bind y
            width: bind h
            height: 20
            rotate: -90
        }

        shape= Rectangle
        {
            x: bind x-w/2
            y: bind y-h/2
            width: bind w
            height: bind h
            styleClass: "pool"
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
        };

        var actions: MenuItem[] = [
            MenuItem {
                caption:##"actDelContents"
                status: bind if (this.graphChilds.size() > 0) MenuItem.STATUS_ENABLED else MenuItem.STATUS_DISABLED
                action: function (e: MouseEvent) {
                    var tit = ##"actDelete";
                    var msg = ##[confirmDelContents]"\"{this.title}\"";
                    if(Alert.confirm(tit, msg)) {
                        removeChilds();
                    }
                }
            },
            MenuItem {isSeparator:true},
            MenuItem {
                caption: ##"actEditTitle"
                status: MenuItem.STATUS_ENABLED
                action: function (e: MouseEvent) {
                    if(text != null) {
                        text.startEditing();
                    }
                }
            }
        ];
        insert actions after menuOptions[0];

        return Group
        {
            content: [
                shape,Line
                {
                    startX: bind x-w/2+20
                    startY: bind y-h/2+1
                    endX: bind x-w/2+20
                    endY: bind y+h/2-1
                    stroke: bind shape.stroke
                    styleClass: "pool"
                },text,
                Group
                {
                    content:bind lanes
                    visible: bind sizeof lanes > 0
                }
            ]
            scaleX: bind s;
            scaleY: bind s;
            visible: bind canView()
            onMouseReleased: function(e: MouseEvent) {
                for (ele in modeler.contents) {
                    if (ele instanceof GraphicalElement and not (ele instanceof Lane or ele instanceof Pool)) {
                        var no = ele as GraphicalElement;
                        if (no.graphParent == null) {
                            if (no.boundsInLocal.minX > boundsInLocal.minX and no.boundsInLocal.minY > boundsInLocal.minY) {
                                if (no.boundsInLocal.maxX < boundsInLocal.minX + boundsInLocal.width and no.boundsInParent.maxY < boundsInLocal.minY + boundsInLocal.height) {
                                    no.setGraphParent(this);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override public function remove(validate:Boolean)
    {
        ModelerUtils.popup.hide();
        var tit = ##"actDelete";
        var msg = ##[confirmDelete]"\"{this.title}\"";
       if(not validate or sizeof graphChilds == 0 or Alert.confirm(tit, msg))
       {
           super.remove(validate);
       }
    }

    public function removeLane(lane:Lane)
    {
        delete lane from lanes;
        if(sizeof lanes==0)resizeType=ResizeNode.RESIZE_A;
        updateSize();
    }

    public function addLane():Lane
    {
        var lane=Lane
        {
            title:"Lane"
            modeler:modeler;
            uri:"new:Lane:{ToolBar.counter++}"
            w:bind w
        };
        lane.setGraphParent(this);
        lane.setContainer(getContainer());
        insert lane into lanes;
        ModelerUtils.setResizeNode(null);
        resizeType=ResizeNode.RESIZE_H;
        updateSize();
        return lane;
    }

    public override function updateSize()
    {
        var t:Number=0;
        for(lane in lanes)
        {
            t=t+lane.h;
        }
        
        if(t>0)
        {
            h=t;
        }

        var ay=y-h/2;
        for(lane in lanes)
        {
            lane.y=ay+lane.h/2;
            ay=ay+lane.h;
        }
    }

    public function getLaneByURI(uri:String):GraphicalElement
    {
        for(lane in lanes)
        {
            println("{lane.uri}={uri}");
            if(lane.uri.equals(uri))return lane;
        }
        return null;
    }

    override public function canStartLink(link:ConnectionObject) : Boolean
    {
        var ret = true;
        if (link instanceof SequenceFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError37");
        }        
        return ret;
    }

    override public function canEndLink(link:ConnectionObject) : Boolean
    {
        var ret = true;
        if (link instanceof SequenceFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError37");
        }
        if (link instanceof MessageFlow) {
            if (link.ini.getPool() == this) {
                ret = false;
                ModelerUtils.setErrorMessage(##"msgError16");
            }
        }
        return ret;
    }

    override public function canAddToDiagram(): Boolean {
        var ret = super.canAddToDiagram();
        if (modeler.containerElement != null and modeler.containerElement instanceof SubProcess) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError45");
        }
        return ret;
    }

    public function removeChilds() {
        var ch = Sequences.shuffle(getgraphChilds());
        for (ele in ch) {
            if (ele instanceof Lane) {
                (ele as Lane).removeChilds();
                removeLane(ele as Lane);
            } else {
                (ele as GraphicalElement).remove(true);
            }
        }
    }
}