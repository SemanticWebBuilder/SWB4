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

import java.awt.EventQueue;
import java.io.File;
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
        private static final String NL = "\r\n";
        private static final String DOTS = "...";
        private static final String PATH_SEPARATOR = "/";
        private static final String UTF8 = "utf-8";
        private static final String ZIP_EXTENSION = ".zip";

        public File contentfile;

        @Override
        public void start(Map wizardData, ResultProgressHandle progress)
        {
            assert !EventQueue.isDispatchThread();
            try
            {
                IOfficeApplication openOfficeDocument = OfficeApplication.getOfficeApplicationProxy();
                progress.setProgress(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OpenResultProducer").getString("DESCARGANDO_DOCUMENTO..."), 1, 4);
                String repositoryName = wizardData.get(Search.WORKSPACE).toString();
                VersionInfo versioninfo = (VersionInfo) wizardData.get(SelectVersionToOpen.VERSION);
                String fileName = openOfficeDocument.openContent(repositoryName, versioninfo);
                fileName=java.net.URLDecoder.decode(fileName,UTF8);
                XmlProxy proxy = (XmlProxy) openOfficeDocument;
                File dir = (File) wizardData.get(SelectDirectory.DIRECTORY);
                contentfile = new File(dir.getAbsolutePath() + PATH_SEPARATOR + fileName);
                String guid = java.util.UUID.randomUUID().toString().replace('-', '_');
                File zipFile = new File(dir.getAbsolutePath() + PATH_SEPARATOR + guid + ZIP_EXTENSION);
                for (Part part : proxy.getResponseParts())
                {
                    FileOutputStream out = new FileOutputStream(zipFile);
                    out.write(part.getContent());
                    out.close();
                }
                // unzip the content
                try
                {
                    progress.setProgress(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OpenResultProducer").getString("DESCOMPRIMIENDO_ARCHIVO..."), 2, 4);
                    ZipFile zip = new ZipFile(zipFile);
                    ZipEntry entry = zip.getEntry(fileName);
                    if (entry != null)
                    {
                        contentfile.getParentFile().mkdirs();
                        if (contentfile.exists())
                        {
                            int res = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OpenResultProducer").getString("¡EXISTE_UN_DOCUMENTO_CON_EL_NOMBRE_") + fileName +  NL +java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OpenResultProducer").getString("¿DESEA_SOBRE_ESCRIBIR_EL_DOCUMENTO?"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OpenResultProducer").getString("APERTURA_DE_CONTENIDO"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (res == JOptionPane.NO_OPTION)
                            {
                                return;
                            }
                            if (!contentfile.delete())
                            {
                                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OpenResultProducer").getString("¡EXISTE_UN_DOCUMENTO_CON_EL_MISMO_NOMBRE")+ NL +java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OpenResultProducer").getString("NO_SE_PUEDE_BORRAR_EL_DOCUMENTO_PARA_REEMPLAZARLO_POR_EL_CONTENIDO_DESCARGADO!"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OpenResultProducer").getString("APERTURA_DE_CONTENIDO"), JOptionPane.ERROR_MESSAGE);
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
                        progress.setProgress(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OpenResultProducer").getString("ABRIENDO_ARCHIVO_") + contentfile.getPath() + DOTS, 3, 4);
                        progress.finished(null);
                        OfficeDocument document = application.open(contentfile);
                        progress.setProgress(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OpenResultProducer").getString("DOCUMENTO_ABIERTO_") + contentfile.getPath() + DOTS, 4, 4);
                        HashMap<String, String> properties = new HashMap<String, String>();
                        properties.put(OfficeDocument.CONTENT_ID_NAME, versioninfo.contentId);
                        properties.put(OfficeDocument.WORKSPACE_ID_NAME, wizardData.get(Search.WORKSPACE).toString());
                        document.saveCustomProperties(properties);

                    }
                }
                catch (ZipException ioe)
                {
                    progress.failed(ioe.getMessage() + NL + StackTraceUtil.getStackTrace(ioe), false);
                }
                catch (IOException ioe)
                {
                    progress.failed(ioe.getMessage() + NL + StackTraceUtil.getStackTrace(ioe), false);
                }
                catch (Exception ioe)
                {
                    progress.failed(ioe.getMessage() +  NL + StackTraceUtil.getStackTrace(ioe), false);
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

    @Override
    public boolean cancel(Map map)
    {
        return true;
    }

    @Override
    public Object finish(Map map) throws WizardException
    {
        BackgroundResultCreator res = new BackgroundResultCreator();
        return res;
    }
}
