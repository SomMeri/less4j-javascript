package com.github.sommeri.less4j_javascript;

import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.NativeArray;

import com.github.sommeri.less4j.LessProblems;
import com.github.sommeri.less4j.core.ast.CssString;
import com.github.sommeri.less4j.core.ast.EmbeddedScript;
import com.github.sommeri.less4j.core.ast.Expression;
import com.github.sommeri.less4j.core.ast.FaultyExpression;
import com.github.sommeri.less4j.core.ast.FunctionExpression;
import com.github.sommeri.less4j.core.ast.IdentifierExpression;
import com.github.sommeri.less4j.core.ast.ListExpression;
import com.github.sommeri.less4j.core.ast.ListExpressionOperator;
import com.github.sommeri.less4j.core.parser.HiddenTokenAwareTree;
import com.github.sommeri.less4j.utils.PrintUtils;
import com.github.sommeri.less4j_javascript.RhinoCompiler.Result;

public class JsToExpression {

  private static final int MAX_ERROR_SHOW = 30;
  private boolean keepStringQuotes=true;
  
  public JsToExpression(boolean keepStringQuotes) {
    this.keepStringQuotes = keepStringQuotes;
  }

  public Expression compile(FunctionExpression call, EmbeddedScript js, LessProblems problems) {
    RhinoCompiler compiler = new RhinoCompiler();
    Result result = compiler.execute(js.getValue());

    if (result.hasError() || result.getResult() == null) {
      problems.addError(call, "Could not evaluate `" + shorten(js.getValue()) + "` error: " + result.getError());
      return new FaultyExpression(call);
    }

    return toExpression(result.getResult(), call.getUnderlyingStructure(), keepStringQuotes);
  }

  private String shorten(String string) {
    if (string==null || string.isEmpty())
      return string;
    
    int max = string.length();
    if (max>MAX_ERROR_SHOW) {
      string = string.substring(0, MAX_ERROR_SHOW);
      string+="...";
    }
    return string;
  }

  private Expression toExpression(Object result, HiddenTokenAwareTree underlying, boolean keepStringQuotes) {
    if (result instanceof NativeArray) {
      List<Expression> list = toExpressions((NativeArray) result, underlying);
      return combineByComma(list, underlying);
    }
    if (keepStringQuotes && result instanceof String) {
      return new CssString(underlying, result.toString(), "\"");
    }
    if (result instanceof Number) {
      return new CssString(underlying, PrintUtils.formatNumber((Number)result), "");
    }
    
    //PrintUtils.formatNumber(node.getValueAsDouble())
    return new CssString(underlying, result.toString(), "");
  }

  private Expression combineByComma(List<Expression> list, HiddenTokenAwareTree underlying) {
    if (list.isEmpty())
      return new IdentifierExpression(underlying, "");//FIXME!!!!!!!! test
//      return new EmptyExpression(underlying); //FIXME!!!!!!!! test

    return new ListExpression(underlying, list, new ListExpressionOperator(underlying, ListExpressionOperator.Operator.COMMA));
  }

  private List<Expression> toExpressions(NativeArray array, HiddenTokenAwareTree underlying) {
    List<Expression> result = new ArrayList<Expression>();
    int index = 0;
    while (array.has(index, null)) {
      Object value = array.get(index, null);
      result.add(toExpression(value, underlying, false));
      index++;
    }
    return result;
  }

}
