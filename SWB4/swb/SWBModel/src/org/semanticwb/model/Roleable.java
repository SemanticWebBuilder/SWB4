package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Roleable extends GenericObject
{

    public GenericIterator<org.semanticwb.model.Role> listRoles();

    public void addRole(org.semanticwb.model.Role role);

    public void removeAllRole();

    public void removeRole(org.semanticwb.model.Role role);

    public Role getRole();
}
