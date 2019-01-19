package boris.tserinher.codeGeneration;

import java.io.Serializable;
import java.util.HashMap;

public class ClassFile implements Serializable {
	
	private HashMap<String, Method> methodsList = new HashMap<>();
	
	public Method addMethod(String methodName/*, Method method*/){
		//Method returnMethod =
		methodsList.put(methodName, new Method());
		//returnMethod = methodsList.put("methodName", new Method());
		//returnMethod = methodsList.put("methodName", new Method());
		//System.out.println(returnMethod);
		//System.out.println(methodsList.size());
		//System.out.println("@@@@@@" + methodsList.get(methodName).getClass());
		//System.out.println("return from add method" + returnMethod);
		return methodsList.get(methodName);
	}
	
	public void print(){
		methodsList.forEach((methodName, method) -> {
			System.out.printf("Method Name: %s%n", methodName);
		});
	}

	
}
