package org.semanticwb.impress.addon;

import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.lib.uno.helper.Factory;
import com.sun.star.lang.XSingleComponentFactory;
import com.sun.star.registry.XRegistryKey;
import com.sun.star.lib.uno.helper.WeakBase;
import javax.swing.JOptionPane;
import org.semanticwb.openoffice.OfficeDocument;
import org.semanticwb.openoffice.OfficeDocumentHelper;
import org.semanticwb.openoffice.WBOfficeException;
import org.semanticwb.openoffice.impress.WB4Impress;
import org.semanticwb.openoffice.writer.WB4WriterApplication;
import org.semanticwb.openoffice.writer.WB4Writer;

public final class wb4impressaddon extends WeakBase
        implements com.sun.star.lang.XServiceInfo,
        com.sun.star.frame.XDispatchProvider,
        com.sun.star.lang.XInitialization,
        com.sun.star.frame.XDispatch
{

    private final XComponentContext m_xContext;
    private com.sun.star.frame.XFrame m_xFrame;
    private static final String m_implementationName = wb4impressaddon.class.getName();
    private static final String[] m_serviceNames = {
        "com.sun.star.frame.ProtocolHandler"
    };

    public wb4impressaddon(XComponentContext context)
    {
        m_xContext = context;
    }

    
    

       ;
    
           public static XSingleComponentFactory __getComponentFactory( String sImplementationName ) {
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
        if (aURL.Protocol.compareTo("org.semanticwb.impress.addon.wb4impressaddon:") == 0)
        {
            if (aURL.Path.compareTo("publish") == 0)
            {
                return this;
            }
            if (aURL.Path.compareTo("open") == 0)
            {
                return this;
            }
            if (aURL.Path.compareTo("delete") == 0)
            {
                return this;
            }
            if (aURL.Path.compareTo("view") == 0)
            {
                return this;
            }
            if (aURL.Path.compareTo("information") == 0)
            {
                return this;
            }
            if (aURL.Path.compareTo("rules") == 0)
            {
                return this;
            }
            if (aURL.Path.compareTo("deleteAssociation") == 0)
            {
                return this;
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
                return this;
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
                return this;
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
        if (aURL.Protocol.compareTo("org.semanticwb.impress.addon.wb4impressaddon:") == 0)
        {
            if (aURL.Path.compareTo("publish") == 0)
            {
                //System.setProperty("WizardDisplayer.default", "org.semanticwb.util.WBWizardDisplayerImpl");
                try
                {                   
                    OfficeDocument document = new WB4Impress(this.m_xContext);
                    OfficeDocumentHelper.publish(document);
                }
                catch (WBOfficeException wboe)
                {
                    JOptionPane.showMessageDialog(null, "Publicación de contenido", wboe.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
                // add your own code here
                return;
            }
            if (aURL.Path.compareTo("open") == 0)
            {
                WB4WriterApplication application = new WB4WriterApplication(this.m_xContext);
                OfficeDocumentHelper.open(application);
                return;
            }
            if (aURL.Path.compareTo("delete") == 0)
            {
                // add your own code here
                return;
            }
            if (aURL.Path.compareTo("view") == 0)
            {
                // add your own code here
                return;
            }
            if (aURL.Path.compareTo("information") == 0)
            {
                // add your own code here
                return;
            }
            if (aURL.Path.compareTo("rules") == 0)
            {
                // add your own code here
                return;
            }
            if (aURL.Path.compareTo("deleteAssociation") == 0)
            {
                // add your own code here
                return;
            }
            if (aURL.Path.compareTo("addLink") == 0)
            {
                // add your own code here
                return;
            }
            if (aURL.Path.compareTo("createSection") == 0)
            {
                // add your own code here
                return;
            }
            if (aURL.Path.compareTo("changePassword") == 0)
            {
                OfficeDocumentHelper.changePassword();
                return;
            }
            if (aURL.Path.compareTo("help") == 0)
            {
                // add your own code here
                return;
            }
            if (aURL.Path.compareTo("about") == 0)
            {
                // add your own code here
                return;
            }
            if (aURL.Path.compareTo("closeSession") == 0)
            {
                // add your own code here
                return;
            }

        }
    }

    public void addStatusListener(com.sun.star.frame.XStatusListener xControl,
            com.sun.star.util.URL aURL)
    {
    // add your own code here
    }

    public void removeStatusListener(com.sun.star.frame.XStatusListener xControl,
            com.sun.star.util.URL aURL)
    {
    // add your own code here
    }
}
