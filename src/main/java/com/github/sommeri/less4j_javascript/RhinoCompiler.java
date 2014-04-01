package com.github.sommeri.less4j_javascript;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Scriptable;

/**
 * Demonstrates how to use Rhino to execute a simple Hello, Rhino JavaScript
 * @author shaines
 */
public class RhinoCompiler {

  public Result execute(String script) {
    // Create and enter a Context. A Context stores information about the execution environment of a script.
    Context cx = Context.enter();
    cx.setOptimizationLevel(-1);
    try {
      // Initialize the standard objects (Object, Function, etc.). This must be done before scripts can be
      // executed. The null parameter tells initStandardObjects 
      // to create and return a scope object that we use
      // in later calls.
      Scriptable scope = cx.initStandardObjects();

      // Execute the script
      Object obj = cx.evaluateString(scope, script, "Embedded Script", 1, null);
      return new Result(obj, null);
    } catch (RhinoException ex) {
      System.err.println(ex.getMessage());
      System.err.println(script);
      return new Result(null, ex.getMessage());
    } finally {
      // Exit the Context. This removes the association between the Context and the current thread and is an
      // essential cleanup action. There should be a call to exit for every call to enter.
      Context.exit();
    }
  }

  public static class Result {
    private final Object result;
    private final String error;

    public Result(Object result, String error) {
      super();
      this.result = result;
      this.error = error;
    }

    public Object getResult() {
      return result;
    }

    public String getError() {
      return error;
    }

    public boolean hasError() {
      return error!=null;
    }

  }

  public static void main(String[] args) throws FileNotFoundException, IOException {
    RhinoCompiler me = new RhinoCompiler();
    System.out.println(me.execute("'hey'.trim()").getResult());
    // ich       me.executeHelloRhino("var s = 'Hello, Rhino'; s;");
//    String test = IOUtils.toString(new FileReader(new File("src\\test\\resources\\test-exact.js")));
    String test = IOUtils.toString(new FileReader(new File("src\\test\\resources\\test.js")));
//    System.out.println(test);
    System.out.println(me.execute(test).getResult());
//    System.out.println(me.execute("(function() { return (1 + 1); })()").getResult());
//    me.execute("\"hello world\"");
//    //      me.executeHelloRhino("typeof process.title");
//    me.execute("(1 + 1 == 2 ? true : false)");
//    me.execute("(function(){var x = 1 + 1;\n      return x})()");
//    me.execute("\"hello\" + \" \" + \"world\"");
//    me.execute("[1, 2, 3].join(', ')");
    //      me.executeHelloRhino("xxxxxxxxxxxxxxxxxxx");
    //      me.executeHelloRhino("xxxxxxxxxxxxxxxxxxx");

    //      me.executeHelloRhino("parseInt(this.foo.toJS())");

  }
}