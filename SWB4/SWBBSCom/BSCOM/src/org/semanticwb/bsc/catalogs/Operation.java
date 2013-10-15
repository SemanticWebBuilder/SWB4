package org.semanticwb.bsc.catalogs;

/*
 * Una operación es usada para definir una regla de evaluación. Evaluar la regla
 * implica evaluar la operación con los valores que se especifican en la regla.
 * Los operadores incluyen operadores lógicos y relacionales principalmente.
 */
public abstract class Operation extends org.semanticwb.bsc.catalogs.base.OperationBase 
{
    protected String formattPattern;
    protected String separator;
    protected String term;
    
    public Operation(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public abstract boolean evaluate(final double value1, final double value2);
    
    public abstract boolean evaluate(final String factor, final double value1, final double value2);
    
    public void parseFactor() {
    }

    /**
     * @return the formattPattern
     */
    public String getFormattPattern() {
        return formattPattern;
    }

    /**
     * @param formattPattern the formattPattern to set
     */
    public void setFormattPattern(String formattPattern) {
        this.formattPattern = formattPattern;
    }

    /**
     * @return the separator
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * @param separator the separator to set
     */
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * @return the term
     */
    public String getTerm() {
        return term;
    }

    /**
     * @param term the term to set
     */
    public void setTerm(String term) {
        this.term = term;
    }
    
}
