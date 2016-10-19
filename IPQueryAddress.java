package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class IPQueryAddress {
	public static void main(String[] args) throws UnknownHostException{

		
//		IPQueryAddress.ipToIp32Str("121.0.0.7"); //01111001000000000000000000000111
//		IPQueryAddress.ipNumToIp32Str("2030043143"); // //01111001000000000000000000000111
//		IPQueryAddress.ip32StrToIpStr("01111001000000000000000000000111"); // //01111001000000000000000000000111
		
		File file = new File("d:/ip-data.dat");
		String[] ipArr = {"121.26.172.69","218.99.33.0","218.99.36.1","218.99.99.1",
				"121.26.172.69","218.99.33.0","218.99.36.1","218.99.99.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1",
				"218.99.99.1","121.26.172.69","218.99.33.0","218.99.36.1"};
		
		long beginTime = System.currentTimeMillis();
		for(int j=0;j<20;j++){	
			for(int i=0;i<ipArr.length;i++){
				boolean flag = IPDivisionQuery(file,ipArr[i]);		
				System.out.println(flag);
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("所用时间为：" + (endTime-beginTime)/1000.00 + " 秒");
		
		//整理代码
	}
	
	//ip二分法查找
	public static boolean IPDivisionQuery(File file,String ip){
		//首先将需要查找的ip转换成数值型
		String numStr = ip2NumStr(ip);
		long num = 0;
		if(isNumeric(numStr)){
			num = Long.parseLong(numStr);			
		}		
		//二分法,初始化begin和end变量的值
		long begin = 0;
		long end = file.length();
		while(true){
			if(end-begin<=8){
				break;
			}
			long m = ((end-begin)/8/2)*8 + begin;
			System.out.println(m);
			BigInteger middle= readToFile(file,m,4);
			if(num == middle.longValue()){
				begin = m;
				break;
			}else if(num > middle.longValue()){
				begin = m;
			}else{
				end = m;
			}
		}
		//已经获取到了ip所在的区间段
		BigInteger targetBegin = readToFile(file,begin,4);
		BigInteger targetEnd = readToFile(file,begin+4,4);
		String targetBeginIp = BinaryStr2Ip(numStr2BinaryStr(targetBegin.toString(),4));
		String targetEndIp = BinaryStr2Ip(numStr2BinaryStr(targetEnd.toString(),4));
		System.out.println("该ip在    "+targetBeginIp+" ~ "+targetEndIp+" 之间");
		return true;
	}
	
	//将ip.txt里面的ip数据，利用二进制转化成字节进行文件存储
	public static void Ips2NumsData(){
		File file = new File("d:/ip.txt");
		File fileSort = new File("d:/ip-sort.txt");
		File ipData = new File("d:/ip-data.dat");
		String readStr;
		List<String> list = new ArrayList<String>();
		try {
			//ip数据原文件
			BufferedReader ipR = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			//ip数据排序后的文件
			BufferedWriter ipSortW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileSort)));
			while((readStr = ipR.readLine())!= null){				
				String[] insertArr = readStr.split("   ");
				//利用二分法实现数组插入数据自动排序
				int len = list.size();
				if(len>1){
					int begin = 0;
					int end = len;					
					while(true){
						if(end-begin<2){
							long beginN = Long.parseLong(ip2NumStr((list.get(0).split("   "))[0]));
							if(begin == 0 && Long.parseLong(ip2NumStr(insertArr[0]))<beginN){
								list.add(0, readStr);
							}else{
								list.add(begin+1,readStr);
							}
							break;
						}
						int m = (end-begin)/2 + begin;
						long readM = Long.parseLong(ip2NumStr((list.get(m).split("   "))[0]));
						if(Long.parseLong(ip2NumStr(insertArr[0])) <= readM){
							end = m;
						}else{
							begin = m;
						}							
					}
				}else{
					list.add(readStr);
				}
			}
			ipR.close();
			System.out.println(list.size());
			
			//将list里面排好序的ip数据存入另一个txt文件中
			for(String str:list){
				ipSortW.write(str+"\n");
				//必须flush，要不然写入的数据可能不完整
				ipSortW.flush();
			}
			ipSortW.close();
			
			//将排序好的ip数据转换成二进制ip文件，也可以直接使用上面的list遍历写入
			BufferedReader ipSortR = new BufferedReader(new InputStreamReader(new FileInputStream(fileSort)));
			while((readStr = ipSortR.readLine())!= null){
				String[] readArr = readStr.split("   ");
				for(String s:readArr){
					writeToFile(ipData,s,4);
				}
			}
			ipSortR.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//TODO 需要重新处理，目前的数据未排序，影响二分法的执行
	//读取ip文件中的ip数据   
	//len 写入的字节长度
	public static void writeToFile(File file,String ip,int len){
		try {
			FileOutputStream out = new FileOutputStream(file,true);
			byte[] bytes = new byte[len];
			bytes = ip2Bytes(ip);
			out.write(bytes,0,len);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//seek:文件读取的指针的偏移位置，以字节为单位计算
	//len:读取的字节长度
	public static BigInteger readToFile(File file,long seek,int len){
		BigInteger num = null;
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
			randomAccessFile.seek(seek);
			byte[] bytes = new byte[len];
			randomAccessFile.read(bytes);
			//使用升级版方法，本流程使用的byte扩展都是使用最新的升级版
			num = byteArr2Num_uv(bytes);
			randomAccessFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
    
	
	
	/**************************主要核心方法***************************/
    //将二进制的(8/16/24/32/...)位长整形字符串转为十进制数值串
    public static BigInteger binaryStr2Num(String binaryStr){
    	BigInteger num = new BigInteger(binaryStr,2);
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
    @Deprecated
     public static int extend(byte b){
     	return (int)(b+128);
     }
     
     //恢复byte的范围为-128~127进行文件存储
    @Deprecated
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
//    	System.out.println("ip32Str:" + ip32Str);
    	return ip32Str.toString();
    }
    
  //使用address类可以直接将ip转化为字节数组,里面byte范围转换为第二种映射
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
    @Deprecated
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
    @Deprecated
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
