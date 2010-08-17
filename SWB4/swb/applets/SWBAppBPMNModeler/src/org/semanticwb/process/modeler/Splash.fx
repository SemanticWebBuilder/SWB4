/*
 * Splash.fx
 *
 * Created on 5/03/2010, 01:46:17 PM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import org.semanticwb.process.modeler.ModelerUtils;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;

/**
 * @author javier.solis
 */

public class Splash extends CustomNode
{
    public var x:Number;
    public var y:Number;
    public var text:String;
    
    var _root: Group;
    var glowColor = Color.TRANSPARENT;
    override var opacity = 0.0;

    def ai=Image{url: "{__DIR__}images/splash.jpg"};
    def timeLine: Timeline = Timeline {
        keyFrames: [
            at (0s) {
                glowColor => Color.TRANSPARENT;
                opacity => 0.0;
            },
            at (800ms) {
                glowColor => Color.color(0.5, 0.5, 0.5, 0.5);
                opacity => 1.0 tween Interpolator.EASEBOTH;
            }
        ]
    }

    protected var img=ImageView
    {
        x: bind x-ai.width/2
        y: bind y-ai.height/2
        image:ai
        smooth: false
    };

    var f=bind focused on replace
    {
        if(not f)visible=false;
    }
    
    function initializeCustomNode():Void 
    {
        var t:Text;
        _root=Group{
            content: [
                img,
                Text {
                    x:bind x+110
                    y:bind y+35
                    content:bind "{ModelerUtils.getLocalizedString("version")}: {ModelerUtils.version}"
                    styleClass: "splash"
                }
            ]
        }
    }

    protected override function create() : Node {
        if (_root == null) {
            initializeCustomNode();
        }
        return _root;
    }

    public function showDialog(x:Number,y:Number)
    {
        this.x=x;
        this.y=y;
        visible=true;
        timeLine.playFromStart();
        requestFocus();
    }
}
