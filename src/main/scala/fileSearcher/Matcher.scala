package fileSearcher

import java.io.File
import scala.annotation.tailrec

class Matcher(filter: String, val rootLocation: String = new File(".").getCanonicalPath,
              contentFilter: Option[String] = None) {
  val rootIOObject = FileConverter.convertToIOObject(new File(rootLocation))

  def execute(): List[(String, Option[Int])] = {

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
        case Some(dataFilter) =>
          files.map(iOObject =>
            (iOObject, Some(
              FilterChecker(dataFilter).matchesFileContentCount(iOObject.file))))
            .filter(tuple => tuple._2.getOrElse(0) > 0)
        case None             => files.map(iOObject => (iOObject, None))
      }
    }

    contentMatch(nameMatch(List(rootIOObject), List())).map {case (iOObject, count) => (iOObject.name, count)}
  }
}
