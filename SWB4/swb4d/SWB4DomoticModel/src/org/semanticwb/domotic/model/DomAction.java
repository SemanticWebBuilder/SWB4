package org.semanticwb.domotic.model;


public class DomAction extends org.semanticwb.domotic.model.base.DomActionBase 
{
    public DomAction(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public final void doAction()
    {
        doActionImp();
    }
    
    public void doActionImp()
    {
        
    }
}
