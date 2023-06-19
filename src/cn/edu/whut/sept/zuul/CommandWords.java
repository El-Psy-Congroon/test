package cn.edu.whut.sept.zuul;

public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
            "go", "quit", "help","look","back","take","drop","dropall","items"
    };

    /**
     * 构造函数-初始化命令字。
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * 检查给定的字符串是否是有效的命令字。
     * @return 是则返回true，否则返回false
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /**
     * 向用户输出所有有效命令。
     */
    public void showAll()
    {
        for(String command: validCommands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
