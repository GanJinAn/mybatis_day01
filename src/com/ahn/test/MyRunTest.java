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
        /*1�����������ļ�.xml��ȫ�������ļ�������һ��SqlSessionFactory����
            �������ݿ��������Ϣ��
        * 2��sql�����ļ�������ÿһ��sql�Լ�sql�ķ�װ����
        * 3����sql�������ļ�ע�ᵽȫ�������ļ���
        * 4�����룺
        *   1�����������ļ�.xml��ȫ�������ļ����õ�һ��SqlSessionFactory����
        *   2������SqlSessionFactory����õ�һ��SqlSession����
        *   3��һ��SqlSession��������ݿ��һ�λỰ������ر�
        *   4��ʹ��sql��Ψһ��ʶ��ִ�ж�Ӧ��sql��sql���Ƿ�װ��sql�������ļ��е�
        *   */
        String resource = "./config/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //��ȡSqlSessionʵ������ִ���Ѿ�ӳ���sql���
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

    //ͨ��ӳ���ļ���ӿڵĶ�̬�󶨣�ʵ�ֶ����ݿ��crud
    //��Խӿڱ��
    @Test
    public void test2() throws IOException {
        /*1���Ȼ��sqlSessionFactory����
        * 2��ȡSqlSessionʵ��
        * 3����ȡ�ӿڵ�ʵ����
        * 4�����ýӿڵķ���*/
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //��ȡSqlSessionʵ������ִ���Ѿ�ӳ���sql���
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try {
            UserMapper mapper= sqlSession.getMapper(UserMapper.class);
            User user=mapper.getUserByid(1);
            //MapperProxy:myBatis���Զ�Ϊ�ӿڴ���һ��������󣬴������ִ��crud����
            System.out.println(mapper);
            System.out.println(user);
        } finally {
            sqlSession.close();
        }
    }

    //��ȡsqlSessionFactory����
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "./config/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }
}
