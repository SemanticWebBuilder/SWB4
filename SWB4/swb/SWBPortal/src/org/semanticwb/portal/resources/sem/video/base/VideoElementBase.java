/*
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
 */
package org.semanticwb.portal.resources.sem.video.base;


// TODO: Auto-generated Javadoc
/**
 * The Class VideoElementBase.
 */
public abstract class VideoElementBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Priorityable,org.semanticwb.model.Searchable,org.semanticwb.model.Tagable,org.semanticwb.model.Traceable,org.semanticwb.model.Viewable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable
{
    
    /** The Constant swbv_videoType. */
    public static final org.semanticwb.platform.SemanticProperty swbv_videoType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBVideo#videoType");
    
    /** The Constant swbv_videoDuration. */
    public static final org.semanticwb.platform.SemanticProperty swbv_videoDuration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBVideo#videoDuration");
    
    /** The Constant swbv_videoCode. */
    public static final org.semanticwb.platform.SemanticProperty swbv_videoCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBVideo#videoCode");
    
    /** The Constant swb_WebPage. */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    
    /** The Constant swbv_videoWebPage. */
    public static final org.semanticwb.platform.SemanticProperty swbv_videoWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBVideo#videoWebPage");
    
    /** The Constant swbv_videoPreview. */
    public static final org.semanticwb.platform.SemanticProperty swbv_videoPreview=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBVideo#videoPreview");
    
    /** The Constant swbv_videoSize. */
    public static final org.semanticwb.platform.SemanticProperty swbv_videoSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBVideo#videoSize");
    
    /** The Constant swbv_VideoElement. */
    public static final org.semanticwb.platform.SemanticClass swbv_VideoElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBVideo#VideoElement");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBVideo#VideoElement");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List video elements.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.video.VideoElement> listVideoElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.video.VideoElement>(it, true);
        }

        /**
         * List video elements.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.video.VideoElement> listVideoElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.video.VideoElement>(it, true);
        }

        /**
         * Creates the video element.
         * 
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.video. video element
         */
        public static org.semanticwb.portal.resources.sem.video.VideoElement createVideoElement(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.video.VideoElement.ClassMgr.createVideoElement(String.valueOf(id), model);
        }

        /**
         * Gets the video element.
         * 
         * @param id the id
         * @param model the model
         * @return the video element
         */
        public static org.semanticwb.portal.resources.sem.video.VideoElement getVideoElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.video.VideoElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the video element.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.video. video element
         */
        public static org.semanticwb.portal.resources.sem.video.VideoElement createVideoElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.video.VideoElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the video element.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeVideoElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for video element.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasVideoElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getVideoElement(id, model)!=null);
        }

        /**
         * List video element by modified by.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.video.VideoElement> listVideoElementByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.video.VideoElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List video element by modified by.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.video.VideoElement> listVideoElementByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.video.VideoElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List video element by web page.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.video.VideoElement> listVideoElementByWebPage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.video.VideoElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbv_videoWebPage, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List video element by web page.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.video.VideoElement> listVideoElementByWebPage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.video.VideoElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbv_videoWebPage,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List video element by creator.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.video.VideoElement> listVideoElementByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.video.VideoElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List video element by creator.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.video.VideoElement> listVideoElementByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.video.VideoElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new video element base.
     * 
     * @param base the base
     */
    public VideoElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the created.
     * 
     * @return the created
     */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    /**
     * Sets the created.
     * 
     * @param value the new created
     */
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    /**
     * Sets the modified by.
     * 
     * @param value the new modified by
     */
    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    /**
     * Removes the modified by.
     */
    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    /**
     * Gets the modified by.
     * 
     * @return the modified by
     */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the video type.
     * 
     * @return the video type
     */
    public String getVideoType()
    {
        return getSemanticObject().getProperty(swbv_videoType);
    }

    /**
     * Sets the video type.
     * 
     * @param value the new video type
     */
    public void setVideoType(String value)
    {
        getSemanticObject().setProperty(swbv_videoType, value);
    }

    /**
     * Gets the title.
     * 
     * @return the title
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    /**
     * Sets the title.
     * 
     * @param value the new title
     */
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    /**
     * Gets the title.
     * 
     * @param lang the lang
     * @return the title
     */
    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    /**
     * Gets the display title.
     * 
     * @param lang the lang
     * @return the display title
     */
    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    /**
     * Sets the title.
     * 
     * @param title the title
     * @param lang the lang
     */
    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    /**
     * Gets the updated.
     * 
     * @return the updated
     */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    /**
     * Sets the updated.
     * 
     * @param value the new updated
     */
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    /**
     * Gets the duration.
     * 
     * @return the duration
     */
    public int getDuration()
    {
        return getSemanticObject().getIntProperty(swbv_videoDuration);
    }

    /**
     * Sets the duration.
     * 
     * @param value the new duration
     */
    public void setDuration(int value)
    {
        getSemanticObject().setIntProperty(swbv_videoDuration, value);
    }

    /**
     * Gets the tags.
     * 
     * @return the tags
     */
    public String getTags()
    {
        return getSemanticObject().getProperty(swb_tags);
    }

    /**
     * Sets the tags.
     * 
     * @param value the new tags
     */
    public void setTags(String value)
    {
        getSemanticObject().setProperty(swb_tags, value);
    }

    /**
     * Gets the video code.
     * 
     * @return the video code
     */
    public String getVideoCode()
    {
        return getSemanticObject().getProperty(swbv_videoCode);
    }

    /**
     * Sets the video code.
     * 
     * @param value the new video code
     */
    public void setVideoCode(String value)
    {
        getSemanticObject().setProperty(swbv_videoCode, value);
    }

    /**
     * Sets the web page.
     * 
     * @param value the new web page
     */
    public void setWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(swbv_videoWebPage, value.getSemanticObject());
    }

    /**
     * Removes the web page.
     */
    public void removeWebPage()
    {
        getSemanticObject().removeProperty(swbv_videoWebPage);
    }

    /**
     * Gets the web page.
     * 
     * @return the web page
     */
    public org.semanticwb.model.WebPage getWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbv_videoWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the priority.
     * 
     * @return the priority
     */
    public int getPriority()
    {
        return getSemanticObject().getIntProperty(swb_priority);
    }

    /**
     * Sets the priority.
     * 
     * @param value the new priority
     */
    public void setPriority(int value)
    {
        getSemanticObject().setIntProperty(swb_priority, value);
    }

    /**
     * Checks if is active.
     * 
     * @return true, if is active
     */
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    /**
     * Sets the active.
     * 
     * @param value the new active
     */
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }

    /**
     * Gets the preview.
     * 
     * @return the preview
     */
    public String getPreview()
    {
        return getSemanticObject().getProperty(swbv_videoPreview);
    }

    /**
     * Sets the preview.
     * 
     * @param value the new preview
     */
    public void setPreview(String value)
    {
        getSemanticObject().setProperty(swbv_videoPreview, value);
    }

    /**
     * Gets the views.
     * 
     * @return the views
     */
    public long getViews()
    {
        //Override this method in VideoElement object
        return getSemanticObject().getLongProperty(swb_views,false);
    }

    /**
     * Sets the views.
     * 
     * @param value the new views
     */
    public void setViews(long value)
    {
        //Override this method in VideoElement object
        getSemanticObject().setLongProperty(swb_views, value,false);
    }

    /**
     * Sets the creator.
     * 
     * @param value the new creator
     */
    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    /**
     * Removes the creator.
     */
    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    /**
     * Gets the creator.
     * 
     * @return the creator
     */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the size.
     * 
     * @return the size
     */
    public int getSize()
    {
        return getSemanticObject().getIntProperty(swbv_videoSize);
    }

    /**
     * Sets the size.
     * 
     * @param value the new size
     */
    public void setSize(int value)
    {
        getSemanticObject().setIntProperty(swbv_videoSize, value);
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    /**
     * Sets the description.
     * 
     * @param value the new description
     */
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    /**
     * Gets the description.
     * 
     * @param lang the lang
     * @return the description
     */
    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    /**
     * Gets the display description.
     * 
     * @param lang the lang
     * @return the display description
     */
    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    /**
     * Sets the description.
     * 
     * @param description the description
     * @param lang the lang
     */
    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    /**
     * Gets the max views.
     * 
     * @return the max views
     */
    public long getMaxViews()
    {
        return getSemanticObject().getLongProperty(swb_maxViews);
    }

    /**
     * Sets the max views.
     * 
     * @param value the new max views
     */
    public void setMaxViews(long value)
    {
        getSemanticObject().setLongProperty(swb_maxViews, value);
    }
    
    public String getTags(String lang)
    {
        return getSemanticObject().getProperty(swb_tags, null, lang);
    }

    public String getDisplayTags(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_tags, lang);
    }

    public void setTags(String tags, String lang)
    {
        getSemanticObject().setProperty(swb_tags, tags, lang);
    }    
}
