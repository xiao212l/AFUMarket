package pv.com.pvcloudgo.model.bean;

public class LoginBean {

    /**
     * status : 10001
     * message : 用户注册成功
     * data : {"username":"BeautifulSoup","gender":"male","profileImg":"http://pmt5ma5mu.bkt.clouddn.com/34a19b7f-7b6a-4710-9431-01d3279277cd.jpg","motto":"生命不息,奋斗不止","email":"beautifulsoup@163.com"}
     */

    private int status;
    private String message;
    private pv.com.pvcloudgo.model.bean.User data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

}
