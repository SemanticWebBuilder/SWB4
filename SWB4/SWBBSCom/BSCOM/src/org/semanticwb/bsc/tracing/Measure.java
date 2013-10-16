package org.semanticwb.bsc.tracing;

import java.util.Iterator;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.catalogs.Operation;


public class Measure extends org.semanticwb.bsc.tracing.base.MeasureBase 
{
    public Measure(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public void evaluate() {
        getSeries();
        getSeries().listEvaluationRules();
        getValue();
        getEvaluation();
        getEvaluation().getStatus();
        getEvaluation().getPeriod();
        
        String factor;
        State appraisal = getSeries().getIndicator().getMinimumState();
        Iterator<EvaluationRule> rules = getSeries().listValidEvaluationRules().iterator();
	while(rules.hasNext())
	{
		EvaluationRule rule = rules.next();
                if(Operation.ClassMgr.hasOperation(rule.getOperationId(), getSeries().getIndicator().getBSC())) {
                    Operation op = Operation.ClassMgr.getOperation(rule.getOperationId(), getSeries().getIndicator().getBSC());
                    factor = rule.getFactor();
                    
                }
//		boolean result = false;
//		if(rule.getOther()==null) {
//			result = op.evaluate(value);
//		}else {
//			result = op.evaluate(value, getOther().getMeasureValue());
//		}
//		if(result) {
//			state = rule.getState();
//			break;
//		}
	}
        getEvaluation().setStatus(appraisal);
    }
    
    
}
