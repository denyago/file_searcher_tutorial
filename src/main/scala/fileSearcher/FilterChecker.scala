package fileSearcher

import java.io.File
import scala.util.control.NonFatal

class FilterChecker(filter: String) {
  val filterAsRegex = filter.r

  def matchesFileContentCount(file: File): Int = {
    import scala.io.Source

    def getFilterMatchCount(content: String): Int =
      (filterAsRegex findAllIn content).length

    try{
      val fileSource = Source.fromFile(file)
      try
        fileSource.getLines().foldLeft(0)(
          (accumulator, line) => accumulator + getFilterMatchCount(line)
        )
      catch {
         case NonFatal(_) => 0
       }
      finally
        fileSource.close()
    } catch {
      case NonFatal(_) => 0
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