package com.taotao.httpclient;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientTest {

    @Test
    public void doGet() throws Exception{
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个GET对象
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        //执行get请求
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        //取响应的结果
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity httpEntity = httpResponse.getEntity();
        String string = EntityUtils.toString(httpEntity, "utf-8");
        System.out.println(string);
        //关闭httpclient
        httpResponse.close();
        httpClient.close();
    }

    @Test
    public void doGetWithParam() throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建 一个uri对象
        URIBuilder uriBuilder = new URIBuilder("http://www.sogou.com/web");
        uriBuilder.addParameter("query", "helloworld");
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        //执行get请求
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        //取响应的结果
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity httpEntity = httpResponse.getEntity();
        String string = EntityUtils.toString(httpEntity, "utf-8");
        System.out.println(string);
        //关闭httpclient
        httpResponse.close();
        httpClient.close();
    }

    @Test
    public void doPost() throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个post文件
        HttpPost httpPost = new HttpPost("http://localhost:8082/httpclient/post.html");
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        //取响应的结果
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity httpEntity = httpResponse.getEntity();
        String string = EntityUtils.toString(httpEntity, "utf-8");
        System.out.println(string);
        //关闭httpclient
        httpResponse.close();
        httpClient.close();
    }

}
