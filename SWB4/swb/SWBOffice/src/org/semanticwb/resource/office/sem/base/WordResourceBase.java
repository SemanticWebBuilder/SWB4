/**  
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
 **/
package org.semanticwb.resource.office.sem.base;


// TODO: Auto-generated Javadoc
/**
 * The Class WordResourceBase.
 */
public abstract class WordResourceBase extends org.semanticwb.resource.office.sem.OfficeResource 
{
    
    /** The Constant swboffice_position. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_position=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#position");
    
    /** The Constant swboffice_txtant. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_txtant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#txtant");
    
    /** The Constant swboffice_txtsig. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_txtsig=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#txtsig");
    
    /** The Constant swboffice_tfont. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_tfont=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#tfont");
    
    /** The Constant swboffice_npages. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_npages=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#npages");
    
    /** The Constant swboffice_content. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_content=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#content");
    
    /** The Constant swboffice_tpred. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_tpred=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#tpred");
    
    /** The Constant swboffice_versionToShow. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_versionToShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#versionToShow");
    
    /** The Constant swboffice_pages. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_pages=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#pages");
    
    /** The Constant swboffice_deletestyles. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_deletestyles=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#deletestyles");
    
    /** The Constant swboffice_repositoryName. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_repositoryName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#repositoryName");
    
    /** The Constant swboffice_WordResource. */
    public static final org.semanticwb.platform.SemanticClass swboffice_WordResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#WordResource");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#WordResource");

    /**
     * Instantiates a new word resource base.
     */
    public WordResourceBase()
    {
    }

    /**
     * Instantiates a new word resource base.
     * 
     * @param base the base
     */
    public WordResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the position.
     * 
     * @return the position
     */
    public int getPosition()
    {
        return getSemanticObject().getIntProperty(swboffice_position);
    }

    /**
     * Sets the position.
     * 
     * @param value the new position
     */
    public void setPosition(int value)
    {
        getSemanticObject().setIntProperty(swboffice_position, value);
    }

    /**
     * Gets the txtant.
     * 
     * @return the txtant
     */
    public String getTxtant()
    {
        return getSemanticObject().getProperty(swboffice_txtant);
    }

    /**
     * Sets the txtant.
     * 
     * @param value the new txtant
     */
    public void setTxtant(String value)
    {
        getSemanticObject().setProperty(swboffice_txtant, value);
    }

    /**
     * Gets the txtsig.
     * 
     * @return the txtsig
     */
    public String getTxtsig()
    {
        return getSemanticObject().getProperty(swboffice_txtsig);
    }

    /**
     * Sets the txtsig.
     * 
     * @param value the new txtsig
     */
    public void setTxtsig(String value)
    {
        getSemanticObject().setProperty(swboffice_txtsig, value);
    }

    /**
     * Gets the tfont.
     * 
     * @return the tfont
     */
    public String getTfont()
    {
        return getSemanticObject().getProperty(swboffice_tfont);
    }

    /**
     * Sets the tfont.
     * 
     * @param value the new tfont
     */
    public void setTfont(String value)
    {
        getSemanticObject().setProperty(swboffice_tfont, value);
    }

    /**
     * Gets the npages.
     * 
     * @return the npages
     */
    public int getNpages()
    {
        return getSemanticObject().getIntProperty(swboffice_npages);
    }

    /**
     * Sets the npages.
     * 
     * @param value the new npages
     */
    public void setNpages(int value)
    {
        getSemanticObject().setIntProperty(swboffice_npages, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.resource.office.sem.base.OfficeResourceBase#getContent()
     */
    public String getContent()
    {
        return getSemanticObject().getProperty(swboffice_content);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.resource.office.sem.base.OfficeResourceBase#setContent(java.lang.String)
     */
    public void setContent(String value)
    {
        getSemanticObject().setProperty(swboffice_content, value);
    }

    /**
     * Checks if is tpred.
     * 
     * @return true, if is tpred
     */
    public boolean isTpred()
    {
        return getSemanticObject().getBooleanProperty(swboffice_tpred);
    }

    /**
     * Sets the tpred.
     * 
     * @param value the new tpred
     */
    public void setTpred(boolean value)
    {
        getSemanticObject().setBooleanProperty(swboffice_tpred, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.resource.office.sem.base.OfficeResourceBase#getVersionToShow()
     */
    public String getVersionToShow()
    {
        return getSemanticObject().getProperty(swboffice_versionToShow);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.resource.office.sem.base.OfficeResourceBase#setVersionToShow(java.lang.String)
     */
    public void setVersionToShow(String value)
    {
        getSemanticObject().setProperty(swboffice_versionToShow, value);
    }

    /**
     * Checks if is pages.
     * 
     * @return true, if is pages
     */
    public boolean isPages()
    {
        return getSemanticObject().getBooleanProperty(swboffice_pages);
    }

    /**
     * Sets the pages.
     * 
     * @param value the new pages
     */
    public void setPages(boolean value)
    {
        getSemanticObject().setBooleanProperty(swboffice_pages, value);
    }

    /**
     * Checks if is deletestyles.
     * 
     * @return true, if is deletestyles
     */
    public boolean isDeletestyles()
    {
        return getSemanticObject().getBooleanProperty(swboffice_deletestyles);
    }

    /**
     * Sets the deletestyles.
     * 
     * @param value the new deletestyles
     */
    public void setDeletestyles(boolean value)
    {
        getSemanticObject().setBooleanProperty(swboffice_deletestyles, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.resource.office.sem.base.OfficeResourceBase#getRepositoryName()
     */
    public String getRepositoryName()
    {
        return getSemanticObject().getProperty(swboffice_repositoryName);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.resource.office.sem.base.OfficeResourceBase#setRepositoryName(java.lang.String)
     */
    public void setRepositoryName(String value)
    {
        getSemanticObject().setProperty(swboffice_repositoryName, value);
    }
}
