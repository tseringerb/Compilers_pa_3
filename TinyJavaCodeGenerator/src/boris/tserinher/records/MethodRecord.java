package boris.tserinher.records;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MethodRecord extends Record {

	public MethodRecord(String id, String type) {
		super(id, type);
	}
	private HashMap<String , VarRecord> parametersList = new HashMap<>();
	
	public void putParameterRecord(VarRecord parameterRecord){
		parametersList.put(parameterRecord.getId(), parameterRecord);
	}
	
	public VarRecord getParameterRecord(String parameterId){
		return parametersList.get(parameterId);
	}
	
	public void printParametersList(){
		parametersList.forEach((id, record)->{
			System.out.format("Parameter: %s \n", record);
		});
	}

	public List<String> getLocals() {
		List<String> locals = new ArrayList<>();
		parametersList.forEach((id, varRecord)->{
			locals.add(varRecord.getId());
		});
		return locals;
	}
	
	/*public String getId(){
		return null;
		
	}*/
}
