package boris.tserinher.codeGeneration;

import java.io.Serializable;
import java.util.HashMap;

public class ClassFile implements Serializable {
	
	private HashMap<String, Method> methodsList = new HashMap<>();
	
	public Method addMethod(String methodName/*, Method method*/){
		methodsList.put(methodName, new Method());
		return methodsList.get(methodName);
	}
	
	public void print(){
		methodsList.forEach((methodName, method) -> {
			System.out.printf("Method Name: %s%n", methodName);
		});
	}
}
