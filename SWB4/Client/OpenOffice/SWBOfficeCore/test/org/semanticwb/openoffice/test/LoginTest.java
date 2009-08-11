/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
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
        DialogLogin login = new DialogLogin();
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
