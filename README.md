less4j-javascript
=================

This plugin adds embedded JavaScript support to [less4j](https://github.com/SomMeri/less4j#readme). 

## Usage
Add maven dependency into pom.xml:
````
pom.xml todo
````

The `configure` method of `Less4jJavascript` configures less4j to use embedded JavaScript:
````java
//create new configuration object
Configuration configuration = new Configuration()
//add embedded javascript support into it
Less4jJavascript.configure(configuration);

//compile files with embedded javascript
LessCompiler compiler = new DefaultLessCompiler();
CompilationResult result = compiler.compile(new File(less), configuration);
````
 


