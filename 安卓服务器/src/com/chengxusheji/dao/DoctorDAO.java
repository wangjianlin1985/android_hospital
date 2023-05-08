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
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
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
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Doctor GetDoctorByDoctorNo(String doctorNo) {
        Session s = factory.getCurrentSession();
        Doctor doctor = (Doctor)s.get(Doctor.class, doctorNo);
        return doctor;
    }

    /*����Doctor��Ϣ*/
    public void UpdateDoctor(Doctor doctor) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(doctor);
    }

    /*ɾ��Doctor��Ϣ*/
    public void DeleteDoctor (String doctorNo) throws Exception {
        Session s = factory.getCurrentSession();
        Object doctor = s.load(Doctor.class, doctorNo);
        s.delete(doctor);
    }

}
