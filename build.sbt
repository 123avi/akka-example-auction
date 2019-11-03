name := "akka-example-auction"

version := "1.0"

scalaVersion := "2.12.8"

lazy val akkaVersion = "2.5.26"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test

)
