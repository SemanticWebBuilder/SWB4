/*
 * EndEvent.fx
 *
 * Created on 13/02/2010, 11:32:09 AM
 */

package org.semanticwb.process.modeler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * @author javier.solis
 */

public class EndEvent extends ThrowEvent
{
    public override var over on replace {
        if (over and not selected) {
            shape.styleClass = "endEventHover";
        } else if (not selected) {
            shape.styleClass = "endEvent";
        }
    }

    public override var selected on replace {
        if (selected) {
            shape.styleClass = "endEventFocused";
        } else {
            shape.styleClass = "endEvent";
        }
    }
    
    public override function create(): Node
    {
         var ret=super.create();
         colorAdjust.hue=-0.03;
         colorAdjust.brightness=-0.33;
         colorAdjust.contrast=0.25;
         colorAdjust.saturation=1;
         shape.styleClass = "endEvent";

         var actions: MenuItem[] = [
             MenuItem {
                caption: ##"actType"
                items: [
                    MenuItem {
                        caption: ##"actMessage"
                        //Redefinir condición para evitar cambios en subprocesos
                        status: bind if (this instanceof MessageEndEvent) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = MessageEndEvent {
                                modeler: modeler
                                title: _title
                                uri:"new:endtevent:{this.modeler.toolBar.counter++}"
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
                        status: bind if (this instanceof MessageEndEvent) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = SignalEndEvent {
                                modeler: modeler
                                title: _title
                                uri:"new:endevent:{this.modeler.toolBar.counter++}"
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
                        caption: ##"actError"
                        status: bind if (this instanceof MessageEndEvent) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = ErrorEndEvent {
                                modeler: modeler
                                title: _title
                                uri:"new:endevent:{this.modeler.toolBar.counter++}"
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
                        caption: ##"actCancel"
                        status: bind if (this instanceof MessageEndEvent) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = CancelationEndEvent {
                                modeler: modeler
                                title: _title
                                uri:"new:endevent:{this.modeler.toolBar.counter++}"
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
                        caption: ##"actCompensa"
                        status: bind if (this instanceof MessageEndEvent) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = CompensationEndEvent {
                                modeler: modeler
                                title: _title
                                uri:"new:endevent:{this.modeler.toolBar.counter++}"
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
                        status: bind if (this instanceof MessageEndEvent) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = MultipleEndEvent {
                                modeler: modeler
                                title: _title
                                uri:"new:endevent:{this.modeler.toolBar.counter++}"
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
                        caption: ##"actEscalate"
                        status: bind if (this instanceof MessageEndEvent) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = ScalationEndEvent {
                                modeler: modeler
                                title: _title
                                uri:"new:endevent:{this.modeler.toolBar.counter++}"
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
                        caption: ##"actTerminate"
                        status: bind if (this instanceof MessageEndEvent) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = TerminationEndEvent {
                                modeler: modeler
                                title: _title
                                uri:"new:endevent:{this.modeler.toolBar.counter++}"
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
                action: function (e: MouseEvent) {
                    ModelerUtils.popup.hide();
                    var t = copy();
                    modeler.setCopyNode(t);
                }
             },
             MenuItem {
                caption: ##"actCut"
                action: function (e: MouseEvent) {
                    ModelerUtils.popup.hide();
                    var t = cut();
                    modeler.setCopyNode(t);
                    ModelerUtils.setResizeNode(null);
                }
             }
         ];
         insert actions before menuOptions[0];

         return ret;
    }

    override public function canStartLink(link:ConnectionObject) : Boolean
    {
        return false;
    }

    override public function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);
        if (not(link instanceof SequenceFlow)) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError8");
        } else if (link instanceof SequenceFlow and link.ini instanceof ExclusiveIntermediateEventGateway) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError9");
        }
        return ret;
    }

    override public function canAddToDiagram(): Boolean {
        var ret = super.canAddToDiagram();
        if (modeler.containerElement != null) {
            if (modeler.containerElement instanceof AdhocSubProcess) {
                ret = false;
                ModelerUtils.setErrorMessage(##"msgError49");
            }
        }
        return ret;
    }

    override public function copy() : GraphicalElement {
        var t = EndEvent {
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