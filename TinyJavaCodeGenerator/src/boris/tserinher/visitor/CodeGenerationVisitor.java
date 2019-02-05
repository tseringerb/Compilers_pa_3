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

	@Override
	public Record visitLessExpression(LessExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitLessExpression(ctx);
	}
	@Override
	public Record visitNotExpression(NotExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitNotExpression(ctx);
	}
	@Override
	public Record visitEqualExpression(EqualExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitEqualExpression(ctx);
	}
	@Override
	public Record visitMethod(MethodContext ctx) {
		System.out.println("VISIT_METHOD_METHOD: " + ctx.getChild(1).getText());
		String currentMethodName = ctx.getChild(1).getText(); //Method name
		
		symtab.enterScope(); //TODO !!!! это костыль, потому что я не понимаю почему при вызове enterScope в классах мы не заходим в нижний Scope 
		MethodRecord mrec = (MethodRecord)symtab.lookup(currentMethodName);
		//System.out.println(mrec);
		currentMethod = classFile.addMethod(currentClass + "." + currentMethodName); // New Method!
		System.out.println("MREC " + mrec.getLocals());
		currentMethod.addVariablesList(mrec.getLocals()); // Add variable array symtab.enterScope();
		
		visit(ctx.getChild(3)); // generate ParamDeclList code
		//System.out.println("~!!!~" + ctx.getChild(3).getText());
		visit(ctx.getChild(6)); // generate MethodBody code
		 
		symtab.exitScope();
		return null; //super.visitMethod(ctx);
	}
	@Override
	public Record visitPrintStatement(PrintStatementContext ctx) {
		return super.visitPrintStatement(ctx);
	}
	@Override
	public Record visitField(FieldContext ctx) {
		return null;
	}
	@Override
	public Record visitParametersList(ParametersListContext ctx) {
		return super.visitParametersList(ctx);
	}
	@Override
	public Record visitIfStatment(IfStatmentContext ctx) {
		return super.visitIfStatment(ctx);
	}
	@Override
	public Record visitMinusExpression(MinusExpressionContext ctx) {
		return super.visitMinusExpression(ctx);
	}
	@Override
	public Record visitMainClass(MainClassContext ctx) {
		currentClass = ctx.getChild(1).getText();
		symtab.enterScope();
		
		visit(ctx.getChild(3));
		symtab.exitScope();
		return null;//super.visitMainClass(ctx);
	}
	@Override
	public Record visitAssignmentStatement(AssignmentStatementContext ctx) {
		//System.out.println("ASSIGNMETN STATEMENT");
		String lhs = ctx.getChild(0).getText(); //LHS name
		visit(ctx.getChild(2)); //Generate RHS code
		int index = currentMethod.getIndexOf(lhs);
		currentMethod.addInstruction(ISTORE, index);
		return null;
	}
	@Override
	public Record visitMethodCallExpression(MethodCallExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitMethodCallExpression(ctx);
	}
	@Override
	public Record visitDivExpression(DivExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDivExpression(ctx);
	}
	@Override
	public Record visitReturnStatement(ReturnStatementContext ctx) {
		// TODO Auto-generated method stub
		return super.visitReturnStatement(ctx);
	}
	@Override
	public Record visitIdentifierType(IdentifierTypeContext ctx) {
		return super.visitIdentifierType(ctx);
	}
	@Override
	public Record visitClassDeclaration(ClassDeclarationContext ctx) {
		currentClass = ctx.getChild(1).getText();
		symtab.enterScope();
		visit(ctx.getChild(3));
		symtab.exitScope();
		return null; //super.visitClassDeclaration(ctx);
	}
	@Override
	public Record visitAndExpression(AndExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitAndExpression(ctx);
	}
	@Override
	public Record visitParameter(ParameterContext ctx) {
		return super.visitParameter(ctx);
	}
	@Override
	public Record visitMultExpression(MultExpressionContext ctx) {
		int numChildren = ctx.getChildCount();
		visit(ctx.getChild(0)); //first factor code
		for (int i=2; i<numChildren; i+=2) {
		visit(ctx.getChild(i)); //i:th factor code
		currentMethod.addInstruction(new Instruction(IMUL, null));
		}
		return null;
	}
	@Override
	public Record visitPlusExpression(PlusExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitPlusExpression(ctx);
	}
	@Override
	public Record visitMainMethod(MainMethodContext ctx) {
		String currentMethodName = ctx.getChild(3).getText();
		currentMethod = new Method();
		symtab.enterScope();
		visit(ctx.getChild(11));
		symtab.exitScope();
		return null; //super.visitMainMethod(ctx);
	}
	@Override
	public Record visitStart(StartContext ctx) {
		classFile = new ClassFile();
		symtab.enterScope();
		visit(ctx.getChild(0));
		return null; //super.visitStart(ctx);
	}
	@Override
	public Record visitWhileStatement(WhileStatementContext ctx) {
		// TODO Auto-generated method stub
		return super.visitWhileStatement(ctx);
	}
	@Override
	public Record visitOrExpression(OrExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitOrExpression(ctx);
	}

	@Override
	public Record visitMethodInvocation(MethodInvocationContext ctx) {
		return super.visitMethodInvocation(ctx);
	}

	@Override
	public Record visitPrePlusMinusIntegerExpression(
			PrePlusMinusIntegerExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitPrePlusMinusIntegerExpression(ctx);
	}

	@Override
	public Record visitIDExpression(IDExpressionContext ctx) { //Only within rhs expressions
		
		if(ctx.getText().equals("this")){ //нужно проверить почему 'this' попадает как ID 
			
		} else{
			//System.out.println("ID = " + ctx.getText());
			int index = currentMethod.getIndexOf(ctx.getText());
			currentMethod.addInstruction(ILOAD,index);
		}	
		return null;		
	}
	
	
	
}
