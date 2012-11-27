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
package org.semanticwb.portal.resources.projectdriver.base;

// TODO: Auto-generated Javadoc
/**
 * The Interface PeriodBase.
 */
public interface PeriodBase extends org.semanticwb.model.GenericObject
{
    
    /** The Constant swbproy_startDate. */
    public static final org.semanticwb.platform.SemanticProperty swbproy_startDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#startDate");
    
    /** The Constant swbproy_currentHour. */
    public static final org.semanticwb.platform.SemanticProperty swbproy_currentHour=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#currentHour");
    
    /** The Constant swbproy_currentPercentage. */
    public static final org.semanticwb.platform.SemanticProperty swbproy_currentPercentage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#currentPercentage");
    
    /** The Constant swbproy_plannedHour. */
    public static final org.semanticwb.platform.SemanticProperty swbproy_plannedHour=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#plannedHour");
    
    /** The Constant swbproy_endDate. */
    public static final org.semanticwb.platform.SemanticProperty swbproy_endDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#endDate");
    
    /** The Constant swbproy_Period. */
    public static final org.semanticwb.platform.SemanticClass swbproy_Period=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#Period");

    /**
     * Gets the start date.
     * 
     * @return the start date
     */
    public java.util.Date getStartDate();

    /**
     * Sets the start date.
     * 
     * @param value the new start date
     */
    public void setStartDate(java.util.Date value);

    /**
     * Gets the current hour.
     * 
     * @return the current hour
     */
    public int getCurrentHour();

    /**
     * Sets the current hour.
     * 
     * @param value the new current hour
     */
    public void setCurrentHour(int value);

    /**
     * Gets the current percentage.
     * 
     * @return the current percentage
     */
    public float getCurrentPercentage();

    /**
     * Sets the current percentage.
     * 
     * @param value the new current percentage
     */
    public void setCurrentPercentage(float value);

    /**
     * Gets the planned hour.
     * 
     * @return the planned hour
     */
    public int getPlannedHour();

    /**
     * Sets the planned hour.
     * 
     * @param value the new planned hour
     */
    public void setPlannedHour(int value);

    /**
     * Gets the end date.
     * 
     * @return the end date
     */
    public java.util.Date getEndDate();

    /**
     * Sets the end date.
     * 
     * @param value the new end date
     */
    public void setEndDate(java.util.Date value);
}
