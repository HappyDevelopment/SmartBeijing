package com.example.user.smartbeijing.domain;

import java.util.List;

/**
 * Describe :
 * Created by 王兆琦 on 2016/10/6 22:26.
 * Email    : wzq1551159@gmail.com
 */

public class TpiNewsData {
    public int				retcode;
    public TPINewsData_Data	data;

    public class TPINewsData_Data
    {
        public String								countcommenturl;
        public String								more;
        public String								title;
        public List<TPINewsData_Data_ListNewsData> news;
        public List<TPINewsData_Data_TopicData>		topic;
        public List<TPINewsData_Data_LunBoData>		topnews;

        public class TPINewsData_Data_ListNewsData
        {
            public String	commentlist;
            public String	commenturl;
            public String	id;
            public String	listimage;
            public String	pubdate;
            public String	title;
            public String	type;
            public String	url;
        }

        public class TPINewsData_Data_TopicData
        {

            public String	description;
            public String	listimage;
            public String	id;
            public String	sort;
            public String	title;
            public String	url;
        }

        public class TPINewsData_Data_LunBoData
        {

            public String	comment;
            public String	commentlist;
            public String	commenturl;
            public String	id;
            public String	pubdate;
            public String	title;
            public String	topimage;
            public String	type;
            public String	url;

        }

    }
}
