/*
 * StartEvent.fx
 *
 * Created on 13/02/2010, 11:29:53 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import org.semanticwb.process.modeler.AdhocSubProcess;
import org.semanticwb.process.modeler.MultipleStartEvent;
import javafx.scene.input.MouseEvent;
import org.semanticwb.process.modeler.MessageStartEvent;

/**
 * @author javier.solis
 */

public class StartEvent extends CatchEvent
{
    public override var over on replace {
        if (over and not selected) {
            shape.styleClass = "startEventHover";
        } else if (not selected) {
            shape.styleClass = "startEvent";
        }
    }

    public override var selected on replace {
        if (selected) {
            shape.styleClass = "startEventFocused";
        } else {
            shape.styleClass = "startEvent";
        }
    }

    public override function create(): Node
    {
         var ret=super.create();
         colorAdjust.hue=0.52;
         colorAdjust.brightness=-0.19;
         colorAdjust.contrast=0.25;
         colorAdjust.saturation=1;
         shape.styleClass = "startEvent";

        var actions : MenuItem[] = [
            MenuItem {
                status: bind if (getContainer() != null and getContainer() instanceof EventSubProcess) MenuItem.STATUS_ENABLED else MenuItem.STATUS_DISABLED
                caption: bind if (isInterrupting) ##"nonInterrupting" else ##"interrupting"
                action: function (e: MouseEvent) {
                    isInterrupting = not isInterrupting;
                    ModelerUtils.popup.hide();
                }
            },
            MenuItem {
                caption: ##"actType"
                items: [
                    MenuItem {
                        caption: ##"actMessage"
                        //Redefinir condición para evitar cambios en subprocesos
                        status: bind if (this.container != null or this instanceof MessageStartEvent) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = MessageStartEvent {
                                modeler: modeler
                                title: _title
                                uri:"new:startevent:{this.modeler.toolBar.counter++}"
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
                            sp.w = w;
                            sp.h = h;
                            sp.setGraphParent(getGraphParent());
                            modeler.add(sp);
                            remove(true);
                        }
                    },
                    MenuItem {
                        caption: ##"actTimer"
                        status: bind if (this.container != null or this instanceof MessageStartEvent) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = TimerStartEvent {
                                modeler: modeler
                                title: _title
                                uri:"new:startevent:{this.modeler.toolBar.counter++}"
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
                            sp.w = w;
                            sp.h = h;
                            sp.setGraphParent(getGraphParent());
                            modeler.add(sp);
                            remove(true);
                        }
                    },
                    MenuItem {
                        caption: ##"actRule"
                        status: bind if (this.container != null or this instanceof MessageStartEvent) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = RuleStartEvent {
                                modeler: modeler
                                title: _title
                                uri:"new:startevent:{this.modeler.toolBar.counter++}"
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
                            sp.w = w;
                            sp.h = h;
                            sp.setGraphParent(getGraphParent());
                            modeler.add(sp);
                            remove(true);
                        }
                    },
                    MenuItem {
                        caption: ##"actSignal"
                        status: bind if (this.container != null or this instanceof MessageStartEvent) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = SignalStartEvent {
                                modeler: modeler
                                title: _title
                                uri:"new:startevent:{this.modeler.toolBar.counter++}"
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
                            sp.w = w;
                            sp.h = h;
                            sp.setGraphParent(getGraphParent());
                            modeler.add(sp);
                            remove(true);
                        }
                    },
                    MenuItem {
                        caption: ##"actMultiple"
                        status: bind if (this.container != null or this instanceof MessageStartEvent) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = MultipleStartEvent {
                                modeler: modeler
                                title: _title
                                uri:"new:startevent:{this.modeler.toolBar.counter++}"
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
                            sp.w = w;
                            sp.h = h;
                            sp.setGraphParent(getGraphParent());
                            modeler.add(sp);
                            remove(true);
                        }
                    },
                    MenuItem {
                        caption: ##"actParallel"
                        status: bind if (this.container != null or this instanceof MessageStartEvent) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = ParallelStartEvent {
                                modeler: modeler
                                title: _title
                                uri:"new:startevent:{this.modeler.toolBar.counter++}"
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
                            sp.w = w;
                            sp.h = h;
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
                    var t = copy();
                    modeler.setCopyNode(t);
                    ModelerUtils.popup.hide();
                }
            }
        ];

        insert actions before menuOptions[0];

        return ret;
    }

    override public function canEndLink(link:ConnectionObject) : Boolean
    {
        var ret = super.canEndLink(link);

        if (link instanceof SequenceFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError33");
        }

        if (link instanceof MessageFlow and not (this instanceof MultipleStartEvent)) {
            ModelerUtils.setErrorMessage(##"msgError34");
            ret = false;
        }
        return ret;
    }

    override public function canStartLink(link:ConnectionObject) : Boolean
    {
        var ret = super.canStartLink(link);
        
        if(link instanceof MessageFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError35");
        }
        return ret;
    }

    override public function canAddToDiagram(): Boolean {
        var ret = super.canAddToDiagram();
        var c = 0;
        
        if (modeler.containerElement != null) {
            for (child in modeler.containerElement.containerChilds) {
                if (child instanceof StartEvent) {
                    c++;
                }
            }
            
            if (modeler.containerElement instanceof EventSubProcess) {
                ret = false;
                if (c != 0) {
                    ModelerUtils.setErrorMessage(##"msgError44");
                } else {
                    ModelerUtils.setErrorMessage(##"msgError41");
                }
            } else if (modeler.containerElement instanceof AdhocSubProcess) {
                ret = false;
                ModelerUtils.setErrorMessage(##"msgError48");
            } else if (c != 0) {
                ret = false;
                ModelerUtils.setErrorMessage(##"msgError44");
            }
        }
        return ret;
    }

    override public function copy() : GraphicalElement {
        var t = StartEvent {
            title: this.title
            type: this.type
            modeler: this.modeler
            container: this.container
            x: this.x + 10
            y: this.y + 10
            uri: "new:event:{modeler.toolBar.counter++}"
        }
        return t;
    }
}