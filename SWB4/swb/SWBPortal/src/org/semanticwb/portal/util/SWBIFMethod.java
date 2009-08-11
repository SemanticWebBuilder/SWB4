/**  
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
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.util;

import com.arthurdo.parser.HtmlTag;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;

/**
 *
 * @author javier.solis
 */
public class SWBIFMethod
{
    private static Logger log = SWBUtils.getLogger(SWBIFMethod.class);
    private Template tpl;
    private HtmlTag tag;
    private String txt;
    private String type=null;
    private boolean notlangs=false;
    private ArrayList<String> langs=null;
    private boolean notdevices=false;
    private ArrayList<Device> devices=null;

    /**
     * @param method
     * @param obj
     * @param arguments
     * @param tpl  */
    public SWBIFMethod(HtmlTag tag, String txt, Template tpl)
    {
        this.tpl=tpl;
        //System.out.println("tag:"+tag);
        this.tag = tag;
        this.txt = txt;
        type=tag.getTagString().toLowerCase();

        initUser();
    }

    private void initUser()
    {
        String lang=tag.getParam("language");
        String device=tag.getParam("device");
        //System.out.println("lang:"+lang);
        //System.out.println("device:"+lang);
        langs=new ArrayList();
        devices=new ArrayList();

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
                    if(d!=null)devices.add(d);
                }
            }
        }catch(Exception e){log.error("Error reading if user devices...",e);}
    }

    public String eval(User user, WebPage webpage)
    {
        String ret=txt;
        if(type.equals("if:user"))
        {
            if(!langs.isEmpty())
            {
                boolean cont=langs.contains(user.getLanguage());
                if((!cont && !notlangs)||(cont && notlangs))
                {
                    return "";
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
                    return "";
                }
            }
        }else if(type.equals("if:topic") || type.equals("if:webpage"))
        {

        }else if(type.equals("if:template"))
        {

        }
        return ret;
    }


}
