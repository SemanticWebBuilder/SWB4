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

package com.infotec.wb.resources.survey;

import java.util.*;
import java.sql.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;


/**
 * User: Juan Antonio Fernández Arias
 * 
 */
public class SurveyRandom {
    private static Logger log = SWBUtils.getLogger(Subject.class);

    private long surveyid;
    private String idtm;

    public SurveyRandom(){
        this.surveyid=0;
        this.idtm=null;
    }

    public SurveyRandom(long psurveyid, String idtm){
        this();
        this.surveyid = psurveyid;
        this.idtm=idtm;
    }

    public long getSurveyID(){
        return this.surveyid;
    }

    public String getRandom(int plen){
        HashMap hmpre = null;
        HashMap hmpos = null;
        StringBuffer strin = new StringBuffer();
        StringBuffer strinson = new StringBuffer();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String strini = null;
        String strfin = null;
        String strson = null;
        String strsonfin = null;
        String strsql = null;
        int ssonquestion = 0;
        int squestionid = 0;
        int inum = 0;
        int showques = 0;
        int itot = 0;
        int ilen = 0;
        int ison = 0;
        int iran = 0;
        int icon = 0;
        if(surveyid != 0){
            hmpre = new HashMap();
            hmpos = new HashMap();
            showques = plen;
            try {
                conn = SWBUtils.DB.getDefaultConnection();
                /*Obtiene las preguntas quer son hijas*/
                strinson.append(" and sr_orderquestion.questionid not in(");
                strsql = "select sonquestion from sr_relatedquestion where surveyid = ? and idtm=?";
                st =conn.prepareStatement(strsql);
                st.setLong(1,surveyid);
                st.setString(2,idtm);
                rs=st.executeQuery();
                while(rs.next()){
                    icon = 1;
                    ssonquestion = rs.getInt("sonquestion");
                    strinson.append(Integer.toString(ssonquestion));
                    strinson.append(",");
                }

                /*Obtiene las preguntas aleatorias ignorando las que son hijas*/
                strsql="select sr_orderquestion.questionid, sr_orderquestion.subjectid, sr_orderquestion.ordernum from sr_orderquestion where isactive = 1 and surveyid = ? and idtm=? ";
                if (icon == 1){
                    strson = strinson.toString();
                    ison = strson.length();
                    strsonfin = strson.substring(0,ison-1);
                    strsonfin = strsonfin + ")";
                    strsql = strsql + strsonfin;
                }
                strsql = strsql + " order by sr_orderquestion.subjectid, sr_orderquestion.ordernum";
                st =conn.prepareStatement(strsql);
                st.setLong(1,surveyid);
                st.setString(2,idtm);
                rs=st.executeQuery();
                while(rs.next()){
                    squestionid = rs.getInt("questionid");
                    hmpre.put(Integer.toString(inum),Integer.toString(squestionid));
                    inum++;
                }
                if(rs!=null)rs.close();
                if(st!=null)st.close();
                if(conn!=null)conn.close();

                strin.append(" and sr_orderquestion.questionid in(");
                strfin = "all";
                if(showques > 0 && inum > showques){
                    iran = 0;
                    while(itot < showques){
                        iran = (int)(Math.random() * inum);
                        if(!hmpos.containsKey(Integer.toString(iran))){
                            hmpos.put(Integer.toString(iran),hmpre.get(Integer.toString(iran)));
                            strin.append((String)hmpre.get(Integer.toString(iran)));
                            strin.append(",");
                            itot++;
                        }
                    }
                    strini = strin.toString();
                    ilen = strini.length();
                    strfin = strini.substring(0,ilen-1);
                    strfin = strfin + ")";
                }
            }
            catch (SQLException e){
                log.error("Error while trying to load records from sr_relatedquestion or sr_orderquestion, class - SurveyRandom, method - getRandom ", e);
            }
            finally{
                rs=null;
                st=null;
                conn=null;
            }
        }
        return strfin;
    }

   
}
