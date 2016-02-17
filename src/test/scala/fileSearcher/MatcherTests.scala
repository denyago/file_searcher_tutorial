package fileSearcher

import java.io.File

import org.scalatest.FlatSpec

class MatcherTests extends FlatSpec{
  val testFilesDirPath = new File("src/test/resources/testfiles").getCanonicalPath

  "Matcher that is passed a file matching the filter" should
  "return a list with that file name" in {
    val matcher = new Matcher("fake", "fakePath")
    val results = matcher.execute()
    assert(results == List("fakePath"))
  }

  "Matcher using a directory containing one file matching the filter" should
  "return a list with that file name" in {
    val matcher = new Matcher("readme", testFilesDirPath)
    val results = matcher.execute()
    assert(results == List("readme.txt"))
  }

  "Matcher that is not passed a root file location" should
  "use the current location" in {
    val matcher = new Matcher("filter")
    assert(matcher.rootLocation == new File(".").getCanonicalPath)
  }

  "Matcher with sub folder checking matching a root location with N subtree files matching" should
  "return a list with those file names" in {
    val matcher = new Matcher("txt", testFilesDirPath)
    val results = matcher.execute()
    assert(results == List("notes.txt", "readme.txt"))
  }

  "Matcher given a path that has one file that matches file filter and content filter" should
  "return a list with that file name" in {
    val matcher = new Matcher("data", testFilesDirPath, Some("content test"))
    val results = matcher.execute()
    assert(results == List("content_tests.data"))
  }

  "Matcher given a path that has no files that matches file filter and content filter" should
    "return an empty list" in {
    val matcher = new Matcher("txt", testFilesDirPath, Some("content test"))
    val results = matcher.execute()
    assert(results == List())
  }
}