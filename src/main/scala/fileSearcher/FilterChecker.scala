package fileSearcher

import java.io.File
import scala.io.{Source, BufferedSource}
import scala.util.{Try, Success, Failure}

class FilterChecker(filter: String) {
  val filterAsRegex = filter.r

  def matchesFileContentCount(file: File): Int = {
    def getFilterMatchCount(content: String): Int =
      (filterAsRegex findAllIn content).length

    def fileContentOccurances(fs: BufferedSource): Int =
      fs.getLines().foldLeft(0)(
          (accumulator, line) => accumulator + getFilterMatchCount(line)
        )

    val fileSource = Try(Source.fromFile(file))
    fileSource match {
      case Success(fs) => {
        Try(fileContentOccurances(fs)) match {
          case Success(n) => n
          case Failure(_) => 0
        }
      }
      case Failure(_) => 0
    }
  }

  def matches(content: String): Boolean =
    filterAsRegex findFirstMatchIn content match {
      case Some(_) => true
      case None    => false
    }

  def findMatchedFiles(iOObjects: List[IOObject]): List[IOObject] =
    for(iOObject <- iOObjects
        if iOObject.isInstanceOf[FileObject]
        if matches(iOObject.name))
    yield iOObject
}

object FilterChecker {
  def apply(filter: String): FilterChecker = new FilterChecker(filter)
}
