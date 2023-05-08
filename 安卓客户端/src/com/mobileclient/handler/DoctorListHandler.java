package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Doctor;
public class DoctorListHandler extends DefaultHandler {
	private List<Doctor> doctorList = null;
	private Doctor doctor;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (doctor != null) { 
            String valueString = new String(ch, start, length); 
            if ("doctorNo".equals(tempString)) 
            	doctor.setDoctorNo(valueString); 
            else if ("password".equals(tempString)) 
            	doctor.setPassword(valueString); 
            else if ("departmentObj".equals(tempString)) 
            	doctor.setDepartmentObj(new Integer(valueString).intValue());
            else if ("name".equals(tempString)) 
            	doctor.setName(valueString); 
            else if ("sex".equals(tempString)) 
            	doctor.setSex(valueString); 
            else if ("doctorPhoto".equals(tempString)) 
            	doctor.setDoctorPhoto(valueString); 
            else if ("education".equals(tempString)) 
            	doctor.setEducation(valueString); 
            else if ("inDate".equals(tempString)) 
            	doctor.setInDate(Timestamp.valueOf(valueString));
            else if ("telephone".equals(tempString)) 
            	doctor.setTelephone(valueString); 
            else if ("visiteTimes".equals(tempString)) 
            	doctor.setVisiteTimes(new Integer(valueString).intValue());
            else if ("memo".equals(tempString)) 
            	doctor.setMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Doctor".equals(localName)&&doctor!=null){
			doctorList.add(doctor);
			doctor = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		doctorList = new ArrayList<Doctor>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Doctor".equals(localName)) {
            doctor = new Doctor(); 
        }
        tempString = localName; 
	}

	public List<Doctor> getDoctorList() {
		return this.doctorList;
	}
}
