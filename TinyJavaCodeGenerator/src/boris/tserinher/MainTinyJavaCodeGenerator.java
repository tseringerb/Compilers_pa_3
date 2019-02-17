package boris.tserinher;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
		
		
		String delimetr = "\\\\";
		String[] subSrings = testProgram.split(delimetr);
		int subStringIndex = subSrings.length - 1;
		String subString = subSrings[subStringIndex];
		String outputFileName = subString.substring(0, subString.indexOf("."));
		
		
		
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
		
		cgv.getClassFile().print();
		
		try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("C:\\EclipseLuna\\PA_3\\TinyJavaCodeGenerator\\" + outputFileName + ".tjc"))) {
			objectOutputStream.writeObject(cgv.getClassFile());
			System.out.println("DONE");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
