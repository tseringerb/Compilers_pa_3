package boris.tserinher.visitor;

import boris.tserinher.MiniJavaGrammarBaseVisitor;
import boris.tserinher.MiniJavaGrammarParser.AndExpressionContext;
import boris.tserinher.MiniJavaGrammarParser.AssignmentStatementContext;
import boris.tserinher.MiniJavaGrammarParser.ClassDeclarationContext;
import boris.tserinher.MiniJavaGrammarParser.DivExpressionContext;
import boris.tserinher.MiniJavaGrammarParser.EqualExpressionContext;
import boris.tserinher.MiniJavaGrammarParser.FieldContext;
import boris.tserinher.MiniJavaGrammarParser.IDExpressionContext;
import boris.tserinher.MiniJavaGrammarParser.IdentifierTypeContext;
import boris.tserinher.MiniJavaGrammarParser.IfStatmentContext;
import boris.tserinher.MiniJavaGrammarParser.LessExpressionContext;
import boris.tserinher.MiniJavaGrammarParser.MainClassContext;
import boris.tserinher.MiniJavaGrammarParser.MainMethodContext;
import boris.tserinher.MiniJavaGrammarParser.MethodCallExpressionContext;
import boris.tserinher.MiniJavaGrammarParser.MethodContext;
import boris.tserinher.MiniJavaGrammarParser.MethodInvocationContext;
import boris.tserinher.MiniJavaGrammarParser.MinusExpressionContext;
import boris.tserinher.MiniJavaGrammarParser.MultExpressionContext;
import boris.tserinher.MiniJavaGrammarParser.NotExpressionContext;
import boris.tserinher.MiniJavaGrammarParser.OrExpressionContext;
import boris.tserinher.MiniJavaGrammarParser.ParameterContext;
import boris.tserinher.MiniJavaGrammarParser.ParametersListContext;
import boris.tserinher.MiniJavaGrammarParser.PlusExpressionContext;
import boris.tserinher.MiniJavaGrammarParser.PrePlusMinusIntegerExpressionContext;
import boris.tserinher.MiniJavaGrammarParser.PrintStatementContext;
import boris.tserinher.MiniJavaGrammarParser.ReturnStatementContext;
import boris.tserinher.MiniJavaGrammarParser.StartContext;
import boris.tserinher.MiniJavaGrammarParser.WhileStatementContext;
import boris.tserinher.codeGeneration.ClassFile;
import boris.tserinher.codeGeneration.Method;
import boris.tserinher.instructions.ICodes;
import boris.tserinher.instructions.Instruction;
import boris.tserinher.records.ClassRecord;
import boris.tserinher.records.MethodRecord;
import boris.tserinher.records.Record;
import boris.tserinher.symbolTable.MiniJavaSymbolTable;
import boris.tserinher.symbolTable.SymbolTable;

public class CodeGenerationVisitor extends MiniJavaGrammarBaseVisitor<Record> implements ICodes{
	
	private MiniJavaSymbolTable symtab; //From previous iteration
	private Method currentMethod; //See visitMethodDecl()
	private String currentClass; //See visitClassDecl()
	private ClassFile classFile; //To be saved on disk
	
	
	public CodeGenerationVisitor(SymbolTable symtab) {
		super();
		this.symtab = (MiniJavaSymbolTable) symtab;
	}
	
	public ClassFile getClassFile() {
		return classFile;
	}

	@Override
	public Record visitLessExpression(LessExpressionContext ctx) {
		int counter = ctx.getChildCount();
		//System.out.println("LESS_EXP " + ctx.getText());		
		visit(ctx.getChild(0));
		for(int i = 2; counter > i; i += 2){
			visit(ctx.getChild(i));
			currentMethod.addInstruction(ILT, null);
			
		}
		
		return null;
	}
	@Override
	public Record visitNotExpression(NotExpressionContext ctx) {
		//System.out.println("NOT_EXPR " + ctx.getText());
		visit(ctx.getChild(1));
		currentMethod.addInstruction(INOT, null);
		return null;
	}
	@Override
	public Record visitEqualExpression(EqualExpressionContext ctx) {
		//System.out.println("EQUAL_EXPR " + ctx.getText());
		int counter = ctx.getChildCount();
		visit(ctx.getChild(0));
		for(int i = 2; counter > i; i += 2){
			visit(ctx.getChild(i));
			currentMethod.addInstruction(IEQ, null);
			
		}
		return null;
	}
	@Override
	public Record visitMethod(MethodContext ctx) {
		//System.out.println("VISIT_METHOD_METHOD: " + ctx.getChild(1).getText());
		String currentMethodName = ctx.getChild(1).getText(); //Method name
		
		symtab.enterScope(); //TODO !!!! это костыль, потому что я не понимаю почему при вызове enterScope в классах мы не заходим в нижний Scope 
		System.out.println("!!! " + currentMethodName);
		MethodRecord mrec = (MethodRecord)symtab.lookup(currentMethodName);
		//System.out.println(mrec);
		currentMethod = classFile.addMethod(currentClass + "." + currentMethodName); // New Method!
		//System.out.println("MREC " + mrec.getLocals());
		currentMethod.addVariablesList(mrec.getLocals()); // Add variable array symtab.enterScope();
		
		visit(ctx.getChild(3)); // generate ParamDeclList code
		//System.out.println("~!!!~" + ctx.getChild(3).getText());
		visit(ctx.getChild(6)); // generate MethodBody code
		 
		symtab.exitScope();
		return null; //super.visitMethod(ctx);
	}
	@Override
	public Record visitPrintStatement(PrintStatementContext ctx) {
		//System.out.println("PRINT " + ctx.getChild(1).getChild(1).getText());
		visit(ctx.getChild(1).getChild(1));
		currentMethod.addInstruction(PRINT, null);
		return null;
	}
	/*@Override
	public Record visitField(FieldContext ctx) {
		visit(ctx.getChild(1));
		System.out.println("FIELD " + ctx.getChild(1).getText());
		int index = currentMethod.getIndexOf(ctx.getChild(1).getText());
		currentMethod.addInstruction(ILOAD,index);
		return null;
	}*/
	@Override
	public Record visitParametersList(ParametersListContext ctx) {
		//System.out.println("PARAMETR_LIST " + ctx.getText() + " " + ctx.getChildCount());
		int paramentersCount = ctx.getChildCount();
		for(int i = paramentersCount - 1; i >= 0; i -= 2){
			//System.out.println(ctx.getChild(i).getText());
			 visit(ctx.getChild(i));
		}
		return null;
	}
	
	@Override
	public Record visitIfStatment(IfStatmentContext ctx) {
		//System.out.println("IF_STATEMENT " + ctx.getText());
		//System.out.println(ctx.getChild(1).getChild(1).getText());
		//System.out.println(ctx.getChild(4).getText());
		//System.out.println(currentMethod.getCurrentInstructionIndex());
		visit(ctx.getChild(1).getChild(1)); // Generate condition
		int iflabel = currentMethod.getCurrentInstructionIndex(); // Save IF-number
		//System.out.println("IFLABEL " + iflabel);
		currentMethod.addInstruction(IFFALSE,null);
		visit(ctx.getChild(2)); // Generate if-body
		Instruction instruction = currentMethod.getInstruction(iflabel);
		//System.out.println("IF_INSTRUCTION " + instruction.getCode());
		instruction.setArgument( currentMethod.getCurrentInstructionIndex() + 1 );//Update IF_FALSE
		//System.out.println("UPDATE_IF " + currentMethod.getCurrentInstructionIndex() + 1);
		int gotolabel = currentMethod.getCurrentInstructionIndex(); // Save GOTO-number
		currentMethod.addInstruction(GOTO,null);
		visit(ctx.getChild(4)); // Generate else-body
		instruction = currentMethod.getInstruction(gotolabel);
		instruction.setArgument(currentMethod.getCurrentInstructionIndex());// Update GOTO
		//TODO
		return null;
	}
	
	
	@Override
	public Record visitMinusExpression(MinusExpressionContext ctx) {
		//System.out.println("MINUS_EXPR " + ctx.getText());
		int counter = ctx.getChildCount();
		visit(ctx.getChild(0));
		for(int i = 2; counter > i; i += 2){
			visit(ctx.getChild(i));
			currentMethod.addInstruction(ISUB, null);
			
		}
		return null;
	}
	@Override
	public Record visitMainClass(MainClassContext ctx) {
		currentClass = ctx.getChild(1).getText();
		symtab.enterScope();
		visit(ctx.getChild(3));
		symtab.exitScope();
		currentMethod.addInstruction(STOP, null);
		return null;
	}
	@Override
	public Record visitAssignmentStatement(AssignmentStatementContext ctx) {
		//System.out.println("ASSIGNMETN STATEMENT" + ctx.getText());
		String lhs = ctx.getChild(0).getText(); //LHS name
		visit(ctx.getChild(2)); //Generate RHS code
		int index = currentMethod.getIndexOf(lhs);
		currentMethod.addInstruction(ISTORE, index);
		return null;
	}
	@Override
	public Record visitMethodCallExpression(MethodCallExpressionContext ctx) {
		//System.out.println("CALLMETHOD");
		visit(ctx.getChild(4)); // Push parameters
		String targetMethod = ctx.getChild(2).getText();
		String containingClass = ctx.getChild(0).getText();
		if(containingClass.equals("this")){
			containingClass = currentClass;
		} else if(ctx.getChild(0).getChildCount() > 1) { 
			containingClass = ctx.getChild(0).getChild(1).getText();

		}
		//else if(ctx.getChild(0).getText())
		//currentMethod.addInstruction(INVOKEVIRTUAL, /*crec.getID() + "." + mrec.getID()*/);
		//MethodRecord mrec = (MethodRecord) symtab.lookup(ctx.getChild(2).getText());
		//MethodRecord mrec = ... a bit of work // Target method
		//ClassRecord crec = mrec.getContainingClass(); // Containing class
		currentMethod.addInstruction(INVOKEVIRTUAL, containingClass + "." + targetMethod);
		//System.out.println("METHOD CALL " + ctx.getChild(4).getText());
		//System.out.println("TARGET METHOD CLASS  " + ctx.getChild(0).getText());
		//System.out.println("TARGET METHOD " + ctx.getChild(2).getText());
		return null;
	}
	@Override
	public Record visitDivExpression(DivExpressionContext ctx) {
		//System.out.println("DIVISION_EXPR " + ctx.getText());
		int counter = ctx.getChildCount();
		visit(ctx.getChild(0));
		for(int i = 2; counter > i; i += 2){
			visit(ctx.getChild(i));
			currentMethod.addInstruction(IDIV, null);
			
		}
		return null;
	}
	@Override
	public Record visitReturnStatement(ReturnStatementContext ctx) {
		//System.out.println("RETURN_STATE " + ctx.getText());
		visit(ctx.getChild(1));
		currentMethod.addInstruction(IRETURN, null);
		return null;
	}
	/*@Override
	public Record visitIdentifierType(IdentifierTypeContext ctx) {
		System.out.println("IDENTIFIRE" + ctx.getText());
		//TODO
		return null;
	}*/
	@Override
	public Record visitClassDeclaration(ClassDeclarationContext ctx) {
		currentClass = ctx.getChild(1).getText();
		symtab.enterScope();
		visit(ctx.getChild(3));
		symtab.exitScope();
		return null; 
	}
	@Override
	public Record visitAndExpression(AndExpressionContext ctx) {
		//System.out.println("AND_EXPR " + ctx.getText());
		int counter = ctx.getChildCount();
		visit(ctx.getChild(0));
		for(int i = 2; counter > i; i += 2){
			visit(ctx.getChild(i));
			currentMethod.addInstruction(IAND, null);
			
		}
		return null;
	}
	@Override
	public Record visitParameter(ParameterContext ctx) {
		//System.out.println("PARAMETRS " + ctx.getText());
		//System.out.println(ctx.getChild(1).getText());
		int variableIndex = currentMethod.getIndexOf(ctx.getChild(1).getText());
		//System.out.println(ctx.getChild(1).getText() + " = " + variableIndex);
		currentMethod.addInstruction(ISTORE, variableIndex);
		return null;
	}
	@Override
	public Record visitMultExpression(MultExpressionContext ctx) {
		//System.out.println("IMUL");
		int numChildren = ctx.getChildCount();
		visit(ctx.getChild(0)); //first factor code
		for (int i=2; i<numChildren; i+=2) {
		visit(ctx.getChild(i)); //i:th factor code
		currentMethod.addInstruction(IMUL, null);
		}
		return null;
	}
	@Override
	public Record visitPlusExpression(PlusExpressionContext ctx) {
		//System.out.println("PLUS_EXPR " + ctx.getText());
		int counter = ctx.getChildCount();
		visit(ctx.getChild(0));
		for(int i = 2; counter > i; i += 2){
			visit(ctx.getChild(i));
			currentMethod.addInstruction(IADD, null);
			
		}
		return null;
	}
	@Override
	public Record visitMainMethod(MainMethodContext ctx) {
		String currentMethodName = ctx.getChild(3).getText();
		currentMethod = classFile.addMethod(currentClass + "." + currentMethodName);
		symtab.enterScope();
		visit(ctx.getChild(11));
		symtab.exitScope();
		return null; 
	}
	@Override
	public Record visitStart(StartContext ctx) {
		classFile = new ClassFile();
		symtab.enterScope();
		visit(ctx.getChild(0));
		return null; 
	}
	@Override
	public Record visitWhileStatement(WhileStatementContext ctx) {
		//TODO
		//System.out.println("WHILE_STATEMENT " + ctx.getText());
		//System.out.println(currentMethod.getCurrentInstructionIndex());
		//System.out.println("Condition " + ctx.getChild(1).getChild(1).getText());
		int whileLabel = currentMethod.getCurrentInstructionIndex();
		visit(ctx.getChild(1).getChild(1)); // Generate condition
		
		int iflabel = currentMethod.getCurrentInstructionIndex(); // Save IF-number
		//System.out.println("IFLABLE " + iflabel);
		currentMethod.addInstruction(IFFALSE,null);
		visit(ctx.getChild(2)); // Generate while-body
		//System.out.println("WHILE_BODY " + ctx.getChild(2).getText());
		Instruction instruction = currentMethod.getInstruction(iflabel);
		instruction.setArgument( currentMethod.getCurrentInstructionIndex() + 1 );//Update IF_FALSE
		//System.out.println("INDEX_WH " + currentMethod.getCurrentInstructionIndex() + 1);
		int gotolabel = currentMethod.getCurrentInstructionIndex(); // Save GOTO-number
		currentMethod.addInstruction(GOTO,null);
		instruction = currentMethod.getInstruction(gotolabel);
		//instruction.setArgument(currentMethod.getCurrentInstructionIndex());// Update GOTO
		instruction.setArgument(whileLabel);// Update GOTO
		//System.out.println("GOTO -> " + gotolabel + " " + currentMethod.getCurrentInstructionIndex());
		return null;
	}
	@Override
	public Record visitOrExpression(OrExpressionContext ctx) {
		//System.out.println("OR_EXPR " + ctx.getText());
		int counter = ctx.getChildCount();
		visit(ctx.getChild(0));
		for(int i = 2; counter > i; i += 2){
			visit(ctx.getChild(i));
			currentMethod.addInstruction(IOR, null);
			
		}
		return null;
	}

	@Override
	public Record visitMethodInvocation(MethodInvocationContext ctx) {
		int paramentrsCount = ctx.getChildCount();
		//System.out.println("METHOD INVOCATION " + ctx.getText() + " " + paramentrsCount);
		for(int i = 0; i < paramentrsCount; i += 2){
			visit(ctx.getChild(i));
		}
		return null;
	}

	@Override
	public Record visitPrePlusMinusIntegerExpression(
			PrePlusMinusIntegerExpressionContext ctx) {
		//System.out.println("INTEGER " + ctx.getText());
		int variableValue = Integer.parseInt(ctx.getText());
		currentMethod.addInstruction(ICONST, variableValue);
		return null;
	}

	@Override
	public Record visitIDExpression(IDExpressionContext ctx) { //Only within rhs expressions
		//System.out.println("ID " + ctx.getText());
		if(ctx.getText().equals("this")){ //нужно проверить почему 'this' попадает как ID 
			
		} else{
			//System.out.println("ID = " + ctx.getText());
			int index = currentMethod.getIndexOf(ctx.getText());
			currentMethod.addInstruction(ILOAD,index);
		}	
		return null;		
	}

	/*@Override
	public Record visitVariableDeclarationStatement(
			VariableDeclarationStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}*/	
}
