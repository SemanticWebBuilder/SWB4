package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class RoleRefBase extends SemanticObject implements Roleable,Statusable
{
    public RoleRefBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
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
    public int getStatus()
    {
        return getIntProperty(Vocabulary.status);
    }
    public void setStatus(int status)
    {
        setLongProperty(Vocabulary.status, status);
    }
}
