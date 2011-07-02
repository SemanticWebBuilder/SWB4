/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * @author hasdai
 */

public class DataStore extends DataObject {
override public function create(): Node
    {
        blocksMouse = true;
        setCommonMenuOptions();
        w=60;
        h=50;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y + h/2 + text.height/2
            width: bind w + 50
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
            styleClass: "dataObject"
            translateX:bind x-w/2
            translateY:bind y-h/2
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
        }

        var actions: MenuItem[] = [
            MenuItem {
                caption: ##"actType"
                items: [
                    MenuItem {
                        caption: ##"actInput"
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            var _desc = description;
                            //crear nuevo elemento
                            var sp = DataInput {
                                modeler: modeler
                                title: _title
                                description: _desc
                                uri:"new:datainput:{this.modeler.toolBar.counter++}"
                            }
                            //pasar las entradas al nuevo elemento
                            for(ele in getInputConnectionObjects()) {
                                ele.end = sp;
                            }

                            for (ele in getOutputConnectionObjects()) {
                                ele.ini = sp;
                            }

                            sp.x = x;
                            sp.y = y;
                            sp.container = container;
                            sp.setGraphParent(getGraphParent());
                            modeler.add(sp);
                            remove(true);
                        }
                    },
                    MenuItem {
                        caption: ##"actOutput"
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            var _desc = description;
                            //crear nuevo elemento
                            var sp = DataOutput {
                                modeler: modeler
                                title: _title
                                description: _desc
                                uri:"new:dataoutput:{this.modeler.toolBar.counter++}"
                            }
                            //pasar las entradas al nuevo elemento
                            for(ele in getInputConnectionObjects()) {
                                ele.end = sp;
                            }

                            for (ele in getOutputConnectionObjects()) {
                                ele.ini = sp;
                            }

                            sp.x = x;
                            sp.y = y;
                            sp.container = container;
                            sp.setGraphParent(getGraphParent());
                            modeler.add(sp);
                            remove(true);
                        }
                    },
                    MenuItem {
                        caption: ##"actDataStore"
                        status: bind if (this instanceof DataStore) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            var _desc = description;
                            //crear nuevo elemento
                            var sp = DataStore {
                                modeler: modeler
                                title: _title
                                description: _desc
                                uri:"new:datastore:{this.modeler.toolBar.counter++}"
                            }
                            //pasar las entradas al nuevo elemento
                            for(ele in getInputConnectionObjects()) {
                                ele.end = sp;
                            }

                            for (ele in getOutputConnectionObjects()) {
                                ele.ini = sp;
                            }

                            sp.x = x;
                            sp.y = y;
                            sp.container = container;
                            sp.setGraphParent(getGraphParent());
                            modeler.add(sp);
                            remove(true);
                        }

                    },
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
        insert actions before menuOptions[0];

        setType(TYPE_STORE);

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
            visible: bind canView()
        };
    }

    override public function copy() : GraphicalElement {
        var t = DataStore {
            title: this.title
            description: this.description
            type: this.type
            modeler: this.modeler
            container: this.container
            x: this.x + 10
            y: this.y + 10
            uri: "new:{type}:{modeler.toolBar.counter++}"
        }
        return t;
    }
}
