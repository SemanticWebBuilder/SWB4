package org.semanticwb.bsc.element;


public class BSCElement extends org.semanticwb.bsc.element.base.BSCElementBase 
{
    public BSCElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setAndEvalRuleRef(boolean value) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAndEvalRuleRef() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    @Override
    public void setNotInheritRuleRef(boolean value) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isNotInheritRuleRef() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return false;
    }
}
