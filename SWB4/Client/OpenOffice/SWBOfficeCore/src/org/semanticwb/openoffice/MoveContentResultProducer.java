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

import java.awt.Cursor;
import java.util.Map;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage.WizardResultProducer;
import org.semanticwb.office.interfaces.ResourceInfo;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.openoffice.components.WebPage;
import org.semanticwb.openoffice.ui.dialogs.DialogEditResource;
import org.semanticwb.openoffice.ui.wizard.SelectPage;

/**
 *
 * @author victor.lorenzana
 */
public class MoveContentResultProducer implements WizardResultProducer
{

    DialogEditResource dialogEditResource;

    public MoveContentResultProducer(DialogEditResource dialogEditResource)
    {
        this.dialogEditResource = dialogEditResource;
    }

    public Object finish(Map map) throws WizardException
    {
        try
        {
            dialogEditResource.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            WebPage webpage = (WebPage) map.get(SelectPage.WEBPAGE);
            WebPageInfo webPageInfo=webpage.getWebPageInfo();
            OfficeApplication.getOfficeDocumentProxy().changeResourceOfWebPage(dialogEditResource.pageInformation, webPageInfo);            
            for (ResourceInfo resourceInfo : OfficeApplication.getOfficeDocumentProxy().listResources(dialogEditResource.repositoryName, dialogEditResource.contentID))
            {
                if (resourceInfo.id.equals(dialogEditResource.pageInformation.id))
                {
                    dialogEditResource.pageInformation = resourceInfo;
                }
            }
            dialogEditResource.initialize();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            dialogEditResource.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        return null;
    }

    public boolean cancel(Map map)
    {
        return true;
    }
}
