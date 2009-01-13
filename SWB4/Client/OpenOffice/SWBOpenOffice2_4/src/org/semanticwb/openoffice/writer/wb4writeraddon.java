package org.semanticwb.openoffice.writer;

import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.lib.uno.helper.Factory;
import com.sun.star.lang.XSingleComponentFactory;
import com.sun.star.registry.XRegistryKey;
import com.sun.star.lib.uno.helper.WeakBase;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.semanticwb.openoffice.DocumentType;
import org.semanticwb.openoffice.OfficeDocument;
import org.semanticwb.openoffice.ui.dialogs.ErrorDialog;

public final class wb4writeraddon extends WeakBase
        implements com.sun.star.lang.XServiceInfo,
        com.sun.star.frame.XDispatchProvider,
        com.sun.star.lang.XInitialization,
        com.sun.star.frame.XDispatch
{

    private final XComponentContext m_xContext;
    private com.sun.star.frame.XFrame m_xFrame;
    private static final String m_implementationName = wb4writeraddon.class.getName();
    private static final String[] m_serviceNames =
    {
        "com.sun.star.frame.ProtocolHandler"
    };
    //private EventListener listener=new EventListener();
    public wb4writeraddon(XComponentContext context)
    {

        m_xContext = context;
    /*XMultiComponentFactory serviceManager = m_xContext.getServiceManager();
    try
    {
    Object xGlobalBroadCaster = serviceManager.createInstanceWithContext("com.sun.star.frame.GlobalEventBroadcaster", m_xContext);
    
    XEventBroadcaster xEventBroad = (XEventBroadcaster) UnoRuntime.queryInterface(XEventBroadcaster.class, xGlobalBroadCaster);
    
    xEventBroad.addEventListener(listener);
    }
    catch (com.sun.star.uno.Exception e)
    {
    System.out.println(e.getMessage());
    }*/

    }

    public static XSingleComponentFactory __getComponentFactory(String sImplementationName)
    {
        XSingleComponentFactory xFactory = null;

        if (sImplementationName.equals(m_implementationName))
        {
            xFactory = Factory.createComponentFactory(wb4writeraddon.class, m_serviceNames);
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
        if (aURL.Protocol.compareTo("org.semanticwb.openoffice.writer.wb4writeraddon:") == 0)
        {
            
            try
            {
                OfficeDocument document = new WB4Writer(this.m_xContext);
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
                    if(document.isPublicated())
                    {
                        return this;
                    }
                    else
                    {
                        return null;
                    }
                }
                if (aURL.Path.compareTo("view") == 0)
                {                    
                    if(document.isPublicated())
                    {
                        return this;
                    }
                    else
                    {
                        return null;
                    }
                }
                if (aURL.Path.compareTo("information") == 0)
                {
                    
                    if(document.isPublicated())
                    {
                        return this;
                    }
                    else
                    {
                        return null;
                    }
                }
                if (aURL.Path.compareTo("rules") == 0)
                {
                    return this;
                }
                if (aURL.Path.compareTo("deleteAssociation") == 0)
                {                    
                    if(document.isPublicated())
                    {
                        return this;
                    }
                    else
                    {
                        return null;
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
        try
        {
            if (aURL.Protocol.compareTo("org.semanticwb.openoffice.writer.wb4writeraddon:") == 0)
            {
                OfficeDocument document = new WB4Writer(this.m_xContext);
                if (aURL.Path.compareTo("publish") == 0)
                {   
                    document.publish();

                    return;
                }
                if (aURL.Path.compareTo("open") == 0)
                {
                    WB4WriterApplication application = new WB4WriterApplication(this.m_xContext);
                    application.open(DocumentType.WORD);
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
        catch (Exception e)
        {
            ErrorDialog errDialog=new ErrorDialog(new JFrame(),true,e);
            errDialog.setVisible(true);
            return;
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
