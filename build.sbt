ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.5"

lazy val root = (project in file("."))
  .settings(
    name := "asmd-llm-code",
    libraryDependencies += "dev.langchain4j" % "langchain4j" % "1.0.0-beta1",
    libraryDependencies += "dev.langchain4j" % "langchain4j-ollama" % "1.0.0-beta1",
    libraryDependencies += "dev.langchain4j" % "langchain4j-google-ai-gemini" % "1.0.0-beta1",
    libraryDependencies += "com.github.haifengl" %% "smile-scala" % "4.3.0",
    libraryDependencies += "com.github.haifengl" % "smile-plot" % "4.3.0",
    // add Junit for testing (java) last version to run tests
    libraryDependencies += "org.junit.platform" % "junit-platform-launcher" % "1.12.0" % Test,
    libraryDependencies += "org.junit.platform" % "junit-platform-runner" % "1.12.0" % Test,
    libraryDependencies += "org.junit.jupiter" % "junit-jupiter-api" % "5.12.0" % Test,
    libraryDependencies += "org.junit.jupiter" % "junit-jupiter-engine" % "5.12.0" % Test,
    // add junit sbt plugin to run tests
    addCompilerPlugin("org.scalatest" %% "scalatest" % "3.2.19" % Test),
  )
