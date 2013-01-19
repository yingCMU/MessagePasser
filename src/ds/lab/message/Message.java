package ds.lab.message;


import java.io.Serializable;

public class Message implements Serializable {

	/**
	 * header and payload
	 */
	private static final long serialVersionUID = 1L;
	
	private Header header = null;
	private Object data = null;	
	
	public Message() {
		this.header = new Header("", "", MessageKind.NONE);
		setId(-1);//for initial use
	}
	public Message(String src, String dest, MessageKind kind, Object data) {
		assert src != null;
		assert dest != null;
		//TODO assert kind != null;
		this.header = new Header(src, dest, kind);
		this.data = data;
	}

	public int getId() {
		return header.id;
	}
	
	public void setId(int id) {
		header.id = id;
	}
	
	public String getDest() {
		return header.dest;
	}
	public String getSrc() {
		return header.src;
	}
	public MessageKind getKind() {
		return header.kind;
	}
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	
	@Override
	public String toString() {
		return this.header + "\n" + data;
	}



	private class Header implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2L;
		int id;
		String src;
		String dest;
		MessageKind kind;
		
		public Header(String src, String dest, MessageKind kind) {
			this.src = src;
			this.dest = dest;
			this.kind = kind;
		}
		
		@Override
		public String toString() {
			return "Message " + this.id + ", From " + this.src + " To " + this.dest + " kind: " + kind;
		}
	}
}
