package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class UserBase extends SemanticObject implements Roleable,Groupable,Statusable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public UserBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public void setUserReposotory(org.semanticwb.model.UserRepository userrepository)
    {
        addObjectProperty(vocabulary.userReposotory, userrepository);
    }

    public void removeUserReposotory()
    {
        removeProperty(vocabulary.userReposotory);
    }

    public UserRepository getUserReposotory()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.userReposotory.getRDFProperty());
         SemanticIterator<org.semanticwb.model.UserRepository> it=new SemanticIterator<org.semanticwb.model.UserRepository>(UserRepository.class, stit);
         return it.next();
    }

    public SemanticIterator<org.semanticwb.model.Role> listRole()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRole.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Role>(org.semanticwb.model.Role.class, stit);
    }

    public void addRole(org.semanticwb.model.Role role)
    {
        addObjectProperty(vocabulary.hasRole, role);
    }

    public void removeAllRole()
    {
        removeProperty(vocabulary.hasRole);
    }

    public void removeRole(org.semanticwb.model.Role role)
    {
        removeObjectProperty(vocabulary.hasRole,role);
    }

    public Role getRole()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRole.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Role> it=new SemanticIterator<org.semanticwb.model.Role>(Role.class, stit);
         return it.next();
    }

    public SemanticIterator<org.semanticwb.model.ObjectGroup> listGroup()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasGroup.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.ObjectGroup>(org.semanticwb.model.ObjectGroup.class, stit);
    }

    public void addGroup(org.semanticwb.model.ObjectGroup objectgroup)
    {
        addObjectProperty(vocabulary.hasGroup, objectgroup);
    }

    public void removeAllGroup()
    {
        removeProperty(vocabulary.hasGroup);
    }

    public void removeGroup(org.semanticwb.model.ObjectGroup objectgroup)
    {
        removeObjectProperty(vocabulary.hasGroup,objectgroup);
    }

    public ObjectGroup getGroup()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasGroup.getRDFProperty());
         SemanticIterator<org.semanticwb.model.ObjectGroup> it=new SemanticIterator<org.semanticwb.model.ObjectGroup>(ObjectGroup.class, stit);
         return it.next();
    }

    public int getStatus()
    {
        return getIntProperty(vocabulary.status);
    }

    public void setStatus(int status)
    {
        setLongProperty(vocabulary.status, status);
    }
}
