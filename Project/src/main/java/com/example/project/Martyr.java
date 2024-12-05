package com.example.project;

public class Martyr implements Comparable<Martyr>{
    private String name;
    private String dateOfDeath;
    private int age;
    private String location;
    private String district;
    private String gender;

    public Martyr(String name, String dateOfDeath, int age, String location, String district, String gender) {
        this.name = name;
        this.dateOfDeath = dateOfDeath;
        this.age = age;
        this.location = location;
        this.district = district;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(String dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    // Compare ages of this Martyr with other Martyr
    @Override
    public int compareTo(Martyr other) {
        return this.age - other.age;
    }

    @Override
    public String toString() {
        return "Martyr{" + "name=" + name + ", dateOfDeath=" + dateOfDeath + ", age=" + age + ", location=" + location + ", district=" + district + ", gender=" + gender + '}';
    }

}
