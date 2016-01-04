package com.quix.aia.cn.imo.utilities;

public class KeyObjPair {
 public KeyObjPair(Object key, Object obj)
    {
        this.key = null;
        this.obj = null;
        this.key = key;
        this.obj = obj;
    }
    public KeyObjPair()
    {
       
    }

    public Object getKey()
    {
        return key;
    }

    public Object getObj()
    {
        return obj;
    }

    private Object key;
    private Object obj;
}
