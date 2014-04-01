package com.github.sommeri.less4j_javascript;

import com.github.sommeri.less4j.LessCompiler.Configuration;

public class Less4jJavascript {

  public static Configuration configure(Configuration configuration) {
    configuration.addCustomFunction(new EmbeddedJavascript());
    configuration.addCustomFunction(new EscapedJavascript());
    configuration.setEmbeddedScriptGenerator(new JavascriptGenerator());
    
    return configuration;
  }
}
