package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface RoleRefable extends GenericObject
{

    public GenericIterator<org.semanticwb.model.RoleRef> listRoleRef();

    public void addRoleRef(org.semanticwb.model.RoleRef roleref);

    public void removeAllRoleRef();

    public void removeRoleRef(org.semanticwb.model.RoleRef roleref);

    public RoleRef getRoleRef();
}
