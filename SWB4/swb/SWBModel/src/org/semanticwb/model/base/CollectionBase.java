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

import org.semanticwb.platform.SemanticLiteral;


   // TODO: Auto-generated Javadoc
/**
    * Define una Collección de objetos de una clase especificada con la propiedad "collectionClass".
    */
public abstract class CollectionBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    
    /** The Constant swb_Class. */
    public static final org.semanticwb.platform.SemanticClass swb_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Class");
    
    /** The Constant swb_collectionClass. */
    public static final org.semanticwb.platform.SemanticProperty swb_collectionClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#collectionClass");
    
    /** The Constant swb_hasCollectionListProperties. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasCollectionListProperties=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasCollectionListProperties");
    
    /** The Constant swb_hasCollectionSearchProperties. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasCollectionSearchProperties=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasCollectionSearchProperties");
   
   /** Define una Collección de objetos de una clase especificada con la propiedad "collectionClass". */
    public static final org.semanticwb.platform.SemanticClass swb_Collection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Collection");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Collection");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of Collection for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.Collection
        */

        public static java.util.Iterator<org.semanticwb.model.Collection> listCollections(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Collection>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.Collection for all models
       * @return Iterator of org.semanticwb.model.Collection
       */

        public static java.util.Iterator<org.semanticwb.model.Collection> listCollections()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Collection>(it, true);
        }

        /**
         * Creates the collection.
         * 
         * @param model the model
         * @return the org.semanticwb.model. collection
         */
        public static org.semanticwb.model.Collection createCollection(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.Collection.ClassMgr.createCollection(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.Collection
       * @param id Identifier for org.semanticwb.model.Collection
       * @param model Model of the org.semanticwb.model.Collection
       * @return A org.semanticwb.model.Collection
       */
        public static org.semanticwb.model.Collection getCollection(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Collection)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.Collection
       * @param id Identifier for org.semanticwb.model.Collection
       * @param model Model of the org.semanticwb.model.Collection
       * @return A org.semanticwb.model.Collection
       */
        public static org.semanticwb.model.Collection createCollection(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Collection)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.Collection
       * @param id Identifier for org.semanticwb.model.Collection
       * @param model Model of the org.semanticwb.model.Collection
       */
        public static void removeCollection(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.Collection
       * @param id Identifier for org.semanticwb.model.Collection
       * @param model Model of the org.semanticwb.model.Collection
       * @return true if the org.semanticwb.model.Collection exists, false otherwise
       */

        public static boolean hasCollection(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCollection(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.Collection with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Collection
       * @return Iterator with all the org.semanticwb.model.Collection
       */

        public static java.util.Iterator<org.semanticwb.model.Collection> listCollectionByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Collection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Collection with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Collection
       */

        public static java.util.Iterator<org.semanticwb.model.Collection> listCollectionByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Collection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Collection with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Collection
       * @return Iterator with all the org.semanticwb.model.Collection
       */

        public static java.util.Iterator<org.semanticwb.model.Collection> listCollectionByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Collection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Collection with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Collection
       */

        public static java.util.Iterator<org.semanticwb.model.Collection> listCollectionByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Collection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
    * Constructs a CollectionBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the Collection
    */
    public CollectionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
 * Gets the Created property.
 * 
 * @return java.util.Date with the Created
 */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
 * Sets the Created property.
 * 
 * @param value long with the Created
 */
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }
   
   /**
    * Sets the value for the property ModifiedBy.
    * 
    * @param value ModifiedBy to set
    */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   
   /**
    * Remove the value for ModifiedBy property.
    */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
    * Gets the ModifiedBy.
    * 
    * @return a org.semanticwb.model.User
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

/**
 * Gets the Title property.
 * 
 * @return String with the Title
 */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
 * Sets the Title property.
 * 
 * @param value long with the Title
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
 * Gets the Updated property.
 * 
 * @return java.util.Date with the Updated
 */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
 * Sets the Updated property.
 * 
 * @param value long with the Updated
 */
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    /**
     * Sets the collection class.
     * 
     * @param value the new collection class
     */
    public void setCollectionClass(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swb_collectionClass, value);
    }

    /**
     * Removes the collection class.
     */
    public void removeCollectionClass()
    {
        getSemanticObject().removeProperty(swb_collectionClass);
    }

/**
 * Gets the CollectionClass property.
 * 
 * @return the value for the property as org.semanticwb.platform.SemanticObject
 */
    public org.semanticwb.platform.SemanticObject getCollectionClass()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swb_collectionClass);
         return ret;
    }

    /**
     * List list propertieses.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<String> listListPropertieses()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(swb_hasCollectionListProperties);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    /**
     * Adds the list properties.
     * 
     * @param value the value
     */
    public void addListProperties(String value)
    {
        getSemanticObject().addLiteralProperty(swb_hasCollectionListProperties, new org.semanticwb.platform.SemanticLiteral(value));
    }

    /**
     * Removes the all list properties.
     */
    public void removeAllListProperties()
    {
        getSemanticObject().removeProperty(swb_hasCollectionListProperties);
    }

    /**
     * Removes the list properties.
     * 
     * @param value the value
     */
    public void removeListProperties(String value)
    {
        getSemanticObject().removeLiteralProperty(swb_hasCollectionListProperties, new SemanticLiteral(value));
    }

    /**
     * List search propertieses.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<String> listSearchPropertieses()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(swb_hasCollectionSearchProperties);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    /**
     * Adds the search properties.
     * 
     * @param value the value
     */
    public void addSearchProperties(String value)
    {
        getSemanticObject().addLiteralProperty(swb_hasCollectionSearchProperties, new org.semanticwb.platform.SemanticLiteral(value));
    }

    /**
     * Removes the all search properties.
     */
    public void removeAllSearchProperties()
    {
        getSemanticObject().removeProperty(swb_hasCollectionSearchProperties);
    }

    /**
     * Removes the search properties.
     * 
     * @param value the value
     */
    public void removeSearchProperties(String value)
    {
        getSemanticObject().removeLiteralProperty(swb_hasCollectionSearchProperties, new SemanticLiteral(value));
        //getSemanticObject().removeProperty(swb_hasCollectionSearchProperties,value);
    }
   
   /**
    * Sets the value for the property Creator.
    * 
    * @param value Creator to set
    */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   
   /**
    * Remove the value for Creator property.
    */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
    * Gets the Creator.
    * 
    * @return a org.semanticwb.model.User
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
 * Gets the Description property.
 * 
 * @return String with the Description
 */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
 * Sets the Description property.
 * 
 * @param value long with the Description
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
    * Gets the WebSite.
    * 
    * @return a instance of org.semanticwb.model.WebSite
    */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
