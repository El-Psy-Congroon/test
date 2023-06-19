package cn.edu.whut.sept.zuul;
/**
 * 继承Command类的HelpCommand类
 */
public class HelpCommand extends Command
{
    private CommandWords commandWords;

    public HelpCommand(CommandWords words)
    {
        commandWords = words;
    }
    /**
     * 执行程序
     */
    public boolean execute(Game game)
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        commandWords.showAll();
        return false;
    }
}
