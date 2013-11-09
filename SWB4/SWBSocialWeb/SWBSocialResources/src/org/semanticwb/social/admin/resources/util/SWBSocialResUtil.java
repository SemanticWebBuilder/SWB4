/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import javaQuery.j2ee.tinyURL;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.ModelProperty;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.UserGroup;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Country;
import org.semanticwb.social.CountryState;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialSite;
import org.semanticwb.social.Stream;
import org.semanticwb.social.util.GeoLocation;
import org.semanticwb.social.util.SWBSocialUtil;

/**
 *
 * @author jorge.jimenez
 */
public class SWBSocialResUtil {
    
    private static Logger log = SWBUtils.getLogger(SWBSocialUtil.class);
    
    public static class Util {
     
     /*
      * Controls pagination
      */
     public static String getContentByPage(long totPages, int npage,int snpages,SWBResourceURL pageURL)
     {
        StringBuilder strb1 = new StringBuilder();
        try {
            strb1.append("<div id=\"pagination\">");
            if (npage > snpages) {
               pageURL.setParameter("page", ""+1);
               strb1.append("<span><a href=\"#\" onClick=\"submitUrl('" + pageURL + "',this); return false;\">&lt;&lt;</a></span>");
            }
            if (npage > 1) {
               pageURL.setParameter("page", ""+(npage - 1));
               strb1.append("<span><a href=\"#\" onClick=\"submitUrl('" + pageURL + "',this); return false;\">&lt;</a></span>");
            }
            long ini = 1;
            long fin = snpages;
            //int dif = 0;
            if ((totPages < snpages)) {
                fin = totPages;
            }else if (totPages > snpages) {
                int numMin=Double.valueOf(snpages/2).intValue();
                if(totPages>=(npage + numMin+1))
                {
                    if((npage-numMin)>=1)
                    {
                        fin=npage + numMin+1;
                        ini=npage - numMin;
                    }
                }else{
                    fin=totPages;
                    if((npage-numMin)>=1)
                    {
                        ini = npage-numMin;
                    }
                }
                
            }
                /*
            if (totPages > snpages && npage > 1) {
                dif = npage - 1;
                if (totPages >= (snpages + dif)) {
                    fin = snpages + dif;
                    ini = 1 + dif;
                } else {
                    fin = totPages;
                    ini = totPages - snpages + 1;
                }
            }*/

            strb1.append("<span class=\"pag-space\"></span>");
            for (long i = ini; i <= fin; i++) {
                if (i != npage) {
                   pageURL.setParameter("page", ""+i); 
                   strb1.append("<a href=\"#\" onClick=\"submitUrl('" + pageURL + "',this); return false;\">" + String.valueOf(i) + "</a> ");
                } else {
                   strb1.append("<strong>" + String.valueOf(i) + " </strong>");
                }
            }
            if (npage < totPages) {
                    pageURL.setParameter("page", ""+(npage + 1));
                    strb1.append("<span><a href=\"#\" onClick=\"submitUrl('" + pageURL + "',this); return false;\">&gt;</a></span>");
            }
            if (npage < totPages-snpages) {
                    pageURL.setParameter("page", ""+(totPages));
                    strb1.append("<span><a href=\"#\" onClick=\"submitUrl('" + pageURL + "',this); return false;\">&gt;&gt;</a></span>");
            }
            strb1.append("<span class=\"pag-space\"></span>");

            strb1.append("</div>");

        } catch (Exception e) {
            log.error(e);
        }
        return strb1.toString();
      }
        
         /*
         * Reemplaza un String http:// por un <a href= y el valor
         */
        public static String createHttpLink(String text)
        {
            //System.out.println("SWBSocialUtil/replaceHref");
            int pos=0;
            int f = text.indexOf("http://", pos);
            int space=text.indexOf(" ", f);
            if(space==-1) {
              space=text.length();
            }
            //System.out.println("f:"+f+",space:"+space);
            while (f >= 0 && space>0)
            {
                //System.out.println("ENTRA WHILE F:"+f+",space:"+space);
                text=text.substring(0, f)+"<a target=\"_new\" href=\""+text.substring(f, space)+"\">"+text.substring(f, space)+"</a>"+text.substring(space);
                pos = f + 24+text.substring(f, space).length()+2+text.substring(f, space).length();
                f = text.indexOf("http://", pos);
                space=text.indexOf(" ", f);
                if(space==-1) {
                    space=text.length();
                }
            }
            return text;
        }
        
        /**
        *  Reemplaza caracteres especiales, tal como lo hace la misma función desde el 
        *  SWBUtils, solo que esta función si deja los espacios en blanco, cosa que la 
        *  del SWBUtils no hace., de hecho me parece que es un bug., decirle a Javier despues.
        */
        public static String replaceSpecialCharacters(String txt, boolean replaceSpaces)
        {
              StringBuffer ret = new StringBuffer();
              String aux = txt;
              //aux = aux.toLowerCase();
              aux = aux.replace('Á', 'A');
              aux = aux.replace('Ä', 'A');
              aux = aux.replace('Å', 'A');
              aux = aux.replace('Â', 'A');
              aux = aux.replace('À', 'A');
              aux = aux.replace('Ã', 'A');

              aux = aux.replace('É', 'E');
              aux = aux.replace('Ê', 'E');
              aux = aux.replace('È', 'E');
              aux = aux.replace('Ë', 'E');

              aux = aux.replace('Í', 'I');
              aux = aux.replace('Î', 'I');
              aux = aux.replace('Ï', 'I');
              aux = aux.replace('Ì', 'I');

              aux = aux.replace('Ó', 'O');
              aux = aux.replace('Ö', 'O');
              aux = aux.replace('Ô', 'O');
              aux = aux.replace('Ò', 'O');
              aux = aux.replace('Õ', 'O');

              aux = aux.replace('Ú', 'U');
              aux = aux.replace('Ü', 'U');
              aux = aux.replace('Û', 'U');
              aux = aux.replace('Ù', 'U');

              aux = aux.replace('Ñ', 'N');


              aux = aux.replace('Ç', 'C');
              aux = aux.replace('Ý', 'Y');

              aux = aux.replace('á', 'a');
              aux = aux.replace('à', 'a');
              aux = aux.replace('ã', 'a');
              aux = aux.replace('â', 'a');
              aux = aux.replace('ä', 'a');
              aux = aux.replace('å', 'a');

              aux = aux.replace('é', 'e');
              aux = aux.replace('è', 'e');
              aux = aux.replace('ê', 'e');
              aux = aux.replace('ë', 'e');

              aux = aux.replace('í', 'i');
              aux = aux.replace('ì', 'i');
              aux = aux.replace('î', 'i');
              aux = aux.replace('ï', 'i');

              aux = aux.replace('ó', 'o');
              aux = aux.replace('ò', 'o');
              aux = aux.replace('ô', 'o');
              aux = aux.replace('ö', 'o');
              aux = aux.replace('õ', 'o');

              aux = aux.replace('ú', 'u');
              aux = aux.replace('ù', 'u');
              aux = aux.replace('ü', 'u');
              aux = aux.replace('û', 'u');

              aux = aux.replace('ñ', 'n');

              aux = aux.replace('ç', 'c');
              aux = aux.replace('ÿ', 'y');
              aux = aux.replace('ý', 'y');

              if (replaceSpaces)
              {
                  aux = aux.replace(' ', '_');
              }
              int l = aux.length();
              for (int x = 0; x < l; x++)
              {
                  char ch = aux.charAt(x);
                  if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z')
                          || (ch >= 'A' && ch <= 'Z') || ch == '_' || ch == ' ')
                  {
                      ret.append(ch);
                  }
              }
              aux = ret.toString();
              return aux;
         }
        
        /**
        *  Reemplaza caracteres especiales, tal como lo hace la misma función desde el 
        *  SWBUtils, solo que esta función tiene la opción de dejar los espacios en blanco.
        *  Dado que la libreria Jsoup al eliminar todos los tags de html de un texto devuelve
        *  los acentos y las tildes condificados en HTML entonces se reemplazan por el caracter 
        *  en ASCII. Se mantienen los acentos en las vocales dado que la búsqueda en el stream
        *  y social topic distingue los acentos. //TODO: ver si se le quitan los ACENTOS cuando
        *  se hace el String.contains('PALABRA A BUSCAR')
        */
        public static String replaceSpecialCharactersAndHtmlCodes(String txt, boolean replaceSpaces)
        {
              StringBuffer ret = new StringBuffer();
              String aux = txt;
              aux = aux.replaceAll("&aacute;", "á");
              aux = aux.replaceAll("&eacute;", "é");
              aux = aux.replaceAll("&iacute;", "í");
              aux = aux.replaceAll("&oacute;", "ó");
              aux = aux.replaceAll("&uacute;", "ú");
              aux = aux.replaceAll("&ntilde;", "ñ");
              //aux = aux.toLowerCase();
              //aux = aux.replace('Á', 'A');
              aux = aux.replace('Ä', 'A');
              aux = aux.replace('Å', 'A');
              aux = aux.replace('Â', 'A');
              aux = aux.replace('À', 'A');
              aux = aux.replace('Ã', 'A');

              //aux = aux.replace('É', 'E');
              aux = aux.replace('Ê', 'E');
              aux = aux.replace('È', 'E');
              aux = aux.replace('Ë', 'E');

              //aux = aux.replace('Í', 'I');
              aux = aux.replace('Î', 'I');
              aux = aux.replace('Ï', 'I');
              aux = aux.replace('Ì', 'I');

              //aux = aux.replace('Ó', 'O');
              aux = aux.replace('Ö', 'O');
              aux = aux.replace('Ô', 'O');
              aux = aux.replace('Ò', 'O');
              aux = aux.replace('Õ', 'O');

              //aux = aux.replace('Ú', 'U');
              aux = aux.replace('Ü', 'U');
              aux = aux.replace('Û', 'U');
              aux = aux.replace('Ù', 'U');

              //aux = aux.replace('Ñ', 'N');


              aux = aux.replace('Ç', 'C');
              aux = aux.replace('Ý', 'Y');

              //aux = aux.replace('á', 'a');
              aux = aux.replace('à', 'a');
              aux = aux.replace('ã', 'a');
              aux = aux.replace('â', 'a');
              aux = aux.replace('ä', 'a');
              aux = aux.replace('å', 'a');

              //aux = aux.replace('é', 'e');
              aux = aux.replace('è', 'e');
              aux = aux.replace('ê', 'e');
              aux = aux.replace('ë', 'e');

              //aux = aux.replace('í', 'i');
              aux = aux.replace('ì', 'i');
              aux = aux.replace('î', 'i');
              aux = aux.replace('ï', 'i');

              //aux = aux.replace('ó', 'o');
              aux = aux.replace('ò', 'o');
              aux = aux.replace('ô', 'o');
              aux = aux.replace('ö', 'o');
              aux = aux.replace('õ', 'o');

              //aux = aux.replace('ú', 'u');
              aux = aux.replace('ù', 'u');
              aux = aux.replace('ü', 'u');
              aux = aux.replace('û', 'u');

              //aux = aux.replace('ñ', 'n');

              aux = aux.replace('ç', 'c');
              aux = aux.replace('ÿ', 'y');
              aux = aux.replace('ý', 'y');

              if (replaceSpaces)
              {
                  aux = aux.replace(' ', '_');
              }
              int l = aux.length();
              for (int x = 0; x < l; x++)
              {
                  char ch = aux.charAt(x);
                  if(Character.isLetterOrDigit(ch) || Character.isWhitespace(ch)){
                    ret.append(ch);
                  }
              }
              //Character.isl
              aux = ret.toString();
              return aux;
         }
                        
        
        /**
	 * Descarga un fichero jpeg y lo guarda en e:/foto.jpg
	 *
	 * @param args
	 */
	public static File downloadWebFile(String fileUrl, String localPath2save)
        {
            try {
                    // Url con la foto
                    //http://mas.lne.es/fotos/img/2007/10/62/203093470447035b9bc5a095.43783284-foto_verano.jpg
                    URL url = new URL(fileUrl);
                
                    // establecemos conexion
                    URLConnection urlCon = url.openConnection();

                    // Sacamos por pantalla el tipo de fichero
                    //System.out.println(urlCon.getContentType());

                    // Se obtiene el inputStream de la foto web y se abre el fichero
                    // local.
                    InputStream is = urlCon.getInputStream();
                    File newLocalFile=new File(localPath2save);
                    
                    newLocalFile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(newLocalFile, true);

                    // Lectura de la foto de la web y escritura en fichero local
                    byte[] array = new byte[1000]; // buffer temporal de lectura.
                    int leido = is.read(array);
                    while (leido > 0) {
                            fos.write(array, 0, leido);
                            leido = is.read(array);
                    }

                    // cierre de conexion y fichero.
                    is.close();
                    fos.close();
                    return newLocalFile;
                } catch (Exception e) {
                        e.printStackTrace();
                }
            return null;
        }
        
        
        
        public static boolean isPointInsideCoodinates(double latitude, double longitude, GeoLocation[] geolocation)
        {
            if(latitude==0 || longitude==0 || geolocation==null) return false;
            
            double south=geolocation[0].getLatitudeInDegrees();
            double west=geolocation[0].getLongitudeInDegrees();
            double north=geolocation[1].getLatitudeInDegrees();
            double east=geolocation[1].getLongitudeInDegrees();
            if(north>=latitude && south<=latitude && east>=longitude && west<=longitude)
            {
                return true;
            }
            return false;
        }
        
        
        public static void sendEmail2UserGroup(UserGroup userGroup, String body)
        {
            //TODO:Ver si se utiliza este metodo e implementarlo de ser el caso.
        }
        
        /*
         * Metodo que obtiene un estado de un país mediante los parametros recibidos
         * @param country país a buscar
         * @param latitude latitude a buscar en un país
         * @param longitude longitude a buscar en un país
         * @return CountryState Estado del país donde encontro las coordenadas
         * @return null si no encontró las coodenadas en ningún estato del país recibido
         */
        public static CountryState getMessageMapState(Country country, float latitude, float longitude)
        {
            if(country!=null && latitude!=0 && longitude!=0)
            {
                Iterator <CountryState> itCountryStates=country.listStatesInvs();
                while(itCountryStates.hasNext())
                {
                    CountryState countryState=itCountryStates.next();
                    //Si se cumple el siguiente if, el mensaje proviene del estado en cuestio.
                    if(countryState.getNorth()>=latitude && countryState.getSouth()<=latitude && 
                            countryState.getEast()>=longitude && countryState.getWest()<=longitude)
                    {
                        return countryState;
                    }
                }
            }
            return null; 
        }
        
        
        /*
         * Metodo que obtiene un país mediante los parametros recibidos
         * @param latitude latitude a buscar en la lista de paises
         * @param longitude longitude a buscar en la lista de paises
         * @return Country que cumple con las coordenadas recibidas
         * @return null si no encontró las coodenadas en ningún país del catálogo
         */
        public static Country getMessageMapCountry(float latitude, float longitude)
        {
            if(latitude!=0 && longitude!=0)
            {
                Iterator <Country> itCountries=Country.ClassMgr.listCountries(SWBContext.getAdminWebSite());
                while(itCountries.hasNext())
                {
                    Country country=itCountries.next();
                    //Si se cumple el siguiente if, el mensaje proviene del estado en cuestio.
                    if(country.getNorth()>=latitude && country.getSouth()<=latitude && 
                            country.getEast()>=longitude && country.getWest()<=longitude)
                    {
                        return country;
                    }
                }
            }
            return null; 
        }
        

        
         //Returns person's age based in his/her bornDate
         public static int calculateAge(Date bornDate) 
         {
            Calendar cal = Calendar.getInstance(); // current date
            int currYear = cal.get(Calendar.YEAR);
            int currMonth = cal.get(Calendar.MONTH);
            int currDay = cal.get(Calendar.DAY_OF_MONTH);
            cal.setTime(bornDate); // now born date
            int years = currYear - cal.get(Calendar.YEAR);
            int bornMonth = cal.get(Calendar.MONTH);
            if (bornMonth == currMonth) { // same month
                return cal.get(Calendar.DAY_OF_MONTH) <= currDay ? years: years - 1;
            } else {
                return bornMonth < currMonth ? years : years - 1;
            }
	}
        

        //Diferencias entre dos fechas
        //@param fechaInicial La fecha de inicio
        //@param fechaFinal  La fecha de fin
        //@return Retorna el numero de dias entre dos fechas
        public static int Datediff(Date fechaInicial, Date fechaFinal) {

            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
            String fechaInicioString = df.format(fechaInicial);
            try {
                fechaInicial = df.parse(fechaInicioString);
            } catch (ParseException ex) {
            }

            String fechaFinalString = df.format(fechaFinal);
            try {
                fechaFinal = df.parse(fechaFinalString);
            } catch (ParseException ex) {
            }

            long fechaInicialMs = fechaInicial.getTime();
            long fechaFinalMs = fechaFinal.getTime();
            long diferencia = fechaFinalMs - fechaInicialMs;
            double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
            return ((int) dias);
        }

        /*
         * Renplaza tags de emails, documentar este tipo de tags que puede poner el usuario en los mensajes de 
         * correos.
         */
        public static String replaceTags(String message, PostIn postIn, SocialSite socialSite, Stream stream, SocialNetwork socialNetwork) 
        {
            try 
            {
                message = SWBUtils.TEXT.replaceAll(message, "{brand.title}", socialSite.getTitle());
                message = SWBUtils.TEXT.replaceAll(message, "{stream.title}", stream.getTitle());
                message = SWBUtils.TEXT.replaceAll(message, "{net.title}", socialNetwork.getTitle());
                if(postIn.getSocialTopic()!=null)
                {
                    message = SWBUtils.TEXT.replaceAll(message, "{postIn.topic}", postIn.getSocialTopic().getTitle());
                }
                if(postIn.getPostInSocialNetworkUser()!=null)
                {
                    message = SWBUtils.TEXT.replaceAll(message, "{postIn.user}", postIn.getPostInSocialNetworkUser().getSnu_name());
                    message = SWBUtils.TEXT.replaceAll(message, "{postIn.userklout}", ""+postIn.getPostInSocialNetworkUser().getSnu_klout()); 
                }

                message = SWBUtils.TEXT.replaceAll(message, "{post.message}", postIn.getMsg_Text());
            } catch (Exception e) {
                log.error(e);
            }

            return message;
        }
        
        /*
         * Regresa el string relacionado con la llave y el lenguaje pasados como parametros
         * TODO:Cargarlo solo una vez desde el metodo init de este singleton y mandar llamar con los parametros.
         */
        public static String getStringFromGenericLocale(String key, String lang)
        {
            String strLocale=SWBUtils.TEXT.getLocaleString("org.semanticwb.social.admin.resources.locales.generic", key,new Locale(lang));
            if(strLocale!=null)
            {
                try
                {
                    return SWBUtils.TEXT.encode(strLocale, "utf8");                    
                }catch(Exception e)
                {
                    return strLocale;
                }                
            }
            return null;
        }
        
        /**
         * Replaces full urls with shorten urls.
         * @param text with full urls
         * @return text with shorten urls
         */
        public static String shortUrl(String urlLong) {
            String parsedMessage = "";
            if (urlLong != null && !urlLong.isEmpty()) {
                String delimiter = " ";
                String[] temp = urlLong.split(delimiter);
                for (int i = 0; i < temp.length; i++) {
                    if ((temp[i].startsWith("http://") || temp[i].startsWith("https://")) && ((temp[i].length() > 9))) {
                        tinyURL tU = new tinyURL();
                        temp[i] = tU.getTinyURL(temp[i]);
                    }
                    parsedMessage += temp[i] + " ";
                }
            }
            return parsedMessage;
        }
        
    }
    
}
