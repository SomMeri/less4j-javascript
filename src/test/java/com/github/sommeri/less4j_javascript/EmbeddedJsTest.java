package com.github.sommeri.less4j_javascript;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.sommeri.less4j.Less4jException;
import com.github.sommeri.less4j.LessCompiler;
import com.github.sommeri.less4j.LessCompiler.CompilationResult;
import com.github.sommeri.less4j.LessCompiler.Configuration;
import com.github.sommeri.less4j.core.ThreadUnsafeLessCompiler;

public class EmbeddedJsTest {

  private static final String lessjs = "src/test/resources/javascript.less";
  private static final String cssLessjs = "src/test/resources/javascript.css";

  private static final String additional = "src/test/resources/additional.less";
  private static final String cssAdditional = "src/test/resources/additional.css";

  private static final String lessHat = "src/test/resources/lesshat-test.less";
  private static final String cssLessHat = "src/test/resources/lesshat-test.css";

  private static final String quick = "src/test/resources/quick-test.less";
  private static final String cssQuick = "src/test/resources/quick-test.css";
  private static final String quick1 = "src/test/resources/quick-test-1.less";
  private static final String cssQuick1 = "src/test/resources/quick-test-1.css";

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @Test
  public void testLessjsJs() throws Less4jException {
    CompilationResult result = compile(lessjs);
    assertEquals(canonize(expectedCss(cssLessjs)), canonize(result.getCss()));
  }

  @Test
  public void testAdditional() throws Less4jException {
    CompilationResult result = compile(additional);
    assertEquals(canonize(expectedCss(cssAdditional)), canonize(result.getCss()));
  }

  @Test
  public void testLesshat() throws Less4jException {
    CompilationResult result = compile(lessHat);
    assertEquals(canonize(expectedCss(cssLessHat)), canonize(result.getCss()));
  }

  @Test
  public void testQuick() throws Less4jException {
    CompilationResult result = compile(quick);
    assertEquals(canonize(expectedCss(cssQuick)), canonize(result.getCss()));
  }

  @Test
  public void testQuick1() throws Less4jException {
    CompilationResult result = compile(quick1);
    assertEquals(canonize(expectedCss(cssQuick1)), canonize(result.getCss()));
  }

  private CompilationResult compile(String less) throws Less4jException {
    Configuration configuration = new Configuration();
    configuration.addCustomFunction(new EmbeddedJavascript());
    configuration.addCustomFunction(new EscapedJavascript());
    configuration.setEmbeddedScriptGenerator(new JavascriptGenerator());
    configuration.setLinkSourceMap(false);

    LessCompiler compiler = new ThreadUnsafeLessCompiler();
    CompilationResult result = compiler.compile(new File(less), configuration);
    return result;
  }

  private String expectedCss(String  css) {
    try {
      return IOUtils.toString(new FileReader(css));
    } catch (Throwable ex) {
      throw new RuntimeException(ex.getMessage(), ex);
    }
  }
  
  protected String canonize(String text) {
    //ignore end of line separator differences
    text = text.replace("\r\n", "\n");

    //ignore differences in various ways to write "1/1"
    text = text.replaceAll("1 */ *1", "1/1");

    //ignore occasional end lines
    while (text.endsWith("\n"))
      text=text.substring(0, text.length()-1);
    
    return text;
  }

}
