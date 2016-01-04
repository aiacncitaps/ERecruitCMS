package com.quix.aia.cn.imo.utilities;

import java.util.LinkedList;

public class Pager {
	public Pager()
    {
    }
    public Pager(int maxPageItems, int maxIndexPages, LinkedList items, int currentPageNumber, int offset, String tableHeader)
    {
        this.maxPageItems = maxPageItems;
        this.maxIndexPages = maxIndexPages;
        this.items = items;
        this.currentPageNumber = currentPageNumber;
        this.offset = offset;
        this.tableHeader = tableHeader;
    }

    public int getCurrentPageNumber()
    {
        return currentPageNumber;
    }

    public boolean isOffset()
    {
        return items.size() % maxPageItems == 0;
    }

    public LinkedList getItems()
    {
        return items;
    }

    public int getMaxIndexPages()
    {
        return maxIndexPages;
    }

    public int getMaxPageItems()
    {
        return maxPageItems;
    }

    public void setCurrentPageNumber(int i)
    {
        currentPageNumber = i;
    }

    public void setOffset(boolean b)
    {
        isOffset = b;
    }

    public void setItems(LinkedList list)
    {
        items = list;
    }

    public void setMaxIndexPages(int i)
    {
        maxIndexPages = i;
    }

    public void setMaxPageItems(int i)
    {
        maxPageItems = i;
    }

    public int getOffset()
    {
        return offset;
    }

    public void setOffset(int i)
    {
        offset = i;
    }

    public String getTableHeader()
    {
        return tableHeader;
    }

    public void setTableHeader(String string)
    {
        tableHeader = string;
    }

    public int getActualSize()
    {
        return actualSize;
    }

    public void setActualSize(int i)
    {
        actualSize = i;
    }

    private int maxPageItems;
    private int maxIndexPages;
    private LinkedList items;
    private boolean isOffset;
    private int currentPageNumber;
    private int offset;
    private String tableHeader;
    private int actualSize;
}
