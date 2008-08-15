package org.semanticwb.model;

import java.security.Principal;
import org.semanticwb.model.base.*;

public class User extends UserBase implements Principal
{
    public User(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }
}
