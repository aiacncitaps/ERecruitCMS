package com.archerlogic.aia.cn.helpers.iCalendar;

public class iCalendarResponseClass
{
    public final String SUCCESS = "00";
    
    private String returnCode;
    private String returnMessage;

    public String getReturnCode()
    {
        return returnCode;
    }

    public void setReturnCode(String returnCode)
    {
        this.returnCode = returnCode;
    }

    public String getReturnMessage()
    {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage)
    {
        this.returnMessage = returnMessage;
    }

    public boolean isSuccess()
    {
        return (returnCode != null && returnCode.equals(SUCCESS));
    }
    
}
