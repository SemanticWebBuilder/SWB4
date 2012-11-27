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
package org.semanticwb.repository.base;

// TODO: Auto-generated Javadoc
/**
 * The Interface VersionableBase.
 */
public interface VersionableBase extends org.semanticwb.repository.Referenceable
{
    
    /** The Constant jcr_isCheckedOut. */
    public static final org.semanticwb.platform.SemanticProperty jcr_isCheckedOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#isCheckedOut");
    
    /** The Constant nt_VersionHistory. */
    public static final org.semanticwb.platform.SemanticClass nt_VersionHistory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionHistory");
    
    /** The Constant jcr_versionHistory. */
    public static final org.semanticwb.platform.SemanticProperty jcr_versionHistory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#versionHistory");
    
    /** The Constant nt_Version. */
    public static final org.semanticwb.platform.SemanticClass nt_Version=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");
    
    /** The Constant jcr_baseVersion. */
    public static final org.semanticwb.platform.SemanticProperty jcr_baseVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#baseVersion");
    
    /** The Constant jcr_mergeFailed. */
    public static final org.semanticwb.platform.SemanticProperty jcr_mergeFailed=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mergeFailed");
    
    /** The Constant mix_Versionable. */
    public static final org.semanticwb.platform.SemanticClass mix_Versionable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/mix/1.0#versionable");

    /**
     * Checks if is checks if is checked out.
     * 
     * @return true, if is checks if is checked out
     */
    public boolean isIsCheckedOut();

    /**
     * Sets the checks if is checked out.
     * 
     * @param value the new checks if is checked out
     */
    public void setIsCheckedOut(boolean value);

    /**
     * Sets the version history.
     * 
     * @param versionhistory the new version history
     */
    public void setVersionHistory(org.semanticwb.repository.VersionHistory versionhistory);

    /**
     * Removes the version history.
     */
    public void removeVersionHistory();

    /**
     * Gets the version history.
     * 
     * @return the version history
     */
    public org.semanticwb.repository.VersionHistory getVersionHistory();

    /**
     * Sets the base version.
     * 
     * @param version the new base version
     */
    public void setBaseVersion(org.semanticwb.repository.Version version);

    /**
     * Removes the base version.
     */
    public void removeBaseVersion();

    /**
     * Gets the base version.
     * 
     * @return the base version
     */
    public org.semanticwb.repository.Version getBaseVersion();

    /**
     * Sets the merge failed.
     * 
     * @param version the new merge failed
     */
    public void setMergeFailed(org.semanticwb.repository.Version version);

    /**
     * Removes the merge failed.
     */
    public void removeMergeFailed();

    /**
     * Gets the merge failed.
     * 
     * @return the merge failed
     */
    public org.semanticwb.repository.Version getMergeFailed();
}
