package org.semanticwb.bsc.catalogs;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/*
 * Una operación es usada para definir una regla de evaluación. Evaluar la regla
 * implica evaluar la operación con los valores que se especifican en la regla.
 * Los operadores incluyen operadores lógicos y relacionales principalmente.
 */
public class Operation extends org.semanticwb.bsc.catalogs.base.OperationBase 
{    
    public Operation(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public boolean evaluate(final double value1, final double value2) throws ScriptException, NoSuchMethodException {
        if(getScript()==null || getScript().trim().isEmpty()) {
            throw new ScriptException("no hay script");
        }
        Boolean res = false;
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine jsEngine = factory.getEngineByName("javascript");
        jsEngine.eval(getScript());
        Invocable invocableEngine = (Invocable) jsEngine;
        res = (Boolean)invocableEngine.invokeFunction("evaluate",value1,value2);
        return res;
    }
    
    public boolean evaluate(final double value1, final double value2, final double value3) throws ScriptException, NoSuchMethodException {
        if(getScript()==null || getScript().trim().isEmpty()) {
            throw new ScriptException("no hay script");
        }
        Boolean res = false;
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine jsEngine = factory.getEngineByName("javascript");
        jsEngine.eval(getScript());
        Invocable invocableEngine = (Invocable) jsEngine;
        res = (Boolean)invocableEngine.invokeFunction("evaluate",value1,value2,value3);
        return res;
    }
    
    public void parseFactor() {
    }
}
