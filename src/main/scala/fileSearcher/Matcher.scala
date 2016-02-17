package fileSearcher

import java.io.File
import scala.annotation.tailrec

class Matcher(filter: String, val rootLocation: String = new File(".").getCanonicalPath,
              contentFilter: Option[String] = None) {
  val rootIOObject = FileConverter.convertToIOObject(new File(rootLocation))

  def execute(): List[String] = {

    @tailrec
    def nameMatch(files: List[IOObject], matches: List[FileObject]): List[FileObject] = {
      files match {
        case List()           => matches
        case iOObject :: rest =>
          iOObject match {
            case file      : FileObject if FilterChecker(filter).matches(file.name) => nameMatch(rest, file :: matches)
            case directory : DirectoryObject => nameMatch(rest ::: directory.children(), matches)
            case _         => nameMatch(rest, matches)
          }
      }
    }

    def contentMatch(files: List[FileObject]) = {
      contentFilter match {
        case Some(dataFilter) => files.filter(iOObject => FilterChecker(dataFilter).matchesFileContent(iOObject.file))
        case None             => files
      }
    }


    contentMatch(nameMatch(List(rootIOObject), List())).map(iOObject => iOObject.name)
  }
}
