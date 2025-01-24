package com.timi.utils;

public class SysConstant {
    public final static char ARTICLE_STATUS_DRAFT='1';
    public final static char ARTICLE_STATUS_NORMAL='0';
    public static final String STATUS_NORMAL="0";
    public static final String LINK_STATUS_NORMAL="0";
    public final static int START_PAGE=1;
    public final static Long NOBODY_ID=-1L;

    public final static int PAGE_SIZE=10;
    public final static int ROOT_COMMENT=-1;
    public static final String ARTICLE_COMMENT="0";
    public static final String LINK_COMMENT="1";
    public static final Long ADMIN_ID=1L;
    public static final Long TOP_MENU_ID=0L;
    public static final String ADMIN="1";
    public static final String REDIS_KEY_OF_VIEWCOUNT="article:viewCount";
}
