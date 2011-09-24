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
import java.util.Comparator;
import org.semanticwb.process.modeler.GraphicalElement;
import java.util.HashMap;
import org.semanticwb.process.modeler.AssociationFlow;

/**
 * Clase que representa un Pool en un diagrama BPMN 2.0.
 * @author javier.solis
 */
public class Pool extends GraphicalElement
{
    public override var over on replace {
        if (over and not selected) {
            shape.styleClass = "poolHover";
        } else if (not selected) {
            shape.styleClass = "pool";
        }
    }

    public override var selected on replace {
        if (selected) {
            shape.styleClass = "poolFocused";
        } else {
            shape.styleClass = "pool";
        }
    }
    
    var py=bind y on replace
    {
        updateSize();
    }

    var c = Comparator {
       override public function compare (arg0 : Object, arg1 : Object) : Integer {
           var l1 = arg0 as Lane;
           var l2 = arg1 as Lane;
           var ret = 0;

           if (l1.idx == l2.idx) ret = 0;
           if (l1.idx < l2.idx) ret = -1;
           if (l1.idx > l2.idx) ret = 1;
           return ret;
       }
    }

    public var lanes:Lane[];

    override public function create(): Node
    {
        blocksMouse = true;
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
                    ModelerUtils.popup.hide();
                }
            },
//            MenuItem {
//                caption: ##"actCopy"
//                action: function(e: MouseEvent) {
//                    var t = copy();
//                    modeler.setCopyNode(t);
//                    ModelerUtils.popup.hide();
//                }
//            },
            MenuItem {isSeparator:true},
            MenuItem {
                caption: ##"actEditTitle"
                status: MenuItem.STATUS_ENABLED
                action: function (e: MouseEvent) {
                    if(text != null) {
                        text.startEditing();
                    }
                    ModelerUtils.popup.hide();
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
//            onMouseReleased: function(e: MouseEvent) {
//                captureChilds();
//            }
        };
    }

    /**Adhiere al pool los elementos que se encuentran debajo*/
    public function captureChilds() {
        for (ele in modeler.contents) {
            if (ele instanceof GraphicalElement and not (ele instanceof Lane or ele instanceof Pool or ele instanceof Artifact)) {
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

    /**Elimina un Lane del Pool*/
    public function removeLane(lane:Lane)
    {
        delete lane from lanes;
        if(sizeof lanes==0)resizeType=ResizeNode.RESIZE_A;
        updateSize();
    }

    public function swapLanes(l1: Integer, l2: Integer) {
        var t = lanes[l1].idx;
        lanes[l1].idx = lanes[l2].idx;
        lanes[l2].idx = t;
        sortLanes();
        updateSize();
    }

    /*Ordena los lanes del Pool de acuerdo a su índice*/
    public function sortLanes() {
        var ls = Sequences.sort(lanes, c);
        delete lanes;
        for (ele in ls) {
            var l = ele as Lane;
            insert l into lanes;
        }
        updateSize();
    }
    
    /**Agrega un Lane al Pool*/
    public function addLane():Lane
    {
        var lane=Lane
        {
            title:"Lane"
            modeler:modeler;
            uri:"new:Lane:{ToolBar.counter++}"
            w:bind w
            idx: lanes.size();
        };
        lane.setGraphParent(this);
        lane.setContainer(getContainer());
        insert lane into lanes;
        ModelerUtils.setResizeNode(null);
        resizeType=ResizeNode.RESIZE_H;
        updateSize();
        return lane;
    }

    /**Actualiza el tamaño del Pool de acuerdo al tamaño de los lanes*/
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

    /**Obtiene el Lane con el URI especificado*/
    public function getLaneByURI(uri:String):GraphicalElement
    {
        for(lane in lanes)
        {
            //println("{lane.uri}={uri}");
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
        if (link instanceof AssociationFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError17");
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

    /**Elimina todos los elementos adheridos al Pool*/
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

    override public function getDefaultFlow() : ConnectionObject {
        var link = MessageFlow {
            modeler:modeler
            uri:"new:messageflow:{modeler.toolBar.counter++}"
        }
        return link;
    }

    public override function copy () : GraphicalElement {
        var t = Pool {
            title: this.title
            description: this.description
            modeler: this.modeler
            w: this.w
            h: this.h
            uri:"new:pool:{modeler.toolBar.counter++}"
        }

        for (lane in lanes) {
            var l = Lane {
                title: lane.title
                description: lane.description
                modeler: this.modeler
                w: bind t.w
                h: lane.h
                uri: "new:lane:{modeler.toolBar.counter++}"
                idx: lane.idx
            };

            l.setGraphParent(t);
            l.setContainer(getContainer());
            insert l into t.lanes;
            
        }
        t.updateSize();

        var conObjects: ConnectionObject[];
        var objMap = HashMap {};

        for (ele in graphChilds where not (ele instanceof Lane)) {
            var tc = ele.copy();
            tc.x = ele.x;
            tc.y = ele.y;
            tc.w = ele.w;
            tc.h = ele.h;
            tc.setGraphParent(t);
            objMap.put(ele, tc);
            for (co in ele.getInputConnectionObjects()) {
                if (Sequences.indexOf(conObjects, co) == -1) {
                    insert co into conObjects;
                }
            }

            for (co in ele.getOutputConnectionObjects()) {
                if (Sequences.indexOf(conObjects, co) == -1) {
                    insert co into conObjects;
                }
            }
        }

        for(co in conObjects) {
            var cini = objMap.get(co.ini) as GraphicalElement;
            var cend = objMap.get(co.end) as GraphicalElement;
            var cop = co.copy();
            cop.ini = cini;
            cop.end = cend;
            insert cop into modeler.contents;
        }

        return t;
    }

    override public function getBounds() : Bounds {
        return Bounds {
            topLeft: Point {
                x: x - w / 2
                y: y - h / 2
            },
            bottomRight: Point {
                x: x - w / 2 +w
                y: y - h / 2 +h
            }
        }
    }
}