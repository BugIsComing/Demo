package com.lc;

import java.security.NoSuchAlgorithmException;

public class Test {
    public static void main(String[] args) {
        String src = "{\"channelId\":\"10290001100\",\"appName\":\"12530\",\"salesPrice\":2000,\"needDeliver\":true,\"userId\":\"126784362\",\"autoSubscription\":true,\"serviceInfo\":{\"name\":\"咪咕视频钻石会员包月20元\",\"code\":\"MiGuShiPinZuanShiHuiYuanBaoYue20Yuan_40NX3XF2JX9\",\"type\":\"NORMAL\",\"price\":-1,\"supportProlongAuth\":false,\"repeatSubscriptionSupport\":true,\"autoSubscriptionSupport\":true,\"supportRefund\":false,\"desc\":\"\"},\"MainDeliveryItem\":{\"name\":\"咪咕视频会员权益[移动话费包月]\",\"handler\":\"MIGU_AUTH\",\"desc\":\"\",\"data\":{\"traceId\":\"3396070992119108\",\"resource_id\":\"2028597138\",\"resource_type\":\"PMS_PRODUCT_ID\",\"cpCode\":\"698040\",\"opCode\":\"69901930838075\"},\"authorization\":{\"authType\":\"BOSS_MONTH\",\"periodUnit\":\"MONTH\",\"amount\":1,\"effectOn\":-1,\"expireOn\":-1,\"validTimeSecond\":-1},\"beforehandSupport\":false,\"revokeDeliverySupport\":true,\"revokeScheduleSupport\":true},\"extDeliveryItems\":[{\"name\":\"合约[话费包月正向]\",\"handler\":\"CONTRACT\",\"desc\":\"送观影券\",\"data\":{\"traceId\":\"3396070992119108\",\"msisdn\":\"\",\"cpId\":\"698040\",\"operCode\":\"69901930838075\",\"productId\":\"2028597138\",\"orderId\":\"#orderId#\",\"contractType\":\"MOBILE_BOSS\",\"giftCodes\":\"DIAMOND\",\"revokeProductIds\":\"2028593549,2028596511,2028593529,2028600438\"},\"beforehandSupport\":false,\"revokeDeliverySupport\":true,\"revokeScheduleSupport\":true},{\"name\":\"钻石会员送观影券[8张]\",\"handler\":\"MIGU_ACCOUNT\",\"desc\":\"\",\"data\":{\"traceId\":\"3396070992119108\",\"accountType\":\"CMVIDEO_TICKET\",\"amount\":\"8\",\"type\":\"1\",\"revokeSupport\":\"false\"},\"authorization\":{\"authType\":\"PERIOD\",\"periodUnit\":\"MONTH\",\"amount\":1,\"effectOn\":-1,\"expireOn\":-1,\"validTimeSecond\":-1},\"beforehandSupport\":false,\"revokeDeliverySupport\":true,\"revokeScheduleSupport\":true},{\"name\":\"观影券限制\",\"handler\":\"MIGU_AUTH\",\"desc\":\"\",\"data\":{\"traceId\":\"3396070992119108\",\"resource_id\":\"DIAMOND\",\"resource_type\":\"ALIAS_ID\",\"revokeSupport\":\"false\"},\"authorization\":{\"authType\":\"TIMES\",\"amount\":1,\"effectOn\":-1,\"expireOn\":1533052800000,\"validTimeSecond\":-1},\"beforehandSupport\":false,\"revokeDeliverySupport\":true,\"revokeScheduleSupport\":true}],\"bids\":[{\"paymentCode\":\"SUNNY_V6_MOBILE_BOSS\",\"amount\":2000,\"extInfo\":{\"appName\":\"12530\",\"failReturnUrl\":\"\",\"phoneNum\":\"13666198573\",\"channelId\":\"10290001100\",\"operCode\":\"69901930838075\",\"isWebSDK\":\"false\",\"type\":\"wap\",\"cpCode\":\"698040\",\"operType\":\"BOSS_MONTH\",\"productId\":\"2028597138\"}}],\"goodsInfo\":{\"id\":\"2028597138\",\"type\":\"MIGU_MEMBER_EXTEND\",\"properties\":{\"phoneNumber\":\"13666198573\",\"productName\":\"咪咕视频钻石会员包月20元\"}}}";
        try {
            String dst1 = Md5Helper.encryptToMD5(src);
            String dst2 = Md5Helper.encryptToMD5_2(src);

            if(dst1.equalsIgnoreCase(dst2)){
                System.out.println("---2--"+dst2);
            }else{
                System.out.println("---1--"+dst1);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

}
