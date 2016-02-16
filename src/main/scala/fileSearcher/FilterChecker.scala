package fileSearcher

import java.io.File
import scala.util.control.NonFatal

class FilterChecker(filter: String) {

  def matchesFileContent(file: File): Boolean = {
    import scala.io.Source

    try{
      val fileSource = Source.fromFile(file)
      try
        fileSource.getLines().exists(line=>matches(line))
      catch {
         case NonFatal(_) => false
       }
      finally
        fileSource.close()
    } catch {
      case NonFatal(_) => false
    }
  }

  def matches(content: String): Boolean = content contains filter
  
  def findMatchedFiles(iOObjects: List[IOObject]): List[IOObject] =
    for(iOObject <- iOObjects
        if(iOObject.isInstanceOf[FileObject])
        if(matches(iOObject.name)))
    yield iOObject
}

object FilterChecker {
  def apply(filter: String): FilterChecker = new FilterChecker(filter)
}