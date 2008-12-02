package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.base.GenericObjectBase;
import org.semanticwb.model.SWBVocabulary;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class UserBase extends GenericObjectBase implements Roleable,Traceable,Activeable
{
    public static SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public UserBase(SemanticObject base)
    {
        super(base);
    }

    public Date getCreated()
    {
        return getSemanticObject().getDateProperty(vocabulary.swb_created);
    }

    public void setCreated(Date created)
    {
        getSemanticObject().setDateProperty(vocabulary.swb_created, created);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(vocabulary.swb_modifiedBy);
    }

    public User getModifiedBy()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_modifiedBy);
         if(obj!=null)
         {
             ret=(User)vocabulary.swb_User.newGenericInstance(obj);
         }
         return ret;
    }

    public String getUsrSecondLastName()
    {
        return getSemanticObject().getProperty(vocabulary.swb_usrSecondLastName);
    }

    public void setUsrSecondLastName(String usrSecondLastName)
    {
        getSemanticObject().setProperty(vocabulary.swb_usrSecondLastName, usrSecondLastName);
    }

    public String getUsrEmail()
    {
        return getSemanticObject().getProperty(vocabulary.swb_usrEmail);
    }

    public void setUsrEmail(String usrEmail)
    {
        getSemanticObject().setProperty(vocabulary.swb_usrEmail, usrEmail);
    }

    public void setGroup(org.semanticwb.model.UserGroup usergroup)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_userGroup, usergroup.getSemanticObject());
    }

    public void removeGroup()
    {
        getSemanticObject().removeProperty(vocabulary.swb_userGroup);
    }

    public UserGroup getGroup()
    {
         UserGroup ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_userGroup);
         if(obj!=null)
         {
             ret=(UserGroup)vocabulary.swb_UserGroup.newGenericInstance(obj);
         }
         return ret;
    }

    public Date getUpdated()
    {
        return getSemanticObject().getDateProperty(vocabulary.swb_updated);
    }

    public void setUpdated(Date updated)
    {
        getSemanticObject().setDateProperty(vocabulary.swb_updated, updated);
    }

    public String getUsrFirstName()
    {
        return getSemanticObject().getProperty(vocabulary.swb_usrFirstName);
    }

    public void setUsrFirstName(String usrFirstName)
    {
        getSemanticObject().setProperty(vocabulary.swb_usrFirstName, usrFirstName);
    }

    public String getLanguage()
    {
        return getSemanticObject().getProperty(vocabulary.swb_usrLanguage);
    }

    public void setLanguage(String usrLanguage)
    {
        getSemanticObject().setProperty(vocabulary.swb_usrLanguage, usrLanguage);
    }

    public Date getUsrPasswordChanged()
    {
        return getSemanticObject().getDateProperty(vocabulary.swb_usrPasswordChanged);
    }

    public void setUsrPasswordChanged(Date usrPasswordChanged)
    {
        getSemanticObject().setDateProperty(vocabulary.swb_usrPasswordChanged, usrPasswordChanged);
    }

    public String getUsrLastName()
    {
        return getSemanticObject().getProperty(vocabulary.swb_usrLastName);
    }

    public void setUsrLastName(String usrLastName)
    {
        getSemanticObject().setProperty(vocabulary.swb_usrLastName, usrLastName);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_active, active);
    }

    public Date getUsrLastLogin()
    {
        return getSemanticObject().getDateProperty(vocabulary.swb_usrLastLogin);
    }

    public void setUsrLastLogin(Date usrLastLogin)
    {
        getSemanticObject().setDateProperty(vocabulary.swb_usrLastLogin, usrLastLogin);
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(vocabulary.swb_creator);
    }

    public User getCreator()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_creator);
         if(obj!=null)
         {
             ret=(User)vocabulary.swb_User.newGenericInstance(obj);
         }
         return ret;
    }

    public String getUsrLogin()
    {
        return getSemanticObject().getProperty(vocabulary.swb_usrLogin);
    }

    public void setUsrLogin(String usrLogin)
    {
        getSemanticObject().setProperty(vocabulary.swb_usrLogin, usrLogin);
    }

    public String getUsrPassword()
    {
        return getSemanticObject().getProperty(vocabulary.swb_usrPassword);
    }

    public void setUsrPassword(String usrPassword)
    {
        getSemanticObject().setProperty(vocabulary.swb_usrPassword, usrPassword);
    }

    public GenericIterator<org.semanticwb.model.Role> listRoles()
    {
        return new GenericIterator<org.semanticwb.model.Role>(org.semanticwb.model.Role.class, getSemanticObject().listObjectProperties(vocabulary.swb_hasRole));    }

    public void addRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().addObjectProperty(vocabulary.swb_hasRole, role.getSemanticObject());
    }

    public void removeAllRole()
    {
        getSemanticObject().removeProperty(vocabulary.swb_hasRole);
    }

    public void removeRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().removeObjectProperty(vocabulary.swb_hasRole,role.getSemanticObject());
    }

    public Role getRole()
    {
         Role ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_hasRole);
         if(obj!=null)
         {
             ret=(Role)vocabulary.swb_Role.newGenericInstance(obj);
         }
         return ret;
    }

    public int getUsrSecurityQuestion()
    {
        return getSemanticObject().getIntProperty(vocabulary.swb_usrSecurityQuestion);
    }

    public void setUsrSecurityQuestion(int usrSecurityQuestion)
    {
        getSemanticObject().setLongProperty(vocabulary.swb_usrSecurityQuestion, usrSecurityQuestion);
    }

    public String getUsrSecurityAnswer()
    {
        return getSemanticObject().getProperty(vocabulary.swb_usrSecurityAnswer);
    }

    public void setUsrSecurityAnswer(String usrSecurityAnswer)
    {
        getSemanticObject().setProperty(vocabulary.swb_usrSecurityAnswer, usrSecurityAnswer);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public Iterator<GenericObject> listRelatedObjects()
    {
        return new GenericIterator((SemanticClass)null, getSemanticObject().listRelatedObjects(),true);
    }

    public UserRepository getUserRepository()
    {
        return new UserRepository(getSemanticObject().getModel().getModelObject());
    }
}
