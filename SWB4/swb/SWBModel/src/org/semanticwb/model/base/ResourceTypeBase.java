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
 * The Class ResourceTypeBase.
 */
public abstract class ResourceTypeBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.FilterableClass,org.semanticwb.model.Traceable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Descriptiveable
{
    
    /** The Constant swb_resourceBundle. */
    public static final org.semanticwb.platform.SemanticProperty swb_resourceBundle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceBundle");
    
    /** The Constant swb_resourceOWL. */
    public static final org.semanticwb.platform.SemanticProperty swb_resourceOWL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceOWL");
    
    /** The Constant swb_ResourceSubType. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceSubType");
    
    /** The Constant swb_hasPTSubType. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasPTSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPTSubType");
    
    /** The Constant swb_resourceMode. */
    public static final org.semanticwb.platform.SemanticProperty swb_resourceMode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceMode");
    
    /** The Constant swb_Resource. */
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    
    /** The Constant swb_hasPTResource. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasPTResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPTResource");
    
    /** The Constant swb_resourceCache. */
    public static final org.semanticwb.platform.SemanticProperty swb_resourceCache=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceCache");
    
    /** The Constant swb_resourceClassName. */
    public static final org.semanticwb.platform.SemanticProperty swb_resourceClassName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceClassName");
    
    /** The Constant swb_ResourceType. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List resource types.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType>(it, true);
        }

        /**
         * List resource types.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType>(it, true);
        }

        /**
         * Gets the resource type.
         * 
         * @param id the id
         * @param model the model
         * @return the resource type
         */
        public static org.semanticwb.model.ResourceType getResourceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ResourceType)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the resource type.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. resource type
         */
        public static org.semanticwb.model.ResourceType createResourceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ResourceType)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the resource type.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeResourceType(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for resource type.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasResourceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (getResourceType(id, model)!=null);
        }

        /**
         * List resource type by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List resource type by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List resource type by sub type.
         * 
         * @param hasptsubtype the hasptsubtype
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeBySubType(org.semanticwb.model.ResourceSubType hasptsubtype,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasPTSubType, hasptsubtype.getSemanticObject()));
            return it;
        }

        /**
         * List resource type by sub type.
         * 
         * @param hasptsubtype the hasptsubtype
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeBySubType(org.semanticwb.model.ResourceSubType hasptsubtype)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(hasptsubtype.getSemanticObject().getModel().listSubjects(swb_hasPTSubType,hasptsubtype.getSemanticObject()));
            return it;
        }

        /**
         * List resource type by resource.
         * 
         * @param hasptresource the hasptresource
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByResource(org.semanticwb.model.Resource hasptresource,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasPTResource, hasptresource.getSemanticObject()));
            return it;
        }

        /**
         * List resource type by resource.
         * 
         * @param hasptresource the hasptresource
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByResource(org.semanticwb.model.Resource hasptresource)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(hasptresource.getSemanticObject().getModel().listSubjects(swb_hasPTResource,hasptresource.getSemanticObject()));
            return it;
        }

        /**
         * List resource type by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        /**
         * List resource type by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new resource type base.
     * 
     * @param base the base
     */
    public ResourceTypeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreated()
     */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreated(java.util.Date)
     */
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setModifiedBy(org.semanticwb.model.User)
     */
    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeModifiedBy()
     */
    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getModifiedBy()
     */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle()
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String)
     */
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle(java.lang.String)
     */
    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayTitle(java.lang.String)
     */
    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String, java.lang.String)
     */
    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    /**
     * Gets the resource bundle.
     * 
     * @return the resource bundle
     */
    public String getResourceBundle()
    {
        return getSemanticObject().getProperty(swb_resourceBundle);
    }

    /**
     * Sets the resource bundle.
     * 
     * @param value the new resource bundle
     */
    public void setResourceBundle(String value)
    {
        getSemanticObject().setProperty(swb_resourceBundle, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getUpdated()
     */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setUpdated(java.util.Date)
     */
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    /**
     * Gets the resource owl.
     * 
     * @return the resource owl
     */
    public String getResourceOWL()
    {
        return getSemanticObject().getProperty(swb_resourceOWL);
    }

    /**
     * Sets the resource owl.
     * 
     * @param value the new resource owl
     */
    public void setResourceOWL(String value)
    {
        getSemanticObject().setProperty(swb_resourceOWL, value);
    }

    /**
     * List sub types.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceSubType> listSubTypes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceSubType>(getSemanticObject().listObjectProperties(swb_hasPTSubType));
    }

    /**
     * Checks for sub type.
     * 
     * @param resourcesubtype the resourcesubtype
     * @return true, if successful
     */
    public boolean hasSubType(org.semanticwb.model.ResourceSubType resourcesubtype)
    {
        boolean ret=false;
        if(resourcesubtype!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasPTSubType,resourcesubtype.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the sub type.
     * 
     * @return the sub type
     */
    public org.semanticwb.model.ResourceSubType getSubType()
    {
         org.semanticwb.model.ResourceSubType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasPTSubType);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceSubType)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the resource mode.
     * 
     * @return the resource mode
     */
    public int getResourceMode()
    {
        return getSemanticObject().getIntProperty(swb_resourceMode);
    }

    /**
     * Sets the resource mode.
     * 
     * @param value the new resource mode
     */
    public void setResourceMode(int value)
    {
        getSemanticObject().setIntProperty(swb_resourceMode, value);
    }

    /**
     * List resources.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> listResources()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(getSemanticObject().listObjectProperties(swb_hasPTResource));
    }

    /**
     * Checks for resource.
     * 
     * @param resource the resource
     * @return true, if successful
     */
    public boolean hasResource(org.semanticwb.model.Resource resource)
    {
        boolean ret=false;
        if(resource!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasPTResource,resource.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the resource.
     * 
     * @return the resource
     */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasPTResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the resource cache.
     * 
     * @return the resource cache
     */
    public int getResourceCache()
    {
        return getSemanticObject().getIntProperty(swb_resourceCache);
    }

    /**
     * Sets the resource cache.
     * 
     * @param value the new resource cache
     */
    public void setResourceCache(int value)
    {
        getSemanticObject().setIntProperty(swb_resourceCache, value);
    }

    /**
     * Gets the resource class name.
     * 
     * @return the resource class name
     */
    public String getResourceClassName()
    {
        return getSemanticObject().getProperty(swb_resourceClassName);
    }

    /**
     * Sets the resource class name.
     * 
     * @param value the new resource class name
     */
    public void setResourceClassName(String value)
    {
        getSemanticObject().setProperty(swb_resourceClassName, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreator(org.semanticwb.model.User)
     */
    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeCreator()
     */
    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreator()
     */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription()
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String)
     */
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription(java.lang.String)
     */
    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayDescription(java.lang.String)
     */
    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String, java.lang.String)
     */
    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
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
