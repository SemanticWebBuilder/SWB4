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
package org.semanticwb.model.base;


// TODO: Auto-generated Javadoc
/**
 * The Class PFlowInstanceBase.
 */
public abstract class PFlowInstanceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.PFlowable
{
    
    /** The Constant swb_pfiTime. */
    public static final org.semanticwb.platform.SemanticProperty swb_pfiTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiTime");
    
    /** The Constant swb_Resource. */
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    
    /** The Constant swb_pfinstResource. */
    public static final org.semanticwb.platform.SemanticProperty swb_pfinstResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfinstResource");
    
    /** The Constant swb_PFlow. */
    public static final org.semanticwb.platform.SemanticClass swb_PFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlow");
    
    /** The Constant swb_pfiPFlow. */
    public static final org.semanticwb.platform.SemanticProperty swb_pfiPFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiPFlow");
    
    /** The Constant swb_pfiStatus. */
    public static final org.semanticwb.platform.SemanticProperty swb_pfiStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiStatus");
    
    /** The Constant swb_pfiVersion. */
    public static final org.semanticwb.platform.SemanticProperty swb_pfiVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiVersion");
    
    /** The Constant swb_pfiStep. */
    public static final org.semanticwb.platform.SemanticProperty swb_pfiStep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiStep");
    
    /** The Constant swb_PFlowInstance. */
    public static final org.semanticwb.platform.SemanticClass swb_PFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List p flow instances.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance>(it, true);
        }

        /**
         * List p flow instances.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance>(it, true);
        }

        /**
         * Creates the p flow instance.
         * 
         * @param model the model
         * @return the org.semanticwb.model. p flow instance
         */
        public static org.semanticwb.model.PFlowInstance createPFlowInstance(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.PFlowInstance.ClassMgr.createPFlowInstance(String.valueOf(id), model);
        }

        /**
         * Gets the p flow instance.
         * 
         * @param id the id
         * @param model the model
         * @return the p flow instance
         */
        public static org.semanticwb.model.PFlowInstance getPFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PFlowInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the p flow instance.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. p flow instance
         */
        public static org.semanticwb.model.PFlowInstance createPFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PFlowInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the p flow instance.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removePFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for p flow instance.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasPFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPFlowInstance(id, model)!=null);
        }

        /**
         * List p flow instance by pfinst resource.
         * 
         * @param pfinstresource the pfinstresource
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstanceByPfinstResource(org.semanticwb.model.Resource pfinstresource,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_pfinstResource, pfinstresource.getSemanticObject()));
            return it;
        }

        /**
         * List p flow instance by pfinst resource.
         * 
         * @param pfinstresource the pfinstresource
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstanceByPfinstResource(org.semanticwb.model.Resource pfinstresource)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance> it=new org.semanticwb.model.GenericIterator(pfinstresource.getSemanticObject().getModel().listSubjects(swb_pfinstResource,pfinstresource.getSemanticObject()));
            return it;
        }

        /**
         * List p flow instance by pflow.
         * 
         * @param pfipflow the pfipflow
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstanceByPflow(org.semanticwb.model.PFlow pfipflow,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_pfiPFlow, pfipflow.getSemanticObject()));
            return it;
        }

        /**
         * List p flow instance by pflow.
         * 
         * @param pfipflow the pfipflow
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstanceByPflow(org.semanticwb.model.PFlow pfipflow)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance> it=new org.semanticwb.model.GenericIterator(pfipflow.getSemanticObject().getModel().listSubjects(swb_pfiPFlow,pfipflow.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new p flow instance base.
     * 
     * @param base the base
     */
    public PFlowInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the time.
     * 
     * @return the time
     */
    public java.util.Date getTime()
    {
        return getSemanticObject().getDateProperty(swb_pfiTime);
    }

    /**
     * Sets the time.
     * 
     * @param value the new time
     */
    public void setTime(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_pfiTime, value);
    }

    /**
     * Sets the pfinst resource.
     * 
     * @param value the new pfinst resource
     */
    public void setPfinstResource(org.semanticwb.model.Resource value)
    {
        getSemanticObject().setObjectProperty(swb_pfinstResource, value.getSemanticObject());
    }

    /**
     * Removes the pfinst resource.
     */
    public void removePfinstResource()
    {
        getSemanticObject().removeProperty(swb_pfinstResource);
    }

    /**
     * Gets the pfinst resource.
     * 
     * @return the pfinst resource
     */
    public org.semanticwb.model.Resource getPfinstResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_pfinstResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the pflow.
     * 
     * @param value the new pflow
     */
    public void setPflow(org.semanticwb.model.PFlow value)
    {
        getSemanticObject().setObjectProperty(swb_pfiPFlow, value.getSemanticObject());
    }

    /**
     * Removes the pflow.
     */
    public void removePflow()
    {
        getSemanticObject().removeProperty(swb_pfiPFlow);
    }

    /**
     * Gets the pflow.
     * 
     * @return the pflow
     */
    public org.semanticwb.model.PFlow getPflow()
    {
         org.semanticwb.model.PFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_pfiPFlow);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlow)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the status.
     * 
     * @return the status
     */
    public int getStatus()
    {
        return getSemanticObject().getIntProperty(swb_pfiStatus);
    }

    /**
     * Sets the status.
     * 
     * @param value the new status
     */
    public void setStatus(int value)
    {
        getSemanticObject().setIntProperty(swb_pfiStatus, value);
    }

    /**
     * Gets the version.
     * 
     * @return the version
     */
    public int getVersion()
    {
        return getSemanticObject().getIntProperty(swb_pfiVersion);
    }

    /**
     * Sets the version.
     * 
     * @param value the new version
     */
    public void setVersion(int value)
    {
        getSemanticObject().setIntProperty(swb_pfiVersion, value);
    }

    /**
     * Gets the step.
     * 
     * @return the step
     */
    public String getStep()
    {
        return getSemanticObject().getProperty(swb_pfiStep);
    }

    /**
     * Sets the step.
     * 
     * @param value the new step
     */
    public void setStep(String value)
    {
        getSemanticObject().setProperty(swb_pfiStep, value);
    }

    /**
     * Gets the web site.
     * 
     * @return the web site
     */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
