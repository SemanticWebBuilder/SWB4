package org.semanticwb.bsc.tracing.base;


   /**
   * Clase que define un factor de riesgo. 
   */
public abstract class FactorBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.Roleable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Filterable,org.semanticwb.bsc.Recognizable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Activeable,org.semanticwb.bsc.Help,org.semanticwb.model.UserGroupable,org.semanticwb.model.Traceable
{
   /**
   * Define un riesgo que puede presentarse mediante un elemento del BSC: Objetivo, Entregable, Iniciativa o Indicador. Un riesgo tambien puede presentarse independientemente.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Risk=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Risk");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasFactorInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasFactorInv");
   /**
   * Define el tipo de factor: Interno o Externo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_factorType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#factorType");
   /**
   * Gestiona la información de un control en un Riesgo.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Control=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Control");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasControl=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasControl");
   /**
   * Define la clasificación de un Factor
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_classificationFactor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#classificationFactor");
   /**
   * Clase que define un factor de riesgo.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Factor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Factor");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Factor");

    public static class ClassMgr
    {
       /**
       * Returns a list of Factor for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.Factor
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Factor> listFactors(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.Factor for all models
       * @return Iterator of org.semanticwb.bsc.tracing.Factor
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Factor> listFactors()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor>(it, true);
        }

        public static org.semanticwb.bsc.tracing.Factor createFactor(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.Factor.ClassMgr.createFactor(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.Factor
       * @param id Identifier for org.semanticwb.bsc.tracing.Factor
       * @param model Model of the org.semanticwb.bsc.tracing.Factor
       * @return A org.semanticwb.bsc.tracing.Factor
       */
        public static org.semanticwb.bsc.tracing.Factor getFactor(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Factor)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.Factor
       * @param id Identifier for org.semanticwb.bsc.tracing.Factor
       * @param model Model of the org.semanticwb.bsc.tracing.Factor
       * @return A org.semanticwb.bsc.tracing.Factor
       */
        public static org.semanticwb.bsc.tracing.Factor createFactor(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Factor)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.Factor
       * @param id Identifier for org.semanticwb.bsc.tracing.Factor
       * @param model Model of the org.semanticwb.bsc.tracing.Factor
       */
        public static void removeFactor(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.Factor
       * @param id Identifier for org.semanticwb.bsc.tracing.Factor
       * @param model Model of the org.semanticwb.bsc.tracing.Factor
       * @return true if the org.semanticwb.bsc.tracing.Factor exists, false otherwise
       */

        public static boolean hasFactor(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFactor(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Factor with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Factor
       * @return Iterator with all the org.semanticwb.bsc.tracing.Factor
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Factor> listFactorByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Factor with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Factor
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Factor> listFactorByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Factor with a determined Risk
       * @param value Risk of the type org.semanticwb.bsc.element.Risk
       * @param model Model of the org.semanticwb.bsc.tracing.Factor
       * @return Iterator with all the org.semanticwb.bsc.tracing.Factor
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Factor> listFactorByRisk(org.semanticwb.bsc.element.Risk value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasFactorInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Factor with a determined Risk
       * @param value Risk of the type org.semanticwb.bsc.element.Risk
       * @return Iterator with all the org.semanticwb.bsc.tracing.Factor
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Factor> listFactorByRisk(org.semanticwb.bsc.element.Risk value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasFactorInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Factor with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.tracing.Factor
       * @return Iterator with all the org.semanticwb.bsc.tracing.Factor
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Factor> listFactorByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Factor with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.tracing.Factor
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Factor> listFactorByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Factor with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Factor
       * @return Iterator with all the org.semanticwb.bsc.tracing.Factor
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Factor> listFactorByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Factor with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Factor
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Factor> listFactorByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Factor with a determined Control
       * @param value Control of the type org.semanticwb.bsc.tracing.Control
       * @param model Model of the org.semanticwb.bsc.tracing.Factor
       * @return Iterator with all the org.semanticwb.bsc.tracing.Factor
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Factor> listFactorByControl(org.semanticwb.bsc.tracing.Control value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasControl, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Factor with a determined Control
       * @param value Control of the type org.semanticwb.bsc.tracing.Control
       * @return Iterator with all the org.semanticwb.bsc.tracing.Factor
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Factor> listFactorByControl(org.semanticwb.bsc.tracing.Control value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasControl,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Factor with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.tracing.Factor
       * @return Iterator with all the org.semanticwb.bsc.tracing.Factor
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Factor> listFactorByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Factor with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.tracing.Factor
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Factor> listFactorByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Factor> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FactorBase.ClassMgr getFactorClassMgr()
    {
        return new FactorBase.ClassMgr();
    }

   /**
   * Constructs a FactorBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Factor
   */
    public FactorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Risk
   * @param value Risk to set
   */

    public void setRisk(org.semanticwb.bsc.element.Risk value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_hasFactorInv, value.getSemanticObject());
        }else
        {
            removeRisk();
        }
    }
   /**
   * Remove the value for Risk property
   */

    public void removeRisk()
    {
        getSemanticObject().removeProperty(bsc_hasFactorInv);
    }

   /**
   * Gets the Risk
   * @return a org.semanticwb.bsc.element.Risk
   */
    public org.semanticwb.bsc.element.Risk getRisk()
    {
         org.semanticwb.bsc.element.Risk ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasFactorInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Risk)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Prefix property
* @return String with the Prefix
*/
    public String getPrefix()
    {
        //Override this method in Factor object
        return getSemanticObject().getProperty(bsc_prefix,false);
    }

/**
* Sets the Prefix property
* @param value long with the Prefix
*/
    public void setPrefix(String value)
    {
        //Override this method in Factor object
        getSemanticObject().setProperty(bsc_prefix, value,false);
    }

/**
* Gets the FactorType property
* @return String with the FactorType
*/
    public String getFactorType()
    {
        return getSemanticObject().getProperty(bsc_factorType);
    }

/**
* Sets the FactorType property
* @param value long with the FactorType
*/
    public void setFactorType(String value)
    {
        getSemanticObject().setProperty(bsc_factorType, value);
    }
   /**
   * Gets all the org.semanticwb.bsc.tracing.Control
   * @return A GenericIterator with all the org.semanticwb.bsc.tracing.Control
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control> listControls()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control>(getSemanticObject().listObjectProperties(bsc_hasControl));
    }

   /**
   * Gets true if has a Control
   * @param value org.semanticwb.bsc.tracing.Control to verify
   * @return true if the org.semanticwb.bsc.tracing.Control exists, false otherwise
   */
    public boolean hasControl(org.semanticwb.bsc.tracing.Control value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasControl,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Control
   * @param value org.semanticwb.bsc.tracing.Control to add
   */

    public void addControl(org.semanticwb.bsc.tracing.Control value)
    {
        getSemanticObject().addObjectProperty(bsc_hasControl, value.getSemanticObject());
    }
   /**
   * Removes all the Control
   */

    public void removeAllControl()
    {
        getSemanticObject().removeProperty(bsc_hasControl);
    }
   /**
   * Removes a Control
   * @param value org.semanticwb.bsc.tracing.Control to remove
   */

    public void removeControl(org.semanticwb.bsc.tracing.Control value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasControl,value.getSemanticObject());
    }

   /**
   * Gets the Control
   * @return a org.semanticwb.bsc.tracing.Control
   */
    public org.semanticwb.bsc.tracing.Control getControl()
    {
         org.semanticwb.bsc.tracing.Control ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasControl);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.Control)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ClassificationFactor property
* @return String with the ClassificationFactor
*/
    public String getClassificationFactor()
    {
        return getSemanticObject().getProperty(bsc_classificationFactor);
    }

/**
* Sets the ClassificationFactor property
* @param value long with the ClassificationFactor
*/
    public void setClassificationFactor(String value)
    {
        getSemanticObject().setProperty(bsc_classificationFactor, value);
    }

   /**
   * Gets the BSC
   * @return a instance of org.semanticwb.bsc.BSC
   */
    public org.semanticwb.bsc.BSC getBSC()
    {
        return (org.semanticwb.bsc.BSC)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
