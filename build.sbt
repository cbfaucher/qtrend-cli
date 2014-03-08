name := "qtrend-cli"

organization := "com.quartz"

version := "1.0"

scalaVersion := "2.10.3"

mainClass := Some("com.quartz.qtrend.cli.QtrendCliApp")

resolvers ++= Seq(
	"Local Maven Repository" at "file://Users/Christian/.m2/repository/",
	"Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
	"org.scalatest" % "scalatest_2.10" % "2.0" % "test",
	"com.quartz" % "qutils-scala_2.10" % "1.0",
	"org.springframework" % "spring-context" % "4.0.2.RELEASE",
	"org.springframework" % "spring-jdbc" % "4.0.2.RELEASE",
	"mysql" % "mysql-connector-java" % "5.1.6",
    	"org.joda" % "joda-convert" % "1.6",
    	"joda-time" % "joda-time" % "2.3"
)
