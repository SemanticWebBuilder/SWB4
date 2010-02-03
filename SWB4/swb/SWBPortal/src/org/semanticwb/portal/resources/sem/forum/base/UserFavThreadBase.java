package org.semanticwb.portal.resources.sem.forum.base;


public abstract class UserFavThreadBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
       public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");
       public static final org.semanticwb.platform.SemanticProperty frm_ftThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#ftThread");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty frm_ftUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#ftUser");
       public static final org.semanticwb.platform.SemanticClass frm_UserFavThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#UserFavThread");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#UserFavThread");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreads(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreads()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread>(it, true);
       }

       public static org.semanticwb.portal.resources.sem.forum.UserFavThread createUserFavThread(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.portal.resources.sem.forum.UserFavThread.ClassMgr.createUserFavThread(String.valueOf(id), model);
       }

       public static org.semanticwb.portal.resources.sem.forum.UserFavThread getUserFavThread(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.resources.sem.forum.UserFavThread)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.portal.resources.sem.forum.UserFavThread createUserFavThread(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.resources.sem.forum.UserFavThread)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeUserFavThread(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasUserFavThread(String id, org.semanticwb.model.SWBModel model)
       {
           return (getUserFavThread(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByThread(org.semanticwb.portal.resources.sem.forum.Thread ftthread,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(frm_ftThread, ftthread.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByThread(org.semanticwb.portal.resources.sem.forum.Thread ftthread)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(ftthread.getSemanticObject().getModel().listSubjects(frm_ftThread,ftthread.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByUser(org.semanticwb.model.User ftuser,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(frm_ftUser, ftuser.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByUser(org.semanticwb.model.User ftuser)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(ftuser.getSemanticObject().getModel().listSubjects(frm_ftUser,ftuser.getSemanticObject()));
       return it;
   }
    }

    public UserFavThreadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }


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

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }


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

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    public void setThread(org.semanticwb.portal.resources.sem.forum.Thread value)
    {
        getSemanticObject().setObjectProperty(frm_ftThread, value.getSemanticObject());
    }

    public void removeThread()
    {
        getSemanticObject().removeProperty(frm_ftThread);
    }


    public org.semanticwb.portal.resources.sem.forum.Thread getThread()
    {
         org.semanticwb.portal.resources.sem.forum.Thread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_ftThread);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Thread)obj.createGenericInstance();
         }
         return ret;
    }

    public void setUser(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(frm_ftUser, value.getSemanticObject());
    }

    public void removeUser()
    {
        getSemanticObject().removeProperty(frm_ftUser);
    }


    public org.semanticwb.model.User getUser()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_ftUser);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
}
