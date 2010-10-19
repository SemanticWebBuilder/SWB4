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

import com.infotec.wb.resources.survey.db.RecAnswer;
import com.infotec.wb.resources.survey.db.RecQuestion;
import com.infotec.wb.resources.survey.db.RecResponseUser;
import com.infotec.wb.resources.survey.db.RecSurvey;
import java.sql.*;
import java.util.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.SWBParamRequest;
import org.w3c.dom.Element;


/**
 * Created by Juan Antonio Fernández Arias
 * 
 */
public class Utils {

    private static Logger log = SWBUtils.getLogger(Utils.class);

    public Utils(){

    }

     public long getPendingSurvey(String p_userid, Resource base, RecSurvey objSurvey)
    {
        String idtm=base.getWebSiteId();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        long l_responseid = 0;
        long l_surveyid = 0;
        try
        {
            l_surveyid = objSurvey.getSurveyID();
            conn = SWBUtils.DB.getDefaultConnection();
            st = conn.prepareStatement("select responseid from sr_responseuser where surveyid=? and wbuser=? and idtm=? and finished=0 order by created desc");
            st.setLong(1,l_surveyid);
            st.setString(2,p_userid);
            st.setString(3,idtm);
            rs=st.executeQuery();
            if(rs.next())
            {
                l_responseid = rs.getLong("responseid");
            }
            if(rs!=null)rs.close();
            if(st!=null)st.close();
            if(conn!=null)conn.close();

        }
        catch(Exception e)
        {
            log.error( "Error while trying to load records from sr_responseuser, class - Utils, method - getPendingSurvey", e);
        }
        finally
        {
            rs=null;
            st=null;
            conn=null;
        }
        return l_responseid;
    }

    public int getReponseNumber(RecSurvey objSurvey, String p_userid, Resource base)
    {
        String idtm=base.getWebSiteId();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs =null;
        String s_last = null;
        int i_res = 0;
        int i_minscore = 0;
        int i_score = 0;
        long l_surveyid = 0;

        try
        {
            // Evaluates if user is logged, if not means anonym user
            if(!p_userid.equals("0_wb")){
                l_surveyid = objSurvey.getSurveyID();
                i_minscore = (int)java.lang.Math.round(objSurvey.getMinScore());

                conn = SWBUtils.DB.getDefaultConnection();
                st = conn.prepareStatement("select responseid, score from sr_responseuser where finished=1 and surveyid=? and wbuser=? and idtm=? order by created asc");
                st.setLong(1,l_surveyid);
                st.setString(2,p_userid);
                st.setString(3,idtm);
                rs=st.executeQuery();
                while(rs.next())
                {
                    i_res++;
                    i_score = rs.getInt("score");
                }
                if(rs!=null)rs.close();
                if(st!=null)st.close();
                if(conn!=null)conn.close();
                // Evaluates if user has a nother chance to answer
                if(i_minscore != 0 && i_res > 0)
                {
                    s_last = base.getAttribute("lastchance","0");
                    if(s_last.equals("1"))
                    {
                        if(i_score < i_minscore)
                        {
                            i_res--;
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            log.error("Error while trying to load records from sr_responseuser, class - Utils, method - getReponseNumber()",e);
        }
        finally
        {
            rs=null;
            st=null;
            conn=null;
        }
        return i_res;
    }

     public boolean hasAccess(long p_responseid, int p_time, String p_finished, String idtm)
    {
        boolean b_ret = false;
        RecResponseUser objResponse = null;
        Timestamp tm_current = null;
        Timestamp tm_created = null;
        long l_createdtime = 0;

        try{
            if(p_finished.equals("0")){
                if(p_time != 0){
                    objResponse = new RecResponseUser();
                    objResponse.setIdtm(idtm);
                    objResponse.setResponseID(p_responseid);
                    objResponse.load();
                    if(objResponse.getFinished() == 0){
                        b_ret = true;
                    }
                    else{
                        return false;
                    }

                    tm_current = new Timestamp(new java.util.Date().getTime());
                    tm_created = objResponse.getCreated();
                    l_createdtime = tm_created.getTime() + (60000 * p_time);
                    if(l_createdtime > tm_current.getTime()){
                        b_ret = true;
                    }
                    else{
                        b_ret = false;
                    }
                }
                else{
                    b_ret = true;
                }
            }
        }
        catch(Exception e){
            log.error( "Error while trying to get access information, class - Utils, method hasAccess", e);
        }
        return b_ret;
    }

    public HashMap getRemainingAnswer(long p_responseid, String idtm)
    {
        HashMap hm_remaining = new HashMap();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs =null;
        int i_index = 0;
        int i_secuential = 0;
        long l_questionid = 0;
        try
        {
            conn = SWBUtils.DB.getDefaultConnection();
            st = conn.prepareStatement("select secuentialid, questionid from sr_answer where responseid=? and idtm=? and mark = 1 and secuentialid > 0");
            st.setLong(1,p_responseid);
            st.setString(2, idtm);
            rs=st.executeQuery();
            while(rs.next())
            {
                i_secuential = rs.getInt("secuentialid");
                l_questionid = rs.getLong("questionid");
                hm_remaining.put(Integer.toString(i_index),Integer.toString(i_secuential) + "|" + Long.toString(l_questionid));
                i_index++;
            }
            if(rs!=null)rs.close();
            if(st!=null)st.close();
            if(conn!=null)conn.close();
        }
        catch(Exception e)
        {
            log.error("Error while trying to load records from sr_answer, class - Utils, method - getRemainingAnswer",e);
        }
        finally
        {
            rs=null;
            st=null;
            conn=null;
        }
        return hm_remaining;
    }

    public int getTotalQuestions(long p_responseid, String idtm )
    {
        int i_res = 0;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs =null;
        try
        {
            conn = SWBUtils.DB.getDefaultConnection();
            st = conn.prepareStatement("select answerid from sr_answer where secuentialid > 0 and responseid = ? and idtm=?");
            st.setLong(1,p_responseid);
            st.setString(2,idtm);
            rs=st.executeQuery();
            while(rs.next())
            {
                i_res++;
            }
            if(rs!=null)rs.close();
            if(st!=null)st.close();
            if(conn!=null)conn.close();
        }
        catch(Exception e)
        {
            log.error("Error while trying to load records from sr_answer, class - Utils, method - getTotalQuestions",e);
        }
        finally
        {
            rs=null;
            st=null;
            conn=null;
        }
        return i_res;
    }

    public long getCurrentAnswer(long p_responseid, int p_secuentialid, String idtm)
    {
        long l_res = 0;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs =null;
        try
        {
            conn = SWBUtils.DB.getDefaultConnection();
            st = conn.prepareStatement("select answerid from sr_answer where responseid=? and secuentialid=? and idtm=?");
            st.setLong(1,p_responseid);
            st.setInt(2,p_secuentialid);
            st.setString(3,idtm);
            rs=st.executeQuery();
            if(rs.next())
            {
                l_res = rs.getLong("answerid");
            }
            if(rs!=null)rs.close();
            if(st!=null)st.close();
            if(conn!=null)conn.close();
        }
        catch(Exception e)
        {
            log.error("Errorwhile trying to load records from sr_answer, class - Utils, method - getCurrentAnswer",e);
        }
        finally
        {
            rs=null;
            st=null;
            conn=null;
        }
        return l_res;
    }

    public int getMarkAnswer(long p_responseid, int p_secuentialid, String idtm)
    {
        int i_res = 0;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs =null;
        try
        {
            conn = SWBUtils.DB.getDefaultConnection();
            st = conn.prepareStatement("select mark from sr_answer where responseid=? and secuentialid=? and idtm=?");
            st.setLong(1,p_responseid);
            st.setInt(2,p_secuentialid);
            st.setString(3,idtm);
            rs=st.executeQuery();
            if(rs.next())
            {
                i_res = rs.getInt("mark");
            }
            if(rs!=null)rs.close();
            if(st!=null)st.close();
            if(conn!=null)conn.close();
        }
        catch(Exception e)
        {
            log.error("Error while trying to load records from sr_answer, class - Utils, method - getMarkAnswer",e);
        }
        finally
        {
            rs=null;
            st=null;
            conn=null;
        }
        return i_res;
    }

    public int getAnswerId(String pquestionid, String presponseid, String idtm)
    {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int answerid = 0;
        try
        {
            conn = SWBUtils.DB.getDefaultConnection();
            st = conn.prepareStatement("select answerid from sr_answer where questionid=? and responseid =? and idtm=?");
            st.setLong(1,Long.parseLong(pquestionid));
            st.setLong(2,Long.parseLong(presponseid));
            st.setString(3,idtm);
            rs=st.executeQuery();
            if(rs.next())
            {
                answerid = rs.getInt("answerid");
            }
            if(rs != null) rs.close();
            if(st != null) st.close();
            if(conn != null) conn.close();
        }
        catch (SQLException e)
        {
            log.error("error while trying to load records from sr_answer, class - Utils, method - getAnswerId",e);
        }
        finally
        {
            rs = null;
            st = null;
            conn = null;
        }

        return answerid;
    }

    public long startSurvey(SWBParamRequest paramsRequest, long p_surveyid, ArrayList p_arr, String idtm)
    {
        //ArrayList arr_question = new ArrayList();
        RecResponseUser objResponse = null;
        RecAnswer objAnswer = null;
        String s_question = null;
        User user = null;
        long l_responseid = 0;
        try
        {
            user = paramsRequest.getUser();
            String userid = user.getId()==null?"0":user.getId();
            objResponse = new RecResponseUser();
            objResponse.setIdtm(idtm);
            objResponse.setSurveyId(p_surveyid);
            objResponse.setUser(userid);
            objResponse.create();
            l_responseid = objResponse.getResponseID();

            //arr_question = getRandomQuestion(p_surveyid);

            for(int i = 0; i< p_arr.size(); i++){
                s_question = (String)p_arr.get(i);
                objAnswer = new RecAnswer();
                objAnswer.setIdtm(idtm);
                objAnswer.setQuestionid(Long.parseLong(s_question));
                objAnswer.setResponseid(l_responseid);
                objAnswer.setSecuentialid(i+1);
                objAnswer.create();
            }
        }
        catch(Exception e)
        {
            log.error( "Error on class - Utils, method -  startSurvey()", e);
        }
        return l_responseid;
    }

    public HashMap cargaHM(long surveyid, String idtm)
    {
        HashMap hm = new HashMap();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try
        {
            conn = SWBUtils.DB.getDefaultConnection();
            pst = conn.prepareStatement("" +
            "select parentquestion, sonquestion " +
            "from sr_relatedquestion " +
            "where surveyid = ? and idtm=?");
            pst.setLong(1,surveyid);
            pst.setString(2, idtm);
            rs = pst.executeQuery();
            while(rs.next())
            {
                hm.put(rs.getString("parentquestion"),rs.getString("sonquestion"));
            }
            if(rs != null) rs.close();
            if(pst != null) pst.close();
            if(conn != null) conn.close();
        }
        catch(Exception e)
        {
            log.error("Error while trying to load records from sr_relatedquestion, class - Utils, method - cargaHM",e);
        }
        finally
        {
            rs = null;
            pst = null;
            conn = null;
        }
        return hm;
    }

    public String getChildQuestion(long psurveyid, String idtm)
    {
        StringBuffer strinson = new StringBuffer();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String strtext = null;
        String strson = null;
        int icon = 0;
        int ison = 0;
        int ssonquestion = 0;
        try
        {
            strinson.append(" and sr_orderquestion.questionid not in(");
            con = SWBUtils.DB.getDefaultConnection();
            st = con.prepareStatement("select sonquestion from sr_relatedquestion where surveyid = ? and idtm=?");
            st.setLong(1,psurveyid);
            st.setString(2,idtm);
            rs = st.executeQuery();
            while(rs.next())
            {
                icon = 1;
                ssonquestion = rs.getInt("sonquestion");
                strinson.append(Integer.toString(ssonquestion));
                strinson.append(",");
            }
            if(rs != null) rs.close();
            if(st != null) st.close();
            if(con != null) con.close();
            if (icon == 1)
            {
                strson = strinson.toString();
                ison = strson.length();
                strtext = strson.substring(0,ison-1);
                strtext = strtext + ")";
            }
        }
        catch(Exception e)
        {
            log.error("Error while trying to load records from sr_relatedquestion, class - Utils, method - getChildQuestion",e);
        }
        finally
        {
            rs = null;
            st = null;
            con = null;
        }
        return strtext;
    }

    public String getRandomQuery(long p_responseid, String idtm)
        {
            StringBuffer sb_query = new StringBuffer();
            Connection con = null;
            PreparedStatement st = null;
            ResultSet rs = null;
            String s_query = null;
            String s_temp = null;
            int icon = 0;
            int i_len = 0;
            int i_question = 0;
            try
            {
                sb_query.append(" and sr_orderquestion.questionid in(");
                con = SWBUtils.DB.getDefaultConnection();
                st = con.prepareStatement("select questionid from sr_answer where secuentialid > 0 and responseid = ? and idtm=?");
                st.setLong(1,p_responseid);
                st.setString(2,idtm);
                rs = st.executeQuery();
                while(rs.next())
                {
                    icon = 1;
                    i_question = rs.getInt("questionid");
                    sb_query.append(Integer.toString(i_question));
                    sb_query.append(",");
                }
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(con != null) con.close();
                if (icon == 1)
                {
                    s_temp = sb_query.toString();
                    i_len = s_temp.length();
                    s_query = s_temp.substring(0,i_len-1);
                    s_query = s_query + ")";
                }
            }
            catch(Exception e)
            {
                log.error("Error while trying to load records from sr_relatedquestion, class - Utils, method - getRandomQuery",e);
            }
            finally
            {
                rs = null;
                st = null;
                con = null;
            }
            return s_query;
        }

    public ArrayList getRandomQuestions(int p_len, long p_surveyid, String idtm){
        HashMap hm_pre = null;
        HashMap hm_pos = null;
        ArrayList arr_question = new ArrayList();
        StringBuffer sb_son = new StringBuffer();
        StringBuffer sb_selected = new StringBuffer();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String s_son = null;
        String strsonfin = null;
        String s_sql = null;
        String s_selected = null;
        String s_final = null;
        int i_sonquestion = 0;
        int i_questionid = 0;
        int i_num = 0;
        int i_showques = 0;
        int i_tot = 0;
        int i_son = 0;
        int i_ran = 0;
        int i_con = 0;
        int i_len = 0;

        i_showques = p_len;
        try {
            conn = SWBUtils.DB.getDefaultConnection();
            /*Obtiene las preguntas quer son hijas*/
            s_sql = "select sonquestion from sr_relatedquestion where surveyid = ? and idtm=? ";
            st =conn.prepareStatement(s_sql);
            st.setLong(1,p_surveyid);
            st.setString(2, idtm);
            rs=st.executeQuery();
            sb_son.append(" and sr_orderquestion.questionid not in(");
            while(rs.next()){
                i_con = 1;
                i_sonquestion = rs.getInt("sonquestion");
                sb_son.append(Integer.toString(i_sonquestion));
                sb_son.append(",");
            }

            /*Obtiene las preguntas ignorando las que son hijas, utiliza la sentencia generada anteriormente*/
            s_sql="select sr_orderquestion.questionid from sr_orderquestion where isactive = 1 and surveyid = ? and idtm=? ";
            if (i_con == 1){
                s_son = sb_son.toString();
                i_son = s_son.length();
                strsonfin = s_son.substring(0,i_son-1);
                strsonfin = strsonfin + ")";
                s_sql = s_sql + strsonfin;
            }
            //s_sql = s_sql + " order by sr_orderquestion.subjectid, sr_orderquestion.ordernum";
            hm_pre = new HashMap();
            st =conn.prepareStatement(s_sql);
            st.setLong(1,p_surveyid);
            st.setString(2,idtm);
            rs=st.executeQuery();
            while(rs.next()){
                i_questionid = rs.getInt("questionid");
                hm_pre.put(Integer.toString(i_num),Integer.toString(i_questionid));
                i_num++;
            }
            if(rs!=null)rs.close();
            if(st!=null)st.close();

            /*De las preguntas obtenidas obtiene las aleatorias o todas si as� se requiere y genera otra sentencia*/
            sb_selected.append(" and sr_orderquestion.questionid in(");
            if(i_showques > 0 && i_num > i_showques){
                i_ran = 0;
                hm_pos = new HashMap();
                while(i_tot < i_showques){
                    i_ran = (int)(Math.random() * i_num);
                    if(!hm_pos.containsKey(Integer.toString(i_ran))){
                        hm_pos.put(Integer.toString(i_ran),hm_pre.get(Integer.toString(i_ran)));
                        sb_selected.append((String)hm_pre.get(Integer.toString(i_ran)));
                        sb_selected.append(",");
                        i_tot++;
                    }
                }
            }
            else{
                for(int i=0; i<hm_pre.size();i++){
                    sb_selected.append((String)hm_pre.get(Integer.toString(i)));
                    sb_selected.append(",");
                }
            }
            s_selected = sb_selected.toString();
            i_len = s_selected.length();
            s_final = s_selected.substring(0,i_len-1);
            s_final = s_final + ")";

            /*Obtiene las preguntas en el orden asignado en la tabla sr_orderquestion*/
            s_sql = "select sr_orderquestion.questionid from sr_orderquestion where surveyid = ? and idtm=? ";
            s_sql = s_sql + s_final;
            s_sql = s_sql + " order by sr_orderquestion.subjectid, sr_orderquestion.ordernum";
            st =conn.prepareStatement(s_sql);
            st.setLong(1,p_surveyid);
            st.setString(2,idtm);
            rs=st.executeQuery();
            while(rs.next()){
                i_questionid = rs.getInt("questionid");
                arr_question.add(Integer.toString(i_questionid));
            }
            if(rs!=null)rs.close();
            if(st!=null)st.close();
            if(conn!=null)conn.close();
        }
        catch (SQLException e){
            log.error("Error while trying to load records from sr_relatedquestion on sr_orderquestion, class - SurveyRandom, method - getRandomQuestions ", e);
        }
        finally{
            rs=null;
            st=null;
            conn=null;
        }
        return arr_question;
    }


     public Hashtable getAllQuestions(long p_surveyid, String pquery, HashMap hm, long p_responseid, String idtm)
    {
        Hashtable ht = new Hashtable();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        StringBuffer strsql = new StringBuffer();
        Utils util = new Utils();
        //String strson = null;
        int i_sec = 0;
        int i_aux = 0;
        int i_num = 0;
        //strson = util.getChildQuestion(p_surveyid);

        try{
            strsql.append("select sr_answer.secuentialid, sr_orderquestion.subjectid, sr_orderquestion.questionid, sr_orderquestion.ordernum ");
            strsql.append("from sr_orderquestion, sr_answer ");
            strsql.append("where sr_answer.questionid = sr_orderquestion.questionid ");
            strsql.append("and surveyid = ? ");
            strsql.append("and sr_answer.responseid = ? and sr_answer.idtm=? ");
            strsql.append("and sr_answer.secuentialid > 0 ");
            strsql.append("order by sr_answer.secuentialid");

            con = SWBUtils.DB.getDefaultConnection();
            st = con.prepareStatement(strsql.toString());
            st.setLong(1,p_surveyid);
            st.setLong(2,p_responseid);
            st.setString(3,idtm);
            rs = st.executeQuery();

            while(rs.next())
            {
                if(!hm.containsValue(Long.toString(rs.getLong("questionid"))))
                {
                    int i_secuentialid = rs.getInt("secuentialid");
                    int sectionid = rs.getInt("subjectid");
                    int questionid = rs.getInt("questionid");
                    if(sectionid != i_aux)
                    {
                        i_num++;
                        i_aux = sectionid;
                    }
                    String result = Integer.toString(sectionid) +"|"+Integer.toString(questionid)+"|"+Integer.toString(i_num);
                    //ht.put( Integer.toString(i_sec),result);
                    ht.put( Integer.toString(i_secuentialid),result);
                    i_sec++;
                }
            }
            if(rs != null) rs.close();
            if(st != null) st.close();
            if(con != null) con.close();
        }
        catch(Exception e)
        {
            log.error("Error while trying to load records from sr_orderquestion, class - Mainsurvey, method - getAllQuestions",e);
        }
        finally
        {
            rs = null;
            st = null;
            con = null;
        }
        return ht;
    }

    public Hashtable sortHashTable(String pquestionid, String idtm)
    {
        Hashtable htsort = new Hashtable();
        Hashtable htque = new Hashtable();
        org.w3c.dom.Document docxml;
        Element resource;
        RecQuestion objQue = null;
        String xmlque = null;
        objQue = new RecQuestion();
        objQue.setIdtm(idtm);
        objQue.setQuestionID(Integer.parseInt(pquestionid));
        objQue.load();
        xmlque = objQue.getStringXML();
        // Obtiene los valores de la pregunta
        try
        {
            if (xmlque != null)
            {
                docxml = SWBUtils.XML.xmlToDom(xmlque);
                if (docxml!=null)
                {
                    resource = (Element) docxml.getFirstChild();
                    org.w3c.dom.NodeList nodes = resource.getChildNodes();
                    for (int i = 0; i < nodes.getLength(); i++)
                    {
                        org.w3c.dom.Node node = nodes.item(i);
                        String qiden = node.getAttributes().getNamedItem("id").getNodeValue();
                        String qrefer = node.getAttributes().getNamedItem("refer").getNodeValue();
                        htque.put(qiden,qrefer);
                    }
                }
            }
        }
        catch(Exception e)
        {
            log.error( "Error while trying to sort HashTable, class - Utils, method - sortHashTable",e);
        }

        // Ordena el hashtable.
        Vector v = new Vector(htque.keySet());
        Collections.sort(v);

        // Muestra el hashtable ordenado.
        for (Enumeration e = v.elements(); e.hasMoreElements();)
        {
            String key = (String)e.nextElement();
            String val = (String)htque.get(key);
            htsort.put(key,val);
        }
        return htsort;
    }

    public HashMap getUserOptionAnswer(String pquestionid, String presponseid, String idtm)
    {
        HashMap hmans = null;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String xmlans = null;
        String val = null;
        org.w3c.dom.Document docxml;
        Element resource;
        try
        {
           hmans = new HashMap();
           if(!presponseid.equalsIgnoreCase("0"))
           {
                conn = SWBUtils.DB.getDefaultConnection();
                st = conn.prepareStatement("select stringxml from sr_answer where questionid=? and responseid =? and idtm=? and stringxml NOT NULL");
                st.setString(1,pquestionid);
                st.setString(2,presponseid);
                st.setString(3, idtm);
                rs = st.executeQuery();
                //System.out.println("select stringxml from sr_answer where questionid="+pquestionid+" and responseid ="+presponseid+" and idtm="+idtm);
                if(rs.next())
                {
//                    String temp = rs.getString("stringxml");
//                    if(null!=temp)
                        xmlans = SWBUtils.IO.readInputStream(rs.getAsciiStream("stringxml"));
                    //System.out.println("Valor: .... "+temp+" ---- "+xmlans);
                }
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(conn != null) conn.close();
                if(xmlans != null)
                {
                    docxml = SWBUtils.XML.xmlToDom(xmlans);
                    if (docxml != null)
                    {
                        resource = (Element) docxml.getFirstChild();
                        org.w3c.dom.NodeList nodes = resource.getChildNodes();
                        val = "";
                        for (int i = 0; i < nodes.getLength(); i++)
                        {
                            org.w3c.dom.Node node = nodes.item(i);
                            val = node.getFirstChild().getNodeValue();
                            hmans.put(Integer.toString(i),val);
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            log.error( "Error while trying to load records from sr_answer, class - Utils, method - getUserOptionAnswer",e);
        }
        finally
        {
            rs = null;
            st = null;
            conn = null;
        }
        return hmans;
    }

    public long getParentQuestion(String p_questionid, long p_surveyid, String idtm){
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        long l_result = 0;
        try{
            conn = SWBUtils.DB.getDefaultConnection();
            st = conn.prepareStatement("select parentquestion from sr_relatedquestion where sonquestion=? and surveyid =? and idtm=?");
            st.setLong(1,Long.parseLong(p_questionid));
            st.setLong(2,p_surveyid);
            st.setString(3,idtm);
            rs = st.executeQuery();
            if(rs.next())
            {
                l_result = rs.getLong("parentquestion");
            }
            if(rs != null) rs.close();
            if(st != null) st.close();
            if(conn != null) conn.close();
        }
        catch(Exception e)
        {
            log.error( "Error while trying to load records from sr_relatedquestion, class - Utils, method - isChildQuestion",e);
        }
        finally
        {
            rs = null;
            st = null;
            conn = null;
        }
        return l_result;
    }

}
