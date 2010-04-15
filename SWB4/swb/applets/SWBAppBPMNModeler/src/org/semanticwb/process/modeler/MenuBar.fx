/*
 * Menus.fx
 *
 * Created on 2/04/2010, 10:58:44 PM
 */

package org.semanticwb.process.modeler;

/**
 * @author javier.solis
 */

import javax.swing.JMenuBar;
import javafx.ext.swing.SwingComponent;
import org.semanticwb.process.modeler.*;
import javafx.scene.Cursor;

public class MenuBar extends SwingComponent{
    var menuBar: JMenuBar;

    public var menus:Menu[] on replace{
        for (m in menus){
            menuBar.add(m.menu);
        }
    }

    public override function createJComponent(){
        cursor=Cursor.DEFAULT;
        menuBar = new JMenuBar();
        return menuBar;
    }

}