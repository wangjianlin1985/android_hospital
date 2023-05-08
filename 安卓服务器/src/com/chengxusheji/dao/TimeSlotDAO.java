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
import com.chengxusheji.domain.TimeSlot;

@Service @Transactional
public class TimeSlotDAO {

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
    public void AddTimeSlot(TimeSlot timeSlot) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(timeSlot);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<TimeSlot> QueryTimeSlotInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From TimeSlot timeSlot where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List timeSlotList = q.list();
    	return (ArrayList<TimeSlot>) timeSlotList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<TimeSlot> QueryTimeSlotInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From TimeSlot timeSlot where 1=1";
    	Query q = s.createQuery(hql);
    	List timeSlotList = q.list();
    	return (ArrayList<TimeSlot>) timeSlotList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<TimeSlot> QueryAllTimeSlotInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From TimeSlot";
        Query q = s.createQuery(hql);
        List timeSlotList = q.list();
        return (ArrayList<TimeSlot>) timeSlotList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From TimeSlot timeSlot where 1=1";
        Query q = s.createQuery(hql);
        List timeSlotList = q.list();
        recordNumber = timeSlotList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public TimeSlot GetTimeSlotByTimeSlotId(int timeSlotId) {
        Session s = factory.getCurrentSession();
        TimeSlot timeSlot = (TimeSlot)s.get(TimeSlot.class, timeSlotId);
        return timeSlot;
    }

    /*����TimeSlot��Ϣ*/
    public void UpdateTimeSlot(TimeSlot timeSlot) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(timeSlot);
    }

    /*ɾ��TimeSlot��Ϣ*/
    public void DeleteTimeSlot (int timeSlotId) throws Exception {
        Session s = factory.getCurrentSession();
        Object timeSlot = s.load(TimeSlot.class, timeSlotId);
        s.delete(timeSlot);
    }

}
