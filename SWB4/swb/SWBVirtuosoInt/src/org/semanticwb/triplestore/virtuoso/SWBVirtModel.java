/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.triplestore.virtuoso;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;

/**
 *
 * @author javier.solis.g
 */
public class SWBVirtModel extends ModelCom 
{
    /**
     * @param base
     */
    public SWBVirtModel(SWBVirtGraph base) 
    {
        super(base);
    }
	
    @Override
    public Model removeAll() 
    {
	try {
	        SWBVirtGraph _graph=(SWBVirtGraph)this.graph;
	        _graph.clear();
	} catch (ClassCastException e) {
		super.removeAll();
	}
	return this;
    }


    public void createRuleSet(String ruleSetName, String uriGraphRuleSet) 
    {
        ((SWBVirtGraph)this.graph).createRuleSet(ruleSetName, uriGraphRuleSet);
    }


    public void removeRuleSet(String ruleSetName, String uriGraphRuleSet) 
    {
        ((SWBVirtGraph)this.graph).removeRuleSet(ruleSetName, uriGraphRuleSet);
    }

    public void setRuleSet(String _ruleSet)
    {
        ((SWBVirtGraph)this.graph).setRuleSet(_ruleSet);
    }

    public void setSameAs(boolean _sameAs)
    {
        ((SWBVirtGraph)this.graph).setSameAs(_sameAs);
    }
	
}