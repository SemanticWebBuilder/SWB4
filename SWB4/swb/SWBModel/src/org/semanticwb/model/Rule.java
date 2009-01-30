package org.semanticwb.model;

import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

public class Rule extends RuleBase 
{
    private static SWBRuleMgr rulemgr = null;

    public Rule(SemanticObject base)
    {
        super(base);
    }

    public static SWBRuleMgr getRuleMgr()
    {
        if(rulemgr==null)
        {
            rulemgr=new SWBRuleMgr();
            rulemgr.init();
        }
        return rulemgr;
    }
}
