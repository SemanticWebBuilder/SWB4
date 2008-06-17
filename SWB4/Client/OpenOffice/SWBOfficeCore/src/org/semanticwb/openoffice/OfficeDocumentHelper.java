/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;
import org.semanticwb.openoffice.ui.dialogs.DialogAbout;
import org.semanticwb.openoffice.ui.dialogs.DialogAddLink;
import org.semanticwb.openoffice.ui.dialogs.DialogChangePassword;
import org.semanticwb.openoffice.ui.wizard.PagContenido;
import org.semanticwb.openoffice.ui.dialogs.DialogContentInformation;
import org.semanticwb.openoffice.ui.dialogs.DialogHistory;
import org.semanticwb.openoffice.ui.dialogs.DialogLogin;
import org.semanticwb.openoffice.ui.dialogs.DialogSaveDocument;
import org.semanticwb.openoffice.ui.wizard.SelectDirectory;
import org.semanticwb.openoffice.ui.wizard.TitleAndDescription;
import org.semanticwb.openoffice.ui.wizard.SelectPage;
import org.semanticwb.openoffice.ui.wizard.SelectTypeToShow;
import org.semanticwb.openoffice.ui.wizard.SelectVersionToOpen;
import org.semanticwb.openoffice.util.ExcelFileFilter;
import org.semanticwb.openoffice.util.PPTFileFilter;
import org.semanticwb.openoffice.util.WordFileFilter;

/**
 *
 * @author victor.lorenzana
 */
public final class OfficeDocumentHelper
{

    static
    {
        Locale.setDefault(new Locale("es"));
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
    private static final String TITLE = "Asistente de publicación";

    private OfficeDocumentHelper()
    {
        throw new UnsupportedOperationException("This object can not be inizialited");
    }

    private final static boolean saveDocument(File file, OfficeDocument document)
    {
        boolean result = false;
        if (document.getDocumentType() == DocumentType.WORD)
        {
            if (!file.getName().endsWith(document.getDefaultExtension()))
            {
                file = new File(file.getPath() + document.getDefaultExtension());
            }
        }
        if (file.exists())
        {
            int resultOption = JOptionPane.showConfirmDialog(null, "El archivo ya existe, ¿Desea sobre escribir?", TITLE, JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
            if (resultOption != JOptionPane.NO_OPTION)
            {
                try
                {
                    document.save(file);
                    result = true;
                }
                catch (WBException wbe)
                {
                    JOptionPane.showMessageDialog(null, wbe.getMessage(), TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return result;
    }

    private final static boolean showSaveDialog(OfficeDocument document)
    {
        boolean result = false;
        DialogSaveDocument fileChooser = new DialogSaveDocument(new JFrame(), true);
        if (document.getDocumentType() == DocumentType.WORD)
        {
            fileChooser.setFileFilter(new WordFileFilter());
        }
        if (document.getDocumentType() == DocumentType.PPT)
        {
            fileChooser.setFileFilter(new PPTFileFilter());
        }
        if (document.getDocumentType() == DocumentType.EXCEL)
        {
            fileChooser.setFileFilter(new ExcelFileFilter());
        }
        fileChooser.setVisible(true);
        boolean isCanceled = fileChooser.isCanceled();
        if (!isCanceled)
        {
            File file = fileChooser.getSelectedFile();
            saveDocument(file, document);
        }
        return result;
    }

    public final static void publish(OfficeDocument document)
    {
        if (document.isReadOnly())
        {
            JOptionPane.showMessageDialog(null, "El documento es de sólo lectura, por lo cuál no puede ser publicado", TITLE, JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            boolean canbepublished = false;
            if (document.isNewDocument())
            {
                canbepublished = showSaveDialog(document);
            }
            else
            {
                canbepublished = true;
            }
            if (canbepublished)
            {
                PublishResultProducer resultProducer = new PublishResultProducer(document);
                Class[] clazz;
                switch (document.getDocumentType())
                {
                    case WORD:
                        clazz = new Class[]{TitleAndDescription.class, SelectPage.class, PagContenido.class, SelectTypeToShow.class};
                        break;
                    case EXCEL:
                        clazz = new Class[]{TitleAndDescription.class, SelectPage.class, PagContenido.class};
                        break;
                    case PPT:
                        clazz = new Class[]{TitleAndDescription.class, SelectPage.class, PagContenido.class};
                        break;
                    default:
                        clazz = new Class[]{TitleAndDescription.class, SelectPage.class};
                        break;

                }
                Wizard wiz = WizardPage.createWizard(TITLE, clazz, resultProducer);
                wiz.show();
            }
        }
    }

    public final static void open(OfficeApplication application)
    {
        OpenResultProducer resultProducer = new OpenResultProducer(application);
        Class[] clazz = new Class[]{SelectPage.class, SelectVersionToOpen.class, SelectDirectory.class};
        Wizard wiz = WizardPage.createWizard("Asistente de apertura de contenido", clazz, resultProducer);
        wiz.show();
    }

    public final static void showDocumentInfo(OfficeDocument document)
    {
        DialogContentInformation dialog = new DialogContentInformation(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public final static void showDocumentInSite(OfficeDocument document)
    {

    }

    public final static void changePassword()
    {
        DialogChangePassword dialog = new DialogChangePassword(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    /*if (!dialog.isCanceled())
    {
    }*/
    }

    private final static void login()
    {
        HashSet<URI> urls = new HashSet<URI>();
        DialogLogin frmlogin = new DialogLogin(new javax.swing.JFrame(), true, urls);
        frmlogin.setLocationRelativeTo(null);
        frmlogin.setVisible(true);
        if (!frmlogin.isCanceled())
        {
            URI url = frmlogin.getURI();
            String login = frmlogin.getLogin();
            String password = frmlogin.getPassword();
        }
    }

    public final static void logout()
    {

    }

    public final static void createPage()
    {
        Class[] clazz = new Class[]{SelectPage.class};
        Wizard wiz = WizardPage.createWizard("Asistente de creación de página web", clazz);
        wiz.show();
    }

    public final static void showAbout()
    {
        DialogAbout dialog = new DialogAbout(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public static void showChanges(OfficeDocument document)
    {
        DialogHistory dialog = new DialogHistory(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public static void addRule(OfficeDocument document)
    {

    }

    public static void addLink(OfficeDocument document)
    {
        DialogAddLink dialog = new DialogAddLink(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public static void delete(OfficeDocument document)
    {

    }

    public static void deleteAssociation(OfficeDocument document)
    {
    /*try
    {
    HashMap<String, String> properties = new HashMap<String, String>();
    document.saveCustomProperties(properties);
    }
    catch (WBAlertException wba)
    {
    }
    catch (WBOfficeException wboe)
    {
    }
    catch (WBException wbe)
    {
    }*/
    }
}
