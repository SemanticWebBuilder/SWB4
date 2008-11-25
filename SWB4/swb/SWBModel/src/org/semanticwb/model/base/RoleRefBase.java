package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class RoleRefBase extends Reference implements Activeable
{

    public RoleRefBase(SemanticObject base)
    {
        super(base);
    }

    public void setRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_role, role.getSemanticObject());
    }

    public void removeRole()
    {
        getSemanticObject().removeProperty(vocabulary.swb_role);
    }

    public Role getRole()
    {
         Role ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_role);
         if(obj!=null)
         {
             ret=(Role)vocabulary.swb_Role.newGenericInstance(obj);
         }
         return ret;
    }
}
