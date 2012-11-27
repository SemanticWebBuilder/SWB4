/*
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
 */
package org.semanticwb.portal.resources.sem.favoriteWebPages.base;


public abstract class SWBFavoriteWebPageBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticProperty fav_subscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/favwebpages#subscription");
   /**
   * Una Página Web es el elemento de SemanticWebBuilder a través del cual se estructura la información del portal.
   */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty fav_favorite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/favwebpages#favorite");
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
       * Gets all org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage with a determined Favorite
       * @param value Favorite of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       * @return Iterator with all the org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage> listSWBFavoriteWebPageByFavorite(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(fav_favorite, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage with a determined Favorite
       * @param value Favorite of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage> listSWBFavoriteWebPageByFavorite(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.favoriteWebPages.SWBFavoriteWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(fav_favorite,value.getSemanticObject(),sclass));
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
* Gets the Subscription property
* @return java.util.Date with the Subscription
*/
    public java.util.Date getSubscription()
    {
        return getSemanticObject().getDateProperty(fav_subscription);
    }

/**
* Sets the Subscription property
* @param value long with the Subscription
*/
    public void setSubscription(java.util.Date value)
    {
        getSemanticObject().setDateProperty(fav_subscription, value);
    }
   /**
   * Sets the value for the property Favorite
   * @param value Favorite to set
   */

    public void setFavorite(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(fav_favorite, value.getSemanticObject());
        }else
        {
            removeFavorite();
        }
    }
   /**
   * Remove the value for Favorite property
   */

    public void removeFavorite()
    {
        getSemanticObject().removeProperty(fav_favorite);
    }

   /**
   * Gets the Favorite
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getFavorite()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(fav_favorite);
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
