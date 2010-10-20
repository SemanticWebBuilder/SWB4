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

package com.infotec.wb.resources.survey.db;

import java.sql.*;

import java.util.StringTokenizer;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 * Esta clase crea un objeto para el manejo de la base de datos
 *
 * This class create an object to manage database table
 *
 * User: Juan Antonio Fernández Arias
 * 
 */
public class RecQuestion
{

    private static Logger log = SWBUtils.getLogger(RecQuestion.class);

    private long questionid;
    private String idtm;
    private String question;
    private long codeid;
    private long validate;
    private long required;
    private long validoptions;
    private long controlid;
    private long freqanswerid;
    private long firstfreqanswerid;
    private String stringxml;
    private Timestamp created;
    private Timestamp lastupdate;
    private int isreuse;
//    private int isactive;
//    private int isdata;
    private int categoryid;
    private String instruction;
    DocumentBuilderFactory dbf=null;
    DocumentBuilder db=null;
    Document doc;
    
    /**
     * Constructor
     */    
    public RecQuestion()
    {
        this.questionid = 0;
        this.idtm=null;
        this.question= null;
        this.codeid = 0;
        this.validate = 0;
        this.required = 0;
        this.validoptions = 0;
        this.controlid = 0;
        this.freqanswerid = 0;
        this.firstfreqanswerid = 0;
        this.stringxml = null;
        this.created = null;
        this.lastupdate = null;
        this.isreuse = 1;
//        this.isactive = 1;
//        this.isdata = 0;
        this.categoryid = 0;
        this.instruction = null;
        try
        {
            dbf=DocumentBuilderFactory.newInstance();
            db=dbf.newDocumentBuilder();
        }
        catch(Exception e)
        {
            log.error( "Error en el dom",e);
        }
    }
    
    /*Se carga el objeto con los valores de la BD*/
    /**
     * Load information from database
     */    
    public void load()
    {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs =null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection();
            pst = con.prepareStatement("select * from sr_question where questionid = ? and idtm=?");
            pst.setLong(1, questionid);
            pst.setString(2,idtm);
            rs = pst.executeQuery();
            if(rs.next())
            {
                questionid = rs.getLong("questionid");
                question = rs.getString("question"); //SWBUtils.IO.readInputStream(rs.getAsciiStream("question"));
                //rs.getString("question");
                instruction = rs.getString("instruction");
                codeid = rs.getLong("codeid");
                validate = rs.getLong("validated");
                required = rs.getLong("required");
                validoptions = rs.getLong("validoptions");
                controlid = rs.getLong("controlid");
                freqanswerid = rs.getLong("freqanswerid");
                stringxml = rs.getString("stringxml"); //SWBUtils.IO.readInputStream(rs.getAsciiStream("stringxml"));
                categoryid = rs.getInt("categoryid");
                isreuse = rs.getInt("isreuse");
//                isactive = rs.getInt("isactive");
//                isdata = rs.getInt("isdata");
                created = rs.getTimestamp("created");
                lastupdate = rs.getTimestamp("lastupdate");
                
                if(instruction==null) instruction="";
            }
            else
            {
                log.error("No se encontró el registro en la DB - srquestion con id = "+questionid);
            }
            if(rs!=null)rs.close();
            if(pst!=null)pst.close();
            if(con!=null)con.close();
        }
        catch(Exception e)
        {
            log.error(e.getMessage(), e);
        }
        finally
        {
            rs=null;
            pst=null;
            con=null;
        }
    }
    
    /**
     * Gets questionid value
     * @return a long value
     */    
    public long getQuestionID()
    {
        return this.questionid;
    }
    
    /**
     * Set questionid value
     * @param pquestionid input int value
     */    
    public void setQuestionID(int pquestionid)
    {
        this.questionid=pquestionid;
    }
    
    /**
     * Gets codeid value
     * @return a long value
     */    
    public long getCodeID()
    {
        return this.codeid;
    }
    
    /**
     * Set codeid value
     * @param pcodeid input long value
     */    
    public void setCodeID(long pcodeid)
    {
        this.codeid = pcodeid;
    }
    
    /**
     * Gets question value
     * @return a string value
     */    
    public String getQuestion()
    {
        return this.question.trim();
    }
    
    /**
     * Set question value
     * @param pquestion input string value
     */    
    public void setQuestion(String pquestion)
    {
        this.question = pquestion.trim();
    }
    
    /**
     * Gets validate value
     * @return a long value
     */    
    public long getValidate()
    {
        return this.validate;
    }
    
    /**
     * Set validate value
     * @param pvalidate input long value
     */    
    public void setValidate(long pvalidate)
    {
        this.validate = pvalidate;
    }
    
    /**
     * Gets required value
     * @return a long value
     */    
    public long getRequired()
    {
        return this.required;
    }
    
    /**
     * Set requiered value
     * @param prequired input long value
     */    
    public void setRequired(long prequired)
    {
        this.required = prequired;
    }
    
    /**
     * Gets validoptions values
     * @return a long value
     */    
    public long getValidOptions()
    {
        return this.validoptions;
    }
    
    /**
     * Set validoptions value
     * @param pvalidoptions input long value
     */    
    public void setValidOptions(long pvalidoptions)
    {
        this.validoptions = pvalidoptions;
    }
    
    /**
     * Gets controlid value
     * @return a long value
     */    
    public long getControlID()
    {
        return this.controlid;
    }
    
    /**
     * Set controlid value
     * @param pcontrolid input long value
     */    
    public void setControlID(long pcontrolid)
    {
        this.controlid = pcontrolid;
    }
    
    /**
     * Gets freqanswerid value
     * @return a long value
     */    
    public long getFreqAnswerID()
    {
        return this.freqanswerid;
    }
    
    /**
     * Set freqanswerid value
     * @param pfreqanswerid input long value
     */    
    public void setFreqAnswerID(long pfreqanswerid)
    {
        this.freqanswerid = pfreqanswerid;
    }
    
    /**
     * Gets instruction value
     * @return a string value
     */    
    public String getInstruction()
    {
        return this.instruction.trim();
    }
    
    /**
     * Set instruction value
     * @param pinstruction input string value
     */    
    public void setInstruction(String pinstruction)
    {
        this.instruction = pinstruction.trim();
    }
    
    /**
     * Gets 1stfreqanswerid value
     * @return a long value
     */    
    public long get1stFreqAnswerID()
    {
        return this.firstfreqanswerid;
    }
    
    /**
     * Gets stringxml value
     * @return a string value
     */    
    public String getStringXML()
    {
        return this.stringxml;
    }
    
    /**
     * Set stringxml value
     * @param pstringxml input string value
     */    
    public void setStringXML(String pstringxml)
    {
        this.stringxml = pstringxml;
    }
    /**
     * Creates an string of xml attributes
     * @param pxml input string value
     * @param answer input string value
     * @return a string value
     */    
    public String paramXMLtoStringXML(String pxml,String answer)
    {
        String strXML = new String("");
        try
        {
            org.w3c.dom.Document  docxml = db.newDocument();
            org.w3c.dom.Element resource = docxml.createElement("resource");
            org.w3c.dom.Element opcion = docxml.createElement("option");
            docxml.appendChild(resource);
            int j;
            StringTokenizer Token1= new StringTokenizer(pxml,"|");
            while(Token1.hasMoreTokens())
            {
                String strToken =   Token1.nextToken();
                StringTokenizer Token2=new StringTokenizer(strToken,"-");
                j=0;
                while(Token2.hasMoreTokens())
                {
                    String strElement = Token2.nextToken();
                    j++;
                    if(j==1) opcion.setAttribute("refer",strElement);
                    if(j==2) opcion.setAttribute("value",strElement);
                    if(j==3)
                    {
                        opcion.setAttribute("id",strElement);
                        if(answer!=null)
                        {
                            opcion.appendChild(docxml.createTextNode(answer));
                        }
                        j=0;
                        org.w3c.dom.Element newOp = (Element)opcion.cloneNode(true);
                        resource.appendChild(newOp);
                    }
                }
            }
            strXML = SWBUtils.XML.domToXml(docxml);
        }
        catch(Exception e)
        {log.error("Error al crear las opciones en la pregunta XML DB - srquestion.",e);}
        
        return strXML;
        
    }
    
    /**
     * Gets created value
     * @return a timestamp value
     */    
    public java.sql.Timestamp getCreated()
    {
        return this.created;
    }
    
    /**
     * Gets lastupdate value
     * @return a timestamp value
     */    
    public java.sql.Timestamp getLastUpdate()
    {
        return this.lastupdate;
    }
    
    /**
     * Gets isreuse value
     * @return an int value
     */    
    public int getIsReUse()
    {
        return this.isreuse;
    }
    
    /**
     * Set isreuse value
     * @param pisreuse input int value
     */    
    public void setIsReUse(int pisreuse)
    {
        this.isreuse = pisreuse;
    }
    
    /**
     * Gets categoryid value
     * @return an int value
     */    
    public int getCategoryID()
    {
        return this.categoryid;
    }
    
    /**
     * Set categoryid value
     * @param pcategoryid input int value
     */    
    public void setCategoryID(int pcategoryid)
    {
        this.categoryid = pcategoryid;
    }
    
    /**
     * Gets category value
     * @return an RecCategory object
     */    
    public RecCategory getCategory()
    {
        RecCategory oRGQ = null;
        try
        {
            oRGQ = new RecCategory();
            oRGQ.setCategoryid(categoryid);
            oRGQ.setIdtm(idtm);
            oRGQ.load();
        }
        catch(Exception e)
        {
            log.error(e);
        }
        return oRGQ;
    }
    
    /**
     * Gets a list of catalog type values
     * @param ptextid input int value
     * @param evento input string value
     * @return a string value
     */    
    public String getCatalogTypeList(int ptextid, String evento)
    {
        if (Integer.toString((int)ptextid).equals(null)) ptextid=0;
        if (evento.equals(null)) evento="";
        StringBuffer ret1=new StringBuffer();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection();
            st = con.prepareStatement("select codeid, title from sr_validatecode where idtm=?");
            st.setString(1, idtm);
            rs = st.executeQuery();
            ret1.append("<select name=\"textid\" "+evento+">");
            while(rs.next())
            {
                String titulo =rs.getString("title");
                long txtID =rs.getLong("codeid");
                String select ="";
                if (ptextid==txtID) select ="selected";
                ret1.append("<option value=\""+txtID+"\" "+select+">"+titulo+"</option>\n");
            }
            if(rs!=null)rs.close();
            if(st!=null)st.close();
            if(con!=null)con.close();
            ret1.append("</select>");
        }
        catch(Exception e)
        { log.error("Error while trying to load a record from sr_validatecode",e); }
        finally
        {
            rs=null;
            st=null;
            con=null;
        }
        return ret1.toString();
    }
    
    /**
     * Gets a list of group question values
     * @param pgroupqid input int value
     * @return a string values
     */    
    public String getGroupQuestionList(int pgroupqid)
    {
        if (Integer.toString((int)pgroupqid).equals(null)) pgroupqid=0;
        StringBuffer ret1=new StringBuffer();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs =null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection();
            st = con.prepareStatement("select categoryid,title from sr_category where idtm=?");
            st.setString(1, idtm);
            rs = st.executeQuery();
            ret1.append("<select name=\"groupqid\">");
            ret1.append("<option value=\"0\">--Todas--</option>");
            while(rs.next())
            {
                String titulo =rs.getString("title");
                long txtID =rs.getLong("categoryid");
                String select ="";
                if (pgroupqid==txtID) select ="selected";
                ret1.append("<option value=\""+txtID+"\" "+select+">"+titulo+"</option>\n");
            }
            if(rs!=null)rs.close();
            if(st!=null)st.close();
            if(con!=null)con.close();
            ret1.append("</select>");
        }
        catch(Exception e)
        { log.error("Error al consultar DB - Catalog Type",e); }
        finally
        {
            rs=null;
            st=null;
            con=null;
        }
        return ret1.toString();
    }
    
    /**
     * Gets validatecode value
     * @return a RecValidateCode object
     */    
    public RecValidateCode getValidateCode()
    {
        RecValidateCode oCT =null;
        try
        {
            oCT = new RecValidateCode();
            oCT.setCodeid((int)codeid);
            oCT.setIdtm(idtm);
            oCT.load();
        }catch(Exception e)
        {
            log.error(e);
        }
        return oCT;
    }
    
    /**
     * Gets a list of control catalog values
     * @param pcontrolid input int value
     * @param evento input string value
     * @return a string value
     */    
    public String getControlCatalogList(int pcontrolid,String evento)
    {
        if (Integer.toString((int)pcontrolid).equals(null)) pcontrolid=-1;
        if(evento==null)evento="";
        StringBuffer ret1=new StringBuffer();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection();
            st = con.prepareStatement("select controlid,title from sr_controlcatalog where idtm=?");
            st.setString(1,idtm);
            rs = st.executeQuery();
            
            ret1.append("<select name=\"controlid\" "+evento+">");
            while(rs.next())
            {
                String titulo =rs.getString("title");
                long txtID =rs.getLong("controlid");
                String select ="";
                if (pcontrolid==txtID) select ="selected";
                ret1.append("<option value=\""+txtID+"\" "+select+">"+titulo+"</option>\n");
            }
            if(rs!=null)rs.close();
            if(st!=null)st.close();
            if(con!=null)con.close();
            ret1.append("</select>");
        }
        catch(Exception e)
        { log.error("Error al consultar DB - Catalog Type",e); }
        finally
        {
            rs=null;
            st=null;
            con=null;
        }
        return ret1.toString();
    }
    
    /**
     * Gets controlcatalog value
     * @return a RecControlCatalog value
     */    
    public RecControlCatalog getControlCatalog()
    {
        RecControlCatalog oCC =null;
        try
        {
            oCC = new RecControlCatalog();
            oCC.setControlid((int)controlid);
            oCC.setIdtm(idtm);
            oCC.load();
        }
        catch(Exception e)
        {
            log.error(e);
        }
        return oCC;
    }
    
    /**
     * Gets a list of freqanswers values
     * @param pfreqanswerid input int value
     * @param evento input string value
     * @return a string value
     */    
    public String getFreqAnswerList(int pfreqanswerid,String evento)
    {
        if (Integer.toString((int)pfreqanswerid).equals(null)) pfreqanswerid=-1;
        if (evento==null) evento="";
        StringBuffer ret1=new StringBuffer();
        boolean entro = false;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs =  null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection();
            st = con.prepareStatement("select freqanswerid,title from sr_freqanswer where idtm=?");
            st.setString(1,idtm);
            rs = st.executeQuery();
            ret1.append("<select name=\"freqanswerid\" "+evento+">");
            while(rs.next())
            {
                String titulo =rs.getString("title");
                long txtID =rs.getLong("freqanswerid");
                if(!entro)
                {
                    firstfreqanswerid=txtID;
                    entro =true;
                }
                String select ="";
                if (pfreqanswerid==txtID) select ="selected";
                ret1.append("<option value=\""+txtID+"\" "+select+">"+titulo+"</option>\n");
            }
            ret1.append("</select>");
            if(rs!=null)rs.close();
            if(st!=null)st.close();
            if(con!=null)con.close();
        }
        catch(Exception e)
        { log.error("Error al consultar DB - Freq Answer",e); }
        finally
        {
            rs=null;
            st=null;
            con=null;
        }
        return ret1.toString();
    }
    
    /**
     * Gets a freqanswer value
     * @return a RecFreqAnswer object
     */    
    public RecFreqAnswer getFreqAnswer()
    {
        RecFreqAnswer oFA =null;
        try
        {
            oFA = new RecFreqAnswer();
            oFA.setFreqanswerid(freqanswerid);
            oFA.setIdtm(idtm); 
            oFA.load();
        }
        catch(Exception e)
        {
            log.error(e);
        }
        return oFA;
    }
    
    /**
     * Creates a record if questionid == 0, updates a record if questionid != 0
     * @throws Exception an Exception
     * @return a boolean value
     */
    public boolean create() throws Exception
    {
        boolean res=false;
        Connection con=null;
        PreparedStatement stmt=null;
        ResultSet rs=null;
        PreparedStatement pst =null;
        
        try
        {
            if(questionid==0&&null!=idtm)
            {
                created=new Timestamp(new java.util.Date().getTime());
                lastupdate=new Timestamp(new java.util.Date().getTime());
                con=SWBUtils.DB.getDefaultConnection();
                stmt=con.prepareStatement("select max(questionid) from sr_question where idtm=?");
                stmt.setString(1,idtm);
                rs= stmt.executeQuery();
                if(rs.next())
                {
                    questionid=rs.getLong(1)+1;
                }else questionid=1;
                if(rs!=null)rs.close();
                if(stmt!=null)stmt.close();
                if(instruction==null) instruction=" ";
                pst = con.prepareStatement("insert into sr_question (questionid,idtm,question,codeid,validated,required,validoptions,controlid,freqanswerid,stringxml,created,lastupdate,categoryid,isreuse,instruction) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pst.setLong(1, questionid);
                pst.setString(2,idtm);
                pst.setString(3,question);
//                if(question==null)
//                    pst.setString(3,null);
//                else
//                    pst.setAsciiStream(3,SWBUtils.IO.getStreamFromString(question),question.length());
                pst.setLong(4,codeid);
                pst.setLong(5,validate);
                pst.setLong(6,required);
                pst.setLong(7,validoptions);
                pst.setLong(8,controlid);
                pst.setLong(9,freqanswerid);
                pst.setString(10,stringxml);
//                if(stringxml==null)
//                    pst.setString(10,null);
//                else
//                    pst.setAsciiStream(10,SWBUtils.IO.getStreamFromString(stringxml),stringxml.length());
                
                //pst.setString(10, stringxml);
                pst.setTimestamp(11, created);
                pst.setTimestamp(12, lastupdate);
                pst.setInt(13,categoryid);
                pst.setInt(14,isreuse);
//                pst.setInt(15,isactive);
//                pst.setInt(16,isdata);
                pst.setString(15,instruction);
                pst.executeUpdate();
                res = true;
                if(pst!=null)pst.close();
                if(con!=null)con.close();
            }
        }
        catch(Exception e)
        {
            log.error("No fue posible crear el elemento en srquestion...\n"+e.getMessage(),e);
        }
        finally
        {
            rs=null;
            stmt=null;
            pst=null;
            con=null;
        }
        return res;
        
    }
    
    /**
     * Updates a record on database
     * @throws Exception an Exception
     * @return a boolean value
     */    
    public boolean update() throws Exception
    {
        boolean res=false;
        Connection con=null;
        PreparedStatement pst =null;
        try
        {
            lastupdate=new Timestamp(new java.util.Date().getTime());
            con=SWBUtils.DB.getDefaultConnection();
            pst = con.prepareStatement("" +
            "update sr_question set " +
            "question=?, " +
            "codeid=?, " +
            "validated=?, " +
            "required=?, " +
            "validoptions=?, " +
            "controlid=?, " +
            "freqanswerid=?, " +
            "stringxml=?, " +
            "created=?, " +
            "lastupdate=?, " +
            "categoryid=?, " +
            "isreuse=?, " +
//            "isactive=?, " +
//            "isdata=?, " +
            "instruction=? " +
            "where questionid = ? and idtm=?");
            //pst.setString(1,question);
            pst.setAsciiStream(1,SWBUtils.IO.getStreamFromString(question),question.length());
            pst.setLong(2,codeid);
            pst.setLong(3,validate);
            pst.setLong(4,required);
            pst.setLong(5,validoptions);
            pst.setLong(6,controlid);
            pst.setLong(7,freqanswerid);
            pst.setString(8,stringxml);
//            if(stringxml==null)
//                pst.setString(8,null);
//            else
//                pst.setAsciiStream(8,SWBUtils.IO.getStreamFromString(stringxml),stringxml.length());
            
            //pst.setString(8, stringxml);
            pst.setTimestamp(9,created);
            pst.setTimestamp(10,lastupdate);
            pst.setInt(11,categoryid);
            pst.setInt(12,isreuse);
//            pst.setInt(13,isactive);
//            pst.setInt(14,isdata);
            pst.setString(13,instruction);
            pst.setLong(14, questionid);
            pst.setString(15,idtm);
            pst.executeUpdate();
            res = true;
            if(pst!=null)pst.close();
            if(con!=null)con.close();
        }
        catch(Exception e)
        {
            log.error("No se pudo actualizar el registro srquestion "+e.getMessage(), e);
        }
        finally
        {
            pst=null;
            con=null;
        }
        
        return res;
    }
    
    /**
     * Removes a record from database
     * @return a boolean value
     */    
    public boolean remove()
    {
        boolean deleted=false;
        try
        {
            Connection conn = SWBUtils.DB.getDefaultConnection();
            String query = "delete from sr_question where questionid=? and idtm=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setLong(1,questionid);
            pst.setString(2,idtm);
            pst.executeUpdate();
            pst.close();
            pst=null;
            conn.close();
            conn=null;
            deleted=true;
        }
        
        catch (Exception e)
        {
            log.error("Error while trying to remove a question",e);
        }
        return deleted;
    }
    
    /**
     * Getter for property idtm.
     * @return Value of property idtm.
     */
    public java.lang.String getIdtm()
    {
        return idtm;
    }
    
    /**
     * Setter for property idtm.
     * @param idtm New value of property idtm.
     */
    public void setIdtm(java.lang.String idtm)
    {
        this.idtm = idtm;
    }
    
}
