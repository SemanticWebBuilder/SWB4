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

/**
 * @author javier.solis
 */

public def TYPE_TRANSACTION="transaction";
public def TYPE_EVENT="event";
public def TYPE_ADHOC="adhoc";
public class SubProcess extends Activity
{    
    protected var isMultiInstance: Boolean = false;
    protected var isLoop: Boolean = false;
    protected var isForCompensation: Boolean = false;
    protected var isAdHoc: Boolean = false;
    protected var isTransaction: Boolean = false;
    protected var strokeDash : Float[];
    var icons: ImageView[];
    var adjust: ColorAdjust = ColorAdjust {
        hue: -0.83
        brightness: -0.07
        contrast: 0.25
        saturation: 1
    }

    def imgCollapsed = ImageView {
        image: Image {
            url: "{__DIR__}images/n_collapsed.png"
        }
        effect: adjust
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

    public override function create(): Node
    {
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
        };

        setType(type);

        var trans;
        if (type.equals(TYPE_TRANSACTION)) {
            isTransaction = true;
            trans = Rectangle
            {
                x: bind x - w / 2 + 3
                y: bind y - h / 2 + 3
                width: bind w - 6
                height: bind h - 6
                stroke: bind shape.stroke
                styleClass: "task"
            }
        }

        if (not isTransaction) {
            var actions: Action[] = [
                Action {
                    label: ModelerUtils.getLocalizedString("actMultiInstance");
                    status: bind if (isMultiInstance) MenuItem.STATUS_SELECTED else MenuItem.STATUS_ENABLED
                    action: function (e: MouseEvent) {
                        this.setModifier(TYPE_MULTIPLE);
                    }
                },
                Action {
                    label: ModelerUtils.getLocalizedString("actLoop");
                    status: bind if (isLoop) MenuItem.STATUS_SELECTED else MenuItem.STATUS_ENABLED
                    action: function (e: MouseEvent) {
                        this.setModifier(TYPE_LOOP);
                    }
                },
                Action {
                    label: ModelerUtils.getLocalizedString("actCompensa");
                    status: bind if (isForCompensation) MenuItem.STATUS_SELECTED else MenuItem.STATUS_ENABLED
                    action: function (e: MouseEvent) {
                        this.setModifier(TYPE_COMPENSATION);
                    }
                },
                Action {isSeparator: true}
            ];
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

    override var onMouseClicked = function (e: MouseEvent): Void {
        if (e.button == e.button.PRIMARY) {
            if (e.clickCount >= 2) {
                if (e.sceneX > text.boundsInParent.minX and e.sceneX < text.boundsInParent.maxX) {
                    if (e.sceneY > text.boundsInParent.minY and e.sceneY < text.boundsInParent.maxY) {
                        text.startEditing()
                    } else {
                        if(containerable)
                        {
                            modeler.containerElement=this;
                        }
                    }
                }
            }
        } else if (e.button == e.button.SECONDARY) {
            if (modeler.getFocusedNode() == this) {
                ModelerUtils.stopToolTip();
                ModelerUtils.popup.setOptions(menuOptions);
                ModelerUtils.popup.show(e);
            }
        }
    }

    public function setModifier(modif: String): Void {
        if(modif.equals(TYPE_COMPENSATION)) {
            isForCompensation = not isForCompensation;
        } else if(modif.equals(TYPE_LOOP)) {
            isLoop = not isLoop;

            if (isLoop) {
                if (isMultiInstance) {
                    isMultiInstance = false;
                }
            }
        } else if(modif.equals(TYPE_MULTIPLE)) {
            isMultiInstance = not isMultiInstance;

            if (isMultiInstance) {
                if (isLoop) {
                    isLoop = false;
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
        }

        if (isForCompensation) {
            insert imgComp into icons;
        }
        insert imgCollapsed into icons;
    }

    override public function remove(validate:Boolean)
    {
        //TODO: Internacionalizar mensaje
        if(not validate or sizeof containerChilds == 0 or Alert.confirm("Remove {this}", "Are you sure you want to delete \"{this.title}\" {this}?"))
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
}