package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class UserBase extends GenericObjectBase implements Roleable,Traceable,Localeable,Groupable,Activeable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public UserBase(SemanticObject base)
    {
        super(base);
    }

    public Date getCreated()
    {
        return getSemanticObject().getDateProperty(vocabulary.created);
    }

    public void setCreated(Date created)
    {
        getSemanticObject().setDateProperty(vocabulary.created, created);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(vocabulary.active, active);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().addObjectProperty(vocabulary.modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(vocabulary.modifiedBy);
    }

    public User getModifiedBy()
    {
         User ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.modifiedBy.getRDFProperty());
         GenericIterator<org.semanticwb.model.User> it=new GenericIterator<org.semanticwb.model.User>(User.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public String getUsrSecondLastName()
    {
        return getSemanticObject().getProperty(vocabulary.usrSecondLastName);
    }

    public void setUsrSecondLastName(String usrSecondLastName)
    {
        getSemanticObject().setProperty(vocabulary.usrSecondLastName, usrSecondLastName);
    }

    public String getUsrEmail()
    {
        return getSemanticObject().getProperty(vocabulary.usrEmail);
    }

    public void setUsrEmail(String usrEmail)
    {
        getSemanticObject().setProperty(vocabulary.usrEmail, usrEmail);
    }

    public String getUsrFirstName()
    {
        return getSemanticObject().getProperty(vocabulary.usrFirstName);
    }

    public void setUsrFirstName(String usrFirstName)
    {
        getSemanticObject().setProperty(vocabulary.usrFirstName, usrFirstName);
    }

    public Date getUsrLastLogin()
    {
        return getSemanticObject().getDateProperty(vocabulary.usrLastLogin);
    }

    public void setUsrLastLogin(Date usrLastLogin)
    {
        getSemanticObject().setDateProperty(vocabulary.usrLastLogin, usrLastLogin);
    }

    public void setLanguage(org.semanticwb.model.Language language)
    {
        getSemanticObject().addObjectProperty(vocabulary.language, language.getSemanticObject());
    }

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(vocabulary.language);
    }

    public Language getLanguage()
    {
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.language);
         return (Language)vocabulary.Language.newGenericInstance(obj);
//         
//         Language ret=null;
//         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.language.getRDFProperty());
//         GenericIterator<org.semanticwb.model.Language> it=new GenericIterator<org.semanticwb.model.Language>(Language.class, stit);
//         if(it.hasNext())
//         {
//             ret=it.next();
//         }
//         return ret;
    }

    public Date getUpdated()
    {
        return getSemanticObject().getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        getSemanticObject().setDateProperty(vocabulary.updated, updated);
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().addObjectProperty(vocabulary.creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(vocabulary.creator);
    }

    public User getCreator()
    {
         User ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.creator.getRDFProperty());
         GenericIterator<org.semanticwb.model.User> it=new GenericIterator<org.semanticwb.model.User>(User.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public String getUsrLogin()
    {
        return getSemanticObject().getProperty(vocabulary.usrLogin);
    }

    public void setUsrLogin(String usrLogin)
    {
        getSemanticObject().setProperty(vocabulary.usrLogin, usrLogin);
    }

    public String getUsrPassword()
    {
        return getSemanticObject().getProperty(vocabulary.usrPassword);
    }

    public void setUsrPassword(String usrPassword)
    {
        getSemanticObject().setProperty(vocabulary.usrPassword, usrPassword);
    }

    public GenericIterator<org.semanticwb.model.Role> listRole()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasRole.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.Role>(org.semanticwb.model.Role.class, stit);
    }

    public void addRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasRole, role.getSemanticObject());
    }

    public void removeAllRole()
    {
        getSemanticObject().removeProperty(vocabulary.hasRole);
    }

    public void removeRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasRole,role.getSemanticObject());
    }

    public Role getRole()
    {
         Role ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasRole.getRDFProperty());
         GenericIterator<org.semanticwb.model.Role> it=new GenericIterator<org.semanticwb.model.Role>(Role.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public String getUsrLastName()
    {
        return getSemanticObject().getProperty(vocabulary.usrLastName);
    }

    public void setUsrLastName(String usrLastName)
    {
        getSemanticObject().setProperty(vocabulary.usrLastName, usrLastName);
    }

    public Date getUsrPasswordChanged()
    {
        return getSemanticObject().getDateProperty(vocabulary.usrPasswordChanged);
    }

    public void setUsrPasswordChanged(Date usrPasswordChanged)
    {
        getSemanticObject().setDateProperty(vocabulary.usrPasswordChanged, usrPasswordChanged);
    }

    public int getUsrSecurityQuestion()
    {
        return getSemanticObject().getIntProperty(vocabulary.usrSecurityQuestion);
    }

    public void setUsrSecurityQuestion(int usrSecurityQuestion)
    {
        getSemanticObject().setLongProperty(vocabulary.usrSecurityQuestion, usrSecurityQuestion);
    }

    public GenericIterator<org.semanticwb.model.ObjectGroup> listGroup()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasGroup.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.ObjectGroup>(org.semanticwb.model.ObjectGroup.class, stit);
    }

    public void addGroup(org.semanticwb.model.ObjectGroup objectgroup)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasGroup, objectgroup.getSemanticObject());
    }

    public void removeAllGroup()
    {
        getSemanticObject().removeProperty(vocabulary.hasGroup);
    }

    public void removeGroup(org.semanticwb.model.ObjectGroup objectgroup)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasGroup,objectgroup.getSemanticObject());
    }

    public ObjectGroup getGroup()
    {
         ObjectGroup ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasGroup.getRDFProperty());
         GenericIterator<org.semanticwb.model.ObjectGroup> it=new GenericIterator<org.semanticwb.model.ObjectGroup>(ObjectGroup.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public String getUsrSecurityAnswer()
    {
        return getSemanticObject().getProperty(vocabulary.usrSecurityAnswer);
    }

    public void setUsrSecurityAnswer(String usrSecurityAnswer)
    {
        getSemanticObject().setProperty(vocabulary.usrSecurityAnswer, usrSecurityAnswer);
    }

    public UserRepository getUserRepository()
    {
        return new UserRepository(getSemanticObject().getModel().getModelObject());
    }
}
