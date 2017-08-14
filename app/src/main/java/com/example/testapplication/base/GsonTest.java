package com.example.testapplication.base;

import com.example.testapplication.utils.Md5Utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
  
public class GsonTest {


    /**打签名*/
    public static String getSignKey(HashMap<String,String> params){
        String keys[] = new String[params.size()];
        final String SIGN_KEY = "meetingAPI@2016";
        int i=0;
        for (String key : params.keySet()){
            keys[i++] = key;
        }
        Arrays.sort(keys);
        String signkey = "" ;
        for (int j=0;j<keys.length;j++){
            signkey = signkey + keys[j]+"="+params.get(keys[j]);
            if(j<keys.length-1){
                signkey = signkey + "&";
            }
        }
        return Md5Utils.str2md5(signkey + SIGN_KEY);
    }
    public static void main(String[] args) {
        HashMap params = new HashMap<String, String>();
//        params.put("code", "code");
//        params.put("cade", "userId");
//        params.put("cbde", "mid");

        System.out.print(Md5Utils.md5_32("meetingAPI@2016"));
    }
//    public static void main(String[] args) {
//        Gson gson = new Gson();
//
//        Student student1 = new Student();
//        student1.setId(1);
//        student1.setName("李坤");
//        student1.setBirthDay(new Date());
//
//        // //////////////////////////////////////////////////////////
//        System.out.println("----------简单对象之间的转化-------------");
//        // 简单的bean转为json
//        String s1 = gson.toJson(student1);
//        System.out.println("简单Bean转化为Json===" + s1);
//
//        // json转为简单Bean
//        Student student = gson.fromJson(s1, Student.class);
//        System.out.println("Json转为简单Bean===" + student);
//        // 结果:
//        // 简单Bean转化为Json==={"id":1,"name":"李坤","birthDay":"Jun 22, 2012 8:27:52 AM"}
//        // Json转为简单Bean===Student [birthDay=Fri Jun 22 08:27:52 CST 2012, id=1,
//        // name=李坤]
//        // //////////////////////////////////////////////////////////
//
//        Student student2 = new Student();
//        student2.setId(2);
//        student2.setName("曹贵生");
//        student2.setBirthDay(new Date());
//
//        Student student3 = new Student();
//        student3.setId(3);
//        student3.setName("柳波");
//        student3.setBirthDay(new Date());
//
//        List<Student> list = new ArrayList<Student>();
//        list.add(student1);
//        list.add(student2);
//        list.add(student3);
//
//        System.out.println("----------带泛型的List之间的转化-------------");
//        // 带泛型的list转化为json
//        String s2 = gson.toJson(list);
//        System.out.println("带泛型的list转化为json==" + s2);
//
//        // json转为带泛型的list
//        List<Student> retList = gson.fromJson(s2,
//                new TypeToken<List<Student>>() {}.getType());
//        for (Student stu : retList) {
//            System.out.println(stu);
//        }
//        String result =1+1+'1' + 1+1+1+1+"1";
//        System.out.println("来吧。。好好干:"+result);
//        // 结果:
//        // 带泛型的list转化为json==[{"id":1,"name":"李坤","birthDay":"Jun 22, 2012 8:28:52 AM"},{"id":2,"name":"曹贵生","birthDay":"Jun 22, 2012 8:28:52 AM"},{"id":3,"name":"柳波","birthDay":"Jun 22, 2012 8:28:52 AM"}]
//        // Student [birthDay=Fri Jun 22 08:28:52 CST 2012, id=1, name=李坤]
//        // Student [birthDay=Fri Jun 22 08:28:52 CST 2012, id=2, name=曹贵生]
//        // Student [birthDay=Fri Jun 22 08:28:52 CST 2012, id=3, name=柳波]
//
//    }

    /**小数点后保留四位有效数字*/
    public static float getNumber(String number){
        DecimalFormat df = new DecimalFormat("#.####");
//        DecimalFormat df = new DecimalFormat("#,###.0000");
        float f=Float.valueOf(df.format(Double.parseDouble(number)));
        return f;
    }
    public static String getNumber(double number){
        DecimalFormat df = new DecimalFormat("#,##0.0000");
//        float f=Float.valueOf(df.format(number));
//        DecimalFormat df = new DecimalFormat("#,###.####");
        return String.valueOf(df.format(BigDecimal.valueOf(number)));
    }
}  