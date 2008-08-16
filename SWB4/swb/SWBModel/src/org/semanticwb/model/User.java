package org.semanticwb.model;

import java.io.Serializable;
import java.security.Principal;
import org.semanticwb.model.base.*;

public class User extends UserBase implements Principal, Serializable
{
    public User(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }
}
