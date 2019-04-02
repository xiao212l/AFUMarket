package pv.com.pvcloudgo.model.bean;

import java.util.List;

public class goodsSimpleBean {


    /**
     * status : 10001
     * message : 请求成功
     * data : [{"id":8,"name":"海尔(Haier) 滚筒","subtitle":"洗衣机全自动 10公斤变频纤维级蒸汽防皱洗烘一体 洗10烘7 XQG100-14HBX20SJD","goodRatio":0.98,"categoryId":1,"detail":"海尔(Haier) 滚筒洗衣机全自动 10公斤变频纤维级蒸汽防皱洗烘一体 洗10烘7 XQG100-14HBX20SJD","mainImage":"rqwerwrf"},{"id":9,"name":"创维（SKYWORTH）55H6","subtitle":"55英寸超薄护眼全面屏 2+32G AI人工智能 4K超高清HDR网络WIFI家用液晶平板电视","goodRatio":0.97,"categoryId":1,"detail":"创维（SKYWORTH）55H655英寸超薄护眼全面屏 2+32G AI人工智能 4K超高清HDR网络WIFI家用液晶平板电视","mainImage":"rqwerwrf"}]
     */

    private int status;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 8
         * name : 海尔(Haier) 滚筒
         * subtitle : 洗衣机全自动 10公斤变频纤维级蒸汽防皱洗烘一体 洗10烘7 XQG100-14HBX20SJD
         * goodRatio : 0.98
         * categoryId : 1
         * detail : 海尔(Haier) 滚筒洗衣机全自动 10公斤变频纤维级蒸汽防皱洗烘一体 洗10烘7 XQG100-14HBX20SJD
         * mainImage : rqwerwrf
         */

        private int id;
        private String name;
        private String subtitle;
        private double goodRatio;
        private int categoryId;
        private String detail;
        private String mainImage;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public double getGoodRatio() {
            return goodRatio;
        }

        public void setGoodRatio(double goodRatio) {
            this.goodRatio = goodRatio;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getMainImage() {
            return mainImage;
        }

        public void setMainImage(String mainImage) {
            this.mainImage = mainImage;
        }
    }
}
