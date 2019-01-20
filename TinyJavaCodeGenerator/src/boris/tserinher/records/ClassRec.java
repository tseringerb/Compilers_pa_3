package boris.tserinher.records;

import java.util.HashMap;

public class ClassRec extends BaseRec {
		
	public ClassRec(String id, String type) {
		super(id, type);
	}
	
	private HashMap<String, MethodRec> methodsList = new HashMap<>();
	private HashMap<String , VariableRec> fieldsList = new HashMap<>();
	
	public void putMethodRecord(MethodRec methodRecord){
		methodsList.put(methodRecord.getId(), methodRecord);
	}
	
	public MethodRec getMethodRecord(String methodId){
		return methodsList.get(methodId);
	}
	
	public void putFieldsRecord(VariableRec fieldRecord){
		fieldsList.put(fieldRecord.getId(), fieldRecord);
	}
	
	public VariableRec getFieldRecord(String fieldId){
		return fieldsList.get(fieldId);
	}
	
	public void printMethodsList(){
		methodsList.forEach((id, record)->{
			System.out.format("Method: %s \n", record);
		});
	}
	
	public void printFieldsList(){
		fieldsList.forEach((id, record)->{
			System.out.format("Field: %s \n", record);
		});
	}

}
