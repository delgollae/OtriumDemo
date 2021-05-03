# OtriumDemo
OtriumDemo Project Source


# About OtriumDemo

This is the codebase of the Connect to Github profile via GraphQL. <br/><br/>
Project setting up. <br/>
Reference URL : https://graphql.org <br/>
 <br/> <br/>


Step 01 : Required Dependencis For project build.gradle <br/>
********************************************* <br/>

ext.graphql_version = "2.5.6"
    
dependencies {
        classpath "com.android.tools.build:gradle:4.1.2"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        classpath "com.apollographql.apollo:apollo-gradle-plugin:$graphql_version"

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }

Step 02 : Required Dependencis For application build.gradle <br/>
************************************************* <br/>
plugins {
  id("com.apollographql.apollo").version("x.y.z")
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("com.apollographql.apollo:apollo-runtime:x.y.z")

  // optional: if you want to use the normalized cache
  implementation("com.apollographql.apollo:apollo-normalized-cache-sqlite:x.y.z")
  // optional: for coroutines support
  implementation("com.apollographql.apollo:apollo-coroutines-support:x.y.z")
  // optional: for RxJava3 support  
  implementation("com.apollographql.apollo:apollo-rx3-support:x.y.z")
  // optional: Most of apollo-android does not depend on Android in practice and runs on any JVM or on Kotlin native. apollo-android-support contains a few Android-only helper classes. For an example to send logs to logcat or run callbacks on the main thread.
  implementation("com.apollographql.apollo:apollo-android-support:x.y.z")
  // optional: if you just want the generated models and parsers and write your own HTTP code/cache code, you can remove apollo-runtime
  // and use apollo-api instead  
  implementation("com.apollographql.apollo:apollo-api:x.y.z")
}




Step 03 : Get Schema JSON <br/>
************************************************* <br/>
sudo apollo schema:download --endpoint="https://api.github.com/graphql"  schema.json --header="Authorization: Bearer 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"

--Authorization token should be generated from github settings<br/>

Step 04 : After setting up need to process and validate qraphql via graphql explorer<br/>
https://docs.github.com/en/graphql/overview/explorer<br/>


Step 05 : Create .graphql file within the project and place tested GraphQL<br/>

Step 06 : After setting up all required dependencis Build Project to generate class files



