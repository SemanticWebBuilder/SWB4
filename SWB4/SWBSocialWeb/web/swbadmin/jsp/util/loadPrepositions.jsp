<%@page import="java.io.*"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%> 
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

Inicia...
<%
    //WebSite wsite=paramRequest.getWebPage().getWebSite();
    
    WebSite wsite=SWBContext.getAdminWebSite();
    //Elimina todos los objetos(instancias) de la clase SentimentWords
    Iterator <Prepositions> itPrepositions=Prepositions.ClassMgr.listPrepositionses(wsite);
    while(itPrepositions.hasNext())
    {
        Prepositions preposition=itPrepositions.next();
        preposition.remove();
    }

    //Inserta detos en la clase SentimentWords
    BufferedReader bf = null;
    try {
        File file=new File(SWBPortal.getWorkPath()+"/../swbadmin/jsp/util/preposiciones.csv");
        bf = new BufferedReader(new FileReader(SWBPortal.getWorkPath()+"/../swbadmin/jsp/util/preposiciones.csv")); 
    } catch (FileNotFoundException e) {
    e.printStackTrace();
    }
    String line = null;
    try {
        while ((line = bf.readLine())!=null) {
               Prepositions preposition=Prepositions.ClassMgr.createPrepositions(line,wsite);
               preposition.setPreposition(line);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    //Lee todos los valores de la clase sentimentWord
    int cont=0;
    itPrepositions=Prepositions.ClassMgr.listPrepositionses(wsite);
    while(itPrepositions.hasNext())
    {
        cont++;
        Prepositions preposition=itPrepositions.next();
        System.out.println("preposition:"+preposition.getPreposition());
    }
    System.out.println("cont:"+cont);

%>

Termina...