package fileSearcher

import java.io.File
import scala.util.{Failure, Success, Try}

trait IOObject {
  val file: File
  val name = file.getName
  val path = file.getCanonicalPath
}

case class FileObject(file: File) extends IOObject
case class DirectoryObject(file: File) extends IOObject {
  def children(): List[IOObject] = {
    def iOObjectChildren() = file.listFiles().toList.map(file => FileConverter.convertToIOObject(file))

    Try(iOObjectChildren()) match {
      case Success(l) => l
      case Failure(_) => List()
    }
  }
}
