/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice.test;

import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.office.interfaces.PropertyInfo;
import org.semanticwb.openoffice.components.PanelPropertyEditor;

/**
 *
 * @author victor.lorenzana
 */
public class PanelPropertyEditorTest {

    public PanelPropertyEditorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
    public void propertyEditor()
     {

         JDialog dialog=new JDialog(new JFrame(),true);
         dialog.setLocationRelativeTo(null);
         PanelPropertyEditor editor=new PanelPropertyEditor();
         HashMap<PropertyInfo,Object> properties=new HashMap<PropertyInfo, Object>();
         PropertyInfo prop=new PropertyInfo();
         prop.id="prop1";
         prop.title="prop1";
         prop.type="string";
         properties.put(prop, "1");
         editor.loadProperties(properties);
         dialog.add(editor);
         dialog.pack();
         dialog.setVisible(true);
         for(PropertyInfo property : editor.getProperties().keySet())
         {
             String value=editor.getProperties().get(property);
             System.out.println(property.id+" "+value);
         }


     }

}