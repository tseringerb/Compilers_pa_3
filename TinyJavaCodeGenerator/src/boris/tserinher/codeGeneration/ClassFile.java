package boris.tserinher.codeGeneration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ClassFile implements Serializable {
	
	private static final long serialVersionUID = -3239548327256764892L;
	
	private HashMap<String, Method> methodsList = new LinkedHashMap<>();
	
	public Method addMethod(String methodName/*, Method method*/){
		methodsList.put(methodName, new Method());
		return methodsList.get(methodName);
	}
	
	public void print(){
		methodsList.forEach((methodName, method) -> {
			System.out.printf("%s%n", methodName);
			method.print();
		});
	}

	public Method getMethod(String nameMethod) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
}
