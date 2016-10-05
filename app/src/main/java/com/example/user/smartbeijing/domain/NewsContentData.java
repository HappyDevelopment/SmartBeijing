package com.example.user.smartbeijing.domain;

import java.util.List;

/**
 * Describe :  新闻数据bean
 * Created by 王兆琦 on 2016/10/4 00:03.
 * Email    : wzq1551159@gmail.com
 */

public class NewsContentData {
    public int retcode;

    public List<NewsData> data;//新闻的数据

    /**
     * 新闻内容又有子内容
     */
    public class NewsData{

        public List<ViewTagData> children;

        public class ViewTagData{
            public String id;
            public String title	;
            public int type ;
            public String url;
        }

        public String id;

        public String title;
        public int type;


        public String url;
        public String url1;

        public String dayurl;
        public String excurl;

        public String weekurl;
    }

    public List<String> extend;
}
