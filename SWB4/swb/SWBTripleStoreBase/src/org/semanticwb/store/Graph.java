/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.semanticwb.store.utils.LexiSortable;
import org.semanticwb.store.utils.NumberFormatTS;
import org.semanticwb.store.utils.Utils;

/**
 *
 * @author javier.solis.g
 */
public abstract class Graph
{
    public final static int MAX_OBJ=255;
    public final static char EXT_NS_CHAR='^';
    private String name=null;
    protected final static char separator=1;
    private boolean trans=false;
    private boolean encodeURIs=true;
    private boolean encodeObjects=true;
    private boolean encodeInstanceNumber=true;
    
    private static NumberFormatTS nf=new NumberFormatTS(Locale.US);
    
    ConcurrentHashMap<String,String> ns_k=new ConcurrentHashMap();
    ConcurrentHashMap<String,String> ns_s=new ConcurrentHashMap();
    
    private Map<String,String> params;

    public Graph(String name, Map<String,String> params)
    {
        this.name=name;
        this.params=params;
    }    
    
    public abstract void begin();

    public abstract void commit();
    
    public abstract void rollback();
    
    public abstract boolean isClosed();      
    
    public abstract void close();      
    
    public abstract void synchDB();
    
    protected abstract SObjectIterator findSObjects(SObject obj, boolean reverse);
    
    protected abstract boolean removeSObject(SObject obj);
    
    protected abstract boolean addSObject(SObject obj, boolean thread);    
    
    public TripleIterator findTriples(Triple triple)
    {
        return findTriples(triple, false);
    }    
    
    public TripleIterator findTriples(Triple triple, boolean reverse)
    {
        //System.out.println("findTriples:"+triple+" "+reverse);
        final SObjectIterator it=findSObjects(new SObject(encTriple(triple)), reverse);
        
        TripleIterator ret=new TripleIterator()
        {

            @Override
            public boolean hasNext()
            {
                return it.hasNext();
            }

            @Override
            public void close()
            {
                it.close();
            }

            @Override
            public long count()
            {
                return it.count();
            }

            @Override
            public boolean isClosed()
            {
                return isClosed();
            }

            @Override
            public Triple next()
            {
                SObject obj=it.next();
                String to=obj.o;
                if(obj.e.length()>0)to=obj.e;
       
                Node s=decNode(obj.s);
                Node p=decNode(obj.p);
                Node o=decNode(to);
                return new Triple(s,p,o);
            }

            @Override
            public void remove()
            {
                it.remove();
            }
        };
        
        return ret;        
    }

    public TripleIterator findTriples(Triple tm, String stype, final Long limit, final Long offset, boolean reverse)
    {
        //System.out.println("findTriples:"+tm+" "+stype+" "+limit+" "+offset+" "+reverse);
        
        final String index;
        if(stype!=null)index=":"+stype+":";
        else index=null;

        final SObjectIterator it=findSObjects(new SObject(encTriple(tm)), reverse);
        
        TripleIterator ret=new TripleIterator()
        {
            SObject obj=null;
            long i=0;
            long l=0;
            boolean hasNext=false;
            
            private boolean internalNext()
            {
                if(i>=offset+limit)
                {
                    it.close();
                    return false;                    
                }
                
                boolean ret=false;                
                while(it.hasNext())
                {
                    obj=it.next();
                    if(index!=null && obj.s.indexOf(index)==-1)continue;     
                    ret=true;
                    i++;
                    break;
                }
                return ret;                
            }
            
            @Override
            public boolean hasNext()
            {
                while(i<offset)
                {
                    if(!internalNext())break;
                    i++;
                }                
                
                if(!hasNext)hasNext=internalNext();
                
                return hasNext;
            }

            @Override
            public void close()
            {
                it.close();
            }

            @Override
            public long count()
            {
                while(internalNext());
                return i;
            }

            @Override
            public boolean isClosed()
            {
                return isClosed();
            }

            @Override
            public Triple next()
            {
                hasNext=false;
                String to=obj.o;
                if(obj.e.length()>0)to=obj.e;
       
                Node s=decNode(obj.s);
                Node p=decNode(obj.p);
                Node o=decNode(to);
                return new Triple(s,p,o);
            }

            @Override
            public void remove()
            {
                it.remove();
            }
        };
        
        return ret;         
    }    

    public long count(Triple tm, String stype)
    {
        long ret=0;
        String index=null;
        if(stype!=null)index=":"+stype+":";
        final SObjectIterator it=findSObjects(new SObject(encTriple(tm)), false);
        while (it.hasNext())
        {
            SObject obj = it.next();
            if(index!=null && obj.s.indexOf(index)==-1)continue;
            ret++;
        }
        return ret;          
    }    
    
    public boolean addTriple(Triple triple)
    {
        return addTriple(triple, false);
    }

    public boolean addTriple(Triple triple, boolean thread)
    {
        String t[]=encTriple(triple);
        return addSObject(new SObject(t), thread);
    }    
    
    public boolean removeTriples(Triple triple)
    {
        //System.out.println("removeTriples:"+triple);
        try
        {
            TripleIterator it=findTriples(triple,false);        
            while (it.hasNext())
            {
                Triple t = it.next();
                //System.out.println("remove:"+t);
                it.remove();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }    
    
    /**
     * Agrega un name space si el prefijo es nulo crea uno y regresa el prefijo
     * @param ns
     * @param prefix
     * @return prefijo definido o creado
     */
    public abstract String addNameSpace(String prefix, String ns);
    
    public abstract void removeNameSpace(String prefix);
    
    public boolean contains(Triple triple) //Sobre escribir para mejorar performance
    {
        boolean ret=false;
        TripleIterator it=findTriples(triple,true);
        ret=it.hasNext();
        it.close();
        return ret;
    }
    
    public Iterator<Triple>read(String fileName, final long from, final long to) throws FileNotFoundException
    {       
        FileInputStream in = new FileInputStream(fileName);
        //FileChannel ch=in.getChannel();

        InputStreamReader rin = new FileReader(fileName);
        final BufferedReader bin = new BufferedReader(rin);
        
        Iterator<Triple> ret = new Iterator<Triple>() 
        {
            boolean end=false;
            int x = 0;
            String line = null;
            boolean rl=false;

            @Override 
            public boolean hasNext()
            {
                if(end)return false;
                try
                {
                    if(!rl)
                    {
                        do
                        {
                            line = bin.readLine();
                            x++;
                        }while(x<=from);
                        rl=true;
                    }                    
                    return (line != null);
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public Triple next() 
            {
                rl=false;
                
                if (to > 0 && x >= to)
                {
                    end=true;
                }
                
                return new Triple(line);
            }

            @Override 
            public void remove() 
            {
            }
        };
        
        return ret;
    }
    
    public Iterator<Triple>read2(String fileName, final long from, final long to) throws IOException
    {
        final org.semanticwb.store.utils.FileReader fr=new org.semanticwb.store.utils.FileReader(fileName);

        Iterator<Triple> ret = new Iterator<Triple>() 
        {
            boolean end=false;
            int x = 0;
            boolean rl=false;
            String s,p,o;

            @Override 
            public boolean hasNext()
            {
                if(end)
                {
                    return false;
                }
                try
                {
                    if(!rl)
                    {
                        do
                        {
                            s=fr.nextSegment((byte)' ');
                            p=fr.nextSegment((byte)' ');
                            o=fr.nextSegment((byte)'\n');
                            x++;
                        }while(x<=from);
                        rl=true;
                        if(s==null)
                        {
                            end=true;
                            fr.close();
                        }
                    }                    
                    return !end;
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public Triple next()
            {
                rl=false;
                
                if (to > 0 && x >= to)
                {
                    end=true;
                    fr.close();
                }
                
                return new Triple(s, p, o.substring(0, o.length()-2));
            }

            @Override 
            public void remove() 
            {
            }
        };
        
        return ret;
    }    
    
    public void load(String fileName, long from, long to) throws FileNotFoundException
    {
        long time = System.currentTimeMillis();
        long time2 = System.currentTimeMillis();
//        begin();
        try
        {
            Iterator<Triple> it=read2(fileName, from, to);
            System.out.println("reader Inited...");
            int x=0;
            while (it.hasNext())
            {
                Triple triple = it.next();
                addTriple(triple,true);
                x++;
                if(x%100000==0)
                {
                    System.out.println("Loading:"+x+" "+(System.currentTimeMillis()-time2));
                    time2 = System.currentTimeMillis();
                }
            }
//            commit();
        }catch(Exception e)
        {
            e.printStackTrace();
//            rollback();
        }
        synchDB();
    }
    
//    protected String encSubj(Resource res)
//    {
//        return shortURI(res.getValue());
//    }
//
//    protected Resource decSubj(String key)
//    {
//        return new Resource(expandUri(key));
//    }
//
//    protected String encProp(Resource res)
//    {
//        return shortURI(res.getValue());
//    }
//
//    protected Resource decProp(String key)
//    {
//        return new Resource(expandUri(key));
//    }

    protected String encNode(Node node)
    {
        if(node.isResource())
        {
            return shortURI(node.asResource().getValue());
        }else
        {
            Literal l=node.asLiteral();
            String value=l.getValue();
            //LexiSortable.toLexiSortable
            String lang=l.getLang();
            String type=l.getType();
            if(type!=null && type.length()>0)
            {
                if(type.startsWith("<http://www.w3.org/2001/XMLSchema#"))
                {
                    String tp=type.substring(34,type.length()-1).toLowerCase();                    
                    type="x:"+tp;
                    
                    if(encodeObjects)
                    {
                        if(tp.equals("float") || tp.equals("double") || tp.equals("decimal"))
                        {
                            Number number = Utils.parseNumber(value);                            
                            value=LexiSortable.toLexiSortable(number.doubleValue());
                            //value=value.replaceAll(",", "");
                            //value=LexiSortable.toLexiSortable(Double.parseDouble(value));
                        }else if(tp.equals("integer") || tp.equals("int") || tp.equals("long") || tp.equals("short") || tp.equals("byte"))
                        {
                            Number number = Utils.parseNumber(value);                            
                            value=LexiSortable.toLexiSortable(number.longValue());                            
//                            value=value.replaceAll(",", "");
//                            value=LexiSortable.toLexiSortable(Long.parseLong(value));
                        }else if(tp.equals("string"))
                        {
                            value=Utils.encodeText(value);
                        }
                    }
                }
            }else
            {
                if(encodeObjects)value=Utils.encodeText(value);
            }
            StringBuilder ret=new StringBuilder();
            ret.append("\"");
            ret.append(value);
            ret.append("\"");
            if(lang!=null && lang.length()>0)
            {
                ret.append("@");
                ret.append(lang);
            }
            if(type!=null && type.length()>0)
            {
                ret.append("^^");
                ret.append(type);
            }
                        
            //System.out.println("encode:"+node+"-->"+ret);
            return  ret.toString();
        }
    }

    protected Node decNode(String key)
    {
        Node ret;
        if(key.charAt(0)=='"')
        {
            int i=key.lastIndexOf('"');
            int j=key.indexOf('@',i);
            int k=key.indexOf("^^",i);
            String value=key.substring(1,i);
            String lang=null;
            String type=null;
            if(j>-1)
            {
                if(k>-1)lang=key.substring(j+1, k);
                else lang=key.substring(j+1,key.length());
            }
            if(k>-1)
            {
                type=key.substring(k+2,key.length());
                if(type.startsWith("x:"))
                {
                    String tp=type.substring(2);
                    type="<http://www.w3.org/2001/XMLSchema#"+tp+">";
                    
                    if(encodeObjects)
                    {
                        if(tp.equals("float") || tp.equals("double") || tp.equals("decimal"))
                        {
                            value=""+LexiSortable.doubleFromLexiSortable(value);
                        }else if(tp.equals("integer") || tp.equals("int") || tp.equals("long") || tp.equals("short") || tp.equals("byte"))
                        {
                            value=""+LexiSortable.longFromLexiSortable(value);
                        }else if(tp.equals("string"))
                        {
                            value=Utils.decodeText(value);
                        }
                    }
                }
            }else if(encodeObjects && (type==null || type.length()==0))
            {
                value=Utils.decodeText(value);
            }                        
            ret=new Literal(value,lang,type);
        }else
        {
            ret=new Resource(expandUri(key));
        }
        //System.out.println("decode:"+key+"-->"+ret);
        return ret;
    }
    
    protected String[] encTriple(Triple triple)
    {
        String ret[]=new String[4];
        
        Node ns=triple.getSubject();
        Node np=triple.getProperty();
        Node no=triple.getObject();
        
        if(ns!=null)ret[0]=encNode(ns);
        if(np!=null)ret[1]=encNode(np);
        if(no!=null)ret[2]=encNode(no);
        ret[3]="";
        
        if(ret[2]!=null && ret[2].length()>MAX_OBJ)
        {
             String th=Utils.getHash(ret[2]);
             if(th!=null)
             {
                 ret[3]=ret[2];
                 ret[2]=ret[2].substring(0,10)+"_"+th+"_";
             }
        }
        
        return ret;
    }
    
    protected Triple decTriple(String key, String val, int shift)
    {        
        String t[]=key.split(""+separator);
        String ts=null,tp=null,to=null;
        
        if(shift==0)
        {
            ts=t[0];
            tp=t[1];
            to=t[2];
        }else if(shift==1)
        {
            ts=t[2];
            tp=t[0];
            to=t[1];
        }else if(shift==2)
        {
            ts=t[1];
            tp=t[2];
            to=t[0];
        }

        if(val.length()>0)to=val;
        
        Node s=decNode(ts);
        Node p=decNode(tp);
        Node o=decNode(to);
        return new Triple(s,p,o);
    }

    public abstract long count();

    public String getName()
    {
        return name;
    }
    
    public void setTransactionEnabled(boolean trans)
    {
        this.trans=trans;
    }
    
    public boolean isTransactionEnabled()
    {
        return this.trans;
    }

    public boolean isEncodeURIs()
    {
        return encodeURIs;
    }

    public void setEncodeURIs(boolean encodeURIs)
    {
        this.encodeURIs = encodeURIs;
    }
    
    protected void addNameSpace2Cache(String prefix, String ns)
    {
        ns_k.put(prefix,ns);
        ns_s.put(ns, prefix);        
    }
    
    protected void removeNameSpace2Cache(String prefix)
    {
        String ns=getNameSpace(prefix);
        ns_k.remove(prefix);
        ns_s.remove(ns);        
    }    

    public String getNSPrefix(String ns)
    {
        String ret=ns_s.get(ns);
        //if(ret==null)System.out.println("getNSPrefix:"+ns+" "+ret);
        return ret;
    }

    public String getNameSpace(String prefix)
    {
        String ret=ns_k.get(prefix);
        //if(ret==null)System.out.println("getNameSpace:"+prefix+" "+ret);
        return ret;
    }
    
    public String getExtNSPrefix(String ns)
    {
        String prefix=getNSPrefix(EXT_NS_CHAR+ns);
        if(prefix!=null)prefix=prefix.substring(1);
        return prefix;
    }

    public String getExtNameSpace(String prefix)
    {
        String ns=getNameSpace(EXT_NS_CHAR+prefix);
        if(ns!=null)ns=ns.substring(1);
        return ns;
    }
    
    public String addExtNameSpace(String prefix, String ns)
    {
        return addNameSpace(EXT_NS_CHAR+prefix, EXT_NS_CHAR+ns);
    }
    
    public void removeExtNameSpace(String prefix)
    {
        removeNameSpace(EXT_NS_CHAR+prefix);
    }
    
    public Map getExtNameSpacesMap()
    {
        HashMap map=new HashMap();
        Iterator<Map.Entry<String,String>> it=ns_k.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<String, String> entry = it.next();
            if(entry.getKey().charAt(0)==EXT_NS_CHAR)       //Filtra los namespaces internos
            {
                map.put(entry.getKey().substring(1), entry.getValue().substring(1));
            }
        }        
        return map;
    }
    
    public String shortURI(String uri)
    {
        if(!isEncodeURIs())return uri;
//        System.out.println("short:"+uri);
        if(uri.charAt(0)=='<')
        {
            int i=uri.lastIndexOf('#');
            if(i==-1)i=uri.lastIndexOf('/');
            if(i>-1)
            {
                String ns=uri.substring(1, i+1);         
                String p=getNSPrefix(ns);
                if(p==null)p=addNameSpace(null,ns);
                
                String t=uri.substring(i+1,uri.length()-1);
                
                StringBuilder buf=new StringBuilder();
                
                buf.append(p);
                buf.append(':');
                
                if(encodeInstanceNumber)
                {
                    int x=t.lastIndexOf(':');
                    if(x>-1)
                    {
                        String base=t.substring(0,x+1);            
                        String n=t.substring(x+1);
                        Long l=Utils.parseLong(n);
                        if(l!=null)
                        {
                            buf.append(base);
                            buf.append('^');
                            buf.append(Utils.encodeSortableLong(l));
                        }else
                        {
                            buf.append(t);
                        }
                    }else
                    {
                        buf.append(t);
                    }
                }else
                {
                    buf.append(t);
                }
//                System.out.println("short2:"+buf);
                return buf.toString();
            }else return uri;
        }
        return uri;
    }
    
    public String expandUri(String suri)
    {
        if(!isEncodeURIs())return suri;
//        System.out.println("expand:"+suri);
        if(suri.charAt(0)!='<')
        {
            int i=suri.indexOf(':');
            if(i>-1)
            {
                String p=suri.substring(0, i);
                String ns=getNameSpace(p); 
                String t=suri.substring(i+1);
                
                StringBuilder ret=new StringBuilder();
                ret.append('<');
                ret.append(ns);
                
                if(encodeInstanceNumber)
                {
                    int x=t.lastIndexOf(":^");
                    if(x>-1)
                    {
                        String base=t.substring(0,x+1);            
                        String n=t.substring(x+2);
                        ret.append(base);
                        ret.append(Utils.decodeLong(n));
                    }else
                    {
                        ret.append(t);
                    }                
                }else
                {
                    ret.append(t);
                }
                
                ret.append('>');
//                System.out.println("expand2:"+ret);
                return ret.toString();
                
            }else return suri;
        }
        return suri;
    }

    public String getParam(String param)
    {
        return params.get(param);
    }
    
    public String getParam(String param, String defValue)
    {
        String ret=getParam(param);
        if(ret==null)ret=defValue;
        return ret;
    }
    
}
