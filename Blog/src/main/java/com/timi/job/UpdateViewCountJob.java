package com.timi.job;

import com.timi.entity.Article;
import com.timi.service.ArticleService;
import com.timi.service.ArticleServiceImpl;
import com.timi.utils.RedisCache;
import com.timi.utils.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;
    @Scheduled(cron="5 * * * * ?")
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String,Integer> viewCountMap=redisCache.getCacheMap(SysConstant.REDIS_KEY_OF_VIEWCOUNT);
        
        //更新到数据库中
        List<Article> articles=viewCountMap.entrySet()
                .stream()
                .map(new Function<Map.Entry<String, Integer>, Article>() {
                    @Override
                    public Article apply(Map.Entry<String, Integer> entry) {
                        Article article=new Article();
                        article.setId(Long.valueOf(entry.getKey()));
                        article.setViewCount(entry.getValue().longValue());
                        return article;
                    }
                }).collect(Collectors.toList());
        articleService.updateBatchById(articles);
    }
}
