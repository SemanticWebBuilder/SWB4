package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class RoleRefBase extends GenericObjectBase implements Activeable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public RoleRefBase(SemanticObject base)
    {
        super(base);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(vocabulary.active, active);
    }

    public void setRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().setObjectProperty(vocabulary.role, role.getSemanticObject());
    }

    public void removeRole()
    {
        getSemanticObject().removeProperty(vocabulary.role);
    }

    public Role getRole()
    {
         Role ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.role);
         if(obj!=null)
         {
             ret=(Role)vocabulary.Role.newGenericInstance(obj);
         }
         return ret;
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public Iterator<GenericObject> listRelatedObjects()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, null, getSemanticObject().getRDFResource());
        return new GenericIterator((SemanticClass)null, stit,true);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
