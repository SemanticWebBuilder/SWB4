package org.semanticwb.portal.resources.sem.base;

import org.semanticwb.portal.resources.sem.BookmarkGroup;


public class SWBBookmarkBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty swb_res_bkm_sortType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBBookmark#sortType");
    public static final org.semanticwb.platform.SemanticClass swb_res_bkm_BookmarkGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBBookmark#BookmarkGroup");
    public static final org.semanticwb.platform.SemanticProperty swb_res_bkm_hasBookmarkGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBBookmark#hasBookmarkGroup");
    public static final org.semanticwb.platform.SemanticClass swb_res_bkm_SWBBookmark=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBBookmark#SWBBookmark");

    public SWBBookmarkBase()
    {
    }

    public SWBBookmarkBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBBookmark#SWBBookmark");

    public int getSortType()
    {
        return getSemanticObject().getIntProperty(swb_res_bkm_sortType);
    }

    public void setSortType(int sortType)
    {
        getSemanticObject().setLongProperty(swb_res_bkm_sortType, sortType);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.BookmarkGroup> listBookmarkGroups()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.BookmarkGroup>(getSemanticObject().listObjectProperties(swb_res_bkm_hasBookmarkGroup));
    }

    public boolean hasBookmarkGroup(org.semanticwb.portal.resources.sem.BookmarkGroup bookmarkgroup)
    {
        if(bookmarkgroup==null)return false;        return getSemanticObject().hasObjectProperty(swb_res_bkm_hasBookmarkGroup,bookmarkgroup.getSemanticObject());
    }

    public void addBookmarkGroup(org.semanticwb.portal.resources.sem.BookmarkGroup bookmarkgroup)
    {
        getSemanticObject().addObjectProperty(swb_res_bkm_hasBookmarkGroup, bookmarkgroup.getSemanticObject());
    }

    public void removeAllBookmarkGroup()
    {
        getSemanticObject().removeProperty(swb_res_bkm_hasBookmarkGroup);
    }

    public void removeBookmarkGroup(org.semanticwb.portal.resources.sem.BookmarkGroup bookmarkgroup)
    {
        getSemanticObject().removeObjectProperty(swb_res_bkm_hasBookmarkGroup,bookmarkgroup.getSemanticObject());
    }

    public org.semanticwb.portal.resources.sem.BookmarkGroup getBookmarkGroup()
    {
         BookmarkGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_res_bkm_hasBookmarkGroup);
         if(obj!=null)
         {
             ret=(BookmarkGroup)obj.createGenericInstance();
         }
         return ret;
    }
}
