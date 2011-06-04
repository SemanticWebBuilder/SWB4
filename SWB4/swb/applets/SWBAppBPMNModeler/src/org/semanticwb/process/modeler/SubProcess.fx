/*
 * SubProcess.fx
 *
 * Created on 13/02/2010, 11:21:05 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;
import javafx.stage.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.geometry.VPos;
import javafx.scene.layout.HBox;
import javafx.util.Sequences;
import java.util.HashMap;

/**
 * @author javier.solis
 */

public def TYPE_TRANSACTION="transaction";
public def TYPE_EVENT="event";
public def TYPE_ADHOC="adhoc";
public class SubProcess extends Activity
{
    protected var strokeDash : Float[];
    var icons: ImageView[];
    var adjust: ColorAdjust = ColorAdjust {
        hue: -0.83
        brightness: -0.07
        contrast: 0.25
        saturation: 1
    }
    
    def imgCollapsed = ImageView {
        blocksMouse: true
        image: Image {
            url: "{__DIR__}images/n_collapsed.png"
        }
        effect: adjust,
        onMouseClicked:function(e: MouseEvent): Void {
            if (e.clickCount >= 2 and containerable) {
                modeler.containerElement=this;
                modeler.unselectAll();
            }
        },
    }

    def imgLoop = ImageView {
        image: Image {
            url: "{__DIR__}images/n_ciclo.png"
        }
        effect: adjust
    }

    def imgMulti = ImageView {
        image: Image {
            url: "{__DIR__}images/n_objeto.png"
        }
        effect: adjust
    }

    def imgMultiSeq = ImageView {
        image: Image {
            url: "{__DIR__}images/n_multi_seq.png"
        }
        effect: adjust
    }

    def imgComp = ImageView {
        image: Image {
            url: "{__DIR__}images/n_compensa_b.png"
        }
        effect: adjust
    }

    def imgAdHoc = ImageView {
        image: Image {
            url: "{__DIR__}images/n_adhoc.png"
        }
        effect: adjust
    }

    var modifiers: HBox = HBox {
        nodeVPos: VPos.CENTER
        translateX: bind shape.boundsInLocal.minX + (shape.boundsInLocal.width - modifiers.boundsInLocal.width) / 2
        translateY: bind shape.boundsInLocal.minY + shape.boundsInLocal.height - (modifiers.boundsInLocal.height + 6)
        spacing: 5
        content: bind icons
    }

    var loop=bind isLoop on replace
    {
        setModifier(TYPE_LOOP, isLoop);
    }

    var compensation=bind isForCompensation on replace
    {
        setModifier(TYPE_COMPENSATION, isForCompensation);
    }

    var multiple=bind isMultiInstance on replace
    {
        setModifier(TYPE_MULTIPLE, isMultiInstance);
    }

    var multipleseq=bind isSequentialMultiInstance on replace
    {
        setModifier(TYPE_MULTIPLE_SEQUENTIAL, isSequentialMultiInstance);
    }
    var trans: Rectangle;

    public override function create(): Node
    {
        blocksMouse=true;
        resizeable=true;
        containerable=true;
        w=100;
        h=60;
        initializeCustomNode();

        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y - h/6
            width: bind w
            height: bind h/2
        }

        shape= Rectangle
        {
            x: bind x-w/2
            y: bind y-h/2
            width: bind w
            height: bind h
            strokeDashArray: bind strokeDash
            styleClass: "task"
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
        };

        setType(type);

        if (type.equals(TYPE_TRANSACTION)) {
            isTransaction = true;
            trans = Rectangle
            {
                x: bind x - w / 2 + 3
                y: bind y - h / 2 + 3
                width: bind w - 6
                height: bind h - 6
                stroke: bind shape.stroke
                styleClass: bind shape.styleClass
            }
        }

        var actions : MenuItem[] = null;
        if (not isTransaction) {
            actions = [
                MenuItem {
                    caption: ##"actBehavior"
                    items: [
                        MenuItem {
                            caption: ##"actMultiInstance";
                            status: bind if (isMultiInstance) MenuItem.STATUS_SELECTED else MenuItem.STATUS_ENABLED
                            action: function (e: MouseEvent) {
                                this.setModifier(TYPE_MULTIPLE, not isMultiInstance);
                                ModelerUtils.popup.hide();
                            }
                        },
                        MenuItem {
                            caption: ##"actMultiInstanceSeq";
                            status: bind if (isSequentialMultiInstance) MenuItem.STATUS_SELECTED else MenuItem.STATUS_ENABLED
                            action: function (e: MouseEvent) {
                                this.setModifier(TYPE_MULTIPLE_SEQUENTIAL, not isSequentialMultiInstance);
                                ModelerUtils.popup.hide();
                            }
                        },
                        MenuItem {
                            caption: ##"actLoop";
                            status: bind if (isLoop) MenuItem.STATUS_SELECTED else MenuItem.STATUS_ENABLED
                            action: function (e: MouseEvent) {
                                this.setModifier(TYPE_LOOP, not isLoop);
                                ModelerUtils.popup.hide();
                            }
                        },
                        MenuItem {
                            caption: ##"actCompensa";
                            status: bind if (isForCompensation) MenuItem.STATUS_SELECTED else MenuItem.STATUS_ENABLED
                            action: function (e: MouseEvent) {
                                this.setModifier(TYPE_COMPENSATION, not isForCompensation);
                                ModelerUtils.popup.hide();
                            }
                        }
                    ]
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
        } else {
            actions = [
                MenuItem {
                    caption: ##"actCopy"
                    action: function(e: MouseEvent) {
                        var t = copy();
                        modeler.setCopyNode(t);
                        ModelerUtils.popup.hide();
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
        }
        if (actions != null) {
            insert actions before menuOptions[0];
        }

        getMarkers();

        return Group
        {
            content: [
                shape, trans, text, modifiers
            ]
            scaleX: bind s;
            scaleY: bind s;
            visible:bind canView()            
        };
    }

//    override var onMouseClicked = function (e: MouseEvent): Void {
//        selected = true;
//        if (e.button == e.button.PRIMARY) {
//            if (e.clickCount >= 2) {
//                if (not (modeler.containerElement == this)) {
//                    text.startEditing()
//                }
//            }
//        } else if (e.button == e.button.SECONDARY) {
//            if (modeler.getFocusedNode() == this) {
//                ModelerUtils.stopToolTip();
//                ModelerUtils.popup.setOptions(menuOptions);
//                ModelerUtils.popup.show(e);
//            }
//        }
//        modeler.onMouseClicked(e);
//    }

    public function setModifier(modif: String, val:Boolean): Void {
        if(modif.equals(TYPE_COMPENSATION)) {
            isForCompensation = val;
        } else if(modif.equals(TYPE_LOOP)) {
            isLoop = val;

            if (isLoop) {
                if (isMultiInstance) {
                    isMultiInstance = false;
                }
                if (isSequentialMultiInstance) {
                    isSequentialMultiInstance = false;
                }
            }
        } else if(modif.equals(TYPE_MULTIPLE)) {
            isMultiInstance = val;

            if (isMultiInstance) {
                if (isLoop) {
                    isLoop = false;
                }
                if (isSequentialMultiInstance) {
                    isSequentialMultiInstance = false;
                }
            }
        } else if (modif.equals(TYPE_MULTIPLE_SEQUENTIAL)) {
            isSequentialMultiInstance = val;

            if (isSequentialMultiInstance) {
                if (isLoop) {
                    isLoop = false;
                }
                if (isMultiInstance) {
                    isMultiInstance = false;
                }
            }
        }
        getMarkers();
    }

    function getMarkers() : Void {
        delete icons;

        if (isAdHoc) {
            insert imgAdHoc into icons;
        }

        if (isLoop) {
            insert imgLoop into icons;
        } else if (isMultiInstance) {
            insert imgMulti into icons;
        } else if (isSequentialMultiInstance) {
            insert imgMultiSeq into icons;
        }

        if (isForCompensation) {
            insert imgComp into icons;
        }
        insert imgCollapsed into icons;
    }

    override public function remove(validate:Boolean)
    {
        //TODO: Internacionalizar mensaje
        if(not validate or sizeof containerChilds == 0 or Alert.confirm(##"actDelete", ##[confirmDelete]"\"{this.title}\""))
        {
            for(child in containerChilds)
            {
                child.remove(false);
            }
            delete containerChilds;

            super.remove(validate);
        }
    }

    override function setType(type: String) {
        if (type.equals(TYPE_ADHOC)) {
            isAdHoc = true;
        }

        if (type.equals(TYPE_EVENT)) {
            strokeDash = [1, 6];
        }
    }

    public override function copy() : GraphicalElement {
        var t = SubProcess {
            title: this.title
            description: this.description
            type: this.type
            modeler: this.modeler
            isLoop: this.isLoop
            isForCompensation: this.isForCompensation
            isMultiInstance: this.isMultiInstance
            container: this.container
            x: this.x + 10
            y: this.y + 10
            uri:"new:{type}subprocess:{modeler.toolBar.counter++}"
        }
        t.setLabelSize(this.text.size);
        t.w = this.w;
        t.h = this.h;
        var conObjects: ConnectionObject[];
        var objMap = HashMap {};

        for (ele in containerChilds where ele instanceof GraphicalElement) {
            var tc = ele.copy();
            tc.x = ele.x;
            tc.y = ele.y;
            tc.w = ele.w;
            tc.h = ele.h;
            tc.setContainer(t);
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

//    override public function getXPDLDefinition(doc: Document) : Element {
//        var ret = doc.createElement("ActivitySet");
//        var activities = doc.createElement("Activities");
//        var graphicInfos = getGraphicsInfos(doc);
//
//        ret.appendChild(graphicInfos);
//        ret.setAttribute("Id", uri);
//        ret.setAttribute("Name", "{title}");
//
//        if (type.equals(TYPE_ADHOC)) {
//            ret.setAttribute("AdHoc", "true");
//            ret.setAttribute("AdHocOrdering", "Parallel");
//        }
//
//        for (ele in getContainerChilds() where ele instanceof GraphicalElement) {
//            var activity = (ele as GraphicalElement).getXPDLDefinition(doc);
//            activities.appendChild(activity);
//        }
//
//        return ret;
//    }
}