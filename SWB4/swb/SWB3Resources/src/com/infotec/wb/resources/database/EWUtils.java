/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


package com.infotec.wb.resources.database;

import java.util.Set;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Locale;
import java.util.TreeSet;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;

//import com.infotec.appfw.util.AFUtils;
//import com.infotec.topicmaps.WebPage;
//import com.infotec.topicmaps.Member;
//import com.infotec.topicmaps.RoleSpec;
//import com.infotec.topicmaps.WebSite;
//import com.infotec.topicmaps.bean.TopicMgr;
//import com.infotec.topicmaps.Association;
//import com.infotec.wb.core.db.DBUser;
//import com.infotec.wb.core.db.RecUser;
//import com.infotec.wb.services.TopicSrv;
//import com.infotec.topicmaps.util.TMComparator;

/**
 * Utilerias para e-Workplace
 *
 * @author: Sergio Tellez
 * @version 1.0
 */
public class EWUtils {

    /**
     * Constructor.
     */
    public EWUtils() {
    }

    /**
	 * Determina si la clave de referencia es clave foranea.
	 *
	 * @param key clave primaria.
	 * @param refer clave foranea.
	 */
    public boolean isForeignKey(String key, String referkey) {
        boolean isKey = false;
        Stack mkey = new Stack();
        Stack mrkey = new Stack();
        if (key.indexOf(',') != -1) {
            String [] keys = key.split(",");
            for (int i=0; i<keys.length; i++)
                mkey.push(keys[i].trim());
        }else
            mkey.push(key);
        if (referkey.indexOf(',') != -1) {
            String [] keys = referkey.split(",");
            for (int i=0; i<keys.length; i++)
                mrkey.push(keys[i].trim());
        }else
            mrkey.push(referkey);
        if (mkey.size() <= mrkey.size())
            isKey = compareKey(mkey, mrkey);
        else
            isKey = compareKey(mrkey, mkey);
        return isKey;
    }
    
    /**
	 * Regresa sentencia sql de acuerdo al orden inferido por el t�pico de referencia.
	 *
	 * @param field Nombre del campo del que se obtendr�n los nombres base.
	 * @param pkey Clave primaria de la tabla.
	 * 
	 * @return pkey Clave primaria de la tabla.
	 */
    public String getForeignKey(String field, String pkey) {
        String orderKey = new String();
        String referKey = new String();
        if (pkey.indexOf(',') != -1) {
            String [] keys = pkey.split(",");
            for (int i=0; i<keys.length; i++) {
                if (keys[i].equals(field))
                    referKey = field;
                else
                    orderKey += "," + keys[i];
            }
            orderKey = referKey + orderKey;
        }else
            orderKey = pkey;
        return orderKey;
    }
    
    /**
	 * Regresa sentencia sql parcial.
	 *
	 * @param db Nombre de  conexion a base de datos.
	 * @param table Nombre de la tabla a consultar.
	 * @param field Nombre del campo del que se obtendr�n los nombres base.
	 * @param pkey Clave primaria de la tabla.
	 * 
	 * @return query Sentencia sql.
	 */
    public String getField(String db, String table, String field) {
        String option = field;
        if (propertyExist(db+"."+table+"."+field))
            option = ResourceBundle.getBundle("dbtopics", Locale.ENGLISH).getString(db+"."+table+"."+field);
        return option;
    }
    
    /**
	 * Crea los topicos para el rol del relacionado.
	 *
	 * @param rol Nombre del campo de la tabla de base de datos de donde se obtendra el rol.
	 * @param table Nombre de la tabla a consultar.
	 * @param db Nombre de  conexion a base de datos.
	 * @param tm Nombre del topicmap que contendra los t�picos para el rol del relacionado.
	 * @param userid Identificador del usuario.
	 * @param topics Topicos relacionados a la tabla.
	 */
//    public void createRolSpec(String rol, String table, String db, String tm, String userid, HashMap topics) {
//        HashMap recs = new HashMap();
//        String rolId = new String();
//        //TopicSrv topsrv = new TopicSrv();
//        QueryGeneric rec = new QueryGeneric(db);
//        WebSite topicmap = SWBContext.getWebSite(tm);
//        String query = "SELECT distinct " + rol + " from " + table;
//        rec.setQuery(query);
//	    Enumeration e = rec.getResults(query);
//	    if (topicmap.existTopic("DBTopic_"+table)) {
//	        WebPage rolSpec = new WebPage();
//            while (e.hasMoreElements()) {
//                recs = (HashMap)e.nextElement();
//                rolId = (String)recs.get(rol);
//	            if (topicmap.existTopic("DBTopic_"+rolId))
//	                rolSpec = topicmap.getTopic("DBTopic_"+rolId);
//	            else {
//	                try {
//	                    rolSpec = topsrv.createTopic(topicmap.getTopic("DBTopic_"+table), "DBTopic_"+rolId, topicmap.getlang().getDisplayName(), rolId, userid);
//	                    rolSpec.setActive(1);
//	                }catch(Exception ex) {
//	                    AFUtils.getInstance().log(ex, AFUtils.getLocaleString("com.infotec.wb.resources.database.DBTopics", "error_DBTopic_getTopicsRec"), true);
//	                }
//	            }
//	            topics.put(rolId, rolSpec);
//	        }
//	    }
//    }
    
    /**
	 * Recupera los topicos para el rol del relacionado.
	 *
	 * @param rol Nombre del campo de la tabla de base de datos de donde se obtendra el rol.
	 * @param table Nombre de la tabla a consultar.
	 * @param db Nombre de  conexion a base de datos.
	 * @param tm Nombre del topicmap que contendra los t�picos para el rol del relacionado.
	 * @param userid Identificador del usuario.
	 * @param topics Topicos relacionados a la tabla.
	 */
//    public ArrayList recoverRolSpec(String rol, String table, String db, String tm, String userid, HashMap topics) {
//        String rolId = new String();
//        HashMap recs = new HashMap();
//        //TopicSrv topsrv = new TopicSrv();
//        ArrayList rolIds = new ArrayList();
//        QueryGeneric rec = new QueryGeneric(db);
//        WebSite topicmap = SWBContext.getWebSite(tm);
//        String query = "SELECT distinct " + rol + " from " + table;
//        rec.setQuery(query);
//	    Enumeration e = rec.getResults(query);
//	    if (topicmap.existTopic("DBTopic_"+table)) {
//	        WebPage rolSpec = new WebPage();
//            while (e.hasMoreElements()) {
//                recs = (HashMap)e.nextElement();
//                rolId = (String)recs.get(rol);
//                rolIds.add(rolId);
//	            if (topicmap.existTopic("DBTopic_"+rolId))
//	                rolSpec = topicmap.getTopic("DBTopic_"+rolId);
//	            else {
//	                try {
//	                    rolSpec = topsrv.createTopic(topicmap.getTopic("DBTopic_"+table), "DBTopic_"+rolId, topicmap.getlang().getDisplayName(), rolId, userid);
//	                    rolSpec.setActive(1);
//	                }catch(Exception ex) {
//	                    AFUtils.getInstance().log(ex, AFUtils.getLocaleString("com.infotec.wb.resources.database.DBTopics", "error_DBTopic_getTopicsRec"), true);
//	                }
//	            }
//	            topics.put(rolId, rolSpec);
//	        }
//	    }
//	    return rolIds;
//    }
    
    /**
	 * Determina si la clave de referencia es igual a la clave foranea.
	 *
	 * @param key clave primaria.
	 * @param refer clave foranea.
	 */
    public boolean equalsKey(String key, String referkey) {
        HashMap mkey = new HashMap();
        HashMap mrkey = new HashMap();
        if (key.indexOf(',') != -1) {
            String [] keys = key.split(",");
            for (int i=0; i<keys.length; i++)
                mkey.put(keys[i].trim(), keys[i].trim());
        }else
            mkey.put(key, key);
        if (referkey.indexOf(',') != -1) {
            String [] keys = referkey.split(",");
            for (int i=0; i<keys.length; i++)
                mrkey.put(keys[i].trim(), keys[i].trim());
        }else
            mrkey.put(referkey, referkey);
        return mkey.equals(mrkey);
    }
    
    /**
	 * Compara las claves de referencia.
	 *
	 * @param key clave primaria.
	 * @param refer clave de referencia.
	 */
    public boolean compareKey(Stack key, Stack refer) {
        boolean isKey = false;
        while (!refer.isEmpty()) {
            if (key.search(refer.pop()) != -1)
                isKey = true;
        }
        return isKey;
    }
    
    /**
	 * Regresa un BaseName v�lido para el t�pico.
	 *
	 * @param basename cadena de car�cteres.
	 */
    public String getBaseName(String basename) {
        String id = new String();
        id = basename.replaceAll(" ", "_"); 
        if (id.length()>150)
            id = id.substring(0, 150);
        id = id.replaceAll("�", "a").replaceAll("�", "e").replaceAll("�", "i").replaceAll("�", "o").replaceAll("�", "u").replaceAll("�", "n").replaceAll("\\.", "").replaceAll("-","").replaceAll(",","");
        return id;
    }
    
    /**
     * Indica si en el catalogo existe la propiedad.
     * 
     * @param bundle Nombre de la propiedad
     * 
     * @return <b>boolean</b> true Si existe la propiedad; false En caso contrario.
     */
    public boolean propertyExist(String bundle) {
        try {
            String option = ResourceBundle.getBundle("dbtopics", Locale.ENGLISH).getString(bundle);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Indica si en el catalogo existe la propiedad.
     * 
     * @param bundle Nombre de la propiedad
     * @param file Nombre del archivo de propiedades
     * 
     * @return <b>boolean</b> true Si existe la propiedad; false En caso contrario.
     */
    public boolean propertyExist(String bundle, String file) {
        try {
            String option = ResourceBundle.getBundle(file, Locale.ENGLISH).getString(bundle);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
    
    /**
	 * Regresa el criterio de activaci�n de t�picos.
	 *
	 * @param db nombre de la conexi�n.
	 * @param table nombre de la tabla de base de datos.
	 */
    public boolean existActiveField(String db, String table) {
        boolean active = false;
        try {
            TableGeneric tbl = new TableGeneric(table, db);
            if (tbl.existColumnName("activo"))
                active = true;
        }catch (SQLException sql) {
            return active;
        }
        return active;
    }
    
    /**
     * Regresa todos los hijos de todos los niveles inferiores
     * No regresa los hijos virtuales o ligados.
     * 
     * @return ArrayList hijos ordenados de todos los niveles inferiores
     */
//    public Iterator getSetChildAll(ArrayList topics) {
//        Iterator it = topics.iterator();
//        TreeSet set=new TreeSet(new TMComparator());
//        while(it.hasNext()) {
//            WebPage tp=(WebPage)it.next();
//            if (tp.getDbdata().getDeleted() == 0 && tp.getDbdata().getActive() != 0) {
//                set.add(tp);
//            }
//        }
//        return set.iterator();
//    }
    
    /**
	 * Regresa true si las asociaciones son iguales, false en caso contrario.
	 *
	 * @param refer asociaci�n de referencia.
	 * @param base asociaci�n base.
	 */
//    public boolean compare(Association base, Association refer) {
//        if (base.getInstanceOf().equals(refer.getInstanceOf())){
//            if (base.getMembers().equals(refer.getMembers()))
//                return true;
//        }
//        return false;
//    }
    
    /**
	 * Regresa las dbassociations del topico de referencia.
	 *
	 * @param topic T�pico.
	 */
//    public ArrayList getDbassociations(WebPage topic) {
//        ArrayList dbassociations = new ArrayList();
//        Iterator it = topic.getAssociations().iterator();
//        while (it.hasNext()) {
//            Association as = (Association)it.next();
//            if (isDbassociation(as))
//                dbassociations.add(as);
//        }
//        return dbassociations;
//    }
    
    /**
	 * Regresa true si la asociacion es generada por la clase DBAssociation, false en caso contrario.
	 *
	 * @param association asociaci�n de referencia.
	 */
//    public boolean isDbassociation(Association association) {
//        boolean isdba = false;
//        if (association.getInstanceOf().getTopicRef().getId().startsWith("DBTopic_")){
//            Iterator it = association.getMembers().iterator();
//            while (it.hasNext()) {
//                Member member = (Member)it.next();
//                if (member.getRoleSpec().getTopicRef().getId().startsWith("DBTopic_"))
//                    isdba = true;
//                else
//                    return false;
//            }
//        }
//        return isdba;
//    }
    
    /**
	 * Regresa true si la asociacion es contenida por las referencias, false en caso contrario.
	 *
	 * @param referencias asociaciones de referencia.
	 * @param association asociaci�n a examinar.
	 */
//    public boolean contains(Association association, HashMap associations) {
//        Iterator it = associations.keySet().iterator();
//        while (it.hasNext()) {
//            if(compare((Association)it.next(), association))
//                return true;
//        }
//        return false;
//    }
}