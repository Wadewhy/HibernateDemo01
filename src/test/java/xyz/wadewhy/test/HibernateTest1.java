package xyz.wadewhy.test;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

import xyz.wadewhy.pojo.User;

public class HibernateTest1 {
    @Test
    public void testHibernate() {
        Configuration cfg = null;
        SessionFactory sf = null;
        Session session = null;
        Transaction tx = null;
        try {
            cfg = new Configuration().configure();
            ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
            sf = cfg.buildSessionFactory(registry);
            session = sf.openSession();
            tx = session.beginTransaction();
            // 查询名字含wade的结果
            String name = "wade";
            String sql = "select *from User where name like '%" + name + "%'";
            User user = new User();
            user.setName("lbj");
            user.setPwd("1");
            session.save(user);
            // 查询的结果输出
            Query q = session.createSQLQuery(sql);// 使用Session的createSQLQuery方法执行标准SQL语句
            List<Object[]> list = q.list();// 因为标准SQL语句有可能返回各种各样的结果，比如多表查询，分组统计结果等等。
                                           // 不能保证其查询结果能够装进一个Product对象中，所以返回的集合里的每一个元素是一个对象数组。
                                           // 然后再通过下标把这个对象数组中的数据取出来。
            for (Object[] os : list) {
                for (Object filed : os) {
                    System.out.print(filed + "\t");
                }
                System.out.println();
            }
            // 6.提交事务
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            tx.rollback();
            throw new HibernateException(e.getCause());

        } finally {
            // 7.关闭session
            if (session != null && session.isOpen())
                session.close();
            sf.close();
        }
    }

}
