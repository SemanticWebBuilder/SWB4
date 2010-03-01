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
 * The Class DeviceBase.
 */
public abstract class DeviceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.FilterableClass,org.semanticwb.model.Traceable,org.semanticwb.model.Filterable,org.semanticwb.model.Descriptiveable
{
    
    /** The Constant swb_Device. */
    public static final org.semanticwb.platform.SemanticClass swb_Device=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Device");
    
    /** The Constant swb_dvcParent. */
    public static final org.semanticwb.platform.SemanticProperty swb_dvcParent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#dvcParent");
    
    /** The Constant swb_hasDvcChild. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasDvcChild=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasDvcChild");
    
    /** The Constant swb_dvcUserAgent. */
    public static final org.semanticwb.platform.SemanticProperty swb_dvcUserAgent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#dvcUserAgent");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Device");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List devices.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Device> listDevices(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Device>(it, true);
        }

        /**
         * List devices.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Device> listDevices()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Device>(it, true);
        }

        /**
         * Gets the device.
         * 
         * @param id the id
         * @param model the model
         * @return the device
         */
        public static org.semanticwb.model.Device getDevice(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Device)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the device.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. device
         */
        public static org.semanticwb.model.Device createDevice(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Device)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the device.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeDevice(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for device.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasDevice(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDevice(id, model)!=null);
        }

        /**
         * List device by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List device by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List device by parent.
         * 
         * @param dvcparent the dvcparent
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByParent(org.semanticwb.model.Device dvcparent,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_dvcParent, dvcparent.getSemanticObject()));
            return it;
        }

        /**
         * List device by parent.
         * 
         * @param dvcparent the dvcparent
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByParent(org.semanticwb.model.Device dvcparent)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(dvcparent.getSemanticObject().getModel().listSubjects(swb_dvcParent,dvcparent.getSemanticObject()));
            return it;
        }

        /**
         * List device by child.
         * 
         * @param hasdvcchild the hasdvcchild
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByChild(org.semanticwb.model.Device hasdvcchild,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasDvcChild, hasdvcchild.getSemanticObject()));
            return it;
        }

        /**
         * List device by child.
         * 
         * @param hasdvcchild the hasdvcchild
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByChild(org.semanticwb.model.Device hasdvcchild)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(hasdvcchild.getSemanticObject().getModel().listSubjects(swb_hasDvcChild,hasdvcchild.getSemanticObject()));
            return it;
        }

        /**
         * List device by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        /**
         * List device by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new device base.
     * 
     * @param base the base
     */
    public DeviceBase(org.semanticwb.platform.SemanticObject base)
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
     * Sets the parent.
     * 
     * @param value the new parent
     */
    public void setParent(org.semanticwb.model.Device value)
    {
        getSemanticObject().setObjectProperty(swb_dvcParent, value.getSemanticObject());
    }

    /**
     * Removes the parent.
     */
    public void removeParent()
    {
        getSemanticObject().removeProperty(swb_dvcParent);
    }

    /**
     * Gets the parent.
     * 
     * @return the parent
     */
    public org.semanticwb.model.Device getParent()
    {
         org.semanticwb.model.Device ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_dvcParent);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Device)obj.createGenericInstance();
         }
         return ret;
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
     * List childs.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> listChilds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Device>(getSemanticObject().listObjectProperties(swb_hasDvcChild));
    }

    /**
     * Checks for child.
     * 
     * @param device the device
     * @return true, if successful
     */
    public boolean hasChild(org.semanticwb.model.Device device)
    {
        boolean ret=false;
        if(device!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasDvcChild,device.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the child.
     * 
     * @return the child
     */
    public org.semanticwb.model.Device getChild()
    {
         org.semanticwb.model.Device ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasDvcChild);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Device)obj.createGenericInstance();
         }
         return ret;
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

    /**
     * Gets the user agent.
     * 
     * @return the user agent
     */
    public String getUserAgent()
    {
        return getSemanticObject().getProperty(swb_dvcUserAgent);
    }

    /**
     * Sets the user agent.
     * 
     * @param value the new user agent
     */
    public void setUserAgent(String value)
    {
        getSemanticObject().setProperty(swb_dvcUserAgent, value);
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
