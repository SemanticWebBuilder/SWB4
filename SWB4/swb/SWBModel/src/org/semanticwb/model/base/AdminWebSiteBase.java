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
    * Objeto que define un Sitio Web de Administración.
    */
public abstract class AdminWebSiteBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.FilterableClass,org.semanticwb.model.Activeable,org.semanticwb.model.Indexable,org.semanticwb.model.Trashable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Localeable,org.semanticwb.model.FilterableNode,org.semanticwb.model.OntologyDepable,org.semanticwb.model.Filterable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Traceable
{
   
   /** Es una pagina web utilizada para mostrar opciones del menu dentro de la administración de SWB. */
    public static final org.semanticwb.platform.SemanticClass swbxf_MenuItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#MenuItem");
   
   /** Es una pagina web utilizada para mostrar comportamientos (tabs) dentro de la administración de SWB. */
    public static final org.semanticwb.platform.SemanticClass swbxf_ObjectBehavior=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectBehavior");
   
   /** Objeto que define un Sitio Web de Administración. */
    public static final org.semanticwb.platform.SemanticClass swb_AdminWebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AdminWebSite");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AdminWebSite");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of AdminWebSite for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.AdminWebSite
        */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSites(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.AdminWebSite for all models
       * @return Iterator of org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSites()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.AdminWebSite
       * @param id Identifier for org.semanticwb.model.AdminWebSite
       * @return A org.semanticwb.model.AdminWebSite
       */
        public static org.semanticwb.model.AdminWebSite getAdminWebSite(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.model.AdminWebSite ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    ret=(org.semanticwb.model.AdminWebSite)obj.createGenericInstance();
                }
            }
            return ret;
        }
       
       /**
        * Create a org.semanticwb.model.AdminWebSite
        * 
        * @param id Identifier for org.semanticwb.model.AdminWebSite
        * @param namespace the namespace
        * @return A org.semanticwb.model.AdminWebSite
        */
        public static org.semanticwb.model.AdminWebSite createAdminWebSite(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.model.AdminWebSite)model.createGenericObject(model.getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.AdminWebSite
       * @param id Identifier for org.semanticwb.model.AdminWebSite
       */
        public static void removeAdminWebSite(String id)
        {
            org.semanticwb.model.AdminWebSite obj=getAdminWebSite(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }
       /**
       * Returns true if exists a org.semanticwb.model.AdminWebSite
       * @param id Identifier for org.semanticwb.model.AdminWebSite
       * @return true if the org.semanticwb.model.AdminWebSite exists, false otherwise
       */

        public static boolean hasAdminWebSite(String id)
        {
            return (getAdminWebSite(id)!=null);
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.model.AdminWebSite
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.model.AdminWebSite
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteBySubModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteBySubModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.AdminWebSite
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @param model Model of the org.semanticwb.model.AdminWebSite
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @param model Model of the org.semanticwb.model.AdminWebSite
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByUserRepository(org.semanticwb.model.UserRepository value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByUserRepository(org.semanticwb.model.UserRepository value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @param model Model of the org.semanticwb.model.AdminWebSite
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByOntology(org.semanticwb.model.Ontology value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByOntology(org.semanticwb.model.Ontology value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.AdminWebSite
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.AdminWebSite
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByHomePage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_homePage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByHomePage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_homePage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @param model Model of the org.semanticwb.model.AdminWebSite
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByDefaultTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminWebSite with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @return Iterator with all the org.semanticwb.model.AdminWebSite
       */

        public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSiteByDefaultTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminWebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
    * Constructs a AdminWebSiteBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the AdminWebSite
    */
    public AdminWebSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the menu item.
     * 
     * @param id the id
     * @return the menu item
     */
    public org.semanticwb.model.MenuItem getMenuItem(String id)
    {
        return org.semanticwb.model.MenuItem.ClassMgr.getMenuItem(id, this);
    }

    /**
     * List menu items.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItems()
    {
        return org.semanticwb.model.MenuItem.ClassMgr.listMenuItems(this);
    }

    /**
     * Creates the menu item.
     * 
     * @param id the id
     * @return the org.semanticwb.model. menu item
     */
    public org.semanticwb.model.MenuItem createMenuItem(String id)
    {
        return org.semanticwb.model.MenuItem.ClassMgr.createMenuItem(id,this);
    }

    /**
     * Removes the menu item.
     * 
     * @param id the id
     */
    public void removeMenuItem(String id)
    {
        org.semanticwb.model.MenuItem.ClassMgr.removeMenuItem(id, this);
    }
    
    /**
     * Checks for menu item.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasMenuItem(String id)
    {
        return org.semanticwb.model.MenuItem.ClassMgr.hasMenuItem(id, this);
    }

    /**
     * Gets the object behavior.
     * 
     * @param id the id
     * @return the object behavior
     */
    public org.semanticwb.model.ObjectBehavior getObjectBehavior(String id)
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.getObjectBehavior(id, this);
    }

    /**
     * List object behaviors.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviors()
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.listObjectBehaviors(this);
    }

    /**
     * Creates the object behavior.
     * 
     * @param id the id
     * @return the org.semanticwb.model. object behavior
     */
    public org.semanticwb.model.ObjectBehavior createObjectBehavior(String id)
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.createObjectBehavior(id,this);
    }

    /**
     * Removes the object behavior.
     * 
     * @param id the id
     */
    public void removeObjectBehavior(String id)
    {
        org.semanticwb.model.ObjectBehavior.ClassMgr.removeObjectBehavior(id, this);
    }
    
    /**
     * Checks for object behavior.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasObjectBehavior(String id)
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.hasObjectBehavior(id, this);
    }
}
