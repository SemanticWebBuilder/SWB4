/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import javax.swing.UIManager;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;
import org.semanticwb.office.interfaces.IOfficeApplication;
import org.semanticwb.openoffice.ui.dialogs.DialogAbout;
import org.semanticwb.openoffice.ui.dialogs.DialogChangePassword;
import org.semanticwb.openoffice.ui.dialogs.DialogLogin;
import org.semanticwb.openoffice.ui.wizard.SelectDirectory;
import org.semanticwb.openoffice.ui.wizard.SelectPage;
import org.semanticwb.openoffice.ui.wizard.SelectVersionToOpen;
import org.semanticwb.xmlrpc.XmlRpcProxyFactory;

/**
 *
 * @author victor.lorenzana
 */
public abstract class OfficeApplication
{
    public static UserInfo user=null;
    public static IOfficeApplication proxyApplication=null;
    static
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
    }

    /**
     * Opens a document in a file path
     * @param file Path for the file
     * @return OfficeDocument opened
     * @throws org.semanticwb.WBException If the document can not be opened
     */
    public abstract OfficeDocument open(File file) throws WBException;

    /**
     * Returns all documents opened
     * @return List of documents
     * @throws org.semanticwb.WBException In case that there was an error
     */
    public abstract List<OfficeDocument> getDocuments() throws WBException;

    public final void changePassword()
    {
        DialogChangePassword dialog = new DialogChangePassword(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    /*if (!dialog.isCanceled())
    {
    }*/
    }

    public final void open()
    {
        OpenResultProducer resultProducer = new OpenResultProducer(this);
        Class[] clazz = new Class[]{SelectPage.class, SelectVersionToOpen.class, SelectDirectory.class};
        Wizard wiz = WizardPage.createWizard("Asistente de apertura de contenido", clazz, resultProducer);
        wiz.show();
    }

    public final void showAbout()
    {
        DialogAbout dialog = new DialogAbout(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private final static boolean login()
    {
        ConfigurationListURI config=new ConfigurationListURI();        
        DialogLogin frmlogin = new DialogLogin(new javax.swing.JFrame(), true, config);
        frmlogin.setLocationRelativeTo(null);
        frmlogin.setVisible(true);
        if ( !frmlogin.isCanceled() )
        {
            URI uri = frmlogin.getURI();
            String login = frmlogin.getLogin();
            String password = frmlogin.getPassword();
            user=new UserInfo(password, login);            
            return true;
        }
        return false;
    }

    public final static void logout()
    {
        user=null;
        proxyApplication=null;
    }
}
