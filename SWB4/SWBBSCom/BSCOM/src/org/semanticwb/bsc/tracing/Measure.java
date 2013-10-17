package org.semanticwb.bsc.tracing;

import java.util.Iterator;


public class Measure extends org.semanticwb.bsc.tracing.base.MeasureBase 
{
    public Measure(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public void evaluate() {
        getEvaluation().setStatus(getSeries().getIndicator().getMinimumState());
        Iterator<EvaluationRule> rules = getSeries().listValidEvaluationRules().iterator();
	while(rules.hasNext())
	{
		EvaluationRule rule = rules.next();
                if(rule.evaluate(getEvaluation().getPeriod())) {
                    getEvaluation().setStatus(rule.getAppraisal());
                    break;
                }
	}
    }
    
    
}
