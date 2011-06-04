/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;
import javafx.scene.Cursor;
import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.util.Math;
import java.lang.Object;
import java.util.Comparator;

public def xAscComparator: Comparator = Comparator {
   override public function compare (arg0 : Object, arg1 : Object) : Integer {
        var h1 = arg0 as LineHandle;
        var h2 = arg1 as LineHandle;

        var ret = 0;
        if (h1.x > h2.x) {
            ret = 1;
        } else if (h1.x == h2.x) {
            ret = 0;
        } else if (h1.x < h2.x) {
             ret = -1;
        }
        return ret;
   }
}

public def xDescComparator: Comparator = Comparator {
   override public function compare (arg0 : Object, arg1 : Object) : Integer {
        var h1 = arg0 as LineHandle;
        var h2 = arg1 as LineHandle;

        var ret = 0;
        if (h1.x > h2.x) {
            ret = -1;
        } else if (h1.x == h2.x) {
            ret = 0;
        } else if (h1.x < h2.x) {
             ret = 1;
        }
        return ret;
   }
}

/**
 * Clase que representa un nodo tirador de un objeto de conexión.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class LineHandle extends CustomNode {
    public var x: Number;
    public var y: Number;
    public var modeler: Modeler;
    public var useGrid:Boolean=true;
    public var owner: ConnectionObject;
    public var graphParent: GraphicalElement;
    var dx: Number;
    var dy: Number;
    var c: Circle;

    override public function create() : Node {
        blocksMouse = true;
        cursor = Cursor.CROSSHAIR;
        c = Circle {
            centerX: bind x
            centerY: bind y
            radius: 5
            styleClass: "lineHandle"
        }
        snapToGrid();
        return c;
    }

    override var onMousePressed = function (e: MouseEvent) {
        cursor = Cursor.MOVE;
        modeler.disablePannable=true;
        dx=x-e.sceneX;
        dy=y-e.sceneY;
    }

    override var onMouseReleased = function(e: MouseEvent) {
        cursor = Cursor.CROSSHAIR;
        setGraphParent();
        snapToGrid();
        modeler.setSelectedNode(owner);
    }

    override var onMouseDragged = function (e: MouseEvent) {
        var ax=dx+e.sceneX;
        var ay=dy+e.sceneY;
        if(ax-c.boundsInLocal.width/2>0)x=ax else x=c.boundsInLocal.width/2;
        if(ay-c.boundsInLocal.height/2>0)y=ay else y=c.boundsInLocal.height/2;
        owner.createPath();
        owner.updateStartPoint();
        owner.updateEndPoint();
    }

    override var onMouseClicked = function (e: MouseEvent) {
        if (e.button == e.button.SECONDARY) {
            owner.removeLineHandler(this);
        }
    }

    /**Ajusta las coordenadas del elemento a la rejilla*/
    public function snapToGrid()
    {
        if(useGrid)
        {
            x=(Math.round(x/25))*25;            //grid
            y=(Math.round(y/25))*25;            //grid
        }
    }

    /**Devuelve un objeto Point con las coordenadas del tirador*/
    public function getPoint() : Point {
        return Point {
            x: x
            y: y
        }
    }

    /**Obtiene el nodo sobre el cual se encuentra el elemento actual.*/
    public function getOverNode() : GraphicalElement {
        var overNode: GraphicalElement = null;
        for (ele in modeler.contents where ele instanceof Pool) {
            var t = ele as Pool;
            if (t.inBounds(this)) {
                overNode = t;
                for (lane in (t as Pool).lanes) {
                    if (lane.inBounds(this)) {
                        overNode = lane;
                    }
                }
            }
        }

        return overNode;
    }

    /**Establece el nodo padre del tirador*/
    public function setGraphParent() {
        graphParent = getOverNode();
    }

    /**Crea una copia del tirador*/
    public function copy() : LineHandle {
        return LineHandle {
            x: this.x
            y: this.y
            modeler: this.modeler
            owner: this.owner
            graphParent: this.graphParent
        }
    }
}