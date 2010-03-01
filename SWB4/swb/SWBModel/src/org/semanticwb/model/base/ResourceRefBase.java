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
 * The Class ResourceRefBase.
 */
public abstract class ResourceRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Priorityable,org.semanticwb.model.Activeable
{
    
    /** The Constant swb_Resource. */
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    
    /** The Constant swb_resource. */
    public static final org.semanticwb.platform.SemanticProperty swb_resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resource");
    
    /** The Constant swb_ResourceRef. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List resource refs.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef>(it, true);
        }

        /**
         * List resource refs.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef>(it, true);
        }

        /**
         * Creates the resource ref.
         * 
         * @param model the model
         * @return the org.semanticwb.model. resource ref
         */
        public static org.semanticwb.model.ResourceRef createResourceRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.ResourceRef.ClassMgr.createResourceRef(String.valueOf(id), model);
        }

        /**
         * Gets the resource ref.
         * 
         * @param id the id
         * @param model the model
         * @return the resource ref
         */
        public static org.semanticwb.model.ResourceRef getResourceRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ResourceRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the resource ref.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. resource ref
         */
        public static org.semanticwb.model.ResourceRef createResourceRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ResourceRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the resource ref.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeResourceRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for resource ref.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasResourceRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getResourceRef(id, model)!=null);
        }

        /**
         * List resource ref by resource.
         * 
         * @param resource the resource
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefByResource(org.semanticwb.model.Resource resource,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_resource, resource.getSemanticObject()));
            return it;
        }

        /**
         * List resource ref by resource.
         * 
         * @param resource the resource
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefByResource(org.semanticwb.model.Resource resource)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef> it=new org.semanticwb.model.GenericIterator(resource.getSemanticObject().getModel().listSubjects(swb_resource,resource.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new resource ref base.
     * 
     * @param base the base
     */
    public ResourceRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.PriorityableBase#getPriority()
     */
    public int getPriority()
    {
        return getSemanticObject().getIntProperty(swb_priority);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.PriorityableBase#setPriority(int)
     */
    public void setPriority(int value)
    {
        getSemanticObject().setIntProperty(swb_priority, value);
    }

    /**
     * Sets the resource.
     * 
     * @param value the new resource
     */
    public void setResource(org.semanticwb.model.Resource value)
    {
        getSemanticObject().setObjectProperty(swb_resource, value.getSemanticObject());
    }

    /**
     * Removes the resource.
     */
    public void removeResource()
    {
        getSemanticObject().removeProperty(swb_resource);
    }

    /**
     * Gets the resource.
     * 
     * @return the resource
     */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_resource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
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
