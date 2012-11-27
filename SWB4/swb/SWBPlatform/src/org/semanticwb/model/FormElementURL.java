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
package org.semanticwb.model;

import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class FormElementURL.
 * 
 * @author javier.solis
 */
public class FormElementURL
{
    
    /** The Constant URLTYPE_RENDER. */
    public static final String URLTYPE_RENDER="render";
    
    /** The Constant URLTYPE_PROCESS. */
    public static final String URLTYPE_PROCESS="process";
    
    /** The Constant URLTYPE_VALIDATE. */
    public static final String URLTYPE_VALIDATE="validate";

    /** The frmele. */
    private FormElement frmele;
    
    /** The obj. */
    private SemanticObject obj;
    
    /** The prop. */
    private SemanticProperty prop;
    
    /** The codetype. */
    private String codetype;
    
    /** The urltype. */
    private String urltype;
    
    /** The mode. */
    private String mode;
    
    /** The lang. */
    private String lang;

    /** The params. */
    private HashMap<String,String> params;
    
    private String contentType=null;
    
    private String characterEncoding=null;
    
    /**
     * Instantiates a new form element url.
     * 
     * @param frmele the frmele
     * @param obj the obj
     * @param prop the prop
     * @param urltype the urltype
     * @param codetype the codetype
     * @param mode the mode
     * @param lang the lang
     */
    public FormElementURL(FormElement frmele, SemanticObject obj, SemanticProperty prop, String urltype, String codetype, String mode, String lang)
    {
        this(frmele, obj, prop, urltype, codetype, mode, lang, null, null);
    }

    /**
     * Instantiates a new form element url.
     * 
     * @param frmele the frmele
     * @param obj the obj
     * @param prop the prop
     * @param urltype the urltype
     * @param codetype the codetype
     * @param mode the mode
     * @param lang the lang
     */
    public FormElementURL(FormElement frmele, SemanticObject obj, SemanticProperty prop, String urltype, String codetype, String mode, String lang, String contentType, String characterEncoding)
    {
        params=new HashMap();
        this.frmele=frmele;
        this.obj=obj;
        this.prop=prop;
        this.urltype=urltype;
        this.codetype=codetype;
        this.mode=mode;
        this.lang=lang;
        this.contentType=contentType;
        this.characterEncoding=characterEncoding;
    }

    /**
     * Sets the parameter.
     * 
     * @param key the key
     * @param value the value
     * @return the form element url
     */
    public FormElementURL setParameter(String key, String value)
    {
        params.put(key, value);
        return this;
    }

    /**
     * Gets the parameter.
     * 
     * @param key the key
     * @return the parameter
     */
    public String getParameter(String key)
    {
        return params.get(key);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuffer ret=new StringBuffer();
        ret.append(SWBPlatform.getContextPath()+"/frmprocess");
        ret.append("?_swb_frmele="+frmele.getSemanticObject().getEncodedURI());
        if(obj!=null && obj.getURI()!=null)ret.append("&_swb_obj="+obj.getEncodedURI());
        else if(obj !=null && obj.getModel()!=null)
        {
            ret.append("&_swb_model="+obj.getModel().getName());
            if(obj.getSemanticClass()!=null)ret.append("&_swb_cls="+obj.getSemanticClass().getEncodedURI());
        }
        ret.append("&_swb_prop="+prop.getEncodedURI());
        ret.append("&_swb_urltp="+URLEncoder.encode(urltype));
        if(codetype!=null)ret.append("&_swb_codetp="+URLEncoder.encode(codetype));
        if(mode!=null)ret.append("&_swb_mode="+URLEncoder.encode(mode));
        if(lang!=null)ret.append("&_swb_lang="+URLEncoder.encode(lang));
        if(contentType!=null)ret.append("&_swb_contentType="+URLEncoder.encode(contentType));
        if(characterEncoding!=null)ret.append("&_swb_characterEncoding="+URLEncoder.encode(characterEncoding));

        Iterator<String> it=params.keySet().iterator();
        while(it.hasNext())
        {
            String key=it.next();
            String val=params.get(key);
            if(val!=null)ret.append("&"+key+"="+URLEncoder.encode(val));
        }
        return ret.toString();
    }

    public FormElementURL setContentType(String contentType)
    {
        this.contentType = contentType;
        return this;
    }

    public String getContentType()
    {
        return contentType;
    }

    public FormElementURL setCharacterEncoding(String characterEncoding)
    {
        this.characterEncoding = characterEncoding;
        return this;
    }

    public String getCharacterEncoding()
    {
        return characterEncoding;
    }
    
}
