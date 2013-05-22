package org.semanticwb.bsc.element;

/*
 * Clase que persiste informaci&oacute;n de un indicador. Un indicador permite informar sobre el avance para alcanzar un objetivo.
 */
public class Indicator extends org.semanticwb.bsc.element.base.IndicatorBase 
{
   
    public Indicator(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /**
     * Obtiene el prefijo para identificar un indicador. 
     * En caso de que el prefijo sea nulo, asigna un prefijo en autom&aacute;tico. 
     * @return {@code String} Regresa un string con el prefijo asociado al indicador, 
     * si el prefijo es asignado en autom&aacute;tico utiliza el prefijo del objetivo
     * seguido de un n&uacute;mero consecutivo asignado por un motor.
     * <p>
     * Ejemplo:
     * </p> 
     * <p>
     * El motor asigna un n&uacute;mero 1 para el Objetivo F1, se obtendr&iacute;a el siguiente prefijo: 
     *  {@literal  F1.1 }
     * </p>
     */
    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if(prefix==null) {
            try {
                prefix = getObjective().getPrefix().trim() + "." + getObjective().getSerial();
            }catch(Exception e) {
                prefix = getObjective().getTitle().substring(0, 1).toUpperCase() + "." + getObjective().getSerial();
            }
            setPrefix(prefix);
        }
        return prefix;
    }

    /**
     * Asigna un prefijo al indicador
     * @param value 
     */
    @Override
    public void setPrefix(String value) {
        super.setPrefix(value);
    }
    
}
