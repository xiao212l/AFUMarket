package pv.com.pvcloudgo.model.bean;

import java.util.List;

public class goodsDetailMessageBean {

    /**
     * status : 10001
     * message : 请求成功
     * data : {"id":1,"categoryId":1,"name":"长虹 55D2P","subtitle":"55英寸32核人工智能4K超高清HDR全金属轻薄语音平板LED液晶电视机（浅金色）","detail":"长虹 55D2P55英寸32核人工智能4K超高清HDR全金属轻薄语音平板LED液晶电视机（浅金色）","status":1,"evaluationNums":600,"goodEvaluationNums":550,"goodRatio":0.92,"purchaseProductSkus":[{"id":1,"attributeName":"黑色","sales":369,"stock":1222,"price":4788,"spellPrice":4533,"productId":1},{"id":2,"attributeName":"银色","sales":450,"stock":1300,"price":4900,"spellPrice":4600,"productId":1},{"id":3,"attributeName":"金色","sales":400,"stock":1000,"price":4700,"spellPrice":4400,"productId":1},{"id":4,"attributeName":"太空灰","sales":440,"stock":1155,"price":5111,"spellPrice":4999,"productId":1}]}
     */

    private int status;
    private String message;
    private goodsDetailBean data;

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

    public goodsDetailBean getData() {
        return data;
    }

    public void setData(goodsDetailBean data) {
        this.data = data;
    }

    public static class goodsDetailBean {
        /**
         * id : 1
         * categoryId : 1
         * name : 长虹 55D2P
         * subtitle : 55英寸32核人工智能4K超高清HDR全金属轻薄语音平板LED液晶电视机（浅金色）
         * detail : 长虹 55D2P55英寸32核人工智能4K超高清HDR全金属轻薄语音平板LED液晶电视机（浅金色）
         * status : 1
         * evaluationNums : 600
         * goodEvaluationNums : 550
         * goodRatio : 0.92
         * purchaseProductSkus : [{"id":1,"attributeName":"黑色","sales":369,"stock":1222,"price":4788,"spellPrice":4533,"productId":1},{"id":2,"attributeName":"银色","sales":450,"stock":1300,"price":4900,"spellPrice":4600,"productId":1},{"id":3,"attributeName":"金色","sales":400,"stock":1000,"price":4700,"spellPrice":4400,"productId":1},{"id":4,"attributeName":"太空灰","sales":440,"stock":1155,"price":5111,"spellPrice":4999,"productId":1}]
         */

        private int id;
        private int categoryId;
        private String name;
        private String subtitle;
        private String detail;
        private int status;
        private int evaluationNums;
        private int goodEvaluationNums;
        private double goodRatio;
        private List<PurchaseProductSkusBean> purchaseProductSkus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
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

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getEvaluationNums() {
            return evaluationNums;
        }

        public void setEvaluationNums(int evaluationNums) {
            this.evaluationNums = evaluationNums;
        }

        public int getGoodEvaluationNums() {
            return goodEvaluationNums;
        }

        public void setGoodEvaluationNums(int goodEvaluationNums) {
            this.goodEvaluationNums = goodEvaluationNums;
        }

        public double getGoodRatio() {
            return goodRatio;
        }

        public void setGoodRatio(double goodRatio) {
            this.goodRatio = goodRatio;
        }

        public List<PurchaseProductSkusBean> getPurchaseProductSkus() {
            return purchaseProductSkus;
        }

        public void setPurchaseProductSkus(List<PurchaseProductSkusBean> purchaseProductSkus) {
            this.purchaseProductSkus = purchaseProductSkus;
        }

        public static class PurchaseProductSkusBean {
            /**
             * id : 1
             * attributeName : 黑色
             * sales : 369
             * stock : 1222
             * price : 4788
             * spellPrice : 4533
             * productId : 1
             */

            private int id;
            private String attributeName;
            private int sales;
            private int stock;
            private int price;
            private int spellPrice;
            private int productId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAttributeName() {
                return attributeName;
            }

            public void setAttributeName(String attributeName) {
                this.attributeName = attributeName;
            }

            public int getSales() {
                return sales;
            }

            public void setSales(int sales) {
                this.sales = sales;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getSpellPrice() {
                return spellPrice;
            }

            public void setSpellPrice(int spellPrice) {
                this.spellPrice = spellPrice;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }
        }
    }
}
