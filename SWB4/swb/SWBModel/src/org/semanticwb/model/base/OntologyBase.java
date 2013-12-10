package org.semanticwb.model.base;


   /**
   * Objeto que define una Ontologia 
   */
public abstract class OntologyBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.Traceable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Filterable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Undeleteable,org.semanticwb.model.FilterableClass
{
   /**
   * Interfaz que define propiedades para elementos que dependen de una ontologia
   */
    public static final org.semanticwb.platform.SemanticClass swb_OntologyDepable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#OntologyDepable");
    public static final org.semanticwb.platform.SemanticProperty swb_hasOntologyDependenceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasOntologyDependenceInv");
   /**
   * Sparql queries que pueden ser definidos y reutilizados dentro de una ontologia
   */
    public static final org.semanticwb.platform.SemanticClass swb_SparqlQuery=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SparqlQuery");
   /**
   * Reglas de inferencia definidas dentro de una ontologia
   */
    public static final org.semanticwb.platform.SemanticClass swb_InfRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#InfRule");
   /**
   * Objeto que define una Ontologia
   */
    public static final org.semanticwb.platform.SemanticClass swb_Ontology=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Ontology");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Ontology");

    public static class ClassMgr
    {
       /**
       * Returns a list of Ontology for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.Ontology
       */

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologies(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.Ontology for all models
       * @return Iterator of org.semanticwb.model.Ontology
       */

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologies()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.Ontology
       * @param id Identifier for org.semanticwb.model.Ontology
       * @return A org.semanticwb.model.Ontology
       */
        public static org.semanticwb.model.Ontology getOntology(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.model.Ontology ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    org.semanticwb.model.GenericObject gobj=obj.createGenericInstance();
                    if(gobj instanceof org.semanticwb.model.Ontology)
                    {
                        ret=(org.semanticwb.model.Ontology)gobj;
                    }
                }
            }
            return ret;
        }
       /**
       * Create a org.semanticwb.model.Ontology
       * @param id Identifier for org.semanticwb.model.Ontology
       * @return A org.semanticwb.model.Ontology
       */
        public static org.semanticwb.model.Ontology createOntology(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.model.Ontology)model.createGenericObject(model.getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.Ontology
       * @param id Identifier for org.semanticwb.model.Ontology
       */
        public static void removeOntology(String id)
        {
            org.semanticwb.model.Ontology obj=getOntology(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }
       /**
       * Returns true if exists a org.semanticwb.model.Ontology
       * @param id Identifier for org.semanticwb.model.Ontology
       * @return true if the org.semanticwb.model.Ontology exists, false otherwise
       */

        public static boolean hasOntology(String id)
        {
            return (getOntology(id)!=null);
        }
       /**
       * Gets all org.semanticwb.model.Ontology with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Ontology
       * @return Iterator with all the org.semanticwb.model.Ontology
       */

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Ontology with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Ontology
       */

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Ontology with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Ontology
       * @return Iterator with all the org.semanticwb.model.Ontology
       */

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Ontology with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Ontology
       */

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Ontology with a determined OntologyDependence
       * @param value OntologyDependence of the type org.semanticwb.model.OntologyDepable
       * @param model Model of the org.semanticwb.model.Ontology
       * @return Iterator with all the org.semanticwb.model.Ontology
       */

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByOntologyDependence(org.semanticwb.model.OntologyDepable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntologyDependenceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Ontology with a determined OntologyDependence
       * @param value OntologyDependence of the type org.semanticwb.model.OntologyDepable
       * @return Iterator with all the org.semanticwb.model.Ontology
       */

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByOntologyDependence(org.semanticwb.model.OntologyDepable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntologyDependenceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Ontology with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @param model Model of the org.semanticwb.model.Ontology
       * @return Iterator with all the org.semanticwb.model.Ontology
       */

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Ontology with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.model.Ontology
       */

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Ontology with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @param model Model of the org.semanticwb.model.Ontology
       * @return Iterator with all the org.semanticwb.model.Ontology
       */

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByModelProperty(org.semanticwb.model.ModelProperty value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Ontology with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @return Iterator with all the org.semanticwb.model.Ontology
       */

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByModelProperty(org.semanticwb.model.ModelProperty value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static OntologyBase.ClassMgr getOntologyClassMgr()
    {
        return new OntologyBase.ClassMgr();
    }

   /**
   * Constructs a OntologyBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Ontology
   */
    public OntologyBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ModifiedBy
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
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
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
   * Sets the value for the property Creator
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
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
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
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
   /**
   * Gets all the org.semanticwb.model.OntologyDepable
   * @return A GenericIterator with all the org.semanticwb.model.OntologyDepable
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.OntologyDepable> listOntologyDependences()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.OntologyDepable>(getSemanticObject().listObjectProperties(swb_hasOntologyDependenceInv));
    }

   /**
   * Gets true if has a OntologyDependence
   * @param value org.semanticwb.model.OntologyDepable to verify
   * @return true if the org.semanticwb.model.OntologyDepable exists, false otherwise
   */
    public boolean hasOntologyDependence(org.semanticwb.model.OntologyDepable value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasOntologyDependenceInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the OntologyDependence
   * @return a org.semanticwb.model.OntologyDepable
   */
    public org.semanticwb.model.OntologyDepable getOntologyDependence()
    {
         org.semanticwb.model.OntologyDepable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasOntologyDependenceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.OntologyDepable)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Undeleteable property
* @return boolean with the Undeleteable
*/
    public boolean isUndeleteable()
    {
        return getSemanticObject().getBooleanProperty(swb_undeleteable);
    }

/**
* Sets the Undeleteable property
* @param value long with the Undeleteable
*/
    public void setUndeleteable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_undeleteable, value);
    }

    public org.semanticwb.model.SparqlQuery getSparqlQuery(String id)
    {
        return org.semanticwb.model.SparqlQuery.ClassMgr.getSparqlQuery(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.SparqlQuery> listSparqlQueries()
    {
        return org.semanticwb.model.SparqlQuery.ClassMgr.listSparqlQueries(this);
    }

    public org.semanticwb.model.SparqlQuery createSparqlQuery(String id)
    {
        return org.semanticwb.model.SparqlQuery.ClassMgr.createSparqlQuery(id,this);
    }

    public org.semanticwb.model.SparqlQuery createSparqlQuery()
    {
        long id=getSemanticObject().getModel().getCounter(swb_SparqlQuery);
        return org.semanticwb.model.SparqlQuery.ClassMgr.createSparqlQuery(String.valueOf(id),this);
    } 

    public void removeSparqlQuery(String id)
    {
        org.semanticwb.model.SparqlQuery.ClassMgr.removeSparqlQuery(id, this);
    }
    public boolean hasSparqlQuery(String id)
    {
        return org.semanticwb.model.SparqlQuery.ClassMgr.hasSparqlQuery(id, this);
    }

    public org.semanticwb.model.InfRule getInfRule(String id)
    {
        return org.semanticwb.model.InfRule.ClassMgr.getInfRule(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.InfRule> listInfRules()
    {
        return org.semanticwb.model.InfRule.ClassMgr.listInfRules(this);
    }

    public org.semanticwb.model.InfRule createInfRule(String id)
    {
        return org.semanticwb.model.InfRule.ClassMgr.createInfRule(id,this);
    }

    public org.semanticwb.model.InfRule createInfRule()
    {
        long id=getSemanticObject().getModel().getCounter(swb_InfRule);
        return org.semanticwb.model.InfRule.ClassMgr.createInfRule(String.valueOf(id),this);
    } 

    public void removeInfRule(String id)
    {
        org.semanticwb.model.InfRule.ClassMgr.removeInfRule(id, this);
    }
    public boolean hasInfRule(String id)
    {
        return org.semanticwb.model.InfRule.ClassMgr.hasInfRule(id, this);
    }
}
