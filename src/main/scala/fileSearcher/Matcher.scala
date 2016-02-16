package fileSearcher

import java.io.File
import scala.annotation.tailrec

class Matcher(filter: String, val rootLocation: String = new File(".").getCanonicalPath) {
  val rootIOObject = FileConverter.convertToIOObject(new File(rootLocation))

  def execute(): List[String] = {

    @tailrec
    def recurciveMatch(files: List[IOObject], matches: List[FileObject]): List[FileObject] = {
      files match {
        case List()           => matches
        case iOObject :: rest =>
          iOObject match {
            case file      : FileObject if FilterChecker(filter).matches(file.name) => recurciveMatch(rest, file :: matches)
            case directory : DirectoryObject => recurciveMatch(rest ::: directory.children(), matches)
            case _         => recurciveMatch(rest, matches)
          }
      }
    }

    recurciveMatch(List(rootIOObject), List()).map(iOObject => iOObject.name)
  }
}
