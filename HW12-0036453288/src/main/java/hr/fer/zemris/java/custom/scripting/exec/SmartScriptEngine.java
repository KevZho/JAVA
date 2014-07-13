package hr.fer.zemris.java.custom.scripting.exec;

import java.util.Stack;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.scripting.exec.functions.FunctionFactory;
import hr.fer.zemris.java.custom.scripting.exec.functions.IFunction;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.ITokenVisitor;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Razred <code>SmartScriptEngine</code> omogućava evaluaciju programskog koda.
 * @author Igor Smolkovič
 *
 */
public class SmartScriptEngine {

	/**
	 * Referenca na korijen dokumenta.
	 */
	private DocumentNode documentNode;

	/**
	 * Referenca na requestContext.
	 */
	private RequestContext requestContext;

	/**
	 * Stack koji se koristi prilikom evaluacije.
	 */
	private ObjectMultistack multiStack = new ObjectMultistack();

	/**
	 * Implementacija visitora za obilazak čvorova stabla.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			requestContext.write(node.getText());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variable = node.getVariable().getName();

			String initialValue = node.getStartExpression().asText();
			String endValue = node.getEndExpression().asText();
			String stepString = node.getStepExpression().asText();
			String step = stepString != null ? stepString : "1"; 

			multiStack.push(variable, new ValueWrapper(initialValue));

			while (multiStack.peek(variable).numCompare(endValue) <= 0) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(visitor);
				}
				multiStack.peek(variable).increment(step);
			}
			multiStack.pop(variable);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			Token[] tokens = node.getTokens();
			TokenVisitor tokenVisitor = new TokenVisitor();

			for (int i = 0; i < tokens.length; i++) {
				tokens[i].accept(tokenVisitor);
			}

			Stack<Object> finalStack = tokenVisitor.getStack();
			int size = finalStack.size();
			for (int i = 0; i < size; i++) {
				requestContext.write(finalStack.get(i).toString());
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(visitor);
			}
		}
	};

	/**
	 * Implementacija visitora za obilazak tokena.
	 * @author Igor Smolkovič
	 *
	 */
	private class TokenVisitor implements ITokenVisitor {

		/**
		 * Pomoćni stog koji se koristi kod evaluacije empty tagova.
		 */
		Stack<Object> stack = new Stack<Object>();

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTokenVariable(TokenVariable token) {
			try {
				String name = token.getName();
				Object value = multiStack.peek(name).getValue();
				stack.push(value);
			} catch (Exception e) {
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTokenString(TokenString token) {
			stack.push(token.getValue());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTokenOperator(TokenOperator token) {
			/**
			 * Budući da treba samo podržati +, -, *, /, a to ujedno i podržava
			 * ValueWrapper biti će direktno implementirano ovdje.
			 */
			try {
				ValueWrapper first = new ValueWrapper(stack.pop());
				String symbol = token.getSymbol();
				if (symbol.compareTo("+") == 0) {
					first.increment(stack.pop());
				} else if (symbol.compareTo("-") == 0) {
					first.decrement(stack.pop());
				} else if (symbol.compareTo("*") == 0) {
					first.multiply(stack.pop());
				} else {
					first.divide(stack.pop());
				}
				stack.push(first.getValue());
			} catch (EmptyStackException e) {
			} catch (Exception e) {
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTokenFunction(TokenFunction token) {
			try {
				IFunction function = FunctionFactory.create(token);
				function.execute(stack, requestContext);
			} catch (Exception e) {
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTokenConstantInteger(TokenConstantInteger token) {
			stack.push(token.getValue());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTokenConstantDouble(TokenConstantDouble token) {
			stack.push(token.getValue());
		}

		/**
		 * {@inheritDoc}
		 */
		public Stack<Object> getStack() {
			return stack;
		}
	};

	/**
	 * Konstruktor.
	 * @param documentNode referenca na korijen dokumenta.
	 * @param requestContext referneca na RequestContext.
	 */
	public SmartScriptEngine(DocumentNode documentNode,
			RequestContext requestContext) {
		super();
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Metoda koja pokreće evaluaciju dokumenta.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
