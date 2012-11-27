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
 * The Interface CommonPropertiesforDefinitionBase.
 */
public interface CommonPropertiesforDefinitionBase extends org.semanticwb.model.GenericObject
{
    
    /** The Constant jcr_autoCreated. */
    public static final org.semanticwb.platform.SemanticProperty jcr_autoCreated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#autoCreated");
    
    /** The Constant jcr_mandatory. */
    public static final org.semanticwb.platform.SemanticProperty jcr_mandatory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mandatory");
    
    /** The Constant jcr_onParentVersion. */
    public static final org.semanticwb.platform.SemanticProperty jcr_onParentVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#onParentVersion");
    
    /** The Constant jcr_protected. */
    public static final org.semanticwb.platform.SemanticProperty jcr_protected=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#protected");
    
    /** The Constant swbrep_CommonPropertiesforDefinition. */
    public static final org.semanticwb.platform.SemanticClass swbrep_CommonPropertiesforDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#CommonPropertiesforDefinition");

    /**
     * Checks if is auto created.
     * 
     * @return true, if is auto created
     */
    public boolean isAutoCreated();

    /**
     * Sets the auto created.
     * 
     * @param value the new auto created
     */
    public void setAutoCreated(boolean value);

    /**
     * Checks if is mandatory.
     * 
     * @return true, if is mandatory
     */
    public boolean isMandatory();

    /**
     * Sets the mandatory.
     * 
     * @param value the new mandatory
     */
    public void setMandatory(boolean value);

    /**
     * Gets the on parent version.
     * 
     * @return the on parent version
     */
    public String getOnParentVersion();

    /**
     * Sets the on parent version.
     * 
     * @param value the new on parent version
     */
    public void setOnParentVersion(String value);

    /**
     * Checks if is protected.
     * 
     * @return true, if is protected
     */
    public boolean isProtected();

    /**
     * Sets the protected.
     * 
     * @param value the new protected
     */
    public void setProtected(boolean value);
}
