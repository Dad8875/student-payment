package edu.javacourse.student.business;

import edu.javacourse.student.dao.*;
import edu.javacourse.student.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentOrderService.class);

    @Autowired
    private StudentOrderRepository dao;
    @Autowired
    private StreetRepository daoStreet;
    @Autowired
    private StudentOrderStatusRepository daoStatus;
    @Autowired
    private PassportOfficeRepository daoPassport;
    @Autowired
    private RegisterOfficeRepository daoRegister;
    @Autowired
    private UniversityRepository daoUniversity;
    @Autowired
    private StudentOrderChildRepository daoChild;

    @Transactional
    public void testSave() {
        StudentOrder so = new StudentOrder();
        so.setStudentOrderDate(LocalDateTime.now());
        so.setStatus(daoStatus.getOne(1L));

        so.setHusband(buildPerson(false));
        so.setWife(buildPerson(true));

        so.setCertificateNumber("CERTIFICATE");
        so.setRegisterOffice(daoRegister.getOne(1L));
        so.setMarriageDate(LocalDate.now());
        dao.save(so);

        StudentOrderChild soc = buildChild(so);
        daoChild.save(soc);
    }

    private Adult buildPerson(boolean wife) {
        Adult p = new Adult();
        p.setDateOfBirth(LocalDate.now());
        Address a = new Address();
        a.setPostCode("190000");
        a.setBuilding("21");
        a.setExtension("B");
        a.setApartment("199");
        Street one = daoStreet.getOne(1L);
        a.setStreet(one);
        p.setAddress(a);
        if (wife) {
            p.setSurName("Рюрик");
            p.setGivenName("Марфа");
            p.setPatronymic("Васильевна");
            p.setPassportSeries("WIFE_S");
            p.setPassportNumber("WIFE_N");
            p.setPassportOffice(daoPassport.getOne(1L));
            p.setIssueDate(LocalDate.now());
            p.setStudentNumber("12345");
            p.setUniversity(daoUniversity.getOne(1L));

        } else {
            p.setSurName("Рюрик");
            p.setGivenName("Иван");
            p.setPatronymic("Васильевич");
            p.setPassportSeries("HUSBAND_S");
            p.setPassportNumber("HUSBAND_N");
            p.setPassportOffice(daoPassport.getOne(1L));
            p.setIssueDate(LocalDate.now());
            p.setStudentNumber("67890");
            p.setUniversity(daoUniversity.getOne(1L));
        }
        return p;
    }

    private StudentOrderChild buildChild(StudentOrder so) {
        StudentOrderChild p = new StudentOrderChild();
        p.setStudentOrder(so);

        Child child = new Child();
        child.setDateOfBirth(LocalDate.now());
        child.setSurName("Рюрик");
        child.setGivenName("Дмитрий");
        child.setPatronymic("Иванович");
        child.setCertificateDate(LocalDate.now());
        child.setCertificateNumber("BIRTH N");
        child.setRegisterOffice(daoRegister.getOne(1l));

        Address a = new Address();
        a.setPostCode("190000");
        a.setBuilding("21");
        a.setExtension("B");
        a.setApartment("199");
        Street one = daoStreet.getOne(1L);
        a.setStreet(one);
        child.setAddress(a);

        p.setChild(child);

        return p;
    }

    @Transactional
    public void testGet() {
        List<StudentOrder> sos = dao.findAll();
        LOGGER.info(sos.get(0).getWife().getGivenName());
        LOGGER.info(sos.get(0).getChildren().get(0).getChild().getGivenName());
    }
}
