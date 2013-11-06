name := "CloudSlides"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
	javaCore,
	javaJdbc, 
	cache,
	filters, //for enabling Gzip, play.filters.gzip.GzipFilter
	javaEbean,
	"mysql" % "mysql-connector-java" % "5.1.27",
	"com.google.inject" % "guice" % "4.0-beta"
)     

play.Project.playJavaSettings