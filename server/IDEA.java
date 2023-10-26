
import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class IDEA
{
  static String circular_shift(String s)
   {

   	String y="";

   	for(int i=25;i<128;i++)
   	y+=s.charAt(i);

    for(int i=0;i<25;i++)
   	y=y+s.charAt(i);

   	return y;

   }
   static String xor(String x,String y)
   {
   	String z="";
   if(x.length()<16)
   	for(int i=x.length();i<16;i++)
   		x="0"+x;
   		if(y.length()<16)
   	for(int i=y.length();i<16;i++)
   		y="0"+y;
   	for(int i=15;i>=0;i--)
   	{
   		if(x.charAt(i)==y.charAt(i))
   			z="0"+z;
   		else
   			z="1"+z;
   	}
   	return z;
   }
   static String mod_add(String x,String y)
   {
   	int temp1=Integer.parseInt(x,2);
   	int temp2=Integer.parseInt(y,2);
   	long l=(long) (temp1+temp2);
   	long ans=(long)(l%(Math.pow(2,16)));
   	String ans1=Long.toBinaryString(ans);
   	return ans1;
   }
     static String mod_mul(String x,String y)
   {
   	int temp1=Integer.parseInt(x,2);
   	int temp2=Integer.parseInt(y,2);
   	long l=(long)temp1*temp2;
   	long ans=(long)(l%(Math.pow(2,16)+1));
   	String ans1=Long.toBinaryString(ans);

   	return ans1;
   }
   static String add_inv(String x)
   {
   	long n1=Long.parseLong(x,2);
   	long k=(long)Math.pow(2,16);
	if(n1>k)
   		n1=n1%k;
   	n1=k-n1;

    String ans=Long.toBinaryString(n1);
      if(ans.length()<16)
      	for(int i=ans.length();i<16;i++)
      		ans="0"+ans;
   return ans;

   }
   static String mul_inv(String x)
   {
      BigInteger n1=new BigInteger(x,2);
      long k=(long)Math.pow(2,16);
   	  k=k+1;
   	  String m=k+"";
   	  BigInteger m1=new BigInteger(m);
      n1=n1.modInverse(m1);
      String ans=n1.toString(2);
      if(ans.length()<16)
      	for(int i=ans.length();i<16;i++)
      		ans="0"+ans;
     return ans;
   }


	public static void main(String args[])
	{
    String key="";
    for(int i=0;i<128;i++)
    {
    	int x=(Math.random()<0.5)?0:1;
    	key+=x;
    }
    System.out.println("key "+key);
    BigInteger bigInt1 = new BigInteger(key, 2);
    String hex=bigInt1.toString(16);
    System.out.println("key hex "+ hex);




    String subk[]=new String[52];
    int x,y;
	for(int j=0;j<6;j++)
	{
		x=0;
	 for(int i=0;i<8;i++)
	 {
	 	subk[i+(8*j)]=key.substring(x,x+16);
	 	x=x+16;
	 }
    key=circular_shift(key);
	}
	subk[48]=key.substring(0,16);
	subk[49]=key.substring(16,32);
	subk[50]=key.substring(32,48);
	subk[51]=key.substring(48,64);

	  // for(int i=0;i<52;i++)
	   //	System.out.println("subkey "+i+"\t"+subk[i]);
	   	String input="01101000011001010110110001101100011011110111011101101111011100100110110001100100";
	   	BigInteger bigInt = new BigInteger(input, 2);
	   	String hex1=bigInt.toString(16);
         System.out.println("input hex "+ hex1);



	   	 String i[]=new String[4];
	   	 i[0]=input.substring(0,16);

	   	 i[1]=input.substring(16,32);

	   	 i[2]=input.substring(32,48);

	   	 i[3]=input.substring(48,64);

	   	 String d[]=new String[15];

	   	 for(int j=0;j<8;j++)
	   	 {

	   	  d[1]=mod_mul(i[0],subk[0+(6*j)]);

	   	  d[2]=mod_add(i[1],subk[1+(6*j)]);

	   	  d[3]=mod_add(i[2],subk[2+(6*j)]);

	   	  d[4]=mod_mul(i[3],subk[3+(6*j)]);

	   	  d[5]=xor(d[1],d[3]);
	  	  d[6]=xor(d[2],d[4]);
	  	  d[7]=mod_mul(d[5],subk[4+(6*j)]);

	  	  d[8]=mod_add(d[6],d[7]);
	  	  d[9]=mod_mul(d[8],subk[5+(6*j)]);

	  	  d[10]=mod_add(d[7],d[9]);
	  	  d[11]=xor(d[1],d[9]);
	  	  d[12]=xor(d[3],d[9]);
	  	  d[13]=xor(d[2],d[10]);
	  	  d[14]=xor(d[4],d[10]);

	  	  i[0]=d[11];
	  	  i[1]=d[13];
	  	  i[2]=d[12];
	  	  i[3]=d[14];
	   	 }
	   	 d[1]=mod_mul(i[0],subk[48]);
	   	 String ohex1=Long.toHexString(Long.parseLong(d[1],2));
	   	 d[2]=mod_add(i[1],subk[49]);
	   	  String ohex2=Long.toHexString(Long.parseLong(d[2],2));
	   	 d[3]=mod_add(i[2],subk[50]);
	   	  String ohex3=Long.toHexString(Long.parseLong(d[3],2));
	   	 d[4]=mod_mul(i[3],subk[51]);
	   	  String ohex4=Long.toHexString(Long.parseLong(d[4],2));

        i[0]=d[1];
        i[1]=d[2];
        i[2]=d[3];
        i[3]=d[4];
	   	 String cipher=ohex1+ohex2+ohex3+ohex4;

	   	 System.out.println("cipher "+cipher);


	   	 int outp1,outp2;
	   	 String p1;
	   	 String p2;
	     for(int j=1;j<5;j++)
	   {
	   	if(d[j].length()<16)
     	for(int k=d[j].length();k<16;k++)
   		d[j]="0"+d[j];

	   	System.out.println("d "+d[j]);
	   	p1=d[j].substring(0,8);
	   	p2=d[j].substring(8,16);
	   	outp1=Integer.parseInt(p1,2);
	   	outp2=Integer.parseInt(p2,2);
	   	System.out.println("outp1 "+outp1 +(char)outp1);
	   	System.out.println("outp2 "+outp2 +(char)outp2);
	 }
	//decryption subkeys
	String invk[]=new String[52];
	invk[0]=mul_inv(subk[48]);
	invk[1]=add_inv(subk[49]);
	invk[2]=add_inv(subk[50]);
	invk[3]=mul_inv(subk[51]);
	invk[4]=(subk[46]);
	invk[5]=(subk[47]);


	invk[6]=mul_inv(subk[42]);
	invk[7]=add_inv(subk[44]);
	invk[8]=add_inv(subk[43]);
	invk[9]=mul_inv(subk[45]);
	invk[10]=(subk[40]);
	invk[11]=(subk[41]);

	invk[12]=mul_inv(subk[36]);
	invk[13]=add_inv(subk[38]);
	invk[14]=add_inv(subk[37]);
	invk[15]=mul_inv(subk[39]);
	invk[16]=(subk[34]);
	invk[17]=(subk[35]);

	invk[18]=mul_inv(subk[30]);
	invk[19]=add_inv(subk[32]);
	invk[20]=add_inv(subk[31]);
	invk[21]=mul_inv(subk[33]);
	invk[22]=(subk[28]);
	invk[23]=(subk[29]);

	invk[24]=mul_inv(subk[24]);
	invk[25]=add_inv(subk[26]);
	invk[26]=add_inv(subk[25]);
	invk[27]=mul_inv(subk[27]);
	invk[28]=(subk[22]);
	invk[29]=(subk[23]);

	invk[30]=mul_inv(subk[18]);
	invk[31]=add_inv(subk[20]);
	invk[32]=add_inv(subk[19]);
	invk[33]=mul_inv(subk[21]);
	invk[34]=(subk[16]);
	invk[35]=(subk[17]);

	invk[36]=mul_inv(subk[12]);
	invk[37]=add_inv(subk[14]);
	invk[38]=add_inv(subk[13]);
	invk[39]=mul_inv(subk[15]);
	invk[40]=(subk[10]);
	invk[41]=(subk[11]);

	invk[42]=mul_inv(subk[6]);
	invk[43]=add_inv(subk[8]);
	invk[44]=add_inv(subk[7]);
	invk[45]=mul_inv(subk[9]);
	invk[46]=(subk[4]);
	invk[47]=(subk[5]);

	invk[48]=mul_inv(subk[0]);
	invk[49]=add_inv(subk[1]);
	invk[50]=add_inv(subk[2]);
	invk[51]=mul_inv(subk[3]);

/*	for(int l1=0;l1<52;l1++)
	{
	BigInteger ibigInt = new BigInteger(invk[l1], 2);
	 String ihex=ibigInt.toString(16);
     System.out.println("inverse hex "+l1+" "+ ihex);

	}*/

		 for(int j=0;j<8;j++)
	   	 {

	   	  d[1]=mod_mul(i[0],invk[0+(6*j)]);
	   	  d[2]=mod_add(i[1],invk[1+(6*j)]);
	   	  d[3]=mod_add(i[2],invk[2+(6*j)]);
	   	  d[4]=mod_mul(i[3],invk[3+(6*j)]);
	   	  d[5]=xor(d[1],d[3]);
	  	  d[6]=xor(d[2],d[4]);
	  	  d[7]=mod_mul(d[5],invk[4+(6*j)]);
	  	  d[8]=mod_add(d[6],d[7]);
	  	  d[9]=mod_mul(d[8],invk[5+(6*j)]);
	  	  d[10]=mod_add(d[7],d[9]);
	  	  d[11]=xor(d[1],d[9]);
	  	  d[12]=xor(d[3],d[9]);
	  	  d[13]=xor(d[2],d[10]);
	  	  d[14]=xor(d[4],d[10]);
	  	  i[0]=d[11];
	  	  i[1]=d[13];
	  	  i[2]=d[12];
	  	  i[3]=d[14];
	   	 }
	   	 d[1]=mod_mul(i[0],invk[48]);
	   	 String dhex1=Long.toHexString(Long.parseLong(d[1],2));
	   	 d[2]=mod_add(i[1],invk[9]);
	   	  String dhex2=Long.toHexString(Long.parseLong(d[2],2));
	   	 d[3]=mod_add(i[2],invk[50]);
	   	  String dhex3=Long.toHexString(Long.parseLong(d[3],2));
	   	 d[4]=mod_mul(i[3],invk[51]);
	   	  String dhex4=Long.toHexString(Long.parseLong(d[4],2));


	   	  String decrypt=dhex1+dhex2+dhex3+dhex4;

	   	 System.out.println("decrypt "+decrypt);

	}
}
