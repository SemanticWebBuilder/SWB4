/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * DBUser.java
 *
 * Created on 3 de junio de 2002, 11:20
 */
package com.infotec.wb.core.db;

import java.util.*;

import com.infotec.appfw.lib.AFAppObject;
import com.infotec.appfw.util.AFUtils;


/** objeto: acceso a DB tabla wbuser
 *
 * object: wbuser table db  access.
 *
 * @author JAJSoftware(2004)
 * @version 1.1
 */
public class DBUser implements AFAppObject
{

    static private DBUser instance;       // The single instance
    private HashMap userReps=new HashMap();
    private Properties userProp;
    private String defaultRep="wb";

    /** Creates new DBUser */
    private DBUser()
    {
        AFUtils.log("Initializing DBUser", true);
    }

    public void destroy()
    {
        instance=null;
        try
        {
            //WBUtils.getInstance().XMLObjectEncoder(usersId, "DBUser.xml");
        } catch (Exception e)
        {
        }
        AFUtils.log("DBUser finalized", true);
    }

//    static synchronized public DBUserRepository getInstance(String repName)
//    {
//        getInstance();
//        return (DBUserRepository)instance.userReps.get(repName);
//    }


    /**
     * @return  */
    static synchronized public DBUser getInstance()
    {
        if (instance == null)
        {
//            WBUserMgr.getInstance();
            instance = new DBUser();
            instance.init();
        }
        return instance;
    }

    public void init()
    {
//        userProp=AFUtils.getPropertyFile("/user.properties");
//        Enumeration propNames = userProp.propertyNames();
//        while (propNames.hasMoreElements())
//        {
//            String name = (String) propNames.nextElement();
//            //System.out.println("name:"+name);
//            if (name.endsWith(".class"))
//            {
//                String repName = name.substring(0, name.lastIndexOf("."));
//                AFUtils.log("Initializing User Repository: "+repName);
//                String clsname = userProp.getProperty(repName + ".class");
//
//                try
//                {
//                    Class cls = Class.forName(clsname);
//                    DBUserRepository rep = (DBUserRepository)cls.newInstance();
//                    if(rep!=null)
//                    {
//                        userReps.put(repName,rep);
//                        rep.init(repName, userProp);
//                    }
//                }catch(Exception e){AFUtils.log(e);}
//
//            }
//       }
//       defaultRep=userProp.getProperty("defaultRepository","wb");
    }

    public static Iterator getRepositories()
    {
        getInstance();
        return instance.userReps.values().iterator();
    }

    /**
     * @param userid
     * @return  */
    public RecUser getUserById(String userid)
    {
//        try
//        {
//            String id=userid.substring(0,userid.indexOf('_'));
//            String rep=userid.substring(userid.indexOf('_')+1);
//            return getInstance(rep).getUserById(Long.parseLong(id));
//        }catch(Exception e){
//            AFUtils.log("Error to get User:"+userid);
//        }
        return null;
    }

    public RecUser getNewRecUser(String userid)
    {
//        try
//        {
//            String id=userid.substring(0,userid.indexOf('_'));
//            String rep=userid.substring(userid.indexOf('_')+1);
//            RecUser rec=getInstance(rep).getNewRecUser();
//            rec.setId(Long.parseLong(id));
//            return rec;
//        }catch(Exception e){AFUtils.log(e);}
        return null;
    }


    public void refresh()
    {
    }

    /* revisar wb3 ***************************/
    public Hashtable getAdminUsers()
    {
        return null;
    }

    /** Getter for property defaultRep.
     * @return Value of property defaultRep.
     *
     */
    public java.lang.String getDefaultRepository()
    {
        return defaultRep;
    }

    /** Setter for property defaultRep.
     * @param defaultRep New value of property defaultRep.
     *
     */
    public void setDefaultRepository(java.lang.String defaultRep)
    {
        this.defaultRep = defaultRep;
    }

}

