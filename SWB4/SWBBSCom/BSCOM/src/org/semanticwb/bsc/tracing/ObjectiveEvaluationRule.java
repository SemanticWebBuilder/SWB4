package org.semanticwb.bsc.tracing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.script.ScriptException;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.catalogs.Operation;
import static org.semanticwb.bsc.tracing.EvaluationRule.Default_FORMAT_PATTERN;
import static org.semanticwb.bsc.tracing.EvaluationRule.Default_SEPARATOR;
import static org.semanticwb.bsc.tracing.EvaluationRule.Default_TERM_PATTERN;


public class ObjectiveEvaluationRule extends org.semanticwb.bsc.tracing.base.ObjectiveEvaluationRuleBase 
{
    public ObjectiveEvaluationRule(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public String getFactor() {
        return super.getFactor();
    }

    @Override
    public void setFactor(String value) {
        super.setFactor(value);
    }
    
    /*
     * Valida el formato del factor del criterio de evaluación
     * @param factor un {@code String} que indica los factores para comparar las series. Por ejemplo: *0.3,*05
     * @return Si el factor cumple con el formato entonces cierto, de lo contrario falso.
     */
    @Override
    public boolean validateFactor(String factor) {
        if(factor==null) {
            return false;
        }
        if(factor.isEmpty()) {
            return true;
        }
        Pattern pattern;
        try{
            pattern = Pattern.compile(Default_FORMAT_PATTERN);
        }catch(PatternSyntaxException pse) {
            return false;
        }            
        Matcher matcher = pattern.matcher(factor);
        return matcher.matches();
    }
    
    private Object[][] lexerFactor() {
        if(getFactor()==null) {
            return null;
        }
        
        Object[][] tkns = null;
        Pattern term = Pattern.compile(Default_TERM_PATTERN);                
        Matcher matcher;
        String[] a = getFactor().split(Default_SEPARATOR);
        if(a.length>0)
        {
            tkns = new Object[a.length][2];
            for(int k=0; k<a.length; k++) {
                matcher = term.matcher(a[k]);
                if(matcher.find()) {
                    tkns[k][0] = matcher.group(1);
                    try {
                        tkns[k][1] = Double.parseDouble(matcher.group(2));
                    }catch(Exception e) {
                        tkns[k][1] = new Double(1.0);
                    }
                }
            }
        }
        return tkns;
    }
    
    @Override
    public boolean evaluate(Period period) {
        boolean res = false;
        if(Operation.ClassMgr.hasOperation(getOperationId(), getSeries().getIndicator().getBSC())) {
            Operation op = Operation.ClassMgr.getOperation(getOperationId(), getSeries().getIndicator().getBSC());
            double value1 = getSeries().getMeasure(period).getValue();
            double value2 = getAnotherSeries()==null?0:(getAnotherSeries().getMeasure(period)==null?0:getAnotherSeries().getMeasure(period).getValue());
            Object[][] f = lexerFactor();
            if(f!=null) {
                if(f.length==1) {
                    try {
                        double coef = (Double)f[0][1];
                        
                        res = op.evaluate(value1, coef*value2);
                    }catch(ScriptException | NoSuchMethodException e) {
                    }
                }else if(f.length==2) {
                    double coef1 = (Double)f[0][1];
                    double coef2 = (Double)f[1][1];
                    try {
                        res = op.evaluate(value1, coef1*value2, coef2*value2);
                    }catch(ScriptException | NoSuchMethodException e) {
                    }
                }
            }else {
                try {
                    res = op.evaluate(value1, value2);
                }catch(ScriptException | NoSuchMethodException e) {
                }
            }
        }
        return res;
    }
}
