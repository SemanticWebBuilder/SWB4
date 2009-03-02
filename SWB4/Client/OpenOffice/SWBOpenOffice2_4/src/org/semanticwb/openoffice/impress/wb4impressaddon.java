package org.semanticwb.openoffice.impress;

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

public final class wb4impressaddon extends WeakBase
        implements com.sun.star.lang.XServiceInfo,
        com.sun.star.frame.XDispatchProvider,
        com.sun.star.lang.XInitialization,
        com.sun.star.frame.XDispatch
{

    private final XComponentContext m_xContext;
    private com.sun.star.frame.XFrame m_xFrame;
    private static final String m_implementationName = wb4impressaddon.class.getName();
    private static final String[] m_serviceNames =
    {
        "com.sun.star.frame.ProtocolHandler"
    };

    public wb4impressaddon(XComponentContext context)
    {
        m_xContext = context;
    }
    ;

    public static XSingleComponentFactory __getComponentFactory(String sImplementationName)
    {
        XSingleComponentFactory xFactory = null;

        if (sImplementationName.equals(m_implementationName))
        {
            xFactory = Factory.createComponentFactory(wb4impressaddon.class, m_serviceNames);
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
        if (aURL.Protocol.compareTo("org.semanticwb.openoffice.impress.wb4impressaddon:") == 0)
        {
            try
            {
                WB4Impress document = new WB4Impress(this.m_xContext);
                if (aURL.Path.compareTo("save") == 0)
                {
                    return this;
                }
                if (aURL.Path.compareTo("publish") == 0)
                {
                    if (document.isPublicated())
                    {
                        return this;
                    }
                }
                if (aURL.Path.compareTo("open") == 0)
                {
                    return this;
                }
                if (aURL.Path.compareTo("delete") == 0)
                {
                    if (document.isPublicated())
                    {
                        return this;
                    }

                }
                if (aURL.Path.compareTo("view") == 0)
                {
                    if (document.isPublicated())
                    {
                        return this;
                    }
                }
                if (aURL.Path.compareTo("information") == 0)
                {
                    if (document.isPublicated())
                    {
                        return this;
                    }
                }
                if (aURL.Path.compareTo("rules") == 0)
                {
                    if (document.isPublicated())
                    {
                        return this;
                    }
                    else
                    {
                        return null;
                    }
                }
                if (aURL.Path.compareTo("deleteAssociation") == 0)
                {
                    if (document.isPublicated())
                    {
                        return this;
                    }
                }
                if (aURL.Path.compareTo("addLink") == 0)
                {
                    return this;
                }
                if (aURL.Path.compareTo("createSection") == 0)
                {
                    return this;
                }
                if (aURL.Path.compareTo("changePassword") == 0)
                {
                    if (WB4ImpressApplication.isLogged())
                    {
                        return this;
                    }
                }
                if (aURL.Path.compareTo("help") == 0)
                {
                    return this;
                }
                if (aURL.Path.compareTo("about") == 0)
                {
                    return this;
                }
                if (aURL.Path.compareTo("closeSession") == 0)
                {
                    if (WB4ImpressApplication.isLogged())
                    {
                        return this;
                    }
                }
                if (aURL.Path.compareTo("openSession") == 0)
                {
                    if (OfficeApplication.isLogged())
                    {
                        return null;
                    }
                    else
                    {
                        return this;
                    }
                }

                if (aURL.Path.compareTo("closeSession") == 0)
                {
                    if (!OfficeApplication.isLogged())
                    {
                        return null;
                    }
                    else
                    {
                        return this;
                    }
                }
                if (aURL.Path.compareTo("showDocumentsToAuthorize") == 0)
                {
                    return this;
                }
            }
            catch (Exception e)
            {
            }
        }
        return null;
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
        if (aURL.Protocol.compareTo("org.semanticwb.openoffice.impress.wb4impressaddon:") == 0)
        {
            try
            {
                OfficeDocument document = new WB4Impress(this.m_xContext);
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
                    WB4ImpressApplication application = new WB4ImpressApplication(this.m_xContext);
                    application.open(DocumentType.PPT);
                    return;
                }
                if (aURL.Path.compareTo("delete") == 0)
                {
                    document.delete();
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
                    WB4ImpressApplication.createPage();
                    return;
                }
                if (aURL.Path.compareTo("changePassword") == 0)
                {
                    WB4ImpressApplication.changePassword();
                    return;
                }
                if (aURL.Path.compareTo("help") == 0)
                {
                    WB4ImpressApplication.showHelp();
                    return;
                }
                if (aURL.Path.compareTo("about") == 0)
                {
                    WB4ImpressApplication.showAbout();
                    return;
                }
                if (aURL.Path.compareTo("openSession") == 0)
                {
                    boolean logged = OfficeApplication.isLogged();
                    OfficeApplication.openSession();
                    if (OfficeApplication.isLogged() == true && logged == false)
                    {
                        JOptionPane.showMessageDialog(null, "¡Se ha iniciado una sesión", "Iniciar sesión", JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                if (aURL.Path.compareTo("closeSession") == 0)
                {
                    OfficeApplication.closeSession();
                    JOptionPane.showMessageDialog(null, "¡Se ha cerrado la sesión", "Cerrar sesión", JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
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
        if (queryDispatch(aURL, "", 0) == null)
        {
            aState.IsEnabled = false;
        }
        else
        {
            aState.IsEnabled = true;
        }
        xControl.statusChanged(aState);
    }

    public void removeStatusListener(com.sun.star.frame.XStatusListener xControl,
            com.sun.star.util.URL aURL)
    {
        // add your own code here
    }
}
