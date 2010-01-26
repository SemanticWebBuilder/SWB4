
import com.infotec.appfw.exception.AFException;
import com.infotec.wb.lib.WBParamRequest;
import com.infotec.wb.resources.GenericResource;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier.solis
 */
public class ResourceWB3Test extends GenericResource
{
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, WBParamRequest paramsRequest) throws AFException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("Hola Mundo");
    }

}
