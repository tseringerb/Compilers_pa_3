package boris.tserinher.classRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.omg.CORBA.TCKind;

import boris.tserinher.codeGeneration.ClassFile;
import boris.tserinher.codeGeneration.Method;

public class ClassRepository {
	
	private ClassFile classFile;
	
	public ClassRepository(String fileName) {
		tjcToExecuteObject(fileName);
	}
	
	public Method getMethod(String nameMethod){
		return classFile.getMethod(nameMethod);
	}
	
	private void tjcToExecuteObject(String fileName){
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
			classFile = (ClassFile) in.readObject();
			classFile.print();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
