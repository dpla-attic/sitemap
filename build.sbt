name := "sitemap"

version := "1.0"

scalaVersion := "2.11.8"

val jacksonVersion = "2.8.2"
val jacksonOrg = "com.fasterxml.jackson.core"

libraryDependencies ++= List(
  "jackson-core",
  "jackson-databind"
).map(p => jacksonOrg % p % jacksonVersion)

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"