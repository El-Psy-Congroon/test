/**
 * 该类通过储存一个命令词汇字符串数组来定义游戏中所有有效命令
 */
package com.example.sept2mars.entity;

public class CommandWords
{
    //游戏中所有有效命令
    private static final String[] validCommands = {
            "go", "quit", "help","look","back"
    };

    /**
     * 构造器
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * 判断用户输入是否为有效命令
     * @param aString 输入的命令
     * @return 为有效命令返回true，否则返回false
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        return false;
    }

    /**
     * 显示所有有效命令
     */
    public void showAll()
    {
        for(String command: validCommands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
