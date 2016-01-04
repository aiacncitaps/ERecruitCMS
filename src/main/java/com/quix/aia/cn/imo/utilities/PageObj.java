package com.quix.aia.cn.imo.utilities;

import com.quix.aia.cn.imo.interfaces.FormPageInterface;



public class PageObj
implements FormPageInterface
{

public PageObj(String functionName, String path, String fileExtension, String key, String headerImageURL, String headerText)
{
    this.functionName = functionName;
    this.path = path;
    this.fileExtension = fileExtension;
    this.key = key;
    this.headerImageURL = headerImageURL;
    this.headerText = headerText;
}

public String getFunctionName()
{
    return functionName;
}

public String getPath()
{
    return path + fileExtension;
}

public void setFunctionName(String string)
{
    functionName = string;
}

public void setPath(String string)
{
    path = string;
}

public String getKey()
{
    return key;
}

public void setKey(String string)
{
    key = string;
}

public String getHeaderImageURL()
{
    return headerImageURL;
}

public String getHeaderText()
{
    return headerText;
}

public void setHeaderImageURL(String string)
{
    headerImageURL = string;
}

public void setHeaderText(String string)
{
    headerText = string;
}

private String functionName;
private String path;
private String fileExtension;
private String key;
private String headerImageURL;
private String headerText;
}