/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice;

import java.awt.EventQueue;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.netbeans.spi.wizard.DeferredWizardResult;
import org.netbeans.spi.wizard.ResultProgressHandle;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage.WizardResultProducer;

/**
 *
 * @author victor.lorenzana
 */
public class OpenResultProducer implements WizardResultProducer{

    private OfficeApplication application;
    public OpenResultProducer(OfficeApplication application)
    {
        this.application=application;
    }
    class BackgroundResultCreator extends DeferredWizardResult
    {

        public void start(Map wizardData, ResultProgressHandle progress)
        {
            assert !EventQueue.isDispatchThread();
            try
            {
                progress.setProgress("Descargando documento", 0, 3);
                // descarga el documento
                progress.setProgress("Abriendo documento", 1, 3);
                File file=new File("");
                OfficeDocument document=application.open(file);
                HashMap<String,String> properties=new HashMap<String, String>();
                document.saveCustomProperties(properties);

                /*progress.setProgress("Doing something", 1, 3);
                //doSomethingElseExpensive(wizardData);
                Thread.currentThread().sleep(1000);
                progress.setProgress("Doing something", 2, 3);
                //Object finalResult = doAnotherExpensiveThing(wizardData);
                Thread.currentThread().sleep(1000);
                //progress.finish(null);
                Summary summary=Summary.create(new DemoSummary(),null);                */                
                progress.finished(null);
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
        return new BackgroundResultCreator();
    }
}
