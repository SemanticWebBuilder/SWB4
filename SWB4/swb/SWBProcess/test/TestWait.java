/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier.solis.g
 */
public class TestWait
{
    public static void main(String args[])
    {
        new TestWait().execute();
    }
    
    public synchronized void execute()
    {
        long time=System.currentTimeMillis();
        System.out.println("Init main");
        
        final TestWait _this=this;
        final Thread t=Thread.currentThread();
        Thread thread=new Thread()
        {
            @Override
            public void run()
            {
                long time=System.currentTimeMillis();
                System.out.println("Init Thread");
                long x=0;
                while(x<2000000000L)
                {
                    x++;
                }
                System.out.println("End Thread:"+(System.currentTimeMillis()-time));
                t.interrupt();
            }
        };
        thread.start();

        try
        {
            wait(500);
        }catch(InterruptedException e){}        
        
        System.out.println("end main:"+(System.currentTimeMillis()-time));          
    }
}
