package com.quix.aia.cn.imo.utilities;

import com.quix.aia.cn.imo.interfaces.FormPageInterface;


public class FormObj
implements FormPageInterface,Cloneable
{

public FormObj(String functionName, String path, String fileExtension, String key, int noOfPages,String headerImageURL, String headerText)
{
    this.functionName = functionName;
    this.path = path;
    this.fileExtension = fileExtension;
    index = 1;
    this.noOfPages = 0;
    this.key = key;
    this.noOfPages = noOfPages;
    canCreate = false;
    canUpdate = false;
    this.headerImageURL = headerImageURL;
    this.headerText = headerText;
}

public int getIndex()
{
    return index;
}

public String getKey()
{
    return key;
}

public int getNoOfPages()
{
    return noOfPages;
}

public void setIndex(int i)
{
    index = i;
}

public void setKey(String string)
{
    key = string;
}

public void setNoOfPages(int i)
{
    noOfPages = i;
}

public boolean hasNext()
{
    return index < noOfPages;
}

public boolean hasPrev()
{
    return index > 1;
}

public void next()
{
    index++;
}

public void prev()
{
    index--;
}

public String getFormType()
{
    return formType;
}

public void setFormType(String string)
{
    formType = string;
    nextPageLink = "FormManager?key=" + key + "&" + "type" + "=" + "NEW" + "&" + "action" + "=" + "NEXT_STEP";
    prevPageLink = "FormManager?key=" + key + "&" + "type" + "=" + "NEW" + "&" + "action" + "=" + "PREV_STEP";
}

public String getNextPageLink()
{
    return nextPageLink;
}

public String getPrevPageLink()
{
    return prevPageLink;
}

public String getPath()
{
    return path + index + fileExtension;
}

public String getFileExtension()
{
    return fileExtension;
}

public String getFunctionName()
{
    return functionName;
}

public void setFileExtension(String string)
{
    fileExtension = string;
}

public void setFunctionName(String string)
{
    functionName = string;
}

public void setPath(String string)
{
    path = string;
}

public String getIndexStr()
{
    return index + "";
}

public String getNoOfPagesStr()
{
    return noOfPages + "";
}

public boolean isCanCreate()
{
    return canCreate;
}

public boolean isCanUpdate()
{
    return canUpdate;
}

public void setCanCreate(boolean b)
{
    canCreate = b;
}

public void setCanUpdate(boolean b)
{
    canUpdate = b;
}

@Override
public Object clone() throws CloneNotSupportedException {
	// TODO Auto-generated method stub
	return super.clone();
}
private String key;
private int noOfPages;
private int index;
private String formType;
private String nextPageLink;
private String prevPageLink;
private String functionName;
private String path;
private String fileExtension;
private boolean canCreate;
private boolean canUpdate;
private String headerImageURL;
private String headerText;

}