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

    /**
     * Gets the total number of BookmarkEntries in the group.
     * @return Number of entries in current group.
     */
    public int getEntryCount() {
        int counter = 0;
        Iterator<BookmarkEntry> entries = listEntrys();
        while (entries.hasNext()) {
            entries.next();
            counter++;
        }
        return counter;
    }
}
