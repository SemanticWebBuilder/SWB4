package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface RoleRefable 
{

    public SemanticIterator<org.semanticwb.model.RoleRef> listRoleRef();

    public void addRoleRef(org.semanticwb.model.RoleRef roleref);

    public void removeAllRoleRef();

    public void removeRoleRef(org.semanticwb.model.RoleRef roleref);

    public RoleRef getRoleRef();
}
