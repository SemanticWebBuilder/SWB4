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
package org.semanticwb.openoffice;

import java.awt.Frame;
import java.io.File;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.openoffice.interfaces.IOpenOfficeApplication;
import org.semanticwb.openoffice.interfaces.IOpenOfficeDocument;
import org.semanticwb.openoffice.ui.dialogs.DialogAbout;
import org.semanticwb.openoffice.ui.dialogs.DialogChangePassword;
import org.semanticwb.openoffice.ui.dialogs.DialogDocumentsAuthorize;
import org.semanticwb.openoffice.ui.dialogs.DialogLogin;
import org.semanticwb.openoffice.ui.dialogs.DialogPreview;
import org.semanticwb.openoffice.ui.wizard.Search;
import org.semanticwb.openoffice.ui.wizard.SelectDirectory;
import org.semanticwb.openoffice.ui.wizard.SelectPage;
import org.semanticwb.openoffice.ui.wizard.SelectVersionToOpen;
import org.semanticwb.openoffice.ui.wizard.SelectWebPageID;
import org.semanticwb.openoffice.ui.wizard.TitleAndDescription;
import org.semanticwb.xmlrpc.HttpException;
import org.semanticwb.xmlrpc.XmlRpcProxyFactory;

/**
 *
 * @author victor.lorenzana
 */
public abstract class OfficeApplication
{

    public static SimpleDateFormat iso8601dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static final String DELETE_CONFIGURATION_ERROR = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("ERROR_TRATANDO_DE_OBTENER_EL_PROXY_SERVER,_FAVOR_DE_BORRA_EL_ARCHIVO_DE_CONFIGURACIÓN_UBICADO_EN:_");
    private static final String EMPTY_STRING = "";
    private static final String NL = "\r\n";
    private static final String HELP_URL = "http://www.webbuilder.org.mx";
    private static MenuListener menuListener;
    private static UserInfo userInfo = null;
    private static URI webAddress = null;
    private static IOpenOfficeDocument document;
    private static IOpenOfficeApplication application;


    static
    {       
        System.setProperty("wizard.sidebar.image", "org/semanticwb/openoffice/ui/icons/sidebar.png");
        System.setProperty("WizardDisplayer.default","org.semanticwb.openoffice.util.WBWizardDisplayerImpl");
        
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ue)
        {
            // No debe hacer nada
            System.out.println(ue.getMessage());
        }
    }    
    public static IOpenOfficeApplication getOfficeApplicationProxy() throws WBException
    {
        if (application == null)
        {
            application = XmlRpcProxyFactory.newInstance(IOpenOfficeApplication.class, OfficeApplication.getWebAddress());
            application.setUser(OfficeApplication.userInfo.getLogin());
            application.setPassword(OfficeApplication.userInfo.getPassword());
            String proxyServer = new Configuration().get(Configuration.PROXY_SERVER);
            String proxyPort = new Configuration().get(Configuration.PROXY_PORT);
            if (proxyServer == null)
            {
                proxyServer = EMPTY_STRING;
            }
            if (proxyPort == null)
            {
                proxyServer = EMPTY_STRING;
            }
            if (!proxyServer.equals(EMPTY_STRING) && !proxyPort.equals(EMPTY_STRING))
            {
                try
                {
                    application.setProxyAddress(new URI(proxyServer));
                    application.setProxyPort(Integer.parseInt(proxyPort));
                }
                catch (URISyntaxException e)
                {
                    String message = DELETE_CONFIGURATION_ERROR +" "+ new Configuration().getPath();
                    throw new WBException(message, e);
                }
                catch (NumberFormatException e)
                {
                    String message = DELETE_CONFIGURATION_ERROR+" " + new Configuration().getPath();
                    throw new WBException(message, e);
                }
            }
            try
            {
                if (!application.isValidVersion(IOpenOfficeApplication.version))
                {
                    throw new WBException(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("LA_VERSIÓN_ENTRE_LA_APLICACIÓN_DE_PUBLICACIÓN_Y_EL_SITIO_NO_ES_COMPATIBLE"));
                }
            }
            catch (ConnectException e)
            {
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("NO_SE_PUEDE_CONECTAR_AL_SERVIDORDETALLE:_")+" "+e.getLocalizedMessage(), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("ERROR_DE_ACCESO"), JOptionPane.OK_OPTION |
                            JOptionPane.ERROR_MESSAGE);
                throw new WBException(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("NO_SE_PUEDE_CONECTAR_AL_SERVIDORDETALLE:_")+" "+e.getLocalizedMessage(), e);
            }
            catch (HttpException e)
            {
                if (e.getCode() == 403 || e.getCode()==404)
                {
                    JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("ERROR_DE_ACCESO"), JOptionPane.OK_OPTION |
                            JOptionPane.ERROR_MESSAGE);
                    throw new WBException(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("NO_SE_PUEDE_VALIDAR_LA_COMPATIBILIDAD_DE_VERSIONES") + e.getMessage(), e);
                }
                else
                {
                    throw new WBException(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("NO_SE_PUEDE_VALIDAR_LA_COMPATIBILIDAD_DE_VERSIONES") + e.getMessage(), e);
                }
            }
            catch (Exception e)
            {
                throw new WBException(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("ERROR_AL_TRATAR_DE_VERIFICAR_COMPATIBILIDAD_DE_VERSIONES_ENTRE_EL_PUBLICADOR_Y_EL_SERVIDOR"), e);

            }
        }
        return application;
    }

    private static void setProxy() throws WBException
    {
        String proxyServer = new Configuration().get(Configuration.PROXY_SERVER);
        String proxyPort = new Configuration().get(Configuration.PROXY_PORT);
        if (proxyServer == null)
        {
            proxyServer = EMPTY_STRING;
        }
        if (proxyPort == null)
        {
            proxyServer = EMPTY_STRING;
        }
        if (!proxyServer.equals(EMPTY_STRING) && !proxyPort.equals(EMPTY_STRING))
        {
            try
            {
                document.setProxyAddress(new URI(proxyServer));
                document.setProxyPort(Integer.parseInt(proxyPort));
            }
            catch (URISyntaxException e)
            {
                String message = DELETE_CONFIGURATION_ERROR +" "+ new Configuration().getPath();
                throw new WBException(message, e);
            }
            catch (NumberFormatException e)
            {
                String message = DELETE_CONFIGURATION_ERROR+" " + new Configuration().getPath();
                throw new WBException(message, e);
            }
        }
    }

    public static IOpenOfficeDocument getOfficeDocumentProxy() throws WBException
    {
        if (document == null)
        {
            try
            {
                if (getOfficeApplicationProxy().isValidVersion(IOpenOfficeApplication.version))
                {
                    document = XmlRpcProxyFactory.newInstance(IOpenOfficeDocument.class, OfficeApplication.getWebAddress());
                    document.setUser(OfficeApplication.userInfo.getLogin());
                    document.setPassword(OfficeApplication.userInfo.getPassword());
                    setProxy();
                }
            }
            catch (ConnectException e)
            {
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("NO_SE_PUEDE_CONECTAR_AL_SERVIDORDETALLE:_")+" "+e.getLocalizedMessage(), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("ERROR_DE_ACCESO"), JOptionPane.OK_OPTION |
                            JOptionPane.ERROR_MESSAGE);
                throw new WBException(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("NO_SE_PUEDE_CONECTAR_AL_SERVIDORDETALLE:_")+" "+e.getLocalizedMessage(), e);
            }
            catch (HttpException e)
            {
                if (e.getCode() == 403)
                {
                    JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("ERROR_DE_ACCESO"), JOptionPane.OK_OPTION |
                            JOptionPane.ERROR_MESSAGE);
                    throw new WBException(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("NO_SE_PUEDE_VALIDAR_LA_COMPATIBILIDAD_DE_VERSIONES") + e.getMessage(), e);
                }
                else
                {
                    throw new WBException(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("NO_SE_PUEDE_VALIDAR_LA_COMPATIBILIDAD_DE_VERSIONES") + e.getMessage(), e);
                }
            }
            catch (Exception e)
            {
                throw new WBException(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("NO_SE_PUEDE_VALIDAR_LA_COMPATIBILIDAD_DE_VERSIONES") + e.getMessage(), e);
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
        if (OfficeApplication.tryLogin())
        {
            DialogChangePassword dialog = new DialogChangePassword();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            if (!dialog.isCanceled())
            {
                try
                {
                    getOfficeApplicationProxy().changePassword(dialog.getNewPassword());
                    userInfo.changePassword(dialog.getNewPassword());
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("CAMBIAR_CONTRASEÑA"), JOptionPane.ERROR);
                }
            }
        }
    }

    public static final void createPage()
    {
        if (OfficeApplication.tryLogin())
        {
            CreatePageResultProducer resultProducer = new CreatePageResultProducer();            
            WizardPage[] clazz = new WizardPage[]
            {
                new SelectPage(null), new TitleAndDescription(false), new SelectWebPageID()
            };
            Wizard wiz = WizardPage.createWizard(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("ASISTENTE_DE_CREACIÓN_DE_PÁGINA"), clazz, resultProducer);
            wiz.show();
        }
    }

    public static final void createPage(WebPageInfo parent)
    {
        CreatePageResultProducer resultProducer = new CreatePageResultProducer(parent);
        WizardPage[] clazz = new WizardPage[]
        {
            new TitleAndDescription(false), new SelectWebPageID(parent)
        };
        Wizard wiz = WizardPage.createWizard(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("ASISTENTE_DE_CREACIÓN_DE_PÁGINA"), clazz, resultProducer);
        wiz.show();
    }

    public final void open(DocumentType type)
    {
        if (OfficeApplication.tryLogin())
        {
            OpenResultProducer resultProducer = new OpenResultProducer(this);
            WizardPage[] clazz = new WizardPage[]
            {
                new Search(type), new SelectVersionToOpen(type.toString().toLowerCase()), new SelectDirectory()
            };
            Wizard wiz = WizardPage.createWizard(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("ASISTENTE_DE_APERTURA_DE_CONTENIDO"), clazz, resultProducer);
            wiz.show();
        }
    }

    public static final void showAbout()
    {
        DialogAbout dialog = new DialogAbout();
        dialog.setVisible(true);
    }

    public static final void showHelp()
    {
        DialogPreview.showInBrowser(HELP_URL, new Frame());
    }

    public static URI getWebAddress() throws WBException
    {

        if (webAddress == null)
        {
            if (!tryLogin())
            {
                throw new WBException(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("EL_USUARIO_NO_PUEDE_ACCEDER"));
            }
        }
        return webAddress;
    }

    static String setupDocument(String workspace, String contentId) throws Exception
    {
        String contentIdToReturn = null;
        if (contentId != null && !contentId.trim().equals(EMPTY_STRING) && OfficeApplication.tryLogin())
        {
            document = getOfficeDocumentProxy();
            try
            {
                if (document.exists(workspace, contentId))
                {
                    contentIdToReturn = contentId;
                }
                else
                {
                    // el contenido no existe en el sitio pero indica que ya tiene un identificador
                    JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("EL_CONTENIDO_PARACE_HABERSE_PUBLICADO_EN_UN_SITIO_WEB.")+ NL +java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("AL_SITIO_DONDE_SE_ESTÁ_INTENTANDO_CONECTAR,_INDICA_QUE_ESTE_CONTENIDO_NO_EXISTE.")+ NL +java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("SI_DESEA_CONTINUAR_SE_PERDERÁ_ESTA_INFORMACIÓN,_DE_LO_CONTRARIO,_CIERRE_ESTE_DOCUMENTO."), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeApplication").getString("VERIFICACIÓN_DE_CONTENIDO_EN_SITIO_WEB"), JOptionPane.WARNING_MESSAGE);
                }
            }
            catch (NumberFormatException nfe)
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
        DialogLogin frmlogin = new DialogLogin();        
        
        frmlogin.setVisible(true);
        if (!frmlogin.isCanceled())
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
        if (userInfo == null || webAddress == null)
        {
            logOn();            
            
            if (userInfo == null || webAddress == null)
            {
                logOff();
                tryLogin = false;
            }
            else
            {                
                try
                {
                    OfficeApplication.getOfficeApplicationProxy().isValidVersion(IOpenOfficeApplication.version);
                    tryLogin = true;
                    ConfigurationListURI configurationListURI = new ConfigurationListURI();
                    configurationListURI.addUserConfiguration(webAddress,userInfo.getLogin());
                }
                catch (Exception e)
                {                    
                    tryLogin = false;
                    logOff();
                }
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
        if (userInfo != null)
        {
            isLogged = true;
        }
        return isLogged;
    }
    public static final void openSession()
    {
        tryLogin();
    }
    public static final void closeSession()
    {
        logOff();
    }
    public static final void showContentsToAuthorize()
    {
        if(tryLogin())
        {
            DialogDocumentsAuthorize dialogDocumentsAtuhorize=new DialogDocumentsAuthorize();
            dialogDocumentsAtuhorize.setVisible(true);
        }
    }

    public final static void logOff()
    {
        userInfo = null;
        webAddress = null;
        application=null;
        if (menuListener != null)
        {
            menuListener.onLogout();
        }
    }
}
