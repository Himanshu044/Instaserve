package com.example.instaserve;

public class ServiousProvider {

    private String name;
    private String Category;
    private String image;
    private String contactNo;
    private String  whatapp;
    private String title;
    private String locality;
    private String aadhar;
    private String address;
    private String Charge;

    @Override
    public String toString() {
        return "ServiousProvider{" +
                "name='" + name + '\'' +
                ", Category='" + Category + '\'' +
                ", image='" + image + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", whatapp='" + whatapp + '\'' +
                ", title='" + title + '\'' +
                ", locality='" + locality + '\'' +
                ", aadhar='" + aadhar + '\'' +
                ", address='" + address + '\'' +
                ", Charge='" + Charge + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getWhatapp() {
        return whatapp;
    }

    public void setWhatapp(String whatapp) {
        this.whatapp = whatapp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCharge() {
        return Charge;
    }

    public void setCharge(String charge) {
        Charge = charge;
    }
}
