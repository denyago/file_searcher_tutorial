package fileSearcher

import java.nio.file.Files
import java.io.File
import scala.io.Source
import scala.util.{Failure, Success, Try}

import org.scalatest.FlatSpec

class SearchResultWriterTests  extends FlatSpec{

  def randomFilePath: String = s"src/test/resources/tmp_${System.currentTimeMillis() / 1000L}.dat"

  "SearchResultWriter when results passed" should
  "save them to a file in a filesystem" in {
    val fileName = randomFilePath
    val results = List(("readme.txt", Some(3)), ("license.txt", None))
    val writer  = SearchResultWriter
    writer.writeToFile(fileName, results)

    val file = new File(fileName)
    val fileSource = Source.fromFile(file)
    val lines = Try(fileSource.getLines().toList)

    fileSource.close()
    Files.delete(file.toPath)

    lines match {
      case Success(lns) => assert(lns == List("readme.txt:3", "license.txt"))
      case Failure(err) => throw err
    }
  }
}
