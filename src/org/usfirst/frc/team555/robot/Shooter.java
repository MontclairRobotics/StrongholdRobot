/**
 * 
 */
public class Shooter
{
    private Motor[] motor;
    
    public Shooter()
    {
        for(int i=0;i<2;i++)
        {
            motor[i]=new Motor('s',i);
        }
    }
}
