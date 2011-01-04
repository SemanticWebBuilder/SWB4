package org.semanticwb.opensocial.model;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import org.semanticwb.opensocial.resources.SocialContainer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Gadget extends org.semanticwb.opensocial.model.base.GadgetBase
{

    private Document doc;

    public Gadget(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public FeatureDetail[] getFeatureDetails()
    {
        getDocument();
        ArrayList<FeatureDetail> getFeatureDetails = new ArrayList<FeatureDetail>();
        if (doc!=null && doc.getElementsByTagName("ModulePrefs").getLength() > 0)
        {
            Element module = (Element) doc.getElementsByTagName("ModulePrefs").item(0);
            NodeList childs = module.getChildNodes();
            for (int i = 0; i < childs.getLength(); i++)
            {
                if (childs.item(i) instanceof Element && ((Element) childs.item(i)).getTagName().equals("Require"))
                {
                    Element require = (Element) childs.item(i);
                    if (require.getAttribute("feature") != null && !"".equals(require.getAttribute("feature")))
                    {
                        String feature = require.getAttribute("feature");
                        if (feature != null)
                        {
                            StringTokenizer st = new StringTokenizer(",");
                            while (st.hasMoreTokens())
                            {
                                String featureName = st.nextToken().trim();
                                FeatureDetail detail = new FeatureDetail();
                                detail.name = featureName;
                                NodeList params = require.getChildNodes();
                                ArrayList<Parameter> parameters = new ArrayList<Parameter>();
                                for (int j = 0; j < params.getLength(); j++)
                                {
                                    if (params.item(j) instanceof Element && ((Element) params.item(j)).getTagName().equals("Param"))
                                    {
                                        Element param = (Element) params.item(j);
                                        String name = param.getAttribute("name");
                                        if (name != null)
                                        {
                                            Parameter p = new Parameter();
                                            p.name = name;
                                            parameters.add(p);
                                        }
                                    }
                                }
                                detail.parameters = parameters.toArray(new Parameter[parameters.size()]);
                                getFeatureDetails.add(detail);
                            }
                        }
                    }
                }
            }
        }
        return getFeatureDetails.toArray(new FeatureDetail[getFeatureDetails.size()]);
    }

    public Document getDocument()
    {
        //if (doc == null)
        {
            try
            {
                doc = SocialContainer.getXML(new URL(this.getUrl()));
            }
            catch (Exception e)
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
            if (doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) doc.getElementsByTagName("ModulePrefs").item(0);
                String thumbnail = module.getAttribute("thumbnail");
                URI urlthumbnail = new URI(thumbnail);
                URI gadget = new URI(this.getUrl());
                if (!urlthumbnail.isAbsolute())
                {
                    urlthumbnail = gadget.resolve(urlthumbnail);
                }
                return urlthumbnail.toURL();
            }
        }
        catch (Exception e)
        {
        }
        return null;
    }

    public URL getScreenshot()
    {
        getDocument();
        try
        {
            if (doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) doc.getElementsByTagName("ModulePrefs").item(0);
                String screenshot = module.getAttribute("screenshot");
                URI urlscreenshot = new URI(screenshot);
                URI gadget = new URI(this.getUrl());
                if (!urlscreenshot.isAbsolute())
                {
                    urlscreenshot = gadget.resolve(urlscreenshot);
                }
                return urlscreenshot.toURL();
            }
        }
        catch (Exception e)
        {
        }
        return null;
    }

    public String getTitle()
    {
        getDocument();
        try
        {
            if (doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) doc.getElementsByTagName("ModulePrefs").item(0);
                return module.getAttribute("title");
            }
        }
        catch (Exception e)
        {
        }
        return null;
    }

    public String getAuthor()
    {
        getDocument();
        try
        {
            if (doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) doc.getElementsByTagName("ModulePrefs").item(0);
                return module.getAttribute("author");
            }
        }
        catch (Exception e)
        {
        }
        return null;
    }

    public String getAuthorEmail()
    {
        getDocument();
        try
        {
            if (doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) doc.getElementsByTagName("ModulePrefs").item(0);
                return module.getAttribute("author_email");
            }
        }
        catch (Exception e)
        {
        }
        return null;
    }

    public int getWidth()
    {
        getDocument();
        try
        {
            if (doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) doc.getElementsByTagName("ModulePrefs").item(0);
                String width = module.getAttribute("width");
                if (width != null)
                {
                    try
                    {
                        return Integer.parseInt(width);
                    }
                    catch (NumberFormatException nfe)
                    {
                        return 0;
                    }
                }
            }
        }
        catch (Exception e)
        {
        }
        return 0;
    }

    public int getHeight()
    {
        getDocument();
        try
        {
            if (doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) doc.getElementsByTagName("ModulePrefs").item(0);
                String width = module.getAttribute("height");
                if (width != null)
                {
                    try
                    {
                        return Integer.parseInt(width);
                    }
                    catch (NumberFormatException nfe)
                    {
                        return 0;
                    }
                }
            }
        }
        catch (Exception e)
        {
        }
        return 0;
    }

    public String getDescription()
    {
        getDocument();
        try
        {
            if (doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) doc.getElementsByTagName("ModulePrefs").item(0);
                return module.getAttribute("description");
            }
        }
        catch (Exception e)
        {
        }
        return null;
    }

    public String[] getFeatures()
    {
        HashSet<String> getFeatures = new HashSet<String>();
        getDocument();
        try
        {
            if (doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) doc.getElementsByTagName("ModulePrefs").item(0);
                NodeList childs = module.getChildNodes();
                for (int i = 0; i < childs.getLength(); i++)
                {
                    if (childs.item(i) instanceof Element && ((Element) childs.item(i)).getTagName().equals("Require"))
                    {
                        Element require = (Element) childs.item(i);
                        if (require.getAttribute("feature") != null && !"".equals(require.getAttribute("feature")))
                        {
                            getFeatures.add(require.getAttribute("feature").trim());
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
        }
        return getFeatures.toArray(new String[getFeatures.size()]);
    }

    public String[] getCategories()
    {
        HashSet<String> getFeatures = new HashSet<String>();
        getDocument();
        try
        {
            if (doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) doc.getElementsByTagName("ModulePrefs").item(0);
                String category = module.getAttribute("category");
                if (category != null)
                {
                    StringTokenizer st = new StringTokenizer(",");
                    while (st.hasMoreTokens())
                    {
                        getFeatures.add(st.nextToken().trim());
                    }
                }
            }
        }
        catch (Exception e)
        {
        }
        return getFeatures.toArray(new String[getFeatures.size()]);
    }

    private View createView(Element content, String viewName)
    {
        View view = new View();
        view.name = viewName;
        String type = "html";
        if (content.getAttribute("type") != null)
        {
            type = content.getAttribute("type");
        }
        view.type = type;
        int preferredHeight = 0;
        if (content.getAttribute("preferred_height") != null)
        {
            try
            {
                preferredHeight = Integer.parseInt(content.getAttribute("preferred_height"));
            }
            catch (NumberFormatException bfe)
            {
            }
        }
        view.preferredHeight = preferredHeight;


        int preferredWidth = 0;
        if (content.getAttribute("preferred_width") != null)
        {
            try
            {
                preferredWidth = Integer.parseInt(content.getAttribute("preferred_width"));
            }
            catch (NumberFormatException bfe)
            {
            }
        }
        view.preferredWidth = preferredWidth;



        boolean scrolling = true;
        if (content.getAttribute("scrolling") != null)
        {
            try
            {
                scrolling = Boolean.parseBoolean(content.getAttribute("scrolling"));
            }
            catch (NumberFormatException bfe)
            {
            }
        }
        view.scrolling = scrolling;
        return view;
    }

    public View[] getViews()
    {
        HashSet<View> getViews = new HashSet<View>();
        getDocument();
        try
        {
            if (doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) doc.getElementsByTagName("ModulePrefs").item(0);
                NodeList childs = module.getChildNodes();
                for (int i = 0; i < childs.getLength(); i++)
                {
                    if (childs.item(i) instanceof Element && ((Element) childs.item(i)).getTagName().equals("Content"))
                    {
                        Element content = (Element) childs.item(i);
                        String view = content.getAttribute("view");
                        if (view != null)
                        {
                            StringTokenizer st = new StringTokenizer(",");
                            while (st.hasMoreTokens())
                            {
                                getViews.add(createView(content, st.nextToken().trim()));
                            }
                        }
                        else
                        {
                            getViews.add(createView(content, "default"));
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
        }
        return getViews.toArray(new View[getViews.size()]);
    }

    public String getTitleUrl()
    {
        getDocument();
        try
        {
            if (doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) doc.getElementsByTagName("ModulePrefs").item(0);
                return module.getAttribute("title_url");
            }
        }
        catch (Exception e)
        {
        }
        return null;
    }

    public boolean getScrolling()
    {
        getDocument();
        try
        {
            if (doc.getElementsByTagName("ModulePrefs").getLength() > 0)
            {
                Element module = (Element) doc.getElementsByTagName("ModulePrefs").item(0);
                String scrolling = module.getAttribute("scrolling");
                if ("true".equals(scrolling))
                {
                    return true;
                }
            }
        }
        catch (Exception e)
        {
        }
        return false;
    }
}
