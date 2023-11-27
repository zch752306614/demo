package com.alice.support.module.demo.until;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alice
 * @version 1.0
 * @date 2020/8/4 15:13
 */
public class Test {

    public static void getGoodsList(String str) throws IOException {
        String cookie = "cna=n2fTGVGu8RcCAXgkJ8d0ArSr; lid=waied%E4%BD%A0; xlly_s=1; taklid=29890b6c6d724b1c888cb92cf4922e76; ali_apache_track=c_mid=b2b-2443227178|c_lid=waied%E4%BD%A0|c_ms=1; last_mid=b2b-2443227178; cbu_mmid=7E3C55520E991B9B3E4FB2E1D617E9C167463B8D09351BB65D12A91069A37C2C09F848C1F03189F2DDDCA31D76FEC845744ADE63D959C8F1A84D4E56789B5A4E; cookie2=11f1a4ffd702a9cb566325ed06124d6d; t=c656aab5e56921b6a97ded4bc749125f; _tb_token_=53b0eb373b11d; cookie1=WvECQ%2BrGS8iTacvR8Gb7TIiCTtJtVNqLdSXl4OYU1SI%3D; cookie17=UUwU2j%2FkV9IeiA%3D%3D; sgcookie=E100YVPEu4yWapzrLy5rsG4w07A6GVp4vyoqxjszytty2goAlxrKE8WWOPXlx%2FZnJ7Yze2QLbid7Y1wM%2F9mn6MvpNDc5j8Yd3XzJ9N2PEWvxeHinSS8j0eRcSNdDl9x2OKee; sg=%E4%BD%A082; csg=86becd1c; unb=2443227178; uc4=id4=0%40U27L95f58C6cBE5ov2EgaqvWcHMd&nk4=0%40FnNd4RKbiw8VIy6XwglLgHA3; __cn_logon__=true; __cn_logon_id__=waied%E4%BD%A0; ali_apache_tracktmp=c_w_signed=Y; _nk_=waied%5Cu4F60; _csrf_token=1683128920525; __mwb_logon_id__=waied%25E4%25BD%25A0; mwb=ng; ali_ab=112.49.106.176.1683129460329.5; keywordsHistory=%E9%94%AE%E7%9B%98%E4%BF%9D%E6%8A%A4%E8%86%9C%3B%E9%94%AE%E7%9B%98%3B%E5%A4%8F%E5%AD%A3%E7%94%B7%E8%A3%85%3B%E9%BC%A0%E6%A0%87%3B1%3B%E7%AB%A5%E8%A3%85%3B%E9%A3%8E%E8%A1%A3%3B%E9%9B%B6%E9%A3%9F; ta_info=810F8857EAA0364D6A55EA8951423B3990E054CD6431AA194F1B83B9E7936069199406F1EBC0FC88229D45EBB177D63F14A5CAB8BECF89771490FA03749186CB79E423E48CC563CA337A6DAE81E821307BEBDA366A123F55910B43381C4F11E61CD05BD258032D153AEDEA75593C1708B35AE744130682C9A298DB3B1D55A5B0ED6CF4F7BFD36E9F5E74B80E49AC3E0FAB78D5A0CC8529DE96D3B0878AEC697551189AAD8612BA1E; _m_h5_tk=9bcc843504a9ef326dcddc657d00c5b6_1683145136723; _m_h5_tk_enc=09e29f63cc7841445cac846b51c843bc; isg=BOPjxqcgg5gLYkgxj4VRzlvDcieN2HcaLd7L_hVVMMK5VAB2mKzOaoXFTiTaT88S; l=fBrspi5qTDCYuBbxBO5Clurza77OmhOf1lVzaNbMiIEGa6IldFgpzNC_3FUDfdtjgTCAQFKyc-fDid3yyizdNxDDBe4yMs4L2xv9GrQog; tfstk=cHCVBJ6WjSFV8hbTvQAN4ypk447fC-sGDb-ynlebEOQYVq0MYZ5c_cLkHHYy04oMn";
        String baseUrl = "https://search.1688.com/service/marketOfferResultViewService";
        String keywords = URLEncoder.encode(str, "GBK");
        String spm = "a26352.13672862.searchbox.input";
        String async = "true";
        String beginPage = "1";
        String pageSize = "60";
        String startIndex = "0";
        String asyncCount = "20";
        String pageName = "major";
        String requestId = "";
        String sessionId = "";
        // 排序类型 va_rmdarkgmv30（成交额）;normal（综合）
        String sortType = "va_rmdarkgmv30";
        // 是否倒序
        String descendOrder = "true";
        String uniqfield = "userid";
        String n = "y";
        String filt = "y";
        String priceStart = "100";
        String priceEnd = "";
        // 新人首单优惠
        String filtMemberTags = "1445761";
        // 4360897（一件包邮）;4413377（优选物流）;price（价格）
        String tags = "4360897";
        // 7X24H相应
        String memberTags = "161664";
        // 48小时发货
        String extendProperties = "buyerProtection:essxsfh,buyerProtection:ssbxsfh";
        // 起订量
        String quantityBegin = "";
        // 筛选条件
        String featurePair = "";

        // 定义要请求的URL地址
        String url = String.format("%s?keywords=%s&spm=%s&async=%s&asyncCount=%s&beginPage=%s&pageSize=%s&requestId=%s&startIndex=%s&pageName=%s&sessionId", baseUrl, keywords, spm, async, asyncCount, beginPage, pageSize, requestId, startIndex, pageName, sessionId);
        // 设置请求方法为GET
        URL obj = new URL(url);
        // 创建HTTP连接对象
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // 设置请求方法为GET
        con.setRequestMethod("GET");
        // 设置请求头参数
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("authority", "search.1688.com");
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
        con.setRequestProperty("cookie", cookie);
        con.setRequestProperty("origin", "https://s.1688.com");
        con.setRequestProperty("referer", "https://s.1688.com/");
        con.setRequestProperty("sec-ch-ua", "\"Chromium\";v=\"112\", \"Microsoft Edge\";v=\"112\", \"Not:A-Brand\";v=\"99\"");
        con.setRequestProperty("sec-ch-ua-mobile", "?0");
        con.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
        con.setRequestProperty("sec-fetch-dest", "empty");
        con.setRequestProperty("sec-fetch-mode", "cors");
        con.setRequestProperty("sec-fetch-site", "same-site");
        con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36 Edg/112.0.1722.68");

        int responseCode = con.getResponseCode();
        System.out.println("Sending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //System.out.println(response);
        List<String> urlList = new ArrayList<>();
        try {
            JSONObject json = JSONObject.parseObject(response.toString());
            //System.out.println(json.toString());
            JSONObject data = json.getJSONObject("data");
            String msg = data.getString("msg");
            String code = data.getString("code");
            if ("200".equals(code)) {
                JSONObject dataSon = data.getJSONObject("data");
                if (ObjectUtil.isNotEmpty(dataSon)) {
                    JSONArray offerList = dataSon.getJSONArray("offerList");
                    if (ObjectUtil.isNotEmpty(offerList)) {
                        for (int i = 0; i < offerList.size(); i++) {
                            JSONObject offer = offerList.getJSONObject(i);
                            JSONObject information = offer.getJSONObject("information");
                            if (ObjectUtil.isNotEmpty(information)) {
                                String detailUrl = information.getString("detailUrl");
                                urlList.add(detailUrl);
                            }
                        }
                    }
                }
            } else {
                System.out.println(msg);
            }
        } catch (JSONException e) {
            try {
                JSONArray jsonArray = JSONArray.parseArray(response.toString());
                System.out.println(jsonArray.toString());
            } catch (JSONException ex) {
                System.out.println("Error parsing response as JSON: " + ex.getMessage());
            }
        }
        for (int i = 0; i < urlList.size(); i++) {
            String s = urlList.get(i);
            System.out.println("url_" + i + ":" + s);
        }
    }

    public static void main(String[] args) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//        String startDate = df.format(new Date());
//        System.out.println(startDate);
        try {
            getGoodsList("键盘保护膜");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
