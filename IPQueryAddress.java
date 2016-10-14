package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

public class IPQueryAddress {
	public static void main(String[] args){

		
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
		IPQueryAddress.readToFile();
	}
	
	//�������1000��ip���ݣ������ö�����ת������ֵ�ַ��������ļ��洢ip.txt
	public static void randomIpData(){
		
	}
	
	//��ȡip�ļ��е�ip����
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
			BigInteger num = byteArr2NumStr(bytes);
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
    
	
	
	/**************************��Ҫ���ķ���***************************/
    //�������Ƶ�(8/16/24/32/...)λ�������ַ���תΪʮ������ֵ��
    public static BigInteger binaryStr2Num(String binaryStr){
    	BigInteger num = new BigInteger(binaryStr,2);
    	System.out.println("num:" + num);
    	return num;
    }
    
    //��ʮ���Ƶ���ֵ��ת��Ϊ�����Ƶ�(8/16/24/32/...)λ�ĳ������ַ���
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
    
    //�ж��ַ����Ƿ��޷�������
    public final static boolean isNumeric(String s) {
		if (s != null && !"".equals(s.trim()))
			return s.matches("^[0-9]*$");
		else
			return false;
	}
    
   //��չbyte�ķ�ΧΪ0~255����java�߼��Ĵ���
    public static int extend(byte b){
    	return (int)(b+128);
    }
    
    //�ָ�byte�ķ�ΧΪ-128~127�����ļ��洢
    public static byte unextend(int i){
    	if(i>255)i=255;
    	if(i<0)i=0;
    	return (byte)(i-128);
    }
    /**********************************************************************/ 
    
   
    
    
    
    /*****************************ip��ع��߷���*********************************/
    //�������Ƶ�32λ������ת��Ϊip��
    public static String BinaryStr2Ip(String binaryStr){
    	StringBuffer ip = new StringBuffer();
    	for(int m=0; m<binaryStr.toString().length(); m+=8){//ÿ8λ��ǰ�ƶ�һ��            
           String str = binaryStr.substring(m, m+8);//��ȡ�ַ�����8λ�ַ�  
           BigInteger bi = binaryStr2Num(str);//����ת��Ϊʮ����  
           if(m!=24){//�������һλ�����ֺ�Ҫ�ӵ�               
        	   ip.append(bi.toString());  
        	   ip.append(".");  
           }else{               
        	   ip.append(bi.toString());  
           }  
        } 
    	return ip.toString();  
    }
    
    
    //��ip��ת��Ϊ32λ������    
    public static String ip2BinaryStr(String ip){
    	String[] ipArr = ip.split("\\.");
    	//��������ת���ɶ����Ƶ�ip��
    	StringBuffer ip32Str = new StringBuffer();
    	for(int i=0;i<4 && i<ipArr.length;i++){
    		ip32Str.append(numStr2BinaryStr(ipArr[i],1));
    	}
    	System.out.println("ip32Str:" + ip32Str);
    	return ip32Str.toString();
    }
    
    //��ip��ͨ��һ�����㷨�õ�һ��Ψһ����ֵ����ipһһ��Ӧ
    public static String ip2NumStr(String ip){
    	String numStr = "";
    	String BinaryStr = ip2BinaryStr(ip);
    	numStr = binaryStr2Num(BinaryStr).toString();
    	return numStr;
    }
    
    
    /***************************��ֵ�ַ���ת�����ֽ�����**********************************/
    
    //1.���Դ��ж������ļ������λ�Ʋ���
    //2.��ip���ݿ������������ɶ������ļ���ʽ
    //3.���Զ��ַ���ѯЧ��
    //4.�����ݿ��ѯ��Ч�����ȶԣ���������
   
    //����Ҫ�������ֵ����ֳ�byte����
    //s==��Ҫ�������ֵ����i==s��ռ���ֽ���
    //return byte[]
    public static byte[] numStr2ByteArr(String s,int i){
    	//���Ƚ���ֵ�ַ�������ɶ����Ƶ��ַ���  8λ/16λ/24λ/32λ/...
    	String binaryStr = numStr2BinaryStr(s,i);
    	byte[] bytes = new byte[i];
    	//�ٽ��������ַ�����8λ���зָ�
    	for(int m=0; m<i; m++){          
            String str = binaryStr.substring(m*8, m*8+8);//��ȡ�ַ�����8λ�ַ�  
            BigInteger bi = binaryStr2Num(str);//����ת��Ϊʮ����  
            //Ȼ��8λ�Ķ������ַ�������ɵ����ֽڴ�Ž��ֽ�������
            //(�ڼ���Ҫ��8λ������ת���ɵ�int��ת����byte����Ҫ��0~255��-128~127��ӳ��)
            byte b = unextend(bi.intValue());
            bytes[m] = b;
    	}
    	return bytes;
    }
    
    //return String ��ֵ�ַ���
    public static BigInteger byteArr2NumStr(byte[] b){
    	BigInteger num = null;
    	if(b.length>0){
    		int n = 0;
    		StringBuffer numStr = new StringBuffer("");
    		for(int i=0; i<b.length; i++){
    			//���Ƚ�ÿ���ֽ�ת��int (-128~127��0~255��ӳ��)
    			n = extend(b[i]);
    			//��intת�ɶ����ƽ���ƴ��    			
    			numStr.append(numStr2BinaryStr(String.valueOf(n),1));
    		}
    		//�ٽ�ƴ�ӵĶ������ַ���ת����ʮ����
    		num = binaryStr2Num(numStr.toString());
    	}
    	return num;
    }
    
}
