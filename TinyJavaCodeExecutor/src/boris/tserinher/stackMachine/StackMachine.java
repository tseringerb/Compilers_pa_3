package boris.tserinher.stackMachine;

import java.util.Stack;

import boris.tserinher.classRepository.ClassRepository;
import boris.tserinher.codeGeneration.Method;
import boris.tserinher.instructions.ICodes;
import boris.tserinher.instructions.Instruction;

public class StackMachine implements ICodes{
	
	private ClassRepository classRepository;
	private String fileName;
	
	public StackMachine(String fileName){
		classRepository = new ClassRepository(fileName);
		this.fileName = fileName;
		System.out.println("CLASSREP " + classRepository);
		
	}
	
	private String onlyNameOfFile(String fileName){
		String delimetr = "\\\\";
		String[] subSrings = fileName.split(delimetr);
		int subStringIndex = subSrings.length - 1;
		String subString = subSrings[subStringIndex];
		String outputFileName = subString.substring(0, subString.indexOf("."));
		return outputFileName;
	}
	
	private Stack<Integer> dataStack = new Stack<>();
	private Stack<Method> activationStack = new Stack<>();
	private Method currentMethodActivation;
	private Instruction instruction;
	private int code;
	
	public void startExecution(){
		int variable1;
		int variable2;
		//TODO
		String mainMethodClassName = onlyNameOfFile(fileName);
		currentMethodActivation = classRepository.getMethod(mainMethodClassName + ".main");
		System.out.println("CURRENT METHOD " + currentMethodActivation);
		//currentMethodActivation.setProgramCounter();
		while (code != STOP ) {
			System.out.println("PROG COUNT " + currentMethodActivation.getProgramCounter());
			instruction = currentMethodActivation.nextInstruction();
			code = instruction.getCode();
			Object arg = instruction.getArgument();
			switch (code){
			case ICONST: // push integer value arg
				dataStack.push((Integer) arg);
			break;
			case IADD: // v1 = pop(), v2 = pop(), push(v1+v2)
				variable1 = dataStack.pop();
				variable2 = dataStack.pop();
				dataStack.push(variable1 + variable2);
			break;
			case GOTO: // pc = arg
				System.out.println("GOTO");
				currentMethodActivation.setProgramCounter((int) arg);
			break;
			case IFFALSE: // v = pop(), let pc = arg if v=0
				System.out.println("IFFALSE");
			if (dataStack.pop() == 0)
				currentMethodActivation.setProgramCounter((int)arg);
			break;
			case ILOAD: 
				variable1 = currentMethodActivation.getVariableValue((int)arg);		//Push integer value stored in local variable n.
				dataStack.push(variable1);
				break;
			case ISTORE:
				int value = dataStack.pop();
				currentMethodActivation.assignmentValueToVariable((int)arg, value);//Pop value v and store it in local variable n.
				break;
			case ISUB:
				variable1 = dataStack.pop();
				variable2 = dataStack.pop();
				dataStack.push(variable1 - variable2);
				break;
			case IMUL:
				variable1 = dataStack.pop();
				variable2 = dataStack.pop();
				dataStack.push(variable1 * variable2);
				break;
			case IDIV:
				variable1 = dataStack.pop();
				variable2 = dataStack.pop();
				dataStack.push(variable1/variable2);
				break;
			case ILT: //Pop value v1, Pop value v2, Push 1 if v2 < v1 else Push 0.
				variable1 = dataStack.pop();
				variable2 = dataStack.pop();
				if(variable2 < variable1){
					dataStack.push(1);
				} else {
					dataStack.push(0);
				}
				break;
			case IEQ: //Pop value v1, Pop value v2, Push 1 if v1 = v2 else Push 0.
				variable1 = dataStack.pop();
				variable2 = dataStack.pop();
				if(variable1 == variable2){
					dataStack.push(1);
				} else {
					dataStack.push(0);
				}
				break;
			case IAND: //Pop value v1, Pop value v2, Push 0 if v1 * v2 = 0 else Push 1.
				variable1 = dataStack.pop();
				variable2 = dataStack.pop();
				if((variable1 * variable2) != 0){
					dataStack.push(1);
				} else {
					dataStack.push(0);
				}
				break;
			case IOR: //Pop value v1, Pop value v2, Push 0 if v1 + v2 = 0 else Push 1.
				variable1 = dataStack.pop();
				variable2 = dataStack.pop();
				if((variable1 + variable2) != 0){
					dataStack.push(1);
				} else {
					dataStack.push(0);
				}
				break;
			case INOT: //Pop value v, Push 1 if v = 0 else Push 0.
				variable1 = dataStack.pop();
				if(variable1 == 0){
					dataStack.push(1);
				}else {
					dataStack.push(0);
				}
				break;
			case INVOKEVIRTUAL: //Push current activation and switch to the method having qualified name m.
				System.out.println("INVOKE " + arg);
				activationStack.push(currentMethodActivation);
				currentMethodActivation = classRepository.getMethod((String)arg);
				System.out.println(currentMethodActivation);
				activationStack.forEach(method -> {
					System.out.println("STACK METHOD PUSH " + method);
				});
				break;
			case IRETURN: //Pop activation and continue.
				currentMethodActivation = activationStack.pop();
				System.out.println("RETURN " + currentMethodActivation);
				activationStack.forEach(method -> {
					System.out.println("STACK METHOD POP " + method);
				});
				break;
			case PRINT: //Pop value and print.
				System.out.println(dataStack.pop());
				break;
			case STOP: 
				break;	
			}
		}
	}
	
	

}