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
package org.semanticwb.portal.social.facebook;

// TODO: Auto-generated Javadoc
/**
 * The Class Fb_User.
 * 
 * @author jorge.jimenez
 */
public class Fb_User {

    /** The name. */
    String name=null;
    
    /** The pic. */
    String pic=null;
    
    /** The city. */
    String city=null;
    
    /** The state. */
    String state=null;
    
    /** The country. */
    String country=null;
    
    /** The profile_url. */
    String profile_url=null;
    
    /** The pic_with_logo. */
    String pic_with_logo=null;
    
    /** The birthday. */
    String birthday=null;

    /**
     * Sets the name.
     * 
     * @param name the new name
     */
    public void setName(String name){
        this.name=name;
    }
    
    /**
     * Sets the pic.
     * 
     * @param pic the new pic
     */
    public void setPic(String pic){
        this.pic=pic;
    }
    
    /**
     * Sets the pic_with_logo.
     * 
     * @param pic_with_logo the new pic_with_logo
     */
    public void setPic_with_logo(String pic_with_logo){
        this.pic_with_logo=pic_with_logo;
    }
    
    /**
     * Sets the city.
     * 
     * @param city the new city
     */
    public void setCity(String city){
        this.city=city;
    }
    
    /**
     * Sets the state.
     * 
     * @param state the new state
     */
    public void setState(String state){
        this.state=state;
    }
    
    /**
     * Sets the country.
     * 
     * @param country the new country
     */
    public void setCountry(String country){
        this.country=country;
    }
    
    /**
     * Sets the profile_url.
     * 
     * @param profile_url the new profile_url
     */
    public void setProfile_url(String profile_url){
        this.profile_url=profile_url;
    }
    
    /**
     * Sets the birthday.
     * 
     * @param birthday the new birthday
     */
    public void setBirthday(String birthday){
        this.birthday=birthday;
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
     * Gets the pic.
     * 
     * @return the pic
     */
    public String getPic(){
        return pic;
    }

    /**
     * Gets the pic_with_logo.
     * 
     * @return the pic_with_logo
     */
    public String getPic_with_logo(){
        return pic_with_logo;
    }

    /**
     * Gets the city.
     * 
     * @return the city
     */
    public String getCity(){
        return city;
    }

    /**
     * Gets the state.
     * 
     * @return the state
     */
    public String getState(){
        return state;
    }

    /**
     * Gets the country.
     * 
     * @return the country
     */
    public String getCountry(){
        return country;
    }

    /**
     * Gets the profile_url.
     * 
     * @return the profile_url
     */
    public String getProfile_url(){
        return profile_url;
    }

    /**
     * Gets the birthday.
     * 
     * @return the birthday
     */
    public String getBirthday(){
        return birthday;
    }
}
