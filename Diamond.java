
/**
 * Makes a diamond
 */
public class Diamond
{
    public static int SIZE=250;
    public static int WAIT=50;
    public static void main()
    {
        try
        {
            HTTPtest.main();
            while(true)
            {
                int x=0,y=SIZE/2;
                for(int i=0;i<SIZE/2;i++)
                {
                    HTTPtest.setResponse(x+","+y);
                    x++;
                    y--;
                    Thread.sleep(WAIT);
                }
                for(int i=0;i<SIZE/2;i++)
                {
                    HTTPtest.setResponse(x+","+y);
                    x++;
                    y++;
                    Thread.sleep(WAIT);
                }
                for(int i=0;i<SIZE/2;i++)
                {
                    HTTPtest.setResponse(x+","+y);
                    x--;
                    y++;
                    Thread.sleep(WAIT);
                }
                for(int i=0;i<SIZE/2;i++)
                {
                    HTTPtest.setResponse(x+","+y);
                    x--;
                    y--;
                    Thread.sleep(WAIT);
                }
            }   
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
