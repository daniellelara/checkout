import sbt.Keys._
import sbt._

name := "itv-checkout"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "3.0.4").map(_ % "test")