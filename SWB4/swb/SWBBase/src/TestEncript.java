
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SFBase64;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jei
 */
public class TestEncript {

    public static void main(String arr[])
    {
        String KEY="12345sdfsdf";
        String password=null;
        System.out.println("password:"+password);

        try
        {
            String encpwd="{"+SFBase64.encodeBytes(SWBUtils.CryptoWrapper.PBEAES128Cipher(KEY, password.getBytes()))+"}";
            System.out.println("encpwd:"+encpwd);
            System.out.println("pre:"+new String(SWBUtils.CryptoWrapper.PBEAES128Cipher(KEY, password.getBytes())));

            String pwd=encpwd.substring(1,encpwd.length()-1);
            System.out.println("pwd:"+pwd);

            System.out.println("pre:"+SFBase64.decode(pwd));
            String decpwd=new String(SWBUtils.CryptoWrapper.PBEAES128Decipher(KEY, SFBase64.decode(pwd)));
            System.out.println("decpwd:"+decpwd);


        }catch(Exception e){e.printStackTrace();}
    }
}
