package boris.tserinher.symbolTable;

import boris.tserinher.MiniJavaGrammarBaseListener;
import boris.tserinher.MiniJavaGrammarParser.ClassDeclarationContext;
import boris.tserinher.MiniJavaGrammarParser.FieldContext;
import boris.tserinher.MiniJavaGrammarParser.MainClassContext;
import boris.tserinher.MiniJavaGrammarParser.MainMethodContext;
import boris.tserinher.MiniJavaGrammarParser.MethodContext;
import boris.tserinher.MiniJavaGrammarParser.ParameterContext;
import boris.tserinher.MiniJavaGrammarParser.ProgramContext;
import boris.tserinher.records.ClassRec;
import boris.tserinher.records.MethodRec;
import boris.tserinher.records.VariableRec;

public class SymbolTableListener extends MiniJavaGrammarBaseListener {
	
	private MiniJavaSymbolTable symbolTable;
	private ClassRec currentClass;
	private MethodRec currentMethod;
	
	public SymbolTableListener() {
		super();
	}

	public SymbolTableListener(SymbolTable symbolTable) {
		super();
		this.symbolTable = (MiniJavaSymbolTable) symbolTable;
	}

	@Override
	public void enterProgram(ProgramContext ctx) {
		symbolTable.setCurrentScopeType("program");
		symbolTable.setCurrentScopeName("program");
		symbolTable.enterScope();
		super.enterProgram(ctx);
	}
	
	@Override
	public void exitProgram(ProgramContext ctx) {
		symbolTable.exitScope();
		super.exitProgram(ctx);
	}

	@Override
	public void enterMainClass(MainClassContext ctx) {
		String id = ctx.getChild(1).toString();
		String type = ctx.getChild(0).toString();
		
		currentClass = new ClassRec(id, type);
		symbolTable.enterScope();
		symbolTable.setCurrentScopeType(type);
		symbolTable.setCurrentScopeName(id);
		symbolTable.putRecord(id, currentClass);
		super.enterMainClass(ctx);
	}

	@Override
	public void exitMainClass(MainClassContext ctx) {
		symbolTable.exitScope();
		super.exitMainClass(ctx);
	}
	
	@Override
	public void enterClassDeclaration(ClassDeclarationContext ctx) {
		String id = ctx.getChild(1).toString();
		String type = ctx.getChild(0).toString();
		
		currentClass = new ClassRec(id, type);
		
		symbolTable.enterScope();
		symbolTable.setCurrentScopeType(type);
		symbolTable.setCurrentScopeName(id);
		symbolTable.putRecord(id, currentClass);
		super.enterClassDeclaration(ctx);

	}

	@Override
	public void exitClassDeclaration(ClassDeclarationContext ctx) {
		symbolTable.exitScope();
		super.exitClassDeclaration(ctx);
	}
	
	@Override
	public void enterMainMethod(MainMethodContext ctx) {
		String id = ctx.getChild(3).toString();
		String type = ctx.getChild(2).toString();
		
		symbolTable.enterScope();
		symbolTable.setCurrentScopeType("method");
		
		currentMethod = new MethodRec(id, type);
		currentClass.putMethodRecord(currentMethod);
		symbolTable.putRecord(id, currentMethod);
		symbolTable.setCurrentScopeName(id);
		
		super.enterMainMethod(ctx);
	}

	@Override
	public void exitMainMethod(MainMethodContext ctx) {
		symbolTable.exitScope();
		super.exitMainMethod(ctx);
	}	

	@Override
	public void enterMethod(MethodContext ctx) {
		String id = ctx.getChild(1).toString();
		String type = ctx.getChild(0).getChild(0).getChild(0).toString();
		symbolTable.enterScope();
		symbolTable.setCurrentScopeType("method");
		
		currentMethod = new MethodRec(id, type);
		currentClass.putMethodRecord(currentMethod);
		symbolTable.putRecord(id, currentMethod	);

		symbolTable.setCurrentScopeName(id);
		super.enterMethod(ctx);
	}

	@Override
	public void exitMethod(MethodContext ctx) {
		symbolTable.exitScope();
		super.exitMethod(ctx);
	}

	@Override
	public void enterField(FieldContext ctx) {
		String id = ctx.getChild(1).toString();
		String type = ctx.getChild(0).getChild(0).getChild(0).toString();
		
		VariableRec fieldRecord = new VariableRec(id, type);
		symbolTable.putRecord(id, fieldRecord);
		currentClass.putFieldsRecord(fieldRecord);
		
		super.enterField(ctx);
	}

	@Override
	public void enterParameter(ParameterContext ctx) {
		String id = ctx.getChild(1).toString();
		String type = ctx.getChild(0).getChild(0).getChild(0).toString();
		
		VariableRec parameterRecord = new VariableRec(id, type);		
		symbolTable.putRecord(id, parameterRecord);
		currentMethod.putParameterRecord(parameterRecord);

		super.enterParameter(ctx);
	}

}
