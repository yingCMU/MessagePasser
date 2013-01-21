package ds.lab.messagepasser;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

import ds.lab.message.Message;

/**
 * thread from listener thread, responsible for reading from socket and add to
 * inputQueue. Be careful on synchronizing queue (should be fine since I use
 * blockingQueue).
 * <p>
 * TODO may add socket to sockMap for reuse. If we want to reuse sockets, no
 * need to close them
 * 
 * @author dmei
 * 
 */
public class WorkerThread implements Runnable {
	private ObjectInputStream in;
	private Socket connection;
	private BlockingQueue<Message> inputQueue; // input queue
	// private static HashMap<String, Socket> sockMap;

	Object rcved = null;

	public WorkerThread(Socket connection, BlockingQueue<Message> inputQueue) {
		super();
		this.connection = connection;
		this.inputQueue = inputQueue;// must lock
		// sockMap.put(connection.getInetAddress().getHostAddress(),
		// connection);
		new Thread(this).start();
	}

	// public static HashMap<String, Socket> getSockMap()
	// {
	// return sockMap;
	// }
	@Override
	public void run() {
		try {
			in = new ObjectInputStream(connection.getInputStream());
			while (true) {
				rcved = in.readObject();
				// TODO data type swtich
				Message tmp = (Message) rcved;
				// synchronized (sockMap) {
				// sockMap.put(tmp.getSrc(), connection);
				// System.out.println("put sockmap: "+tmp.getSrc());
				// }
				if (tmp.getData() == null) {
					System.err.println("worker>>>>" + connection.getInetAddress().getHostAddress() + " went offile");
				} else {
					inputQueue.add(tmp);
				}
			}

		} catch (IOException e) {
			if (e instanceof EOFException) {
				System.err.println("end...eof");
			} else {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
