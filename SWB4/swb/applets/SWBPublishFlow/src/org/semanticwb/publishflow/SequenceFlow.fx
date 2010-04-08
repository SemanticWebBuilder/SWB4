/*
 * SequenceFlow.fx
 *
 * Created on 26/02/2010, 12:45:00 PM
 */

package org.semanticwb.publishflow;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;

/**
 * @author victor.lorenzana
 */

public class SequenceFlow extends ConnectionObject
{


    public override function mousePressed(e: MouseEvent) : Void
    {
        //println("onMouseClicked node:{e}");
        if(e.clickCount >= 2 and e.button==MouseButton.PRIMARY)
        {
               
        }
    }
}
