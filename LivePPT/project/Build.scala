import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "LivePPT"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,

    //MySql Connector for Java
    "mysql" % "mysql-connector-java" % "5.1.24",

    //Guice
    "com.google.inject" % "guice" % "3.0"

    //Memcached
    //"com.github.mumoshu" %% "play2-memcached" % "0.3.0.2"
  )

  

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    // Memcached https://github.com/mumoshu/play2-memcached
    //resolvers += "Spy Repository" at "http://files.couchbase.com/maven2" // required to resolve `spymemcached`, the plugin's dependency.
  )

}
