package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPQueryAddress {
	public static void main(String[] args) throws UnknownHostException{

		
//		IPQueryAddress.ipToIp32Str("121.0.0.7"); //01111001000000000000000000000111
//		IPQueryAddress.ipNumToIp32Str("2030043143"); // //01111001000000000000000000000111
//		IPQueryAddress.ip32StrToIpStr("01111001000000000000000000000111"); // //01111001000000000000000000000111		
//		String ip32Str = "";
//		int i = 0;
//		while(true){
//			int ipNum = 2030043143;
//			if((ipNum>>(i)) == 0){
//				break;
//			}else{
//				ip32Str = (ipNum>>(i++) & 1) + ip32Str;
//			}
//		}  
//		System.out.println(ip32Str);	
//		IPQueryAddress.writeToFile();
//		IPQueryAddress.readToFile();
//		byte a = 120;
//		System.out.println(extend_uv(a));
//		System.out.println(int2Byte(236));
//		System.out.println(130<<24);

		byte[] bytes = {-1,-1,-1,-1};
		System.out.println(byteArr2Num_uv(bytes));
		numStr2ByteArr_uv(byteArr2Num_uv(bytes)+"",4);
		System.out.println(ip2NumStr("255.255.255.255"));

//		System.out.println(bytes2Int(bytes));
//		System.out.println();
//		byte[] b = numStr2ByteArr("2030043143",4);

	}
	
	//随机生成1000调ip数据，并利用二进制转化成数值字符串进行文件存储ip.txt
	public static void randomIpData(){
//		for(){
//			
//		}
	}
	
	//读取ip文件中的ip数据
	public static void writeToFile(){
		File file = new File("d:/ip-data.dat");
		try {
			FileOutputStream out = new FileOutputStream(file,true);
			byte[] bytes = new byte[4];
			bytes = numStr2ByteArr("2030043143",4);			
			out.write(bytes,0,4);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void readToFile(){
		File file = new File("d:/ip-data.dat");
		try {
			FileInputStream in = new FileInputStream(file);
			byte[] bytes = new byte[4];
			in.read(bytes);
			BigInteger num = byteArr2Num(bytes);
			in.close();
			System.out.println(num.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	
	
	/**************************主要核心方法***************************/
    //将二进制的(8/16/24/32/...)位长整形字符串转为十进制数值串
    public static BigInteger binaryStr2Num(String binaryStr){
    	BigInteger num = new BigInteger(binaryStr,2);
    	System.out.println("num:" + num);
    	return num;
    }
    
    //将十进制的数值串转化为二进制的(8/16/24/32/...)位的长整型字符串
    public static String numStr2BinaryStr(String numStr,int i){
    	StringBuffer binaryStr = new StringBuffer("");
    	if(isNumeric(numStr)){
    		long num = Long.parseLong(numStr);
    		int bit = 8*i;
    		for(int k=0; k<bit; k++){
    			binaryStr.append(num>>(bit-1-k) & 1);
    		}    		
    	}
    	System.out.println("binaryStr:" + binaryStr);
    	return binaryStr.toString();
    }
       
    
    //判断字符串是否无符号整数
    public final static boolean isNumeric(String s) {
		if (s != null && !"".equals(s.trim()))
			return s.matches("^[0-9]*$");
		else
			return false;
	}
    
    /////////////////int和 byte相互转换，变相的拓展byte的取值范围//////////////////
    //拓展byte的范围为0~255进行java逻辑的处理
     public static int extend(byte b){
     	return (int)(b+128);
     }
     
     //恢复byte的范围为-128~127进行文件存储
     public static byte unextend(int i){
     	if(i>255)i=255;
     	if(i<0)i=0;
     	return (byte)(i-128);
     }
     
     //////////////////第二种映射///////////////
     //拓展byte的范围为0~255进行java逻辑的处理--
     private static int extend_uv(byte b) {
 	        return b & 0xFF;
 	}
   //恢复byte的范围为-128~127进行文件存储
 	public static byte unextend_uv(int num) {
 	        return (byte) num;
 	}    
    /////////////////////////////////////////////////////////////////////
   
    
    
    /*****************************ip相关工具方法*********************************/
    //将二进制的32位长整形转化为ip串
    public static String BinaryStr2Ip(String binaryStr){
    	StringBuffer ip = new StringBuffer();
    	for(int m=0; m<binaryStr.toString().length(); m+=8){//每8位向前移动一次            
           String str = binaryStr.substring(m, m+8);//获取字符串的8位字符  
           BigInteger bi = binaryStr2Num(str);//将其转化为十进制  
           if(m!=24){//除了最后一位，数字后都要加点               
        	   ip.append(bi.toString());  
        	   ip.append(".");  
           }else{               
        	   ip.append(bi.toString());  
           }  
        } 
    	return ip.toString();  
    }
    
    
    //将ip串转化为32位长整形    
    public static String ip2BinaryStr(String ip){
    	String[] ipArr = ip.split("\\.");
    	//用来承载转换成二进制的ip段
    	StringBuffer ip32Str = new StringBuffer();
    	for(int i=0;i<4 && i<ipArr.length;i++){
    		ip32Str.append(numStr2BinaryStr(ipArr[i],1));
    	}
    	System.out.println("ip32Str:" + ip32Str);
    	return ip32Str.toString();
    }
    
  //使用address类可以直接将ip转化为字节数组
    public static byte[] ip2Bytes(String ip){
        InetAddress address;
        byte[] bytes = null;
		try {
			address = InetAddress.getByName(ip);// 在给定主机名的情况下确定主机的 IP 址。
			bytes = address.getAddress();// 返回此 InetAddress 对象的原始 IP 地址
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return bytes;
    }
    
    
    //将ip串通过一定的算法得到一个唯一的数值串与ip一一对应
    public static String ip2NumStr(String ip){
    	String numStr = "";
    	String BinaryStr = ip2BinaryStr(ip);
    	numStr = binaryStr2Num(BinaryStr).toString();
    	return numStr;
    }
    
    
    /***************************数值字符串与字节数组的相互转化**********************************/
    
    //1.测试存有二进制文件里面的位移操作
    //2.将ip数据库里面数据做成二进制文件格式
    //3.测试二分法查询效率
    //4.和数据库查询的效率作比对，分析性能
   
    //将所要处理的数值串拆分成byte类型
    //s==所要处理的数值串，i==s所占的字节数
    //return byte[]
    public static byte[] numStr2ByteArr(String s,int i){
    	//首先将数值字符串处理成二进制的字符串  8位/16位/24位/32位/...
    	String binaryStr = numStr2BinaryStr(s,i);
    	byte[] bytes = new byte[i];
    	//再将二进制字符创按8位进行分割
    	for(int m=0; m<i; m++){          
            String str = binaryStr.substring(m*8, m*8+8);//获取字符串的8位字符  
            BigInteger bi = binaryStr2Num(str);//将其转化为十进制  
            //然后将8位的二进制字符串处理成单个字节存放进字节数组中
            //(期间需要将8位二进制转换成的int在转换成byte，需要做0~255到-128~127的映射)
            byte b = unextend(bi.intValue());
            bytes[m] = b;
    	}
    	return bytes;
    }
    
    //return String 数值字符串
    public static BigInteger byteArr2Num(byte[] b){
    	BigInteger num = null;
    	if(b.length>0){
    		int n = 0;
    		StringBuffer numStr = new StringBuffer("");
    		for(int i=0; i<b.length; i++){
    			//首先将每个字节转成int (-128~127到0~255的映射)
    			n = extend(b[i]);
    			//将int转成二进制进行拼接    			
    			numStr.append(numStr2BinaryStr(String.valueOf(n),1));
    		}
    		//再将拼接的二进制字符串转换成十进制
    		num = binaryStr2Num(numStr.toString());
    	}
    	return num;
    }
    
    
    
    
    /****************数值字符串与字节数组的相互转化优化版****************/
    //相比上面的方法，该算法更加简洁
    //byteArr2NumStr升级版,更加简洁(限制4个字节以内)
    public static BigInteger byteArr2Num_uv(byte[] b){
    	long l = 0;
    	int len = b.length < 4 ? b.length : 4;
 	    for (int i = 0; i < len; i++) {
 	        l |= (extend_uv(b[i])) << ((len-1-i) << 3);
 	    }
 	    long L = l & 0x7fffffffL;
 	    if (l < 0) {
 	    	L |= 0x080000000L;
 	    }
 	    return new BigInteger(String.valueOf(L));
    }
    //numStr2ByteArr升级版,更加简洁(限制4个字节以内)
    public static byte[] numStr2ByteArr_uv(String s,int i){
    	byte[] b = null;
    	if(isNumeric(s)){
    		long n = Long.parseLong(s);
    		b = new byte[i];
    		int len = i < 4 ? i : 4;
    		for (int k = 0; k < len; k++) {
    			b[k] = unextend_uv((int)(n >> ((len-1-k) << 3)));
    		}
		}
	    return b;
    }
}
