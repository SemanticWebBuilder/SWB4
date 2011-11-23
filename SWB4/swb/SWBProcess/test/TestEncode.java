
import java.nio.charset.Charset;
import org.semanticwb.SWBUtils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier.solis.g
 */
public class TestEncode
{
    public static void main(String args[])
    {
        try
        {
            String txt="Solís";
            System.out.print("iso8859-1:");System.out.write(txt.getBytes("iso8859-1"));
            System.out.println();
            System.out.print("utf8:");System.out.write(txt.getBytes("utf8"));
            System.out.println();
            System.out.print("utf16:");System.out.write(txt.getBytes("utf16"));
        }catch(Exception e){e.printStackTrace();}
    }
}
