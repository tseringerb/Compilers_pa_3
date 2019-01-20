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
import boris.tserinher.records.MethodRec;
import boris.tserinher.records.BaseRec;
import boris.tserinher.symbolTable.MiniJavaSymbolTable;
import boris.tserinher.symbolTable.SymbolTable;

public class CodeGenerationVisitor extends MiniJavaGrammarBaseVisitor<BaseRec> {
	
	private MiniJavaSymbolTable symtab; //From previous iteration
	private Method currentMethod; //See visitMethodDecl()
	private String currentClass; //See visitClassDecl()
	private ClassFile classFile; //To be saved on disk
	
	public CodeGenerationVisitor(SymbolTable symtab) {
		super();
		this.symtab = (MiniJavaSymbolTable) symtab;
	}

	@Override
	public BaseRec visitLessExpression(LessExpressionContext ctx) {
		// TODO Auto-generated method stub
		/*int numChildren = ctx.getChildCount();
		visit(ctx.getChild(0)); // first factor code
		for(int i= 2; i < numChildren; i+=2){
			visit(ctx.getChild(i));
			currentMethod.addInstruction(new Instruction(ILT, null));
		}		
		return null;*/
		return super.visitLessExpression(ctx);
	}
	@Override
	public BaseRec visitNotExpression(NotExpressionContext ctx) {
		// TODO Auto-generated method stub
		/*visit(ctx.getChild(1));
		currentMethod.addInstruction(new Instruction(INOT, null));
		return null;*/
		return super.visitNotExpression(ctx);
	}
	@Override
	public BaseRec visitEqualExpression(EqualExpressionContext ctx) {
		// TODO Auto-generated method stub
		/*int numChildren = ctx.getChildCount();
		visit(ctx.getChild(0)); // first factor code
		for(int i= 2; i < numChildren; i+=2){
			visit(ctx.getChild(i));
			currentMethod.addInstruction(new Instruction(IEQ, null));
		}		
		return null;*/
		return super.visitEqualExpression(ctx);
	}
	@Override
	public BaseRec visitMethod(MethodContext ctx) {
		String currentMethodName = ctx.getChild(1).getText(); //Method name
		//System.out.println("Current Method: " + currentMethodName);
		//System.out.println("Visit method "+ currentMethodName + " CurrentScopeName: " + symtab.getCurrentScopeName());
		//symtab.enterScope();
		//System.out.println("Current Method Scope: " + symtab.getCurrentScopeName());
		//TODO !!!! вызов ниже это костыль, потому что я не понимаю почему при вызове enterScope в классах мы не заходим в нижний Scope 
		symtab.enterScope();
		//System.out.println(symtab.getCurrentScopeName());
		//System.out.println("BaseRecs" + symtab.getBaseRecs());
		MethodRec mrec = (MethodRec)symtab.lookup(currentMethodName);
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
	public BaseRec visitPrintStatement(PrintStatementContext ctx) {
		// TODO Auto-generated method stub
		/*visit(ctx.getChild(2)); // visit inside print
		currentMethod.addInstruction(new Instruction(PRINT,null));
		return null;*/
		return super.visitPrintStatement(ctx);
	}
	@Override
	public BaseRec visitField(FieldContext ctx) {
		System.out.println("VISIT FIELD");
		System.out.println(ctx.getText());
		System.out.println(ctx.getChild(0).getText());
		return super.visitField(ctx);
	}
	@Override
	public BaseRec visitParametersList(ParametersListContext ctx) {
		System.out.println("Parametr list " + ctx.getText());
		return super.visitParametersList(ctx);
	}
	@Override
	public BaseRec visitIfStatment(IfStatmentContext ctx) {
		// TODO Auto-generated method stub
		/*visit(ctx.getChild(2)); // generate condition		
		int iflabel = currentMethod.getIndex();
		currentMethod.addInstruction(new Instruction(IF_FALSE, null));
		visit(ctx.getChild(4)); // generate if body
		Instruction instr = currentMethod.getInstruction(iflabel);
		instr.setArgument(currentMethod.getIndex()+1); // update if_false
		
		int gotolabel = currentMethod.getIndex(); // save goto
		currentMethod.addInstruction(new Instruction(GOTO, null));
		if(ctx.getChild(6) != null){
			visit(ctx.getChild(6)); // generate if else body
			instr = currentMethod.getInstruction(gotolabel);
			instr.setArgument(currentMethod.getIndex());
		}
		return null;*/
		return super.visitIfStatment(ctx);
	}
	@Override
	public BaseRec visitMinusExpression(MinusExpressionContext ctx) {
		// TODO Auto-generated method stub
		/*int numChildren = ctx.getChildCount();
		visit(ctx.getChild(0)); // first factor code
		for(int i= 2; i < numChildren; i+=2){
			visit(ctx.getChild(i));
			currentMethod.addInstruction(new Instruction(ISUB, null));
		}
		return null;*/
		return super.visitMinusExpression(ctx);
	}
	@Override
	public BaseRec visitMainClass(MainClassContext ctx) {
		currentClass = ctx.getChild(1).getText();
		System.out.println("Current class: " + currentClass);
		System.out.println("Visit main Class CurrentScopeName: " + symtab.getCurrentScopeName());
		symtab.enterScope();
		visit(ctx.getChild(3));
		symtab.exitScope();
		return null;//super.visitMainClass(ctx);
	}
	@Override
	public BaseRec visitAssignmentStatement(AssignmentStatementContext ctx) {
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
	public BaseRec visitMethodCallExpression(MethodCallExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitMethodCallExpression(ctx);
	}
	@Override
	public BaseRec visitDivExpression(DivExpressionContext ctx) {
		// TODO Auto-generated method stub
		/*int numChildren = ctx.getChildCount();
		visit(ctx.getChild(0)); // first factor code
		for(int i= 2; i < numChildren; i+=2){
			visit(ctx.getChild(i));
			currentMethod.addInstruction(new Instruction(IDIV, null));
		}
		return null;*/
		return super.visitDivExpression(ctx);
	}
	@Override
	public BaseRec visitReturnStatement(ReturnStatementContext ctx) {
		// TODO Auto-generated method stub
		return super.visitReturnStatement(ctx);
	}
	@Override
	public BaseRec visitIdentifierType(IdentifierTypeContext ctx) {
		System.out.println("VISIT IDDENTIFIERTYPE");
		System.out.println(ctx.getText());
		return super.visitIdentifierType(ctx);
	}
	@Override
	public BaseRec visitClassDeclaration(ClassDeclarationContext ctx) {
		currentClass = ctx.getChild(1).getText();
		System.out.println("Current class: " + currentClass);
		System.out.println("Visit class Declar CurrentScope: " + symtab.getCurrentScopeName());
		symtab.enterScope();
		visit(ctx.getChild(3));
		symtab.exitScope();
		return null; //super.visitClassDeclaration(ctx);
	}
	@Override
	public BaseRec visitAndExpression(AndExpressionContext ctx) {
		// TODO Auto-generated method stub
		/*int numChildren = ctx.getChildCount();
		visit(ctx.getChild(0)); // first factor code
		for(int i= 2; i < numChildren; i+=2){
			visit(ctx.getChild(i));
			if(ctx.getChild(i-1).toString().equals("&&")){
				currentMethod.addInstruction(new Instruction(IAND, null));
			}else if(ctx.getChild(i-1).toString().equals("||")){
				currentMethod.addInstruction(new Instruction(IOR, null));
			}else{
				System.out.println("ERROR ERROR ERROR");
			}
		}		
		return null;*/
		return super.visitAndExpression(ctx);
	}
	@Override
	public BaseRec visitParameter(ParameterContext ctx) {
		System.out.println("VISIT PARAMETR");
		System.out.println(ctx.getText());
		return super.visitParameter(ctx);
	}
	@Override
	public BaseRec visitMultExpression(MultExpressionContext ctx) {
		// TODO Auto-generated method stub
		/*int numChildren = ctx.getChildCount();
		visit(ctx.getChild(0)); // first factor code
		for(int i= 2; i < numChildren; i+=2){
			visit(ctx.getChild(i));
			currentMethod.addInstruction(new Instruction(IMUL, null));
		}
		return null;*/
		return super.visitMultExpression(ctx);
	}
	@Override
	public BaseRec visitPlusExpression(PlusExpressionContext ctx) {
		// TODO Auto-generated method stub
		/*int numChildren = ctx.getChildCount();
		visit(ctx.getChild(0)); // first factor code
		for(int i= 2; i < numChildren; i+=2){
			visit(ctx.getChild(i));
			currentMethod.addInstruction(new Instruction(IADD, null));
		}
		return null;*/
		return super.visitPlusExpression(ctx);
	}
	@Override
	public BaseRec visitMainMethod(MainMethodContext ctx) {
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
	public BaseRec visitStart(StartContext ctx) {
		classFile = new ClassFile();
		System.out.println("Visit start CurrentScope: " + symtab.getCurrentScopeName());
		symtab.enterScope();
		visit(ctx.getChild(0));
		return null; //super.visitStart(ctx);
	}
	@Override
	public BaseRec visitWhileStatement(WhileStatementContext ctx) {
		// TODO Auto-generated method stub
		/*int whilelabel = currentMethod.getIndex();
		visit(ctx.getChild(2)); // generate condition	
		int iffalse = currentMethod.getIndex();
		currentMethod.addInstruction(new Instruction(IF_FALSE, null));
		visit(ctx.getChild(4)); // generate while body				
		Instruction instr = currentMethod.getInstruction(iffalse);
		instr.setArgument(currentMethod.getIndex()+1); // update if_false
		
		int gotolabel = currentMethod.getIndex();
		currentMethod.addInstruction(new Instruction(GOTO, null));
		instr = currentMethod.getInstruction(gotolabel);
		instr.setArgument(whilelabel);
		return null;*/
		return super.visitWhileStatement(ctx);
	}
	@Override
	public BaseRec visitOrExpression(OrExpressionContext ctx) {
		// TODO Auto-generated method stub
		/*int numChildren = ctx.getChildCount();
		visit(ctx.getChild(0)); // first factor code
		for(int i= 2; i < numChildren; i+=2){
			visit(ctx.getChild(i));
			if(ctx.getChild(i-1).toString().equals("&&")){
				currentMethod.addInstruction(new Instruction(IAND, null));
			}else if(ctx.getChild(i-1).toString().equals("||")){
				currentMethod.addInstruction(new Instruction(IOR, null));
			}else{
				System.out.println("ERROR ERROR ERROR");
			}
		}		
		return null;*/
		return super.visitOrExpression(ctx);
	}

	@Override
	public BaseRec visitMethodInvocation(MethodInvocationContext ctx) {
		System.out.println("VISIT METHOD INVOCATION");
		System.out.println(ctx.getText());
		return super.visitMethodInvocation(ctx);
	}
	
	
	
	
	
	
}
