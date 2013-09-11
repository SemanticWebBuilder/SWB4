package org.semanticwb.bsc.element;


public class Initiative extends org.semanticwb.bsc.element.base.InitiativeBase 
{
    public Initiative(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /**
     * Genera un String que representa el nombre de la iniciativa antecedido por su prefijo
     * @param lang un String que indica el idoma en que se espera el valor devuelto
     * @return un String que representa el nombre de la iniciativa antecedido por su prefijo
     */
    public String renderInitiativeName(String lang) {
        
        StringBuilder value = new StringBuilder(64);
        /* TODO: Cuando se desarrolle por completo las iniciativas, se podra quitar este comentario
        if (this.getPrefix() != null) {
            value.append(this.getPrefix());
            value.append(" ");
        }*/
        value.append(this.getDisplayTitle(lang));
        return value.toString();
    }
}
