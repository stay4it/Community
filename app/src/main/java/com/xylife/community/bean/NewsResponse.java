package com.xylife.community.bean;

import java.util.List;

public class NewsResponse extends Response{

    public int total;
    public int error_code;
    public String reason;
    public List<News> result;

}
