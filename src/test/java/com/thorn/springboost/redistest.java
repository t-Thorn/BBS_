package com.thorn.springboost;

import com.thorn.springboot.dao.postMapper;
import com.thorn.springboot.model.post;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class redistest {

    @Autowired
    postMapper postMapper;

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        try {
            RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
            StatefulRedisConnection connection = redisClient.connect();
            RedisCommands<String, String> commands = connection.sync();

            //构建一个post
            post post = new post();
            post.setId(12);
            post.setUsername("hhh");
            //存入redis
            List<post> posts = new ArrayList<>();
            posts.add(post);
            commands.set("posts", new String(serialize(posts), "ISO-8859-1"));

            //从redis中取出
            List<post> post1 = (List<post>) unserizlize(commands.get("posts").getBytes
                    ("ISO-8859-1"));
            System.out.println(post1.get(0).getId());
            connection.close();
            redisClient.shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static byte[] serialize(Object obj) {
        ObjectOutputStream obi = null;
        ByteArrayOutputStream bai = null;
        try {
            bai = new ByteArrayOutputStream();
            obi = new ObjectOutputStream(bai);
            obi.writeObject(obj);
            byte[] byt = bai.toByteArray();
            return byt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //反序列化
    public static Object unserizlize(byte[] byt) {
        ObjectInputStream oii = null;
        ByteArrayInputStream bis = null;
        bis = new ByteArrayInputStream(byt);
        try {
            oii = new ObjectInputStream(bis);
            Object obj = oii.readObject();
            return obj;
        } catch (Exception e) {

            e.printStackTrace();
        }


        return null;
    }

}
