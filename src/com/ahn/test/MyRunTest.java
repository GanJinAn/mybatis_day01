package com.ahn.test;

import com.ahn.dao.UserMapper;
import com.ahn.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyRunTest {

    @Test
    public void test() throws IOException {
        /*1、根据配置文件.xml（全局配置文件）创建一个SqlSessionFactory对象；
            包括数据库的配置信息等
        * 2、sql配置文件，配置每一个sql以及sql的封装规则
        * 3、将sql的配置文件注册到全局配置文件中
        * 4、代码：
        *   1）根据配置文件.xml（全局配置文件）得到一个SqlSessionFactory对象
        *   2）根据SqlSessionFactory对象得到一个SqlSession对象
        *   3）一个SqlSession代表和数据库的一次会话，用完关闭
        *   4）使用sql的唯一标识来执行对应的sql，sql都是封装在sql的配置文件中的
        *   */
        String resource = "./config/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //获取SqlSession实例，能执行已经映射的sql语句
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try {
            //List<User> list=sqlSession.selectList("com.ahn.entity.userMapping.selectUser");
            User user = sqlSession.selectOne("com.ahn.entity.userMapping.selectUser",1);
            System.out.println(user);
/*            for (User user:list) {
                System.out.println(user);
            }*/
        } finally {
            sqlSession.close();
        }
    }

    //通过映射文件与接口的动态绑定，实现对数据库的crud
    //针对接口编程
    @Test
    public void test2() throws IOException {
        /*1、先获得sqlSessionFactory对象
        * 2、取SqlSession实例
        * 3、获取接口的实现类
        * 4、调用接口的方法*/
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取SqlSession实例，能执行已经映射的sql语句
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try {
            UserMapper mapper= sqlSession.getMapper(UserMapper.class);
            User user=mapper.getUserByid(1);
            //MapperProxy:myBatis能自动为接口创建一个代理对象，代理对象执行crud操作
            System.out.println(mapper);
            System.out.println(user);
        } finally {
            sqlSession.close();
        }
    }

    //获取sqlSessionFactory对象
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "./config/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }
}
