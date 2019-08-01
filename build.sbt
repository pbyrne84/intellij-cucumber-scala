import com.dancingrobot84.sbtidea.Keys._
import sbt.Keys._

name :=  "Cucumber for Scala"
normalizedName :=  "intellij-cucumber-scala"
version := "2019.2"
scalaVersion :=  "2.12.4"
//https://plugins.jetbrains.com/plugin/7212-cucumber-for-java/update/65391
//https://plugins.jetbrains.com/plugin/1347-scala/update/66462


//https://plugins.jetbrains.com/plugin/9164-gherkin/update/65392
lazy val `scala-plugin` = IdeaPlugin.Zip("scala-plugin", url("https://plugins.jetbrains.com/plugin/download?updateId=66462"))
lazy val `cucumber-java` = IdeaPlugin.Zip("cucumber-java", url("https://plugins.jetbrains.com/plugin/download?updateId=65391"))
lazy val gherkin = IdeaPlugin.Zip("gherkin", url("https://plugins.jetbrains.com/plugin/download?updateId=65392"))
lazy val ideaBuildNumber  = "192.5728.98"

lazy val `cucumber-scala` = project.in(file( "."))
  .enablePlugins(SbtIdeaPlugin)
  .enablePlugins(SbtIdeaPluginPimps)
  .settings(scalariformSettings)
  .settings(
    scalaVersion := "2.12.4",
    javacOptions in Global ++= Seq("-source", "1.6", "-target", "1.6"),
    scalacOptions in Global += "-target:jvm-1.6",
    ideaExternalPlugins ++= Seq(`scala-plugin`, gherkin, `cucumber-java`),
    // check https://s3-eu-west-1.amazonaws.com/intellij-releases/ for valid builds
    ideaBuild in ThisBuild := ideaBuildNumber,
    ideaEdition in ThisBuild := IdeaEdition.Community,
    ideaPublishSettings := PublishSettings(pluginId = "com.github.danielwegener.cucumber-scala", username = "", password = "", channel = None),
    fork in Test := true,
    parallelExecution := true
)

lazy val `runner-cucumber-scala-idea`  = project.in(file(s"idea"))
  .settings(
      autoScalaLibrary := false,
      unmanagedBase := baseDirectory.value / s"$ideaBuildNumber/lib",
      fork in run := true,
      mainClass in(Compile, run) := Some("com.intellij.idea.Main"),
      javaOptions in run ++= Seq(
          "-Xmx800m",
          "-XX:ReservedCodeCacheSize=64m",
          "-XX:MaxPermSize=250m",
          "-XX:+HeapDumpOnOutOfMemoryError",
          "-ea",
          "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005",
          "-Didea.is.internal=true",
          "-Didea.debug.mode=true",
          s"-Didea.plugins.path=idea/$ideaBuildNumber/externalPlugins",
          s"-Didea.config.path=idea/$ideaBuildNumber/system",
          "-Dapple.laf.useScreenMenuBar=true",
          "-Didea.ProcessCanceledException=disabled"
      )
  )



///Users/byrnep/Dev/personal/intellij-cucumber-scala/idea/192.5728.98/plugins/java/lib/java-impl.jar


( unmanagedJars in Compile) += new File("/Users/byrnep/Dev/personal/intellij-cucumber-scala/idea/192.5728.98/plugins/java/lib/java-impl.jar")
