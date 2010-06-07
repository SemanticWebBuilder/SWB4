/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.Restriction;
import org.semanticwb.SWBPlatform;

/**
 *
 * @author javier.solis
 */
public class SemanticRestriction extends SemanticClass
{
    public SemanticRestriction(Restriction res)
    {
        super(res);
    }

    public boolean isAllValuesFromRestriction()
    {
       return ((Restriction)getOntClass()).isAllValuesFromRestriction();
    }

    public boolean isSomeValuesFromRestriction()
    {
       return ((Restriction)getOntClass()).isSomeValuesFromRestriction();
    }

    public boolean isCardinalityRestriction()
    {
       return ((Restriction)getOntClass()).isCardinalityRestriction();
    }

    public boolean isHasValueRestriction()
    {
       return ((Restriction)getOntClass()).isHasValueRestriction();
    }

    public boolean isMaxCardinalityRestriction()
    {
       return ((Restriction)getOntClass()).isMaxCardinalityRestriction();
    }

    public boolean isMinCardinalityRestriction()
    {
       return ((Restriction)getOntClass()).isMinCardinalityRestriction();
    }

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
