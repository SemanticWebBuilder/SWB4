package org.semanticwb.process.model;

import org.semanticwb.model.User;


   /**
   * Nodo Raiz de Eventos Iniciales 
   */
public class StartEventNode extends org.semanticwb.process.model.base.StartEventNodeBase 
{
    public StartEventNode(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        instance.close(user);
    }    
}
