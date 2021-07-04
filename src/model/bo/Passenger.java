/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bo;

/**
 *
 * @author Johan C
 */
public class Passenger {
    private final int profileId;
    private final String userName;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String telNo;
    private final String email;

    public Passenger(PassengerBuilder builder) {
        this.profileId = builder.profileId;
        this.userName = builder.userName;
        this.password = builder.password;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.address = builder.address;
        this.telNo = builder.telNo;
        this.email = builder.email;
    }
    
    public int getProfileId(){
        return profileId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the telNo
     */
    public String getTelNo() {
        return telNo;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Passenger: ").append(this.profileId).append(", ").append(this.userName).append(", ").append(this.password).append(", ").append(this.firstName).append(", ").append(this.lastName).append(", ")
                .append(this.address).append(", ").append(this.telNo).append(", ").append(this.email);
        return sb.toString();
    }

    public static class PassengerBuilder {

        private final String userName;
        private final String password;
        private int profileId;
        private String firstName;
        private String lastName;
        private String address;
        private String telNo;
        private String email;

        public PassengerBuilder(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public PassengerBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PassengerBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PassengerBuilder address(String address) {
            this.address = address;
            return this;
        }

        public PassengerBuilder telNo(String telNo) {
            this.telNo = telNo;
            return this;
        }

        public PassengerBuilder email(String email) {
            this.email = email;
            return this;
        }

        public Passenger build() {
            Passenger Passenger = new Passenger(this);
            return Passenger;
        }
    }
}
