package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class UserBase extends SemanticObject implements Roleable,Groupable,Statusable
{
    public UserBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }
    public SemanticIterator<org.semanticwb.model.UserRepository> listUserRepository()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasUserReposotory.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.UserRepository>(org.semanticwb.model.UserRepository.class, stit);
    }
    public void addUserRepository(org.semanticwb.model.UserRepository userrepository)
    {
        addObjectProperty(Vocabulary.hasUserReposotory, userrepository);
    }
    public void removeAllUserRepository()
    {
        getRDFResource().removeAll(Vocabulary.hasUserReposotory.getRDFProperty());
    }
    public UserRepository getUserRepository()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasUserReposotory.getRDFProperty());
         SemanticIterator<org.semanticwb.model.UserRepository> it=new SemanticIterator<org.semanticwb.model.UserRepository>(UserRepository.class, stit);
         return it.next();
    }
    public SemanticIterator<org.semanticwb.model.Role> listRole()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasRole.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Role>(org.semanticwb.model.Role.class, stit);
    }
    public void addRole(org.semanticwb.model.Role role)
    {
        addObjectProperty(Vocabulary.hasRole, role);
    }
    public void removeAllRole()
    {
        getRDFResource().removeAll(Vocabulary.hasRole.getRDFProperty());
    }
    public Role getRole()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasRole.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Role> it=new SemanticIterator<org.semanticwb.model.Role>(Role.class, stit);
         return it.next();
    }
    public SemanticIterator<org.semanticwb.model.ObjectGroup> listObjectGroup()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasGroup.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.ObjectGroup>(org.semanticwb.model.ObjectGroup.class, stit);
    }
    public void addObjectGroup(org.semanticwb.model.ObjectGroup objectgroup)
    {
        addObjectProperty(Vocabulary.hasGroup, objectgroup);
    }
    public void removeAllObjectGroup()
    {
        getRDFResource().removeAll(Vocabulary.hasGroup.getRDFProperty());
    }
    public ObjectGroup getObjectGroup()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasGroup.getRDFProperty());
         SemanticIterator<org.semanticwb.model.ObjectGroup> it=new SemanticIterator<org.semanticwb.model.ObjectGroup>(ObjectGroup.class, stit);
         return it.next();
    }
    public int getStatus()
    {
        return getIntProperty(Vocabulary.status);
    }
    public void setStatus(int status)
    {
        setLongProperty(Vocabulary.status, status);
    }
}
