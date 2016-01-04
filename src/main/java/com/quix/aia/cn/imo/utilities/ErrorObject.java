package com.quix.aia.cn.imo.utilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.locale.LocaleObject;

public class ErrorObject {
	
    public ErrorObject(String errorMsg, String errorField, LocaleObject localeObj)
    {
        this.errorMsg =localeObj!=null ?  localeObj.getTranslatedText(errorMsg) : errorMsg;
        this.errorField = localeObj!=null ?localeObj.getTranslatedText(errorField) : errorField;
    }

    public ErrorObject()
    {
        errorMsg = "";
        errorField = "";
    }

    public String getErrorField()
    {
        return errorField;
    }

    public String getErrorMsg()
    {
        return errorMsg + errorField;
    }

    public void setErrorField(String string)
    {
        errorField = string;
    }

    public void setErrorMsg(String string)
    {
        errorMsg = string;
    }

    private String errorMsg;
    private String errorField;
    
}
