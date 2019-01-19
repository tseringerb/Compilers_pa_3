package boris.tserinher.visitor;

import boris.tserinher.MiniJavaGrammarBaseVisitor;
import boris.tserinher.MiniJavaGrammarParser.AndExpressionContext;
import boris.tserinher.MiniJavaGrammarParser.AssignmentStatementContext;
import boris.tserinher.MiniJavaGrammarParser.ClassDeclarationContext;
import boris.tserinher.MiniJavaGrammarParser.DivExpressionContext;
import boris.tserinher.MiniJavaGrammarParser.EqualExpressionContext;
import boris.tserinher.MiniJavaGrammarParser.FieldContext;
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
import boris.tserinher.MiniJavaGrammarParser.PrintStatementContext;
import boris.tserinher.MiniJavaGrammarParser.ReturnStatementContext;
import boris.tserinher.MiniJavaGrammarParser.StartContext;
import boris.tserinher.MiniJavaGrammarParser.WhileStatementContext;
import boris.tserinher.codeGeneration.ClassFile;
import boris.tserinher.codeGeneration.Method;
import boris.tserinher.records.MethodRecord;
import boris.tserinher.records.Record;
import boris.tserinher.symbolTable.MiniJavaSymbolTable;
import boris.tserinher.symbolTable.SymbolTable;

public class CodeGenerationVisitor extends MiniJavaGrammarBaseVisitor<Record> {
	
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
		String currentMethodName = ctx.getChild(1).getText(); //Method name
		//System.out.println("Current Method: " + currentMethodName);
		//System.out.println("Visit method "+ currentMethodName + " CurrentScopeName: " + symtab.getCurrentScopeName());
		//symtab.enterScope();
		//System.out.println("Current Method Scope: " + symtab.getCurrentScopeName());
		//TODO !!!! вызов ниже это костыль, потому что я не понимаю почему при вызове enterScope в классах мы не заходим в нижний Scope 
		symtab.enterScope();
		//System.out.println(symtab.getCurrentScopeName());
		//System.out.println("Records" + symtab.getRecords());
		MethodRecord mrec = (MethodRecord)symtab.lookup(currentMethodName);
		//System.out.println("SYMTAB " + symtab);
		//System.out.println("MREC " + mrec);
		currentMethod = classFile.addMethod(currentClass + "." + currentMethodName); // New Method!
		currentMethod.setVariablesList(mrec.getLocals()); // Add variable array symtab.enterScope();
		
		visit(ctx.getChild(3));
		visit(ctx.getChild(6));
		 
		symtab.exitScope();
		return null; //super.visitMethod(ctx);
	}
	@Override
	public Record visitPrintStatement(PrintStatementContext ctx) {
		// TODO Auto-generated method stub
		return super.visitPrintStatement(ctx);
	}
	@Override
	public Record visitField(FieldContext ctx) {
		System.out.println("VISIT FIELD");
		System.out.println(ctx.getText());
		System.out.println(ctx.getChild(0).getText());
		return super.visitField(ctx);
	}
	@Override
	public Record visitParametersList(ParametersListContext ctx) {
		System.out.println("Parametr list " + ctx.getText());
		return super.visitParametersList(ctx);
	}
	@Override
	public Record visitIfStatment(IfStatmentContext ctx) {
		// TODO Auto-generated method stub
		return super.visitIfStatment(ctx);
	}
	@Override
	public Record visitMinusExpression(MinusExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitMinusExpression(ctx);
	}
	@Override
	public Record visitMainClass(MainClassContext ctx) {
		currentClass = ctx.getChild(1).getText();
		System.out.println("Current class: " + currentClass);
		System.out.println("Visit main Class CurrentScopeName: " + symtab.getCurrentScopeName());
		symtab.enterScope();
		visit(ctx.getChild(3));
		symtab.exitScope();
		return null;//super.visitMainClass(ctx);
	}
	@Override
	public Record visitAssignmentStatement(AssignmentStatementContext ctx) {
		String lhs = ctx.getChild(0).getText(); //LHS name
		visit(ctx.getChild(1)); //Generate RHS code
		//int index = currentMethod.getIndexOf(lhs);
		//currentMethod.addInstruction(ISTORE,index);
		System.out.println("ASSIGNMETN STATEMENT");
		System.out.println(ctx.getText());
		return null;
		//return super.visitAssignmentStatement(ctx);
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
		System.out.println("VISIT IDDENTIFIERTYPE");
		System.out.println(ctx.getText());
		return super.visitIdentifierType(ctx);
	}
	@Override
	public Record visitClassDeclaration(ClassDeclarationContext ctx) {
		currentClass = ctx.getChild(1).getText();
		System.out.println("Current class: " + currentClass);
		System.out.println("Visit class Declar CurrentScope: " + symtab.getCurrentScopeName());
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
		System.out.println("VISIT PARAMETR");
		System.out.println(ctx.getText());
		return super.visitParameter(ctx);
	}
	@Override
	public Record visitMultExpression(MultExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitMultExpression(ctx);
	}
	@Override
	public Record visitPlusExpression(PlusExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitPlusExpression(ctx);
	}
	@Override
	public Record visitMainMethod(MainMethodContext ctx) {
		String currentMethodName = ctx.getChild(3).getText();
		System.out.println("Current Method: " + currentMethodName);
		System.out.println("Visit main method CurrentScope:" + symtab.getCurrentScopeName());
		currentMethod = new Method();
		//System.out.println(ctx.getChild(11).getText());
		symtab.enterScope();
		visit(ctx.getChild(11));
		symtab.exitScope();
		return null; //super.visitMainMethod(ctx);
	}
	@Override
	public Record visitStart(StartContext ctx) {
		classFile = new ClassFile();
		System.out.println("Visit start CurrentScope: " + symtab.getCurrentScopeName());
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
		System.out.println("VISIT METHOD INVOCATION");
		System.out.println(ctx.getText());
		return super.visitMethodInvocation(ctx);
	}
	
	
	
	
	
	
}
