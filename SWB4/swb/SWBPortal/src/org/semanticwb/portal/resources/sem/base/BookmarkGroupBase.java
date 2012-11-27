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
package org.semanticwb.portal.resources.sem.base;


// TODO: Auto-generated Javadoc
/**
 * The Class BookmarkGroupBase.
 */
public class BookmarkGroupBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    
    /** The Constant swb_created. */
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    
    /** The Constant swb_User. */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    
    /** The Constant swb_modifiedBy. */
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    
    /** The Constant swb_res_bkm_entryCount. */
    public static final org.semanticwb.platform.SemanticProperty swb_res_bkm_entryCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#entryCount");
    
    /** The Constant swb_title. */
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    
    /** The Constant swb_updated. */
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    
    /** The Constant swb_res_bkm_BookmarkEntry. */
    public static final org.semanticwb.platform.SemanticClass swb_res_bkm_BookmarkEntry=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#BookmarkEntry");
    
    /** The Constant swb_res_bkm_hasEntry. */
    public static final org.semanticwb.platform.SemanticProperty swb_res_bkm_hasEntry=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#hasEntry");
    
    /** The Constant swb_creator. */
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    
    /** The Constant swb_description. */
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    
    /** The Constant swb_res_bkm_BookmarkGroup. */
    public static final org.semanticwb.platform.SemanticClass swb_res_bkm_BookmarkGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#BookmarkGroup");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#BookmarkGroup");

    /**
     * Instantiates a new bookmark group base.
     * 
     * @param base the base
     */
    public BookmarkGroupBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * List bookmark groups.
     * 
     * @param model the model
     * @return the java.util. iterator
     */
    public static java.util.Iterator<org.semanticwb.portal.resources.sem.BookmarkGroup> listBookmarkGroups(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.BookmarkGroup>(it, true);
    }

    /**
     * List bookmark groups.
     * 
     * @return the java.util. iterator
     */
    public static java.util.Iterator<org.semanticwb.portal.resources.sem.BookmarkGroup> listBookmarkGroups()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.BookmarkGroup>(it, true);
    }

    /**
     * Creates the bookmark group.
     * 
     * @param model the model
     * @return the org.semanticwb.portal.resources.sem. bookmark group
     */
    public static org.semanticwb.portal.resources.sem.BookmarkGroup createBookmarkGroup(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.resources.sem.BookmarkGroup.createBookmarkGroup(String.valueOf(id), model);
    }

    /**
     * Gets the bookmark group.
     * 
     * @param id the id
     * @param model the model
     * @return the bookmark group
     */
    public static org.semanticwb.portal.resources.sem.BookmarkGroup getBookmarkGroup(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.resources.sem.BookmarkGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    /**
     * Creates the bookmark group.
     * 
     * @param id the id
     * @param model the model
     * @return the org.semanticwb.portal.resources.sem. bookmark group
     */
    public static org.semanticwb.portal.resources.sem.BookmarkGroup createBookmarkGroup(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.resources.sem.BookmarkGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    /**
     * Removes the bookmark group.
     * 
     * @param id the id
     * @param model the model
     */
    public static void removeBookmarkGroup(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    /**
     * Checks for bookmark group.
     * 
     * @param id the id
     * @param model the model
     * @return true, if successful
     */
    public static boolean hasBookmarkGroup(String id, org.semanticwb.model.SWBModel model)
    {
        return (getBookmarkGroup(id, model)!=null);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreated()
     */
    /**
     * Gets the created.
     * 
     * @return the created
     */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreated(java.util.Date)
     */
    /**
     * Sets the created.
     * 
     * @param created the new created
     */
    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(swb_created, created);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setModifiedBy(org.semanticwb.model.User)
     */
    /**
     * Sets the modified by.
     * 
     * @param user the new modified by
     */
    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, user.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeModifiedBy()
     */
    /**
     * Removes the modified by.
     */
    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getModifiedBy()
     */
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
     * Gets the entry count.
     * 
     * @return the entry count
     */
    public int getEntryCount()
    {
        return getSemanticObject().getIntProperty(swb_res_bkm_entryCount);
    }

    /**
     * Sets the entry count.
     * 
     * @param entryCount the new entry count
     */
    public void setEntryCount(int entryCount)
    {
        getSemanticObject().setIntProperty(swb_res_bkm_entryCount, entryCount);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle()
     */
    /**
     * Gets the title.
     * 
     * @return the title
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String)
     */
    /**
     * Sets the title.
     * 
     * @param title the new title
     */
    public void setTitle(String title)
    {
        getSemanticObject().setProperty(swb_title, title);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle(java.lang.String)
     */
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayTitle(java.lang.String)
     */
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String, java.lang.String)
     */
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getUpdated()
     */
    /**
     * Gets the updated.
     * 
     * @return the updated
     */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setUpdated(java.util.Date)
     */
    /**
     * Sets the updated.
     * 
     * @param updated the new updated
     */
    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    /**
     * List entrys.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.BookmarkEntry> listEntrys()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.BookmarkEntry>(getSemanticObject().listObjectProperties(swb_res_bkm_hasEntry));
    }

    /**
     * Checks for entry.
     * 
     * @param bookmarkentry the bookmarkentry
     * @return true, if successful
     */
    public boolean hasEntry(org.semanticwb.portal.resources.sem.BookmarkEntry bookmarkentry)
    {
        if(bookmarkentry==null)return false;
        return getSemanticObject().hasObjectProperty(swb_res_bkm_hasEntry,bookmarkentry.getSemanticObject());
    }

    /**
     * Adds the entry.
     * 
     * @param bookmarkentry the bookmarkentry
     */
    public void addEntry(org.semanticwb.portal.resources.sem.BookmarkEntry bookmarkentry)
    {
        getSemanticObject().addObjectProperty(swb_res_bkm_hasEntry, bookmarkentry.getSemanticObject());
    }

    /**
     * Removes the all entry.
     */
    public void removeAllEntry()
    {
        getSemanticObject().removeProperty(swb_res_bkm_hasEntry);
    }

    /**
     * Removes the entry.
     * 
     * @param bookmarkentry the bookmarkentry
     */
    public void removeEntry(org.semanticwb.portal.resources.sem.BookmarkEntry bookmarkentry)
    {
        getSemanticObject().removeObjectProperty(swb_res_bkm_hasEntry,bookmarkentry.getSemanticObject());
    }

    /**
     * Gets the entry.
     * 
     * @return the entry
     */
    public org.semanticwb.portal.resources.sem.BookmarkEntry getEntry()
    {
         org.semanticwb.portal.resources.sem.BookmarkEntry ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_res_bkm_hasEntry);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.BookmarkEntry)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreator(org.semanticwb.model.User)
     */
    /**
     * Sets the creator.
     * 
     * @param user the new creator
     */
    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_creator, user.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeCreator()
     */
    /**
     * Removes the creator.
     */
    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreator()
     */
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription()
     */
    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String)
     */
    /**
     * Sets the description.
     * 
     * @param description the new description
     */
    public void setDescription(String description)
    {
        getSemanticObject().setProperty(swb_description, description);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription(java.lang.String)
     */
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayDescription(java.lang.String)
     */
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String, java.lang.String)
     */
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
}
