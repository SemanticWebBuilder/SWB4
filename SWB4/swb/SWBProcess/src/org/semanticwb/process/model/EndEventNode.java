package org.semanticwb.process.model;

import org.semanticwb.model.User;


   /**
   * Nodo Raiz de Eventos Finales 
   */
public class EndEventNode extends org.semanticwb.process.model.base.EndEventNodeBase 
{
    public EndEventNode(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        instance.close(user);
        instance.getParentInstance().close(user);
    }    
}
