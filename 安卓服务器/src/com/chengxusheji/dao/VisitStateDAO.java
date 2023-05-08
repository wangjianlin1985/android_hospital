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
import com.chengxusheji.domain.VisitState;

@Service @Transactional
public class VisitStateDAO {

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
    public void AddVisitState(VisitState visitState) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(visitState);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<VisitState> QueryVisitStateInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From VisitState visitState where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List visitStateList = q.list();
    	return (ArrayList<VisitState>) visitStateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<VisitState> QueryVisitStateInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From VisitState visitState where 1=1";
    	Query q = s.createQuery(hql);
    	List visitStateList = q.list();
    	return (ArrayList<VisitState>) visitStateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<VisitState> QueryAllVisitStateInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From VisitState";
        Query q = s.createQuery(hql);
        List visitStateList = q.list();
        return (ArrayList<VisitState>) visitStateList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From VisitState visitState where 1=1";
        Query q = s.createQuery(hql);
        List visitStateList = q.list();
        recordNumber = visitStateList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public VisitState GetVisitStateByVisitStateId(int visitStateId) {
        Session s = factory.getCurrentSession();
        VisitState visitState = (VisitState)s.get(VisitState.class, visitStateId);
        return visitState;
    }

    /*����VisitState��Ϣ*/
    public void UpdateVisitState(VisitState visitState) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(visitState);
    }

    /*ɾ��VisitState��Ϣ*/
    public void DeleteVisitState (int visitStateId) throws Exception {
        Session s = factory.getCurrentSession();
        Object visitState = s.load(VisitState.class, visitStateId);
        s.delete(visitState);
    }

}
