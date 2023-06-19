package cn.edu.whut.sept.zuul;
/**
 * 继承Command类的QuitCommand类
 */
public class QuitCommand extends Command
{
    /**
     * 执行程序
     */
    public boolean execute(Game game)
    {
        if(hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
