package com.lpf.ssm.mybatis;

import com.lpf.ssm.mybatis.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        App app = new App();



    }



    // 根据用户id 查询用户信息
    @Test
    public  void findUserByIdTest() throws Exception{

        // mybatis 配置文件
        String resource = "SqlMapConfig.xml";
        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);

        // 创建会话工厂，传入 mybatis的配置文件的信息
       SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

       // 通过工厂得到SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //
        User user  = sqlSession.selectOne("test.findUserById",10);

        System.out.println("查询出来的结果是：" + user.toString());

        // 释放资源
        sqlSession.close();
    }


    // 根据用户信息查询用户列表
    @Test
    public void findUsersByName() throws IOException {

        String resource = "SqlMapConfig.xml";

        InputStream inputStream = Resources.getResourceAsStream(resource);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        List<User> lists = sqlSession.selectList("test.findUserByName","李");

        if (lists != null && lists.size() > 0) {
            for (int i = 0; i < lists.size(); i++) {
                System.out.println(i + " ==" + lists.get(i));
            }

        }
        sqlSession.close();
    }

    @Test
    public void insertUser() throws IOException{
        String resource = "SqlMapConfig.xml";

        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 插入用户对象， 会自动生成id
        User user = new User();
        user.setUsername("lpf");
        user.setBirthday(new Date());
        user.setSex("0");
        user.setAddress("山西省太原市");


        //sqlSession.insert("test.insertUser",user);  // 没有显示的使用自增主键 last_insert_id()
        sqlSession.insert("test.insertUsetWithAutoId",user);    // 在mapper 中明显的自定义了自增主键
        /*
         还有一种非自增主键返回，使用uuid ，这样的话，需要使用mysql 的uuid() 函数生成主键，需要修改表中id 的字段类型为 String， 长度为35 位
         执行思路： 先通过uuid() 查询到主键，将主键输入到 sql 语句中
         执行 uuid()语句属性相对于insert 语句之前执行 。
          */



        // 提交事务 ---- 这是和查询操作不同的地方
        sqlSession.commit();
        // 关闭会话
        sqlSession.close();
    }


    @Test
    public void deleteUser() throws IOException{

        String resource="SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        int num = sqlSession.delete("test.deleteUser", 29);
        System.out.println("删除的记录的个数：" + num);

        //提交事务
        sqlSession.commit();

        // 关闭会话
        sqlSession.close();

    }


    @Test
    public void updateUser() throws IOException{
        String resources = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resources);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setSex("4");
        user.setAddress("天津");   // 在mapper 中没有指定地址，这里就是设置了地址，也没有用
        user.setBirthday(new Date());
        user.setUsername("aaa");
        user.setId(10);  // 当不指定id 的时候，程序没有报错，但是，数据也没有修改，应为都不知道该修改那一条记录
        sqlSession.update("test.updateUser",user);

        sqlSession.commit();
        sqlSession.close();


    }


    // 方式二： 通过在原始dao开发 （程序员需要写dao 接口和dao 实现类）

    // 方式三：  mapper 代理方法(程序员只需要编写 mapper 接口 (相当于dao 接口))
    //  程序员编写 mapper 接口，需要遵循一些开发规范，mybatis 可以自动生成mapper 接口实现类代理对象
    /**
     * mapper : 开发的开发规范
     *  1. 在 mapper.xml 中 namespace 等于mapper 接口地址， （namespace 命名空间，作用就是对 sql 进行分类管理，理解sql 隔离）
     *      <mapper namespace="com.lpf.ssm.mybatis.mapper.UserMapper"/>
     *  2. mapper.java 接口中的方法名和mapper.xml 中的 statement 的id 一致
     *  3. mapper.java  接口中的方法输入参数类型和 mapper.xml  中statement的 parameterType 指定的类型一致
     *  4.  mapper.java 接口中的方法返回值类型和 mapper.xml 中statement 的 resultType指定类型一致
     */


}
