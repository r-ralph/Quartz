# Quartz

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Download](https://api.bintray.com/packages/r-ralph/maven/quartz/images/download.svg) ](https://bintray.com/hr-ralph/maven/quartz/_latestVersion)
[![Build Status](https://travis-ci.org/r-ralph/Quartz.svg?branch=master)](https://travis-ci.org/r-ralph/Quartz)

Quartz is annotation-based library for generating Intent builder.

Easy to construct Intent and remove boilerplate code from your project.

## Usage

### 1. Attach annotations

|Annotation|Required|Description|
|---|---|---|
|`@Quartz`|**✓**|Register an `Activity` for creating Intent builder|
|`@Required(setter = "")`||Annotate a field which is required to creating Intent. `setter` is optional.|
|`@Optional(setter = "")`||Annotate a field which is not required to creating Intent. `setter` is optional.|

```java
@Quartz
public class MainActivity extends AppCompatActivity {

    @Required
    protected String required1;

    @Optional
    protected String optional1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // RESTOREING CODE
    }
}
```

The field annotated with `@Required` or `@Optional` must not be `private`, `static`, `final`.

Exceptionally, `private` modifire is only available when setter method is available. (Looks [here](#use-in-private-field-with-setter))

### 2. Delegate to generated class

`Build -> Make Project` to build sources and generate Intent builder class that named `MainActivityIntentBuilder`.

You must add following restoring code into `onCreate` (commented with `RESTOREING CODE`) of activity class.

```java
MainActivityIntentBuilder.restore(this);
```

### 3. Create Intent

Finally, use builder class and create Intent for launching Activity.

```java
Intent i = MainActivityIntentBuilder.create(this, "foo") // 1st parameter is Context, 2nd and behind parameters are required parameters
        .optional1("bar") // Optional parameters are divided to single method that name is same to field's name
        .build(); // Last, call `build` to make Intent
startActivity(i);
```

### Use in `private` field with setter

If field is annotated with `private` modifier, you can specify settter method name with `setter` attribute.

Setter method is must be returning `void` and parameter is only one and same class with field.

```java
@Quartz
public class MainActivity extends AppCompatActivity {

    @Required(setter = "setRequired1")
    private String required1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityIntentBuilder.restore(this);
    }

    public void setRequired1(String required1){
        this.required1 = required1;
    }
}
```

## Download

### apt(Java)

First, Add following code into your **project** `build.gradle` file:

```groovy
buildscript {
  dependencies {
    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
  }
}
```

Next, Add following code into your **app module** `build.gradle` file:

```groovy
apply plugin: 'android-apt'

dependencies {
  compile 'ms.ralph:quartz:0.0.1'
  apt 'ms.ralph:quartz-compiler:0.0.1'
  provided 'ms.ralph:quartz-compiler:0.0.1'
}
```

### kapt(Kotlin)

If you use this plugin in Kotlin project with kapt, use following code:

```groovy
dependencies {
  compile 'ms.ralph:quartz:0.0.1'
  kapt 'ms.ralph:quartz-compiler:0.0.1'
  provided 'ms.ralph:quartz-compiler:0.0.1'
}
```


## Licence

```
Copyright 2016 Ralph (Tamaki Hidetsugu)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
