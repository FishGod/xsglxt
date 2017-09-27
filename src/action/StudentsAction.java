package action;

import java.text.SimpleDateFormat;
import java.util.List;

import entity.Students;
import service.StudentsDAO;
import service.impl.StudentDAOimpl;

//学生Action
public class StudentsAction extends SuperAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    //查询所有学生的动作
	public String query() {
		StudentsDAO sdao = new StudentDAOimpl();
		List<Students> list = sdao.queryAllStudents();
        //放进session中
		if(list!=null && list.size()>0){
		session.setAttribute("students_list", list);
		}
		return "query_success";
	}

	//删除学生动作
	public String delete() {
		StudentsDAO sdao = new StudentDAOimpl();
		String sid = request.getParameter("sid");
		sdao.deleteStudents(sid);//调用删除方法
		return "delete_success";

	}

	public String modify() {
		String sid = request.getParameter("sid");
		StudentsDAO sdao = new StudentDAOimpl();
		Students s = sdao.queryStudentsbySid(sid);
		// 在会话SESSION中保存
		session.setAttribute("modify_students", s);
		return "modify_success";
	}

	public String add() throws Exception {
		Students s = new Students();
		s.setSname(request.getParameter("sname"));
		s.setGender(request.getParameter("gender"));
		s.setAddress(request.getParameter("address"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		s.setBirthday(sdf.parse(request.getParameter("birthday")));
		StudentsDAO sdao = new StudentDAOimpl();
		sdao.addStudents(s);
		return "add_success";
	}

	public String save() throws Exception {
		Students s = new Students();
		s.setSid(request.getParameter("sid"));
		s.setSname(request.getParameter("sname"));
		s.setGender(request.getParameter("gender"));
		s.setAddress(request.getParameter("address"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		s.setBirthday(sdf.parse(request.getParameter("birthday")));
		StudentsDAO sdao = new StudentDAOimpl();
		sdao.updateStudents(s);
		return "save_success";
	}
}
