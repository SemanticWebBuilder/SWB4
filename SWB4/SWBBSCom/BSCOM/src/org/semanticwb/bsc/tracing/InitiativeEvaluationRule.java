package org.semanticwb.bsc.tracing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.semanticwb.bsc.accessory.Period;
import static org.semanticwb.bsc.tracing.EvaluationRule.Default_FORMAT_PATTERN;
import static org.semanticwb.bsc.tracing.EvaluationRule.Default_SEPARATOR;
import static org.semanticwb.bsc.tracing.EvaluationRule.Default_TERM_PATTERN;


public class InitiativeEvaluationRule extends org.semanticwb.bsc.tracing.base.InitiativeEvaluationRuleBase 
{
    public InitiativeEvaluationRule(org.semanticwb.platform.SemanticObject base)
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

    /**
     *
     * @param period
     * @return
     */
    @Override
    public boolean evaluate(Period period) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
}
