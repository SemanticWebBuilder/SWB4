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
package org.semanticwb.process.resources.kpi.base;


public abstract class ProcessKPIBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty kpi_responseEndDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessKPI#responseEndDate");
    public static final org.semanticwb.platform.SemanticProperty kpi_responseShowLog=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessKPI#responseShowLog");
    public static final org.semanticwb.platform.SemanticProperty kpi_instancesShowLog=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessKPI#instancesShowLog");
    public static final org.semanticwb.platform.SemanticProperty kpi_responseStartDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessKPI#responseStartDate");
    public static final org.semanticwb.platform.SemanticProperty kpi_responseTimeUnit=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessKPI#responseTimeUnit");
    public static final org.semanticwb.platform.SemanticProperty kpi_instancesGraphType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessKPI#instancesGraphType");
    public static final org.semanticwb.platform.SemanticProperty kpi_performanceShowTaskData=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessKPI#performanceShowTaskData");
    public static final org.semanticwb.platform.SemanticProperty kpi_responseGraphTheme=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessKPI#responseGraphTheme");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty kpi_responseGraphType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessKPI#responseGraphType");
    public static final org.semanticwb.platform.SemanticProperty kpi_instancesPeriodicity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessKPI#instancesPeriodicity");
    public static final org.semanticwb.platform.SemanticProperty kpi_responsePeriodicity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessKPI#responsePeriodicity");
    public static final org.semanticwb.platform.SemanticClass kpi_ProcessKPI=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/ProcessKPI#ProcessKPI");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/ProcessKPI#ProcessKPI");

    public ProcessKPIBase()
    {
    }

   /**
   * Constructs a ProcessKPIBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessKPI
   */
    public ProcessKPIBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() 
    {
        return getSemanticObject().hashCode();
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) 
    {
        if(obj==null)return false;
        return hashCode()==obj.hashCode();
    }

/**
* Gets the ResponseEndDate property
* @return java.util.Date with the ResponseEndDate
*/
    public java.util.Date getResponseEndDate()
    {
        return getSemanticObject().getDateProperty(kpi_responseEndDate);
    }

/**
* Sets the ResponseEndDate property
* @param value long with the ResponseEndDate
*/
    public void setResponseEndDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(kpi_responseEndDate, value);
    }

/**
* Gets the ResponseShowLog property
* @return boolean with the ResponseShowLog
*/
    public boolean isResponseShowLog()
    {
        return getSemanticObject().getBooleanProperty(kpi_responseShowLog);
    }

/**
* Sets the ResponseShowLog property
* @param value long with the ResponseShowLog
*/
    public void setResponseShowLog(boolean value)
    {
        getSemanticObject().setBooleanProperty(kpi_responseShowLog, value);
    }

/**
* Gets the InstancesShowLog property
* @return boolean with the InstancesShowLog
*/
    public boolean isInstancesShowLog()
    {
        return getSemanticObject().getBooleanProperty(kpi_instancesShowLog);
    }

/**
* Sets the InstancesShowLog property
* @param value long with the InstancesShowLog
*/
    public void setInstancesShowLog(boolean value)
    {
        getSemanticObject().setBooleanProperty(kpi_instancesShowLog, value);
    }

/**
* Gets the ResponseStartDate property
* @return java.util.Date with the ResponseStartDate
*/
    public java.util.Date getResponseStartDate()
    {
        return getSemanticObject().getDateProperty(kpi_responseStartDate);
    }

/**
* Sets the ResponseStartDate property
* @param value long with the ResponseStartDate
*/
    public void setResponseStartDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(kpi_responseStartDate, value);
    }

/**
* Gets the ResponseTimeUnit property
* @return int with the ResponseTimeUnit
*/
    public int getResponseTimeUnit()
    {
        return getSemanticObject().getIntProperty(kpi_responseTimeUnit);
    }

/**
* Sets the ResponseTimeUnit property
* @param value long with the ResponseTimeUnit
*/
    public void setResponseTimeUnit(int value)
    {
        getSemanticObject().setIntProperty(kpi_responseTimeUnit, value);
    }

/**
* Gets the InstancesGraphType property
* @return int with the InstancesGraphType
*/
    public int getInstancesGraphType()
    {
        return getSemanticObject().getIntProperty(kpi_instancesGraphType);
    }

/**
* Sets the InstancesGraphType property
* @param value long with the InstancesGraphType
*/
    public void setInstancesGraphType(int value)
    {
        getSemanticObject().setIntProperty(kpi_instancesGraphType, value);
    }

/**
* Gets the PerformanceShowTaskData property
* @return boolean with the PerformanceShowTaskData
*/
    public boolean isPerformanceShowTaskData()
    {
        return getSemanticObject().getBooleanProperty(kpi_performanceShowTaskData);
    }

/**
* Sets the PerformanceShowTaskData property
* @param value long with the PerformanceShowTaskData
*/
    public void setPerformanceShowTaskData(boolean value)
    {
        getSemanticObject().setBooleanProperty(kpi_performanceShowTaskData, value);
    }

/**
* Gets the ResponseGraphTheme property
* @return int with the ResponseGraphTheme
*/
    public int getResponseGraphTheme()
    {
        return getSemanticObject().getIntProperty(kpi_responseGraphTheme);
    }

/**
* Sets the ResponseGraphTheme property
* @param value long with the ResponseGraphTheme
*/
    public void setResponseGraphTheme(int value)
    {
        getSemanticObject().setIntProperty(kpi_responseGraphTheme, value);
    }
   /**
   * Sets the value for the property Resource
   * @param value Resource to set
   */

    public void setResource(org.semanticwb.model.Resource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_semanticResourceInv, value.getSemanticObject());
        }else
        {
            removeResource();
        }
    }
   /**
   * Remove the value for Resource property
   */

    public void removeResource()
    {
        getSemanticObject().removeProperty(swb_semanticResourceInv);
    }

   /**
   * Gets the Resource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_semanticResourceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ResponseGraphType property
* @return int with the ResponseGraphType
*/
    public int getResponseGraphType()
    {
        return getSemanticObject().getIntProperty(kpi_responseGraphType);
    }

/**
* Sets the ResponseGraphType property
* @param value long with the ResponseGraphType
*/
    public void setResponseGraphType(int value)
    {
        getSemanticObject().setIntProperty(kpi_responseGraphType, value);
    }

/**
* Gets the InstancesPeriodicity property
* @return int with the InstancesPeriodicity
*/
    public int getInstancesPeriodicity()
    {
        return getSemanticObject().getIntProperty(kpi_instancesPeriodicity);
    }

/**
* Sets the InstancesPeriodicity property
* @param value long with the InstancesPeriodicity
*/
    public void setInstancesPeriodicity(int value)
    {
        getSemanticObject().setIntProperty(kpi_instancesPeriodicity, value);
    }

/**
* Gets the ResponsePeriodicity property
* @return int with the ResponsePeriodicity
*/
    public int getResponsePeriodicity()
    {
        return getSemanticObject().getIntProperty(kpi_responsePeriodicity);
    }

/**
* Sets the ResponsePeriodicity property
* @param value long with the ResponsePeriodicity
*/
    public void setResponsePeriodicity(int value)
    {
        getSemanticObject().setIntProperty(kpi_responsePeriodicity, value);
    }
}
