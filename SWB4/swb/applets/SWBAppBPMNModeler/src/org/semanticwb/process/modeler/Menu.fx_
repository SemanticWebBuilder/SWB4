/*
 * Menu.fx
 *
 * Created on 2/04/2010, 11:03:12 PM
 */

package org.semanticwb.process.modeler;

/**
 * @author javier.solis
 */

import javax.swing.JMenu;

import javafx.ext.swing.SwingComponent;
import org.semanticwb.process.modeler.*;
import javafx.scene.Cursor;

public class Menu extends SwingComponent{

    protected var menu: JMenu;

    public var text: String on replace{
        menu.setText(text);
    }

    public var items:MenuItem[] on replace{
        for (item in items){
            menu.add(item.menuItem);
        }
    }

    public var menus:Menu[] on replace{
        for (m in menus){
            menu.add(m.menu);
        }
    }

    public override function createJComponent(){
        menu = new JMenu();
        cursor=Cursor.DEFAULT;
        return menu;
    }
}

