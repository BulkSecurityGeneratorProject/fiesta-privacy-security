package eu.fiestaiot.security.ui.web.rest.vm;

public class TestbedInformation {

    private String name;
    private String userID;
    private Float  locationLat;
    private Float locationLong;

    private Float deviceLat;
    private Float deviceLong;

    private Long totalDevices;

    private Long totalSharedDevices;

    private Long totalRequest;

    private Long totalUsers;

    private String sharedDevices;

    private String sharedUsers;

    public TestbedInformation() {
    }

    public String getName() {
        return name;
    }

    public TestbedInformation setName(String name) {
        this.name = name;
        return this;
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public TestbedInformation setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
        return this;
    }

    public String getUserID() {
        return userID;
    }

    public TestbedInformation setUserID(String userID) {
        this.userID = userID;
        return this;
    }

    public Float getLocationLat() {
        return locationLat;
    }

    public TestbedInformation setLocationLat(Float locationLat) {
        this.locationLat = locationLat;
        return this;
    }

    public Float getLocationLong() {
        return locationLong;
    }

    public TestbedInformation setLocationLong(Float locationLong) {
        this.locationLong = locationLong;
        return this;
    }

    public Float getDeviceLat() {
        return deviceLat;
    }

    public TestbedInformation setDeviceLat(Float deviceLat) {
        this.deviceLat = deviceLat;
        return this;
    }

    public Float getDeviceLong() {
        return deviceLong;
    }

    public TestbedInformation setDeviceLong(Float deviceLong) {
        this.deviceLong = deviceLong;
        return this;
    }

    public Long getTotalDevices() {
        return totalDevices;
    }

    public TestbedInformation setTotalDevices(Long totalDevices) {
        this.totalDevices = totalDevices;
        return this;
    }

    public Long getTotalSharedDevices() {
        return totalSharedDevices;
    }

    public TestbedInformation setTotalSharedDevices(Long totalSharedDevices) {
        this.totalSharedDevices = totalSharedDevices;
        return this;
    }

    public Long getTotalRequest() {
        return totalRequest;
    }

    public TestbedInformation setTotalRequest(Long totalRequest) {
        this.totalRequest = totalRequest;
        return this;
    }

    public String getSharedDevices() {
        return sharedDevices;
    }

    public TestbedInformation setSharedDevices(String sharedDevices) {
        this.sharedDevices = sharedDevices;
        return this;
    }

    public String getSharedUsers() {
        return sharedUsers;
    }

    public TestbedInformation setSharedUsers(String sharedUsers) {
        this.sharedUsers = sharedUsers;
        return this;
    }

    @Override
    public String toString() {
        return "TestbedInformation{" +
            "name='" + name + '\'' +
            ", userID='" + userID + '\'' +
            ", locationLat=" + locationLat +
            ", locationLong=" + locationLong +
            ", deviceLat=" + deviceLat +
            ", deviceLong=" + deviceLong +
            ", totalDevices=" + totalDevices +
            ", totalSharedDevices=" + totalSharedDevices +
            ", totalRequest=" + totalRequest +
            ", sharedDevices='" + sharedDevices + '\'' +
            ", sharedUsers='" + sharedUsers + '\'' +
            '}';
    }
}
