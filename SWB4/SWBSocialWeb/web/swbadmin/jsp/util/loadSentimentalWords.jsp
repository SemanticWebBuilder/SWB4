<%@page import="java.io.*"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

//Imports de Lucene
<%@page import="org.apache.lucene.analysis.Analyzer,org.apache.lucene.analysis.TokenStream,org.apache.lucene.analysis.tokenattributes.CharTermAttribute,org.apache.lucene.analysis.tokenattributes.OffsetAttribute,org.semanticwb.social.util.lucene.SpanishAnalizer"%>


Inicia...
<%!
    static ArrayList<String> aDoubles=new ArrayList();
    static HashMap<String, String> hmapChanges=new HashMap();
%>

<%
    //WebSite wsite=paramRequest.getWebPage().getWebSite();
    
    WebSite wsite=SWBContext.getAdminWebSite();

    initialize();

    //Elimina todos los objetos(instancias) de la clase SentimentWords
    Iterator <SentimentWords> itSentimentWords=SentimentWords.ClassMgr.listSentimentWordses(wsite);
    while(itSentimentWords.hasNext())
    {
        SentimentWords sentimentWord=itSentimentWords.next();
        sentimentWord.remove();
    }

    //Inserta detos en la clase SentimentWords
    BufferedReader bf = null;
    try {
        bf = new BufferedReader(new FileReader(SWBPortal.getWorkPath()+"/../swbadmin/jsp/util/sentimentalWords2Process_SinAcentos.csv"));
    } catch (FileNotFoundException e) {
    e.printStackTrace();
    }
    String line = null;
    boolean isFirstLine=true;
    try {
        while ((line = bf.readLine())!=null) {
            if(isFirstLine){isFirstLine=false; continue;}
            int colum=0;
            SentimentWords sentimentWord=null;
            StringTokenizer tokens = new StringTokenizer(line, ";");
            while(tokens.hasMoreTokens()){
                colum++;
                String tokenValue=tokens.nextToken();
                //if((colum==1 && tokenValue==null) || colum>3) break;
                if(tokenValue==null) continue;
                if(colum==1) {
                    System.out.println("tokenValue-1:"+tokenValue);
                    String tmpToken=getRootWord(tokenValue);
                    if(tmpToken!=null) tokenValue=tmpToken;
                    System.out.println("tokenValue-1.1:"+tokenValue);
                    tokenValue=phonematize(tokenValue);
                    System.out.println("tokenValue-2:"+tokenValue);
                    sentimentWord=SentimentWords.ClassMgr.getSentimentWords(tokenValue,wsite);
                    if(sentimentWord==null)
                    {
                        sentimentWord=SentimentWords.ClassMgr.createSentimentWords(tokenValue,wsite);
                    }else{
                        out.println("YA SE ENCUENTRAAAAAAAAAAAAAA:"+tokenValue);
                    }
                    //sentimentWord.setSentimentalWord(tokenValue);
                }
                else if(colum==2 && sentimentWord!=null) {
                    //System.out.println("tokenValue:"+tokenValue);
                    sentimentWord.setSentimentalValue(Float.parseFloat(tokenValue));
                }
                else if(colum==3 && sentimentWord!=null) {
                    //System.out.println("tokenValue:"+tokenValue);
                    sentimentWord.setIntensityValue(Float.parseFloat(tokenValue));
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    int cont=0;
    //Lee todos los valores de la clase sentimentWord
    //Iterator <SentimentWords> itSentimentWords=SentimentWords.ClassMgr.listSentimentWordses(wsite);
    itSentimentWords=SentimentWords.ClassMgr.listSentimentWordses(wsite);
    while(itSentimentWords.hasNext())
    {
        cont++;
        SentimentWords sentimentWord=itSentimentWords.next();
        out.println("sentimentWord:"+sentimentWord.getId()+", sentimentValue:"+sentimentWord.getSentimentalValue()+", IntensityValue:"+sentimentWord.getIntensityValue()+"<br/>");
        //if(cont==10) break;
    }
    System.out.println("cont:"+cont);
%>

<%!
private static String getRootWord(String word)
{
    try
    {
        TokenStream tokenStream = new SpanishAnalizer().tokenStream("contents", new StringReader(word));
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

        while (tokenStream.incrementToken()) {
            //int startOffset = offsetAttribute.startOffset();
            //int endOffset = offsetAttribute.endOffset();
            String term = charTermAttribute.toString();
            System.out.println("term Jorge:"+term);
            return term;
        }
    }catch(Exception e){ e.printStackTrace();}
    return null;
}


private static String phonematize(String in_word)
    {
        String tmp="";
        String out_word = "";
        in_word = in_word.toLowerCase();

        char [] in_wordArray=in_word.toCharArray();
        for(int i=0;i<in_wordArray.length;i++)
        {
            String in_wordChar=String.valueOf(in_wordArray[i]);
            if(aDoubles.contains(in_wordChar))
            {
                tmp+=in_wordChar;
                continue;
            }else if(tmp.trim().length()>0)    //Busca sonidos que se representan graficamente con dos caracteres
            {
                if(hmapChanges.containsKey(tmp + in_wordChar))
                {
                    out_word += hmapChanges.get(tmp + in_wordChar);
                    tmp = "";
                    continue;
                }else if(hmapChanges.containsKey(tmp))
                {
                    out_word+= hmapChanges.get(tmp) + in_wordChar;
                    tmp = "";
                    continue;
                }
                if(hmapChanges.containsKey(in_wordChar))
                {
                    if(aDoubles.contains(hmapChanges.get(in_wordChar)))
                    {
                        tmp+=hmapChanges.get(in_wordChar);
                        continue;
                    }
                } else {
                        out_word+=tmp+in_wordChar;
                        tmp="";
                        continue;
                    }
            }else { //Mapea los caracteres con su sonido correspondiente
                if(hmapChanges.containsKey(in_wordChar))
                {
                    if(aDoubles.contains(hmapChanges.get(in_wordChar)))
                    {
                        tmp+=hmapChanges.get(in_wordChar);
                        continue;
                    }else{
                        out_word+=hmapChanges.get(in_wordChar);
                        tmp="";
                        continue;
                    }
                }else if(in_wordChar.equals("h")){  //Elimina la h
                    continue;
                }
                out_word+=in_wordChar;
                tmp="";
                continue;
            }
        }
        if(tmp.length()>0)
        {
            out_word+=tmp;
            tmp="";
        }

        return out_word;
    }


    private static void initialize()
    {
         //Carga Valores a ArrayList
         aDoubles.add("b");
         aDoubles.add("p");
         aDoubles.add("q");
         aDoubles.add("s");
         aDoubles.add("c");
         aDoubles.add("g");
         aDoubles.add("n");
         aDoubles.add("m");
         aDoubles.add("l");
         aDoubles.add("p");
         aDoubles.add("t");
         aDoubles.add("f");
         aDoubles.add("d");


         //Carga valores a HashMap
         hmapChanges.put("nge", "nje");
         hmapChanges.put("ngi", "nji");
         hmapChanges.put("ch", "ch");
         hmapChanges.put("cc", "ks");
         hmapChanges.put("ci", "si");
         hmapChanges.put("qu", "k");
         hmapChanges.put("w", "gu");
         hmapChanges.put("nn", "n");
         hmapChanges.put("mm", "m");
         hmapChanges.put("ll", "y");
         hmapChanges.put("pp", "p");
         hmapChanges.put("ce", "se");
         hmapChanges.put("q", "k");
         hmapChanges.put("ss", "s");
         hmapChanges.put("ge", "je");
         hmapChanges.put("tt", "t");
         hmapChanges.put("ff", "f");
         hmapChanges.put("v", "b");
         hmapChanges.put("x", "ks");
         hmapChanges.put("z", "s");
         hmapChanges.put("dd", "d");
         hmapChanges.put("gi", "ji");
         hmapChanges.put("bb", "b");
         hmapChanges.put("c", "k");
    }

%>


Termina...