/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.util;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentStyles.
 * 
 * @author Jorge Jiménez
 */
public class ContentStyles {

    /** The name. */
    private String name;
    
    /** The font. */
    private String font;
    
    /** The size. */
    private String size;
    
    /** The color. */
    private String color;
   
    /**
     * Instantiates a new content styles.
     */
    public ContentStyles(){
        this.name=null;
        this.font=null;
        this.size=null;
        this.color=null;
    }

    //sets
    /**
     * Sets the name.
     * 
     * @param name the new name
     */
    public void setName(String name){
        this.name=name;
    }
    
    /**
     * Sets the font.
     * 
     * @param font the new font
     */
    public void setFont(String font){
        this.font=font;
    }
    
    /**
     * Sets the size.
     * 
     * @param size the new size
     */
    public void setSize(String size){
        this.size=size;
    }
    
    /**
     * Sets the color.
     * 
     * @param color the new color
     */
    public void setColor(String color){
        this.color=color;
    }

    //gets
    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Gets the font.
     * 
     * @return the font
     */
    public String getFont(){
        return font;
    }
    
    /**
     * Gets the size.
     * 
     * @return the size
     */
    public String getSize(){
        return size;
    }
    
    /**
     * Gets the color.
     * 
     * @return the color
     */
    public String getColor(){
        return color;
    }

}