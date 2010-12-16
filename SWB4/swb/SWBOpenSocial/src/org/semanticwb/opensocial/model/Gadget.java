package org.semanticwb.opensocial.model;

import java.net.URI;
import java.net.URL;
import org.semanticwb.opensocial.resources.SocialContainer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Gadget extends org.semanticwb.opensocial.model.base.GadgetBase 
{
    private Document doc;
    public Gadget(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public Document getDocument()
    {
        if(doc==null)
        {
            try
            {
                doc=SocialContainer.getXML(new URL(this.getUrl()));
            }
            catch(Exception e)
            {
                
            }
        }
        return doc;
    }
    
    public URL getThumbnail()
    {
        getDocument();
        try
        {
            if(doc.getElementsByTagName("ModulePrefs").getLength()>0)
            {
                Element module=(Element)doc.getElementsByTagName("ModulePrefs").item(0);
                String thumbnail=module.getAttribute("thumbnail");
                URI urlthumbnail=new URI(thumbnail);
                URI gadget=new URI(this.getUrl());
                if(!urlthumbnail.isAbsolute())
                {
                    urlthumbnail=gadget.resolve(urlthumbnail);
                }
                return urlthumbnail.toURL();
            }
        }
        catch(Exception e)
        {

        }
        return null;
    }

    public URL getScreenshot()
    {
        getDocument();
        try
        {
            if(doc.getElementsByTagName("ModulePrefs").getLength()>0)
            {
                Element module=(Element)doc.getElementsByTagName("ModulePrefs").item(0);
                String screenshot=module.getAttribute("screenshot");
                URI urlscreenshot=new URI(screenshot);
                URI gadget=new URI(this.getUrl());
                if(!urlscreenshot.isAbsolute())
                {
                    urlscreenshot=gadget.resolve(urlscreenshot);
                }
                return urlscreenshot.toURL();
            }
        }
        catch(Exception e)
        {

        }
        return null;
    }
    public String getTitle()
    {
        getDocument();
        try
        {
            if(doc.getElementsByTagName("ModulePrefs").getLength()>0)
            {
                Element module=(Element)doc.getElementsByTagName("ModulePrefs").item(0);
                return module.getAttribute("title");
            }
        }
        catch(Exception e)
        {

        }
        return null;
    }
    public String getCategory()
    {
        getDocument();
        try
        {
            if(doc.getElementsByTagName("ModulePrefs").getLength()>0)
            {
                Element module=(Element)doc.getElementsByTagName("ModulePrefs").item(0);
                return module.getAttribute("category");
            }
        }
        catch(Exception e)
        {

        }
        return null;
    }
    public boolean getScrolling()
    {
        getDocument();
        try
        {
            if(doc.getElementsByTagName("ModulePrefs").getLength()>0)
            {
                Element module=(Element)doc.getElementsByTagName("ModulePrefs").item(0);
                String scrolling=module.getAttribute("scrolling");
                if("true".equals(scrolling))
                {
                    return true;
                }
            }
        }
        catch(Exception e)
        {

        }
        return false;
    }
}
