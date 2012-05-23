<%@page import="java.io.*"%>
<%@page import="org.semanticwb.social.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

Inicia...
<%
    WebSite wsite=paramRequest.getWebPage().getWebSite();
    /*
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
        bf = new BufferedReader(new FileReader("C:\\proyectos\\SSMCC\\Octavio\\sentimentalWords2Process_SinAcentos.csv"));
    } catch (FileNotFoundException e) {
    e.printStackTrace();
    }
    String line = null;
    boolean isFirstLine=true;
    try {
        while ((line = bf.readLine())!=null) {
            if(isFirstLine){isFirstLine=false; continue;}
            int colum=0;
            SentimentWords sentimentWord=SentimentWords.ClassMgr.createSentimentWords(wsite);
            StringTokenizer tokens = new StringTokenizer(line, ";");
            while(tokens.hasMoreTokens()){
                colum++;
                String tokenValue=tokens.nextToken();
                //if((colum==1 && tokenValue==null) || colum>3) break;
                if(tokenValue==null) continue;
                if(colum==1) {
                    System.out.println("tokenValue:"+tokenValue); 
                    sentimentWord.setSentimentalWord(tokenValue);
                }
                else if(colum==2) {
                    System.out.println("tokenValue:"+tokenValue); 
                    sentimentWord.setSentimentalValue(Float.parseFloat(tokenValue));
                }
                else if(colum==3) {
                    System.out.println("tokenValue:"+tokenValue); 
                    sentimentWord.setIntensityValue(Float.parseFloat(tokenValue));
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
 * */
    int cont=0;
    //Lee todos los valores de la clase sentimentWord
    Iterator <SentimentWords> itSentimentWords=SentimentWords.ClassMgr.listSentimentWordses(wsite);
    while(itSentimentWords.hasNext())
    {
        cont++;
        SentimentWords sentimentWord=itSentimentWords.next();
        System.out.println("sentimentWord:"+sentimentWord.getSentimentalWord()+", sentimentValue:"+sentimentWord.getSentimentalValue()+", IntensityValue:"+sentimentWord.getIntensityValue());
    }
    System.out.println("cont:"+cont);
%>
Termina...