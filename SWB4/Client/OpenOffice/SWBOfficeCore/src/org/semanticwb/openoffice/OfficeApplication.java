/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;
import org.semanticwb.openoffice.interfaces.IOpenOfficeApplication;
import org.semanticwb.openoffice.interfaces.IOpenOfficeDocument;
import org.semanticwb.openoffice.ui.dialogs.DialogAbout;
import org.semanticwb.openoffice.ui.dialogs.DialogChangePassword;
import org.semanticwb.openoffice.ui.dialogs.DialogLogin;
import org.semanticwb.openoffice.ui.wizard.Search;
import org.semanticwb.openoffice.ui.wizard.SelectDirectory;
import org.semanticwb.openoffice.ui.wizard.SelectVersionToOpen;
import org.semanticwb.xmlrpc.XmlRpcProxyFactory;

/**
 *
 * @author victor.lorenzana
 */
public abstract class OfficeApplication
{

    private static final String DELETE_CONFIGURATION_ERROR = "Error trying to get the proxy server, delete the file ";
    private static MenuListener menuListener;
    private static UserInfo userInfo = null;
    private static URI webAddress = null;
    private static IOpenOfficeDocument document;
    private static IOpenOfficeApplication application;

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

    public static IOpenOfficeApplication getOfficeApplicationProxy() throws WBException
    {
        if ( application == null )
        {
            application = XmlRpcProxyFactory.newInstance(IOpenOfficeApplication.class, OfficeApplication.getWebAddress());
            application.setUser(OfficeApplication.userInfo.getLogin());
            application.setPassword(OfficeApplication.userInfo.getPassword());
            String proxyServer = new Configuration().get(Configuration.PROXY_SERVER);
            String proxyPort = new Configuration().get(Configuration.PROXY_PORT);
            if ( proxyServer == null )
            {
                proxyServer = "";
            }
            if ( proxyPort == null )
            {
                proxyServer = "";
            }
            if ( !proxyServer.equals("") && !proxyPort.equals("") )
            {
                try
                {
                    application.setProxyAddress(new URI(proxyServer));
                    application.setProxyPort(Integer.parseInt(proxyPort));
                }
                catch ( URISyntaxException e )
                {
                    String message = DELETE_CONFIGURATION_ERROR + new Configuration().getPath();
                    throw new WBException(message, e);
                }
                catch ( NumberFormatException e )
                {
                    String message = "Error trying to get the proxy port, delete the file " + new Configuration().getPath();
                    throw new WBException(message, e);
                }
            }
            try
            {
                if ( !application.isValidVersion(IOpenOfficeApplication.version) )
                {
                    throw new WBException("La versión entre la aplicación de publicación y el sitio no es compatible");
                }
            }
            catch ( Exception e )
            {                
                throw new WBException("Error al tratar de verificar compatibilidad de versiones entre el publicador y el servidor", e);
                
            }
        }
        return application;
    }

    private static void setProxy() throws WBException
    {
        String proxyServer = new Configuration().get(Configuration.PROXY_SERVER);
        String proxyPort = new Configuration().get(Configuration.PROXY_PORT);
        if ( proxyServer == null )
        {
            proxyServer = "";
        }
        if ( proxyPort == null )
        {
            proxyServer = "";
        }
        if ( !proxyServer.equals("") && !proxyPort.equals("") )
        {
            try
            {
                document.setProxyAddress(new URI(proxyServer));
                document.setProxyPort(Integer.parseInt(proxyPort));
            }
            catch ( URISyntaxException e )
            {
                String message = DELETE_CONFIGURATION_ERROR + new Configuration().getPath() + " or fix it.";
                throw new WBException(message, e);
            }
            catch ( NumberFormatException e )
            {
                String message = "Error trying to get the proxy port, delete the file " + new Configuration().getPath() + " or fix it.";
                throw new WBException(message, e);
            }
        }
    }

    public static IOpenOfficeDocument getOfficeDocumentProxy() throws WBException
    {
        if ( document == null )
        {
            try
            {
                if ( getOfficeApplicationProxy().isValidVersion(IOpenOfficeApplication.version) )
                {
                    document = XmlRpcProxyFactory.newInstance(IOpenOfficeDocument.class, OfficeApplication.getWebAddress());
                    document.setUser(OfficeApplication.userInfo.getLogin());
                    document.setPassword(OfficeApplication.userInfo.getPassword());
                    setProxy();
                }
            }
            catch ( Exception e )
            {
                throw new WBException("No se puede validar la compatibilidad de versiones", e);
            }
        }
        return document;
    }

    public void setMenuListener(MenuListener menuListener)
    {
        OfficeApplication.menuListener = menuListener;
    }

    protected OfficeApplication()
    {

    }

    /*private void verifyVersion()
    {
    if ( OfficeApplication.tryLogin() )
    {
    IOpenOfficeApplication officeApplication = XmlRpcProxyFactory.newInstance(IOpenOfficeApplication.class, webAddress);
    try
    {
    if ( officeApplication.isValidVersion(IOpenOfficeApplication.version) && menuListener != null )
    {
    menuListener.onLogin();
    }
    }
    catch ( Exception e )
    {
    JOptionPane.showMessageDialog(null, e.getMessage(), "Verificación de versión de publicación", JOptionPane.ERROR_MESSAGE);
    }
    }
    }*/
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

    public static final void changePassword()
    {
        if ( OfficeApplication.tryLogin() )
        {
            DialogChangePassword dialog = new DialogChangePassword(new javax.swing.JFrame(), true);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            if ( !dialog.isCanceled() )
            {
                try
                {
                    getOfficeApplicationProxy().changePassword(dialog.getNewPassword());
                    userInfo.changePassword(dialog.getNewPassword());
                }
                catch ( Exception e )
                {
                    JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "Cambiar contraseña", JOptionPane.ERROR);
                }
            }
        }
    }

    public static final void createPage()
    {

    }

    public final void open()
    {
        OpenResultProducer resultProducer = new OpenResultProducer(this);
        Class[] clazz = new Class[]{Search.class, SelectVersionToOpen.class, SelectDirectory.class};
        Wizard wiz = WizardPage.createWizard("Asistente de apertura de contenido", clazz, resultProducer);
        wiz.show();
    }

    public static final void showAbout()
    {
        DialogAbout dialog = new DialogAbout(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public static URI getWebAddress() throws WBException
    {

        if ( webAddress == null )
        {
            if ( !tryLogin() )
            {
                throw new WBException("The user can be logged");
            }
        }
        return webAddress;
    }

    static String setupDocument(String contentId) throws Exception
    {
        String contentIdToReturn = null;
        if ( contentId != null && !contentId.trim().equals("") && OfficeApplication.tryLogin() )
        {
            document = getOfficeDocumentProxy();
            try
            {
                if ( document.exists(contentId) )
                {
                    contentIdToReturn = contentId;
                }
                else
                {
                    // el contenido no existe en el sitio pero indica que ya tiene un identificador
                    JOptionPane.showMessageDialog(null, "El contenido parace haberse publicado en un sitio web.\r\nAl sitio donde se está intentando conectar, indica que este contenido no existe.\r\nSi desea continuar se perdra esta información, de lo contrario, cierre este documento.", "Verificación de contenido en sitio web", JOptionPane.WARNING_MESSAGE);
                }
            }
            catch ( NumberFormatException nfe )
            {
                ErrorLog.log(nfe);
            }

        }
        return contentIdToReturn;
    }
    /*public static MenuListener getMenuListener()
    {
    return menuListener;
    }*/

    private final static boolean logOn()
    {
        boolean logOn = false;
        DialogLogin frmlogin = new DialogLogin(new javax.swing.JFrame(), true);
        frmlogin.setAlwaysOnTop(true);
        frmlogin.setLocationRelativeTo(null);
        frmlogin.setVisible(true);
        if ( !frmlogin.isCanceled() )
        {
            String login = frmlogin.getLogin();
            String password = frmlogin.getPassword();
            userInfo = new UserInfo(password, login);
            webAddress = frmlogin.getWebAddress();
            logOn = true;
        }
        else
        {
            logOff();
        }
        return logOn;
    }

    public static boolean tryLogin()
    {
        boolean tryLogin = false;
        if ( userInfo == null || webAddress == null )
        {
            logOn();
            if ( userInfo == null || webAddress == null )
            {
                logOff();
                tryLogin = false;
            }
            else
            {
                tryLogin = true;
            }
        }
        else
        {
            tryLogin = true;
        }
        return tryLogin;
    }

    public static final boolean isLogged()
    {
        boolean isLogged = false;
        if ( userInfo == null )
        {
            isLogged = false;
        }
        return isLogged;
    }

    public static final void closeSession()
    {

    }

    public final static void logOff()
    {
        userInfo = null;
        webAddress = null;
        if ( menuListener != null )
        {
            menuListener.onLogout();
        }
    }
}
