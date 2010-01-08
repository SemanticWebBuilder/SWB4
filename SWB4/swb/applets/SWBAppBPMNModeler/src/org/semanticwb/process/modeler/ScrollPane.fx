/*
 * ScrollPane.fx
 *
 * Created on 27/12/2009, 12:35:25 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Resizable;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * @author Sergey A. Malenkov
 */

public class ScrollPane extends Resizable, CustomNode
{
  public var background: Paint;
  public var border: Number;
  public var content: Node;

  def bounds = Bounds {
    content: bind content
  }

  def w: Number = bind bounds.width + border + border + vsb.width;
  override function getPrefWidth(height) {w}

  def h: Number = bind bounds.height + border + border + hsb.height;
  override function getPrefHeight(width) {h}

  def vsb: ScrollBar = ScrollBar {
    min: 0
    max: bind if (h > height) h - height else 0
    visible: bind h > height
    vertical: true
    height: bind height
  }
  def hsb: ScrollBar = ScrollBar {
    min: 0
    max: bind if (w > width) w - width else 0
    visible: bind w > width
    width: bind if (vsb.visible)
      then width - vsb.width
      else width
  }
  override function create() {
    Group {
      clip: Rectangle {
        width: bind width
        height: bind height
      }
      content: [
        Rectangle {
          fill: bind background
          width: bind width
          height: bind height
        }
        Group {
          translateX: bind border - bounds.x - hsb.value
          translateY: bind border - bounds.y - vsb.value
          content: bind content
        }
        Group {
          translateX: bind width - vsb.width
          content: vsb
        }
        Group {
          translateY: bind height - hsb.height
          content: hsb
        }
      ]
      onMouseWheelMoved: function(event) {
        vsb.value += event.wheelRotation * 10
      }
    }
  }
}


