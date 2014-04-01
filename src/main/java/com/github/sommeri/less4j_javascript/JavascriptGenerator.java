package com.github.sommeri.less4j_javascript;

import java.util.List;

import com.github.sommeri.less4j.EmbeddedLessGenerator;
import com.github.sommeri.less4j.EmbeddedScriptGenerator;
import com.github.sommeri.less4j.LessProblems;
import com.github.sommeri.less4j.core.ast.ASTCssNodeType;
import com.github.sommeri.less4j.core.ast.CssString;
import com.github.sommeri.less4j.core.ast.Expression;
import com.github.sommeri.less4j.core.ast.ListExpression;

public class JavascriptGenerator implements EmbeddedScriptGenerator {

  private EmbeddedLessGenerator defaultScripting = new EmbeddedLessGenerator();
  
  public String toScript(Expression value, LessProblems problemsHandler) {
    if (value.getType()==ASTCssNodeType.LIST_EXPRESSION) {
      return toJsString((ListExpression) value, problemsHandler);
    }
    
    return toNonArrayScript(value, problemsHandler);
  }

  // this should disappear later on
  private String toNonArrayScript(Expression value, LessProblems problemsHandler) {
    if (value.getType()==ASTCssNodeType.STRING_EXPRESSION) {
      return toJsString((CssString) value);
    }

    return defaultScripting.toScript(value, problemsHandler);
  }

  private String toJsString(ListExpression value, LessProblems problemsHandler) {
    List<Expression> split = value.getExpressions();
    if (split.size()==1) {
      Expression expression = split.get(0);
      if (expression instanceof ListExpression) { // I do this to make lesshat work, it is kind of inconsistent
        return toScript(expression, problemsHandler);
      } else {
        return defaultScripting.toScript(value, problemsHandler);
      }
    }
    
    StringBuilder builder = new StringBuilder("[");
    boolean first = true;
    for (Expression expression : split) {
      if (!first)
        builder.append(", ");
      first=false;
      builder.append(toNonArrayScript(expression, problemsHandler));
        
    }
    builder.append("]");
    return builder.toString();
  }

  private String toJsString(CssString value) {
    return "\""+value.getValue()+"\"";
  }

}
