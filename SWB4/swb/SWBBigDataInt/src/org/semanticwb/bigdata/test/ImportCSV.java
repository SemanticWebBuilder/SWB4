/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bigdata.test;

import com.bigdata.rdf.internal.XSD;
import com.bigdata.rdf.sail.BigdataSail;
import com.bigdata.rdf.sail.BigdataSailRepository;
import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;

/**
 *
 * @author javier.solis.g
 */
public class ImportCSV
{
    BigdataSail sail=null;
    Repository repo=null;

    boolean debug = false;

    public InputStream getFile(String file) throws FileNotFoundException
    {
        System.out.println("getFile:" + file);
        //file = "C:/import/" + file;
        file = "/programming/proys/hackaton_data/siippg/" + file;
        InputStream in = new FileInputStream(file);
        return in;
    }

    public String filterString(String txt)
    {
        if (debug)
        {
            System.out.print("Debug (in):" + txt + " ");
        }

        if (txt != null)
        {
            if (txt.trim().equals("_NULL_") || txt.trim().equals("NULL"))
            {
                txt = null;
            } else
            {
                txt = txt.trim();
                if (txt.startsWith("\""))
                {
                    txt = txt.substring(1);
                }
                if (txt.endsWith("\""))
                {
                    txt = txt.substring(0, txt.length() - 1);
                }
            }
        }

        if (debug)
        {
            if (txt != null)
            {
                System.out.print("(out):" + txt + "\n");
            } else
            {
                System.out.print("(out):null\n");
            }

        }

        return txt;
    }

    public Date parseDate(String date)
    {
        if (date == null || date.equals("NULL"))
        {
            return null;
        }
        try
        {
            StringTokenizer st = new StringTokenizer(date, "/- ");
            String d = filterString(st.nextToken());
            String m = filterString(st.nextToken());
            String y = filterString(st.nextToken());
            return new Date(parseInt(y) - 1900, parseInt(m) - 1, parseInt(d));
        } catch (Exception e)
        {
        }
        return null;
    }

    public String prepareLine(String lin)
    {
        if (lin.startsWith("\t"))
        {
            lin = "_NULL_"+lin;
        }
        if (lin.endsWith("\t"))
        {
            lin = lin + "_NULL_";
        }
        while (lin.indexOf("\t\t") != -1)
        {
            lin = lin.replace("\t\t", "\t_NULL_\t");
        }
        return lin;
    }

    public double parseDouble(String data)
    {
        if (data == null)
        {
            data = "0";
        }
        data = data.replace(',', '.');
        double ret = 0;
        try
        {
            ret = Double.parseDouble(data);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    public int parseInt(String data)
    {
        if (data == null)
        {
            data = "0";
        }
        data=data.replace(",", "");
        int ret = 0;
        try
        {
            ret = Integer.parseInt(data);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    public String readLine(InputStream in) throws IOException
    {
        StringBuilder ret = new StringBuilder();
        while (true)
        {
            int ch = in.read();
            if (ch == -1)
            {
                break;
            }
//            if (ch == '\r')
//            {
//                int ch2 = in.read();
//                if (ch2 == -1)
//                {
//                    break;
//                }
//                if (ch2 == '\n')
//                {
//                    break;
//                }
//            }
            if (ch == 13)
            {
                break;
            }
            ret.appendCodePoint(ch);
            //System.out.print(((char)ch)+"-"+ch+" ");
        }
        String r = ret.toString();
        if (r.length() == 0)
        {
            r = null;
        }
        //System.out.println("Line:"+r+"-");
        return r;
    }
    
    public Statement getStringStatement(String sub, String prop, String value)
    {
        Resource s = new URIImpl(sub);
        URI p = new URIImpl(prop);        
        Value o = new LiteralImpl(value);
        return new StatementImpl(s, p, o);
    }
    
    public Statement getLiteralStatement(String sub, String prop, String value, URI datatype)
    {
        Resource s = new URIImpl(sub);
        URI p = new URIImpl(prop);        
        Value o = new LiteralImpl(value,datatype);
        return new StatementImpl(s, p, o);
    }
    
    
    public Statement getObjectStatement(String sub, String prop, String value)
    {
        Resource s = new URIImpl(sub);
        URI p = new URIImpl(prop);
        Value o = new URIImpl(value);
        return new StatementImpl(s, p, o);
    }
    
    
    void processFile(String file, String prefix, int anio, boolean botro,int offset)
    {
        try
        {        
            RepositoryConnection con = repo.getConnection();
            
            con.setAutoCommit(false);            
            
            String lestado=null;
            String ldependencia=null;
            int x=0;
            InputStream in = getFile(file);
            String lin = readLine(in);
            for(int i=0;i<offset;i++)lin = readLine(in);
            while (lin != null)
            {
                lin = prepareLine(lin);
                try
                {
                    if (lin.trim().length() > 0)
                    {
                        StringTokenizer st = new StringTokenizer(lin, "\t");
                        String estado = filterString(st.nextToken());
                        if(estado==null)estado=lestado;
                        else lestado=estado;
                        String municipio = filterString(st.nextToken());
                        String dependencia = filterString(st.nextToken());
                        if(dependencia==null)dependencia=ldependencia;
                        else ldependencia=dependencia;
                        String programa = filterString(st.nextToken());
                        int hombre = parseInt(filterString(st.nextToken()));
                        int mujer = parseInt(filterString(st.nextToken()));
                        int otro=0;
                        if(botro)otro = parseInt(filterString(st.nextToken()));
                        int total = parseInt(filterString(st.nextToken()));
                        if(estado!=null && municipio!=null)
                        {
                            x++;
                            String uri=prefix+x;
                            System.out.println(x+":"+estado+"|"+municipio+"|"+dependencia+"|"+programa+"|"+hombre+"|"+mujer+"|"+otro+"|"+total);
                            con.add(getObjectStatement(uri,RDF.TYPE.getNamespace(),"http://datosabiertos.gob.mx/ontology/progsocial.owl#RegistroProgramaSocial"));
                            con.add(getStringStatement(uri, "http://datosabiertos.gob.mx/ontology/progsocial.owl#estado", estado));
                            con.add(getStringStatement(uri, "http://datosabiertos.gob.mx/ontology/progsocial.owl#municipio", municipio));
                            con.add(getStringStatement(uri, "http://datosabiertos.gob.mx/ontology/progsocial.owl#dependencia", dependencia));
                            con.add(getStringStatement(uri, "http://datosabiertos.gob.mx/ontology/progsocial.owl#programa", programa));
                            con.add(getLiteralStatement(uri, "http://datosabiertos.gob.mx/ontology/progsocial.owl#hombre", ""+hombre,XSD.INT));
                            con.add(getLiteralStatement(uri, "http://datosabiertos.gob.mx/ontology/progsocial.owl#mujer", ""+mujer,XSD.INT));
                            con.add(getLiteralStatement(uri, "http://datosabiertos.gob.mx/ontology/progsocial.owl#otro", ""+otro,XSD.INT));                        
                            con.add(getLiteralStatement(uri, "http://datosabiertos.gob.mx/ontology/progsocial.owl#total", ""+total,XSD.INT));                        
                            con.add(getLiteralStatement(uri, "http://datosabiertos.gob.mx/ontology/progsocial.owl#anio", ""+anio,XSD.INT));                        
                        }
                    }
                }catch(Exception e)
                {
                    System.out.println(x+":Error:"+lin);
                }
                lin = readLine(in);
            }   
            in.close();
            
            con.commit();
            con.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        System.out.println("Size:"+sail.getDatabase().getStatementCount()+" "+sail.getDatabase().getURICount());

    }
    
    public void init()
    {
        Properties properties = new Properties();
        File journal = new File("/Users/javier.solis.g/bigdata.jnl");
        properties.setProperty(BigdataSail.Options.FILE, journal.getAbsolutePath());
        //The persistence engine.  Use 'Disk' for the WORM or 'DiskRW' for the RWStore.

        properties.setProperty("com.bigdata.journal.AbstractJournal.bufferMode","DiskRW");
        properties.setProperty("com.bigdata.btree.writeRetentionQueue.capacity", "4000");
        properties.setProperty("com.bigdata.btree.BTree.branchingFactor", "128");

        //# 200M initial extent.
        properties.setProperty("com.bigdata.journal.AbstractJournal.initialExtent", "209715200");
        properties.setProperty("com.bigdata.journal.AbstractJournal.maximumExtent", "209715200");

        //##
        //## Setup for QUADS mode without the full text index.
        //##
        properties.setProperty("com.bigdata.rdf.sail.isolatableIndices", "true");
        properties.setProperty("com.bigdata.rdf.sail.truthMaintenance", "false");
        //com.bigdata.rdf.store.AbstractTripleStore.quads=false
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.quads", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.statementIdentifiers", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.textIndex", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.axiomsClass", "com.bigdata.rdf.axioms.NoAxioms");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.vocabularyClass", "com.bigdata.rdf.vocab.NoVocabulary");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.justify", "false");
                 
        // instantiate a sail
        sail = new BigdataSail(properties);
        System.out.println("NameSapce:"+sail.getDatabase().getNamespace());  
        
        repo = new BigdataSailRepository(sail);        
        try
        {        
            repo.initialize();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void end()
    {
        try
        {
            sail.shutDown();            
        } catch (Exception e)
        {
            e.printStackTrace();
        }        
    }

    public static void main(String args[])
    {
        ImportCSV i=new ImportCSV();
        
        i.init();
        
        String file;
        String prefix;
        
        file="Sedesol_g.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:sedesol_2011_";
        i.processFile(file,prefix,2011,false,0);
        
        file="Conacyt_g.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:conacyt_2011_";
        i.processFile(file,prefix,2011,true,0);
        
        file="sct_g.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:sct_2011_";
        i.processFile(file,prefix,2011,false,0);
        
        file="sagarpa_g.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:sagarpa_2011_";
        i.processFile(file,prefix,2011, true,0);
        
        file="se_g.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:se_2011_";
        i.processFile(file,prefix,2011, true,0);
        
        file="sep_g.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:sep_2011_";
        i.processFile(file,prefix,2011, false,0);
        
        file="styps_g.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:stps_2011_";
        i.processFile(file,prefix,2011, false,0);

        
        
        
        file="conacyt_g12.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:conacyt_2012_";
        i.processFile(file,prefix,2012,false,1);
        
        file="sagarpa_g12.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:sagarpa_2012_";
        i.processFile(file,prefix,2012,false,1);
        
        file="sct_g12.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:sct_2012_";
        i.processFile(file,prefix,2012,false,1);
        
        file="se_g12.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:se_2012_";
        i.processFile(file,prefix,2012,false,1);
        
        file="sedesol_g12.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:sedesol_2012_";
        i.processFile(file,prefix,2012,false,1);
        
        file="semarnat_g12.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:semarnat_2012_";
        i.processFile(file,prefix,2012,false,1);
        
        file="sep_g12.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:sep_2012_";
        i.processFile(file,prefix,2012,false,1);
        
        file="shcp_g12.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:shcp_2012_";
        i.processFile(file,prefix,2012,false,1);
        
        file="sra_g12.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:sra_2012_";
        i.processFile(file,prefix,2012,false,1);
        
        file="stps_g12.txt";
        prefix="http://datosabiertos.gob.mx/data/prosoc_RegistroProgramaSocial:stps_2012_";
        i.processFile(file,prefix,2012,false,1);
                
        i.end();
        
    }
}
