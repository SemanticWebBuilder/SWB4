package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface Roleable 
{

    public SemanticIterator<org.semanticwb.model.Role> listRole();

    public void addRole(org.semanticwb.model.Role role);

    public void removeAllRole();

    public void removeRole(org.semanticwb.model.Role role);

    public Role getRole();
}
