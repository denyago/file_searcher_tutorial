package fileSearcher

import java.io.File

import org.scalatest.FlatSpec

class FilterCheckerTests extends FlatSpec {
  "FilterChecker passed a list where one file matches the filter" should
  "return a list with that file" in {
    val matchingFile = FileObject(new File("match"))
    val listOfFiles  = List(FileObject(new File("random")), matchingFile)
    val matchedFiles = FilterChecker("match") findMatchedFiles listOfFiles
    assert(matchedFiles == List(matchingFile))
  }

  "FilterChecker passed a list whith a directory that matches the filter" should
  "not return the directory" in {
    val listOfObjects = List(FileObject(new File("random")), DirectoryObject(new File("match")))
    val matchedFiles  = FilterChecker("match") findMatchedFiles listOfObjects
    assert(matchedFiles.length == 0)
  }

  "FilterChecker passed a file with content that matches the filter" should
  "return that the match succeeded" in {
    val isContentMatched = FilterChecker("content test")
                             .matchesFileContent(new File("src/test/resources/testfiles/content_tests.data"))
    assert(isContentMatched == true)
  }

  "FilterChecker passed a file with content that does not match the filter" should
  "return that the match failed" in {
    val isContentMatched = FilterChecker("missing content")
                             .matchesFileContent(new File("src/test/resources/testfiles/content_tests.data"))
    assert(isContentMatched == false)
  }
}