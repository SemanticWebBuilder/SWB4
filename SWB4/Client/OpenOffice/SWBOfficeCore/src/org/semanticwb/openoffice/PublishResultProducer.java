/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.awt.EventQueue;
import java.util.Map;
import org.netbeans.spi.wizard.DeferredWizardResult;
import org.netbeans.spi.wizard.ResultProgressHandle;
import org.netbeans.spi.wizard.Summary;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage.WizardResultProducer;
import org.semanticwb.openoffice.ui.wizard.SummaryPublish;


/**
 *
 * @author victor.lorenzana
 */
public class PublishResultProducer implements WizardResultProducer
{

    private OfficeDocument document;

    public PublishResultProducer(OfficeDocument document)
    {
        this.document = document;
    }

    class BackgroundResultCreator extends DeferredWizardResult
    {

        public void start(Map wizardData, ResultProgressHandle progress)
        {
            assert !EventQueue.isDispatchThread();
            try
            {
                progress.setProgress("Preparando documento para publicar", 0, 2);
                //byte[] zipFile = document.getZipFile();
                progress.setProgress("Publicando Documento", 1, 2);
                Thread.currentThread().sleep(1000);
                progress.setProgress("Publicando Documento", 1, 2);
                /*progress.setProgress("Doing something", 1, 3);
                //doSomethingElseExpensive(wizardData);
                Thread.currentThread().sleep(1000);
                progress.setProgress("Doing something", 2, 3);
                //Object finalResult = doAnotherExpensiveThing(wizardData);
                Thread.currentThread().sleep(1000);
                //progress.finish(null);
                Summary summary=Summary.create(new DemoSummary(),null);                */
                Summary summary=Summary.create(new SummaryPublish(),null);               
                progress.finished(summary);
            }
            /*catch (WBOfficeException e)
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
            }*/
            catch (Exception e)
            {
                progress.failed(e.getMessage(), false);
            }
        }
    }

    public boolean cancel(Map map)
    {        
//        boolean dialogShouldClose = JOptionPane.showConfirmDialog(null,
//                "¿Desea cerrar el asistente de publicación de contenido?","Asistente de Publicación",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION;
//        return dialogShouldClose;
        return true;
    }

    public Object finish(Map map) throws WizardException
    {
        return new BackgroundResultCreator();
    }
}
