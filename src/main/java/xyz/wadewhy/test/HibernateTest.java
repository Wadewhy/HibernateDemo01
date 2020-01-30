package xyz.wadewhy.test;

import org.hibernate.Session;
import org.hibernate.Transaction;

import xyz.wadewhy.pojo.User;
import xyz.wadewhy.util.HibernateUtil;

public class HibernateTest {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        try {
            User User = new User();
            User.setId(4);
            User.setPwd("123");
            User.setName("hibernate demo");
            session.save(User);// 这里操作的是java对象
            tx.commit();
            System.out.println("保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            System.out.println("保存失败!");
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
