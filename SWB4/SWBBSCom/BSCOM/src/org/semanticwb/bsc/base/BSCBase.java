package org.semanticwb.bsc.base;


public abstract class BSCBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Trashable,org.semanticwb.model.Filterable,org.semanticwb.model.Countryable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.OntologyDepable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Traceable,org.semanticwb.model.Localeable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Indexable
{
    public static final org.semanticwb.platform.SemanticClass bsc_BSC=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BSC");
    public static final org.semanticwb.platform.SemanticProperty bsc_parent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#parent");
    public static final org.semanticwb.platform.SemanticClass bsc_Measure=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Measure");
    public static final org.semanticwb.platform.SemanticClass bsc_Objective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Objective");
    public static final org.semanticwb.platform.SemanticClass bsc_Indicator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Indicator");
    public static final org.semanticwb.platform.SemanticClass bsc_Risk=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Risk");
    public static final org.semanticwb.platform.SemanticClass bsc_Action=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Action");
    public static final org.semanticwb.platform.SemanticClass bsc_Perspective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Perspective");
    public static final org.semanticwb.platform.SemanticClass bsc_Initiative=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Initiative");
   /**
   * Período de medición.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Period=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Period");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BSC");

    public static class ClassMgr
    {
       /**
       * Returns a list of BSC for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.BSC for all models
       * @return Iterator of org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.BSC
       * @param id Identifier for org.semanticwb.bsc.BSC
       * @return A org.semanticwb.bsc.BSC
       */
        public static org.semanticwb.bsc.BSC getBSC(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.bsc.BSC ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    org.semanticwb.model.GenericObject gobj=obj.createGenericInstance();
                    if(gobj instanceof org.semanticwb.bsc.BSC)
                    {
                        ret=(org.semanticwb.bsc.BSC)gobj;
                    }
                }
            }
            return ret;
        }
       /**
       * Create a org.semanticwb.bsc.BSC
       * @param id Identifier for org.semanticwb.bsc.BSC
       * @return A org.semanticwb.bsc.BSC
       */
        public static org.semanticwb.bsc.BSC createBSC(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.bsc.BSC)model.createGenericObject(model.getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.BSC
       * @param id Identifier for org.semanticwb.bsc.BSC
       */
        public static void removeBSC(String id)
        {
            org.semanticwb.bsc.BSC obj=getBSC(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.BSC
       * @param id Identifier for org.semanticwb.bsc.BSC
       * @return true if the org.semanticwb.bsc.BSC exists, false otherwise
       */

        public static boolean hasBSC(String id)
        {
            return (getBSC(id)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCBySubModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCBySubModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Parent
       * @param value Parent of the type org.semanticwb.bsc.BSC
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByParent(org.semanticwb.bsc.BSC value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Parent
       * @param value Parent of the type org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByParent(org.semanticwb.bsc.BSC value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByDefaultTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByDefaultTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByOntology(org.semanticwb.model.Ontology value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByOntology(org.semanticwb.model.Ontology value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByHomePage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_homePage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByHomePage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_homePage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByUserRepository(org.semanticwb.model.UserRepository value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByUserRepository(org.semanticwb.model.UserRepository value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @param model Model of the org.semanticwb.bsc.BSC
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByModelProperty(org.semanticwb.model.ModelProperty value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.BSC with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @return Iterator with all the org.semanticwb.bsc.BSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.BSC> listBSCByModelProperty(org.semanticwb.model.ModelProperty value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.BSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static BSCBase.ClassMgr getBSCClassMgr()
    {
        return new BSCBase.ClassMgr();
    }

   /**
   * Constructs a BSCBase with a SemanticObject
   * @param base The SemanticObject with the properties for the BSC
   */
    public BSCBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Parent
   * @param value Parent to set
   */

    public void setParent(org.semanticwb.bsc.BSC value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_parent, value.getSemanticObject());
        }else
        {
            removeParent();
        }
    }
   /**
   * Remove the value for Parent property
   */

    public void removeParent()
    {
        getSemanticObject().removeProperty(bsc_parent);
    }

   /**
   * Gets the Parent
   * @return a org.semanticwb.bsc.BSC
   */
    public org.semanticwb.bsc.BSC getParent()
    {
         org.semanticwb.bsc.BSC ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_parent);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.BSC)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.bsc.element.Measure getMeasure(String id)
    {
        return org.semanticwb.bsc.element.Measure.ClassMgr.getMeasure(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.element.Measure> listMeasures()
    {
        return org.semanticwb.bsc.element.Measure.ClassMgr.listMeasures(this);
    }

    public org.semanticwb.bsc.element.Measure createMeasure(String id)
    {
        return org.semanticwb.bsc.element.Measure.ClassMgr.createMeasure(id,this);
    }

    public org.semanticwb.bsc.element.Measure createMeasure()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Measure);
        return org.semanticwb.bsc.element.Measure.ClassMgr.createMeasure(String.valueOf(id),this);
    } 

    public void removeMeasure(String id)
    {
        org.semanticwb.bsc.element.Measure.ClassMgr.removeMeasure(id, this);
    }
    public boolean hasMeasure(String id)
    {
        return org.semanticwb.bsc.element.Measure.ClassMgr.hasMeasure(id, this);
    }

    public org.semanticwb.bsc.Objective getObjective(String id)
    {
        return org.semanticwb.bsc.Objective.ClassMgr.getObjective(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.Objective> listObjectives()
    {
        return org.semanticwb.bsc.Objective.ClassMgr.listObjectives(this);
    }

    public org.semanticwb.bsc.Objective createObjective(String id)
    {
        return org.semanticwb.bsc.Objective.ClassMgr.createObjective(id,this);
    }

    public org.semanticwb.bsc.Objective createObjective()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Objective);
        return org.semanticwb.bsc.Objective.ClassMgr.createObjective(String.valueOf(id),this);
    } 

    public void removeObjective(String id)
    {
        org.semanticwb.bsc.Objective.ClassMgr.removeObjective(id, this);
    }
    public boolean hasObjective(String id)
    {
        return org.semanticwb.bsc.Objective.ClassMgr.hasObjective(id, this);
    }

    public org.semanticwb.bsc.Indicator getIndicator(String id)
    {
        return org.semanticwb.bsc.Indicator.ClassMgr.getIndicator(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicators()
    {
        return org.semanticwb.bsc.Indicator.ClassMgr.listIndicators(this);
    }

    public org.semanticwb.bsc.Indicator createIndicator(String id)
    {
        return org.semanticwb.bsc.Indicator.ClassMgr.createIndicator(id,this);
    }

    public org.semanticwb.bsc.Indicator createIndicator()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Indicator);
        return org.semanticwb.bsc.Indicator.ClassMgr.createIndicator(String.valueOf(id),this);
    } 

    public void removeIndicator(String id)
    {
        org.semanticwb.bsc.Indicator.ClassMgr.removeIndicator(id, this);
    }
    public boolean hasIndicator(String id)
    {
        return org.semanticwb.bsc.Indicator.ClassMgr.hasIndicator(id, this);
    }

    public org.semanticwb.bsc.tracing.Risk getRisk(String id)
    {
        return org.semanticwb.bsc.tracing.Risk.ClassMgr.getRisk(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRisks()
    {
        return org.semanticwb.bsc.tracing.Risk.ClassMgr.listRisks(this);
    }

    public org.semanticwb.bsc.tracing.Risk createRisk(String id)
    {
        return org.semanticwb.bsc.tracing.Risk.ClassMgr.createRisk(id,this);
    }

    public org.semanticwb.bsc.tracing.Risk createRisk()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Risk);
        return org.semanticwb.bsc.tracing.Risk.ClassMgr.createRisk(String.valueOf(id),this);
    } 

    public void removeRisk(String id)
    {
        org.semanticwb.bsc.tracing.Risk.ClassMgr.removeRisk(id, this);
    }
    public boolean hasRisk(String id)
    {
        return org.semanticwb.bsc.tracing.Risk.ClassMgr.hasRisk(id, this);
    }

    public org.semanticwb.bsc.tracing.Action getAction(String id)
    {
        return org.semanticwb.bsc.tracing.Action.ClassMgr.getAction(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.tracing.Action> listActions()
    {
        return org.semanticwb.bsc.tracing.Action.ClassMgr.listActions(this);
    }

    public org.semanticwb.bsc.tracing.Action createAction(String id)
    {
        return org.semanticwb.bsc.tracing.Action.ClassMgr.createAction(id,this);
    }

    public org.semanticwb.bsc.tracing.Action createAction()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Action);
        return org.semanticwb.bsc.tracing.Action.ClassMgr.createAction(String.valueOf(id),this);
    } 

    public void removeAction(String id)
    {
        org.semanticwb.bsc.tracing.Action.ClassMgr.removeAction(id, this);
    }
    public boolean hasAction(String id)
    {
        return org.semanticwb.bsc.tracing.Action.ClassMgr.hasAction(id, this);
    }

    public org.semanticwb.bsc.element.Perspective getPerspective(String id)
    {
        return org.semanticwb.bsc.element.Perspective.ClassMgr.getPerspective(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectives()
    {
        return org.semanticwb.bsc.element.Perspective.ClassMgr.listPerspectives(this);
    }

    public org.semanticwb.bsc.element.Perspective createPerspective(String id)
    {
        return org.semanticwb.bsc.element.Perspective.ClassMgr.createPerspective(id,this);
    }

    public org.semanticwb.bsc.element.Perspective createPerspective()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Perspective);
        return org.semanticwb.bsc.element.Perspective.ClassMgr.createPerspective(String.valueOf(id),this);
    } 

    public void removePerspective(String id)
    {
        org.semanticwb.bsc.element.Perspective.ClassMgr.removePerspective(id, this);
    }
    public boolean hasPerspective(String id)
    {
        return org.semanticwb.bsc.element.Perspective.ClassMgr.hasPerspective(id, this);
    }

    public org.semanticwb.bsc.element.Initiative getInitiative(String id)
    {
        return org.semanticwb.bsc.element.Initiative.ClassMgr.getInitiative(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.element.Initiative> listInitiatives()
    {
        return org.semanticwb.bsc.element.Initiative.ClassMgr.listInitiatives(this);
    }

    public org.semanticwb.bsc.element.Initiative createInitiative(String id)
    {
        return org.semanticwb.bsc.element.Initiative.ClassMgr.createInitiative(id,this);
    }

    public org.semanticwb.bsc.element.Initiative createInitiative()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Initiative);
        return org.semanticwb.bsc.element.Initiative.ClassMgr.createInitiative(String.valueOf(id),this);
    } 

    public void removeInitiative(String id)
    {
        org.semanticwb.bsc.element.Initiative.ClassMgr.removeInitiative(id, this);
    }
    public boolean hasInitiative(String id)
    {
        return org.semanticwb.bsc.element.Initiative.ClassMgr.hasInitiative(id, this);
    }

    public org.semanticwb.bsc.accessory.Period getPeriod(String id)
    {
        return org.semanticwb.bsc.accessory.Period.ClassMgr.getPeriod(id, this);
    }

    public java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriods()
    {
        return org.semanticwb.bsc.accessory.Period.ClassMgr.listPeriods(this);
    }

    public org.semanticwb.bsc.accessory.Period createPeriod(String id)
    {
        return org.semanticwb.bsc.accessory.Period.ClassMgr.createPeriod(id,this);
    }

    public org.semanticwb.bsc.accessory.Period createPeriod()
    {
        long id=getSemanticObject().getModel().getCounter(bsc_Period);
        return org.semanticwb.bsc.accessory.Period.ClassMgr.createPeriod(String.valueOf(id),this);
    } 

    public void removePeriod(String id)
    {
        org.semanticwb.bsc.accessory.Period.ClassMgr.removePeriod(id, this);
    }
    public boolean hasPeriod(String id)
    {
        return org.semanticwb.bsc.accessory.Period.ClassMgr.hasPeriod(id, this);
    }
}
