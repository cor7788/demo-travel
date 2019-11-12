package com.example.servlet;

import com.example.domain.Category;
import com.example.service.CategoryService;
import com.example.service.impl.CategoryServiceImpl;
import com.example.utils.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet(name = "CategoryServlet", value = "/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService categoryService = new CategoryServiceImpl();

    public void findAll(HttpServletRequest req, HttpServletResponse resp) {

        List<Category> list = null;

        // 1.先查询 redis 缓存
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> categorySet = jedis.zrangeWithScores("category", 0, -1);
        // 判断缓存数据
        if(categorySet == null || categorySet.size() == 0) {
            // 1.1 如果缓存为空，从数据库查询，并保存到 redis 缓存中
            System.out.println("查询数据库 category...");
            list = categoryService.findAll();
            for(Category category: list) {
                jedis.zadd("category", category.getCid(), category.getCname());
            }
        } else {
            // 1.2 如果缓存不为空，转换缓存数据
            System.out.println("查询 redis category...");
            list = new ArrayList<>();
            for(Tuple tuple: categorySet) {
                Category category = new Category();
                category.setCid((int) tuple.getScore());
                category.setCname(tuple.getElement());
                list.add(category);
            }
        }

        try {
            writeValue(list, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
