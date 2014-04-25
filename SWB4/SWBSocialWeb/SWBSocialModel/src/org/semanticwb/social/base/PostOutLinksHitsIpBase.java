package org.semanticwb.social.base;


   /**
   * Clase en la que se guardan las ips de los usuarios que le dan click en una liga de un postOut, talvez despues estas ips se pueda inferir su localización y mostrar en un mapa de google. 
   */
public abstract class PostOutLinksHitsIpBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
   /**
   * Ip desde donde se generó el click a la liga
   */
    public static final org.semanticwb.platform.SemanticProperty social_userIP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#userIP");
   /**
   * Clase en la que se guardan las ips de los usuarios que le dan click en una liga de un postOut, talvez despues estas ips se pueda inferir su localización y mostrar en un mapa de google.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutLinksHitsIp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutLinksHitsIp");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutLinksHitsIp");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostOutLinksHitsIp for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostOutLinksHitsIp
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHitsIp> listPostOutLinksHitsIps(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHitsIp>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostOutLinksHitsIp for all models
       * @return Iterator of org.semanticwb.social.PostOutLinksHitsIp
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHitsIp> listPostOutLinksHitsIps()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHitsIp>(it, true);
        }

        public static org.semanticwb.social.PostOutLinksHitsIp createPostOutLinksHitsIp(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.PostOutLinksHitsIp.ClassMgr.createPostOutLinksHitsIp(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.PostOutLinksHitsIp
       * @param id Identifier for org.semanticwb.social.PostOutLinksHitsIp
       * @param model Model of the org.semanticwb.social.PostOutLinksHitsIp
       * @return A org.semanticwb.social.PostOutLinksHitsIp
       */
        public static org.semanticwb.social.PostOutLinksHitsIp getPostOutLinksHitsIp(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutLinksHitsIp)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostOutLinksHitsIp
       * @param id Identifier for org.semanticwb.social.PostOutLinksHitsIp
       * @param model Model of the org.semanticwb.social.PostOutLinksHitsIp
       * @return A org.semanticwb.social.PostOutLinksHitsIp
       */
        public static org.semanticwb.social.PostOutLinksHitsIp createPostOutLinksHitsIp(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutLinksHitsIp)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostOutLinksHitsIp
       * @param id Identifier for org.semanticwb.social.PostOutLinksHitsIp
       * @param model Model of the org.semanticwb.social.PostOutLinksHitsIp
       */
        public static void removePostOutLinksHitsIp(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostOutLinksHitsIp
       * @param id Identifier for org.semanticwb.social.PostOutLinksHitsIp
       * @param model Model of the org.semanticwb.social.PostOutLinksHitsIp
       * @return true if the org.semanticwb.social.PostOutLinksHitsIp exists, false otherwise
       */

        public static boolean hasPostOutLinksHitsIp(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostOutLinksHitsIp(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostOutLinksHitsIp with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostOutLinksHitsIp
       * @return Iterator with all the org.semanticwb.social.PostOutLinksHitsIp
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHitsIp> listPostOutLinksHitsIpByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHitsIp> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutLinksHitsIp with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostOutLinksHitsIp
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHitsIp> listPostOutLinksHitsIpByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHitsIp> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutLinksHitsIp with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostOutLinksHitsIp
       * @return Iterator with all the org.semanticwb.social.PostOutLinksHitsIp
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHitsIp> listPostOutLinksHitsIpByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHitsIp> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutLinksHitsIp with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostOutLinksHitsIp
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHitsIp> listPostOutLinksHitsIpByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHitsIp> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostOutLinksHitsIpBase.ClassMgr getPostOutLinksHitsIpClassMgr()
    {
        return new PostOutLinksHitsIpBase.ClassMgr();
    }

   /**
   * Constructs a PostOutLinksHitsIpBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostOutLinksHitsIp
   */
    public PostOutLinksHitsIpBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the UserIP property
* @return String with the UserIP
*/
    public String getUserIP()
    {
        return getSemanticObject().getProperty(social_userIP);
    }

/**
* Sets the UserIP property
* @param value long with the UserIP
*/
    public void setUserIP(String value)
    {
        getSemanticObject().setProperty(social_userIP, value);
    }
}
