package fileSearcher

import java.io.File

class Matcher(filter: String, rootLocation: String) {
  val rootIOObject = FileConverter.convertToIOObject(new File(rootLocation))

  def execute(): List[String] = {
    val matchedFiles = rootIOObject match {
      case file      :  FileObject      if FilterChecker(filter).matches(file.name) => List(file)
      case directory :  DirectoryObject                                             => ???
      case _                                                                        => List()
    }

    matchedFiles map(iOObject => iOObject.name)
  }
}
