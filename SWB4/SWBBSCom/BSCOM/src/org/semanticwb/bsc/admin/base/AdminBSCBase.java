package org.semanticwb.bsc.admin.base;


   /**
   * Administración de BSC 
   */
public abstract class AdminBSCBase extends org.semanticwb.model.AdminWebSite implements org.semanticwb.model.Indexable,org.semanticwb.model.Trashable,org.semanticwb.model.Traceable,org.semanticwb.model.Undeleteable,org.semanticwb.model.FilterableClass,org.semanticwb.model.FilterableNode,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable,org.semanticwb.model.Filterable,org.semanticwb.model.OntologyDepable,org.semanticwb.model.Localeable,org.semanticwb.model.Countryable
{
   /**
   * Es una pagina web utilizada para mostrar comportamientos (tabs) dentro de la administración de SWB
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_ObjectBehavior=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectBehavior");
   /**
   * Define una Collección de objetos de una clase especificada con la propiedad "collectionClass"
   */
    public static final org.semanticwb.platform.SemanticClass swb_Collection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Collection");
   /**
   * Es una pagina web utilizada para mostrar opciones del menu dentro de la administración de SWB
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_MenuItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#MenuItem");
   /**
   * Administración de BSC
   */
    public static final org.semanticwb.platform.SemanticClass bsc_AdminBSC=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#AdminBSC");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#AdminBSC");

    public static class ClassMgr
    {
       /**
       * Returns a list of AdminBSC for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.admin.AdminBSC for all models
       * @return Iterator of org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.admin.AdminBSC
       * @param id Identifier for org.semanticwb.bsc.admin.AdminBSC
       * @return A org.semanticwb.bsc.admin.AdminBSC
       */
        public static org.semanticwb.bsc.admin.AdminBSC getAdminBSC(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.bsc.admin.AdminBSC ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    org.semanticwb.model.GenericObject gobj=obj.createGenericInstance();
                    if(gobj instanceof org.semanticwb.bsc.admin.AdminBSC)
                    {
                        ret=(org.semanticwb.bsc.admin.AdminBSC)gobj;
                    }
                }
            }
            return ret;
        }
       /**
       * Create a org.semanticwb.bsc.admin.AdminBSC
       * @param id Identifier for org.semanticwb.bsc.admin.AdminBSC
       * @return A org.semanticwb.bsc.admin.AdminBSC
       */
        public static org.semanticwb.bsc.admin.AdminBSC createAdminBSC(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.bsc.admin.AdminBSC)model.createGenericObject(model.getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.admin.AdminBSC
       * @param id Identifier for org.semanticwb.bsc.admin.AdminBSC
       */
        public static void removeAdminBSC(String id)
        {
            org.semanticwb.bsc.admin.AdminBSC obj=getAdminBSC(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.admin.AdminBSC
       * @param id Identifier for org.semanticwb.bsc.admin.AdminBSC
       * @return true if the org.semanticwb.bsc.admin.AdminBSC exists, false otherwise
       */

        public static boolean hasAdminBSC(String id)
        {
            return (getAdminBSC(id)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.admin.AdminBSC
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.bsc.admin.AdminBSC
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCBySubModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCBySubModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @param model Model of the org.semanticwb.bsc.admin.AdminBSC
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByDefaultTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByDefaultTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.bsc.admin.AdminBSC
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.bsc.admin.AdminBSC
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.admin.AdminBSC
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @param model Model of the org.semanticwb.bsc.admin.AdminBSC
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByOntology(org.semanticwb.model.Ontology value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByOntology(org.semanticwb.model.Ontology value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.bsc.admin.AdminBSC
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByHomePage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_homePage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByHomePage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_homePage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @param model Model of the org.semanticwb.bsc.admin.AdminBSC
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByUserRepository(org.semanticwb.model.UserRepository value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByUserRepository(org.semanticwb.model.UserRepository value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @param model Model of the org.semanticwb.bsc.admin.AdminBSC
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @param model Model of the org.semanticwb.bsc.admin.AdminBSC
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByModelProperty(org.semanticwb.model.ModelProperty value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.admin.AdminBSC with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @return Iterator with all the org.semanticwb.bsc.admin.AdminBSC
       */

        public static java.util.Iterator<org.semanticwb.bsc.admin.AdminBSC> listAdminBSCByModelProperty(org.semanticwb.model.ModelProperty value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.admin.AdminBSC> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static AdminBSCBase.ClassMgr getAdminBSCClassMgr()
    {
        return new AdminBSCBase.ClassMgr();
    }

   /**
   * Constructs a AdminBSCBase with a SemanticObject
   * @param base The SemanticObject with the properties for the AdminBSC
   */
    public AdminBSCBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.ObjectBehavior getObjectBehavior(String id)
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.getObjectBehavior(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviors()
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.listObjectBehaviors(this);
    }

    public org.semanticwb.model.ObjectBehavior createObjectBehavior(String id)
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.createObjectBehavior(id,this);
    }

    public void removeObjectBehavior(String id)
    {
        org.semanticwb.model.ObjectBehavior.ClassMgr.removeObjectBehavior(id, this);
    }
    public boolean hasObjectBehavior(String id)
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.hasObjectBehavior(id, this);
    }

    public org.semanticwb.model.Collection getCollection(String id)
    {
        return org.semanticwb.model.Collection.ClassMgr.getCollection(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Collection> listCollections()
    {
        return org.semanticwb.model.Collection.ClassMgr.listCollections(this);
    }

    public org.semanticwb.model.Collection createCollection(String id)
    {
        return org.semanticwb.model.Collection.ClassMgr.createCollection(id,this);
    }

    public org.semanticwb.model.Collection createCollection()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Collection);
        return org.semanticwb.model.Collection.ClassMgr.createCollection(String.valueOf(id),this);
    } 

    public void removeCollection(String id)
    {
        org.semanticwb.model.Collection.ClassMgr.removeCollection(id, this);
    }
    public boolean hasCollection(String id)
    {
        return org.semanticwb.model.Collection.ClassMgr.hasCollection(id, this);
    }

    public org.semanticwb.model.MenuItem getMenuItem(String id)
    {
        return org.semanticwb.model.MenuItem.ClassMgr.getMenuItem(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItems()
    {
        return org.semanticwb.model.MenuItem.ClassMgr.listMenuItems(this);
    }

    public org.semanticwb.model.MenuItem createMenuItem(String id)
    {
        return org.semanticwb.model.MenuItem.ClassMgr.createMenuItem(id,this);
    }

    public void removeMenuItem(String id)
    {
        org.semanticwb.model.MenuItem.ClassMgr.removeMenuItem(id, this);
    }
    public boolean hasMenuItem(String id)
    {
        return org.semanticwb.model.MenuItem.ClassMgr.hasMenuItem(id, this);
    }
}
