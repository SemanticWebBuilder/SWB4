package leveldb.test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.util.Map;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.Range;
import org.iq80.leveldb.WriteBatch;
import org.iq80.leveldb.impl.Iq80DBFactory;

/**
 *
 * @author javier.solis.g
 */
public class LevelDBJava
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        long time = System.currentTimeMillis();
        try
        {
            Iq80DBFactory factory=Iq80DBFactory.factory;
            
            Options options = new Options();
            options.createIfMissing(true);
            options.cacheSize(1000 * 1048576); // 100MB cache
            DB db = factory.open(new File("/data/leveldb/example2"), options);

            System.out.println("Open:" + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();

            long c = 0;
            
            long[] sizes = db.getApproximateSizes(new Range(factory.bytes("j"), factory.bytes("jj")));
            c=sizes[0];
           
            System.out.println(c);
            System.out.println("count:" + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
            
            String stats = db.getProperty("leveldb.stats");
            System.out.println(stats);

            try
            {
/*                
                if (c < 10000000)
                {
                    for (long x = c; x < c + 10000000; x++)
                    {
                        db.put(factory.bytes("jei" + x), factory.bytes(""));//"prop"+ran.nextInt());
                    }
                    System.out.println("load:" + (System.currentTimeMillis() - time));
                }
*/ 
                time = System.currentTimeMillis();


                if (c < 10000000)
                {
                    for(int i=0;i<10;i++)
                    {
                        WriteBatch batch = db.createWriteBatch();
                        try
                        {
                            c = 1000000*i;
                            for (long x = c; x < c + 1000000; x++)
                            {
                                batch.put(factory.bytes("jei" + x), factory.bytes(""));//"prop"+ran.nextInt());
                            }
                            db.write(batch);
                        } finally
                        {
                            // Make sure you close the batch to avoid resource leaks.
                            batch.close();
                        }
                    }
                    System.out.println("load batch:" + (System.currentTimeMillis() - time));
                }
                time = System.currentTimeMillis();
                
                String str=null;
                for(int x=0;x<1000;x++)
                {
                    str=factory.asString(db.get(factory.bytes("jei9"+x)));
                }
                System.out.println("get:" +str+" "+ (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();

                DBIterator iterator = db.iterator();
                //System.out.println("iterate 1:" + (System.currentTimeMillis() - time));
                int x=0;
                try
                {
                    iterator.seek(factory.bytes("jei9"));
                    while (iterator.hasNext())
                    {
                        Map.Entry<byte[], byte[]> entry = iterator.next();
                        x++;
                        if(x==1000000)break;
                    }
                } finally
                {
                    // Make sure you close the iterator to avoid resource leaks.
                    iterator.close();
                }
                System.out.println("iterate:" +x+" "+ (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();



            } finally
            {
                db.close();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
