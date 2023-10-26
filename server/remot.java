
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface remot extends Remote {
	public String cryptage(String msg ,String algo)throws RemoteException ;
	public String decryptage(String msg ,String algo)throws RemoteException ;

    public Date getserverDate()throws RemoteException;
}
