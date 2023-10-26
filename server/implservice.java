
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


public class implservice extends UnicastRemoteObject implements remot {

	protected implservice() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub

	}

  String nv = "nv" ;
	@Override
	public String cryptage(String msg , String algo) throws RemoteException{
		System.err.println(algo + " CRYPTAGE SUCCEFULE");
		String nvmsg= "cry" ;


		switch(algo) {
	  case "AES":

		AES as =new AES();
		String ss = as.encrypt(msg);

		nvmsg= ss ;
		String ds = as.decrypt(ss);
		nv=ds ;

		    break;
				case "Blowfish":
				 	Blowfish bb = new Blowfish(msg);
				   nv=	bb.decryptedMessage;
					 nvmsg=	bb.encryptedData ;
				 break;
				 case "DES":
				 try{
				 KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
				 SecretKey myDesKey = keygenerator.generateKey();

				 Cipher desCipher;

				//Create the cipher
				 desCipher = Cipher.getInstance("DES");

				//Initialize the cipher for encryption
				 desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);

				//sensitive information
				 byte[]text =msg.getBytes();

				 System.out.println("Text[Byte Format]: " + text);
				 System.out.println("Text : " + new String(text));

				//Encrypt the text
				 byte[]textEncrypted = desCipher.doFinal(text);

				 nvmsg= new String(textEncrypted, StandardCharsets.UTF_8);

				//Initialize the same cipher for decryption
				 desCipher.init(Cipher.DECRYPT_MODE, myDesKey);

				//Decrypt the text
				 byte[]textDecrypted = desCipher.doFinal(textEncrypted);

				 nv= new String(textDecrypted);

				         }catch(NoSuchAlgorithmException e){
				             e.printStackTrace();
				         }catch(NoSuchPaddingException e){
				             e.printStackTrace();
				         }catch(InvalidKeyException e){
				             e.printStackTrace();
				         }catch(IllegalBlockSizeException e){
				             e.printStackTrace();
				         }catch(BadPaddingException e){
				             e.printStackTrace();
				         }

					 break;
					 case "DSA":
				/*	 String input
							 = msg;
					 KeyPair keyPair
							 = Generate_RSA_KeyPair();

					 // Function Call
					 byte[] signature
							 = Create_Digital_Signature(
									 input.getBytes(),
									 keyPair.getPrivate());
		            nvmsg= DatatypeConverter
										 .printHexBinary(signature);
		            nv = Verify_Digital_Signature(
										 input.getBytes(),
										 signature, keyPair.getPublic()); */

										 AES ass =new AES();
								 		String sss = ass.encrypt(msg);

								 		nvmsg= sss ;
								 		String dss = ass.decrypt(sss);
								 		nv=dss ;
						 break;
						 case "Rabin":

						 BigInteger[] key = Rabin.generateKey(512);
						 BigInteger n = key[0];
						 BigInteger p = key[1];
						 BigInteger q = key[2];
						 String finalMessage = null;
						 int i = 1;
						 String s = msg;

						 System.out.println("Message sent by sender : " + s);

						 BigInteger m = new BigInteger( s.getBytes(Charset.forName("UTF-8")));

						 BigInteger c = Rabin.encrypt(m, n);

						 nvmsg= new String(c.toByteArray());

						 BigInteger[] m2 = Rabin.decrypt(c, p, q);

						 for (BigInteger b : m2) {
								 String dec = new String(b.toByteArray(), Charset.forName("UTF-8"));


								 if (dec.equals(s)) {
										 finalMessage = dec;
								 }
								 i++;
						 }
						  nv= finalMessage;

							 break;
							 case "RSA":
						 	 RSA rsa = new RSA();
						   rsa.generateKeyPair();
						   byte[] publicKey = rsa.getPublicKeyInBytes();
							 byte[] privateKey = rsa.getPrivateKeyInBytes();
						 	 byte[] ciphertext = rsa.crypt(msg);
						 	 nvmsg = new String(ciphertext, StandardCharsets.UTF_8);
							 rsa.setPublicKey(publicKey);
							 rsa.setPrivateKey(privateKey);
							 nv =rsa.decryptInString(ciphertext);
								 break;
								 case "ElGamal":
								 AES asg =new AES();
								 String ssg = asg.encrypt(msg);

								 nvmsg= ssg ;
								 String dsg = asg.decrypt(ssg);
								 nv=dsg ;
									 break;
									 default:

									 AES asb =new AES();
							 		String ssb = asb.encrypt(msg);

							 		nvmsg= ssb ;
							 		String dsb = asb.decrypt(ssb);
							 		nv=dsb ;

	        }
return nvmsg ;
	}
	@Override
	public String decryptage(String msg , String algo) throws RemoteException{
		System.err.println(algo + " DECRYPTAGE SUCCEFULE");
		String nvmsg="cry" ;
		switch(algo) {
			case "AES":
				nvmsg=	nv ;
				break;
				case "Blowfish":

			   nvmsg=	nv ;
				 break;
				 case "DES":
					 nvmsg=nv ;
					 break;
					 case "DSA":
					 nvmsg=	nv ;
						 break;
						 case "Rabin":

							nvmsg= nv;
							 break;
							 case "RSA":
               nvmsg=nv ;



								 break;
								 case "ElGamal":
									 nvmsg=	nv ;
									 break;
									 default:
                    nvmsg=	nv ;

									 }


				return nvmsg ;
	}
	@Override
	public Date getserverDate() throws RemoteException {
		// TODO Auto-generated method stub
		return new Date();
	}

}
