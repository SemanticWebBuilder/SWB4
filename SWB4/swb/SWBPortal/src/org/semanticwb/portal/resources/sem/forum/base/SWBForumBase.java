package org.semanticwb.portal.resources.sem.forum.base;


public abstract class SWBForumBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty frm_notifyThreadCreation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#notifyThreadCreation");
    public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");
    public static final org.semanticwb.platform.SemanticProperty frm_hasThreads=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasThreads");
    public static final org.semanticwb.platform.SemanticProperty frm_showThreadBody=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#showThreadBody");
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty frm_adminRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#adminRole");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty frm_acceptGuessUsers=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#acceptGuessUsers");
    public static final org.semanticwb.platform.SemanticProperty frm_jspView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#jspView");
    public static final org.semanticwb.platform.SemanticProperty frm_onlyAdminCreateThreads=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#onlyAdminCreateThreads");
    public static final org.semanticwb.platform.SemanticProperty frm_captcha=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#captcha");
    public static final org.semanticwb.platform.SemanticProperty frm_pagination=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#pagination");
    public static final org.semanticwb.platform.SemanticClass frm_SWBForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#SWBForum");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#SWBForum");

    public SWBForumBase()
    {
    }

   /**
   * Constructs a SWBForumBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SWBForum
   */
    public SWBForumBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() 
    {
        return getSemanticObject().hashCode();
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) 
    {
        if(obj==null)return false;
        return hashCode()==obj.hashCode();
    }

/**
* Gets the NotifyThreadCreation property
* @return boolean with the NotifyThreadCreation
*/
    public boolean isNotifyThreadCreation()
    {
        return getSemanticObject().getBooleanProperty(frm_notifyThreadCreation);
    }

/**
* Sets the NotifyThreadCreation property
* @param value long with the NotifyThreadCreation
*/
    public void setNotifyThreadCreation(boolean value)
    {
        getSemanticObject().setBooleanProperty(frm_notifyThreadCreation, value);
    }
   /**
   * Gets all the org.semanticwb.portal.resources.sem.forum.Thread
   * @return A GenericIterator with all the org.semanticwb.portal.resources.sem.forum.Thread
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread>(getSemanticObject().listObjectProperties(frm_hasThreads));
    }

   /**
   * Gets true if has a Threads
   * @param value org.semanticwb.portal.resources.sem.forum.Thread to verify
   * @return true if the org.semanticwb.portal.resources.sem.forum.Thread exists, false otherwise
   */
    public boolean hasThreads(org.semanticwb.portal.resources.sem.forum.Thread value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_hasThreads,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Threads
   * @return a org.semanticwb.portal.resources.sem.forum.Thread
   */
    public org.semanticwb.portal.resources.sem.forum.Thread getThreads()
    {
         org.semanticwb.portal.resources.sem.forum.Thread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasThreads);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Thread)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ShowThreadBody property
* @return boolean with the ShowThreadBody
*/
    public boolean isShowThreadBody()
    {
        return getSemanticObject().getBooleanProperty(frm_showThreadBody);
    }

/**
* Sets the ShowThreadBody property
* @param value long with the ShowThreadBody
*/
    public void setShowThreadBody(boolean value)
    {
        getSemanticObject().setBooleanProperty(frm_showThreadBody, value);
    }
   /**
   * Sets the value for the property AdminRole
   * @param value AdminRole to set
   */

    public void setAdminRole(org.semanticwb.model.Role value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(frm_adminRole, value.getSemanticObject());
        }else
        {
            removeAdminRole();
        }
    }
   /**
   * Remove the value for AdminRole property
   */

    public void removeAdminRole()
    {
        getSemanticObject().removeProperty(frm_adminRole);
    }

   /**
   * Gets the AdminRole
   * @return a org.semanticwb.model.Role
   */
    public org.semanticwb.model.Role getAdminRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_adminRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Resource
   * @param value Resource to set
   */

    public void setResource(org.semanticwb.model.Resource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_semanticResourceInv, value.getSemanticObject());
        }else
        {
            removeResource();
        }
    }
   /**
   * Remove the value for Resource property
   */

    public void removeResource()
    {
        getSemanticObject().removeProperty(swb_semanticResourceInv);
    }

   /**
   * Gets the Resource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_semanticResourceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the AcceptGuessUsers property
* @return boolean with the AcceptGuessUsers
*/
    public boolean isAcceptGuessUsers()
    {
        return getSemanticObject().getBooleanProperty(frm_acceptGuessUsers);
    }

/**
* Sets the AcceptGuessUsers property
* @param value long with the AcceptGuessUsers
*/
    public void setAcceptGuessUsers(boolean value)
    {
        getSemanticObject().setBooleanProperty(frm_acceptGuessUsers, value);
    }

/**
* Gets the JspView property
* @return String with the JspView
*/
    public String getJspView()
    {
        return getSemanticObject().getProperty(frm_jspView);
    }

/**
* Sets the JspView property
* @param value long with the JspView
*/
    public void setJspView(String value)
    {
        getSemanticObject().setProperty(frm_jspView, value);
    }

/**
* Gets the OnlyAdminCreateThreads property
* @return boolean with the OnlyAdminCreateThreads
*/
    public boolean isOnlyAdminCreateThreads()
    {
        return getSemanticObject().getBooleanProperty(frm_onlyAdminCreateThreads);
    }

/**
* Sets the OnlyAdminCreateThreads property
* @param value long with the OnlyAdminCreateThreads
*/
    public void setOnlyAdminCreateThreads(boolean value)
    {
        getSemanticObject().setBooleanProperty(frm_onlyAdminCreateThreads, value);
    }

/**
* Gets the Captcha property
* @return boolean with the Captcha
*/
    public boolean isCaptcha()
    {
        return getSemanticObject().getBooleanProperty(frm_captcha);
    }

/**
* Sets the Captcha property
* @param value long with the Captcha
*/
    public void setCaptcha(boolean value)
    {
        getSemanticObject().setBooleanProperty(frm_captcha, value);
    }

/**
* Gets the Pagination property
* @return int with the Pagination
*/
    public int getPagination()
    {
        return getSemanticObject().getIntProperty(frm_pagination);
    }

/**
* Sets the Pagination property
* @param value long with the Pagination
*/
    public void setPagination(int value)
    {
        getSemanticObject().setIntProperty(frm_pagination, value);
    }
}
