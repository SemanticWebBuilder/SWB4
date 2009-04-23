package org.semanticwb.portal.resources.sem.base;


public class BookmarkGroupBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swb_res_bkm_entryCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#entryCount");
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    public static final org.semanticwb.platform.SemanticClass swb_res_bkm_BookmarkEntry=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#BookmarkEntry");
    public static final org.semanticwb.platform.SemanticProperty swb_res_bkm_hasEntry=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#hasEntry");
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    public static final org.semanticwb.platform.SemanticClass swb_res_bkm_BookmarkGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#BookmarkGroup");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#BookmarkGroup");

    public BookmarkGroupBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.resources.sem.BookmarkGroup> listBookmarkGroups(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.BookmarkGroup>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.resources.sem.BookmarkGroup> listBookmarkGroups()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.BookmarkGroup>(it, true);
    }

    public static org.semanticwb.portal.resources.sem.BookmarkGroup createBookmarkGroup(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.resources.sem.BookmarkGroup.createBookmarkGroup(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.resources.sem.BookmarkGroup getBookmarkGroup(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.resources.sem.BookmarkGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.resources.sem.BookmarkGroup createBookmarkGroup(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.resources.sem.BookmarkGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeBookmarkGroup(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasBookmarkGroup(String id, org.semanticwb.model.SWBModel model)
    {
        return (getBookmarkGroup(id, model)!=null);
    }

    public int getEntryCount()
    {
        return getSemanticObject().getIntProperty(swb_res_bkm_entryCount);
    }

    public void setEntryCount(int entryCount)
    {
        getSemanticObject().setLongProperty(swb_res_bkm_entryCount, entryCount);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(swb_title, title);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.BookmarkEntry> listEntrys()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.BookmarkEntry>(getSemanticObject().listObjectProperties(swb_res_bkm_hasEntry));
    }

    public boolean hasEntry(org.semanticwb.portal.resources.sem.BookmarkEntry bookmarkentry)
    {
        if(bookmarkentry==null)return false;        return getSemanticObject().hasObjectProperty(swb_res_bkm_hasEntry,bookmarkentry.getSemanticObject());
    }

    public void addEntry(org.semanticwb.portal.resources.sem.BookmarkEntry bookmarkentry)
    {
        getSemanticObject().addObjectProperty(swb_res_bkm_hasEntry, bookmarkentry.getSemanticObject());
    }

    public void removeAllEntry()
    {
        getSemanticObject().removeProperty(swb_res_bkm_hasEntry);
    }

    public void removeEntry(org.semanticwb.portal.resources.sem.BookmarkEntry bookmarkentry)
    {
        getSemanticObject().removeObjectProperty(swb_res_bkm_hasEntry,bookmarkentry.getSemanticObject());
    }

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

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(swb_description, description);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
}
