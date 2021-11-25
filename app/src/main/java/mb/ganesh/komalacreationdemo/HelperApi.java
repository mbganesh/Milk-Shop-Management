package mb.ganesh.komalacreationdemo;

public class HelperApi {

    private final String addCustomerAPI = "http://192.168.1.26:2000/addCustomer";
    private final String allCustomerAPI = "http://192.168.1.26:2000/allCustomer";
    private final String rateUpdateAPI = "http://192.168.1.26:2000/milkRateUpdate";

    public String getRateUpdateAPI() {
        return rateUpdateAPI;
    }

    public String getAddCustomerAPI() {
        return addCustomerAPI;
    }

    public String getAllCustomerAPI() {
        return allCustomerAPI;
    }
}
