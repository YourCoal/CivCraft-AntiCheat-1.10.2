package com.avrgaming.civcraftac;

public class Log
{

    public Log()
    {
    }

    public static void info(String message)
    {
        System.out.println((new StringBuilder()).append("[CivCraftAC] ").append(message).toString());
    }
}
