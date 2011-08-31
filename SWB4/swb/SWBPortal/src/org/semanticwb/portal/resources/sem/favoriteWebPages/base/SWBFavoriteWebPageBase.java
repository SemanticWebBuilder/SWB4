package org.semanticwb.portal.resources.sem.favoriteWebPages.base;


public abstract class SWBFavoriteWebPageBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Una Página Web es el elemento de SemanticWebBuilder a través del cual se estructura la información del portal.
   */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty fav_hasWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/favwebpages#hasWebPage");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty fav_user=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/favwebpages#user");
    public static final org.semanticwb.platform.SemanticClass fav_SWBFavoriteWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/favwebpages#SWBFavoriteWebPage");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/favwebpages#SWBFavoriteWebPage");

    public static class ClassMgr
    {
       /**
       * Returns a list of SWBFavoriteWebPage for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage> listSWBFavoriteWebPages(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage> listSWBFavoriteWebPages()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage createSWBFavoriteWebPage(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage.ClassMgr.createSWBFavoriteWebPage(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       * @param id Identifier for org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       * @param model Model of the org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       * @return A org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       */
        public static org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage getSWBFavoriteWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       * @param id Identifier for org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       * @param model Model of the org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       * @return A org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       */
        public static org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage createSWBFavoriteWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       * @param id Identifier for org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       * @param model Model of the org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       */
        public static void removeSWBFavoriteWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       * @param id Identifier for org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       * @param model Model of the org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       * @return true if the org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage exists, false otherwise
       */

        public static boolean hasSWBFavoriteWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSWBFavoriteWebPage(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage with a determined WebPage
       * @param value WebPage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       * @return Iterator with all the org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage> listSWBFavoriteWebPageByWebPage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(fav_hasWebPage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage with a determined WebPage
       * @param value WebPage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage> listSWBFavoriteWebPageByWebPage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(fav_hasWebPage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage with a determined User
       * @param value User of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       * @return Iterator with all the org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage> listSWBFavoriteWebPageByUser(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(fav_user, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage with a determined User
       * @param value User of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage> listSWBFavoriteWebPageByUser(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(fav_user,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a SWBFavoriteWebPageBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SWBFavoriteWebPage
   */
    public SWBFavoriteWebPageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.model.WebPage
   * @return A GenericIterator with all the org.semanticwb.model.WebPage
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> listWebPages()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(getSemanticObject().listObjectProperties(fav_hasWebPage));
    }

   /**
   * Gets true if has a WebPage
   * @param value org.semanticwb.model.WebPage to verify
   * @return true if the org.semanticwb.model.WebPage exists, false otherwise
   */
    public boolean hasWebPage(org.semanticwb.model.WebPage value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(fav_hasWebPage,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a WebPage
   * @param value org.semanticwb.model.WebPage to add
   */

    public void addWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().addObjectProperty(fav_hasWebPage, value.getSemanticObject());
    }
   /**
   * Removes all the WebPage
   */

    public void removeAllWebPage()
    {
        getSemanticObject().removeProperty(fav_hasWebPage);
    }
   /**
   * Removes a WebPage
   * @param value org.semanticwb.model.WebPage to remove
   */

    public void removeWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().removeObjectProperty(fav_hasWebPage,value.getSemanticObject());
    }

   /**
   * Gets the WebPage
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(fav_hasWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property User
   * @param value User to set
   */

    public void setUser(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(fav_user, value.getSemanticObject());
        }else
        {
            removeUser();
        }
    }
   /**
   * Remove the value for User property
   */

    public void removeUser()
    {
        getSemanticObject().removeProperty(fav_user);
    }

   /**
   * Gets the User
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getUser()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(fav_user);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
}
