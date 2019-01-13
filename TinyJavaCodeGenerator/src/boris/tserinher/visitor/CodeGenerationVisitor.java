package boris.tserinher.visitor;

import java.lang.reflect.Method;

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
import boris.tserinher.records.Record;
import boris.tserinher.symbolTable.SymbolTable;

public class CodeGenerationVisitor extends MiniJavaGrammarBaseVisitor<Record> {
	
	private SymbolTable symtab; //From previous iteration
	private Method currentMethod; //See visitMethodDecl()
	private String currentClass; //See visitClassDecl()
	private ClassFile classfile; //To be saved on disk
	
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
		// TODO Auto-generated method stub
		return super.visitMethod(ctx);
	}
	@Override
	public Record visitPrintStatement(PrintStatementContext ctx) {
		// TODO Auto-generated method stub
		return super.visitPrintStatement(ctx);
	}
	@Override
	public Record visitField(FieldContext ctx) {
		// TODO Auto-generated method stub
		return super.visitField(ctx);
	}
	@Override
	public Record visitParametersList(ParametersListContext ctx) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return super.visitMainClass(ctx);
	}
	@Override
	public Record visitAssignmentStatement(AssignmentStatementContext ctx) {
		// TODO Auto-generated method stub
		return super.visitAssignmentStatement(ctx);
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
		// TODO Auto-generated method stub
		return super.visitIdentifierType(ctx);
	}
	@Override
	public Record visitClassDeclaration(ClassDeclarationContext ctx) {
		// TODO Auto-generated method stub
		return super.visitClassDeclaration(ctx);
	}
	@Override
	public Record visitAndExpression(AndExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitAndExpression(ctx);
	}
	@Override
	public Record visitParameter(ParameterContext ctx) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return super.visitMainMethod(ctx);
	}
	@Override
	public Record visitStart(StartContext ctx) {
		// TODO Auto-generated method stub
		return super.visitStart(ctx);
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
	
	
	
	
}
