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

import com.arthurdo.parser.HtmlTag;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBIFMethod.
 * 
 * @author javier.solis
 */
public class SWBIFMethod
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBIFMethod.class);
    
    /** The tpl. */
    private Template tpl;
    
    /** The tag. */
    private HtmlTag tag;
    
    /** The base parts. */
    private ArrayList baseParts;

    /** The parts. */
    private ArrayList parts;
    
    /** The type. */
    private String type=null;
    
    /** The notlangs. */
    private boolean notlangs=false;
    
    /** The langs. */
    private ArrayList<String> langs=null;
    
    /** The notdevices. */
    private boolean notdevices=false;
    
    /** The devices. */
    private ArrayList<Device> devices=null;

    /** The notinstanceOf. */
    private boolean notinstanceOfs=false;

    /** The instanceOf. */
    private ArrayList<SemanticClass> instanceOfs=null;

    /** The notinstanceOf. */
    private boolean nottypeOfs=false;

    /** The instanceOf. */
    private ArrayList<SemanticClass> typeOfs=null;
    
    /** The notids. */
    private boolean notids=false;
    
    /** The instanceOf. */
    private ArrayList<String> ids=null;    
    
    /** The notinstanceOf. */
    private boolean notchildOfs=false;

    /** The instanceOf. */
    private ArrayList<WebPage> childOfs=null;
    

    /**
     * Instantiates a new sWBIF method.
     * 
     * @param tag the tag
     * @param baseParts the base parts
     * @param tpl the tpl
     */
    public SWBIFMethod(HtmlTag tag, ArrayList baseParts, Template tpl)
    {
        this.tpl=tpl;
        //System.out.println("tag:"+tag);
        this.tag = tag;
        this.baseParts=baseParts;
        this.parts = new ArrayList();
        type=tag.getTagString().toLowerCase();
        initUser();
    }

    /**
     * Inits the user.
     */
    private void initUser()
    {
        String lang=tag.getParam("language");
        String device=tag.getParam("device");
        String instanceOf=tag.getParam("instanceof");
        String childOf=tag.getParam("childof");
        String typeOf=tag.getParam("typeof");
        String id=tag.getParam("id");
        //System.out.println("lang:"+lang);
        //System.out.println("device:"+lang);
        //System.out.println("instanceof:"+instanceOf);
        langs=new ArrayList();
        devices=new ArrayList();
        instanceOfs=new ArrayList();
        typeOfs=new ArrayList();        
        ids=new ArrayList();        
        childOfs=new ArrayList();        

        try
        {
            if(id!=null)
            {
                if(id.startsWith("!"))
                {
                    id=id.substring(1);
                    notids=true;
                }
                StringTokenizer st=new StringTokenizer(id,"|");
                while(st.hasMoreTokens())
                {
                    String tx=st.nextToken();
                    ids.add(tx);
                }
            }
        }catch(Exception e){log.error("Error reading if ids...",e);}                
        
        try
        {
            if(lang!=null)
            {
                if(lang.startsWith("!"))
                {
                    lang=lang.substring(1);
                    notlangs=true;
                }
                StringTokenizer st=new StringTokenizer(lang,"|");
                while(st.hasMoreTokens())
                {
                    String lg=st.nextToken();
                    langs.add(lg);
                }
            }
        }catch(Exception e){log.error("Error reading if user langs...",e);}

        try
        {
            if(device!=null)
            {
                if(device.startsWith("!"))
                {
                    device=device.substring(1);
                    notdevices=true;
                }
                StringTokenizer st=new StringTokenizer(device,"|");
                while(st.hasMoreTokens())
                {
                    String dv=st.nextToken();
                    Device d=tpl.getWebSite().getDevice(dv);
                    if(d!=null)
                    {
                        devices.add(d);
                    }
                }
            }
        }catch(Exception e){log.error("Error reading if user devices...",e);}

        try
        {
            if(instanceOf!=null)
            {
                if(instanceOf.startsWith("!"))
                {
                    instanceOf=instanceOf.substring(1);
                    notinstanceOfs=true;
                }
                StringTokenizer st=new StringTokenizer(instanceOf,"|");
                while(st.hasMoreTokens())
                {
                    String aux=st.nextToken();
                    //System.out.println("aux:("+aux+")");
                    SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(aux);
                    //System.out.println("cls:"+cls);
                    if(cls!=null)
                    {
                        instanceOfs.add(cls);
                    }
                }
            }
        }catch(Exception e){log.error("Error reading if WebPage instanceof...",e);}

        try
        {
            if(typeOf!=null)
            {
                if(typeOf.startsWith("!"))
                {
                    typeOf=typeOf.substring(1);
                    nottypeOfs=true;
                }
                StringTokenizer st=new StringTokenizer(typeOf,"|");
                while(st.hasMoreTokens())
                {
                    String aux=st.nextToken();
                    //System.out.println("aux:("+aux+")");
                    SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(aux);
                    //System.out.println("cls:"+cls);
                    if(cls!=null)
                    {
                        typeOfs.add(cls);
                    }
                }
            }
        }catch(Exception e){log.error("Error reading if WebPage typeof...",e);}
        
        try
        {
            if(childOf!=null)
            {
                if(childOf.startsWith("!"))
                {
                    childOf=childOf.substring(1);
                    notchildOfs=true;
                }
                StringTokenizer st=new StringTokenizer(childOf,"|");
                while(st.hasMoreTokens())
                {
                    String aux=st.nextToken();
                    
                    WebPage page=tpl.getWebSite().getWebPage(aux);
                    if(page!=null)
                    {
                        childOfs.add(page);
                    }
                }
            }
        }catch(Exception e){log.error("Error reading if WebPage instanceof...",e);}        
    }

    /**
     * Eval.
     * 
     * @param user the user
     * @param webpage the webpage
     * @return the string
     */
    public ArrayList eval(User user, WebPage webpage, Template template)
    {
        //System.out.println("type:"+type);
        //System.out.println("parts:"+parts);
        ArrayList ret=parts;
        if(type.equals("if:user"))
        {
            if(!ids.isEmpty())
            {
                boolean cont=ids.contains(user.getId());
                if((!cont && !notids)||(cont && notids))
                {
                    return null;
                }
            }
            
            if(!langs.isEmpty())
            {
                boolean cont=langs.contains(user.getLanguage());
                if((!cont && !notlangs)||(cont && notlangs))
                {
                    return null;
                }
            }
            if(!devices.isEmpty())
            {
                boolean cont=false;
                Iterator<Device> it=devices.iterator();
                while(it.hasNext())
                {
                    Device dvc=it.next();
                    if(user.hasDevice(dvc))
                    {
                        cont=true;
                        break;
                    }
                }
                if((!cont && !notdevices)||(cont && notdevices))
                {
                    return null;
                }
            }
        }else if(type.equals("if:topic") || type.equals("if:webpage"))
        {
            if(!ids.isEmpty())
            {
                boolean cont=ids.contains(webpage.getId());
                if((!cont && !notids)||(cont && notids))
                {
                    return null;
                }
            }            
            
            //System.out.println("instanceOfs:"+instanceOfs);
            if(!instanceOfs.isEmpty())
            {
                boolean cont=false;
                Iterator<SemanticClass> it=instanceOfs.iterator();
                while(it.hasNext())
                {
                    SemanticClass cls=it.next();
                    //System.out.println("cls:"+cls);
                    if(webpage.getSemanticObject().instanceOf(cls))
                    {
                        cont=true;
                        break;
                    }
                }
                if((!cont && !notinstanceOfs)||(cont && notinstanceOfs))
                {
                    return null;
                }
            }
            //System.out.println("typeOfs:"+typeOfs);
            if(!typeOfs.isEmpty())
            {
                boolean cont=false;
                Iterator<SemanticClass> it=typeOfs.iterator();
                while(it.hasNext())
                {
                    SemanticClass cls=it.next();
                    //System.out.println("cls:"+cls);
                    if(webpage.getSemanticObject().getSemanticClass().equals(cls))
                    {
                        cont=true;
                        break;
                    }
                }
                if((!cont && !nottypeOfs)||(cont && nottypeOfs))
                {
                    return null;
                }
            }
            //System.out.println("childOfs:"+childOfs);
            if(!childOfs.isEmpty())
            {
                boolean cont=false;
                Iterator<WebPage> it=childOfs.iterator();
                while(it.hasNext())
                {
                    WebPage page=it.next();
                    //System.out.println("cls:"+cls);
                    if(webpage.isChildof(page))
                    {
                        cont=true;
                        break;
                    }
                }
                if((!cont && !notchildOfs)||(cont && notchildOfs))
                {
                    return null;
                }
            }            
            
        }else if(type.equals("if:website"))
        {
            if(!ids.isEmpty())
            {
                boolean cont=ids.contains(webpage.getWebSite().getId());
                if((!cont && !notids)||(cont && notids))
                {
                    return null;
                }
            }            
            
            //System.out.println("instanceOfs:"+instanceOfs);
            if(!instanceOfs.isEmpty())
            {
                boolean cont=false;
                Iterator<SemanticClass> it=instanceOfs.iterator();
                while(it.hasNext())
                {
                    SemanticClass cls=it.next();
                    //System.out.println("cls:"+cls);
                    if(webpage.getWebSite().getSemanticObject().instanceOf(cls))
                    {
                        cont=true;
                        break;
                    }
                }
                if((!cont && !notinstanceOfs)||(cont && notinstanceOfs))
                {
                    return null;
                }
            }
            //System.out.println("typeOfs:"+typeOfs);
            if(!typeOfs.isEmpty())
            {
                boolean cont=false;
                Iterator<SemanticClass> it=typeOfs.iterator();
                while(it.hasNext())
                {
                    SemanticClass cls=it.next();
                    //System.out.println("cls:"+cls);
                    if(webpage.getWebSite().getSemanticObject().getSemanticClass().equals(cls))
                    {
                        cont=true;
                        break;
                    }
                }
                if((!cont && !nottypeOfs)||(cont && nottypeOfs))
                {
                    return null;
                }
            }
        }else if(type.equals("if:template"))
        {
            if(!ids.isEmpty())
            {
                boolean cont=ids.contains(template.getId());
                if((!cont && !notids)||(cont && notids))
                {
                    return null;
                }
            }            
            //TODO:
        }
        return ret;
    }

    /**
     * Gets the base parts.
     * 
     * @return the base parts
     */
    public ArrayList getBaseParts()
    {
        return baseParts;
    }

    /**
     * Gets the parts.
     * 
     * @return the parts
     */
    public ArrayList getParts()
    {
        return parts;
    }
}
