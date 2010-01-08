/*
 * Bounds.fx
 *
 * Created on 27/12/2009, 12:38:01 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author Sergey A. Malenkov
 */

public class Bounds {
  public var content: Node;

  public-read var x: Number; //on replace old { println("x: {old} -> {x}") };
//  def cx = bind content.boundsInParent.minX on replace old
//  {
//    //println("cx: {old} -> {cx}");
//    if (cx != old)
//    {
//      x = cx
//    }
//  }

  public-read var y: Number;// on replace old { println("y: {old} -> {y}") };
//  def cy = bind content.boundsInParent.minY on replace old
//  {
//    //println("cy: {old} -> {cy}");
//    if (cy != old)
//    {
//      y = cy
//    }
//  }

  public-read var width: Number;// on replace old { println("width: {old} -> {width}") };
  def cwidth = bind content.boundsInParent.width on replace old
  {
    if (cwidth != old)
    {
      width = cwidth
    }
  }

  public-read var height: Number;// on replace old { println("height: {old} -> {height}") };
  def cheight = bind content.boundsInParent.height on replace old
  {
    //println("cheight: {old} -> {cheight}");
    if (cheight != old)
    {
      height = cheight
    }
  }
}

