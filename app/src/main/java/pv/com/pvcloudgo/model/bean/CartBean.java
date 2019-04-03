package pv.com.pvcloudgo.model.bean;

import java.util.List;

public class CartBean {


    /**
     * status : 10001
     * message : 请求成功
     * data : {"nickname":"hanson24","cartItems":[{"productId":1,"skuId":4,"name":"长虹 55D2P 太空灰","subtitle":"55英寸32核人工智能4K超高清HDR全金属轻薄语音平板LED液晶电视机（浅金色）","isChecked":1,"price":5111,"count":1500,"totalPrice":7666500}]}
     */

    private int status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * nickname : hanson24
         * cartItems : [{"productId":1,"skuId":4,"name":"长虹 55D2P 太空灰","subtitle":"55英寸32核人工智能4K超高清HDR全金属轻薄语音平板LED液晶电视机（浅金色）","isChecked":1,"price":5111,"count":1500,"totalPrice":7666500}]
         */

        private String nickname;
        private List<CartItemsBean> cartItems;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public List<CartItemsBean> getCartItems() {
            return cartItems;
        }

        public void setCartItems(List<CartItemsBean> cartItems) {
            this.cartItems = cartItems;
        }

        public static class CartItemsBean {
            /**
             * productId : 1
             * skuId : 4
             * name : 长虹 55D2P 太空灰
             * subtitle : 55英寸32核人工智能4K超高清HDR全金属轻薄语音平板LED液晶电视机（浅金色）
             * isChecked : 1
             * price : 5111
             * count : 1500
             * totalPrice : 7666500
             */

            private int productId;
            private int skuId;
            private String name;
            private String subtitle;
            private int isChecked;
            private int price;
            private int count;
            private int totalPrice;

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public int getSkuId() {
                return skuId;
            }

            public void setSkuId(int skuId) {
                this.skuId = skuId;
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

            public int getIsChecked() {
                return isChecked;
            }

            public void setIsChecked(int isChecked) {
                this.isChecked = isChecked;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(int totalPrice) {
                this.totalPrice = totalPrice;
            }
        }
    }
}
