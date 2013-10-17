package org.semanticwb.bsc.tracing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.catalogs.Operation;


public class EvaluationRule extends org.semanticwb.bsc.tracing.base.EvaluationRuleBase 
{
    public static final String Default_FORMAT_PATTERN = "(([\\*\\+-])(0|\\d*\\.?\\d+),)*(([\\*\\+-])(0|\\d*\\.?\\d+))";
    public static final String Default_SEPARATOR = ",";
    public static final String Default_TERM_PATTERN = "([\\*\\+-])(0|\\d*\\.?\\d+)";
    
    public EvaluationRule(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /*
     * Valida el formato del factor del criterio de evaluación
     * @param factor un {@code String} que indica los factores para comparar las series. Por ejemplo: *0.3,*05
     * @return Si el factor cumple con el formato entonces cierto, de lo contrario falso.
     */
    public boolean validateFactor(String factor) {
        Pattern pattern;
        try{
            pattern = Pattern.compile(Default_FORMAT_PATTERN);
        }catch(PatternSyntaxException pse) {
            return false;
        }            
        Matcher matcher = pattern.matcher(factor);
        return matcher.matches();
    }
    
    public Object[][] lexerFactor() {
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
    
    public boolean evaluate(Period period) {
        boolean res = false;
        if(Operation.ClassMgr.hasOperation(getOperationId(), getSeries().getIndicator().getBSC())) {
            Operation op = Operation.ClassMgr.getOperation(getOperationId(), getSeries().getIndicator().getBSC());
            double value1 = getSeries().getMeasureByPeriod(period).getValue();
            double value2 = getAnotherSeries().getMeasureByPeriod(period).getValue();
            Object[][] f = lexerFactor();
            if(f!=null) {
                if(f.length==1) {
                    try {
                        double coef = (Double)f[0][1];
                        res = op.evaluate(value1, coef*value2);
                    }catch(Exception e) {
                    }
                }else if(f.length==2) {
                    double coef1 = (Double)f[0][1];
                    double coef2 = (Double)f[1][1];
                    try {
                        res = op.evaluate(value1, coef1*value2, coef2*value2);
                    }catch(Exception e) {
                    }
                }
            }else {
                try {
                    res = op.evaluate(value1, value2);
                }catch(Exception e) {
                }
            }
        }
        return res;
    }
}
