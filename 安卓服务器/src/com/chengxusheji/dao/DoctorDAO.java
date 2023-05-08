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
import com.chengxusheji.domain.Doctor;

@Service @Transactional
public class DoctorDAO {

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
    public void AddDoctor(Doctor doctor) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(doctor);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Doctor> QueryDoctorInfo(String doctorNo,Department departmentObj,String name,String education,String inDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Doctor doctor where 1=1";
    	if(!doctorNo.equals("")) hql = hql + " and doctor.doctorNo like '%" + doctorNo + "%'";
    	if(null != departmentObj && departmentObj.getDepartmentId()!=0) hql += " and doctor.departmentObj.departmentId=" + departmentObj.getDepartmentId();
    	if(!name.equals("")) hql = hql + " and doctor.name like '%" + name + "%'";
    	if(!education.equals("")) hql = hql + " and doctor.education like '%" + education + "%'";
    	if(!inDate.equals("")) hql = hql + " and doctor.inDate like '%" + inDate + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List doctorList = q.list();
    	return (ArrayList<Doctor>) doctorList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Doctor> QueryDoctorInfo(String doctorNo,Department departmentObj,String name,String education,String inDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Doctor doctor where 1=1";
    	if(!doctorNo.equals("")) hql = hql + " and doctor.doctorNo like '%" + doctorNo + "%'";
    	if(null != departmentObj && departmentObj.getDepartmentId()!=0) hql += " and doctor.departmentObj.departmentId=" + departmentObj.getDepartmentId();
    	if(!name.equals("")) hql = hql + " and doctor.name like '%" + name + "%'";
    	if(!education.equals("")) hql = hql + " and doctor.education like '%" + education + "%'";
    	if(!inDate.equals("")) hql = hql + " and doctor.inDate like '%" + inDate + "%'";
    	Query q = s.createQuery(hql);
    	List doctorList = q.list();
    	return (ArrayList<Doctor>) doctorList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Doctor> QueryAllDoctorInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Doctor";
        Query q = s.createQuery(hql);
        List doctorList = q.list();
        return (ArrayList<Doctor>) doctorList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String doctorNo,Department departmentObj,String name,String education,String inDate) {
        Session s = factory.getCurrentSession();
        String hql = "From Doctor doctor where 1=1";
        if(!doctorNo.equals("")) hql = hql + " and doctor.doctorNo like '%" + doctorNo + "%'";
        if(null != departmentObj && departmentObj.getDepartmentId()!=0) hql += " and doctor.departmentObj.departmentId=" + departmentObj.getDepartmentId();
        if(!name.equals("")) hql = hql + " and doctor.name like '%" + name + "%'";
        if(!education.equals("")) hql = hql + " and doctor.education like '%" + education + "%'";
        if(!inDate.equals("")) hql = hql + " and doctor.inDate like '%" + inDate + "%'";
        Query q = s.createQuery(hql);
        List doctorList = q.list();
        recordNumber = doctorList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Doctor GetDoctorByDoctorNo(String doctorNo) {
        Session s = factory.getCurrentSession();
        Doctor doctor = (Doctor)s.get(Doctor.class, doctorNo);
        return doctor;
    }

    /*更新Doctor信息*/
    public void UpdateDoctor(Doctor doctor) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(doctor);
    }

    /*删除Doctor信息*/
    public void DeleteDoctor (String doctorNo) throws Exception {
        Session s = factory.getCurrentSession();
        Object doctor = s.load(Doctor.class, doctorNo);
        s.delete(doctor);
    }

}
