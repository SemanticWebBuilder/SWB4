/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.test;

import javax.swing.JFrame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.openoffice.Configuration;
import org.semanticwb.openoffice.ConfigurationListURI;
import org.semanticwb.openoffice.ErrorLog;
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
        System.setProperty(ConfigurationListURI.CONFIGURATION, "c:\\temp\\list.xml");
        System.setProperty(Configuration.CONFIGURATION, "c:\\temp\\config.xml");
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
    public void DialogTest()
    {
        ConfigurationListURI conf = new ConfigurationListURI();
        DialogLogin login = new DialogLogin(new JFrame(), true, conf);
        login.setVisible(true);
    }
}
