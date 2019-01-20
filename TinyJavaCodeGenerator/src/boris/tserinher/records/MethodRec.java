package boris.tserinher.records;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MethodRec extends BaseRec {

	public MethodRec(String id, String type) {
		super(id, type);
	}
	private HashMap<String , VariableRec> parametersList = new HashMap<>();
	
	public void putParameterRecord(VariableRec parameterRecord){
		parametersList.put(parameterRecord.getId(), parameterRecord);
	}
	
	public VariableRec getParameterRecord(String parameterId){
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
}
