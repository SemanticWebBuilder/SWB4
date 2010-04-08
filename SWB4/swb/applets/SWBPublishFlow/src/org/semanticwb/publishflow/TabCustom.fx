/*
 * TabCustom.fx
 *
 * Created on 1/03/2010, 01:27:38 PM
 */

package org.semanticwb.publishflow;



import javafx.scene.CustomNode;
import javafx.scene.Group;
import javafx.scene.Node;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 * @author victor.lorenzana
 */

public class TabCustom extends CustomNode {

    var darkGradient = LinearGradient {
             startX : 0.0
             startY : 0.0
             endX : 0.0
             endY : 1.0
             stops: [
                 Stop {
                     color : Color.rgb(230, 230, 230)
                     offset: 0.0
                 },

                 Stop {
                     color : Color.BLACK
                     offset: 0.6
                 },
                 Stop {
                     color : Color.BLACK
                     offset: 1.0
                 },

             ]
         };

     var lightGradient = LinearGradient {
                 startX : 0.0
                 startY : 0.0
                 endX : 0.0
                 endY : 1.0
                 stops: [
                     Stop {
                         color : Color.rgb(230, 230, 230)
                         offset: 0.0
                     },

                     Stop {
                         color : Color.rgb(100, 100, 100)
                         offset: 0.6
                     },
                     Stop {
                         color : Color.rgb(100, 100, 100)
                         offset: 1.0
                     },

                 ]
             };


     var tabText = Text {
         font : Font {
             size: 24
         }
         x: 20, y: 40
         content: "HelloWorld"

         fill: Color.WHITE;
     };

     var rectHover: Boolean = false;
     var rectActive: Boolean = false;

     var rect : Rectangle = Rectangle {
         x: 10, y: 10
         width: 140, height: 40
         arcHeight: 10, arcWidth: 10
         fill: lightGradient

           onMouseEntered: function(enter : MouseEvent) : Void {
               if(not rectHover) {
                    rect.fill = darkGradient;
                    rectHover = true;
               }
           }

           onMouseExited: function(exit : MouseEvent) : Void {
               if(rectHover and not rectActive) {
                   rect.fill = lightGradient;
                   rectHover = false;
               }
           }

           onMouseClicked: function(click : MouseEvent) : Void {
              setActive(not rectActive);
           }
     }

     var tabContents: Node;

     var myTab = Group {
         content: [
                rect, tabText
             ]
     }

     public function setActive(isActive : Boolean) : Void {
          rectActive = isActive;
           if(rectActive) {
               rect.fill = darkGradient;
           } else {
               rect.fill = lightGradient;
           }
     }


     public function setTabContents(content : Node) : Void {
        tabContents = content;
     }

     public function getTabContents() : Node {
         return tabContents;
     }



    public override function create(): Node {
        return Group {
            content: myTab
        };
    }
}

