package edu.javacourse.student.domain;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
public class Adult extends Person {

    private String passportSeries;
    private String passportNumber;
    private LocalDate issueDate;

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
}
