/*
 * Artifact.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.shape.Polyline;
import javafx.scene.input.MouseEvent;
import org.semanticwb.process.modeler.InputArtifact;
import org.semanticwb.process.modeler.OutputArtifact;
import org.semanticwb.process.modeler.DataStoreArtifact;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import javafx.scene.image.Image;
import javafx.geometry.VPos;
import javafx.scene.layout.HBox;
import javafx.scene.effect.ColorAdjust;

public def TYPE_COLLECTION="datacollection";
public def TYPE_INPUT="datainput";
public def TYPE_OUTPUT="dataoutput";
public def TYPE_OBJECT="dataobject";
public def TYPE_STORE="datastore";
public def TYPE_ANNOTATION="annotationartifact";
public def TYPE_GROUP="groupartifact";
public class Artifact extends GraphicalElement
{
    public var isCollection: Boolean = false;
    protected var ix:Number;                          //offset imagen x
    protected var iy:Number;                          //offset imagen x
    protected var is:Number=1;                        //image scale
    var icons: ImageView[];

    def adjust: ColorAdjust = ColorAdjust {
        hue: -0.83
        brightness: -0.07
        contrast: 0.25
        saturation: 1
    }
    
    protected var message=ImageView
    {
        x: bind x+ix;
        y: bind y+iy;
        scaleX: bind is;
        scaleY: bind is;
        visible:false;
    };

    var modifiers: HBox = HBox {
        nodeVPos: VPos.CENTER
        translateX: bind this.boundsInLocal.minX + (this.boundsInLocal.width - modifiers.boundsInLocal.width) / 2
        translateY: bind this.boundsInLocal.minY + this.boundsInLocal.height - (modifiers.boundsInLocal.height + 6)
        spacing: 5
        content: bind icons
    }

    public override var over on replace {
        if (over and not selected) {
            shape.styleClass = "dataObjectHover";
        } else if (not selected) {
            shape.styleClass = "dataObject";
        }
    }

    public override var selected on replace {
        if (selected) {
            shape.styleClass = "dataObjectFocused";
        } else {
            shape.styleClass = "dataObject";
        }
    }

    def imgMulti = ImageView {
        image: Image {
            url: "{__DIR__}images/n_objeto.png"
        }
        effect: adjust
    }

    var collection = bind isCollection on replace {
        if (not isCollection) {
            delete icons;
        } else {
            insert imgMulti into icons;
        }
        //setModifier(TYPE_COLLECTION, isCollection);
    }

    override public function create(): Node
    {
        blocksMouse = true;
        setCommonMenuOptions();
        w=55;
        h=60;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y 
            width: bind w
            height: bind h
            styleClass: "dataObject"
            id: "label"
        }

        setType(type);

        shape=Polyline
        {
            points: [
                w-w/4,0,
                0,0,
                0,h,
                w,h,
                w,h/4,
                w-w/4,0,
            ]
            translateX:bind x-w/2
            translateY:bind y-h/2
            styleClass: "dataObject"
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
        };

        var actions: MenuItem[] = [
            MenuItem {
                caption: ##"actType"
                items: [
                    MenuItem {
                        caption: ##"actCollection"
                        status: bind if (isCollection) MenuItem.STATUS_SELECTED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            this.isCollection = not isCollection;
//                            var _title = title;
//                            //crear nuevo elemento
//                            var sp = Artifact {
//                                modeler: modeler
//                                title: _title
//                                isCollection: true;
//                                uri:"new:datacollection:{this.modeler.toolBar.counter++}"
//                            }
//                            //pasar las entradas al nuevo elemento
//                            for(ele in getInputConnectionObjects()) {
//                                ele.end = sp;
//                            }
//
//                            for (ele in getOutputConnectionObjects()) {
//                                ele.ini = sp;
//                            }
//
//                            sp.x = x;
//                            sp.y = y;
//                            sp.container = container;
//                            sp.setGraphParent(getGraphParent());
//                            modeler.add(sp);
//                            remove(true);
                        }
                    },
                    MenuItem {
                        caption: ##"actInput"
                        status: bind if (this instanceof InputArtifact) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = InputArtifact {
                                modeler: modeler
                                title: _title
                                isCollection: isCollection
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
                        status: bind if (this instanceof OutputArtifact) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = OutputArtifact {
                                modeler: modeler
                                title: _title
                                isCollection: isCollection
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
                        status: bind if (this instanceof DataStoreArtifact) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            //crear nuevo elemento
                            var sp = DataStoreArtifact {
                                modeler: modeler
                                title: _title
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

        return Group
        {
            content: [
                shape,
                Polyline{
                    points: [
                        w-w/4,0,
                        w-w/4,h/4,
                        w,h/4,
                    ]
                    translateX:bind x-w/2
                    translateY:bind y-h/2
                    stroke:bind shape.stroke
                    strokeWidth:bind shape.strokeWidth
                },
                message,text,modifiers
            ]
            scaleX: bind s;
            scaleY: bind s;
            visible: bind canView()
        };
    }    

    public override function setType(type:String):Void
    {
        super.setType(type);
        message.visible=true;
        if(type.equals(TYPE_INPUT))
        {
            message.styleClass = "modifierLinkCatch";
            ix=-25;
            iy=-30;
            is=.7;
        }else if(type.equals(TYPE_OUTPUT))
        {
            message.styleClass = "modifierLinkThrow";
            ix=-25;
            iy=-30;
            is=.7;
        } //else if(type.equals(TYPE_COLLECTION)) {
//            message.styleClass = "modifierMultiInstance";
//            ix=-6;
//            iy=13;
//            is=.8;
        else {
        //} else {
             message.visible=false;
        }
    }

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
        } else if (link.ini instanceof Artifact) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError5");
        }
        return ret;
    }

    override public function copy() : GraphicalElement {
        var t = Artifact {
            title: this.title
            type: this.type
            modeler: this.modeler
            isCollection: isCollection
            isLoop: this.isLoop
            isForCompensation: this.isForCompensation
            isMultiInstance: this.isMultiInstance
            container: this.container
            x: this.x + 10
            y: this.y + 10
            uri: "new:{type}:{modeler.toolBar.counter++}"
        }
        return t;
    }

//    override public function getXPDLDefinition(doc: Document) : Element {
//        var ret = doc.createElement("Artifact");
//        var graphicInfos = getGraphicsInfos(doc);
//        ret.appendChild(graphicInfos);
//
//        ret.setAttribute("Id", uri);
//        ret.setAttribute("Name", "{title}");
//
//        if (type.equals(TYPE_OBJECT)) {
//            ret.setAttribute("ArtifactType", "DataObject");
//        }
//        if (type.equals(TYPE_GROUP)) {
//            ret.setAttribute("ArtifactType", "Group");
//        }
//        if (type.equals(TYPE_ANNOTATION)) {
//            ret.setAttribute("ArtifactType", "Annotation");
//            ret.setAttribute("TextAnnotation", "{title}");
//        }
//        return ret;
//    }
}