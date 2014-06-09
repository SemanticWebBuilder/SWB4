package org.semanticwb.resources.sem.forumcat.base;


   /**
   * Almacena los puntos acumulados del usuario en el foro 
   */
public abstract class UserPointsBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticProperty forumCat_points=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#points");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
   /**
   * Usuario que recibe los puntos de acuerdo al sistema de puntaje
   */
    public static final org.semanticwb.platform.SemanticProperty forumCat_pointsUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#pointsUser");
    public static final org.semanticwb.platform.SemanticClass forumCat_SWBForumCatResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#SWBForumCatResource");
   /**
   * Almacena el foro en el cual se registraron los puntos del usuario
   */
    public static final org.semanticwb.platform.SemanticProperty forumCat_pointsForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#pointsForum");
   /**
   * Almacena los puntos acumulados del usuario en el foro
   */
    public static final org.semanticwb.platform.SemanticClass forumCat_UserPoints=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#UserPoints");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#UserPoints");

    public static class ClassMgr
    {
       /**
       * Returns a list of UserPoints for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.resources.sem.forumcat.UserPoints
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.UserPoints> listUserPointses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.UserPoints>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.resources.sem.forumcat.UserPoints for all models
       * @return Iterator of org.semanticwb.resources.sem.forumcat.UserPoints
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.UserPoints> listUserPointses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.UserPoints>(it, true);
        }

        public static org.semanticwb.resources.sem.forumcat.UserPoints createUserPoints(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.resources.sem.forumcat.UserPoints.ClassMgr.createUserPoints(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.resources.sem.forumcat.UserPoints
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.UserPoints
       * @param model Model of the org.semanticwb.resources.sem.forumcat.UserPoints
       * @return A org.semanticwb.resources.sem.forumcat.UserPoints
       */
        public static org.semanticwb.resources.sem.forumcat.UserPoints getUserPoints(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.UserPoints)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.resources.sem.forumcat.UserPoints
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.UserPoints
       * @param model Model of the org.semanticwb.resources.sem.forumcat.UserPoints
       * @return A org.semanticwb.resources.sem.forumcat.UserPoints
       */
        public static org.semanticwb.resources.sem.forumcat.UserPoints createUserPoints(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.UserPoints)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.resources.sem.forumcat.UserPoints
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.UserPoints
       * @param model Model of the org.semanticwb.resources.sem.forumcat.UserPoints
       */
        public static void removeUserPoints(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.resources.sem.forumcat.UserPoints
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.UserPoints
       * @param model Model of the org.semanticwb.resources.sem.forumcat.UserPoints
       * @return true if the org.semanticwb.resources.sem.forumcat.UserPoints exists, false otherwise
       */

        public static boolean hasUserPoints(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserPoints(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.UserPoints with a determined PointsUser
       * @param value PointsUser of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.resources.sem.forumcat.UserPoints
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.UserPoints
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.UserPoints> listUserPointsByPointsUser(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.UserPoints> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_pointsUser, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.UserPoints with a determined PointsUser
       * @param value PointsUser of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.UserPoints
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.UserPoints> listUserPointsByPointsUser(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.UserPoints> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_pointsUser,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.UserPoints with a determined PointsForum
       * @param value PointsForum of the type org.semanticwb.resources.sem.forumcat.SWBForumCatResource
       * @param model Model of the org.semanticwb.resources.sem.forumcat.UserPoints
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.UserPoints
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.UserPoints> listUserPointsByPointsForum(org.semanticwb.resources.sem.forumcat.SWBForumCatResource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.UserPoints> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_pointsForum, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.UserPoints with a determined PointsForum
       * @param value PointsForum of the type org.semanticwb.resources.sem.forumcat.SWBForumCatResource
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.UserPoints
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.UserPoints> listUserPointsByPointsForum(org.semanticwb.resources.sem.forumcat.SWBForumCatResource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.UserPoints> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_pointsForum,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static UserPointsBase.ClassMgr getUserPointsClassMgr()
    {
        return new UserPointsBase.ClassMgr();
    }

   /**
   * Constructs a UserPointsBase with a SemanticObject
   * @param base The SemanticObject with the properties for the UserPoints
   */
    public UserPointsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Points property
* @return int with the Points
*/
    public int getPoints()
    {
        return getSemanticObject().getIntProperty(forumCat_points);
    }

/**
* Sets the Points property
* @param value long with the Points
*/
    public void setPoints(int value)
    {
        getSemanticObject().setIntProperty(forumCat_points, value);
    }
   /**
   * Sets the value for the property PointsUser
   * @param value PointsUser to set
   */

    public void setPointsUser(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(forumCat_pointsUser, value.getSemanticObject());
        }else
        {
            removePointsUser();
        }
    }
   /**
   * Remove the value for PointsUser property
   */

    public void removePointsUser()
    {
        getSemanticObject().removeProperty(forumCat_pointsUser);
    }

   /**
   * Gets the PointsUser
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getPointsUser()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_pointsUser);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property PointsForum
   * @param value PointsForum to set
   */

    public void setPointsForum(org.semanticwb.resources.sem.forumcat.SWBForumCatResource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(forumCat_pointsForum, value.getSemanticObject());
        }else
        {
            removePointsForum();
        }
    }
   /**
   * Remove the value for PointsForum property
   */

    public void removePointsForum()
    {
        getSemanticObject().removeProperty(forumCat_pointsForum);
    }

   /**
   * Gets the PointsForum
   * @return a org.semanticwb.resources.sem.forumcat.SWBForumCatResource
   */
    public org.semanticwb.resources.sem.forumcat.SWBForumCatResource getPointsForum()
    {
         org.semanticwb.resources.sem.forumcat.SWBForumCatResource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_pointsForum);
         if(obj!=null)
         {
             ret=(org.semanticwb.resources.sem.forumcat.SWBForumCatResource)obj.createGenericInstance();
         }
         return ret;
    }
}
