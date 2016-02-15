package fileSearcher

import org.scalatest.FlatSpec

class FilterCheckerTests extends FlatSpec {
  "FilterChecker passed a list where one file matches the filter" should 
  "return a list with that file" in {
    val matchingFile = new FileObject("match")
    val listOfFiles  = List(new FileObject("random"), matchingFile)
    val matchedFiles = FilterChecker("match") findMatchedFiles listOfFiles
    assert(matchedFiles == List(matchingFile))
  }

  "FilterChecker passed a list whith a directory that matches the filter" should
  "not return the directory" in {
    val listOfObjects = List(new FileObject("random"), new DirectoryObject("match"))
    val matchedFiles  = FilterChecker("match") findMatchedFiles listOfObjects
    assert(matchedFiles.length == 0)
  }
}