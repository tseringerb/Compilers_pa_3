package boris.tserinher;

import java.io.IOException;

import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import boris.tserinher.symbolTable.MiniJavaSymbolTable;
import boris.tserinher.symbolTable.SymbolTableListener;
import boris.tserinher.visitor.CodeGenerationVisitor;

public class MainTinyJavaCodeGenerator {

	public static void main(String[] args) {
		String testProgram = args[0];
		
		MiniJavaGrammarLexer lexer = null;
		try {
			lexer = new MiniJavaGrammarLexer( new ANTLRFileStream(testProgram) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MiniJavaGrammarParser parser = new MiniJavaGrammarParser(new BufferedTokenStream(lexer));		 
		MiniJavaGrammarParser.StartContext tree = parser.start();
		
		Trees.inspect(tree, parser);
		
		MiniJavaSymbolTable miniJavaSymbolTable = new MiniJavaSymbolTable();
		ParseTreeWalker walker = new ParseTreeWalker();
		SymbolTableListener listener = new SymbolTableListener(miniJavaSymbolTable);
		walker.walk(listener, tree);
		miniJavaSymbolTable.printTable();
		miniJavaSymbolTable.resetTable();
		
		CodeGenerationVisitor cgv = new CodeGenerationVisitor(miniJavaSymbolTable);
		cgv.visit(tree);
		
	}

}
