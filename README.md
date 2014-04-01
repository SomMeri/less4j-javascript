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
  margin: 4.61;
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

## Compatibility
Less4j JavaScript is supposed to be as close enough to less.js to compile [LessHat](https://github.com/csshat/lesshat) the same way. However, it does not have to behave exactly the same way in all circumstances. Most important differences are:
* environment,
* less scope accessibility.

**Environment**: Less.js runs either on node.js or inside a browser while less4j JavaScript runs in Rhino. Global variables and functions available only in node.js or browser are not available in Rhino. Following will not work:
```
title: `typeof process.title`; // accessing node.js global variable
``` 

**Scope**: Less.js allows access to local scope using `this.variablename` trick. Less4j JavaScript does not support the same. Following will NOT work:
```
.scope {
    @foo: 42;
    var: `parseInt(this.foo.toJS())`;
}
```

Use interpolation instead:
```
.scope {
    @foo: 42;
    var: `parseInt(@{foo})`;
}
```

compiles into:
```
.scope {
  var: 42;
}
```

In any case, it is supposed to compile [LessHat](https://github.com/csshat/lesshat) the same way as less.js. 

## Embedded JavaScript
TODO


