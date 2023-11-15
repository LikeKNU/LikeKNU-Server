package ac.knu.likeknu.controller.dto.device.request;

public class DeviceTokenRequest {

    private String deviceId;
    private String token;

    public DeviceTokenRequest(String deviceId, String token) {
        this.deviceId = deviceId;
        this.token = token;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getToken() {
        return token;
    }
}
