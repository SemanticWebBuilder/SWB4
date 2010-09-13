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
 * The Class PPTResourceBase.
 */
public abstract class PPTResourceBase extends org.semanticwb.resource.office.sem.OfficeResource 
{
    
    /** The Constant swboffice_content. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_content=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#content");
    
    /** The Constant swboffice_showDownload. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_showDownload=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#showDownload");
    
    /** The Constant swboffice_versionToShow. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_versionToShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#versionToShow");
    
    /** The Constant swboffice_repositoryName. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_repositoryName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#repositoryName");
    
    /** The Constant swboffice_PPTResource. */
    public static final org.semanticwb.platform.SemanticClass swboffice_PPTResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#PPTResource");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#PPTResource");

    /**
     * Instantiates a new pPT resource base.
     */
    public PPTResourceBase()
    {
    }

    /**
     * Instantiates a new pPT resource base.
     * 
     * @param base the base
     */
    public PPTResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
     * Checks if is show download.
     * 
     * @return true, if is show download
     */
    public boolean isShowDownload()
    {
        return getSemanticObject().getBooleanProperty(swboffice_showDownload);
    }

    /**
     * Sets the show download.
     * 
     * @param value the new show download
     */
    public void setShowDownload(boolean value)
    {
        getSemanticObject().setBooleanProperty(swboffice_showDownload, value);
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
