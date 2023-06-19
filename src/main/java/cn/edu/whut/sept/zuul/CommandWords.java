package cn.edu.whut.sept.zuul;

import java.util.HashMap;
import java.util.Iterator;

public class CommandWords
{
    private HashMap<String, Command> commands;//以哈希表形式存储指令
    /**
     * 指令集合
     */
    public CommandWords()
    {
        commands = new HashMap<String, Command>();
        commands.put("go", new GoCommand());
        commands.put("help", new HelpCommand(this));
        commands.put("quit", new QuitCommand());
    }
    /**
     * 将单词转化为指令
     * @return Command
     */
    public Command get(String word)
    {
        return (Command)commands.get(word);
    }
    /**
     * 输出所有已知指令
     */
    public void showAll()
    {
        for(Iterator i = commands.keySet().iterator(); i.hasNext(); ) {
            System.out.print(i.next() + "  ");
        }
        System.out.println();
    }
}
