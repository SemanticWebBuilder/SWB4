/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.swing.JOptionPane;
import org.netbeans.spi.wizard.DeferredWizardResult;
import org.netbeans.spi.wizard.ResultProgressHandle;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage.WizardResultProducer;
import org.semanticwb.office.interfaces.IOfficeApplication;
import org.semanticwb.office.interfaces.VersionInfo;
import org.semanticwb.openoffice.ui.wizard.Search;
import org.semanticwb.openoffice.ui.wizard.SelectDirectory;
import org.semanticwb.openoffice.ui.wizard.SelectVersionToOpen;
import org.semanticwb.openoffice.util.StackTraceUtil;
import org.semanticwb.xmlrpc.Part;
import org.semanticwb.xmlrpc.XmlProxy;

/**
 *
 * @author victor.lorenzana
 */
public class OpenResultProducer implements WizardResultProducer
{

    private OfficeApplication application;

    public OpenResultProducer(OfficeApplication application)
    {
        this.application = application;
    }

    class BackgroundResultCreator extends DeferredWizardResult
    {

        public File contentfile;
       

        public void start(Map wizardData, ResultProgressHandle progress)
        {
            assert !EventQueue.isDispatchThread();
            try
            {                
                IOfficeApplication openOfficeDocument = OfficeApplication.getOfficeApplicationProxy();
                progress.setProgress("Descargando Documento...", 1, 4);
                String repositoryName = wizardData.get(Search.WORKSPACE).toString();
                VersionInfo versioninfo = (VersionInfo) wizardData.get(SelectVersionToOpen.VERSION);
                String fileName = openOfficeDocument.openContent(repositoryName, versioninfo);
                XmlProxy proxy = (XmlProxy) openOfficeDocument;
                File dir = (File) wizardData.get(SelectDirectory.DIRECTORY);
                contentfile = new File(dir.getAbsolutePath() + "/" + fileName);
                String guid = java.util.UUID.randomUUID().toString().replace('-', '_');
                File zipFile = new File(dir.getAbsolutePath() + "/" + guid + ".zip");
                for (Part part : proxy.getResponseParts())
                {
                    FileOutputStream out = new FileOutputStream(zipFile);
                    out.write(part.getContent());
                    out.close();
                }
                // unzip the content
                try
                {
                    progress.setProgress("Descomprimiendo archivo...", 2, 4);
                    ZipFile zip = new ZipFile(zipFile);
                    ZipEntry entry = zip.getEntry(fileName);
                    if (entry != null)
                    {
                        contentfile.getParentFile().mkdirs();
                        if (contentfile.exists())
                        {
                            int res = JOptionPane.showConfirmDialog(null, "¡Existe un documento con el nombre " + fileName + "\r\n¿Desea sobre escribir el documento?", "Apertura de contenido", JOptionPane.YES_NO_OPTION);
                            if (res == JOptionPane.NO_OPTION)
                            {
                                return;
                            }
                            if (!contentfile.delete())
                            {
                                JOptionPane.showMessageDialog(null, "¡Existe un documento con el mismo nombre\r\nNo se puede borrar el documento para reemplazarlo por el contenido descargado!", "Apertura de contenido", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        InputStream in = zip.getInputStream(entry);
                        FileOutputStream out = new FileOutputStream(contentfile);
                        byte[] buffer = new byte[2048];
                        int read = in.read(buffer);
                        while (read != -1)
                        {
                            out.write(buffer, 0, read);
                            read = in.read(buffer);
                        }
                        in.close();
                        out.close();
                        zip.close();
                        progress.setProgress("Abriendo archivo " + contentfile.getPath() + "...", 3, 4);
                        progress.finished(null);
                        OfficeDocument document = application.open(contentfile);
                        progress.setProgress("Documento abierto " + contentfile.getPath() + "...", 4, 4);
                        HashMap<String, String> properties = new HashMap<String, String>();                        
                        properties.put(OfficeDocument.CONTENT_ID_NAME, versioninfo.contentId);
                        properties.put(OfficeDocument.WORKSPACE_ID_NAME, wizardData.get(Search.WORKSPACE).toString());
                        document.saveCustomProperties(properties);
                        
                    }
                }
                catch (ZipException ioe)
                {                    
                    progress.failed(ioe.getMessage() + "\r\n" + StackTraceUtil.getStackTrace(ioe), false);
                }
                catch (IOException ioe)
                {                    
                    progress.failed(ioe.getMessage() + "\r\n" + StackTraceUtil.getStackTrace(ioe), false);
                }
                catch (Exception ioe)
                {                    
                    progress.failed(ioe.getMessage() + "\r\n" + StackTraceUtil.getStackTrace(ioe), false);
                }
                finally
                {
                    zipFile.delete();
                }

            }
            catch (WBOfficeException e)
            {
                progress.failed(e.getMessage(), false);
            }
            catch (WBAlertException e)
            {
                progress.failed(e.getMessage(), false);
            }
            catch (WBException e)
            {
                progress.failed(e.getMessage(), false);
            }
            catch (Exception e)
            {
                progress.failed(e.getMessage(), false);
            }
        }
    }

    public boolean cancel(Map map)
    {
//        boolean dialogShouldClose = JOptionPane.showConfirmDialog(null,
//                "¿Desea cerrar el asistente de apertura de contenido?","Asistente de Publicación",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION;
//        return dialogShouldClose;
        return true;
    }

    public Object finish(Map map) throws WizardException
    {
        BackgroundResultCreator res = new BackgroundResultCreator();
        return res;
    }
}
