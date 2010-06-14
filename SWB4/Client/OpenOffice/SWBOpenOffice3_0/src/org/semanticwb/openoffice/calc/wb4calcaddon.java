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
package org.semanticwb.openoffice.calc;

import com.sun.star.frame.FeatureStateEvent;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.lib.uno.helper.Factory;
import com.sun.star.lang.XSingleComponentFactory;
import com.sun.star.registry.XRegistryKey;
import com.sun.star.lib.uno.helper.WeakBase;
import javax.swing.JOptionPane;
import org.semanticwb.openoffice.DocumentType;
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.OfficeDocument;

public final class wb4calcaddon extends WeakBase
        implements com.sun.star.lang.XServiceInfo,
        com.sun.star.frame.XDispatchProvider,
        com.sun.star.lang.XInitialization,
        com.sun.star.frame.XDispatch
{

    private final XComponentContext m_xContext;
    private com.sun.star.frame.XFrame m_xFrame;
    private static final String m_implementationName = wb4calcaddon.class.getName();
    private static final String[] m_serviceNames =
    {
        "com.sun.star.frame.ProtocolHandler"
    };

    public wb4calcaddon(XComponentContext context)
    {
        m_xContext = context;
    }

    public static XSingleComponentFactory __getComponentFactory(String sImplementationName)
    {
        XSingleComponentFactory xFactory = null;

        if (sImplementationName.equals(m_implementationName))
        {
            xFactory = Factory.createComponentFactory(wb4calcaddon.class, m_serviceNames);
        }
        return xFactory;
    }

    public static boolean __writeRegistryServiceInfo(XRegistryKey xRegistryKey)
    {
        return Factory.writeRegistryServiceInfo(m_implementationName,
                m_serviceNames,
                xRegistryKey);
    }

    // com.sun.star.lang.XServiceInfo:
    public String getImplementationName()
    {
        return m_implementationName;
    }

    public boolean supportsService(String sService)
    {
        int len = m_serviceNames.length;

        for (int i = 0; i < len; i++)
        {
            if (sService.equals(m_serviceNames[i]))
            {
                return true;
            }
        }
        return false;
    }

    public String[] getSupportedServiceNames()
    {
        return m_serviceNames;
    }

    // com.sun.star.frame.XDispatchProvider:
    public com.sun.star.frame.XDispatch queryDispatch(com.sun.star.util.URL aURL,
            String sTargetFrameName,
            int iSearchFlags)
    {

        return this;
    }

    // com.sun.star.frame.XDispatchProvider:
    public com.sun.star.frame.XDispatch[] queryDispatches(
            com.sun.star.frame.DispatchDescriptor[] seqDescriptors)
    {
        int nCount = seqDescriptors.length;
        com.sun.star.frame.XDispatch[] seqDispatcher =
                new com.sun.star.frame.XDispatch[seqDescriptors.length];

        for (int i = 0; i < nCount; ++i)
        {
            seqDispatcher[i] = queryDispatch(seqDescriptors[i].FeatureURL,
                    seqDescriptors[i].FrameName,
                    seqDescriptors[i].SearchFlags);
        }
        return seqDispatcher;
    }

    // com.sun.star.lang.XInitialization:
    public void initialize(Object[] object)
            throws com.sun.star.uno.Exception
    {
        if (object.length > 0)
        {
            m_xFrame = (com.sun.star.frame.XFrame) UnoRuntime.queryInterface(
                    com.sun.star.frame.XFrame.class, object[0]);
        }
    }

    // com.sun.star.frame.XDispatch:
    public void dispatch(com.sun.star.util.URL aURL,
            com.sun.star.beans.PropertyValue[] aArguments)
    {

        if (aURL.Protocol.compareTo("org.semanticwb.openoffice.calc.wb4calcaddon:") == 0)
        {
            try
            {
                OfficeDocument document = new WB4Calc(this.m_xContext);
                if (aURL.Path.compareTo("publish") == 0)
                {
                    document.publish();
                    return;
                }
                if (aURL.Path.compareTo("save") == 0)
                {
                    document.saveToSite();
                    return;
                }
                if (aURL.Path.compareTo("open") == 0)
                {
                    WB4CalcApplication application = new WB4CalcApplication(this.m_xContext);
                    application.open(DocumentType.EXCEL);
                    return;
                }
                if (aURL.Path.compareTo("delete") == 0)
                {
                    document.delete();
                    return;
                }
                if (aURL.Path.compareTo("detail") == 0)
                {
                    document.showDocumentDetail();
                    return;
                }
                if (aURL.Path.compareTo("view") == 0)
                {
                    // add your own code here
                    return;
                }
                if (aURL.Path.compareTo("information") == 0)
                {
                    document.showDocumentInfo();
                    return;
                }
                if (aURL.Path.compareTo("rules") == 0)
                {
                    // add your own code here
                    return;
                }
                if (aURL.Path.compareTo("deleteAssociation") == 0)
                {
                    document.deleteAssociation();
                    return;
                }
                if (aURL.Path.compareTo("addLink") == 0)
                {
                    document.insertLink();
                    return;
                }
                if (aURL.Path.compareTo("createSection") == 0)
                {
                    WB4CalcApplication.createPage();
                    return;
                }
                if (aURL.Path.compareTo("changePassword") == 0)
                {
                    WB4CalcApplication.changePassword();
                    return;
                }
                if (aURL.Path.compareTo("help") == 0)
                {
                    WB4CalcApplication.showHelp();
                    return;
                }
                if (aURL.Path.compareTo("about") == 0)
                {
                    WB4CalcApplication.showAbout();
                    return;
                }
                if (aURL.Path.compareTo("openSession") == 0)
                {
                    boolean logged = OfficeApplication.isLogged();
                    OfficeApplication.openSession();
                    if (OfficeApplication.isLogged() == true && logged == false)
                    {
                        JOptionPane.showMessageDialog(null, "¡Se ha iniciado una sesión!", "Iniciar sesión", JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                if (aURL.Path.compareTo("closeSession") == 0)
                {
                    OfficeApplication.closeSession();
                    JOptionPane.showMessageDialog(null, "¡Se ha cerrado la sesión!", "Cerrar sesión", JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (aURL.Path.compareTo("showDocumentsToAuthorize") == 0)
                {
                    OfficeApplication.showContentsToAuthorize();
                }
            }
            catch (Exception e)
            {
            }

        }
    }

    public void addStatusListener(com.sun.star.frame.XStatusListener xControl,
            com.sun.star.util.URL aURL)
    {
        FeatureStateEvent aState = new FeatureStateEvent();
        aState.FeatureURL = aURL;
        if (aURL.Protocol.compareTo("org.semanticwb.openoffice.calc.wb4calcaddon:") == 0)
        {
            try
            {
                //WB4Calc document = new WB4Calc(this.m_xContext);
                if (aURL.Path.compareTo("publish") == 0)
                {
                    WB4Calc document = new WB4Calc(this.m_xContext);
                    if (document.isPublicated())
                    {
                        aState.IsEnabled = true;
                    }
                    else
                    {
                        aState.IsEnabled = false;
                    }
                }
                else if (aURL.Path.compareTo("delete") == 0)
                {
                    WB4Calc document = new WB4Calc(this.m_xContext);
                    if (document.isPublicated())
                    {
                        aState.IsEnabled = true;
                    }
                    else
                    {
                        aState.IsEnabled = false;
                    }
                }
                else if (aURL.Path.compareTo("view") == 0)
                {
                    WB4Calc document = new WB4Calc(this.m_xContext);
                    if (document.isPublicated())
                    {
                        aState.IsEnabled = true;
                    }
                    else
                    {
                        aState.IsEnabled = false;
                    }
                }
                else  if (aURL.Path.compareTo("information") == 0)
                {
                    WB4Calc document = new WB4Calc(this.m_xContext);
                    if (document.isPublicated())
                    {
                        aState.IsEnabled = true;
                    }
                    else
                    {
                        aState.IsEnabled = false;
                    }
                }
                else  if (aURL.Path.compareTo("rules") == 0)
                {
                    WB4Calc document = new WB4Calc(this.m_xContext);
                    if (document.isPublicated())
                    {
                        aState.IsEnabled = true;
                    }
                    else
                    {
                        aState.IsEnabled = false;
                    }
                }
                else if (aURL.Path.compareTo("deleteAssociation") == 0)
                {
                    WB4Calc document = new WB4Calc(this.m_xContext);
                    if (document.isPublicated())
                    {
                        aState.IsEnabled = true;
                    }
                    else
                    {
                        aState.IsEnabled = false;
                    }
                }
                else if (aURL.Path.compareTo("changePassword") == 0)
                {
                    if (WB4CalcApplication.isLogged())
                    {
                        aState.IsEnabled = true;
                    }
                    else
                    {
                        aState.IsEnabled = false;
                    }
                }
                else if (aURL.Path.compareTo("openSession") == 0)
                {
                    if (OfficeApplication.isLogged())
                    {
                        aState.IsEnabled = false;
                    }
                    else
                    {
                        aState.IsEnabled = true;
                    }
                }
                else if (aURL.Path.compareTo("closeSession") == 0)
                {
                    if (!OfficeApplication.isLogged())
                    {
                        aState.IsEnabled = false;
                    }
                    else
                    {
                        aState.IsEnabled = true;
                    }
                }
                else
                {
                    aState.IsEnabled = true;
                }
            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }
        }        
        xControl.statusChanged(aState);
    }

    public void removeStatusListener(com.sun.star.frame.XStatusListener xControl,
            com.sun.star.util.URL aURL)
    {
        // add your own code here
    }
}
