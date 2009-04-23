package org.semanticwb.portal.resources.sem;

import java.util.Iterator;

/**
 *
 * @author Hasdai Pacheco {haxdai(at)gmail.com}
 */
public class BookmarkGroup extends org.semanticwb.portal.resources.sem.base.BookmarkGroupBase 
{
    public BookmarkGroup(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void addEntry(org.semanticwb.portal.resources.sem.BookmarkEntry bookmarkentry)
    {
        getSemanticObject().addObjectProperty(swb_res_bkm_hasEntry, bookmarkentry.getSemanticObject());
        setEntryCount(getEntryCount() + 1);
        System.out.println(getTitle() + " is now in " + getEntryCount());
    }

    @Override
    public void removeAllEntry()
    {
        getSemanticObject().removeProperty(swb_res_bkm_hasEntry);
        setEntryCount(0);
    }

    @Override
    public void removeEntry(org.semanticwb.portal.resources.sem.BookmarkEntry bookmarkentry)
    {
        getSemanticObject().removeObjectProperty(swb_res_bkm_hasEntry,bookmarkentry.getSemanticObject());
        setEntryCount(getEntryCount() - 1);
        System.out.println(getTitle() + " reduced to " + getEntryCount());
    }

    /**
     * Gets a BookmarkEntry object with the given id.
     * @param id ID of the BookmarkEntry to search for.
     * @return BookmarkEntry with ID equals to id or null if BookmarkEntry does not
     * exist.
     */
    public BookmarkEntry getEntryById(String id) {
        Iterator<BookmarkEntry> entries = listEntrys();

        while (entries.hasNext()) {
            BookmarkEntry en = entries.next();

            if(en.getSemanticObject().getId().equals(id)) {
                return en;
            }
        }
        return null;
    }
}
