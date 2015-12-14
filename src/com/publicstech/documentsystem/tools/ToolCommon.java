package com.publicstech.documentsystem.tools;

import java.io.FileOutputStream;
import java.io.IOException;

public class ToolCommon {

    /**  
         * JSON字符串特殊字符处理
         * @param s  
         * @return String  
         */  
        public static String string2Json(String s) {         
            StringBuffer sb = new StringBuffer();         
            for (int i=0; i<s.length(); i++) {   
                char c = s.charAt(i);     
                 switch (c){   
                 case '\"':         
                     sb.append("\\\"");         
                     break;         
                 case '\\':         
                     sb.append("\\\\");         
                     break;         
                 case '/':         
                     sb.append("\\/");         
                     break;         
                 case '\b':         
                     sb.append("\\b");         
                     break;         
                 case '\f':         
                     sb.append("\\f");         
                     break;         
                 case '\n':         
                     sb.append("\\n");         
                     break;         
                 case '\r':         
                     sb.append("\\r");         
                     break;         
                 case '\t':         
                     sb.append("\\t");         
                     break;         
                 default:         
                     sb.append(c);      
                 }   
             }       
            return sb.toString();      
            }  
        
        
      //写数据到SD中的文件
        public static void writeFileSdcardFile(String fileName,String write_str) throws IOException{ 
         try{ 

               FileOutputStream fout = new FileOutputStream(fileName); 
               byte bytes[]=new byte[1024];
               bytes=write_str.getBytes();   //新加的
               int b=write_str.length();   //改
               fout.write(bytes,0,b);
               fout.close(); 
               System.out.println("ss");
             }

              catch(Exception e){ 
                e.printStackTrace(); 
               } 
           } 
}
