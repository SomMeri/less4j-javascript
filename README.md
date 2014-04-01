Less4j JavaScript
=================

This plugin adds embedded/escaped JavaScript support to [less4j](https://github.com/SomMeri/less4j#readme). Embedded JavaScript is JavaScript snippet closed inside ticks `` `JavaScript` `` and escaped JavaScript is the same preceded by tilde `~` e.g. `` ~`JavaScript` ``. 

Example less:
```
@number: 100;
@content: "less symbol is < and more symbol is >";
.logaritmic-thing {
  // escaped JavaScript - calculate logarithm
  margin: ~`Math.log(@{number})`; 
  // embedded JavaScript - escape < and > characters
  content: `@{content}.replace(/</g, '&lt;').replace(/>/g, '&gt;')`; 
}
```

compiles into:
```
.logaritmic-thing {
  margin: 4.60517019;
  content: "less symbol is &lt; and more symbol is &gt;";
}
```

## Usage
Add maven dependency into pom.xml:
````
pom.xml todo
````

The `configure` method of `Less4jJavascript` configures less4j to use embedded JavaScript:
````java
//create new less4j configuration object
Configuration configuration = new Configuration()
//add embedded javascript support into it
Less4jJavascript.configure(configuration);

//compile files with embedded javascript
LessCompiler compiler = new DefaultLessCompiler();
CompilationResult result = compiler.compile(new File(less), configuration);
````
## Fair Warning
LessHat should be compatible. 

## Embedded JavaScript

### Differences Against Less.js

