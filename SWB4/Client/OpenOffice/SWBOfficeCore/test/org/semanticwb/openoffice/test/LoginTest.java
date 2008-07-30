/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.test;

import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.UIManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.openoffice.Configuration;
import org.semanticwb.openoffice.ConfigurationListURI;
import org.semanticwb.openoffice.ErrorLog;
import org.semanticwb.openoffice.ui.dialogs.DialogError;
import org.semanticwb.openoffice.ui.dialogs.DialogLogin;
import static org.junit.Assert.*;

/**
 *
 * @author victor.lorenzana
 */
public class LoginTest
{

    public LoginTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        Locale.setDefault(new Locale("es"));
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch ( Exception ue )
        {
            // No debe hacer nada
            System.out.println(ue.getMessage());
        }
        System.setProperty(ConfigurationListURI.CONFIGURATION, "c:\\temp\\list.xml");
        System.setProperty(Configuration.CONFIGURATION_PROPERTY_NAME, "c:\\temp\\config.xml");
        System.setProperty(ErrorLog.CONFIGURATION, "c:\\temp");
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    @Ignore
    public void DialogLoginTest()
    {
        ConfigurationListURI conf = new ConfigurationListURI();
        DialogLogin login = new DialogLogin(new JFrame(), true);
        login.setVisible(true);
    }

    @Test
    public void DialogErrorTest()
    {
        try
        {
            throw new Exception("Mensaje");
        }
        catch ( Exception e )
        {
            DialogError error = new DialogError(new javax.swing.JFrame("Error"), true, e);            
            error.setTitle("");
            error.setLocationRelativeTo(null);
            error.setVisible(true);
        }
    }
}
