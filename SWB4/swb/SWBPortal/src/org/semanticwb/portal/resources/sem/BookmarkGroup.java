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
package org.semanticwb.portal.resources.sem;

import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * Group of bookmarks. Composed by a set of bookmark entries.
 * <p>
 * Grupo de favoritos. Compuesto por un conjunto de entradas de favoritos.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class BookmarkGroup extends org.semanticwb.portal.resources.sem.base.BookmarkGroupBase 
{
    
    /**
     * Instantiates a new bookmark group.
     * 
     * @param base the base
     */
    public BookmarkGroup(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.sem.base.BookmarkGroupBase#addEntry(org.semanticwb.portal.resources.sem.BookmarkEntry)
     */
    @Override
    public void addEntry(org.semanticwb.portal.resources.sem.BookmarkEntry bookmarkentry)
    {
        getSemanticObject().addObjectProperty(swb_res_bkm_hasEntry, bookmarkentry.getSemanticObject());
        setEntryCount(getEntryCount() + 1);
        //System.out.println(getTitle() + " is now in " + getEntryCount());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.sem.base.BookmarkGroupBase#removeAllEntry()
     */
    @Override
    public void removeAllEntry()
    {
        getSemanticObject().removeProperty(swb_res_bkm_hasEntry);
        setEntryCount(0);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.sem.base.BookmarkGroupBase#removeEntry(org.semanticwb.portal.resources.sem.BookmarkEntry)
     */
    @Override
    public void removeEntry(org.semanticwb.portal.resources.sem.BookmarkEntry bookmarkentry)
    {
        getSemanticObject().removeObjectProperty(swb_res_bkm_hasEntry,bookmarkentry.getSemanticObject());
        setEntryCount(getEntryCount() - 1);
        //System.out.println(getTitle() + " reduced to " + getEntryCount());
    }

    /**
     * Gets a BookmarkEntry object with the given id.
     * <p>
     * Obtiene un BookmarkEntry con el id especificado.
     *
     * @param id    ID of the BookmarkEntry to search for. ID de la entrada
     *              requerida.
     *
     * @return      BookmarkEntry with ID equals to id or null if BookmarkEntry
     *              does not exist. BookmarkEntry con ID igual a id o nulo si no
     *              existe.
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
