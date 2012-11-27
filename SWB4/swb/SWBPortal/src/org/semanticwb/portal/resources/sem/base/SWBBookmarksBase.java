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
 * The Class SWBBookmarksBase.
 */
public class SWBBookmarksBase extends org.semanticwb.portal.api.GenericSemResource 
{
    
    /** The Constant swb_res_bkm_displayMode. */
    public static final org.semanticwb.platform.SemanticProperty swb_res_bkm_displayMode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#displayMode");
    
    /** The Constant swb_res_bkm_admUrl. */
    public static final org.semanticwb.platform.SemanticProperty swb_res_bkm_admUrl=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#admUrl");
    
    /** The Constant swb_res_bkm_BookmarkGroup. */
    public static final org.semanticwb.platform.SemanticClass swb_res_bkm_BookmarkGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#BookmarkGroup");
    
    /** The Constant swb_res_bkm_hasGroup. */
    public static final org.semanticwb.platform.SemanticProperty swb_res_bkm_hasGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#hasGroup");
    
    /** The Constant swb_res_bkm_sortType. */
    public static final org.semanticwb.platform.SemanticProperty swb_res_bkm_sortType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#sortType");
    
    /** The Constant swb_res_bkm_SWBBookmarks. */
    public static final org.semanticwb.platform.SemanticClass swb_res_bkm_SWBBookmarks=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#SWBBookmarks");

    /**
     * Instantiates a new sWB bookmarks base.
     */
    public SWBBookmarksBase()
    {
    }

    /**
     * Instantiates a new sWB bookmarks base.
     * 
     * @param base the base
     */
    public SWBBookmarksBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sem/SWBBookmarks#SWBBookmarks");

    /**
     * Gets the display mode.
     * 
     * @return the display mode
     */
    public int getDisplayMode()
    {
        return getSemanticObject().getIntProperty(swb_res_bkm_displayMode);
    }

    /**
     * Sets the display mode.
     * 
     * @param displayMode the new display mode
     */
    public void setDisplayMode(int displayMode)
    {
        getSemanticObject().setIntProperty(swb_res_bkm_displayMode, displayMode);
    }

    /**
     * Gets the adm url.
     * 
     * @return the adm url
     */
    public String getAdmUrl()
    {
        return getSemanticObject().getProperty(swb_res_bkm_admUrl);
    }

    /**
     * Sets the adm url.
     * 
     * @param admUrl the new adm url
     */
    public void setAdmUrl(String admUrl)
    {
        getSemanticObject().setProperty(swb_res_bkm_admUrl, admUrl);
    }

    /**
     * List groups.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.BookmarkGroup> listGroups()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.BookmarkGroup>(getSemanticObject().listObjectProperties(swb_res_bkm_hasGroup));
    }

    /**
     * Checks for group.
     * 
     * @param bookmarkgroup the bookmarkgroup
     * @return true, if successful
     */
    public boolean hasGroup(org.semanticwb.portal.resources.sem.BookmarkGroup bookmarkgroup)
    {
        if(bookmarkgroup==null)return false;
        return getSemanticObject().hasObjectProperty(swb_res_bkm_hasGroup,bookmarkgroup.getSemanticObject());
    }

    /**
     * Adds the group.
     * 
     * @param bookmarkgroup the bookmarkgroup
     */
    public void addGroup(org.semanticwb.portal.resources.sem.BookmarkGroup bookmarkgroup)
    {
        getSemanticObject().addObjectProperty(swb_res_bkm_hasGroup, bookmarkgroup.getSemanticObject());
    }

    /**
     * Removes the all group.
     */
    public void removeAllGroup()
    {
        getSemanticObject().removeProperty(swb_res_bkm_hasGroup);
    }

    /**
     * Removes the group.
     * 
     * @param bookmarkgroup the bookmarkgroup
     */
    public void removeGroup(org.semanticwb.portal.resources.sem.BookmarkGroup bookmarkgroup)
    {
        getSemanticObject().removeObjectProperty(swb_res_bkm_hasGroup,bookmarkgroup.getSemanticObject());
    }

    /**
     * Gets the group.
     * 
     * @return the group
     */
    public org.semanticwb.portal.resources.sem.BookmarkGroup getGroup()
    {
         org.semanticwb.portal.resources.sem.BookmarkGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_res_bkm_hasGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.BookmarkGroup)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the sort type.
     * 
     * @return the sort type
     */
    public int getSortType()
    {
        return getSemanticObject().getIntProperty(swb_res_bkm_sortType);
    }

    /**
     * Sets the sort type.
     * 
     * @param sortType the new sort type
     */
    public void setSortType(int sortType)
    {
        getSemanticObject().setIntProperty(swb_res_bkm_sortType, sortType);
    }
}
