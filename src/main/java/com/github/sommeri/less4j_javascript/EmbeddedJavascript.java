package com.github.sommeri.less4j_javascript;

import java.util.List;

import com.github.sommeri.less4j.LessFunction;
import com.github.sommeri.less4j.LessProblems;
import com.github.sommeri.less4j.core.ast.ASTCssNodeType;
import com.github.sommeri.less4j.core.ast.EmbeddedScript;
import com.github.sommeri.less4j.core.ast.Expression;
import com.github.sommeri.less4j.core.ast.FunctionExpression;

public class EmbeddedJavascript implements LessFunction {
  private final static String NAME = "`";

  public boolean canEvaluate(FunctionExpression call, List<Expression> parameters) {
    return call.getName().equals(NAME) && parameters.size() == 1 && parameters.get(0).getType() == ASTCssNodeType.EMBEDDED_SCRIPT;
  }

  public Expression evaluate(FunctionExpression call, List<Expression> parameters, Expression evaluatedParameter, LessProblems problems) {
    EmbeddedScript js = (EmbeddedScript) parameters.get(0);
    
    JsToExpression compiler = new JsToExpression(true);
    return compiler.compile(call, js, problems);
  }

}
