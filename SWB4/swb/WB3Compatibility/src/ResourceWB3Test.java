
import com.infotec.appfw.exception.AFException;
import com.infotec.topicmaps.Topic;
import com.infotec.wb.core.Resource;
import com.infotec.wb.core.WBUser;
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
        WBUser user=paramsRequest.getUser();
        Topic topic=paramsRequest.getTopic();
        Resource resource=paramsRequest.getResourceBase();
        out.println("Hola Mundo");
        out.println("User:"+user.getEmail());
        out.println("Topic:"+topic.getDisplayName());
        out.println("Resource:"+resource.getId()+" "+resource.getTitle());
    }

}
