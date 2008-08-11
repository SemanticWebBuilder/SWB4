package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class RoleRefableBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public RoleRefableBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public SemanticIterator<org.semanticwb.model.RoleRef> listRoleRef()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRoleRef.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.RoleRef>(org.semanticwb.model.RoleRef.class, stit);
    }

    public void addRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        addObjectProperty(vocabulary.hasRoleRef, roleref);
    }

    public void removeAllRoleRef()
    {
        removeProperty(vocabulary.hasRoleRef);
    }

    public void removeRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        removeObjectProperty(vocabulary.hasRoleRef,roleref);
    }

    public RoleRef getRoleRef()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRoleRef.getRDFProperty());
         SemanticIterator<org.semanticwb.model.RoleRef> it=new SemanticIterator<org.semanticwb.model.RoleRef>(RoleRef.class, stit);
         return it.next();
    }
}
