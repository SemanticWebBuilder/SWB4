package org.semanticwb.bsc.resources;


import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticProperty;


/**
 * Mantiene la estructura de criterios de b&uacute;squeda de informaci&oacute;n seleccionada por
 * el usuario para la extracci&oacute;n de datos que formar&aacute;n el reporte.
 * @author jose.jimenez
 */
public class ReportCriteria {
    
    
    /**
     * Indica el tipo de elementos a mostrar en el reporte: Objective, Indicator, Initiative, Deliverable
     */
    private String elementType;
    
    /**
     * Almacena el t&iacute;tulo del objeto a buscar
     */
    private String objectTitle;
    
    /**
     * Indica el per&iacute;odo seleccionado como inicio del intervalo de b&uacute;squeda de datos
     */
    private Period initialPeriod;
    
    /**
     * Indica el per&iacute;odo seleccionado como t&eacute;rmino del intervalo de b&uacute;squeda de datos
     */
    private Period finalPeriod;
    
    /**
     * Almacena el estado seleccionado como criterio de búsqueda para la extracción de datos.
     */
    private State status;
    
    /**
     * Almacena el usuario con rol de Champion seleccionado como criterio de b&uacute;squeda para la extracci&oacute;n de datos
     */
    private User champion;
    
    /**
     * Almacena el usuario con rol de Sponsor seleccionado como criterio de b&uacute;squeda para la extracci&oacute;n de datos
     */
    private User sponsor;
    
    /**
     * Almacena los tipos de elementos relacionados que se desea se incluyan en los resultados de extracci&oacute;n de datos del reporte
     */
    private String[] relatedElements;
    
    /**
     * Almacena las propiedades seleccionadas como estructura del listado de resultados
     */
    private SemanticProperty[] props2Show;

    /**
     * Obtiene el tipo de elementos a mostrar en el reporte
     * @return un {@literal String} indicando el tipo de elementos a mostrar en el reporte
     */
    public String getElementType() {
        return elementType;
    }

    /**
     * Fija el valor del tipo de elementos a mostrar en el reporte
     * @param elementType el tipo de elementos a mostrar en el reporte
     */
    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    /**
     * Obtiene el t&iacute;tulo del objeto a buscar
     * @return un {@literal String} indicando el t&iacute;tulo del objeto a buscar
     */
    public String getObjectTitle() {
        return objectTitle;
    }

    /**
     * Fija el t&iacute;tulo del objeto a buscar
     * @param objectTitle el t&iacute;tulo del objeto a mostrar en el reporte
     */
    public void setObjectTitle(String objectTitle) {
        this.objectTitle = objectTitle;
    }

    /**
     * Obtiene el per&iacute;odo seleccionado como inicio del intervalo de b&uacute;squeda de datos
     * @return la instancia del {@literal Period} que define el inicio del intervalo de b&uacute;squeda
     */
    public Period getInitialPeriod() {
        return initialPeriod;
    }

    /**
     * Fija el valor del per&iacute;odo seleccionado como inicio del intervalo de b&uacute;squeda de datos
     * @param initialPeriod el {@literal Period} que define el inicio del intervalo de b&uacute;squeda
     */
    public void setInitialPeriod(Period initialPeriod) {
        this.initialPeriod = initialPeriod;
    }

    /**
     * Obtiene el per&iacute;odo seleccionado como final del intervalo de b&uacute;squeda de datos
     * @return la instancia del {@literal Period} que define el final del intervalo de b&uacute;squeda
     */
    public Period getFinalPeriod() {
        return finalPeriod;
    }

    /**
     * Fija el valor del per&iacute;odo seleccionado como fin del intervalo de b&uacute;squeda de datos
     * @param finalPeriod el {@literal Period} que define el fin del intervalo de b&uacute;squeda
     */
    public void setFinalPeriod(Period finalPeriod) {
        this.finalPeriod = finalPeriod;
    }

    /**
     * Obtiene el {@code State} seleccionado como criterio de b&uacute;squeda para la extracci&oacute;n de datos.
     * @return el {@code State} a buscar
     */
    public State getStatus() {
        return status;
    }

    /**
     * Fija el {@code State} seleccionado como criterio de b&uacute;squeda para la extracci&oacute;n de datos.
     * @param status el {@code State} a buscar
     */
    public void setStatus(State status) {
        this.status = status;
    }

    /**
     * Obtiene el usuario seleccionado con rol de Champion como criterio de b&uacute;squeda para la extracci&oacute;n de datos
     * @return el usuario seleccionado con rol de Champion a buscar
     */
    public User getChampion() {
        return champion;
    }

    /**
     * Fija al usuario seleccionado con rol de Champion como criterio de b&uacute;squeda para la extracci&oacute;n de datos
     * @param champion el usuario seleccionado con rol de Champion a buscar
     */
    public void setChampion(User champion) {
        this.champion = champion;
    }

    /**
     * Obtiene el usuario con rol de Sponsor seleccionado como criterio de b&uacute;squeda para la extracci&oacute;n de datos
     * @return el usuario seleccionado con rol de Sponsor a buscar
     */
    public User getSponsor() {
        return sponsor;
    }

    /**
     * Fija el usuario con rol de Sponsor seleccionado como criterio de b&uacute;squeda para la extracci&oacute;n de datos
     * @param sponsor el usuario seleccionado con rol de Sponsor a buscar
     */
    public void setSponsor(User sponsor) {
        this.sponsor = sponsor;
    }

    /**
     * Obtiene los tipos de elementos relacionados que se desea se incluyan en los resultados de extracci&oacute;n de datos del reporte
     * @return los tipos de elementos relacionados que se desea incluir
     */
    public String[] getRelatedElements() {
        return relatedElements;
    }

    /**
     * Fija los tipos de elementos relacionados que se desea se incluyan en los resultados de extracci&oacute;n de datos del reporte
     * @param relatedElements los tipos de elementos relacionados que se desea incluir
     */
    public void setRelatedElements(String[] relatedElements) {
        this.relatedElements = relatedElements;
    }

    /**
     * Obtiene las propiedades seleccionadas como estructura del listado de resultados
     * @return el arreglo con las {@code SemanticProperty} seleccionadas
     */
    public SemanticProperty[] getProps2Show() {
        return props2Show;
    }

    /**
     * Fija las propiedades seleccionadas como estructura del listado de resultados
     * @param props2Show el arreglo con las {@code SemanticProperty} seleccionadas
     */
    public void setProps2Show(SemanticProperty[] props2Show) {
        this.props2Show = props2Show;
    }
    
    /**
     * Constructor de instancias de objetos para esta clase
     */
    public ReportCriteria() {
    }
    
    @Override
    public String toString() {
        
        StringBuilder toReturn = new StringBuilder(256);
        toReturn.append("elementType:");
        toReturn.append(this.getElementType() != null ? this.getElementType() : "");
        toReturn.append(";objectTitle:");
        toReturn.append(this.getObjectTitle() != null ? this.getObjectTitle() : "");
        toReturn.append(";initialPeriod:");
        toReturn.append(this.getInitialPeriod() != null ? this.getInitialPeriod().getURI() : "");
        toReturn.append(";finalPeriod:");
        toReturn.append(this.getFinalPeriod() != null ? this.getFinalPeriod().getURI() : "");
        toReturn.append(";status:");
        toReturn.append(this.getStatus() != null ? this.getStatus().getURI() : "");
        toReturn.append(";champion:");
        toReturn.append(this.getChampion() != null ? this.getChampion().getURI() : "");
        toReturn.append(";sponsor:");
        toReturn.append(this.getSponsor() != null ? this.getSponsor().getURI() : "");
        toReturn.append(";relatedElements:[");
        for (String s : this.getRelatedElements()) {
            toReturn.append(s);
            toReturn.append(",");
        }
        toReturn.append("];props2Show:[");
        for (SemanticProperty prop : this.getProps2Show()) {
            toReturn.append(prop.getURI());
            toReturn.append(",");
        }
        toReturn.append("]");
        
        return toReturn.toString();
    }
    
}
