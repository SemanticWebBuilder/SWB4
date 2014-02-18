
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jorge.jimenez
 */
class TestKlout {
    
    public static void main(String args[])
    {
        String userThird_party_id="cCY-UMxe1FcbTsc4eZINhyOvlj8";
        String url_1="http://api.klout.com/v2/identity.json/fb/"+userThird_party_id;
        String kloutJsonResponse_1=getData(url_1);
        System.out.println("kloutResult step-1:"+kloutJsonResponse_1);

        //Obtener id de json
        try
        {
            if(kloutJsonResponse_1!=null)
            {
                JSONObject userData = new JSONObject(kloutJsonResponse_1);
                String kloutUserId = userData != null && userData.get("id") != null ? (String) userData.get("id") : "";
                System.out.println("kloutId de Resultado en Json:"+kloutUserId);

                //Segunda llamada a la red social Klout, para obtener Json de Score del usuario (kloutUserId) encontrado
                if(kloutUserId!=null)
                {
                    String url_2="http://api.klout.com/v2/user.json/"+kloutUserId+"/score";
                    String kloutJsonResponse_2=getData(url_2);
                    System.out.println("kloutResult step-2-Json:"+kloutJsonResponse_2);

                    if(kloutJsonResponse_2!=null)
                    {
                         JSONObject userScoreData = new JSONObject(kloutJsonResponse_2);
                         Double kloutUserScore = userScoreData != null && userScoreData.get("score") != null ? (Double) userScoreData.get("score") : 0.00;
                         System.out.println("ResFinal/Klout:"+Math.rint(kloutUserScore.doubleValue()));
                         
                    }
                }
            }
        }catch(JSONException je)
        {
            System.out.println(je.getMessage());
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    private static String getData(String url)
    {
        String answer = null;
        //String key=SWBContext.getAdminWebSite().getProperty("kloutKey");    //TODO:Ver con Jei x que no funciona esto...
        String key="8fkzgz7ngf7bth3nk94gnxkd";
        //if(key==null) key="8fkzgz7ngf7bth3nk94gnxkd";   //Solo para fines de pruebas, quitar despues y dejar línea anterior.
        //System.out.println("key para KLOUT--Gg:"+key);
        if(key!=null)
        {
            url=url+"?key="+key;
            URLConnection conex = null;
            try {
                //System.out.println("Url a enviar a Klout:"+url);
                URL pagina = new URL(url);

                String host = pagina.getHost();
                //Se realiza la peticion a la página externa
                conex = pagina.openConnection();
                /*
                if (userAgent != null) {
                    conex.setRequestProperty("user-agent", userAgent);
                }*/
                if (host != null) {
                    conex.setRequestProperty("host", host);
                }
                conex.setDoOutput(true);

                conex.setConnectTimeout(20000); //15 segundos maximo, si no contesta la red Klout, cortamos la conexión
            } catch (Exception nexc) {
                System.out.println("nexc Error:"+nexc.getMessage());
                conex = null;
            }
            //System.out.println("Twitter Klout/conex:"+conex);
            //Analizar la respuesta a la peticion y obtener el access token
            if (conex != null) {
                try
                {
                    //System.out.println("Va a checar esto en Klit:"+conex.getInputStream());
                    answer = getResponse(conex.getInputStream());
                }catch(Exception e)
                {
                    //log.error(e);
                }
                //System.out.println("Twitter Klout/answer-1:"+answer);
            }
        }
        //System.out.println("Twitter Klout/answer-2:"+answer);
        return answer;
    }
    
    private static String getResponse(InputStream data) throws IOException {

        Reader in = new BufferedReader(new InputStreamReader(data, "UTF-8"));
        StringBuilder response = new StringBuilder(256);
        char[] buffer = new char[1000];
        int charsRead = 0;
        while (charsRead >= 0) {
            response.append(buffer, 0, charsRead);
            charsRead = in.read(buffer);
        }
        in.close();
        return response.toString();
    }
}
