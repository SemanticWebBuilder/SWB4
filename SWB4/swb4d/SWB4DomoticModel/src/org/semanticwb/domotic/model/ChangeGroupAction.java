package org.semanticwb.domotic.model;


public class ChangeGroupAction extends org.semanticwb.domotic.model.base.ChangeGroupActionBase 
{
    public ChangeGroupAction(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public void doActionImp()
    {
        System.out.println("ChangeGroupAction:doAction");                
        DomGroup obj=getChangeGroup();
        obj.setStatus(getChangeGroupStat());
    }
    
}
