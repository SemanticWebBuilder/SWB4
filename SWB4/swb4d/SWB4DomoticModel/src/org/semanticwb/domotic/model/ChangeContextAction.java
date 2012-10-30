package org.semanticwb.domotic.model;


public class ChangeContextAction extends org.semanticwb.domotic.model.base.ChangeContextActionBase 
{
    public ChangeContextAction(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doActionImp()
    {
        System.out.println("ChangeContextAction:doAction");
        DomContext con=getChangeContext();
        con.setActive(true);
    }
    
    
}
