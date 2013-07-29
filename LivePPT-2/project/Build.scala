import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "LivePPT-2"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,

    //mysql connerctor 
    "mysql" % "mysql-connector-java" % "5.1.25",

    //Guice source
    "com.google.inject" % "guice" % "3.0"

    //Memcached 暂时不使用
    //"com.github.mumoshu" %% "play2-memcached" % "0.3.0.2"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
