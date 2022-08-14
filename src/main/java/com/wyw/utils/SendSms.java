package com.wyw.utils;

import com.zhenzi.sms.ZhenziSmsClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
public class SendSms {
    public static void send(String phoneNumber, String code) {
        // 使用自己的 AppId 和 AppSecret
        ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "112061", "8375938e-0b02-4ccd-a4f4-82ffac2199e7");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("number", phoneNumber);
        // 修改为自己的templateId
        params.put("templateId", "9958");
        String[] templateParams = new String[1];
        templateParams[0] = code;
        params.put("templateParams", templateParams);
        try {
            String result = client.send(params);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
