using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WBOffice4.Interfaces
{
    public class FlowContentInformation {
    public ResourceInfo resourceInfo;
    public String id;
    public String title;
    public String step;
    public int status;

    
    public override String ToString()
    {
        return title.ToString();
    }


}
}
