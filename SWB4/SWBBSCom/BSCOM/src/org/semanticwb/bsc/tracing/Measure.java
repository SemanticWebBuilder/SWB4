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
System.out.println("\n\nm="+m);
                    Period p = m.getEvaluation().getPeriod();
System.out.println("evaluation="+m.getEvaluation());
System.out.println("p="+p);
System.out.println("serie="+m.getSeries());
                    Iterator<Series> serieses = m.getSeries().getIndicator().listValidSerieses().iterator();
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
System.out.println("0...");
        getEvaluation().setStatus(getSeries().getIndicator().getMinimumState());
System.out.println("1...");
        Iterator<EvaluationRule> rules = getSeries().listValidEvaluationRules(false).iterator();
System.out.println("2...rules="+rules);
System.out.println("2.1...rules.hasNext()="+rules.hasNext());
	while(rules.hasNext())
	{
System.out.println("3...");
            EvaluationRule rule = rules.next();
System.out.println("rule="+rule);
System.out.println("getEvaluation().getPeriod()="+getEvaluation().getPeriod());
            if(rule.evaluate(getEvaluation().getPeriod())) {
System.out.println("4");
                getEvaluation().setStatus(rule.getAppraisal());
System.out.println("5...");
                break;
            }
	}
    }
    
    
}
