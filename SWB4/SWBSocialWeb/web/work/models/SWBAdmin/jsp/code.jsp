<%-- 
    Document   : code
    Created on : 22/07/2013, 06:37:17 PM
    Author     : francisco.jimenez
--%>

<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.DataOutputStream"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>


<%!
public void upload(String token, String clientID, String developerKey,String xml, File file)
{
    String boundary = "f93dcbA3";
          try
                {
                        URL url = new URL("http://uploads.gdata.youtube.com/feeds/api/users/default/uploads");
                        System.out.println("url:" + url);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.setRequestMethod("POST");
                        conn.setUseCaches(false);

                        conn.setRequestProperty("Host", "uploads.gdata.youtube.com");
                        conn.setRequestProperty("Authorization", "Bearer " + token);
                        conn.setRequestProperty("GData-Version", "2");
                        //conn.setRequestProperty("X-GData-Client", clientID);
                        conn.setRequestProperty("X-GData-Key", "key=" + developerKey);
                        conn.setRequestProperty("Slug", file.getName());
                        conn.setRequestProperty("Content-Type", "multipart/related; boundary=\"" + boundary + "\"");
//                          conn.setRequestProperty("Content-Length", getLength());
                        conn.setRequestProperty("Connection", "close");

                        DataOutputStream writer = new DataOutputStream(conn.getOutputStream());

                        writer.write(("\r\n--" + boundary + "\r\n").getBytes());
                        writer.write("Content-Type: application/atom+xml; charset=UTF-8\r\n\r\n".getBytes());
                        writer.write(getXml().getBytes("UTF-8"));
                        writer.write(("--" + boundary + "\r\n").getBytes());
                        writer.write("Content-Type: video/mp4\r\n".getBytes());
                        writer.write("Content-Transfer-Encoding: binary\r\n\r\n".getBytes());

                        FileInputStream reader = new FileInputStream(file);
                        byte[] array;

                        int bufferSize = Math.min(reader.available(), 2048);
                        array = new byte[bufferSize];

                        int read = 0;

                        read = reader.read(array, 0, bufferSize);

                        while ( read > 0)
                        {
                                writer.write(array, 0, bufferSize);
                                bufferSize = Math.min(reader.available(), 2048);
                                array = new byte[bufferSize];
                                read = reader.read(array, 0, bufferSize);
                        }

                        writer.write(("--" + boundary + "--\r\n").getBytes());

                        writer.flush();
                        writer.close();
                        reader.close();
                        
                        conn.connect();

                        BufferedReader readerl = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                        String s;

                        while ( (s = readerl.readLine()) != null)
                                //Log.d(TAG, s);
                            System.out.print(s);

                        }
                        catch(Exception ex)
                        {
                           //Log.e(TAG, ex.toString());
                            System.out.println("ERROR" + ex.toString());
                            ex.printStackTrace();
                        }
}

private String getXml()
{
  String xml = "<?xml version=\"1.0\"?>\r\n" +
                                                "<entry xmlns=\"http://www.w3.org/2005/Atom\"" + "\r\n" +
                                                "xmlns:media=\"http://search.yahoo.com/mrss/\"\r\n" +
                                                "xmlns:yt=\"http://gdata.youtube.com/schemas/2007\">\r\n" +
                                                "<media:group>\r\n" +
                                                "<media:title type=\"plain\">Bad Wedding Toast</media:title>\r\n" +
                                                "<media:description type=\"plain\">\r\n" +
                                                "I gave a bad toast at my friend's wedding.\r\n" +
                                                "</media:description>\r\n" +
                                                "<media:category\r\n" +
                                                "scheme=\"http://gdata.youtube.com/schemas/2007/categories.cat\">People\r\n" +
                                                "</media:category>\r\n" +
                                                "<media:keywords>toast, wedding</media:keywords>\r\n" +
                                                "</media:group>\r\n" +
                                                "</entry>\r\n";

        return xml;
}
%>

<%
    upload("ya29.AHES6ZSFVpH-SWlHlQ1Ez5UFUuzMSo0P_sc72dBCI2ZOlyfyKFiyAzjm", "", "AI39si6qGbdkMEmoCbOWPMzvpQS5NePu1UR946TwwxEKah3AMB8e49-mvcTyUOnqmtV5bK5JXJG91toC-e77-jq_F_KAvxxPkg", getXml(), new File("C:/Users/francisco.jimenez/Videos/Video.asf"));
%>