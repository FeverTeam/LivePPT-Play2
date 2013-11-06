name := "CloudSlides"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
	javaCore,
	javaJdbc, 
	cache,
	filters, //for enabling Gzip, play.filters.gzip.GzipFilter
	javaEbean,
	"mysql" % "mysql-connector-java" % "5.1.27", //mysql java driver
	"com.google.inject" % "guice" % "4.0-beta", //guice DI
	"com.amazonaws" % "aws-java-sdk" % "1.6.4", //AWS java sdk
	"org.imgscalr" % "imgscalr-lib" % "4.2" //imgscalr
)     

play.Project.playJavaSettings
