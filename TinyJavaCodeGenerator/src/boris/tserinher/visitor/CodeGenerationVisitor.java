package boris.tserinher.visitor;

import java.lang.reflect.Method;

import boris.tserinher.MiniJavaGrammarBaseVisitor;
import boris.tserinher.symbolTable.SymbolTable;

public class CodeGenerationVisitor extends MiniJavaGrammarBaseVisitor<T> {
	
	private SymbolTable symtab; //From previous iteration
	private Method currentMethod; //See visitMethodDecl()
	private String currentClass; //See visitClassDecl()
	private ClassFile classfile; //To be saved on disk
	
}
