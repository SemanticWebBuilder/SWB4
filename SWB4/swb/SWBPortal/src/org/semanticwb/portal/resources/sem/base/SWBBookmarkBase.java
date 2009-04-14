package org.semanticwb.portal.resources.sem.base;


public class SWBBookmarkBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass swb_res_bkm_BookmarkEntry=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBBookmark.owl#BookmarkEntry");
    public static final org.semanticwb.platform.SemanticProperty swb_res_bkm_hasEntry=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBBookmark.owl#hasEntry");
    public static final org.semanticwb.platform.SemanticClass swb_res_bkm_SWBBookmark=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBBookmark.owl#SWBBookmark");

    public SWBBookmarkBase()
    {
    }

    public SWBBookmarkBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBBookmark.owl#SWBBookmark");

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
}
