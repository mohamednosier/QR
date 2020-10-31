package eu.kudan.qrcode.Adapter;

public class MyListData {
    private String product;
    private String date;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code;
    public MyListData(String product, String date ,String code) {
        this.product = product;
        this.date = date;
        this.code = code;
    }

}
