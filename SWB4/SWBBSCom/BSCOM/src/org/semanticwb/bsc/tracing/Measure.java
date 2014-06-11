package org.semanticwb.bsc.tracing;

import java.util.Iterator;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;


public class Measure extends org.semanticwb.bsc.tracing.base.MeasureBase 
{
    static {
        bsc_value.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
                if("SET".equalsIgnoreCase(action)) {
                    Measure m = (Measure)obj.getGenericInstance();
                    Period p = m.getEvaluation().getPeriod();
                    Iterator<Series> serieses = m.getSeries().getSm().listValidSerieses().iterator();
                    while(serieses.hasNext()) {
                        Series s = serieses.next();
                        if(s.equals(m.getSeries())) {
                            continue;
                        }
                        Measure measure = s.getMeasure(p);
                        if(measure!=null) {
                            measure.evaluate();
                        }
                    }
                }
            }
        });
    }
    
    public Measure(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public void evaluate() {
        getEvaluation().setStatus(     getSeries().getSm().getMinimumState()     );
        Iterator<EvaluationRule> rules = getSeries().listValidEvaluationRules(false).iterator();
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
