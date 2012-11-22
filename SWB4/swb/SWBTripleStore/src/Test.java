
import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.impl.LiteralLabelFactory;
import java.util.StringTokenizer;
import org.apache.lucene.util.NumericUtils;
import org.semanticwb.base.util.LexiSortable;
import org.semanticwb.triplestore.SWBTSUtil;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jei
 */
public class Test
{

    private static String getSegment(String ext, String node)
    {
        String ret=null;
        String tk1=null;
        String tk2=null;
        do
        {
            tk1=null;
            StringTokenizer st=new StringTokenizer(ext,"|");
            if(st.hasMoreTokens())tk1=st.nextToken();
            if(st.hasMoreTokens())tk2=st.nextToken();
            int s=Integer.parseInt(tk2);
            int i=tk1.length()+tk2.length()+3;
            ret=ext.substring(i,s+i);
            ext=ext.substring(s+i);
        }while(tk1!=null && !tk1.equals(node));
        return ret;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        String ext="|subj|10|S123456789|prop|10|P123456789|obj|10|O123456789";
        String node="subj";

        System.out.println(getSegment(ext, node));
        
        
        //String subj="uri|http://www.google.com/data#swb_Class:56";
        String subj="uri|http://www.google.com/data/swb_Class:56";
        //String subj="uri|http://www.google.com/data/swb_Class";
        System.out.println(SWBTSUtil.getSTypeFromSUBJ(subj));
        
        
        Node n=Node.createLiteral(LiteralLabelFactory.create(new Long(-100L)));        
        System.out.println(SWBTSUtil.node2SortString(n));
        n=Node.createLiteral(LiteralLabelFactory.create(new Long(100L)));        
        System.out.println(SWBTSUtil.node2SortString(n));
        n=Node.createLiteral(LiteralLabelFactory.create(new Integer(100)));        
        System.out.println(SWBTSUtil.node2SortString(n));
        n=Node.createLiteral(LiteralLabelFactory.create(new Integer(-100)));        
        System.out.println(SWBTSUtil.node2SortString(n));
        n=Node.createLiteral(LiteralLabelFactory.create("Hola"));        
        System.out.println(SWBTSUtil.node2SortString(n));
        
        double d=-23.5877868768767876;
        String s=NumericUtils.doubleToPrefixCoded(d);
        System.out.println(d);
        System.out.println("("+s+")");
        System.out.println(s.length());
        System.out.println(NumericUtils.prefixCodedToDouble(s));
        
        System.out.println(LexiSortable.toLexiSortable(d));
        System.out.println(LexiSortable.doubleFromLexiSortable(LexiSortable.toLexiSortable(d)));
        

    }

}
