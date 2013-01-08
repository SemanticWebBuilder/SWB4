package org.semanticwb.domotic.model.base;


public abstract class DomiticSiteBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Traceable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Indexable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Localeable,org.semanticwb.model.Countryable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Activeable,org.semanticwb.model.Filterable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Trashable,org.semanticwb.model.OntologyDepable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomPeriodRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomPeriodRef");
    public static final org.semanticwb.platform.SemanticClass swb4d_OnGroupChange=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnGroupChange");
    public static final org.semanticwb.platform.SemanticClass swb4d_SendMailAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#SendMailAction");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomItem");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomEvent");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomAccessLevel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomAccessLevel");
    public static final org.semanticwb.platform.SemanticClass swb4d_ChangeContextAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeContextAction");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomContext=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomContext");
    public static final org.semanticwb.platform.SemanticClass swb4d_ChangeGroupAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeGroupAction");
    public static final org.semanticwb.platform.SemanticClass swb4d_OnSchedule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnSchedule");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomRule");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomPermission=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomPermission");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomPeriod=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomPeriod");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomGroup");
    public static final org.semanticwb.platform.SemanticClass swb4d_SendSMSAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#SendSMSAction");
    public static final org.semanticwb.platform.SemanticClass swb4d_OnDeviceChange=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnDeviceChange");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomMacro=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomMacro");
    public static final org.semanticwb.platform.SemanticClass swb4d_SendTwitterAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#SendTwitterAction");
    public static final org.semanticwb.platform.SemanticClass swb4d_StartTimerAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#StartTimerAction");
    public static final org.semanticwb.platform.SemanticClass swb4d_OnSignal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnSignal");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomAction");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomDevice=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDevice");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomGateway");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomDeviceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDeviceType");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomDeviceStat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDeviceStat");
    public static final org.semanticwb.platform.SemanticClass swb4d_OnContextChange=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnContextChange");
    public static final org.semanticwb.platform.SemanticClass swb4d_ChangeDeviceAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeDeviceAction");
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

    public org.semanticwb.domotic.model.DomPeriodRef getDomPeriodRef(String id)
    {
        return org.semanticwb.domotic.model.DomPeriodRef.ClassMgr.getDomPeriodRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.DomPeriodRef> listDomPeriodRefs()
    {
        return org.semanticwb.domotic.model.DomPeriodRef.ClassMgr.listDomPeriodRefs(this);
    }

    public org.semanticwb.domotic.model.DomPeriodRef createDomPeriodRef(String id)
    {
        return org.semanticwb.domotic.model.DomPeriodRef.ClassMgr.createDomPeriodRef(id,this);
    }

    public org.semanticwb.domotic.model.DomPeriodRef createDomPeriodRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_DomPeriodRef);
        return org.semanticwb.domotic.model.DomPeriodRef.ClassMgr.createDomPeriodRef(String.valueOf(id),this);
    } 

    public void removeDomPeriodRef(String id)
    {
        org.semanticwb.domotic.model.DomPeriodRef.ClassMgr.removeDomPeriodRef(id, this);
    }
    public boolean hasDomPeriodRef(String id)
    {
        return org.semanticwb.domotic.model.DomPeriodRef.ClassMgr.hasDomPeriodRef(id, this);
    }

    public org.semanticwb.domotic.model.OnGroupChange getOnGroupChange(String id)
    {
        return org.semanticwb.domotic.model.OnGroupChange.ClassMgr.getOnGroupChange(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.OnGroupChange> listOnGroupChanges()
    {
        return org.semanticwb.domotic.model.OnGroupChange.ClassMgr.listOnGroupChanges(this);
    }

    public org.semanticwb.domotic.model.OnGroupChange createOnGroupChange(String id)
    {
        return org.semanticwb.domotic.model.OnGroupChange.ClassMgr.createOnGroupChange(id,this);
    }

    public org.semanticwb.domotic.model.OnGroupChange createOnGroupChange()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_OnGroupChange);
        return org.semanticwb.domotic.model.OnGroupChange.ClassMgr.createOnGroupChange(String.valueOf(id),this);
    } 

    public void removeOnGroupChange(String id)
    {
        org.semanticwb.domotic.model.OnGroupChange.ClassMgr.removeOnGroupChange(id, this);
    }
    public boolean hasOnGroupChange(String id)
    {
        return org.semanticwb.domotic.model.OnGroupChange.ClassMgr.hasOnGroupChange(id, this);
    }

    public org.semanticwb.domotic.model.SendMailAction getSendMailAction(String id)
    {
        return org.semanticwb.domotic.model.SendMailAction.ClassMgr.getSendMailAction(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.SendMailAction> listSendMailActions()
    {
        return org.semanticwb.domotic.model.SendMailAction.ClassMgr.listSendMailActions(this);
    }

    public org.semanticwb.domotic.model.SendMailAction createSendMailAction(String id)
    {
        return org.semanticwb.domotic.model.SendMailAction.ClassMgr.createSendMailAction(id,this);
    }

    public org.semanticwb.domotic.model.SendMailAction createSendMailAction()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_SendMailAction);
        return org.semanticwb.domotic.model.SendMailAction.ClassMgr.createSendMailAction(String.valueOf(id),this);
    } 

    public void removeSendMailAction(String id)
    {
        org.semanticwb.domotic.model.SendMailAction.ClassMgr.removeSendMailAction(id, this);
    }
    public boolean hasSendMailAction(String id)
    {
        return org.semanticwb.domotic.model.SendMailAction.ClassMgr.hasSendMailAction(id, this);
    }

    public org.semanticwb.domotic.model.DomItem getDomItem(String id)
    {
        return org.semanticwb.domotic.model.DomItem.ClassMgr.getDomItem(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.DomItem> listDomItems()
    {
        return org.semanticwb.domotic.model.DomItem.ClassMgr.listDomItems(this);
    }

    public org.semanticwb.domotic.model.DomItem createDomItem(String id)
    {
        return org.semanticwb.domotic.model.DomItem.ClassMgr.createDomItem(id,this);
    }

    public void removeDomItem(String id)
    {
        org.semanticwb.domotic.model.DomItem.ClassMgr.removeDomItem(id, this);
    }
    public boolean hasDomItem(String id)
    {
        return org.semanticwb.domotic.model.DomItem.ClassMgr.hasDomItem(id, this);
    }

    public org.semanticwb.domotic.model.DomEvent getDomEvent(String id)
    {
        return org.semanticwb.domotic.model.DomEvent.ClassMgr.getDomEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.DomEvent> listDomEvents()
    {
        return org.semanticwb.domotic.model.DomEvent.ClassMgr.listDomEvents(this);
    }

    public org.semanticwb.domotic.model.DomEvent createDomEvent(String id)
    {
        return org.semanticwb.domotic.model.DomEvent.ClassMgr.createDomEvent(id,this);
    }

    public org.semanticwb.domotic.model.DomEvent createDomEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_DomEvent);
        return org.semanticwb.domotic.model.DomEvent.ClassMgr.createDomEvent(String.valueOf(id),this);
    } 

    public void removeDomEvent(String id)
    {
        org.semanticwb.domotic.model.DomEvent.ClassMgr.removeDomEvent(id, this);
    }
    public boolean hasDomEvent(String id)
    {
        return org.semanticwb.domotic.model.DomEvent.ClassMgr.hasDomEvent(id, this);
    }

    public org.semanticwb.domotic.model.DomAccessLevel getDomAccessLevel(String id)
    {
        return org.semanticwb.domotic.model.DomAccessLevel.ClassMgr.getDomAccessLevel(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.DomAccessLevel> listDomAccessLevels()
    {
        return org.semanticwb.domotic.model.DomAccessLevel.ClassMgr.listDomAccessLevels(this);
    }

    public org.semanticwb.domotic.model.DomAccessLevel createDomAccessLevel(String id)
    {
        return org.semanticwb.domotic.model.DomAccessLevel.ClassMgr.createDomAccessLevel(id,this);
    }

    public void removeDomAccessLevel(String id)
    {
        org.semanticwb.domotic.model.DomAccessLevel.ClassMgr.removeDomAccessLevel(id, this);
    }
    public boolean hasDomAccessLevel(String id)
    {
        return org.semanticwb.domotic.model.DomAccessLevel.ClassMgr.hasDomAccessLevel(id, this);
    }

    public org.semanticwb.domotic.model.ChangeContextAction getChangeContextAction(String id)
    {
        return org.semanticwb.domotic.model.ChangeContextAction.ClassMgr.getChangeContextAction(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActions()
    {
        return org.semanticwb.domotic.model.ChangeContextAction.ClassMgr.listChangeContextActions(this);
    }

    public org.semanticwb.domotic.model.ChangeContextAction createChangeContextAction(String id)
    {
        return org.semanticwb.domotic.model.ChangeContextAction.ClassMgr.createChangeContextAction(id,this);
    }

    public org.semanticwb.domotic.model.ChangeContextAction createChangeContextAction()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_ChangeContextAction);
        return org.semanticwb.domotic.model.ChangeContextAction.ClassMgr.createChangeContextAction(String.valueOf(id),this);
    } 

    public void removeChangeContextAction(String id)
    {
        org.semanticwb.domotic.model.ChangeContextAction.ClassMgr.removeChangeContextAction(id, this);
    }
    public boolean hasChangeContextAction(String id)
    {
        return org.semanticwb.domotic.model.ChangeContextAction.ClassMgr.hasChangeContextAction(id, this);
    }

    public org.semanticwb.domotic.model.DomContext getDomContext(String id)
    {
        return org.semanticwb.domotic.model.DomContext.ClassMgr.getDomContext(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.DomContext> listDomContexts()
    {
        return org.semanticwb.domotic.model.DomContext.ClassMgr.listDomContexts(this);
    }

    public org.semanticwb.domotic.model.DomContext createDomContext(String id)
    {
        return org.semanticwb.domotic.model.DomContext.ClassMgr.createDomContext(id,this);
    }

    public org.semanticwb.domotic.model.DomContext createDomContext()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_DomContext);
        return org.semanticwb.domotic.model.DomContext.ClassMgr.createDomContext(String.valueOf(id),this);
    } 

    public void removeDomContext(String id)
    {
        org.semanticwb.domotic.model.DomContext.ClassMgr.removeDomContext(id, this);
    }
    public boolean hasDomContext(String id)
    {
        return org.semanticwb.domotic.model.DomContext.ClassMgr.hasDomContext(id, this);
    }

    public org.semanticwb.domotic.model.ChangeGroupAction getChangeGroupAction(String id)
    {
        return org.semanticwb.domotic.model.ChangeGroupAction.ClassMgr.getChangeGroupAction(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActions()
    {
        return org.semanticwb.domotic.model.ChangeGroupAction.ClassMgr.listChangeGroupActions(this);
    }

    public org.semanticwb.domotic.model.ChangeGroupAction createChangeGroupAction(String id)
    {
        return org.semanticwb.domotic.model.ChangeGroupAction.ClassMgr.createChangeGroupAction(id,this);
    }

    public org.semanticwb.domotic.model.ChangeGroupAction createChangeGroupAction()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_ChangeGroupAction);
        return org.semanticwb.domotic.model.ChangeGroupAction.ClassMgr.createChangeGroupAction(String.valueOf(id),this);
    } 

    public void removeChangeGroupAction(String id)
    {
        org.semanticwb.domotic.model.ChangeGroupAction.ClassMgr.removeChangeGroupAction(id, this);
    }
    public boolean hasChangeGroupAction(String id)
    {
        return org.semanticwb.domotic.model.ChangeGroupAction.ClassMgr.hasChangeGroupAction(id, this);
    }

    public org.semanticwb.domotic.model.OnSchedule getOnSchedule(String id)
    {
        return org.semanticwb.domotic.model.OnSchedule.ClassMgr.getOnSchedule(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.OnSchedule> listOnSchedules()
    {
        return org.semanticwb.domotic.model.OnSchedule.ClassMgr.listOnSchedules(this);
    }

    public org.semanticwb.domotic.model.OnSchedule createOnSchedule(String id)
    {
        return org.semanticwb.domotic.model.OnSchedule.ClassMgr.createOnSchedule(id,this);
    }

    public org.semanticwb.domotic.model.OnSchedule createOnSchedule()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_OnSchedule);
        return org.semanticwb.domotic.model.OnSchedule.ClassMgr.createOnSchedule(String.valueOf(id),this);
    } 

    public void removeOnSchedule(String id)
    {
        org.semanticwb.domotic.model.OnSchedule.ClassMgr.removeOnSchedule(id, this);
    }
    public boolean hasOnSchedule(String id)
    {
        return org.semanticwb.domotic.model.OnSchedule.ClassMgr.hasOnSchedule(id, this);
    }

    public org.semanticwb.domotic.model.DomRule getDomRule(String id)
    {
        return org.semanticwb.domotic.model.DomRule.ClassMgr.getDomRule(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.DomRule> listDomRules()
    {
        return org.semanticwb.domotic.model.DomRule.ClassMgr.listDomRules(this);
    }

    public org.semanticwb.domotic.model.DomRule createDomRule(String id)
    {
        return org.semanticwb.domotic.model.DomRule.ClassMgr.createDomRule(id,this);
    }

    public org.semanticwb.domotic.model.DomRule createDomRule()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_DomRule);
        return org.semanticwb.domotic.model.DomRule.ClassMgr.createDomRule(String.valueOf(id),this);
    } 

    public void removeDomRule(String id)
    {
        org.semanticwb.domotic.model.DomRule.ClassMgr.removeDomRule(id, this);
    }
    public boolean hasDomRule(String id)
    {
        return org.semanticwb.domotic.model.DomRule.ClassMgr.hasDomRule(id, this);
    }

    public org.semanticwb.domotic.model.DomPermission getDomPermission(String id)
    {
        return org.semanticwb.domotic.model.DomPermission.ClassMgr.getDomPermission(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.DomPermission> listDomPermissions()
    {
        return org.semanticwb.domotic.model.DomPermission.ClassMgr.listDomPermissions(this);
    }

    public org.semanticwb.domotic.model.DomPermission createDomPermission(String id)
    {
        return org.semanticwb.domotic.model.DomPermission.ClassMgr.createDomPermission(id,this);
    }

    public org.semanticwb.domotic.model.DomPermission createDomPermission()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_DomPermission);
        return org.semanticwb.domotic.model.DomPermission.ClassMgr.createDomPermission(String.valueOf(id),this);
    } 

    public void removeDomPermission(String id)
    {
        org.semanticwb.domotic.model.DomPermission.ClassMgr.removeDomPermission(id, this);
    }
    public boolean hasDomPermission(String id)
    {
        return org.semanticwb.domotic.model.DomPermission.ClassMgr.hasDomPermission(id, this);
    }

    public org.semanticwb.domotic.model.DomPeriod getDomPeriod(String id)
    {
        return org.semanticwb.domotic.model.DomPeriod.ClassMgr.getDomPeriod(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.DomPeriod> listDomPeriods()
    {
        return org.semanticwb.domotic.model.DomPeriod.ClassMgr.listDomPeriods(this);
    }

    public org.semanticwb.domotic.model.DomPeriod createDomPeriod(String id)
    {
        return org.semanticwb.domotic.model.DomPeriod.ClassMgr.createDomPeriod(id,this);
    }

    public org.semanticwb.domotic.model.DomPeriod createDomPeriod()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_DomPeriod);
        return org.semanticwb.domotic.model.DomPeriod.ClassMgr.createDomPeriod(String.valueOf(id),this);
    } 

    public void removeDomPeriod(String id)
    {
        org.semanticwb.domotic.model.DomPeriod.ClassMgr.removeDomPeriod(id, this);
    }
    public boolean hasDomPeriod(String id)
    {
        return org.semanticwb.domotic.model.DomPeriod.ClassMgr.hasDomPeriod(id, this);
    }

    public org.semanticwb.domotic.model.DomGroup getDomGroup(String id)
    {
        return org.semanticwb.domotic.model.DomGroup.ClassMgr.getDomGroup(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.DomGroup> listDomGroups()
    {
        return org.semanticwb.domotic.model.DomGroup.ClassMgr.listDomGroups(this);
    }

    public org.semanticwb.domotic.model.DomGroup createDomGroup(String id)
    {
        return org.semanticwb.domotic.model.DomGroup.ClassMgr.createDomGroup(id,this);
    }

    public org.semanticwb.domotic.model.DomGroup createDomGroup()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_DomGroup);
        return org.semanticwb.domotic.model.DomGroup.ClassMgr.createDomGroup(String.valueOf(id),this);
    } 

    public void removeDomGroup(String id)
    {
        org.semanticwb.domotic.model.DomGroup.ClassMgr.removeDomGroup(id, this);
    }
    public boolean hasDomGroup(String id)
    {
        return org.semanticwb.domotic.model.DomGroup.ClassMgr.hasDomGroup(id, this);
    }

    public org.semanticwb.domotic.model.SendSMSAction getSendSMSAction(String id)
    {
        return org.semanticwb.domotic.model.SendSMSAction.ClassMgr.getSendSMSAction(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.SendSMSAction> listSendSMSActions()
    {
        return org.semanticwb.domotic.model.SendSMSAction.ClassMgr.listSendSMSActions(this);
    }

    public org.semanticwb.domotic.model.SendSMSAction createSendSMSAction(String id)
    {
        return org.semanticwb.domotic.model.SendSMSAction.ClassMgr.createSendSMSAction(id,this);
    }

    public org.semanticwb.domotic.model.SendSMSAction createSendSMSAction()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_SendSMSAction);
        return org.semanticwb.domotic.model.SendSMSAction.ClassMgr.createSendSMSAction(String.valueOf(id),this);
    } 

    public void removeSendSMSAction(String id)
    {
        org.semanticwb.domotic.model.SendSMSAction.ClassMgr.removeSendSMSAction(id, this);
    }
    public boolean hasSendSMSAction(String id)
    {
        return org.semanticwb.domotic.model.SendSMSAction.ClassMgr.hasSendSMSAction(id, this);
    }

    public org.semanticwb.domotic.model.OnDeviceChange getOnDeviceChange(String id)
    {
        return org.semanticwb.domotic.model.OnDeviceChange.ClassMgr.getOnDeviceChange(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChanges()
    {
        return org.semanticwb.domotic.model.OnDeviceChange.ClassMgr.listOnDeviceChanges(this);
    }

    public org.semanticwb.domotic.model.OnDeviceChange createOnDeviceChange(String id)
    {
        return org.semanticwb.domotic.model.OnDeviceChange.ClassMgr.createOnDeviceChange(id,this);
    }

    public org.semanticwb.domotic.model.OnDeviceChange createOnDeviceChange()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_OnDeviceChange);
        return org.semanticwb.domotic.model.OnDeviceChange.ClassMgr.createOnDeviceChange(String.valueOf(id),this);
    } 

    public void removeOnDeviceChange(String id)
    {
        org.semanticwb.domotic.model.OnDeviceChange.ClassMgr.removeOnDeviceChange(id, this);
    }
    public boolean hasOnDeviceChange(String id)
    {
        return org.semanticwb.domotic.model.OnDeviceChange.ClassMgr.hasOnDeviceChange(id, this);
    }

    public org.semanticwb.domotic.model.DomMacro getDomMacro(String id)
    {
        return org.semanticwb.domotic.model.DomMacro.ClassMgr.getDomMacro(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.DomMacro> listDomMacros()
    {
        return org.semanticwb.domotic.model.DomMacro.ClassMgr.listDomMacros(this);
    }

    public org.semanticwb.domotic.model.DomMacro createDomMacro(String id)
    {
        return org.semanticwb.domotic.model.DomMacro.ClassMgr.createDomMacro(id,this);
    }

    public org.semanticwb.domotic.model.DomMacro createDomMacro()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_DomMacro);
        return org.semanticwb.domotic.model.DomMacro.ClassMgr.createDomMacro(String.valueOf(id),this);
    } 

    public void removeDomMacro(String id)
    {
        org.semanticwb.domotic.model.DomMacro.ClassMgr.removeDomMacro(id, this);
    }
    public boolean hasDomMacro(String id)
    {
        return org.semanticwb.domotic.model.DomMacro.ClassMgr.hasDomMacro(id, this);
    }

    public org.semanticwb.domotic.model.SendTwitterAction getSendTwitterAction(String id)
    {
        return org.semanticwb.domotic.model.SendTwitterAction.ClassMgr.getSendTwitterAction(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.SendTwitterAction> listSendTwitterActions()
    {
        return org.semanticwb.domotic.model.SendTwitterAction.ClassMgr.listSendTwitterActions(this);
    }

    public org.semanticwb.domotic.model.SendTwitterAction createSendTwitterAction(String id)
    {
        return org.semanticwb.domotic.model.SendTwitterAction.ClassMgr.createSendTwitterAction(id,this);
    }

    public org.semanticwb.domotic.model.SendTwitterAction createSendTwitterAction()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_SendTwitterAction);
        return org.semanticwb.domotic.model.SendTwitterAction.ClassMgr.createSendTwitterAction(String.valueOf(id),this);
    } 

    public void removeSendTwitterAction(String id)
    {
        org.semanticwb.domotic.model.SendTwitterAction.ClassMgr.removeSendTwitterAction(id, this);
    }
    public boolean hasSendTwitterAction(String id)
    {
        return org.semanticwb.domotic.model.SendTwitterAction.ClassMgr.hasSendTwitterAction(id, this);
    }

    public org.semanticwb.domotic.model.StartTimerAction getStartTimerAction(String id)
    {
        return org.semanticwb.domotic.model.StartTimerAction.ClassMgr.getStartTimerAction(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActions()
    {
        return org.semanticwb.domotic.model.StartTimerAction.ClassMgr.listStartTimerActions(this);
    }

    public org.semanticwb.domotic.model.StartTimerAction createStartTimerAction(String id)
    {
        return org.semanticwb.domotic.model.StartTimerAction.ClassMgr.createStartTimerAction(id,this);
    }

    public org.semanticwb.domotic.model.StartTimerAction createStartTimerAction()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_StartTimerAction);
        return org.semanticwb.domotic.model.StartTimerAction.ClassMgr.createStartTimerAction(String.valueOf(id),this);
    } 

    public void removeStartTimerAction(String id)
    {
        org.semanticwb.domotic.model.StartTimerAction.ClassMgr.removeStartTimerAction(id, this);
    }
    public boolean hasStartTimerAction(String id)
    {
        return org.semanticwb.domotic.model.StartTimerAction.ClassMgr.hasStartTimerAction(id, this);
    }

    public org.semanticwb.domotic.model.OnSignal getOnSignal(String id)
    {
        return org.semanticwb.domotic.model.OnSignal.ClassMgr.getOnSignal(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.OnSignal> listOnSignals()
    {
        return org.semanticwb.domotic.model.OnSignal.ClassMgr.listOnSignals(this);
    }

    public org.semanticwb.domotic.model.OnSignal createOnSignal(String id)
    {
        return org.semanticwb.domotic.model.OnSignal.ClassMgr.createOnSignal(id,this);
    }

    public org.semanticwb.domotic.model.OnSignal createOnSignal()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_OnSignal);
        return org.semanticwb.domotic.model.OnSignal.ClassMgr.createOnSignal(String.valueOf(id),this);
    } 

    public void removeOnSignal(String id)
    {
        org.semanticwb.domotic.model.OnSignal.ClassMgr.removeOnSignal(id, this);
    }
    public boolean hasOnSignal(String id)
    {
        return org.semanticwb.domotic.model.OnSignal.ClassMgr.hasOnSignal(id, this);
    }

    public org.semanticwb.domotic.model.DomAction getDomAction(String id)
    {
        return org.semanticwb.domotic.model.DomAction.ClassMgr.getDomAction(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.DomAction> listDomActions()
    {
        return org.semanticwb.domotic.model.DomAction.ClassMgr.listDomActions(this);
    }

    public org.semanticwb.domotic.model.DomAction createDomAction(String id)
    {
        return org.semanticwb.domotic.model.DomAction.ClassMgr.createDomAction(id,this);
    }

    public org.semanticwb.domotic.model.DomAction createDomAction()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_DomAction);
        return org.semanticwb.domotic.model.DomAction.ClassMgr.createDomAction(String.valueOf(id),this);
    } 

    public void removeDomAction(String id)
    {
        org.semanticwb.domotic.model.DomAction.ClassMgr.removeDomAction(id, this);
    }
    public boolean hasDomAction(String id)
    {
        return org.semanticwb.domotic.model.DomAction.ClassMgr.hasDomAction(id, this);
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

    public org.semanticwb.domotic.model.OnContextChange getOnContextChange(String id)
    {
        return org.semanticwb.domotic.model.OnContextChange.ClassMgr.getOnContextChange(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.OnContextChange> listOnContextChanges()
    {
        return org.semanticwb.domotic.model.OnContextChange.ClassMgr.listOnContextChanges(this);
    }

    public org.semanticwb.domotic.model.OnContextChange createOnContextChange(String id)
    {
        return org.semanticwb.domotic.model.OnContextChange.ClassMgr.createOnContextChange(id,this);
    }

    public org.semanticwb.domotic.model.OnContextChange createOnContextChange()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_OnContextChange);
        return org.semanticwb.domotic.model.OnContextChange.ClassMgr.createOnContextChange(String.valueOf(id),this);
    } 

    public void removeOnContextChange(String id)
    {
        org.semanticwb.domotic.model.OnContextChange.ClassMgr.removeOnContextChange(id, this);
    }
    public boolean hasOnContextChange(String id)
    {
        return org.semanticwb.domotic.model.OnContextChange.ClassMgr.hasOnContextChange(id, this);
    }

    public org.semanticwb.domotic.model.ChangeDeviceAction getChangeDeviceAction(String id)
    {
        return org.semanticwb.domotic.model.ChangeDeviceAction.ClassMgr.getChangeDeviceAction(id, this);
    }

    public java.util.Iterator<org.semanticwb.domotic.model.ChangeDeviceAction> listChangeDeviceActions()
    {
        return org.semanticwb.domotic.model.ChangeDeviceAction.ClassMgr.listChangeDeviceActions(this);
    }

    public org.semanticwb.domotic.model.ChangeDeviceAction createChangeDeviceAction(String id)
    {
        return org.semanticwb.domotic.model.ChangeDeviceAction.ClassMgr.createChangeDeviceAction(id,this);
    }

    public org.semanticwb.domotic.model.ChangeDeviceAction createChangeDeviceAction()
    {
        long id=getSemanticObject().getModel().getCounter(swb4d_ChangeDeviceAction);
        return org.semanticwb.domotic.model.ChangeDeviceAction.ClassMgr.createChangeDeviceAction(String.valueOf(id),this);
    } 

    public void removeChangeDeviceAction(String id)
    {
        org.semanticwb.domotic.model.ChangeDeviceAction.ClassMgr.removeChangeDeviceAction(id, this);
    }
    public boolean hasChangeDeviceAction(String id)
    {
        return org.semanticwb.domotic.model.ChangeDeviceAction.ClassMgr.hasChangeDeviceAction(id, this);
    }
}
