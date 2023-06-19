/**
 * 一个Commend类代表了用户输入的命令
 */
package com.example.sept2mars.entity;

public class Command
{
    private String commandWord;
    private String secondWord;

    /**
     * Commend类的有参构造器
     * @param firstWord 用户输入的第一个单词
     * @param secondWord 用户输入的第二个单词
     */
    public Command(String firstWord, String secondWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
    }

    /**
     * @return 用户输入的第一个单词
     */
    public String getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return 用户输入的第二个单词
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * 判断用户输入是否为空
     * @return 返回用户输入是否为空
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }

    /**
     * 判断用户是否输入了第二个词
     * @return 输入第二个词返回true，否则返回false
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
}
