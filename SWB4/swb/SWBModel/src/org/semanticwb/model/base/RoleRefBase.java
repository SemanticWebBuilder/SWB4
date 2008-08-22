package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class RoleRefBase extends GenericObjectBase 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public RoleRefBase(SemanticObject base)
    {
        super(base);
    }

    public void setRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().addObjectProperty(vocabulary.role, role.getSemanticObject());
    }

    public void removeRole()
    {
        getSemanticObject().removeProperty(vocabulary.role);
    }

    public Role getRole()
    {
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.role.getRDFProperty());
         GenericIterator<org.semanticwb.model.Role> it=new GenericIterator<org.semanticwb.model.Role>(Role.class, stit);
         return it.next();
    }

    public int getStatus()
    {
        return getSemanticObject().getIntProperty(vocabulary.status);
    }

    public void setStatus(int status)
    {
        getSemanticObject().setLongProperty(vocabulary.status, status);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
