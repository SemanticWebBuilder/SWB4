package org.semanticwb.process.model;

import org.semanticwb.model.User;


public class ConnectionObject extends org.semanticwb.process.model.base.ConnectionObjectBase 
{
    public ConnectionObject(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void execute(FlowNodeInstance source, User user)
    {
        //Implementar en subclases
    }

}
