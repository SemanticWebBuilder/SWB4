/*
 * MenuItem.fx
 *
 * Created on 2/04/2010, 11:04:10 PM
 */

package org.semanticwb.process.modeler;

/**
 * @author javier.solis
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.ext.swing.SwingComponent;
import javax.swing.JMenuItem;
import javafx.scene.Cursor;

public class MenuItem extends SwingComponent{

    protected var menuItem: JMenuItem;

    public var text: String on replace{
        menuItem.setText(text);
    }

    public var action: function();

    public override function createJComponent(){
        cursor=Cursor.DEFAULT;
        menuItem = new JMenuItem();
        menuItem.addActionListener(
            ActionListener{
                public override function actionPerformed(e:ActionEvent){
                    println("menus: action");
                    action();
                }
            }
        );
        return menuItem;
    }
}
