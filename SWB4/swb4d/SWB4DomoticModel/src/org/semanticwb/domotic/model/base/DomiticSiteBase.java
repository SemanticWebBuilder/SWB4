package org.semanticwb.domotic.model.base;


public abstract class DomiticSiteBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Indexable,org.semanticwb.model.Localeable,org.semanticwb.model.Filterable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.Countryable,org.semanticwb.model.Trashable,org.semanticwb.model.FilterableClass,org.semanticwb.model.OntologyDepable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomGateway");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomDevice=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDevice");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomDeviceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDeviceType");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomDeviceStat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDeviceStat");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomiticSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomiticSite");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomiticSite");

    public static class ClassMgr
    {
       /**
       * Returns a list of DomiticSite for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSites(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.DomiticSite for all models
       * @return Iterator of org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSites()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite>(it, true);
        }
       /**
       * Gets a org.semanticwb.domotic.model.DomiticSite
       * @param id Identifier for org.semanticwb.domotic.model.DomiticSite
       * @return A org.semanticwb.domotic.model.DomiticSite
       */
        public static org.semanticwb.domotic.model.DomiticSite getDomiticSite(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.domotic.model.DomiticSite ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    org.semanticwb.model.GenericObject gobj=obj.createGenericInstance();
                    if(gobj instanceof org.semanticwb.domotic.model.DomiticSite)
                    {
                        ret=(org.semanticwb.domotic.model.DomiticSite)gobj;
                    }
                }
            }
            return ret;
        }
       /**
       * Create a org.semanticwb.domotic.model.DomiticSite
       * @param id Identifier for org.semanticwb.domotic.model.DomiticSite
       * @return A org.semanticwb.domotic.model.DomiticSite
       */
        public static org.semanticwb.domotic.model.DomiticSite createDomiticSite(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.domotic.model.DomiticSite)model.createGenericObject(model.getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.DomiticSite
       * @param id Identifier for org.semanticwb.domotic.model.DomiticSite
       */
        public static void removeDomiticSite(String id)
        {
            org.semanticwb.domotic.model.DomiticSite obj=getDomiticSite(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.DomiticSite
       * @param id Identifier for org.semanticwb.domotic.model.DomiticSite
       * @return true if the org.semanticwb.domotic.model.DomiticSite exists, false otherwise
       */

        public static boolean hasDomiticSite(String id)
        {
            return (getDomiticSite(id)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.domotic.model.DomiticSite
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.domotic.model.DomiticSite
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteBySubModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteBySubModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @param model Model of the org.semanticwb.domotic.model.DomiticSite
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByDefaultTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByDefaultTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.domotic.model.DomiticSite
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.domotic.model.DomiticSite
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.domotic.model.DomiticSite
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @param model Model of the org.semanticwb.domotic.model.DomiticSite
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByOntology(org.semanticwb.model.Ontology value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByOntology(org.semanticwb.model.Ontology value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.domotic.model.DomiticSite
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByHomePage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_homePage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByHomePage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_homePage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @param model Model of the org.semanticwb.domotic.model.DomiticSite
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByUserRepository(org.semanticwb.model.UserRepository value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByUserRepository(org.semanticwb.model.UserRepository value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @param model Model of the org.semanticwb.domotic.model.DomiticSite
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @param model Model of the org.semanticwb.domotic.model.DomiticSite
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByModelProperty(org.semanticwb.model.ModelProperty value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomiticSite with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @return Iterator with all the org.semanticwb.domotic.model.DomiticSite
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomiticSite> listDomiticSiteByModelProperty(org.semanticwb.model.ModelProperty value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomiticSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DomiticSiteBase.ClassMgr getDomiticSiteClassMgr()
    {
        return new DomiticSiteBase.ClassMgr();
    }

   /**
   * Constructs a DomiticSiteBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DomiticSite
   */
    public DomiticSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.domotic.model.DomGateway getDomGateway(String id)
    {
        return org.semanticwb.domotic.model.DomGateway.ClassMgr.getDomGateway(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.DomGateway> listDomGateways()
    {
        return org.semanticwb.domotic.model.DomGateway.ClassMgr.listDomGateways(this);
    }

    public org.semanticwb.domotic.model.DomGateway createDomGateway(String id)
    {
        return org.semanticwb.domotic.model.DomGateway.ClassMgr.createDomGateway(id,this);
    }

    public org.semanticwb.domotic.model.DomGateway createDomGateway()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_DomGateway);
        return org.semanticwb.domotic.model.DomGateway.ClassMgr.createDomGateway(String.valueOf(id),this);
    } 

    public void removeDomGateway(String id)
    {
        org.semanticwb.domotic.model.DomGateway.ClassMgr.removeDomGateway(id, this);
    }
    public boolean hasDomGateway(String id)
    {
        return org.semanticwb.domotic.model.DomGateway.ClassMgr.hasDomGateway(id, this);
    }

    public org.semanticwb.domotic.model.DomDevice getDomDevice(String id)
    {
        return org.semanticwb.domotic.model.DomDevice.ClassMgr.getDomDevice(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDevices()
    {
        return org.semanticwb.domotic.model.DomDevice.ClassMgr.listDomDevices(this);
    }

    public org.semanticwb.domotic.model.DomDevice createDomDevice(String id)
    {
        return org.semanticwb.domotic.model.DomDevice.ClassMgr.createDomDevice(id,this);
    }

    public org.semanticwb.domotic.model.DomDevice createDomDevice()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_DomDevice);
        return org.semanticwb.domotic.model.DomDevice.ClassMgr.createDomDevice(String.valueOf(id),this);
    } 

    public void removeDomDevice(String id)
    {
        org.semanticwb.domotic.model.DomDevice.ClassMgr.removeDomDevice(id, this);
    }
    public boolean hasDomDevice(String id)
    {
        return org.semanticwb.domotic.model.DomDevice.ClassMgr.hasDomDevice(id, this);
    }

    public org.semanticwb.domotic.model.DomDeviceType getDomDeviceType(String id)
    {
        return org.semanticwb.domotic.model.DomDeviceType.ClassMgr.getDomDeviceType(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.DomDeviceType> listDomDeviceTypes()
    {
        return org.semanticwb.domotic.model.DomDeviceType.ClassMgr.listDomDeviceTypes(this);
    }

    public org.semanticwb.domotic.model.DomDeviceType createDomDeviceType(String id)
    {
        return org.semanticwb.domotic.model.DomDeviceType.ClassMgr.createDomDeviceType(id,this);
    }

    public org.semanticwb.domotic.model.DomDeviceType createDomDeviceType()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_DomDeviceType);
        return org.semanticwb.domotic.model.DomDeviceType.ClassMgr.createDomDeviceType(String.valueOf(id),this);
    } 

    public void removeDomDeviceType(String id)
    {
        org.semanticwb.domotic.model.DomDeviceType.ClassMgr.removeDomDeviceType(id, this);
    }
    public boolean hasDomDeviceType(String id)
    {
        return org.semanticwb.domotic.model.DomDeviceType.ClassMgr.hasDomDeviceType(id, this);
    }

    public org.semanticwb.domotic.model.DomDeviceStat getDomDeviceStat(String id)
    {
        return org.semanticwb.domotic.model.DomDeviceStat.ClassMgr.getDomDeviceStat(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.DomDeviceStat> listDomDeviceStats()
    {
        return org.semanticwb.domotic.model.DomDeviceStat.ClassMgr.listDomDeviceStats(this);
    }

    public org.semanticwb.domotic.model.DomDeviceStat createDomDeviceStat(String id)
    {
        return org.semanticwb.domotic.model.DomDeviceStat.ClassMgr.createDomDeviceStat(id,this);
    }

    public org.semanticwb.domotic.model.DomDeviceStat createDomDeviceStat()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_DomDeviceStat);
        return org.semanticwb.domotic.model.DomDeviceStat.ClassMgr.createDomDeviceStat(String.valueOf(id),this);
    } 

    public void removeDomDeviceStat(String id)
    {
        org.semanticwb.domotic.model.DomDeviceStat.ClassMgr.removeDomDeviceStat(id, this);
    }
    public boolean hasDomDeviceStat(String id)
    {
        return org.semanticwb.domotic.model.DomDeviceStat.ClassMgr.hasDomDeviceStat(id, this);
    }
}
