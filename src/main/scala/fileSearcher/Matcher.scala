package fileSearcher

import java.io.File
import scala.annotation.tailrec

/**
  * Matches matches
  *
  * @param filter pattern to search in file names.
  * @param rootLocation directory to recursively search from. Defaults to current work directory.
  * @param contentFilter filters files contents. Omitted if not specified.
  */
class Matcher(val filter: String, val rootLocation: String = new File(".").getCanonicalPath,
              val contentFilter: Option[String] = None) {
  val rootIOObject = FileConverter.convertToIOObject(new File(rootLocation))

  override def hashCode: Int = List(filter, rootLocation, contentFilter).
    flatMap(f = (field) => field.toString.getBytes).
    foldLeft(0)((acc, b) => acc + b.toInt)
  override def equals(other: Any): Boolean = other match {
    case that: Matcher =>
      filter == that.filter &&
        rootLocation == that.rootLocation &&
        contentFilter == that.contentFilter
    case _ => false
  }

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
            .filter {case (_, occurrences) => occurrences.getOrElse(0) > 0}
        case None             => files.map(iOObject => (iOObject, None))
      }
    }

    contentMatch(nameMatch(List(rootIOObject), List())).map {case (iOObject, count) => (iOObject.path, count)}
  }
}
