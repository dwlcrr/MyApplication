package com.example.testapplication.base;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

public class Test {


    public static void main(String[] args) throws ParseException {
//        String spfs_history = "aaa,";
//        List<String> historyList = new ArrayList<>();
//        String tempHistory[] = spfs_history.split(",");
//        historyList.addAll(Arrays.asList(tempHistory));
//
//        if(historyList.size()>0){
//            System.out.print(historyList.get(0)+"NIma::");
//        }
        String product_attr_json = "";
        JSONArray jsonarray = null;
        for (int i=0;i<5;i++){
            String value = "22";
            int id = 1;
            try {
                jsonarray = new JSONArray();//json数组，里面包含的内容为id,value的所有对象
                JSONObject jsonObj = new JSONObject();//idValue对象，json形式
                jsonObj.put("id",id);//向idValue对象里面添加值
                jsonObj.put("value", value);
                // 把每个数据当作一对象添加到数组里
                 jsonarray.put(jsonObj);//向json数组里面添加pet对象
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        product_attr_json = jsonarray.toString();
        System.out.print(product_attr_json);
    }

    /**
     * 小数点后保留四位有效数字
     */
    public static float getNumber(String number) {
        DecimalFormat df = new DecimalFormat("#.####");
//        DecimalFormat df = new DecimalFormat("#,###.0000");
        float f = Float.valueOf(df.format(Double.parseDouble(number)));
        return f;
    }

    public static String getNumber(double number) {
        DecimalFormat df = new DecimalFormat("#,##0.0000");
//        float f=Float.valueOf(df.format(number));
//        DecimalFormat df = new DecimalFormat("#,###.####");
        return String.valueOf(df.format(BigDecimal.valueOf(number)));
    }
}  