package boris.tserinher.records;

public class BaseRec {
	
	private String id;
	private String type;
	
	public BaseRec() {
		super();
	}
	
	public BaseRec(String id, String type) {
		super();
		this.id = id;
		this.type = type;
	}
	
	public String getId() {
		return id;
	}
	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", type=" + type + "]";
	}

}
