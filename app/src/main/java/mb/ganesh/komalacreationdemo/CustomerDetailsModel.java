package mb.ganesh.komalacreationdemo;

public class CustomerDetailsModel {
    String fastName , lastName , primaryNo , secondaryNo , address , regDate , regId;

    public CustomerDetailsModel(String fastName, String lastName, String primaryNo, String secondaryNo, String address, String regDate, String regId) {
        this.fastName = fastName;
        this.lastName = lastName;
        this.primaryNo = primaryNo;
        this.secondaryNo = secondaryNo;
        this.address = address;
        this.regDate = regDate;
        this.regId = regId;
    }

    public String getFastName() {
        return fastName;
    }

    public void setFastName(String fastName) {
        this.fastName = fastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPrimaryNo() {
        return primaryNo;
    }

    public void setPrimaryNo(String primaryNo) {
        this.primaryNo = primaryNo;
    }

    public String getSecondaryNo() {
        return secondaryNo;
    }

    public void setSecondaryNo(String secondaryNo) {
        this.secondaryNo = secondaryNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
}
