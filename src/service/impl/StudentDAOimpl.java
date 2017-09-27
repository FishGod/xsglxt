package service.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import db.MyHibernateSessionFactory;
import entity.Students;
import service.StudentsDAO;

//学生业务逻辑接口的实现类
public class StudentDAOimpl implements StudentsDAO {
	public StudentDAOimpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	//查询所有学生资料
	public List<Students> queryAllStudents() {
		Transaction tx = null;
		List<Students> list = null;
		String hql = "";
		try {
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql = "from Students";
			Query query = session.createQuery(hql);
			list = query.list();
			tx.commit();
			// session.close();
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
			tx.commit();
			return list;

		} finally {
			if (tx != null) {
				tx = null;
				System.out.println("事务置空");
			}

		}

	}

	public Students queryStudentsbySid(String sid) {
		Transaction tx = null;
		Students s = null;
		try {
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			s = (Students) session.get(Students.class, sid);
			tx.commit();
			return s;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.commit();
			return s;
		} finally {
			if (tx != null) {
				tx = null;
			}
		}
	}

	public boolean updateStudents(Students s) {
		Transaction tx = null;
		// Students s2 = null;
		try {
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			session.update(s);
			tx.commit();
			return true;
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			tx.commit();
			return false;
		} finally {
			if (tx != null) {
				tx = null;
			}

		}
	}
	//添加学生资料
	public boolean addStudents(Students s) {
		s.setSid(getNewSid());//设置学生的学号
		Transaction tx = null;
		try {
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			session.save(s);
			tx.commit();
			return true;
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			return false;
		} finally {
			if (tx != null)
				tx = null;

		}
	}
	//删除学生资料
	public boolean deleteStudents(String sid) {
		Transaction tx2 = null;

		try {
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx2 = session.beginTransaction();
			Students s = (Students) session.get(Students.class, sid);
			session.delete(s);
			tx2.commit();
			// session.close();
			return true;

		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			// tx.commit();

			return false;

		} finally {
			if (tx2 != null)
				tx2 = null;
		}

	}

	//生成学生学号
	public String getNewSid() {
		Transaction tx = null;
		String hql = "";
		String sid = null;
		try {
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			//获得当前学生的最大编号
			hql = "select max(sid) from Students";
			Query query = session.createQuery(hql);
			sid = (String) query.uniqueResult();
			//表中没有数据
			if (sid == null || "".equals(sid)) {
				//给一个默认的最大编号
				sid = "S0000001";
			} else {
				String temp = sid.substring(1);//去掉第一个字母S，取后面的值
				//System.out.println(temp);
				int i = Integer.parseInt(temp);//转成数字
				i++;
				temp = String.valueOf(i);//还原成字符串
				int len = temp.length();//获取字符串长度
				//凑够7位
				for (int j = 0; j < 7 - len; j++) {
					temp = "0" + temp;
				}
				sid = "S" + temp;
			}
			tx.commit();
			return sid;
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			tx.commit();
			return sid;
		} finally {
			if (tx != null)
				tx = null;

		}
	}
}