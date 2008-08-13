package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class RoleRefBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public RoleRefBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public void setRole(org.semanticwb.model.Role role)
    {
        addObjectProperty(vocabulary.role, role);
    }

    public void removeRole()
    {
        removeProperty(vocabulary.role);
    }

    public Role getRole()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.role.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Role> it=new SemanticIterator<org.semanticwb.model.Role>(Role.class, stit);
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
