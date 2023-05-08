package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.Department;

@Service @Transactional
public class DepartmentDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddDepartment(Department department) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(department);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Department> QueryDepartmentInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Department department where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List departmentList = q.list();
    	return (ArrayList<Department>) departmentList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Department> QueryDepartmentInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Department department where 1=1";
    	Query q = s.createQuery(hql);
    	List departmentList = q.list();
    	return (ArrayList<Department>) departmentList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Department> QueryAllDepartmentInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Department";
        Query q = s.createQuery(hql);
        List departmentList = q.list();
        return (ArrayList<Department>) departmentList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From Department department where 1=1";
        Query q = s.createQuery(hql);
        List departmentList = q.list();
        recordNumber = departmentList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Department GetDepartmentByDepartmentId(int departmentId) {
        Session s = factory.getCurrentSession();
        Department department = (Department)s.get(Department.class, departmentId);
        return department;
    }

    /*更新Department信息*/
    public void UpdateDepartment(Department department) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(department);
    }

    /*删除Department信息*/
    public void DeleteDepartment (int departmentId) throws Exception {
        Session s = factory.getCurrentSession();
        Object department = s.load(Department.class, departmentId);
        s.delete(department);
    }

}
