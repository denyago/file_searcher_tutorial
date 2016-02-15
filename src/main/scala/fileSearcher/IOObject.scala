package fileSearcher

/**
  * Created by di on 15/02/2016.
  */
trait IOObject {
  val name: String
}

class FileObject(val name: String) extends IOObject {}
class DirectoryObject(val name: String) extends IOObject {}
