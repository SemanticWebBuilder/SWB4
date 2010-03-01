/*
 * TabPanelCustom.fx
 *
 * Created on 1/03/2010, 01:27:01 PM
 */

package org.semanticwb.publishflow;

import javafx.scene.CustomNode;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import java.lang.IllegalArgumentException;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import org.semanticwb.publishflow.TabCustom;



/**
 * @author victor.lorenzana
 */

public class TabPanelCustom extends CustomNode {

    var tabs : TabCustom[];
    var activeTab : Integer = -1;
    public var width : Number = 200 on replace oldWidth {
            if(width < 0) {
                throw new IllegalArgumentException("can't have negative width!");
            }
        };
    public var height : Number = 200 on replace oldHeight {
            if(height < 0) {
                throw new IllegalArgumentException("can't have negative height!");
            }
        };

    public function addTab(tab : TabCustom, active : Boolean) : Void {
        insert tab into tabs;
        if(activeTab == -1) {
            activeTab = 0;
        }
        if(active) {
            tabs[activeTab].setActive(false);
            activeTab = sizeof tabs - 1;//the tab being added
            tabs[activeTab].setActive(true);
        }
    }

    function renderPanel() : Node {
        if(activeTab != -1){
            var contents = tabs[activeTab];
            var tabPanel = Group {
                content:
                        VBox {
                            width: width
                            height: height
                            content:[
                                    HBox {
                                         content: tabs
                                    },
                                    ]
                        }
            };

            return tabPanel;
        } else {
            return Text {
                      font : Font {
                          size: 24
                      }
                      x: 10, y: 30
                      content: "No tabs yet"
                  };
        }

    }

    public override function create(): Node {
        return Group {
            content: renderPanel()
        };
    }
}

