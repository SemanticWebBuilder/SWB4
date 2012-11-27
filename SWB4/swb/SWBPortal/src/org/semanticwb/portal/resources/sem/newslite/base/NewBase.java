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
package org.semanticwb.portal.resources.sem.newslite.base;


// TODO: Auto-generated Javadoc
/**
 * The Class NewBase.
 */
public abstract class NewBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    
    /** The Constant news_created. */
    public static final org.semanticwb.platform.SemanticProperty news_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#created");
    
    /** The Constant news_newsThumbnail. */
    public static final org.semanticwb.platform.SemanticProperty news_newsThumbnail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#newsThumbnail");
    
    /** The Constant news_expiration. */
    public static final org.semanticwb.platform.SemanticProperty news_expiration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#expiration");
    
    /** The Constant news_image. */
    public static final org.semanticwb.platform.SemanticProperty news_image=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#image");
    
    /** The Constant news_body. */
    public static final org.semanticwb.platform.SemanticProperty news_body=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#body");
    
    /** The Constant news_Category. */
    public static final org.semanticwb.platform.SemanticClass news_Category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/newslite#Category");
    
    /** The Constant news_category. */
    public static final org.semanticwb.platform.SemanticProperty news_category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#category");
    
    /** The Constant swb_User. */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    
    /** The Constant news_creator. */
    public static final org.semanticwb.platform.SemanticProperty news_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#creator");
    
    /** The Constant news_source. */
    public static final org.semanticwb.platform.SemanticProperty news_source=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#source");
    
    /** The Constant news_author. */
    public static final org.semanticwb.platform.SemanticProperty news_author=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#author");
    
    /** The Constant news_New. */
    public static final org.semanticwb.platform.SemanticClass news_New=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/newslite#New");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/newslite#New");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List news.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.newslite.New> listNews(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.newslite.New>(it, true);
        }

        /**
         * List news.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.newslite.New> listNews()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.newslite.New>(it, true);
        }

        /**
         * Creates the new.
         * 
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.newslite. new
         */
        public static org.semanticwb.portal.resources.sem.newslite.New createNew(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.newslite.New.ClassMgr.createNew(String.valueOf(id), model);
        }

        /**
         * Gets the new.
         * 
         * @param id the id
         * @param model the model
         * @return the new
         */
        public static org.semanticwb.portal.resources.sem.newslite.New getNew(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.newslite.New)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the new.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.newslite. new
         */
        public static org.semanticwb.portal.resources.sem.newslite.New createNew(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.newslite.New)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the new.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeNew(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for new.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasNew(String id, org.semanticwb.model.SWBModel model)
        {
            return (getNew(id, model)!=null);
        }

        /**
         * List new by category.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.newslite.New> listNewByCategory(org.semanticwb.portal.resources.sem.newslite.Category value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.newslite.New> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(news_category, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List new by category.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.newslite.New> listNewByCategory(org.semanticwb.portal.resources.sem.newslite.Category value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.newslite.New> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(news_category,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List new by creator.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.newslite.New> listNewByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.newslite.New> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(news_creator, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List new by creator.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.newslite.New> listNewByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.newslite.New> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(news_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new new base.
     * 
     * @param base the base
     */
    public NewBase(org.semanticwb.platform.SemanticObject base)
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
        return getSemanticObject().getDateProperty(news_created);
    }

    /**
     * Sets the created.
     * 
     * @param value the new created
     */
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(news_created, value);
    }

    /**
     * Gets the news thumbnail.
     * 
     * @return the news thumbnail
     */
    public String getNewsThumbnail()
    {
        return getSemanticObject().getProperty(news_newsThumbnail);
    }

    /**
     * Sets the news thumbnail.
     * 
     * @param value the new news thumbnail
     */
    public void setNewsThumbnail(String value)
    {
        getSemanticObject().setProperty(news_newsThumbnail, value);
    }

    /**
     * Gets the expiration.
     * 
     * @return the expiration
     */
    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(news_expiration);
    }

    /**
     * Sets the expiration.
     * 
     * @param value the new expiration
     */
    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(news_expiration, value);
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
     * Gets the image.
     * 
     * @return the image
     */
    public String getImage()
    {
        return getSemanticObject().getProperty(news_image);
    }

    /**
     * Sets the image.
     * 
     * @param value the new image
     */
    public void setImage(String value)
    {
        getSemanticObject().setProperty(news_image, value);
    }

    /**
     * Gets the body.
     * 
     * @return the body
     */
    public String getBody()
    {
        return getSemanticObject().getProperty(news_body);
    }

    /**
     * Sets the body.
     * 
     * @param value the new body
     */
    public void setBody(String value)
    {
        getSemanticObject().setProperty(news_body, value);
    }

    /**
     * Sets the category.
     * 
     * @param value the new category
     */
    public void setCategory(org.semanticwb.portal.resources.sem.newslite.Category value)
    {
        getSemanticObject().setObjectProperty(news_category, value.getSemanticObject());
    }

    /**
     * Removes the category.
     */
    public void removeCategory()
    {
        getSemanticObject().removeProperty(news_category);
    }

    /**
     * Gets the category.
     * 
     * @return the category
     */
    public org.semanticwb.portal.resources.sem.newslite.Category getCategory()
    {
         org.semanticwb.portal.resources.sem.newslite.Category ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(news_category);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.newslite.Category)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the creator.
     * 
     * @param value the new creator
     */
    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(news_creator, value.getSemanticObject());
    }

    /**
     * Removes the creator.
     */
    public void removeCreator()
    {
        getSemanticObject().removeProperty(news_creator);
    }

    /**
     * Gets the creator.
     * 
     * @return the creator
     */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(news_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
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
     * Gets the source.
     * 
     * @return the source
     */
    public String getSource()
    {
        return getSemanticObject().getProperty(news_source);
    }

    /**
     * Sets the source.
     * 
     * @param value the new source
     */
    public void setSource(String value)
    {
        getSemanticObject().setProperty(news_source, value);
    }

    /**
     * Gets the author.
     * 
     * @return the author
     */
    public String getAuthor()
    {
        return getSemanticObject().getProperty(news_author);
    }

    /**
     * Sets the author.
     * 
     * @param value the new author
     */
    public void setAuthor(String value)
    {
        getSemanticObject().setProperty(news_author, value);
    }
}
