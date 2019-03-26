package com.taotao.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * 项目的返回结果自定义响应结构
 */
public class TaotaoResult {

    //定义Jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    //响应业务状态
    private Integer status;
    //响应消息
    private String msg;

    public static ObjectMapper getMAPPER() {
        return MAPPER;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    //响应中的数据
    private Object data;

    public TaotaoResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public TaotaoResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public TaotaoResult() {
    }

    public static TaotaoResult build(Integer status, String msg, Object data){
        return new TaotaoResult(status, msg, data);
    }

    public static TaotaoResult ok(Object data){
        return new TaotaoResult(data);
    }

    public static TaotaoResult ok(){
        return new TaotaoResult(null);
    }

    public static TaotaoResult build(Integer status, String msg){
        return new TaotaoResult(status, msg, null);
    }

    /**
     * 将json结果集转换成TaotaoResult对象
     * @param jsonData
     * @param clazz
     * @return
     */
    public static TaotaoResult formatToPojo(String jsonData, Class<?> clazz){
        try {
            if (clazz == null){
                return MAPPER.readValue(jsonData, TaotaoResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()){
                    obj = MAPPER.readValue(data.traverse(), clazz);
                }else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            System.out.println("-------------\n报错：" + e.getMessage());
            return null;
        }
    }

    /**
     * 没有object对象的转化
     * @param json
     * @return
     */
    public static TaotaoResult format(String json){
        try{
            return MAPPER.readValue(json,TaotaoResult.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     * @param jsonData
     * @param clazz
     * @return
     */
    public static TaotaoResult formatToList(String jsonData, Class<?> clazz){
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0){
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e){
            System.out.println("-----------\n转化错误： " + e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "TaotaoResult{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
