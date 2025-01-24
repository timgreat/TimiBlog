package com.timi.runner;

import com.timi.entity.Article;
import com.timi.mapper.ArticleMapper;
import com.timi.utils.RedisCache;
import com.timi.utils.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//实现CommandLineRunner接口以完成在程序启动时进行指定处理
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;
    @Override
    public void run(String... args) throws Exception {
        //查询博客信息
        List<Article> articles=articleMapper.selectList(null);
        Map<String,Integer> viewCountMap=articles.stream()
                .collect(Collectors.toMap(o->o.getId().toString(),a->{
                    return a.getViewCount().intValue();
                }));
        //存储到Redis中
        redisCache.setCacheMap(SysConstant.REDIS_KEY_OF_VIEWCOUNT,viewCountMap);
    }
}
