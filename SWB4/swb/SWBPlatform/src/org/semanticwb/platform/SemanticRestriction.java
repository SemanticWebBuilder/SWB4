/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.Restriction;
import org.semanticwb.SWBPlatform;

// TODO: Auto-generated Javadoc
/**
 * The Class SemanticRestriction.
 * 
 * @author javier.solis
 */
public class SemanticRestriction extends SemanticClass
{
    
    /**
     * Instantiates a new semantic restriction.
     * 
     * @param res the res
     */
    public SemanticRestriction(Restriction res)
    {
        super(res);
    }

    /**
     * Checks if is all values from restriction.
     * 
     * @return true, if is all values from restriction
     */
    public boolean isAllValuesFromRestriction()
    {
       return ((Restriction)getOntClass()).isAllValuesFromRestriction();
    }

    /**
     * Checks if is some values from restriction.
     * 
     * @return true, if is some values from restriction
     */
    public boolean isSomeValuesFromRestriction()
    {
       return ((Restriction)getOntClass()).isSomeValuesFromRestriction();
    }

    /**
     * Checks if is cardinality restriction.
     * 
     * @return true, if is cardinality restriction
     */
    public boolean isCardinalityRestriction()
    {
       return ((Restriction)getOntClass()).isCardinalityRestriction();
    }

    /**
     * Checks if is checks for value restriction.
     * 
     * @return true, if is checks for value restriction
     */
    public boolean isHasValueRestriction()
    {
       return ((Restriction)getOntClass()).isHasValueRestriction();
    }

    /**
     * Checks if is max cardinality restriction.
     * 
     * @return true, if is max cardinality restriction
     */
    public boolean isMaxCardinalityRestriction()
    {
       return ((Restriction)getOntClass()).isMaxCardinalityRestriction();
    }

    /**
     * Checks if is min cardinality restriction.
     * 
     * @return true, if is min cardinality restriction
     */
    public boolean isMinCardinalityRestriction()
    {
       return ((Restriction)getOntClass()).isMinCardinalityRestriction();
    }

    /**
     * Gets the restriction value.
     * 
     * @return the restriction value
     */
    public SemanticClass getRestrictionValue()
    {
        SemanticClass cls=null;
        Restriction res=((Restriction)getOntClass());
        if(res.isAllValuesFromRestriction())
        {
            cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(res.asAllValuesFromRestriction().getAllValuesFrom().getURI());
        }else if(res.isSomeValuesFromRestriction())
        {
            cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(res.asSomeValuesFromRestriction().getSomeValuesFrom().getURI());
        }
        return cls;
    }
}
