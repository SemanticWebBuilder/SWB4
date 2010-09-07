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

import java.net.URI;
import java.util.Map;
import javax.swing.JOptionPane;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage.WizardResultProducer;
import org.semanticwb.openoffice.components.WebPage;
import org.semanticwb.openoffice.ui.wizard.SelectPage;
import org.semanticwb.openoffice.ui.wizard.SelectTitle;

/**
 *
 * @author victor.lorenzana
 */
public class AddLinkProducer implements WizardResultProducer
{

    private OfficeDocument document;

    public AddLinkProducer(OfficeDocument document)
    {
        this.document = document;
    }

    public boolean cancel(Map map)
    {

        return true;
    }

    public Object finish(Map map) throws WizardException
    {
        WebPage page = (WebPage) map.get(SelectPage.WEBPAGE);
        try
        {
            URI uri = document.getOfficeDocumentProxy().getWebAddress();
            String url = uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort() + "" + page.getURL();
            String title = map.get(SelectTitle.TITLE).toString();
            document.insertLink(url, title);
            JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/AddLinkProducer").getString("LIGA_A_LA_PÁGINA_") + " " + page.toString() + " " + java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/AddLinkProducer").getString("_INSERTADA_CON_ÉXITO"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/AddLinkProducer").getString("INSERCIÓN_LIGA_DE_PÁGINA"), JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
