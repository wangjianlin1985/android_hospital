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
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.LeaveWord;

@Service @Transactional
public class LeaveWordDAO {

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
    public void AddLeaveWord(LeaveWord leaveWord) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(leaveWord);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LeaveWord> QueryLeaveWordInfo(String title,UserInfo userObj,String replyTime,String replyDoctor,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LeaveWord leaveWord where 1=1";
    	if(!title.equals("")) hql = hql + " and leaveWord.title like '%" + title + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and leaveWord.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!replyTime.equals("")) hql = hql + " and leaveWord.replyTime like '%" + replyTime + "%'";
    	if(!replyDoctor.equals("")) hql = hql + " and leaveWord.replyDoctor like '%" + replyDoctor + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List leaveWordList = q.list();
    	return (ArrayList<LeaveWord>) leaveWordList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LeaveWord> QueryLeaveWordInfo(String title,UserInfo userObj,String replyTime,String replyDoctor) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LeaveWord leaveWord where 1=1";
    	if(!title.equals("")) hql = hql + " and leaveWord.title like '%" + title + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and leaveWord.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!replyTime.equals("")) hql = hql + " and leaveWord.replyTime like '%" + replyTime + "%'";
    	if(!replyDoctor.equals("")) hql = hql + " and leaveWord.replyDoctor like '%" + replyDoctor + "%'";
    	Query q = s.createQuery(hql);
    	List leaveWordList = q.list();
    	return (ArrayList<LeaveWord>) leaveWordList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LeaveWord> QueryAllLeaveWordInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From LeaveWord";
        Query q = s.createQuery(hql);
        List leaveWordList = q.list();
        return (ArrayList<LeaveWord>) leaveWordList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String title,UserInfo userObj,String replyTime,String replyDoctor) {
        Session s = factory.getCurrentSession();
        String hql = "From LeaveWord leaveWord where 1=1";
        if(!title.equals("")) hql = hql + " and leaveWord.title like '%" + title + "%'";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and leaveWord.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!replyTime.equals("")) hql = hql + " and leaveWord.replyTime like '%" + replyTime + "%'";
        if(!replyDoctor.equals("")) hql = hql + " and leaveWord.replyDoctor like '%" + replyDoctor + "%'";
        Query q = s.createQuery(hql);
        List leaveWordList = q.list();
        recordNumber = leaveWordList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public LeaveWord GetLeaveWordByLeaveWordId(int leaveWordId) {
        Session s = factory.getCurrentSession();
        LeaveWord leaveWord = (LeaveWord)s.get(LeaveWord.class, leaveWordId);
        return leaveWord;
    }

    /*����LeaveWord��Ϣ*/
    public void UpdateLeaveWord(LeaveWord leaveWord) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(leaveWord);
    }

    /*ɾ��LeaveWord��Ϣ*/
    public void DeleteLeaveWord (int leaveWordId) throws Exception {
        Session s = factory.getCurrentSession();
        Object leaveWord = s.load(LeaveWord.class, leaveWordId);
        s.delete(leaveWord);
    }

}
