package org.semanticwb.bsc.tracing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


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
}
