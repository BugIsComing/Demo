package com.lc;


import com.google.gson.*;

public class Test {
    public static void main(String[] args) {
        /*QueryMiguVideoRegisterReq paras = new QueryMiguVideoRegisterReq();
        long startTime = System.currentTimeMillis();
        paras.setTimestamp(startTime);
        paras.setAccountType("MOBILE");
        paras.setAccountName("18880406378");
        //QueryMiguVideoRegisterResp queryMiguVideoRegisterResp = new QueryMiguVideoRegisterResp();
        //String url = "http://117.131.17.78:28080/private/user/registerImplicit?clientId=e7141413-5797-4367-8fc2-5df2d0f9bb3ff&sign=255336&signType=RSA|AES";
        String url = "http://117.131.17.78:28080/private/user/registerImplicit";
        String strJon = null;
        strJon = paras.toString();

        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json; charset=utf-8");

        try {
            String notifyResult = HttpHelper.Post(url, strJon, header);
            System.out.println(notifyResult);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        String json = "{\"resultCode\":\"SUCCESS\",\"resultDesc\":\"SUCCESS\",\"userProfile\":{\"userId\":\"901228507\",\"registTime\":\"20180622161442\",\"accounts\":[{\"accountName\":\"18880406378\",\"accoutType\":\"MOBILE\",\"bindTime\":null,\"verified\":false,\"accountExtInfo\":null}],\"avatar\":null,\"nickName\":null,\"userRole\":null,\"usernum\":null,\"extInfo\":null}}";

        JsonParser parser = new JsonParser();

        // 2.获得 根节点元素
        JsonElement element = parser.parse(json);

        // 3.根据 文档判断根节点属于 什么类型的 Gson节点对象
        JsonObject root = element.getAsJsonObject();

        String resultCode = root.get("resultCode").getAsString();

        System.out.println(resultCode);

        // 4. 取得 节点 下 的某个节点的 value
        JsonObject userProfile = root.getAsJsonObject("userProfile");

        System.out.println(userProfile.get("userId").getAsString());


//        String url = "http://117.131.17.78:28080/" + "private/user/registerImplicit?clientid=";
//        url += "5dd7695c-8414-4cb1-9af1-a367197e699f";
//        url +="&sign=255336&signType=RSA|AES";
//        System.out.println(url);
    }

}
