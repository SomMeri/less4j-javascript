less4j-javascript
=================

This plugin adds embedded/escaped JavaScript support to [less4j](https://github.com/SomMeri/less4j#readme). Embedded JavaScript is JavaScript snippet closed inside `` `<JavaScript>` `` and escaped JavaScript is JavaScript code closed inside `` ~`<JavaScript>` ``. 

Example less:
```
```

compiles into:
```
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

## Embedded JavaScript

### Differences Against Less.js

