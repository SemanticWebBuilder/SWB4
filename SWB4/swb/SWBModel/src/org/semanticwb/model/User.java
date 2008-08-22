package org.semanticwb.model;

import java.security.Principal;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

public class User extends UserBase implements Principal
{
    public User(SemanticObject base)
    {
        super(base);
    }

    public String getName() {
        return getUsrLogin();
    }
}
