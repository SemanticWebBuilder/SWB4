/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author javier.solis
 */
public class FormElementURL
{
    public static final String URLTYPE_RENDER="render";
    public static final String URLTYPE_PROCESS="process";
    public static final String URLTYPE_VALIDATE="validate";

    private FormElement frmele;
    private SemanticObject obj;
    private SemanticProperty prop;
    private String codetype;
    private String urltype;
    private String mode;
    private String lang;

    private HashMap<String,String> params;

    public FormElementURL(FormElement frmele, SemanticObject obj, SemanticProperty prop, String urltype, String codetype, String mode, String lang)
    {
        params=new HashMap();
        this.frmele=frmele;
        this.obj=obj;
        this.prop=prop;
        this.urltype=urltype;
        this.codetype=codetype;
        this.mode=mode;
        this.lang=lang;
    }

    public FormElementURL setParameter(String key, String value)
    {
        params.put(key, value);
        return this;
    }

    public String getParameter(String key)
    {
        return params.get(key);
    }

    @Override
    public String toString()
    {
        StringBuffer ret=new StringBuffer();
        ret.append(SWBPlatform.getContextPath()+"/frmprocess");
        ret.append("?_swb_frmele="+frmele.getSemanticObject().getEncodedURI());
        if(obj!=null)ret.append("&_swb_obj="+obj.getEncodedURI());
        ret.append("&_swb_prop="+prop.getEncodedURI());
        ret.append("&_swb_urltp="+URLEncoder.encode(urltype));
        if(codetype!=null)ret.append("&_swb_codetp="+URLEncoder.encode(codetype));
        if(mode!=null)ret.append("&_swb_mode="+URLEncoder.encode(mode));
        if(lang!=null)ret.append("&_swb_lang="+URLEncoder.encode(lang));

        Iterator<String> it=params.keySet().iterator();
        while(it.hasNext())
        {
            String key=it.next();
            String val=params.get(key);
            if(val!=null)ret.append("&"+key+"="+URLEncoder.encode(val));
        }
        return ret.toString();
    }


}
